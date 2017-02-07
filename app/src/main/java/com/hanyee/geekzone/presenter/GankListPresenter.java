package com.hanyee.geekzone.presenter;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.gank.GankDateNewsBean;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.GankListContract.Presenter;
import com.hanyee.geekzone.presenter.contract.GankListContract.View;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/11/2.
 */
public class GankListPresenter extends SuperiorPresenter<View> implements Presenter {

    private static final int PAGESIZE = 20;

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private int mPageIndex;

    @Inject
    public GankListPresenter(LocalDataSourceManager localDataSourceManager,
                             RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadGankNewsByCategory(String catrgory) {
        mPageIndex = 1;
        mRemoteDataSourceManager.getGankNewsByCategory(catrgory, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<GankNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<GankNewsBean> response) {
                        mPageIndex++;
                        mView.showGankNews(response);
                    }

                    @Override
                    public void onError(String erro) {
                        mView.showError(erro, false);
                    }

                    @Override
                    public void onFailure() {
                        mView.showError("", false);
                    }
                });
    }

    @Override
    public void loadMoreGankNewsByCategory(String catrgory) {
        mRemoteDataSourceManager.getGankNewsByCategory(catrgory, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<GankNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<GankNewsBean> response) {
                        mPageIndex++;
                        mView.showMoreGankNews(response);
                    }

                    @Override
                    public void onError(String erro) {
                        mView.showError(erro, true);
                    }

                    @Override
                    public void onFailure() {
                        mView.showError("", true);
                    }
                });
    }

    @Override
    public void loadGankNewsOfOneDay(int year, int month, int day) {
        mRemoteDataSourceManager.getGankNewsOfSomeday(year, month, day, new RemoteApiCallback<GankDateNewsBean>(this) {
            @Override
            public void onSuccess(GankDateNewsBean response) {
                //TODO:
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, true);
            }

            @Override
            public void onFailure() {
                mView.showError("", true);
            }
        });
    }

    @Override
    public void loadRecommendGankNews(String catrgory, int num) {
        mRemoteDataSourceManager.getRecommendGankNews(catrgory, num, new RemoteApiCallback<List<GankNewsBean>>(this) {
            @Override
            public void onSuccess(List<GankNewsBean> response) {
                mView.showGankNews(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, true);
            }

            @Override
            public void onFailure() {
                mView.showError("", true);
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
