package com.hanyee.androidlib.cache.image.glide;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Hanyee on 16/11/21.
 */

public class GlideConfiguration implements GlideModule {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        // Get default memory cache size
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        // Set custom memory cache size
        int customMemoryCacheSize = (int) (1.5 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.5 * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        // Set disk cache size
        int diskCacheSize = 1024 * 1024 * 100;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));

        // Prefer higher quality images unless we're on a low RAM device
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        builder.setDecodeFormat(activityManager.isLowRamDevice() ?
                DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, final Glide glide) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                glide.clearDiskCache();
            }
        }).start();
    }
}
