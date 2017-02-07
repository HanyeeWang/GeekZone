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
import com.hanyee.geekzone.model.source.remote.RemoteDataSource.GankDataSource;
import com.hanyee.geekzone.model.source.remote.RemoteDataSource.TianXinDataSource;
import com.hanyee.geekzone.model.source.remote.RemoteDataSource.ZhiHuDataSource;

import java.util.List;

/**
 * Created by Hanyee on 16/10/17.
 */
public class RemoteDataSourceManager implements ZhiHuDataSource, GankDataSource, TianXinDataSource {

    private GankDataSource mGankDataSource;
    private ZhiHuDataSource mZhiHuDataSource;
    private TianXinDataSource mTianXinDataSource;

    public RemoteDataSourceManager(ZhiHuDataSource zhiHuDataSource, GankDataSource gankDataSource,
                                   TianXinDataSource tianXinDataSource) {
        mGankDataSource = gankDataSource;
        mZhiHuDataSource = zhiHuDataSource;
        mTianXinDataSource = tianXinDataSource;
    }


    /**
     * 启动界面图片文字
     *
     * @param res
     * @param callback
     */
    @Override
    public void getSplashInfo(String res, RemoteApiCallback<SplashBean> callback) {
        mZhiHuDataSource.getSplashInfo(res, callback);
    }

    /**
     * 最新日报
     *
     * @param callback
     */
    @Override
    public void getNewsDailyList(RemoteApiCallback<NewsDailyBean> callback) {
        mZhiHuDataSource.getNewsDailyList(callback);
    }

    /**
     * 往期日报
     *
     * @param date
     * @param callback
     */
    @Override
    public void getNewsDailyBefore(String date, RemoteApiCallback<NewsDailyBean> callback) {
        mZhiHuDataSource.getNewsDailyBefore(date, callback);
    }

    /**
     * 主题日报
     *
     * @param callback
     */
    @Override
    public void getThemes(RemoteApiCallback<ThemesBean> callback) {
        mZhiHuDataSource.getThemes(callback);
    }

    /**
     * 主题日报详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getThemesDetail(int id, RemoteApiCallback<ThemesListBean> callback) {
        mZhiHuDataSource.getThemesDetail(id, callback);
    }

    /**
     * 热门日报
     *
     * @param callback
     */
    @Override
    public void getHotNews(RemoteApiCallback<HotNewsBean> callback) {
        mZhiHuDataSource.getHotNews(callback);
    }

    /**
     * 日报详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getNewsDetailInfo(int id, RemoteApiCallback<NewsDetailBean> callback) {
        mZhiHuDataSource.getNewsDetailInfo(id, callback);
    }

    /**
     * 日报的额外信息
     *
     * @param id
     * @param callback
     */
    @Override
    public void getNewsExtraInfo(int id, RemoteApiCallback<NewsExtraBean> callback) {
        mZhiHuDataSource.getNewsExtraInfo(id, callback);
    }

    /**
     * 日报的长评论
     *
     * @param id
     * @param callback
     */
    @Override
    public void getLongCommentInfo(int id, RemoteApiCallback<CommentsBean> callback) {
        mZhiHuDataSource.getLongCommentInfo(id, callback);
    }

    /**
     * 日报的短评论
     *
     * @param id
     * @param callback
     */
    @Override
    public void getShortCommentInfo(int id, RemoteApiCallback<CommentsBean> callback) {
        mZhiHuDataSource.getShortCommentInfo(id, callback);
    }

    /**
     * 专题特刊
     *
     * @param callback
     */
    @Override
    public void getSpecials(RemoteApiCallback<SpecialsBean> callback) {
        mZhiHuDataSource.getSpecials(callback);
    }

    /**
     * 专题特刊列表
     *
     * @param id
     * @param callback
     */
    @Override
    public void getSpecialList(int id, RemoteApiCallback<SpecialListBean> callback) {
        mZhiHuDataSource.getSpecialList(id, callback);
    }

