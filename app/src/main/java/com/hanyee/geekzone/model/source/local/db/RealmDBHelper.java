package com.hanyee.geekzone.model.source.local.db;

import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;
import com.hanyee.geekzone.model.source.local.LocalDataSource;

import java.util.List;

import javax.inject.Inject;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Hanyee on 16/10/10.
 */
public class RealmDBHelper implements LocalDataSource.DataBaseSource {

    private static final String DB_NAME  = "GeekZone.realm";
    private static final long DB_VERSION = 0l;

    private Realm mRealm;

    private final static class LikeArticleFieldMap {
        public final static String ID             = "id";
        public final static String TYPE           = "type";
        public final static String POSTSCOUNT     = "postsCount";
        public final static String FOLLOWERSCOUNT = "followersCount";
        public final static String LIKETCOUNT     = "likeCount";
        public final static String COMMENTCOUNT   = "commentCount";
        public final static String BROWSECOUNT    = "browseCount";
        public final static String INSERTTIME     = "insertTime";
        public final static String TITLE          = "title";
        public final static String CTIME          = "ctime";
        public final static String AUTHORHREF     = "authorHref";
        public final static String AUTHORNAME     = "authorName";
        public final static String PICURL         = "picUrl";
        public final static String DESCRIPTION    = "description";
        public final static String ARTICLEURL     = "articleUrl";
    }

