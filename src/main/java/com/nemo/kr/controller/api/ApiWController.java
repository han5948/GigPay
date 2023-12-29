package com.nemo.kr.controller.api;

import java.io.File;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.DeviceDTO;
import com.nemo.kr.dto.EvaluateDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.NoticeDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.AlimService;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.DeviceService;
import com.nemo.kr.service.EvaluateService;
import com.nemo.kr.service.FileService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.JobAlimService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.NoticeService;
import com.nemo.kr.service.PushReserveService;
import com.nemo.kr.service.PushSendService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.FileCoder;
import com.nemo.kr.util.JsonUtil;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
  * @FileName : ApiWController.java
  * @Project : ilgaja
  * @Date : 2020. 10. 26. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 구직지용 app api v2
  */
@Controller
@RequestMapping("/api/v2")
public class ApiWController {
	private static final Logger logger = LoggerFactory.getLogger(ApiWController.class);

	@Resource(name = "commonProperties")
	private Properties commonProperties;

	@Autowired	DeviceService deviceService;	
	@Autowired	WorkerService workerService;	
	@Autowired	CompanyService companyService;
	@Autowired	PushSendService pushSendService;
	@Autowired	PushReserveService pushReserveService;
	@Autowired	IlboService ilboService;
	@Autowired	CodeService codeService;
	@Autowired	ManagerService managerService;
	@Autowired	CommonService commonService;
	@Autowired	FileService fileService;
	@Autowired	NoticeService noticeService;
	@Autowired	AlimService alimService;
	@Autowired	JobAlimService jobAlimService;
	@Autowired	EvaluateService evaluateService;
	@Autowired  RestTemplate restTemplate;
	/**
	 * App version 체크
	 * 
	 * @param response
	 * @param request
	 * @param deviceDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLogin/getVersion")
	public void checkDeviceInfo(HttpServletResponse response, HttpServletRequest request, DeviceDTO deviceDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_type]", deviceDTO.getApp_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]", deviceDTO.getOs_type());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			DeviceDTO resultDTO = deviceService.getAppVersion(deviceDTO);
			result.put("appInfo", resultDTO);

			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));
			e.printStackTrace();
		}
	}

	/**
	 * 디바이스 등록
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLogin/setDevice")
	public void setDevice(HttpServletResponse response, HttpServletRequest request, DeviceDTO deviceDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_type]", deviceDTO.getApp_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]", deviceDTO.getOs_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_version]", deviceDTO.getApp_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_version]", deviceDTO.getOs_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_brand]", deviceDTO.getDevice_brand());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_id]", deviceDTO.getDevice_id());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			DeviceDTO resultDTO = deviceService.getDeviceInfo(deviceDTO);

			if (resultDTO != null) {
				// throw new Exception(msg.setThrowMsg(Const.CODE_USER_0019,
				// Const.MSG_USER_0019+"[" +resultDTO.getDevice_seq() + "]"));
				result.put("device_seq", resultDTO.getDevice_seq());
				result.put("isdevice", "1");
				msg.setResult(result);
				jsonUtil.responseJson(response, msg.getMsg());
			} else {
				deviceService.insertDeviceInfo(deviceDTO);
				result.put("device_seq", deviceDTO.getDevice_seq());
				result.put("isdevice", "0");
				msg.setResult(result);
				jsonUtil.responseJson(response, msg.getMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));
			e.printStackTrace();
		}
	}

	/**
	 * app 로그인 토큰 조회
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLogin/getLoginKey")
	public void getLoginKey(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_pass]", workerDTO.getWorker_pass());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[push_token]", workerDTO.getPush_token());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_version]", workerDTO.getApp_version());

		try {
			// worker 정보
			workerDTO.setUse_yn("1");
			WorkerDTO resultDTO = workerService.getAppLoginToKenProcV2(workerDTO);
			result.put("loginKey", resultDTO.getLogin_key());

			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			result.put("workerInfo", resultDTO);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * app 로그아웃
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setLogOut")
	public void setLogOut(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[login_key]", workerDTO.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerService.updateWorkerLogout(workerDTO);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}

	/**
	 * 인증번호 받기 앱설치 일가자사용 유무 일가자 푸쉬 일반 푸쉬 등을 모두 update 한다.
	 * 
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLogin/getAuth")
	public void getAuth(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerDTO.setUse_yn("1");
			String smsAuth = "";
			if (workerDTO.getAuth_type() == 0) { // 회원가입
				smsAuth = workerService.getJoinAuthNum(workerDTO);
			} else {// 기존회원
				smsAuth = workerService.getAuthNum(workerDTO);
			}

			result.put("smsAuth", smsAuth);
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
	 * 패스워드 등록 및 변경
	 * 
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/unLogin/setPass")
	public void setPass(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_pass]", workerDTO.getWorker_pass());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[sms_auth]", workerDTO.getSms_auth());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerService.setWorkerPassword(workerDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/unLogin/getJobList")
	public void getJobList(HttpServletResponse response, HttpServletRequest request) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setUse_yn("1");

			List<List<CodeDTO>> jobGroup = new ArrayList<List<CodeDTO>>();

			codeDTO.setCode_type("400");
			List<CodeDTO> jobList1 = commonService.getCommonCodeList(codeDTO);
			jobGroup.add(jobList1);

			codeDTO.setCode_type("800");
			List<CodeDTO> jobList2 = commonService.getCommonCodeList(codeDTO);
			jobGroup.add(jobList2);

			codeDTO.setCode_type("900");
			List<CodeDTO> jobList3 = commonService.getCommonCodeList(codeDTO);
			jobGroup.add(jobList3);

			result.put("jobGroup", jobGroup);

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
	 * @Method Name : insertWorker
	 * @작성일 : 2020. 5. 20.
	 * @작성자 : JangjaehoO
	 * @변경이력 :
	 * @Method 설명 : 회원가입
	 * 
	 */
	@RequestMapping(value = "/unLogin/insertWorker")
	public void insertWorker(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_pass]", workerDTO.getWorker_pass());
		//pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_jumin]", workerDTO.getWorker_jumin());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_name]", workerDTO.getWorker_name());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[job_code]", workerDTO.getWorker_job_code());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO paramDTO = new WorkerDTO();
			paramDTO.setWorker_phone(workerDTO.getWorker_phone());
			paramDTO.setUse_yn("1");
			int count = workerService.selectWorkerCount(paramDTO);

			if (count > 0) {

				throw new Exception(Const.CODE_USER_0021 + "!;!" + Const.MSG_USER_0021);

			}
			
