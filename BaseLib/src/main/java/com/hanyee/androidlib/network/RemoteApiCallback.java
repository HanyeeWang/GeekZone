package com.hanyee.androidlib.network;


import com.hanyee.androidlib.base.BasePresenter;

public abstract class RemoteApiCallback<T> {

    private BasePresenter mPresenter;

    public RemoteApiCallback(BasePresenter presenter) {
        mPresenter = presenter;
    }

    public boolean isAttached() {
        return mPresenter.isAttached();
    }

    public void detachPresenter() {
        mPresenter = null;
    }

    // 请求数据成功
    public abstract void onSuccess(T response);

    // 请求数据错误
    public abstract void onError(String erro);

    // 网络请求失败
    public abstract void onFailure();
}