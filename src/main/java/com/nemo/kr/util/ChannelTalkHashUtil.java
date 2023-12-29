package com.nemo.kr.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;

public class ChannelTalkHashUtil {

	private final static String CHANNEL_TALK_SECRET_KEY = "6d0a002f27d7ab536025b93229c3359270c46c509aac36f4b4f70f013965bf2e";
	private final static String HASH_ALGORITHM = "HMACSHA256";
	
	public static String channelTalkEncode(String adminId) throws Exception {
		
		try {
			byte[] data = adminId.getBytes();
		    SecretKey macKey = new SecretKeySpec(DatatypeConverter.parseHexBinary(CHANNEL_TALK_SECRET_KEY), HASH_ALGORITHM);
		    Mac mac = Mac.getInstance(HASH_ALGORITHM);
		    mac.init(macKey);
		    byte[] digest = mac.doFinal(data);
		    byte[] hexBytes = new Hex().encode(digest);
		    return new String(hexBytes, StandardCharsets.UTF_8);
		    
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new Exception(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(channelTalkEncode("gj"));
	}
}
