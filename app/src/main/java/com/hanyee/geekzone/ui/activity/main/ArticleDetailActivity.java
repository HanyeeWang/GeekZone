/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hanyee.geekzone.ui.activity.main;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanyee.androidlib.utils.SnackbarUtil;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorActivity;
import com.hanyee.geekzone.model.bean.zhihu.ColumnArticleDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnCommentsBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsExtraBean;
import com.hanyee.geekzone.presenter.ArticleDetailPresenter;
import com.hanyee.geekzone.presenter.contract.ArticleDetailContract;
import com.hanyee.geekzone.widget.ArticleWebView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_DESC;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TIME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TITLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_AUTHOR_NAME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_IMAGE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_ARTICLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_GANK;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;
import static com.hanyee.geekzone.util.Constants.TIANXIN.WECHAT;
import static com.hanyee.geekzone.util.Constants.WebView.COLUMN_CSS_FILE;
import static com.hanyee.geekzone.util.Constants.WebView.NEWS_CSS_FILE;

public class ArticleDetailActivity extends SuperiorActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.article_view)
    ArticleWebView mArticleView;
    @BindView(R.id.image_source)
    TextView mImageSource;
    @BindView(R.id.like_article)
    FloatingActionButton mLikeArticleButton;

    private int mId;
    private int mLikesCount;
    private int mCommentsCount;
    private String mType;
    private String mDesc;
    private String mTitle;
    private String mArticleUrl;
    private String mImageUrl;
    private String mAuthorName;
    private String mCreateTime;
    private boolean mIsLiked;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initData() {
        mToolbar.setTitle(null);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mId = getIntent().getIntExtra(EXTRA_ARTICLE_ID, -1);
        mType = getIntent().getStringExtra(EXTRA_ARTICLE_TYPE);
        if (TextUtils.equals(mType, EXTRA_TYPE_COLUMN_ARTICLE)) {
            mIsLiked = mPresenter.isZhiHuColumnArticleLiked(mId);
            mPresenter.loadColumnArticleContent(mId);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_ZHIHU_NEWS)) {
            mIsLiked = mPresenter.isZhiHuNewsLiked(mId);
            mPresenter.loadNewsArticleContent(mId);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_TIANXIN)) {
            mDesc = getIntent().getStringExtra(EXTRA_ARTICLE_DESC);
            mTitle = getIntent().getStringExtra(EXTRA_ARTICLE_TITLE);
            mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
            mArticleUrl = getIntent().getStringExtra(EXTRA_ARTICLE_URL);
            mCreateTime = getIntent().getStringExtra(EXTRA_ARTICLE_TIME);
            String tianxinType = getIntent().getStringExtra(EXTRA_TYPE_TIANXIN_TYPE);

            mPresenter.loadArticleTitleImage(mImageUrl, mBackdrop);
            mIsLiked = mPresenter.isWeChatArticleLiked(mArticleUrl);
            if (TextUtils.equals(tianxinType, WECHAT)) {
                mCollapsingToolbar.setTitle(getString(R.string.wechat));
            } else if (TextUtils.equals(tianxinType, WECHAT)) {
                mCollapsingToolbar.setTitle(getString(R.string.news));
            }
            mPresenter.loadWebViewUrl(mArticleView, mArticleUrl);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_GANK)) {
            mTitle = getIntent().getStringExtra(EXTRA_ARTICLE_TITLE);
            mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
            mArticleUrl = getIntent().getStringExtra(EXTRA_ARTICLE_URL);
            mAuthorName = getIntent().getStringExtra(EXTRA_AUTHOR_NAME);
            mCreateTime = getIntent().getStringExtra(EXTRA_ARTICLE_TIME);

            mPresenter.loadArticleTitleImage(mImageUrl, mBackdrop);
            mIsLiked = mPresenter.isGankArticleLiked(mArticleUrl);
            mCollapsingToolbar.setTitle(getString(R.string.gank));
            mPresenter.loadWebViewUrl(mArticleView, mArticleUrl);
        }
        mLikeArticleButton.setImageResource(mIsLiked ? R.drawable.starred : R.drawable.star);
    }

    @Override
    public void initWebView(boolean isNoImage, boolean isNeedCache) {
        mArticleView.setWebArticleImageState(isNoImage);
        mArticleView.setWebArticleCacheState(isNeedCache);
    }

    @Override
    public void showNewsArticleContent(NewsDetailBean detailBean) {
        if (mIsFirstLoad) finishLoading();

        mTitle = detailBean.getTitle();
        mImageUrl = detailBean.getImages() == null ? null : detailBean.getImages().get(0);

        mCollapsingToolbar.setTitle(detailBean.getTitle());
        mImageSource.setText(detailBean.getImage_source());
        mPresenter.loadArticleTitleImage(detailBean.getImage(), mBackdrop);
        //Load css from local to improve loading speed
        mArticleView.loadDataWithLocalStyle(detailBean.getBody(), NEWS_CSS_FILE, detailBean.getJs());
    }

    @Override
    public void showColumnArticleContent(ColumnArticleDetailBean detailBean) {
        if (mIsFirstLoad) finishLoading();

        mTitle = detailBean.getTitle();
        mImageUrl = detailBean.getTitleImage();
        mAuthorName = detailBean.getAuthor().getName();
        mCreateTime = detailBean.getPublishedTime();
        mLikesCount = detailBean.getLikesCount();
        mCommentsCount = detailBean.getCommentsCount();

        mCollapsingToolbar.setTitle(detailBean.getTitle());
        mPresenter.loadArticleTitleImage(detailBean.getTitleImage(), mBackdrop);
        //Load css from local to improve loading speed
        mArticleView.loadDataWithLocalStyle(detailBean.getContent(), COLUMN_CSS_FILE, null);
    }

    @Override
    public void showNewsArticleExtraContent(NewsExtraBean extraBean) {

    }

    @Override
    public void showColumnArticleComment(List<ColumnCommentsBean> extraBean) {

    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        if (TextUtils.equals(mType, EXTRA_TYPE_COLUMN_ARTICLE)) {
            mPresenter.loadColumnArticleContent(mId);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_ZHIHU_NEWS)) {
            mPresenter.loadNewsArticleContent(mId);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_TIANXIN)) {
            mPresenter.loadWebViewUrl(mArticleView, mArticleUrl);
        } else if (TextUtils.equals(mType, EXTRA_TYPE_GANK)) {
            mPresenter.loadWebViewUrl(mArticleView, mArticleUrl);
        }
    }

    @OnClick(R.id.like_article)
    public void onClick(View view) {
        if (mIsLiked) {
            if (TextUtils.equals(mType, EXTRA_TYPE_ZHIHU_NEWS)) {
                mPresenter.unlikeZhiHuNews(mId);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_COLUMN_ARTICLE)) {
                mPresenter.unlikeZhiHuColumnArticle(mId);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_TIANXIN)) {
                mPresenter.unlikeWeChatArticle(mArticleUrl);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_GANK)) {
                mPresenter.unlikeGankArticle(mArticleUrl);
            }
            ((FloatingActionButton) view).setImageResource(R.drawable.star);
            SnackbarUtil.LongSnackbar(view, R.string.article_unliked, SnackbarUtil.INFO).show();
            mIsLiked = false;
        } else {
            if (TextUtils.equals(mType, EXTRA_TYPE_ZHIHU_NEWS)) {
                mPresenter.likeZhiHuNews(mTitle, mImageUrl, mId, mType);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_COLUMN_ARTICLE)) {
                mPresenter.likeZhiHuColumnArticle(mTitle, mImageUrl, mAuthorName, mCreateTime,
                        mLikesCount, mCommentsCount, mId, mType);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_TIANXIN)) {
                mPresenter.likeWeChatArticle(mTitle, mDesc, mCreateTime, mImageUrl, mArticleUrl, mType);
            } else if (TextUtils.equals(mType, EXTRA_TYPE_GANK)) {
                mPresenter.likeGankArticle(mTitle, mAuthorName, mCreateTime, mImageUrl, mArticleUrl, mType);
            }
            ((FloatingActionButton) view).setImageResource(R.drawable.starred);
            SnackbarUtil.LongSnackbar(view, R.string.article_liked, SnackbarUtil.INFO).show();
            mIsLiked = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mArticleView.canGoBack()) {
            mArticleView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mArticleView != null) {
            mArticleView.destroy();
        }
        super.onDestroy();
        System.gc();
    }
}
