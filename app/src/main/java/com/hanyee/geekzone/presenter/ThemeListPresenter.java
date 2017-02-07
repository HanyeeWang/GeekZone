package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.ThemesListBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.ThemeListContract.Presenter;
import com.hanyee.geekzone.presenter.contract.ThemeListContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class ThemeListPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public ThemeListPresenter(LocalDataSourceManager localDataSourceManager,
                              RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadThemesList(int id) {
        mRemoteDataSourceManager.getThemesDetail(id, new RemoteApiCallback<ThemesListBean>(this) {
            @Override
            public void onSuccess(ThemesListBean response) {
                mView.showThemesList(response);
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
    public void loadMoreThemesList(int id) {

    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        mRemoteDataSourceManager = null;
        super.detachView();
    }
}
