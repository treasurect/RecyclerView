package com.treasure.recycler_view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.adapter.SnapHelperAdapter;
import com.treasure.recycler_view.bean.SnapHelperBean;
import com.treasure.recycler_view.helper.MediaHelper;
import com.treasure.recycler_view.helper.SpaceItemDecoration;
import com.treasure.recycler_view.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperActivity extends AppCompatActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, SnapHelperActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<SnapHelperBean> snapHelperBeanList;
    private LinearLayoutManager layoutManager;
    private SnapHelperAdapter adapter;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null){
            type = intent.getIntExtra("type",0);
        }
        initViews();
        initData();
        initRecyclerView();
    }

    private void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_snap_helper);
        ButterKnife.bind(this);
    }

    private void initData() {
        //网络视频路径
        String url = "http://ips.ifeng.com/video19.ifeng.com/video09/2017/05/24/4664192-102-008-1012.mp4";

        //数据的初始化
        snapHelperBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            snapHelperBeanList.add(new SnapHelperBean(i, url));
        }
    }

    private void initRecyclerView() {
        //初始化RecyclerView
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 添加分割线
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(SnapHelperActivity.this, 8), 0));
        adapter = new SnapHelperAdapter(this, snapHelperBeanList);
        recyclerView.setAdapter(adapter);
        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            //进行滑动
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取屏幕上显示的第一个条目和最后一个条目的下标
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                //获取播放条目的下标
                int currentPosition = adapter.currentPosition;
                if ((firstVisibleItemPosition > currentPosition || lastVisibleItemPosition < currentPosition) && currentPosition > -1) {
                    //让播放隐藏的条目停止
                    MediaHelper.release();
                    adapter.currentPosition = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        if (type == 0) {//类似ViewPager
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
        }else {//滑动结束最中间item居中
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaHelper.release();
    }
}
