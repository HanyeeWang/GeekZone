package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface DailyNewsContract {

    interface View extends BaseView {
        void showDailyNews(NewsDailyBean list);

        void showMoreDaliyNews(NewsDailyBean list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadLatestDailyNews();

        void loadDailyNewsBefore();
    }

}
