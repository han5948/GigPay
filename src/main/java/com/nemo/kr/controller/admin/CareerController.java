package com.nemo.kr.controller.admin;

import java.util.HashMap;
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
import com.nemo.kr.dto.CareerDTO;
import com.nemo.kr.service.CareerService;

@Controller
@RequestMapping("/admin/career")
public class CareerController {
	private static final Logger logger = LoggerFactory.getLogger(CareerController.class);
	
	@Autowired CareerService careerService;
	
	@RequestMapping("/careerOption")
	public String careerOption(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "경력옵션");
		
		CareerDTO careerDTO = careerService.selectCareerOption();
		
		model.addAttribute("careerDTO", careerDTO);
		
		return "/admin/career/careerOption.leftAdmin";
	}
	
	@RequestMapping("/insertCareer")
	@ResponseBody
	public Map<String, Object> insertCareer(HttpServletRequest request, HttpServletResponse response, HttpSession session, CareerDTO careerDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			careerDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			careerService.insertCareer(careerDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateCareer")
	@ResponseBody
	public Map<String, Object> updateCareer(HttpServletRequest request, HttpServletResponse response, HttpSession session, CareerDTO careerDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			careerDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			careerService.updateCareer(careerDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/selectCareer")
	@ResponseBody
	public Map<String, Object> selectCareer(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			CareerDTO careerDTO = careerService.selectCareerOption();
			
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("careerDTO", careerDTO);
		}catch(Exception e) {
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
