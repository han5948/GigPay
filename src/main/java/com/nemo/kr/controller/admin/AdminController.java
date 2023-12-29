package com.nemo.kr.controller.admin;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.dto.TaxbillDTO;
import com.nemo.kr.excel.TaxbillListExcel;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.SmsService;
import com.nemo.kr.service.TaxbillService;
//import com.nemo.kr.util.StringUtil;


/**
 * 공통 컨트롤러
 *
 * @author nemo
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired AdminService adminService;
	@Autowired CommonService commonService;
	@Autowired SmsService smsService;
	@Autowired TaxbillService taxbillService;
	@Autowired CompanyService companyService;
	
	// Session 설정
	private AdminDTO setSessionToDTO(HttpSession session, AdminDTO adminDTO) throws Exception {
		// Session 정보
	    int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
	    String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
	    String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
	    String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
	    String get_company_seq = StringUtil.isNullToString(adminDTO.getCompany_seq());

	    // Session 설정
	    adminDTO.setAdminLevel(""+ auth_level);
	    
	    if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
	    	adminDTO.setSrh_company_seq(company_seq);
	    }
	    
	    if ( "".equals(get_company_seq) ) {
	    	adminDTO.setCompany_seq(company_seq);
	    }
	    
	    adminDTO.setCompany_name(company_name);
	    adminDTO.setReg_admin(admin_id);
	    adminDTO.setMod_admin(admin_id);
	    
	    return adminDTO;
	}
	  
	@RequestMapping(value = { "", "/" })
	public String index(Model model, HttpSession session) {
		return "redirect:/admin/job/jobList";
	}

	@RequestMapping(value="/setFcmToken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setFcmToken(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			String token = request.getParameter("token");

			AdminDTO adminDTO = new AdminDTO();

			String adminSeq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq();

			adminDTO.setWeb_token(token);
			adminDTO.setAdmin_seq(adminSeq);
			adminDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());

			adminService.updateInfo(adminDTO);

			resultMap.put("code", "0000");
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			e.printStackTrace();
		}

		return resultMap;
	}
	
	/**
	 * 메인
	 * @author nemo
	 *  2016. 2. 23.
	 * @param session
	 * @param model
	 * @return String
	 *
	 */
	//  @RequestMapping(value="/main")
	//  public String main(HttpSession session, Model model) {
	//    String adminLevel = ((AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level();
	//    String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
	//    
	//    model.addAttribute("htmlHeader", "Dashboard");
	//    return "/admin/main.dashboard";
	//  }

	/**
	 * 로그인 화면
	 * @param model
	 * @param session
	 * @return String
	 *
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model, HttpSession session, HttpServletRequest request) throws Exception{
		// RSA 오류로 임시로 주석
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


		} catch(Exception e) {
			e.printStackTrace();
		}

		//    return "/admin/loginForm.no_admin";
		//    return "/admin/test.no_admin";
		return "/admin/index.no_admin";
	}
	

	/**
	 * 로그인 처리
	 * @param request
	 * @param response
	 * @param session
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyADMIN__");
			adminDTO.setAdmin_id((StringUtil.decryptRsa(privateKey, adminDTO.getAdmin_id() )));
			adminDTO.setAdmin_pass((StringUtil.decryptRsa(privateKey, adminDTO.getAdmin_pass() )));
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "8000");
			e.printStackTrace();
			return resultMap;
		}

		try {
			resultMap = adminService.adminLoginProc(request, adminDTO);

			//if ( resultMap.get("code").equals("0000") ) {
				//session.removeAttribute("__rsaPrivateKeyADMIN__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
			//}

		}catch (NoSuchElementException e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_USER_0001);
			resultMap.put("message", e.getMessage());
			logger.error("", e);
		}catch (AccessDeniedException e) {
			resultMap.put("code", Const.CODE_USER_0003);
			resultMap.put("message", e.getMessage());
			logger.error("", e);
		}catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			logger.error("", e);
		}
		return resultMap;
	}


	/**
	 * 로그아웃
	 *  2016. 2. 25.
	 * @param request
	 * @param response
	 * @return String
	 *
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.invalidate();

		return "redirect:/admin";
	}

	@RequestMapping("/selectToken")
	@ResponseBody
	public Map<String, Object> selectToken(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("code", Const.CODE_SUCCESS);

			AdminDTO adminDTO = new AdminDTO();
			adminDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			adminDTO = adminService.selectInfo(adminDTO);

			resultMap.put("adminDTO", adminDTO);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}


	@RequestMapping(value="/adminList")
	public String adminList(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		/*
		 * Cookie cookie = new Cookie("loginKey", CommonUtil.getAppLoginKey("seq"));
		 * cookie.setMaxAge(60*10*6*24*365*5); response.addCookie(cookie);
		 * 
		 */		
		model.addAttribute("htmlHeader" , "관리자관리");
		
		return "/admin/adminList.leftAdmin";
	}
	
	@RequestMapping(value="/getAdminList")
	@ResponseBody
	public JSONObject getAdminList(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception {
		int auth_level;

	    String where    = adminDTO.getWhere();
	    String srh_type = adminDTO.getSrh_type();
	    String srh_text = adminDTO.getSrh_text();

	    // Session 설정
	    adminDTO = setSessionToDTO(session, adminDTO);

	    if ( srh_text != null && !"".equals(srh_text) ) {
	    	if ( "".equals(where) ) {
	    		where = " AND ";
	    	}

	    	if ( srh_type != null && !"".equals(srh_type) ) {
	    		where += srh_type +" LIKE '%"+ srh_text +"%'";
	    	} else {
	    		auth_level = StringUtil.isNullToInt(adminDTO.getAdminLevel());

	    		where += " (";
	    		
	    		if ( auth_level == 0 ) {
	    			where += " c.company_name LIKE '%"+ srh_text +"%' OR";
	    		}

	    		where += " a.admin_name     LIKE '%"+ srh_text +"%'";
	    		where += " OR a.admin_id    LIKE '%"+ srh_text +"%'";
	    		where += " OR a.admin_phone LIKE '%"+ srh_text +"%'";
	    		where += " OR a.admin_email LIKE '%"+ srh_text +"%'";
	    		where += " )";
	    	}

	    	adminDTO.setWhere(where);
	    }

	    int rowCount = adminService.getAdminTotalCnt(adminDTO);

	    adminDTO.getPaging().setRowCount(rowCount);

	    List<AdminDTO> result = adminService.getAdminList(adminDTO);
	    
	    JSONObject jsonObj = new JSONObject();

	    jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
	    jsonObj.put("page",    adminDTO.getPaging().getPageNo());                   // 현재페이지
	    jsonObj.put("total",   adminDTO.getPaging().getTotalPageCnt());             // 총페이지수
	    jsonObj.put("records", adminDTO.getPaging().getRowCount());                 // 총글목록수

	    return jsonObj;
	}
	  

	// 행 추가
	@RequestMapping(value="/setAdminCell")
	@ResponseBody
	public JSONObject setAdminCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		adminDTO = setSessionToDTO(session, adminDTO);

		adminService.setAdminCell(adminDTO);

		jsonObj.put("admin_seq", adminDTO.getAdmin_seq());                          // 성공

		return jsonObj;
	}

	// 변경된 행 저장
	@RequestMapping(value="/setAdminInfo")
	@ResponseBody
	public JSONObject setAdminInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		adminDTO = setSessionToDTO(session, adminDTO);

		try {
			jsonObj.put("code", Const.CODE_SUCCESS);

			adminService.updateInfo(adminDTO);

			if(adminDTO.getAdmin_phone() != null) {
				session.setAttribute("admin_phone", adminDTO.getAdmin_phone());
			}
		}catch(Exception e) {
			e.printStackTrace();
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("msg", Const.MSG_SYSTEM_ERROR);
		}

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeAdminInfo")
	@ResponseBody
	public JSONObject removeAdminInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		adminDTO = setSessionToDTO(session, adminDTO);

		adminService.removeAdminInfo(adminDTO);

		return jsonObj;
	}

	/**
	* @Method Name : setOrderSMS
	* @작성일 : 2021. 3. 18.
	* @작성자 : Jangjaeho
	* @변경이력 : 
	* @Method 설명 : orderList 페이지에서 호출 SMS 받을 지 유무
	*/
	@RequestMapping(value="/setOrderSMS")
	@ResponseBody
	public Map<String, String> setOrderSMS(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

	    try {
	    	adminDTO.setMod_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
	    	adminDTO.setAdmin_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
	    	
	    	adminService.updateInfo(adminDTO);

	    	 resultMap.put("code", "0000");
	      
	    } catch (Exception e) {
	      // TODO: handle exception
	      resultMap.put("code", "9999");
	      e.printStackTrace();
	    }

	    return resultMap;
	}
	
	/**
	* @Method Name : setAdminSms
	* @작성일 : 2021. 3. 25.
	* @작성자 : Jangjaeho
	* @변경이력 : 
	* @Method 설명 : 관리자 SMS 보내기
	*/
	@RequestMapping(value="/setAdminSms")
	@ResponseBody
	public Map<String, Object> setAdminSms(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdminDTO adminDTO) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if(StringUtil.isNull(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_phone())) {
				resultMap.put("code", "0000");
				resultMap.put("message","발신자 번호가 없습니다. 발신자 번호 등록 후 다시 로그인 하세요.");
				return resultMap;
			}
			
			String tr_msg = request.getParameter("tr_msg");
			
			SmsDTO smsDTO = new SmsDTO();
			smsDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			smsDTO.setTr_callback(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_phone());
			smsDTO.setTr_msg(tr_msg);
			
			List<AdminDTO> adminList = adminService.selectList(adminDTO);
			
			for (AdminDTO adminDTO2 : adminList) {
				if(!StringUtil.isNull(adminDTO2.getAdmin_phone())) {
					smsDTO.setTr_phone(adminDTO2.getAdmin_phone());
					smsService.sendSMS(smsDTO);
				}
			}
			
			resultMap.put("code", "0000");
			resultMap.put("sendCount", adminList.size());

		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			resultMap.put("message", "오류가 발생 했습니다.");
			e.printStackTrace();
		}

		return resultMap;
	}
	
	@RequestMapping(value="/taxbillList")
	public String taxList(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		
		model.addAttribute("htmlHeader" , "계산서 관리");
		model.addAttribute("adminSession", ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)));
		
		return "/admin/taxbillList.leftAdmin";
	}
	
	@RequestMapping(value="/getTaxbillList")
	@ResponseBody
	public JSONObject getTaxbillList(HttpServletRequest request, HttpServletResponse response, HttpSession session, TaxbillDTO taxbillDTO) throws Exception {

	    int rowCount = taxbillService.selectTaxbillListCnt(taxbillDTO);

	    taxbillDTO.getPaging().setRowCount(rowCount);

	    List<TaxbillDTO> result = taxbillService.selectTaxbillList(taxbillDTO);
	    
	    JSONObject jsonObj = new JSONObject();

	    jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
	    jsonObj.put("page",    taxbillDTO.getPaging().getPageNo());                   // 현재페이지
	    jsonObj.put("total",   taxbillDTO.getPaging().getTotalPageCnt());             // 총페이지수
	    jsonObj.put("records", taxbillDTO.getPaging().getRowCount());                 // 총글목록수

	    return jsonObj;
	}
	
	// 변경된 행 저장
	@RequestMapping(value="/setTaxbillInfo")
	@ResponseBody
	public JSONObject setTaxbillInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, TaxbillDTO taxbillDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		AdminDTO sessionDTO = (com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession);

		try {
			jsonObj.put("code", Const.CODE_SUCCESS);

			taxbillDTO.setReg_admin(sessionDTO.getAdmin_id());
			
			taxbillService.updateTaxbill(taxbillDTO);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObj.put("code", Const.CODE_SYSTEM_ERROR);
			jsonObj.put("msg", Const.MSG_SYSTEM_ERROR);
		}

		return jsonObj;
	}
	
	@RequestMapping(value="/taxbillListExcel")
	@ResponseBody
	public View taxbillListExcel(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session, TaxbillDTO taxbillDTO) throws Exception {
		
		AdminDTO sessionDTO = (com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String fileName = sessionDTO.getCompany_name() + "면세계산서_" + format.format(today) + ".xlsx";
		
		String[] selArr = taxbillDTO.getSel_seq_arr()[0].toString().split(",");
		
		taxbillDTO.setSel_seq_arr(selArr);
		
		taxbillDTO.setPaging(null);
		List<TaxbillDTO> resultList = taxbillService.selectTaxbillList(taxbillDTO);
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", sessionDTO.getCompany_name());
		model.addAttribute("taxbillDTO", taxbillDTO);
		model.addAttribute("resultList", resultList);
		
		return new TaxbillListExcel();
		
	}
}
