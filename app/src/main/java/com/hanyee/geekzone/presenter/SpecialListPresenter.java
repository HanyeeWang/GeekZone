package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.SpecialListBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.SpecialListContract.Presenter;
import com.hanyee.geekzone.presenter.contract.SpecialListContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class SpecialListPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public SpecialListPresenter(LocalDataSourceManager localDataSourceManager,
                                RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadSpecialsList(int id) {
        mRemoteDataSourceManager.getSpecialList(id, new RemoteApiCallback<SpecialListBean>(this) {
            @Override
            public void onSuccess(SpecialListBean response) {
                mView.showSpecialsList(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, mView.isFirstLoad());
            }

            @Override
            public void onFailure() {
                mView.showError("", mView.isFirstLoad());
            }
        });
    }

    @Override
    public void loadMoreSpecialsList(int id) {

    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        mRemoteDataSourceManager = null;
        super.detachView();
    }
}
