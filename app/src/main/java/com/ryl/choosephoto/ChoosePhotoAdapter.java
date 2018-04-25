package com.ryl.choosephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ryl.R;

import java.io.File;
import java.util.List;

/**
 * 任玉林
 */
public class ChoosePhotoAdapter extends HeaderViewRecyclerAdapter {
    private List<ImageBucket> dataList;
    private Context context;

    public ChoosePhotoAdapter(Context context, List<ImageBucket> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getHeaderViewCount() {
        return 0;
    }

    @Override
    public int getFooterViewCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_photos, viewGroup, false);
        ChoosePhotoAdapter.ViewHolder holder = new ChoosePhotoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ImageBucket item = dataList.get(position);
        if (item.count == -1) {
            holder.item_choose_photos_count.setVisibility(View.GONE);
        } else {
            holder.item_choose_photos_count.setText("" + item.count);
        }
        holder.item_choose_photos_tv.setText(item.bucketName);
        if (item.imageList != null && item.imageList.size() > 0) {
            String thumbPath = item.imageList.get(0).thumbnailPath;
            String sourcePath = item.imageList.get(0).imagePath;
            File file = new File(sourcePath);
            Glide.with(context).load(file).into(holder.item_choose_photos_pic);
        } else {
            holder.item_choose_photos_pic.setImageBitmap(null);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAlbumActivity.class);
                intent.putExtra(ViewAlbumActivity.INTENT_KEY_TITLE,
                        context.getResources().getString(R.string.page_title_pick_picture));
                intent.putExtra("imagelist", position);
                ((Activity) context).startActivityForResult(intent, 0);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_choose_photos_tv;
        ImageView item_choose_photos_pic;
        TextView item_choose_photos_count;

        public ViewHolder(View itemView) {
            super(itemView);
            item_choose_photos_tv = itemView.findViewById(R.id.item_choose_photos_tv);
            item_choose_photos_pic = itemView.findViewById(R.id.item_choose_photos_pic);
            item_choose_photos_count = itemView.findViewById(R.id.item_choose_photos_count);
        }
    }
}