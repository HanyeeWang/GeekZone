package com.hanyee.androidlib.network.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.hanyee.androidlib.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class BaseAPIHelper {

    protected static final String PARAMS_SEPARATOR = "#";
    protected static Map<Integer, String> urlMap = new HashMap<Integer, String>();
    protected Context mContext;
    protected int mRetryMethodType;
    protected int mRetryApi;
    protected Object[] mRetryUrlParams;
    protected Map<String, String> mRetryParams;
    protected byte[] mRetryBody;
    protected AbstractResponse mResponse;
    protected RequestQueue mRequestQueue;

    public BaseAPIHelper(Context context, String hostName) {
        mContext = context;
        mRequestQueue = BaseRequestQueue.newNormalRequestQueue(mContext.getApplicationContext(), hostName);
    }

    public BaseAPIHelper(Context context) {
        mContext = context;
        mRequestQueue = BaseRequestQueue.newNormalRequestQueue(mContext.getApplicationContext());
    }

    /**
     * 发起请求
     * @param method
     * @param api
     * @param urlParams
     * @param params
     * @param body
     * @param reponse
     */
    protected void request(final int method,
                           final int api,
                           final Object[] urlParams,
                           final Map<String, String> params,
                           final byte[] body,
                           final AbstractResponse reponse) {
        request(method, api, urlParams, null, params, body, reponse);
    }

    /**
     * 发起请求
     * @param method
     * @param api
     * @param urlParams
     * @param headers
     * @param params
     * @param body
     * @param reponse
     */
    protected void request(final int method,
                           final int api,
                           final Object[] urlParams,
                           Map<String, String> headers,
                           final Map<String, String> params,
                           final byte[] body,
                           final AbstractResponse reponse) {
        // 生成请求URL
        final String targetUrl = buildUrl(method, api, urlParams, params);
        // 构建Headers
        headers = buildHeaders(headers);
        // 实例请求
        BaseRequest request = new BaseRequest(method, targetUrl, reponse, params);

        LogUtils.d("[ApiCode:" + api + "]===========================BaseAPIHelper.sendRequest======================================");
        LogUtils.d("[ApiCode:" + api + "]method   : " + method);
        LogUtils.d("[ApiCode:" + api + "]url      : " + targetUrl);
        LogUtils.d("[ApiCode:" + api + "]headers  : " + headers);
        LogUtils.d("[ApiCode:" + api + "]params   : " + params);
        LogUtils.d("[ApiCode:" + api + "]body     : " + body);
        LogUtils.d("[ApiCode:" + api + "]==========================================================================================");

        request(headers, body, request);
    }

    /**
     * 请求
     * @param headers
     * @param body
     * @param request
     */
    private void request(final Map<String, String> headers,
                         final byte[] body,
                         final BaseRequest request) {
        if (headers != null && headers.size() != 0) {
            request.setHeaders(headers);
        }
        if (body != null) {
            request.setBody(body);
        }
        mRequestQueue.add(request);
    }

    /**
     * 取消
     * @param method
     * @param api
     * @param urlParams
     * @param params
     */
    public void cancel(final int method,
                       final int api,
                       final Object[] urlParams,
                       final Map<String, String> params) {
        final String targetUrl = buildUrl(method, api, urlParams, params);
        mRequestQueue.cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return String.valueOf(request.getTag()) == targetUrl;
            }
        });
    }

    /**
     * 取消所有请求
     */
    public void cancel() {
        mRequestQueue.cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return (request instanceof BaseRequest);
            }
        });
    }

    /**
     * 队列清理
     */
    public void clear() {
        cancel();
        mRequestQueue.getCache().clear();
    }

    /**
     * 刷新
     */
    public void refresh() {
        request(mRetryMethodType, mRetryApi, mRetryUrlParams, mRetryParams, mRetryBody, mResponse);
    }

    /**
     * 设置刷新数据
     * @param methodType
     * @param api
     * @param urlParams
     * @param params
     * @param body
     * @param response
     */
    protected void setRetryData(final int methodType,
                                final int api,
                                final Object[] urlParams,
                                final Map<String, String> params,
                                final byte[] body,
                                final AbstractResponse response) {
        mRetryMethodType = methodType;
        mRetryApi = api;
        mRetryUrlParams = urlParams;
        mRetryParams = params;
        mRetryBody = body;
        mResponse = response;
    }

    /**
     * 构建Headers
     * @param headers
     * @return
     */
    protected Map<String, String> buildHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        return headers;
    }

    /**
     * 构建实际请求的url
     * @param method
     * @param api
     * @param urlParams
     * @param params
     * @return
     */
    protected static String buildUrl(final int method,
                                     final int api,
                                     final Object[] urlParams,
                                     final Map<String, String> params) {
        String url = urlMap.get(api);
        if (urlParams != null && urlParams.length > 0) {
            url = formatUrlWithParams(url, urlParams);
        }
        if (method == Request.Method.GET) {
            return BaseRequest.setUrlByParams(url, params);
        } else {
            return BaseRequest.setUrlByParams(url, null);
        }
    }

    /**
     * url中替换parameters 占位符使用 @link PARAMS_SEPARATOR
     * @param url
     * @param values
     * @return
     */
    protected static String formatUrlWithParams(String url,
                                                Object... values) {
        String formateUrl = url;
        for (Object obj : values) {
            formateUrl = formateUrl.replaceFirst(PARAMS_SEPARATOR, String.valueOf(obj));
        }
        return formateUrl;
    }

}
