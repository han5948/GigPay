package com.nemo.kr.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.ArsDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.ArsService;
import com.nemo.kr.util.JsonUtil;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * branch API 컨트롤러 지점앱
 * @author nemojjang
 *
 	  * 
	  * TO-be 2021-0924
	  * 1. 일일대장에서 오늘 이후 데이터 중 현장메니져 전화 번호 가 있는지? 구직자 지점이 배정되었으면 구직자 지점으로 전화 , 구직자 지점이 배정되지 않았으면 구인자 지점으로 전화
	  * 2. 일일대장에서 오늘 이후 데이터 중 현장 담장자 전화 번호 가 있는지? 구직자 지점이 배정되었으면 구직자 지점으로 전화 , 구직자 지점이 배정되지 않았으면 구인자 지점으로 전화
	  * 3. 매니저 테이블에서 전화 번호가 있는지? 있으면 해당지점으로 전화
	  * 4. 구직자 테이블에서 전화 번호가 있는지? 있으면 해당지점으로 전화
	  * 5. 다 없으면 07086661936 로 전화 (본사)
 */
@Controller
@RequestMapping("/api/ARS")
public class ApiARSController {
	private static final Logger logger = LoggerFactory.getLogger(ApiARSController.class);

	@Autowired ArsService arsService;
	@Autowired RestTemplate restTemplate;
	
	/**
	  * @Method Name : getCid
	  * @작성일 : 2020. 2. 19.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : ARS 연동 api
	
	  */
	@RequestMapping(value = "/getCid")	
	public  void getCid(HttpServletResponse response,HttpServletRequest request, String USERCID) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();			
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			resultMap 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();
		
		String returnType = "0";
		String seq ="0";
		String companySeq = "";
		String receivePhone = "";
		String resultCode = "0000";
		
		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[USERCID]", USERCID);

