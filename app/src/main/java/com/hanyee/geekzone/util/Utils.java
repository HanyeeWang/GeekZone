package com.hanyee.geekzone.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author hanyee
 */
public class Utils {

    private static final int MINUTE = 60;
    private static final int HOUR   = MINUTE * 60;
    private static final int DAY    = HOUR * 24;
    private static final int MONTH  = DAY * 30;
    private static final int YEAR   = MONTH * 12;

    private static final String EMAIL_ADDRESS = "hanyee.hw@gmail.com";
    private static final DateFormat FORMAT    = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public static String convertPublishTime(String time) {
        try {
            long s = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(time).getTime());

            long count = s / YEAR;
            if (count != 0) {
                return count + "年前";
            }

            count = s / MONTH;
            if (count != 0) {
                return count + "月前";
            }

            count = s / DAY;
            if (count != 0) {
                return count + "天前";
            }

            return "今天";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知时间";
    }

    public static boolean withinDays(String time, int days) {
        try {
            long s = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(time).getTime());
            long count = s / DAY;
            if (0 < count && count <= days) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int compareTime(String a, String b) {
        try {
            long timeA = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(a).getTime());
            long timeB = TimeUnit.MILLISECONDS.toSeconds(
                    new Date().getTime() - FORMAT.parse(b).getTime());

            return timeA >= timeB ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String removeHtmlCode(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }

        StringBuilder dest = new StringBuilder();
        for (int i = 0, flag = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            if (c == '<') {
                flag = 1;
            }

            if (c == '>') {
                flag = 0;
                continue;
            }

            if (flag == 1) {
                continue;
            }
            dest.append(c);
        }

        return dest.toString();
    }

    public static void sendEmail(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                "mailto:" + EMAIL_ADDRESS));
        context.startActivity(Intent.createChooser(intent, title));
    }
}
