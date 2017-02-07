package com.hanyee.androidlib.network.volley.error;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

@SuppressWarnings("serial")
public class SSLNetworkError extends NetworkError {

    public SSLNetworkError() {
        super();
    }

    public SSLNetworkError(Throwable cause) {
        super(cause);
    }

    public SSLNetworkError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
