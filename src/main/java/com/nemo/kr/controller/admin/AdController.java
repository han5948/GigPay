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
import com.nemo.kr.dto.AdDTO;
import com.nemo.kr.service.AdService;

@Controller
@RequestMapping("/admin/ad")
public class AdController {
	private static final Logger logger = LoggerFactory.getLogger(AdController.class);
	
	@Autowired AdService adService;
	
	@RequestMapping("/adList")
	public String adList(HttpSession session, Model model) {
		AdDTO adDTO = new AdDTO();
		adDTO.getPaging().setPageSize(20);
		
		int cnt = adService.selectAdListCnt(adDTO);
		
		if(cnt > 0) {
			List<AdDTO> adList = adService.selectAdList(adDTO);
			
			model.addAttribute("adList", adList);
		}
		
		model.addAttribute("adDTO", adDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader", "광고 현황");
		
		return "/admin/ad/adList.leftAdmin";
	}
	
	@RequestMapping("/getAdList")
	@ResponseBody
	public Map<String, Object> getAdList(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdDTO adDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			int cnt = adService.selectAdListCnt(adDTO);
			
			adDTO.getPaging().setPageSize(20);
			
			if(cnt > 0) {
				List<AdDTO> adList = adService.selectAdList(adDTO);
				
				resultMap.put("adList", adList);
			}

			resultMap.put("adDTO", adDTO);
			resultMap.put("totalCnt", cnt);
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
