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





public  class FcmIlgajaBUtil {
	
	
	private static ObjectMapper mapper = new ObjectMapper();

	
	// server key
	private static final String SERVER_KEY_B = "AAAAh6pFV6I:APA91bHiLa6fiINgnYY4lvqgDyzvfzrCnUovMf1GeP0GxaBC4urcNktsHau5C8QCPSjB4sMcTyeISkpOv9NlirAFkFUfGgQ43mC8Gd9-x_Aq-b7JBnzGqEo9G8gwTn9f2n6wuFWsSs1u";
	// 요청 url
	private static final String urlStr = "https://fcm.googleapis.com/fcm/send";

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String [] tokens= {
				"frGJqa5CR0KJ73Aj6iDA9u:APA91bE37tPhJnqznIfaN9TP0ZI4JgwHrn6fn3Apt6n5ET1-7kDB-NlWtVv1rXCGYbkdQBwJiWcKREiJb7NMmAQQBDctS2bHWYWP26lFfTfG-ePzCgp1Ie_WUFOjCfaxiL-oCqFad2xL"
		};
		
		sendPush(
				"aa",
				tokens,
				"M",
				"1111111231",
				"0"
			);
		
	}
	
	public static JSONObject sendPush( String workName, String[] tokens, String pushType, String sendSeq, String isMain ){
		try {
			
			Map params = new HashMap();
			Map data = new HashMap();
			
			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			String[] registration_ids = tokens;			
			params.put("registration_ids", registration_ids);
			
			// popup
			data.put("title","신규 오더[WEB]" + sendSeq);
			data.put("body","[" + workName + "] WEB에서 신규 오더가 접수되었습니다.");
			data.put("pushType",pushType);
			data.put("sendSeq", sendSeq);
			data.put("isMain", isMain);
			
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






