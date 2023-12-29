package com.nemo.kr.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemo.kr.dto.NotificationDTO;

public class NotificationUtil {
	
	private RestTemplate restTemplate;
	
	public NotificationUtil(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void ilboAlim(NotificationDTO notificationDTO) {
		Thread t = new NotificationThread(notificationDTO);
	}
	
	class NotificationThread extends Thread {
		private NotificationDTO notificationDTO;
		
		@Override
		public void run() {
//			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
//			
//			factory.setReadTimeout(5000); // 읽기시간초과, ms 
//			factory.setConnectTimeout(3000); // 연결시간초과, ms 
//			
//			HttpClient httpClient = HttpClientBuilder.create() 
//					.setMaxConnTotal(100) // connection pool 적용 
//					.setMaxConnPerRoute(5) // connection pool 적용 
//					.build(); 
//			
//			factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅 
//					
//			RestTemplate restTemplate = new RestTemplate(factory); 
			
			String apiUrl = "";
			
			String act = System.getProperty("spring.profiles.active");
				
			try {
				Properties properties = new Properties();
				Reader reader;
				reader = Resources.getResourceAsReader("properties/" + act + "/common-config.properties");
				properties.load(reader);
				
				if(StringUtil.isNull(notificationDTO.getApi_url()))
					apiUrl = properties.getProperty("notificationBaseUrl") + "/notificationPush";
				else {
					apiUrl = properties.getProperty("notificationBaseUrl") + notificationDTO.getApi_url(); 
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
			
			ObjectMapper mapper = new ObjectMapper();
			
			String jsonString = "";
			
			try {
				jsonString = mapper.writeValueAsString(notificationDTO);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			parameters.add("notificationDTO", jsonString);

			// return 값 설정 가능
			String responseEntity = restTemplate.postForObject(apiUrl, parameters, String.class);
		}
		
		public NotificationThread(NotificationDTO notificationDTO) {
			// TODO Auto-generated constructor stub
			this.notificationDTO = notificationDTO;
			this.start();
		}
	}
}
