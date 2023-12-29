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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.LaborContractDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.excel.WorkListExcel;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.WorkService;
//import com.nemo.kr.util.StringUtil;


/**
 * 일가자 현장 관리 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
@Controller
@RequestMapping("/admin")
public class WorkController {
	private static final Logger logger = LoggerFactory.getLogger(WorkController.class);

	@Autowired WorkService workService;
	@Autowired ManagerService managerService;
	@Autowired CompanyService companyService;
	@Autowired ContractService contractService;
	@Autowired EmployerService employerService;

	// Session 설정
	private WorkDTO setSessionToDTO(HttpSession session, WorkDTO workDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(workDTO.getCompany_seq());

		// Session 설정
		workDTO.setAdminLevel(""+ auth_level);

		if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
			workDTO.setSrh_company_seq(company_seq);
			
			if ( "".equals(get_company_seq) ) {
				workDTO.setCompany_seq(company_seq);
			}
		}

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			workDTO.setOwner_id( admin_id);
		}
    
		workDTO.setCompany_name(company_name);
		workDTO.setReg_admin(admin_id);
		
		return workDTO;
	}

	private WorkDTO setSessionToWorkDTO(HttpSession session, WorkDTO workDTO) throws Exception {
		// Session 정보
	    int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
	    String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
	    String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
	    String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

	    //파트너 일 경우는 파트너 인것만 가져 온다.
	    if(auth_level == 3 ){
	    	workDTO.setOwner_id( admin_id);
	    }
	    
	    return workDTO;
	}
  
	@RequestMapping(value="/workList")
	public String workList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "현장관리");
		model.addAttribute("companySeq" 	, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		model.addAttribute("authLevel" 		, ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
			  	
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
		
		return "/admin/workList.admin";
	}

	@RequestMapping(value="/getWorkList")
	@ResponseBody
	public JSONObject getWorkList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		int auth_level;

		String where    = workDTO.getWhere();
		String srh_type = workDTO.getSrh_type();
		String srh_text = workDTO.getSrh_text();
		
		// Session 설정
		workDTO = setSessionToWorkDTO(session, workDTO);

		if ( srh_text != null && !"".equals(srh_text) ) {
			//if ( "".equals(where) ) {
				//where = " AND ";
			//}
			
			// filter 검색 했을 때도 AND 절이 추가 되게 
			if ( "".equals(where) || (workDTO.getFilters() != null && !workDTO.getFilters().equals(""))) {
				where += " AND ";
			}
			
			
			if ( srh_type != null && !"".equals(srh_type) ) {
				if("w.work_manager_name".equals(srh_type) || "w.work_manager_phone".equals(srh_type) || "w.work_manager_email".equals(srh_type) ) {
					where += "fn_decrypt(" + srh_type +")" +" LIKE '%"+ srh_text +"%' ";
				}else {
					where += srh_type +" LIKE '%"+ srh_text +"%' ";
				}
			} else {
				auth_level = StringUtil.isNullToInt(workDTO.getAdminLevel());
				
				where += " (";
				
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				where += " e.employer_name         LIKE '%"+ srh_text +"%'";
				where += " OR w.work_name          LIKE '%"+ srh_text +"%'";
				where += " OR w.work_addr          LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(w.work_manager_name)  LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(w.work_manager_phone) LIKE '%"+ srh_text +"%'";
				where += " OR w.work_manager_fax   LIKE '%"+ srh_text +"%'";
				where += " OR fn_decrypt(w.work_manager_email) LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			workDTO.setWhere(where);
		}

		int rowCount = workService.getWorkTotalCnt(workDTO);
		
		workDTO.getPaging().setRowCount(rowCount);
		
		List<WorkDTO> result = workService.getWorkList(workDTO);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
		jsonObj.put("page",    workDTO.getPaging().getPageNo());                    // 현재페이지
		jsonObj.put("total",   workDTO.getPaging().getTotalPageCnt());              // 총페이지수
		jsonObj.put("records", workDTO.getPaging().getRowCount());                  // 총글목록수

		return jsonObj;
	}

	// 행 추가
	@RequestMapping(value="/setWorkCell")
	@ResponseBody
	public JSONObject setWorkCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		workDTO = setSessionToDTO(session, workDTO);
		
		workService.setWorkCell(workDTO);
		
		jsonObj.put("work_seq", workDTO.getWork_seq());                             // 성공
		
		return jsonObj;
	}
	
	// 변경된 행 저장
	@RequestMapping(value="/setWorkInfo")
	@ResponseBody
	public JSONObject setWorkInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		workDTO = setSessionToDTO(session, workDTO);

		if("employer_name".equals(workDTO.getCellname())) {
			EmployerDTO employerDTO = new EmployerDTO();
			
			employerDTO.setEmployer_seq(workDTO.getEmployer_seq());
			employerDTO.setWhere("");
			
			employerDTO = employerService.getEmployerView(employerDTO);
			
			workDTO.setReceive_contract_seq(employerDTO.getReceive_contract_seq());
			workDTO.setLabor_contract_seq(employerDTO.getLabor_contract_seq());
			
			jsonObj.put("receive_contract_seq", workDTO.getReceive_contract_seq());
			jsonObj.put("labor_contract_seq", workDTO.getLabor_contract_seq());
		}
		
		workService.setWorkInfo(workDTO);
    
		if("manager_name".equals(workDTO.getCellname()) ) {
			if("0".equals(workDTO.getWm_seq()) ) {
				ManagerDTO managerDTO = new ManagerDTO();
				managerDTO.setWork_seq(workDTO.getWork_seq());
				managerDTO.setEmployer_seq(workDTO.getEmployer_seq());
				managerDTO.setManager_seq(workDTO.getManager_seq());
				managerDTO.setReg_admin(workDTO.getReg_admin());
				managerDTO.setMod_admin(workDTO.getReg_admin());
        		
				managerService.insertRelationM(managerDTO);
    		
				jsonObj.put("wm_seq",   managerDTO.getWm_seq()); 
			}else {
				if("0".equals(workDTO.getManager_seq())) {
					ManagerDTO managerDTO = new ManagerDTO();
					managerDTO.setWm_seq(workDTO.getWm_seq());
					managerService.deleteRelationM(managerDTO);
					jsonObj.put("wm_seq",   "0"); 
				}else {
					ManagerDTO managerDTO = new ManagerDTO();
					managerDTO.setWm_seq(workDTO.getWm_seq());
					managerDTO.setManager_seq(workDTO.getManager_seq());
	        	
					managerService.updateWm(managerDTO);
				}
			}
		}
    
		if("employer_name".equals(workDTO.getCellname()) ) {
			if(!"0".equals(workDTO.getWm_seq()) ){
				ManagerDTO managerDTO = new ManagerDTO();
				managerDTO.setWm_seq(workDTO.getWm_seq());
				managerDTO.setEmployer_seq(workDTO.getEmployer_seq());
				
				managerService.updateRelation(managerDTO);
			}
		}
		
		if("accident_use_yn".equals(workDTO.getCellname())) {
			LaborContractDTO laborContractDTO = new LaborContractDTO();

			WorkDTO workInfo = new WorkDTO();
			
			workInfo.setWork_seq(workDTO.getWork_seq());
			
			workInfo = workService.getSelectInfo(workInfo);
			
			laborContractDTO.setLabor_contract_use_yn(workInfo.getLabor_contract_seq());
			
			laborContractDTO.setReg_admin(workDTO.getReg_admin());
			
			workService.insertLaborContractLog(laborContractDTO);
		}

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeWorkInfo")
	@ResponseBody
	public JSONObject removeWorkInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		workDTO = setSessionToDTO(session, workDTO);

		workService.removeWorkInfo(workDTO);

		return jsonObj;
	}
	
	@RequestMapping(value="/workListExcel")
	@ResponseBody
	public View WorkListExcel(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		int auth_level;
		String companyName = "";
		String where    = workDTO.getWhere();
		String srh_type = workDTO.getSrh_type();
		String srh_text = workDTO.getSrh_text();
    
		workDTO.setSrh_use_yn("1");
		workDTO.setSidx("work_seq");
		workDTO.setSord("desc");

		// Session 설정
		workDTO = setSessionToDTO(session, workDTO);
		if( !"".equals(workDTO.getCompany_seq()) && workDTO.getCompany_seq() != null ) {
			CompanyDTO com = new CompanyDTO();
			com.setCompany_seq(workDTO.getCompany_seq());
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
				auth_level = StringUtil.isNullToInt(workDTO.getAdminLevel());
				
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

			workDTO.setWhere(where);
		}

		int rowCount = workService.getWorkTotalCnt(workDTO);

		workDTO.getPaging().setEndPage(rowCount);
	
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String fileName = companyName + "현장관리_" + format.format(today) + ".xlsx";
		List<WorkDTO> resultList = workService.getWorkList(workDTO);
	
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("managerDTO", workDTO);
		model.addAttribute("resultList", resultList);
	
		return new WorkListExcel();
	}
  
  	/**
  	  * @Method Name : workMerge
  	  * @작성일 : 2021. 8. 24.
  	  * @작성자 : Park YunSoo
  	  * @필수 param : 
  	  * @선택 param :
  	  * @Method 설명 : 현장 병합 팝업
  	  */
  	@RequestMapping("/workMerge")
	public String workMerge(Model model, WorkDTO workDTO) {
		List<WorkDTO> workList = workService.selectInList(workDTO);
		model.addAttribute("workList", workList);

		return "/admin/popup/workMerge.popup_admin";
	}
  	
  	/**
  	  * @Method Name : setWorkMerge
  	  * @작성일 : 2021. 8. 24.
  	  * @작성자 : Park YunSoo
  	  * @필수 param : 
  	  * @선택 param :
  	  * @Method 설명 : 현장 병합 process
  	  */
  	@RequestMapping(value="/setWorkMerge")
	@ResponseBody
	public JSONObject setWorkMerge(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		jsonObj = workService.setWorkMerge(workDTO, session);

		return jsonObj;
	}
}
