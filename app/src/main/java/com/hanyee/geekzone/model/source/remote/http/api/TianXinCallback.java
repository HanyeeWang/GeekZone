package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.androidlib.network.RetrofitResponseCallback;

import retrofit2.Call;
import retrofit2.Response;

public class TianXinCallback<T> extends RetrofitResponseCallback<TianXinResponse<T>, T> {

    public TianXinCallback(RemoteApiCallback<T> callback) {
        super(callback);
    }

    @Override
    protected void onBusinessResponse(RemoteApiCallback<T> callback, Call<TianXinResponse<T>> call,
                                      Response<TianXinResponse<T>> response) {
        if (mCallback.isAttached()) {
            if (response.body().getCode() == 200) {
                callback.onSuccess(response.body().getNewslist());
            } else {
                callback.onError(response.body().getMsg());
            }
        } else {
            mCallback.detachPresenter();
        }
    }
}