    private static class RealmDBMigration implements RealmMigration {
        /**
         * This method will be called if a migration is needed. The entire method is wrapped in a
         * write transaction so it is possible to create, update or delete any existing objects
         * without wrapping it in your own transaction.
         *
         * @param realm      the Realm schema on which to perform the migration.
         * @param oldVersion the schema version of the Realm at the start of the migration.
         * @param newVersion the schema version of the Realm after executing the migration.
         */
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        }
    }

    @Inject
    public RealmDBHelper() {
        Realm.init(GeekZoneApplication.getInstance());
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .migration(new RealmDBMigration())
                .schemaVersion(DB_VERSION)
                .name(DB_NAME)
                .build());
    }

    @Override
    public void likeZhiHuNews(final String title, final String picUrl, final int articleId, final String type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LikeArticleBean likeArticleBean = realm.createObject(LikeArticleBean.class);
                likeArticleBean.setTitle(title);
                likeArticleBean.setPicUrl(picUrl);
                likeArticleBean.setId(articleId);
                likeArticleBean.setType(type);
                likeArticleBean.setInsertTime(System.currentTimeMillis());
            }
        });
    }

    @Override
    public boolean isZhiHuNewsLiked(int articleId) {
        LikeArticleBean articleBean = mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.ID, articleId).findFirst();
        if (articleBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlikeZhiHuNews(final int articleId) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LikeArticleBean> results = realm.where(LikeArticleBean.class)
                        .equalTo(LikeArticleFieldMap.ID, articleId).findAll();
                if (results != null && !results.isEmpty() && results.isValid()) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    @Override
    public void likeZhiHuColumnAuthor(final String title, final String picUrl, final String desc,
                                      final String authorHref, final int followersCount, final int postsCount,
                                      final String type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LikeArticleBean likeArticleBean = realm.createObject(LikeArticleBean.class);
                likeArticleBean.setTitle(title);
                likeArticleBean.setPicUrl(picUrl);
                likeArticleBean.setDescription(desc);
                likeArticleBean.setAuthorHref(authorHref);
                likeArticleBean.setFollowersCount(followersCount);
                likeArticleBean.setPostsCount(postsCount);
                likeArticleBean.setType(type);
                likeArticleBean.setInsertTime(System.currentTimeMillis());
            }
        });
    }

    @Override
    public boolean isZhiHuColumnAuthorLiked(String authorHref) {
        LikeArticleBean articleBean = mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.AUTHORHREF, authorHref).findFirst();
        if (articleBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlikeZhiHuColumnAuthor(final String authorHref) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LikeArticleBean> results = realm.where(LikeArticleBean.class)
                        .equalTo(LikeArticleFieldMap.AUTHORHREF, authorHref).findAll();
                if (results != null && !results.isEmpty() && results.isValid()) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    @Override
    public void likeZhiHuColumnArticle(final String title, final String picUrl, final String authorName,
                                       final String time, final int supportCount, final int commentCount,
                                       final int articleId, final String type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LikeArticleBean likeArticleBean = realm.createObject(LikeArticleBean.class);
                likeArticleBean.setTitle(title);
                likeArticleBean.setPicUrl(picUrl);
                likeArticleBean.setAuthorName(authorName);
                likeArticleBean.setCtime(time);
                likeArticleBean.setLikeCount(supportCount);
                likeArticleBean.setCommentCount(commentCount);
                likeArticleBean.setId(articleId);
                likeArticleBean.setType(type);
                likeArticleBean.setInsertTime(System.currentTimeMillis());
            }
        });
    }

    @Override
    public boolean isZhiHuColumnArticleLiked(int articleId) {
        LikeArticleBean articleBean = mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.ID, articleId).findFirst();
        if (articleBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlikeZhiHuColumnArticle(final int articleId) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LikeArticleBean> results = realm.where(LikeArticleBean.class)
                        .equalTo(LikeArticleFieldMap.ID, articleId).findAll();
                if (results != null && !results.isEmpty() && results.isValid()) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    @Override
    public void likeWeChatArticle(final String title, final String desc, final String time,
                                  final String picUrl, final String articleUrl, final String type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LikeArticleBean likeArticleBean = realm.createObject(LikeArticleBean.class);
                likeArticleBean.setTitle(title);
                likeArticleBean.setDescription(desc);
                likeArticleBean.setCtime(time);
                likeArticleBean.setPicUrl(picUrl);
                likeArticleBean.setArticleUrl(articleUrl);
                likeArticleBean.setType(type);
                likeArticleBean.setInsertTime(System.currentTimeMillis());
            }
        });
    }

    @Override
    public boolean isWeChatArticleLiked(String articleUrl) {
        LikeArticleBean articleBean = mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.ARTICLEURL, articleUrl).findFirst();
        if (articleBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlikeWeChatArticle(final String articleUrl) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LikeArticleBean> results = realm.where(LikeArticleBean.class)
                        .equalTo(LikeArticleFieldMap.ARTICLEURL, articleUrl).findAll();
                if (results != null && !results.isEmpty() && results.isValid()) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    @Override
    public void likeGankArticle(final String title, final String authorName, final String time,
                                final String picUrl, final String articleUrl, final String type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LikeArticleBean likeArticleBean = realm.createObject(LikeArticleBean.class);
                likeArticleBean.setTitle(title);
                likeArticleBean.setAuthorName(authorName);
                likeArticleBean.setCtime(time);
                likeArticleBean.setPicUrl(picUrl);
                likeArticleBean.setArticleUrl(articleUrl);
                likeArticleBean.setType(type);
                likeArticleBean.setInsertTime(System.currentTimeMillis());
            }
        });
    }

    @Override
    public boolean isGankArticleLiked(String articleUrl) {
        LikeArticleBean articleBean = mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.ARTICLEURL, articleUrl).findFirst();
        if (articleBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlikeGankArticle(final String articleUrl) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LikeArticleBean> results = realm.where(LikeArticleBean.class)
                        .equalTo(LikeArticleFieldMap.ARTICLEURL, articleUrl).findAll();
                if (results != null && !results.isEmpty() && results.isValid()) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    @Override
    public List<LikeArticleBean> getAllLikedArticles() {
        return mRealm.where(LikeArticleBean.class)
                .findAll()
                .sort(LikeArticleFieldMap.INSERTTIME, Sort.DESCENDING);
    }

    @Override
    public List<LikeArticleBean> getLikedArticlesByType(String type) {
        return mRealm.where(LikeArticleBean.class)
                .equalTo(LikeArticleFieldMap.TYPE, type)
                .findAll()
                .sort(LikeArticleFieldMap.INSERTTIME, Sort.DESCENDING);
    }
}
