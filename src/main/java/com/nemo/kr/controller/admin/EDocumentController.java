package com.nemo.kr.controller.admin;

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

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CategoryDTO;
import com.nemo.kr.dto.SignHistoryDTO;
import com.nemo.kr.dto.SignManagementDTO;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.service.EDocumentService;

@Controller
@RequestMapping("/admin")
public class EDocumentController {
	private static final Logger logger = LoggerFactory.getLogger(EDocumentController.class);
	
	@Autowired EDocumentService eDocumentService;
	@Autowired ContractService contractService;
	
	@RequestMapping("/eDocumentList")
	public String eDocumentList(HttpSession session, Model model) {
		CategoryDTO categoryDTO = new CategoryDTO();
		
		List<CategoryDTO> categoryList = contractService.selectCategoryList(categoryDTO); 
		
		model.addAttribute("htmlHeader", "전자문서 관리");
		model.addAttribute("categoryList", categoryList);
		
		return "/admin/eDocument/eDocumentList.leftAdmin";
	}
	
	@RequestMapping("/getEDocumentList")
	@ResponseBody
	public JSONObject getEDocumentList(SignManagementDTO signManagementDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// 전자서명 확인 리스트 total row count
		int cnt = eDocumentService.selectEDocumentListCnt(signManagementDTO);
		signManagementDTO.getPaging().setRowCount(cnt);
		
		if( cnt > 0 ) {
			// 전자서명 확인 리스트 조회
			List<SignManagementDTO> resultList = eDocumentService.selectEDocumentList(signManagementDTO);
			jsonObj.put("rows", resultList);
		}
		jsonObj.put("total",   signManagementDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", signManagementDTO.getPaging().getRowCount());              // 총글목록수
		
		return jsonObj;
	}
	
	@RequestMapping("/getHistoryList")
	@ResponseBody
	public JSONObject getHistoryList(HttpServletRequest request, HttpServletResponse response, HttpSession session, SignHistoryDTO signHistoryDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		try {
			List<SignHistoryDTO> resultList = eDocumentService.getHistoryList(signHistoryDTO);
			SignHistoryDTO resultDTO = new SignHistoryDTO();
			
			resultDTO.setManagement_seq(signHistoryDTO.getManagement_seq());
			resultDTO.setCode_value("ESH006");
			
			List<SignHistoryDTO> boolList = eDocumentService.getHistoryList(resultDTO);

			jsonObj.put("code", "0000");
			jsonObj.put("resultList", resultList);
			jsonObj.put("boolSize", boolList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonObj.put("code", "9999");
			jsonObj.put("message", "오류가 발생했습니다.");
		}

		return jsonObj;
	}
	
	@RequestMapping("/getManagementPdf")
	@ResponseBody
	public JSONObject getManagementPdf(HttpServletRequest request, HttpServletResponse response, HttpSession session, SignManagementDTO signManagementDTO) {
		JSONObject jsonObj = new JSONObject();
		
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
			
			jsonObj.put("code", "9999");
			jsonObj.put("message", "오류가 발생했습니다.");
		}
		
		return jsonObj;
	}
}
