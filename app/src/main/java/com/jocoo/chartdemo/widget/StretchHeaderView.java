package com.jocoo.chartdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by jocoo on 2018/6/17.
 */

public class StretchHeaderView extends FrameLayout {
  public StretchHeaderView(@NonNull Context context) {
    super(context);
  }

  public StretchHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public StretchHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    Log.i("Jocoo", "onLayout: ");
  }

  @Override
  protected void onAnimationStart() {
    super.onAnimationStart();
    Log.i("Jocoo", "onAnimationStart: ");
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    Log.i("Jocoo", "dispatchDraw: ");
  }
}
