package com.jocoo.chartdemo.base.presenter;

import com.jocoo.chartdemo.base.view.BaseView;
import com.jocoo.chartdemo.util.Util;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 关联view并管理disposable的生命周期
 *
 * @param <T> view type
 */
public class BasePresenter<T extends BaseView> implements IPresenter<T> {
    private T mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        Util.requireNonNull(view, "view shouldn't be null");
        this.mView = view;
    }

    @Override
    public void detachView() {
        clearSubscribeDisposable();
        this.mView = null;
    }

    @Override
    public void addSubscribeDisposable(Disposable disposable) {
        Util.requireNonNull(disposable, "disposable shouldn't be null");
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearSubscribeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
