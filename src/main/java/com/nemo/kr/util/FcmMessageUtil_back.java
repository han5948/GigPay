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



public class FcmMessageUtil_back {
	private String jsonPath;
	
	public FcmMessageUtil_back(String path) {
		// TODO Auto-generated constructor stub
		jsonPath = path;
	}
	

	
	public PushSendDTO send(String token, JSONObject notification, JSONObject data, PushSendDTO pushDTO) {
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
	        
	       // message.put("priority", "high");
	        
	        message.put("token", token);
	        message.put("notification", notification);
	        message.put("data", data);
	        
	        
	        JSONObject jsonParams = new JSONObject();
	        jsonParams.put("message", message);
	        
	        System.out.println( "message ::: "+ message);
	        
	        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
	        RestTemplate rt = new RestTemplate();            
	        
	        ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/ilgajaw-97446/messages:send"
	                , HttpMethod.POST
	                , httpEntity
	                , String.class);
	    
	        
	        
	        if (res.getStatusCode() != HttpStatus.OK) {
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
		String token = "dsBLhzuCluE:APA91bF0V5Zzo771gxsw6jgzVfgO14b2qYoCnTNh50q9b5xtBAbr0NKMJUru675Vm7cvRqLab_F7u4X_PVsPb5uO85BD2rD5cpuiNCXEq9pZFeFvd0fFwPA4WfimMu_VZvPXj4OrgUK0";
				  
        
        JSONObject notification = new JSONObject();
        notification.put("title", "TEST Server");
        notification.put("body", "TEST body");
        
        JSONObject data = new JSONObject();
        data.put("pushType", "I");
        
        
        FcmMessageUtil_back fcm = new FcmMessageUtil_back("C:/STS-workspace-3.9.10/ilgaja/src/main/webapp/resources/google/json/ilgajaw-97446-firebase-adminsdk-t4lhh-38b0cf30a6.json");
        fcm.send( token, notification, data, new PushSendDTO());
	}



}
