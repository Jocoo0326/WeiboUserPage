package com.jocoo.chartdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.MainPagerAdapter;
import com.jocoo.chartdemo.base.activity.BaseActivity;
import com.jocoo.chartdemo.contract.MainPageContract;
import com.jocoo.chartdemo.presenter.MainPagePresenter;
import com.jocoo.chartdemo.util.Util;
import com.jocoo.chartdemo.widget.StretchSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class MainPageActivity extends BaseActivity<MainPagePresenter>
        implements MainPageContract.View {
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.topPanelContainer) FrameLayout topPanelContainer;
//    @BindView(R.id.stretchLayout) StretchSwipeRefreshLayout stretchSwipeRefreshLayout;
    private int topPanelMaxOffset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_page;
    }

    private void initData() {
        FragmentPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        topPanelMaxOffset = Util.dp2px(this, 120);
//        stretchSwipeRefreshLayout.setOnPanelHeightChangedListener(new StretchSwipeRefreshLayout.OnPanelHeightChangedListener() {
//            @Override
//            public void onPanelHeightChanged(int height) {
//                final List<Fragment> fragments = getSupportFragmentManager().getFragments();
//                for (Fragment fragment : fragments) {
//                    final NewsFragment newsFragment = (NewsFragment) fragment;
//                    newsFragment.offsetRecyclerViewTopPanel(height);
//                }
//            }
//        });
    }

    public void offsetTopPanel(NewsFragment childFragment, int offset, int dy) {
        if (offset > topPanelMaxOffset && dy < 0) return;
        int lastY = (int) topPanelContainer.getY();
        final int newY = Math.max(-topPanelMaxOffset,
                Math.min(offset <= topPanelMaxOffset ? -offset : (lastY - dy), 0));
        topPanelContainer.setY(newY);
        int deltaY = lastY - newY;
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != childFragment) {
                final NewsFragment newsFragment = (NewsFragment) fragment;
                newsFragment.offsetRecyclerView(deltaY);
            }
        }
    }
}
