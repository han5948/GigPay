package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.IlboSettingDTO;
import com.nemo.kr.service.IlboSettingService;

/**
  * @FileName : IlboSettingController.java
  * @Date : 2020. 9. 7. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 :
  */
@Controller
@RequestMapping("/admin/ilboSetting")
public class IlboSettingController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired IlboSettingService ilboSettingService;
	
	/**
	  * @Method Name : ilboSetting
	  * @작성일 : 2020. 9. 7.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : 일일대장 설정 화면 
	  */
	@RequestMapping("/ilboSettingInfo")
	public String ilboSetting(HttpSession session, Model model) {
		IlboSettingDTO ilboSettingDTO = new IlboSettingDTO();
		
		ilboSettingDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		
		ilboSettingDTO = ilboSettingService.selectSettingInfo(ilboSettingDTO);
		
		if(ilboSettingDTO == null) {
			ilboSettingDTO = new IlboSettingDTO();
			
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
		}
		
		model.addAttribute("ilboSettingDTO", ilboSettingDTO);
		model.addAttribute("htmlHeader", "일일대장 설정");
		
		return "/admin/ilboSetting/ilboSetting.leftAdmin";
	}
	
	@RequestMapping("/updateIlboSetting")
	@ResponseBody
	public Map<String, Object> updateIlboSetting(HttpSession session, IlboSettingDTO ilboSettingDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			ilboSettingDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			ilboSettingDTO.setSetting_mod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			IlboSettingDTO ilboSettingDTO1 = new IlboSettingDTO();
			
			ilboSettingDTO1.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			
			ilboSettingDTO1 = ilboSettingService.selectSettingInfo(ilboSettingDTO);
			
			if(ilboSettingDTO1 == null) {
				ilboSettingService.insertIlboSetting(ilboSettingDTO);
			}else {
				ilboSettingService.updateIlboSetting(ilboSettingDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateIlboSettingReset")
	@ResponseBody
	public Map<String, Object> updateIlboSettingReset(HttpSession session, IlboSettingDTO ilboSettingDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			ilboSettingDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			ilboSettingDTO.setSetting_mod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			IlboSettingDTO ilboSettingDTO1 = new IlboSettingDTO();
			
			ilboSettingDTO1.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			
			ilboSettingDTO1 = ilboSettingService.selectSettingInfo(ilboSettingDTO);
			
			if(ilboSettingDTO1 == null) {
				ilboSettingService.insertIlboSetting(ilboSettingDTO);
			}else {
				ilboSettingService.updateIlboSettingReset(ilboSettingDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
