package com.hanyee.androidlib.utils;

import android.content.Context;
import android.text.TextUtils;

import com.hanyee.androidlib.BuildConfig;
import com.umeng.analytics.MobclickAgent;

public class UMengAnalyticsUtils {

    public static void init() {
        // 如果没有在AndroidManifest 设置AppKey
        // AnalyticsConfig.setAppkey(UMENG_APPKEY);
        MobclickAgent.setDebugMode(BuildConfig.UMENG_DEBUG);
        MobclickAgent.setSessionContinueMillis(1000l);
        MobclickAgent.setCatchUncaughtExceptions(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
    }

    public static void onPageStart(String page) {
        if (!TextUtils.isEmpty(page)) {
            MobclickAgent.onPageStart(page);
            LogUtils.i("[" + page + "] page start.");
        }
    }

    public static void onPageStart(String page, Context ctx) {
        onPageStart(page);
        MobclickAgent.onResume(ctx);
    }

    public static void onPageEnd(String page) {
        if (!TextUtils.isEmpty(page)) {
            MobclickAgent.onPageEnd(page);
            LogUtils.i("[" + page + "] page end.");
        }
    }

    public static void onPageEnd(String page, Context ctx) {
        onPageEnd(page);
        MobclickAgent.onPause(ctx);
    }

    public static void onEvent(Context ctx, String eventId) {
        MobclickAgent.onEvent(ctx, eventId);
    }

    private UMengAnalyticsUtils() {
    }
}
