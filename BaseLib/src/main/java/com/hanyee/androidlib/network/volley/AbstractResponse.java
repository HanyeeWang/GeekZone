package com.hanyee.androidlib.network.volley;

import com.android.volley.VolleyError;

import java.util.Map;

public abstract class AbstractResponse {

    public abstract void onSuccess(Response response);

    public abstract void onFailure(Response response);

    public abstract void onError(Response response);

    public static class Response {
        public final int statusCode;
        public final Map<String, String> headers;
        public final String body;
        public final VolleyError error;

        public Response(int statusCode, Map<String, String> headers, String body) {
            this.statusCode = statusCode;
            this.headers = headers;
            this.body = body;
            this.error = null;
        }

        public Response(int statusCode, Map<String, String> headers, VolleyError error) {
            this.statusCode = statusCode;
            this.headers = headers;
            this.body = null;
            this.error = error;
        }

        public Response(VolleyError error) {
            this.statusCode = 0;
            this.headers = null;
            this.body = null;
            this.error = error;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Response{StatusCode=" + statusCode + "\t");
            sb.append("Headers=" + headers + "\t");
            sb.append("Body=" + body + "\t");
            sb.append("Error=" + error + "}");
            return sb.toString();
        }
    }

}
