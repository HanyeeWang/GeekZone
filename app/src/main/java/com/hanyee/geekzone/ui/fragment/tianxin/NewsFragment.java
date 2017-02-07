package com.hanyee.geekzone.ui.fragment.tianxin;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.androidlib.base.BaseFragment;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.ui.adapter.main.Adapter;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.TIANXIN.APPLE;
import static com.hanyee.geekzone.util.Constants.TIANXIN.GUONEI;
import static com.hanyee.geekzone.util.Constants.TIANXIN.HEALTH;
import static com.hanyee.geekzone.util.Constants.TIANXIN.HUABIAN;
import static com.hanyee.geekzone.util.Constants.TIANXIN.KEJI;
import static com.hanyee.geekzone.util.Constants.TIANXIN.SOCIAL;
import static com.hanyee.geekzone.util.Constants.TIANXIN.TIYU;
import static com.hanyee.geekzone.util.Constants.TIANXIN.WORLD;

/**
 * Created by Hanyee on 16/10/18.
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;

    @Override
    protected void initData() {
        setToolBar((BaseActivity) mActivity, mToolbar, getString(R.string.news));
        setupViewPager(mViewPager);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.fragment_gank;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new NewsListFragment().category(GUONEI), getString(R.string.news_guonei));
        adapter.addFragment(new NewsListFragment().category(SOCIAL), getString(R.string.news_social));
        adapter.addFragment(new NewsListFragment().category(WORLD), getString(R.string.news_world));
        adapter.addFragment(new NewsListFragment().category(HUABIAN), getString(R.string.news_huabian));
        adapter.addFragment(new NewsListFragment().category(KEJI), getString(R.string.news_keji));
        adapter.addFragment(new NewsListFragment().category(TIYU), getString(R.string.news_tiyu));
        adapter.addFragment(new NewsListFragment().category(HEALTH), getString(R.string.news_health));
        adapter.addFragment(new NewsListFragment().category(APPLE), getString(R.string.news_apple));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }
}
