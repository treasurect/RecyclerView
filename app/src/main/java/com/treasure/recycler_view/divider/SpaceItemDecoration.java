package com.treasure.recycler_view.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by treasure on 2017/11/11.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = SpaceItemDecoration.class.getName();
    private int space;
    private int columnCount;

    public SpaceItemDecoration(int space, int columnCount) {
        this.space = space;
        this.columnCount = columnCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // item position
        switch (columnCount) {
            case 1://纵向1列
                outRect.top = space;
                break;
            case 2://纵向 2列
                if (position == 0 || position == 1) {
                    outRect.top = space * 10 / 16;
                }else {
                    outRect.top = 0;
                }
                outRect.bottom = space * 30 / 16;
                if (position % 2 == 0) {
                    outRect.left = space;
                    outRect.right = space / 2;
                } else {
                    outRect.left = space / 2;
                    outRect.right = space;
                }
                break;
            case 20://横向 1行
                outRect.left = space;
                if (position == 0) {
                    outRect.left = space * 16 / 14;
                }
                outRect.right = 0;
                break;
        }
    }
}
