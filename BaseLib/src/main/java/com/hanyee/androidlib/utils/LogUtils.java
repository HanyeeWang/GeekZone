package com.hanyee.androidlib.utils;

import android.text.TextUtils;
import android.util.Log;

import com.hanyee.androidlib.BuildConfig;

import java.util.Locale;

/**
 * 日志工具类 会自动在Log中增加类名方法名
 *
 * @author hanyee
 */
public class LogUtils {

    private static String TAG = BuildConfig.TAG;
    private static boolean DEBUG = BuildConfig.LOG_DEBUG;
    private static String DEFAULT_MSG = "No msg for this report";
    private static int MAX_ENABLED_LOG_LEVEL = DEBUG ? Log.VERBOSE : Log.INFO;

    public static boolean isLoggable(int level) {
        return MAX_ENABLED_LOG_LEVEL <= level;
    }

    public static void i(String msg) {
        if (isLoggable(Log.INFO)) {
            log('i', TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isLoggable(Log.DEBUG)) {
            log('d', TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isLoggable(Log.ERROR)) {
            log('e', TAG, msg);
        }
    }

    public static void v(String msg) {
        if (isLoggable(Log.VERBOSE)) {
            log('v', TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isLoggable(Log.WARN)) {
            log('w', TAG, msg);
        }
    }

    public static void wtf(String msg) {
        if (isLoggable(Log.ASSERT)) {
            log('a', TAG, msg);
        }
    }

    private static void log(char flag, String tag, String msg) {
        String logMsg = null;
        final int maxLength = 3500;
        for (int start = 0; start < msg.length(); start += maxLength) {
            if (start + maxLength < msg.length()) {
                logMsg = checkMsg(msg.substring(start, start + maxLength));
            } else {
                logMsg = checkMsg(msg.substring(start, msg.length()));
            }
            logMsg = buildMessage(logMsg);
            switch (flag) {
                case 'v':
                    Log.v(tag, logMsg);
                    break;
                case 'i':
                    Log.i(tag, logMsg);
                    break;
                case 'd':
                    Log.d(tag, logMsg);
                    break;
                case 'e':
                    Log.e(tag, logMsg);
                    break;
                case 'w':
                    Log.w(tag, logMsg);
                    break;
                default:
                    Log.wtf(tag, logMsg);
                    break;
            }
        }
    }

    public static String checkMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = DEFAULT_MSG;
        }
        return msg;
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        String className;
        for (int i = 3; i < trace.length; i++) {
            className = trace[i].getClassName();
            if (!className.equals(LogUtils.class.getName())) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }

    private LogUtils() {
    }
}
