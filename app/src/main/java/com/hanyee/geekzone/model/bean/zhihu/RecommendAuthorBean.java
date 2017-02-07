package com.hanyee.geekzone.model.bean.zhihu;
import com.hanyee.geekzone.model.bean.zhihu.AuthorBean.AvatarBean;
/**
 * Created by Hanyee on 16/10/24.
 */

public class RecommendAuthorBean {

    /**
     * followersCount : 35894
     * name : 与时间无关的故事
     * url : /wille
     * postsCount : 5
     * description :
     * slug : wille
     * avatar : {"id":"b852e77d1","template":"https://pic2.zhimg.com/{id}_{size}.jpg"}
     */

    private int followersCount;
    private String name;
    private String url;
    private int postsCount;
    private String description;
    private String slug;
    /**
     * id : b852e77d1
     * template : https://pic2.zhimg.com/{id}_{size}.jpg
     */

    private AvatarBean avatar;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }
}
