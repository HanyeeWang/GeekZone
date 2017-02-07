package com.hanyee.geekzone.util;


import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by Hanyee on 16/11/11.
 */

public class DateUtils {

    private static final long DAY_OF_MILLISECONDS = 1000 * 60 * 60 * 24;

    public static String getBeforeDateString(int offset) {
        long beforeDate = new Date().getTime() - offset * DAY_OF_MILLISECONDS;
        return DateFormat.format("yyyyMMdd", beforeDate).toString();
    }
}
