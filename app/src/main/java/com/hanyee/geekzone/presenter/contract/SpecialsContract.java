package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.SpecialsBean.DataBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface SpecialsContract {

    interface View extends BaseView {
        void showAllSpecials(List<DataBean> response);
    }

    interface Presenter extends BasePresenter<View> {
        void loadAllSpecials();
    }

}
