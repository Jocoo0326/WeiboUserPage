package com.jocoo.chartdemo.di.module;

import com.jocoo.chartdemo.ui.MainPageActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllActivitiesModule {

    @ContributesAndroidInjector(modules = MainPageModule.class)
    abstract MainPageActivity contributeMainPageActivityInjector();
}
