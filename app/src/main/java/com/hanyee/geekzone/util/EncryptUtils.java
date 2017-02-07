package com.hanyee.geekzone.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtils {

    public static String decrypt(String key, String data) {

        EncryptUtils encryptUtils = new EncryptUtils();

        String result = "";

        if (data != null && data.length() != 0) {
            String temp;
            try {
                temp = encryptUtils.generateDecryptString(
                        encryptUtils.generateKeyHexString(key), data);
                int endIndex = temp.indexOf("00");
                if (endIndex != -1) {
                    if (endIndex % 2 != 1) {
                        temp = temp.substring(0, endIndex);
                    } else {
                        temp = temp.substring(0, endIndex + 1);
                    }
                }
                result = new String(encryptUtils.convertHexString2Int(temp),
                        "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    public static String encrypt(String key, String data) {

        EncryptUtils encryptUtils = new EncryptUtils();

        String result = "";

        if (data != null && data.length() != 0) {
            try {
                result = encryptUtils.generateEncryptString(
                        encryptUtils.generateKeyHexString(key),
                        encryptUtils.generateDataHexString(data));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private String generateKeyHexString(String key) {
        String result = "";
        result = convertByteArray2HexString(resizeByteArray(key.getBytes()));
        return result;
    }

    private String generateDataHexString(String data) {
        String result = "";
        try {
            result = convertByteArray2HexString(expandByteArray(data
                    .getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String convertByteArray2HexString(byte[] data) {

        int length = data.length;

        StringBuffer result = new StringBuffer(2 * length);

        String prefix = "";

        for (int i = 0; i < length; i++) {

            int item = data[i];

            prefix = ((item & 0xF0) == 0) ? "0" : "";

            result.append(prefix + Integer.toHexString(item & 0xFF));
        }

        return result.toString();
    }

    private void copyByteArray(byte[] source, int offset, byte[] dest,
                               int start, int end) {
        if ((dest != null) && (source != null)) {
            int length = source.length;
            for (int i = 0; i < end; i++) {
                if ((offset + i) < length) {
                    dest[(start + i)] = source[(offset + i)];
                }
            }
        }
    }

    private String generateEncryptString(String key, String data)
            throws Exception {
        EncryptInner locala = new EncryptInner(this, key, data, null);
        Cipher localCipher = Cipher.getInstance("DES/ECB/NoPadding");
        SecretKeyFactory localSecretKeyFactory = SecretKeyFactory
                .getInstance("DES");
        localCipher.init(1, localSecretKeyFactory
                .generateSecret(new DESKeySpec(EncryptInner.getKeyHexIntByteArray(locala))));
        return convertByteArray2HexString(
                localCipher.doFinal(EncryptInner.getDataHexIntByteArray(locala))).toUpperCase();
    }

    private byte[] expandByteArray(byte[] data) {

        int length = data.length;

        int expandLength = length + 8 - length % 8;

        byte[] result = new byte[expandLength];

        copyByteArray(data, 0, result, 0, length);

        return result;
    }

    private byte[] resizeByteArray(byte[] data) {
        byte[] result = new byte[8];

        if (data.length < 8) {
            data = expandByteArray(data);
        }

        copyByteArray(data, 0, result, 0, 8);

        return result;
    }

    private byte[] convertHexString2Int(String data) {

        byte[] result = new byte[data.length() / 2];

        int index = 0;
        int offset = 0;

        while (index < result.length) {
            result[index] = ((byte) Integer.parseInt(
                    data.substring(offset, offset + 2), 16));
            index += 1;
            offset += 2;
        }

        return result;
    }

    private String generateDecryptString(String key, String data)
            throws Exception {
        EncryptInner locala = new EncryptInner(this, key, data, null);
        Cipher localCipher = Cipher.getInstance("DES/ECB/NoPadding");
        SecretKeyFactory localSecretKeyFactory = SecretKeyFactory
                .getInstance("DES");
        localCipher.init(2, localSecretKeyFactory
                .generateSecret(new DESKeySpec(EncryptInner.getKeyHexIntByteArray(locala))));
        return convertByteArray2HexString(
                localCipher.doFinal(EncryptInner.getDataHexIntByteArray(locala))).toUpperCase();
    }

    private static class EncryptInner {

        private final byte[] keyHexIntByteArray;
        private final byte[] dataHexIntByteArray;

        private EncryptInner(EncryptUtils encryptUtils, String key, String data) {
            keyHexIntByteArray = encryptUtils.convertHexString2Int(key);
            dataHexIntByteArray = encryptUtils.convertHexString2Int(data);
        }

        private EncryptInner(EncryptUtils encryptUitls, String key, String data,
                             EncryptInner inner) {
            this(encryptUitls, key, data);
        }

        static byte[] getKeyHexIntByteArray(EncryptInner inner) {
            return inner.keyHexIntByteArray;
        }

        static byte[] getDataHexIntByteArray(EncryptInner inner) {
            return inner.dataHexIntByteArray;
        }
    }
}
