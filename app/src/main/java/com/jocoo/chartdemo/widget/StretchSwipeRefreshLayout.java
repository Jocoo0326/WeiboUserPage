package com.jocoo.chartdemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jocoo.chartdemo.util.Util;

public class StretchSwipeRefreshLayout extends BaseSwipeRefreshLayout {


    private static final float DRAG_RATE = .5f;
    private View mPanelLayout;
    private View mLoading;
    private int MAX_DRAG_DISTANCE = Util.dp2px(getContext(), 240);
    private OnPanelHeightChangedListener mOnPanelHeightChangedListener;
    private View target;
    private int originalStretchHeight = Util.dp2px(getContext(), 160);

    public StretchSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public StretchSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StretchSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPanelLayout = findViewWithTag("panel");
        mLoading = findViewWithTag("loading");
        target = findViewWithTag("target");
    }

    @Override
    protected void finishSwipe(float yDiff) {
        ValueAnimator restoreAnim = ValueAnimator.ofFloat(MAX_DRAG_DISTANCE + yDiff * DRAG_RATE, MAX_DRAG_DISTANCE);
        restoreAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                setPanelLayoutHeight((int) val);
            }
        });
        restoreAnim.start();
    }

    private void setPanelLayoutHeight(int heightDst) {
        if (mPanelLayout != null) {
            ViewGroup.LayoutParams params = mPanelLayout.getLayoutParams();
            if (params != null) {
                params.height = heightDst;
                mPanelLayout.setLayoutParams(params);
                if (mOnPanelHeightChangedListener != null) {
                    mOnPanelHeightChangedListener.onPanelHeightChanged(heightDst);
                }
            }
        }
    }

    @Override
    public View findTarget() {
        return findFirstRecyclerView(target);
    }

    private View findFirstRecyclerView(View view) {
        ViewGroup parent;
        if (view != null && view instanceof ViewGroup) {
            parent = (ViewGroup) view;
            for (int i = parent.getChildCount() - 1; i >= 0; i--) {
                final View child = parent.getChildAt(i);
                if (child instanceof RecyclerView) return child;
                View target = findFirstRecyclerView(child);
                if (target != null) return target;
            }
        }
        return null;
    }

    @Override
    protected void onDraggingDown(float yDiff) {
        setPanelLayoutHeight((int) ((MAX_DRAG_DISTANCE + yDiff * DRAG_RATE) * 1));
    }

    public interface OnPanelHeightChangedListener {
        void onPanelHeightChanged(int height);
    }

    public void setOnPanelHeightChangedListener(OnPanelHeightChangedListener mOnPanelHeightChangedListener) {
        this.mOnPanelHeightChangedListener = mOnPanelHeightChangedListener;
    }
}
