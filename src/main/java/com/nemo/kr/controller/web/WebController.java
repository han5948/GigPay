package com.nemo.kr.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AccessDTO;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.OrderDTO;
import com.nemo.kr.dto.OrderWorkDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.AccessService;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.OrderService;
import com.nemo.kr.service.SmsService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

/**
 * @author : nemojjang
 * @date : 2019. 1. 4.
 * @desc : 일가자 웹 컨트롤러
 *
 */
@Controller
@RequestMapping("/web")
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired	CommonService commonService;
	@Autowired	WorkerService workerService;
	@Autowired	OrderService orderService;
	@Autowired	AccessService accessService;
	@Autowired	SmsService smsService;
	@Autowired	AdminService adminService;
	
	@RequestMapping(value = { "", "/" })
	public String index(HttpServletRequest request, Model model, HttpSession session) {
		// 접속자 IP
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}

		String agent = request.getHeader("User-Agent");

		// 브라우져 구분
		String brower = null;

		if (agent != null) {
			if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
				brower = "I";
			} else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
				brower = "A";
			} else {
				brower = "W";
			}
		}

		String a_referer = request.getHeader("referer");

		// 로그 쌓기
		AccessDTO accessDTO = new AccessDTO();
		accessDTO.setUtm_campaign(request.getParameter("utm_campaign"));
		accessDTO.setUtm_source(request.getParameter("utm_source"));
		accessDTO.setUtm_medium(request.getParameter("utm_medium"));
		accessDTO.setUtm_content(request.getParameter("utm_content"));
		accessDTO.setUtm_term(request.getParameter("tm_term"));
		accessDTO.setN_media(request.getParameter("n_media"));
		accessDTO.setN_query(request.getParameter("n_query"));
		accessDTO.setN_rank(request.getParameter("n_rank"));
		accessDTO.setN_ad_group(request.getParameter("n_ad_group"));
		accessDTO.setN_ad(request.getParameter("n_ad"));
		accessDTO.setN_keyword_id(request.getParameter("n_keyword_id"));
		accessDTO.setN_keyword(request.getParameter("n_keyword"));
		accessDTO.setN_campaign_type(request.getParameter("n_campaign_type"));
		accessDTO.setA_ip(ip);
		accessDTO.setA_type("W");
		accessDTO.setA_os(brower);
		accessDTO.setA_agent(agent);
		accessDTO.setA_referer(a_referer);

		accessService.insertInfo(accessDTO);
		System.out.println("=====================");
		System.out.println("         " + accessDTO.getA_seq());
		System.out.println("=====================");

		// 로그정보 셋팅
		session.setAttribute(Const.ACCESS_INFO, accessDTO);

		return "redirect:/web/main";
	}
	
	/**
	 * @author : nemojjang
	 * @date : 2019. 1. 4.
	 * @dec : 메인페이지
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, HttpSession session, Model model) {
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setUse_yn("1");
		codeDTO.setCode_type("400");
		List<CodeDTO> resultList = commonService.getCommonCodeList(codeDTO);
		model.addAttribute("kindList", resultList);
		codeDTO.setCode_type("400, 800, 900");
		List<CodeDTO> jobList = commonService.getCommonCodeList(codeDTO);
		model.addAttribute("jobList", jobList);

		return "/web/main.web";

	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.invalidate();

		return "redirect:/web";
	}

	/**
	 * Work 등록
	 */
