package com.jocoo.chartdemo.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jocoo.chartdemo.R;
import com.jocoo.chartdemo.adapter.MainPagerAdapter;
import com.jocoo.chartdemo.base.activity.BaseActivity;
import com.jocoo.chartdemo.contract.MainPageContract;
import com.jocoo.chartdemo.presenter.MainPagePresenter;
import com.jocoo.chartdemo.util.Util;

import java.util.List;

import butterknife.BindView;

public class MainPageActivity extends BaseActivity<MainPagePresenter>
    implements MainPageContract.View {
  @BindView(R.id.viewPager) ViewPager viewPager;
  @BindView(R.id.topPanelContainer) FrameLayout topPanelContainer;
  //    @BindView(R.id.stretchLayout) StretchSwipeRefreshLayout stretchSwipeRefreshLayout;
  private int topPanelMaxOffset;
  protected int mOffset;

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
    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
      }
    });
  }

  private void initView() {
    topPanelMaxOffset = Util.dp2px(this, 160);
  }

  public void offsetTopPanel(NewsFragment childFragment, int offset, int dy, Point point, int currentPosition) {
    int currentY, newY, deltaY;
    newY = currentY = (int) topPanelContainer.getY();
    if (currentPosition >= -topPanelMaxOffset && currentPosition <= 0) {
      newY = Util.clamp(currentY + dy, -topPanelMaxOffset, 0);
    } else if (currentPosition < -topPanelMaxOffset) {
      if (currentY > -topPanelMaxOffset) {
        newY = Util.clamp(currentY + dy, -topPanelMaxOffset, 0);
      } else {
        newY = -topPanelMaxOffset;
      }
    } else if (currentPosition > 0) {
      newY = 0;
    }
    deltaY = newY - currentY;
    mOffset = newY;
//    Util.setViewHeight(topPanelContainer, point.y - point.x);
    topPanelContainer.setY(newY);
    final List<Fragment> fragments = getSupportFragmentManager().getFragments();
    for (Fragment fragment : fragments) {
      if (fragment != childFragment) {
        final NewsFragment newsFragment = (NewsFragment) fragment;
        newsFragment.offsetRecyclerView(offset, -deltaY);
      }
    }
  }

  public View getTopPanelLayout() {
    return topPanelContainer;
  }
}
