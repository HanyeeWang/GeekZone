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

package com.hanyee.geekzone.ui.fragment.tianxin;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
import com.hanyee.geekzone.presenter.NewsListPresenter;
import com.hanyee.geekzone.presenter.contract.NewsListContract;
import com.hanyee.geekzone.ui.adapter.tianxin.TianXinNewsAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.TIANXIN.NEWS;

public class NewsListFragment extends SuperiorFragment<NewsListPresenter> implements NewsListContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout mRefresh;

    @Inject
    TianXinNewsAdapter mRecycleAdapter;

    private String mCategory;

    public NewsListFragment category(String category) {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleAdapter.setTianXinType(NEWS);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRefresh.setOnRefreshListener(new TwinklingRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mIsFirstLoad = false;
                mPresenter.loadNews(mCategory);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mIsFirstLoad = false;
                mPresenter.loadMoreNews(mCategory);
            }
        });
        mPresenter.loadNews(mCategory);
    }

    @Override
    public void showNews(List<TianXinNewsBean> list) {
        if (mIsFirstLoad) finishLoading();
        mRefresh.finishRefreshing();
        mRecycleAdapter.setData(list);
    }

    @Override
    public void showMoreNews(List<TianXinNewsBean> list) {
        mRefresh.finishLoadmore();
        mRecycleAdapter.addData(list);
    }

    @Override
    public void showError(String msg, boolean showErrorView) {
        super.showError(msg, showErrorView);
        mRefresh.finishRefreshing();
        mRefresh.finishLoadmore();
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadNews(mCategory);
    }
}
