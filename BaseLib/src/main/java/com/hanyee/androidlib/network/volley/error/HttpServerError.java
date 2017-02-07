package com.hanyee.androidlib.network.volley.error;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

@SuppressWarnings("serial")
public class HttpServerError extends NetworkError {

    public HttpServerError() {
        super();
    }

    public HttpServerError(Throwable cause) {
        super(cause);
    }

    public HttpServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
