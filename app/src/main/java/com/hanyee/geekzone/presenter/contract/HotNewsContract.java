package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.HotNewsBean.RecentBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface HotNewsContract {

    interface View extends BaseView {
        void showHotNews(List<RecentBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadHotNews();
    }

}
