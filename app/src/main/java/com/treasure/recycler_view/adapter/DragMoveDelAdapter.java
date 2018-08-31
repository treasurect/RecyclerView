package com.treasure.recycler_view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.VerticalBean;
import com.treasure.recycler_view.helper.DragTouchCallBack;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by treasure on 2017/12/6.
 */

public class DragMoveDelAdapter extends RecyclerView.Adapter<DragMoveDelAdapter.ViewHolder> implements DragTouchCallBack.DragTouchListener {
    private List<VerticalBean> list;
    private Context context;

    public DragMoveDelAdapter(List<VerticalBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_vertical_recycler, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VerticalBean verGridBean = position < list.size() ? list.get(position) : null;
        if (verGridBean == null) {
            return;
        }

        holder.use.setText(verGridBean.getTitle());
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean onItemMove(int fPos, int tPos) {
        Collections.swap(list,fPos,tPos);
        notifyItemMoved(fPos,tPos);
        return true;
    }

    @Override
    public boolean onItemRemove(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView use;
        @BindView(R.id.item_cover)
        View cover;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
