package com.nemo.kr.common.interceptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nemo.kr.util.DateUtil;


public class WebLoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(WebLoginCheckInterceptor.class);
	
	//로그인 에외url
	static final String[] EXCLUDE_URL_LIST = {
		"/web/main",
		"/web/loginForm",
		"/web/loginProc",
		"/web/regWorker",
		"/web/regWorkerProcess",
		"/web/regWork",
		"/web/regWorkProcess"};

	@Override
	public boolean preHandle(HttpServletRequest request,	HttpServletResponse response, Object handler) throws Exception {
		String reqUrl = request.getRequestURI().toString();
		
		logger.info(" >>>>>>>>> WebLoginCheckInterceptor url : "+reqUrl);

		boolean debug = true;

		if(debug){
			System.out.println("--------------------------------------------------------------------------------");
	    	System.out.println("URL : "+reqUrl);
		    	  
	    	Enumeration eNames = request.getParameterNames();
	    	
	    	if (eNames.hasMoreElements()) {
	    		String title = "Parameters";
	    		Map entries = new TreeMap();
	    		
	    		while (eNames.hasMoreElements()) {
	    			String name = (String) eNames.nextElement();
	    			String[] values = request.getParameterValues(name);
	    			
		            if (values.length > 0) {
		            	String value = values[0];
		            	
		                for (int i = 1; i < values.length; i++) {
		                   value += "," + values[i];
		                }
		                
		                System.out.println(name+" ["+value+"]");
		            }
	    		}
	    	}
		    
	    	System.out.println("---------------------------------------------------------------------------------");
		}
		   
		for( String target : EXCLUDE_URL_LIST ){
			if(reqUrl.indexOf(target)>-1){
				return true;
			}
		}
		
		HttpSession session = request.getSession();
		/*		CompanyDTO companySession = (CompanyDTO)  session.getAttribute(Const.userSession);
		
		if(companySession ==null || companySession.getUserId().equals("")){
			returnError(request, response);		
			return false;
		}*/

		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>> END TIME : "+DateUtil.getNowDate("yyyy.MM.dd HH:mm:ss"));
//		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
	}
	private void returnError(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(request.getHeader("AJAX") != null && ("true").equals(request.getHeader("AJAX"))){
			response.sendError(901);
		}else{
			//HttpSession session = request.getSession();
			//session.invalidate();
			response.sendRedirect(request.getContextPath() + "/web/main");			
		}
	}
}
