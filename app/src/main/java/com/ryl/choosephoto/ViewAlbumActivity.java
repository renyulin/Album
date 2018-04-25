package com.ryl.choosephoto;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ryl.R;

import java.util.List;

public class ViewAlbumActivity extends Activity {
    public static final String INTENT_KEY_TITLE = "title";
    private List<ImageItem> dataList;
    private AlbumHelper helper;
    private Button activity_view_album_btn;
    private RecyclerView activity_view_album_recycler;
    private ViewAlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);
        initView();
    }

    private void initView() {
        int position = getIntent().getIntExtra("imagelist", -1);
        if (position != -1) {
            helper = AlbumHelper.getHelper();
            helper.init(getApplicationContext());
            dataList = helper.getImagesBucketList(false).get(position).imageList;
        }
        activity_view_album_recycler = findViewById(R.id.activity_view_album_recycler);
        activity_view_album_recycler.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ViewAlbumAdapter(this, dataList, position);
        activity_view_album_recycler.setAdapter(adapter);
        activity_view_album_btn = findViewById(R.id.activity_view_album_btn);
        activity_view_album_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
