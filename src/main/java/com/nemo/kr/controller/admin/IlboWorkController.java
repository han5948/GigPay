package com.nemo.kr.controller.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.GradeFeeDTO;
import com.nemo.kr.dto.IlboWorkOptionDTO;
import com.nemo.kr.dto.JobLimitCountDTO;
import com.nemo.kr.dto.ViewTimeDTO;
import com.nemo.kr.service.GradeFeeService;
import com.nemo.kr.service.IlboWorkService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/ilboWork")
public class IlboWorkController {
	private static final Logger logger = LoggerFactory.getLogger(IlboWorkController.class);
	
	@Autowired IlboWorkService ilboWorkService;
	@Autowired GradeFeeService gradeFeeService;
	
	@RequestMapping("/ilboWorkOption")
	public String ilboWorkOption(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "맞춤일자리 설정");
		
		IlboWorkOptionDTO ilboWorkOptionDTO = new IlboWorkOptionDTO();
		
		List<IlboWorkOptionDTO> optionList = ilboWorkService.selectOptionList(ilboWorkOptionDTO); 
		
		model.addAttribute("optionList", optionList);
		model.addAttribute("work_limit_count", optionList.get(0).getWork_limit_count());
		model.addAttribute("employer_limit_count", optionList.get(0).getEmployer_limit_count());
		model.addAttribute("complete_score", optionList.get(0).getComplete_score());
		model.addAttribute("re_score", optionList.get(0).getRe_score());
		model.addAttribute("punk_score", optionList.get(0).getPunk_score());
		model.addAttribute("eva_score", optionList.get(0).getEva_score());
		
		List<GradeFeeDTO> gradeList = gradeFeeService.selectGradeFeeList();
		model.addAttribute("gradeList", gradeList);
		
		JobLimitCountDTO imsJobLimitCountDTO = ilboWorkService.selectJobLimitCount("IMS");
		model.addAttribute("imsLimitCountDTO", imsJobLimitCountDTO);
		
		JobLimitCountDTO wJobLimitCountDTO = ilboWorkService.selectJobLimitCount("W");
		model.addAttribute("wLimitCountDTO", wJobLimitCountDTO);
		
		ViewTimeDTO viewTimeDTO = ilboWorkService.selectViewTime();
		model.addAttribute("viewTimeDTO", viewTimeDTO);
		
		int viewTimeCnt = ilboWorkService.selectViewTimeCnt();
		model.addAttribute("viewTimeCnt", viewTimeCnt);
		
		return "/admin/ilboWork/ilboWorkOption.leftAdmin";
	}
	
	@RequestMapping(value = "/updateOption")
	@ResponseBody
	public Map<String, Object> updateOption(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String buffer;
        while ((buffer = input.readLine()) != null) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(buffer);
        }
		
        String data = builder.toString();
        
		try {
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			
			paramList = JSONArray.fromObject(data);
			Gson gson = new Gson();
			IlboWorkOptionDTO ilboWorkOptionDTO = new IlboWorkOptionDTO();
			
			for(int i = 0; i < paramList.size(); i++) {
				ilboWorkOptionDTO = gson.fromJson(paramList.get(i).toString(), IlboWorkOptionDTO.class);
				
				ilboWorkOptionDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
				
				if(ilboWorkOptionDTO.getData_flag().equals("1")) {
					ilboWorkService.updateOption(ilboWorkOptionDTO);
				}else {
					ilboWorkService.insertOption(ilboWorkOptionDTO);
				}
			}
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateGradePrice")
	@ResponseBody
	public Map<String, Object> updateGradePrice(HttpServletRequest request, HttpServletResponse response, HttpSession session, GradeFeeDTO gradeFeeDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			gradeFeeService.updateGradeFee(gradeFeeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateJobLimitCount")
	@ResponseBody
	public Map<String, Object> updateJobLimitCount(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobLimitCountDTO jobLimitCountDTO) {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			jobLimitCountDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			ilboWorkService.updateJobLimitCount(jobLimitCountDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
//	@RequestMapping("/insertJobLimitCount")
//	@ResponseBody
//	public Map<String, Object> insertJobLimitCount(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobLimitCountDTO jobLimitCountDTO) {
//		HashMap<String, Object> resultMap = new HashMap<>();
//		
//		try {
//			int cnt = ilboWorkService.selectJobLimitCountCnt();
//			
//			if(cnt == 0) {
//				jobLimitCountDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
//				
//				ilboWorkService.insertJobLimitCount(jobLimitCountDTO);
//			}
//			
//			resultMap.put("code", Const.CODE_SUCCESS);
//		}catch(Exception e) {
//			e.printStackTrace();
//			
//			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
//			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
//		}
//		
//		return resultMap;
//	}
	
	@RequestMapping("/insertViewTime")
	@ResponseBody
	public Map<String, Object> insertViewTime(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewTimeDTO viewTimeDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			viewTimeDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			viewTimeDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			ilboWorkService.insertViewTime(viewTimeDTO);
			
			int viewTimeCnt = ilboWorkService.selectViewTimeCnt();
			resultMap.put("viewTimeCnt", viewTimeCnt);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateViewTime")
	@ResponseBody
	public Map<String, Object> updateViewTime(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewTimeDTO viewTimeDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			viewTimeDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			ilboWorkService.updateViewTime(viewTimeDTO);
			
			int viewTimeCnt = ilboWorkService.selectViewTimeCnt();
			resultMap.put("viewTimeCnt", viewTimeCnt);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/taxRateOption")
	public String taxRateOption(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "세금 및 사회보험");
		
		return "/admin/taxRateOption.leftAdmin";
	}
}
