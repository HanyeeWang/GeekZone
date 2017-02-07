package com.hanyee.geekzone.model.bean.gank;

import java.util.List;

/**
 * Created by Hanyee on 16/11/2.
 */

public class GankNewsBean {

    /**
     * _id : 58136806421aa90e799ec22e
     * createdAt : 2016-10-28T23:00:22.904Z
     * desc : 实现系统通知拦截功能的 App
     * images : ["http://img.gank.io/1dce0d48-99aa-4d68-83bc-d3f08b68c1c3"]
     * publishedAt : 2016-11-01T11:46:01.617Z
     * source : chrome
     * type : Android
     * url : https://github.com/gavinliu/NotificationBox
     * used : true
     * who : Jason
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
