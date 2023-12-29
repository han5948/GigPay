package com.nemo.kr.controller.admin;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nemo.kr.dto.*;
import com.nemo.kr.util.FileUtil;
import com.nemo.kr.util.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.service.ContractService;


/**
 * 공통 컨트롤러
 *
 * @author nemo
 *
 */
@Controller
@RequestMapping("/admin")
public class ContractController {
	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	ContractService contractService;
	
	// Session 설정
	private CategoryDTO setSessionToDTO(HttpSession session, CategoryDTO paramDTO) throws Exception {
		// Session 정보
		paramDTO.setMod_admin("GIGPAY");
		paramDTO.setReg_admin("GIGPAY");
		return paramDTO;
	}
	
	// Session 설정
	private ContractDTO setSessionToContractDTO(HttpSession session, ContractDTO paramDTO) throws Exception {
		// Session 정보
		AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
		
		if( adminSession != null ) {
			paramDTO.setMod_admin(adminSession.getAdmin_id());
			paramDTO.setReg_admin(adminSession.getAdmin_id());
		}else {
			paramDTO.setMod_admin("");
			paramDTO.setReg_admin("");
		}
		
		return paramDTO;
	}
	
	@RequestMapping("/categoryList")
	public String testList(HttpSession session, Model model, CategoryDTO categoryDTO) {
		model.addAttribute("htmlHeader", "계약서 관리");
		
		int cnt = contractService.selectCategoryListCnt();
		
		List<CategoryDTO> categoryList = contractService.selectCategoryList(categoryDTO);
	
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("totalCnt", cnt);
		categoryDTO.getPaging().setRowCount(cnt);
		model.addAttribute("categoryDTO", categoryDTO);
		
		return "/admin/contract/categoryList.leftAdmin";
	}
	
	@RequestMapping("/getCategoryList")
	@ResponseBody
	public Map<String, Object> getCategoryList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CategoryDTO categoryDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = contractService.selectCategoryListCnt();
			
			List<CategoryDTO> categoryList = contractService.selectCategoryList(categoryDTO);
			
			categoryDTO.getPaging().setRowCount(cnt);
			
			resultMap.put("categoryDTO", categoryDTO);
			resultMap.put("totalCnt", cnt);
			resultMap.put("categoryList", categoryList);
			
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/categoryReg")
	public String categoryReg(HttpSession session, Model model, CategoryDTO categoryDTO) {
		model.addAttribute("htmlHeader", "계약서 카테고리 등록");
		model.addAttribute("categoryDTO", categoryDTO);
		
		return "/admin/contract/categoryReg.leftAdmin";
	}
	
	@RequestMapping("/insertCategory")
	@ResponseBody
	public Map<String, Object> insertCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session, CategoryDTO categoryDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		categoryDTO = setSessionToDTO(session, categoryDTO);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			CategoryDTO validDTO = new CategoryDTO();
			
			validDTO.setCategory_name(categoryDTO.getCategory_name());
			validDTO.setUse_yn("1");
			validDTO.setDel_yn("0");
			
			validDTO = contractService.selectCategoryInfo(validDTO);
			
			if(validDTO != null && validDTO.getCategory_seq() != null) {
				resultMap.put("code", "9999");
				resultMap.put("message", "중복된 카테고리명이 존재합니다.");
				
				return resultMap;
			}
			
			validDTO = new CategoryDTO();
			
			validDTO.setCategory_code(categoryDTO.getCategory_code());
			validDTO.setUse_yn("1");
			validDTO.setDel_yn("0");
			
			validDTO = contractService.selectCategoryInfo(categoryDTO);
			
			if(validDTO != null && validDTO.getCategory_seq() != null) {
				resultMap.put("code", "9998");
				resultMap.put("message", "중복된 문서명 코드가 존재합니다.");
				
				return resultMap;
			}
			
			contractService.insertCategory(categoryDTO);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/categoryEdit")
	public String categoryEdit(HttpSession session, Model model, CategoryDTO categoryDTO) {
		model.addAttribute("htmlHeader", "계약서 카테고리 수정");
		model.addAttribute("categoryDTO", contractService.selectCategoryInfo(categoryDTO));
		
		return "/admin/contract/categoryEdit.leftAdmin";
	}
	
	@RequestMapping("/updateCategory")
	@ResponseBody
	public Map<String, Object> updateCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session, CategoryDTO categoryDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		categoryDTO = setSessionToDTO(session, categoryDTO);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			CategoryDTO validDTO = new CategoryDTO();
			
