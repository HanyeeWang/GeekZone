package com.hanyee.androidlib.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitResponseCallback<K, V> implements Callback<K> {

    protected RemoteApiCallback<V> mCallback;

    public RetrofitResponseCallback(RemoteApiCallback<V> mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onResponse(Call<K> call, Response<K> response) {
        if (mCallback == null) {
            throw new NullPointerException("mCallback == null");
        }
        if (response != null && response.body() != null) {
            onBusinessResponse(mCallback, call, response);
        } else {
            if (mCallback.isAttached()) {
                mCallback.onFailure();
            } else {
                mCallback.detachPresenter();
            }
        }
    }

    @Override
    public void onFailure(Call<K> call, Throwable t) {
        if (mCallback == null) {
            throw new NullPointerException("mCallback == null");
        }
        if (mCallback.isAttached()) {
            mCallback.onFailure();
        } else {
            mCallback.detachPresenter();
        }
    }

    protected abstract void onBusinessResponse(RemoteApiCallback<V> mCallback, Call<K> call, Response<K> response);
}