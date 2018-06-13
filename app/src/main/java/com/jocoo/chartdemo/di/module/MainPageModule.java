package com.jocoo.chartdemo.di.module;

import com.jocoo.chartdemo.presenter.MainPagePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainPageModule {

    @Provides
    public MainPagePresenter provideMainPagePresenter() {
        return new MainPagePresenter();
    }
}
