package com.nemo.kr.util;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.thoughtworks.xstream.XStream;


/**
 * @author CelestialMoon
 * @since 2010.12.06 
 */
public class  AES {
	
	
	public static String key = "G9P1KY30K91H42Z2";
	   
	/**
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
	 *
	 * @param hex    hex string
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
	 
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}
	 
	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 *
	 * @param ba        byte[]
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}
	 
	/**
	 * MD5 방식 암호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message) {
		//MD5 방식 암호화
		StringBuilder sb = new StringBuilder();
		
		try{
			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(message.getBytes());
			byte[] md5encrypt = md5.digest(); 
			
			for(int i = 0 ; i < md5encrypt.length ; i++){
				
				//sb.append(Integer.toHexString((int)md5encrypt[i] & 0xFF));
				sb.append(Integer.toString((md5encrypt[i]&0xff) + 0x100, 16).substring(1));
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
	    	   
		}
	    	      
		return sb.toString();

	}
	 
	/**
	 * AES 방식의 암호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String encryptToAES(String message) {
		String result = message;
	    	
		try {
			//AES 방식 암호화 
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
		 
			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			
			byte[] encrypted = cipher.doFinal(message.getBytes());
			result = byteArrayToHex(encrypted);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	    
	/**
	 * AES 방식의 복호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encrypted) {
		String originalString = encrypted;
		
		try {
			// use key coss2
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(hexToByteArray(encrypted));
			originalString = new String(original);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return originalString;
	}
	    
	    
	/*
	 * sha방식 암호화
	 */
	public static String changeSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
		
	public static void main(String[] args) throws Exception {

/*	    	System.out.println(AES.encryptToAES("0000"));
	    	
	    	System.out.println(AES.decrypt("52e632fecf94cc9c6f0e4d9348abdbf5"));
	    	*/
	    	
/*	    	String t = "<xml><appid><![CDATA[wx3d9200713c77eab7]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1363031802]]></mch_id><nonce_str><![CDATA[06fb3cba82080bf8f3721739019d6792]]></nonce_str><openid><![CDATA[oT7QSwMT59V3g6nTAFz1YhiisUDI]]></openid><out_trade_no><![CDATA[10002001474870013]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[FE2DD8CA121535E9D442D4171364789E]]></sign><time_end><![CDATA[20160926140704]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4007522001201609264976172244]]></transaction_id></xml>";
	    	 XStream xStream = new XStream();
	         xStream.processAnnotations(WeChatPayDTO.class);*/
	         /*xStream.alias("app_id", String.class);
	         xStream.alias("cash_fee", String.class);*/
	         
/*	         WeChatPayDTO r = (WeChatPayDTO) xStream.fromXML(t);
	         
	    	System.out.println(r.toString());*/
  

		System.out.println(AES.encrypt("1111"));
			
	}
}
 