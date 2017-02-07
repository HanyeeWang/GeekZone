package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface GankListContract {

    interface View extends BaseView {
        void showGankNews(List<GankNewsBean> list);
        void showMoreGankNews(List<GankNewsBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadGankNewsByCategory(String catrgory);

        void loadMoreGankNewsByCategory(String catrgory);

        void loadGankNewsOfOneDay(int year, int month, int day);

        void loadRecommendGankNews(String catrgory, int num);
    }
}