		try {
			if(pVali.getErrorCnt() > 0) {
				 throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			}
				
			// -- 1. 현장테이블에서 현장 메니져 폰번호에 맞는 현장을 찾는다.
//			WorkDTO workDTO = arsService.getWork(USERCID);
//			if(workDTO != null) {
//				String workSeq = workDTO.getWork_seq();
//				
//				/*
//				 * 2 현장이 있을 경우 오늘이후 일일대장 에서 현장에 대한 지점을 찾는다.
//				 * 오늘이후 지점으로 본사가 준일이 있는지? 이전의 일은 본사가 받아야 된다.
//				 */
//				IlboDTO mIlboDTO = arsService.getMilbo(workSeq);
//				if(mIlboDTO != null) {
//					if("0".equals(mIlboDTO.getWorker_company_seq())) {
//						companySeq = mIlboDTO.getCompany_seq();
//					}else {
//						companySeq = mIlboDTO.getWorker_company_seq();
//					}
//					
//					seq = mIlboDTO.getIlbo_seq();
//					returnType = "1";	//현장매니져전화 일일대장에서 worker의 소속 지점으로연결 
//				}
//			}
			// -- 1. 오늘 이후에 일일대장에서 매니져 폰번호로 등록된 일을 찾는다
			IlboDTO mIlboDTO = arsService.getMilbo(USERCID);
			if( mIlboDTO != null ) {
				if( "0".equals(mIlboDTO.getWorker_company_seq()) ) {
					companySeq = mIlboDTO.getWork_company_seq();
				}else {
					companySeq = mIlboDTO.getWorker_company_seq();
				}
				seq = mIlboDTO.getIlbo_seq();
				returnType = "1";	//오늘 이후 일일대장에 일요청을 한 매니저 전화 
			}
			
			/*2. 1번이 존재 하지 않을 때. 오늘이후 일일대장에서 현장담장자 번호에 대한 지점을 찾는다.*/
			if("".equals(companySeq)) {
				IlboDTO wIlboDTO = arsService.getWilbo(USERCID);
				if(wIlboDTO != null) {
					if("0".equals(wIlboDTO.getWorker_company_seq())) {
						companySeq = wIlboDTO.getWork_company_seq();
					}else {
						companySeq = wIlboDTO.getWorker_company_seq();
					}
					seq = wIlboDTO.getIlbo_seq();
					returnType = "2";	//현장담당자전화 일일대장에서 worker의 소속 지점으로연결
				}
			}
			
			// 3. 2번이 존재 하지 않을 경우 메니져 테이블에서 처음등록한 지점을 찾는다. 현장매니져 본사 매니져 (현장매니져가 있으면 현장 매니져가 먼저 검색된다.)
			if("".equals(companySeq)) {
				ManagerDTO managerDTO = arsService.getManager(USERCID);
				if(managerDTO != null) {
					companySeq = managerDTO.getCompany_seq();
					
					seq = managerDTO.getManager_seq();
					returnType = "3"; 	//현장매니져 본사매니져전화 현장매니져 등록지점
					
				}
			}
			
			// 4. 3번이 존재 하지 않으면 구직자 테이블에서 해당 지점을 찾는다.
			if("".equals(companySeq)) {
				WorkerDTO workerDTO = arsService.getWorker(USERCID);
				if(workerDTO != null) {
					companySeq = workerDTO.getCompany_seq();
					
					seq = workerDTO.getWorker_seq();
					returnType = "4"; 	//구직자 전화 
				}
			}
				
			if("".equals(companySeq)) {
				companySeq = Const.JOBNPARTNER_SEQ;
				
				seq = "0";
				returnType ="0";	//등록되지 않은 전화
			}
			
			CompanyDTO companyDTO = arsService.getComapny(companySeq);
			
			if(StringUtil.isNull(companyDTO.getArs_phone())) {
				receivePhone = "07086661936";
			}else {
				receivePhone = StringUtil.numberFormat(companyDTO.getArs_phone());
			}
			
			resultMap.put("LOGINCID", receivePhone);
			msg.setResult(resultMap);
			
			// 전화 알림------------------------------------------------------------------------------
			NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setApi_url("/callWebNotificationPush");
			notificationDTO.setArs_phone(StringUtil.numberFormat(USERCID));
			notificationDTO.setIlbo_seq(seq);
			notificationDTO.setReturn_type(returnType);
			notificationDTO.setCompany_seq(companySeq);
			NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
			notificationUtil.ilboAlim(notificationDTO);
			//===============================================

			// 정상 결과 리턴
			jsonUtil.responseArsJson(response, msg.getMsg());
		} catch (Exception e) {
			jsonUtil.responseArsJson(response, msg.getThrowMsg(e.getMessage()));	
			resultCode= "9999";
			e.printStackTrace();
		}
		
		//본사로 받아야 되념 api를 호출하여 notification을 보낸다.
		if(Const.JOBNPARTNER_SEQ.equals(companySeq) ) {
			
			//thread 처리 되어야 된다.
		}
		
		// ARS 로그 쌓기====================================		
		ArsDTO arsDTO = new ArsDTO();
		arsDTO.setUser_cid(USERCID);
		arsDTO.setCompany_seq(companySeq);
		arsDTO.setReturn_type(returnType);
		arsDTO.setType_seq(seq);
		arsDTO.setReceive_phone(receivePhone);
		arsDTO.setResult_code(resultCode);
		
		arsService.insertInfo(arsDTO);
	}
	
	@RequestMapping(value = "/test")	
	public  void test(HttpServletResponse response,HttpServletRequest request, String USERCID) throws Exception {
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setApi_url("/callWebNotificationPush");
		notificationDTO.setArs_phone("01039178283");
		// 알림 보내는 메소드
		NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
		notificationUtil.ilboAlim(notificationDTO);
		
	}
	
	public static void main(String[] args) {
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setApi_url("/callWebNotificationPush");
		notificationDTO.setArs_phone("01039178283");
		notificationDTO.setIlbo_seq("11155");
		notificationDTO.setReturn_type("4");
		// 알림 보내는 메소드
//		NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
//		notificationUtil.ilboAlim(notificationDTO);
	}
}
