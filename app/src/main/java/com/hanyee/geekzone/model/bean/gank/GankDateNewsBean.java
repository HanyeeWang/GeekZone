package com.hanyee.geekzone.model.bean.gank;

import java.util.List;

/**
 * Created by Hanyee on 16/11/2.
 */

public class GankDateNewsBean {

    /**
     * _id : 56cc6d23421aa95caa707a69
     * createdAt : 2015-08-06T07:15:52.65Z
     * desc : 类似Link Bubble的悬浮式操作设计
     * publishedAt : 2015-08-07T03:57:48.45Z
     * type : Android
     * url : https://github.com/recruit-lifestyle/FloatingView
     * used : true
     * who : mthli
     */

    private List<GankNewsBean> Android;
    /**
     * _id : 56cc6d1d421aa95caa707769
     * createdAt : 2015-08-07T01:32:51.588Z
     * desc : LLVM 简介
     * publishedAt : 2015-08-07T03:57:48.70Z
     * type : iOS
     * url : http://adriansampson.net/blog/llvm.html
     * used : true
     * who : CallMeWhy
     */

    private List<GankNewsBean> iOS;
    /**
     * _id : 56cc6d23421aa95caa707c68
     * createdAt : 2015-08-06T13:06:17.211Z
     * desc : 听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址
     http://v.youku.com/v_show/id_XMTQxODA5NDM2.html
     * publishedAt : 2015-08-07T03:57:48.104Z
     * type : 休息视频
     * url : http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button
     * used : true
     * who : lxxself
     */

    private List<GankNewsBean> 休息视频;
    /**
     * _id : 56cc6d23421aa95caa707bdf
     * createdAt : 2015-08-07T01:36:06.932Z
     * desc : Display GitHub code in tree format
     * publishedAt : 2015-08-07T03:57:48.81Z
     * type : 拓展资源
     * url : https://github.com/buunguyen/octotree
     * used : true
     * who : lxxself
     */

    private List<GankNewsBean> 拓展资源;
    /**
     * _id : 56cc6d23421aa95caa707bd0
     * createdAt : 2015-08-07T01:52:30.267Z
     * desc : 程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊
     * publishedAt : 2015-08-07T03:57:48.84Z
     * type : 瞎推荐
     * url : https://cmd.fm/
     * used : true
     * who : lxxself
     */

    private List<GankNewsBean> 瞎推荐;
    /**
     * _id : 56cc6d23421aa95caa707c52
     * createdAt : 2015-08-07T01:21:06.112Z
     * desc : 8.7——（1）
     * publishedAt : 2015-08-07T03:57:47.310Z
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg
     * used : true
     * who : 张涵宇
     */

    private List<GankNewsBean> 福利;

    public List<GankNewsBean> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GankNewsBean> android) {
        Android = android;
    }

    public List<GankNewsBean> getIOS() {
        return iOS;
    }

    public void setIOS(List<GankNewsBean> iOS) {
        this.iOS = iOS;
    }

    public List<GankNewsBean> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GankNewsBean> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GankNewsBean> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<GankNewsBean> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GankNewsBean> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<GankNewsBean> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<GankNewsBean> get福利() {
        return 福利;
    }

    public void set福利(List<GankNewsBean> 福利) {
        this.福利 = 福利;
    }

}
