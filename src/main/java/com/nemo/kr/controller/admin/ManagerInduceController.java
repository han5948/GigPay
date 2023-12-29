package com.nemo.kr.controller.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.ManagerInduceDTO;
import com.nemo.kr.dto.ManagerSubInduceDTO;
import com.nemo.kr.service.ManagerInduceService;
import com.nemo.kr.service.ManagerService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/managerInduce")
public class ManagerInduceController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerInduceController.class);
	
	@Autowired ManagerInduceService managerInduceService;
	@Autowired ManagerService managerService;
	
	@RequestMapping("/managerInduceList")
	public String managerInduceList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "도출유도 관리");
		
		return "/admin/managerInduce/managerInduceList.leftAdmin";
	}
	
	@RequestMapping("/getManagerInduceList")
	@ResponseBody
	public Map<String, Object> getManagerInduceList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerInduceDTO managerInduceDTO) throws Exception {
		String where = managerInduceDTO.getWhere();
		
		int rowCount = managerInduceService.selectManagerInduceListCnt(managerInduceDTO);

		managerInduceDTO.getPaging().setRowCount(rowCount);

		List<ManagerInduceDTO> result = managerInduceService.selectManagerInduceList(managerInduceDTO);

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             				// 현재페이지의 목록데이터
		jsonObj.put("page",    managerInduceDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   managerInduceDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", managerInduceDTO.getPaging().getRowCount());              // 총글목록수

		return jsonObj;
	}
	
	@RequestMapping("/getManagerSubList")
	@ResponseBody
	public Map<String, Object> getManagerSubList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerSubInduceDTO managerSubInduceDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		List<ManagerSubInduceDTO> result = managerInduceService.selectManagerSubInduceList(managerSubInduceDTO);
		
		jsonObj.put("rows", result);
		
		return jsonObj;
	}
	
	@RequestMapping("/updateManagerInduce")
	@ResponseBody
	public JSONObject updateManagerInduce(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerInduceDTO managerInduceDTO, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		
		if(managerInduceDTO.getCellname().equals("manager_use_status")) {
			managerDTO.setReg_admin(admin_id);
			
			managerService.setManagerInfo(managerDTO);
		}else {
			managerInduceDTO.setMod_admin(admin_id);
			
			managerInduceService.updateManagerInduce(managerInduceDTO);
		}
		
		jsonObj.put("result", "0000");
		
		return jsonObj;
	}
	
	@RequestMapping("/deleteManagerInduce")
	@ResponseBody
	public JSONObject deleteManagerInduce(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerInduceDTO managerInduceDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		try {
			managerInduceDTO.setMod_admin(admin_id);
			
			managerInduceService.deleteManagerInduce(managerInduceDTO);
			jsonObj.put("result", Const.CODE_SUCCESS);
		}catch(Exception e) {
			jsonObj.put("result", Const.CODE_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
}
