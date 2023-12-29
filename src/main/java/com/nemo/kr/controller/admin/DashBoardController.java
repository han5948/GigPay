package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CityDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IssueDTO;
import com.nemo.kr.dto.NoticeDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.NotificationLogDTO;
import com.nemo.kr.dto.OrderStatisticsDTO;
import com.nemo.kr.dto.OutputDTO;
import com.nemo.kr.dto.ProcedureDTO;
import com.nemo.kr.dto.StatusSumDTO;
import com.nemo.kr.dto.UseStatisticsDTO;
import com.nemo.kr.dto.WeatherDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.CityService;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.IssueService;
import com.nemo.kr.service.NoticeService;
import com.nemo.kr.service.NotificationLogService;
import com.nemo.kr.service.NotificationService;
import com.nemo.kr.service.ProcedureService;
import com.nemo.kr.service.StaticService;
import com.nemo.kr.service.WeatherService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.ChannelTalkHashUtil;
//import com.nemo.kr.util.StringUtil;

/**
  * @FileName : DashBoardController.java
  * @Date : 2020. 10. 29. 
  * @작성자 : Na GilJin
  * @프로그램 설명 :
  */
@Controller
@RequestMapping("/admin")
public class DashBoardController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@Autowired NoticeService noticeService;
	@Autowired NotificationService notificationService;
	@Autowired StaticService staticService;
	@Autowired CompanyService companyService;
	@Autowired NotificationLogService notificationLogService;
	@Autowired WeatherService weatherService;
	@Autowired CityService cityService;
	@Autowired IssueService issueService;
	@Autowired WorkerService workerService;
	@Autowired IlboService ilboService;
	@Autowired CodeService codeService;
	@Autowired ProcedureService procedureService;
	
	// Session 설정
	private NoticeDTO setSessionToDTO(HttpSession session, NoticeDTO noticeDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		// Session 설정
		noticeDTO.setAdminLevel(""+ auth_level);
		noticeDTO.setNotice_writer(company_name);
		noticeDTO.setReg_admin(admin_id);
		noticeDTO.setCompany_seq(company_seq);

		if(!noticeDTO.getAdminLevel().equals("0"))
			noticeDTO.setAuth_company_seq(company_seq);

		noticeDTO.setAdminAuth(company_seq);
		
		return noticeDTO;
	}
	
	/**
	  * @Method Name : setSessionToNotificationDTO
	  * @작성일 : 2020. 10. 14.
	  * @작성자 : Na GilJin
	  * @필수 param : auth_level, reg_admin, admin_seq
	  * @선택 param :
	  * @Method 설명 : 로그인한 관리자에게 필요한 알림만 보이게끔 설정
	  */
	private NotificationDTO setSessionToNotificationDTO(HttpSession session, NotificationDTO notificationDTO) throws Exception {
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String admin_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq();
		
		notificationDTO.setAdminSeq(admin_seq);
		notificationDTO.setAdminLevel(""+ auth_level);
		notificationDTO.setReg_admin(admin_id);
		notificationDTO.setNotification_type("W");
		
		return notificationDTO;
	}
	
	/**
	  * @Method Name : setSessionToOrderStatisticsDTO
	  * @작성일 : 2020. 10. 29.
	  * @작성자 : Na GilJin
	  * @필수 param :auth_level, company_seq 
	  * @Method 설명 : 오더종류를 통계내기위해서 로그인한 관리자의 auth_level과 company_seq를 설정함 
	  */
	private OrderStatisticsDTO setSessionToOrderStatisticsDTO(HttpSession session, OrderStatisticsDTO orderStatisticsDTO) throws Exception {
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		orderStatisticsDTO.setAdminLevel("" + auth_level);
		orderStatisticsDTO.setCompany_seq(company_seq);
		
		return orderStatisticsDTO;
	}
	
	/**
	  * @Method Name : setSessionToUseStatisticsDTO
	  * @작성일 : 2020. 10. 29.
	  * @작성자 : Na GilJin
	  * @필수 param : auth_level, company_seq 
	  * @Method 설명 : 공개여부를 통계내기 위해서 로그인한 관리자의 auth_level과 worker_company_seq를 설정함
	  */
	private UseStatisticsDTO setSessionToUseStatisticsDTO(HttpSession session, UseStatisticsDTO useStatisticsDTO) throws Exception {
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		useStatisticsDTO.setAdminLevel("" + auth_level);
		if( useStatisticsDTO.getCompany_flag().equals("0") ) {
			useStatisticsDTO.setCompany_seq(null);
			useStatisticsDTO.setWorker_company_seq(company_seq);
		}else if( useStatisticsDTO.getCompany_flag().equals("1") ){
			useStatisticsDTO.setWorker_company_seq(null);
			useStatisticsDTO.setCompany_seq(company_seq);
		}
		
		return useStatisticsDTO;
	}
	
	/**
	  * @Method Name : setSessionToNotificationLogDTO
	  * @작성일 : 2020. 10. 29.
	  * @작성자 : Na GilJin
	  * @필수 param : admin_id 
	  * @Method 설명 : 
	  */
	private NotificationLogDTO setSessionToNotificationLogDTO(HttpSession session, NotificationLogDTO notificationLogDTO) {
		String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String adminLevel = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level();
		notificationLogDTO.setAdminLevel(adminLevel);
		notificationLogDTO.setMod_admin(admin_id);
		notificationLogDTO.setRead_yn("1");
		
		return notificationLogDTO;
	}

	/**
	  * @Method Name : main
	  * @작성일 : 2020. 10. 8.
	  * @작성자 : Na GilJin
	  * @Method 설명 : main.jsp + dashboard tile 호출 
	  */
	@RequestMapping("/main")
	public String main(HttpSession session, Model model, NoticeDTO noticeDTO) throws Exception {
	    model.addAttribute("htmlHeader", "Dashboard");
	    CodeDTO paramDTO = new CodeDTO();
	    paramDTO.setCode_type("ALR");
	    paramDTO.setUse_yn("1");
	    List<CodeDTO> codeList = codeService.getCodeList(paramDTO);
	    model.addAttribute("codeList", codeList);
	    return "/admin/main.dashboard";
	}
	
	/**
	  * @Method Name : getMainNoticeList
	  * @작성일 : 2020. 10. 8.
	  * @작성자 : Na GilJin
	  * @Method 설명 : 메인페이지에 노출되는 공지사항 보내주는 메소드 (ajax 통신)
	  *              최대 10개, 로그인한 아이디의 지점에 따라 다른 노출 
	  *              ex) 서부지점 아이디는 보여지는지점이 서부지점인것과, 전체인것만 보임
	  */
	@RequestMapping("/main/getMainNoticeList")
	@ResponseBody
	public HashMap<String, Object> getMainNoticeList(HttpSession session, NoticeDTO noticeDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		noticeDTO = setSessionToDTO(session, noticeDTO);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = noticeService.selectListCnt(noticeDTO);
			if( cnt > 10 ) {
				cnt = 10;
			}
			List<NoticeDTO> noticeList = noticeService.selectList(noticeDTO);
			resultMap.put("noticeList", noticeList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("noticeDTO", noticeDTO);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getMainNotificationList
	  * @작성일 : 2020. 10. 13.
	  * @작성자 : Na GilJin
	  * @필수 param : data
	  * @Method 설명 : bootstrap datatable에서 notificationList 받아오는 함수
	  *				 key명을 data로 넘겨야함 			  
	  */
	@RequestMapping("/main/getMainNotificationList")
	@ResponseBody
	public HashMap<String, Object> getMainNotificationList(HttpSession session, NotificationDTO notificationDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		notificationDTO = setSessionToNotificationDTO(session, notificationDTO);

		// 20건씩 가져오기 위해 pageSize set
	    notificationDTO.getPaging().setPageSize(20);
	    
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			int cnt = notificationService.selectListCnt(notificationDTO);
			if( cnt > 0 ) {
				// 20건씩 가져오기 위해 endPage set
				notificationDTO.getPaging().setEndPage(20);
				List<NotificationDTO> notificationList = notificationService.selectList(notificationDTO);
				resultMap.put("notificationList", notificationList);
			}			
			resultMap.put("totalCnt", cnt);
			resultMap.put("notificationDTO", notificationDTO);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getOrderStatisticsList
	  * @작성일 : 2020. 10. 27.
	  * @작성자 : Na GilJin
	  * @필수 param : orderStatisticsDTO.authLevel, orderStatisticsDTO.company_seq
	  * @선택 param : orderStatisticsDTO.srh_type
	  * @Method 설명 : 최근 10일 이내의 일일대장의 오더종류 합계를 날짜별로 표시 해줌
	  * 			  최고관리자 : 모든 지점의 오더종류 합계가 날짜별로 표시 
	  *                         srh_text의 값으로 원하는 지점의 오더종류 합계만 볼 수 있음.
	  *              나머지관리자 : 본인 지점의 오더종류 합계가 날짜별로 표시
	  */
	@RequestMapping("/main/getOrderStatisticsList")
	@ResponseBody
	public HashMap<String, Object> getOrderStatisticsList(HttpSession session, OrderStatisticsDTO orderStatisticsDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		orderStatisticsDTO = setSessionToOrderStatisticsDTO(session, orderStatisticsDTO);
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			List<OrderStatisticsDTO> orderStatisticsList;
			
			//슈퍼관리자 계정으로 로그인시
			if( orderStatisticsDTO.getAdminLevel() == null || orderStatisticsDTO.getAdminLevel().equals("0") 
					|| (orderStatisticsDTO.getAdminLevel().equals("1") && orderStatisticsDTO.getCompany_seq().equals("13"))) {
				
				//selectBox를 전체로 하거나 선택 안했을 때
				if( orderStatisticsDTO.getSrh_text() == null || orderStatisticsDTO.getSrh_text().isEmpty() || orderStatisticsDTO.getSrh_text().equals("0") ) {
					orderStatisticsList = staticService.selectAllOrderStatisticsList(orderStatisticsDTO);
				}else { //selectBox에서 항목을 선택 했을 때
					orderStatisticsDTO.setCompany_seq(orderStatisticsDTO.getSrh_text());
					orderStatisticsList = staticService.selectOrderStatisticsList(orderStatisticsDTO);
				}
			}else {//슈퍼관리자 계정이 아닐 때 해당 회사의 오더종류만 나타남
				orderStatisticsList = staticService.selectOrderStatisticsList(orderStatisticsDTO);
			}
			resultMap.put("orderStatisticsList", orderStatisticsList);
			resultMap.put("orderStatisticsDTO", orderStatisticsDTO);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : readNotification
	  * @작성일 : 2020. 10. 29.
	  * @작성자 : Na GilJin
	  * @필수 param : admin_id  
	  * @Method 설명 : 알림테이블에서 알림을 봤을 때 read_admin = 로그인한 관리자, mod_admin = 로그인한 관리자, read_yn = 1로 만들어줌
	  */
	@RequestMapping("/main/readNotification")
	@ResponseBody
	public HashMap<String, Object> readNotification(HttpSession session, NotificationLogDTO notificationLogDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		notificationLogDTO = setSessionToNotificationLogDTO(session, notificationLogDTO);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			if( notificationLogDTO.getAdminLevel() != null && !notificationLogDTO.getAdminLevel().equals("0")) {
				notificationLogService.updateNotificationLog(notificationLogDTO);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getUseStatisticsList
	  * @작성일 : 2020. 10. 27.
	  * @작성자 : Na GilJin
	  * @필수 param : orderStatisticsDTO.authLevel, orderStatisticsDTO.company_seq
	  * @선택 param : orderStatisticsDTO.srh_type
	  * @Method 설명 : 최근 10일 이내의 일일대장의 오더종류 합계를 날짜별로 표시 해줌
	  *              company_flag : 0-구직자 소속지점 기준 1-구인처 소속지점 기준으로 정렬
	  * 			  최고관리자 : 모든 지점의 오더종류 합계가 날짜별로 표시 
	  *                         srh_text의 값으로 원하는 지점의 오더종류 합계만 볼 수 있음.
	  *              나머지관리자 : 본인 지점의 오더종류 합계가 날짜별로 표시
	  */
	@RequestMapping("/main/getUseStatisticsList")
	@ResponseBody
	public HashMap<String, Object> getUseStatisticsList(HttpSession session, UseStatisticsDTO useStatisticsDTO) throws Exception {
		AdminDTO sessionDTO = ((AdminDTO) session.getAttribute(Const.adminSession));
		
		HashMap<String, Object> resultMap = new HashMap<>();
		useStatisticsDTO = setSessionToUseStatisticsDTO(session, useStatisticsDTO);
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			List<UseStatisticsDTO> useStatisticsList;
			//슈퍼관리자 계정으로 로그인시
			if( useStatisticsDTO.getAdminLevel() == null || useStatisticsDTO.getAdminLevel().equals("0") 
					|| (useStatisticsDTO.getAdminLevel().equals("1") && sessionDTO.getCompany_seq().equals("13"))) {
				//selectBox를 전체로 하거나 선택 안했을 때
				if( useStatisticsDTO.getSrh_text() == null || useStatisticsDTO.getSrh_text().isEmpty() || useStatisticsDTO.getSrh_text().equals("0") ) {
					useStatisticsList = staticService.selectAllUseStatisticsList(useStatisticsDTO);
				}else { //selectBox에서 항목을 선택 했을 때
					if( useStatisticsDTO.getCompany_flag().equals("0") ) {
						useStatisticsDTO.setWorker_company_seq(useStatisticsDTO.getSrh_text());
					}else if( useStatisticsDTO.getCompany_flag().equals("1") ) {
						useStatisticsDTO.setCompany_seq(useStatisticsDTO.getSrh_text());
					}
					useStatisticsList = staticService.selectUseStatisticsList(useStatisticsDTO);
				}
			}else {//슈퍼관리자 계정이 아닐 때 해당 회사의 오더종류만 나타남
				useStatisticsList = staticService.selectUseStatisticsList(useStatisticsDTO);
			}
			resultMap.put("useStatisticsList", useStatisticsList);
			resultMap.put("useStatisticsDTO", useStatisticsDTO);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getWeather
	  * @작성일 : 2020. 11. 27.
	  * @작성자 : Na GilJin
	  * @필수 param : fcst_date, fcst_time, city_code 
	  * @Method 설명 : fcst_date와 fcst_time을 main.jsp에서 받아와 해당 날씨 정보를 불러온다.
	  * 			   해당 날짜에 06시에는 최저기온 값이 나오고 15시에는 최고기온 값이 나온다.
	  */
	@RequestMapping("/main/getWeather")
	@ResponseBody
	public HashMap<String, Object> getWeather(HttpSession session, WeatherDTO weatherDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			//날씨 api 버젼
			weatherDTO.setVersion("2");
			
			//현재시간에 가까운 날씨
			weatherDTO = weatherService.getWeather(weatherDTO);
			resultMap.put("weatherDTO", weatherDTO);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getCityList
	  * @작성일 : 2020. 11. 27.
	  * @작성자 : Na GilJin
	  * @Method 설명 : tbl_city 테이블에서 use_yn = 1 인 city목록을 가져옴.
	  */
	@RequestMapping("/main/getCityList")
	@ResponseBody
	public HashMap<String, Object> getCityList(HttpSession session, CityDTO cityDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		cityDTO.setUse_yn("1");
		cityDTO.setPaging(null);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			List<CityDTO> cityList = cityService.getCityList(cityDTO);
			resultMap.put("cityList", cityList);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
	
	/**
	  * @Method Name : insertIssue
	  * @작성일 : 2020. 12. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 :
	  */
	@RequestMapping("/main/insertIssue")
	@ResponseBody
	public HashMap<String, Object> insertIssue(HttpSession session, IssueDTO issueDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		issueDTO.getPaging().setPageSize(10);
		issueDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		issueDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		issueDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		issueDTO.setAuth_level(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			issueService.insertIssue(issueDTO);
			
			int cnt = issueService.selectIssueListCnt(issueDTO);
			
			if(cnt > 0) { 
				List<IssueDTO> issueList = issueService.selectIssueList(issueDTO);
				
				resultMap.put("issueList", issueList);
			}
			
			resultMap.put("totalCnt", cnt);
			resultMap.put("issueDTO", issueDTO);
		}catch(Exception e) {
			e.printStackTrace();

			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getIssueList
	  * @작성일 : 2020. 12. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 :
	  */
	@RequestMapping("/main/getIssueList")
	@ResponseBody
	public HashMap<String, Object> getIssueList(HttpSession session, IssueDTO issueDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		issueDTO.getPaging().setPageSize(10);
		issueDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		issueDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		issueDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		issueDTO.setAuth_level(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = issueService.selectIssueListCnt(issueDTO);
			
			if(cnt > 0) { 
				List<IssueDTO> issueList = issueService.selectIssueList(issueDTO);
				
				resultMap.put("issueList", issueList);
			}
			
			resultMap.put("totalCnt", cnt);
			resultMap.put("issueDTO", issueDTO);
		}catch(Exception e) {
			e.printStackTrace();

			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getWaitApprovalList
	  * @작성일 : 2021. 1. 4.
	  * @작성자 : Na GilJin
	  * @Method 설명 : 지점별 구직자 승인대기 갯수 리스트 가져오기
	  */
	@RequestMapping("/main/getWaitApprovalList")
	@ResponseBody
	public HashMap<String, Object> getWaitApprovalList(HttpSession session, WorkerDTO workerDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			workerService.selectWaitApprovalCount(workerDTO);
			
			resultMap.put("waitApprovalList", workerService.selectWaitApprovalCount(workerDTO));
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
	
	@RequestMapping("/main/getIlboInfo")
	@ResponseBody
	public HashMap<String, Object> getIlboInfo(HttpSession session, IlboDTO ilboDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			IlboDTO resultDTO = ilboService.selectIlboInfo(ilboDTO);
			
			resultMap.put("ilboDTO", resultDTO);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
	
	/**
	  * @Method Name : getOutputStatusSum
	  * @작성일 : 2021. 3. 24.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 실시간 출역 현황
	
	  */
	@RequestMapping("/main/geStatusSum")
	@ResponseBody
	public HashMap<String, Object> getOutputStatusSum(HttpSession session, IlboDTO ilboDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
			if("13".equals(company_seq)) {
				if("0".equals(ilboDTO.getCompany_seq())) {
					ilboDTO.setCompany_seq("");
				}
			}else {
				ilboDTO.setCompany_seq(company_seq);
			}
			
			OutputDTO outputDTO = ilboService.getOutputStatusSum(ilboDTO);
			resultMap.put("ouputStatus", outputDTO);
			
			StatusSumDTO assignSumDTO = ilboService.getAssignWorkSum(ilboDTO);
			resultMap.put("assignWork", assignSumDTO);
			
			StatusSumDTO workSumDTO = ilboService.getNotAssignWorkSum(ilboDTO);
			resultMap.put("notAssignWork", workSumDTO);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : getCompanyList
	  * @작성일 : 2021. 3. 24.
	  * @작성자 : Na GilJin
	  * @Method 설명 : main page - 회사 선택 selectBox 정보
	  */
	@RequestMapping("/main/getCompanyList")
	@ResponseBody
	public HashMap<String, Object> getCompanyList(){
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			CompanyDTO companyDTO = new CompanyDTO();
			companyDTO.setPaging(null);
			companyDTO.setWhere("AND company_seq != 1");
			companyDTO.setSrh_use_yn("1");
			companyDTO.setSidx("company_seq");
			resultMap.put("companyList", companyService.getCompanyList(companyDTO));
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
	
	@RequestMapping("/main/getProcedureList")
	@ResponseBody
	public HashMap<String, Object> getProcedureList(ProcedureDTO procedureDTO){
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			
			int totalCnt = procedureService.getProcedureTotalCnt(procedureDTO);
			if( totalCnt > 0 ) {
				resultMap.put("procedureList", procedureService.getProcedureList(procedureDTO));
			}
			
			resultMap.put("totalCnt", totalCnt);
			resultMap.put("procedureDTO", procedureDTO);
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
}
