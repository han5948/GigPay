package com.nemo.kr.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.EnhancedApnsNotification;





public  class FcmUtil {
	
	
	private static ObjectMapper mapper = new ObjectMapper();

	
	// server key
	private static final String SERVER_KEY_W = "AAAAU1W9riA:APA91bEyq4yFUI-vpfzdAfgu9eoUFKmGLbr0mW6y3LBy0LnSfWEocC14WQkJH20Z-k8KXkYqZOj8Hq0i996UUGbrIK9HIpDnU4D2ePMpXPEZH4Gl5yY3eoXA9_f5CRfrji_Wg7EV3K_g";
	private static final String SERVER_KEY_M = "";
	private static final String SERVER_KEY_B = "AAAAh6pFV6I:APA91bHiLa6fiINgnYY4lvqgDyzvfzrCnUovMf1GeP0GxaBC4urcNktsHau5C8QCPSjB4sMcTyeISkpOv9NlirAFkFUfGgQ43mC8Gd9-x_Aq-b7JBnzGqEo9G8gwTn9f2n6wuFWsSs1u";
	
	// 요청 url
	private static final String urlStr = "https://fcm.googleapis.com/fcm/send";

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		sendWorkerPush("test","clFJ-QgXTuWXHfdxmAwtTc:APA91bEf22ynMSML5PPZ0fK7xiE2xpBPrKy4o3lgnFS8Um7t_mYnqcXax08NEiM2L-XRlSGRK51pm-Pk2N4F5qIDxzfmWv2Kb-vGi_equco-7q5R0xqm1WJSgoK2hAhzkirT8LTVG0VF","A","1","1");
		
	}
	
	//ilgajaW 에 한건씩 보내기
	public static JSONObject sendWorkerPush( String workName, String token, String pushType, String sendSeq, String ilboSeq ){
		try {
			
			Map params = new HashMap();
			Map data = new HashMap();
			
			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			String[] registration_ids = {token};			
			params.put("registration_ids", registration_ids);
			
			// popup
			data.put("title","test");
			data.put("msg","[" + workName + "] 현장 일자리");
			data.put("pushId", "1");										//같은 아이디를 보내서 중복으로 올때 갱신 하도록 한다.
			data.put("pushType",pushType);
			data.put("sendSeq", sendSeq);
			data.put("ilboSeq", ilboSeq);
			
			params.put("data", data);
			
			JSONObject resultJson = fcmPost(SERVER_KEY_B, urlStr, toJson(params));
			
			
			if(resultJson.isEmpty()){
				System.out.println("전송요청에러");							
			}else{				
				System.out.println("FCM전송요청완료");
				System.out.println(resultJson.get("success") + "건 성공");
				System.out.println(resultJson.get("failure") + "건 실패");
			}
			System.out.println(resultJson.toJSONString());
			
			return resultJson;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	//ilgajaW 에 한건씩 보내기
	public static JSONObject sendAlimPush(String alimTitle, String alimContent, String pushToken, String pushType, String sendSeq, String alimSeq, String isMain){
		try {
			
			Map params = new HashMap();
			Map data = new HashMap();
			
			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			String[] registration_ids = {pushToken};			
			params.put("registration_ids", registration_ids);
			
			// popup
			data.put("title", alimTitle);
			data.put("msg", alimContent);
			data.put("pushId", sendSeq);										//같은 아이디를 보내서 중복으로 올때 갱신 하도록 한다.
			data.put("pushType",pushType);
			data.put("sendSeq", sendSeq);
			data.put("ilboSeq", alimSeq);
			data.put("isMain", isMain);
			
			
			params.put("data", data);
			
			JSONObject resultJson = fcmPost(SERVER_KEY_W, urlStr, toJson(params));
			
			
			if(resultJson.isEmpty()){
				System.out.println("전송요청에러");							
			}else{				
				System.out.println("FCM전송요청완료");
				System.out.println(resultJson.get("success") + "건 성공");
				System.out.println(resultJson.get("failure") + "건 실패");
			}
			System.out.println(resultJson.toJSONString());
			
			return resultJson;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//ilgajaW 에 한건씩 보내기
	public static JSONObject sendJobAlimPush(String jobAlim_content, String pushToken, String pushType, String sendSeq, String jobAlimSeq, String isMain ){
		try {
			
			Map params = new HashMap();
			Map data = new HashMap();
			
			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			String[] registration_ids = {pushToken};			
			params.put("registration_ids", registration_ids);
			
			// popup
			data.put("title","일가자 일자리");
			data.put("msg", jobAlim_content);
			data.put("pushId", "2");										//같은 아이디를 보내서 중복으로 올때 갱신 하도록 한다.
			data.put("pushType",pushType);
			data.put("sendSeq", sendSeq);
			data.put("ilboSeq", jobAlimSeq);
			data.put("isMain", isMain);
			
			params.put("data", data);
			
			JSONObject resultJson = fcmPost(SERVER_KEY_W, urlStr, toJson(params));
			
			
			if(resultJson.isEmpty()){
				System.out.println("전송요청에러");							
			}else{				
				System.out.println("FCM전송요청완료");
				System.out.println(resultJson.get("success") + "건 성공");
				System.out.println(resultJson.get("failure") + "건 실패");
			}
			System.out.println(resultJson.toJSONString());
			
			return resultJson;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//ilgajaW 에 한건씩 보내기
	public static JSONObject sendManagerPush( String osType, String startDate, String token, String pushType, String workDate, String workName, String workerName, String sendStatus ){
		
		if(osType.equals("A")){
			return androidManagerPush(startDate, token, pushType, workDate,  workName,  workerName,  sendStatus );
		}else{
			return null;
//			return IosManagerPush(startDate, token, pushType, workDate,  workName,  workerName,  sendStatus );
		}
	}
		
		
	//ilgajaW 에 한건씩 보내기
//	public static JSONObject IosManagerPush(  String startDate, String token, String pushType, String workDate, String workName, String workerName, String workStatus ){
		
//		System.out.println("===IOS PUSH====" + token);
//		try {
//			if("출역".equals(workStatus) || "출력".equals(workStatus) ) workStatus = "출발";
//		
//			String sendMessage = "구인일: " +workDate + "\n현장명: " + workName +"\n구직자: "+ workerName + "["+workStatus+ "] " ;
//			
//			IosPushUtil apns = new IosPushUtil();
//			apns.sendApns(
//					2,	//RUN_MODE_PRODUCTION
//					token,
//					sendMessage,
//					0,			//badge count
//					"default");
//			
//			
//			ArrayList<String> deviceTokens = apns.sendFeedback(2); //RUN_MODE_PRODUCTION
//			
//			for (String deviceToken : deviceTokens) {
//				System.out.println(deviceToken);
//			}
//			
//			return null;
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
		
	//ilgajaW 에 한건씩 보내기
	public static JSONObject androidManagerPush(  String startDate, String token, String pushType, String workDate, String workName, String workerName, String sendStatus ){
		
		try {
			
			Map params = new HashMap();
			Map data = new HashMap();
			
			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			String[] registration_ids = {token};			
			params.put("registration_ids", registration_ids);
			
			// popup
			data.put("title","일가자M");
			data.put("workDate",workDate);
			data.put("workName",workName);
			data.put("workerName",workerName);
			data.put("workStatus", sendStatus );
			data.put("pushId", "1");		//같은 아이디를 보내서 중복으로 올때 갱신 하도록 한다.
			data.put("pushType",pushType);	//A : alim
			data.put("startDate", startDate);
			
			params.put("data", data);
			
			JSONObject resultJson = fcmPost(SERVER_KEY_M, urlStr, toJson(params));
			
			if(resultJson.isEmpty()){
				System.out.println("전송요청에러");							
			}else{				
				System.out.println("전송요청완료");
				System.out.println(resultJson.get("success") + "건 성공");
				System.out.println(resultJson.get("failure") + "건 실패");
			}
			System.out.println(resultJson.toJSONString());
			
			return resultJson;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static <T> String toJson(T src) {
        try {
            if (null != mapper) {
                if (src instanceof String) {
                    return (String) src;
                } else {
                    //return mapper.writeValueAsString(src);
                    return mapper.writeValueAsString(src);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	private static JSONObject fcmPost(String apiKey, String urlStr, String params) throws Exception {

		PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject returnJson = new JSONObject();
        
        try {
        	System.out.println("client param : "+params);
        	System.out.println("URL : "+urlStr);
        	
        	
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setDoOutput(true);   
            httpConn.setDoInput(true);
            
            httpConn.setConnectTimeout(10000 * 10);
            httpConn.setReadTimeout(10000 * 10);
            
            httpConn.setRequestMethod("POST");   
            httpConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConn.setRequestProperty("Authorization", "key="+apiKey);

            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.write(params.getBytes("utf-8"));
            dos.flush();
            dos.close();

            int resultCode = httpConn.getResponseCode();
            
            System.out.println("resultCode : "+resultCode);
            System.out.println("resultCode : "+httpConn.getResponseMessage());
            
            if (HttpURLConnection.HTTP_OK == resultCode) {
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    //result.append(readLine).append("\n");
                	result.append(readLine);
                }
                responseReader.close();
                
                returnJson = (JSONObject) parser.parse(result.toString());
                
            	
            	
            }else{
            	System.out.println(">>>>>>>>>>ERROR CODE : "+resultCode);
            	
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return returnJson;
	}

	
}






