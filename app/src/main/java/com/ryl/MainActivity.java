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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button camera_btn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera_btn = (Button) findViewById(R.id.camera_btn);
        imageView = (ImageView) findViewById(R.id.main_image);
        camera_btn.setOnClickListener(this);
//        Util.isOnMainThread()
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
        b2();
    }

    private void allPic() {
        ImageItemKeeper keeper = ImageItemKeeper.getInstance();
        File[] files = keeper.getFileArray(this);
//        keeper.clearSelected();
    }

    int[] ints = new int[]{1, 5, 2, 4, 8, 5, 86, 413, 4, 56, 4, 3, 4, 68, 432, 1, 5, 43, 21, 6, 4, 32, 1, 65, 4, 32, 1, 8, 4, 2, 1, 86, 4, 9, 7, 6, 24, 6, 2, 4, 16, 87, 84, 8};
    private int k = 0;
    private List<String> list = new ArrayList<>();

    private void a() {//940
        k = 0;
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
        Log.e("ssssssssssss", "time:" + k);
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
        Log.e("ssssssssssss", "time:" + l);
        for (int f : ints) {
            Log.e("ssssssssssss", "number:" + f);
        }
    }
}














