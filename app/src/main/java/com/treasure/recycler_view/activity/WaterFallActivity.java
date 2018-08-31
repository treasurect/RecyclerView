package com.treasure.recycler_view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.treasure.recycler_view.R;
import com.treasure.recycler_view.adapter.WaterFallAdapter;
import com.treasure.recycler_view.bean.VerGridBean;
import com.treasure.recycler_view.helper.SpaceItemDecoration;
import com.treasure.recycler_view.utils.Tools;
import com.treasure.recycler_view.widget.LeafLoadView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaterFallActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, WaterFallActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.list_detail_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.list_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_detail_refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.loading_progress_view)
    LeafLoadView loadingView;
    @BindView(R.id.loading_progress_num)
    TextView loadingNum;
    @BindView(R.id.loading_progress_image)
    ImageView loadingImg;
    @BindView(R.id.loading_progress_layout)
    FrameLayout loadingLayout;

    private WaterFallAdapter waterFallAdapter;
    private List<VerGridBean> allList;
    private int widthPixels;
    private int position;
    private int progress = 0;
    private THandler handler = new THandler(this) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    loadingView.setPrograss(msg.arg1);
                    loadingNum.setText(msg.arg1 + "%");
                    if (msg.arg1 == 100) {
                        loadingLayout.setVisibility(View.GONE);
                        progress = 0;
                        loadingView.setPrograss(0);

                        allList.get(position).setType(1);
                        waterFallAdapter.notifyDataSetChanged();
                    }
                    break;
                case 300:
                    refreshLayout.finishRefreshing();
                    break;
                case 301:
                    refreshLayout.finishLoadmore();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_fall);
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        ButterKnife.bind(this);

        initToolBar();
        initRecyclerView();
        initListener();
        setLoadingLayout();

        refreshLayout.startRefresh();
        handler.sendEmptyMessageDelayed(300, 1000);
    }

    @OnClick({R.id.loading_progress_layout})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.loading_progress_layout:
                break;
        }
    }

    private void initToolBar() {
        toolbar.setTitle("WaterFull");
        toolbar.setTitleTextColor(Color.parseColor("#12171A"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_icon_return_color);
    }

    private void initRecyclerView() {
        allList = new ArrayList<>();
        int width = (widthPixels - Tools.dip2px(this, 18)) / 2;
        for (int i = 0; i < 6; i++) {

            VerGridBean verGridBean = new VerGridBean(width, (int) (width * 1.2f), R.mipmap.pic_beautiful_girl, 12345, 0);
            allList.add(verGridBean);
            VerGridBean verGridBean1 = new VerGridBean(width, width, R.mipmap.pic_beautiful_girl, 54321, 1);
            allList.add(verGridBean1);
            VerGridBean verGridBean2 = new VerGridBean(width, (int) (width * 1.8f), R.mipmap.pic_beautiful_girl, 12345, 0);
            allList.add(verGridBean2);
        }
        waterFallAdapter = new WaterFallAdapter(allList, this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(waterFallAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(this, 6), 2));
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        waterFallAdapter.setOnShowProgress(new WaterFallAdapter.OnShowProgress() {
            @Override
            public void showProgress(int position) {
                loadingLayout.setVisibility(View.VISIBLE);
                WaterFallActivity.this.position = position;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(100);
                                progress = progress + 2;
                                Message message = handler.obtainMessage(200);
                                message.arg1 = progress;
                                handler.sendMessage(message);
                                if (progress == 100)
                                    break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(WaterFallActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
//                mainListAdapter.setData();
                handler.sendEmptyMessageDelayed(300, 1000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(WaterFallActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
//                mainListAdapter.addData();
                handler.sendEmptyMessageDelayed(301, 1000);
            }
        });
    }

    private void setLoadingLayout() {
        loadingView.setTotalProgress(100);
        loadingView.rotationView(loadingImg);
    }

    private static class THandler extends Handler {
        private WeakReference<Activity> activityWeakReference;

        public THandler(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }
    }
}
