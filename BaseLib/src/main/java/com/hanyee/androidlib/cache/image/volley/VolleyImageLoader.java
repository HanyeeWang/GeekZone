package com.hanyee.androidlib.cache.image.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.hanyee.androidlib.network.volley.BaseRequestQueue;

public class VolleyImageLoader {

    public static final String IMAGE_CACHE_DIR = "temp";

    private RequestQueue mImageQueue;
    private ImageLoader mImageLoader;
    private ImageLoader.ImageCache mImageCache;
    private static VolleyImageLoader mInstance;

    private VolleyImageLoader(Context context) {
        mImageQueue = BaseRequestQueue.newSpecialRequestQueue(context);
        ImageBitmapCache.ImageCacheParams cacheParams = new ImageBitmapCache.ImageCacheParams(IMAGE_CACHE_DIR);
        cacheParams.memCacheSize = 1024 * 1024 * com.hanyee.androidlib.cache.image.ImageUtils.getMemoryClass(context) / 3;
        mImageCache = ImageBitmapCache.getInstanceCache(context, cacheParams);
        mImageLoader = new ImageLoader(mImageQueue, mImageCache);
    }
    public static synchronized VolleyImageLoader getInstance(Context context){
        if (mInstance==null){
            mInstance = new VolleyImageLoader(context);
        }
        return  mInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public ImageLoader.ImageCache getImageCache() {
        return mImageCache;
    }

}
