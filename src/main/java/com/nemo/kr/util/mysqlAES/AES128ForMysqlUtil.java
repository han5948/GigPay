package com.nemo.kr.util.mysqlAES;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AES128ForMysqlUtil {
	
	private String encode = "UTF-8";
	private SecretKeySpec byte16Key;	// 실제 적용되는 AES123 16byte 키값
	private byte[] temp;
	private String resultStr;	//암호화 및 복화된 되서 리턴될 문자열
	
	public AES128ForMysqlUtil() {
		
	}
	
	public void setEncoding(String encode) {
		this.encode = encode;
	}
	
	private static SecretKeySpec generateAES128Key(final String key, final String encoding) {
		try {
			final byte[] finalKey = new byte[16];
			int i = 0;
			for(byte b: key.getBytes(encoding)) {
				finalKey[i++ % 16] ^= b;
			}
			return new SecretKeySpec(finalKey, "AES");
			
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	/**
	  * @Method Name : encryt
	  * @작성일 : 2021. 2. 18.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 암호화
	
	  */
	public AES128ForMysqlUtil encrypt(String origin, String secretKey) {
		
		this.byte16Key = generateAES128Key(secretKey, this.encode);
		
		try {
			final Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, this.byte16Key);
			this.temp = encryptCipher.doFinal(origin.getBytes(this.encode));
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		return this;
	}
	
	public AES128ForMysqlUtil toHex() {
		this.resultStr = new String(Hex.encodeHex(this.temp)).toUpperCase();
		return this;
	}
	
	public AES128ForMysqlUtil toBase64() {
		this.resultStr = new String(Base64.getEncoder().encode(this.temp));
		return this;
	}
	
	/**
	  * @Method Name : decrypt
	  * @작성일 : 2021. 2. 18.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 복호화
	
	  */
	public AES128ForMysqlUtil decrypt(String secretKey) {
		
		this.byte16Key = generateAES128Key(secretKey, this.encode);
		try {
			final Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, this.byte16Key);
			
			this.resultStr = new String(decryptCipher.doFinal(this.temp));
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		return this;
	}
	
	public AES128ForMysqlUtil unHex(String origin) {
		try {
			this.temp = Hex.decodeHex(origin.toCharArray());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		return this;
	}
	
	public AES128ForMysqlUtil unBase64(String origin) {
		this.temp = Base64.getDecoder().decode(origin);
		return this;
	}
	
	protected String build() {
		return this.resultStr;
	}
	
}
