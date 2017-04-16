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

package com.hanyee.geekzone.ui.fragment.gank;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.hanyee.androidlib.widgets.recycler.SwipeRefreshLayout;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;
import com.hanyee.geekzone.presenter.GankListPresenter;
import com.hanyee.geekzone.presenter.contract.GankListContract;
import com.hanyee.geekzone.ui.adapter.gank.GankListAdapter;
import com.hanyee.geekzone.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class GankListFragment extends SuperiorFragment<GankListPresenter> implements GankListContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    GankListAdapter mRecycleAdapter;

    private String mCategory;

    public GankListFragment category(String category) {
        mCategory = category;
        return this;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.refresh_recycler;
    }

    @Override
    protected void initData() {
        final RecyclerView.LayoutManager layoutManager;
        if (TextUtils.equals(mCategory, Constants.GANK.WELFARE)) {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            ((StaggeredGridLayoutManager) layoutManager)
                    .setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        } else {
            layoutManager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    ((StaggeredGridLayoutManager) layoutManager).invalidateSpanAssignments();
                }
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        mRecycleAdapter.setScrollStateFling(false);
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        mRecycleAdapter.setScrollStateFling(false);
                        break;
                    case SCROLL_STATE_FLING:
                        mRecycleAdapter.setScrollStateFling(true);
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                Fragment parent = getParentFragment();
                if (parent instanceof GankFragment) {
                    ((GankFragment) parent).startWaveAnimation();
                }
                mIsFirstLoad = false;
                mPresenter.loadGankNewsByCategory(mCategory);
            }

            @Override
            public void onLoad(int index) {
                mIsFirstLoad = false;
                mPresenter.loadMoreGankNewsByCategory(mCategory);
            }
        });
        mPresenter.loadGankNewsByCategory(mCategory);
    }

    @Override
    public void showGankNews(List<GankNewsBean> list) {
        if (mIsFirstLoad) finishLoading();
        mRefresh.setRefreshing(false);
        mRecycleAdapter.setData(list);
    }

    @Override
    public void showMoreGankNews(List<GankNewsBean> list) {
        mRefresh.setRefreshing(false);
        mRecycleAdapter.addData(list);
    }

    @Override
    public void showError(String msg, boolean showErrorView) {
        super.showError(msg, showErrorView);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadGankNewsByCategory(mCategory);
    }
}
