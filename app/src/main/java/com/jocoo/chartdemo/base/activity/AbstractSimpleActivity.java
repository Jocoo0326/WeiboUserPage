package com.jocoo.chartdemo.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractSimpleActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindButterKnife();
        onViewCreated();
    }

    private void bindButterKnife() {
        unbinder = ButterKnife.bind(this);
    }

    private void unbindButterKnife() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindButterKnife();
    }

    protected abstract void onViewCreated();

    protected abstract int getLayoutId();
}
