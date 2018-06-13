package com.jocoo.chartdemo.base.presenter;

import com.jocoo.chartdemo.base.view.BaseView;

import io.reactivex.disposables.Disposable;

public interface IPresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

    void addSubscribeDisposable(Disposable disposable);

    void clearSubscribeDisposable();
}
