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

package com.hanyee.geekzone.ui.activity.main;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorActivity;
import com.hanyee.geekzone.presenter.MainPresenter;
import com.hanyee.geekzone.presenter.contract.MainContract;
import com.hanyee.geekzone.ui.fragment.gank.GankFragment;
import com.hanyee.geekzone.ui.fragment.main.AboutFragment;
import com.hanyee.geekzone.ui.fragment.main.LikeFragment;
import com.hanyee.geekzone.ui.fragment.main.SettingFragment;
import com.hanyee.geekzone.ui.fragment.tianxin.NewsFragment;
import com.hanyee.geekzone.ui.fragment.tianxin.WeChatFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.ZhiHuFragment;
import com.hanyee.geekzone.util.Constants;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Hanyee on 2016/10/14.
 */
public class MainActivity extends SuperiorActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.main_container)
    FrameLayout mContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ViewGroup mViewRoot;
    private MenuItem mLastMenuItem;

    private int mCurrentFragment = Constants.NavigationItem.TYPE_ZHIHU;

    private SupportFragment mZhihuFragment;
    private SupportFragment mGankFragment;
    private SupportFragment mWechatFragment;
    private SupportFragment mLikeFragment;
    private SupportFragment mAboutFragment;
    private SupportFragment mSettingFragment;
    private SupportFragment mNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPresenter.setNightModeState(false);
        } else {
            mCurrentFragment = mPresenter.getCurrentItem();
            replaceLoadRootFragment(R.id.main_container, getTargetFragment(mCurrentFragment), true);
            mNavView.getMenu().findItem(R.id.zhihu).setChecked(false);
        }
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        mViewRoot = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        return mViewRoot;
    }

    @Override
    protected void initData() {
        mZhihuFragment = new ZhiHuFragment();
        mWechatFragment = new WeChatFragment();
        mGankFragment = new GankFragment();
        mNewsFragment = new NewsFragment();
        mLikeFragment = new LikeFragment();
        mSettingFragment = new SettingFragment();
        mAboutFragment = new AboutFragment();

        replaceLoadRootFragment(R.id.main_container, mZhihuFragment, true);
        setupDrawerContent(mNavView);

        finishLoading();
    }

    @Override
    public void syncDrawerState(Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        mLastMenuItem = navigationView.getMenu().findItem(R.id.zhihu);
        disableNavigationViewScrollbars(navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.zhihu:
                                mCurrentFragment = Constants.NavigationItem.TYPE_ZHIHU;
                                break;
                            case R.id.gank:
                                mCurrentFragment = Constants.NavigationItem.TYPE_GANK;
                                break;
                            case R.id.wechat:
                                mCurrentFragment = Constants.NavigationItem.TYPE_WECHAT;
                                break;
                            case R.id.other:
                                mCurrentFragment = Constants.NavigationItem.TYPE_NEWS;
                                break;
                            case R.id.like:
                                mCurrentFragment = Constants.NavigationItem.TYPE_LIKE;
                                break;
                            case R.id.settings:
                                mCurrentFragment = Constants.NavigationItem.TYPE_SETTING;
                                break;
                            case R.id.about:
                                mCurrentFragment = Constants.NavigationItem.TYPE_ABOUT;
                                break;
                        }
                        if (mLastMenuItem != null) {
                            mLastMenuItem.setChecked(false);
                        }
                        mLastMenuItem = menuItem;
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mPresenter.setCurrentItem(mCurrentFragment);
                        replaceLoadRootFragment(R.id.main_container, getTargetFragment(mCurrentFragment), true);
                        return true;
                    }
                });
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.NavigationItem.TYPE_ZHIHU:
                return mZhihuFragment;
            case Constants.NavigationItem.TYPE_GANK:
                return mGankFragment;
            case Constants.NavigationItem.TYPE_WECHAT:
                return mWechatFragment;
            case Constants.NavigationItem.TYPE_NEWS:
                return mNewsFragment;
            case Constants.NavigationItem.TYPE_LIKE:
                return mLikeFragment;
            case Constants.NavigationItem.TYPE_SETTING:
                return mSettingFragment;
            case Constants.NavigationItem.TYPE_ABOUT:
                return mAboutFragment;
        }
        return mZhihuFragment;
    }
}
