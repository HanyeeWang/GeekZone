package com.hanyee.geekzone.model.bean.zhihu;

import com.hanyee.geekzone.model.bean.zhihu.AuthorBean.AvatarBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/21.
 */
public class ColumnAuthorDetailBean {

    /**
     * followersCount : 12037
     * creator : {"profileUrl":"https://www.zhihu.com/people/rio","bio":"天使投资新人、伪全栈工程师、业余播客主播","hash":"0d81a29a497b91e0f374ae0508de779d","uid":26675878297600,"isOrg":false,"description":"《疯投圈》 https://crazy.capital/ \n《内核恐慌》 https://kernelpanic.fm/","slug":"rio","avatar":{"id":"bc89e75b26846b4b2a5a6fd815be6558","template":"https://pic1.zhimg.com/{id}_{size}.jpg"},"name":"Rio"}
     * topics : [{"url":"https://www.zhihu.com/topic/19623806","id":"19623806","name":"Rio（知乎用户）"},{"url":"https://www.zhihu.com/topic/19556664","id":"19556664","name":"科技"}]
     * activateState : activated
     * href : /api/columns/riobard
     * acceptSubmission : true
     * firstTime : false
     * postTopics : [{"postsCount":1,"id":2,"name":"知乎"},{"postsCount":1,"id":11,"name":"史蒂夫·乔布斯（Steve Jobs）"},{"postsCount":2,"id":16,"name":"MacBook"},{"postsCount":2,"id":26,"name":"iPad"},{"postsCount":1,"id":40,"name":"Facebook"},{"postsCount":1,"id":42,"name":"工业设计"},{"postsCount":1,"id":45,"name":"亚马逊 (Amazon.com)"},{"postsCount":1,"id":66,"name":"风险投资（VC）"},{"postsCount":3,"id":77,"name":"iOS"},{"postsCount":1,"id":84,"name":"Keynote"},{"postsCount":1,"id":108,"name":"移动互联网"},{"postsCount":1,"id":112,"name":"创业"},{"postsCount":1,"id":186,"name":"电子商务"},{"postsCount":1,"id":484,"name":"微软（Microsoft）"},{"postsCount":17,"id":520,"name":"苹果公司 (Apple Inc.)"},{"postsCount":2,"id":847,"name":"英特尔 (Intel)"},{"postsCount":1,"id":1028,"name":"ARM"},{"postsCount":1,"id":1030,"name":"中央处理器 (CPU)"},{"postsCount":2,"id":1242,"name":"H.264"},{"postsCount":1,"id":1421,"name":"iTunes Store"},{"postsCount":1,"id":1987,"name":"Xcode"},{"postsCount":2,"id":2462,"name":"HTML5"},{"postsCount":1,"id":2520,"name":"应用开发"},{"postsCount":1,"id":2607,"name":"Apple TV"},{"postsCount":1,"id":3068,"name":"IBM"},{"postsCount":1,"id":3132,"name":"笔记本电脑"},{"postsCount":1,"id":3714,"name":"出口贸易"},{"postsCount":1,"id":4253,"name":"云服务"},{"postsCount":1,"id":4298,"name":"性价比"},{"postsCount":3,"id":5231,"name":"谷歌 (Google)"},{"postsCount":1,"id":5979,"name":"Cisco（思科）"},{"postsCount":3,"id":8907,"name":"财报"},{"postsCount":1,"id":12973,"name":"罗永浩"},{"postsCount":1,"id":16178,"name":"商业评论"},{"postsCount":1,"id":17686,"name":"Android"},{"postsCount":1,"id":22581,"name":"电子邮件营销（EDM）"},{"postsCount":1,"id":24105,"name":"Apple MagSafe Charger"},{"postsCount":1,"id":28933,"name":"iBookstore"},{"postsCount":1,"id":33603,"name":"Tizen"},{"postsCount":3,"id":74656,"name":"Apple 公司财报"},{"postsCount":1,"id":76078,"name":"王自如"},{"postsCount":1,"id":77679,"name":"iPhone 5s"},{"postsCount":1,"id":82723,"name":"视频编码"},{"postsCount":1,"id":85043,"name":"iPhone 5c"},{"postsCount":1,"id":88977,"name":"WebRTC"},{"postsCount":1,"id":94988,"name":"极路由"},{"postsCount":1,"id":97983,"name":"锤子手机"},{"postsCount":1,"id":102452,"name":"OS X Mavericks"},{"postsCount":1,"id":108175,"name":"Chromecast"},{"postsCount":1,"id":155093,"name":"IPN (Intelligent Podcast Network)"},{"postsCount":1,"id":156513,"name":"HoloLens"},{"postsCount":1,"id":160652,"name":"XcodeGhost"}]
     * pendingName :
     * avatar : {"id":"6cf3b3138","template":"https://pic1.zhimg.com/{id}_{size}.jpg"}
     * canManage : false
     * description : 科技・商业・设计・体验
     * pendingTopics : []
     * nameCanEditUntil : 0
     * reason :
     * banUntil : 0
     * slug : riobard
     * name : Rio 说
     * url : /riobard
     * intro : 科技・商业・设计・体验
     * topicsCanEditUntil : 0
     * activateAuthorRequested : none
     * commentPermission : anyone
     * following : false
     * postsCount : 41
     * canPost : false
     */

