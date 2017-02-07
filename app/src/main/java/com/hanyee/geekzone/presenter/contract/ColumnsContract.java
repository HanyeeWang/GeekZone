package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.RecommendAuthorBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface ColumnsContract {

    interface View extends BaseView {
        void showColumnAuthorInfo(List<RecommendAuthorBean> authors);
        void showMoreColumnAuthorInfo(List<RecommendAuthorBean> authors);
    }

    interface Presenter extends BasePresenter<View> {
        void loadColumnAuthorInfo();
        void loadMoreColumnAuthorInfo();
    }

}
