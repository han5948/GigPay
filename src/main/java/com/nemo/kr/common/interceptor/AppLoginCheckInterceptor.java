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

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.JsonUtil;
/**
 * 앱 회원 로그인 인터셉터
 * 
 */

public class AppLoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AppLoginCheckInterceptor.class);
	
	@Autowired WorkerService workerService;
	
	// 로그인 예외 url 리스트
	static final String[] APP_EXCLUDE_URL_LIST = {
		"/api/getLoginKey",
		"/api/setLogOut",
		"/api/getAuthNum",
		"/api/setWorkerPassword",
		"/api/setWorkerConsulting",
		"/api/setDeviceInfo",
		"/api/checkDeviceInfo",
		"/api/setManagerApp"};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ApiMsg msg = new ApiMsg();
		JsonUtil jsonUtil = new JsonUtil();
		
		String reqUrl = request.getRequestURL().toString();
		
		boolean debug = true;
		//넘어온 변수 체크
		if(debug){
			System.out.println(">>>>>>>aa AppCheckInterceptor reqUrl : "+reqUrl + ", START TIME : "+DateUtil.getNowDate("yyyy.MM.dd HH:mm:ss"));
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
		
		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		
		ParamValidate pVali = new ParamValidate();
		
		try {
			pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[login_key]", request.getParameter("login_key"));
					
			if(pVali.getErrorCnt() > 0){
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			}
				
			String loginKey = request.getParameter("login_key");
			
			// 회원순번과 로그인키를 셋팅하여 회원정보 취득
				WorkerDTO paramDTO = new WorkerDTO();
				paramDTO.setLogin_key(loginKey);
				paramDTO.setUse_yn("1");
				
				WorkerDTO workerDTO = new WorkerDTO();
				try {
					workerDTO = workerService.getWorkerInfo(paramDTO);	
				} catch (Exception e) {
					// TODO: handle exception
					// 로그인 유도
					throw new Exception(msg.setThrowMsg(Const.CODE_USER_0002, Const.MSG_USER_0002));
				}
							
				if(workerDTO == null){
					// 로그인 유도
					throw new Exception(msg.setThrowMsg(Const.CODE_USER_0002, Const.MSG_USER_2003));
				}
				
				if(!"1".equals(workerDTO.getWorker_app_status()) ){
					throw new Exception(Const.CODE_USER_0016+"!;!"+ Const.MSG_USER_0016);
				}
				
				if(!"1".equals(workerDTO.getWorker_app_use_status()) ){
					throw new Exception(Const.CODE_USER_0017+"!;!"+ Const.MSG_USER_0017);
				}
				
				
				// 로그인한 회원 정보 셋팅
				request.setAttribute(Const.APP_WORKER_INFO, workerDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			logger.error(">> AppCheckInterceptor Exception : "+e.getMessage());
			
			// 로그인 유도 셋팅
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));
			return false;
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
