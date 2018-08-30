package com.treasure.recycler_view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.VerGridBean;
import com.treasure.recycler_view.bean.VerHorBean;
import com.treasure.recycler_view.bean.VerticalBean;
import com.treasure.recycler_view.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by treasure on 2017/12/6.
 */

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {
    private List<VerticalBean> list;
    private Context context;

    public VerticalAdapter(List<VerticalBean> list, Context context) {
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
                if (onClickListener != null)
                    onClickListener.onViewClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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

    public interface OnClickListener {
        void onViewClick(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
