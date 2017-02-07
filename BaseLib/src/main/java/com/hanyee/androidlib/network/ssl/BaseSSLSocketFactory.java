package com.hanyee.androidlib.network.ssl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class BaseSSLSocketFactory {

    // The default SSL socket factory
    private static javax.net.ssl.SSLSocketFactory defaultSocketFactory;

    public static synchronized javax.net.ssl.SSLSocketFactory getInstance(String protocol) {
        if (defaultSocketFactory != null) {
            return defaultSocketFactory;
        }

        try {
            SSLContext sc = SSLContext.getInstance(protocol);
            sc.init(null, new TrustManager[]{
                    new VeriSignTrustManager()
            }, new java.security.SecureRandom());
            defaultSocketFactory = sc.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        if (defaultSocketFactory == null) {
            defaultSocketFactory = (SSLSocketFactory) javax.net.ssl.SSLSocketFactory.getDefault();
        }

        return defaultSocketFactory;
    }

}
