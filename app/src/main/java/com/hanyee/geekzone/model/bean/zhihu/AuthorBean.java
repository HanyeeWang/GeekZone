package com.hanyee.geekzone.model.bean.zhihu;

import java.util.List;

public class AuthorBean {
        private String profileUrl;
        private String bio;
        private String hash;
        private long uid;
        private boolean isOrg;
        private String description;
        /**
         * identity : {"description":"「最美应用」与「最美有物」创始人"}
         * best_answerer : {"topics":[],"description":"优秀回答者"}
         */

        private BadgeBean badge;
        private String slug;
        /**
         * id : ba332a401
         * template : https://pic2.zhimg.com/{id}_{size}.jpg
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

        public BadgeBean getBadge() {
            return badge;
        }

        public void setBadge(BadgeBean badge) {
            this.badge = badge;
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

        public static class BadgeBean {
            /**
             * description : 「最美应用」与「最美有物」创始人
             */

            private IdentityBean identity;
            /**
             * topics : []
             * description : 优秀回答者
             */

            private BestAnswererBean best_answerer;

            public IdentityBean getIdentity() {
                return identity;
            }

            public void setIdentity(IdentityBean identity) {
                this.identity = identity;
            }

            public BestAnswererBean getBest_answerer() {
                return best_answerer;
            }

            public void setBest_answerer(BestAnswererBean best_answerer) {
                this.best_answerer = best_answerer;
            }

            public static class IdentityBean {
                private String description;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }

            public static class BestAnswererBean {
                private String description;
                private List<?> topics;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<?> getTopics() {
                    return topics;
                }

                public void setTopics(List<?> topics) {
                    this.topics = topics;
                }
            }
        }

        public static class AvatarBean {
            private String id;
            private String template;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTemplate() {
                return template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }
        }
    }
