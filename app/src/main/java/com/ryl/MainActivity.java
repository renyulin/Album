package com.ryl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ryl.choosephoto.ChoosePhotosActivity;
import com.ryl.choosephoto.ImageItemKeeper;
import com.ryl.choosephoto.PermissionUtils;
import com.ryl.wheel.lib.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity<T> extends AppCompatActivity implements View.OnClickListener {
    private Button camera_btn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera_btn = (Button) findViewById(R.id.camera_btn);
        imageView = (ImageView) findViewById(R.id.main_image);
        findViewById(R.id.data_btn).setOnClickListener(this);
        camera_btn.setOnClickListener(this);
        findViewById(R.id.date_btn).setOnClickListener(this);
//        Util.isOnMainThread()
//        timeSchedule();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_btn:
//                Glide.with(this)
//                        .load("http://p1.pstatp.com/large/166200019850062839d3")
//                        .into(imageView);
                if (PermissionUtils.checkPermissionAllGranted(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, this)) {
                    startActivity(new Intent(this, ChoosePhotosActivity.class));
                } else {
                    PermissionUtils.requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, this, 123);
                }
                break;
            case R.id.date_btn:
                Utils.setTime(view, this);
                break;
            case R.id.data_btn:
                Utils.setData(view, this);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                startActivity(new Intent(this, ChoosePhotosActivity.class));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        allPic();
//        a();
//        b();
//        b2();
        try {
            Log.e("aaaaaaaaaaaaaaaaaa", "str：" + getString());
        } catch (IOException e) {
            Log.e("aaaaaaaaaaaaaaaaaa", "str：empty");
            e.printStackTrace();
        }
        c();
    }

    public String getString() throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);
        String s = br.readLine();
        return s;
    }

    private void allPic() {
        ImageItemKeeper keeper = ImageItemKeeper.getInstance();
        File[] files = keeper.getFileArray(this);
//        keeper.clearSelected();
    }

    int[] ints = new int[]{1, 5, 2, 4, 8, 5, 86, 413, 4, 56, 4, 3, 4, 68, 432, 1, 5, 43, 21, 6, 4, 32, 1, 65, 4, 32, 1, 8, 4, 2, 1, 86, 4, 9, 7, 6, 24, 6, 2, 4, 16, 87, 84, 8};
    private int k = 0;
    private List<String> list = new ArrayList<>();

    /**
     * 插入排休
     */
    private void c() {
        long start = System.nanoTime();
        for (int i = 1; i < ints.length; i++) {
            int temp = ints[i];
            int j = i;
            while (j > 0 && ints[j - 1] > temp) {
                ints[j] = ints[j - 1];
                j--;
                k++;
            }
            ints[j] = temp;
        }
        long end = System.nanoTime();
        Log.e("ssssssssssss", "time:" + k + ";;" + "date:" + (end - start));
        for (int f : ints) {
            Log.e("ssssssssssss", "number:" + f);
        }
    }

    private void a() {//940
        k = 0;
        long start = System.nanoTime();
        for (int i = 0; i < ints.length - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < ints.length - i - 1; j++) {
                if (ints[j] < ints[j + 1]) {
                    replace(j);
                    flag = false;
                }
                k++;
            }
            if (flag) break;
        }
        long end = System.nanoTime();
        Log.e("ssssssssssss", "time:" + k + ";;" + "date:" + (end - start));
        for (int f : ints) {
            Log.e("ssssssssssss", "number:" + f);
        }
    }

    private void replace(int t) {
        replace(t, t + 1);
    }

    private void replace(int t, int l) {
        int temp = ints[t];
        ints[t] = ints[l];
        ints[l] = temp;
    }

    /***
     * 选择排序
     */
    public void b() {
        int l = 0;
        for (int i = 0; i < ints.length - 1; i++) {
            int k = i;
            for (int j = i; j < ints.length - 1; j++) {
                if (ints[k] > ints[j + 1]) {
                    k = j + 1;
                }
//                if (j == i) {//989
//                    temp = ints[j];
//                } else if (temp > ints[j]) {
//                    temp = ints[j];
//                    k = j;
//                }
                l++;
            }
            int temp = ints[k];
            ints[k] = ints[i];
            ints[i] = temp;
        }
        Log.e("ssssssssssss", "time:" + l);
        for (int f : ints) {
            Log.e("ssssssssssss", "number:" + f);
        }
    }

    private void b2() {
        int i, j, k, l = 0;
        long start = System.nanoTime();
        for (i = 0; i < ints.length - 1; i++)   // ier loop
        {
            k = i;                     // kimum
            for (j = i + 1; j < ints.length; j++) { // inner loop
                if (ints[j] < ints[k])         // if k greater,
                    k = j;               // we have a new k
                l++;
            }
            replace(i, k);                // swap them
        }  // end for(i)
        long end = System.nanoTime();
        Log.e("ssssssssssss", "time:" + l + ";;" + "date:" + (end - start));
        for (int f : ints) {
            Log.e("ssssssssssss", "number:" + f);
        }
    }

    /**
     * 定时任务
     */
    private void timeSchedule() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                time();
            }
        }, 1, 1, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();
    }

    /**
     * command：执行线程
     * initialDelay：初始化延时
     * period：前一次执行结束到下一次执行开始的间隔时间（间隔执行延迟时间）
     * unit：计时单位
     */
    private void time() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = format.format(curDate);
        Log.e("currentTimeMillis:", time);
    }

    /**
     * 多线程会冲突
     * delay表示任务延迟多久执行（毫秒级别），
     * period是周期时间，也就是开始以后，每过多久再去执行一
     */
    private void timeSchedule2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time();
            }
        }, 0, 1000);
//        timer.cancel();
    }

    interface A {

    }

    interface B {

    }

    interface C extends A, B {

    }
}














