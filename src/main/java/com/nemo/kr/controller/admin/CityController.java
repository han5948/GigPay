package com.nemo.kr.controller.admin;

import java.util.HashMap;
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
import com.nemo.kr.dto.CityDTO;
import com.nemo.kr.service.CityService;

@Controller
@RequestMapping("/admin")
public class CityController {
	private static final Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired CityService cityService;

	@RequestMapping(value="/cityList")
	public String cityList(HttpSession session, Model model) {
		CityDTO cityDTO = new CityDTO();
		cityDTO.getPaging().setPageSize(30);
		int cnt = cityService.getCityListCnt(cityDTO);
		if( cnt > 0 ) {
			List<CityDTO> cityList = cityService.getCityList(cityDTO);
			model.addAttribute("cityList", cityList);
		}
		model.addAttribute("cityDTO", cityDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader" , "날씨지역 관리");
		return "/admin/cityList.leftAdmin";
	}

	@RequestMapping(value="/getCityList")
	@ResponseBody
	public HashMap<String, Object> getCityList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CityDTO cityDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		cityDTO.getPaging().setPageSize(30);
		int cnt = cityService.getCityListCnt(cityDTO);
		if( cnt > 0 ) {
			List<CityDTO> cityList = cityService.getCityList(cityDTO);
			resultMap.put("cityList", cityList);
		}
		resultMap.put("cityDTO", cityDTO);
		resultMap.put("totalCnt", cnt);
		
		return resultMap;
	}
	
	@RequestMapping(value="/setCityDTO")
	@ResponseBody
	public HashMap<String, Object> setCityDTO(HttpServletRequest request, HttpServletResponse response, HttpSession session, CityDTO cityDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			cityService.updateCityDTO(cityDTO);
			
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
