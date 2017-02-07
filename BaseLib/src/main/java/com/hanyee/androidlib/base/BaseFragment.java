package com.hanyee.androidlib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hanyee.androidlib.R;
import com.hanyee.androidlib.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Hanyee on 2016/10/14.
 */
public abstract class BaseFragment extends SupportFragment implements BaseView {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected View mLoadingView;
    protected View mErrorView;
    protected boolean mIsInited;
    protected boolean mIsRequestView;
    protected boolean mIsFirstLoad = true;
    protected boolean mIsLoadingMargin;
    private FrameLayout mRootLayout;
    private Unbinder mUnBinder;
    private View mFloatLoadingView;

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        mContext = (Context) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = getContentView(inflater);
        return mView;
    }

    private View getContentView(LayoutInflater inflater) {
        Object res = getLayoutRes();
        if (res instanceof View) {
            return initView((View) res);
        } else if (res instanceof Integer) {
            return initView(inflater.inflate((Integer) res, null));
        } else {
            throw new IllegalArgumentException("Layout resource type is illegal!");
        }
    }

    private View initView(View view) {
        mRootLayout = new FrameLayout(mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mRootLayout.setLayoutParams(layoutParams);
        mRootLayout.addView(view);

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (mIsLoadingMargin && mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        flp.topMargin = actionBarHeight;

        mErrorView = LayoutInflater.from(mContext).inflate(R.layout.load_failed, null);
        mErrorView.setVisibility(View.GONE);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryLoading();
            }
        });
        mRootLayout.addView(mErrorView, flp);

        mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.loading, null);
        mLoadingView.setVisibility(View.GONE);
        mRootLayout.addView(mLoadingView, flp);

        mFloatLoadingView = LayoutInflater.from(mContext).inflate(R.layout.float_loading, null);
        mFloatLoadingView.setVisibility(View.GONE);
        mRootLayout.addView(mFloatLoadingView, flp);

        return mRootLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        if (mIsRequestView) showLoading(false, true);
        initData();
        mIsInited = true;
    }

    protected void setToolBar(BaseActivity activity, Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
        activity.syncDrawerState(toolbar);
    }

    @Override
    public boolean isFirstLoad() {
        return mIsFirstLoad;
    }

    @Override
    public void showLoading(boolean isFloating, boolean isFirstLoad) {
        mErrorView.setVisibility(View.GONE);
        mIsFirstLoad = isFirstLoad;
        if (isFloating) {
            mFloatLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finishLoading() {
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void retryLoading() {
        showLoading(false, true);
    }

    @Override
    public void showError(String msg, boolean showErrorView) {
        ToastUtils.showMessage(mContext, msg);
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (showErrorView) {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Fragment");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        mIsInited = false;
        System.gc();
    }

    @Override
    public void openNightMode(boolean isNight) {
    }

    protected abstract Object getLayoutRes();

    protected abstract void initData();
}
