package com.nemo.kr.controller.admin;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CommSearchDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.dto.WorkerLandingLogDTO;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.FileService;
import com.nemo.kr.service.JobService;
import com.nemo.kr.util.FileCoder;
import com.nemo.kr.util.FileUtil;
import com.nemo.kr.util.JsonUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * 일가자 공통모듈 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
/**
  * @FileName : CommonController.java
  * @Date : 2020. 9. 15. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 :
  */
@Controller
@RequestMapping("/admin")
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired CommonService commonService;
	@Autowired JobService jobService;
	@Autowired FileService fileService;
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	// 일일대장
	@RequestMapping(value="/jobPriceCal")
	public String ilboList(HttpSession session, Model model) throws IllegalArgumentException, IllegalAccessException {
		return "/admin/jobPriceCal.popup_admin";
	}
  
	// Session 설정
	private CommonDTO setSessionToDTO(HttpSession session, CommonDTO commonDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(commonDTO.getCompany_seq());

		// Session 설정
		commonDTO.setAdminLevel(""+ auth_level);

		if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
			if ( "".equals(get_company_seq) ) {
				commonDTO.setCompany_seq(company_seq);
			}
		}

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			commonDTO.setOwner_id( admin_id);
		}

		commonDTO.setCompany_name(company_name);
		commonDTO.setReg_admin(admin_id);

		return commonDTO;
	}
	
	// Session 설정
	private CommonDTO setSessionToCommonDTO(HttpSession session, CommonDTO commonDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			commonDTO.setOwner_id( admin_id);
		}

		commonDTO.setCompany_name(company_name);
		commonDTO.setReg_admin(admin_id);

		return commonDTO;
	}


	// 코드 목록
	@RequestMapping(value="/getCommonCodeList")
	@ResponseBody
	public List<CodeDTO> getCommonCodeList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeDTO codeDTO) throws Exception {
		codeDTO.setDel_yn("0");
		List<CodeDTO> result = commonService.getCommonCodeList(codeDTO);

		return result;
	}
	
	@RequestMapping(value="/getJobCodeList")
	@ResponseBody
	public List<JobDTO> getJobCodeList(HttpServletRequest request, HttpServletResponse response, HttpSession session,  JobDTO jobDTO) throws Exception {
		if("N".equals(jobDTO.getPagingYn())) {
			jobDTO.setPaging(null);
		}
		List<JobDTO> result = jobService.selectJobList(jobDTO);

		return result;
	}
	
	@RequestMapping(value = "/getJobList")
	public void getJobList(HttpServletResponse response, HttpServletRequest request ,  JobDTO jobDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		//pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_version]", workerDTO.getApp_version());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			List<JobDTO> resultList = jobService.getJobList(jobDTO);
			
			result.put("jobList", resultList);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {

			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}
	
	// 회사명 검색
	@RequestMapping(value="/getCompanyNameList")
	@ResponseBody
	public List<CommonDTO> getCompanyNameList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {
		// Session 설정
		commonDTO = setSessionToDTO(session, commonDTO);

		List<CommonDTO> result = commonService.getCompanyNameList(commonDTO);

		return result;
	}

	//회사명 검색
	/**
	 * @author : nemojjang
	 * @date   : 2018. 10. 26.
	 * @dec    : 위에것은 관리자 인지 안닌지를 구분해서 사용을 못하고 그냥 다시 만듬
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param commonDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCompanyNameList2")
	@ResponseBody
	public List<CommonDTO> getCompanyNameList2(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {

		List<CommonDTO> result = commonService.getCompanyNameList(commonDTO);

		return result;
	}


	// 구인처명 검색
	@RequestMapping(value="/getEmployerNameList")
	@ResponseBody
	public List<CommonDTO> getEmployerNameList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {

		List<CommonDTO> result = commonService.getEmployerNameList(commonDTO);

		return result;
	}

	// 구인처명 확인
	@RequestMapping(value="/chkEmployerName")
	@ResponseBody
	public boolean chkEmployerName(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {
		boolean result = false;

		// Session 설정
		commonDTO = setSessionToDTO(session, commonDTO);

		int iCnt = commonService.chkEmployerName(commonDTO);
		if ( iCnt <= 0 ) {
			result =  true;
		}

		return result;
	}

	// 메니져폰 확인
	@RequestMapping(value="/chkManagerPhone")
	@ResponseBody
	public boolean chkManagerPhone(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {
		boolean result = false;

		int iCnt = commonService.chkManagerPhone(commonDTO);
		if ( iCnt <= 0 ) {
			result =  true;
		}

		return result;
	}

	// 구직자명 검색
	@RequestMapping(value="/getWorkerNameList")
	@ResponseBody
	public List<WorkerDTO> getWorkerNameList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		CommonDTO commonDTO = new CommonDTO();
		commonDTO.setCompany_seq(workerDTO.getCompany_seq());

		// Session 설정
		commonDTO = setSessionToDTO(session, commonDTO);

		workerDTO.setSrh_company_seq(commonDTO.getSrh_company_seq());
		workerDTO.setCompany_seq(commonDTO.getCompany_seq());
		
		workerDTO.setReg_admin(commonDTO.getReg_admin());
		workerDTO.setOwner_id(commonDTO.getOwner_id());

		List<WorkerDTO> result = commonService.getWorkerNameList(workerDTO);

		return result;
	}

	//현장매니저 이름 검색
	@RequestMapping(value="/getManagerNameList")
	@ResponseBody
	public List<WorkerDTO> getManagerNameList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		CommonDTO commonDTO = new CommonDTO();

		List<WorkerDTO> result = commonService.getManagerNameList(managerDTO);

		//System.out.println(result);

		return result;
	}

	// 출근한 구직자 검색
	@RequestMapping(value="/getTodayWorkerList")
	@ResponseBody
	public List<WorkerDTO> getTodayWorkerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		CommonDTO commonDTO = new CommonDTO();

		commonDTO.setCompany_seq(workerDTO.getCompany_seq());

		// Session 설정
		commonDTO = setSessionToDTO(session, commonDTO);

		workerDTO.setSrh_company_seq(commonDTO.getSrh_company_seq());
		workerDTO.setCompany_seq(commonDTO.getCompany_seq());
		workerDTO.setReg_admin(commonDTO.getReg_admin());

		List<WorkerDTO> result = commonService.getTodayWorkerList(workerDTO);

		return result;
	}

	// 현장명 검색 
	@RequestMapping(value="/getWorkNameList")
	@ResponseBody
	public List<WorkDTO> getWorkNameList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		CommonDTO commonDTO = new CommonDTO();
		commonDTO.setCompany_seq(workDTO.getCompany_seq());

		// Session 설정
		commonDTO = setSessionToCommonDTO(session, commonDTO);

		workDTO.setReg_admin(commonDTO.getReg_admin());
		workDTO.setOwner_id(commonDTO.getOwner_id());

		List<WorkDTO> result = commonService.getWorkNameList(workDTO);

		return result;
	}
	
	// 현장명 검색 > session param 없이 검색
	@RequestMapping(value="/getWorkNameList2")
	@ResponseBody
	public List<WorkDTO> getWorkNameList2(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		if("0".equals(workDTO.getSrh_company_seq()))
			workDTO.setSrh_company_seq("");

		List<WorkDTO> result = commonService.getWorkNameList(workDTO);

		return result;
	}
	
	// 현장명 검색  현장명만 검색 함.
	@RequestMapping(value="/getWorkNameList3")
	@ResponseBody
	public List<WorkDTO> getWorkNameList3(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		List<WorkDTO> result = commonService.getWorkNameList3(workDTO);

		return result;
	}
	
	@RequestMapping(value="/getWorkNameList4")
	@ResponseBody
	public HashMap<String, Object> getWorkNameList4(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		List<WorkDTO> result = commonService.getWorkNameList4(workDTO);

		resultMap.put("result", result);
		
		return resultMap;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 7. 11.
	 * @dec    : orderList 에서 사용한다...
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param workDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSearchWorkList")
	@ResponseBody
	public List<WorkDTO> getSearchWorkList(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkDTO workDTO) throws Exception {
		if("0".equals(workDTO.getSrh_company_seq()))
			workDTO.setSrh_company_seq("");

		List<WorkDTO> result = commonService.getSearchWorkList(workDTO);

		return result;
	}

	@RequestMapping(value="/getSearchEmployerList")
	@ResponseBody
	public List<EmployerDTO> getSearchEmployerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO EmployerDTO) throws Exception {
		if("0".equals(EmployerDTO.getSrh_company_seq()))
			EmployerDTO.setSrh_company_seq("");

		List<EmployerDTO> result = commonService.getSearchEmployerList(EmployerDTO);

		return result;
	}

	private String getHTML(List<CommonDTO> result) throws Exception {
		String html = "";
		/*
    Map<String, String> resultMap = new HashMap<String, String>();

    JSONArray jsonList = new JSONArray();
    JSONObject jsonObj = new JSONObject();

    for (int i = 0; i < result.size(); i++) {

      jsonObj.put("result", "sucess");    
      jsonList.add(jsonObj);
    }
		 */
		html += "<select>";
		html += "<option value='?'>?</option>";
		for ( int i = 0; i < result.size(); i++ ) {
			html += "<option value='" + result.get(i).getCode() +"'>"+ result.get(i).getText() +"</option>";
		}
		html +="</select>";

		return html;
	}

	//  public void imgLoad(ThumbNailTO to, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
	@RequestMapping(value = "/imgLoad")
	public void imgLoad(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String path = StringUtil.isNullToString(request.getParameter("path"));
		String name = StringUtil.isNullToString(request.getParameter("name"));
		String imgformat = "";
		String filename = "";

		name = name.replaceAll("\\.\\./", "");
		path = path.replaceAll("\\.\\./", "").toLowerCase();

		filename = path +"/"+  name;

		imgformat = filename.substring(filename.lastIndexOf(".") + 1);
		imgformat = imgformat.toLowerCase();
		filename = filename.replaceAll("//", "/");

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
			}else {
				System.out.println("파일없음");
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
	}
	
	/**
	  * @Method Name : decImgLoad
	  * @작성일 : 2021. 2. 15.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 이미지를 디코딩 해서 뿌리기
	
	  */
	@RequestMapping(value = "/decImgLoad")
	public void decImgLoad(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String path = StringUtil.isNullToString(request.getParameter("path"));
		String name = StringUtil.isNullToString(request.getParameter("name"));

		String filename = "";

		name = name.replaceAll("\\.\\./", "");
		path = path.replaceAll("\\.\\./", "").toLowerCase();

		filename = path +"/"+  name;
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

	/**
	 * 공통 파일 등록 프로세스
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @param boardCode
	 * @param commBoardDTO
	 * @return
	 */
	@RequestMapping(value = "/workerUploadFile")
	@ResponseBody
	public String workerUploadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, CommonDTO commonDTO) throws Exception {
		String result = "1";

		try {
			int serviceSeq = StringUtil.isNullToInt(request.getParameter("service_seq"));

			// Session 설정
			commonDTO = setSessionToDTO(session, commonDTO);

			commonDTO.setService_type("WORKER");
			commonDTO.setService_seq(""+ serviceSeq);

			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") +"worker/", "worker/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "0";
		}

		return result;
	}
	
	/**
	  * @Method Name : upLoadImgBase64
	  * @작성일 : 2020. 3. 24.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 이미지 수정
	
	  */
	@RequestMapping(value = "/upLoadImgBase64")
	@ResponseBody
	public String upLoadImgBase64(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		String result = "1";

		request.setCharacterEncoding("utf-8");
		String base64Str = request.getParameter("imgBase64");
		String file_path = request.getParameter("file_path");
		String file_name = request.getParameter("file_name");
		String filePath = file_path + file_name;
		String data = base64Str.split(",")[1];

		byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);

		try {
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			File target = new File(file_path +"temp_" +file_name);
			ImageIO.write(bufImg, "jpg", target);
			
			//파일 암호화 하고 기존 파일 삭제 하기....=========================================
			FileCoder coder = new FileCoder(commonProperties.getProperty("fileSecretKeyPath"));
			File dest = new File(filePath);
			coder.encrypt(target, dest);
			FileUtil.deleteFile(target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "0";

		}

		return result;
	}
	
	@RequestMapping(value = "/boardUploadImage")
	@ResponseBody
	public String boardUploadImage(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, FileDTO fileDTO) throws Exception {
		String result = "1";
		try {
			AdminDTO adminDTO = (AdminDTO) session.getAttribute(Const.adminSession);
			fileDTO.setFile_org_name(request.getHeader("file-org-name"));
			fileDTO.setFile_name(request.getHeader("file-name"));
			fileDTO.setFile_size(Integer.parseInt(request.getHeader("file-size")));
			fileDTO.setService_type("ADMIN");
			fileDTO.setService_code("BOARDIMAGE");
			fileDTO.setService_seq(adminDTO.getAdmin_seq());
			fileDTO.setFile_ext(fileDTO.getFile_name().substring(fileDTO.getFile_name().lastIndexOf(".")+1));
			fileDTO.setFile_order("0");
			fileDTO.setFile_url("admin/"+fileDTO.getFile_name());
			fileDTO.setFile_path(commonProperties.getProperty("imageUploadPath"));
			fileDTO.setReg_admin(adminDTO.getAdmin_id());
			fileService.insertInfo(fileDTO);
		}catch(Exception e) {
			e.printStackTrace();
			result = "0";
		}
		return result;
	}

	@RequestMapping(value = "/companyUploadFile")
	@ResponseBody
	public String companyUploadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, CommonDTO commonDTO) throws Exception {
		String result = "1";

		try {
			int serviceSeq = StringUtil.isNullToInt(request.getParameter("service_seq"));

			// Session 설정
			commonDTO = setSessionToDTO(session, commonDTO);

			commonDTO.setAdminSeq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_seq());
			commonDTO.setService_type("COMPANY");
			commonDTO.setService_seq(""+ serviceSeq);
			commonDTO.setName(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_name());
			commonDTO.setPhone(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_phone());
			
			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath")+"company/", "company/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "0";
		}

		return result;
	}

	@RequestMapping(value = "/employerUploadFile")
	@ResponseBody
	public String employerUploadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, CommonDTO commonDTO) throws Exception {
		String result = "1";
		try {
			int serviceSeq = StringUtil.isNullToInt(request.getParameter("service_seq"));

			// Session 설정
			commonDTO = setSessionToDTO(session, commonDTO);

			commonDTO.setService_type("EMPLOYER");
			commonDTO.setService_seq(""+ serviceSeq);
			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") +"employer/", "employer/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "0";
		}

		return result;
	}

	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public Map<String, String> deleteFile(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, FileDTO fileDTO) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			commonService.deleteFile(fileDTO);

			resultMap.put("code", "0000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "오류가 발생했습니다.");
		}

		return resultMap;
	}

	// 사진불러오기 popup을 두어 인터셉터에서 예외 처리
	/**
	  * @Method Name : getFile
	  * @작성일 : 2020. 3. 24.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : imageCrop, imagePopup	 	 type 으로 호출됨
	
	  */
	@RequestMapping(value="/popup/{type}")
	public String getFile(@PathVariable("type") String imageType, HttpServletRequest request, HttpServletResponse response, HttpSession session, FileDTO fileDTO, String vFlag, ModelMap model) throws Exception {
		//닫기 버튼 유무
		model.addAttribute("vFlag", vFlag); 

		List<FileDTO> list = commonService.selectFileList(fileDTO);

		if ( list.size() > 0 ) {
			FileDTO resultDTO = list.get(0);
			model.addAttribute("isFile", "Y");
			model.addAttribute("result", resultDTO);
		} else {
			model.addAttribute("isFile", "N");
		}
		
		return "/admin/"+ imageType+".popup_admin";
	}

	// code 로그 검색
	@RequestMapping(value="/getCodeLog")
	@ResponseBody
	public List<CodeLogDTO> getCodeLog(HttpServletRequest request, HttpServletResponse response, HttpSession session, CodeLogDTO logDTO) throws Exception {

		logDTO.setPaging(null);
		List<CodeLogDTO> result = commonService.getCodeLogList(logDTO);

		return result;
	}
	
	/**
	 * @Method Name : getBase64String
	 * @작성일 : 2020. 2. 4.
	 * @작성자 : Jangjaeho
	 * @변경이력 : 
	 * @Method 설명 : 이미지 base64 인코딩

	 */
	public static String getBase64String( String imageUrl ) throws Exception{
		String content ="a";
		String imageString = "";

		String filePathName = imageUrl.replace("file:///", "");
		String fileExtName = filePathName.substring( filePathName.lastIndexOf(".") + 1);

		FileInputStream inputStream = null;
		ByteArrayOutputStream byteOutStream = null;

		try {
			File file = new File( filePathName );

			if( file.exists() ) {
				inputStream = new FileInputStream( file );
				byteOutStream = new ByteArrayOutputStream();

				int len = 0;
				byte[] buf = new byte[1024];
				while( (len = inputStream.read( buf )) != -1 ) {
					byteOutStream.write(buf, 0, len);
				}

				byte[] fileArray = byteOutStream.toByteArray();
				imageString = new String( Base64.encodeBase64( fileArray ) );
				
				String changeString = "data:image/"+ fileExtName +";base64, "+ imageString;

				content = changeString;
			}else {
				System.out.println("파일없음");
			}
		}catch( IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}finally {
			inputStream.close();
			byteOutStream.close();
		}

		return content;
	}

	/**
	  * @Method Name : getKindCount
	  * @작성일 : 2020. 3. 17.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : workerList worker의 직종별 출역 횟수를 알아온다.
	
	  */
	@RequestMapping(value = "/getKindCount")
	@ResponseBody
	public Map<String, Object> getKindCount(HttpServletRequest request, HttpServletResponse response, HttpSession session, WorkerDTO workerDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			List<HashMap> resultList = commonService.selectWorkerKindCount(workerDTO);

			resultMap.put("code", "0000");
			resultMap.put("resultList", resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message", "오류가 발생했습니다.");
		}

		return resultMap;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getBase64String("C://Users/Jangjaeho/Desktop/unnamed.jpg") );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  * @Method Name : setSearchOptSession
	  * @작성일 : 2020. 9. 15.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : 페이지별 검색 조건 session set
	  */
	@RequestMapping("/setSearchOptSession")
  	@ResponseBody
  	public JSONObject setSearchOptSession(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommSearchDTO commSearchDTO) throws Exception {
  		JSONObject jsonObj = new JSONObject();

  		CommSearchDTO sessionCommSearchDTO = (com.nemo.kr.dto.CommSearchDTO) session.getAttribute(Const.SEARCHOPTION);
  		
  		if(sessionCommSearchDTO == null) {
  			sessionCommSearchDTO = new CommSearchDTO();
  		}
  		
  		Object obj = commSearchDTO;
		Method[] method = obj.getClass().getDeclaredMethods();
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(int i = 0; i < method.length; i++) {
			if(method[i].getName().toString().indexOf("set") != -1) {
				for(int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					
					if(method[i].getName().toString().substring(3, method[i].getName().length()).equals(fields[j].getName().substring(0, 1).toUpperCase() + fields[j].getName().substring(1))) {
						if(fields[j].get(obj) != null)
							method[i].invoke(sessionCommSearchDTO, fields[j].get(obj));
					}
				}
			}
		}
		
		session.setAttribute(Const.SEARCHOPTION, sessionCommSearchDTO);
		
  		return jsonObj;
  	}
	
	/**
	  * @Method Name : getWorkList
	  * @작성일 : 2020. 11. 10.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : 구인처 등록
	  */
	@RequestMapping(value="/selectEmployerList")
	@ResponseBody
	public List<EmployerDTO> selectEmployerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployerDTO employerDTO) throws Exception {
		CommonDTO commonDTO = new CommonDTO();
		//commonDTO.setCompany_seq(workDTO.getCompany_seq());

		// Session 설정
		commonDTO = setSessionToDTO(session, commonDTO);

		employerDTO.setReg_admin(commonDTO.getReg_admin());
		employerDTO.setOwner_id(commonDTO.getOwner_id());
		employerDTO.setCompany_seq(commonDTO.getCompany_seq());
		
		List<EmployerDTO> result = commonService.selectEmployerList(employerDTO);

		return result;
	}
	
	@RequestMapping(value="/regLandingLog")
	@ResponseBody
	public void regLandingLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//접속자 IP 가져오기
		String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	    }
	    
        //접속자 OS 가져오기
        String userAgent = request.getHeader("user-agent");
        String os_type = "";
        if(userAgent.indexOf("iPhone") > -1){
        	os_type = "I";
	    }else if(userAgent.indexOf("Android") > -1){
	    	os_type = "A";
	    }else if(userAgent.indexOf("Windows") > -1) {
	    	os_type = "W";
	    }else {
	    	os_type = "*";
	    }
        
    	WorkerLandingLogDTO workerLandingLogDTO = new WorkerLandingLogDTO();
        workerLandingLogDTO.setConnection_ip(ip);
        workerLandingLogDTO.setOs_type(os_type);
        commonService.insertWorkerLandingLog(workerLandingLogDTO);
	}
	
	@RequestMapping(value="/selectRecommendationCount")
	@ResponseBody
	public Map<String,Object> selectEmployerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CommonDTO commonDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int cnt = commonService.selectRecommendationCount(commonDTO);
			resultMap.put("count", cnt);
			
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("message", Const.MSG_SUCCESS);
		}catch(Exception e) {
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
