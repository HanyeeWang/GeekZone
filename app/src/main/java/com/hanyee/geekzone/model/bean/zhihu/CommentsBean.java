package com.hanyee.geekzone.model.bean.zhihu;

import java.util.List;

/**
 * Created by Hanyee on 16/10/13.
 */
public class CommentsBean {

    /**
     * author : 想取个让你一眼记住的名字好难
     * content : 这就是一句谎话。
     * avatar : http://pic3.zhimg.com/44c31fda70384eaf2aa76a2ce03917ee_im.jpg
     * time : 1476328367
     * reply_to : {"content":"\u201c每天普通人平均会讲 10 到 200 个谎言\u201d是真的吗\u2026\u2026\u2026","status":0,"id":26856720,"author":"小椰子熊"}
     * id : 26857655
     * likes : 0
     */

    private List<CommentBean> comments;

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public static class CommentBean {
        private String author;
        private String content;
        private String avatar;
        private int time;
        /**
         * content : “每天普通人平均会讲 10 到 200 个谎言”是真的吗………
         * status : 0
         * id : 26856720
         * author : 小椰子熊
         */

        private ReplyToBean reply_to;
        private int id;
        private int likes;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public static class ReplyToBean {
            private String content;
            private int status;
            private int id;
            private String author;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }
    }
}
