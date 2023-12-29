package com.nemo.kr.controller.admin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboSettingDTO;
import com.nemo.kr.service.IlboExpireService;
import com.nemo.kr.service.IlboSettingService;
import com.nemo.kr.util.jqGridUtil;

@Controller
@RequestMapping("/admin")
public class ilboExpireController {
	@Autowired IlboExpireService ilboExpireService; // service에 요청을 위해 autowired
	@Autowired IlboSettingService ilboSettingService;
	
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
		
		return ilboSettingDTO;
	}
	
	//탈퇴회원근로이력
	@RequestMapping(value="/workerDrop")
	public String workerDrop(HttpServletRequest request, HttpSession session, Model model, IlboDTO ilboDTO) throws Exception {
		model = getIlboWidth(session, model);
		
		model.addAttribute("htmlHeader", "탈퇴회원근로이력");
		model.addAttribute("companySeq", ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		model.addAttribute("shareYn", ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getShare_yn());
		model.addAttribute("authLevel", ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
				
		return "/admin/workerDrop.leftAdmin";
	}
	
	@RequestMapping(value="/getWorkerDrop")
	@ResponseBody
	public JSONObject getWorkerDrop(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		String srh_type = ilboDTO.getSrh_type();
		String srh_text = ilboDTO.getSrh_text();
		
		if(srh_text.equals("")) {
			return jsonObj;
		}else {
			int rowCount = ilboExpireService.getIlboExpireTotalCnt(ilboDTO);
			
			ilboDTO.getPaging().setRowCount(rowCount);
			
			List<IlboDTO> result = ilboExpireService.selectList(ilboDTO);
			
			jsonObj.put("rows", result);
			jsonObj.put("page", ilboDTO.getPaging().getPageNo());
			jsonObj.put("total", ilboDTO.getPaging().getTotalPageCnt());
			jsonObj.put("records", ilboDTO.getPaging().getRowCount());
			
			return jsonObj;
		}
	}
}