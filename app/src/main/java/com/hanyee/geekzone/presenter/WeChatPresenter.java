package com.hanyee.geekzone.presenter;


import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.WeChatContract.Presenter;
import com.hanyee.geekzone.presenter.contract.WeChatContract.View;

import java.util.List;

import javax.inject.Inject;

import static com.hanyee.geekzone.util.Constants.TIANXIN.APPKEY;
import static com.hanyee.geekzone.util.Constants.TIANXIN.WECHAT;

/**
 * Created by Hanyee on 16/10/14.
 */
public class WeChatPresenter extends SuperiorPresenter<View> implements Presenter {

    private static final int PAGESIZE = 20;

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private int mPageIndex;

    @Inject
    public WeChatPresenter(LocalDataSourceManager localDataSourceManager,
                           RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadWeChatNewsList() {
        mPageIndex = 1;
        mRemoteDataSourceManager.getTianXinNews(WECHAT, APPKEY, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<TianXinNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<TianXinNewsBean> response) {
                        mPageIndex++;
                        mView.showWeChatNewsList(response);
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
    public void loadMoreWeChatNews() {
        mRemoteDataSourceManager.getTianXinNews(WECHAT, APPKEY, PAGESIZE, mPageIndex,
                new RemoteApiCallback<List<TianXinNewsBean>>(this) {
                    @Override
                    public void onSuccess(List<TianXinNewsBean> response) {
                        mPageIndex++;
                        mView.showMoreWeChatNews(response);
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
