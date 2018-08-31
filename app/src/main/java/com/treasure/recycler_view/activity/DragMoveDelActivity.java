package com.treasure.recycler_view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.treasure.recycler_view.R;
import com.treasure.recycler_view.adapter.DragMoveDelAdapter;
import com.treasure.recycler_view.adapter.VerticalAdapter;
import com.treasure.recycler_view.bean.VerticalBean;
import com.treasure.recycler_view.helper.DragTouchCallBack;
import com.treasure.recycler_view.helper.SpaceItemDecoration;
import com.treasure.recycler_view.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragMoveDelActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, DragMoveDelActivity.class);
        context.startActivity(intent);
    }
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_move_del);
        ButterKnife.bind(this);

        initToolBar();
        initRecyclerView();
    }

    private void initToolBar() {
        toolbar.setTitle("Move Delete Item");
        toolbar.setTitleTextColor(Color.parseColor("#12171A"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_icon_return_color);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerView() {
        List<VerticalBean> allList = new ArrayList<>();
        allList.add(new VerticalBean("0", "1   第一个Item"));
        allList.add(new VerticalBean("1", "2   哈哈哈哈"));
        allList.add(new VerticalBean("2", "3   我就是测试的"));
        allList.add(new VerticalBean("3", "4   Android天下第一"));
        allList.add(new VerticalBean("4", "5   Google最牛逼"));
        allList.add(new VerticalBean("5", "6   Android国产手机华为最屌"));
        allList.add(new VerticalBean("6", "7   Android国产手机OPPO最省电"));
        allList.add(new VerticalBean("7", "8   Android国产手机MIUI最好玩"));

        DragMoveDelAdapter dragMoveDelAdapter = new DragMoveDelAdapter(allList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dragMoveDelAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(this, 10), 1));

        DragTouchCallBack dragTouchCallBack = new DragTouchCallBack(dragMoveDelAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
