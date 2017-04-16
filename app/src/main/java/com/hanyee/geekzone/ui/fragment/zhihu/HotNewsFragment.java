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

package com.hanyee.geekzone.ui.fragment.zhihu;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanyee.androidlib.widgets.recycler.SwipeRefreshLayout;
import com.hanyee.androidlib.widgets.recycler.SwipeRefreshLayoutDirection;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.zhihu.HotNewsBean.RecentBean;
import com.hanyee.geekzone.presenter.HotNewsPresenter;
import com.hanyee.geekzone.presenter.contract.HotNewsContract;
import com.hanyee.geekzone.ui.adapter.zhihu.HotNewsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class HotNewsFragment extends SuperiorFragment<HotNewsPresenter> implements HotNewsContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    HotNewsAdapter mHotNewsAdapter;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mHotNewsAdapter);
        mRefresh.setDirection(SwipeRefreshLayoutDirection.TOP);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                Fragment parent = getParentFragment();
                if (parent instanceof ZhiHuFragment) {
                    ((ZhiHuFragment)parent).startWaveAnimation();
                }
                mIsFirstLoad = false;
                mPresenter.loadHotNews();
            }

            @Override
            public void onLoad(int index) {
            }
        });
        mPresenter.loadHotNews();
    }

    @Override
    public void showHotNews(List<RecentBean> list) {
        if (mIsFirstLoad) finishLoading();
        mRefresh.setRefreshing(false);
        mHotNewsAdapter.setData(list);
    }

    @Override
    public void showError(String msg, boolean onlyToast) {
        super.showError(msg, onlyToast);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadHotNews();
    }
}
