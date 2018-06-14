package com.jocoo.chartdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class StretchLinearLayoutManager extends LinearLayoutManager {

    private int mOriginalStretchHeight;
    private int mMaxStretchDistance;
    private boolean mStretching;
    private int mCurrentStretchHeight;

    public StretchLinearLayoutManager(Context context) {
        super(context);
    }

    public StretchLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public StretchLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setRecyclerViewForInit(RecyclerView view) {
        if (view instanceof StretchRecyclerView) {
            StretchRecyclerView stretchRecyclerView = (StretchRecyclerView) view;
            mOriginalStretchHeight = stretchRecyclerView.getOriginalStretchHeight();
            mMaxStretchDistance = stretchRecyclerView.getMaxStretchDistance();
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                if (getChildCount() == 0) return false;
                final int action = e.getActionMasked();
                if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) && mStretching) {
                    ValueAnimator restoreAnim = ValueAnimator.ofFloat(mCurrentStretchHeight, mOriginalStretchHeight);
                    restoreAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                    restoreAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float val = (float) animation.getAnimatedValue();
                            final View view = getChildAt(0);
                            ViewGroup.LayoutParams params = view.getLayoutParams();
                            if (params != null) {
//                                final int oldHeight = params.height;
                                params.height = (int) val;
                                view.setLayoutParams(params);
//                                view.offsetTopAndBottom();
                            }
                        }
                    });
                    restoreAnim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mStretching = false;
                        }
                    });
                    restoreAnim.start();
                }
                return false;
            }
        });
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int result = super.scrollVerticallyBy(dy, recycler, state);
        if (getChildCount() == 0) return 0;
        boolean reachedTop = reachedTop();
        Log.i("Jocoo", "reached to top: " + reachedTop);
        if (reachedTop) {
            final View view = getChildAt(0);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null) {
                int h, oldHeight;
                oldHeight = h = params.height;
                h = Math.max(h, mOriginalStretchHeight);
                h += -dy;
                h = Math.max(Math.min(h, mMaxStretchDistance), mOriginalStretchHeight);
                params.height = mCurrentStretchHeight = h;
                mStretching = true;
                view.offsetTopAndBottom(oldHeight - h);
                view.setLayoutParams(params);
            }
        }
        return result;
    }


    private boolean reachedTop() {
        return getChildCount() > 0 && findFirstCompletelyVisibleItemPosition() == 0;
    }
}
