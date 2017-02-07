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

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorActivity;
import com.hanyee.geekzone.model.bean.zhihu.ThemesListBean;
import com.hanyee.geekzone.presenter.ThemeListPresenter;
import com.hanyee.geekzone.presenter.contract.ThemeListContract;
import com.hanyee.geekzone.ui.adapter.zhihu.ThemeListAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_THEME_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_THEME_NAME;

public class ThemeListActivity extends SuperiorActivity<ThemeListPresenter> implements ThemeListContract.View {

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.theme_list_content)
    CoordinatorLayout mZhihuListContent;
    @BindView(R.id.theme_list_recycler_view)
    RecyclerView mZhihuListRecyclerView;

    @Inject
    ThemeListAdapter mThemeListAdapter;

    private int mId;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.activity_theme_list;
    }

    @Override
    protected void initData() {
        mId = getIntent().getIntExtra(EXTRA_THEME_ID, 0);
        final String name = getIntent().getStringExtra(EXTRA_THEME_NAME);

        mToolbar.setTitle(name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mZhihuListRecyclerView.setLayoutParams(new NestedScrollView.LayoutParams(
                NestedScrollView.LayoutParams.MATCH_PARENT,
                NestedScrollView.LayoutParams.MATCH_PARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mZhihuListRecyclerView.setLayoutManager(layoutManager);
        mZhihuListRecyclerView.setAdapter(mThemeListAdapter);
        mPresenter.loadThemesList(mId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public void showThemesList(ThemesListBean list) {
        if (mIsFirstLoad) finishLoading();
        Glide.with(getApplicationContext()).load(list.getBackground())
                .centerCrop().into(mBackdrop);
        mThemeListAdapter.setData(list.getStories());
    }

    @Override
    public void showMoreThemesList(ThemesListBean list) {

    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadThemesList(mId);
    }
}
