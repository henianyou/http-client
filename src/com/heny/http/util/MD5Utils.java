package com.heny.http.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F' };

    /**
     * md5加密(默认utf-8编码)
     * @param input
     * @return 返回16进制大写
     */
    public final static String md5Hex(String input) {
        return md5Hex(input, "UTF-8");
    }

    /**
     * md5加密 
     * @param input
     * @param charset 编码格式
     * @return 16进制大写
     */
    public final static String md5Hex(String input, String charset) {
        String result = "";
        try {
            byte[] md = md5(input.getBytes(charset));
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = HEX_DIGITS[b >> 4 & 0xf];
                str[k++] = HEX_DIGITS[b & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * md5加密
     * @param input
     * @return
     */
    public final static byte[] md5(byte[] input) {
        byte[] result = null;
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(input);
            result = mdTemp.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
