package com.hanyee.geekzone.model.source.remote.http.api;

import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hanyee on 16/10/10.
 */
public interface TianXinAPIService {

    String HOST = "http://api.tianapi.com/";

    /**
     * 获取天行数据精选
     */
    @GET("{type}")
    Call<TianXinResponse<List<TianXinNewsBean>>> getTianXinNews(@Path("type") String type, @Query("key") String key, @Query("num") int num, @Query("page") int page);

    /**
     * 通过关键字获取天行数据精选
     */
    @GET("{type}")
    Call<TianXinResponse<List<TianXinNewsBean>>> getTianXinNewsByWord(@Path("type") String type, @Query("key") String key, @Query("num") int num, @Query("page") int page, @Query("word") String word);

}
