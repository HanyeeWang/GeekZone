package com.hanyee.geekzone.model.source.remote.http;

import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.androidlib.utils.NetWorkUtil;
import com.hanyee.geekzone.BuildConfig;
import com.hanyee.geekzone.app.GeekZoneApplication;
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
import com.hanyee.geekzone.model.source.remote.http.api.GankAPIService;
import com.hanyee.geekzone.model.source.remote.http.api.GankCallback;
import com.hanyee.geekzone.model.source.remote.http.api.GankResponse;
import com.hanyee.geekzone.model.source.remote.http.api.TianXinAPIService;
import com.hanyee.geekzone.model.source.remote.http.api.TianXinCallback;
import com.hanyee.geekzone.model.source.remote.http.api.TianXinResponse;
import com.hanyee.geekzone.model.source.remote.http.api.ZhiHuAPIService.ZhiHuColumnsAPIService;
import com.hanyee.geekzone.model.source.remote.http.api.ZhiHuAPIService.ZhiHuDailyAPIService;
import com.hanyee.geekzone.model.source.remote.http.api.ZhiHuCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hanyee on 16/10/10.
 */
public class RetrofitHelper implements ZhiHuDataSource, GankDataSource, TianXinDataSource {

    private OkHttpClient mOkHttpClient;
    private GankAPIService mGankAPIService;
    private TianXinAPIService mTianXinAPIService;
    private ZhiHuDailyAPIService mZhiHuDailyAPIService;
    private ZhiHuColumnsAPIService mZhiHuColumnsAPIService;

    @Inject
    public RetrofitHelper() {
        initOKHttp();
        mGankAPIService = getGankApiService();
        mTianXinAPIService = getWechatApiService();
        mZhiHuDailyAPIService = getZhihuDailyApiService();
        mZhiHuColumnsAPIService = getZhihuColumnsApiService();
    }

