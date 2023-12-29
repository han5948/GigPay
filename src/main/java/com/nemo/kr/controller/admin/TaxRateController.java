package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.TaxRateDTO;
import com.nemo.kr.service.TaxRateService;

@Controller
@RequestMapping("/admin/taxRate")
public class TaxRateController {
	private static final Logger logger = LoggerFactory.getLogger(TaxRateController.class);
	
	@Autowired TaxRateService taxRateService;
	
	@RequestMapping("/taxRateList")
	public String taxRateOption(HttpSession session, Model model) {
		model.addAttribute("htmlHeader", "세금 및 사회보험");
		
		TaxRateDTO taxRateDTO = new TaxRateDTO();
		taxRateDTO.setUse_yn("1");
		List<TaxRateDTO> taxList = taxRateService.getTaxRateList(taxRateDTO);
		model.addAttribute("taxListSize", taxList.size());
		if( taxList.size() > 0 ) {
			model.addAttribute("taxList", taxList);
		}
		
		return "/admin/taxRateList.leftAdmin";
	}
	
	@RequestMapping("/taxRateAdd")
	@ResponseBody
	public Map<String, Object> taxRateAdd(HttpSession session, TaxRateDTO taxRateDTO){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//해당 적용일에 등록된 정보가 있는지 확인
			taxRateDTO.setUse_yn("1");
			TaxRateDTO resultTaxRate = taxRateService.getTaxRateInfo(taxRateDTO);
			if( resultTaxRate != null ) {
				result.put("code", Const.CODE_SYSTEM_ERROR);
				result.put("message", "해당 적용일에는 등록된 정보가 있습니다.");
				return result;
			}
			
			//콤마 제거
			taxRateDTO.setDeduction_price(taxRateDTO.getDeduction_price().replaceAll("[^0-9]", ""));
			taxRateDTO.setReg_admin("Park");
			taxRateService.addTaxRate(taxRateDTO);
			result.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			result.put("code", Const.CODE_SYSTEM_ERROR);
			result.put("message", Const.MSG_SYSTEM_ERROR);
			
			logger.error("근로자 부담금 요율 추가시 오류", e);
		}
		return result;
	}
	
	@RequestMapping("/taxRateDel")
	@ResponseBody
	public Map<String, Object> taxRateDel(HttpSession session, TaxRateDTO taxRateDTO){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			taxRateService.setTaxRate(taxRateDTO);
			result.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			result.put("code", Const.CODE_SYSTEM_ERROR);
			result.put("message", Const.MSG_SYSTEM_ERROR);
			
			logger.error("근로자 부담금 요율 삭제시 오류", e);
		}
		return result;
	}
	
	@RequestMapping("/getLastRateInfo")
	@ResponseBody
	public Map<String, Object> getLastRateInfo(TaxRateDTO taxRateDTO){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			TaxRateDTO resultTaxRate = taxRateService.getLastTaxRateInfo(taxRateDTO);
			if( resultTaxRate != null ) {
				result.put("taxRateDTO", resultTaxRate);
			}
			result.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			result.put("code", Const.CODE_SYSTEM_ERROR);
			result.put("message", Const.MSG_SYSTEM_ERROR);
			
			logger.error("최근 추가한 근로자 부담금 요율 가져오기 오류", e);
		}
		return result;
	}
}
