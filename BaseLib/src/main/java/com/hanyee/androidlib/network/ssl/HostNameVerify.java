package com.hanyee.androidlib.network.ssl;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HostNameVerify implements HostnameVerifier {

    private String mHostName;

    public HostNameVerify(String hostName) {
        mHostName = hostName;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        try {
            Certificate[] certificates = session.getPeerCertificates();
            X509Certificate cert = (X509Certificate) certificates[0];
            String cn = cert.getSubjectDN().getName().split(",")[0].substring(3);
            return cn.equals(mHostName) && verifyHostName(hostname, cn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyHostName(String hostName, String cn) {
        if (hostName == null || hostName.isEmpty() || cn == null || cn.isEmpty()) {
            return false;
        }

        cn = cn.toLowerCase(Locale.US);

        if (!cn.contains("*")) {
            return hostName.equals(cn);
        }

        if (cn.startsWith("*.") && hostName.regionMatches(0, cn, 2, cn.length() - 2)) {
            return true; // "*.smartisan.com" matches "smartisan.com"
        }

        int asterisk = cn.indexOf('*');
        int dot = cn.indexOf('.');
        if (asterisk > dot) {
            // malformed; wildcard must be in the first part of the cn
            return false;
        }

        if (!hostName.regionMatches(0, cn, 0, asterisk)) {
            // prefix before '*' doesn't match
            return false;
        }

        int suffixLength = cn.length() - (asterisk + 1);
        int suffixStart = hostName.length() - suffixLength;

        if (!hostName.regionMatches(suffixStart, cn, asterisk + 1, suffixLength)) {
            // suffix after '*' doesn't match
            return false;
        }

        return true;
    }
}