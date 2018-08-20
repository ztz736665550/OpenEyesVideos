package com.example.ztz.openeyesvideos.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 添加了长按点击事件的RecyclerViewAdapter
 * Created by dell on 2015/7/7.
 */
public abstract class BaseRecyclerViewAdapter<mVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<mVH>{

    private static final String TAG = "BaseRecyclerViewAdapter";

    public interface OnMyItemClickLitener {
        void onItemClick(View view, int position);
    }

    public interface OnMyItemLongClickLitener {
        void onItemLongClick(View view, int position);
    }
    public OnMyItemClickLitener onMyItemClickLitener;
    public void setOnMyItemClickLitener(OnMyItemClickLitener onMyItemClickLitener) {
        this.onMyItemClickLitener = onMyItemClickLitener;
    }

    public OnMyItemLongClickLitener onMyItemLongClickLitener;
    public void setOnMyItemLongClickLitener(OnMyItemLongClickLitener onMyItemLongClickLitener) {
        this.onMyItemLongClickLitener = onMyItemLongClickLitener;
    }

    @Override
    public void onBindViewHolder(final mVH vh, int i) {
        // 如果设置了回调，则设置点击事件
        if (onMyItemClickLitener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getPosition();
                    onMyItemClickLitener.onItemClick(vh.itemView, pos);
                }
            });
        }
        //如果设置了长按点击
        if(onMyItemLongClickLitener != null){
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = vh.getPosition();
                    onMyItemLongClickLitener.onItemLongClick(vh.itemView, pos);
                    return true;
                }
            });
        }
    }
}
