package com.hanyee.androidlib.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class FileUtils {

    private static final String MOBILE_DIRECTORY_NAME = "Hanyee";

    private static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

    public static String makeDateTimeFileName(String prefix, String suffix) {
        return String.format("%s_%s.%s", prefix, DATE_TIME_FORMAT.format(new Date()), suffix);
    }

    public static File makePictureDirectory() {
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                MOBILE_DIRECTORY_NAME);
    }

    public static void ensureParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory()) {
            parent.mkdirs();
        }
    }


    private FileUtils() {
    }
}
