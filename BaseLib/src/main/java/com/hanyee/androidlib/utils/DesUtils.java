package com.hanyee.androidlib.utils;

import android.util.Base64;
import android.util.Base64InputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;

public class DesUtils {
    public static String strDefaultKey = "UWtwTlJraEtNakF4TlVOUFVGbFNTVWRJVkNFaElTRWgK";
    private static DesUtils instance;
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public static synchronized DesUtils getInstance() {
        if (instance == null) {
            instance = new DesUtils(strDefaultKey);
        }
        return instance;
    }

    private DesUtils(String strKey) {
        // Security.addProvider(null);
        Key key;
        strKey = Base64Security.decode(Base64Security.decode(strKey));
        try {
            key = getKey(strKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

    public static class Base64Security {

        public static String decode(String str) {
            byte[] buffer = new byte[getBufferSize(str)];
            InputStream inputStream = null;
            try {
                inputStream = new Base64InputStream(new ByteArrayInputStream(str.getBytes()),
                        Base64.DEFAULT);
                inputStream.read(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
            return new String(buffer);
        }

        public static int getBufferSize(String s) {
            int size = s.length() / 4 * 3;
            int equalFlagIndex = s.indexOf('=');
            if (equalFlagIndex != -1)
                size -= (s.length() - equalFlagIndex);
            return size;
        }
    }
}
