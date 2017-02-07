package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.ThemesBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.ThemesContract.Presenter;
import com.hanyee.geekzone.presenter.contract.ThemesContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class ThemesPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public ThemesPresenter(LocalDataSourceManager localDataSourceManager,
                           RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadAllThemes() {
        mRemoteDataSourceManager.getThemes(new RemoteApiCallback<ThemesBean>(this) {
            @Override
            public void onSuccess(ThemesBean response) {
                mView.showAllThemes(response);
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
