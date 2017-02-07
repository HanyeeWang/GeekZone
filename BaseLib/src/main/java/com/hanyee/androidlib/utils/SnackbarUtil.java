package com.hanyee.androidlib.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanyee.androidlib.R;

public class SnackbarUtil {

    public static final int INFO    = 1;
    public static final int CONFIRM = 2;
    public static final int WARNING = 3;
    public static final int ALERT   = 4;

    /**
     * 短显示Snackbar，自定义颜色
     *
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar ShortSnackbar(View view, Object message, int messageColor, int backgroundColor) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_SHORT);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_SHORT);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 长显示Snackbar，自定义颜色
     *
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar LongSnackbar(View view, Object message, int messageColor, int backgroundColor) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_SHORT);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_SHORT);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 自定义时长显示Snackbar，自定义颜色
     *
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, Object message, int duration, int messageColor,
                                              int backgroundColor) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 短显示Snackbar，可选预设类型
     *
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar ShortSnackbar(View view, Object message, int type) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_SHORT);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_SHORT);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 长显示Snackbar，可选预设类型
     *
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar LongSnackbar(View view, Object message, int type) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_LONG);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_LONG);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 自定义时常显示Snackbar，可选预设类型
     *
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, Object message, int duration, int type) {
        Snackbar snackbar;
        if (message instanceof String) {
            snackbar = Snackbar.make(view, (String)message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        } else if (message instanceof Integer) {
            snackbar = Snackbar.make(view, (Integer) message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        } else {
            throw new IllegalArgumentException("Message resource type is illegal!");
        }
        switchType(snackbar, type);
        return snackbar;
    }

    //选择预设类型
    private static void switchType(Snackbar snackbar, int type) {
        switch (type) {
            case INFO:
                setSnackbarColor(snackbar, R.color.snackbarBlack);
                break;
            case CONFIRM:
                setSnackbarColor(snackbar, R.color.snackbarGreen);
                break;
            case WARNING:
                setSnackbarColor(snackbar, R.color.snackbarOrange);
                break;
            case ALERT:
                setSnackbarColor(snackbar, Color.YELLOW, R.color.snackbarRed);
                break;
        }
    }

    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundResource(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

}