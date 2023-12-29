package com.nemo.kr.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.AdminLogDTO;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.util.CommonUtil;
//import com.nemo.kr.util.StringUtil;
import com.nemo.kr.util.DateUtil;
import com.ilgajaComm.util.StringUtil;

/**
 * 관리자 사이트 로그인 인터셉터
 *
 */
public class AdminLoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginCheckInterceptor.class);

	static final String[] GIGPAY_URL_LIST = {
			"/admin/job/jobList",
			"/admin/taxRate/taxRateList",
			"/admin/categoryList",
			"/admin/eDocumentList"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String reqUrl = request.getRequestURL().toString();
		logger.info("========AdminLoginCheckInterceptor=======================");
		logger.info("URL : {}", reqUrl);

		Enumeration eNames = request.getParameterNames();

		if (eNames.hasMoreElements()) {
			String params = "";
			while (eNames.hasMoreElements()) {
				String name = (String) eNames.nextElement();
				String[] values = request.getParameterValues(name);

				if (values.length > 0) {
					String value = values[0];

					for (int i = 1; i < values.length; i++) {
						value += "," + values[i];
					}

					if (!"".equals(value)) {
						params += "&" + name + "=" + value;
					}
					logger.info("{} [{}]", name, value);

				}
			}
			logger.info("=====================================================");

		}
		HttpSession session = request.getSession();
		if (StringUtils.isNotEmpty(request.getParameter(Const.adminDept1code))) {
			session.setAttribute(Const.adminDept1code, request.getParameter(Const.adminDept1code));
		}else if(reqUrl.contains("/admin/job/jobList")){
			session.setAttribute(Const.adminDept1code, "0");
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception ex) {
		logger.info(">>>>>>>>>>>>> afterCompletion");
	}
	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		logger.info(">>>>>>>>>>>>> postHandle");
	}
}
