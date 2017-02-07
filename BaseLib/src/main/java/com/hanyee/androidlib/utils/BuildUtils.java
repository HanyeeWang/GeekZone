package com.hanyee.androidlib.utils;

import android.content.Context;

import com.hanyee.androidlib.BuildConfig;

/**
 * Created by Hanyee on 15/12/3.
 */
public class BuildUtils {

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static void throwOrPrint(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwRuntimeException(throwable);
        } else {
            throwable.printStackTrace();
        }
    }

    private static void throwRuntimeException(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        } else {
            throw new RuntimeException(throwable);
        }
    }

    // NOTE: If in productive build, this method prints stack trace before reporting.
    public static void throwOrPrintAndReport(Throwable throwable, Context context) {
        if (BuildConfig.DEBUG) {
            throwRuntimeException(throwable);
        } else {
            throwable.printStackTrace();
//            UmengUtils.reportError(throwable, context);
        }
    }


    private BuildUtils() {}
}
