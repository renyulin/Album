package com.ryl.choosephoto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ryl.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * ryl
 */
public class BigImageActivity extends Activity implements View.OnClickListener {
    private TextView big_image_cancel;
    private TextView big_image_choose;
    private PhotoView big_image_view;
    private ImageItemKeeper keeper;
    private ImageItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        initView();
    }

    private void initView() {
        big_image_cancel = findViewById(R.id.big_image_cancel);
        big_image_choose = findViewById(R.id.big_image_choose);
        big_image_cancel.setOnClickListener(this);
        big_image_choose.setOnClickListener(this);
        big_image_view = findViewById(R.id.big_image_view);
        int position = getIntent().getIntExtra("picturePosition", -1);
        int mPosition = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            AlbumHelper helper = AlbumHelper.getHelper();
            helper.init(getApplicationContext());
            List<ImageItem> dataList = helper.getImagesBucketList(false).get(position).imageList;
            item = dataList.get(mPosition);
        }
        File file = new File(item.imagePath);
        Glide.with(this).load(file).into(big_image_view);
        keeper = ImageItemKeeper.getInstance();
//        addPic();
    }

    private void addPic() {
        int mb = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        keeper = ImageItemKeeper.getInstance();
        String url = TextUtils.isEmpty(item.imagePath) ? item.thumbnailPath : item.imagePath;
//        convertToJpg(url, url.substring(0, url.lastIndexOf('.') + 1) + "jpg");
        Log.e("APP_MaxMemory:", item.imagePath + "***" + item.thumbnailPath);
        File file = new File(item.imagePath);
        try {
            File file2 = new File(item.thumbnailPath);
            double mid = file.length() / 1024 / 1024;
            double mid2 = file2.length();
            Log.e("APP_MaxMemory", ":" + mb + ";" + ";mid:" + mid + "-" + mid2);
        } catch (Exception e) {
        }
        Bitmap bmp = BitmapTools.compressImageSize(url);
//        Bitmap bitmap = BitmapFactory.decodeFile(item.thumbnailPath);
        big_image_view.setImageBitmap(bmp);
    }

    private void convertToJpg(String pngFilePath, String jpgFilePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(jpgFilePath));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
        Bitmap bitmap2 = BitmapFactory.decodeFile(pngFilePath);
        big_image_view.setImageBitmap(bitmap2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.big_image_choose:
                if (!keeper.contains(item, this)) {
                    boolean flag = keeper.add(item);
                }
                finish();
                break;
            case R.id.big_image_cancel:
                finish();
                break;
        }
    }
}
