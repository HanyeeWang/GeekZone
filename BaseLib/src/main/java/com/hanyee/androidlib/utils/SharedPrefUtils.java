package com.hanyee.androidlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hanyee.androidlib.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefUtils {

    private static final String PERFERENCENAME = BuildConfig.TAG;

    private static SharedPreferences getPreferences(Context context, String xmlname) {
        return context.getSharedPreferences(TextUtils.isEmpty(xmlname) ? PERFERENCENAME : xmlname,
                Context.MODE_PRIVATE);
    }

    /**********************************************/

    public static boolean putString(Context context, String key, String value, String xmlname) {
        try {
            value = DesUtils.getInstance().encrypt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPreferences(context, xmlname).edit().putString(key, value).commit();
    }

    public static boolean putString(Context context, String key, String value) {
        return putString(context, key, value, null);
    }

    public static String getString(Context context, String key, String xmlname) {
        return getString(context, key, xmlname, "");
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String xmlname, String defaultValue) {
        String result = getPreferences(context, xmlname).getString(key, defaultValue);
        try {
            if (result != defaultValue) {
                result = DesUtils.getInstance().decrypt(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isContainValue(Context context, String value, String fileName) {
        Map<String, ?> map = getAll(context, fileName);
        if (map != null && map.size() > 0) {
            try {
                value = DesUtils.getInstance().encrypt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map.containsValue(value);
        } else {
            return false;
        }
    }

    public static Map<String, String> getAllString(Context context, String xmlname) {
        Map<String, String> result = null;
        Map<String, ?> all = getAll(context, xmlname);
        if (all == null || all.size() == 0)
            return result;
        for (String key : all.keySet()) {
            if (result == null)
                result = new HashMap<String, String>();
            String value = String.valueOf(all.get(key));
            try {
                value = DesUtils.getInstance().decrypt(value);
            } catch (Exception e) {
                value = "";
            }
            result.put(key, value);
        }
        return result;
    }

    public static Map<String, String> getAllString(Context context) {
        return getAllString(context, null);
    }

    public static boolean putBoolean(Context context, String key, boolean value, String xmlname) {
        return getPreferences(context, xmlname).edit().putBoolean(key, value).commit();
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        return putBoolean(context, key, value, null);
    }

    public static Boolean getBoolean(Context context, String key, String xmlname) {
        return getBoolean(context, key, xmlname, false);
    }

    public static Boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, null);
    }

    public static Boolean getBoolean(Context context, String key, String xmlname, boolean defaultValue) {
        return getPreferences(context, xmlname).getBoolean(key, defaultValue);
    }

    public static Boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getBoolean(context, key, null, defaultValue);
    }

    public static boolean putFloat(Context context, String key, Float value, String xmlname) {
        return getPreferences(context, xmlname).edit().putFloat(key, value).commit();
    }

    public static boolean putFloat(Context context, String key, Float value) {
        return putFloat(context, key, value, null);
    }

    public static Float getFloat(Context context, String key, String xmlname, float defaultValue) {
        return getPreferences(context, xmlname).getFloat(key, defaultValue);
    }

    public static Float getFloat(Context context, String key, float defaultValue) {
        return getFloat(context, key, null, defaultValue);
    }

    public static Float getFloat(Context context, String key, String xmlname) {
        return getFloat(context, key, xmlname, 0.0f);
    }

    public static Float getFloat(Context context, String key) {
        return getFloat(context, key, null, 0.0f);
    }

    public static boolean putInt(Context context, String key, int value, String xmlname) {
        return getPreferences(context, xmlname).edit().putInt(key, value).commit();
    }

    public static boolean putInt(Context context, String key, int value) {
        return putInt(context, key, value, null);
    }

    public static int getInt(Context context, String key, String xmlname, int defaultValue) {
        return getPreferences(context, xmlname).getInt(key, defaultValue);
    }

    public static int getInt(Context context, String key, String xmlname) {
        return getInt(context, key, xmlname, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getInt(context, key, null, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, null, -1);
    }

    public static boolean putLong(Context context, String key, Long value, String xmlname) {
        return getPreferences(context, xmlname).edit().putLong(key, value).commit();
    }

    public static boolean putLong(Context context, String key, Long value) {
        return putLong(context, key, value, null);
    }

    public static long getLong(Context context, String key, String xmlname, long defaultValue) {
        return getPreferences(context, xmlname).getLong(key, defaultValue);
    }

    public static long getLong(Context context, String key, String xmlname) {
        return getLong(context, key, xmlname, 0l);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getLong(context, key, null, defaultValue);
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, null, 0l);
    }

    public static void clear(Context context, String xmlname) {
        getPreferences(context, xmlname).edit().clear().commit();
    }

    public static void clear(Context context) {
        clear(context, null);
    }

    public static boolean remove(Context context, String key, String xmlname) {
        return getPreferences(context, xmlname).edit().remove(key).commit();
    }

    public static boolean remove(Context context, String key) {
        return remove(context, key, null);
    }

    public static boolean contains(Context context, String key, String xmlname) {
        return getPreferences(context, xmlname).contains(key);
    }

    public static boolean contains(Context context, String key) {
        return contains(context, key, null);
    }

    public static Map<String, ?> getAll(Context context, String xmlname) {
        return getPreferences(context, xmlname).getAll();
    }

    public static Map<String, ?> getAll(Context context) {
        return getAll(context, null);
    }

    private SharedPrefUtils() {
    }
}
