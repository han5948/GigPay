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
import com.nemo.kr.dto.AutoSettingDTO;
import com.nemo.kr.dto.OnOffDTO;
import com.nemo.kr.service.AutoSettingService;
import com.nemo.kr.service.OnOffService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/autoSetting")
public class AutoSettingController {
	private static final Logger logger = LoggerFactory.getLogger(AutoSettingController.class);
	
	@Autowired AutoSettingService autoSettingService; 
	@Autowired OnOffService onOffService;
	
	@RequestMapping("/autoSettingView")
	public String ilboWorkOption(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "AI 배정 설정");
		
		List<AutoSettingDTO> autoSettingList = autoSettingService.selectAutoSettingList();
		model.addAttribute("autoSettingList", autoSettingList);
		
		OnOffDTO onOffDTO = new OnOffDTO();
		onOffDTO.setFunction_type("A");
		model.addAttribute("onOffDTO", onOffService.getOnOffInfo(onOffDTO));
		
		return "/admin/autoSetting/autoSettingView.leftAdmin";
	}
	
	@RequestMapping(value = "/onOff")
	@ResponseBody
	public Map<String, Object> onOff(OnOffDTO onOffDTO){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			onOffService.setOnOffInfo(onOffDTO);
			
			result.put("code", Const.CODE_SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("{}", e);
			result.put("code", Const.CODE_SYSTEM_ERROR);
			result.put("message", Const.MSG_SYSTEM_ERROR);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateAutoSetting")
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
			List<AutoSettingDTO> autoSettingList = new ArrayList<AutoSettingDTO>();
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			
			paramList = JSONArray.fromObject(data);
			Gson gson = new Gson();
			AutoSettingDTO autoSettingDTO = new AutoSettingDTO();
			
			for(int i = 0; i < paramList.size(); i++) {
				autoSettingDTO = gson.fromJson(paramList.get(i).toString(), AutoSettingDTO.class);
				autoSettingDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
				
				autoSettingList.add(autoSettingDTO);
			}
			
			autoSettingService.insertAutoSetting(autoSettingList);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
