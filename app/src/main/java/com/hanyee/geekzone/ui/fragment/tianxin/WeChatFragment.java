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
import android.support.v7.widget.Toolbar;

import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.androidlib.widgets.recycler.SwipeRefreshLayout;
import com.hanyee.androidlib.widgets.recycler.SwipeRefreshLayoutDirection;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
import com.hanyee.geekzone.presenter.WeChatPresenter;
import com.hanyee.geekzone.presenter.contract.WeChatContract;
import com.hanyee.geekzone.ui.adapter.tianxin.TianXinNewsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.TIANXIN.WECHAT;

public class WeChatFragment extends SuperiorFragment<WeChatPresenter> implements WeChatContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    TianXinNewsAdapter mTianXinNewsAdapter;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        mIsLoadingMargin = true;
        return R.layout.fragment_wechat;
    }

    @Override
    protected void initData() {
        setToolBar((BaseActivity) mActivity, mToolbar, getString(R.string.wechat));

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTianXinNewsAdapter.setTianXinType(WECHAT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTianXinNewsAdapter);
        mRefresh.setDirection(SwipeRefreshLayoutDirection.BOTH);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                mIsFirstLoad = false;
                mPresenter.loadWeChatNewsList();
            }

            @Override
            public void onLoad(int index) {
                mIsFirstLoad = false;
                mPresenter.loadMoreWeChatNews();
            }
        });
        mPresenter.loadWeChatNewsList();
    }

    @Override
    public void showWeChatNewsList(List<TianXinNewsBean> list) {
        if (mIsFirstLoad) finishLoading();
        mRefresh.setRefreshing(false);
        mTianXinNewsAdapter.setData(list);
    }

    @Override
    public void showMoreWeChatNews(List<TianXinNewsBean> list) {
        mRefresh.setRefreshing(false);
        mTianXinNewsAdapter.addData(list);
    }

    @Override
    public void showError(String msg, boolean showErrorView) {
        super.showError(msg, showErrorView);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadWeChatNewsList();
    }
}
