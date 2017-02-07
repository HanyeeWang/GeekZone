package com.hanyee.geekzone.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.di.component.ActivityComponent;
import com.hanyee.geekzone.di.component.DaggerActivityComponent;
import com.hanyee.geekzone.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public abstract class SuperiorActivity<T extends BasePresenter> extends BaseActivity {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        mIsRequestView = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(GeekZoneApplication.getInstance().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void syncDrawerState(Toolbar toolbar) {
    }

    protected abstract void inject();
}