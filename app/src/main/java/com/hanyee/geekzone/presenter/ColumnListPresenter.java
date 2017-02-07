package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.ColumnAuthorDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnsBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.ColumnListContract.Presenter;
import com.hanyee.geekzone.presenter.contract.ColumnListContract.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class ColumnListPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public ColumnListPresenter(LocalDataSourceManager localDataSourceManager,
                               RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadColumnAuthorInfo(String name) {
        mRemoteDataSourceManager.getColumnAuthorDetail(name, new RemoteApiCallback<ColumnAuthorDetailBean>(this) {
            @Override
            public void onSuccess(ColumnAuthorDetailBean response) {
                mView.showColumnAuthorInfo(response);
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
    public void loadColumnList(String name) {
        mRemoteDataSourceManager.getColumnsOfAuthor(name, 20, 0, new RemoteApiCallback<List<ColumnsBean>>(this) {
            @Override
            public void onSuccess(List<ColumnsBean> response) {
                mView.showColumnList(response);
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
    public void likeZhiHuColumnAuthor(String title, String picUrl, String desc, String authorHref, int followersCount, int postsCount, String type) {
        mLocalDataSourceManager.likeZhiHuColumnAuthor(title, picUrl, desc, authorHref, followersCount, postsCount, type);
    }

    @Override
    public boolean isZhiHuColumnAuthorLiked(String authorHref) {
        return mLocalDataSourceManager.isZhiHuColumnAuthorLiked(authorHref);
    }

    @Override
    public void unlikeZhiHuColumnAuthor(String authorHref) {
        mLocalDataSourceManager.unlikeZhiHuColumnAuthor(authorHref);
    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        mRemoteDataSourceManager = null;
        super.detachView();
    }
}
