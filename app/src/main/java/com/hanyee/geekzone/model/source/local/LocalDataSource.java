package com.hanyee.geekzone.model.source.local;

import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/17.
 */
public interface LocalDataSource {

    interface DataBaseSource {

        void likeZhiHuNews(String title, String picUrl, int articleId, String type);

        boolean isZhiHuNewsLiked(int articleId);

        void unlikeZhiHuNews(int articleId);

        void likeZhiHuColumnAuthor(String title, String picUrl, String desc, String authorHref, int followersCount, int postsCount, String type);

        boolean isZhiHuColumnAuthorLiked(String authorHref);

        void unlikeZhiHuColumnAuthor(String authorHref);

        void likeZhiHuColumnArticle(String title, String picUrl, String authorName, String time, int supportCount, int commentCount, int articleId, String type);

        boolean isZhiHuColumnArticleLiked(int articleId);

        void unlikeZhiHuColumnArticle(int articleId);

        void likeWeChatArticle(String title, String desc, String time, String picUrl, String articleUrl, String type);

        boolean isWeChatArticleLiked(String articleUrl);

        void unlikeWeChatArticle(String articleUrl);

        void likeGankArticle(String title, String authorName, String time, String picUrl, String articleUrl, String type);

        boolean isGankArticleLiked(String articleUrl);

        void unlikeGankArticle(String articleUrl);

        List<LikeArticleBean> getAllLikedArticles();

        List<LikeArticleBean> getLikedArticlesByType(String type);
    }

    interface FileCacheSource {

        void setNightModeState(boolean state);

        boolean isNightModeState();

        void setCurrentItem(int item);

        int getCurrentItem();

        void setArticleNoImageState(boolean state);

        boolean isArticleNoImage();

        void setArticleAutoCacheState(boolean state);

        boolean isArticleAutoCache();
    }
}
