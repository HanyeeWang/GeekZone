package com.hanyee.androidlib.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseRequest extends Request<String> {

    private static final int RETRYPOLICY_TIMEOUT_MILLS          = 2 * 60 * 1000;
    private static final int RETRYPOLICY_MAX_RETRIES            = 0;
    private static final float RETRYPOLICY_BACKOFF_MULT         = 0.2f;

    private static final String DEFAULT_PARAMS_ENCODING         = "UTF-8";
    private static final String DEFAULT_BODY_CONTENT_TYPE       = "application/json";

    private static final String RESPONSE_STATUS_CODE            = "Status-Code";
    private static final String RESPONSE_HEADERS                = "Response-Header";
    private static final String RESPONSE_BODY                   = "Response-Body";

    private AbstractResponse mResponse;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private byte[] mBody;
    private Priority mPriority = null;

    public BaseRequest(int method, String url, AbstractResponse response, Map<String, String> params) {
        super(method, url, null);
        setTag(url);
        mHeaders = new HashMap<String, String>();
        mParams = params;
        mResponse = response;
        setRetryPolicy(new DefaultRetryPolicy(RETRYPOLICY_TIMEOUT_MILLS, RETRYPOLICY_MAX_RETRIES,
                RETRYPOLICY_BACKOFF_MULT));
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority == null ? Priority.HIGH : mPriority;
    }

    @Override
    protected void deliverResponse(String response) {
        if (isCanceled()) {
            return;
        }
        if (mResponse != null) {
            int statusCode = 0;
            Map<String, String> headers = null;
            String body = null;
            try {
                JSONObject responseJson = new JSONObject(response);
                if (!responseJson.isNull(RESPONSE_STATUS_CODE)) {
                    statusCode = responseJson.getInt(RESPONSE_STATUS_CODE);
                }
                if (!responseJson.isNull(RESPONSE_HEADERS)) {
                    headers = toMap(responseJson);
                }
                if (!responseJson.isNull(RESPONSE_BODY)) {
                    body = responseJson.getString(RESPONSE_BODY);
                }
                if (statusCode < HttpStatus.SC_BAD_REQUEST) {
                    mResponse.onSuccess(new AbstractResponse.Response(statusCode, headers, body));
                } else {
                    mResponse.onFailure(new AbstractResponse.Response(statusCode, headers, body));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mResponse.onError(new AbstractResponse.Response(statusCode, headers, new VolleyError(e)));
            }
        }
    }

    @Override
    public void deliverError(VolleyError e) {
        if (isCanceled()) {
            return;
        }
        e.printStackTrace();
        if (mResponse != null) {
            mResponse.onError(new AbstractResponse.Response(e));
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (isCanceled() || response == null) {
            return null;
        }
        String bodyString;
        try {
            bodyString = new String(response.data, getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            bodyString = new String(response.data);
        }
        try {
            JSONObject responseJson = new JSONObject();
            responseJson.put(RESPONSE_STATUS_CODE, response.statusCode);
            JSONObject responseHeadersJson = toJsonObject(response.headers);
            responseJson.put(RESPONSE_HEADERS, responseHeadersJson);
            responseJson.put(RESPONSE_BODY, bodyString);
            return Response.success(responseJson.toString(), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    public String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    @Override
    public String getBodyContentType() {
        return DEFAULT_BODY_CONTENT_TYPE;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaders != null && mHeaders.size() != 0) {
            return mHeaders;
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null && mParams.size() != 0) {
            return mParams;
        }
        return super.getParams();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mBody != null && mBody.length != 0) {
            return mBody;
        }
        return super.getBody();
    }

    public BaseRequest setHeaders(Map<String, String> headers) {
        mHeaders.putAll(headers);
        return this;
    }

    public BaseRequest setParams(Map<String, String> params) {
        mParams.putAll(params);
        return this;
    }

    public BaseRequest setBody(byte[] body) {
        mBody = body;
        return this;
    }

    private Map<String, String> toMap(JSONObject responseJson) throws JSONException {
        Map<String, String> headers = new HashMap<String, String>();
        JSONObject headersJObj = responseJson.getJSONObject(RESPONSE_HEADERS);
        @SuppressWarnings("unchecked")
        Iterator<String> iterator = headersJObj.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = headersJObj.getString(key);
            headers.put(key, value);
        }
        return headers;
    }

    private JSONObject toJsonObject(Map<String, String> headers) throws JSONException {
        JSONObject responseHeadersJson = new JSONObject();
        for (String key : headers.keySet()) {
            responseHeadersJson.put(key, headers.get(key));
        }
        return responseHeadersJson;
    }

    public static String setUrlByParams(String url, Map<String, String> params) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(url).append(convertParamsToQueryString(params));
        return resultBuilder.toString();
    }

    public static String convertParamsToQueryString(Map<String, String> params) {
        StringBuilder resultBuilder = new StringBuilder();
        String value = null;
        if (params != null && params.size() != 0) {
            for (String key : params.keySet()) {
                try {
                    value = URLEncoder.encode(params.get(key), DEFAULT_PARAMS_ENCODING);
                } catch (UnsupportedEncodingException e) {
                    continue;
                }
                if (resultBuilder.toString().contains("?")) {
                    resultBuilder.append("&");
                } else {
                    resultBuilder.append("?");
                }
                resultBuilder.append(key);
                resultBuilder.append("=");
                resultBuilder.append(value);
            }
        }
        return resultBuilder.toString();
    }

    public static String convertParamsToJSONString(Map<String, String> params) {
        JSONObject json = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
