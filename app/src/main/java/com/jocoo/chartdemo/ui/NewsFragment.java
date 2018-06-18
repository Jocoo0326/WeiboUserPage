package com.jocoo.chartdemo.ui;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.NewsListAdapter;
import com.jocoo.chartdemo.util.Util;
import com.jocoo.chartdemo.widget.StretchListView;

public class NewsFragment extends Fragment {

  private StretchListView listView;
  private NewsListAdapter mAdapter;
  private MainPageActivity mContainerActivity;
  protected int offset = 0;
  private int topPanelHeight;
  private View topPanelLayout;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContainerActivity = (MainPageActivity) getActivity();
    topPanelLayout = mContainerActivity.getTopPanelLayout();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_news, container, false);
    listView = view.findViewById(R.id.recyclerView);
    mAdapter = new NewsListAdapter();
    listView.setAdapter(mAdapter);
    listView.setTopPanelLayout(topPanelLayout);
    listView.setMyOnScrollChangeListener(new StretchListView.MyOnScrollChangeListener() {
      @Override
      public void onScrollChangeListener(AbsListView view, int dy, int currentPosition) {
        if (!getUserVisibleHint()) return;
        int newOffset;
        if (dy == 0) {
          return;
        }
        Point point = new Point();
        if (view.getFirstVisiblePosition() == 0) {
          View child = view.getChildAt(0);
          if (child != null) {
            newOffset = child.getTop();
            topPanelHeight = child.getHeight();
            point.x = child.getTop();
            point.y = child.getBottom();
          } else {
            return;
          }
        } else {
          newOffset = -topPanelHeight;
          point.x = -topPanelHeight;
          point.y = 0;
        }
        offsetTopPanel(NewsFragment.this, dy, point, currentPosition);
        offset = newOffset;
      }
    });
    listView.addHeaderView(inflater.inflate(R.layout.top_panel, listView, false));
    return view;
  }

  private void offsetTopPanel(NewsFragment childFragment, int dy, Point point, int currentPosition) {
    if (mContainerActivity != null && getUserVisibleHint()) {
      mContainerActivity.offsetTopPanel(childFragment, offset, dy, point, currentPosition);
    }
  }

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  public void offsetRecyclerView(int offset, int dy) {
    if (listView != null) {
      Util.compatListScrollBy(listView, dy);
    }
  }
}
