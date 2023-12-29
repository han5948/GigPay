package com.nemo.kr.controller.manager;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.excel.LaborExpensesExcel;
import com.nemo.kr.service.FileService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.FileCoder;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;



@Controller
@RequestMapping("/managerBack")
public class OrderManagerController {

	private static final Logger logger = LoggerFactory.getLogger(OrderManagerController.class);

	@Resource(name="commonProperties")	  private Properties commonProperties;
  
	@Autowired ManagerService managerService;
	@Autowired IlboService ilboService;
	@Autowired FileService fileService;
  
	@RequestMapping(value = { "", "/" })
	public String index(Model model, HttpSession session) {
		return "redirect:/manager/intro"; //인트로 컨트롤러
	}

	@RequestMapping(value="/intro")
	public String intro(HttpSession session, Model model) {
		return "/manager/intro.no_manager";	//인트로 페이지 html
	}
  
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
  
  /**
   * 로그인 처리
   * @param request
   * @param response
   * @param session
   * @param userDTO
   * @return
   * @throws Exception
   */
	@RequestMapping(value="/loginProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKeyManager__");

			managerDTO.setManager_phone((StringUtil.decryptRsa(privateKey, managerDTO.getManager_phone() )));
			managerDTO.setManager_pass((StringUtil.decryptRsa(privateKey, managerDTO.getManager_pass() )));
		} catch (Exception e) {
			// 	TODO: handle exception
			resultMap.put("code", "8000");
			e.printStackTrace();
			return resultMap;
		}

		try {
			//처음 등록 시킨 메니져만 사용 하도록 한다.
			managerDTO.setParent_company_seq("0");
			resultMap = managerService.managerLoginProc(request, managerDTO);

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
  
  /**
   * 로그인 처리
   * 
   * @param request
   * @param response
   * @param session
   * @param userDTO
   * @return
   * @throws Exception
   */
	@RequestMapping(value="/autoLoginProc")
	public String autoLoginProc(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			managerDTO.setParent_company_seq("0");
			resultMap = managerService.managerLoginProc(request, managerDTO);
			
			if ( resultMap.get("code").equals("0000") ) {
				return "redirect:/manager/main?start_ilbo_date="+managerDTO.getStartDate()+"&end_ilbo_date="+managerDTO.getStartDate();
			}else{
				return "redirect:/manager/login";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "redirect:/manager/login";
		}
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
	public String password(HttpSession session, Model model,ManagerDTO managerDTO)  {
		if("".equals(managerDTO.getManager_phone()) || "".equals(managerDTO.getAuth_num())){
			//  System.out.println("managerDTO.getManager_phone() :" + managerDTO.getManager_phone());
			//  System.out.println("managerDTO.getAuth_num() :" + managerDTO.getAuth_num());
			return "redirect:/manager/auth";
		}
		managerDTO.setParent_company_seq("0");
		
		ManagerDTO resultDTO = managerService.selectManagerInfo(managerDTO);
		if(resultDTO == null || !resultDTO.getAuth_num().equals(managerDTO.getAuth_num())){
			//  System.out.println("managerDTO.getAuth_num() :" + managerDTO.getAuth_num());
			//  System.out.println("resultDTO.getAuth_num() :" + resultDTO.getAuth_num());
			return "redirect:/manager/auth";
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
	    model.addAttribute("manager_phone" , resultDTO.getManager_phone());
	  
	    return "/manager/password.manager";
	}
  
  /**
   * 패스워드 처리
  
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
        
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_seq(managerDTO.getManager_seq());
			paramDTO.setManager_pass(managerDTO.getManager_pass());
			managerService.setManagerInfo(paramDTO);
			
			resultMap = managerService.managerLoginProc(request, managerDTO);

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
  
  /**
   * 패스워드 처리
  
   * @throws Exception
   */
	@RequestMapping(value="/setManagerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setOrderManager(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
    
		if(StringUtil.isNull(managerDTO.getManager_seq())){
			resultMap.put("code", "1000");
			return resultMap;
		}
    
		try {
			managerDTO.setParent_company_seq("0");    	
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
  
	@RequestMapping(value="/main")
	public String main(HttpServletRequest request, HttpServletResponse response, HttpSession session,IlboDTO ilboDTO, Model model) throws Exception {
		ManagerDTO sessionDTO = (ManagerDTO) session.getAttribute(Const.managerSession);
	  
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
		ilboDTO.setManager_seq(sessionDTO.getManager_seq());
		ilboDTO.setManager_phone(sessionDTO.getManager_phone());	//메이져 중복등록 하면서 추가 20190516
		ilboDTO.setStart_ilbo_date( start_ilbo_date );
		ilboDTO.setEnd_ilbo_date( end_ilbo_date );
		ilboDTO.setWork_seq(work_seq);
		ilboDTO.setUse_yn("1");
	  
		ilboDTO.getPaging().setPageSize(15);
		int rowCount = managerService.getIlboTotalCnt(ilboDTO);
		ilboDTO.getPaging().setRowCount(rowCount);
		List<IlboDTO> resultList = managerService.getIlboList(ilboDTO);		
	  
		//현장 리스트
		//현장리스트 paramDTO
		WorkDTO paramDTO = new WorkDTO();
		//구인처 현장매니저 일때
		if(manager_type.equals("E")){	//구인처 현장매니저
			paramDTO.setEmployer_seq( employer_seq );	//현장리스트 파람에 추가
		}else{
			paramDTO.setManager_phone(sessionDTO.getManager_phone());
		}
	  	  
		List<WorkDTO> workList = managerService.getWorkList(paramDTO);
    
		model.addAttribute("isCloseButton" , true);
		model.addAttribute("isMenu" , true);
		model.addAttribute("pageTitle" , "구인현황");
		model.addAttribute("prevLink" , "");
		model.addAttribute("manager_seq" , sessionDTO.getManager_seq());
		model.addAttribute("manager_name" , sessionDTO.getManager_name());
		model.addAttribute("manager_push_yn" , sessionDTO.getManager_push_yn());
	  
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
		model.addAttribute("workList" ,workList);			//현장리스트
		
		return "/manager/main.manager";
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
			ManagerDTO sessionDTO = (ManagerDTO) session.getAttribute(Const.managerSession);
    	    	
			IlboDTO resultDTO = managerService.getIlboWorker(ilboDTO);
    	
			FileDTO paramDTO = new FileDTO();
			paramDTO.setService_type("WORKER");
			paramDTO.setService_seq(resultDTO.getWorker_seq());
			List<FileDTO> fileList = fileService.selectList(paramDTO);
			
			for (FileDTO fileDTO : fileList) {
				resultMap.put(fileDTO.getService_code() + "_path", fileDTO.getFile_path());
				resultMap.put(fileDTO.getService_code() + "_fileName", fileDTO.getFile_name());
			}

			resultMap.put("code", "0000");
			resultMap.put("resultDTO", resultDTO);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "9999");
			e.printStackTrace();
		}
		
		return resultMap;
	}
  
	@RequestMapping(value = "/logOut")
	public String logOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.invalidate();

		return "redirect:/manager/login";
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
		
		// 	일력소 정보
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
 
	@RequestMapping(value = "/imgLoad")
	public void imgLoad(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String basePath = commonProperties.getProperty("uploadPath");
		String path = StringUtil.isNullToString(request.getParameter("path"));
		String name = StringUtil.isNullToString(request.getParameter("name"));
		String imgformat = "";
		String filename = "";

		name = name.replaceAll("\\.\\./", "");
		path = path.replaceAll("\\.\\./", "");
		
		if ( path.startsWith(basePath) ) {
			if ( name != null && name.length() > 0 ) {
				filename = path + "/" + name;
			}
		} else {
			filename = basePath + path + "/" + name;
		}
		
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
		
		/*
		   if (imgformat.equals("gif")) {
		     imgformat = "image/gif";
		   } else if (imgformat.equals("jpg") || imgformat.equals("jpeg") || imgformat.equals("jpe")) {
		     imgformat = "image/jpeg";
		   } else if (imgformat.equals("tif") || imgformat.equals("tiff")) {
		     imgformat = "image/tiff";
		   } else if (imgformat.equals("bmp")) {
		     imgformat = "image/bmp";
		   } else if (imgformat.equals("png")) {
		     imgformat = "image/png";
		   } else {
		     imgformat = "text/html;charset=utf-8";
		   }
		
		   		
		   InputStream in = null;
		   OutputStream os = null;
		
		   try {
		     File file = new File(filename);
		
		     if ( file.exists() ) {
		       in = new BufferedInputStream(new FileInputStream(file));
		
		       if ( in != null ) {
		 response.reset();
		 response.setContentType(imgformat);
		 os = new BufferedOutputStream(response.getOutputStream());
		 byte[] b = new byte[512];
		 int leng = 0;
		 while ( (leng = in.read(b)) != -1 ) {
		   os.write(b, 0, leng);
		 }
		 os.close();
		 in.close();
		       }
		
		       os = null;
		       in = null;
		     }
		   } catch (SocketException se) {
		     se.printStackTrace();
		   } catch (Exception e) {
		     e.printStackTrace();
		   } finally {
		     if ( in != null ) try {
		       in.close();
		     } catch (Exception ise) {
		       ise.printStackTrace();
		     } finally {
		       in = null;
		     }
		     if ( os != null ) try {
		       os.close();
		     } catch (Exception ose) {
		       ose.printStackTrace();
		     } finally {
		       os = null;
		     }
		   }    
		*/
	}
}
