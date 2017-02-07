package com.hanyee.geekzone.model.source.local;

import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;
import com.hanyee.geekzone.model.source.local.LocalDataSource.DataBaseSource;
import com.hanyee.geekzone.model.source.local.LocalDataSource.FileCacheSource;

import java.util.List;

/**
 * Created by Hanyee on 16/10/17.
 */
public class LocalDataSourceManager implements FileCacheSource, DataBaseSource {

    private FileCacheSource mFileCacheSource;
    private DataBaseSource mDataBaseSource;

    public LocalDataSourceManager(FileCacheSource fileCacheSource, DataBaseSource dataBaseSource) {
        mFileCacheSource = fileCacheSource;
        mDataBaseSource = dataBaseSource;
    }

    @Override
    public void setNightModeState(boolean state) {
        mFileCacheSource.setNightModeState(state);
    }

    @Override
    public boolean isNightModeState() {
        return mFileCacheSource.isNightModeState();
    }

    @Override
    public void setCurrentItem(int item) {
        mFileCacheSource.setCurrentItem(item);
    }

    @Override
    public int getCurrentItem() {
        return mFileCacheSource.getCurrentItem();
    }

    @Override
    public void setArticleNoImageState(boolean state) {
        mFileCacheSource.setArticleNoImageState(state);
    }

    @Override
    public boolean isArticleNoImage() {
        return mFileCacheSource.isArticleNoImage();
    }

    @Override
    public void setArticleAutoCacheState(boolean state) {
        mFileCacheSource.setArticleAutoCacheState(state);
    }

    @Override
    public boolean isArticleAutoCache() {
        return mFileCacheSource.isArticleAutoCache();
    }

    @Override
    public void likeZhiHuNews(String title, String picUrl, int articleId, String type) {
        mDataBaseSource.likeZhiHuNews(title, picUrl, articleId, type);
    }

    @Override
    public boolean isZhiHuNewsLiked(int articleId) {
        return mDataBaseSource.isZhiHuNewsLiked(articleId);
    }

    @Override
    public void unlikeZhiHuNews(int articleId) {
        mDataBaseSource.unlikeZhiHuNews(articleId);
    }

    @Override
    public void likeZhiHuColumnAuthor(String title, String picUrl, String desc, String authorHref, int followersCount, int postsCount, String type) {
        mDataBaseSource.likeZhiHuColumnAuthor(title, picUrl, desc, authorHref, followersCount, postsCount, type);
    }

    @Override
    public boolean isZhiHuColumnAuthorLiked(String authorHref) {
        return mDataBaseSource.isZhiHuColumnAuthorLiked(authorHref);
    }

    @Override
    public void unlikeZhiHuColumnAuthor(String authorHref) {
        mDataBaseSource.unlikeZhiHuColumnAuthor(authorHref);
    }

    @Override
    public void likeZhiHuColumnArticle(String title, String picUrl, String authorName, String time, int supportCount, int commentCount, int articleId, String type) {
        mDataBaseSource.likeZhiHuColumnArticle(title, picUrl, authorName, time, supportCount, commentCount, articleId, type);
    }

    @Override
    public boolean isZhiHuColumnArticleLiked(int articleId) {
        return mDataBaseSource.isZhiHuColumnArticleLiked(articleId);
    }

    @Override
    public void unlikeZhiHuColumnArticle(int articleId) {
        mDataBaseSource.unlikeZhiHuColumnArticle(articleId);
    }

    @Override
    public void likeWeChatArticle(String title, String desc, String time, String picUrl, String articleUrl, String type) {
        mDataBaseSource.likeWeChatArticle(title, desc, time, picUrl, articleUrl, type);
    }

    @Override
    public boolean isWeChatArticleLiked(String articleUrl) {
        return mDataBaseSource.isWeChatArticleLiked(articleUrl);
    }

    @Override
    public void unlikeWeChatArticle(String articleUrl) {
        mDataBaseSource.unlikeWeChatArticle(articleUrl);
    }

    @Override
    public void likeGankArticle(String title, String authorName, String time, String picUrl, String articleUrl, String type) {
        mDataBaseSource.likeGankArticle(title, authorName, time, picUrl, articleUrl, type);
    }

    @Override
    public boolean isGankArticleLiked(String articleUrl) {
        return mDataBaseSource.isGankArticleLiked(articleUrl);
    }

    @Override
    public void unlikeGankArticle(String articleUrl) {
        mDataBaseSource.unlikeGankArticle(articleUrl);
    }

    @Override
    public List<LikeArticleBean> getAllLikedArticles() {
        return mDataBaseSource.getAllLikedArticles();
    }

    @Override
    public List<LikeArticleBean> getLikedArticlesByType(String type) {
        return mDataBaseSource.getLikedArticlesByType(type);
    }
}
