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
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.GroupCodeDTO;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.GroupCodeService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/groupCode")
public class GroupCodeController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired GroupCodeService groupCodeService;
	@Autowired CodeService codeService;
	
	// Session 설정
	private GroupCodeDTO setSessionToDTO(HttpSession session, GroupCodeDTO groupCodeDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		// Session 설정
		groupCodeDTO.setAdminLevel(""+ auth_level);
		groupCodeDTO.setReg_admin(admin_id);
		
		return groupCodeDTO;
	}
	
	/**
	 * 
	  * @throws Exception 
	 * @Method Name : groupCodeList
	  * @작성일 : 2020. 6. 16.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param : 
	  * @Method 설명 : 그룹 코드 리스트
	 */
	@RequestMapping("/groupCodeList")
	public String groupCodeList(HttpServletRequest request, HttpSession session, Model model, GroupCodeDTO groupCodeDTO) throws Exception {
		groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
		groupCodeDTO.getPaging().setPageSize(15);
		
		int cnt = groupCodeService.selectListCnt(groupCodeDTO);
		
		if(cnt > 0) {
			List<GroupCodeDTO> groupCodeList = groupCodeService.selectList(groupCodeDTO);
			
			model.addAttribute("groupCodeList", groupCodeList);
		}
		
		model.addAttribute("htmlHeader", "그룹 코드 관리");
		
		model.addAttribute("groupCodeDTO", groupCodeDTO);
		model.addAttribute("totalCnt", cnt);
		
		return "/admin/groupCode/groupCodeList.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : getGroupCodeList
	  * @작성일 : 2020. 6. 16.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param : start_date, end_date, use_yn, group_code
	  * @Method 설명 : ajax 그룹 코드 리스트 
	 */
	@RequestMapping("/getGroupCodeList")
	@ResponseBody
	public Map<String, Object> getGroupCodeList(HttpSession session, GroupCodeDTO groupCodeDTO) throws Exception {
		groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
		groupCodeDTO.getPaging().setPageSize(15);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = groupCodeService.selectListCnt(groupCodeDTO);
			
			List<GroupCodeDTO> groupCodeList = groupCodeService.selectList(groupCodeDTO);
		
			resultMap.put("groupCodeList", groupCodeList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("groupCodeDTO", groupCodeDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : groupCodeWrite
	  * @작성일 : 2020. 6. 16.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : 그룹 코드 추가 페이지 이동
	 */
	@RequestMapping("/groupCodeWrite")
	public String groupCodeWrite(HttpSession session, Model model, GroupCodeDTO groupCodeDTO) throws Exception {
		groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
		
		groupCodeDTO.setUse_yn("1");
		groupCodeDTO.setDel_yn("0");
		
		List<GroupCodeDTO> groupCodeList = groupCodeService.selectList(groupCodeDTO);
		
		model.addAttribute("htmlHeader", "그룹 코드 추가");
		model.addAttribute("groupCodeList", groupCodeList);
		model.addAttribute("groupCodeDTO", groupCodeDTO);
		
		return "/admin/groupCode/groupCodeWrite.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : insertGroupCode
	  * @작성일 : 2020. 6. 16.
	  * @작성자 : Park YunSoo
	  * @필수 param : group_name, use_yn
	  * @선택 param : 
	  * @Method 설명 : 그룹 코드 추가 로직
	 */
	@RequestMapping("/insertGroupCode")
	@ResponseBody
	public Map<String, Object> insertGroupCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, GroupCodeDTO groupCodeDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
			
			int cnt = groupCodeService.selectListCnt(groupCodeDTO);
			
			if(cnt == 0) {
				groupCodeService.insertGroupCode(groupCodeDTO);
			
				resultMap.put("code", Const.CODE_SUCCESS);
			}else {
				resultMap.put("code", Const.CODE_SYSTEM_ERROR);
				resultMap.put("message", "이미 존재하는 코드명 입니다.");
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : selectInfo
	  * @작성일 : 2020. 6. 17.
	  * @작성자 : Park YunSoo
	  * @필수 param : groupCode_seq 
	  * @선택 param :
	  * @Method 설명 : 그룹 상세
	 */
	@RequestMapping("/selectInfo")
	public String selectInfo(HttpSession session, Model model, GroupCodeDTO groupCodeDTO) throws Exception {
		groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
		
		GroupCodeDTO groupCodeInfo = groupCodeService.selectInfo(groupCodeDTO);
		
		model.addAttribute("groupCodeDTO", groupCodeDTO);
		model.addAttribute("htmlHeader", "그룹 수정");
		model.addAttribute("groupCodeInfo", groupCodeInfo);
		
		return "/admin/groupCode/groupCodeEdit.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : updateGroupCode
	  * @작성일 : 2020. 6. 17.
	  * @작성자 : Park YunSoo
	  * @필수 param : group_name, use_yn, reg_admin, group_seq 
	  * @선택 param :
	  * @Method 설명 : 그룹 수정
	 */
	@RequestMapping("updateGroupCode")
	@ResponseBody
	public Map<String, Object> updateGroupCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, GroupCodeDTO groupCodeDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		CodeDTO codeDTO = new CodeDTO();
		
		try {
			groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
			
			int cnt = groupCodeService.selectListCnt(groupCodeDTO);
			
			if(cnt <= 1) {
				groupCodeService.updateGroupCode(groupCodeDTO);
				
				codeDTO.setCode_type(groupCodeDTO.getBefore_code());
				
				List<CodeDTO> codeList = codeService.getCodeList(codeDTO);
				
				for(int i = 0; i < codeList.size(); i++) {
					codeDTO.setCode_value(codeList.get(i).getCode_value().replace(groupCodeDTO.getBefore_code(), groupCodeDTO.getGroup_code()));
					codeDTO.setCode_seq(codeList.get(i).getCode_seq());
					codeDTO.setCode_name(codeList.get(i).getCode_name());
					codeDTO.setCode_color(codeList.get(i).getCode_color());
					codeDTO.setCode_bgcolor(codeList.get(i).getCode_bgcolor());
					codeDTO.setCode_rank(codeList.get(i).getCode_rank());
					codeDTO.setUse_yn(codeList.get(i).getUse_yn());
					codeDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
					codeDTO.setCode_gender(codeList.get(i).getCode_gender());
					codeDTO.setCode_price(codeList.get(i).getCode_price());
					codeDTO.setApp_code(codeList.get(i).getApp_code());
					codeDTO.setCode_type(groupCodeDTO.getGroup_code());
					
					codeService.updateCode(codeDTO);
				}
			
				resultMap.put("code", Const.CODE_SUCCESS);
			}else {
				resultMap.put("code", Const.CODE_SYSTEM_ERROR);
				resultMap.put("message", "이미 존재하는 코드명 입니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : deleteGroupCode
	  * @작성일 : 2020. 6. 17.
	  * @작성자 : Park YunSoo
	  * @필수 param : group_seq 
	  * @선택 param :
	  * @Method 설명 : 그룹 삭제
	 */
	@RequestMapping("deleteGroupCode")
	@ResponseBody
	public Map<String, Object> deleteGroupCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, GroupCodeDTO groupCodeDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			groupCodeDTO = setSessionToDTO(session, groupCodeDTO);
			
			groupCodeService.deleteGroupCode(groupCodeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
