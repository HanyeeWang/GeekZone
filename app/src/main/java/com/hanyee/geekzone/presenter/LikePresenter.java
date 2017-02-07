package com.hanyee.geekzone.presenter;


import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.presenter.contract.LikeContract.Presenter;
import com.hanyee.geekzone.presenter.contract.LikeContract.View;

import java.util.List;

import javax.inject.Inject;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_ARTICLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_AUTHOR;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_GANK;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;

/**
 * Created by Hanyee on 16/10/14.
 */
public class LikePresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;

    @Inject
    public LikePresenter(LocalDataSourceManager localDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
    }

    @Override
    public void loadLikedArticles() {
        List<LikeArticleBean> zhiHuNews = mLocalDataSourceManager.getLikedArticlesByType(EXTRA_TYPE_ZHIHU_NEWS);
        List<LikeArticleBean> columnAuthors = mLocalDataSourceManager.getLikedArticlesByType(EXTRA_TYPE_COLUMN_AUTHOR);
        List<LikeArticleBean> columnArticles = mLocalDataSourceManager.getLikedArticlesByType(EXTRA_TYPE_COLUMN_ARTICLE);
        List<LikeArticleBean> weChatNews = mLocalDataSourceManager.getLikedArticlesByType(EXTRA_TYPE_TIANXIN);
        List<LikeArticleBean> gankNews = mLocalDataSourceManager.getLikedArticlesByType(EXTRA_TYPE_GANK);
        mView.showLikedArticles(zhiHuNews, columnAuthors, columnArticles, weChatNews, gankNews);
    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        super.detachView();
    }
}
