package com.jocoo.chartdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.NewsListAdapter;
import com.jocoo.chartdemo.util.Util;
import com.jocoo.chartdemo.widget.StretchLinearLayoutManager;

public class NewsFragment extends Fragment {

    public static boolean viewPagerChanging;
    private ListView recyclerView;
    private NewsListAdapter mAdapter;
    private MainPageActivity mContainerActivity;
    private StretchLinearLayoutManager layoutManager;
    protected int offset = 0;
    private int topPanelHeight;
    private boolean isIdleState;
    private int lastTop;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContainerActivity = (MainPageActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
//        layoutManager = new StretchLinearLayoutManager(getContext());
//        layoutManager.setRecyclerViewForInit(recyclerView);
//        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("Jocoo", "onScroll: ");
                if (!getUserVisibleHint()) return;
                if (view.getFirstVisiblePosition() == 0) {
                    View child = view.getChildAt(0);
                    if (child != null) {
                        offset = child.getTop();
                        topPanelHeight = child.getHeight();
                    } else {
                        return;
                    }
                } else {
                    offset = -topPanelHeight;
                }
                offsetTopPanel(NewsFragment.this, offset);
            }
        });
        recyclerView.addHeaderView(inflater.inflate(R.layout.top_panel, recyclerView, false));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Jocoo", "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Jocoo", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Jocoo", "onStop: ");
    }

    private void offsetTopPanel(NewsFragment childFragment, int offset) {
        if (mContainerActivity != null && getUserVisibleHint()) {
            mContainerActivity.offsetTopPanel(childFragment, offset);
        }
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public void offsetRecyclerView(int offset, int dy) {
        if (recyclerView != null) {
            Util.compatListScrollBy(recyclerView, dy);
            this.offset = offset;
        }
    }

    public void offsetRecyclerViewTopPanel(int height) {
//        mAdapter.notifyHeightChanged(height);
    }
}
