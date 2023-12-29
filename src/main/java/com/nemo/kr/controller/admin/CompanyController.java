package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.SmsService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * 일가자 회사관리 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
@Controller
@RequestMapping("/admin")
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired CompanyService companyService;
	@Autowired CommonService commonService;
	@Autowired SmsService smsService;

	// Session 설정
	private CompanyDTO setSessionToDTO(HttpSession session, CompanyDTO companyDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(companyDTO.getCompany_seq());

		// Session 설정
		companyDTO.setAdminLevel(""+ auth_level);
//    companyDTO.setSrh_company_seq(company_seq);
		companyDTO.setReg_admin(admin_id);

		return companyDTO;
	}

	@RequestMapping(value="/companyList")
	public String companyList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "지점관리");
		
		return "/admin/companyList.leftAdmin";
	}

	@RequestMapping(value="/getCompanyList")
	@ResponseBody
	public JSONObject getCompanyList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CompanyDTO companyDTO) throws Exception {
		String where    = companyDTO.getWhere();
		String srh_type = companyDTO.getSrh_type();
		String srh_text = companyDTO.getSrh_text();

		// Session 설정
		companyDTO = setSessionToDTO(session, companyDTO);

		if ( srh_text != null && !"".equals(srh_text) ) {
			if ( "".equals(where) ) {
				where = " AND ";
			}

			if ( srh_type != null && !"".equals(srh_type) ) {
				where += srh_type +" LIKE '%"+ srh_text +"%'";
			} else {
				where += " (";
				where += " company_name  LIKE '%"+ srh_text +"%'";
				where += " OR company_owner LIKE '%"+ srh_text +"%'";
				where += " OR company_num   LIKE '%"+ srh_text +"%'";
				where += " OR company_email LIKE '%"+ srh_text +"%'";
				where += " OR company_phone LIKE '%"+ srh_text +"%'";
				where += " OR company_tel   LIKE '%"+ srh_text +"%'";
				where += " OR company_addr  LIKE '%"+ srh_text +"%'";
				where += " )";
			}

			companyDTO.setWhere(where);
		}

		int rowCount = companyService.getCompanyTotalCnt(companyDTO);
		
		companyDTO.getPaging().setRowCount(rowCount);
		
		List<CompanyDTO> result = companyService.getCompanyList(companyDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
		jsonObj.put("page",    companyDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   companyDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", companyDTO.getPaging().getRowCount());               // 총글목록수

		return jsonObj;
	}

	// 행 추가
	@RequestMapping(value="/setCompanyCell")
	@ResponseBody
	public JSONObject setCompanyCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, CompanyDTO companyDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		companyDTO = setSessionToDTO(session, companyDTO);

		companyService.setCompanyCell(companyDTO);

		if(companyDTO.getCompany_phone() != null)
			session.setAttribute("company_phone", companyDTO.getCompany_phone());
    
		jsonObj.put("company_seq", companyDTO.getCompany_seq());                    // 성공

		return jsonObj;
	}

	// 변경된 행 저장
	@RequestMapping(value="/setCompanyInfo")
	@ResponseBody
	public JSONObject setCompanyInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, CompanyDTO companyDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
    
		// Session 설정
		companyDTO = setSessionToDTO(session, companyDTO);

		try {
			jsonObj.put("code", Const.CODE_SUCCESS);
    	
			companyService.setCompanyInfo(companyDTO);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("msg", Const.MSG_SYSTEM_ERROR);
		}

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeCompanyInfo")
	@ResponseBody
	public JSONObject removeCompanyInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, CompanyDTO companyDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		companyDTO = setSessionToDTO(session, companyDTO);

		companyService.removeCompanyInfo(companyDTO);

		return jsonObj;
	}
  
	@RequestMapping("/phoneAdd")
	public String phoneAdd(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String company_seq = "";
		String admin_seq = "";
		String page_flag = request.getParameter("page_flag");
	  
		if(page_flag.equals("C")) {
			company_seq = request.getParameter("phone_company_seq");
			model.addAttribute("company_seq", company_seq);
		}else if(page_flag.equals("A")) {
			admin_seq = request.getParameter("phone_admin_seq");
			model.addAttribute("admin_seq", admin_seq);
		}
	  
		model.addAttribute("page_flag", page_flag);
		model.addAttribute("htmlHeader", "폰번호 등록");
	  
		return "/admin/phoneAdd";
	}

	@RequestMapping("/certNum")
	@ResponseBody
	public Map<String, Object> certNum(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
	  
		String company_phone = request.getParameter("company_phone");
	  
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			Random rand = new Random();
			String numStr = "";
			
			for(int i = 0; i < 4; i++) {
				String ran = Integer.toString(rand.nextInt(10));
				
				numStr += ran;
			}
			
			SmsDTO smsDTO = new SmsDTO();
			String tr_msg = "발신번호 등록 인증번호 [" + numStr + "]";
			
			smsDTO.setTr_msg(tr_msg);
			smsDTO.setTr_phone(company_phone);
			
			smsService.insertInfo(smsDTO);
			
			resultMap.put("certNum", numStr);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
	  
		return resultMap;
	}
}
