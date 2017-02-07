package com.hanyee.androidlib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().create();

    public static Gson get() {
        return GSON;
    }

    private GsonUtils() {}
}
