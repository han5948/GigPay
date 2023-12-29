package com.nemo.kr.controller.admin;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.SerializationUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.ilgajaComm.dto.CommIlboDTO;
import com.ilgajaComm.service.CommIlboService;
import com.ilgajaComm.util.DateUtil;
import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.column.LimitCount;
import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.exception.FutureIlboException;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.AutoIlboDTO;
import com.nemo.kr.dto.CareerDTO;
import com.nemo.kr.dto.CareerLogDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.EvaluateDTO;
import com.nemo.kr.dto.IlboContractLogDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.IlboSettingDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.PriceLogDTO;
import com.nemo.kr.dto.RatingDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.excel.LaborExpensesExcel;
import com.nemo.kr.service.AutoSettingService;
import com.nemo.kr.service.BranchService;
import com.nemo.kr.service.CareerLogService;
import com.nemo.kr.service.CareerService;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.service.DeductibleService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.EvaluateService;
import com.nemo.kr.service.IlboContractLogService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.IlboSettingService;
import com.nemo.kr.service.LogService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.RatingService;
import com.nemo.kr.service.TaxRateService;
import com.nemo.kr.service.WorkService;
import com.nemo.kr.service.WorkerService;
//import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.JsonUtil;
import com.nemo.kr.util.MessageUtil;
import com.nemo.kr.util.NotificationUtil;
import com.nemo.kr.util.jqGridUtil;

