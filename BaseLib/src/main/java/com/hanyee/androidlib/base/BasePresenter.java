package com.hanyee.androidlib.base;

/**
 * Created by Hanyee on 2016/10/14.
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    boolean isAttached();

    void detachView();
}
