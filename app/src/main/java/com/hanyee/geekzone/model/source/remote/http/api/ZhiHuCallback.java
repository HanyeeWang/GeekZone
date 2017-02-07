package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.androidlib.network.RetrofitResponseCallback;

import retrofit2.Call;
import retrofit2.Response;

public class ZhiHuCallback<T> extends RetrofitResponseCallback<T, T> {

    public ZhiHuCallback(RemoteApiCallback<T> callback) {
        super(callback);
    }

    @Override
    protected void onBusinessResponse(RemoteApiCallback<T> mCallback, Call<T> call, Response<T> response) {
        if (mCallback.isAttached()) {
            mCallback.onSuccess(response.body());
        } else {
            mCallback.detachPresenter();
        }
    }

}