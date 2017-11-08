package com.bozhengjianshe.shenghuobang.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 自适应高度的RecyclerView
 */
public class AutoRecyclerView extends RecyclerView {
    public AutoRecyclerView(Context context) {
        super(context);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
