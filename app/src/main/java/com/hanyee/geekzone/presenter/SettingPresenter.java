package com.hanyee.geekzone.presenter;


import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.presenter.contract.SettingContract.Presenter;
import com.hanyee.geekzone.presenter.contract.SettingContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class SettingPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;

    @Inject
    public SettingPresenter(LocalDataSourceManager localDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
    }

    @Override
    public void setNightModeState(boolean state) {
        mLocalDataSourceManager.setNightModeState(state);
    }

    @Override
    public boolean isNightModeState() {
        return mLocalDataSourceManager.isNightModeState();
    }

    @Override
    public void setArticleNoImageState(boolean state) {
        mLocalDataSourceManager.setArticleNoImageState(state);
    }

    @Override
    public boolean isArticleNoImage() {
        return mLocalDataSourceManager.isArticleNoImage();
    }

    @Override
    public void setArticleAutoCacheState(boolean state) {
        mLocalDataSourceManager.setArticleAutoCacheState(state);
    }

    @Override
    public boolean isArticleAutoCache() {
        return mLocalDataSourceManager.isArticleAutoCache();
    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        super.detachView();
    }
}
