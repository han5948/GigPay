package com.nemo.kr.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
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
import com.nemo.kr.util.CommonUtil;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.JsonUtil;
//import com.nemo.kr.util.StringUtil;
/**
 * 앱 회원 로그인 인터셉터
 * 
 */

public class IlgajaWApiV2Interceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(IlgajaWApiV2Interceptor.class);
	
	@Autowired WorkerService workerService;
	
	// 로그인 예외 url 리스트
	static final String[] APP_EXCLUDE_URL_LIST = {
			"/api/v2/unLogin/"			//unLogin 경로는 로그인 체크 안함.
		};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ApiMsg msg = new ApiMsg();
		JsonUtil jsonUtil = new JsonUtil();
		
		String reqUrl = request.getRequestURL().toString();
		
		boolean debug = true;
		//넘어온 변수 체크
		if(debug){
			System.out.println("\n>========= IlgajaWApiV2Interceptor reqUrl : "+reqUrl + ", START TIME : "+DateUtil.getNowDate("yyyy.MM.dd HH:mm:ss") + "=========");
			
			System.out.println("---------------------------------------------------------------------------------");
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
			if (reqUrl.indexOf(target) > -1) {
				System.out.println("Exclude URL");
				return true;	//제외할 url은 무조건 true 로 return;			
			}
		}
		
		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
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
					//System.out.println("WorkerSeq ::" + workerDTO.getWorker_seq());
				} catch (Exception e) {
					// TODO: handle exception
					// 로그인 유도
					result.put("loginKey", "");
					throw new Exception(msg.setThrowMsg(Const.CODE_USER_0002, Const.MSG_USER_0002));
				}
							
				if(workerDTO == null){
					// 로그인 유도
					result.put("loginKey", "");
					throw new Exception(msg.setThrowMsg(Const.CODE_USER_0002, Const.MSG_USER_2003));
				}
				
				// 로그인 상태가 아니면...
				if(!"1".equals(workerDTO.getWorker_app_status()) ){
					result.put("loginKey",loginKey);
					throw new Exception(Const.CODE_USER_0016+"!;!"+ Const.MSG_USER_0016);
				}
							
				//System.out.println("workerDTO.getWorker_app_use_status() ::" + workerDTO.getWorker_app_use_status());
				if ( "0".equals(workerDTO.getWorker_app_use_status()) ||  "2".equals(workerDTO.getWorker_app_use_status() )) {
					result.put("loginKey",loginKey);
					//자동 로그아웃 되도록 code 는 0002 를 넘김...
					throw new Exception(Const.CODE_USER_0002+"!;!"+ Const.MSG_USER_0017);
				}
				
				if( "3".equals(workerDTO.getWorker_app_use_status() )) {
					result.put("loginKey",loginKey);
					//자동 로그아웃 되도록 code 는 0002 를 넘김...
					throw new Exception(Const.CODE_USER_0023+"!;!"+ Const.MSG_USER_0023);
				}
				
				int sessionTime = Integer.parseInt(DateUtil.twoDateBetweenMinute(workerDTO.getApp_last_date(), DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss")));
				
				//한달 지났으면 session 만료
				if(sessionTime > 43200) {
					result.put("loginKey","");
					throw new Exception(Const.CODE_INVALID_SESSION_PARAMETER + "!;!" + Const.MSG_INVALID_SESSION_PARAMETER);
				}
				
				//하루 loinKey 값을 다시 구해 온다.
				if(sessionTime > 1440) {
					  loginKey = CommonUtil.getAppLoginKey(workerDTO.getWorker_seq());
				      
					  workerDTO.setLogin_key(loginKey);
					  
					  WorkerDTO paramDTO2 = new WorkerDTO();
				      paramDTO2.setWorker_seq(workerDTO.getWorker_seq());
				      paramDTO2.setLogin_key(loginKey);
				      paramDTO2.setApp_last_date("now");
				      
				      workerService.updateWorkerInfo(paramDTO2);
				}
				
				// 로그인한 회원 정보 셋팅
				request.setAttribute(Const.APP_WORKER_INFO, workerDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			logger.error(">> AppCheckInterceptor Exception : "+e.getMessage());
			
			// 로그인 유도 셋팅
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));
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