			validDTO.setCategory_name(categoryDTO.getCategory_name());
			validDTO.setUse_yn("1");
			validDTO.setDel_yn("0");
			
			List<CategoryDTO> resultList = contractService.selectCategoryList(validDTO);
			
			validDTO = new CategoryDTO();
			
			validDTO.setCategory_code(categoryDTO.getCategory_code());
			validDTO.setUse_yn("1");
			validDTO.setDel_yn("0");
			
			List<CategoryDTO> resultCodeList = contractService.selectCategoryList(validDTO);
			
			if(resultList.size() > 0 && !resultList.get(0).getCategory_seq().equals(categoryDTO.getCategory_seq())) {
				resultMap.put("code", "9999");
				resultMap.put("message", "중복된 카테고리명이 존재합니다.");
				
				return resultMap;
			}
			
			if(resultCodeList.size() > 0 && !resultCodeList.get(0).getCategory_seq().equals(categoryDTO.getCategory_seq())) {
				resultMap.put("code", "9998");
				resultMap.put("message", "중복된 문서명 코드가 존재합니다.");
				
				return resultMap;
			}

			contractService.updateCategory(categoryDTO);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/contractList")
	public String contractList(HttpSession session, Model model, ContractDTO contractDTO) throws Exception {
		int cnt = contractService.selectContractListCnt(contractDTO);
		
		List<ContractFileDTO> contractList = contractService.selectContractList(contractDTO);
		
		contractDTO.getPaging().setRowCount(cnt);
		
		model.addAttribute("contractList", contractList);
		model.addAttribute("htmlHeader", "계약서 양식 관리");
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("contractDTO", contractDTO);
		
		return "/admin/contract/contractList.leftAdmin";
	}
	
	@RequestMapping("/getContractList")
	@ResponseBody
	public Map<String, Object> getContractDTOList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ContractDTO contractDTO, @RequestParam String query) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			contractDTO.setCategory_seq(query);
			
			int cnt = contractService.selectContractListCnt(contractDTO);
			
			List<ContractFileDTO> contractList = contractService.selectContractList(contractDTO);
			
			contractDTO.getPaging().setRowCount(cnt);
			
			resultMap.put("totalCnt", cnt);
			resultMap.put("contractList", contractList);
			resultMap.put("contractDTO", contractDTO);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/contractReg")
	public String contractReg(HttpSession session, Model model, ContractDTO contractDTO) {
		model.addAttribute("htmlHeader", "계약서 양식 등록");
		model.addAttribute("contractDTO", contractDTO);
		
		int useCnt = contractService.selectContractUseCnt(contractDTO);
		
		model.addAttribute("useCnt", useCnt);
		
		return "/admin/contract/contractReg.leftAdmin";
	}
	
	@RequestMapping("/insertContract")
	@ResponseBody
	public Map<String, Object> insertContract(HttpServletRequest request, HttpServletResponse response, HttpSession session, ContractDTO contractDTO, ContractFileDTO contractFileDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		contractDTO = setSessionToContractDTO(session, contractDTO);
		
		try {
			contractService.insertContract(request, contractDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/contractEdit")
	public String contractEdit(HttpSession session, Model model, ContractDTO contractDTO) {
		model.addAttribute("htmlHeader", "계약서 양식 수정");
		model.addAttribute("contractDTO", contractService.selectContractInfo(contractDTO));
		model.addAttribute("useCnt", contractService.selectContractUseCnt(contractDTO));
		
		return "/admin/contract/contractEdit.leftAdmin";
	}
	
	@RequestMapping("/updateContract")
	@ResponseBody
	public Map<String, Object> updateContract(HttpServletRequest request, HttpServletResponse response, HttpSession session, ContractDTO contractDTO, ContractFileDTO contractFileDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		contractDTO = setSessionToContractDTO(session, contractDTO);
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);

			contractService.updateContract(request, contractDTO);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}

	@RequestMapping("/contractDownload")
	@ResponseBody
	public Map<String, Object> contractDownload(HttpServletRequest request, HttpServletResponse response, ContractDTO contractDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("code", Const.CODE_SUCCESS);

		try{
			FileDTO fileDTO = contractService.selectContractFile(contractDTO);
			if(fileDTO == null){
				resultMap.put("code", Const.CODE_SYSTEM_ERROR);
				resultMap.put("message", "파일이 존재하지 않습니다.");
			}else {
				FileUtil.fileDown(fileDTO.getFile_name(), fileDTO.getFile_path(), fileDTO.getFile_org_name(), request, response);
			}
		}catch (Exception e){
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
}
