package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.SpecialListBean;


/**
 * Created by Hanyee on 16/10/14.
 */
public interface SpecialListContract {

    interface View extends BaseView {
        void showSpecialsList(SpecialListBean list);
        void showMoreSpecialsList(SpecialListBean list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadSpecialsList(int id);
        void loadMoreSpecialsList(int id);
    }
}
