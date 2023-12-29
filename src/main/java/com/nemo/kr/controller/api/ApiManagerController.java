package com.nemo.kr.controller.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.controller.LoginManager;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.DeviceDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.ManagerLoginDTO;
import com.nemo.kr.dto.ManagerWorkDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.excel.LaborExpensesExcel;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.DeviceService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.FileService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.PushReserveService;
import com.nemo.kr.service.PushSendService;
import com.nemo.kr.service.WorkService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.CommonUtil;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.FileCoder;
import com.nemo.kr.util.JsonUtil;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * 회원 API 컨트롤러 
 * @author nemojjang
 *
 */
@Controller
@RequestMapping("/manager")
public class ApiManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ApiManagerController.class);

	@Resource(name="commonProperties")	  private Properties commonProperties;

	@Autowired DeviceService deviceService;
	@Autowired WorkerService workerService;
	@Autowired WorkService workService;
	@Autowired PushSendService pushSendService;
	@Autowired PushReserveService pushReserveService;
	@Autowired IlboService ilboService;
	@Autowired CodeService codeService;
	@Autowired ManagerService managerService;
	@Autowired EmployerService employerService;
	@Autowired CommonService commonService;
	@Autowired FileService fileService;
	@Autowired RestTemplate restTemplate;
	
	@RequestMapping(value = { "", "/" })
	public String index(Model model, HttpSession session) {
		return "redirect:/manager/intro"; //인트로 컨트롤러
	}

	@RequestMapping(value="/intro")
	public String intro(HttpSession session, Model model) {
		return "/manager/intro.no_manager";	//인트로 페이지 html

	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 8. 28.
	 * @dec    : 로그인 페이지
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value="/login")
	public String login(HttpSession session, Model model) throws NoSuchAlgorithmException {
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
			session.setAttribute("__rsaPrivateKeyManager__", privateKey);

			// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

			model.addAttribute("publicKeyModulus", publicKeyModulus);
			model.addAttribute("publicKeyExponent", publicKeyExponent);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return "/manager/login.no_manager";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) throws NoSuchAlgorithmException {
		ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);

		LoginManager loginManager = LoginManager.getInstance();
		
		if ( loginManager.isUsing(sessionDTO.getLogin_id()) ) {
			session.removeAttribute(Const.managerSession);
			session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
			
			loginManager.removeSession(sessionDTO.getLogin_id());
		}
		
		return "redirect:/manager/login"; //인트로 컨트롤러
	}
	
	/**
	 *  단말기정보 저장
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setManagerApp")	
	public void setManagerApp(HttpServletResponse response,HttpServletRequest request, ManagerLoginDTO loginDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[login_id]", loginDTO.getLogin_id());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]"		, loginDTO.getOs_type());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			if( "M".equals(loginDTO.getLogin_type() )){
				ManagerDTO managerDTO = new ManagerDTO();
				managerDTO.setManager_phone(loginDTO.getLogin_id());
				managerDTO.setOs_type(loginDTO.getOs_type());
				managerDTO.setPush_token(loginDTO.getPush_token());
				managerDTO.setApp_version(loginDTO.getApp_version());
				managerDTO.setVisit_date("1");
				
				managerService.setManagerInfo(managerDTO);	
			}else{
				EmployerDTO eDTO = new EmployerDTO();
				eDTO.setEmployer_num(loginDTO.getLogin_id());
				eDTO.setOs_type(loginDTO.getOs_type());
				eDTO.setPush_token(loginDTO.getPush_token());
				eDTO.setApp_version(loginDTO.getApp_version());
				eDTO.setReg_admin("employer");
				eDTO.setVisit_date("1");
				
				employerService.setEmployerInfo(eDTO);
			}

			//device 정보
			DeviceDTO deviceDTO = new DeviceDTO();
			deviceDTO.setOs_type(loginDTO.getOs_type());
			deviceDTO.setApp_type("M");					//w 는 worker app

			result.put("deviceInfo", deviceService.getAppVersion(deviceDTO));
			result.put("iosMarket", "http://itunes.apple.com/app/id1350649347?mt=8");

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();
		}
	}

	/**
	 * 수동 로그인 처리
	 * @param request
	 * @param response
	 * @param session
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loginProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerLoginDTO loginDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyManager__");
		
		loginDTO.setLogin_id( (StringUtil.decryptRsa(privateKey, loginDTO.getLogin_id() )));
		loginDTO.setLogin_pass((StringUtil.decryptRsa(privateKey, loginDTO.getLogin_pass() )));
        
		resultMap = LoginProcess( request,  response,  session,  loginDTO);

		return resultMap;
	}

	/**
	 * 자동 로그인 처리
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/autoLoginProc")
	public String autoLoginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerLoginDTO loginDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyManager__");
			
			loginDTO.setLogin_id( (StringUtil.decryptRsa(privateKey, loginDTO.getLogin_id() )));
			loginDTO.setLogin_pass((StringUtil.decryptRsa(privateKey, loginDTO.getLogin_pass() )));
	        
			resultMap = LoginProcess( request,  response,  session,  loginDTO);

			if ( resultMap.get("code").equals("0000") ) {

				return "redirect:/manager/main";
			}else{
				return "redirect:/manager/login";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "redirect:/manager/login";
		}
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 8. 28.
	 * @dec    : 통합 로그인 처리 
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param managerDTO
	 * @return
	 */
	public  Map<String, String> LoginProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerLoginDTO loginDTO){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			//아이디 비밀번호 공백 체크
			if ( StringUtil.isNull( loginDTO.getLogin_id()) || StringUtil.isNull( loginDTO.getLogin_pass() ) ) {
				resultMap.put("code", Const.CODE_USER_0001);
				resultMap.put("message", Const.MSG_USER_0001);
				return resultMap;
			}

			String visit_type = "W";
			if(CommonUtil.isMobile(request)){
				visit_type = "M";
			}

			if( StringUtil.isNull( loginDTO.getLogin_id()) ) loginDTO.setLogin_type("M");

			if("M".equals(loginDTO.getLogin_type()) ){
				//처음 등록 시킨 메니져만 사용 하도록 한다.
				ManagerDTO managerDTO = new ManagerDTO();
				managerDTO.setManager_phone( loginDTO.getLogin_id() );
				managerDTO.setManager_pass( loginDTO.getLogin_pass());
				managerDTO.setVisit_type(visit_type);
				managerDTO.setParent_company_seq("0");
				managerDTO.setUse_yn("1");
				resultMap = managerService.managerLoginProc(request, managerDTO);
			}else{
				EmployerDTO eDTO = new EmployerDTO();
				eDTO.setEmployer_num( loginDTO.getLogin_id() );
				eDTO.setEmployer_pass( loginDTO.getLogin_pass());
				eDTO.setUseYn("1");
				eDTO.setVisit_type(visit_type);

				resultMap = employerService.employerLoginProc(request,eDTO);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			e.printStackTrace();
		}

		return resultMap;
	}

	@RequestMapping(value="/auth")
	public String auth(HttpSession session, Model model) {
		model.addAttribute("isCloseButton" , false);
		model.addAttribute("isMenu" , false);
		model.addAttribute("pageTitle" , "휴대폰 인증");
		model.addAttribute("prevLink" , "/manager/login");

		return "/manager/auth.manager";
	}

	/**
	 * 인증번호 받기
	 * 앱설치 일가자사용 유무 일가자 푸쉬 일반 푸쉬 등을 모두 update 한다.
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAuthNum")	
	@ResponseBody
	public Map<String, String> getAuthNum(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		ApiMsg 			msg 	= new ApiMsg();				
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			managerDTO.setParent_company_seq("0");
			managerDTO.setUse_yn("1");
			
			String smsAuth = managerService.getAuthNum(managerDTO);

			resultMap.put("code", "0000");
			resultMap.put("smsAuth", smsAuth);
		} catch (Exception e) {
			e.printStackTrace();				// 예외 리턴
			String Msg = e.getMessage();

			try {
				resultMap.put("code", Msg.split("!;!")[0]);
				resultMap.put("message", Msg.split("!;!")[1]);
			} catch (Exception e2) {
				// TODO: handle exception
				resultMap.put("code", "9999");
				resultMap.put("message", "오류가 발생했습니다.");
			}
		}

		return resultMap;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 5. 16.
	 * @dec    : 패스워드 변경 폼
	 * 
	 * @param session
	 * @param model
	 * @param managerDTO
	 * @return
	 */
	@RequestMapping(value="/password")
	public String password(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model,ManagerDTO managerDTO, RedirectAttributes redirectAttributes)  {
		if("".equals(managerDTO.getManager_phone()) || "".equals(managerDTO.getAuth_num())){
			//  System.out.println("managerDTO.getManager_phone() :" + managerDTO.getManager_phone());
			//  System.out.println("managerDTO.getAuth_num() :" + managerDTO.getAuth_num());
			return "redirect:/manager/auth";
		}
		managerDTO.setParent_company_seq("0");
		managerDTO.setUse_yn("1");

		ManagerDTO resultDTO = managerService.selectManagerInfo(managerDTO);
		if(resultDTO == null ){

			redirectAttributes.addAttribute("manager_phone", managerDTO.getManager_phone());
			return "redirect:/manager/managerReg";
		}

		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			final int KEY_SIZE = 2048;
			generator.initialize(KEY_SIZE);

			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();

			// 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
			session.setAttribute("__rsaPrivateKeyManager__", privateKey);

			// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

			model.addAttribute("publicKeyModulus", publicKeyModulus);
			model.addAttribute("publicKeyExponent", publicKeyExponent);
		} catch(Exception e) {
			model.addAttribute("publicKeyModulus", "");
			model.addAttribute("publicKeyExponent", "");
			e.printStackTrace();
		}

		model.addAttribute("isCloseButton" , false);
		model.addAttribute("isMenu" , false);
		model.addAttribute("pageTitle" , "비밀번호 만들기");
		model.addAttribute("prevLink" , "/manager/login");

		model.addAttribute("manager_seq" , resultDTO.getManager_seq());
		model.addAttribute("manager_name" , resultDTO.getManager_name());
		model.addAttribute("manager_phone" , resultDTO.getManager_phone());

		return "/manager/password.manager";
	}
	  
	  /**
	   * 패스워드 처리
	  *  패스워드 변경처리
	   * @throws Exception
	   */
	@RequestMapping(value="/passwordProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> passwordProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
	    
	    try {
	    	PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyManager__");

	    	managerDTO.setManager_seq((StringUtil.decryptRsa(privateKey, managerDTO.getManager_seq() )));
	    	managerDTO.setManager_phone((StringUtil.decryptRsa(privateKey, managerDTO.getManager_phone() )));
	        managerDTO.setManager_pass((StringUtil.decryptRsa(privateKey, managerDTO.getManager_pass() )));
	        
	    	//패스워드 변경
	    	ManagerDTO paramDTO = new ManagerDTO();
	    	paramDTO.setManager_seq(managerDTO.getManager_seq());
	    	paramDTO.setManager_pass(managerDTO.getManager_pass());
	    	managerService.setManagerInfo(paramDTO);
	    	
	    	//로그인 프로 세스
	    	ManagerLoginDTO loginDTO = new ManagerLoginDTO();
	    	loginDTO.setLogin_type("M");
	    	loginDTO.setLogin_id(managerDTO.getManager_phone());
	    	loginDTO.setLogin_pass(managerDTO.getManager_pass());
	    	
	    	resultMap = LoginProcess( request,  response,  session,  loginDTO);

			if ( resultMap.get("code").equals("0000") ) {
				session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
			}
	    } catch (Exception e) {
	    	// TODO: handle exception
	    	resultMap.put("code", "9999");
	    	e.printStackTrace();
	    }

	    return resultMap;
	}
	
	@RequestMapping(value="/personalTerms")
	public String personalTerms(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model ,ManagerDTO managerDTO)  {
		model.addAttribute("isCloseButton" , false);
		model.addAttribute("isMenu" , false);
		model.addAttribute("pageTitle" , "개인정보취급방침");
		model.addAttribute("prevLink" , "/manager/managerReg?manager_phone="+managerDTO.getManager_phone());
		model.addAttribute("manager_phone" , managerDTO.getManager_phone());

		return "/manager/personalTerms.manager";
	}
	  
	@RequestMapping(value="/serviceTerms")
	public String serviceTerms(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model ,ManagerDTO managerDTO)  {
		model.addAttribute("isCloseButton" , false);
		model.addAttribute("isMenu" , false);
		model.addAttribute("pageTitle" , "서비스이용약관");
		model.addAttribute("prevLink" , "/manager/managerReg?manager_phone="+managerDTO.getManager_phone());
		model.addAttribute("manager_phone" , managerDTO.getManager_phone());

		return "/manager/serviceTerms.manager";
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 8. 30.
	 * @dec    : 매니저 등록
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @param managerDTO
	 * @return
	 */
	@RequestMapping(value="/managerReg")
	public String managerReg(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, ManagerDTO managerDTO)  {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			final int KEY_SIZE = 2048;
			generator.initialize(KEY_SIZE);

			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();

			// 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
			session.setAttribute("__rsaPrivateKeyManager__", privateKey);

			// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

			model.addAttribute("publicKeyModulus", publicKeyModulus);
			model.addAttribute("publicKeyExponent", publicKeyExponent);
		} catch(Exception e) {
			model.addAttribute("publicKeyModulus", "");
			model.addAttribute("publicKeyExponent", "");
			e.printStackTrace();
		}

		model.addAttribute("isCloseButton" , false);
		model.addAttribute("isMenu" , false);
		model.addAttribute("pageTitle" , "현장매니저 등록");
		model.addAttribute("prevLink" , "/manager/login");
		model.addAttribute("manager_phone" , managerDTO.getManager_phone());

		return "/manager/managerReg.manager";
	}
	
	/**
	 *매니저등록
	 * @throws Exception
	 */
	@RequestMapping(value="/managerProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> managerProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
	    
	    try {
	    	PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyManager__");

	        managerDTO.setManager_phone((StringUtil.decryptRsa(privateKey, managerDTO.getManager_phone() )));
	        managerDTO.setManager_pass((StringUtil.decryptRsa(privateKey, managerDTO.getManager_pass() )));
	        
	        //메니저 등록 하기
	        managerDTO.setManager_type("O");
	        managerDTO.setCompany_seq(Const.JOBNPARTNER_SEQ);
	        managerDTO.setEmployer_seq("0");
	        managerDTO.setReg_admin("manager");
	        managerDTO.setMod_admin("manager");
	        
	        managerService.setManager(managerDTO);
	        
	    	//로그인 프로 세스
	    	ManagerLoginDTO loginDTO = new ManagerLoginDTO();
	    	loginDTO.setLogin_type("M");
	    	loginDTO.setLogin_id(managerDTO.getManager_phone());
	    	loginDTO.setLogin_pass(managerDTO.getManager_pass());
	    	
	    	resultMap = LoginProcess( request,  response,  session,  loginDTO);
	    	
			if ( resultMap.get("code").equals("0000") ) {
				session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
			}
	    } catch (Exception e) {
	      // TODO: handle exception
	      resultMap.put("code", "9999");
	      e.printStackTrace();
	    }

	    return resultMap;
	}
	  
	@RequestMapping(value="/main")
	public String main(HttpServletRequest request, HttpServletResponse response, HttpSession session,IlboDTO ilboDTO, Model model) throws Exception {
		ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
		  
		String start_ilbo_date 		= ilboDTO.getStart_ilbo_date();
		String end_ilbo_date 		= ilboDTO.getEnd_ilbo_date();
		String work_seq 			= ilboDTO.getWork_seq();		//현장 번호
		  
		if(StringUtil.isNull(start_ilbo_date)){
			//start_ilbo_date = DateUtil.getCurDate2();
			start_ilbo_date = DateUtil.addDay(-30,"yyyy-MM-dd" );
		}
		
		if(StringUtil.isNull(end_ilbo_date)){
			end_ilbo_date  = DateUtil.addDay(30,"yyyy-MM-dd" );
		}
		  
		if(StringUtil.isNull(work_seq)){
			work_seq  = "0";	//map 에서 0 이 넘어 오면 where 절에서 if 에 걸리도록 처리 하였음
		}
		  
		String manager_type = sessionDTO.getManager_type();
		String employer_seq = sessionDTO.getEmployer_seq();
		  
		ilboDTO.setManager_type( manager_type);
		ilboDTO.setEmployer_seq( employer_seq ); 	//일보리스트 파람 에 추가
		ilboDTO.setManager_seq(sessionDTO.getSeq());
		ilboDTO.setManager_phone(sessionDTO.getLogin_id());	//메이져 중복등록 하면서 추가 20190516
		ilboDTO.setStart_ilbo_date( start_ilbo_date );
		ilboDTO.setEnd_ilbo_date( end_ilbo_date );
		ilboDTO.setWork_seq(work_seq);
		ilboDTO.setUse_yn("1");
		ilboDTO.setLogin_type(sessionDTO.getLogin_type());
		  
		ilboDTO.getPaging().setPageSize(15);
		int rowCount = managerService.getIlboTotalCnt(ilboDTO);
		ilboDTO.getPaging().setRowCount(rowCount);
		List<IlboDTO> resultList = managerService.getIlboList(ilboDTO);		
		
		model.addAttribute("isCloseButton" , true);
		model.addAttribute("isMenu" , true);
		model.addAttribute("pageTitle" , "구인현황");
		model.addAttribute("prevLink" , "");
		model.addAttribute("manager_seq" , sessionDTO.getSeq());
		model.addAttribute("loginName" , sessionDTO.getLogin_name());
		model.addAttribute("managerName", sessionDTO.getManager_name());
		model.addAttribute("managerPhone", sessionDTO.getManager_phone());
		model.addAttribute("manager_push_yn" , sessionDTO.getManager_push_yn());
		model.addAttribute("login_type", sessionDTO.getLogin_type());
		  
		//전화번호를 구분하기 위함 이다.
		if("company".equals( sessionDTO.getOwner_id())){
			model.addAttribute("company_tel" ,  sessionDTO.getCompany_tel());  
		}else{
			model.addAttribute("company_tel" ,  sessionDTO.getPartner_tel());
		}
		  
		model.addAttribute("manager_type" , manager_type);
		model.addAttribute("employer_seq" , employer_seq);
		model.addAttribute("work_seq" , work_seq);
		model.addAttribute("start_ilbo_date" , start_ilbo_date);
		model.addAttribute("end_ilbo_date" , end_ilbo_date);
		model.addAttribute("resultList" ,resultList);
		  
		String managerMode = "E";
		  
		if("O".endsWith(manager_type) ){
			managerMode = "M"; 
		}
		  
		model.addAttribute("managerMode" ,managerMode);
		  
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setUse_yn("1");
		codeDTO.setCode_type("400");
		List<CodeDTO> kindList = commonService.getCommonCodeList(codeDTO);
		model.addAttribute("kindList", kindList);
	
	    return "/manager/main.manager";
	}

	/**
	 * worker 정보 가져오기
	 * @throws Exception
	 */
	@RequestMapping(value="/getWorkList")
	@ResponseBody
	public Map<String, Object> getWorkList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);

			//현장 리스트
			//현장리스트 paramDTO
			WorkDTO paramDTO = new WorkDTO();
			String manager_type = sessionDTO.getManager_type();

			//구인처 현장매니저 일때
			if(manager_type.equals("E")){	//구인처 현장매니저
				if("E".equals(sessionDTO.getLogin_type()) ){
					paramDTO.setEmployer_seq( sessionDTO.getSeq() );	//현장리스트 파람에 추가
				}else{
					paramDTO.setEmployer_seq( sessionDTO.getEmployer_seq() );	//본사 매니저인경우
				}
			}else{
				paramDTO.setManager_phone(sessionDTO.getLogin_id());
			}

			List<WorkDTO> workList = managerService.getWorkList(paramDTO);

			resultMap.put("code", "0000");
		    resultMap.put("resultList", workList);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			e.printStackTrace();
		}

		return resultMap;
	}
	  
	  /**
	   * worker 정보 가져오기
	  
	   * @throws Exception
	   */
	@RequestMapping(value="/getIlboWorker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getIlboWorker(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception{
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    
	    try {
	    	ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
	    	    	
	    	IlboDTO resultDTO = managerService.getIlboWorker(ilboDTO);

	    	FileDTO paramDTO = new FileDTO();
	    	paramDTO.setService_type("WORKER");
	    	paramDTO.setService_seq(resultDTO.getWorker_seq());
	    	List<FileDTO> fileList = fileService.selectFileList(paramDTO);
	    	
	    	for (FileDTO fileDTO : fileList) {
	    		resultMap.put(fileDTO.getService_code() + "_path", fileDTO.getFile_path());
	    		resultMap.put(fileDTO.getService_code() + "_fileName", fileDTO.getFile_name());
			}
	    	
	    	resultMap.put("code", "0000");
	    	resultMap.put("resultDTO", resultDTO);
	    	
	    	//resultMap.put("ilbo_work_name", resultDTO.getIlbo_work_name());
	    } catch (Exception e) {
	    	// TODO: handle exception
	    	resultMap.put("code", "9999");
	    	e.printStackTrace();
	    }

	    return resultMap;
	}
	  
	//Excel 다운로드
	@RequestMapping(value="/getIlboExcel")
	public View getIlboExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, IlboDTO ilboDTO) throws Exception {
	   // Session 설정
	   String start_ilbo_date = ilboDTO.getStart_ilbo_date();
	   String end_ilbo_date   = ilboDTO.getEnd_ilbo_date();

	   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	   Calendar c = Calendar.getInstance();

	   int iYear, iMonth, iDay;
	   iYear  = Integer.parseInt(end_ilbo_date.substring(0, 4));
	   iMonth = Integer.parseInt(end_ilbo_date.substring(5, 7));
	   iDay   = Integer.parseInt(end_ilbo_date.substring(8, 10));

	   String sCaseWhere = "", sSumWhere = "";

	   int days = (int) DateUtil.getDateDiff(start_ilbo_date, end_ilbo_date, "yyyy-MM-dd");

	   List dayList = new ArrayList();
	   for ( int i = days; i >= 0; i-- ) {
		   c.clear();
		   c.set(iYear, iMonth - 1, iDay - i);
		   java.util.Date d = c.getTime();
		   String thedate = sdf.format(d);

		   sCaseWhere += "CASE WHEN a.ilbo_day = '"+ thedate +"' THEN COUNT(*) ELSE 0 END AS day_"+ ((days + 1) - i) +", ";
		   sSumWhere  += "SUM(b.day_"+ ((days + 1) - i) +") AS day_"+ ((days + 1) - i) +", ";
		   
		   dayList.add(days - i, thedate);
	   }
	   
	   ilboDTO.setWhere(sCaseWhere);
	   ilboDTO.setSord(sSumWhere);

	   // 일력소 정보
	   IlboDTO info = ilboService.getIlboExcelInfo(ilboDTO);
	   
	   info.setSrh_type(ilboDTO.getSrh_type());
	   info.setStart_ilbo_date(start_ilbo_date);
	   info.setEnd_ilbo_date(end_ilbo_date);

	   model.addAttribute("filename", "["+ start_ilbo_date +"~"+ end_ilbo_date +"]노무비대장_"+ info.getEmployer_name() +"_"+ info.getWork_name() +".xls");
	   model.addAttribute("subject", "일용근로자 노무비 대장");
	   model.addAttribute("info", info);
	   model.addAttribute("list", ilboService.getIlboExcel(ilboDTO));
	   model.addAttribute("dayList", dayList);

	   return new LaborExpensesExcel();
	}
	 
	 
	  /**
	 * @author : nemojjang
	 * @date   : 2019. 9. 4.
	 * @dec    : 메니져정보 바꾸기
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param managerDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setManagerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setManagerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    if(StringUtil.isNull(managerDTO.getManager_seq())){
	    	resultMap.put("code", "1000");
	    	return resultMap;
		}
	    
	    try {
	    	managerDTO.setParent_company_seq("0");   
	    	managerDTO.setUse_yn("1");
	    	
	    	managerService.setManagerInfo(request, session, managerDTO);
	    	    	
	    	resultMap.put("code", "0000");
	    } catch (Exception e) {
	      // TODO: handle exception
	      resultMap.put("code", "9999");
	      resultMap.put("message", "오류가 발생 하였습니다..");
	      e.printStackTrace();
	    }

	    return resultMap;
	}
	
	/**
	 * @author : nemojjang
	 * @date   : 2019. 9. 5.
	 * @dec    : 현장등록
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param workDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setManagerWork")
	@ResponseBody
	public Map<String, String> setManagerWork(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception{
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
	    
	    resultMap = managerService.setManagerWork(sessionDTO, workDTO);

	    return resultMap;
	}
	
	/**
	 * 앱 오더 등록
	 * 
	 */
	@RequestMapping("/regWorkProcess")
	@ResponseBody
	public Map<String, String> regWorkProcess(HttpServletRequest request,HttpServletResponse response , HttpSession session, ManagerWorkDTO mDTO){
		Map<String, String> resultMap = new HashMap<String, String>();
 
		try {
			managerService.regWorkProcess( session, mDTO);
			
			resultMap.put("code", "0000");
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "시스템 오류 입니다.");
		}

		return resultMap;
	}
	
	// 내일보내주세요.
	@RequestMapping(value="/copyIlboCell")
	@ResponseBody
	public Map<String, String> copyIlboCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
	    
	    try {
	    	ilboDTO.setTo_ilbo_date(DateUtil.addDay(1, "yyyy-MM-dd "));
			ilboDTO.setIlbo_work_order_code("ORD002");
			ilboDTO.setIlbo_work_order_name("APP");

			if(ilboDTO.getCopy_worker().equals("Y")){
				/*
				 * ilboDTO.setOutput_status_code("100018"); ilboDTO.setOutput_status_name("요청");
				 */
				ilboDTO.setIlbo_worker_status_code("WSC005");
				ilboDTO.setIlbo_worker_status_name("요청");
			}else{
				ilboDTO.setOutput_status_code("100002");
				ilboDTO.setOutput_status_name("대기");
			}
			
			ilboDTO.setReg_admin( "manager");
			ilboDTO.setOwner_id(sessionDTO.getOwner_id());

	    	ilboService.setCopyIlbo(ilboDTO);
		    
		    resultMap.put("code", "0000");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "시스템 오류 입니다.");
		}
	    
	    return resultMap;
	}

	// 작업취소
	@RequestMapping(value="/setWorkCancel")
	@ResponseBody
	public  Map<String, String> setWorkCancel(HttpServletRequest request, HttpServletResponse response, HttpSession session, IlboDTO ilboDTO,CodeLogDTO logDTO) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
	    
	    try {
	        // Session 설정
	        ilboDTO.setMod_admin("manager");

	        //code log 추가 ======================================
	        IlboDTO paramDTO = new IlboDTO();
		    paramDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
		    IlboDTO ilboInfo = ilboService.selectIlboInfo(paramDTO);
		    
	        logDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	        
	        logDTO.setCode_value(ilboDTO.getIlbo_status_code());
	        logDTO.setCode_name("작업취소");
	        logDTO.setLog_user_type(sessionDTO.getLogin_type());
	        logDTO.setReg_admin(sessionDTO.getLogin_id());
	        logDTO.setLog_text( "manager");
	        
			logDTO.setWorker_seq(ilboInfo.getWorker_seq());
		    logDTO.setWorker_company_seq(ilboInfo.getWorker_company_seq());
		    logDTO.setWork_seq(ilboInfo.getWork_seq());
		    logDTO.setWork_company_seq(ilboInfo.getCompany_seq());
		    	        
	        ilboService.insertCodeLog(logDTO);
	        //=================================================
	        
	        // 일보정보 업데이트
	        ilboService.setIlboInfo(ilboDTO);
	        
	        try {
		        NotificationDTO notificationDTO = new NotificationDTO();
		        
		        notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	        	notificationDTO.setStatus_flag("ALR008");
		        
		        // 알림 보내는 메소드
	        	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
	        	notificationUtil.ilboAlim(notificationDTO);
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
		    
		    resultMap.put("code", "0000");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "시스템 오류 입니다.");
		}
	    
	    return resultMap;
	}
  
	  /**
	 * @author : nemojjang
	 * @date   : 2019. 9. 8.
	 * @dec    : 패스워드 변경
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param managerDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
	    Map<String, String> resultMap = new HashMap<String, String>();
	  
	    try {
	    	ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
	    	
	    	if("M".equals(sessionDTO.getLogin_type() )){
	    		managerDTO.setManager_phone(sessionDTO.getLogin_id());
	    		managerDTO.setParent_company_seq("0");
	    		managerDTO.setUse_yn("1");
	    		
	    		ManagerDTO resultDTO = managerService.selectManagerInfo(managerDTO);
	    		
	    		if(resultDTO == null){
	    			resultMap.put("code", "1000");
	    			resultMap.put("message", "현재 비밀번호를 잘못 입력 하셨습니다.");
	    			
	    			return resultMap;
	    		}
	    		
	    		managerDTO.setManager_seq(resultDTO.getManager_seq());
	    		managerDTO.setManager_pass(managerDTO.getManager_new_pass());
	    		managerDTO.setParent_company_seq("0");   
		    	managerDTO.setUse_yn("1");
		    	
		    	managerService.setManagerInfo(request, session, managerDTO);
	    	}else{
	    		EmployerDTO employerDTO = new EmployerDTO();
	    		employerDTO.setEmployer_num(sessionDTO.getLogin_id());
	    		employerDTO.setEmployer_pass(managerDTO.getManager_pass());
	    		
	    		EmployerDTO resultDTO = employerService.selectEmployerInfo(employerDTO);
	    		
	    		if(resultDTO == null){
	    			resultMap.put("code", "1000");
	    			resultMap.put("message", "현재 비밀번호를 잘못 입력 하셨습니다.");
	    			
	    			return resultMap;
	    		}
	    	
	    		employerDTO.setReg_admin("employer");
	    		employerDTO.setEmployer_seq(resultDTO.getEmployer_seq());
	    		managerDTO.setEmployer_pass(managerDTO.getManager_new_pass());
		    	
		    	employerService.setEmployerInfo( employerDTO);
	    	}
	    	    	
	    	resultMap.put("code", "0000");
	    } catch (Exception e) {
	    	// TODO: handle exception
	    	resultMap.put("code", "9999");
	    	resultMap.put("message", "오류가 발생 하였습니다..");
	    	e.printStackTrace();
	    }

	    return resultMap;
	}
	  
	  /**
	   *  employe 정보 가져오기
	  
	   * @throws Exception
	   */
	@RequestMapping(value="/getEmployerInfo")
	@ResponseBody
	public Map<String, Object> getEmployerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
			employerDTO.setEmployer_seq(sessionDTO.getSeq());
			  
			EmployerDTO employerInfo = employerService.getEmployerView(employerDTO);

			resultMap.put("code", "0000");
		    resultMap.put("employerInfo", employerInfo);

		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			resultMap.put("message", "시스템 오류 입니다.");
			e.printStackTrace();
		}

		return resultMap;
	}
	  
	@RequestMapping(value="/setEmployerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setEmployerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception{
	    Map<String, String> resultMap = new HashMap<String, String>();
	    ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);

	    try {
	    	CommonDTO commonDTO = new CommonDTO();
	        commonDTO.setService_type("EMPLOYER");
	        commonDTO.setService_seq(sessionDTO.getSeq());
	        commonDTO.setReg_admin("EMPLOYER");
	        
	        commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") +"employer/", "employer/");
	        
	    	employerDTO.setEmployer_seq(sessionDTO.getSeq());
	    	employerDTO.setReg_admin("employer");
	    	employerDTO.setMod_admin("employer");
	    	employerService.setEmployerInfo(employerDTO);
	    	
	    	resultMap.put("code", "0000");
	    } catch (Exception e) {
	    	// TODO: handle exception
		    resultMap.put("code", "9999");
		    resultMap.put("message", "시스템 오류 입니다.");
		    e.printStackTrace();
	    }

	    return resultMap;
	}
	  
	@RequestMapping(value = "/imgLoad")
	public void imgLoad(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String path = StringUtil.isNullToString(request.getParameter("path"));
		String name = StringUtil.isNullToString(request.getParameter("name"));
		String imgformat = "";
		String filename = "";
	
		name = name.replaceAll("\\.\\./", "");
		path = path.replaceAll("\\.\\./", "");
		filename = path + "/" + name;
		imgformat = filename.substring(filename.lastIndexOf(".") + 1);
		imgformat = imgformat.toLowerCase();
		filename = filename.replaceAll("//", "/");
	
		try {
			File file = new File(filename);
	
			if ( file.exists() ) {
				FileCoder coder = new FileCoder(commonProperties.getProperty("fileSecretKeyPath"));
				coder.decryptWeb(Cipher.DECRYPT_MODE, file, response);
			}else {
					System.out.println("파일없음");
			}
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}    
	}
}