			// 주민번호에서 성별 구하기.
			if(!StringUtil.isNull(workerDTO.getWorker_jumin()) ){
				String worker_sex = "M";
				if(workerDTO.getWorker_jumin().length() > 6) {
					String sex = workerDTO.getWorker_jumin().substring(6,7);
					if("2".equals(sex) || "4".equals(sex)) {
						worker_sex = "F";
					}
				}
			workerDTO.setWorker_sex(worker_sex);
			}
			// job 이름 구하기....
			String jobCode[] = workerDTO.getWorker_job_code().split(",");
			String jobName = codeService.selectCodeNameGroup(jobCode);

			workerDTO.setWorker_job_name(jobName);
			workerDTO.setCompany_seq(Const.JOBNPARTNER_SEQ); // 기본회사 정는 본사로 한다.
			workerDTO.setApp_last_date("1"); // 1을 넣으면 now() 가 들어감...
			workerDTO.setWorker_app_use_status("4"); // 승인대기
			workerDTO.setWorker_app_status("2");
			workerDTO.setReg_admin("WORKER");
			workerDTO.setWorker_push_yn("1");

			workerService.insertWorker(workerDTO);

			// 이미지 등록
			// Session 설정
			CommonDTO commonDTO = new CommonDTO();

			commonDTO.setService_type("WORKER");
			commonDTO.setService_seq(workerDTO.getWorker_seq());
			commonDTO.setReg_admin("WORKER");

			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") + "worker/",	"worker/");

