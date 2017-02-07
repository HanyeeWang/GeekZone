package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void setNightModeState(boolean state);

        int getCurrentItem();

        void setCurrentItem(int item);
    }
}
