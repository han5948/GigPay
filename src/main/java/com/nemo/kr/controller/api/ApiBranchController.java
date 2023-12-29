package com.nemo.kr.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.ArsDTO;
import com.nemo.kr.dto.BsmsDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.NotificationLogDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.ArsService;
import com.nemo.kr.service.BranchService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.NotificationLogService;
import com.nemo.kr.service.NotificationService;
import com.nemo.kr.util.JsonUtil;


/**
 * branch API 컨트롤러 지점앱
 * @author nemojjang
 *
 */
@Controller
@RequestMapping("/branch/")
public class ApiBranchController {
	private static final Logger logger = LoggerFactory.getLogger(ApiBranchController.class);

	@Autowired IlboService ilboService;
	@Autowired ArsService arsService;
	@Autowired BranchService branchService;
	@Autowired NotificationService notificationService;
	@Autowired NotificationLogService notificationLogService;
	@Autowired AdminService adminService;
	
	@RequestMapping(value = "/updateNotificationLog")
	public void updateNotificationLog(HttpServletResponse response,HttpServletRequest request, NotificationLogDTO notificationLogDTO) throws Exception {
		if( notificationLogDTO.getNotification_log_seq() != null && !notificationLogDTO.getNotification_log_seq().equals("") ) {
			notificationLogService.updateNotificationLog(notificationLogDTO);
		}
	}
	
	/**
	  * @Method Name : getNotificationInfo
	  * @작성일 : 2020. 10. 14.
	  * @작성자 : Na GilJin
	  * @필수 param : paging.pageNo, ars_phone 
	  * @Method 설명 : Json 형태로 데이터 보내기
	  */
	@RequestMapping(value = "/getNotificationList")
	public void getNotificationList(HttpServletResponse response,HttpServletRequest request, AdminDTO adminDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();	
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();
		
		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[pageNo]", Integer.toString( adminDTO.getPaging().getPageNo()) );
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[admin_phone]", adminDTO.getAdmin_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[admin_seq]", adminDTO.getAdmin_seq());
		
		try {
			// String ars_phone = notificationDTO.getArs_phone();
			// int pageNo = notificationDTO.getPaging().getPageNo();
			
			if(pVali.getErrorCnt() > 0) throw new Exception( pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER) );
			
			/*
			 * logger.debug("\n========================================");
			 * System.out.println("ars_phone : " + ars_phone);
			 * System.out.println("pageNo : " + pageNo);
			 * logger.debug("========================================");
			 */
			
			//companyDTO 설정
			
			AdminDTO resultDTO = adminService.selectInfo( adminDTO );
			
			if(resultDTO == null){
				throw new Exception( msg.setThrowMsg( Const.CODE_USER_0007, Const.MSG_USER_0007 ) );
			}
			
			if("0".equals(resultDTO.getUse_yn())) {
				throw new Exception( msg.setThrowMsg( Const.CODE_USER_0007, Const.MSG_USER_0007 ) );
			}
			
			NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setNotification_type("A");
			notificationDTO.setCompany_seq( resultDTO.getCompany_seq() );
			notificationDTO.setAdmin_seq( adminDTO.getAdmin_seq() );
			notificationDTO.getPaging().setPageNo(adminDTO.getPaging().getPageNo());
			notificationDTO.getPaging().setPageSize( Const.MOBILE_PAGE_SIZE );
			
			int listCnt = notificationService.selectNotificationListCnt(notificationDTO);
			List<NotificationDTO> notificationList = null;
			if(listCnt > 0) {
				notificationList = notificationService.selectNotificationList(notificationDTO);
				notificationDTO.getPaging().setRowCount(listCnt);
				notificationDTO.getPaging().setCurrentCnt(notificationList.size());
			}
						
			result.put( "notificationList", notificationList );
			result.put("paging", notificationDTO.getPaging());
			
