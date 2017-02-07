package com.hanyee.geekzone.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hanyee.geekzone.di.annotation.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hanyee on 16/10/10.
 */
@Module
public class FragmentModule {

    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @FragmentScope
    @Provides
    public Fragment provideFragment() {
        return mFragment;
    }
}
