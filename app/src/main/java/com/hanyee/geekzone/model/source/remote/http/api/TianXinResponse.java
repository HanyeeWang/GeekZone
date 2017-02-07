package com.hanyee.geekzone.model.source.remote.http.api;



/**
 * Created by Hanyee on 16/10/20.
 */
public class TianXinResponse<T> {

    private int code;
    private String msg;
    private T newslist;

    public T getNewslist() {
        return newslist;
    }

    public void setNewslist(T newslist) {
        this.newslist = newslist;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
