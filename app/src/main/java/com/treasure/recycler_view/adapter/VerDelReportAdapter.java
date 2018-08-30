package com.treasure.recycler_view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.VerDelReportBean;
import com.treasure.recycler_view.utils.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by treasure on 2017/12/6.
 */

public class VerDelReportAdapter extends RecyclerView.Adapter<VerDelReportAdapter.PhotoViewHolder> {
    private List<VerDelReportBean> list;
    private Context context;
    private List<VerDelReportBean> selectedPhotos;
    private Set<Integer> selectedPhotoPositions;
    private boolean isDeleteClicked;
    private int widthPixels;

    public VerDelReportAdapter(List<VerDelReportBean> list, Context context) {
        this.list = list;
        this.context = context;
        selectedPhotos = new ArrayList<>();
        selectedPhotoPositions = new HashSet<>();
        widthPixels = (context.getResources().getDisplayMetrics().widthPixels - Tools.dip2px(context, 48)) / 2;
    }

    public List<VerDelReportBean> getSelectedPhotos() {
        return selectedPhotos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_ver_del_report_recycler, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        VerDelReportBean verDelReportBean = position < list.size() ? list.get(position) : null;
        if (verDelReportBean == null) {
            return;
        }

        //解决View复用时的问题
        holder.image.setImageResource(list.get(position).getId());

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.image.getLayoutParams();
        layoutParams.width= widthPixels;
        layoutParams.height = widthPixels;
        holder.image.setLayoutParams(layoutParams);
        holder.cover.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) holder.layout.getLayoutParams();
        layoutParams3.width= widthPixels;
        layoutParams3.height = widthPixels;
        holder.layout.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.use.getLayoutParams();
        layoutParams2.width = widthPixels;
        holder.use.setLayoutParams(layoutParams2);

        if (isDeleteClicked) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.topLayout.setVisibility(View.GONE);
            holder.use.setVisibility(View.GONE);
            if (!selectedPhotoPositions.contains(position)) {
                holder.checkBox.setImageResource(R.mipmap.ic_uncheck);
            } else if (selectedPhotoPositions.contains(position)) {
                holder.checkBox.setImageResource(R.mipmap.ic_check);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
            selectedPhotoPositions.clear();
            selectedPhotos.clear();
            holder.topLayout.setVisibility(View.VISIBLE);
            holder.use.setVisibility(View.VISIBLE);
        }

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeleteClicked){
                    int pos = holder.getAdapterPosition();
                    if (selectedPhotoPositions.contains(pos)) {

                        selectedPhotoPositions.remove(pos);
                        selectedPhotos.remove(list.get(pos));

                        holder.checkBox.setImageResource(R.mipmap.ic_uncheck);

                    } else {
                        selectedPhotoPositions.add(pos);
                        selectedPhotos.add(list.get(pos));
                        holder.checkBox.setImageResource(R.mipmap.ic_check);
                    }
                }
            }
        });
        holder.use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_download_image) ImageView image;
        @BindView(R.id.item_download_checkbox) ImageView checkBox;
        @BindView(R.id.item_download_top) ImageView top;
        @BindView(R.id.item_download_low) ImageView low;
        @BindView(R.id.item_download_top_layout) LinearLayout topLayout;
        @BindView(R.id.item_download_use)
        TextView use;
        @BindView(R.id.item_download_cover) View cover;
        @BindView(R.id.item_download_layout) RelativeLayout layout;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setClicked(boolean clicked) {
        isDeleteClicked = clicked;
        notifyDataSetChanged();
    }
}
