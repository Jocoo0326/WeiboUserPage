package com.jocoo.chartdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.jocoo.chartdemo.util.Util;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

public class StretchListView extends ListView {
  public static final int MIN_PANEL_DP = 160;
  public static final int MAX_PANEL_DP = 300;
  private final int mTouchSlop;
  private final int mMinimumVelocity;
  private final int mMaximumVelocity;
  private int mOriginalStretchHeight = Util.dp2px(getContext(), MIN_PANEL_DP);
  private int mMaxStretchDistance = Util.dp2px(getContext(), MAX_PANEL_DP);
  private int mActivePointerId = -1;
  private float mInitialDownY;
  private SparseIntArray mChildrenHeight;
  private int lastPosition;
  private MyOnScrollChangeListener myOnScrollChangeListener;
  private boolean isFirstMoveAfterReachedTop = true;
  private View topPanelLayout;
  private float mInitialDownX;
  private VelocityTracker mVelocityTracker;
  private float mVelocityScale = 1f;

  public StretchListView(Context context) {
    this(context, null);
  }

  public StretchListView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StretchListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    mTouchSlop = viewConfiguration.getScaledTouchSlop();
    mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    setOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == 0) return;
        if (mChildrenHeight == null) {
          mChildrenHeight = new SparseIntArray(totalItemCount);
        }
        for (int i = 0; i < visibleItemCount; i++) {
          final View child = getChildAt(i);
          if (child != null) {
            mChildrenHeight.put(firstVisibleItem + i, child.getHeight());
          }
        }
        final int oldPosition = lastPosition;
        int currentPosition = getCurrentScrollPosition();
        if (myOnScrollChangeListener != null) {
          myOnScrollChangeListener.onScrollChangeListener(StretchListView.this,
              currentPosition - oldPosition, currentPosition);
        }
        lastPosition = currentPosition;
      }
    });
  }

  private int getCurrentScrollPosition() {
    int position = 0;
    if (getChildCount() > 0) {
      final int firstVisiblePosition = getFirstVisiblePosition();
      for (int i = 0; i < firstVisiblePosition; i++) {
        position += mChildrenHeight.get(i, 0);
      }
      position -= getChildAt(0).getTop();
    }
    return -position;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    boolean result = super.onTouchEvent(event);
    final int action = event.getActionMasked();
    int pointerIndex;

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        mActivePointerId = event.getPointerId(0);
        break;
      case MotionEvent.ACTION_MOVE: {
        if (!hasReachedToTop()) {
          return result;
        }
        pointerIndex = event.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }
        final float x = event.getX(pointerIndex);
        final float y = event.getY(pointerIndex);
        if (isFirstMoveAfterReachedTop) {
          mInitialDownX = x;
          mInitialDownY = y;
          isFirstMoveAfterReachedTop = false;
          initVelocityTracker();
          mVelocityTracker.addMovement(event);
        }

        final float xDiff = x - mInitialDownX;
        final float yDiff = y - mInitialDownY;
        if (yDiff > 0 && yDiff > mTouchSlop && Math.abs(yDiff) > Math.abs(xDiff)) {
          reachedToTop(yDiff * .5f);
          getParent().requestDisallowInterceptTouchEvent(true);
        }
        return true;
      }
      case MotionEvent.ACTION_POINTER_DOWN: {
        pointerIndex = event.getActionIndex();
        if (pointerIndex < 0) {
          return false;
        }
        mActivePointerId = event.getPointerId(pointerIndex);
        break;
      }
      case MotionEvent.ACTION_POINTER_UP: {
        onSecondaryPointerUp(event);
        break;
      }
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP: {
        pointerIndex = event.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }

        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

        final int initialVelocity = (int)
            (velocityTracker.getYVelocity(mActivePointerId) * mVelocityScale);
        boolean flingVelocity = Math.abs(initialVelocity) > mMinimumVelocity;
        Log.i("Jocoo", "onTouchEvent: fling: " + initialVelocity);
        restoreHeaderView();
        mActivePointerId = INVALID_POINTER;
        isFirstMoveAfterReachedTop = true;
        break;
      }
    }
    return result;
  }

  private void initVelocityTracker() {
    if (mVelocityTracker == null) {
      mVelocityTracker = VelocityTracker.obtain();
    } else {
      mVelocityTracker.clear();
    }
  }

  private void restoreHeaderView() {
    if (getChildCount() == 0) return;
    final View headerView = getChildAt(0);
    int mCurrentStretchHeight = headerView.getHeight();
    if (mCurrentStretchHeight == mOriginalStretchHeight) return;
    ValueAnimator restoreAnim = ValueAnimator.ofFloat(mCurrentStretchHeight, mOriginalStretchHeight);
    restoreAnim.setInterpolator(new AccelerateDecelerateInterpolator());
    restoreAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        Log.i("Jocoo", "onAnimationUpdate: " + getFirstVisiblePosition());
        if (getFirstVisiblePosition() != 0) {
          Util.setViewHeight(topPanelLayout, mOriginalStretchHeight);
          if (topPanelLayout != null) {
            topPanelLayout.setY(-mOriginalStretchHeight);
          }
          return;
        }
        float val = (float) animation.getAnimatedValue();
        final View view = getChildAt(0);
        Util.setViewHeight(view, (int) val);
        Util.setViewHeight(topPanelLayout, (int) val);
      }
    });
    restoreAnim.addListener(new AnimatorListenerAdapter() {
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
      }
    });
    restoreAnim.start();
  }

  private void onSecondaryPointerUp(MotionEvent ev) {
    final int pointerIndex = ev.getActionIndex();
    final int pointerId = ev.getPointerId(pointerIndex);
    if (pointerId == mActivePointerId) {
      final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
      mActivePointerId = ev.getPointerId(newPointerIndex);
    }
  }

  private boolean hasReachedToTop() {
    if (getFirstVisiblePosition() == 0) {
      final View view = getChildAt(0);
      if (view != null && view.getTop() == 0) {
        return true;
      }
    }
    return false;
  }

  private boolean reachedToTop(float dy) {
    if (getFirstVisiblePosition() == 0) {
      final View view = getChildAt(0);
      if (view != null && view.getTop() == 0) {
        int h = Util.clamp((int) (mOriginalStretchHeight + dy), mOriginalStretchHeight, mMaxStretchDistance);
        Util.setViewHeight(view, h);
        Util.setViewHeight(topPanelLayout, h);
        return true;
      }
    }
    return false;
  }

  public void setMyOnScrollChangeListener(MyOnScrollChangeListener myOnScrollChangeListener) {
    this.myOnScrollChangeListener = myOnScrollChangeListener;
  }

  public void setTopPanelLayout(View topPanelLayout) {
    this.topPanelLayout = topPanelLayout;
  }

  public interface MyOnScrollChangeListener {
    void onScrollChangeListener(AbsListView stretchListView, int dy, int currentPosition);
  }
}
