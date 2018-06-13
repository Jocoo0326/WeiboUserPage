package com.jocoo.chartdemo.contract;

import com.jocoo.chartdemo.base.presenter.IPresenter;
import com.jocoo.chartdemo.base.view.BaseView;

public interface MainPageContract {
    interface Presenter extends IPresenter<View> {

        void demo();

    }

    interface View extends BaseView {

    }
}
