package com.nemo.kr.util;

import java.io.FileInputStream;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.nemo.kr.dto.PushSendDTO;



public class FcmMessageUtil {
	private String jsonPath;
	
	public FcmMessageUtil(String path) {
		// TODO Auto-generated constructor stub
		jsonPath = path;
	}
	

	
	public PushSendDTO send(String OsType, String token, JSONObject notification, JSONObject data, PushSendDTO pushDTO) {
		// TODO Auto-generated method stub
		try {    
	        String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	        String[] SCOPES = { MESSAGING_SCOPE };
	        
	        GoogleCredential googleCredential = GoogleCredential
	                            .fromStream(new FileInputStream(jsonPath))
	                            .createScoped(Arrays.asList(SCOPES));
	        
	        googleCredential.refreshToken();
	                            
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("content-type" , MediaType.APPLICATION_JSON_VALUE);
	        headers.add("Authorization", "Bearer " + googleCredential.getAccessToken());
	        
		
	        JSONObject message = new JSONObject();
	        
	        JSONObject android = new JSONObject();
	        android.put("priority", "high");	
	        android.put("direct_boot_ok", true);	
			
			
			JSONObject apns = new JSONObject();
			
			JSONObject apnHeaders = new JSONObject();
			apnHeaders.put("apns-priority", "10");	
			apns.put("headers", apnHeaders);
			
			
			
			// android background 에서는 notification 을 보내면 onMessageReceived 에서 받을 수 없다. 그래서 data 에만 값들을 실어 보낸다.........
			if("A".equals(OsType)) {
				data.put("title", notification.get("title"));
				data.put("body", notification.get("body"));
				
			}else {
				message.put("notification", notification);
				
				JSONObject apnPayload = new JSONObject();
				JSONObject aps = new JSONObject();
								
				if("A".equals(data.get("pushType"))) { // A : 알선 관리자 매치 I : 실시간 일감 알림 N : 일반 알림
					System.out.println("알선");
					aps.put("sound","ilgaja_alsun.aiff");
				}else {
					aps.put("sound","ilgaja_alarm.aiff");
				}
				
				apnPayload.put("aps",aps);
				apns.put("payload", apnPayload);			
				
			}
			
			message.put("token", token);
			message.put("data", data);
	        message.put("android", android);
	        message.put("apns", apns);
	        
	        
	        JSONObject jsonParams = new JSONObject();
	        jsonParams.put("message", message);
	        
	        System.out.println( "message ::: "+ message);
	        
	        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
	        RestTemplate rt = new RestTemplate();            
	        
	        ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/ilgajaw-8ec37/messages:send"
	                , HttpMethod.POST
	                , httpEntity
	                , String.class);
	    
	        if (res.getStatusCode() != HttpStatus.OK) {
				/*
				System.out.println("FCM-Exception");
				System.out.println(res.getStatusCode().toString());
				System.out.println(res.getHeaders().toString());
				System.out.println(res.getBody().toString());
				*/
	            pushDTO.setSend_status("0");
	            pushDTO.setFcm_success("0");
	            pushDTO.setFcm_message( res.getBody().toString() );
	            pushDTO.setFcm_result(res.getHeaders().toString());		
	         	
	        } else {
	        	String send_status ="1";
				String fcm_success = res.getStatusCode().toString();
				String fcm_message = res.getBody().toLowerCase();
				
				pushDTO.setSend_status(send_status);
				pushDTO.setFcm_success(fcm_success);
				pushDTO.setFcm_message( fcm_message );
				pushDTO.setFcm_result(res.getHeaders().toString());
			
	        	System.out.println(res.toString());
	        }
	       
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	     
	        pushDTO.setSend_status("0");
            pushDTO.setFcm_success("0");
            pushDTO.setFcm_message("500");
            pushDTO.setFcm_result("FCM-Exception");
	
	    }
		
		
		 return pushDTO;
	}
	

	public static void main(String[] args) {
		//String token = "fBt28mScTsaCPb9yg2zHzy:APA91bErBNhD2r0Wy2QMv2rAYpkEOxBMart5mF7ukoKzj1fkAOEUSGy7i9AZ8joD-HAjiduWYV0UpNdFZkOOyqo0fxauyey3-n05r_k-_U4vnkmvfyq3YCqkL5jU37iTkpP1ayUSnPyf";
		String token = "f-DLLylyd0VPll9eyDVbI5:APA91bH__WZnnoQff-wVesk1JCiFO1iykVizvHuplm6NDWWIXfpmEO7Z2uNKE5CEAm3BL4r1W67Lkv-aYBH-aBC2umHFYrhwpCn2CKLeqwT12fQForEjCNStxlpWJ4Y5NN1PO4wXkqPp";
        
        JSONObject notification = new JSONObject();
        notification.put("title", "타이틀입니다.");
        notification.put("body", "내용입니다.");
        
        JSONObject data = new JSONObject();
        data.put("pushType", "A");
        
        
        FcmMessageUtil fcm = new FcmMessageUtil("C:/STS-workspace-3.9.10/ilgaja/src/main/webapp/resources/google/json/ilgajaw-8ec37-firebase-adminsdk-d4en5-fe647bfb4f.json");
        fcm.send("I", token, notification, data, new PushSendDTO());
	}



}
