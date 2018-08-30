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
import com.treasure.recycler_view.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by treasure on 2017/12/6.
 */

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.ViewHolder> {
    private List<VerGridBean> list;
    private Context context;

    public WaterFallAdapter(List<VerGridBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_ver_grid_recycler, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VerGridBean verGridBean = position < list.size() ? list.get(position) : null;
        if (verGridBean == null) {
            return;
        }

        holder.image.setImageResource(list.get(position).getId());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.image.getLayoutParams();
        layoutParams.width = list.get(position).getWidth();
        layoutParams.height = list.get(position).getHeight();
        holder.image.setLayoutParams(layoutParams);
        holder.cover.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) holder.layout.getLayoutParams();
        layoutParams.width = list.get(position).getWidth();
        layoutParams.height = list.get(position).getHeight();
        holder.layout.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.use.getLayoutParams();
        layoutParams.width = list.get(position).getWidth();
        holder.use.setLayoutParams(layoutParams2);

        holder.hotNum.setText(list.get(position).getHotNum() + "");

        if (list.get(position).getType() == 0){
            holder.use.setBackgroundResource(R.drawable.scene_add_item_selector);
            holder.use.setText("ADD");
            holder.use.setTextColor(Color.parseColor("#FF0BA6E8"));
        }else {
            holder.use.setBackgroundResource(R.drawable.scene_use_item_selector);
            holder.use.setText("USE");
            holder.use.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getType() == 0){
                    onShowProgress.showProgress(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_detaillist_image)
        ImageView image;
        @BindView(R.id.item_detaillist_hot_num)
        TextView hotNum;
        @BindView(R.id.item_detaillist_use)
        TextView use;
        @BindView(R.id.item_detaillist_cover)
        View cover;
        @BindView(R.id.item_detaillist_layout)
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnShowProgress {
        void showProgress(int position);
    }

    private OnShowProgress onShowProgress;

    public void setOnShowProgress(OnShowProgress onShowProgress) {
        this.onShowProgress = onShowProgress;
    }
}
