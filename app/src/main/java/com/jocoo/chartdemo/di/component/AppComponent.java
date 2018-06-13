package com.jocoo.chartdemo.di.component;

import com.jocoo.chartdemo.app.App;
import com.jocoo.chartdemo.di.module.AllActivitiesModule;

import dagger.Component;

@Component(modules = {
        AllActivitiesModule.class
})
public interface AppComponent {

    void inject(App app);
}
