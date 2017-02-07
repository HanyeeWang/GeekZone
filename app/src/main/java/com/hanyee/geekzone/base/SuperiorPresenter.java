package com.hanyee.geekzone.base;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;

/**
 * Created by Hanyee on 16/10/18.
 */
public class SuperiorPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
