package com.jocoo.chartdemo.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jocoo.chartdemo.base.presenter.IPresenter;
import com.jocoo.chartdemo.base.view.BaseView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends IPresenter> extends AbstractSimpleActivity
        implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMessage() {

    }
}
