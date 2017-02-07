package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.ThemesListBean;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface ThemeListContract {

    interface View extends BaseView {
        void showThemesList(ThemesListBean list);
        void showMoreThemesList(ThemesListBean list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadThemesList(int id);
        void loadMoreThemesList(int id);
    }
}
