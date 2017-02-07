package com.hanyee.geekzone.presenter;


import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.presenter.contract.AboutContract.Presenter;
import com.hanyee.geekzone.presenter.contract.AboutContract.View;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class AboutPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;

    @Inject
    public AboutPresenter(LocalDataSourceManager localDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        super.detachView();
    }
}
