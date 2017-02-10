package com.hanyee.geekzone.presenter;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanyee.androidlib.network.RemoteApiCallback;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.base.SuperiorPresenter;
import com.hanyee.geekzone.model.bean.zhihu.ColumnArticleDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnCommentsBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsExtraBean;
import com.hanyee.geekzone.model.source.local.LocalDataSourceManager;
import com.hanyee.geekzone.model.source.remote.RemoteDataSourceManager;
import com.hanyee.geekzone.presenter.contract.ArticleDetailContract.Presenter;
import com.hanyee.geekzone.presenter.contract.ArticleDetailContract.View;
import com.hanyee.geekzone.widget.ArticleWebView;
import com.tencent.smtt.sdk.WebView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/14.
 */
public class ArticleDetailPresenter extends SuperiorPresenter<View> implements Presenter {

    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;

    @Inject
    public ArticleDetailPresenter(LocalDataSourceManager localDataSourceManager,
                                  RemoteDataSourceManager remoteDataSourceManager) {
        mLocalDataSourceManager = localDataSourceManager;
        mRemoteDataSourceManager = remoteDataSourceManager;
    }

    @Override
    public void loadNewsArticleContent(int id) {
        mView.initWebView(mLocalDataSourceManager.isArticleNoImage(),
                mLocalDataSourceManager.isArticleAutoCache());
        mRemoteDataSourceManager.getNewsDetailInfo(id, new RemoteApiCallback<NewsDetailBean>(this) {
            @Override
            public void onSuccess(NewsDetailBean response) {
                mView.showNewsArticleContent(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, mView.isFirstLoad());
            }

            @Override
            public void onFailure() {
                mView.showError("", mView.isFirstLoad());
            }
        });
    }

    @Override
    public void loadColumnArticleContent(int id) {
        mView.initWebView(mLocalDataSourceManager.isArticleNoImage(),
                mLocalDataSourceManager.isArticleAutoCache());
        mRemoteDataSourceManager.getColumnArticleDetail(id, new RemoteApiCallback<ColumnArticleDetailBean>(this) {
            @Override
            public void onSuccess(ColumnArticleDetailBean response) {
                mView.showColumnArticleContent(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, mView.isFirstLoad());
            }

            @Override
            public void onFailure() {
                mView.showError("", mView.isFirstLoad());
            }
        });
    }

    @Override
    public void loadNewsArticleExtraContent(int id) {
        mRemoteDataSourceManager.getNewsExtraInfo(id, new RemoteApiCallback<NewsExtraBean>(this) {
            @Override
            public void onSuccess(NewsExtraBean response) {
                mView.showNewsArticleExtraContent(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, mView.isFirstLoad());
            }

            @Override
            public void onFailure() {
                mView.showError("", mView.isFirstLoad());
            }
        });
    }

    @Override
    public void loadColumnArticleComments(int id) {
        mRemoteDataSourceManager.getColumnComments(id, 20, 0, new RemoteApiCallback<List<ColumnCommentsBean>>(this) {
            @Override
            public void onSuccess(List<ColumnCommentsBean> response) {
                mView.showColumnArticleComment(response);
            }

            @Override
            public void onError(String erro) {
                mView.showError(erro, mView.isFirstLoad());
            }

            @Override
            public void onFailure() {
                mView.showError("", mView.isFirstLoad());
            }
        });
    }

    @Override
    public void likeZhiHuNews(String title, String picUrl, int articleId, String type) {
        mLocalDataSourceManager.likeZhiHuNews(title, picUrl, articleId, type);
    }

    @Override
    public boolean isZhiHuNewsLiked(int articleId) {
        return mLocalDataSourceManager.isZhiHuNewsLiked(articleId);
    }

    @Override
    public void unlikeZhiHuNews(int articleId) {
        mLocalDataSourceManager.unlikeZhiHuNews(articleId);
    }

    @Override
    public void likeZhiHuColumnArticle(String title, String picUrl, String authorName, String time, int likeCount, int commentCount, int articleId, String type) {
        mLocalDataSourceManager.likeZhiHuColumnArticle(title, picUrl, authorName, time, likeCount, commentCount, articleId, type);
    }

    @Override
    public boolean isZhiHuColumnArticleLiked(int articleId) {
        return mLocalDataSourceManager.isZhiHuColumnArticleLiked(articleId);
    }

    @Override
    public void unlikeZhiHuColumnArticle(int articleId) {
        mLocalDataSourceManager.unlikeZhiHuColumnArticle(articleId);
    }

    @Override
    public void likeWeChatArticle(String title, String desc, String time, String picUrl, String articleUrl, String type) {
        mLocalDataSourceManager.likeWeChatArticle(title, desc, time, picUrl, articleUrl, type);
    }

    @Override
    public boolean isWeChatArticleLiked(String articleUrl) {
        return mLocalDataSourceManager.isWeChatArticleLiked(articleUrl);
    }

    @Override
    public void unlikeWeChatArticle(String articleUrl) {
        mLocalDataSourceManager.unlikeWeChatArticle(articleUrl);
    }

    @Override
    public void likeGankArticle(String title, String authorName, String time, String picUrl, String articleUrl, String type) {
        mLocalDataSourceManager.likeGankArticle(title, authorName, time, picUrl, articleUrl, type);
    }

    @Override
    public boolean isGankArticleLiked(String articleUrl) {
        return mLocalDataSourceManager.isGankArticleLiked(articleUrl);
    }

    @Override
    public void unlikeGankArticle(String articleUrl) {
        mLocalDataSourceManager.unlikeGankArticle(articleUrl);
    }

    @Override
    public void loadWebViewUrl(ArticleWebView webView, String url) {
        webView.loadUrl(url, new ArticleWebView.OnArticleViewLoadListener() {
            @Override
            public void onPageStarted(WebView view, String s, Bitmap bitmap) {

            }

            @Override
            public void onLoadResource(WebView view, String s) {

            }

            @Override
            public void onPageFinished(WebView view, String s) {
                mView.finishLoading();
            }

            @Override
            public void onReceivedError(WebView view, int erroCode, String description, String failingUrl) {
                mView.showError(description, mView.isFirstLoad());
            }
        });
    }

    @Override
    public void loadArticleTitleImage(String url, ImageView imageView) {
        Glide.with(GeekZoneApplication.getInstance())
                .load(url)
//                .override(imageView.getWidth(), imageView.getHeight())
                .fitCenter()
                .error(R.drawable.ic_default_image)
                .into(imageView);
    }

    @Override
    public void detachView() {
        mLocalDataSourceManager = null;
        mRemoteDataSourceManager = null;
        super.detachView();
    }
}
