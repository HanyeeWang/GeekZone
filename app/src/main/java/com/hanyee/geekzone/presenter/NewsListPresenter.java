package com.hanyee.geekzone.presenter;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.NewsListContract.Presenter;
import com.hanyee.geekzone.presenter.contract.NewsListContract.View;

import java.util.List;

import javax.inject.Inject;

import static com.hanyee.geekzone.util.Constants.TIANXIN.APPKEY;

/**
 * Created by Hanyee on 16/11/2.
 */
public class NewsListPresenter extends SuperiorPresenter<View> implements Presenter {

    private static final int PAGESIZE = 20;

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private int mPageIndex;

    @Inject
    public NewsListPresenter(LocalDataSourceManager localDataSourceManager,
                             RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadNews(String type) {
        mPageIndex = 1;
        mRemoteDataSourceManager.getTianXinNews(type, APPKEY, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<TianXinNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<TianXinNewsBean> response) {
                        mPageIndex++;
                        mView.showNews(response);
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
    public void loadMoreNews(final String type) {
        mRemoteDataSourceManager.getTianXinNews(type, APPKEY, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<TianXinNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<TianXinNewsBean> response) {
                        mPageIndex++;
                        mView.showMoreNews(response);
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
