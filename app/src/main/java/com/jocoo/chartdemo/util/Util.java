package com.jocoo.chartdemo.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util {

  private static Method trackMotionScrollMethod;

  public static int dp2px(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  public static int px2dp(Context context, float px) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (px / scale + 0.5f);
  }

  public static <T> T requireNonNull(T obj, String message) {
    if (obj == null)
      throw new NullPointerException(message);
    return obj;
  }

  public static void compatListScrollBy(final ListView recyclerView, int dy) {
    Class clazz = AbsListView.class;
    try {
      if (trackMotionScrollMethod == null) {
        trackMotionScrollMethod = clazz.getDeclaredMethod("trackMotionScroll", int.class, int.class);
        trackMotionScrollMethod.setAccessible(true);
      }
      trackMotionScrollMethod.invoke(recyclerView, -dy, -dy);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static int clamp(int value, int min, int max) {
    return Math.min(Math.max(value, min), max);
  }

  public static void setViewHeight(final View view, final int h) {
    ViewGroup.LayoutParams params;
    if (view == null || (params = view.getLayoutParams()) == null) return;
    params.height = h;
    view.setLayoutParams(params);
  }
}
