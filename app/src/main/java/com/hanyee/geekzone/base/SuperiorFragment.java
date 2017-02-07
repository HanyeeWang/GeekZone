package com.hanyee.geekzone.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanyee.androidlib.base.BaseFragment;
import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.di.component.DaggerFragmentComponent;
import com.hanyee.geekzone.di.component.FragmentComponent;
import com.hanyee.geekzone.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * Created by Hanyee on 2016/10/14.
 */
public abstract class SuperiorFragment<T extends BasePresenter> extends BaseFragment {

    @Inject
    protected T mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mIsRequestView = true;
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(GeekZoneApplication.getInstance().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void inject();
}
