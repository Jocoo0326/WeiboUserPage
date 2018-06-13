package com.jocoo.chartdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.jocoo.chartdemo.util.Util;

public class StretchRecyclerView extends RecyclerView {
    private int mOriginalStretchHeight = Util.dp2px(getContext(), 160);
    private int mMaxStretchDistance = Util.dp2px(getContext(), 240);

    public StretchRecyclerView(Context context) {
        super(context);
    }

    public StretchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StretchRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean result = super.onInterceptTouchEvent(e);
        Log.i("MyRecyclerView", "RecyclerView --> onInterceptTouchEvent: " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean result = super.onTouchEvent(e);
        Log.i("MyRecyclerView", "RecyclerView --> onTouchEvent: " + result);
        return result;
    }

    public int getOriginalStretchHeight() {
        return mOriginalStretchHeight;
    }

    public int getMaxStretchDistance() {
        return mMaxStretchDistance;
    }
}
