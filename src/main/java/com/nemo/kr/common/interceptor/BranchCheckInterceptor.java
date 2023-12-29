package com.nemo.kr.common.interceptor;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.JsonUtil;
/**
 * 앱 회원 로그인 인터셉터
 * 
 */

public class BranchCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(BranchCheckInterceptor.class);
	
	@Autowired WorkerService workerService;
	
	//예외 url 리스트
	static final String[] APP_EXCLUDE_URL_LIST = {
			
	};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ApiMsg msg = new ApiMsg();
		JsonUtil jsonUtil = new JsonUtil();
		
		String reqUrl = request.getRequestURL().toString();
		
		boolean debug = true;
		//넘어온 변수 체크
		if(debug){
			System.out.println("\n>>>>>>> BranchCheckInterceptor reqUrl : "+reqUrl + ", START TIME : "+DateUtil.getNowDate("yyyy.MM.dd HH:mm:ss"));
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
		
		for (String target : APP_EXCLUDE_URL_LIST) {
			if (reqUrl.indexOf(target) > -1) return true;	//제외할 url은 무조건 true 로 return;			
		}
		
		return true;
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>> END TIME : "+DateUtil.getNowDate("yyyy.MM.dd HH:mm:ss"));
//		super.postHandle(request, response, handler, modelAndView);
	}
}
