package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.geekzone.model.bean.gank.GankDateNewsBean;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hanyee on 16/10/10.
 */
public interface GankAPIService {

    String HOST = "http://gank.io/api/";

    /**
     * 获取某个类别的信息
     */
    @GET("data/{category}/{num}/{page}")
    Call<GankResponse<List<GankNewsBean>>> getGankNewsByCategory(@Path("category") String category, @Path("num") int num, @Path("page") int page);

    /**
     * 获取某天的所有类别信息
     */
    @GET("day/{year}/{month}/{day}")
    Call<GankResponse<GankDateNewsBean>> getGankNewsOfSomeday(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 获取随机推荐的信息
     */
    @GET("random/data/{category}/{num}")
    Call<GankResponse<List<GankNewsBean>>> getRecommendGankNews(@Path("category") String category, @Path("num") int num);
}
