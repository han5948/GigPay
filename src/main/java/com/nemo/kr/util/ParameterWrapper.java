package com.nemo.kr.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class ParameterWrapper extends HttpServletRequestWrapper{

	public String URL;
	
	public ParameterWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		this.URL = request.getRequestURI();
	}
	
	public String getParameter(String name){
		
		String paramVal = super.getParameter(name);
		if(URL.indexOf("/admin/") > -1 || URL.indexOf("/web/") > -1 || URL.indexOf("/api/") > -1){
		
			if( paramVal != null ){
				/*if(paramVal.indexOf("&") > -1){
					paramVal = paramVal.replaceAll("&", "&amp;");	&를 무조건 가장 처음 처리해야 한다. 
				}
				
				if(paramVal.indexOf("\"") > -1){
					paramVal = paramVal.replaceAll("\"", "&quot;");
				}*/
				
				if(paramVal.indexOf("<") > -1){
					paramVal = paramVal.replaceAll("<", "&lt;");
				}
				
				if(paramVal.indexOf(">") > -1){
					paramVal = paramVal.replaceAll(">", "&gt;");
				}
			}
		}
		return paramVal;
	}
	

	public String[] getParameterValues(String name) {
		
		String paramVal[] = super.getParameterValues(name);
		if(URL.indexOf("/admin/") > -1 || URL.indexOf("/web/") > -1){
			
			for(int i=0 ; i<paramVal.length ; i++){
				
				if(paramVal[i].indexOf("&") > -1){
					paramVal[i] = paramVal[i].replaceAll("&", "&amp;");
					System.out.println(paramVal[i]);
				}
				
				if(paramVal[i].indexOf("\"") > -1){
					paramVal[i] = paramVal[i].replaceAll("\"", "&quot;");
					System.out.println(paramVal[i]);
				}
				
				if(paramVal[i].indexOf("<") > -1){
					paramVal[i] = paramVal[i].replaceAll("<", "&lt;");
					System.out.println(paramVal[i]);
				}
				
				if(paramVal[i].indexOf(">") > -1){
					paramVal[i] = paramVal[i].replaceAll(">", "&gt;");
					System.out.println(paramVal[i]);
				}
				
			}
			
		}
		return paramVal;
	}
		    
	public Map getParameterMap() {
	
		return super.getParameterMap();
		
	}
}
