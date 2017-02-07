package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.DailyNewsContract.Presenter;
import com.hanyee.geekzone.presenter.contract.DailyNewsContract.View;
import com.hanyee.geekzone.util.DateUtils;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class DailyNewsPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private int mOffsetDay;

    @Inject
    public DailyNewsPresenter(LocalDataSourceManager localDataSourceManager,
                              RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadLatestDailyNews() {
        mRemoteDataSourceManager.getNewsDailyList(new RemoteApiCallback<NewsDailyBean>(this) {
            @Override
            public void onSuccess(NewsDailyBean response) {
                mOffsetDay = 0;
                mView.showDailyNews(response);
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
    public void loadDailyNewsBefore() {
        mRemoteDataSourceManager.getNewsDailyBefore(DateUtils.getBeforeDateString(mOffsetDay++),
                new RemoteApiCallback<NewsDailyBean>(this) {
                    @Override
                    public void onSuccess(NewsDailyBean response) {
                        mView.showMoreDaliyNews(response);
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