    private void initOKHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(GeekZoneApplication.getInstance().getCacheDir(), "net_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtil.isNetWorkAvailable(GeekZoneApplication.getInstance())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtil.isNetWorkAvailable(GeekZoneApplication.getInstance())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    private ZhiHuDailyAPIService getZhihuDailyApiService() {
        Retrofit zhihuRetrofit = new Retrofit.Builder()
                .baseUrl(ZhiHuDailyAPIService.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return zhihuRetrofit.create(ZhiHuDailyAPIService.class);
    }

    private ZhiHuColumnsAPIService getZhihuColumnsApiService() {
        Retrofit zhihuRetrofit = new Retrofit.Builder()
                .baseUrl(ZhiHuColumnsAPIService.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return zhihuRetrofit.create(ZhiHuColumnsAPIService.class);
    }

    private GankAPIService getGankApiService() {
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(GankAPIService.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return gankRetrofit.create(GankAPIService.class);
    }

    private TianXinAPIService getWechatApiService() {
        Retrofit weChatRetrofit = new Retrofit.Builder()
                .baseUrl(TianXinAPIService.HOST)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return weChatRetrofit.create(TianXinAPIService.class);
    }

    /**
     * 启动界面图片文字
     *
     * @param res
     * @param callback
     */
    @Override
    public void getSplashInfo(String res, RemoteApiCallback<SplashBean> callback) {
        Call<SplashBean> splashBeanCall = mZhiHuDailyAPIService.getSplashInfo(res);
        splashBeanCall.enqueue(new ZhiHuCallback<SplashBean>(callback));
    }

    /**
     * 最新日报
     *
     * @param callback
     */
    @Override
    public void getNewsDailyList(RemoteApiCallback<NewsDailyBean> callback) {
        Call<NewsDailyBean> newsDailyBeanCall = mZhiHuDailyAPIService.getNewsDailyList();
        newsDailyBeanCall.enqueue(new ZhiHuCallback<NewsDailyBean>(callback));
    }

    /**
     * 往期日报
     *
     * @param date
     * @param callback
     */
    @Override
    public void getNewsDailyBefore(String date, RemoteApiCallback<NewsDailyBean> callback) {
        Call<NewsDailyBean> newsDailyBeanCall = mZhiHuDailyAPIService.getNewsDailyBefore(date);
        newsDailyBeanCall.enqueue(new ZhiHuCallback<NewsDailyBean>(callback));
    }

    /**
     * 主题日报
     *
     * @param callback
     */
    @Override
    public void getThemes(RemoteApiCallback<ThemesBean> callback) {
        Call<ThemesBean> themesBeanCall = mZhiHuDailyAPIService.getThemes();
        themesBeanCall.enqueue(new ZhiHuCallback<ThemesBean>(callback));
    }

    /**
     * 主题日报详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getThemesDetail(int id, RemoteApiCallback<ThemesListBean> callback) {
        Call<ThemesListBean> themesListBeanCall = mZhiHuDailyAPIService.getThemesDetail(id);
        themesListBeanCall.enqueue(new ZhiHuCallback<ThemesListBean>(callback));
    }

    /**
     * 热门日报
     *
     * @param callback
     */
    @Override
    public void getHotNews(RemoteApiCallback<HotNewsBean> callback) {
        Call<HotNewsBean> hotNewsBeanCall = mZhiHuDailyAPIService.getHotNews();
        hotNewsBeanCall.enqueue(new ZhiHuCallback<HotNewsBean>(callback));
    }

    /**
     * 日报详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getNewsDetailInfo(int id, RemoteApiCallback<NewsDetailBean> callback) {
        Call<NewsDetailBean> newsDetailBeanCall = mZhiHuDailyAPIService.getNewsDetailInfo(id);
        newsDetailBeanCall.enqueue(new ZhiHuCallback<NewsDetailBean>(callback));
    }

    /**
     * 日报的额外信息
     *
     * @param id
     * @param callback
     */
    @Override
    public void getNewsExtraInfo(int id, RemoteApiCallback<NewsExtraBean> callback) {
        Call<NewsExtraBean> newsExtraBeanCall = mZhiHuDailyAPIService.getNewsExtraInfo(id);
        newsExtraBeanCall.enqueue(new ZhiHuCallback<NewsExtraBean>(callback));
    }

    /**
     * 日报的长评论
     *
     * @param id
     * @param callback
     */
    @Override
    public void getLongCommentInfo(int id, RemoteApiCallback<CommentsBean> callback) {
        Call<CommentsBean> commentsBeanCall = mZhiHuDailyAPIService.getLongCommentInfo(id);
        commentsBeanCall.enqueue(new ZhiHuCallback<CommentsBean>(callback));
    }

    /**
     * 日报的短评论
     *
     * @param id
     * @param callback
     */
    @Override
    public void getShortCommentInfo(int id, RemoteApiCallback<CommentsBean> callback) {
        Call<CommentsBean> commentsBeanCall = mZhiHuDailyAPIService.getShortCommentInfo(id);
        commentsBeanCall.enqueue(new ZhiHuCallback<CommentsBean>(callback));
    }

    /**
     * 专题特刊
     *
     * @param callback
     */
    @Override
    public void getSpecials(RemoteApiCallback<SpecialsBean> callback) {
        Call<SpecialsBean> specialsBeanCall = mZhiHuDailyAPIService.getSpecials();
        specialsBeanCall.enqueue(new ZhiHuCallback<SpecialsBean>(callback));
    }

    /**
     * 专题特刊列表
     *
     * @param id
     * @param callback
     */
    @Override
    public void getSpecialList(int id, RemoteApiCallback<SpecialListBean> callback) {
        Call<SpecialListBean> specialListBeanCall = mZhiHuDailyAPIService.getSpecialList(id);
        specialListBeanCall.enqueue(new ZhiHuCallback<SpecialListBean>(callback));
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
        Call<SpecialListBean> specialListBeanCall = mZhiHuDailyAPIService.getBeforeSectionsDetails(id, timestamp);
        specialListBeanCall.enqueue(new ZhiHuCallback<SpecialListBean>(callback));
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
        Call<List<RecommendAuthorBean>> recommendAuthorBeanCall = mZhiHuColumnsAPIService.getRecommendedAuthorColumns(limit, offset, seed);
        recommendAuthorBeanCall.enqueue(new ZhiHuCallback<List<RecommendAuthorBean>>(callback));
    }

    /**
     * 获取专栏作者的详细信息
     *
     * @param name
     * @param callback
     */
    @Override
    public void getColumnAuthorDetail(String name, RemoteApiCallback<ColumnAuthorDetailBean> callback) {
        Call<ColumnAuthorDetailBean> columnAuthorDetailBeanCall = mZhiHuColumnsAPIService.getColumnAuthorDetail(name);
        columnAuthorDetailBeanCall.enqueue(new ZhiHuCallback<ColumnAuthorDetailBean>(callback));
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
        Call<List<RecommendArticleBean>> recommendArticleBean = mZhiHuColumnsAPIService.getRecommendedAuthorArticles(limit, offset, seed);
        recommendArticleBean.enqueue(new ZhiHuCallback<List<RecommendArticleBean>>(callback));
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
        Call<List<ColumnsBean>> listCall = mZhiHuColumnsAPIService.getColumnsOfAuthor(name, limit, offset);
        listCall.enqueue(new ZhiHuCallback<List<ColumnsBean>>(callback));
    }

    /**
     * 获取某篇专题文章详情
     *
     * @param id
     * @param callback
     */
    @Override
    public void getColumnArticleDetail(int id, RemoteApiCallback<ColumnArticleDetailBean> callback) {
        Call<ColumnArticleDetailBean> columnArticleDetailBeanCall = mZhiHuColumnsAPIService.getColumnArticleDetail(id);
        columnArticleDetailBeanCall.enqueue(new ZhiHuCallback<ColumnArticleDetailBean>(callback));
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
        Call<List<ColumnCommentsBean>> listCall = mZhiHuColumnsAPIService.getColumnComments(id, limit, offset);
        listCall.enqueue(new ZhiHuCallback<List<ColumnCommentsBean>>(callback));
    }

    /**
     * 获取微信精选
     *
     * @param key
     * @param num
     * @param page
     * @param callback
     */
    @Override
    public void getTianXinNews(String type, String key, int num, int page, RemoteApiCallback<List<TianXinNewsBean>> callback) {
        Call<TianXinResponse<List<TianXinNewsBean>>> tianXinNewsBeanCall = mTianXinAPIService.getTianXinNews(type, key, num, page);
        tianXinNewsBeanCall.enqueue(new TianXinCallback<List<TianXinNewsBean>>(callback));
    }

    /**
     * 通过关键字获取微信精选
     *
     * @param key
     * @param num
     * @param page
     * @param word
     * @param callback
     */
    @Override
    public void getTianXinNewsByWord(String type, String key, int num, int page, String word, RemoteApiCallback<List<TianXinNewsBean>> callback) {
        Call<TianXinResponse<List<TianXinNewsBean>>> tianXinNewsBeanCall = mTianXinAPIService.getTianXinNewsByWord(type, key, num, page, word);
        tianXinNewsBeanCall.enqueue(new TianXinCallback<List<TianXinNewsBean>>(callback));
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
        Call<GankResponse<List<GankNewsBean>>> gankResponseCall = mGankAPIService.getGankNewsByCategory(category, num, page);
        gankResponseCall.enqueue(new GankCallback<List<GankNewsBean>>(callback));
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
        Call<GankResponse<GankDateNewsBean>> gankDateNewsBeanCall = mGankAPIService.getGankNewsOfSomeday(year, month, day);
        gankDateNewsBeanCall.enqueue(new GankCallback<GankDateNewsBean>(callback));
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
        Call<GankResponse<List<GankNewsBean>>> gankResponseCall = mGankAPIService.getRecommendGankNews(category, num);
        gankResponseCall.enqueue(new GankCallback<List<GankNewsBean>>(callback));
    }
}
