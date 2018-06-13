package com.jocoo.chartdemo.presenter;

import com.jocoo.chartdemo.base.presenter.BasePresenter;
import com.jocoo.chartdemo.contract.MainPageContract;

import javax.inject.Inject;

public class MainPagePresenter extends BasePresenter<MainPageContract.View>
        implements MainPageContract.Presenter {

//    @Inject
    public MainPagePresenter() {

    }

    @Override
    public void demo() {
        System.out.println("hello");
    }
}
