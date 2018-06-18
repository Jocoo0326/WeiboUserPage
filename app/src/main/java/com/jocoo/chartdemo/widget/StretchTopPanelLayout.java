package com.jocoo.chartdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.util.Util;

/**
 * Created by jocoo on 2018/6/18.
 */

public class StretchTopPanelLayout extends FrameLayout {
  private int mOriginalStretchHeight =
      Util.dp2px(getContext(), StretchListView.MIN_PANEL_DP);
  private int mMaxStretchDistance =
      Util.dp2px(getContext(), StretchListView.MAX_PANEL_DP);
  private View loading;
  private boolean isRefreshing;
  private ValueAnimator rotateAnim;

  public StretchTopPanelLayout(@NonNull Context context) {
    super(context);
  }

  public StretchTopPanelLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public StretchTopPanelLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    loading = findViewById(R.id.loading);
    rotateAnim = ValueAnimator.ofFloat(0, -360);
    rotateAnim.setDuration(500);
    rotateAnim.setRepeatMode(ValueAnimator.RESTART);
    rotateAnim.setInterpolator(new LinearInterpolator());
    rotateAnim.setRepeatCount(3);
    rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        final float value = (float) animation.getAnimatedValue();
        loading.setRotation(value);
      }
    });
    rotateAnim.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        loading.setRotation(0);
        isRefreshing = false;
      }
    });
  }

  @Override
  public void setLayoutParams(ViewGroup.LayoutParams params) {
    super.setLayoutParams(params);
    if (loading == null) return;
    if (!isRefreshing) {
      float rate = 1f * (params.height - mOriginalStretchHeight)
          / (mMaxStretchDistance - mOriginalStretchHeight);
      if (rate < .9f) {
        float degree = 360 * rate;
        loading.setRotation(degree);
      } else {
        rotateAnim.start();
      }
    }
  }
}
