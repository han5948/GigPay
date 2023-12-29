package com.nemo.kr.controller.admin;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdDTO;
import com.nemo.kr.dto.InquiryDTO;
import com.nemo.kr.dto.ManagerInduceDTO;
import com.nemo.kr.service.InquiryService;

@Controller
@RequestMapping("/admin/inquiry")
public class InquiryController {
	private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
	
	@Autowired InquiryService inquiryService;
	
	@RequestMapping("/inquiryList")
	public String inquiryList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "기성문의 내역");
		
		return "/admin/inquiry/inquiryList.leftAdmin";
	}
	
	@RequestMapping("/selectInquiryList")
	@ResponseBody
	public Map<String, Object> selectInquiryList(HttpServletRequest request, HttpServletResponse response, HttpSession session, InquiryDTO inquiryDTO) throws Exception {
		int rowCount = inquiryService.selectInquiryListCnt(inquiryDTO);

		inquiryDTO.getPaging().setRowCount(rowCount);

		List<InquiryDTO> result = inquiryService.selectInquiryList(inquiryDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             				// 현재페이지의 목록데이터
		jsonObj.put("page",    inquiryDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   inquiryDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", inquiryDTO.getPaging().getRowCount());              // 총글목록수
		
		return jsonObj;
	}
	
	@RequestMapping("/updateInquiry")
	@ResponseBody
	public Map<String, Object> updateInquiry(HttpServletRequest request, HttpServletResponse response, HttpSession session, InquiryDTO inquiryDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		try {
			inquiryDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			inquiryService.updateInquiry(inquiryDTO);
			
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			jsonObj.put("code", "9999");
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/deleteInquiry")
	@ResponseBody
	public Map<String, Object> deleteInquiry(HttpServletRequest request, HttpServletResponse response, HttpSession session, InquiryDTO inquiryDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		try {
			inquiryDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			inquiryService.deleteInquiry(inquiryDTO);
			
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			jsonObj.put("code", "9999");
		}
		
		return jsonObj;
	}
}
