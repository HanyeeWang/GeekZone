package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.RecommendAuthorBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.ColumnsContract.Presenter;
import com.hanyee.geekzone.presenter.contract.ColumnsContract.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class ColumnsPresenter extends SuperiorPresenter<View> implements Presenter {

    private static final int PAGESIZE = 20;

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private int mOffset;
    private int mSeed;

    @Inject
    public ColumnsPresenter(LocalDataSourceManager localDataSourceManager,
                            RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadColumnAuthorInfo() {
        mOffset = 0;
        mSeed = 0;
        mRemoteDataSourceManager.getRecommendedAuthorColumns(PAGESIZE, mOffset, mSeed,
                new RemoteApiCallback<List<RecommendAuthorBean>>(this) {
                    @Override
                    public void onSuccess(List<RecommendAuthorBean> response) {
                        mSeed++;
                        mOffset = +PAGESIZE;
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
    public void loadMoreColumnAuthorInfo() {
        mRemoteDataSourceManager.getRecommendedAuthorColumns(PAGESIZE, mOffset, mSeed,
                new RemoteApiCallback<List<RecommendAuthorBean>>(this) {
                    @Override
                    public void onSuccess(List<RecommendAuthorBean> response) {
                        mOffset = +PAGESIZE;
                        mView.showMoreColumnAuthorInfo(response);
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
