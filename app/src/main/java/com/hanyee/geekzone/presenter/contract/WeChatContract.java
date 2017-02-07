package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface WeChatContract {

    interface View extends BaseView {
        void showWeChatNewsList(List<TianXinNewsBean> list);
        void showMoreWeChatNews(List<TianXinNewsBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadWeChatNewsList();
        void loadMoreWeChatNews();
    }

}
