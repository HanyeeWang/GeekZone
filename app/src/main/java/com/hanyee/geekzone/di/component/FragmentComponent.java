package com.hanyee.geekzone.di.component;

import android.app.Activity;

import com.hanyee.geekzone.di.annotation.FragmentScope;
import com.hanyee.geekzone.di.module.FragmentModule;
import com.hanyee.geekzone.ui.fragment.gank.GankListFragment;
import com.hanyee.geekzone.ui.fragment.main.AboutFragment;
import com.hanyee.geekzone.ui.fragment.main.LikeFragment;
import com.hanyee.geekzone.ui.fragment.main.SettingFragment;
import com.hanyee.geekzone.ui.fragment.tianxin.NewsFragment;
import com.hanyee.geekzone.ui.fragment.tianxin.NewsListFragment;
import com.hanyee.geekzone.ui.fragment.tianxin.WeChatFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.ColumnsFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.DailyNewsFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.HotNewsFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.SpecialsFragment;
import com.hanyee.geekzone.ui.fragment.zhihu.ThemesFragment;

import dagger.Component;

/**
 * Created by Hanyee on 16/10/10.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(WeChatFragment fragment);

    void inject(AboutFragment fragment);

    void inject(SettingFragment fragment);

    void inject(NewsFragment fragment);

    void inject(LikeFragment fragment);

    void inject(DailyNewsFragment fragment);

    void inject(ThemesFragment fragment);

    void inject(ColumnsFragment fragment);

    void inject(HotNewsFragment fragment);

    void inject(SpecialsFragment fragment);

    void inject(GankListFragment fragment);

    void inject(NewsListFragment fragment);
}
