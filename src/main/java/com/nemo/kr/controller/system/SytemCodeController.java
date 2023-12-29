package com.nemo.kr.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
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
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.FileService;
import com.nemo.kr.util.FileUtil;



/**
  * @FileName : AdminCodeController.java
  * @Project : nemoSystem
  * @Date : 2019. 10. 16. 
  * @작성자 : nemojjang
  * @변경이력 : 코드관리
  * @프로그램 설명 :
  */
@Controller
@RequestMapping("/system/code")
public class SytemCodeController {
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	@Autowired CodeService codeService;
	@Autowired FileService 	fileService;

	private static final Logger logger = LoggerFactory.getLogger(SytemCodeController.class);
	
	// Session 설정
	private CodeDTO setSessionToDTO(HttpSession session, CodeDTO paramDTO) throws Exception {
		// Session 정보
		AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

		paramDTO.setMod_admin(adminSession.getAdmin_id());
		paramDTO.setReg_admin(adminSession.getAdmin_id());

		return paramDTO;
	}
	
	@RequestMapping("/codeGroupList")
	public String codeGroupList(HttpServletRequest request, HttpSession session, Model model, CodeDTO codeDTO) throws Exception {
		String uri = request.getRequestURI();

		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);
		
		int cnt = codeService.selectGroupListCnt(codeDTO);
		if ( cnt > 0 ) {
			codeDTO.setPaging(null);
			model.addAttribute("codeGroupList", codeService.selectGroupList(codeDTO));
		}
		
		return "/system/code/codeGroupList.system";
	}

	@RequestMapping(value="/codeGroupReg")
	public String codeGroupReg(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, CodeDTO codeDTO) throws Exception {
		// Session 정보 설정
		logger.debug("============codeGroupReg==============");
		codeDTO = setSessionToDTO(session, codeDTO);

		model.addAttribute("codeDTO", codeDTO);

		return "/system/code/codeGroupReg.system";
	}

	@RequestMapping("/codeGroupRegProcess")
	@ResponseBody
	public Map<String, String> codeGroupRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			codeDTO = setSessionToDTO(session, codeDTO);

			codeService.insertCodeGroup(codeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	@RequestMapping(value="/codeGroupEdit")
	public String codeGroupEdit(HttpSession session, Model model,  CodeDTO codeDTO) throws Exception {
		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);

		//상세정보
		CodeDTO resultDTO = codeService.selectCodeGroup(codeDTO);

		model.addAttribute("resultDTO", resultDTO);


		return "/system/code/codeGroupEdit.system";
	}

	@RequestMapping(value="/codeGroupEditProcess")
	@ResponseBody
	public Map<String, String> codeGroupEditProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			// Session 정보 설정
			codeDTO = setSessionToDTO(session, codeDTO);

			codeService.updateCodeGroup(codeDTO);

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	@RequestMapping("/codeList")
	public String codeList(HttpServletRequest request, HttpSession session, Model model, CodeDTO codeDTO) throws Exception {
		String uri = request.getRequestURI();

		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);
		
		int cnt = codeService.selectListCnt(codeDTO);
		
		if ( cnt > 0 ) {
			codeDTO.setPaging(null);
			model.addAttribute("codeList", codeService.getCodeList(codeDTO));
		}
		
		return "/system/code/codeList.system";
	}

	@RequestMapping(value="/codeReg")
	public String codeReg(HttpSession session, Model model, CodeDTO codeDTO) throws Exception {
		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);
		
		model.addAttribute("codeGroup", codeService.selectCodeGroup(codeDTO));
		
		return "/system/code/codeReg.system";
	}

	@RequestMapping("/codeRegProcess")
	@ResponseBody
	public Map<String, String> codeRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			codeDTO = setSessionToDTO(session, codeDTO);	

			codeService.insertInfo(codeDTO);

			List<FileDTO> listFileDTO =  FileUtil.fileUpload(request, "code", codeDTO.getCode_seq(), commonProperties.getProperty("uploadPath"), commonProperties.getProperty("fileUrl"));

			fileService.insertInfo(adminSession.getAdmin_id(), listFileDTO);

		
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	/**
	  * @Method Name : codeRead
	  * @작성일 : 2019. 10. 15.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 게시팜 보기
	
	  */
	@RequestMapping(value="/codeDetail")
	public String codeRead(HttpSession session, Model model, CodeDTO codeDTO) throws Exception {
		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);

		//상세정보
		CodeDTO resultDTO = codeService.selectInfo(codeDTO);
		model.addAttribute("resultDTO", resultDTO);
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setService_type("code");
		fileDTO.setService_seq(codeDTO.getCode_seq());

		model.addAttribute("fileList", fileService.selectList(fileDTO));

		return "/system/code/codeDetail.system";
	}
	
	@RequestMapping(value="/codeEdit")
	public String codeEdit(HttpSession session, Model model,  CodeDTO codeDTO) throws Exception {
		// Session 정보 설정
		codeDTO = setSessionToDTO(session, codeDTO);
		
		//상세정보
		CodeDTO resultDTO = codeService.selectInfo(codeDTO);

		model.addAttribute("resultDTO", resultDTO);
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setService_type("code");
		fileDTO.setService_seq(codeDTO.getCode_seq());

		model.addAttribute("fileList", fileService.selectList(fileDTO));

		return "/system/code/codeEdit.system";
	}

	@RequestMapping(value="/codeEditProcess")
	@ResponseBody
	public Map<String, String> codeEditProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			// Session 정보 설정
			codeDTO = setSessionToDTO(session, codeDTO);

			codeService.updateInfo(codeDTO);

			List<FileDTO> listFileDTO =  FileUtil.fileUpload(request, "code", codeDTO.getCode_seq(), commonProperties.getProperty("uploadPath"), commonProperties.getProperty("fileUrl") );

			fileService.editInfo(adminSession.getAdmin_id(), listFileDTO);

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	@RequestMapping(value="/codeDelProcess")
	@ResponseBody
	public Map<String, String> codeDelProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			codeDTO = setSessionToDTO(session, codeDTO);

			codeService.deleteInfo(codeDTO);

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
}
