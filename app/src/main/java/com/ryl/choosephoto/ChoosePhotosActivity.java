package com.ryl.choosephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ryl.R;

import java.util.List;

/**
 * ryl
 * <p>
 * ImageItemKeeper keeper = ImageItemKeeper.getInstance();
 * keeper.switchWorkList(ImageItemKeeper.INDEX_SINGLE);
 * return File[] files = keeper.getFileArray(this);
 * clear data: keeper.clearSelected();
 */
public class ChoosePhotosActivity extends Activity {
    private List<ImageBucket> dataList;
    private AlbumHelper helper;
    public static Bitmap bimap;
    private RecyclerView choose_photos_list;
    private ChoosePhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photos);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        initData();
        initView();
    }

    private void initData() {
        dataList = helper.getImagesBucketList(false);
        bimap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    private void initView() {
        adapter = new ChoosePhotoAdapter(this, dataList);
        choose_photos_list = findViewById(R.id.choose_photos_list);
        choose_photos_list.setLayoutManager(new LinearLayoutManager(this));
        choose_photos_list.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
