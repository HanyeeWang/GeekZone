package com.hanyee.androidlib.network.volley;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.hanyee.androidlib.BuildConfig;
import com.hanyee.androidlib.network.ssl.BaseSSLSocketFactory;
import com.hanyee.androidlib.utils.LogUtils;

import java.io.File;

public class BaseRequestQueue {

    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 5;
    private static final int DEFAULT_IMAGE_THREAD_POOL_SIZE = 8;
    private static final String DEFAULT_CACHE_DIR = "volley";

    /**
     * Creates a normal network request instance of the worker pool and calls
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newNormalRequestQueue(Context context, String hostName) {
        return newRequestQueue(context,
                false,
                new BaseStack(null, BaseSSLSocketFactory.getInstance("TLS"), hostName),
                DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a normal network request instance of the worker pool and calls
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newNormalRequestQueue(Context context) {
        return newRequestQueue(context,
                false,
                new BaseStack(null, BaseSSLSocketFactory.getInstance("TLS")),
                DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a normal network request instance of the worker pool and calls
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newNormalRequestQueueWithoutSSL(Context context) {
        return newRequestQueue(context,
                false,
                new BaseStack(),
                DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a images network request instance of the worker pool and calls
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newSpecialRequestQueue(Context context) {
        return newRequestQueue(context,
                true,
                null,
                DEFAULT_IMAGE_THREAD_POOL_SIZE);
    }

    /**
     * Creates a normal network request instance of the worker pool and calls
     * which is based on OkHttpClient
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newOkHttpRequestQueueWithoutSSL(Context context) {
        return newRequestQueue(context,
                false,
                new OkHttpStack(),
                DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a normal network request instance of the worker pool and calls
     * which is based on OkHttpClient
     * {@link com.android.volley.RequestQueue#start()} on it.
     */
    public static RequestQueue newOkHttpRequestQueue(Context context) {
        return newRequestQueue(context,
                false,
                new OkHttpStack(BaseSSLSocketFactory.getInstance("TLS")),
                DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /**
     * Creates a default instance of the worker pool and calls {@link com.android.volley.RequestQueue#start()} on it.
     *
     * @param context               A {@link Context} to use for creating the cache dir.
     * @param isImageRequest        An {@link Boolean} to mark for this request type.
     * @param stack                 An {@link com.android.volley.toolbox.HttpStack} to use for the network, or null for default.
     * @param defaultThreadPoolSize An {@link Integer} to use for the worker thread pool default size.
     * @return A started {@link com.android.volley.RequestQueue} instance.
     */
    private static RequestQueue newRequestQueue(Context context, boolean isImageRequest,
                                                HttpStack stack, int defaultThreadPoolSize) {
        String userAgent = BuildConfig.TAG + "/" + BuildConfig.VERSION_CODE;

        if (isImageRequest || stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BaseNetwork(stack);

        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, defaultThreadPoolSize);
        try {
            queue.start();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            LogUtils.e("Caught OOM in BaseRequestQueue newRequestQueue e:" + e.getMessage());
            queue.getCache().clear();
            queue.start();
        }

        return queue;
    }
}
