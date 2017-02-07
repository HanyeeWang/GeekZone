package com.hanyee.androidlib.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hanyee.androidlib.R;
import com.hanyee.androidlib.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Hanyee on 2016/10/14.
 */
public abstract class BaseActivity extends SupportActivity implements BaseView {

    private Unbinder mUnBinder;
    private FrameLayout mRootLayout;
    private View mLoadingView;
    private View mErrorView;
    private View mFloatLoadingView;

    protected boolean mIsRequestView;
    protected boolean mIsFirstLoad = true;
    protected boolean mIsLoadingMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        mUnBinder = ButterKnife.bind(this);
        if (mIsRequestView) showLoading(false, true);
        initData();
    }

    private void setContentView() {
        Object res = getLayoutRes();
        if (res instanceof View) {
            initView((View) res);
        } else if (res instanceof Integer) {
            initView(LayoutInflater.from(this).inflate((Integer) res, null));
        } else {
            throw new IllegalArgumentException("Layout resource type is illegal!");
        }
    }

    private void initView(View view) {
        mRootLayout = new FrameLayout(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mRootLayout.setLayoutParams(layoutParams);
        mRootLayout.addView(view);

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (mIsLoadingMargin && getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        flp.topMargin = actionBarHeight;

        mErrorView = LayoutInflater.from(this).inflate(R.layout.load_failed, null);
        mErrorView.setVisibility(View.GONE);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryLoading();
            }
        });
        mRootLayout.addView(mErrorView, flp);

        mLoadingView = LayoutInflater.from(this).inflate(R.layout.loading, null);
        mLoadingView.setLayoutParams(layoutParams);
        mLoadingView.setVisibility(View.GONE);
        mRootLayout.addView(mLoadingView, flp);

        mFloatLoadingView = LayoutInflater.from(this).inflate(R.layout.float_loading, null);
        mFloatLoadingView.setVisibility(View.GONE);
        mRootLayout.addView(mFloatLoadingView, flp);

        setContentView(mRootLayout);
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
        mLoadingView.setVisibility(View.GONE);
        mFloatLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void retryLoading() {
        showLoading(false, true);
    }

    @Override
    public void showError(String msg, boolean showErrorView) {
        ToastUtils.showMessage(getApplicationContext(), msg);
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (showErrorView) {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        System.gc();
    }

    @Override
    public void openNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    protected abstract Object getLayoutRes();

    protected abstract void initData();

    protected abstract void syncDrawerState(Toolbar toolbar);
}
