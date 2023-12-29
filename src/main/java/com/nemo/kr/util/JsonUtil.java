package com.nemo.kr.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

/**
 * JSON Util
 * @author nemo
 *
 */
public class JsonUtil {
	public JsonUtil() {
		
	}

	/**
	 * Json 으로 응답
	 * @param response
	 * @param dataMap
	 */
	public void responseJson(HttpServletResponse response,Map dataMap) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		MediaType jsonMimeType = MediaType.APPLICATION_JSON;
		//MediaType jsonMimeType = new MediaType("text/html");
		
		response.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		response.addHeader("Content-Type", "text/html; charset=UTF-8");
		
		if (jsonConverter.canWrite(dataMap.getClass(), jsonMimeType)) {
			try {
				jsonConverter.write(dataMap, jsonMimeType, new ServletServerHttpResponse(response));
				
			} catch (IOException m_Ioe) {
				m_Ioe.printStackTrace();
			} catch (HttpMessageNotWritableException p_Nwe) {
				p_Nwe.printStackTrace();
			}
		}
	}
	
	/**
	 * Json 으로 응답
	 * @param response
	 * @param dataMap
	 */
	public void responseArsJson(HttpServletResponse response,Map dataMap) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		MediaType jsonMimeType = MediaType.APPLICATION_JSON;
		//MediaType jsonMimeType = new MediaType("text/html");
		
		response.setCharacterEncoding("UTF-8");
		
		response.setContentType("application/json;");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		
		if (jsonConverter.canWrite(dataMap.getClass(), jsonMimeType)) {
			try {
				jsonConverter.write(dataMap, jsonMimeType, new ServletServerHttpResponse(response));
				
			} catch (IOException m_Ioe) {
				m_Ioe.printStackTrace();
			} catch (HttpMessageNotWritableException p_Nwe) {
				p_Nwe.printStackTrace();
			}
		}
	}

}
