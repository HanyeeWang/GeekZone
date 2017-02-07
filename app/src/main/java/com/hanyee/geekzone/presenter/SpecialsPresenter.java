package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.SpecialsBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.SpecialsContract.Presenter;
import com.hanyee.geekzone.presenter.contract.SpecialsContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class SpecialsPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public SpecialsPresenter(LocalDataSourceManager localDataSourceManager,
                             RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadAllSpecials() {
        mRemoteDataSourceManager.getSpecials(new RemoteApiCallback<SpecialsBean>(this) {
            @Override
            public void onSuccess(SpecialsBean response) {
                mView.showAllSpecials(response.getData());
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
    public void detachView() {
        mLocalDataSourceManager = null;
        mRemoteDataSourceManager = null;
        super.detachView();
    }
}