    private int followersCount;
    /**
     * profileUrl : https://www.zhihu.com/people/rio
     * bio : 天使投资新人、伪全栈工程师、业余播客主播
     * hash : 0d81a29a497b91e0f374ae0508de779d
     * uid : 26675878297600
     * isOrg : false
     * description : 《疯投圈》 https://crazy.capital/
     * 《内核恐慌》 https://kernelpanic.fm/
     * slug : rio
     * avatar : {"id":"bc89e75b26846b4b2a5a6fd815be6558","template":"https://pic1.zhimg.com/{id}_{size}.jpg"}
     * name : Rio
     */

    private CreatorBean creator;
    private String activateState;
    private String href;
    private boolean acceptSubmission;
    private boolean firstTime;
    private String pendingName;
    /**
     * id : 6cf3b3138
     * template : https://pic1.zhimg.com/{id}_{size}.jpg
     */

    private AvatarBean avatar;
    private boolean canManage;
    private String description;
    private int nameCanEditUntil;
    private String reason;
    private int banUntil;
    private String slug;
    private String name;
    private String url;
    private String intro;
    private int topicsCanEditUntil;
    private String activateAuthorRequested;
    private String commentPermission;
    private boolean following;
    private int postsCount;
    private boolean canPost;
    /**
     * url : https://www.zhihu.com/topic/19623806
     * id : 19623806
     * name : Rio（知乎用户）
     */

    private List<TopicsBean> topics;
    /**
     * postsCount : 1
     * id : 2
     * name : 知乎
     */

    private List<PostTopicsBean> postTopics;
    private List<?> pendingTopics;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }

    public String getActivateState() {
        return activateState;
    }

    public void setActivateState(String activateState) {
        this.activateState = activateState;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isAcceptSubmission() {
        return acceptSubmission;
    }

    public void setAcceptSubmission(boolean acceptSubmission) {
        this.acceptSubmission = acceptSubmission;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getPendingName() {
        return pendingName;
    }

    public void setPendingName(String pendingName) {
        this.pendingName = pendingName;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public boolean isCanManage() {
        return canManage;
    }

    public void setCanManage(boolean canManage) {
        this.canManage = canManage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNameCanEditUntil() {
        return nameCanEditUntil;
    }

    public void setNameCanEditUntil(int nameCanEditUntil) {
        this.nameCanEditUntil = nameCanEditUntil;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getBanUntil() {
        return banUntil;
    }

    public void setBanUntil(int banUntil) {
        this.banUntil = banUntil;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getTopicsCanEditUntil() {
        return topicsCanEditUntil;
    }

    public void setTopicsCanEditUntil(int topicsCanEditUntil) {
        this.topicsCanEditUntil = topicsCanEditUntil;
    }

    public String getActivateAuthorRequested() {
        return activateAuthorRequested;
    }

    public void setActivateAuthorRequested(String activateAuthorRequested) {
        this.activateAuthorRequested = activateAuthorRequested;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    public List<PostTopicsBean> getPostTopics() {
        return postTopics;
    }

    public void setPostTopics(List<PostTopicsBean> postTopics) {
        this.postTopics = postTopics;
    }

    public List<?> getPendingTopics() {
        return pendingTopics;
    }

    public void setPendingTopics(List<?> pendingTopics) {
        this.pendingTopics = pendingTopics;
    }

    public static class CreatorBean {
        private String profileUrl;
        private String bio;
        private String hash;
        private long uid;
        private boolean isOrg;
        private String description;
        private String slug;
        /**
         * id : bc89e75b26846b4b2a5a6fd815be6558
         * template : https://pic1.zhimg.com/{id}_{size}.jpg
         */

        private AvatarBean avatar;
        private String name;

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public boolean isIsOrg() {
            return isOrg;
        }

        public void setIsOrg(boolean isOrg) {
            this.isOrg = isOrg;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TopicsBean {
        private String url;
        private String id;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PostTopicsBean {
        private int postsCount;
        private int id;
        private String name;

        public int getPostsCount() {
            return postsCount;
        }

        public void setPostsCount(int postsCount) {
            this.postsCount = postsCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
