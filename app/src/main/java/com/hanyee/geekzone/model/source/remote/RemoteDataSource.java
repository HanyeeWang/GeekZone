package com.hanyee.geekzone.model.source.remote;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.model.bean.gank.GankDateNewsBean;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
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

/**
 * Created by Hanyee on 16/10/17.
 */
public interface RemoteDataSource {

    interface ZhiHuDataSource {
        /**
         * 启动界面图片文字
         */
        void getSplashInfo(String res, RemoteApiCallback<SplashBean> callback);

        /**
         * 最新日报
         */
        void getNewsDailyList(RemoteApiCallback<NewsDailyBean> callback);

        /**
         * 往期日报
         */
        void getNewsDailyBefore(String date, RemoteApiCallback<NewsDailyBean> callback);

        /**
         * 主题日报
         */
        void getThemes(RemoteApiCallback<ThemesBean> callback);

        /**
         * 主题日报详情
         */
        void getThemesDetail(int id, RemoteApiCallback<ThemesListBean> callback);

        /**
         * 热门日报
         */
        void getHotNews(RemoteApiCallback<HotNewsBean> callback);

        /**
         * 日报详情
         */
        void getNewsDetailInfo(int id, RemoteApiCallback<NewsDetailBean> callback);

        /**
         * 日报的额外信息
         */
        void getNewsExtraInfo(int id, RemoteApiCallback<NewsExtraBean> callback);

        /**
         * 日报的长评论
         */
        void getLongCommentInfo(int id, RemoteApiCallback<CommentsBean> callback);

        /**
         * 日报的短评论
         */
        void getShortCommentInfo(int id, RemoteApiCallback<CommentsBean> callback);

        /**
         * 专题特刊
         */
        void getSpecials(RemoteApiCallback<SpecialsBean> callback);

        /**
         * 专题特刊列表
         */
        void getSpecialList(int id, RemoteApiCallback<SpecialListBean> callback);

        /**
         * 获取专栏的之前消息
         */
        void getBeforeSpecialListDetail(int id, long timestamp, RemoteApiCallback<SpecialListBean> callback);

        /**
         * 获取推荐的作者专题
         */
        void getRecommendedAuthorColumns(int limit, int offset, int seed, RemoteApiCallback<List<RecommendAuthorBean>> callback);

        /**
         * 获取专栏作者的详细信息
         */
        void getColumnAuthorDetail(String name, RemoteApiCallback<ColumnAuthorDetailBean> callback);

        /**
         * 获取推荐的文章
         */
        void getRecommendedAuthorArticles(int limit, int offset, int seed, RemoteApiCallback<List<RecommendArticleBean>> callback);

        /**
         * 获取某人的专题
         */
        void getColumnsOfAuthor(String name, int limit, int offset, RemoteApiCallback<List<ColumnsBean>> callback);

        /**
         * 获取某篇专题文章详情
         */
        void getColumnArticleDetail(int id, RemoteApiCallback<ColumnArticleDetailBean> callback);

        /**
         * 获取文章的评论
         */
        void getColumnComments(int id, int limit, int offset, RemoteApiCallback<List<ColumnCommentsBean>> callback);
    }

    interface GankDataSource {
        /**
         * 获取某个类别的信息
         */
        void getGankNewsByCategory(String category, int num, int page, RemoteApiCallback<List<GankNewsBean>> callback);

        /**
         * 获取某天的所有类别信息
         */
        void getGankNewsOfSomeday(int year, int month, int day, RemoteApiCallback<GankDateNewsBean> callback);

        /**
         * 获取随机推荐的信息
         */
        void getRecommendGankNews(String category, int num, RemoteApiCallback<List<GankNewsBean>> callback);
    }

    interface TianXinDataSource {
        /**
         * 获取天行数据精选
         */
        void getTianXinNews(String type, String key, int num, int page, RemoteApiCallback<List<TianXinNewsBean>> callback);

        /**
         * 通过关键字获取天行数据精选
         */
        void getTianXinNewsByWord(String type, String key, int num, int page, String word, RemoteApiCallback<List<TianXinNewsBean>> callback);
    }
}
