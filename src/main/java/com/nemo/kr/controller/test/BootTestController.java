package com.nemo.kr.controller.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nemo.kr.dto.CompanyDTO;



@Controller
@RequestMapping("/test")
public class BootTestController {
	@RequestMapping(value="/bootTest")
	public void bootTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
			
			factory.setReadTimeout(5000); // 읽기시간초과, ms 
			factory.setConnectTimeout(3000); // 연결시간초과, ms 
			
			HttpClient httpClient = HttpClientBuilder.create() 
					.setMaxConnTotal(100) // connection pool 적용 
					.setMaxConnPerRoute(5) // connection pool 적용 
					.build(); 
			
			factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅 
					
			RestTemplate restTemplate = new RestTemplate(factory); 
			
			// local host 용 주소 
			String url = "http://localhost:8090/restTemplateTest"; 
			
			CompanyDTO companyDTO = new CompanyDTO();
			
			companyDTO.setCompany_seq("13");
			
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
			
			parameters.add("Test", companyDTO.getCompany_seq());
			parameters.add("Test1", "test1");
			
			String responseEntity = restTemplate.postForObject(url, parameters, String.class);
		}catch(RestClientException e) {
			e.printStackTrace();
		}

    }

	 
}
