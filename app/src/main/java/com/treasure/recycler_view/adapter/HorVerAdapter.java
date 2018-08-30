package com.treasure.recycler_view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.VerGridBean;
import com.treasure.recycler_view.bean.VerHorBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by treasure on 2017/12/9.
 */

public class HorVerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VerHorBean> list;
    private Context context;
    private LayoutInflater inflater;
    private List<HorVerItemAdapter> adapterList = new ArrayList<>();
    private static final int VIEW_TYPE_DEFAULT = -1;
    private static final int VIEW_TYPE_BANNER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public HorVerAdapter(List<VerHorBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

        addAdapterList();//更新adapter list
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                View view = inflater.inflate(R.layout.layout_hor_ver_recycler, parent, false);
                return new ViewHolderItem(view);
            case VIEW_TYPE_BANNER:
                View segmentView = inflater.inflate(R.layout.layout_hor_ver_banner, parent, false);
                return new ViewHolderBanner(segmentView);
            default:
            case VIEW_TYPE_DEFAULT:
                View viewDefault = inflater.inflate(R.layout.layout_empty_view, parent, false);
                return new RecyclerView.ViewHolder(viewDefault) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        VerHorBean verHorBean = position < list.size() ? list.get(position) : null;
        if (verHorBean == null) {
            return;
        }
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                ViewHolderItem viewHolderItem = (ViewHolderItem) holder;
                bindItemViewHolder(viewHolderItem, verHorBean, position);
                break;
            case VIEW_TYPE_BANNER:
                ViewHolderBanner viewHolderSegment = (ViewHolderBanner) holder;
                viewHolderSegment.image.setImageResource(R.mipmap.pic_beautiful_girl);

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < list.size()) {
            if (position == 0) {
                return VIEW_TYPE_BANNER;
            } else {
                return VIEW_TYPE_ITEM;
            }
        }
        return VIEW_TYPE_DEFAULT;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.main_label_title)
        TextView title;
        @BindView(R.id.main_label_to_more)
        TextView toMore;
        @BindView(R.id.main_label_recycler_view)
        RecyclerView recyclerView;
        @BindView(R.id.main_label_bottom)
        View bottom;

        public ViewHolderItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void bindItemViewHolder(ViewHolderItem viewHolderItem, VerHorBean verHorBean, final int position) {
        viewHolderItem.title.setText(list.get(position).getLabel());
        //设置底部留白
        if (position == list.size() - 1) {
            viewHolderItem.bottom.setVisibility(View.VISIBLE);
        } else {
            viewHolderItem.bottom.setVisibility(View.GONE);
        }

        HorVerItemAdapter mainLabelListAdapter = adapterList.get(position);
        mainLabelListAdapter.setOnShowProgress(new HorVerItemAdapter.OnShowProgress() {
            @Override
            public void showProgress(int position1) {
                iMainLabelClick.toShowProgress(position, position1);
            }
        });
        viewHolderItem.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        viewHolderItem.recyclerView.setAdapter(mainLabelListAdapter);


        viewHolderItem.toMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMainLabelClick.toMoreClick(list.get(position).getLabel(), list.get(position).getListBeen());
            }
        });
    }

    public static class ViewHolderBanner extends RecyclerView.ViewHolder {
        @BindView(R.id.main_banner_image)
        ImageView image;

        public ViewHolderBanner(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface IMainLabelClick {
        void toMoreClick(String title, List<VerGridBean> verGrid);

        void toShowProgress(int positionParent, int position);
    }

    private IMainLabelClick iMainLabelClick;

    public void setiMainLabelClick(IMainLabelClick iMainLabelClick) {
        this.iMainLabelClick = iMainLabelClick;
    }

    /**
     * 刷新 填充数据
     *
     * @param listBeen
     */
    public void setData(List<VerHorBean> listBeen) {
        if (listBeen == null) {
            return;
        }
        list.clear();
        if (listBeen.size() > 0) {
            list.addAll(listBeen);
            addAdapterList();//更新adapter list
        }
        notifyDataSetChanged();
    }

    public void addData(List<VerHorBean> listBeen) {
        if (listBeen == null) {
            return;
        }
        if (listBeen.size() > 0) {
            list.addAll(listBeen);
            addAdapterList();//更新adapter list
        }
        notifyDataSetChanged();
    }

    /**
     * adapter的列表  列表大小取决于 实体类list 的大小
     */
    private void addAdapterList() {
        adapterList.clear();
        for (int i = 0; i < list.size(); i++) {
            HorVerItemAdapter mainLabelListAdapter = new HorVerItemAdapter(list.get(i).getListBeen(), context);
            adapterList.add(mainLabelListAdapter);
        }
        Log.d("~~~~~~~~~", "adapterList:" + adapterList.size());
    }

    public List<HorVerItemAdapter> getAdapterList() {
        return adapterList;
    }
}
