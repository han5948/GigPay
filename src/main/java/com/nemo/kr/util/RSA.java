package com.nemo.kr.util;

import java.security.PrivateKey;

import javax.crypto.Cipher;

/**
 * RSA 암호화
 * @author NEMODREAM Co., Ltd.
 *
 */
public class RSA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*
		System.out.println(RSA.encrypt("test123"));
		System.out.println(RSA.encrypt("0000"));
		System.out.println(RSA.encrypt("1111"));
*/
	}

	// rsa util
	public static String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		byte[] encryptedBytes = hexToByteArray(securedValue);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.

		return decryptedValue;
	}

	/**
	 * 16진 문자열을 byte 배열로 변환한다.
	 */
	public static byte[] hexToByteArray(String hex) {
		if ( hex == null || hex.length() % 2 != 0 ) {
			return new byte[]{};
		}
		
		byte[] bytes = new byte[hex.length() / 2];
		for ( int i = 0; i < hex.length(); i +=  2) {
	    byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
	    bytes[(int) Math.floor(i / 2)] = value;
		}

		return bytes;
	}

}