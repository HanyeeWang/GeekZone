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

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorActivity;
import com.hanyee.geekzone.model.bean.zhihu.SpecialListBean;
import com.hanyee.geekzone.presenter.SpecialListPresenter;
import com.hanyee.geekzone.presenter.contract.SpecialListContract;
import com.hanyee.geekzone.ui.adapter.zhihu.SpecialListAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_SPECIAL_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_SPECIAL_NAME;

public class SpecialListActivity extends SuperiorActivity<SpecialListPresenter> implements SpecialListContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.special_list_recycler_view)
    RecyclerView mSpecialListRecyclerView;

    @Inject
    SpecialListAdapter mSpecialListAdapter;

    private int mId;

    @Override
    protected void inject() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        getActivityComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.activity_special_list;
    }

    @Override
    protected void initData() {
        mId = getIntent().getIntExtra(EXTRA_SPECIAL_ID, 0);
        final String name = getIntent().getStringExtra(EXTRA_SPECIAL_NAME);
        mToolbar.setTitle(name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpecialListRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSpecialListRecyclerView.setLayoutManager(layoutManager);
        mSpecialListRecyclerView.setAdapter(mSpecialListAdapter);
        mPresenter.loadSpecialsList(mId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public void showSpecialsList(SpecialListBean list) {
        if (mIsFirstLoad) finishLoading();
        mSpecialListAdapter.setData(list.getStories());
    }

    @Override
    public void showMoreSpecialsList(SpecialListBean list) {

    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadSpecialsList(mId);
    }
}