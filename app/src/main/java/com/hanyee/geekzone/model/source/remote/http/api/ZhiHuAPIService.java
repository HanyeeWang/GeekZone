package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.geekzone.model.bean.zhihu.ColumnArticleDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnAuthorDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnCommentsBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnsBean;
import com.hanyee.geekzone.model.bean.zhihu.CommentsBean;
import com.hanyee.geekzone.model.bean.zhihu.HotNewsBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsExtraBean;
import com.hanyee.geekzone.model.bean.zhihu.RecommendArticleBean;
import com.hanyee.geekzone.model.bean.zhihu.RecommendAuthorBean;
import com.hanyee.geekzone.model.bean.zhihu.SpecialListBean;
import com.hanyee.geekzone.model.bean.zhihu.SpecialsBean;
import com.hanyee.geekzone.model.bean.zhihu.SplashBean;
import com.hanyee.geekzone.model.bean.zhihu.ThemesBean;
import com.hanyee.geekzone.model.bean.zhihu.ThemesListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hanyee on 16/10/10.
 */
public interface ZhiHuAPIService {

    interface ZhiHuDailyAPIService {

        String HOST = "http://news-at.zhihu.com/api/4/";

        /**
         * 启动界面图片文字
         */
        @GET("start-image/{res}")
        Call<SplashBean> getSplashInfo(@Path("res") String res);

        /**
         * 最新日报
         */
        @GET("news/latest")
        Call<NewsDailyBean> getNewsDailyList();

        /**
         * 往期日报
         */
        @GET("news/before/{date}")
        Call<NewsDailyBean> getNewsDailyBefore(@Path("date") String date);

        /**
         * 主题日报
         */
        @GET("themes")
        Call<ThemesBean> getThemes();

        /**
         * 主题日报详情
         */
        @GET("theme/{id}")
        Call<ThemesListBean> getThemesDetail(@Path("id") int id);

        /**
         * 热门日报
         */
        @GET("news/hot")
        Call<HotNewsBean> getHotNews();

        /**
         * 日报详情
         */
        @GET("news/{id}")
        Call<NewsDetailBean> getNewsDetailInfo(@Path("id") int id);

        /**
         * 日报的额外信息
         */
        @GET("story-extra/{id}")
        Call<NewsExtraBean> getNewsExtraInfo(@Path("id") int id);

        /**
         * 日报的长评论
         */
        @GET("story/{id}/long-comments")
        Call<CommentsBean> getLongCommentInfo(@Path("id") int id);

        /**
         * 日报的短评论
         */
        @GET("story/{id}/short-comments")
        Call<CommentsBean> getShortCommentInfo(@Path("id") int id);

        /**
         * 专题特刊
         */
        @GET("sections")
        Call<SpecialsBean> getSpecials();

        /**
         * 专题特刊列表
         */
        @GET("section/{id}")
        Call<SpecialListBean> getSpecialList(@Path("id") int id);

        /**
         * 获取专栏的之前消息
         */
        @GET("section/{id}/before/{timestamp}")
        Call<SpecialListBean> getBeforeSectionsDetails(@Path("id") int id, @Path("timestamp") long timestamp);
    }

    interface ZhiHuColumnsAPIService {

        String HOST = "https://zhuanlan.zhihu.com/api/";

        /**
         * 获取推荐的作者专题
         */
        @GET("recommendations/columns")
        Call<List<RecommendAuthorBean>> getRecommendedAuthorColumns(
                @Query("limit") int limit, @Query("offset") int offset, @Query("seed") int seed);

        /**
         * 获取推荐的文章
         */
        @GET("recommendations/posts")
        Call<List<RecommendArticleBean>> getRecommendedAuthorArticles(
                @Query("limit") int limit, @Query("offset") int offset, @Query("seed") int seed);

        /**
         * 获取某人的专题
         */
        @GET("columns/{name}/posts")
        Call<List<ColumnsBean>> getColumnsOfAuthor(
                @Path("name") String name, @Query("limit") int limit, @Query("offset") int offset);

        /**
         * 获取专栏作者的详细信息
         */
        @GET("columns/{name}")
        Call<ColumnAuthorDetailBean> getColumnAuthorDetail(@Path("name") String name);

        /**
         * 获取某篇专题文章详情
         */
        @GET("posts/{id}")
        Call<ColumnArticleDetailBean> getColumnArticleDetail(@Path("id") int id);

        /**
         * 获取文章的评论
         */
        @GET("posts/{id}/comments")
        Call<List<ColumnCommentsBean>> getColumnComments(
                @Path("id") int id, @Query("limit") int limit, @Query("offset") int offset);
    }
}
