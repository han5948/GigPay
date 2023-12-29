package com.nemo.kr.util.mysqlAES;

import java.security.MessageDigest;

/**
  * @FileName : MyAESUtil.java
  * @Project : ilgaja
  * @Date : 2021. 2. 18. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : DB 암호화 된것을 java에서 복호화 , 
  */
public class AESUtil extends AES128ForMysqlUtil{

	private String mySecretKey = "dlfrkwkspahemfla!";
	
	public AESUtil() {
		super();
		this.mySecretKey = sha256(mySecretKey);
	}
	
	public AESUtil(String secretKey) {
		super();
		this.mySecretKey = sha256(secretKey);
	}
	
	public void setSecretKey(String secretKey) {
		this.mySecretKey = sha256(secretKey);
	}
	
	public String getEncrypt_To_Hex(String origin) {
		return super.encrypt(origin, mySecretKey).toHex().build();
	}
	
	public String getDecrypt_From_Hex(String origin) {
		return super.unHex(origin).decrypt(mySecretKey).build();
	}
	
	public String getEncrypt_To_Base64(String origin) {
		return super.encrypt(origin, mySecretKey).toBase64().build();
	}
	
	public String getDecrypt_From_Base64(String origin) {
		return super.unBase64(origin).decrypt(mySecretKey).build();
	}
	
	public String sha256(String secreKey) {

		String hexKey = "";
		
		try{

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(secreKey.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			//출력
			hexKey  = hexString.toString();

		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
		
		return hexKey;
		
	}
	
	public static void main(String[] args) {
		

		
		AESUtil aesUtil = new AESUtil();
				
		System.out.println("java 암호화 to hex : " + aesUtil.getEncrypt_To_Hex("안녕하세요"));
		System.out.println("java 복화화 form hex : " + aesUtil.getDecrypt_From_Hex("77148649E6DC4D9FFD2EF42EF1A25DA0"));
		
		System.out.println("===================================================");
		
		System.out.println("java 암호화 to base64 : " + aesUtil.getEncrypt_To_Base64("안녕하세요"));
		System.out.println("java 복화화 from base64 : " + aesUtil.getDecrypt_From_Base64("dxSGSebcTZ/9LvQu8aJdoA=="));
		
	}
}
