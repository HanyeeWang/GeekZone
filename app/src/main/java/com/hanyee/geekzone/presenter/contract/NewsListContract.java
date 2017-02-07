package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface NewsListContract {

    interface View extends BaseView {
        void showNews(List<TianXinNewsBean> list);

        void showMoreNews(List<TianXinNewsBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNews(String type);

        void loadMoreNews(String type);
    }
}
