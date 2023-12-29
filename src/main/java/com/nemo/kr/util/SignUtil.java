package com.nemo.kr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.security.MessageDigest;

/**
 * Created by pocket on 16/7/17.
 */
public class SignUtil {

    private static final char lowercaseHexs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String buildSignature(long timestamp, String accessToken, String rivet) {
        String[] params = new String[] {String.valueOf(timestamp), accessToken, rivet};
        Arrays.sort(params);
        StringBuffer originVal = new StringBuffer();
        for(int i=0; i<params.length; i++){
            originVal.append(params[i]);
        }
        try {
            byte[] md = digest(originVal.toString());
            String signVal = byteToHex(md);
            return signVal;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] digest(String str) throws NoSuchAlgorithmException {
        byte[] btInput = str.getBytes();
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        return md;
    }

    private static String byteToHex(byte[] byteArray){
        char str[] = new char[byteArray.length * 2];
        int k = 0;
        char[] hexsAlpha = lowercaseHexs;
        for (int i = 0; i < byteArray.length; i++) {
            byte byte0 = byteArray[i];
            str[k++] = hexsAlpha[byte0 >>> 4 & 0xf];
            str[k++] = hexsAlpha[byte0 & 0xf];
        }
        return new String(str);
    }
}
