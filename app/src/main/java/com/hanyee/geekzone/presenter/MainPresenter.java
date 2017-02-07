package com.hanyee.geekzone.presenter;


import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.event.NightModeEvent;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.presenter.contract.MainContract.Presenter;
import com.hanyee.geekzone.presenter.contract.MainContract.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class MainPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;

    @Inject
    public MainPresenter(LocalDataSourceManager localDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
    }

    @Override
    public void setNightModeState(boolean state) {
        mLocalDataSourceManager.setNightModeState(state);
    }

    @Override
    public int getCurrentItem() {
        return mLocalDataSourceManager.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int item) {
        mLocalDataSourceManager.setCurrentItem(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateNightMode(NightModeEvent event) {
        mView.openNightMode(event.isNightMode());
    }

    @Override
    public void attachView(View view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        mLocalDataSourceManager = null;
        EventBus.getDefault().unregister(this);
    }

}
