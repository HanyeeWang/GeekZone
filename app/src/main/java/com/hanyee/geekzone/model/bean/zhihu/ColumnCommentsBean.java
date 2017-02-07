package com.hanyee.geekzone.model.bean.zhihu;

/**
 * Created by Hanyee on 16/10/24.
 */

public class ColumnCommentsBean {

    /**
     * content : 好
     * liked : false
     * href : /api/posts/22646649/comments/170130441
     * inReplyToCommentId : 0
     * reviewing : false
     * author : {"profileUrl":"https://www.zhihu.com/people/liu-zhi-wen-42-20","bio":"韩国海归硕士，一带一路国企员工","hash":"f52415f8d163e482d7d062bb91fc6111","uid":731141197803814900,"isOrg":false,"description":"基督徒，生长于书香门第，优雅而内向; 喜欢咖啡，红酒，洋酒，精酿啤酒; 爱听巴赫，海顿，莫扎特，贝多芬的作品以及拉丁语和希腊语的圣咏; 对自然科学，历史，中西古典文化很感兴趣。","slug":"liu-zhi-wen-42-20","avatar":{"id":"da8e974dc","template":"https://pic1.zhimg.com/{id}_{size}.jpg"},"name":"刘植玟"}
     * createdTime : 2016-09-28T09:35:59+08:00
     * featured : true
     * id : 170130441
     * likesCount : 1
     */

    private String content;
    private boolean liked;
    private String href;
    private int inReplyToCommentId;
    private boolean reviewing;
    /**
     * profileUrl : https://www.zhihu.com/people/liu-zhi-wen-42-20
     * bio : 韩国海归硕士，一带一路国企员工
     * hash : f52415f8d163e482d7d062bb91fc6111
     * uid : 731141197803814900
     * isOrg : false
     * description : 基督徒，生长于书香门第，优雅而内向; 喜欢咖啡，红酒，洋酒，精酿啤酒; 爱听巴赫，海顿，莫扎特，贝多芬的作品以及拉丁语和希腊语的圣咏; 对自然科学，历史，中西古典文化很感兴趣。
     * slug : liu-zhi-wen-42-20
     * avatar : {"id":"da8e974dc","template":"https://pic1.zhimg.com/{id}_{size}.jpg"}
     * name : 刘植玟
     */

    private AuthorBean author;
    private String createdTime;
    private boolean featured;
    private int id;
    private int likesCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getInReplyToCommentId() {
        return inReplyToCommentId;
    }

    public void setInReplyToCommentId(int inReplyToCommentId) {
        this.inReplyToCommentId = inReplyToCommentId;
    }

    public boolean isReviewing() {
        return reviewing;
    }

    public void setReviewing(boolean reviewing) {
        this.reviewing = reviewing;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

}
