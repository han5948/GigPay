package com.nemo.kr.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nemo.kr.dto.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nemo.kr.util.IlgajaPayUtil;
import com.ilgajaComm.util.IlgajaPayUtil;
import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.CareerService;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.JobService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.OrderService;
import com.nemo.kr.service.WorkService;
import com.nemo.kr.util.JacksonHelper;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;



/**
  * @FileName : OrderController.java
  * @Project : ilgaja
  * @Date : 2021. 4. 13. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 오더 관리
  */
@Controller
@RequestMapping("/admin")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired OrderService orderService;
	@Autowired EmployerService employerService;
	@Autowired ManagerService managerService;
	@Autowired WorkService workService;
	@Autowired IlboService ilboService; 
	@Autowired AdminService adminService;
	@Autowired JobService jobService;
	@Autowired ContractService contractService;
	@Autowired CareerService careerService;
	@Autowired RestTemplate restTemplate;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Value("#{common['managerBaseUrl']}")
	String managerBaseUrl;
	
	// Session 설정
	private OrderDTO setSessionToDTO(HttpSession session, OrderDTO orderDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		String get_company_seq = StringUtil.isNullToString(orderDTO.getCompany_seq());

		orderDTO.setAdminLevel(auth_level + "");
		orderDTO.setCompany_seq(company_seq);
		orderDTO.setAdmin_id(admin_id);
		
		/*
		    orderDTO.setAdminLevel(""+ auth_level);

		    if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
		      orderDTO.setSrh_company_seq(company_seq);

		      if ( "".equals(get_company_seq) ) {
		          orderDTO.setCompany_seq(company_seq);
		        }
		    }

		  //파트너 일 경우는 파트너 인것만 가져 온다.
		    if(auth_level == 3 ){
		    	orderDTO.setOwner_id( admin_id);
		    }
		    orderDTO.setCompany_name(company_name);
		 */

		orderDTO.setMod_admin(admin_id);

		return orderDTO;
	}

	@RequestMapping(value="/orderList")
	public String workList(HttpSession session, Model model, String work_date) throws JsonProcessingException {
		AdminDTO adminParam = new AdminDTO();
		adminParam.setAdmin_seq(((AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq() );
		AdminDTO adminDTO = adminService.selectInfo(adminParam);
		model.addAttribute("adminSeq", adminDTO.getAdmin_seq());
		model.addAttribute("work_app_sms", adminDTO.getWork_app_sms());
		
		model.addAttribute("htmlHeader" , "오더관리");
		model.addAttribute("managerBaseUrl", managerBaseUrl);
		
		JobDTO jobDTO = new JobDTO();
		jobDTO.setPaging(null);
		jobDTO.setUse_yn("1");
		jobDTO.setDel_yn("0");
		jobDTO.setOrderBy("job_kind, job_order");
		
		List<JobDTO> resultList = jobService.selectJobList(jobDTO);
		model.addAttribute("jobList", resultList);

		/*
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setUse_yn("1");
		codeDTO.setCode_type("400");
		List<CodeDTO> resultList = commonService.getCommonCodeList(codeDTO);
		model.addAttribute("kindList", resultList);
		
		codeDTO.setCode_type("400, 800, 900");
		List<CodeDTO> jobList = commonService.getCommonCodeList(codeDTO);
		model.addAttribute("jobList", jobList);
		*/
		
		if( work_date != null ) {
			model.addAttribute("work_date", work_date);
		}
		
		// 근로계약서 양식 목록
		ContractDTO laborContractDTO = new ContractDTO();
		
		laborContractDTO.setUse_yn("1");
		laborContractDTO.setCategory_code("ELM");
		laborContractDTO.setPaging(null);
		
		List<ContractFileDTO> laborContractList = contractService.selectContractList(laborContractDTO);
		
		String laborOptions = "0:미사용;";
		
		for(int i = 0; i < laborContractList.size(); i++) {
			laborOptions += laborContractList.get(i).getContract_seq() + ":" + laborContractList.get(i).getContract_name() + ";";
		}
		
		laborOptions = laborOptions.substring(0, laborOptions.length() - 1);
		
		model.addAttribute("laborContract", laborOptions);
		// 대리수령동의서 양식 목록
		ContractDTO receiveContractDTO = new ContractDTO();
		
		receiveContractDTO.setUse_yn("1");
		receiveContractDTO.setCategory_code("ERM");
		receiveContractDTO.setPaging(null);
		
		List<ContractFileDTO> receiveContractList = contractService.selectContractList(receiveContractDTO);
		
		String receiveOptions = "0:미사용;";
		
		for(int i = 0; i < receiveContractList.size(); i++) {
			receiveOptions += receiveContractList.get(i).getContract_seq() + ":" + receiveContractList.get(i).getContract_name() + ";";
		}
		
		receiveOptions = receiveOptions.substring(0, receiveOptions.length() - 1);
		
		model.addAttribute("receiveContract", receiveOptions);
		
		CareerDTO careerDTO = new CareerDTO();
		
		careerDTO = careerService.selectCareerOption();
		
		String careerOptions = "";
		
		if(careerDTO.getOption_setting_use_yn_1().equals("1")) 
			//careerOptions += careerDTO.getOption_setting_name_1() + "1:" + careerDTO.getOption_setting_name_1() + ";";
			careerOptions += "1:" + careerDTO.getOption_setting_name_1() + ";";
		
	
		if(careerDTO.getOption_setting_use_yn_2().equals("1"))
			//careerOptions += careerDTO.getOption_setting_name_2() + ":" + careerDTO.getOption_setting_name_2() + ";";
			careerOptions += "2:" + careerDTO.getOption_setting_name_2() + ";";
		
		
		careerOptions += "0:" + careerDTO.getOption_setting_default_name() + ";";
		
		if(careerDTO.getOption_setting_use_yn_3().equals("1"))
			//careerOptions += careerDTO.getOption_setting_name_3() + ":" + careerDTO.getOption_setting_name_3() + ";";
			careerOptions += "3:" + careerDTO.getOption_setting_name_3() + ";";
			
		if(careerDTO.getOption_setting_use_yn_4().equals("1"))
			//careerOptions += careerDTO.getOption_setting_name_4() + ":" + careerDTO.getOption_setting_name_4() + ";";
			careerOptions += "4:" + careerDTO.getOption_setting_name_4() + ";";

		careerOptions = careerOptions.substring(0, careerOptions.length() - 1);
		
		model.addAttribute("careerOptions", careerOptions);
		
		ObjectMapper om = new ObjectMapper();
		String careerDTOJson = om.writeValueAsString(careerDTO);
		//String careerDTOJson = JacksonHelper.toJSON(careerDTO);
		
		model.addAttribute("careerDTO", careerDTOJson);
		
		return "/admin/orderList.admin";
	}

	@RequestMapping(value="/getOrderList")
	@ResponseBody
	public JSONObject getOrderList(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		int auth_level;

		String where    = orderDTO.getWhere();
		String srh_type = orderDTO.getSrh_type();
		String srh_text = orderDTO.getSrh_text();

		// Session 설정
		orderDTO = setSessionToDTO(session, orderDTO);

		
		if ( srh_text != null && !"".equals(srh_text) ) {
			if ( "".equals(where) ) {
				where = " AND ";
			}

			if ( srh_type != null && !"".equals(srh_type) ) {
				where += srh_type +" LIKE '%"+ srh_text +"%'";

			} else {
				auth_level = StringUtil.isNullToInt(orderDTO.getAdminLevel());

				where += " (";
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				where += " e.employer_name         LIKE '%"+ srh_text +"%'";
				where += " OR w.work_name          LIKE '%"+ srh_text +"%'";
				where += " OR w.work_addr          LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_name  LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_phone LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_fax   LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_email LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			orderDTO.setWhere(where);
		}

		int rowCount = orderService.getOrderTotalCnt(orderDTO);

		orderDTO.getPaging().setRowCount(rowCount);

		List<OrderDTO> result = orderService.getOrderList(orderDTO);
		
		int totalWork = orderService.getTotalWork(orderDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             				// 현재페이지의 목록데이터
		jsonObj.put("page",    orderDTO.getPaging().getPageNo());                    // 현재페이지
		jsonObj.put("total",   orderDTO.getPaging().getTotalPageCnt());              // 총페이지수
		jsonObj.put("records", orderDTO.getPaging().getRowCount());                 // 총글목록수
		
		jsonObj.put("totalWork",totalWork);                 // 총글목록수

		return jsonObj;
	}

	@RequestMapping(value="/getOrderWorkList")
	@ResponseBody
	public JSONObject getOrderWorkList(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderWorkDTO orderWorkDTO) throws Exception {
		int auth_level;

		String where    = orderWorkDTO.getWhere();
		String srh_type = orderWorkDTO.getSrh_type();
		String srh_text = orderWorkDTO.getSrh_text();

		if ( srh_text != null && !"".equals(srh_text) ) {
			if ( "".equals(where) ) {
				where = " AND ";
			}

			if ( srh_type != null && !"".equals(srh_type) ) {
				where += srh_type +" LIKE '%"+ srh_text +"%'";

			} else {
				auth_level = StringUtil.isNullToInt(orderWorkDTO.getAdminLevel());

				where += " (";
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				where += " e.employer_name         LIKE '%"+ srh_text +"%'";
				where += " OR w.work_name          LIKE '%"+ srh_text +"%'";
				where += " OR w.work_addr          LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_name  LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_phone LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_fax   LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_email LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			orderWorkDTO.setWhere(where);
		}

		int rowCount = orderService.getOrderWorkTotalCnt(orderWorkDTO);

		orderWorkDTO.getPaging().setRowCount(rowCount);

		List<OrderDTO> result = orderService.getOrderWorkList(orderWorkDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             						// 현재페이지의 목록데이터
		jsonObj.put("page",    orderWorkDTO.getPaging().getPageNo());                    	// 현재페이지
		jsonObj.put("total",   orderWorkDTO.getPaging().getTotalPageCnt());              	// 총페이지수
		jsonObj.put("records", orderWorkDTO.getPaging().getRowCount());                 	// 총글목록수

		return jsonObj;
	}
	@RequestMapping(value="/getEmployerByEmployerNum")
	@ResponseBody
	public EmployerDTO getCommonCodeList(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		orderDTO = setSessionToDTO(session, orderDTO);
		EmployerDTO employerDTO = new EmployerDTO();
		try{
			employerDTO = orderService.getEmployerByEmployerNum(orderDTO);
		}catch (Exception e){
			e.printStackTrace();
		}

		return employerDTO;
	}
	// 행 추가
	@RequestMapping(value="/setOrderCell")
	@ResponseBody
	public JSONObject setOrderCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		orderDTO = setSessionToDTO(session, orderDTO);

		orderService.setOrderCell(orderDTO);

		jsonObj.put("work_seq", orderDTO.getOrder_seq());                             // 성공

		return jsonObj;
	}

	// 변경된 행 저장
	@RequestMapping(value="/setOrderInfo")
	@ResponseBody
	public JSONObject setOrderInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session,OrderDTO orderDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		/*
	    //입력시 - 빼고 입력
	    orderDTO.setOrder_manager_phone( orderDTO.getOrder_manager_phone().replaceAll("-", "") );
	    orderDTO.setOrder_arrival( orderDTO.getOrder_arrival().replaceAll(":", "") );
	    orderDTO.setOrder_finish( orderDTO.getOrder_finish().replaceAll(":", "") );
		 */

		// session company_seq 로 update 되는 부분 제거
		AdminDTO tempAdminDTO = (com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession);
		orderDTO.setAdminLevel(String.valueOf(StringUtil.isNullToInt(tempAdminDTO.getAuth_level(), -1)));
		orderDTO.setAdmin_id(tempAdminDTO.getAdmin_id());
		orderDTO.setMod_admin(tempAdminDTO.getAdmin_id());
		if(orderDTO.getWork_latlng() != null && orderDTO.getWork_latlng().indexOf(">")>0){
			orderDTO.setWork_latlng(orderDTO.getWork_latlng().split(">")[1].split("<")[0]);
		}
		orderService.setOrderInfo(orderDTO);

		jsonObj.put("result", "0000");
		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeOrderInfo")
	@ResponseBody
	public JSONObject removeOrderInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		orderDTO = setSessionToDTO(session, orderDTO);

		orderService.removeOrderInfo(orderDTO);

		return jsonObj;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 7. 5.
	 * @dec    : order 내용
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param orderWorkDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSelectOrder")
	@ResponseBody
	public JSONObject getSelectOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		try {
			OrderDTO resultDTO = orderService.getSelectOrder(orderDTO);

			jsonObj.put("result", "0000");
			jsonObj.put("resultData",    resultDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonObj.put("result", "9999");
		}
		return jsonObj;
	}

	//  로그 검색
	@RequestMapping(value="/getOrderLog")
	@ResponseBody
	public List<OrderLogDTO> getOrderLog(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderLogDTO orderLogDTO) throws Exception {
		orderLogDTO.setPaging(null);
		List<OrderLogDTO> result = orderService.getOrderLog(orderLogDTO);

		return result;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 7. 9.
	 * @dec    : 로그복구
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param orderDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setOrderLogRestore")
	@ResponseBody
	public JSONObject setOrderLogRestore(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderLogDTO orderLogDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		try {
			String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
			orderLogDTO.setReg_admin(admin_id);

			orderService.setOrderLogRestore(orderLogDTO);

			jsonObj.put("result", "0000");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonObj.put("result", "9999");
		}
		return jsonObj;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 7. 18.
	 * @dec    : 오더 완료
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param orderDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setOrderProcess")
	@ResponseBody
	public Map<String, String>  setOrderProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO)  throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		AdminDTO sessionDTO = (AdminDTO) session.getAttribute(Const.adminSession);
		try {
			orderDTO.setReg_admin(sessionDTO.getAdmin_id());
			if(orderDTO.getWork_latlng() != null && orderDTO.getWork_latlng().indexOf(">")>0){
				orderDTO.setWork_latlng(orderDTO.getWork_latlng().split(">")[1].split("<")[0]);
			}
			resultMap = orderService.setOrderProcess(orderDTO, session);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * @author : nemojjang
	 * @date   : 2019. 7. 19.
	 * @dec    : 일보에 저장
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param owkDTO
	 * @return
	 */
	@RequestMapping("/setOrderIlboProcess")
	@ResponseBody
	public Map<String, String> setOrderIlboProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session,  OrderWorkDTO owkDTO)  {
		Map<String, String> resultMap = new HashMap<String, String>();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			AdminDTO sessionDTO = (AdminDTO) session.getAttribute(Const.adminSession);
			
			//1.오더 정보를 구해온다.
			OrderDTO paramDTO = new OrderDTO();
			paramDTO.setOrder_seq(owkDTO.getOrder_seq());

			OrderDTO 			orderDTO 		= orderService.getSelectInfo(paramDTO);			//해당 오더 정보
			OrderWorkDTO 	orderWorkDTO	= orderService.getOrderWorkInfo(owkDTO);			//일 정보
			
			if(orderWorkDTO.getIlbo_state().equals("1")) {
				resultMap.put("code", "9999");
				resultMap.put("message", "이미 적용된 오더입니다.");
				
				transactionManager.rollback(status);
				return resultMap;
			}
			
			ManagerDTO mParamDTO = new ManagerDTO();
			mParamDTO.setManager_seq(orderDTO.getManager_seq());
			
			ManagerDTO managerDTO = managerService.selectManagerInfo(mParamDTO);
			
			IlboDTO ilboDTO = new IlboDTO();
			
			ilboDTO.setManager_seq(managerDTO.getManager_seq());
			ilboDTO.setManager_name(managerDTO.getManager_name());
			ilboDTO.setManager_phone(managerDTO.getManager_phone());
			
			ilboDTO.setCompany_seq( sessionDTO.getCompany_seq());
			
			if(!orderWorkDTO.getWorker_company_seq().isEmpty()) {
				ilboDTO.setWorker_company_seq( orderWorkDTO.getWorker_company_seq() );
				ilboDTO.setWorker_company_name( orderWorkDTO.getWorker_company_name() );
			}else {
				ilboDTO.setWorker_company_name(managerDTO.getCompany_name());
				ilboDTO.setWorker_company_seq(managerDTO.getCompany_seq()); //해당매니져의 지점으로 일보등록
			}
			
			ilboDTO.setWork_company_seq(managerDTO.getCompany_seq());		
			
			//ilboDTO.setOwner_id(managerDTO.getOwner_id());
			
			if(orderDTO.getOrder_owner_id() != null) {
				ilboDTO.setOwner_id(orderDTO.getOrder_owner_id());
				ilboDTO.setCompany_seq(orderDTO.getOrder_company_seq());
			}else {
				ilboDTO.setOwner_id(managerDTO.getOwner_id());
			}
			
			if(managerDTO.getAuth_level().equals("3")) {
				ilboDTO.setWork_owner(managerDTO.getOwner_id());
			}
			
			ilboDTO.setCounselor_rate(managerDTO.getCounselor_rate());
			
			ilboDTO.setOrder_work_seq( orderWorkDTO.getWork_seq());
			ilboDTO.setIlbo_date( orderDTO.getOrder_date() );
			
			ilboDTO.setEmployer_seq( orderDTO.getEmployer_seq());
			ilboDTO.setWork_seq( orderDTO.getWork_seq());
			ilboDTO.setIlbo_work_name( orderDTO.getWork_name());
			ilboDTO.setIlbo_work_arrival( orderDTO.getWork_arrival());
			ilboDTO.setIlbo_work_finish( orderDTO.getWork_finish());
			ilboDTO.setIlbo_work_breaktime(orderDTO.getWork_breaktime());
			ilboDTO.setIlbo_work_order_name(orderDTO.getOrder_request());
			
			
			// 2021-07-06 해당 매니저의 지점으로 들어가기 위한 주석처리
			//ilboDTO.setWorker_company_seq( orderWorkDTO.getWorker_company_seq() );
			//ilboDTO.setWorker_company_name( orderWorkDTO.getWorker_company_name() );
			
			ilboDTO.setIlbo_use_code( orderWorkDTO.getUse_code() );
			ilboDTO.setIlbo_use_name( orderWorkDTO.getUse_name() );

			if(orderDTO.getOrder_request().equals("CALL")) {
				ilboDTO.setIlbo_work_order_code("ORD006");
				ilboDTO.setIlbo_work_order_name("CALL");
			}else if(orderDTO.getOrder_request().equals("WEB")) {
				ilboDTO.setIlbo_work_order_code("ORD009");
				ilboDTO.setIlbo_work_order_name("WEB비회원");
			}else if(orderDTO.getOrder_request().equals("MWEB")) {
				ilboDTO.setIlbo_work_order_code("ORD011");
				ilboDTO.setIlbo_work_order_name("MWEB비회원");
			}
			
			ilboDTO.setIlbo_work_addr( orderDTO.getWork_addr());
			ilboDTO.setWork_sido(orderDTO.getWork_sido());
			ilboDTO.setWork_sigugun(orderDTO.getWork_sigugun());
			ilboDTO.setWork_dongmyun(orderDTO.getWork_dongmyun());
			ilboDTO.setWork_rest(orderDTO.getWork_rest());
			ilboDTO.setIlbo_work_latlng( orderDTO.getWork_latlng());
			ilboDTO.setOutput_status_code("100002");
			ilboDTO.setOutput_status_name("대기");
			ilboDTO.setIlbo_order_code("500000");
			ilboDTO.setIlbo_order_name("순서");
			//ilboDTO.setIlbo_order_code("500018");
			//ilboDTO.setIlbo_order_name("일가자");
			ilboDTO.setIlbo_work_age( orderDTO.getWork_age());
			ilboDTO.setIlbo_work_blood_pressure( orderDTO.getWork_blood_pressure());
			ilboDTO.setIlbo_work_breakfast_yn(orderDTO.getBreakfast_yn());
			ilboDTO.setIlbo_work_manager_name( orderDTO.getWork_manager_name());
			ilboDTO.setIlbo_work_manager_phone( orderDTO.getWork_manager_phone());
			
			ilboDTO.setWork_day(orderDTO.getWork_day());
			ilboDTO.setIlbo_job_comment(orderWorkDTO.getWork_memo());
//			if( "1일".equals(orderDTO.getWork_day()) ) {
//				//ilboDTO.setIlbo_job_comment( "상세 메모 :" + orderWorkDTO.getWork_memo());
//				ilboDTO.setIlbo_job_comment(orderWorkDTO.getWork_memo());
//			}else {
//				ilboDTO.setIlbo_job_comment(orderWorkDTO.getWork_memo() + "\n" + "작업일수 : " + orderDTO.getWork_day());
//			}
			ilboDTO.setIlbo_kind_code( orderWorkDTO.getWork_kind_code());                                                                                  
			ilboDTO.setIlbo_kind_name( orderWorkDTO.getWork_kind_name());  
			ilboDTO.setIlbo_job_detail_code( orderWorkDTO.getWork_job_detail_code());                                                                                  
			ilboDTO.setIlbo_job_detail_name( orderWorkDTO.getWork_job_detail_name());  
			ilboDTO.setIlbo_job_add_code( orderWorkDTO.getWork_job_add_code());                                                                                  
			ilboDTO.setIlbo_job_add_name( orderWorkDTO.getWork_job_add_name());  
						
			// pay 계산=================================================
			
			JobDTO jobDTO = new JobDTO();
			
			jobDTO.setJob_seq(ilboDTO.getIlbo_kind_code());
			
			jobDTO = jobService.selectInfo(jobDTO);
			
			IlgajaPayUtil payUtil = new IlgajaPayUtil(
					orderWorkDTO.getWork_price()
					, ilboDTO.getWork_company_seq()
					, ilboDTO.getWorker_company_seq()
					, managerDTO.getCounselor_rate()
					, ilboDTO.getWork_owner()
					, Double.parseDouble(jobDTO.getJob_work_fee())
					, Double.parseDouble(jobDTO.getJob_worker_fee()));
			
			ilboDTO.setIlbo_unit_price( payUtil.getIlboUnitPrice());
			ilboDTO.setIlbo_fee( payUtil.getIlboFee() );
			ilboDTO.setShare_fee(payUtil.getShareFee());
			ilboDTO.setIlbo_pay(payUtil.getIlboPay() );
			ilboDTO.setCounselor_fee(payUtil.getCounselorFee() );
			ilboDTO.setFee_info(payUtil.getFee_info());
			ilboDTO.setPay_info(payUtil.getPay_info());
			ilboDTO.setWork_fee(payUtil.getWorkFee() + "");
			ilboDTO.setWorker_fee(payUtil.getWorkerFee() + "");
			
			if(orderDTO.getEmployer_income_code() != null && !orderDTO.getEmployer_income_code().equals("0") && !orderDTO.getEmployer_income_code().isEmpty()) {
				ilboDTO.setIlbo_income_code(orderDTO.getEmployer_income_code());
				ilboDTO.setIlbo_income_name(orderDTO.getEmployer_income_name());
			}else {
				ilboDTO.setIlbo_income_code( orderDTO.getPay_type());
				
				String payType = "(노현)";
				if("300006".equals(orderDTO.getPay_type())){
					payType = "(노통)";
				}
				
				ilboDTO.setIlbo_income_name(payType );
			}
			
			if(orderDTO.getEmployer_pay_code() != null && !orderDTO.getEmployer_pay_code().equals("0") && !orderDTO.getEmployer_pay_code().isEmpty()) {
				ilboDTO.setIlbo_pay_code(orderDTO.getEmployer_pay_code());
				ilboDTO.setIlbo_pay_name(orderDTO.getEmployer_pay_name());
			}else {
				ilboDTO.setIlbo_pay_name("(통수)");
				ilboDTO.setIlbo_pay_code("200006");
			}
			
			ilboDTO.setReg_admin( sessionDTO.getAdmin_id() );
			ilboDTO.setLabor_contract_seq(orderDTO.getLabor_contract_seq());
			ilboDTO.setReceive_contract_seq(orderDTO.getReceive_contract_seq());
			ilboDTO.setSign_manager_seq(orderDTO.getSign_manager_seq());
			ilboDTO.setIlbo_career_name(orderWorkDTO.getCareer_name());
			ilboDTO.setIlbo_career_use_seq(orderWorkDTO.getCareer_use_seq());
			ilboDTO.setParking_option(orderDTO.getParking_option());
			ilboDTO.setEmployer_detail(orderDTO.getEmployer_detail());
			
			//작업자 수 만큼 일보에 추가 한다.....=========================================
			int workerCount = Integer.parseInt(orderWorkDTO.getWorker_count());
			
			//일보에 추가			
			for (int i = 0; i < workerCount; i++) {
				ilboService.addOrderToIlboCell(ilboDTO);
			}
			
			//orderWork 수정=============================================================
			OrderWorkDTO wParamDTO = new OrderWorkDTO();
			wParamDTO.setWork_seq(owkDTO.getWork_seq());
			
			wParamDTO.setIlbo_state("1");
			wParamDTO.setMod_admin(sessionDTO.getAdmin_id() );
			orderService.setOrderWorkInfo(wParamDTO);
			
			resultMap.put("code", "0000");
			transactionManager.commit(status);
			
			try {
				NotificationDTO notificationDTO = new NotificationDTO();
				
				notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				notificationDTO.setStatus_flag("ALR022");
				
				NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
				notificationUtil.ilboAlim(notificationDTO);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		} catch(Exception e) {
			System.out.println("===throw");
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "실패 하였습니다.");
			
			transactionManager.rollback(status);
		}

		return resultMap;
	}
	
	@RequestMapping(value="/setOrderWorkInfo")
	@ResponseBody
	public Map<String, String>  setOrderWorkInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderWorkDTO orderWorkDTO)  throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		AdminDTO sessionDTO = (AdminDTO) session.getAttribute(Const.adminSession);
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			orderWorkDTO.setMod_admin(sessionDTO.getAdmin_id());
			
			int careerPrice = 0;
			
			if("career_name".equals(orderWorkDTO.getCellname())) {
				CareerDTO careerDTO = new CareerDTO();
				
				careerDTO = careerService.selectCareerOption();
				
				OrderWorkDTO infoDTO = new OrderWorkDTO();
				
				infoDTO.setWork_seq(orderWorkDTO.getWork_seq());
				
				infoDTO = orderService.getOrderWorkInfo(infoDTO);
				
				int workPrice = Integer.parseInt(infoDTO.getHold_work_price());
				
				// 기본이 아닐 때
				if(orderWorkDTO.getCareer_use_seq() != null && !orderWorkDTO.getCareer_use_seq().equals("0")) {
					if(orderWorkDTO.getCareer_use_seq().equals("1")) {
						orderWorkDTO.setCareer_name(careerDTO.getOption_setting_name_1());
						careerPrice = Integer.parseInt(careerDTO.getOption_setting_price_1());
						careerPrice = workPrice - careerPrice;
					}else if(orderWorkDTO.getCareer_use_seq().equals("2")) {
						orderWorkDTO.setCareer_name(careerDTO.getOption_setting_name_2());
						careerPrice = Integer.parseInt(careerDTO.getOption_setting_price_2());
						careerPrice = workPrice - careerPrice;
					}else if(orderWorkDTO.getCareer_use_seq().equals("3")) {
						orderWorkDTO.setCareer_name(careerDTO.getOption_setting_name_3());
						careerPrice = Integer.parseInt(careerDTO.getOption_setting_price_3());
						careerPrice = workPrice + careerPrice;
					}else if(orderWorkDTO.getCareer_use_seq().equals("4")){
						orderWorkDTO.setCareer_name(careerDTO.getOption_setting_name_4());
						careerPrice = Integer.parseInt(careerDTO.getOption_setting_price_4());
						careerPrice = workPrice + careerPrice;
					}
				}else {
					orderWorkDTO.setCareer_name(careerDTO.getOption_setting_default_name());
					careerPrice = workPrice + 0;
				}
				
				orderWorkDTO.setWork_price(careerPrice + "");
				
				resultMap.put("work_price", careerPrice + "");
			}
			
			orderService.setOrderWorkInfo(orderWorkDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 오더 등록
	 */
	@RequestMapping("/regOrderProcess")
	@ResponseBody
	public Map<String, String> regWorkProcess(HttpServletRequest request, HttpServletResponse response, 	HttpSession session, OrderDTO orderDTO, OrderWorkDTO orderWorkDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			AdminDTO sessionDTO = (AdminDTO) session.getAttribute(Const.adminSession);
			
			orderDTO.setReg_admin(sessionDTO.getAdmin_id());
			orderWorkDTO.setReg_admin(sessionDTO.getAdmin_id());
			
			if(sessionDTO.getAuth_level().equals("3")) {
				orderDTO.setOwner_id(sessionDTO.getAdmin_id());
				orderDTO.setOrder_owner_id(sessionDTO.getAdmin_id());
			}else {
				orderDTO.setOwner_id("company");
			}
			
			orderDTO.setOrder_company_seq(sessionDTO.getCompany_seq());
			resultMap = orderService.regWorkProcess(orderDTO, orderWorkDTO);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "시스템 오류 입니다.");
		}

		return resultMap;
	}

	@RequestMapping(value="/getWorkListByWorkLatlng")
	@ResponseBody
	public List<WorkDTO> getWorkListByWorkLatlng(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrderDTO orderDTO) throws Exception {
		List<WorkDTO> workDTOList = new ArrayList<>();
		try{
			workDTOList = orderService.getWorkListByWorkLatlng(orderDTO);
		}catch (Exception e){
			e.printStackTrace();
		}
		return workDTOList;
	}
}
