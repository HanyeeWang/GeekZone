package com.hanyee.androidlib.network.volley.error;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

@SuppressWarnings("serial")
public class ClientVersionLowError extends NetworkError {

    public ClientVersionLowError() {
        super();
    }

    public ClientVersionLowError(Throwable cause) {
        super(cause);
    }

    public ClientVersionLowError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
