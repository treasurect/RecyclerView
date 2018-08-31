package com.treasure.recycler_view.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.treasure.recycler_view.R;

/**
 * Created by treasure on 2018/8/31.
 */


public class DragTouchCallBack extends Callback {
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 是否长按拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        boolean itemMove = false;
        if (dragTouchListener != null) {
            itemMove = dragTouchListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return itemMove;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (dragTouchListener != null) {
            dragTouchListener.onItemRemove(viewHolder.getAdapterPosition());
        }
    }

    /**
     * 拖动时item的背景颜色
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.color_black_shadow));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 交换数据后停止拖动
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface DragTouchListener {
        boolean onItemMove(int fPos, int tPos);

        boolean onItemRemove(int pos);
    }

    private DragTouchListener dragTouchListener;

    public void setDragTouchListener(DragTouchListener dragTouchListener) {
        this.dragTouchListener = dragTouchListener;
    }

    public DragTouchCallBack(DragTouchListener dragTouchListener) {
        this.dragTouchListener = dragTouchListener;
    }
}