//	@RequestMapping("/regWorkProcess")
//	@ResponseBody
//	public Map<String, String> regWorkProcess(HttpServletRequest request, HttpServletResponse response,	HttpSession session, OrderDTO orderDTO, OrderWorkDTO orderWorkDTO) {
//		Map<String, String> resultMap = new HashMap<String, String>();
//
//		try {
//			orderDTO.setReg_admin("work");
//			orderWorkDTO.setReg_admin("work");
//
//			// 로그 정보 ===============================================================
//			String a_seq = "0";
//			try {
//				AccessDTO accessDTO = (AccessDTO) session.getAttribute(Const.ACCESS_INFO);
//				if (accessDTO != null) {
//					a_seq = ((AccessDTO) session.getAttribute(Const.ACCESS_INFO)).getA_seq();
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				a_seq = "0";
//			}
//			orderDTO.setA_seq(a_seq);
//
//			resultMap = orderService.regWorkProcess(orderDTO, orderWorkDTO);
//		
//			try {
//				if("WEB".equals(orderDTO.getOrder_request())) {
//					NotificationDTO notificationDTO = new NotificationDTO();
//				    
//					String tr_msg = "일가자인력이 신청되었습니다.\n" + 
//							"신청내용 확인 후 콜센터(☎1668-1903)\n에서 연락드리겠습니다.";
//					
//					//신청자 SMS
//					SmsDTO smsDTO = new SmsDTO();
//					smsDTO.setTr_msg(tr_msg);
//					smsDTO.setTr_phone(orderDTO.getManager_phone());
//					smsDTO.setTr_etc1("1");
//					smsService.insertInfo(smsDTO);
//					
//			    	notificationDTO.setStatus_flag("ALR010");
//			    	notificationDTO.setOrder_seq(orderDTO.getOrder_seq());
//				    
//				    // 알림 보내는 메소드
//			    	NotificationUtil notificationUtil = new NotificationUtil();
//			    	notificationUtil.ilboAlim(notificationDTO);
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		    
//			String reqUrl = request.getRequestURL().toString();
//			
//			if ( reqUrl.indexOf("localhost") <= -1 && "WEB".equals(orderDTO.getOrder_request())) {
//				if("0000".equals( resultMap.get("code") )){
//					String message = "[★구인접수 WEB]\n"+ orderDTO.getOrder_date()+ "\n"+orderDTO.getEmployer_name() + "\n" + orderDTO.getManager_name() +"\n" + orderDTO.getManager_phone()  ;
//					
//					SmsDTO smsDTO = new SmsDTO();
//					smsDTO.setTr_msg(message);
//					smsDTO.setTr_etc1("1");
//					
//					AdminDTO adminDTO = new AdminDTO();
//					adminDTO.setSrh_company_seq("13");
//					adminDTO.setWork_app_sms("1");
//					adminDTO.setSidx("admin_seq");
//					adminDTO.setPaging(null);
//					adminDTO.setSrh_use_yn("1");
//					
//					List<AdminDTO> smsList = adminService.getAdminList(adminDTO);
//					if(smsList.size() > 0) {
//						for (int j = 0; j < smsList.size(); j++) {
//							AdminDTO smsAdmin = smsList.get(j);
//							if(!StringUtil.isNull(smsAdmin.getAdmin_phone())) {
//								smsDTO.setTr_phone(smsAdmin.getAdmin_phone().replaceAll("-", ""));
//								smsService.insertInfo(smsDTO);
//							}
//						}
//					}
//					// SMS 끝 ===================================================================================================================================================
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("code", "9999");
//			resultMap.put("message", "시스템 오류 입니다.");
//		}
//
//		return resultMap;
//	}

	/**
	 * Worker 등록
	 */
	@RequestMapping("/regWorkerProcess")
	@ResponseBody
	public Map<String, String> regWorkerProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			resultMap.put("code", "0000");

			workerDTO.setCompany_seq("13"); // 잡앤파트너
			workerDTO.setWorker_app_status("0"); // 미설치
			workerDTO.setReg_admin("worker");

			// 로그 정보 ===============================================================
			String a_seq = "0";
			try {
				AccessDTO accessDTO = (AccessDTO) session.getAttribute(Const.ACCESS_INFO);
				if (accessDTO != null) {
					a_seq = ((AccessDTO) session.getAttribute(Const.ACCESS_INFO)).getA_seq();
				}
			} catch (Exception e) {
				// TODO: handle exception
				a_seq = "0";
			}
			// ===================================================================
			workerDTO.setA_seq(a_seq);

			WorkerDTO paramDTO = new WorkerDTO();
			paramDTO.setWorker_phone(workerDTO.getWorker_phone());
			int count = workerService.selectWorkerCount(paramDTO);

			if (count > 0) {
				resultMap.put("code", Const.CODE_USER_0021);
				resultMap.put("message", Const.MSG_USER_0021);

				return resultMap;
			}

			workerService.insertWorker(workerDTO);
			resultMap.put("worker_seq", workerDTO.getWorker_seq()); // 성공

			String reqUrl = request.getRequestURL().toString();
		
			if (reqUrl.indexOf("localhost") <= -1) {
				// [일가자 구직접수] 이름 전화번호
				String message = "[일가자 구직접수]" + workerDTO.getWorker_name() + " / " + workerDTO.getWorker_phone();

				SmsDTO smsDTO = new SmsDTO();
				smsDTO.setTr_msg(message);
				smsDTO.setTr_etc1("2");

				smsDTO.setTr_phone("01090089095");
				smsService.insertInfo(smsDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "9999");
		}

		return resultMap;
	}
}
