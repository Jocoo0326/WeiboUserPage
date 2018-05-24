package com.jocoo.chartdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private MainPageActivity mContainerActivity;
    private LinearLayoutManager layoutManager;
    int offset = 0;

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
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offset += dy;
                offsetTopPanel(NewsFragment.this, offset, dy);
            }
        });
        return view;
    }

    private void offsetTopPanel(NewsFragment childFragment, int offset, int dy) {
        if (mContainerActivity != null && getUserVisibleHint()) {
            mContainerActivity.offsetTopPanel(childFragment, offset, dy);
        }
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public void offsetRecyclerView(int dy) {
        if (recyclerView != null) {
            recyclerView.scrollBy(0, dy);
        }
    }
}
