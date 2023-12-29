package com.nemo.kr.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.dto.JobLimitCountDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.RatingDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.excel.WorkerListExcel;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.IlboWorkService;
import com.nemo.kr.service.JobService;
import com.nemo.kr.service.RatingService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.DateUtil;
//import com.nemo.kr.util.StringUtil;
import com.nemo.kr.util.NotificationUtil;


/**
  * @FileName : WorkerController.java
  * @Project : ilgaja
  * @Date : 2021. 8. 17. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 
  */
@Controller
@RequestMapping("/admin")
public class WorkerController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

	@Autowired WorkerService workerService;
	@Autowired RatingService ratingService;
	@Autowired CompanyService companyService;
	@Autowired JobService jobService;
	//@Autowired SmsService smsService;
	@Autowired RestTemplate restTemplate;
	@Autowired IlboWorkService ilboWorkService;
	@Autowired CodeService codeService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	// Session 설정
	private WorkerDTO setSessionToDTO(HttpSession session, WorkerDTO workerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(workerDTO.getCompany_seq());
		
		// Session 설정
		workerDTO.setAdminLevel(""+ auth_level);
   
		if ( auth_level != 0 ) {
			if ( "".equals(get_company_seq) ) {
				workerDTO.setCompany_seq(company_seq);
			}
		}
    
		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			workerDTO.setOwner_id( admin_id );
		}
    
		workerDTO.setCompany_name(company_name);
		workerDTO.setReg_admin(admin_id);

		return workerDTO;
	}
  
	private WorkerDTO setSessionToWorkerDTO(HttpSession session, WorkerDTO workerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		
		if( auth_level != 0 ) {
			if( !(auth_level == 1 && "13".equals(company_seq)) ) {
				workerDTO.setCompany_seq(company_seq);
			}
		}
		
		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			workerDTO.setOwner_id( admin_id );
		}

		return workerDTO;
	}

	@RequestMapping(value="/workerList")
	public String workerList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "구직자관리");
		
		JobLimitCountDTO jobLimitCountDTO = ilboWorkService.selectJobLimitCount("IMS");
		if(jobLimitCountDTO != null) {
			model.addAttribute("jobLimitCount", jobLimitCountDTO.getLimit_count());
		}else {
			model.addAttribute("jobLimitCount", 5);
		}
		
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setCode_type("WJC");
		model.addAttribute("joinRouteOpts", codeService.getCodeJqgridSelectFormat(codeDTO));
		
		return "/admin/workerList.admin";
	}

	@RequestMapping(value="/getWorkerList")
	@ResponseBody
	public JSONObject getWorkerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		int auth_level;

		String where    = workerDTO.getWhere();
		String srh_type = workerDTO.getSrh_type();
		String srh_text = workerDTO.getSrh_text();

		// Session 설정
		workerDTO = setSessionToWorkerDTO(session, workerDTO);

		if ( !StringUtil.isNull(srh_text) ) {
			where += " AND ";

			if (!StringUtil.isNull(srh_type) ) {
				if("w.worker_name".equals(srh_type) || "w.worker_phone".equals(srh_type) || "w.worker_jumin".equals(srh_type) ) {
					where += "fn_decrypt(" + srh_type +")" +" LIKE '%"+ srh_text +"%' ";
				}else {
					where += srh_type +" LIKE '%"+ srh_text +"%' ";
				}
			} else {
				auth_level = StringUtil.isNullToInt(workerDTO.getAdminLevel());
				where += " (";
        
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				where += " fn_decrypt(w.worker_name)     LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(w.worker_phone) LIKE '%"+ srh_text +"%'";
				where += " OR w.worker_addr  LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(w.worker_jumin) LIKE '%"+ srh_text +"%'";
				where += " OR w.worker_job_name LIKE '%"+ srh_text +"%'";
				where += " OR w.worker_feature LIKE '%"+ srh_text +"%'";
				where += " OR w.worker_memo LIKE '%"+ srh_text +"%'";
				where += " )";
			}
		}

		//상세검색 유무
		if(workerDTO.getDetailSearch() == 1) {
			where += " AND worker_app_use_status = 1 AND worker_app_status = 1 AND worker_push_yn = 1 AND w.push_token != ''";
			//소장평가
			String ratingWhere = "";

			if(workerDTO.getSearch_worker_rating() != null) {
				for (int i = 0; i < workerDTO.getSearch_worker_rating().length; i++) {
					if(i>0) ratingWhere +=  ", ";
					ratingWhere += workerDTO.getSearch_worker_rating()[i];
				}
	    	
				if(workerDTO.getSearch_worker_rating().length >0 ) {
					where += " AND worker_rating IN (" + ratingWhere +") ";
				}
			}else {
				where += " AND worker_rating > 0";
			}
			//-------------------------------------------------------------------------------------------
    	
			//나이 검색
			String ageWhere = "";
			
			if(workerDTO.getSearch_worker_age() != null) {
				for (int i = 0; i < workerDTO.getSearch_worker_age().length; i++) {
		    		if(i>0) ageWhere +=  ", ";
					String age =workerDTO.getSearch_worker_age()[i];
		    			
		    		if("70".equals(age)) {
						age = "70, 80, 90";
					}
		    			
					ageWhere += age;
				}
	    	
				if(workerDTO.getSearch_worker_age().length >0 ) {
					where += " AND fn_age_s(worker_jumin) IN (" + ageWhere +") ";
				}
			}
			//-------------------------------------------------------------------------------------------
    	
			//직종검색
			String jobKindWhere = "";
			for (int i = 0; i < workerDTO.getSearch_job_kind().length; i++) {
				if(i>0) jobKindWhere +=  "|";
				jobKindWhere += workerDTO.getSearch_job_kind()[i];
			}
    	
			if(!"".equals(jobKindWhere)) {
				where += " AND worker_job_code REGEXP '" + jobKindWhere +"' ";
			}
    	
			jobKindWhere = "";
			//차량보유
			if( workerDTO.getSearch_worker_car() != null ) {
				for (int i = 0; i < workerDTO.getSearch_worker_car().length; i++) {
					//if(!"".equals(jobKindWhere)) jobKindWhere += "|";
					if( i> 0) jobKindWhere +=  "|";
	    		
					jobKindWhere += workerDTO.getSearch_worker_car()[i];
				}
			}
			
    	
			if(!"".equals(jobKindWhere)) {
				where += " AND worker_job_code REGEXP '" + jobKindWhere +"' ";
			}
    	    	
			//-------------------------------------------------------------------------------------------
    	
			if("1".equals(workerDTO.getSearch_worker_etc())) { // 보통인부제외
				JobDTO jobParam = new JobDTO();
				jobParam.setJob_kind("1");
				jobParam.setJob_rank("1");
    		
				List<JobDTO> resultJobList = jobService.getJobList(jobParam);
				
				for (JobDTO jobDTO : resultJobList) {
					where += "AND worker_job_code NOT LIKE '%"+ jobDTO.getJob_seq()+ "%' ";
				}
				
				//where += "AND worker_job_code NOT REGEXP (SELECT GROUP_CONCAT(job_seq SEPARATOR'|') AS code_value FROM tbl_job WHERE job_kind != 1 AND use_yn =1)";
			}
    	
			if("1".equals(workerDTO.getSearch_worker_today())) { // 오늘출력자 제외
				where += " AND w.worker_seq NOT IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.getCurDate2() +"' AND worker_seq >0 AND work_seq >0 AND output_status_code IN (100003, 100005,100014) AND use_yn = 1)";
			}
    	
			if("1".equals(workerDTO.getSearch_worker_tommorrow())) { // 내일출력자 제외
				where += " AND w.worker_seq NOT IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.addDay(1, "yyyy-MM-dd") +"' AND worker_seq >0 AND work_seq >0 AND output_status_code IN (100003, 100005,100014) AND use_yn = 1) ";
			}
			
			if("1".equals(workerDTO.getSearch_ilbo_worker())) {//오늘 신청자
				where += " AND w.worker_seq  IN   (SELECT worker_seq FROM (SELECT worker_seq FROM  (SELECT worker_seq   FROM    ilbo   WHERE ilbo_date = '"+ DateUtil.getCurDate2() +"' AND worker_seq > 0 AND work_seq = 0 AND output_status_code IN ( 100002, 100020) ) w1 WHERE worker_seq NOT IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.getCurDate2() +"' AND worker_seq > 0 AND work_seq > 0  AND output_status_code IN (100003, 100005, 100014, 100015))) aa)  ";
				//	where += " AND worker_seq IN ( SELECT worker_seq from (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.getCurDate2() +"' AND worker_seq >0 AND work_seq =0 AND output_status_code = '100002' ) ) w1 where worker_seq NOT IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.getCurDate2() + "' AND worker_seq >0 AND work_seq >0 AND output_status_code IN (100003, 100005,100014,100015))";
			}else if("2".equals(workerDTO.getSearch_ilbo_worker())) { //지금 신청자
				where += " AND w.worker_seq IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.getCurDate2() +"' AND worker_seq >0 AND work_seq =0 AND output_status_code = '100020' )";
			}else if("3".equals(workerDTO.getSearch_ilbo_worker())) {
				where += " AND w.worker_seq IN (SELECT worker_seq FROM ilbo WHERE ilbo_date = '"+ DateUtil.addDay(1, "yyyy-MM-dd") +"' AND worker_seq >0 AND work_seq =0 AND output_status_code IN (100002))";
			}
			
			String loginCompanySeq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
			
			// 지점 선택
			workerDTO.setCompany_seq(""); //관리자, 직원인경우 본인 지점것만 보이던 것을 해제 
			if("1".equals(workerDTO.getSearch_company())) {
				where += " AND w.company_seq = " + loginCompanySeq;
			}else if("2".equals(workerDTO.getSearch_company())) {
				where += " AND w.company_seq != " + loginCompanySeq;
			}
			
			// 기초안전보건교육증 보유자
			if( "1".equals(workerDTO.getSearch_worker_osh()) ) {
				where += " AND w.worker_OSH_yn = 1";
			}
			
			// 성별 검색
			if(workerDTO.getSearch_worker_sex() != null && !workerDTO.getSearch_worker_sex().equals("0")) {
				where += " AND w.worker_sex = '" + workerDTO.getSearch_worker_sex() + "'";
			}
			
			// 일자리선택 후 기출역자 검색
			if( workerDTO.getSearch_work_seq() != null && workerDTO.getSearch_work_seq() != "" ) {
				
				String join_query = "LEFT OUTER JOIN ilbo i ON w.worker_seq = i.worker_seq";
				
				workerDTO.setDone_join_query(join_query);
				where += " AND i.work_seq = " + workerDTO.getSearch_work_seq();
				where += " AND i.output_status_code = '100015' "; 
				where += " AND i.employer_seq NOT IN (SELECT employer_seq from tbl_worker_rating where rate = 1 and rating_type = 'I' and worker_seq = w.worker_seq GROUP BY employer_seq )"; 
			  	where += " AND i.manager_seq NOT IN (SELECT manager_seq  FROM    tbl_worker_rating  WHERE rate = 1    AND rating_type = 'M'    AND worker_seq = w.worker_seq  GROUP BY manager_seq)"; 
			  	where += " AND i.employer_seq NOT IN  (SELECT  employer_seq FROM tbl_evaluate   WHERE evaluate_grade <= 1   AND worker_seq = w.worker_seq  GROUP BY employer_seq)";
			  	where += " GROUP BY i.worker_seq";
			}
			
		}

		if(workerDTO.getSearch_worker_latlng() == null || workerDTO.getSearch_worker_latlng().length < 1) {
			workerDTO.setSearch_worker_latlng(null);
		}
		
		workerDTO.setWhere(where);
		
		int rowCount = workerService.getWorkerTotalCnt(workerDTO);

		workerDTO.getPaging().setRowCount(rowCount);

		List<WorkerDTO> result = workerService.getWorkerList(workerDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             				// 현재페이지의 목록데이터
		jsonObj.put("page",    workerDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   workerDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", workerDTO.getPaging().getRowCount());              // 총글목록수

		return jsonObj;
	}

	// 구직자 정보 (JSP)
	@RequestMapping(value="/getWorkerViewJSP")
	public String getWorkerViewJSP(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO, ModelMap model) throws Exception {
		// Session 설정
		workerDTO = setSessionToDTO(session, workerDTO);

		workerDTO = workerService.getWorkerView(workerDTO);

		model.addAttribute("result", workerDTO);

		return "/admin/workerInfo.layout_admin";
	}

	//구직자 정보 (JSON)
	@RequestMapping(value="/getWorkerView")
	@ResponseBody
	public JSONObject getWorkerView(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		// Session 설정
		workerDTO = setSessionToDTO(session, workerDTO);

		workerDTO = workerService.getWorkerView(workerDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("result", workerDTO);

		return jsonObj;
	}

	// 행 추가
	@RequestMapping(value="/setWorkerCell")
	@ResponseBody
	public JSONObject setWorkerCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		// Session 설정
		workerDTO = setSessionToDTO(session, workerDTO);
		workerDTO.setJoin_route("WJC001");	//가입경로 : 사무실(소장추가)
		workerService.setWorkerCell(workerDTO);
		jsonObj.put("worker_seq", workerDTO.getWorker_seq());                       // 성공

		return jsonObj;
	}
  
	// 변경된 행 저장
	@RequestMapping(value="/setWorkerInfo")
	@ResponseBody
	//@Transactional
	public JSONObject setWorkerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		// jqgrid 2번 타는거 막기 위해 worker_seq 검사
		if(workerDTO.getWorker_seq() == null) {
			return jsonObj;
		}
		
		String worker_job_code = "";
		String worker_job_name = "";
		String cellName = workerDTO.getCellname();
		String companyFlag = workerDTO.getCompanyFlag();
		
		// Session 설정
		workerDTO = setSessionToDTO(session, workerDTO);
		
		// 전문분야코드 다중선택으로 ,로 구분하여 저장
		worker_job_code = StringUtil.isNullToString(workerDTO.getWorker_job_code());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			if ( worker_job_code != null && worker_job_code.length() > 0 ) {
				worker_job_code = worker_job_code.replaceAll("_", ",");
				worker_job_code = worker_job_code.substring(1);
				
				workerDTO.setWorker_job_code(worker_job_code);
				jsonObj.put("worker_job_code", workerDTO.getWorker_job_code());
			}
			
			worker_job_name = StringUtil.isNullToString(workerDTO.getWorker_job_name());
	    
			if ( worker_job_name != null && worker_job_name.length() > 0 ) {
				worker_job_name = worker_job_name.replaceAll("_", ",");
				worker_job_name = worker_job_name.substring(1);
				
				workerDTO.setWorker_job_name(worker_job_name);
				jsonObj.put("worker_job_name", workerDTO.getWorker_job_name());
			}
			
			WorkerDTO beforeWorker = new WorkerDTO();
			if( "company_name".equals(cellName) || "worker_app_use_status".equals(cellName) || "worker_bank_owner".equals(cellName) ) {
				beforeWorker.setWorker_seq(workerDTO.getWorker_seq());
				beforeWorker = workerService.selectWorkerInfo(beforeWorker);
			}
			
			if("worker_app_use_status".equals(cellName)) {
				if( workerDTO.getWorker_app_use_status().equals("1") ) {
					if( !beforeWorker.getWorker_name().replaceAll("\\s", "").equals(beforeWorker.getWorker_bank_owner().replaceAll("\\s", "")) ) {
						transactionManager.rollback(status);
						
						jsonObj.put("result", "9101");
						jsonObj.put("message", "계좌 소유주의 이름과 구직자 이름은 동일해야 합니다");
						
						return jsonObj;
					}
				}
			}
			
			if("worker_bank_owner".equals(cellName) && beforeWorker.getWorker_app_use_status().equals("1")) {
				transactionManager.rollback(status);
				
				jsonObj.put("result", "9102");
				jsonObj.put("message", "승인 상태에서는 예금주 이름을 변경 할 수 없습니다.<br />승인대기로 변경 후 이름을 수정하세요.");
				
				return jsonObj;
			}
			
			workerService.setWorkerInfo(workerDTO);
			workerDTO = workerService.selectWorkerInfoMasterDB(workerDTO);
			
			//승인대기 -> 승인
			if( "worker_app_use_status".equals(cellName) ) {
				if( beforeWorker.getWorker_app_use_status().equals("4") && workerDTO.getWorker_app_use_status().equals("1") ) {
					NotificationDTO notificationDTO =  new NotificationDTO();
					notificationDTO.setWorker_seq(workerDTO.getWorker_seq());
					notificationDTO.setStatus_flag("ALR030");
					
					NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
					notificationUtil.ilboAlim(notificationDTO);
				}
			}
			
			//구직자 평가==============================
			if("worker_rating".equals(cellName)) {
				RatingDTO ratingDTO = new RatingDTO();
				ratingDTO.setWorker_seq(workerDTO.getWorker_seq());
				ratingDTO.setRating_type("C");
				ratingDTO.setRate(workerDTO.getWorker_rating());
				
				//로그인한 어드민 계정으로 설정
				String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
				ratingDTO.setReg_admin(admin_id);
				ratingDTO.setMod_admin(admin_id);
				
				//ratingDTO.setReg_admin(workerDTO.getReg_admin());
				//ratingDTO.setMod_admin(workerDTO.getReg_admin());
				
				ratingService.insertInfo(ratingDTO);
			}
			
			jsonObj.put("result", "0000");
			transactionManager.commit(status);
			// 문자전송
			if("company_name".equals(cellName)) {
				if( beforeWorker.getCompany_seq().equals(Const.JOBNPARTNER_SEQ) && beforeWorker.getWorker_app_use_status().equals("4") ) {
					NotificationDTO notificationDTO =  new NotificationDTO();
					notificationDTO.setWorker_seq(workerDTO.getWorker_seq());
					//notificationDTO.setCompany_flag(companyFlag); //구직자에게 발송 유무
					notificationDTO.setStatus_flag("ALR029");
					
					NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
					notificationUtil.ilboAlim(notificationDTO);
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
			
			transactionManager.rollback(status);
		}

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeWorkerInfo")
	@ResponseBody
	public JSONObject removeWorkerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		workerDTO = setSessionToDTO(session, workerDTO);

		workerService.removeWorkerInfo(workerDTO);

		return jsonObj;
	}

	// 구인자 폰번호 유니크 검색 - 같은 지점 내에서 검색
	@RequestMapping(value="/chkWorkerPhone")
	@ResponseBody
	public boolean chkWorkerPhone(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		boolean result = false;
    
		int iCnt = workerService.chkWorkerPhone(workerDTO);

		if ( iCnt <= 0 ) {
			result =  true;
		}

		return result;
	}

	// 구인자 주민등록 번호 유니크 검색 - 전체에서 검색
	@RequestMapping(value="/chkWorkerJumin")
	@ResponseBody
	public boolean chkWorkerJumin(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		boolean result = false;

		int iCnt = workerService.chkWorkerJumin(workerDTO);

		if ( iCnt <= 0 ) {
			result =  true;
		}

		return result;
	}
  
  	@RequestMapping("/locationMap")
	@ResponseBody
	public JSONObject locationMap(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		workerDTO = setSessionToDTO(session, workerDTO);
		
		List<WorkerDTO> workerList = workerService.locationMap(workerDTO);
		
		jsonObj.put("workerList", workerList);
		
		return jsonObj;
	}
  	
  	public static void main(String[] args) {
		int auth = 2;
		int company = 14;
		
		if( !(auth == 0 || (company == 13 && auth == 1) || (company == 13 && auth == 2 )) ) {
			System.out.println("yes");
		}else {
			System.out.println("no");
		}
	}
  	
  	@RequestMapping(value="/workerListExcel")
    @ResponseBody
    public View WorkListExcel(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
  		int auth_level;
  		String companyName = "";
  		String where    = workerDTO.getWhere();
  		String srh_type = workerDTO.getSrh_type();
  		String srh_text = workerDTO.getSrh_text();

  		workerDTO.setSrh_use_yn("1");
  		workerDTO.setSidx("w.worker_seq desc, w.company_seq, w.worker_name");
  		workerDTO.setSord("desc");

  		// Session 설정
  		workerDTO = setSessionToDTO(session, workerDTO);

  		if( !"".equals(workerDTO.getCompany_seq()) && workerDTO.getCompany_seq() != null ) {
  			CompanyDTO com = new CompanyDTO();
  			com.setCompany_seq(workerDTO.getCompany_seq());
  			com = companyService.getCompanyInfo(com);
  			companyName = com.getCompany_name() + "_";
  		}

  		if ( srh_text != null && !"".equals(srh_text) ) {
  			if ( "".equals(where) ) {
  				where = " AND ";
  			}

  			if ( srh_type != null && !"".equals(srh_type) ) {
  				where += srh_type +" LIKE '%"+ srh_text +"%'";

  			} else {
  				auth_level = StringUtil.isNullToInt(workerDTO.getAdminLevel());

  				where += " (";
  				if ( auth_level == 0 ) {
  					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
  				}

  				where += " w.worker_name     LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_phone LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_addr  LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_jumin LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_job_name LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_feature LIKE '%"+ srh_text +"%'";
  				where += " OR w.worker_memo LIKE '%"+ srh_text +"%'";
  				where += " )";
  			}

  			workerDTO.setWhere(where);
  		}

  		if( workerDTO.getAdminLevel().equals("1") ) {
  			workerDTO.setWhere(workerDTO.getWhere() + " AND w.reg_admin = '" + workerDTO.getReg_admin() + "'");
  		}
  		
  		int rowCount = workerService.getWorkerTotalCnt(workerDTO);

  		workerDTO.getPaging().setEndPage(rowCount);

  		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  		Date today = new Date();
  		String fileName = companyName + "구직자관리_" + format.format(today) + ".xlsx";
  		List<WorkerDTO> resultList = workerService.getWorkerList(workerDTO);

  		model.addAttribute("fileName", fileName);
  		model.addAttribute("companyName", companyName);
  		model.addAttribute("managerDTO", workerDTO);
  		model.addAttribute("resultList", resultList);

  		return new WorkerListExcel();
  	}
}
