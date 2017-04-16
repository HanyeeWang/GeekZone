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

package com.hanyee.geekzone.ui.activity.zhihu;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hanyee.androidlib.cache.image.glide.BlurTransformation;
import com.hanyee.androidlib.cache.image.glide.CircleTransform;
import com.hanyee.androidlib.utils.SnackbarUtil;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorActivity;
import com.hanyee.geekzone.model.bean.zhihu.AuthorBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnAuthorDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnsBean;
import com.hanyee.geekzone.presenter.ColumnListPresenter;
import com.hanyee.geekzone.presenter.contract.ColumnListContract;
import com.hanyee.geekzone.ui.adapter.zhihu.ColumnListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_AUTHOR_NAME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_AUTHOR;

public class ColumnListActivity extends SuperiorActivity<ColumnListPresenter> implements ColumnListContract.View {

    @BindView(R.id.author_name)
    TextView mAuthorName;
    @BindView(R.id.author_intro)
    TextView mAuthorIntro;
    @BindView(R.id.author_avatar)
    ImageView mAuthorAvatar;
    @BindView(R.id.author_desc)
    TextView mAuthorDesc;
    @BindView(R.id.follow_times)
    TextView mFollowTimes;
    @BindView(R.id.follower)
    LinearLayout mFollower;
    @BindView(R.id.posts_num)
    TextView mPostsNum;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.column_list_recycler_view)
    RecyclerView mColumnListRecyclerView;
    @BindView(R.id.head_layout)
    LinearLayout mHeadLayout;
    @BindView(R.id.column_list_content)
    CoordinatorLayout mColumnListContent;
    @BindView(R.id.action_bar_title)
    TextView mActionBarTitle;
    @BindView(R.id.like_author)
    TextView mLikeAuthor;

    @Inject
    ColumnListAdapter mColumnListAdapter;

    private boolean mIsLiked;
    private String mColumnAuthorHref;
    private String mColumnName;
    private String mColumnIntro;
    private String mColumnDesc;
    private String mAvatar;
    private int mColumnFollowers;
    private int mColumnPosts;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.activity_column_list;
    }

    @Override
    protected void initData() {
        mColumnAuthorHref = getIntent().getStringExtra(EXTRA_AUTHOR_NAME);
        mToolbar.setTitle(null);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mColumnListRecyclerView.setLayoutParams(new NestedScrollView.LayoutParams(
                NestedScrollView.LayoutParams.MATCH_PARENT,
                NestedScrollView.LayoutParams.MATCH_PARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mColumnListRecyclerView.setLayoutManager(layoutManager);
        mColumnListRecyclerView.setAdapter(mColumnListAdapter);
        mPresenter.loadColumnAuthorInfo(mColumnAuthorHref);
        mPresenter.loadColumnList(mColumnAuthorHref);
        mIsLiked = mPresenter.isZhiHuColumnAuthorLiked(mColumnAuthorHref);
        mLikeAuthor.setText(getString(mIsLiked ? R.string.zhihu_followed : R.string.zhihu_follow));
    }

    @Override
    public void showColumnAuthorInfo(final ColumnAuthorDetailBean author) {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadLayout.getHeight() / 2) {
                    mActionBarTitle.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    mActionBarTitle.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        mAuthorName.setText(mColumnName = author.getName());
        mAuthorIntro.setText(mColumnIntro = author.getIntro());
        mAuthorDesc.setText(mColumnDesc = author.getDescription());
        mFollowTimes.setText(getString(R.string.zhihu_follower_number,
                mColumnFollowers = author.getFollowersCount()));
        mPostsNum.setText(getString(R.string.zhihu_post_number,
                mColumnPosts = author.getPostsCount()));

        AuthorBean.AvatarBean avatar = author.getAvatar();
        mAvatar = avatar.getTemplate().replace("{id}", avatar.getId()).replace("{size}", "m");
        Glide.with(this).load(mAvatar)
                .override(mAuthorAvatar.getWidth(), mAuthorAvatar.getHeight())
                .transform(new CircleTransform(this)).into(mAuthorAvatar);
        Glide.with(this).load(mAvatar)
                .override(mHeadLayout.getWidth(), mHeadLayout.getHeight())
                .bitmapTransform(new BlurTransformation(this, 8, 5))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mHeadLayout.setBackground(resource);
                    }
                });
    }

    @Override
    public void showColumnList(List<ColumnsBean> list) {
        if (mIsFirstLoad) finishLoading();
        mColumnListAdapter.setData(list);
    }

    @OnClick(R.id.like_author)
    public void onClick(View view) {
        if (mIsLiked) {
            mPresenter.unlikeZhiHuColumnAuthor(mColumnAuthorHref);
            ((TextView) view).setText(R.string.zhihu_follow);
            SnackbarUtil.LongSnackbar(view, R.string.author_unfollowed, SnackbarUtil.INFO);
            mIsLiked = false;
        } else {
            mPresenter.likeZhiHuColumnAuthor(mColumnName, mAvatar, mColumnDesc, mColumnAuthorHref,
                    mColumnFollowers, mColumnPosts, EXTRA_TYPE_COLUMN_AUTHOR);
            ((TextView) view).setText(R.string.zhihu_followed);
            SnackbarUtil.LongSnackbar(view, R.string.author_followed, SnackbarUtil.INFO).show();
            mIsLiked = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadColumnAuthorInfo(mColumnAuthorHref);
        mPresenter.loadColumnList(mColumnAuthorHref);
    }
}
