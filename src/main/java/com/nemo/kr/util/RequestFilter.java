package com.nemo.kr.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class RequestFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// 요청 래퍼 객체 생성
        HttpServletRequestWrapper requestWrapper = 
                     new ParameterWrapper((HttpServletRequest)request);
        
        requestWrapper.setCharacterEncoding("UTF-8");
        
		
        filterChain.doFilter(requestWrapper,response);
        
		/*
		Enumeration eparam = requestWrapper.getParameterNames ();
	    while (eparam.hasMoreElements()) { 
	        String name = (String) eparam.nextElement(); 
	        String value = requestWrapper.getParameter(name);
        	System.out.println("{parameterName : " + name + "} " + "{parameterValue : " + value + "}");
	    }*/
	   
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

	
}
