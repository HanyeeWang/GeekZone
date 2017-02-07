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

/**
 * Created by Hanyee on 2016/10/14.
 */
public class ZhiHuFragment extends BaseFragment {

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
        setToolBar((BaseActivity) mActivity, mToolbar, getString(R.string.zhihu));
        setupViewPager(mViewPager);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.fragment_zhihu;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new DailyNewsFragment(), getString(R.string.zhihu_news));
        adapter.addFragment(new ThemesFragment(), getString(R.string.zhihu_themes));
        adapter.addFragment(new ColumnsFragment(), getString(R.string.zhihu_columns));
        adapter.addFragment(new HotNewsFragment(), getString(R.string.zhihu_hot));
        adapter.addFragment(new SpecialsFragment(), getString(R.string.zhihu_special));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }
}
