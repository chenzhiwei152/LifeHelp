package com.bozhengjianshe.shenghuobang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bozhengjianshe.shenghuobang.R;


public class MaxHeightRecyclerView extends RecyclerView {
    int maxHeight = -1;

    public MaxHeightRecyclerView(Context context) {
        super(context);

    }

    private void init(AttributeSet attrs) {

        TypedArray attribute = getContext().obtainStyledAttributes(attrs,
                R.styleable.MaxHeightRecyclerView);
        maxHeight = attribute.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_maxHeight, -1);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (-1 < maxHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

}
