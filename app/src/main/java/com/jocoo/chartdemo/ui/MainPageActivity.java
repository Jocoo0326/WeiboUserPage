package com.jocoo.chartdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.MainPagerAdapter;
import com.jocoo.chartdemo.util.Util;

import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FrameLayout topPanelContainer;
    private int topPanelMaxOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initView();
        initData();
    }

    private void initData() {
        FragmentPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        topPanelContainer = findViewById(R.id.topPanelContainer);
        topPanelMaxOffset = Util.dp2px(this, 120);
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
