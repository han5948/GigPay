package com.nemo.kr.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;






public class IosPushUtil {
	public static int RUN_MODE_DEVELOPMENT = 1;
	public static int RUN_MODE_PRODUCTION = 2;
	public static String APNS_DEVELOPMENT_GATEWAY = "gateway.sandbox.push.apple.com";
	public static String APNS_PRODUCTION_GATEWAY = "gateway.push.apple.com";
	public static String APNS_DEVELOPMENT_FEEDBACK = "feedback.sandbox.push.apple.com";
	public static String APNS_PRODUCTION_FEEDBACK = "feedback.push.apple.com";
	

	public static String APNS_DEVELOPMENT_KEY = "./keystore/LiveScore_Push_Sandbox.p12";
	public static String APNS_PRODUCTION_KEY = "D:/PUSHCERT/AuthKey_XY379X3QYU.p8";// 
	
	public static String APNS_DEVELOPMENT_KEY_PASSWORD = "1234";
	public static String APNS_PRODUCTION_KEY_PASSWORD = "spahemfla";

//	public void sendApns(int runMode, String deviceToken, String alertMessage, int badgeCount, String soundFile) throws Exception {
//		try {
//			
////			PayLoad payLoad = new PayLoad();
////			payLoad.addAlert(alertMessage);
////			payLoad.addBadge(badgeCount);
////			payLoad.addSound(soundFile);
//			
////			payLoad.addCustomDictionary("cheer_no", "372165056"); 
////			payLoad.addCustomDictionary("compe", "baseball");
////			payLoad.addCustomDictionary("country_code", "KR");
////			payLoad.addCustomDictionary("cheer_no", "372165056");
////			payLoad.addCustomDictionary("league_id", "13");
////			payLoad.addCustomDictionary("match_date", "20170927");
////			payLoad.addCustomDictionary("push_type", "6");
////			payLoad.addCustomDictionary("schedule_id", "20171320170927TOBO0");
////			payLoad.addCustomDictionary("user_country_code", "KR");
////			payLoad.addCustomDictionary("writer", "1991484");
//			
//			System.out.println("deviceToken ::" + deviceToken);
//			PushNotificationManager pushManager = PushNotificationManager.getInstance();
//			pushManager.addDevice("iPhone", deviceToken);
//
//			int port 				= 2195;
//			String host 			= null;
//			String certificatePath 	= null;
//			String certificatePassword = null;
//			
//			if (runMode == RUN_MODE_DEVELOPMENT) {
//				
//				host 				= APNS_DEVELOPMENT_GATEWAY;
//				certificatePath 	= APNS_DEVELOPMENT_KEY;
//				certificatePassword = APNS_DEVELOPMENT_KEY_PASSWORD;
//				
//			} else if (runMode == RUN_MODE_PRODUCTION) {
//				System.out.println( "IOS 상용 PUSH" );
//				host 				= APNS_PRODUCTION_GATEWAY;
//				certificatePath 	= APNS_PRODUCTION_KEY;
//				certificatePassword = APNS_PRODUCTION_KEY_PASSWORD;
//			}
//			
//			pushManager.initializeConnection(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//
//			Device client = pushManager.getDevice("iPhone");
//			pushManager.sendNotification(client, payLoad);
//			pushManager.stopConnection();
//
//			pushManager.removeDevice("iPhone");
//		} catch (Exception ex) {
//			ex.printStackTrace(); 
//		}
//	}
	
//	public ArrayList<String> sendFeedback(int runMode) {
//		ArrayList<String> deviceTokens = new ArrayList<String> ();
//		
//		try {
//			int port = 2196;
//			String host = null;
//			String certificatePath = null;
//			String certificatePassword = null;
//			if (runMode == RUN_MODE_DEVELOPMENT) {
//				
//				host 				= APNS_DEVELOPMENT_FEEDBACK;
//				certificatePath 	= APNS_DEVELOPMENT_KEY;
//				certificatePassword = APNS_DEVELOPMENT_KEY_PASSWORD;
//			} else if (runMode == RUN_MODE_PRODUCTION) {
//				
//				host 				= APNS_PRODUCTION_FEEDBACK;
//				certificatePath 	= APNS_PRODUCTION_KEY;
//				certificatePassword = APNS_PRODUCTION_KEY_PASSWORD;
//			}
//			
//			FeedbackServiceManager feedbackManager = FeedbackServiceManager.getInstance();
//			LinkedList<Device> devices = feedbackManager.getDevices(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//			
//			if (devices.size() > 0) {
//				ListIterator<Device> itr = devices.listIterator();
//				while (itr.hasNext()) {
//					Device device = itr.next();
//					deviceTokens.add(device.getToken());
//					
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		return deviceTokens;
//	}
	
	public static void main(String... args) throws Exception{
		
		
	}
}

