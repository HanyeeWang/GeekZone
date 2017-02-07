package com.hanyee.geekzone.di.module;

import android.app.Activity;

import com.hanyee.geekzone.di.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hanyee on 16/10/10.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }
}
