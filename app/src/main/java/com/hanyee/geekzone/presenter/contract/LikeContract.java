package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;

import java.util.List;


/**
 * Created by Hanyee on 16/10/14.
 */
public interface LikeContract {

    interface View extends BaseView {
        void showLikedArticles(List<LikeArticleBean> zhihuList, List<LikeArticleBean> columnAuthorList,
                               List<LikeArticleBean> columnArticleList, List<LikeArticleBean> wechatList,
                               List<LikeArticleBean> gankList);
    }

    interface Presenter extends BasePresenter<View> {
        void loadLikedArticles();
    }

}
