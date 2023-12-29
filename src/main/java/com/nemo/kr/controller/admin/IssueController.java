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
import com.nemo.kr.dto.IssueDTO;
import com.nemo.kr.service.IssueService;

@Controller
@RequestMapping("/admin/issue")
public class IssueController {
	private static final Logger logger = LoggerFactory.getLogger(IssueController.class);
	
	@Autowired
	IssueService issueService;
	
	@RequestMapping("/issueList")
	public String adList(HttpSession session, Model model) {
		IssueDTO issueDTO = new IssueDTO();
		issueDTO.getPaging().setPageSize(20);
		issueDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
		issueDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		issueDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		issueDTO.setAuth_level(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
		
		int cnt = issueService.selectIssueListCnt(issueDTO);
		
		if(cnt > 0) {
			List<IssueDTO> issueList = issueService.selectIssueList(issueDTO);
			
			model.addAttribute("issueList", issueList);
		}
		
		model.addAttribute("issueDTO", issueDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader", "이슈 관리");
		
		return "/admin/issue/issueList.leftAdmin";
	}
	
	@RequestMapping("/getIssueList")
	@ResponseBody
	public Map<String, Object> getIssueList(HttpServletRequest request, HttpServletResponse response, HttpSession session, IssueDTO issueDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			issueDTO.setAuth_level(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level());
			int cnt = issueService.selectIssueListCnt(issueDTO);
			
			issueDTO.getPaging().setPageSize(20);
			issueDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			issueDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
			issueDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			if(cnt > 0) {
				List<IssueDTO> issueList = issueService.selectIssueList(issueDTO);
				
				resultMap.put("issueList", issueList);
			}

			resultMap.put("issueDTO", issueDTO);
			resultMap.put("totalCnt", cnt);
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/selectIssueInfo")
	public String selectIssueInfo(HttpSession session, Model model, IssueDTO issueDTO) {
		issueDTO = issueService.selectIssueInfo(issueDTO);
		
		model.addAttribute("issueDTO", issueDTO);
		model.addAttribute("htmlHeader", "이슈 관리 상세");
		return "/admin/issue/issueEdit.leftAdmin";
	}
	
	@RequestMapping("deleteIssueInfo")
	@ResponseBody
	public Map<String, Object> deleteIssueInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, IssueDTO issueDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			issueService.deleteIssueInfo(issueDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("updateIssueInfo")
	@ResponseBody
	public Map<String, Object> updateIssueInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, IssueDTO issueDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			issueService.updateIssueInfo(issueDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