			msg.setResult(result);
			jsonUtil.responseJson( response, msg.getMsg() );
		
		}catch(Exception e) {
			// 예외 리턴
			jsonUtil.responseJson( response, msg.getThrowMsg(e.getMessage()) );		

			e.printStackTrace();
		}
	}
	
	/**
	  * @Method Name : getCrmInfo
	  * @작성일 : 2020. 2. 18.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : crm 조회
	
	  */
	@RequestMapping(value = "/getCrmInfo")	
	public void getCrmInfo(HttpServletResponse response,HttpServletRequest request, String callPhoneNum, String receivePhoneNum) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[callPhoneNum]", callPhoneNum);
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[receivePhoneNum]", receivePhoneNum);

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			logger.debug("\n========================================");
			System.out.println("callPhoneNum : " + callPhoneNum);
			System.out.println("receivePhoneNum : " + receivePhoneNum);
			logger.debug("========================================");
			
			String seq = "";
			Map crmInfo 	= new HashMap();

			// -- 1. 현장테이블에서 현장 메니져 폰번호에 맞는 현장을 찾는다.
			WorkDTO workDTO = branchService.getWork(callPhoneNum);
			if(workDTO != null) {
				String workSeq = workDTO.getWork_seq();

				//2 현장이 있을 경우 일일대장 에서 현장에 대한 지점을 찾는다.
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setWork_seq(workSeq);
				IlboDTO ilboDTO = branchService.selectIlboLimitOne(paramDTO);
				
				if(ilboDTO != null) {
					
					seq = ilboDTO.getIlbo_seq();
					
					result.put("crmType", 1);
					
					crmInfo.put("seq", seq);
					crmInfo.put("crmTitle", "일일대장");
					crmInfo.put("employerName", ilboDTO.getEmployer_name());
					crmInfo.put("companyName", ilboDTO.getCompany_name());
					crmInfo.put("managerName", ilboDTO.getManager_name());
					crmInfo.put("managerPhone", ilboDTO.getManager_phone());
					crmInfo.put("managerPosition", "현장매니져");
					crmInfo.put("workName", ilboDTO.getWork_name());
					crmInfo.put("ilboDate", ilboDTO.getIlbo_date());
					crmInfo.put("ilboArrival", ilboDTO.getIlbo_work_arrival());
					crmInfo.put("ilboKind", ilboDTO.getIlbo_kind_name());
					crmInfo.put("ilboFee", ilboDTO.getIlbo_unit_price());
					
				}
			}

			/*3. 1,2번이 존재 하지 않을 때. 일보에서 현장담장자 번호에 대한 지점을 찾는다.*/
			if("".equals(seq)) {
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setIlbo_work_manager_phone(callPhoneNum);
				IlboDTO ilboDTO = branchService.selectIlboLimitOne(paramDTO);
				
				if(ilboDTO != null) {
					
					seq = ilboDTO.getIlbo_seq();
					
					result.put("crmType", 2);
					
					crmInfo.put("seq", seq);
					crmInfo.put("employerName", ilboDTO.getEmployer_name());
					crmInfo.put("companyName", ilboDTO.getCompany_name());
					crmInfo.put("managerName", ilboDTO.getIlbo_work_manager_name());
					crmInfo.put("managerPhone", ilboDTO.getIlbo_work_manager_phone());
					crmInfo.put("managerPosition", "현장담당자");
					crmInfo.put("workName", ilboDTO.getWork_name());
					crmInfo.put("ilboDate", ilboDTO.getIlbo_date());
					crmInfo.put("ilboArrival", ilboDTO.getIlbo_work_arrival());
					crmInfo.put("ilboKind", ilboDTO.getIlbo_kind_name());
					crmInfo.put("ilboFee", ilboDTO.getIlbo_unit_price());
				}
			}

			// 4. 3번이 존재 하지 않을 경우 메니져 테이블에서 처음등록한 지점을 찾는다. 현장매니져 본사 매니져 (현장매니져가 있으면 현장 매니져가 먼저 검색된다.)
			if("".equals(seq)) {
				ManagerDTO paramDTO = new ManagerDTO();
				paramDTO.setManager_phone(callPhoneNum);
				ManagerDTO managerDTO = branchService.selectManagerInfo(paramDTO);

				if( managerDTO != null) {
					seq = managerDTO.getManager_seq();
					
					result.put("crmType", 3);
					
					crmInfo.put("seq", seq);
					crmInfo.put("crmTitle", "매니져");
					crmInfo.put("employerName", managerDTO.getEmployer_name());
					crmInfo.put("companyName", managerDTO.getCompany_name());
					crmInfo.put("managerName", managerDTO.getManager_name());
					crmInfo.put("managerPhone", managerDTO.getManager_phone());
	
					if("O".equals(managerDTO.getManager_type()) ){
						crmInfo.put("managerPosition", "현장매니져");
					}else {
						crmInfo.put("managerPosition", "본사담당자");
					}
				}
			}

			// 5. 4번이 존재 하지 않으면 구직자 테이블에서 해당 지점을 찾는다.
			if("".equals(seq)) {
				WorkerDTO paramDTO = new WorkerDTO();
				paramDTO.setWorker_phone(callPhoneNum);
				WorkerDTO workerDTO = branchService.selectWorkerInfo(paramDTO);

				if(workerDTO != null) {
					seq = workerDTO.getWorker_seq();
					
					result.put("crmType", 4);
					
					crmInfo.put("seq", seq);
					crmInfo.put("crmTitle", "구직자");
					crmInfo.put("companyName", workerDTO.getCompany_name());
					crmInfo.put("workerName", workerDTO.getWorker_name());
					crmInfo.put("workerPhone", workerDTO.getWorker_phone());
					crmInfo.put("workerIlgajaAddr", workerDTO.getWorker_ilgaja_addr());
	
					IlboDTO paramDTO2 = new IlboDTO();
					paramDTO2.setWorker_seq(workerDTO.getWorker_seq());
					IlboDTO ilboDTO = ilboService.selectIlboInfo(paramDTO2);
	
					if(ilboDTO == null) {
						crmInfo.put("workerIlboDate", "없음");
						crmInfo.put("workerIlbo", "");
	
					}else {
						crmInfo.put("workerIlboDate", ilboDTO.getIlbo_date());
						crmInfo.put("workerIlbo", ilboDTO.getIlbo_work_name());
					}
				}
			}

			//모두 해당이 되지 않을때..
			if("".equals(seq)) {
				result.put("crmType", 0);
			}
			
			ArsDTO arsParamDTO = new ArsDTO();
			arsParamDTO.setUser_cid(callPhoneNum);
			ArsDTO arsDTO = arsService.getSelectInfoLimitOne(arsParamDTO);
			
			if(arsDTO == null) {
				result.put("arsInfo", 0);
			}else {
				result.put("arsInfo", 1);
			}
			
			result.put("crmInfo", crmInfo);
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getAuth")
	public void getAuth(HttpServletResponse response, HttpServletRequest request, AdminDTO adminDTO)  {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[admin_phone]", adminDTO.getAdmin_phone());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			adminDTO.setUse_yn("1");
			String smsAuth = "";
			
			result = adminService.getAuthNum(adminDTO);
			
			
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}	
	
	@RequestMapping(value = "/setUpdateNotificationLog")
	public void setUpdateNotificationLog(HttpServletResponse response, HttpServletRequest request,  NotificationLogDTO notificationLogDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[notification_log_seq]", notificationLogDTO.getNotification_log_seq());

		try {
			if (pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			notificationLogDTO.setRead_yn("1");
			notificationLogService.updateNotificationLog(notificationLogDTO);
			//msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}	

	@RequestMapping(value = "/setPushToken")
	public void setPushToken(HttpServletResponse response, HttpServletRequest request, AdminDTO adminDTO) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[admin_seq]", adminDTO.getAdmin_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[push_token]", adminDTO.getPush_token());

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			adminDTO.setMod_admin("ilbajaB");
			adminService.updateInfo(adminDTO);
			
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {

			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/sendSMSList", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void sendSMSList(HttpServletResponse response, HttpServletRequest request,   @RequestBody List<BsmsDTO> smsList) throws Exception {
		ApiMsg msg = new ApiMsg();
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[push_token]", adminDTO.getPush_token());

		 */

		try {
			if (pVali.getErrorCnt() > 0)
				throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			for (BsmsDTO bsmsDTO : smsList) {
				System.out.println(bsmsDTO.getType() + " / " +bsmsDTO.getAddress() + " / " + bsmsDTO.getBody() );	
			}
			 
			/*
				System.out.println(test);
				System.out.println("1111> " +  test.get(0).getBody());
			*/	
			msg.setResult(result);
			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));

			e.printStackTrace();
		}
	}
}
