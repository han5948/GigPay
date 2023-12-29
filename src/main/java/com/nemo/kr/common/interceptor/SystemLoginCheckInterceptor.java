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
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.util.DateUtil;


/**
 * 관리자 사이트 로그인 인터셉터
 *
 */
public class SystemLoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SystemLoginCheckInterceptor.class);

	// 로그인 예외 url 리스트
	static final String[] EXCLUDE_URL_LIST = {
		"/system/loginForm",
		"/system/loginProc",
		"/system/test",
		"/system/board/boardDetail",
		"/system/board/boardList",
		"/system/board/boardEdit",
		"/system/board/boardGroupList",
		"/system/board/boardReg",
		"/system/board/boardRegProcess",
		"/system/board/boardDetail",
		"/system/board/boardDelProcess",
		"/system/board/boardCheckPass",
		"/system/board/boardCheckAdmin",
		"/system/board/replyRegProcess",
		"/system/board/replyDelProcess",
		"/system/board/replyModifyProcess",
		"/system/board/getReplyInfo",
		"/system/board/getBoardInfo",
		"/system/board/getReplyList",
		"/system/board/fileDown"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String reqUrl = request.getRequestURL().toString();
		//System.out.println(reqUrl);

		logger.info(" >>>>>>>>> SystemLoginCheckInterceptor url : "+reqUrl);

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
		    
		// 로그인체크 제외 리스트
		for ( String target : EXCLUDE_URL_LIST ) {
			if ( reqUrl.indexOf(target) > -1 ) {
				logger.debug("EXCLUDE_URL_LIST===" + target);
				return true;
			}
		}

		HttpSession session = request.getSession();
		AdminDTO adminDTO = (AdminDTO) session.getAttribute(Const.adminSession);

		// login session 이 없을 때
		if ( adminDTO == null || "".equals(adminDTO.getAdmin_id()) ) {
			logger.debug("=======> session 없음");
			returnError(request, response);
			return false;
		}

		return true;
	}

	// 슈퍼 관리자 여부
	private boolean isSuperAdmin(HttpSession session) {
		String adminLevel = ((AdminDTO) session.getAttribute(Const.adminSession)).getAdminLevel();
		boolean isAdmin = "0".equals(adminLevel) ? true : false;

		return isAdmin;
	}

	private void returnError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String reqUrl = request.getRequestURL().toString();

		if ( request.getHeader("AJAX") != null && ("true").equals(request.getHeader("AJAX")) ) {
			response.sendError(901);
		} else if ( reqUrl.indexOf("/admin/popup/") > -1 ) {
			StringBuffer sb = new StringBuffer();
			String message = "다시 로그인 해 주세요.";

			sb.append("<script type=\"text/javascript\">");
			sb.append("  alert('" + message + "');");
			sb.append("  self.close(); opener.location.href='"+request.getContextPath() + "/system/loginForm'; ");
			sb.append("</script>");

			response.setContentType("text/html; charset=utf-8");

			PrintWriter out = response.getWriter();

			out.println(sb);
			out.flush();
			out.close();
		} else {
			//HttpSession session = request.getSession();
			//session.invalidate();
			response.sendRedirect(request.getContextPath() + "/system/loginForm");
		}
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