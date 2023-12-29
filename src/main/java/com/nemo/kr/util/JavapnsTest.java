package com.nemo.kr.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;



//import javapns.back.FeedbackServiceManager;
//import javapns.back.PushNotificationManager;
//import javapns.back.SSLConnectionHelper;
//import javapns.data.Device;
//import javapns.data.PayLoad;



public class JavapnsTest {
	public static int RUN_MODE_DEVELOPMENT = 1;
	public static int RUN_MODE_PRODUCTION = 2;
	public static String APNS_DEVELOPMENT_GATEWAY = "gateway.sandbox.push.apple.com";
	public static String APNS_PRODUCTION_GATEWAY = "gateway.push.apple.com";
	public static String APNS_DEVELOPMENT_FEEDBACK = "feedback.sandbox.push.apple.com";
	public static String APNS_PRODUCTION_FEEDBACK = "feedback.push.apple.com";
	
	// Change Here.
	/*public static String APNS_DEVELOPMENT_KEY = "./keystore/your_apns_development_key.p12";
	public static String APNS_PRODUCTION_KEY = "./keystore/your_production_key.p12";
	public static String APNS_DEVELOPMENT_KEY_PASSWORD = "YOUR DEVELOPMENT PASSWORD";
	public static String APNS_PRODUCTION_KEY_PASSWORD = "YOUR DEVELOPMENT PASSWORD";
	*/
	public static String APNS_DEVELOPMENT_KEY = "";// 
	public static String APNS_PRODUCTION_KEY = "";// 
	public static String APNS_DEVELOPMENT_KEY_PASSWORD = "1234";
	public static String APNS_PRODUCTION_KEY_PASSWORD = "spahemfla";
	/*
	 * 
	 * _game_id : 20171320170927TOBO0
		_league_id : 13
		_match_date : 20170927
		_match_time : 08:10
		_home_name : 보스턴
		_away_name : 토론토

	 */

//	public void sendApns(int runMode, String deviceToken, String alertMessage, int badgeCount, String soundFile) throws Exception {
//		try {
//			
//			PayLoad payLoad = new PayLoad();
//			payLoad.addAlert(alertMessage);
//			payLoad.addBadge(badgeCount);
//			payLoad.addSound(soundFile);
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
//				
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
//	
//	public ArrayList<String> sendFeedback(int runMode) {
//		ArrayList<String> deviceTokens = new ArrayList<String> ();
//		
//		try {
//			int port = 2196;
//			String host = null;
//			String certificatePath = null;
//			String certificatePassword = null;
//			if (runMode == RUN_MODE_DEVELOPMENT) {
//				host 				= APNS_DEVELOPMENT_FEEDBACK;
//				certificatePath 	= APNS_DEVELOPMENT_KEY;
//				certificatePassword = APNS_DEVELOPMENT_KEY_PASSWORD;
//			} else if (runMode == RUN_MODE_PRODUCTION) {
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
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		return deviceTokens;
//	}
	
	public static void main(String... args) throws Exception{
		
		System.out.println(" push TEST ");
//		String fileNameProd = "./keystore/LiveScore_Push.p12";
//		String fileNameProd = "./keystore/CoinBellPushCert.p12";
//		CoinBellPushCert34
//		String fileNameProd = "./keystore/CoinBellPushCert34.p12";
		String fileNameProd = "C:/ilgaja/ilgajaPushCert.p12";
		String fileNameDev = "./keystore/LiveScore_Push_Sandbox.p12";
//		String fileNameDev = "./keystore/demo_push_dev.p12";
//		String fileNameDev = "./keystore/push_demo_dev.p12";
//		String fileNameDev = "./keystore/dibidibi_push_dev.p12";
		APNS_DEVELOPMENT_KEY  = fileNameDev;
		APNS_PRODUCTION_KEY   = fileNameProd;
		
//		JavapnsTest apns = new JavapnsTest();
//		apns.sendApns(
//				RUN_MODE_PRODUCTION,
////				"a193a075c7677fdb2852618abf40c924b5669dab7552042adb4afdd2256601b5",			// 일가자 이강구 개인폰 
////				"b1478eeb7a9a9f89c50fb633cb02f731568c263c8a464a623c6498170a8da6fa", 		// 일가자 솔씨
////				"5f4136c14e7f8db35c2b40748e0d38257c58e4c1f676bb070e2167614097391d",			// 일가자 iphone 7+
////				"d8995a6510a82d49c8984c8aa1b909ad248d16c1929c8e58b29d9b6defb28dda",
////				"cc929b5cfec37215c13bf56308a3a8db2d9b7c2f5e36077c6525b1c816560e23",
////				"cb6b55e856662ab84239ae32111c470b1083709ec39a9abb6545188531863345",
////				"7a9f58213f44393d667ce1b4867fb19908f84a752480e679460905c32e52d5c9",
////				"91faad865323bfd34f64e9a9b08e0fb2b46002eaef148f59896c0f783153230c",
////				"3f1546398eb9c8ce83e8475615015c814c3256776ebf68a2648bb7678c721315",
////				"9bfc47f21e4352185f9ed06b82f474caaff69c1857f4fde5ed1721cb1a03892d",
////				"8029279f5f9cf1adf8b2a52d1ac8ed5ec8a8fe3c80e0ed9d9752a798068a6365",
////				"d9e4bc0d0d8f74fe6992278d50de7d5a1b7e0ebeab296b055a461d1d46a94aab",
////				"323ab1add4113f965f561c9b676fb1bbff853155b4218954b44559d6a928e3a5",
//				"6865072d1586f8143c6dfaec4bdca3d92989a12dc6823b6fa8ca69f954f34b5e",
//				"APNS Test2222!~\n1216",
//				1,
//				"default");
//		
//		
//		ArrayList<String> deviceTokens = apns.sendFeedback(RUN_MODE_PRODUCTION);
//		
//		for (String deviceToken : deviceTokens) {
//			System.out.println(deviceToken);
//		}
	}
}
