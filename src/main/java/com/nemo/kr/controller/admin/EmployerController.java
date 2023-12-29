package com.nemo.kr.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.InsuranceDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.excel.EmployerListExcel;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.InsuranceLogService;
import com.nemo.kr.service.LogService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.WorkService;
//import com.nemo.kr.util.StringUtil;


/**
 * 일가자 구인처 관리 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
@Controller
@RequestMapping("/admin")
public class EmployerController {
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);

	@Autowired EmployerService employerService;
	@Autowired WorkService workService;
	@Autowired CompanyService companyService;
	@Autowired ManagerService managerService;
	@Autowired IlboService ilboService;
	@Autowired InsuranceLogService insuranceLogService;
	@Autowired ContractService contractService;
	@Autowired LogService logService;
	@Autowired
	private PlatformTransactionManager transactionManager;
  
	// Session 설정
	private EmployerDTO setSessionToDTO(HttpSession session, EmployerDTO employerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(employerDTO.getCompany_seq());

		// Session 설정
		employerDTO.setAdminLevel(""+ auth_level);
		
		if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
			employerDTO.setSrh_company_seq(company_seq);

			if ( "".equals(get_company_seq) ) {
				employerDTO.setCompany_seq(company_seq);
			}
		}else if(auth_level == 0) {
//			if ( "".equals(get_company_seq) ) {
//				employerDTO.setCompany_seq(company_seq);
//			}
		} 

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			employerDTO.setOwner_id( admin_id);
		}

		employerDTO.setCompany_name(company_name);
		employerDTO.setReg_admin(admin_id);
		
		return employerDTO;
	}
	
	private EmployerDTO setSessionToEmployerDTO(HttpSession session, EmployerDTO employerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		
		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			employerDTO.setOwner_id( admin_id);
		}

		return employerDTO;
	}
  
	@RequestMapping(value="/employerList")
	public String employerList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "구인처관리");
		
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
		
		return "/admin/employerList.admin";
	}

	@RequestMapping(value="/getEmployerList")
	@ResponseBody
	public JSONObject getEmployerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		int auth_level;

		String where    = employerDTO.getWhere();
		String srh_type = employerDTO.getSrh_type();
		String srh_text = employerDTO.getSrh_text();

		// Session 설정
		employerDTO = setSessionToEmployerDTO(session, employerDTO);

		if ( srh_text != null && !"".equals(srh_text) ) {
			//if ( "".equals(where) ) {
				where += " AND ";
			//}

			if ( srh_type != null && !"".equals(srh_type) ) {
				if ( "e.employer_phone".equals(srh_type) ) {
					where += " (";
					where += " fn_decrypt(e.employer_phone1) LIKE '%"+ srh_text +"%'";
					where += " OR fn_decrypt(e.employer_phone2) LIKE '%"+ srh_text +"%'";
					where += " OR fn_decrypt(e.employer_phone3) LIKE '%"+ srh_text +"%'";
					where += " OR fn_decrypt(e.employer_phone4) LIKE '%"+ srh_text +"%'";
					where += " )";
				} else if("e.employer_owner".equals(srh_type) || "e.employer_tel".equals(srh_type) )  { 
					where += "fn_decrypt(" + srh_type +")" +" LIKE '%"+ srh_text +"%' ";
				}else {
					where += srh_type +" LIKE '%"+ srh_text +"%'";
				}
			} else {
				auth_level = StringUtil.isNullToInt(employerDTO.getAdminLevel());
				
				where += " (";
				
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				where += " e.employer_name      LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_owner)  LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_num    LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_email  LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_phone1) LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_phone2) LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_phone3) LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_phone4) LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(e.employer_tel)    LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_addr   LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			employerDTO.setWhere(where);
		}

		int rowCount = employerService.getEmployerTotalCnt(employerDTO);

		employerDTO.getPaging().setRowCount(rowCount);

		List<EmployerDTO> result = employerService.getEmployerList(employerDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
		jsonObj.put("page",    employerDTO.getPaging().getPageNo());                // 현재페이지
		jsonObj.put("total",   employerDTO.getPaging().getTotalPageCnt());          // 총페이지수
		jsonObj.put("records", employerDTO.getPaging().getRowCount());              // 총글목록수
		
		return jsonObj;
	}
	
	// 구인처 정보 (JSP)
	@RequestMapping(value="/getEmployerViewJSP")
	public String getEmployerViewJSP(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO, ModelMap model) throws Exception {
		// Session 설정
		employerDTO = setSessionToDTO(session, employerDTO);
		
		employerDTO = employerService.getEmployerView(employerDTO);

		model.addAttribute("result", employerDTO);

		return "/admin/employerInfo.layout_admin";
	}

	//구인처 정보 (JSON)
	@RequestMapping(value="/getEmployerView")
	@ResponseBody
	public JSONObject getEmployerView(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		// Session 설정
		employerDTO = setSessionToDTO(session, employerDTO);
		
		employerDTO = employerService.getEmployerView(employerDTO);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("result", employerDTO);
		
		return jsonObj;
	}

	// 행 추가
	@RequestMapping(value="/setEmployerCell")
	@ResponseBody
	public JSONObject setEmployerCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		employerDTO = setSessionToDTO(session, employerDTO);
		
		employerService.setEmployerCell(employerDTO);
		
		jsonObj.put("employer_seq", employerDTO.getEmployer_seq());                 // 성공
		
		return jsonObj;
	}
	
	// 변경된 행 저장
	@RequestMapping(value="/setEmployerInfo")
	@ResponseBody
	public JSONObject setEmployerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		if( !"company_name".equals(employerDTO.getCellname()) ) {
			employerDTO = setSessionToDTO(session, employerDTO);
		}
    
		if("?".equals(employerDTO.getEmployer_name()) ){
			employerDTO.setEmployer_name("");
		}

		//사업자 등록 번호 검사
		if(!StringUtil.isNull( employerDTO.getEmployer_num()) && !"?".equals(employerDTO.getEmployer_num()) ){
			EmployerDTO paramDTO = new EmployerDTO();
			paramDTO.setEmployer_num(employerDTO.getEmployer_num());
			List<EmployerDTO> resultList =employerService.chkEmployerNum(paramDTO);
    	
			if(resultList.size() > 0){
				EmployerDTO resultDTO = resultList.get(0);
				jsonObj.put("code", "1111");
				jsonObj.put("message", "[" + resultDTO.getCompany_name() +"] 에서 이미 등록된 사업자 번호 입니다.");
				
				return jsonObj;
			}
		}
   
		if("manager_name".equals(employerDTO.getCellname())) {
			ManagerDTO managerDTO = new ManagerDTO();
			managerDTO.setEmployer_seq(employerDTO.getEmployer_seq());
			managerDTO.setManager_seq(employerDTO.getManager_seq());
			managerDTO.setReg_admin(employerDTO.getReg_admin());
			
			managerService.setProcessEm(managerDTO);
		}
   
		// 노임지급 로그 쌓기
		if("employer_income_code".equals(employerDTO.getCellname()) || "employer_pay_code".equals(employerDTO.getCellname())) {
			CodeLogDTO logDTO = new CodeLogDTO();

			logDTO.setEmployer_seq(employerDTO.getEmployer_seq());
			logDTO.setReg_admin(employerDTO.getReg_admin());
			
			if("employer_income_code".equals(employerDTO.getCellname())) {
				logDTO.setCode_type("300");
				logDTO.setCode_name(employerDTO.getEmployer_income_name());
				logDTO.setCode_value(employerDTO.getEmployer_income_code());
			}else {
				logDTO.setCode_type("200");
				logDTO.setCode_name(employerDTO.getEmployer_pay_name());
				logDTO.setCode_value(employerDTO.getEmployer_pay_code());
			}
			
			if("0".equals(logDTO.getCode_value() )){
				logDTO.setCode_name("초기화");
			}
			
			logService.insertEmployerCodeLog(logDTO);
		}
		
		// 출역일수 제한 수정으로 인한 사회보험 수정 로그 쌓기
		if("limit_count".equals(employerDTO.getCellname())) {
			InsuranceDTO insuranceDTO = new InsuranceDTO();
			
			String insurance_log_type = "";
			
			if(employerDTO.getNational_pension() != null)
				insurance_log_type += employerDTO.getNational_pension().equals("1") ? "국민연금" : "";
			
			if(employerDTO.getHealth_insurance() != null)
				insurance_log_type += employerDTO.getHealth_insurance().equals("1") ? ",건강보험" : "";
			
			if(!insurance_log_type.equals("")) {
	    		if(insurance_log_type.substring(0, 1).equals(",")) {
	    			insurance_log_type = insurance_log_type.substring(1, insurance_log_type.length());
	    		}
			}
			
			insuranceDTO.setUser_type("ADMIN");
			insuranceDTO.setEmployer_seq(employerDTO.getEmployer_seq());
			insuranceDTO.setReg_admin(employerDTO.getReg_admin());
			insuranceDTO.setInsurance_log_type(insurance_log_type);
			insuranceDTO.setSeq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		}
		
		//구인처 정보 수정=======================================================================
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		employerDTO.setReg_admin(admin_id);
		employerService.setEmployerInfo(employerDTO);
		//=================================================================================
    
		jsonObj.put("code", "0000");
		jsonObj.put("message", "변경성공");                 // 성공
    
		return jsonObj;
	}

	// 구인처 등록 폼 저장
	@RequestMapping(value="/setEmployerForm")
	@ResponseBody
	public JSONObject setEmployerForm(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String work_yn = StringUtil.isNullToString(request.getParameter("work_yn"));

		// Session 설정
		employerDTO = setSessionToDTO(session, employerDTO);

		// 행 추가
		employerService.setEmployerCell(employerDTO);

		if ( "Y".equals(work_yn) ) {        // 현장등록에서 구인처를 바로 등록한 경우, 현장 정보 변경
			String work_seq = StringUtil.isNullToString(request.getParameter("work_seq"));

			WorkDTO workDTO = new WorkDTO();

			workDTO.setWork_seq(work_seq);
			workDTO.setCompany_seq(employerDTO.getCompany_seq());
			workDTO.setEmployer_seq(employerDTO.getEmployer_seq());
			workDTO.setReg_admin(employerDTO.getReg_admin());

			workService.setWorkInfo(workDTO);
		}

		jsonObj.put("code", "0000");
		jsonObj.put("employer_seq", employerDTO.getEmployer_seq());                 // 성공
		
		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeEmployerInfo")
	@ResponseBody
	public JSONObject removeEmployerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		employerDTO = setSessionToDTO(session, employerDTO);

		employerService.removeEmployerInfo(employerDTO);

		return jsonObj;
	}

	// 구인자 폰번호 유니크 검색 - 같은 지점 내에서 검색
	// 안쓰는 메소드
	@RequestMapping(value="/chkEmployerPhone")
	@ResponseBody
	public boolean chkWorkerPhone(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		boolean result = false;

		String company_seq = ((AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();

		employerDTO.setCompany_seq(company_seq);

		int iCnt = employerService.chkWorkerPhone(employerDTO);

		if ( iCnt <= 0 ) {
			result =  true;
		}

		return result;
	}
  
	@RequestMapping(value="/employerListExcel")
	@ResponseBody
	public View employerListExcel(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		int auth_level;
		String companyName = "";
		String where    = employerDTO.getWhere();
		String srh_type = employerDTO.getSrh_type();
		String srh_text = employerDTO.getSrh_text();

		// Session 설정
		employerDTO.setSrh_use_yn("1");
		employerDTO.setSidx("e.reg_date desc, e.employer_name");
		employerDTO.setSord("desc");
		employerDTO = setSessionToDTO(session, employerDTO);
    
		if( !employerDTO.getCompany_seq().equals("") || !employerDTO.getCompany_seq().isEmpty() ) {
			CompanyDTO com = new CompanyDTO();
			com.setCompany_seq(employerDTO.getCompany_seq());
			com = companyService.getCompanyInfo(com);
			companyName = com.getCompany_name() + "_";
		}
		
		if ( srh_text != null && !"".equals(srh_text) ) {
			if ( "".equals(where) ) {
				where = " AND ";
			}
			
			if ( srh_type != null && !"".equals(srh_type) ) {
				if ( "e.employer_phone".equals(srh_type) ) {
					where += " (";
					where += " e.employer_phone1 LIKE '%"+ srh_text +"%'";
					where += " OR e.employer_phone2 LIKE '%"+ srh_text +"%'";
					where += " OR e.employer_phone3 LIKE '%"+ srh_text +"%'";
					where += " OR e.employer_phone4 LIKE '%"+ srh_text +"%'";
					where += " )";
				} else {
					where += srh_type +" LIKE '%"+ srh_text +"%'";
				}
			} else {
				auth_level = StringUtil.isNullToInt(employerDTO.getAdminLevel());

				where += " (";
				
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}
				
				where += " e.employer_name      LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_owner  LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_num    LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_email  LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_phone1 LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_phone2 LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_phone3 LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_phone4 LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_tel    LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_addr   LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			employerDTO.setWhere(where);
		}
    
		int rowCount = employerService.getEmployerTotalCnt(employerDTO);
		employerDTO.getPaging().setEndPage(rowCount);
		
		if( rowCount < 1 ) {
			return null;
		}
    
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String fileName = companyName + "구인처관리_" + format.format(today) + ".xlsx";
		List<EmployerDTO> resultList = employerService.getEmployerList(employerDTO);
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("managerDTO", employerDTO);
		model.addAttribute("resultList", resultList);
		
		return new EmployerListExcel();
	}

  	/**
  	  * @throws Exception 
  	 * @Method Name : insertEmployer
  	  * @작성일 : 2020. 11. 12.
  	  * @작성자 : Park YunSoo
  	  * @필수 param : 
  	  * @선택 param :
  	  * @Method 설명 : 구인처 및 현장 등록
  	  */
  	@RequestMapping("insertEmployer")
  	@ResponseBody
  	public JSONObject insertEmployer(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO, WorkDTO workDTO, IlboDTO ilboDTO) throws Exception {
  		JSONObject jsonObj = new JSONObject();
  		employerDTO = setSessionToDTO(session, employerDTO);
  		employerDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
  		
  		// employer_seq가 0이면 insert else update
  		if(employerDTO.getEmployer_seq().equals("0")) {
  			employerService.setEmployerCell(employerDTO);
  		}else {
  			employerService.setEmployerInfo(employerDTO);
  		}
  		
  		// 현장 insert
  		workDTO.setEmployer_seq(employerDTO.getEmployer_seq());
  		workDTO.setReg_admin(employerDTO.getReg_admin());
  		workDTO.setCompany_seq(employerDTO.getCompany_seq());
  		workService.setWorkCell(workDTO);
  		
  		// 매니저DTO insert
  		ManagerDTO managerDTO = new ManagerDTO();
  		managerDTO.setManager_phone(workDTO.getManager_phone());
  		managerDTO.setManager_name(workDTO.getManager_name());
  		managerDTO.setManager_type("O");
  		managerDTO.setManager_memo("");
  		managerDTO.setCompany_seq(employerDTO.getCompany_seq());
  		managerDTO.setReg_admin(employerDTO.getReg_admin());
  		
  		//String parent_company_seq = managerDTO.getCompany_seq();
  		
  		if(workDTO.getManager_overlap_flag().equals("1")) {
  			String parent_company_seq = managerService.selectParentCompanySeq(managerDTO);
  			
  			if(!parent_company_seq.equals(managerDTO.getCompany_seq())) {
  				managerDTO.setParent_company_seq(parent_company_seq);
  				managerDTO.setEmployer_seq(employerDTO.getEmployer_seq());
  				
  				managerService.setManager(managerDTO);
  			}else {
  				managerDTO.setUse_yn("1");
  				managerDTO = managerService.selectManagerInfo(managerDTO);
  			}
  		}else {
  			managerService.setManager(managerDTO);
  		}
  		
  		// Work 테이블 manager_seq 수정
  		WorkDTO updateWorkDTO = new WorkDTO();
  		updateWorkDTO.setWork_seq(workDTO.getWork_seq());
  		updateWorkDTO.setMod_admin(employerDTO.getReg_admin());
  		updateWorkDTO.setManager_seq(managerDTO.getManager_seq());
  		workService.setWorkInfo(updateWorkDTO);
  		
  		// work_owner_id 가져오기
  		updateWorkDTO = workService.getSelectInfo(updateWorkDTO);

  		// ilbo insert
  		ilboDTO.setCompany_seq(employerDTO.getCompany_seq());
  		ilboDTO.setEmployer_seq(employerDTO.getEmployer_seq());
  		ilboDTO.setWork_seq(workDTO.getWork_seq());
  		ilboDTO.setIlbo_work_name(workDTO.getWork_name());
  		ilboDTO.setIlbo_work_arrival(workDTO.getWork_arrival());
  		ilboDTO.setIlbo_work_finish(workDTO.getWork_finish());
  		ilboDTO.setIlbo_work_addr(workDTO.getWork_addr());
  		ilboDTO.setIlbo_work_latlng(workDTO.getWork_latlng());
  		ilboDTO.setIlbo_work_breakfast_yn(workDTO.getWork_breakfast_yn());
  		ilboDTO.setIlbo_work_age_min(workDTO.getWork_age_min());
  		ilboDTO.setIlbo_work_age(workDTO.getWork_age());
  		ilboDTO.setIlbo_work_blood_pressure(workDTO.getWork_blood_pressure());
  		ilboDTO.setIlbo_work_manager_name(workDTO.getWork_manager_name());
  		ilboDTO.setIlbo_work_manager_phone(workDTO.getWork_manager_phone());
  		ilboDTO.setIlbo_worker_name("?");
  		ilboDTO.setReg_admin(employerDTO.getReg_admin());
  		ilboDTO.setWork_owner(updateWorkDTO.getOwner_id());
  		
  		for(int i = 0; i < ilboDTO.getCopy_rows(); i++) {
  			ilboService.insertIlbo(ilboDTO);
  		}
  		
  	    jsonObj.put("result", "success");
  	    
  	    return jsonObj;
  	}
  	
  	@RequestMapping("/employerMerge")
	public String employerMerge(Model model, EmployerDTO employerDTO) {
  		List<EmployerDTO> employerList = employerService.selectInList(employerDTO);
		model.addAttribute("employerList", employerList);

		return "/admin/popup/employerMerge.popup_admin";
	}
  	
	/**
	  * @Method Name : setEmployerMerge
	  * @작성일 : 2021. 7. 16.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 구인처 병합.
	
	  */
	@RequestMapping(value="/setEmployerMerge")
	@ResponseBody
	public JSONObject setEmployerMerge(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		jsonObj = employerService.setEmployerMerge(employerDTO);

		return jsonObj;
	}
	
	@RequestMapping("/updateInsurance")
	@ResponseBody
	public Map<String, Object> updateInsurance(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		employerDTO = setSessionToDTO(session, employerDTO);
		
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		
		String insurance_log_type = "";
		
		if(employerDTO.getEmployer_insurance() != null)
			insurance_log_type += employerDTO.getEmployer_insurance().equals("1") ? "고용보험" : "";
		
		if(employerDTO.getAccident_insurance() != null)
			insurance_log_type += employerDTO.getAccident_insurance().equals("1") ? ",산재보험" : "";
		
		if(employerDTO.getNational_pension() != null)
			insurance_log_type += employerDTO.getNational_pension().equals("1") ? ",국민연금" : "";
		
		if(employerDTO.getHealth_insurance() != null)
			insurance_log_type += employerDTO.getHealth_insurance().equals("1") ? ",건강보험" : "";
		
		if(!insurance_log_type.equals("")) {
    		if(insurance_log_type.substring(0, 1).equals(",")) {
    			insurance_log_type = insurance_log_type.substring(1, insurance_log_type.length());
    		}
		}
		
		insuranceDTO.setUser_type("ADMIN");
		insuranceDTO.setEmployer_seq(employerDTO.getEmployer_seq());
		insuranceDTO.setReg_admin(employerDTO.getReg_admin());
		insuranceDTO.setInsurance_log_type(insurance_log_type);
		insuranceDTO.setSeq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		
		try {
			if(employerDTO.getAdminLevel().equals("0")) {
				employerDTO.setCompany_seq(null);
			}
			
			employerService.setEmployerInfo(employerDTO);
			insuranceLogService.insertInsuranceLog(insuranceDTO);
			
			transactionManager.commit(status);
		}catch(Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}
		return result;
	}

	@RequestMapping("/selectEmployerCodeLogeList")
	@ResponseBody
	public List<CodeLogDTO> selectEmployerCodeLogeList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeLogDTO logDTO) throws Exception {
		List<CodeLogDTO> resultList = logService.selectEmployerCodeLogList(logDTO);
		
		return resultList;
	}
}
