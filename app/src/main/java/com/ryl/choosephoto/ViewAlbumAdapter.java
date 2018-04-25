package com.ryl.choosephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ryl.R;

import java.util.List;

/**
 * rul
 */
public class ViewAlbumAdapter extends HeaderViewRecyclerAdapter {
    private Context mContext;
    private List<ImageItem> dataList;
    private ImageItemKeeper keeper;
    private int picturePosition;
    private int mPosition;

    public ViewAlbumAdapter(Context context, List<ImageItem> list, int picturePosition) {
        this.picturePosition = picturePosition;
        mContext = context;
        keeper = ImageItemKeeper.getInstance();
        if (list == null) {
            dataList = keeper.getWorkingList();
        } else {
            dataList = list;
        }
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_album, viewGroup, false);
        ViewAlbumAdapter.ViewHolder holder = new ViewAlbumAdapter.ViewHolder(view);
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
        ViewAlbumAdapter.ViewHolder holder = (ViewAlbumAdapter.ViewHolder) viewHolder;
        final ImageItem item = dataList.get(position);
        Glide.with(mContext).load(item.imagePath).into(holder.iv);
        updateItemState(holder, item);
        holder.selected.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!keeper.contains(item, mContext)) {
                    keeper.add(item);
                } else {
                    keeper.remove(item);
                }
                if (mPosition == position || mPosition == -1) {
                    notifyItemChanged(position);
                } else {
                    notifyItemChanged(mPosition);
                    notifyItemChanged(position);
                }
                mPosition = position;
            }
        });
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BigImageActivity.class);
                intent.putExtra("picturePosition", picturePosition);
                intent.putExtra("position", position);
                ((Activity) mContext).startActivity(intent);
            }
        });
    }

    private void updateItemState(ViewAlbumAdapter.ViewHolder holder, ImageItem item) {
        if (keeper.contains(item, mContext)) {
            holder.selected.setImageResource(R.drawable.selected_circle);
        } else {
            holder.selected.setImageResource(R.drawable.empty_circle);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private ImageView selected;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.item_view_album_image);
            selected = itemView.findViewById(R.id.item_view_album_selected);
        }
    }
}

