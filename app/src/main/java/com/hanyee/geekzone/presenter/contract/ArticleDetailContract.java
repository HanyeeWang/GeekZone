package com.hanyee.geekzone.presenter.contract;

import android.widget.ImageView;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.ColumnArticleDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnCommentsBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsExtraBean;
import com.hanyee.geekzone.widget.ArticleWebView;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface ArticleDetailContract {

    interface View extends BaseView {
        void initWebView(boolean isNoImage, boolean isNeedCache);

        void showNewsArticleContent(NewsDetailBean detailBean);

        void showColumnArticleContent(ColumnArticleDetailBean detailBean);

        void showNewsArticleExtraContent(NewsExtraBean extraBean);

        void showColumnArticleComment(List<ColumnCommentsBean> extraBean);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNewsArticleContent(int id);

        void loadColumnArticleContent(int id);

        void loadNewsArticleExtraContent(int id);

        void loadColumnArticleComments(int id);

        void likeZhiHuNews(String title, String picUrl, int articleId, String type);

        boolean isZhiHuNewsLiked(int articleId);

        void unlikeZhiHuNews(int articleId);

        void likeZhiHuColumnArticle(String title, String picUrl, String authorName, String time, int supportCount, int commentCount, int articleId, String type);

        boolean isZhiHuColumnArticleLiked(int articleId);

        void unlikeZhiHuColumnArticle(int articleId);

        void likeWeChatArticle(String title, String desc, String time, String picUrl, String articleUrl, String type);

        boolean isWeChatArticleLiked(String articleUrl);

        void unlikeWeChatArticle(String articleUrl);

        void likeGankArticle(String title, String authorName, String time, String picUrl, String articleUrl, String type);

        boolean isGankArticleLiked(String articleUrl);

        void unlikeGankArticle(String articleUrl);

        void loadWebViewUrl(ArticleWebView webView, String url);

        void loadArticleTitleImage(String url, ImageView imageView);
    }

}
