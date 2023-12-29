package com.nemo.kr.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.nemo.kr.common.Const;

public class AES256Util {
	private String iv;
	private Key keySpec;
	
	public static void main(String[] args) throws Exception {
		
		Random rnd =new Random();
		StringBuffer buf =new StringBuffer();
		 
		for(int i=0;i<16;i++){
		    if(rnd.nextBoolean()){
		        buf.append((char)((int)(rnd.nextInt(26))+97));
		    }else{
		        buf.append((rnd.nextInt(10))); 
		    }
		}
		
		//System.out.println("buf : "+buf.toString());
		
		AES256Util ae = new AES256Util(Const.AES256_KEY);
		
		String enc = ae.encrypt("12311231231212312313213123122312sf326");
		//System.out.println(ae.decrypt("RAt2Ito3RshMXiru5vBXhg=="));
		//String enc2 = ae.encrypt("32!;!20160831115244634!;!20160901115244634");
		
		
		
		
		
		
		
		String test = "-0.9";
		//System.out.println( Double.parseDouble(test));
		
		//System.out.println(Double.parseDouble(test) > -1);
		
		double test1 = 11.1111111;
		double test2 = 11.0;
		
		BigDecimal result;
		BigDecimal d1 = new BigDecimal("11.01");
		BigDecimal d2 = new BigDecimal("11.01");
		
		result = d1.add(d2);
		//System.out.println(result);
		
		
		
		
		//System.out.println("enc : "+enc);
		//System.out.println("enc2 : "+enc2);
		
		//System.out.println(ae.decrypt("p9mB2g8cWOxXjuql2eaKX1e+GCOwE6EQnlTeMbNxyQsnwqyWXWQZYvk18Qw1MMlO"));
		/*
		System.out.println(ae.decrypt("YEp+ic+NAiNzKiI0aprX2A=="));
		System.out.println(ae.decrypt("YEp+ic+NAiNzKiI0aprX2A=="));
		System.out.println(ae.decrypt("YEp+ic+NAiNzKiI0aprX2A=="));
		System.out.println(ae.decrypt("YEp+ic+NAiNzKiI0aprX2A=="));*/
		/*System.out.println(ae.decrypt("HCNyaxp8hvj6gyOoA9GvdA"));*/
		
		
	}
	
	
	
	    
	 /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     * @param key 암/복호화를 위한 키값
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생
     */
    public AES256Util(String key) throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    
    
    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     * @param str 복호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str){
        String result = "";
        Cipher c;

        
        //System.out.println(str);
	
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			result = new String(c.doFinal(byteStr), "UTF-8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
        
        return result;
    }
}