			// 정상 결과 리턴
			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "setEditWorker")
	public void setEditWorker(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// session 정보
			workerDTO.setApp_last_date("1"); // 1을 넣으면 now() 가 들어감.

			// 회원정보 수정
			workerDTO.setLogin_key(workerSession.getLogin_key());
			workerService.updateWorkerInfo(workerDTO);
			
			// 이미지 등록
			CommonDTO commonDTO = new CommonDTO();

			commonDTO.setService_type("WORKER");
			commonDTO.setService_seq(workerSession.getWorker_seq());
			commonDTO.setReg_admin("WORKER");

			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") + "worker/", "worker/");

			// 정상 결과 리턴
			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getCompanyInfo")
	public void getCompanyInfo(HttpServletResponse response, HttpServletRequest request, CompanyDTO companyDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[company_seq]", companyDTO.getCompany_seq());
		
		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// company 정보
			CompanyDTO resultDTO = companyService.getCompanyInfo(companyDTO);

			result.put("companyInfo", resultDTO);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : getWorkerInfo
	 * @작성일 : 2020. 5. 20.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : worker 정보
	 * 
	 */
	@RequestMapping(value = "/getWorkerInfo")
	public void getWorkerInfo(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			result.put("workerInfo", workerSession);

			/*
			 * FileDTO paramDTO = new FileDTO(); paramDTO.setService_type("WORKER");
			 * paramDTO.setService_seq(workerSession.getWorker_seq());
			 * 
			 * List<FileDTO> fileList = fileService.selectFileList(paramDTO);
			 * 
			 * result.put("fileList", fileList);
			 */

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setEditWorkerStep1
	 * @작성일 : 2020. 5. 20.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 내정보수정1
	 * 
	 */
	@RequestMapping(value = "/setEditWorkerStep1")
	public void setEditWorkerStep1(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_name]", workerDTO.getWorker_name());
		//pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_sex]", workerDTO.getWorker_sex());
		//pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_addr]", workerDTO.getWorker_addr());

		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// session 정보
			workerDTO.setApp_last_date("1"); // 1을 넣으면 now() 가 들어감.

			// 회원정보 수정
			workerDTO.setLogin_key(workerSession.getLogin_key());
			workerService.updateWorkerInfo(workerDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setEditWorkerStep2
	 * @작성일 : 2020. 5. 20.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 내정보수정2
	 * 
	 */
	@RequestMapping(value = "/setEditWorkerStep2")
	public void setEditWorkerStep2(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_job_code]", workerDTO.getWorker_job_code());

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// LoginKey 수정
			workerDTO.setLogin_key(workerSession.getLogin_key());
			workerDTO.setApp_last_date("1"); // 1을 넣으면 now() 가 들어감.

			String jobCode[] = workerDTO.getWorker_job_code().split(",");
			String jobName = codeService.selectCodeNameGroup(jobCode);
			workerDTO.setWorker_job_name(jobName);

			// 회원정보 수정
			workerService.updateWorkerInfo(workerDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "setEditWorkerStep3")
	public void setEditWorkerStep3(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// 이미지 등록
			CommonDTO commonDTO = new CommonDTO();

			commonDTO.setService_type("WORKER");
			commonDTO.setService_seq(workerSession.getWorker_seq());
			commonDTO.setReg_admin("WORKER");

			commonService.uploadFile(request, commonDTO, commonProperties.getProperty("uploadPath") + "worker/","worker/");

			// 정상 결과 리턴
			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/imgLoad")
	public void imgLoad(HttpServletRequest request, HttpServletResponse response, HttpSession session, FileDTO fileDTO) throws Exception {
		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		fileDTO.setService_type("WORKER");
		fileDTO.setService_seq(workerSession.getWorker_seq());

		FileDTO resultFile = fileService.selectInfo(fileDTO);

		if (resultFile != null) {
			String path = StringUtil.isNullToString(resultFile.getFile_path());
			String name = StringUtil.isNullToString(resultFile.getFile_name());

			String imgformat = "";
			String filename = "";

			name = name.replaceAll("\\.\\./", "");
			path = path.replaceAll("\\.\\./", "").toLowerCase();

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

	/**
	 * @Method Name : getIlboList
	 * @작성일 : 2020. 5. 21.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 오늘 이후 날짜에서 worker 가 등록된 일 정보를 가져 온다....
	 * 
	 */
	@RequestMapping(value = "/getIlboList")
	public void getIlboList(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			result.put("appUseStatus", workerSession.getWorker_app_use_status());
			result.put("appUseMessage", getAppUseMessage(workerSession.getWorker_app_use_status()));
			result.put("companySeq", workerSession.getCompany_seq());

			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setWorker_seq(workerSession.getWorker_seq());
			
			// 1. 일보에 신청, 예약, 진행 중 이 있는 작업을 가져 온다.

			List<IlboDTO> ilboList = ilboService.getIlboListW2(paramDTO);
			result.put("ilboList", ilboList);
			msg.setResult(result);

			// 공지사항 가장 위에 것 가져오기
			NoticeDTO noticeParam = new NoticeDTO(); 
			noticeParam.setCompany_seq(workerSession.getCompany_seq());
								
			NoticeDTO noticeDTO = noticeService.selectOneInfo(noticeParam);
			result.put("noticeInfo", noticeDTO);
			
			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}
	
	/**
	  * @Method Name : getIlboListV3
	  * @작성일 : 2020. 12. 9.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 앱에서 recyclerView 버젼에서 사용
	  * 어제부터 예약 된걸 모두 보여 준다.
	
	  */
	@RequestMapping(value = "/getIlboListV3")
	public void getIlboListV3(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			result.put("appUseStatus", workerSession.getWorker_app_use_status());
			result.put("appUseMessage", getAppUseMessage(workerSession.getWorker_app_use_status()));
			result.put("companySeq", workerSession.getCompany_seq());

			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setWorker_seq(workerSession.getWorker_seq());
			
			// 1. 일보에 신청, 예약, 진행 중 이 있는 작업을 가져 온다.

			List<IlboDTO> ilboList = ilboService.getIlboListV3(paramDTO);
			result.put("ilboList", ilboList);
			msg.setResult(result);

			// 공지사항 가장 위에 것 가져오기
			NoticeDTO noticeParam = new NoticeDTO(); 
			noticeParam.setCompany_seq(workerSession.getCompany_seq());
								
			NoticeDTO noticeDTO = noticeService.selectOneInfo(noticeParam);
			result.put("noticeInfo", noticeDTO);
			
			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	private String getAppUseMessage(String appUseStatus) {
		String message = "";

		switch (Integer.parseInt(appUseStatus)) {
		case 0:
			message = "APP 미설치 회원 입니다.";
			break;
		case 1:
			message = "승인된 회원입니다.";
			break;
		case 2:
			message = "APP 탈퇴 회원 입니다.";
			break;
		case 3:
			message = "일가자 이용기준 위반으로 알선서비스가 중지되었습니다.";
			break;
		case 4:
			message = "현재 회원님은\n승인 대기 상태 입니다.\n승인 후 사용하실 수 있습니다.\n(회원가입 후 1~2일 소요)";
			break;
		case 5:
			message = "소개 수수료 미납으로 알선서비스가 중지되었습니다.";
			break;

		default:
			message = "승인된 회원입니다.";
			break;
		}

		return message;
	}

	/**
	 * @Method Name : setIlbo
	 * @작성일 : 2020. 5. 22.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 일요청
	 * 
	 */
	@RequestMapping(value = "/setIlbo")
	public void setIlbo(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_date]", ilboDTO.getIlbo_date());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[output_status_code()]", ilboDTO.getOutput_status_code());

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			// 코드가 잘못 되었을때
			if (!"100002".equals(ilboDTO.getOutput_status_code()) && !"100020".equals(ilboDTO.getOutput_status_code())) {
				pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[output_status_code()]", ilboDTO.getOutput_status_code());
			}

			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			ilboDTO.setWorker_seq(workerSession.getWorker_seq());

			String[] codeArray = { "100002",  "100020" };	//대기 , 지금
			ilboDTO.setSel_code(codeArray);

			// 현황을 가져 온다.
			IlboDTO ilboResult = ilboService.getIlboInfoW2(ilboDTO);

			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setOutput_status_code(ilboDTO.getOutput_status_code());

			if ("100002".equals(ilboDTO.getOutput_status_code())) {
				paramDTO.setOutput_status_name("대기");
			} else {
				paramDTO.setOutput_status_name("지금");
			}

			if (ilboResult == null) {

				paramDTO.setIlbo_date(ilboDTO.getIlbo_date());
				paramDTO.setIlbo_status("0");
				paramDTO.setIlbo_assign_type("R");			//일요청
				paramDTO.setCompany_seq(workerSession.getCompany_seq());
				paramDTO = setParam(paramDTO, workerSession);
				
				ilboService.insertIlboWorker(paramDTO); // insert

			} else {
				throw new Exception(Const.CODE_ILBO_0201 + "!;!" + Const.MSG_ILBO_0201);
			}

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	private IlboDTO setParam(IlboDTO paramDTO, WorkerDTO workerDTO) {
		paramDTO.setWorker_company_seq(workerDTO.getCompany_seq());
		paramDTO.setWorker_company_name(workerDTO.getCompany_name());
		paramDTO.setWorker_seq(workerDTO.getWorker_seq());
		paramDTO.setIlbo_worker_name(workerDTO.getWorker_name());
		paramDTO.setIlbo_worker_phone(workerDTO.getWorker_phone());
		paramDTO.setIlbo_worker_addr(workerDTO.getWorker_addr());
		paramDTO.setIlbo_worker_latlng(workerDTO.getWorker_latlng());
		paramDTO.setIlbo_worker_ilgaja_addr(workerDTO.getWorker_ilgaja_addr());
		paramDTO.setIlbo_worker_ilgaja_latlng(workerDTO.getWorker_ilgaja_latlng());
		paramDTO.setIlbo_worker_jumin(workerDTO.getWorker_jumin());
		paramDTO.setIlbo_worker_job_code(workerDTO.getWorker_job_code());
		paramDTO.setIlbo_worker_job_name(workerDTO.getWorker_job_name());
		paramDTO.setIlbo_worker_barcode(workerDTO.getWorker_barcode());
		paramDTO.setIlbo_worker_memo(workerDTO.getWorker_memo());
		paramDTO.setIlbo_worker_bank_code(workerDTO.getWorker_bank_code());
		paramDTO.setIlbo_worker_bank_name(workerDTO.getWorker_bank_name());
		paramDTO.setIlbo_worker_bank_account(workerDTO.getWorker_bank_account());
		paramDTO.setIlbo_worker_bank_owner(workerDTO.getWorker_bank_owner());
		paramDTO.setIlbo_worker_feature(workerDTO.getWorker_feature());
		paramDTO.setIlbo_worker_blood_pressure(workerDTO.getWorker_blood_pressure());
		paramDTO.setIlbo_worker_OSH_yn(workerDTO.getWorker_OSH_scan_yn());
		paramDTO.setIlbo_worker_bankbook_scan_yn(workerDTO.getWorker_bankbook_scan_yn());
		paramDTO.setIlbo_worker_jumin_scan_yn(workerDTO.getWorker_jumin_scan_yn());
		paramDTO.setIlbo_worker_OSH_scan_yn(workerDTO.getWorker_OSH_scan_yn());
		paramDTO.setIlbo_worker_certificate_scan_yn(workerDTO.getWorker_certificate_scan_yn());
		paramDTO.setIlbo_worker_app_status(workerDTO.getWorker_app_status());
		paramDTO.setIlbo_worker_app_use_status(workerDTO.getWorker_app_use_status());
		paramDTO.setIlbo_worker_reserve_push_status(workerDTO.getWorker_reserve_push_status());
		paramDTO.setWorker_owner(workerDTO.getOwner_id());
		paramDTO.setMod_admin("WORKER");

		return paramDTO;
	}

	/**
	 * @Method Name : setIlboStatus
	 * @작성일 : 2020. 6. 4.O
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 일요청 취소취소, 수락, 거절, 출발, 도착, 완료
	 * 
	 */
	@RequestMapping(value = "/setIlboStatus")
	public void setIlboStatus(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[output_status_code()]", ilboDTO.getOutput_status_code());

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());
		
		try {
			boolean isSet = true;
			
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			IlboDTO ilboInfo = ilboService.getIlboInfoW2(ilboDTO);

			//현장 출발일때 : 날짜가 같지 않으면... 
			//작업날짜보다 작거나 같으면
			if ("100003".equals(ilboDTO.getOutput_status_code())) {
				if (!ilboInfo.getIlbo_date().equals(DateUtil.getCurDate2())) {
					throw new Exception(Const.CODE_ILBO_0301 + "!;!" + Const.MSG_ILBO_0301);
				}
			}
			
			CodeDTO paramDTO = new CodeDTO();
			paramDTO.setCode_value(ilboDTO.getOutput_status_code());
			CodeDTO codeDTO = codeService.getCodeInfo(paramDTO);

			//코드 로그를 남길지 여부
			boolean isCodeLog = true;
			
			// 일요청 취소이고 현장이 없으면 삭제 한다.
			if("100013".equals(ilboDTO.getOutput_status_code())) {
				if("0".equals(ilboInfo.getWork_seq())) {	
					ilboDTO.setUse_yn("0");
					isCodeLog = false;
				}
			}
			
			// 완료 버튼을 누를때
			// 내일또 가요 팝업을 띄울지 유무 나중에 체크하여 0: 띄우지 않음 1: 띄움
			if("100015".equals(ilboDTO.getOutput_status_code())) {	//완료
				result.put("popup", 1);
			}
	
			//푸취
			if( "100017".equals(ilboDTO.getOutput_status_code()) ) {
				//푸쉬
				if( "100012".equals(ilboInfo.getOutput_status_code())) {
					if(!workerSession.getWorker_seq().equals(ilboInfo.getWorker_seq())){
						isSet = false;
					}
				}else {
					isSet = false;
				}
			}
			
			// 예약 (푸쉬로 예약을 할때)
			if( "100005".equals(ilboDTO.getOutput_status_code())) {
				ilboDTO.setIlbo_assign_type("P");
				
				//푸쉬
				if( "100012".equals(ilboInfo.getOutput_status_code())) {	
					if(!workerSession.getWorker_seq().equals(ilboInfo.getWorker_seq())){
						throw new Exception(Const.CODE_ILBO_0104 + "!;!" + Const.MSG_ILBO_0104);
					}
				}else {
					throw new Exception(Const.CODE_ILBO_0107 + "!;!" + Const.MSG_ILBO_0107);
				}
			}
			
			ilboDTO.setOutput_status_name(codeDTO.getCode_name());
			ilboDTO.setMod_output_date("1"); // 상태 날짜를 넣도록
			ilboDTO.setMod_admin("WORKER");
			ilboService.setIlboInfo(ilboDTO);

			if(isSet) {
				if(isCodeLog) {
					//codelog 남기기 ====================================================
					WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
					CodeLogDTO logDTO = new CodeLogDTO();
					logDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
					logDTO.setCode_type("100");
					logDTO.setCode_value(ilboDTO.getOutput_status_code());
					logDTO.setCode_name( codeDTO.getCode_name());
					logDTO.setLog_user_type("U"); //구직자
					logDTO.setLog_text( infoDTO.getWorker_name() );
					
					logDTO.setWorker_seq(ilboInfo.getWorker_seq());
				    logDTO.setWorker_company_seq(ilboInfo.getWorker_company_seq());
				    logDTO.setWork_seq(ilboInfo.getWork_seq());
				    logDTO.setWork_company_seq(ilboInfo.getCompany_seq());
				    
					logDTO.setReg_admin( infoDTO.getWorker_phone());
					ilboService.insertCodeLog(logDTO);
					//===============================================================
					
				    //------------메니져 푸쉬 보내기------------------------//
				    ilboService.sendManagerPush(ilboDTO);
				    
				    try {
						NotificationDTO notificationDTO = new NotificationDTO();
		
						notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
						
						// 대마
						if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100008")) {
							notificationDTO.setStatus_flag("ALR004");
							
							// 알림 보내는 메소드
							NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
							notificationUtil.ilboAlim(notificationDTO);
						}
						// 펑크
						else if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100009")) {
							notificationDTO.setStatus_flag("ALR005");
							
							// 알림 보내는 메소드
							NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
							notificationUtil.ilboAlim(notificationDTO);
						}
//						// 수락
//						else if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100005")) {
//							notificationDTO.setStatus_flag("ALR006");
//							
//							// 알림 보내는 메소드
//							NotificationUtil.ilboAlim(notificationDTO);
//						}
//						// 공개 전체를 예약 했을 때
//						else if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100005") && ilboDTO.getIlbo_use_code().equals("USE002")) { 
//							notificationDTO.setStatus_flag("ALR007");
//							
//							// 알림 보내는 메소드
//							NotificationUtil.ilboAlim(notificationDTO);
//						}
						// 째앰
						else if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100010")) {
							notificationDTO.setStatus_flag("ALR011");
							
							// 알림 보내는 메소드
							NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
							notificationUtil.ilboAlim(notificationDTO);
						}
				    }catch(Exception e) {
				    	e.printStackTrace();
				    }
				}
			}
			
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : getIlboPushInfo
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 푸쉬를 보냈을때 사용하는 일일대장 정보 다른 사람에게 배정되었는지? 푸쉬 상태인지 를 체크 한다.
	 * 
	 */
	@RequestMapping(value = "/getIlboPushInfo")
	public void getIlboPushInfo(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			IlboDTO ilboInfo = ilboService.getIlboInfoW2(ilboDTO);

			if (ilboInfo == null) {
				throw new Exception(Const.CODE_ILBO_0103 + "!;!" + Const.MSG_ILBO_0103);
			}

			if (!ilboInfo.getWorker_seq().equals(workerSession.getWorker_seq())) {
				throw new Exception(Const.CODE_ILBO_0101 + "!;!" + Const.MSG_ILBO_0101);
			}

			if (!"100012".equals(ilboInfo.getOutput_status_code())) {
				throw new Exception(Const.CODE_ILBO_0103 + "!;!" + Const.MSG_ILBO_0103);
			}

			// push 확인 일자를 확인 하기
			// 위해서.........................................................
			WorkerDTO workerDTO = new WorkerDTO();
			workerDTO.setWorker_seq(workerSession.getWorker_seq());
			workerDTO.setMod_admin("WORKER");
			workerDTO.setApp_last_date("1");
			workerService.updateWorkerInfo(workerDTO);
			// ============================================

			result.put("ilboInfo", ilboInfo);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : getIlboPushInfo
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 일보정보
	 * 
	 */
	@RequestMapping(value = "/getIlboInfo")
	public void getIlboInfo(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		
		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)	throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			IlboDTO ilboInfo = ilboService.getIlboInfoW2(ilboDTO);
			result.put("ilboInfo", ilboInfo);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * 오늘 현장 내일또 가요, 다른분보내 주세요.
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setCopyIlbo")
	public void setCopyIlbo(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO)	throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[to_ilbo_date]", ilboDTO.getTo_ilbo_date());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[oper]", ilboDTO.getOper());

		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			// ---------------------------------------------------------------------------------------------------------------------
			IlboDTO pDTO = new IlboDTO();
			pDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
			
			IlboDTO resultDTO = ilboService.selectIlboInfo(pDTO);

			// parmeter [to_ilbo_date] 를 무시하고 서버에서 일 시작 날짜 기준 내일을 구한다.
			String to_date = DateUtil.addStringDay(1, resultDTO.getIlbo_date(), "yyyy-MM-dd");
			
			ilboDTO.setTo_ilbo_date(to_date);
			ilboDTO.setIlbo_assign_type("A");
			ilboDTO.setIlbo_status("3");				
			ilboDTO.setCopy_rows(1);
			ilboDTO.setReg_admin("Worker");
			
			if ("I".equals(ilboDTO.getOper())) { // 내일 또 가요
				// 대기, 예약, 푸쉬, 취소, 푸취, 지금
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setIlbo_date(to_date);
				paramDTO.setWorker_seq(workerSession.getWorker_seq());

				String[] codeArray = { "100002", "100005", "100012", "100013", "100017", "100020" }; // 대기,에약,푸쉬,취소,푸취,지금
				paramDTO.setSel_code(codeArray);
				
				// 현황을 가져 온다.
				List<IlboDTO> resultList = ilboService.getIlboOutputCodeListW2(paramDTO);

				int copyType = 0;
				String ilboSeq = "";

				if (resultList.size() == 0) {
					copyType = 0; // 없을때
				} else {
					for (int i = 0; i < resultList.size(); i++) {
						IlboDTO resultIlbo = resultList.get(i);

						if ("100005".equals(resultIlbo.getOutput_status_code())) {
							copyType = 1; // 예약이 있음
							break;
						} else {
							copyType = 2; // 대기 가 있음.
							ilboSeq = resultIlbo.getIlbo_seq();
						}
						
						if(Integer.parseInt(resultIlbo.getWork_seq()) > 0) {
							copyType = 1; // 배정할려는 현장이 있음
							break;
						}
					}
				}

				if (copyType == 0) { // 같은 날짜에 예약 사항이 없을때
					ilboDTO.setCopy_worker("Y");
					ilboDTO.setIlbo_memo("같은 현장 또 가요");
					
					ilboDTO.setOutput_status_code("100005");
					ilboDTO.setOutput_status_name("예약");
					
					ilboDTO.setIlbo_work_order_code("ORD004");
					ilboDTO.setIlbo_work_order_name("또가요");

					ilboService.copyIlboCell(ilboDTO);
				} else if (copyType == 1) { // 같은 날짜에 예약사항이 있을때.
					
					msg.setMessage("["+workerSession.getWorker_name() + "]님은 이미 다른 일이 예약 되어 있습니다.\n내일은 다른분 보냅니다.");
					
					ilboDTO.setIlbo_memo("예약되어 있음-다른분 보내주세요.["+ workerSession.getWorker_name()+"]");
					ilboDTO.setIlbo_work_order_code("ORD005");
					ilboDTO.setIlbo_work_order_name("다른분");
					
					ilboService.copyIlboCell(ilboDTO);
				} else {// 대기 값이 있으면 ilbo 값을 구해 와서 대기에 update 한다. copyType = 2
					
					resultDTO.setIlbo_assign_type("A");
					resultDTO.setIlbo_memo("같은 현장 또 가요");
					resultDTO.setIlbo_work_order_code("ORD004");
					resultDTO.setIlbo_work_order_name("또가요");
					resultDTO.setIlbo_seq(ilboSeq);
					
					ilboService.copyIlboWork(resultDTO);
				}

			} else if ("W".equals(ilboDTO.getOper())) { // W 다른사람 보내주세요
				ilboDTO.setIlbo_memo("다른분 보내주세요.["+ workerSession.getWorker_name()+"]");
				ilboDTO.setIlbo_work_order_code("ORD005");
				ilboDTO.setIlbo_work_order_name("다른분");
				
				ilboService.copyIlboCell(ilboDTO);

				try {
				    // 알림 보내는 메소드
					NotificationDTO notificationDTO = new NotificationDTO();
					
					notificationDTO.setIlbo_seq(pDTO.getIlbo_seq());
					notificationDTO.setStatus_flag("ALR003");
					
					NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
					notificationUtil.ilboAlim(notificationDTO);
				}catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				throw new Exception(Const.CODE_INVALID_PARAMETER + "!;!" + Const.MSG_INVALID_PARAMETER + "[oper]");
			}

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setIlgajaAddress
	 * @작성일 : 2020. 6. 4.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 일가자 주소설정
	 * 
	 */
	@RequestMapping(value = "/setIlgajaAddress")
	public void setIlgajaAddress(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_ilgaja_addr]", workerDTO.getWorker_ilgaja_addr());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_ilgaja_latlng]", workerDTO.getWorker_ilgaja_latlng());

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerDTO.setWorker_seq(workerSession.getWorker_seq());
			workerDTO.setMod_admin("WORKER");

			workerService.updateWorkerInfo(workerDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	// 공지사항 리스트
	@RequestMapping("/unLogin/getNoticeList")
	public void getNoticeList(HttpServletResponse response, HttpServletRequest request, NoticeDTO noticeDTO) {
		ApiMsg msg = new ApiMsg();
		Map result = new HashMap();
		ParamValidate pVali = new ParamValidate();
		JsonUtil jsonUtil = new JsonUtil();

		try {
			noticeDTO.setUse_yn("1");
			noticeDTO.setDel_yn("0");
			noticeDTO.setAuth_view("1");
			noticeDTO.setAuth_company_seq(noticeDTO.getCompany_seq());
			noticeDTO.getPaging().setPageSize(Const.MOBILE_PAGE_SIZE);

			int listCnt = noticeService.selectListCnt(noticeDTO);
			List<NoticeDTO> noticeList = noticeService.selectList(noticeDTO);

			noticeDTO.getPaging().setRowCount(listCnt);
			noticeDTO.getPaging().setCurrentCnt(noticeList.size());

			result.put("paging", noticeDTO.getPaging());
			result.put("noticeList", noticeList);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : getIlboHistList
	 * @작성일 : 2020. 5. 21.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 오늘 이후 날짜에서 worker 가 등록된 일 정보를 가져 온다....
	 * 
	 */
	@RequestMapping(value = "/getIlboHistList")
	public void getIlboHistList(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			result.put("appUseStatus", workerSession.getWorker_app_use_status());
			result.put("appUseMessage", getAppUseMessage(workerSession.getWorker_app_use_status()));

			if ("100005".equals(ilboDTO.getOutput_status_code())) { // 예약
				String[] sel_code = { "100005" };
				ilboDTO.setSel_code(sel_code);
			} else if ("100015".equals(ilboDTO.getOutput_status_code())) { // 완료
				String[] sel_code = { "100015" };
				ilboDTO.setSel_code(sel_code);
			} else {
				String[] sel_code = { "100005", "100015" };
				ilboDTO.setSel_code(sel_code);
			}

			ilboDTO.setWorker_seq(workerSession.getWorker_seq());
			ilboDTO.getPaging().setPageSize(Const.MOBILE_PAGE_SIZE);

			int listCnt = ilboService.selectIlboHistCnt(ilboDTO);
			List<IlboDTO> ilboList = ilboService.getIlboHistList(ilboDTO);

			ilboDTO.getPaging().setRowCount(listCnt);
			ilboDTO.getPaging().setCurrentCnt(ilboList.size());

			result.put("paging", ilboDTO.getPaging());
			result.put("ilboList", ilboList);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setEvaluate
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 평가하기
	 * 
	 */
	@RequestMapping("/setEvaluate")
	public void setEvaluate(HttpServletResponse response, HttpServletRequest request, EvaluateDTO evaluateDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", evaluateDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[Evaluate_grade]", evaluateDTO.getEvaluate_grade());

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());
	
		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setIlbo_seq(evaluateDTO.getIlbo_seq());
			IlboDTO ilboDTO = ilboService.selectIlboInfo(paramDTO);
			
			// String evaluate = Integer.toString((int) Float.parseFloat(evaluateDTO.getEvaluate_grade()) );
					
			evaluateDTO.setEmployer_seq(ilboDTO.getEmployer_seq());
			evaluateDTO.setWork_seq(ilboDTO.getWork_seq());
			evaluateDTO.setWorker_seq(ilboDTO.getWorker_seq());
			evaluateService.insertInfo(evaluateDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	// 알림 리스트
	@RequestMapping("/getAlimList")
	public void getAlimList(HttpServletResponse response, HttpServletRequest request, AlimDTO alimDTO) {
		ApiMsg msg = new ApiMsg();
		Map result = new HashMap();
		ParamValidate pVali = new ParamValidate();
		JsonUtil jsonUtil = new JsonUtil();

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			AlimReceiveDTO alimReceiveDTO = new AlimReceiveDTO();

			alimReceiveDTO.setPaging(alimDTO.getPaging());
			alimReceiveDTO.setWorker_seq(workerSession.getWorker_seq());
			alimReceiveDTO.getPaging().setPageSize(Const.MOBILE_PAGE_SIZE);

			List<AlimDTO> alimList = alimService.selectAlimList(alimReceiveDTO);
			int listCnt = alimService.selectAlimListCnt(alimReceiveDTO);

			alimReceiveDTO.getPaging().setRowCount(listCnt);
			alimReceiveDTO.getPaging().setCurrentCnt(alimList.size());

			result.put("paging", alimReceiveDTO.getPaging());
			result.put("alimList", alimList);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setUpdateAlim
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 : 알림 읽음 삭제 updateFlag R: 읽음 D: 삭제
	 * 
	 */
	@RequestMapping("/setUpdateAlim")
	public void setUpdateAlim(HttpServletResponse response, HttpServletRequest request, AlimReceiveDTO alimReceiveDTO) {
		ApiMsg msg = new ApiMsg();
		Map result = new HashMap();
		ParamValidate pVali = new ParamValidate();
		JsonUtil jsonUtil = new JsonUtil();

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[receive_seq]", alimReceiveDTO.getReceive_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[updateFlag]", alimReceiveDTO.getUpdateFlag());

		// 인터 셉터에서 근로자 DTO 를 받아온다.
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			if ("R".equals(alimReceiveDTO.getUpdateFlag())) {
				alimReceiveDTO.setRead_yn("1");
			} else {
				alimReceiveDTO.setDel_yn("1");
			}
			alimService.setUpdateAlim(alimReceiveDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	  * @Method Name : getIlboWorkList
	  * @작성일 : 2020. 12. 17.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 공개된 일자리
	
	  */
	@RequestMapping(value = "/getIlboWorkList")
	public void getIlboWorkList(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) {

		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[sidx]", ilboDTO.getSidx());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			result.put("appUseStatus", workerSession.getWorker_app_use_status());
			result.put("appUseMessage", getAppUseMessage(workerSession.getWorker_app_use_status()));

			//일보리트트
			List<IlboDTO> ilboList = new ArrayList<IlboDTO>();
			
			ilboDTO.setWorker_company_seq(workerSession.getCompany_seq());
			ilboDTO.setIlbo_kind_code(workerSession.getWorker_job_code());
			ilboDTO.getPaging().setPageSize(Const.MOBILE_PAGE_SIZE);
			ilboDTO.setWorker_seq(workerSession.getWorker_seq());
			ilboDTO.setIlbo_worker_blood_pressure(workerSession.getWorker_blood_pressure()); 	// 혈압 조건 추가.

			//출발 시간추가
			if("ilbo_date".equals(ilboDTO.getSidx())) {
				ilboDTO.setSidx("ilbo_date, ilbo_work_arrival");
			}
			
			//소장평가.....
			if("1".equals(workerSession.getWorker_rating())) {	//F
				ilboDTO.setIlbo_unit_price("129999");
			}else if("2".equals(workerSession.getWorker_rating())) { //E
				ilboDTO.setIlbo_unit_price("130000");
			}else if("3".equals(workerSession.getWorker_rating())) { //D
				ilboDTO.setIlbo_unit_price("140000");
			}else if("4".equals(workerSession.getWorker_rating())) { //C
				ilboDTO.setIlbo_unit_price("150000");
			}else if("5".equals(workerSession.getWorker_rating())) { //B
				ilboDTO.setIlbo_unit_price("160000");
			}else if("6".equals(workerSession.getWorker_rating())) { //A
				ilboDTO.setIlbo_unit_price("");
			}else {
				//workerSession.getWorker_rating() == 0 소장평가가 가 없으면 일자리 리스트가 없다. 
			}

			if(!StringUtil.isNull(workerSession.getWorker_ilgaja_latlng()) && ilboDTO.getIlbo_unit_price() != null) { // 일자리주소가 없거나 소장평가 등급이 있으면...
				
				ilboDTO.setIlbo_worker_ilgaja_latlng(workerSession.getWorker_ilgaja_latlng());

				//공개된 일자리를 가져 온다.
				ilboList 		= ilboService.getIlboWorkList(ilboDTO);
				
				//예약, 출발, 펑크 취소 일자리를 가져온다.
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setWorker_seq(workerSession.getWorker_seq());
				String[] sel_code = {"100003","100005","100010", "100009"};	// 출발, 예약,  째앰, 펑크 
				paramDTO.setSel_code(sel_code);
				
				List<IlboDTO> myList = ilboService.getMyIlboList(paramDTO);

				for (IlboDTO myIlboDTO : myList) {
					
					int cStart = Integer.parseInt(myIlboDTO.getIlbo_work_arrival()) ;
					int cEnd = Integer.parseInt(myIlboDTO.getIlbo_work_finish()) ;
					
					for(Iterator<IlboDTO> it = ilboList.iterator() ; it.hasNext() ; ) {
						IlboDTO mDTO = it.next();
					  
						int mStart = Integer.parseInt(mDTO.getIlbo_work_arrival() );
						int mEnd = Integer.parseInt(mDTO.getIlbo_work_finish()) ;
						
						if(myIlboDTO.getIlbo_date().equals(mDTO.getIlbo_date()) && !"STA008".equals(myIlboDTO.getIlbo_status_code()) ) {
							if("100010".equals(myIlboDTO.getOutput_status_code())){ //째앰이면 해당 날짜의 것을 모두 뺀다.
								it.remove();
							}else if("100009".equals(myIlboDTO.getOutput_status_code()) && !myIlboDTO.getIlbo_seq().equals(mDTO.getIlbo_seq())) { // 펑크 일 경우 해당 일자리 이외에는 삭제 시킨다.
								it.remove();
							}else if("100005".equals(myIlboDTO.getOutput_status_code()) || "100003".equals(myIlboDTO.getOutput_status_code())) {	// 예약 또는 출발 일때는 겹치는 시간대의 것을 뺀다.
								if( cStart >= mStart &&  cStart <= mEnd ) {
									it.remove();
								}else if( cEnd >= mStart &&  cEnd <= mEnd) {
									it.remove();
								}else if(cStart <= mStart && cEnd >= mEnd){
									it.remove();
								}
							}
						}
						
					}
					
				}

				ilboDTO.getPaging().setRowCount(ilboList.size());
				ilboDTO.getPaging().setCurrentCnt(ilboList.size());
			}
			
			result.put("paging", ilboDTO.getPaging());
			result.put("ilboList", ilboList);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * 일자리 예약
	  * @Method Name : setIlboReserve
	  * @작성일 : 2020. 8. 4.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 :
	 */
	@RequestMapping(value = "/setIlboReserve")
	public void setIlboReserve(HttpServletResponse response, HttpServletRequest request, IlboDTO ilboDTO) {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		
		// session 정보
		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			// 일가자 앱 사용이 승인된 폰번호가 아닙니다.
			if (!"1".equals(workerSession.getWorker_app_use_status())
					&& !"4".equals(workerSession.getWorker_app_use_status())) {
				throw new Exception(	Const.CODE_USER_0017 + "!;!" + getAppUseMessage(workerSession.getWorker_app_use_status()));
			}

			IlboDTO ilboInfo = ilboService.getIlboInfoW2(ilboDTO);
			
			// 예약할려는 일보 정보가 없을때====================================================
			if (ilboInfo == null) {
				throw new Exception(Const.CODE_ILBO_0104 + "!;!" + Const.MSG_ILBO_0104);
			}
			
			//전체 공개 인지 지점 공개인지 확인
			if(!"USE002".equals(ilboInfo.getIlbo_use_code())) {	//전체공개가 아니고.
				if("USE003".equals(ilboInfo.getIlbo_use_code()) && ( ("0".equals(ilboInfo.getWorker_company_seq()) && ilboInfo.getCompany_seq().equals(workerSession.getCompany_seq()) )  || ilboInfo.getWorker_company_seq().equals(workerSession.getCompany_seq()) ) ) {	//내지점 공개이면
					
				}else {
					throw new Exception(Const.CODE_ILBO_0107 + "!;!" + Const.MSG_ILBO_0107);
				}
			}
			
			//현장상태확인 ilbo_status_code  STA007: 휴지 STA008: 취소  STA009 : 메모
			if("STA007".equals(ilboInfo.getIlbo_status_code()) || "STA008".equals(ilboInfo.getIlbo_status_code()) || "STA009".equals(ilboInfo.getIlbo_status_code()) ) {	
					throw new Exception(Const.CODE_ILBO_0107 + "!;!" + Const.MSG_ILBO_0107);
			}
			
			
			// and output_status_code in ('0', '100002','100009', '100012', '100013', '100017', '100019')
			// 예약 할 수 있는 상태가 아니면.
			if (!"0".equals(ilboInfo.getOutput_status_code()) && !"100002".equals(ilboInfo.getOutput_status_code())
					&& !"100009".equals(ilboInfo.getOutput_status_code())
					&& !"100013".equals(ilboInfo.getOutput_status_code())
					&& !"100017".equals(ilboInfo.getOutput_status_code())
					&& !"100019".equals(ilboInfo.getOutput_status_code())) {
				throw new Exception(Const.CODE_ILBO_0101 + "!;!" + Const.MSG_ILBO_0101);
			}

			// 같은 시간대에 본인이 예약 되어 있는지 확인	===========================================		
			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setWorker_seq(workerSession.getWorker_seq());
			paramDTO.setIlbo_date(ilboInfo.getIlbo_date());
			
			String selCode[] = {"100003","100005","100014"};	// 출발, 예약, 도착
			paramDTO.setSel_code(selCode);
			paramDTO.setStartDate(ilboInfo.getIlbo_work_arrival());
			paramDTO.setEndDate(ilboInfo.getIlbo_work_finish());
			
			List<IlboDTO> workerIlboList = ilboService.selectIlboTimeList(paramDTO);
			
			if(workerIlboList.size() > 0) {
				throw new Exception(Const.CODE_ILBO_0105 + "!;!" + Const.MSG_ILBO_0105);
			}
			//================================================================

			//******** 	예약 ************/
			ilboDTO = setParam(ilboDTO, workerSession);
			ilboDTO.setIlbo_assign_type("C"); // 맞춤일자리
			ilboDTO.setIlbo_status("0");
			ilboDTO.setOutput_status_code("100005");
			ilboDTO.setOutput_status_name("예약");
			
			//변경된 행 저장.
			ilboService.setIlboReserve(ilboInfo, ilboDTO);
			
			//codelog 남기기 ====================================================
			CodeLogDTO logDTO = new CodeLogDTO();
			
			logDTO.setCode_type("100");
			logDTO.setCode_value("100005");
			logDTO.setCode_name( "예약");
			logDTO.setLog_user_type("U"); //구직자
			logDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
			logDTO.setWorker_seq(workerSession.getWorker_seq());
		    logDTO.setWorker_company_seq(workerSession.getCompany_seq());
		    logDTO.setWork_seq(ilboInfo.getWork_seq());
		    logDTO.setWork_company_seq(ilboInfo.getCompany_seq());
		    logDTO.setLog_text( workerSession.getWorker_name() );
			logDTO.setReg_admin( workerSession.getWorker_phone());
			ilboService.insertCodeLog(logDTO);
			//===============================================================
			
			try {
				NotificationDTO notificationDTO = new NotificationDTO();
				
				notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
				
				// 공개 전체(예약)
				if(ilboDTO.getOutput_status_code() != null && ilboDTO.getOutput_status_code().equals("100005") && !ilboDTO.getWorker_company_seq().equals(ilboDTO.getCompany_seq())) {
					notificationDTO.setStatus_flag("ALR012");
					
					// 알림 보내는 메소드
					NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
					notificationUtil.ilboAlim(notificationDTO);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			// 정상 결과 리턴
			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());

		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));

			e.printStackTrace();
		}
	}

	/**
	 * @Method Name : setUpdateWorker
	 * @작성일 : 2020. 6. 18.
	 * @작성자 : Jangjaeho
	 * @변경이력 :
	 * @Method 설명 :
	 * 
	 */
	@RequestMapping(value = "/setUpdateWorker")
	public void setUpdateWorker(HttpServletResponse response, HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		WorkerDTO workerSession = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
		result.put("loginKey", workerSession.getLogin_key());

		try {
			// 회원정보 수정
			workerDTO.setLogin_key(workerSession.getLogin_key());
			workerDTO.setApp_last_date("1"); // 날짜가 들어감..
			workerService.updateWorkerInfo(workerDTO);

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(), result));
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Method Name : getJobAlimList
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Park YunSoo
	 * @Method 설명 : API 일자리 알림 푸쉬 온 리스트
	 */
	/* TODO : 2020-06-11 안쓰는 로직 성급하게 만듬.... 
	 * @RequestMapping("/getJobAlimList") public void
	 * getJobAlimList(HttpServletResponse response, HttpServletRequest request,
	 * JobAlimReceiveDTO jobAlimReceiveDTO) { ApiMsg msg = new ApiMsg(); Map result
	 * = new HashMap(); ParamValidate pVali = new ParamValidate(); JsonUtil jsonUtil
	 * = new JsonUtil();
	 * 
	 * // 인터 셉터에서 근로자 DTO 를 받아온다. WorkerDTO workerSession = (WorkerDTO)
	 * request.getAttribute(Const.APP_WORKER_INFO); result.put("loginKey",
	 * workerSession.getLogin_key());
	 * 
	 * try { jobAlimReceiveDTO.setWorker_seq(workerSession.getWorker_seq());
	 * jobAlimReceiveDTO.getPaging().setPageSize(Const.MOBILE_PAGE_SIZE);
	 * 
	 * int listCnt = jobAlimService.selectListCntToAPI(jobAlimReceiveDTO);
	 * List<JobAlimDTO> jobAlimList =
	 * jobAlimService.selectListToAPI(jobAlimReceiveDTO);
	 * 
	 * jobAlimReceiveDTO.getPaging().setRowCount(listCnt);
	 * jobAlimReceiveDTO.getPaging().setCurrentCnt(jobAlimList.size());
	 * 
	 * result.put("paging", jobAlimReceiveDTO.getPaging());
	 * result.put("jobAlimList", jobAlimList);
	 * 
	 * msg.setResult(result);
	 * 
	 * // 정상 결과 리턴 jsonUtil.responseJson(response, msg.getMsg()); }catch(Exception
	 * e) { jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(),
	 * result));
	 * 
	 * e.printStackTrace(); } }
	 * 
	 *//**
		 * 
		 * @Method Name : setUpdateJobAlim
		 * @작성일 : 2020. 6. 9.
		 * @작성자 : Park YunSoo
		 * @Method 설명 : API 일자리 알림 푸쉬 수정 updateFlag (R : 읽음, D : 삭제)
		 *//*
			 * @RequestMapping("/setUpdateJobAlim") public void
			 * setUpdateJobAlim(HttpServletResponse response, HttpServletRequest request,
			 * JobAlimReceiveDTO jobAlimReceiveDTO) { ApiMsg msg = new ApiMsg(); Map result
			 * = new HashMap(); ParamValidate pVali = new ParamValidate(); JsonUtil jsonUtil
			 * = new JsonUtil();
			 * 
			 * pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[receive_seq]",
			 * jobAlimReceiveDTO.getReceive_seq());
			 * pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[updateFlag]",
			 * jobAlimReceiveDTO.getUpdateFlag());
			 * 
			 * // 인터 셉터에서 근로자 DTO 를 받아온다. WorkerDTO workerSession = (WorkerDTO)
			 * request.getAttribute(Const.APP_WORKER_INFO); result.put("loginKey",
			 * workerSession.getLogin_key());
			 * 
			 * try { if (pVali.getErrorCnt() > 0) throw new
			 * Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			 * 
			 * if("R".equals(jobAlimReceiveDTO.getUpdateFlag())) {
			 * jobAlimReceiveDTO.setRead_yn("1"); }else { jobAlimReceiveDTO.setDel_yn("1");
			 * }
			 * 
			 * jobAlimService.updateInfoToAPI(jobAlimReceiveDTO);
			 * 
			 * msg.setResult(result);
			 * 
			 * // 정상 결과 리턴 jsonUtil.responseJson(response, msg.getMsg()); }catch(Exception
			 * e) { jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage(),
			 * result));
			 * 
			 * e.printStackTrace(); } }
			 */

	public static void main(String[] args) {
		String jumin = "000002";
		String worker_sex = "M";
		if(jumin != null && jumin.length() > 6) {
			String sex = jumin.substring(6,7);
			if("2".equals(sex) || "4".equals(sex)) {
				worker_sex = "F";
			}
		}
		System.out.println(worker_sex);
	}
}
