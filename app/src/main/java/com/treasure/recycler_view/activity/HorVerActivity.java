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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.treasure.recycler_view.R;
import com.treasure.recycler_view.adapter.HorVerItemAdapter;
import com.treasure.recycler_view.adapter.HorVerAdapter;
import com.treasure.recycler_view.bean.VerGridBean;
import com.treasure.recycler_view.bean.VerHorBean;
import com.treasure.recycler_view.helper.SpaceItemDecoration;
import com.treasure.recycler_view.utils.ToastUtil;
import com.treasure.recycler_view.utils.Tools;
import com.treasure.recycler_view.widget.LeafLoadView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HorVerActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, HorVerActivity.class);
        context.startActivity(intent);
    }
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_progress_view)
    LeafLoadView loadingView;
    @BindView(R.id.loading_progress_num)
    TextView loadingNum;
    @BindView(R.id.loading_progress_image)
    ImageView loadingImg;
    @BindView(R.id.loading_progress_layout)
    FrameLayout loadingLayout;
    @BindView(R.id.main_refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private List<VerHorBean> mainListBeen;
    private HorVerAdapter mainListAdapter;
    private int position;//点击的item的position
    private int positionParent;//点击的item的父布局 position
    private int progress = 0;//进度条进度
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

                        mainListBeen.get(positionParent).getListBeen().get(position).setType(1);
                        HorVerItemAdapter mainLabelListAdapter = mainListAdapter.getAdapterList().get(positionParent);
                        mainLabelListAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_hor_ver);
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
        toolbar.setTitle("Horizontal Vertical");
        toolbar.setNavigationIcon(R.drawable.toolbar_icon_return_color);
        toolbar.setTitleTextColor(Color.parseColor("#12171A"));
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        mainListBeen = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            List<VerGridBean> detailListBeen = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                detailListBeen.add(new VerGridBean(0, R.mipmap.pic_beautiful_girl, 12345, 0));
            }
            mainListBeen.add(new VerHorBean("Hello", detailListBeen));
        }
        for (int i = 0; i < 3; i++) {
            List<VerGridBean> detailListBeen = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                detailListBeen.add(new VerGridBean(0, R.mipmap.pic_beautiful_girl, 12345, 0));
            }
            mainListBeen.add(new VerHorBean("World", detailListBeen));
        }

        mainListAdapter = new HorVerAdapter(mainListBeen, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(this, 36), 21));
        mainListAdapter.setiMainLabelClick(new HorVerAdapter.IMainLabelClick() {
            @Override
            public void toMoreClick(String title, List<VerGridBean> verGrid) {
                ToastUtil.showToast("More");
            }

            @Override
            public void toShowProgress(int positionParent, int position) {
                loadingLayout.setVisibility(View.VISIBLE);
                HorVerActivity.this.position = position;
                HorVerActivity.this.positionParent = positionParent;
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
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(HorVerActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
//                mainListAdapter.setData();
                handler.sendEmptyMessageDelayed(300, 1000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(HorVerActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
//                mainListAdapter.addData();
                handler.sendEmptyMessageDelayed(301, 1000);
            }
        });
    }

    private void setLoadingLayout() {
        loadingView.setTotalProgress(100);
        loadingView.rotationView(loadingImg);
    }

    private static class THandler extends Handler{
        WeakReference<Activity> mActivityReference;

        public THandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }
    }
}