    /**
     * 获取专栏的之前消息
     *
     * @param id
     * @param timestamp
     * @param callback
     */
    @Override
    public void getBeforeSpecialListDetail(int id, long timestamp, RemoteApiCallback<SpecialListBean> callback) {
        mZhiHuDataSource.getBeforeSpecialListDetail(id, timestamp, callback);
    }

    /**
     * 获取推荐的作者专题
     *
     * @param limit
     * @param offset
     * @param seed
     * @param callback
     */
    @Override
    public void getRecommendedAuthorColumns(int limit, int offset, int seed, RemoteApiCallback<List<RecommendAuthorBean>> callback) {
        mZhiHuDataSource.getRecommendedAuthorColumns(limit, offset, seed, callback);
    }

    /**
     * 获取专栏作者的详细信息
     *
     * @param name
     * @param callback
     */
    @Override
    public void getColumnAuthorDetail(String name, RemoteApiCallback<ColumnAuthorDetailBean> callback) {
        mZhiHuDataSource.getColumnAuthorDetail(name, callback);
    }

    /**
     * 获取推荐的文章
     *
     * @param limit
     * @param offset
     * @param seed
     * @param callback
     */
    @Override
    public void getRecommendedAuthorArticles(int limit, int offset, int seed, RemoteApiCallback<List<RecommendArticleBean>> callback) {
        mZhiHuDataSource.getRecommendedAuthorArticles(limit, offset, seed, callback);
    }

    /**
     * 获取某人的专题
     *
     * @param name
     * @param limit
     * @param offset
     * @param callback
     */
    @Override
    public void getColumnsOfAuthor(String name, int limit, int offset, RemoteApiCallback<List<ColumnsBean>> callback) {
        mZhiHuDataSource.getColumnsOfAuthor(name, limit, offset, callback);
    }

    /**
     * 获取某篇专题文章详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getColumnArticleDetail(int id, RemoteApiCallback<ColumnArticleDetailBean> callback) {
        mZhiHuDataSource.getColumnArticleDetail(id, callback);
    }

    /**
     * 获取文章的评论
     *
     * @param id
     * @param limit
     * @param offset
     * @param callback
     */
    @Override
    public void getColumnComments(int id, int limit, int offset, RemoteApiCallback<List<ColumnCommentsBean>> callback) {
        mZhiHuDataSource.getColumnComments(id, limit, offset, callback);
    }

    /**
     * 获取天行数据精选
     *
     * @param type
     * @param key
     * @param num
     * @param page
     * @param callback
     */
    @Override
    public void getTianXinNews(String type, String key, int num, int page, RemoteApiCallback<List<TianXinNewsBean>> callback) {
        mTianXinDataSource.getTianXinNews(type, key, num, page, callback);
    }

    /**
     * 通过关键字获取天行数据精选
     *
     * @param type
     * @param key
     * @param num
     * @param page
     * @param word
     * @param callback
     */
    @Override
    public void getTianXinNewsByWord(String type, String key, int num, int page, String word, RemoteApiCallback<List<TianXinNewsBean>> callback) {
        mTianXinDataSource.getTianXinNewsByWord(type, key, num, page, word, callback);
    }

    /**
     * 获取某个类别的信息
     *
     * @param category
     * @param num
     * @param page
     * @param callback
     */
    @Override
    public void getGankNewsByCategory(String category, int num, int page, RemoteApiCallback<List<GankNewsBean>> callback) {
        mGankDataSource.getGankNewsByCategory(category, num, page, callback);
    }

    /**
     * 获取某天的所有类别信息
     *
     * @param year
     * @param month
     * @param day
     * @param callback
     */
    @Override
    public void getGankNewsOfSomeday(int year, int month, int day, RemoteApiCallback<GankDateNewsBean> callback) {
        mGankDataSource.getGankNewsOfSomeday(year, month, day, callback);
    }

    /**
     * 获取随机推荐的信息
     *
     * @param category
     * @param num
     * @param callback
     */
    @Override
    public void getRecommendGankNews(String category, int num, RemoteApiCallback<List<GankNewsBean>> callback) {
        mGankDataSource.getRecommendGankNews(category, num, callback);
    }
}
