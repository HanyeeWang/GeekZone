package com.hanyee.geekzone.model.bean.zhihu;

import java.util.List;

/**
 * Created by Hanyee on 16/10/13.
 */
public class HotNewsBean {

    /**
     * news_id : 8872005
     * url : http://news-at.zhihu.com/api/2/news/8872005
     * thumbnail : http://pic4.zhimg.com/b467184bc46eb0f52a3ac557c054ba83.jpg
     * title : 瞎扯 · 如何正确地吐槽
     */

    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }

    public static class RecentBean {
        private int news_id;
        private String url;
        private String thumbnail;
        private String title;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
