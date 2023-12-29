package com.nemo.kr.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by pocket on 16/7/6.
 */
public class HttpRequest {
	
	private static final Logger dadaLogger = LoggerFactory.getLogger(HttpRequest.class);

    private int timeout = 10000;

    private RequestConfig requestConfig;

    private static final HttpRequest httpsRequest = new HttpRequest();

    private HttpRequest(){
        requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectionRequestTimeout(timeout).build();

    }

    public static HttpRequest getHttpsRequestSingleton() {
        return httpsRequest;
    }
    
    
    public static void main(String[] args) {
		HttpRequest httpRequest = HttpRequest.getHttpsRequestSingleton();
		Map<String, String> param = new HashMap<String, String>();
		param.put("key", "test");
		param.put("locations", "116.488279020712,39.99687743403794");
		param.put("coordsys", "baidu");
		param.put("output", "JSON");
		
						
		JSONObject resultJson = httpRequest.sendGet("URL을 적으시요", param);
		System.out.println("resultJson : "+resultJson);
		if(!"ok".equals(resultJson.get("info"))){
			//throw new Exception();
			System.out.println("error");
		}

    }
    /**
     * get 방식 http 통신
     * @param url
     * @param params
     * @return
     */
    public JSONObject sendGet(String url, Map<String, String> params) {
    	
    	StringBuffer urlParamsBuffer = new StringBuffer();
    	
    	if(params != null){
	        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
	        
	        while(iter.hasNext()) {
	            Map.Entry<String, String> entry = iter.next();
	            urlParamsBuffer.append(entry.getKey()+"="+entry.getValue()+"&");
	        }
    	}
        String getUrl = url;
       /* if(urlParamsBuffer.length() > 0) {
            urlParamsBuffer.deleteCharAt(urlParamsBuffer.length() - 1);
            getUrl += '?'+ urlParamsBuffer.toString();
        }*/
        
        System.out.println("sendGet : URL : "+getUrl);
        
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(getUrl);
        httpGet.setConfig(requestConfig);
        JSONObject jsonObject = new JSONObject();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            
            System.out.println("responseContent : "+responseContent);
            
            jsonObject = JSON.parseObject(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }


    /**
     * post 방식 http 통신
     * @param url
     * @param params
     * @return
     */
    public JSONObject sendPost(String url, Map<String, String> params) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        httpost.setConfig(requestConfig);
        JSONObject jsonObject = new JSONObject();
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpost);
            HttpEntity entity = response.getEntity();            
            String responseContent = EntityUtils.toString(entity);
            jsonObject = JSON.parseObject(responseContent);
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
    




}
