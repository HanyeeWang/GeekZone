package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.ThemesBean;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface ThemesContract {

    interface View extends BaseView {
        void showAllThemes(ThemesBean response);
    }

    interface Presenter extends BasePresenter<View> {
        void loadAllThemes();
    }

}
