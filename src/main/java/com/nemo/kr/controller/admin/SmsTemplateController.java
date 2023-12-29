package com.nemo.kr.controller.admin;

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
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.SmsTemplateDTO;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.SmsTemplateService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/smsTemplate")
public class SmsTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired SmsTemplateService smsTemplateService;
	@Autowired CompanyService companyService;
	
	// Session 설정
	private SmsTemplateDTO setSessionToDTO(HttpSession session, SmsTemplateDTO smsTemplateDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		// Session 설정
		smsTemplateDTO.setAdminLevel(""+ auth_level);
		smsTemplateDTO.setReg_admin(admin_id);
		
		return smsTemplateDTO;
	}
	
	/**
	 * 
	  * @throws Exception 
	 * @Method Name : smsTemplateList
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param : 
	  * @Method 설명 : SMS 템플릿 리스트
	 */
	@RequestMapping("/smsTemplateList")
	public String smsTemplateList(HttpServletRequest request, HttpSession session, Model model, SmsTemplateDTO smsTemplateDTO) throws Exception {
		smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
		smsTemplateDTO.getPaging().setPageSize(15);
		
		if(smsTemplateDTO.getUse_yn() == null)
			smsTemplateDTO.setUse_yn("1");
		
		if(!smsTemplateDTO.getAdminLevel().equals("0")) 
			smsTemplateDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		
		int cnt = smsTemplateService.selectListCnt(smsTemplateDTO);
		
		if(cnt > 0) {
			List<SmsTemplateDTO> smsTemplateList = smsTemplateService.selectList(smsTemplateDTO);
			
			model.addAttribute("smsTemplateList", smsTemplateList);
		}
		
		model.addAttribute("htmlHeader", "SMS 템플릿 관리");
		
		model.addAttribute("smsTemplateDTO", smsTemplateDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("leftMenu", "SMS");
		
		return "/admin/smsTemplate/smsTemplateList.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : getSmsTemplateList
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param : start_date, end_date, use_yn
	  * @Method 설명 : ajax 그룹 코드 리스트 
	 */
	@RequestMapping("/getSmsTemplateList")
	@ResponseBody
	public Map<String, Object> getSmsTemplateList(HttpSession session, SmsTemplateDTO smsTemplateDTO) throws Exception {
		smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
		smsTemplateDTO.getPaging().setPageSize(15);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			if(!smsTemplateDTO.getAdminLevel().equals("0")) 
				smsTemplateDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
			
			int cnt = smsTemplateService.selectListCnt(smsTemplateDTO);
			
			List<SmsTemplateDTO> smsTemplateList = smsTemplateService.selectList(smsTemplateDTO);
		
			resultMap.put("smsTemplateList", smsTemplateList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("smsTemplateDTO", smsTemplateDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : smsTemplateWrite
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param :  
	  * @선택 param :
	  * @Method 설명 : 그룹 코드 추가 페이지 이동
	 */
	@RequestMapping("/smsTemplateWrite")
	public String smsTemplateWrite(HttpSession session, Model model, SmsTemplateDTO smsTemplateDTO) throws Exception {
		smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
		
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		model.addAttribute("htmlHeader", "SMS 템플릿 추가");
		model.addAttribute("companyList", companyList);
		model.addAttribute("smsTemplateDTO", smsTemplateDTO);
		model.addAttribute("leftMenu", "SMS");
		
		return "/admin/smsTemplate/smsTemplateWrite.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : insertSmsTemplate
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param : template_title, template_content, use_yn, company_seq
	  * @선택 param : 
	  * @Method 설명 : SMS 템플릿 추가 로직
	 */
	@RequestMapping("/insertSmsTemplate")
	@ResponseBody
	public Map<String, Object> insertSmsTemplate(HttpServletRequest request, HttpServletResponse response, HttpSession session, SmsTemplateDTO smsTemplateDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
			
			if(!smsTemplateDTO.getAdminLevel().equals("0")) 
				smsTemplateDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
			
			smsTemplateService.insertSmsTemplate(smsTemplateDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : selectInfo
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param : template_seq 
	  * @선택 param :
	  * @Method 설명 : SMS 템플릿 상세
	 */
	@RequestMapping("/selectInfo")
	public String selectInfo(HttpSession session, Model model, SmsTemplateDTO smsTemplateDTO) throws Exception {
		smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
		
		SmsTemplateDTO smsTemplateInfo = smsTemplateService.selectInfo(smsTemplateDTO);
		
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		model.addAttribute("smsTemplateDTO", smsTemplateDTO);
		model.addAttribute("htmlHeader", "SMS 템플릿 수정");
		model.addAttribute("smsTemplateInfo", smsTemplateInfo);
		model.addAttribute("companyList", companyList);
		model.addAttribute("leftMenu", "SMS");
		
		if(smsTemplateDTO.getAdminLevel().equals("0") || smsTemplateDTO.getReg_admin().equals(smsTemplateInfo.getReg_admin())) {
			return "/admin/smsTemplate/smsTemplateEdit.leftAdmin";
		}else {
			return "/admin/smsTemplate/smsTemplateDetail.leftAdmin";
		}
	}
	
	/**
	 * 
	  * @Method Name : updateSmsTemplate
	  * @작성일 : 2020. 6. 23.
	  * @작성자 : Park YunSoo
	  * @필수 param : template_title, template_content, use_yn 
	  * @선택 param :
	  * @Method 설명 : SMS 템플릿 수정
	 */
	@RequestMapping("updateSmsTemplate")
	@ResponseBody
	public Map<String, Object> updateSmsTemplate(HttpServletRequest request, HttpServletResponse response, HttpSession session, SmsTemplateDTO smsTemplateDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
			
			if(smsTemplateDTO.getCompany_seq() == null)
				smsTemplateDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
			
			smsTemplateService.updateSmsTemplate(smsTemplateDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : deleteGroupCode
	  * @작성일 : 2020. 6. 17.
	  * @작성자 : Park YunSoo
	  * @필수 param : template_seq 
	  * @선택 param :
	  * @Method 설명 : 그룹 삭제
	 */
	@RequestMapping("deleteGroupCode")
	@ResponseBody
	public Map<String, Object> deleteGroupCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, SmsTemplateDTO smsTemplateDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			smsTemplateDTO = setSessionToDTO(session, smsTemplateDTO);
			
			smsTemplateService.deleteSmsTemplate(smsTemplateDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
