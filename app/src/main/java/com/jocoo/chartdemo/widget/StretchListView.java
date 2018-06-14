package com.jocoo.chartdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.jocoo.chartdemo.util.Util;

public class StretchListView extends ListView {
    private int mOriginalStretchHeight =
            Util.dp2px(getContext(), StretchSwipeRefreshLayout.MIN_PANEL_DP);
    private int mMaxStretchDistance =
            Util.dp2px(getContext(), StretchSwipeRefreshLayout.MAX_PANEL_DP);

    public StretchListView(Context context) {
        super(context);
    }

    public StretchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StretchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getOriginalStretchHeight() {
        return mOriginalStretchHeight;
    }

    public int getMaxStretchDistance() {
        return mMaxStretchDistance;
    }
}
