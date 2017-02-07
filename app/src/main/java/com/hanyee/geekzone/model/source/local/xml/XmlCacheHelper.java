package com.hanyee.geekzone.model.source.local.xml;

import com.hanyee.androidlib.utils.SharedPrefUtils;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.model.source.local.LocalDataSource;
import com.hanyee.geekzone.util.Constants;

import javax.inject.Inject;

/**
 * Created by Hanyee on 16/10/17.
 */
public class XmlCacheHelper implements LocalDataSource.FileCacheSource {

    private static final boolean DEFAULT_NIGHT_MODE = false;
    private static final boolean DEFAULT_ARTICLE_IMAGE_MODE = false;
    private static final boolean DEFAULT_ARTICLE_CACHE_MODE = true;
    private static final int DEFAULT_CURRENT_FRAGMENT = Constants.NavigationItem.TYPE_ZHIHU;

    @Inject
    public XmlCacheHelper() {
    }

    @Override
    public void setNightModeState(boolean state) {
        SharedPrefUtils.putBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.NIGHT_MODE, state);
    }

    @Override
    public boolean isNightModeState() {
        return SharedPrefUtils.getBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.NIGHT_MODE, DEFAULT_NIGHT_MODE);
    }

    @Override
    public void setCurrentItem(int item) {
        SharedPrefUtils.putInt(GeekZoneApplication.getInstance(),
                Constants.SharePreference.CURRENT_ITEM, item);
    }

    @Override
    public int getCurrentItem() {
        return SharedPrefUtils.getInt(GeekZoneApplication.getInstance(),
                Constants.SharePreference.CURRENT_ITEM, DEFAULT_CURRENT_FRAGMENT);
    }

    @Override
    public void setArticleNoImageState(boolean state) {
        SharedPrefUtils.putBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.ARTICLE_IMAGE, state);
    }

    @Override
    public boolean isArticleNoImage() {
        return SharedPrefUtils.getBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.ARTICLE_IMAGE, DEFAULT_ARTICLE_IMAGE_MODE);
    }

    @Override
    public void setArticleAutoCacheState(boolean state) {
        SharedPrefUtils.putBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.ARTICLE_CACHE, state);
    }

    @Override
    public boolean isArticleAutoCache() {
        return SharedPrefUtils.getBoolean(GeekZoneApplication.getInstance(),
                Constants.SharePreference.ARTICLE_CACHE, DEFAULT_ARTICLE_CACHE_MODE);
    }
}