/**
 * 일가자 관리자 관리 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
@Controller
@RequestMapping("/admin")
public class ilboController {
	private static final Logger logger = LoggerFactory.getLogger(ilboController.class);

	@Autowired IlboService ilboService;
	@Autowired WorkerService workerService;
	@Autowired ManagerService managerService;
	@Autowired RatingService ratingService;
	@Autowired EvaluateService evaluateService;
	@Autowired IlboSettingService ilboSettingService;
	@Autowired LogService logService;
	@Autowired MessageUtil messageUtil;
	@Autowired WorkService workService;
	@Autowired BranchService branchService;
	@Autowired CommIlboService commIlboService;
	@Autowired ContractService contractService;
	@Autowired CareerService careerService;
	@Autowired CareerLogService careerLogService;
	@Autowired EmployerService employerService;
	@Autowired IlboContractLogService ilboContractLogService;
	@Autowired TaxRateService taxRateService;
	@Autowired DeductibleService deductibleService;
	@Autowired AutoSettingService autoSettingService; 
	
	@Autowired RestTemplate restTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	// Session 설정
	private IlboDTO setSessionToDTO(HttpSession session, IlboDTO ilboDTO) throws Exception {
		// Session 정보
		int auth_level      		= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name 	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     		= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(ilboDTO.getCompany_seq());
		String auto_company_use_yn = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuto_company_use_yn();
		
		// Session 설정
		ilboDTO.setAdminLevel(""+ auth_level);
    
		if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
			ilboDTO.setSrh_company_seq(company_seq);

			if ( "".equals(get_company_seq) ) {
				ilboDTO.setCompany_seq(company_seq);
			}
		}

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			ilboDTO.setOwner_id( admin_id);
		}
		
		ilboDTO.setReg_admin(admin_id);
		ilboDTO.setMod_admin(admin_id);
		ilboDTO.setAuto_company_use_yn(auto_company_use_yn);
		
		return ilboDTO;
	}
	
	
	private IlboSettingDTO setIlboSettingDTO(HttpSession session, IlboSettingDTO ilboSettingDTO) throws Exception {
		ilboSettingDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		ilboSettingDTO.setIlbo_date("1");
		ilboSettingDTO.setWorker_company_name("1");
		ilboSettingDTO.setWorker_owner("1");
		ilboSettingDTO.setIlbo_worker_view("1");
		ilboSettingDTO.setWorker_ilbo("1");
		ilboSettingDTO.setIlbo_worker_name("1");
		ilboSettingDTO.setIlbo_worker_feature("1");
		ilboSettingDTO.setIlbo_worker_memo("1");
		ilboSettingDTO.setIlbo_assign_type("1");
		ilboSettingDTO.setIlbo_worker_info("1");
		ilboSettingDTO.setIlbo_worker_status_info("1");
		ilboSettingDTO.setIlbo_memo("1");
		ilboSettingDTO.setWork_company_name("1");
		ilboSettingDTO.setWork_owner("1");
		ilboSettingDTO.setIlbo_employer_view("1");
		ilboSettingDTO.setEmployer_ilbo("1");
		ilboSettingDTO.setEmployer_name("1");
		ilboSettingDTO.setIlbo_employer_feature("1");
		ilboSettingDTO.setAddr_edit("1");
		ilboSettingDTO.setAddr_location("1");
		ilboSettingDTO.setIlbo_work_info("1");
		ilboSettingDTO.setIlbo_work_order_name("1");
		ilboSettingDTO.setIlbo_use_info("1");
		ilboSettingDTO.setIlbo_status_info("1");
		ilboSettingDTO.setIlbo_work_breakfast_yn("1");
		ilboSettingDTO.setIlbo_work_age_min("1");
		ilboSettingDTO.setIlbo_work_age("1");
		ilboSettingDTO.setIlbo_work_blood_pressure("1");
		ilboSettingDTO.setManager_name("1");
		ilboSettingDTO.setManager_phone("1");
		ilboSettingDTO.setIlbo_work_manager_name("1");
		ilboSettingDTO.setIlbo_work_manager_phone("1");
		ilboSettingDTO.setIlbo_work_arrival("1");
		ilboSettingDTO.setIlbo_work_finish("1");
		ilboSettingDTO.setWork_ilbo("1");
		ilboSettingDTO.setWork_name("1");
		ilboSettingDTO.setIlbo_kind_name("1");
		ilboSettingDTO.setIlbo_job_comment("1");
		ilboSettingDTO.setIlbo_chief_memo("1");
		ilboSettingDTO.setIlbo_income_name("1");
		ilboSettingDTO.setIlbo_income_time("1");
		ilboSettingDTO.setIlbo_unit_price("1");
		ilboSettingDTO.setIlbo_fee("1");
		ilboSettingDTO.setShare_fee("1");
		ilboSettingDTO.setIlbo_deduction("1");
		ilboSettingDTO.setIlbo_pay("1");
		ilboSettingDTO.setIlbo_pay_name("1");
		ilboSettingDTO.setIlbo_pay_time("1");
		ilboSettingDTO.setIlbo_income_memo("1");
		ilboSettingDTO.setEmployer_rating("1");
		ilboSettingDTO.setEvaluate_grade("1");
		ilboSettingDTO.setUse_yn("1");
		ilboSettingDTO.setReg_date("1");
		ilboSettingDTO.setReg_admin("1");
		ilboSettingDTO.setOwner_id("1");
		ilboSettingDTO.setSetting_reg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		ilboSettingDTO.setSetting_mod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		
		ilboSettingDTO.setCompany_name("1");
		ilboSettingDTO.setIlbo_work_breaktime("1");
		ilboSettingDTO.setIlbo_career_name("1");
		ilboSettingDTO.setReceive_contract_seq("1");
		ilboSettingDTO.setLabor_contract_seq("1");
		ilboSettingDTO.setWork_fee("1");
		ilboSettingDTO.setWorker_fee("1");
		ilboSettingDTO.setFee_info("1");
		ilboSettingDTO.setPay_info("1");
		ilboSettingDTO.setManager_rating("1");
		ilboSettingDTO.setSurrogate("1");
		ilboSettingDTO.setLabor_contract("1");
		ilboSettingDTO.setDeductible_sum("1");
		ilboSettingDTO.setDeductible_info("1");
		ilboSettingDTO.setWages_received("1");
		
		ilboSettingDTO.setEmployer_detail("1");
		ilboSettingDTO.setParking_option("1");
		
		return ilboSettingDTO;
	}

	public String getLaborOptions() {
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
		return laborOptions;
	}
	
	public String getReceiveOptions() {
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
		return receiveOptions;
	}
	
	// 일일대장
	@RequestMapping(value="/ilboList")
	public String ilboList(HttpSession session, Model model) throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
		model = getIlboWidth(session, model);
		
		model.addAttribute("htmlHeader" 	, "일일대장");
		model.addAttribute("companySeq" 	, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		model.addAttribute("shareYn" 			, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getShare_yn());
		model.addAttribute("authLevel" 		, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		model.addAttribute("laborContract", getLaborOptions());
		model.addAttribute("receiveContract", getReceiveOptions());
		
		CareerDTO careerDTO = new CareerDTO();
		
		careerDTO = careerService.selectCareerOption();
		
		String careerOptions = "";
		
		if(careerDTO.getOption_setting_use_yn_1().equals("1")) 
			careerOptions += careerDTO.getOption_setting_name_1() + ":" + careerDTO.getOption_setting_name_1() + ";";
	
		if(careerDTO.getOption_setting_use_yn_2().equals("1"))
			careerOptions += careerDTO.getOption_setting_name_2() + ":" + careerDTO.getOption_setting_name_2() + ";";
		
		careerOptions += "0:" + careerDTO.getOption_setting_default_name() + ";";
		
		if(careerDTO.getOption_setting_use_yn_3().equals("1"))
			careerOptions += careerDTO.getOption_setting_name_3() + ":" + careerDTO.getOption_setting_name_3() + ";";
			
		if(careerDTO.getOption_setting_use_yn_4().equals("1"))
			careerOptions += careerDTO.getOption_setting_name_4() + ":" + careerDTO.getOption_setting_name_4() + ";";

		careerOptions = careerOptions.substring(0, careerOptions.length() - 1);
		
		model.addAttribute("careerOptions", careerOptions);
		
		ObjectMapper om = new ObjectMapper();
		String careerDTOJson = om.writeValueAsString(careerDTO);
		//String careerDTOJson = JacksonHelper.toJSON(careerDTO);
		
		model.addAttribute("careerDTO", careerDTOJson);
		
		return "/admin/ilboList.admin";
	}
  
	/**
	* @Method Name : getIlboWidth
	* @작성일 : 2020. 11. 18.
	* @작성자 : Jangjaeho
	* @변경이력 : 
	* @Method 설명 : setting 에 설정되어 있는 보기 내용만 출력
	*/
	private Model getIlboWidth(HttpSession session, Model model ) throws IllegalArgumentException, IllegalAccessException {
		IlboSettingDTO ilboSettingDTO = new IlboSettingDTO();
	  	
	  	ilboSettingDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
	  	IlboSettingDTO ilboSettingWidthDTO = ilboSettingService.selectSettingWidth(ilboSettingDTO);
		ilboSettingDTO = ilboSettingService.selectSettingInfo(ilboSettingDTO);
		
		if(ilboSettingWidthDTO == null) {
			IlboSettingDTO emptyIlboSettingDTO = new IlboSettingDTO();
			IlboSettingDTO paramDTO = new IlboSettingDTO();
			
			paramDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			try {
				setIlboSettingDTO(session, emptyIlboSettingDTO);
				
				ilboSettingService.insertIlboSetting(emptyIlboSettingDTO);
				
				ilboSettingWidthDTO = ilboSettingService.selectSettingWidth(emptyIlboSettingDTO);
				ilboSettingDTO = ilboSettingService.selectSettingInfo(paramDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Map<String, String> testMap = jqGridUtil.converObjectToMap(ilboSettingWidthDTO);
		
		int width = 0;
		
		for(String key : testMap.keySet()) {
			if(!key.equals("serialVersionUID") && 
				!key.equals("setting_seq") &&
				!key.equals("admin_seq") &&
				!key.equals("setting_use_yn") &&
				!key.equals("setting_del_yn") &&
				!key.equals("setting_reg_date") &&
				!key.equals("setting_reg_admin") &&
				!key.equals("setting_mod_date") &&
				!key.equals("setting_mod_admin") &&
				!key.substring(key.length() - 5, key.length()).equals("width")) {
				
				if(testMap.get(key).equals("1")) {
					width += Integer.parseInt(testMap.get(key + "_width"));
				}
			}
		}
		
		if(ilboSettingDTO != null) {
			Object obj = ilboSettingDTO;
			String fieldName = "";
			
			for(Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				
				
				Object value = field.get(obj);
				
				if((!field.getName().equals("setting_use_yn") && 
					!field.getName().equals("setting_seq") &&
					!field.getName().equals("setting_del_yn")) && value.equals("0") && value != null) {
					fieldName += field.getName() + ",";
				}
			}
			
			model.addAttribute("fieldName", fieldName);
			model.addAttribute("smallWidth", width);
		}else {
			ilboSettingDTO = new IlboSettingDTO();

			try {
				setIlboSettingDTO(session, ilboSettingDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("ilboSettingDTO", ilboSettingDTO);
		
		return model;
	}

	// 구직대장
	@RequestMapping(value="workerIlbo")
	public String workerIlbo(HttpServletRequest request, HttpSession session, Model model) throws IllegalArgumentException, IllegalAccessException {
		IlboSettingDTO ilboSettingDTO = new IlboSettingDTO();

		model = getIlboWidth(session, model);

		model.addAttribute("htmlHeader" 	, "구직대장");
		model.addAttribute("companySeq" , ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		model.addAttribute("shareYn" 		, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getShare_yn());
		model.addAttribute("authLevel" 	, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		model.addAttribute("laborContract", getLaborOptions());
		model.addAttribute("receiveContract", getReceiveOptions());

		return "/admin/ilboList.admin";
	}

	// 구인대장
	@RequestMapping(value="workIlbo")
	public String workIlbo(HttpServletRequest request, HttpSession session, Model model, IlboDTO ilboDTO) throws IllegalArgumentException, IllegalAccessException {
		model = getIlboWidth(session, model);
	  
		String srh_type = request.getParameter("srh_ilbo_type");
		  
		if("i.work_seq".equals(srh_type)){
			model.addAttribute("htmlHeader" , "현장대장");
		}else{
			model.addAttribute("htmlHeader" , "구인대장");
		}
		
		model.addAttribute("companySeq" , ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		model.addAttribute("shareYn" , ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getShare_yn());
		model.addAttribute("authLevel" , ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		model.addAttribute("laborContract", getLaborOptions());
		model.addAttribute("receiveContract", getReceiveOptions());
		
		return "/admin/ilboList.admin";
	}
	
	
	/**
	* @Method Name : getIlboList
	* @작성일 : 2020. 10. 30.
	* @작성자 : Jangjaeho
	* @변경이력 : 
	* @Method 설명 : 일보리스트 일일대장에서 요청 됨
	*/
	@RequestMapping(value="/getIlboList")
	@ResponseBody
	public JSONObject getIlboList(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		String where     	= ilboDTO.getWhere();
		String srh_type 		= ilboDTO.getSrh_type();
		String srh_text  	= ilboDTO.getSrh_text();
		String srh_seq  		= ilboDTO.getSrh_seq();

		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);

		if ( "i.worker_seq".equals(srh_type) || "i.employer_seq".equals(srh_type) || "i.work_seq".equals(srh_type) ) {
			where += " AND "+ srh_type +" = '"+ srh_seq +"'";
		} else if ( srh_text != null && !"".equals(srh_text) ) {
			//if ( "".equals(where) ) {
				where += " AND ";
			//}

			if ( !StringUtil.isNull(srh_type) ) {
				if ( "ilbo_work".equals(srh_type) ) {
					where += "(e.employer_name LIKE '%"+ srh_text +"%' OR i.ilbo_work_name LIKE '%"+ srh_text +"%')";
				} else {
					if("i.ilbo_worker_name".equals(srh_type)) {
						where += "fn_decrypt(i.ilbo_worker_name)	LIKE '%"+ srh_text +"%'";
					}else {
						where += srh_type +" LIKE '%"+ srh_text +"%'";
					}
				}
			} else {
				where += " (";
				where += " i.work_company_name        	LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(i.ilbo_worker_name)	LIKE '%"+ srh_text +"%'";
				where += " OR e.employer_name		LIKE '%"+ srh_text +"%'";
				where += " OR i.ilbo_work_name		LIKE '%"+ srh_text +"%'";
				where += " )";
			}
		}
	  	
		if("0".equals(ilboDTO.getSrh_worker_request())) { //구직신청자 미포함
			where += " AND (i.worker_seq = 0 OR (i.output_status_code != '100002' AND i.output_status_code != '100020' AND i.output_status_code != '100013'))";
		}
		
		if("1".equals(ilboDTO.getSrh_option())) {	// 배정완료
			where += " AND i.work_seq != '0' AND i.worker_seq != '0'";  
		}else if("2".equals(ilboDTO.getSrh_option())) {	// 미배정현장
			where += " AND i.work_seq != '0' AND (i.worker_seq = '0' OR i.output_status_code IN ('100009', '100010'))" ;
		}else if("3".equals(ilboDTO.getSrh_option())) {	// 미배정 구직자
			where += " AND i.work_seq = '0' AND i.worker_seq != '0'" ;
		}else if("4".equals(ilboDTO.getSrh_option())) {	// 현장
			where += " AND i.work_seq != '0'";
		}else if("5".equals(ilboDTO.getSrh_option())) { // 구직자
			where += " AND i.worker_seq != '0'";
		} 
	   
		ilboDTO.setWhere(where);

		if("ilbo_worker_info".equals(ilboDTO.getSidx())) {		//예약 , 대기 ....
			ilboDTO.setSidx("output_status_code");
		}else if("ilbo_use_info".equals(ilboDTO.getSidx())) { // 공개 여부
			ilboDTO.setSidx("ilbo_use_code");
		}else if("ilbo_work_info".equals(ilboDTO.getSidx())) {	//1차 2차
			ilboDTO.setSidx("ilbo_order_code");
		}else if("ilbo_status_info".equals(ilboDTO.getSidx())) {	//1차 2차
			ilboDTO.setSidx("ilbo_status_code");
		}else if("auto_status_info".equals(ilboDTO.getSidx())) {
			ilboDTO.setSidx("auto_status");
		}
	  
		int rowCount = ilboService.getIlboTotalCnt(ilboDTO);
	  
		ilboDTO.getPaging().setRowCount(rowCount);

		List<IlboDTO> result = ilboService.getIlboList(ilboDTO);
		
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             					// 현재페이지의 목록데이터
		jsonObj.put("page",    ilboDTO.getPaging().getPageNo());                   		// 현재페이지
		jsonObj.put("total",   ilboDTO.getPaging().getTotalPageCnt());              	// 총페이지수
		jsonObj.put("records", ilboDTO.getPaging().getRowCount());                  	// 총글목록수

		return jsonObj;
	}

	// Excel 다운로드
	@RequestMapping(value="/getIlboExcel")
	public View getIlboExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, IlboDTO ilboDTO) throws Exception {
		// Session 설정
		setSessionToDTO(session, ilboDTO);

		String start_ilbo_date = ilboDTO.getStart_ilbo_date();
		String end_ilbo_date   = ilboDTO.getEnd_ilbo_date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();

		int iYear, iMonth, iDay;
		iYear  = Integer.parseInt(end_ilbo_date.substring(0, 4));
		iMonth = Integer.parseInt(end_ilbo_date.substring(5, 7));
		iDay   = Integer.parseInt(end_ilbo_date.substring(8, 10));

		String sCaseWhere = "", sSumWhere = "";

		int days = (int) DateUtil.getDateDiff(start_ilbo_date, end_ilbo_date, "yyyy-MM-dd");

		List dayList = new ArrayList();
		for ( int i = days; i >= 0; i-- ) {
			c.clear();
			c.set(iYear, iMonth - 1, iDay - i);
			java.util.Date d = c.getTime();
			String thedate = sdf.format(d);

			sCaseWhere += "CASE WHEN a.ilbo_day = '"+ thedate +"' THEN COUNT(*) ELSE 0 END AS day_"+ ((days + 1) - i) +", ";
			sSumWhere  += "SUM(b.day_"+ ((days + 1) - i) +") AS day_"+ ((days + 1) - i) +", ";

			dayList.add(days - i, thedate);
		}
		ilboDTO.setWhere(sCaseWhere);
		ilboDTO.setSord(sSumWhere);

		// 일력소 정보
		IlboDTO info = ilboService.getIlboExcelInfo(ilboDTO);
		info.setSrh_type(ilboDTO.getSrh_type());
		info.setStart_ilbo_date(start_ilbo_date);
		info.setEnd_ilbo_date(end_ilbo_date);

		model.addAttribute("filename", "["+ start_ilbo_date +"~"+ end_ilbo_date +"]노무비대장_"+ info.getEmployer_name() +"_"+ info.getWork_name() +".xlsx");
		model.addAttribute("subject", "출역대장(임금+소개요금)");
		model.addAttribute("info", info);
		model.addAttribute("list", ilboService.getIlboExcel(ilboDTO));
		model.addAttribute("dayList", dayList);

		return new LaborExpensesExcel();
	}

	// 행 추가
	@RequestMapping(value="/setIlboCell")
	@ResponseBody
	public JSONObject setIlboCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name 	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     		= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		int auth_level      		= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
    
		ilboDTO.setCompany_seq(company_seq);
		ilboDTO.setCompany_name(company_name);
		ilboDTO.setWorker_company_seq(company_seq);
		ilboDTO.setWorker_company_name(company_name);
		ilboDTO.setWork_company_seq(company_seq);
		ilboDTO.setWork_company_name(company_name);
		ilboDTO.setReg_admin(admin_id);
		ilboDTO.setMod_admin(admin_id);

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			ilboDTO.setOwner_id( admin_id);
		}
    
		ilboService.setIlboCell(ilboDTO);

		jsonObj.put("ilbo_seq", ilboDTO.getIlbo_seq());                              // 성공

		return jsonObj;
	}

	// 변경된 행 저장
	@RequestMapping(value="/setIlboInfo")
	@ResponseBody
	public JSONObject setIlboInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO, CodeLogDTO logDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);
		String workerSeq = "";
		
		// 일보 정보 바꿀 때 근로계약서에 구직자만 서명이 완료되어 있는 상태면 정보 수정 안되게
		IlboDTO ilboSignDTO = new IlboDTO();
		ilboSignDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
		ilboSignDTO = ilboService.selectIlboSignInfo(ilboSignDTO);
		
		// 펑크, 째앰, 대마, 휴지, 취소, 메모, 미사용 일때 공제액이 계산 된
//		if(ilboSignDTO.getLabor_contract().equals("2")) {
//			if(!ilboDTO.getCellname().equals("output_status_code") 
//				&& !ilboDTO.getCellname().equals("ilbo_worker_status_code")
//				&& !ilboDTO.getCellname().equals("ilbo_memo")
//				&& !ilboDTO.getCellname().equals("ilbo_order_code")
//				&& !ilboDTO.getCellname().equals("ilbo_status_code")
//				&& !ilboDTO.getCellname().equals("ilbo_income_code")
//				&& !ilboDTO.getCellname().equals("ilbo_pay_code")
//				&& !ilboDTO.getCellname().equals("ilbo_income_memo")
//				&& !ilboDTO.getCellname().equals("employer_rating")
//				&& !ilboDTO.getCellname().equals("manager_rating")
//				&& !ilboDTO.getCellname().equals("evaluate_grade")
//				&& !ilboDTO.getCellname().equals("use_yn")
//			)
//			{
//				jsonObj.put("code", "0021");
//				jsonObj.put("message", "구직자가 서명을 완료하여 수정이 불가능합니다.");
//				
//				return jsonObj;
//			}
//		}
		
		//변경전 일보
		IlboDTO paramDTO = new IlboDTO();
		paramDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
		IlboDTO beforeIlboInfo = ilboService.selectIlboInfo(paramDTO);
		IlboDTO valiIlboDTO = SerializationUtils.clone(beforeIlboInfo);
		if(!StringUtil.isNull(logDTO.getCode_value())) {
			workerSeq = beforeIlboInfo.getWorker_seq();
		}
		
		//validation 체크
		String cellName = StringUtil.isNullToString(ilboDTO.getCellname());
		switch (cellName) {
			case "output_status_code":
				//구직자가 없으면
				if( "0".equals(beforeIlboInfo.getWorker_seq()) ) {
					jsonObj.put("result", "1001");
					jsonObj.put("message", "구직자 를 확인 하세요.");
					return jsonObj;
				}
				if( "0".equals(beforeIlboInfo.getEmployer_seq()) ) {
					jsonObj.put("result", "1002");
					jsonObj.put("message", "구인처를 입력해주세요.");
					return jsonObj;
				}
				break;
			case "ilbo_income_code" :
				// 노임수령의 상태값을 기성으로 바꿀 때 구인처의 기성-대불 확인
				if( ilboDTO.getIlbo_income_code().equals("300011") || ilboDTO.getIlbo_income_code().equals("300012") ) {
					if(!"3".equals(beforeIlboInfo.getPayment())) {
						jsonObj.put("result", "9998");
						jsonObj.put("message", "구인처의 결재방법이 기성-대불이 아닙니다.");
						return jsonObj;
					}
				}
				break;
			case "ilbo_use_code" :
				//근로계약이 진행중이면 공개 수정 불가능
				if( ilboSignDTO.getLabor_contract().equals("2") ) {
					jsonObj.put("code", "0019");
					jsonObj.put("message", "계약이 진행중인 일은 변경할 수 없습니다.");
					
					return jsonObj;
				}
				break;	
			case "ilbo_pay_code" :
				//근로계약서, 대리수령동의서 중 하나라도 계약이 진행중일 때
				if( !ilboSignDTO.getLabor_contract().equals("0") || !ilboSignDTO.getSurrogate().equals("0") ) {
					//직수,페이 -> 통수,수수 / 통수,수수->직수,페이 로 변경 불가능
					boolean isBeforeEmployerPayCode = beforeIlboInfo.isEmployerPayCode();
					boolean isAfterEmployerPayCode = ilboDTO.isEmployerPayCode();
					if( isBeforeEmployerPayCode != isAfterEmployerPayCode ) {
						jsonObj.put("code", "0019");
						jsonObj.put("message", "계약이 진행중인 일은 변경할 수 없습니다.");
						
						return jsonObj;
					}
				}
				break;
			case "ilbo_worker_name" :
				// 구직자 변경 시 소장님이 새로고침 하기 위해 던지는 에러
				if(!beforeIlboInfo.getWorker_seq().equals(ilboDTO.getBefore_worker_seq())) {
					jsonObj.put("code", "0099");
					jsonObj.put("message", "새로고침 한 후 진행해주세요.");
					
					return jsonObj;
				}
				break;
			case "worker_company_name" :
				if(!beforeIlboInfo.getWorker_company_seq().equals(ilboDTO.getBefore_worker_company_seq())) {
					jsonObj.put("code", "0099");
					jsonObj.put("message", "새로고침 한 후 진행해주세요.");
					
					return jsonObj;
				}
				break;
			case "ilbo_date" :
				int deductibleSum = 0;
				if( valiIlboDTO.getDeductible_sum() != null ) {
					deductibleSum = Integer.parseInt(valiIlboDTO.getDeductible_sum());
				}
				if(deductibleSum > 0) {
					String msg = valiIlboDTO.getIlbo_worker_name() + "구직자의 " + valiIlboDTO.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.";
					jsonObj.put("code", "0098");
					jsonObj.put("message", msg);
					return jsonObj;
				}
				break;
			default:
				break;
		}
		
		if(!StringUtil.isNull(ilboDTO.getIlbo_seq())) {	
			// 일보가 내것이 아니면 수수료와 쉐어료를 서로 바꾼다.
			if(ilboDTO.getWork_company_seq() != null && ("ilbo_fee".equals(ilboDTO.getCellname()) || "share_fee".equals(ilboDTO.getCellname()))) {
				if(!ilboDTO.getWorker_company_seq().equals(ilboDTO.getWork_company_seq()) && ilboDTO.getWorker_company_seq().equals(ilboDTO.getCompany_seq()) && !"0".equals(ilboDTO.getWork_company_seq()) ) {
					String ilboFee 		= ilboDTO.getIlbo_fee();
					String shareFee 	= ilboDTO.getShare_fee();

					ilboDTO.setIlbo_fee(shareFee);
					ilboDTO.setShare_fee(ilboFee);
				}
			}
			
			if( ("ilbo_fee".equals(ilboDTO.getCellname()) || "share_fee".equals(ilboDTO.getCellname()) || "ilbo_unit_price".equals(ilboDTO.getCellname()) || "ilbo_deduction".equals(ilboDTO.getCellname()) || "counselor_fee".equals(ilboDTO.getCellname()) ) ) {
				ilboDTO.setWorker_company_seq(null);
				ilboDTO.setWork_company_seq(null);
			}

			//일보 정보 수정
			int auth_level	= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
			
			if(auth_level != 0 ){//최고 관리자 일때는 수정가능 하도록한다.  
				ilboDTO.setCompany_seq("");	//바꾸지 않겠다는 뜻...
			}
			/*
			// 전문분야코드 다중선택으로 ,로 구분하여 저장
			if(!StringUtil.isNull(ilboDTO.getIlbo_kind_code())){
				String ilbo_kind_code = ilboDTO.getIlbo_kind_code().replaceAll("_",",");
				ilbo_kind_code = ilbo_kind_code.substring(1);
				
				ilboDTO.setIlbo_kind_code(ilbo_kind_code);
			}
			
			if(!StringUtil.isNull(ilboDTO.getIlbo_kind_name())){
				String ilbo_kind_name = ilboDTO.getIlbo_kind_name().replaceAll("_",",");
				ilbo_kind_name = ilbo_kind_name.substring(1);
				
				ilboDTO.setIlbo_kind_name(ilbo_kind_name);
			}
			*/
    	
			// 공제액이 1 ~ 8일째라면 true, 아니면 false;
			// 관리자가 위 3개의 옵션으로 변경시 수수료, 쉐어료, 상담사를 0으로 변경한다.
			if("ilbo_status_code".equals(ilboDTO.getCellname()) && auth_level < 4) {
				if(ilboDTO.getIlbo_status_code().equals("STA007") || ilboDTO.getIlbo_status_code().equals("STA008") || ilboDTO.getIlbo_status_code().equals("STA009")) {
					ilboDTO.setIlbo_fee("0");
					ilboDTO.setShare_fee("0");
					ilboDTO.setCounselor_fee("0");
					ilboDTO.setIlbo_order_code("500008");
					ilboDTO.setIlbo_order_name("7차");
					ilboDTO.setIlbo_pay_code("0");
					ilboDTO.setIlbo_pay_name("");
				}
			}
			
			if(workerSeq != null && !"".equals(workerSeq) && !"0".equals(workerSeq) && ilboDTO.getCellname().equals("ilbo_status_code")) {
				if(ilboDTO.isWorkCancel()) {
					if( !deductibleService.isDeletePermisson(beforeIlboInfo) ) {
						jsonObj.put("code", "0095");
						jsonObj.put("message", beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
						
						return jsonObj;
					}
					
					deductibleService.cancelInsurance(beforeIlboInfo, ilboDTO.getReg_admin());
				}
				
				if( valiIlboDTO.isInsuranceCalculate() ) {
					if( valiIlboDTO.isWorkCancel() && !ilboDTO.isWorkCancel() ) {
						WorkerDTO workerDTO = new WorkerDTO();
						workerDTO.setWorker_seq(workerSeq);
						workerDTO = workerService.selectWorkerInfo(workerDTO);
						
//							IlboDTO limitIlboDTO = new IlboDTO();
//							
//							limitIlboDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
//							limitIlboDTO = ilboService.selectIlboInfo(ilboDTO);
						
						// 해당 현장, 구인처의 출역일수 제한 확인 =============================================
						LimitCount resultLimitCount = valiIlboDTO.getLimitCount();
						if( resultLimitCount.isCheckedLimitCount() ) {
							CommIlboDTO commParam = new CommIlboDTO();
							commParam.setWorker_seq(workerDTO.getWorker_seq() + "");
							commParam.setIlbo_date(valiIlboDTO.getIlbo_date());
							commParam.setEmployer_seq(valiIlboDTO.getEmployer_seq() + "");
							commParam.setWork_seq(valiIlboDTO.getWork_seq() + "");
							
							CommIlboDTO commResult = commIlboService.selectLimitList(commParam);
							
							if( LimitCount.EMPLOYER == resultLimitCount ) {
								if( !commResult.getEmployer_limit() ) {
									jsonObj.put("code", "0019");
									jsonObj.put("message", workerDTO.getWorker_name() + "구직자는 월 최대 출역일수 초과로 출역이 제한됩니다.");
									
									return jsonObj;
								}
							}else if( LimitCount.WORK == resultLimitCount ) {
								if( !commResult.getWork_limit() ) {
									jsonObj.put("code", "0019");
									jsonObj.put("message", workerDTO.getWorker_name() + "구직자는 월 최대 출역일수 초과로 출역이 제한됩니다.");
									
									return jsonObj;
								}
							}
						}
						
						try {
							String deducationSum = deductibleService.joinInsurance(ilboDTO);
							ilboDTO.setDeductible_sum(deducationSum);
						}catch(FutureIlboException i) {
							jsonObj.put("code", "0096");
							jsonObj.put("message", i.getMessage());
							
							return jsonObj;
						}
					}
				}
			}else if( workerSeq != null && !"".equals(workerSeq) && !"0".equals(workerSeq) && "output_status_code".equals(ilboDTO.getCellname()) ) {
				if(ilboDTO.getOutput_status_code().equals("100003") 
				|| ilboDTO.getOutput_status_code().equals("100005")
				|| ilboDTO.getOutput_status_code().equals("100014")
				|| ilboDTO.getOutput_status_code().equals("100015")) {
					// 해당 현장, 구인처의 출역일수 제한 확인 =============================================
					LimitCount resultLimitCount = beforeIlboInfo.getLimitCount();
					
					WorkerDTO workerDTO = new WorkerDTO();
					workerDTO.setWorker_seq(beforeIlboInfo.getWorker_seq());
					workerDTO = workerService.selectWorkerInfo(workerDTO);
					
					// 예약, 출발, 도착, 완료가 기존 상태값인 경우에는 예약, 출발, 도착, 완료중 한가지로 변경 가능
					if(beforeIlboInfo.getOutput_status_code() != null 
					&& !beforeIlboInfo.getOutput_status_code().equals("100003") 
					&& !beforeIlboInfo.getOutput_status_code().equals("100005")
					&& !beforeIlboInfo.getOutput_status_code().equals("100014")
					&& !beforeIlboInfo.getOutput_status_code().equals("100015")) {
//						IlboDTO limitIlboDTO = new IlboDTO();
//						
//						limitIlboDTO.setIlbo_seq(ilboInfo.getIlbo_seq());
//						limitIlboDTO = ilboService.selectIlboInfo(ilboInfo);
						
						if( resultLimitCount.isCheckedLimitCount() ) {
							CommIlboDTO commParam = new CommIlboDTO();
							commParam.setWorker_seq(workerDTO.getWorker_seq() + "");
							commParam.setIlbo_date(beforeIlboInfo.getIlbo_date());
							commParam.setEmployer_seq(beforeIlboInfo.getEmployer_seq() + "");
							commParam.setWork_seq(beforeIlboInfo.getWork_seq() + "");
							
							CommIlboDTO commResult = commIlboService.selectLimitList(commParam);
							
							if( LimitCount.EMPLOYER == resultLimitCount ) {
								if( !commResult.getEmployer_limit() ) {
									jsonObj.put("code", "0019");
									jsonObj.put("message", workerDTO.getWorker_name() + "구직자는 월 최대 출역일수 초과로 출역이 제한됩니다.");
									
									return jsonObj;
								}
							}else if( LimitCount.WORK == resultLimitCount ) {
								if( !commResult.getWork_limit() ) {
									jsonObj.put("code", "0019");
									jsonObj.put("message", workerDTO.getWorker_name() + "구직자는 월 최대 출역일수 초과로 출역이 제한됩니다.");
									
									return jsonObj;
								}
							}
						}
					}
					
					// 공제액 계산 시점 => 출발, 도착, 완료
					if(ilboDTO.getOutput_status_code().equals("100003")
					|| ilboDTO.getOutput_status_code().equals("100014")
					|| ilboDTO.getOutput_status_code().equals("100015")) {
						
						try {
							String deducationSum = deductibleService.joinInsurance(ilboDTO);
							ilboDTO.setDeductible_sum(deducationSum);
						}catch(FutureIlboException i) {
							jsonObj.put("code", "0096");
							jsonObj.put("message", i.getMessage());
							
							return jsonObj;
						}
					}
					
					// 공제액이 계산 된 일보는 예약으로 바꿀수 없다.
					if(Integer.parseInt(beforeIlboInfo.getDeductible_sum()) > 0) {
						if(ilboDTO.getOutput_status_code().equals("100005")) {
							jsonObj.put("code", "0095");
							jsonObj.put("message", beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
							
							return jsonObj;
						}
					}
				}else if("100008".equals(ilboDTO.getOutput_status_code()) || "100009".equals(ilboDTO.getOutput_status_code()) 
						|| "100010".equals(ilboDTO.getOutput_status_code()) || "0".equals(ilboDTO.getOutput_status_code())) {
					if( !deductibleService.isDeletePermisson(beforeIlboInfo) ) {
						jsonObj.put("code", "0095");
						jsonObj.put("message", beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
						
						return jsonObj;
					}
					
					deductibleService.cancelInsurance(beforeIlboInfo, ilboDTO.getReg_admin());
				}
			}else if("use_yn".equals(ilboDTO.getCellname())) {
				if("0".equals(ilboDTO.getUse_yn())) {
					if( !deductibleService.isDeletePermisson(beforeIlboInfo) ) {
						jsonObj.put("code", "0095");
						jsonObj.put("message", beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
						
						return jsonObj;
					}
					
					deductibleService.cancelInsurance(beforeIlboInfo, ilboDTO.getReg_admin());
				}else {
					if( valiIlboDTO.isInsuranceCalculate() ) {
						try {
							String deducationSum = deductibleService.joinInsurance(ilboDTO);
							ilboDTO.setDeductible_sum(deducationSum);
						}catch(FutureIlboException i) {
							jsonObj.put("code", "0096");
							jsonObj.put("message", i.getMessage());
							
							return jsonObj;
						}
					}
				}
			}
			
			// 경력옵션을 수정할 때 쌓는 로그
			if("ilbo_career_name".equals(ilboDTO.getCellname())) {
				CareerLogDTO careerLogDTO = new CareerLogDTO();
				
				careerLogDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				
				careerLogDTO.setCareer_name(ilboDTO.getIlbo_career_name());
				careerLogDTO.setReg_admin(ilboDTO.getReg_admin());
				
				if(ilboDTO.getIlbo_career_use_seq().equals("0")) {
					ilboDTO.setIlbo_career_name(ilboDTO.getSelCareerName());
					careerLogDTO.setCareer_name(ilboDTO.getSelCareerName());
				}
				
				careerLogService.insertCareerLog(careerLogDTO);
			}
			
			// 양식이 수정될 때 쌓는 로그
			if("labor_contract_seq".equals(ilboDTO.getCellname()) || "receive_contract_seq".equals(ilboDTO.getCellname())) {
				IlboDTO ilboInfo = new IlboDTO();
				
				ilboInfo.setIlbo_seq(ilboDTO.getIlbo_seq());
				
				ilboInfo = ilboService.selectIlboInfo(ilboInfo);
				
				IlboContractLogDTO ilboContractLogDTO = new IlboContractLogDTO();
				
				ilboContractLogDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				
				if("labor_contract_seq".equals(ilboDTO.getCellname())) {
					ilboContractLogDTO.setBefore_contract_seq(ilboInfo.getLabor_contract_seq());
					ilboContractLogDTO.setAfter_contract_seq(ilboDTO.getLabor_contract_seq());
				}else {
					ilboContractLogDTO.setBefore_contract_seq(ilboInfo.getReceive_contract_seq());
					ilboContractLogDTO.setAfter_contract_seq(ilboDTO.getReceive_contract_seq());
				}
				
				ilboContractLogDTO.setReg_admin(ilboDTO.getReg_admin());
				
				if("labor_contract_seq".equals(ilboDTO.getCellname()) && !ilboContractLogDTO.getBefore_contract_seq().equals(ilboContractLogDTO.getAfter_contract_seq())) {
					if(ilboContractLogDTO.getAfter_contract_seq() != null)
						ilboContractLogService.insertLog(ilboContractLogDTO);
				}
				
				if("receive_contract_seq".equals(ilboDTO.getCellname()) && !ilboContractLogDTO.getBefore_contract_seq().equals(ilboContractLogDTO.getAfter_contract_seq())) {
					if(ilboContractLogDTO.getAfter_contract_seq() != null)
						ilboContractLogService.insertLog(ilboContractLogDTO);
				}
			}
			
			//code log 추가
			if(!StringUtil.isNull(logDTO.getCode_value())) {
				logDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				logDTO.setLog_user_type("A");
				logDTO.setReg_admin(ilboDTO.getReg_admin());
				logDTO.setLog_text(ilboDTO.getReg_admin());
				logDTO.setWorker_seq(beforeIlboInfo.getWorker_seq());
				logDTO.setWorker_company_seq(beforeIlboInfo.getWorker_company_seq());
				logDTO.setWork_seq(beforeIlboInfo.getWork_seq());
				logDTO.setWork_company_seq(beforeIlboInfo.getCompany_seq());
		    
				if("0".equals(logDTO.getCode_value() )){
					logDTO.setCode_name("초기화");
				}
				
				logService.insertCodeLog(logDTO);
			}
			
			// 단가, 수수료, 쉐어료, 상담사, 구직자 금액이 바뀌면 로그를 쌓는다.
			if("ilbo_unit_price".equals(ilboDTO.getCellname())
			|| "ilbo_fee".equals(ilboDTO.getCellname())
			|| "share_fee".equals(ilboDTO.getCellname())
			|| "counselor_fee".equals(ilboDTO.getCellname())
			|| "ilbo_pay".equals(ilboDTO.getCellname())) {
				PriceLogDTO priceLogDTO = new PriceLogDTO();
				
				priceLogDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
				priceLogDTO.setCellName(ilboDTO.getCellname());
				priceLogDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				priceLogDTO.setUser_id(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
				
				if("ilbo_unit_price".equals(ilboDTO.getCellname())) {
					priceLogDTO.setAfter_price(ilboDTO.getIlbo_unit_price());
					priceLogDTO.setBefore_price(beforeIlboInfo.getIlbo_unit_price());
				}else if("ilbo_fee".equals(ilboDTO.getCellname())) {
					priceLogDTO.setAfter_price(ilboDTO.getIlbo_fee());
					priceLogDTO.setBefore_price(beforeIlboInfo.getIlbo_fee());
				}else if("share_fee".equals(ilboDTO.getCellname())) {
					priceLogDTO.setAfter_price(ilboDTO.getShare_fee());
					priceLogDTO.setBefore_price(beforeIlboInfo.getShare_fee());
				}else if("counselor_fee".equals(ilboDTO.getCellname())){
					priceLogDTO.setAfter_price(ilboDTO.getCounselor_fee());
					priceLogDTO.setBefore_price(beforeIlboInfo.getCounselor_fee());
				}else if("ilbo_pay".equals(ilboDTO.getCellname())) {
					priceLogDTO.setAfter_price(ilboDTO.getIlbo_pay());
					priceLogDTO.setBefore_price(beforeIlboInfo.getIlbo_pay());
				}
				
				logService.insertPriceLog(priceLogDTO);
			}
				
			if( "ilbo_pay_code".equals(ilboDTO.getCellname()) ) {
//				IlboDTO ilboInfo = new IlboDTO();
//				ilboInfo.setIlbo_seq(ilboDTO.getIlbo_seq());
//				ilboInfo = ilboService.selectIlboInfo(ilboInfo);
				boolean isBeforeEmployerPayCode = beforeIlboInfo.isEmployerPayCode();
				boolean isAfterEmployerPayCode = ilboDTO.isEmployerPayCode();
				if( isBeforeEmployerPayCode != isAfterEmployerPayCode ) {
					IlboContractLogDTO ilboContractLogDTO = new IlboContractLogDTO();
					ilboContractLogDTO.setReg_admin(ilboDTO.getReg_admin());
					if( isAfterEmployerPayCode ) {
						//직수, 페이 설정
						ilboDTO.setReceive_contract_seq("0");
						
						ilboContractLogDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
						ilboContractLogDTO.setBefore_contract_seq(beforeIlboInfo.getReceive_contract_seq());
						ilboContractLogDTO.setAfter_contract_seq("0");
					}else {
						//직수, 페이 풀기
						ContractDTO contractParam = new ContractDTO();
						contractParam.setCategory_code("ERM");
						ContractDTO defaultReceiveContract = contractService.selectContractDefaultInfo(contractParam);
						ilboDTO.setReceive_contract_seq(defaultReceiveContract.getContract_seq());
						
						ilboContractLogDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
						ilboContractLogDTO.setBefore_contract_seq(beforeIlboInfo.getReceive_contract_seq());
						ilboContractLogDTO.setAfter_contract_seq(defaultReceiveContract.getContract_seq());
						jsonObj.put("receive_contract_name", defaultReceiveContract.getContract_name());
					}
					jsonObj.put("receive_contract_seq", ilboContractLogDTO.getAfter_contract_seq());
					
					ilboContractLogService.insertLog(ilboContractLogDTO);
				}
			}
			
//			if("output_status_code".equals(ilboDTO.getCellname())) {
//				if(ilboDTO.isWorkerCancel()) {
//					ilboDTO.setDeductible_sum("0");
//					// status 0으로 바꿔야함
//					IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
//					ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
//					ilboDeductionDTO.setDeduction_status("0");
//					ilboService.updateIlboDeduction(ilboDeductionDTO);
//				}
//			}
//			
//			if("ilbo_status_code".equals(ilboDTO.getCellname())) {
//				if(ilboDTO.isWorkCancel()) {
//					ilboDTO.setDeductible_sum("0");
//					// status 0으로 바꿔야함
//					IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
//					ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
//					ilboDeductionDTO.setDeduction_status("0");
//					ilboService.updateIlboDeduction(ilboDeductionDTO);
//				}
//			}
//			
//			if("use_yn".equals(ilboDTO.getCellname())) {
//				if(ilboDTO.getUse_yn().equals("0")) {
//					ilboDTO.setDeductible_sum("0");
//					// status 0으로 바꿔야함
//					IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
//					ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
//					ilboDeductionDTO.setDeduction_status("0");
//					ilboService.updateIlboDeduction(ilboDeductionDTO);
//				}
//			}
			
			ilboService.setIlboInfo(ilboDTO);
			
			if("ilbo_unit_price".equals(cellName)) {
				String deducationSum = deductibleService.updateInsurance(ilboDTO);
				ilboDTO.setDeductible_sum(deducationSum);
			}
			
			if( "output_status_code".equals(ilboDTO.getCellname()) ) {
				if(ilboDTO.getOutput_status_code().equals("100003") 
						|| ilboDTO.getOutput_status_code().equals("100005")
						|| ilboDTO.getOutput_status_code().equals("100014")
						|| ilboDTO.getOutput_status_code().equals("100015")) {
					
					AutoIlboDTO autoIlboDTO = ilboService.selectAutoIlbo(ilboDTO);
					if( autoIlboDTO != null && !autoIlboDTO.isAutoSendStopStatus() ) {
						ilboDTO.setAuto_status("3");
						ilboDTO.setAuto_status_name("중지▣");
						ilboDTO.setReg_admin(ilboDTO.getReg_admin()); // 임시(변경 가능성 있음)
						ilboService.setAutoIlbo(ilboDTO);
					}
				}
			}
		}
    
		//------------메니져 푸쉬 보내기------------------------//
		ilboService.sendManagerPush(ilboDTO);
		//----------------------------------------------------//
    
		//구인처 ->구직자 평가=============================================================
		if( "employer_rating".equals(ilboDTO.getCellname()) ) {
			RatingDTO ratingDTO = new RatingDTO();
	    	ratingDTO.setRating_type("I");
	    	ratingDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	    	ratingDTO.setEmployer_seq(ilboDTO.getEmployer_seq());
	    	ratingDTO.setWork_seq(ilboDTO.getWork_seq());
	    	ratingDTO.setWorker_seq(ilboDTO.getWorker_seq());
	    	ratingDTO.setRate(ilboDTO.getEmployer_rating());
	    	ratingDTO.setReg_admin(ilboDTO.getReg_admin());
	    	ratingDTO.setMod_admin(ilboDTO.getReg_admin());
	    	
	    	RatingDTO resultDTO = ratingService.selectInfo(ratingDTO);
	    	
	    	if(resultDTO == null) {
	    		ratingService.insertInfo(ratingDTO);
	    	}else {
	    		ratingDTO.setRating_seq(resultDTO.getRating_seq());
	    		ratingService.updateInfo(ratingDTO);
	    	}
		}
    
		//구인자 -> 구직자 평가=============================================================
		if( "manager_rating".equals(ilboDTO.getCellname()) ) {
//			WorkDTO workDTO = new WorkDTO();
//			workDTO.setWork_seq(ilboDTO.getWork_seq());
//			workDTO = workService.getSelectInfo(workDTO);
    	
	    	RatingDTO ratingDTO = new RatingDTO();
	    	ratingDTO.setRating_type("M");
	    	ratingDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	    	ratingDTO.setEmployer_seq(ilboDTO.getEmployer_seq());
	    	ratingDTO.setManager_seq(ilboDTO.getManager_seq());
	    	ratingDTO.setWork_seq(ilboDTO.getWork_seq());
	    	ratingDTO.setWorker_seq(ilboDTO.getWorker_seq());
	    	ratingDTO.setRate(ilboDTO.getManager_rating());
	    	ratingDTO.setReg_admin(ilboDTO.getReg_admin());
	    	ratingDTO.setMod_admin(ilboDTO.getReg_admin());
	    	
	    	RatingDTO resultDTO = ratingService.selectInfo(ratingDTO);
	    	
	    	if(resultDTO == null) {
	    		ratingService.insertInfo(ratingDTO);
	    	}else {
	    		ratingDTO.setRating_seq(resultDTO.getRating_seq());
	    		ratingService.updateInfo(ratingDTO);
	    	}
		}
		
		//구직자 평가를 위한 분기 및 서비스 (parameter :: seq, evaluate_grade) 
		if("evaluate_grade".equals(ilboDTO.getCellname()) && !"0".equals(ilboDTO.getEvaluate_grade())) {
	    	EvaluateDTO evaluateDTO = new EvaluateDTO();
	    	evaluateDTO.setSeq(ilboDTO.getSeq());
	    	evaluateDTO.setMod_admin(ilboDTO.getReg_admin());
	    	evaluateDTO.setEvaluate_grade(ilboDTO.getEvaluate_grade());
	    	
	    	evaluateService.updateInfo(evaluateDTO);
		}
		
		// 알림을 보내기 위한 코드 추가
		try {
			NotificationDTO notificationDTO = new NotificationDTO();
	    
			notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	    
			// worker_company_seq != company_seq
			if(ilboDTO.getCellname() != null && ilboDTO.getCellname().equals("worker_company_name") && !ilboDTO.getWorker_company_seq().equals("0") && !ilboDTO.getWorker_company_seq().equals(ilboDTO.getCompany_seq())) {     // 지점간 구인 요청 알림
		    	notificationDTO.setStatus_flag("ALR001");
		    	
		    	// 알림 보내는 메소드
		    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
		    	notificationUtil.ilboAlim(notificationDTO);
			}else if(ilboDTO.getIlbo_status_code() != null && ilboDTO.getIlbo_status_code().equals("STA008")) {	// 본사접수 구인취소 알림
		    	notificationDTO.setStatus_flag("ALR002");
		    	
		    	// 알림 보내는 메소드
		    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
		        notificationUtil.ilboAlim(notificationDTO);
			}else if( "STA007".equals(logDTO.getCode_value()) ) { //휴지
				notificationDTO.setStatus_flag("ALR031");
		    	// 알림 보내는 메소드
		    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
		    	notificationUtil.ilboAlim(notificationDTO);
			}else if( "STA009".equals(logDTO.getCode_value()) ) { //메모
				notificationDTO.setStatus_flag("ALR032");
		    	// 알림 보내는 메소드
		    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
		    	notificationUtil.ilboAlim(notificationDTO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
    
		jsonObj.put("deductible_sum", ilboDTO.getDeductible_sum());
		jsonObj.put("result", "0000");
		
		return jsonObj;
	}
  
	@PostMapping("/notificationSend")
	@ResponseBody
	public JSONObject matchingFailMessage(NotificationDTO notificationDTO) {
		JSONObject jsonObj = new JSONObject();
    	// 알림 보내는 메소드
    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
    	notificationUtil.ilboAlim(notificationDTO);
    	jsonObj.put("code", "0000");
    	return jsonObj;
	}
	
	/**
	* ilgajaM push
	* 오더메니져 푸쉬 보내기..
	* @param ilbo_seq
	* @param output_code
	*/
//	public void sendManagerPush(String ilbo_seq, String output_code){
//		if(!StringUtil.isNull(output_code)){// code 가 있으면
//	    	if(output_code.equals("100005") ||  output_code.equals("100003") || output_code.equals("100014")){
//				IlboDTO iDTO = new IlboDTO();
//		    	iDTO.setIlbo_seq(ilbo_seq);
//		    	
//		    	IlboDTO resultDTO = ilboService.selectIlboInfo(iDTO);	//일보 정보
//		    	
//		    	if(!"0".equals(resultDTO.getManager_seq()) ){//메니져가 있으면
//		    		ManagerDTO paramDTO = new ManagerDTO();
//		    		paramDTO.setManager_seq(resultDTO.getManager_seq());
//		    		paramDTO.setUse_yn("1");
//		    		ManagerDTO managerDTO = managerService.selectManagerInfo(paramDTO);
//		    		
//		    		if(!"N".equals(managerDTO.getOs_type()) && "1".equals(managerDTO.getManager_push_yn() )){
//		    			PushUtil.sendManagerPush( 	managerDTO.getOs_type()
//		    										,resultDTO.getIlbo_date()
//		    										, managerDTO.getPush_token()
//		    										, "A"
//		    										, resultDTO.getIlbo_date()
//		    										,resultDTO.getIlbo_work_name()
//		    										,resultDTO.getIlbo_worker_name()
//		    										, resultDTO.getOutput_status_name());
//		    		}
//		    	}
//	    	}
//	    }
//	}

	// 행 복사
	@RequestMapping(value="/copyIlboCell")
	@ResponseBody
	public JSONObject copyIlboCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		try {
			// Session 설정
			ilboDTO = setSessionToDTO(session, ilboDTO);

			ilboService.copyIlboCell(ilboDTO);
		
			jsonObj.put("code", Const.CODE_SUCCESS);
			jsonObj.put("ilbo_seq", ilboDTO.getManager_seq());                 // 성공
		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.put("code", "9999");
			jsonObj.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	/**
	* @Method Name : addWorkToIlboCell
	* @작성일 : 2021. 5. 13.
	* @작성자 : Jangjaeho
	* @변경이력 : 
	* @Method 설명 : 현장관리에서 일보로 등록
	*/
	@RequestMapping(value="/addWorkToIlboCell")
	@ResponseBody
	public JSONObject addWorkToIlboCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO, WorkDTO workDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);
    
		try {
			if(StringUtil.isNullNZero(ilboDTO.getWork_seq())) throw new Exception("파라메터 누락 : 현장 순번 ");
			if(StringUtil.isNullNZero(ilboDTO.getManager_seq())) throw new Exception("파라메터 누락 : 매니져 순번 ");
			if(StringUtil.isNullNZero(ilboDTO.getTo_ilbo_date())) { throw new Exception("파라메터 누락 : 일일대장 날짜 ");}
		
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_seq(ilboDTO.getManager_seq());
		
			ManagerDTO managerDTO = managerService.selectManagerInfo(paramDTO);
			
			if(managerDTO == null) {
				throw new Exception("현장 매니져가 존재 하지 않습니다.");
			}
		
			String companySeq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
			String companyName	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		
			ilboDTO.setCompany_seq(companySeq);
			ilboDTO.setCompany_name(companyName);
			ilboDTO.setWorker_company_seq(companySeq);
			ilboDTO.setWorker_company_name(companyName);
			ilboDTO.setWork_company_seq(managerDTO.getCompany_seq());
			ilboDTO.setWork_company_name(managerDTO.getCompany_name());
			ilboDTO.setManager_seq(managerDTO.getManager_seq());
			ilboDTO.setManager_name(managerDTO.getManager_name());
			ilboDTO.setManager_phone(managerDTO.getManager_phone());
			ilboDTO.setOwner_id(managerDTO.getOwner_id());
			ilboDTO.setWork_owner(managerDTO.getOwner_id());
			
			EmployerDTO employerInfo = new EmployerDTO();
			
			employerInfo.setEmployer_seq(workDTO.getEmployer_seq());
			
			employerInfo = employerService.getEmployerView(employerInfo);
			
			// 구인처관리의 서명 매니저 타입이 현장매니저이면 일일대장의 현장매니저로 들어간다
			if(employerInfo.getSign_manager_type().equals("O")) {
				ilboDTO.setSign_manager_seq(managerDTO.getManager_seq());
			}else {
				ilboDTO.setSign_manager_seq(employerInfo.getSign_manager_seq());
			}
		
			ilboService.addWorkToIlboCell(ilboDTO);
    	
			jsonObj.put("code", Const.CODE_SUCCESS);
			jsonObj.put("ilbo_seq", ilboDTO.getIlbo_seq());                 // 성공
		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.put("code", "9999");
			jsonObj.put("message", e.getMessage());
			e.printStackTrace();
		}

		return jsonObj;
	}

	// 구직자관리에서 일보로 등록
	@RequestMapping(value="/addWorkerToIlboCell")
	@ResponseBody
	public JSONObject addWorkerToIlboCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
    
		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);
    
		ilboService.addWorkerToIlboCell(ilboDTO);

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeIlboInfo")
	@ResponseBody
	public JSONObject removeIlboInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);

		
		for(String ilboSeq : ilboDTO.getSel_ilbo_seq()) {
			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setIlbo_seq(ilboSeq);
			IlboDTO beforeIlboInfo = ilboService.selectIlboInfo(paramDTO);
			
			if( "0".equals(beforeIlboInfo.getDeductible_sum()) ) {
				continue;
			}
			
			if( !deductibleService.isDeletePermisson(beforeIlboInfo) ) {
				jsonObj.put("code", "0095");
				jsonObj.put("message", beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
				
				return jsonObj;
			}
			
			deductibleService.cancelInsurance(beforeIlboInfo, ilboDTO.getReg_admin());
		}
		
		ilboService.removeIlboInfo(ilboDTO);
		jsonObj.put("code", "0000");
		return jsonObj;
	}

	// 구직자정보 초기화
	@RequestMapping(value="/setIlboWorkerReset")
	@ResponseBody
	public JSONObject setIlboWorkerReset(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		// Session 설정
		try {
			ilboDTO = setSessionToDTO(session, ilboDTO);
			
			CodeLogDTO logDTO = new CodeLogDTO();
			
			List<IlboDTO> ilboList = ilboService.selectIlboResetList(ilboDTO);
			
			for(int i = 0; i < ilboList.size(); i++) {
				logDTO.setIlbo_seq(ilboList.get(i).getIlbo_seq());
				logDTO.setLog_user_type("A");
				logDTO.setReg_admin(ilboDTO.getReg_admin());
				logDTO.setLog_text(ilboDTO.getReg_admin());
				logDTO.setWorker_seq(ilboList.get(i).getWorker_seq());
				logDTO.setWorker_company_seq(ilboList.get(i).getWorker_company_seq());
				logDTO.setWork_seq(ilboList.get(i).getWork_seq());
				logDTO.setWork_company_seq(ilboList.get(i).getWork_company_seq());
				logDTO.setCode_name("초기화");
				logDTO.setCode_value("0");
				logDTO.setCode_type("100");
				
				logService.insertCodeLog(logDTO);
			}
			
			ilboService.setIlboWorkerReset(ilboDTO);
			
			transactionManager.commit(status);
		}catch(Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}

		return jsonObj;
	}

	// 수동푸쉬
	@RequestMapping(value="/setIlboPush")
	@ResponseBody
	public JSONObject setIlboPush(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);

		//일보 테이블에 푸쉬 푸쉬로 상태 바꾸기
		ilboDTO.setOutput_status_code("100012");
		ilboDTO.setOutput_status_name("푸쉬");
		ilboService.setIlboOutputStatus(ilboDTO);
    
		//체크된 ilbo_seq push 보내기=============================================
		for(String ilbo_seq: ilboDTO.getSel_ilbo_seq()){
			//Thread t = new PushThreadTask(ilbo_seq, ilboDTO.getReg_admin());
			//일보 정보 가져오기
			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setIlbo_seq(ilbo_seq);
			IlboDTO ilboResult = ilboService.selectIlboInfo(paramDTO);

			//사용자의 push 정보 가져오기
//			WorkerDTO paramWorker = new WorkerDTO();
//			paramWorker.setWorker_seq(ilboResult.getWorker_seq());
//			WorkerDTO workerResult = workerService.getWorkerInfo(paramWorker);
			
			WorkerDTO workerDTO = new WorkerDTO();
			workerDTO.setWorker_seq(ilboResult.getWorker_seq());
			
			List<WorkerDTO> tokenList = workerService.selectReceiveTokenInfo(workerDTO);

			// code log ==========================================
			CodeLogDTO logDTO = new CodeLogDTO();
		    logDTO.setIlbo_seq(ilboResult.getIlbo_seq());
		    logDTO.setLog_user_type("A");
		    logDTO.setReg_admin(ilboDTO.getReg_admin());
		    logDTO.setLog_text(ilboDTO.getReg_admin());
		    
		    logDTO.setWorker_seq(ilboResult.getWorker_seq());
		    logDTO.setWorker_company_seq(ilboResult.getWorker_company_seq());
		    logDTO.setWork_seq(ilboResult.getWork_seq());
		    logDTO.setWork_company_seq(ilboResult.getCompany_seq());
		    logDTO.setCode_type("100");
		    logDTO.setCode_value("100012");
		    logDTO.setCode_name("푸쉬");
		    logService.insertCodeLog(logDTO);
		    //=====================================================
			//String content = "[" + ilboResult.getWork_name() +"] 현장 일자리"; 
		    //String content = "[" + ilboResult.getWork_skip_addr() +"] 현장 일자리";
		    String[] ilboDateArray = ilboResult.getIlbo_date().split("-");
		    String contentDate = Integer.parseInt(ilboDateArray[1]) + "월 " + Integer.parseInt(ilboDateArray[2]) + "일";
		    String content = contentDate + " " + "\"" + ilboResult.getWork_skip_addr() +"\" 현장 일자리";
		    
			//messageUtil.commThread("일가자 알선", content, ilboResult.getWorker_seq(), tokenList, "0", "1", "W", 1);
			messageUtil.commThread("일가자 알선 도착", content, ilbo_seq, tokenList, "1", "A", "W", 1);
			
//			if(tokenList.size() > 0) {
//				WorkerDTO wDTO = tokenList.get(0);
//
//				if("A".equals(wDTO.getOs_type())) {
//					messageUtil.androidPush("일가자 알선", content, ilbo_seq, wDTO, "1", "A", "W", 1);
//				}else if("I".equals(wDTO.getOs_type())) {
//					messageUtil.IosPush("일가자 알선", content, ilbo_seq, wDTO, "1", "A", "W", 1);
//					
//				}
//				
//			}
		}

		return jsonObj;
	}
	
	@RequestMapping(value="/aiPushTest")
	@ResponseBody
	public Map<String, Object> aiPushTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// Session 설정
		ilboDTO = setSessionToDTO(session, ilboDTO);

		IlboDTO ilboResult = ilboService.selectIlboInfo(ilboDTO);
		
		WorkerDTO workerDTO = new WorkerDTO();
		workerDTO.setWorker_seq(ilboResult.getWorker_seq());
		
		List<WorkerDTO> tokenList = workerService.selectReceiveTokenInfo(workerDTO);
		
		//PUSH
		String pushTitle = "일가자 AI 알선 도착";
		//예) 2월1일 “강남구 역삼동” 현장 일당 150,000원 일자리
		String[] ilboDateArray = ilboResult.getIlbo_date().split("-");
	    String contentDate = Integer.parseInt(ilboDateArray[1]) + "월 " + Integer.parseInt(ilboDateArray[2]) + "일";
	    String pushBody = contentDate + " " + "\"" + ilboResult.getWork_skip_addr() +"\" 현장 일당 " + StringUtil.strComma(ilboResult.getIlbo_pay()) + "원 일자리";
		
		ApnsConfig apnsConfig = null;
		if( tokenList.get(0).getOs_type().equals("I")) {
			apnsConfig = crateApnsConfig(pushTitle, pushBody);
		}
		
		messageUtil.commThread("일가자 AI 알선 도착", pushBody, ilboResult.getIlbo_seq(), tokenList, "1", "O", "W", 1);
		
//		//push보내기
//		Message msg = Message.builder()
//				.setToken(tokenList.get(0).getPush_token())
//				.putData("title", pushTitle)
//				.putData("body", pushBody)
//				.putData("pushId", "1")
//				.putData("pushType", "AI")
//				//.putData("sendSeq", ilbo.getIlboSeq())
//				.putData("ilboSeq", ilboResult.getIlbo_seq())
//				.putData("isMain", "1")
//				.setApnsConfig(apnsConfig)
//				.build();
//		
//		fcm.send(msg);
		
		resultMap.put("code", "0000");
		
		return resultMap;
	}
	
	private ApnsConfig crateApnsConfig(String title, String body) {
		return crateApnsConfig(title, body, "ilgaja_alsun.aiff");
	}
	
	private ApnsConfig crateApnsConfig(String title, String body, String sound) {
		ApsAlert alert = ApsAlert.builder()
				.setTitle(title)
				.setBody(body)
				.build();
		
		Aps aps = Aps.builder()
				.setAlert(alert)
				.setSound(sound)
				.setContentAvailable(true)
				.build();
		
		return ApnsConfig.builder()
				.setAps(aps)
				.build();
	}
  
	@RequestMapping(value = "/checkOutputStatus")
	@ResponseBody
	public Map<String, Object> checkOutputStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			String outputCode = StringUtil.isNullToString(ilboDTO.getOutput_status_code(), "0");
			
			ilboDTO.setOutput_status_code(null);
			IlboDTO resultDTO = ilboService.selectIlboInfo(ilboDTO);
			
			if(!outputCode.equals(StringUtil.isNullToString(resultDTO.getOutput_status_code(), "0"))) {
				resultMap.put("code", "1111");
				resultMap.put("ilboDTO",  resultDTO);
			}else {
				resultMap.put("code", "0000");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "오류가 발생했습니다.");
		}

		return resultMap;
	}
	

  	@RequestMapping("/setIlboInfoToLocation")
  	@ResponseBody
  	public JSONObject setIlboInfoToLocation(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
  		JSONObject json = new JSONObject();
  		
  		// Session 설정
  	    ilboDTO = setSessionToDTO(session, ilboDTO);

  	    // 일보가 내것이 아니면 수수료와 쉐어료를 서로 바꾼다.
  		if(ilboDTO.getWorker_company_seq() != null) {
  		    if(!ilboDTO.getWorker_company_seq().equals(ilboDTO.getCompany_seq()) && !"0".equals(ilboDTO.getWorker_company_seq()) ) {
  		    	
  		    	String ilboFee 		= ilboDTO.getIlbo_fee();
  		    	String shareFee 	= ilboDTO.getShare_fee();
  		    			
  		    	ilboDTO.setIlbo_fee(shareFee);
  		    	ilboDTO.setShare_fee(ilboFee);
  		    }
  		}
  		
  	    //일보 정보 수정
  	    int auth_level	= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
  	    if(auth_level != 0 ){//최고 관리자 일때는 수정가능 하도록한다.  
  	    	ilboDTO.setCompany_seq("");	//바꾸지 않겠다는 뜻...
  	    }
  	    
  	    ilboService.setIlboInfo(ilboDTO);
  	    
  		return json;
  	}
  	
  	@RequestMapping("/writeEmployer") 
  	public String writeEmployer(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
  		model.addAttribute("htmlHeader" , "구인처 등록");
  		
  		return "/admin/writeEmployer";
  	}
  	
  	@RequestMapping("/selectCompanion")
  	@ResponseBody
  	public Map<String, Object> selectCompanion(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
  		Map<String, Object> resultMap = new HashMap<String, Object>();

  		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			List<IlboDTO> companionList = ilboService.selectCompanionList(ilboDTO);
			
			resultMap.put("companionList", companionList);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
  	}
  	
  	@RequestMapping("/alimLocation")
  	public String alimLocation(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, RedirectAttributes redirect) throws IllegalArgumentException, IllegalAccessException {
  		String status = request.getParameter("status").split("\\|")[0];
  		String ilbo_seq = request.getParameter("status").split("\\|")[1];
  		
  		Map<String, Object> paramMap = new HashMap<String, Object>();
  		
  		paramMap.put("status", status);
  		
  		IlboDTO ilboDTO = new IlboDTO();
  		ilboDTO.setIlbo_seq(ilbo_seq);
  		ilboDTO = ilboService.selectIlboInfo(ilboDTO);
  		
  		WorkDTO workDTO = new WorkDTO();
  		WorkerDTO workerDTO = new WorkerDTO();
  		
  		if(status.equals("employerIlbo")) { // 현장 대장
  			paramMap.put("srh_ilbo_type", "i.work_seq");
  			paramMap.put("srh_seq", ilboDTO.getWork_seq());
  		}else if(status.equals("workerIlbo")) { // 구직 대장
  			paramMap.put("srh_ilbo_type", "i.worker_seq");
  			paramMap.put("srh_seq", ilboDTO.getWorker_seq());
  		}else if(status.equals("workIlbo")) { // 구인 대장
  			paramMap.put("srh_ilbo_type", "i.employer_seq");
  			paramMap.put("srh_seq", ilboDTO.getEmployer_seq());
  		}else if(status.equals("order")){ // 오더 관리
  			paramMap.put("srh_ilbo_type", "order");
  		}else if(status.equals("CALL")){ // CALL
  			// 1. 일을 신청한 매니저 or 현장담당자 => 본사 : 구인대장, (현장매니저, 현장담당자) : 현장대장
  			IlboDTO paramDTO = new IlboDTO();
  			paramDTO.setManager_phone(ilbo_seq);
  			IlboDTO resultDTO = ilboService.selectWebPushIlboInfo(paramDTO);
  			
  			if( resultDTO != null ) {
				paramMap.put("srh_ilbo_type", "i.work_seq");
	  			paramMap.put("srh_seq", resultDTO.getWork_seq());
  			}else {  // 1이 없다면  2. 구직자 => 구직대장
  				workerDTO.setWorker_phone(ilbo_seq);
  	  			workerDTO = branchService.selectWorkerInfo(workerDTO);
  				
  	  			if( workerDTO != null ) {
	  	  			paramMap.put("srh_ilbo_type", "i.worker_seq");
	  	  			paramMap.put("srh_seq", workerDTO.getWorker_seq());
  	  			}else { // 다없으면 오더관리
  	  				paramMap.put("srh_ilbo_type", "order");
  	  			}
  			}
  		}
  		redirect.addFlashAttribute("map", paramMap);
  		
  		return "redirect:/admin/alimRedirect";
  	}
  	
  	@RequestMapping("/alimRedirect")
  	public String alimRedirect(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
  		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
  		Map<String, Object> params = new HashMap<String, Object>();
  		  
  		if(flashMap != null) {
  			params = (Map<String, Object>) flashMap.get("map");
  			
  			if( params.get("srh_ilbo_type") != null ) {
  				String srh_type = params.get("srh_ilbo_type").toString();
  				model.addAttribute("srh_ilbo_type", srh_type);
  			}
  			
  			if( params.get("srh_seq") != null ) {
  				String srh_seq = params.get("srh_seq").toString();
  				model.addAttribute("srh_seq", srh_seq);
  			}
  		}
  		
  		if( "order".equals(model.asMap().get("srh_ilbo_type")) ) {
  			return "/admin/alimRedirect.admin";
  		}
  		  
  		return "/admin/alimRedirect";
  	}
  	

	/**
	  * @Method Name : getSelectIlboList
	  * @작성일 : 2021. 2. 10.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 선택한 일보리스트
	
	  */
	@RequestMapping(value = "/getSelectIlboList")
	public void getSelectIlboList(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		
		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			//CommIlboDTO limitIlboDTO = new CommIlboDTO();
			
			List<IlboDTO> ilboList = ilboService.getSelectIlboList(ilboDTO);
			
			boolean employerLimit = true;
			boolean workLimit = true;
			
			for(int x = 0; x < ilboList.size(); x++) {
				//limitIlboDTO.setIlbo_date(ilboList.get(x).getIlbo_date());
				//limitIlboDTO.setWorker_seq(ilboList.get(x).getWorker_seq());
				//limitIlboDTO.setEmployer_seq(ilboList.get(x).getEmployer_seq());
				//limitIlboDTO.setWork_seq(ilboList.get(x).getWork_seq());
				
				//CommIlboDTO resultDTO = commIlboService.selectLimitList(limitIlboDTO);
				
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setIlbo_seq(ilboList.get(x).getIlbo_seq());
				IlboDTO ilboInfo = ilboService.selectIlboInfo(paramDTO);
				
				WorkerDTO workerDTO = new WorkerDTO();
				
				workerDTO.setWorker_seq(ilboList.get(x).getWorker_seq());
				
				workerDTO = workerService.selectWorkerInfo(workerDTO);
				
				IlboDTO limitIlboInfoDTO = new IlboDTO();
				
				limitIlboInfoDTO.setIlbo_seq(ilboList.get(x).getIlbo_seq());
				limitIlboInfoDTO = ilboService.selectIlboInfo(ilboInfo);
				
				// 해당 현장, 구인처의 출역일수 제한 확인 =============================================
				LimitCount resultLimitCount = ilboInfo.getLimitCount();

				if( resultLimitCount.isCheckedLimitCount() ) {
					CommIlboDTO commParam = new CommIlboDTO();
					commParam.setWorker_seq(workerDTO.getWorker_seq() + "");
					commParam.setIlbo_date(ilboInfo.getIlbo_date());
					commParam.setEmployer_seq(ilboInfo.getEmployer_seq() + "");
					commParam.setWork_seq(ilboInfo.getWork_seq() + "");
					
					CommIlboDTO commResult = commIlboService.selectLimitList(commParam);
					
					if( LimitCount.EMPLOYER == resultLimitCount ) {
						if( !commResult.getEmployer_limit() ) {
							employerLimit = false;
							
							break;
						}
					}else if( LimitCount.WORK == resultLimitCount ) {
						if( !commResult.getWork_limit() ) {
							workLimit = false;
							
							break;
						}
					}
				}
			}
			
			result.put("ilboList", ilboList);
			result.put("employerLimit", employerLimit);
			result.put("workLimit", workLimit);
			
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {

			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();

		}
	}
	
	@RequestMapping("/updateIlboManager")
	@ResponseBody
	public Map<String, Object> updateIlboManager(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			// manager 정보 구하기
			ManagerDTO managerDTO = new ManagerDTO();
			
			managerDTO.setManager_seq(ilboDTO.getManager_seq());
			
			managerDTO = managerService.selectManagerInfo(managerDTO);
			if( managerDTO != null) {
				// ilbo에 data 넣고 update
				ilboDTO.setWork_owner(managerDTO.getOwner_id());
				ilboDTO.setManager_name(managerDTO.getManager_name());
				ilboDTO.setManager_phone(managerDTO.getManager_phone());
				ilboDTO.setWork_company_seq(managerDTO.getCompany_seq());
				ilboDTO.setWork_company_name(managerDTO.getCompany_name());
				
				ilboService.setIlboInfo(ilboDTO);
			}
			resultMap.put("resultDTO", managerDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/getDeductibleInfo")
	@ResponseBody
	public Map<String, Object> getDeductibleInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			IlboDeductibleDTO ilboDeductibleDTO = new IlboDeductibleDTO();
			
			ilboDeductibleDTO = ilboService.selectDeductibleInfo(ilboDTO);
			
			resultMap.put("ilboDeductibleDTO", ilboDeductibleDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/setAutoIlboStatus")
	@ResponseBody
	public Map<String, Object> setAutoIlboStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			ilboDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			ilboService.setAutoIlbo(ilboDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/getAutoIlboLog")
	@ResponseBody
	public Map<String, Object> getAutoIlboLog(HttpServletRequest request, HttpServletResponse response, IlboDTO ilboDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);

			List<AutoIlboDTO> logList = ilboService.selectAutoIlboLog(ilboDTO);
			
			resultMap.put("logList", logList);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/getAutoIlboStatus")
	@ResponseBody
	public Map<String, Object> getAutoIlboStatus(HttpServletRequest request, HttpServletResponse response, IlboDTO ilboDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			AutoIlboDTO result = ilboService.selectAutoIlbo(ilboDTO);
			resultMap.put("autoIlboStatus", result == null ? null : result.getAuto_status());
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@GetMapping(value="/getPriceLogs")
	@ResponseBody
	public Map<String, Object> getPriceLogs(PriceLogDTO priceLogDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("list", logService.getPriceLogList(priceLogDTO));
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return resultMap;
	}
}
