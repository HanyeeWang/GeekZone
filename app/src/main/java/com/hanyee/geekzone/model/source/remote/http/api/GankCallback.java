package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.androidlib.network.RetrofitResponseCallback;

import retrofit2.Call;
import retrofit2.Response;

public class GankCallback<T> extends RetrofitResponseCallback<GankResponse<T>, T> {

    public GankCallback(RemoteApiCallback<T> callback) {
        super(callback);
    }

    @Override
    protected void onBusinessResponse(RemoteApiCallback<T> mCallback, Call<GankResponse<T>> call,
                                      Response<GankResponse<T>> response) {
        if (mCallback.isAttached()) {
            if (!response.body().isError()) {
                mCallback.onSuccess(response.body().getResults());
            } else {
                mCallback.onError("Error message!");
            }
        } else {
            mCallback.detachPresenter();
        }
    }
}