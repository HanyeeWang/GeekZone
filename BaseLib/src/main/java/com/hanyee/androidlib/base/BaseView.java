package com.hanyee.androidlib.base;

/**
 * Created by Hanyee on 2016/10/14.
 */
public interface BaseView {

    boolean isFirstLoad();

    void showLoading(boolean isFloating, boolean isFirstLoad);

    void retryLoading();

    void finishLoading();

    void showError(String msg, boolean showErrorView);

    void openNightMode(boolean isNight);
}
