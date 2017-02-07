package com.hanyee.androidlib.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5CaculateUtil {

    private static char[] hexChar = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String getHash(String fileName, String hashType) throws IOException, NoSuchAlgorithmException {

        File f = new File(fileName);

        InputStream ins = new FileInputStream(f);

        byte[] buffer = new byte[8192];
        MessageDigest md5 = MessageDigest.getInstance(hashType);

        int len;
        while ((len = ins.read(buffer)) != -1) {
            md5.update(buffer, 0, len);
        }

        ins.close();
        // 自己写的转计算MD5方法
        return toHexString(md5.digest());
    }

    protected static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}