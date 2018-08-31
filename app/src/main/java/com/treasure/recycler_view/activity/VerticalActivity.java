package com.treasure.recycler_view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.treasure.recycler_view.R;
import com.treasure.recycler_view.adapter.VerticalAdapter;
import com.treasure.recycler_view.bean.VerticalBean;
import com.treasure.recycler_view.helper.DragTouchCallBack;
import com.treasure.recycler_view.helper.SpaceItemDecoration;
import com.treasure.recycler_view.utils.Tools;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerticalActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    private THandler handler = new THandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        ButterKnife.bind(this);
        refreshLayout.startRefresh();

        initToolBar();
        initRecyclerView();
        initListener();
    }

    private void initToolBar() {
        toolbar.setTitle("酷炫  RecyclerView");
        toolbar.setTitleTextColor(Color.parseColor("#12171A"));
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        List<VerticalBean> allList = new ArrayList<>();
        allList.add(new VerticalBean("0", "1   < GridView >+上拉下拉+下载进度条"));
        allList.add(new VerticalBean("1", "2   < 横纵向结合 >+上拉下拉+下载进度条+局部更新"));
        allList.add(new VerticalBean("2", "3   GridView+上拉下拉+< Delete >+Report"));
        allList.add(new VerticalBean("3", "4   < 瀑布流 >+上拉下拉+下载进度条"));
        allList.add(new VerticalBean("4", "5   < PagerSnapHelper >+视频播放+视频圆角"));
        allList.add(new VerticalBean("5", "6   < LinearSnapHelper >+视频播放+视频圆角"));
        allList.add(new VerticalBean("6", "7   < Item交换位置 删除Item >"));

        VerticalAdapter detailListAdapter = new VerticalAdapter(allList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(this, 10), 1));
        detailListAdapter.setOnClickListener(new VerticalAdapter.OnClickListener() {
            @Override
            public void onViewClick(int position) {
                switch (position) {
                    case 0:
                        VerGridActivity.start(VerticalActivity.this);
                        break;
                    case 1:
                        HorVerActivity.start(VerticalActivity.this);
                        break;
                    case 2:
                        VerDelReportActivity.start(VerticalActivity.this);
                        break;
                    case 3:
                        WaterFallActivity.start(VerticalActivity.this);
                        break;
                    case 4:
                        SnapHelperActivity.start(VerticalActivity.this,0);
                        break;
                    case 5:
                        SnapHelperActivity.start(VerticalActivity.this,1);
                        break;
                    case 6:
                        DragMoveDelActivity.start(VerticalActivity.this);
                        break;
                }
            }
        });
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(VerticalActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
//                mainListAdapter.setData();
                if (handler != null)
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefreshing();
                        }
                    }, 1000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(VerticalActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
//                mainListAdapter.addData();
                if (handler != null)
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishLoadmore();
                        }
                    }, 1000);
            }
        });
    }

    private static class THandler extends Handler {
        private WeakReference<Activity> activityWeakReference;

        public THandler(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }
    }
}
