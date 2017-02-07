package com.hanyee.androidlib.network.volley.error;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

@SuppressWarnings("serial")
public class ServerSafeguardError extends NetworkError {

    public ServerSafeguardError() {
        super();
    }

    public ServerSafeguardError(Throwable cause) {
        super(cause);
    }

    public ServerSafeguardError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
