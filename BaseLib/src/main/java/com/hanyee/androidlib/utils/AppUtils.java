package com.hanyee.androidlib.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.widget.LinearLayout;

import com.hanyee.androidlib.BuildConfig;
import com.hanyee.androidlib.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanyee on 15/12/3.
 */
public class AppUtils {

    public static void crossfadeViews(final View fromView, View toView) {

        int duration = fromView.getResources()
                .getInteger(android.R.integer.config_shortAnimTime);

        fromView.animate()
                .setDuration(duration)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCanceled = false;

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mCanceled = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (!mCanceled) {
                            fromView.setVisibility(View.INVISIBLE);
                        }
                    }
                })
                .start();

        toView.setAlpha(0);
        toView.setVisibility(View.VISIBLE);
        toView.animate()
                .setDuration(duration)
                .setListener(null)
                .alpha(1)
                .start();
    }

    public static Drawable getActionBarBackground(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarStyle, outValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(outValue.resourceId,
                new int[]{android.R.attr.background});
        Drawable background = typedArray.getDrawable(0);
        typedArray.recycle();
        return background;
    }

    public static Drawable getSplitActionBarBackground(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarStyle, outValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(outValue.resourceId,
                new int[]{android.R.attr.backgroundSplit});
        Drawable background = typedArray.getDrawable(0);
        typedArray.recycle();
        return background;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // Should not happen.
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo info = getPackageInfo(context);
            if (BuildConfig.DEBUG) {
                return info.versionName;
            } else {
                return getVersion(info.versionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.unkown_version);
        }
    }

    private static String getVersion(String version) {
        return getSelectedString(version, "\\d+.\\d+.\\d+");
    }

    /**
     * 获正则表达式匹配的指定字符串
     *
     * @return 匹配的字符串
     */
    public static String getSelectedString(String region, String regex) {
        if (TextUtils.isEmpty(region) || TextUtils.isEmpty(regex)) {
            return region;
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(region);
        if (m.find()) {
            return m.group();
        } else {
            return region;
        }
    }

    public static void navigateUp(Activity activity, Bundle extras) {
        Intent upIntent = NavUtils.getParentActivityIntent(activity);
        if (upIntent == null) {
            com.hanyee.androidlib.utils.LogUtils.w("No parent found for activity, will just call finish(): "
                    + activity.getClass().getName());
        } else {
            if (extras != null) {
                upIntent.putExtras(extras);
            }
            if (NavUtils.shouldUpRecreateTask(activity, upIntent)) {
                com.hanyee.androidlib.utils.LogUtils.i("Creating new task");
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(activity)
                        // Add all of this activity's parents to the back stack.
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent.
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                com.hanyee.androidlib.utils.LogUtils.i("Using original task");
                // According to http://stackoverflow.com/a/14792752/2420519
                //NavUtils.navigateUpTo(activity, upIntent);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(upIntent);
            }
        }
        activity.finish();
    }

    public static void navigateUp(Activity activity) {
        navigateUp(activity, null);
    }


    public static void post(Runnable runnable) {
        new Handler().post(runnable);
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        new Handler().postDelayed(runnable, delayMillis);
    }

    public static void postOnPreDraw(final View view, final Runnable runnable) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });
    }

    public static void removeAllCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public static void getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.getCookie(url);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setAlarmExact(Context context, int type, long triggerAtMillis,
                                     PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(type, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(type, triggerAtMillis, pendingIntent);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(background);
        }
    }


    public static void setViewLayoutParamsHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static View makeDoneCancelLayout(int layoutId,
                                            LayoutInflater layoutInflater,
                                            int doneId,
                                            View.OnClickListener onDoneListener,
                                            int cancelId,
                                            View.OnClickListener onCancelListener) {
        @SuppressLint("InflateParams")
        LinearLayout doneDiscardLayout = (LinearLayout) layoutInflater.inflate(layoutId,
                null);
        doneDiscardLayout.setLayoutParams(new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        doneDiscardLayout.findViewById(doneId)
                .setOnClickListener(onDoneListener);
        doneDiscardLayout.findViewById(cancelId)
                .setOnClickListener(onCancelListener);
        return doneDiscardLayout;
    }

    public static View makeDoneCancelLayout(int layoutId,
                                            Activity activity,
                                            int doneId,
                                            View.OnClickListener onDoneListener,
                                            int cancelId,
                                            View.OnClickListener onCancelListener) {
        return makeDoneCancelLayout(layoutId, activity.getLayoutInflater(),
                doneId, onDoneListener,
                cancelId, onCancelListener);
    }

    public static void setupActionBar(ActionBar actionBar, int rLogoId) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
                | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setHomeButtonEnabled(true);
        // NOTE:
        // There is a bug in the handling of ActionView (SearchView) icon inside ActionBarImpl, so
        // we have to workaround it by setting the icon to logo programmatically.
        actionBar.setIcon(rLogoId);
    }

    public static void setupActionBar(Activity activity, int rLogoId) {
        setupActionBar(activity.getActionBar(), rLogoId);
    }

    public static void setupActionBarIfHas(Activity activity, int rLogoId) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar, rLogoId);
        }
    }

    public static void setupActionBarNoUp(ActionBar actionBar, int rLogoId) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setIcon(rLogoId);
    }

    public static void setupActionBarNoUp(Activity activity, int rLogoId) {
        setupActionBarNoUp(activity.getActionBar(), rLogoId);
    }

    public static void setupActionBarDoneCancel(int layoutId,
                                                Activity activity,
                                                int doneId,
                                                View.OnClickListener onDoneListener,
                                                int cancelId,
                                                View.OnClickListener onCancelListener) {
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(makeDoneCancelLayout(layoutId, activity,
                doneId, onDoneListener,
                cancelId, onCancelListener));
    }

    public static void startActivity(Intent intent, Context context) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            com.hanyee.androidlib.utils.ToastUtils.showMessage(context, "没有找到Activity");
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            com.hanyee.androidlib.utils.ToastUtils.showMessage(activity, "没有找到Activity");
        }
    }


    public static void swapViewGroupChildren(ViewGroup viewGroup, View firstView, View secondView) {
        int firstIndex = viewGroup.indexOfChild(firstView);
        int secondIndex = viewGroup.indexOfChild(secondView);
        if (firstIndex < secondIndex) {
            viewGroup.removeViewAt(secondIndex);
            viewGroup.removeViewAt(firstIndex);
            viewGroup.addView(secondView, firstIndex);
            viewGroup.addView(firstView, secondIndex);
        } else {
            viewGroup.removeViewAt(firstIndex);
            viewGroup.removeViewAt(secondIndex);
            viewGroup.addView(firstView, secondIndex);
            viewGroup.addView(secondView, firstIndex);
        }
    }

    private AppUtils() {
    }
}
