package com.hanyee.geekzone.di.component;

import android.app.Activity;

import com.hanyee.geekzone.di.annotation.ActivityScope;
import com.hanyee.geekzone.di.module.ActivityModule;
import com.hanyee.geekzone.ui.activity.main.MainActivity;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;
import com.hanyee.geekzone.ui.activity.zhihu.ColumnListActivity;
import com.hanyee.geekzone.ui.activity.zhihu.SpecialListActivity;
import com.hanyee.geekzone.ui.activity.zhihu.ThemeListActivity;

import dagger.Component;

/**
 * Created by Hanyee on 16/10/10.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity activity);

    void inject(ThemeListActivity activity);

    void inject(SpecialListActivity activity);

    void inject(ColumnListActivity activity);

    void inject(ArticleDetailActivity activity);
}
