package com.nemo.kr.controller.system;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
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
import com.nemo.kr.controller.LoginManager;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.util.RSA;


@Controller
@RequestMapping("/system")
public class SystemController {
	@Autowired AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	// Session 설정
	private AdminDTO setSessionToDTO(HttpSession session, AdminDTO paramDTO) throws Exception {
		// Session 정보
		AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

		paramDTO.setAdminLevel(adminSession.getAdminLevel());
		paramDTO.setMod_admin(adminSession.getAdmin_id());
		paramDTO.setReg_admin(adminSession.getAdmin_id());

		return paramDTO;
	}
	  
	@RequestMapping(value = { "", "/" })
	public String index(Model model, HttpSession session) {
		return "redirect:/system/loginForm";
	}

	@RequestMapping(value="/index")
	public String main(HttpServletRequest request,HttpServletResponse response, HttpSession session,Model model) throws Exception{
		// Session 정보
		
		return "redirect:/system/loginForm";
	}

	/**
	  * @Method Name : loginForm
	  * @작성일 : 2019. 10. 4.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 로그인 화면
	  * @param model
	  * @param session
	  * @param request
	  * @return
	  * @throws Exception
	  */
	@RequestMapping(value = "/loginForm")
	public String loginForm(Model model, HttpSession session, HttpServletRequest request) throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

		try {
			final int KEY_SIZE = 2048;
			generator.initialize(KEY_SIZE);

			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();

			// 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
			session.setAttribute("__rsaPrivateKeyADMIN__", privateKey);

			// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

			model.addAttribute("publicKeyModulus", publicKeyModulus);
			model.addAttribute("publicKeyExponent", publicKeyExponent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/index.no_system";
	}
	
	/**
	  * @Method Name : loginProc
	  * @작성일 : 2019. 10. 4.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 로그인 프로세스
	  * @param request
	  * @param response
	  * @param session
	  * @param adminDTO
	  * @return
	  * @throws Exception
	  */
	@RequestMapping(value="/loginProc")
	@ResponseBody
	public Map<String, String> loginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyADMIN__");

			String sAdminId = RSA.decryptRsa(privateKey, adminDTO.getAdmin_id());
			String sAdminPwd = RSA.decryptRsa(privateKey, adminDTO.getAdmin_pass());

			adminDTO.setAdmin_id(sAdminId);
			adminDTO.setAdmin_pass(sAdminPwd);

		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_INVALID_SESSION_PARAMETER);
			resultMap.put("message", Const.MSG_INVALID_SESSION_PARAMETER);
			e.printStackTrace();
			return resultMap;
		}

		try {
			resultMap = adminService.adminLoginProc(request, adminDTO);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
			e.printStackTrace();
		}

		return resultMap;
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) throws NoSuchAlgorithmException {
		AdminDTO sessionDTO = (AdminDTO) session.getAttribute(Const.adminSession);

		LoginManager loginManager = LoginManager.getInstance();
		
		if ( loginManager.isUsing(sessionDTO.getAdmin_id() ) ) {
			session.removeAttribute(Const.adminSession);
			session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
			
			loginManager.removeSession(sessionDTO.getAdmin_id());
		}
		
		return "redirect:/system/loginForm"; 
	}

	/**
	  * @Method Name : main
	  * @작성일 : 2019. 10. 4.
	  * @작성자 : nemojjang
	  * @변경이력 : 메인
	  * @Method 설명 :
	
	  */
	@RequestMapping(value="/main")
	public String main(HttpSession session, Model model, AdminDTO adminDTO) throws Exception {
		// Session 정보 설정
		adminDTO = setSessionToDTO(session, adminDTO);

		//return "/system/main.admin";
		return "/system/main.system";
	}
	
	@RequestMapping(value="/adminList")
	public String adminList(HttpSession session, Model model, AdminDTO adminDTO) throws Exception {
		// Session 정보 설정
		adminDTO = setSessionToDTO(session, adminDTO);
		adminDTO.setUseYn("");	//사용유무 모두 보여 준다.
		
		int cnt = adminService.selectListCnt(adminDTO);
		if ( cnt > 0 ) {
			model.addAttribute("adminList", adminService.selectList(adminDTO));
		}
		adminDTO.getPaging().setRowCount(cnt);
		
		return "/system/admin/adminList.system";
		
	}

	@RequestMapping(value="/adminReg")
	public String adminReg(HttpSession session, Model model, AdminDTO adminDTO) throws Exception {
		// Session 정보 설정
		adminDTO = setSessionToDTO(session, adminDTO);

		model.addAttribute("adminDTO", adminDTO);

		return "/system/admin/adminReg.system";
	}

	@RequestMapping(value="/adminDuplicationCheck")
	@ResponseBody
	public Map<String, String> adminDuplicationCheck(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// ID 중복체크
			int adminCnt = adminService.duplicationCheckAdminId(adminDTO);
			if ( adminCnt > 0 ) {
				resultMap.put("code", Const.CODE_USER_0005);
				resultMap.put("message", Const.MSG_USER_0005);
			} else {
				resultMap.put("code", Const.CODE_SUCCESS);
			}

		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	/**
	  * @Method Name : adminRegProcess
	  * @작성일 : 2019. 10. 4.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 관리자 등록
	
	  */
	@RequestMapping("/adminRegProcess")
	@ResponseBody
	public Map<String, String> adminRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			adminDTO = setSessionToDTO(session, adminDTO);

			// ID 중복체크
			int adminCnt = adminService.duplicationCheckAdminId(adminDTO);
			if ( adminCnt > 0 ) {
				resultMap.put("code", Const.CODE_USER_0005);
				resultMap.put("message", Const.MSG_USER_0005);

				return resultMap;
			}
		
			adminService.insertInfo(adminDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	@RequestMapping(value="/adminEdit")
	public String complaintEdit(HttpSession session, Model model,  AdminDTO adminDTO) throws Exception {
		// Session 정보 설정
		adminDTO = setSessionToDTO(session, adminDTO);

		//상세정보
		AdminDTO resultDTO = adminService.selectInfo(adminDTO);

		// Session 정보
		AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

		model.addAttribute("resultDTO", resultDTO);

		return "/system/admin/adminEdit.system";
	}

	@RequestMapping(value="/adminEditProcess")
	@ResponseBody
	public Map<String, String> adminEditProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			adminDTO = setSessionToDTO(session, adminDTO);

			// ID 중복체크
			int adminCnt = adminService.duplicationCheckAdminId(adminDTO);
			if ( adminCnt > 1 ) {
				resultMap.put("code", Const.CODE_USER_0005);
				resultMap.put("message", Const.MSG_USER_0005);

				return resultMap;
			}
			
			adminService.updateInfo(adminDTO);
			resultMap.put("code", Const.CODE_SUCCESS);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
}