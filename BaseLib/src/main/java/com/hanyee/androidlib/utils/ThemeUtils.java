package com.hanyee.androidlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hanyee on 15/12/3.
 */
public class ThemeUtils {

    private static final int[] THEME_IDS = new int[]{
            android.R.style.Theme_Holo,
            android.R.style.Theme_Holo_Light,
            android.R.style.Theme_DeviceDefault
    };

    private static final Map<Integer, Integer> NO_ACTION_BAR_THEME_MAP;
    private static final String KEY_THEME = "KEY_THEME";
    private static final String KEY_WALLPAPER_DIM = "KEY_WALLPAPER_DIM";
    private static final int MAX_WALLPAPER_DIM = 100;

    static {
        NO_ACTION_BAR_THEME_MAP = new HashMap<Integer, Integer>();
        NO_ACTION_BAR_THEME_MAP.put(android.R.style.Theme_Holo,
                android.R.style.Theme_Holo_NoActionBar);
        NO_ACTION_BAR_THEME_MAP.put(android.R.style.Theme_Holo_Light,
                android.R.style.Theme_Holo_Light_NoActionBar);
        NO_ACTION_BAR_THEME_MAP.put(android.R.style.Theme_DeviceDefault,
                android.R.style.Theme_DeviceDefault_NoActionBar);
    }


    public static int getThemeId(Context context) {

        int index = Integer.valueOf(SharedPrefUtils.getString(context, KEY_THEME));

        return THEME_IDS[index];
    }

    public static int getNoActionBarThemeId(int themeId) {
        return NO_ACTION_BAR_THEME_MAP.get(themeId);
    }

    public static boolean isWallpaperTheme(int themeId) {
        return themeId == android.R.style.Theme_DeviceDefault
                || themeId == android.R.style.Theme_DeviceDefault_NoActionBar;
    }

    public static boolean isWallpaperTheme(Context context) {
        return isWallpaperTheme(getThemeId(context));
    }

    public static void applyTheme(Activity activity) {
        int themeId = getThemeId(activity);
        activity.setTheme(themeId);
        if (isWallpaperTheme(themeId)) {
            applyWallpaperDim(activity);
        }
    }

    public static void applyNoActionBarTheme(Activity activity) {
        int themeId = getNoActionBarThemeId(getThemeId(activity));
        activity.setTheme(themeId);
        if (isWallpaperTheme(themeId)) {
            applyWallpaperDim(activity);
        }
    }

    public static void applyWallpaperDim(Activity activity) {
        Drawable background = new ColorDrawable(Color.BLACK);
        int dim = SharedPrefUtils.getInt(activity, KEY_WALLPAPER_DIM);
        int alpha = Math.round((float) dim / MAX_WALLPAPER_DIM * 255);
        background.setAlpha(alpha);
        activity.getWindow().setBackgroundDrawable(background);
    }

    public static Context wrapContextWithTheme(Context context, int themeRes) {
        return new ContextThemeWrapper(context, themeRes);
    }

    public static Context wrapContextWithTheme(Context context) {
        return wrapContextWithTheme(context, getThemeId(context));
    }

    public static int getResId(Context themedContext, int attrRes) {
        // NOTE: Theme.resolveAttribute() and TypedValue.resourceId doesn't seem to work, with
        // resolveRefs set to either true or false.
        TypedArray attributes = themedContext.obtainStyledAttributes(new int[]{attrRes});
        int resId = attributes.getResourceId(0, -1);
        attributes.recycle();
        return resId;
    }

    public static boolean getBoolean(Context themedContext, int attrRes, boolean defaultValue) {
        TypedArray attributes = themedContext.obtainStyledAttributes(new int[]{attrRes});
        boolean result = attributes.getBoolean(0, defaultValue);
        attributes.recycle();
        return result;
    }

    public static int getColor(Context themedContext, int attrRes, int defaultValue) {
        TypedArray attributes = themedContext.obtainStyledAttributes(new int[]{attrRes});
        int result = attributes.getColor(0, defaultValue);
        attributes.recycle();
        return result;
    }


    private ThemeUtils() {
    }

}
