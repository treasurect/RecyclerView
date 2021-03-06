package com.treasure.recycler_view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.SnapHelperBean;
import com.treasure.recycler_view.utils.Tools;
import com.treasure.recycler_view.widget.TextureVideoViewOutlineProvider;
import com.treasure.recycler_view.widget.VideoPlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SnapHelperBean> snapHelperBeanList;

    //记录之前播放的条目下标
    public  int currentPosition = -1;
    public SnapHelperAdapter(Context context, List<SnapHelperBean> snapHelperBeanList) {
        this.context = context;
        this.snapHelperBeanList = snapHelperBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_helper_item, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ListViewHolder viewHolder = (ListViewHolder) holder;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.ivBg.getLayoutParams();
        layoutParams.width = Tools.getScreenWidth(context) - Tools.dip2px(context, 32);
        layoutParams.height = Tools.getScreenHeight(context) - Tools.dip2px(context, 143);
        layoutParams.addRule(Gravity.CENTER);
        viewHolder.ivBg.setLayoutParams(layoutParams);
        viewHolder.videoPlayer.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.ivBg.setOutlineProvider(new TextureVideoViewOutlineProvider(context, Tools.dip2px(context, 6)));
            viewHolder.ivBg.setClipToOutline(true);
            viewHolder.videoPlayer.setOutlineProvider(new TextureVideoViewOutlineProvider(context, Tools.dip2px(context, 6)));
            viewHolder.videoPlayer.setClipToOutline(true);
        }

        //获取到条目对应的数据
        SnapHelperBean info = snapHelperBeanList.get(position);
        //传递给条目里面的MyVideoPlayer
        viewHolder.videoPlayer.setPlayData(info);
        //把条目的下标传递给MyVideoMediaController对象
        viewHolder.videoPlayer.mediaController.setPosition(position);
        //把Adapter对象传递给MyVideoMediaController对象
        viewHolder.videoPlayer.mediaController.setAdapter(this);
        if(position != currentPosition){
            //设置为初始化状态
            viewHolder.videoPlayer.initViewDisplay();
        }
    }

    @Override
    public int getItemCount() {
        return snapHelperBeanList != null ? snapHelperBeanList.size() : 0;
    }

    public void setPlayPosition(int position) {
        currentPosition = position;
    }


    static class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_bg)
        ImageView ivBg;
        @BindView(R.id.video_player)
        VideoPlayerView videoPlayer;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
