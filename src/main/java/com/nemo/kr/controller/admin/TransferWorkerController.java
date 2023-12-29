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
import com.nemo.kr.domain.administrativeDistrict.Sido;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.dto.OnOffDTO;
import com.nemo.kr.dto.TransferWorkerLocationDTO;
import com.nemo.kr.dto.TransferWorkerOptionDTO;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.JobService;
import com.nemo.kr.service.OnOffService;
import com.nemo.kr.service.RoadNameService;
import com.nemo.kr.service.TransferWorkerLocationService;
import com.nemo.kr.service.TransferWorkerOptionService;


@Controller
@RequestMapping("/admin/transferWorker")
public class TransferWorkerController {
	private static final Logger logger = LoggerFactory.getLogger(TransferWorkerController.class);
	
	@Autowired JobService jobService;
	@Autowired RoadNameService roadNameService;
	@Autowired CompanyService companyService;
	@Autowired TransferWorkerOptionService transferWorkerOptionService;
	@Autowired TransferWorkerLocationService transferWorkerLocationService;
	@Autowired OnOffService onOffService;
	
	@RequestMapping("/optionList")
	public String optionList(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "구직자이관");
		
		//직종목록
		JobDTO jobParam = new JobDTO();
		jobParam.setUse_yn("1");
		jobParam.setPaging(null);
		List<JobDTO> jobList = jobService.selectJobList(jobParam);
		model.addAttribute("jobList", jobList);
		
		//지점목록
		CompanyDTO companyParam = new CompanyDTO();
		companyParam.setSrh_use_yn("1");
		companyParam.setSidx("company_name");
		companyParam.setSord("asc");
		companyParam.setPaging(null);
		companyParam.setWhere("AND company_seq NOT IN (1, 13)");
		List<CompanyDTO> companyList = companyService.getCompanyList(companyParam);
		model.addAttribute("companyList", companyList);
		
		//기능 온오프 유무
		OnOffDTO onOffDTO = new OnOffDTO();
		onOffDTO.setFunction_type("T");
		OnOffDTO resultOnOff = onOffService.getOnOffInfo(onOffDTO);
		model.addAttribute("transferWorkerOnOff", resultOnOff.getUse_yn());
		
		return "/admin/transferWorker/optionList.leftAdmin";
	}
	
	@RequestMapping("/getAdministrativeDistrictList")
	@ResponseBody
	public List<Sido> getAdministrativeDistrictList(){
		return roadNameService.getAdministrativeDistrictList();
	}
	
	@RequestMapping("/getTransferWorkerOptionList")
	@ResponseBody
	public JSONObject getTransferWorkerOptionList(TransferWorkerOptionDTO transferWorkerOptionDTO){
		//옵션목록
		int rowCount = transferWorkerOptionService.getTransferWorkerOptionListCnt(transferWorkerOptionDTO);
		transferWorkerOptionDTO.getPaging().setRowCount(rowCount);
		List<TransferWorkerOptionDTO> result = transferWorkerOptionService.getTransferWorkerOptionList(transferWorkerOptionDTO);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("rows",    result);                                             			 // 현재페이지의 목록데이터
		jsonObj.put("page",    transferWorkerOptionDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   transferWorkerOptionDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", transferWorkerOptionDTO.getPaging().getRowCount());               // 총글목록수
		
		return jsonObj;
	}
	
	@RequestMapping("/addTransferWorkerOption")
	@ResponseBody
	public JSONObject addTransferWorkerOption(HttpSession session, TransferWorkerOptionDTO transferWorkerOptionDTO){
		
		JSONObject jsonObj = new JSONObject();
		try {
			AdminDTO loginAdmin = (AdminDTO)session.getAttribute(Const.adminSession);
			transferWorkerOptionDTO.setReg_admin(loginAdmin.getAdmin_id());
			transferWorkerOptionDTO.setMod_admin(loginAdmin.getAdmin_id());
			
			transferWorkerOptionService.addTracsferWorkerOption(transferWorkerOptionDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/setTransferWorkerOption")
	@ResponseBody
	public JSONObject setTransferWorkerOption(HttpSession session, TransferWorkerOptionDTO transferWorkerOptionDTO){
		
		JSONObject jsonObj = new JSONObject();
		try {
			AdminDTO loginAdmin = (AdminDTO)session.getAttribute(Const.adminSession);
			transferWorkerOptionDTO.setMod_admin(loginAdmin.getAdmin_id());
			
			transferWorkerOptionService.setTracsferWorkerOption(transferWorkerOptionDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/removeTransferWorkerOption")
	@ResponseBody
	public JSONObject removeTransferWorkerOption(HttpSession session, TransferWorkerOptionDTO transferWorkerOptionDTO){
		
		JSONObject jsonObj = new JSONObject();
		try {
			AdminDTO loginAdmin = (AdminDTO)session.getAttribute(Const.adminSession);
			transferWorkerOptionDTO.setMod_admin(loginAdmin.getAdmin_id());
			
			transferWorkerOptionService.removeTracsferWorkerOption(transferWorkerOptionDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/getTransferWorkerLocationList")
	@ResponseBody
	public JSONObject getTransferWorkerLocationList(TransferWorkerLocationDTO transferWorkerLocationDTO){
		JSONObject jsonObj = new JSONObject();
		
		try {
			//지역목록
			List<TransferWorkerLocationDTO> result = transferWorkerLocationService.getTransferWorkerLocationList(transferWorkerLocationDTO);
			jsonObj.put("list", result);
			
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/locationSave")
	@ResponseBody
	public JSONObject locationSave(HttpSession session, TransferWorkerLocationDTO transferWorkerLocationDTO){
		JSONObject jsonObj = new JSONObject();
		
		try {
			AdminDTO loginAdmin = (AdminDTO)session.getAttribute(Const.adminSession);
			transferWorkerLocationDTO.setReg_admin(loginAdmin.getAdmin_id());
			
			transferWorkerLocationService.locationSave(transferWorkerLocationDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/rankChange")
	@ResponseBody
	public JSONObject rankChange(HttpSession session, TransferWorkerOptionDTO transferWorkerOptionDTO){
		JSONObject jsonObj = new JSONObject();
		
		try {
			AdminDTO loginAdmin = (AdminDTO)session.getAttribute(Const.adminSession);
			transferWorkerOptionDTO.setMod_admin(loginAdmin.getAdmin_id());
			
			transferWorkerOptionService.rankChange(transferWorkerOptionDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/setTransferWorkerOnOff")
	@ResponseBody
	public JSONObject setTransferWorkerOnOff(HttpSession session, OnOffDTO onOffDTO){
		JSONObject jsonObj = new JSONObject();
		
		try {
			onOffService.setOnOffInfo(onOffDTO);
			jsonObj.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			logger.error("", e);
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return jsonObj;
	}
}
