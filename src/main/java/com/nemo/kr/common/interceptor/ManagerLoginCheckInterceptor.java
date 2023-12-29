package com.nemo.kr.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.ManagerLoginDTO;
//import com.nemo.kr.util.StringUtil;
import com.nemo.kr.util.DateUtil;


/**
 * 관리자 사이트 로그인 인터셉터
 *
 */
public class ManagerLoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ManagerLoginCheckInterceptor.class);

	// 로그인 예외 url 리스트
	static final String[] EXCLUDE_URL_LIST = {
			"/manager/intro",
			"/manager/login",
			"/manager/auth",
			"/manager/password",
			"/manager/getAuthNum",
			"/manager/getIlboExcel",
			"/manager/managerReg",
			"/manager/managerProc",
			"/manager/setManagerApp",
			"/manager/personalTerms",
			"/manager/serviceTerms"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String reqUrl = request.getRequestURL().toString();

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
    
		/** 로그인체크 제외 리스트 */
		for ( String target : EXCLUDE_URL_LIST ) {
			if ( reqUrl.indexOf(target) > -1 ) {
				System.out.println("--------로그인체크 제외-------------------------------------------------------------------------");
				return true;
			}
		}

		HttpSession session = request.getSession();
		ManagerLoginDTO sessionDTO = (ManagerLoginDTO)session.getAttribute(Const.managerSession);
		
		//login session 이 없을 때
		if ( sessionDTO == null || "".equals(sessionDTO.getLogin_id()) ) {
			returnError(request, response);
			System.out.println("--------login session 없음------");
			return false;
		}
    
		if ( !menuAccessDenied(sessionDTO, reqUrl) ) {
			returnError(request, response);
			System.out.println("--------menuAccessDenied------");
			return false;
		}

		return true;
	}

	private boolean menuAccessDenied(ManagerLoginDTO sessionDTO, String reqUrl) throws IOException {
	    //  권한 - 0:전체최고관리자 1:회사최고관리자 2: 회사관리자
	    String manager_phone = StringUtil.isNullToString(sessionDTO.getLogin_id());

	    if ( reqUrl.indexOf("/manager/") > -1 || reqUrl.indexOf("/manager/imgLoad") > -1 || reqUrl.indexOf("/manager/getIlboExcel") > -1) {
	        return true;
	    }
	    
	    return false;
	}

	private void returnError(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String reqUrl = request.getRequestURL().toString();

		if ( request.getHeader("AJAX") != null && "true".equals(request.getHeader("AJAX")) ) {
			response.sendError(901);
		} else if ( reqUrl.indexOf("/manager/popup/") > -1 ) {
			StringBuffer sb = new StringBuffer();
			String message = "다시 로그인 해 주세요.";

			sb.append("<script type=\"text/javascript\">");
			sb.append("  alert('" + message + "');");
			sb.append("  self.close(); opener.location.href='"+request.getContextPath() + "/manager/login'; ");
			sb.append("</script>");

			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb);
			out.flush();
			out.close();
		}else {
			response.sendRedirect(request.getContextPath() + "/manager/login");
		}
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
}
