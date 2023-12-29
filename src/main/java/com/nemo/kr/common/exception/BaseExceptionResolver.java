package com.nemo.kr.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.nemo.kr.util.CommonUtil;

public class BaseExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(BaseExceptionResolver.class);
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex ) {
    	String url = request.getServletPath();
    	ModelAndView model = new ModelAndView();
		
		logger.error("BaseExceptionResolver >>>>>>>> url : "+url);						
		logger.error(">>>>>>>>>>  ERROR     [BaseExceptionResolver ]  "+CommonUtil.getStackTrace(ex));
		
		if(url.indexOf("/mw") > -1){
			model.setViewName("/mw/error.mw");			
		}else if(url.indexOf("/admin") > -1){
			model.setViewName("/admin/error.admin");			
		}
		
    	 request.setAttribute("exception",ex);
    	 return model;
    }
}
