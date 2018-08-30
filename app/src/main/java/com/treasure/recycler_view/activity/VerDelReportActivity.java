package com.treasure.recycler_view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.treasure.recycler_view.adapter.VerDelReportAdapter;
import com.treasure.recycler_view.R;
import com.treasure.recycler_view.bean.VerDelReportBean;
import com.treasure.recycler_view.divider.SpaceItemDecoration;
import com.treasure.recycler_view.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerDelReportActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, VerDelReportActivity.class);
        context.startActivity(intent);
    }
    @BindView(R.id.download_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.download_toolbar)
    Toolbar toolbar;
    @BindView(R.id.download_refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private VerDelReportAdapter myDownLoadListAdapter;
    private List<VerDelReportBean> allList;
    private int widthPixels;
    private PopupWindow popupWindow;
    private int MORE_STATE = 0;// 0: more状态   1：delete 状态  2：report 状态
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_del_report);
        ButterKnife.bind(this);
        widthPixels = getResources().getDisplayMetrics().widthPixels;

        initToolBar();
        initRecyclerView();
        initListener();

        refreshLayout.startRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishRefreshing();
            }
        }, 1000);
    }

    private void initToolBar() {
        toolbar.setTitle("Vertical Delete Report");
        toolbar.setTitleTextColor(Color.parseColor("#12171A"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_icon_return_color);
    }

    private void initRecyclerView() {
        allList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VerDelReportBean verDelReportBean = new VerDelReportBean((widthPixels - Tools.dip2px(this, 48)) / 2, R.mipmap.pic_beautiful_girl);
            allList.add(verDelReportBean);
        }
        myDownLoadListAdapter = new VerDelReportAdapter(allList, this);
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        gridManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(myDownLoadListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(Tools.dip2px(this, 16), 2));
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MORE_STATE != 0) {
                    MORE_STATE = 0;
                    setMenuType(0);
                    myDownLoadListAdapter.setClicked(false);
                } else {
                    onBackPressed();
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (MORE_STATE == 0) {
                    showMoreWindow();
                } else if (MORE_STATE == 1) {
                    List<VerDelReportBean> selectedPhotos = myDownLoadListAdapter.getSelectedPhotos();
                    allList.removeAll(selectedPhotos);

                    setMenuType(0);
                    MORE_STATE = 0;
                    myDownLoadListAdapter.setClicked(false);
                } else if (MORE_STATE == 2) {
                    List<VerDelReportBean> selectedPhotos2 = myDownLoadListAdapter.getSelectedPhotos();
                    if (selectedPhotos2.size() != 0)
                        Toast.makeText(VerDelReportActivity.this, "Thank for your report!", Toast.LENGTH_SHORT).show();

                    MORE_STATE = 0;
                    setMenuType(0);
                    myDownLoadListAdapter.setClicked(false);
                }
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(VerDelReportActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
//                mainListAdapter.setData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                Toast.makeText(VerDelReportActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
//                mainListAdapter.addData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 1000);
            }
        });
    }

    /**
     * more 弹窗
     */
    private void showMoreWindow() {
        View convertView = LayoutInflater.from(this).inflate(R.layout.layout_ver_del_report_more, null);
        popupWindow = new PopupWindow(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        TextView btnDelete = (TextView) convertView.findViewById(R.id.btn_download_more_delete);
        TextView btnReport = (TextView) convertView.findViewById(R.id.btn_download_more_report);
        View layout = convertView.findViewById(R.id.download_more_layout);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();

                setMenuType(1);
                MORE_STATE = 1;
                myDownLoadListAdapter.setClicked(true);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();

                setMenuType(2);
                MORE_STATE = 2;
                myDownLoadListAdapter.setClicked(true);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ver_del_report, null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        if (MORE_STATE != 0) {
            MORE_STATE = 0;
            setMenuType(0);
            myDownLoadListAdapter.setClicked(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_style, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setMenuType(0);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 动态设置 menu 图标
     *
     * @param type
     */
    public void setMenuType(int type) {
        if (type == 0) {//more 状态
            menu.findItem(R.id.toolbar_more).setVisible(true);
            menu.findItem(R.id.toolbar_delete).setVisible(false);
            menu.findItem(R.id.toolbar_report).setVisible(false);
        } else if (type == 1) {//delete 状态
            menu.findItem(R.id.toolbar_more).setVisible(false);
            menu.findItem(R.id.toolbar_delete).setVisible(true);
            menu.findItem(R.id.toolbar_report).setVisible(false);
        } else if (type == 2) {//report 状态
            menu.findItem(R.id.toolbar_more).setVisible(false);
            menu.findItem(R.id.toolbar_delete).setVisible(false);
            menu.findItem(R.id.toolbar_report).setVisible(true);
        }
    }
}
