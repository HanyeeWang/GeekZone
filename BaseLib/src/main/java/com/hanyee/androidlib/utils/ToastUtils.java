package com.hanyee.androidlib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * 自定义Toast工具类，处理了多个Toast显示时间过长的问题
 * 当调用显示Toast时，如果没有Toast直接show；如果有需要先Cancel之前当Toast再Show当前的Toast
 *
 * @author Hanyee
 */
public class ToastUtils {

    private static final String HAPPYSTRING = "I'm Easter Egg, (@_@)";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Object mLockObject = new Object();
    private static Toast mToast = null;

    public static void showMessage(Context context, final int msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, final String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, final int msg, final int len) {
        showMessage(context, context.getString(msg), len);
    }

    public static void showMessage(final Context context, final String msg, final int len) {
        new Thread(new Runnable() {
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLockObject) {
                            showToast(context, msg, len);
                        }
                    }
                });
            }
        }).start();
    }

    private static void showToast(Context context, final String msg, final int len) {
        if (mToast != null) {
            mToast.cancel();
            // Easter egg
            if (String.valueOf(SystemClock.elapsedRealtime()).endsWith("513")) {
                mToast.setText(HAPPYSTRING);
            } else {
                mToast.setText(msg);
            }
            mToast.setDuration(len);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mToast.show();
                }
            }, Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(context, msg, len);
            mToast.show();
        }
    }

    public static void cancelCurrentToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private ToastUtils() {
    }
}
