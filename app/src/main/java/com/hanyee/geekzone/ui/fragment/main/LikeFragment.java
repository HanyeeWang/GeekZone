package com.hanyee.geekzone.ui.fragment.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;
import com.hanyee.geekzone.presenter.LikePresenter;
import com.hanyee.geekzone.presenter.contract.LikeContract;
import com.hanyee.geekzone.ui.adapter.main.LikedArticleAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Hanyee on 16/10/18.
 */
public class LikeFragment extends SuperiorFragment<LikePresenter> implements LikeContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_content)
    LinearLayout mMainContent;

    @Inject
    LikedArticleAdapter mLikedArticleAdapter;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.fragment_like;
    }

    @Override
    protected void initData() {
        setToolBar((BaseActivity) mActivity, mToolbar, getString(R.string.like));

        mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mLikedArticleAdapter);
        mPresenter.loadLikedArticles();
    }

    @Override
    public void showLikedArticles(List<LikeArticleBean> zhihuList, List<LikeArticleBean> columnAuthorList,
                                  List<LikeArticleBean> columnArticleList, List<LikeArticleBean> wechatList,
                                  List<LikeArticleBean> gankList) {
        if (mIsFirstLoad) finishLoading();
        mLikedArticleAdapter.setData(zhihuList, columnAuthorList, columnArticleList, wechatList, gankList);
    }

    @Override
    public void retryLoading() {
        super.retryLoading();
        mPresenter.loadLikedArticles();
    }
}
