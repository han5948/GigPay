package com.nemo.kr.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.PushLogDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.PushSendService;

@Component
public  class MessageUtil {
	@Autowired
	PushSendService pushSendService;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	// server key ilgajaW / ilgajaM
	private static final String SERVER_KEY = "AAAAnpn1n30:APA91bFLOsn83k-vDVU09Xuf3EOzvT9sBL1r3pM4bgNTJEonAXsxTijA0s80lS8gjkSJObHoT-mlc1pX3OwepwqXvfiPmN0WE6fyEfWcrCC-FCbDKuFXROsvE_XiCDNonRBwWVkM5021";
	// 요청 url
	private static final String urlStr = "https://fcm.googleapis.com/fcm/send";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	
	private static <T> String toJson(T src) {
        try {
            if (null != mapper) {
                if (src instanceof String) {
                    return (String) src;
                } else {
                    return mapper.writeValueAsString(src);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public void commThread(String title, String content, String seq, List<WorkerDTO> tokenList, String isMain, String pushType, String receiveType, int totalCnt) {
		try {
			String[] andTokens;
			String[] iosTokens;
			String[] receives;
			int maxSize = 900;
			
			List<WorkerDTO> andTokenList = new ArrayList<WorkerDTO>();
			List<WorkerDTO> iosTokenList = new ArrayList<WorkerDTO>();
			
			// push_send 테이블에 푸쉬 정보 넣기
			PushLogDTO pDTO = new PushLogDTO();
			pDTO.setPush_type(pushType);
			pDTO.setTotal_cnt(totalCnt + "");
			pDTO.setContents_seq(seq);
			pushSendService.insertPushLog(pDTO);
			
			// Android IOS List 나누기
			for(int i = 0; i < tokenList.size(); i++) {
				
				if("A".equals(tokenList.get(i).getOs_type())) {
					andTokenList.add(tokenList.get(i));
				}else if("I".equals(tokenList.get(i).getOs_type())) {
					iosTokenList.add(tokenList.get(i));
				}
			}
			
			System.out.println(" tokenList.size() ::" +  tokenList.size());
			System.out.println(" andTokenList.size() ::" +  andTokenList.size());
			System.out.println(" iosTokenList.size() ::" +  iosTokenList.size());
			
			// Android Push를 위한 분기
			if(andTokenList.size() > 0) {
				if(andTokenList.size() > maxSize) {
					andTokens = new String[maxSize];
					receives = new String[maxSize];
				}else {
					andTokens = new String[andTokenList.size()];
					receives = new String[andTokenList.size()];
				}
				
				int size = 0;
				
				//maxSize 만큼 잘라서 보낸다.
				for(int i = 0; i < andTokenList.size(); i++) {
					if(size == maxSize) {
						Thread t = new PushThreadTask(title, content, seq, andTokens, receives, isMain, pushType, receiveType, totalCnt, false, pDTO, "W");
						size = 0;
						
						if((andTokenList.size() - i) >= maxSize) {
							andTokens = new String[maxSize];
							receives = new String[maxSize];
						}else {
							andTokens = new String[andTokenList.size() - i + 1];
							receives = new String[andTokenList.size() - i + 1];
						}
					}
					
					String token = andTokenList.get(i).getPush_token();
					andTokens[size] = token;
					
					String receiveSeq = andTokenList.get(i).getWorker_seq();
					receives[size++] = receiveSeq;
				}
				
				Thread t = new PushThreadTask(title, content, seq, andTokens, receives, isMain, pushType, receiveType, totalCnt, false, pDTO, "W");
			}
			
			// IOS Push를 위한 분기
			if(iosTokenList.size() > 0) {
				if(andTokenList.size() > maxSize) {
					iosTokens = new String[maxSize];
					receives = new String[maxSize];
				}else {
					iosTokens = new String[iosTokenList.size()];
					receives = new String[iosTokenList.size()];
				}
				
				int size = 0;
				
				//maxSize 만큼 잘라서 보낸다.
				for(int i = 0; i < iosTokenList.size(); i++) {
					if(size == maxSize) {
						Thread t = new PushThreadTask(title, content, seq, iosTokens, receives, isMain, pushType, receiveType, totalCnt, true, pDTO, "W");
						size = 0;
						
						if((iosTokenList.size() - i) >= maxSize) {
							iosTokens = new String[maxSize];
							receives = new String[maxSize];
						}else {
							iosTokens = new String[iosTokenList.size() - i + 1];
							receives = new String[iosTokenList.size() - i + 1];
						}
					}
					
					String token = iosTokenList.get(i).getPush_token();
					iosTokens[size] = token;
					
					String receiveSeq = iosTokenList.get(i).getWorker_seq();
					receives[size++] = receiveSeq;
				}
				
				Thread t = new PushThreadTask(title, content, seq, iosTokens, receives, isMain, pushType, receiveType, totalCnt, true, pDTO, "W");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commManagerThread(String title, String content, String seq, List<ManagerDTO> tokenList, String isMain, String pushType, String receiveType, int totalCnt) {
		try {
			String[] andTokens;
			String[] iosTokens;
			String[] receives;
			int maxSize = 900;
			
			List<ManagerDTO> andTokenList = new ArrayList<ManagerDTO>();
			List<ManagerDTO> iosTokenList = new ArrayList<ManagerDTO>();
			
			// push_send 테이블에 푸쉬 정보 넣기
			PushLogDTO pDTO = new PushLogDTO();
			pDTO.setPush_type(pushType);
			pDTO.setTotal_cnt(totalCnt + "");
			pDTO.setContents_seq(seq);
			pushSendService.insertPushLog(pDTO);
			
			// Android IOS List 나누기
			for(int i = 0; i < tokenList.size(); i++) {
				
				if("A".equals(tokenList.get(i).getOs_type())) {
					andTokenList.add(tokenList.get(i));
				}else if("I".equals(tokenList.get(i).getOs_type())) {
					iosTokenList.add(tokenList.get(i));
				}
			}
			
			System.out.println(" tokenList.size() ::" +  tokenList.size());
			System.out.println(" andTokenList.size() ::" +  andTokenList.size());
			System.out.println(" iosTokenList.size() ::" +  iosTokenList.size());
			
			// Android Push를 위한 분기
			if(andTokenList.size() > 0) {
				if(andTokenList.size() > maxSize) {
					andTokens = new String[maxSize];
					receives = new String[maxSize];
				}else {
					andTokens = new String[andTokenList.size()];
					receives = new String[andTokenList.size()];
				}
				
				int size = 0;
				
				//maxSize 만큼 잘라서 보낸다.
				for(int i = 0; i < andTokenList.size(); i++) {
					if(size == maxSize) {
						Thread t = new PushThreadTask(title, content, seq, andTokens, receives, isMain, pushType, receiveType, totalCnt, false, pDTO, "M");
						size = 0;
						
						if((andTokenList.size() - i) >= maxSize) {
							andTokens = new String[maxSize];
							receives = new String[maxSize];
						}else {
							andTokens = new String[andTokenList.size() - i + 1];
							receives = new String[andTokenList.size() - i + 1];
						}
					}
					
					String token = andTokenList.get(i).getPush_token();
					andTokens[size] = token;
					
					String receiveSeq = andTokenList.get(i).getManager_seq();
					receives[size++] = receiveSeq;
				}
				
				Thread t = new PushThreadTask(title, content, seq, andTokens, receives, isMain, pushType, receiveType, totalCnt, false, pDTO, "M");
			}
			
			// IOS Push를 위한 분기
			if(iosTokenList.size() > 0) {
				if(andTokenList.size() > maxSize) {
					iosTokens = new String[maxSize];
					receives = new String[maxSize];
				}else {
					iosTokens = new String[iosTokenList.size()];
					receives = new String[iosTokenList.size()];
				}
				
				int size = 0;
				
				//maxSize 만큼 잘라서 보낸다.
				for(int i = 0; i < iosTokenList.size(); i++) {
					if(size == maxSize) {
						Thread t = new PushThreadTask(title, content, seq, iosTokens, receives, isMain, pushType, receiveType, totalCnt, true, pDTO, "M");
						size = 0;
						
						if((iosTokenList.size() - i) >= maxSize) {
							iosTokens = new String[maxSize];
							receives = new String[maxSize];
						}else {
							iosTokens = new String[iosTokenList.size() - i + 1];
							receives = new String[iosTokenList.size() - i + 1];
						}
					}
					
					String token = iosTokenList.get(i).getPush_token();
					iosTokens[size] = token;
					
					String receiveSeq = iosTokenList.get(i).getManager_seq();
					receives[size++] = receiveSeq;
				}
				
				Thread t = new PushThreadTask(title, content, seq, iosTokens, receives, isMain, pushType, receiveType, totalCnt, true, pDTO, "M");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	class PushThreadTask extends Thread {
		private String title;
		private String content;
		private String seq;
		private String[] token;
		private String[] receive;
		private String isMain;
		private String receiveType;
		private String pushType;
		private int totalCnt;
		private boolean isNotification;
		private PushLogDTO pDTO;
		private String keyFlag;
		
		@Override
		public void run() {
			try {
				JSONObject notification = new JSONObject();
				notification.put("title", title);
				notification.put("body", content);
				
				System.out.println("pushType :::" + pushType);
				if("A".equals(pushType)) {
					notification.put("sound", "ilgaja_alsun.aiff");
				}else {
					notification.put("sound", "ilgaja_alarm.aiff");
				}
				
				// ilboSeq 값 sendSeq 값 통합 필요(sendSeq로 통합 필요)
				// 구분 Flag 값 필요
				JSONObject data = new JSONObject();
				data.put("title", title);
				data.put("body", content);
				data.put("pushId", pDTO.getPush_log_seq());	
				data.put("pushType", pushType);
				data.put("sendSeq",  seq);
				data.put("ilboSeq",  seq);
				data.put("isMain", isMain);

				JSONObject resultJson = sendMessagePush(token, notification, data, pDTO, isNotification, keyFlag);
				
				//로그테이블 업데이트
				pDTO.setSuccess_cnt(resultJson.get("success").toString());
				pDTO.setFail_cnt(totalCnt - Integer.parseInt(pDTO.getSuccess_cnt()) + "");
				pDTO.setStatus_code(resultJson.get("resultCode").toString());
				
				pushSendService.updatePushLog(pDTO);
				
				JSONArray resultArray = (JSONArray) resultJson.get("results");
				
				List<PushLogDTO> pushLogList = new ArrayList<PushLogDTO>();
				
				System.out.println("receive.length :: " + receive.length);
				
				for(int i = 0; i < receive.length; i++) {
					JSONObject resultArrKey = (JSONObject) resultArray.get(i);
					
					PushLogDTO paramDTO = new PushLogDTO();
					
					//개인별(receive) 로그 쌓기
					pDTO.setReceive_seq(receive[i]);
					pDTO.setReceive_type(receiveType);
					
					paramDTO.setReceive_seq(receive[i]);
					paramDTO.setReceive_type(receiveType);
					paramDTO.setPush_log_seq(pDTO.getPush_log_seq());
					
					if(resultArrKey.keySet().contains("error")) {
						pDTO.setPush_result("0");	//실패
						paramDTO.setPush_result("0");
					}else {
						pDTO.setPush_result("1"); //성공
						paramDTO.setPush_result("1");
					}
					
					pDTO.setResult_msg(resultArray.get(i).toString());
					paramDTO.setResult_msg(resultArray.get(i).toString());
					//pushSendService.insertReceivePushLog(pDTO);
					
					pushLogList.add(pDTO);
				}
				
				pushSendService.insertPushLogList(pushLogList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public PushThreadTask(String title, String content, String seq, String[] token, String[] receives, String isMain, String pushType, String receiveType, int totalCnt, boolean isNotification, PushLogDTO pDTO, String keyFlag) {
			// TODO Auto-generated constructor stub
			this.title = title;
			this.content = content;
			this.seq = seq;
			this.token = token;
			this.receive = receives;
			this.isMain = isMain;
			this.receiveType = receiveType;
			this.pushType = pushType;
			this.totalCnt = totalCnt;
			this.isNotification = isNotification;
			this.pDTO = pDTO;
			this.keyFlag = keyFlag;
			
			this.start();
		}
	}
	

	public static JSONObject sendMessagePush(String[] token, JSONObject notification, JSONObject data, PushLogDTO pDTO, boolean isNotification, String keyFlag) {
		try {
			Map params = new HashMap();

			// 디바이스 토큰 배열(단일이든, 복수개든 지원됨)
			params.put("registration_ids", token);
			params.put("data", data);
			params.put("content_available", true);
			params.put("priority", "high");
			
			if(isNotification) {
				params.put("notification", notification);
			}
			
			JSONObject resultJson = null;
			
			resultJson = fcmPost(SERVER_KEY, urlStr, toJson(params));
			
			if(resultJson.isEmpty()){
				System.out.println("전송요청에러");							
			}else{				
				System.out.println("전송요청완료");
				System.out.println(resultJson.get("success") + "건 성공");
				System.out.println(resultJson.get("failure") + "건 실패");
			}
			
			pDTO.setFail_cnt(resultJson.get("failure").toString());
			pDTO.setSuccess_cnt(resultJson.get("success").toString());
			pDTO.setStatus_code(resultJson.get("resultCode").toString());
			
			System.out.println(resultJson.toJSONString());
			
			return resultJson;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static JSONObject fcmPost(String apiKey, String urlStr, String params) throws Exception {
		PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject returnJson = new JSONObject();
        HttpURLConnection httpConn = null;
        try {
        	System.out.println("client param : "+params);
        	System.out.println("URL : "+urlStr);
        	
        	
            URL url = new URL(urlStr);
            httpConn = (HttpURLConnection) url.openConnection();

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
                
                returnJson.put("resultCode", resultCode);
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
                if (httpConn != null) {
                	httpConn.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return returnJson;
	}

}