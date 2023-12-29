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
import org.springframework.web.bind.annotation.RequestMapping;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.ParamValidate;
import com.nemo.kr.common.msg.ApiMsg;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.DeviceDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.PushReservekDTO;
import com.nemo.kr.dto.PushSendDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.DeviceService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.PushReserveService;
import com.nemo.kr.service.PushSendService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.JsonUtil;
import com.nemo.kr.util.PushUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * 회원 API 컨트롤러 
 * @author nemojjang
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired DeviceService deviceService;
	@Autowired WorkerService workerService;
	@Autowired PushSendService pushSendService;
	@Autowired PushReserveService pushReserveService;
	@Autowired IlboService ilboService;
	@Autowired CodeService codeService;
	@Autowired ManagerService managerService;

	/**
	 *  app 로그인  토큰 조회
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLoginKey")	
	public void getLoginKey(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_pass]", workerDTO.getWorker_pass());		

		//버젼체크
		
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]"		, workerDTO.getOs_type());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			//worker 정보
			WorkerDTO resultDTO = workerService.getAppLoginToKenProc(workerDTO);
			
			//device 정보
			DeviceDTO deviceDTO = new DeviceDTO();
			deviceDTO.setOs_type(workerDTO.getOs_type());
			deviceDTO.setApp_type("W");					//w 는 worker app
			
			result.put("deviceInfo", deviceService.getAppVersion(deviceDTO));
			
			//result.put("loginKey", loginKey);
			result.put("result", resultDTO);
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
	 *  app 로그아웃
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setLogOut")	
	public void setLogOut(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_seq]", workerDTO.getWorker_seq());	
				
		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			WorkerDTO paramDTO = new WorkerDTO();
			paramDTO.setWorker_seq(workerDTO.getWorker_seq());
			paramDTO.setWorker_app_status("2");		//로그아웃
			paramDTO.setLogin_key("");
			
			workerService.updateWorkerInfo(paramDTO);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();
		}
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
	public void getAuthNum(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			String smsAuth = workerService.getAuthNum(workerDTO);

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
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setWorkerPassword")	
	public void setWorkerPassword(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone() );
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_pass]", workerDTO.getWorker_pass() );
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[sms_auth]", workerDTO.getSms_auth());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

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

	/**
	 * 컨설팅신청
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setWorkerConsulting")	
	public void setWorkerConsulting(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_phone]", workerDTO.getWorker_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_name]", workerDTO.getWorker_name());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerService.setWorkerConsulting(workerDTO);

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
	 * 일반 푸쉬상태 변경
	 * 일반푸쉬 사용유무 -0:미사용 1:사용 2:강제중지 ',
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setWorkerPush")	
	public void setWorkerPush(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_push_yn]", workerDTO.getWorker_push_yn());

		try {
			WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			//관리자가 강제로 중지 했을때는 app에서 수정 할 수 없다.
			if("2".equals(infoDTO.getWorker_push_yn())){
				throw new Exception(Const.CODE_USER_0008+"!;!"+ Const.MSG_USER_0008);
			}

			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerService.updateWorkerInfo(workerDTO);
			result.put("worker_push_yn", workerDTO.getWorker_push_yn());
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
	 * 작업 예약 PUSH 상태 변경
	 * 일가자 작업 예약 상태 0:대기 1:사용 2:사중 3:관중 - 앱을 설치 하면 기본값으로 사용으로 바꾼다
	 * 
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setIlboStatus")	
	public void setIlboStatus(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_reserve_push_status ]", workerDTO.getWorker_reserve_push_status());

		try {
			WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			//관리자가 강제로 중지 했을때는 app에서 수정 할 수 없다.
			if("3".equals(infoDTO.getWorker_reserve_push_status()) ){
				throw new Exception(Const.CODE_USER_0008+"!;!"+ Const.MSG_USER_0008);
			}

			// 1 or 2 외에는 받을 수 없다.
			if(!"1".equals(workerDTO.getWorker_reserve_push_status()) && !"2".equals(workerDTO.getWorker_reserve_push_status())){
				throw new Exception(Const.CODE_USER_0008+"!;!"+ Const.MSG_USER_0008);
			}

			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			workerService.updateWorkerInfo(workerDTO);

			result.put("worker_reserve_push_status", workerDTO.getWorker_reserve_push_status());
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
	 * 일반 PUSH 상태 변경
	 * @param response
	 * @param request
	 * @param workerDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setPushYn")	
	public void setPushYn(HttpServletResponse response,HttpServletRequest request, WorkerDTO workerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[worker_push_yn]", workerDTO.getWorker_push_yn());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			//관리자가 강제로 중지 했을때는 app에서 수정 할 수 없다.
			if("2".equals(infoDTO.getWorker_push_yn()) ){
				throw new Exception(Const.CODE_USER_0008+"!;!"+ Const.MSG_USER_0008);
			}

			// 1 or 2 외에는 받을 수 없다.
			if(!"1".equals(workerDTO.getWorker_push_yn()) && !"2".equals(workerDTO.getWorker_push_yn())){
				throw new Exception(Const.CODE_USER_0008+"!;!"+ Const.MSG_USER_0008);
			}

			workerService.updateWorkerInfo(workerDTO);

			result.put("worker_push_yn", workerDTO.getWorker_push_yn());
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
	 * 푸쉬 접속시 (푸쉬를 클릭 해서 접속 할때..
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPushInfo")	
	public void getPushInfo(HttpServletResponse response,HttpServletRequest request, PushSendDTO pushSendDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[send_seq]", pushSendDTO.getSend_seq());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			//worker 정보 가져오기
			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			//send 정보 가져오기
			PushSendDTO 	resultDTO 	= pushSendService.selectPushSendInfo(pushSendDTO);	// send push 정보

			String ilbo_seq 	= resultDTO.getIlbo_seq();		//일보 순번
			String pushType  	= resultDTO.getPush_type();		//1:수동푸쉬 2:자동푸쉬
			String pushConfirm  = resultDTO.getPush_confirm();	//푸쉬 확인 유무

			//1. 푸쉬 확인 유무 체크-------------------------------------------------------------------------------------------------------------
			if(pushConfirm.equals("0")){						//푸쉬 확인 안함
				//푸쉬 상태 업데이트
				pushSendDTO.setPush_confirm("1"); 				//푸쉬보낸 상태 0:푸쉬확인안함 1:푸쉬확인
				pushSendService.updatePushSend(pushSendDTO);  	//send_status

			}

			//2. 푸쉬 type 비교 -----------------------------------------------------------------------------------------------------------------
			if(pushType.equals("1")){	//수동푸쉬
				//3. 해당 ilbo가 이미 수락한 worker 가 있는지 체크 한다.
				IlboDTO paramDTO = new IlboDTO();
				paramDTO.setIlbo_seq(ilbo_seq);

				IlboDTO resultIlbo = ilboService.selectIlboInfo(paramDTO);

				if(resultIlbo != null){
					String output_status_code = resultIlbo.getOutput_status_code();
					if( "100012".equals(output_status_code) ){	//상태코드가 푸쉬이면
						//지난 시간인지 비교
						String ilboDate = resultIlbo.getIlbo_date();
						String ilbWorkArrival = resultIlbo.getIlbo_work_arrival().replace(":", "");
						String nowDate = DateUtil.getCurDate2();
						
						int dateCompare = DateUtil.dateCompare(nowDate, ilboDate);
						if(dateCompare > 0){// 오늘날짜가 일보날짜 보다 크면
							throw new Exception(Const.CODE_ILBO_0104+"!;!"+ Const.MSG_ILBO_0104);
						}else if(dateCompare ==0){//오늘날짜와 일보 날짜가 같으면
							String nowHour   = new java.text.SimpleDateFormat("HHmm").format(new java.util.Date());

							if(Integer.parseInt(nowHour) >= Integer.parseInt(ilbWorkArrival)){ //현재 시간이 도착 시간 보다 크면
								throw new Exception(Const.CODE_ILBO_0104+"!;!"+ Const.MSG_ILBO_0104);
							}else{
								result.put("ilboInfo", resultIlbo);	
							}
						}else{
							result.put("ilboInfo", resultIlbo);
						}
					}else{
						throw new Exception(Const.CODE_ILBO_0103+"!;!"+ Const.MSG_ILBO_0103);
					}
				}else{
					throw new Exception(Const.CODE_ILBO_0102+"!;!"+ Const.MSG_ILBO_0102);
				}
			}else{						//자동푸쉬
				// work push 정보
				PushReservekDTO pwDTO = new PushReservekDTO();
				pwDTO.setPush_seq(resultDTO.getReserve_seq());
				PushReservekDTO pushReserveDTO	= pushReserveService.selectPushReserveInfo(pwDTO);		

				//이미 수락한 worker 가 있는지 체크
				String workerSeq = pushReserveDTO.getWorker_seq(); 
				if(workerSeq.equals("0")){

					//일보의 정보를 가져 온다.
					IlboDTO paramDTO = new IlboDTO();
					paramDTO.setIlbo_seq(resultDTO.getIlbo_seq());
					IlboDTO ilboDTO = ilboService.selectIlboInfo(paramDTO);

					result.put("ilboInfo", ilboDTO);
				}else{
					throw new Exception(Const.CODE_ILBO_0101+"!;!"+ Const.MSG_ILBO_0101);
				}
			}

			//시간이 지났는지 체크 해서 APP 에서 시간이 지났다고 표현을 해야 됨
			msg.setResult(result);
			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();
		}
	}

	// 변경된 행 저장
	/*
	 * 푸쉬받고 수락 여부.
   */
	@RequestMapping(value = "/setPushInfo")	
	public void  setPushInfo(HttpServletRequest request, HttpServletResponse response, PushSendDTO pushSendDTO )throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[send_seq]", pushSendDTO.getSend_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[send_result]", pushSendDTO.getSend_result());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			//worker 정보 가져오기
			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			//send 정보 가져오기
			PushSendDTO 	resultDTO 	= pushSendService.selectPushSendInfo(pushSendDTO);	// send push 정보

			String ilbo_seq 		= resultDTO.getIlbo_seq();				//일보 순번
			String pushType  		= resultDTO.getPush_type();				//1:수동푸쉬 2:자동푸쉬
			String worker_seq 	= resultDTO.getWorker_seq();
			String sendResult   	= resultDTO.getSend_result();			//응답결과

			// 일보 정보 가져 오기--------------------------------------------------------------
			IlboDTO pDTO   = new IlboDTO();
			pDTO.setIlbo_seq(ilbo_seq);
			IlboDTO ilboDTO = ilboService.selectIlboInfo( pDTO );

			if(ilboDTO == null){
				throw new Exception(Const.CODE_ILBO_0102+"!;!"+ Const.MSG_ILBO_0102);
			}
			
			if(!"0".equals(sendResult)){
				throw new Exception(Const.CODE_ILBO_0103+"!;!"+ Const.MSG_ILBO_0103);
			}

			//수동 푸쉬 일때
			if("1".equals( pushType ) ){
				if( worker_seq.equals( ilboDTO.getWorker_seq() ) ){
					if( "100012".equals( ilboDTO.getOutput_status_code() ) ){//상태가 푸쉬 이면
						//worker 정보를 param 에 담는다.
						IlboDTO paramDTO = new IlboDTO();

						if(pushSendDTO.getSend_result().equals("1")) {
							paramDTO.setOutput_status_code("100005");
							paramDTO.setOutput_status_name("예약");
						}else{
							paramDTO.setOutput_status_code("100017");
							paramDTO.setOutput_status_name("푸취");
							
							//근로자 다시 예약 상태 insert
							IlboDTO inParamDTO = new IlboDTO();
							inParamDTO.setIlbo_date(ilboDTO.getIlbo_date());
							inParamDTO = setParamDto(inParamDTO, workerDTO);
							
							ilboService.insertIlboWorker(inParamDTO);
						}
						
						paramDTO.setIlbo_seq( ilbo_seq );
						paramDTO.setMod_output_date("1");	//상태 날짜를 넣도록
						//일보 상태 업데이트
						ilboService.setIlboInfo(paramDTO);
						//푸쉬 상태 업데이트
						pushSendService.updatePushSend(pushSendDTO);  	//send_status
						
						//codelog 남기기 ====================================================
						WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
						CodeLogDTO logDTO = new CodeLogDTO();
						logDTO.setIlbo_seq(paramDTO.getIlbo_seq());
						logDTO.setCode_type( "100" );
						logDTO.setCode_value( paramDTO.getOutput_status_code());
						logDTO.setCode_name( paramDTO.getOutput_status_name());
						logDTO.setLog_user_type("U");
						logDTO.setLog_text( infoDTO.getWorker_phone() );
						logDTO.setReg_admin( infoDTO.getWorker_seq());
						ilboService.insertCodeLog(logDTO);
						//=============================================================
						
						if(pushSendDTO.getSend_result().equals("1")) {
							String output_code 	= "100005";
						    //------------메니져 푸쉬 보내기------------------------//
							pDTO.setOutput_status_code("100005");
							ilboService.sendManagerPush(pDTO);
							//sendManagerPush(ilbo_seq, output_code);
						    //-----------------------------------------------//
						}	
					}else{ // 이미 처리된 일감
						throw new Exception(Const.CODE_ILBO_0103+"!;!"+ Const.MSG_ILBO_0103);
					}
				}else{// // 다른 사람에게 배정
					throw new Exception(Const.CODE_ILBO_0101+"!;!"+ Const.MSG_ILBO_0101);
				}
			}else{ //자동 푸쉬 일때
				/*
				 * 
				 *  
				 * 해당 일을 누가 먼저 잡았는지 여부를 체크하는 로직 필요  
				 *  
				 *  
				 *  
				 */
				//worker 정보를 param 에 담는다.
				IlboDTO paramDTO = new IlboDTO();

				paramDTO.setCompany_seq(workerDTO.getCompany_seq());
				paramDTO.setWork_seq(workerDTO.getWorker_seq());
				paramDTO.setIlbo_worker_name(workerDTO.getWorker_name());
				paramDTO.setIlbo_worker_phone(workerDTO.getWorker_phone());
				paramDTO.setIlbo_worker_addr(workerDTO.getWorker_addr());
				paramDTO.setIlbo_worker_latlng(workerDTO.getWorker_latlng());
				paramDTO.setIlbo_worker_ilgaja_addr(workerDTO.getWorker_ilgaja_addr());
				paramDTO.setIlbo_worker_ilgaja_latlng(workerDTO.getWorker_ilgaja_latlng());
				paramDTO.setIlbo_worker_jumin(workerDTO.getWorker_jumin());
				paramDTO.setIlbo_worker_job_code(workerDTO.getWorker_job_code());
				paramDTO.setIlbo_worker_job_name(workerDTO.getWorker_name());
				paramDTO.setIlbo_worker_barcode(workerDTO.getWorker_barcode());
				paramDTO.setIlbo_worker_memo(workerDTO.getWorker_memo());
				paramDTO.setIlbo_worker_bank_name(workerDTO.getWorker_bank_name());
				paramDTO.setIlbo_worker_bank_account(workerDTO.getWorker_bank_account());
				paramDTO.setIlbo_worker_bank_owner(workerDTO.getWorker_bank_owner());
				paramDTO.setIlbo_worker_feature(workerDTO.getWorker_feature());
				paramDTO.setIlbo_worker_blood_pressure(workerDTO.getWorker_blood_pressure());
				paramDTO.setIlbo_worker_OSH_yn(workerDTO.getWorker_OSH_yn());
				paramDTO.setIlbo_worker_jumin_scan_yn(workerDTO.getWorker_jumin_scan_yn());
				paramDTO.setIlbo_worker_OSH_scan_yn(workerDTO.getWorker_OSH_scan_yn());
				paramDTO.setIlbo_worker_app_status(workerDTO.getWorker_app_status());
				paramDTO.setIlbo_worker_reserve_push_status(workerDTO.getWorker_reserve_push_status());
				paramDTO.setIlbo_worker_app_use_status(workerDTO.getWorker_app_use_status());
				paramDTO.setOutput_status_code("100013");
				paramDTO.setOutput_status_name("일예");
				paramDTO.setIlbo_seq(ilbo_seq);
				paramDTO.setMod_output_date("1");	//상태 날짜를 넣도록

				ilboService.setIlboInfo(paramDTO);
			}
			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();
		}
	}

	/**
	 * ilbo에 예약된 작업 리스트
	 * 하루전 부터 전부 보여준다.
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIlgajaIlboList")	
	public void getIlgajaIlboList(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		try {
			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setWorker_seq(workerDTO.getWorker_seq());

			List<IlboDTO> resultList = ilboService.getIlgajaIlboList(paramDTO);

			if(resultList == null){
				result.put("resultCount", 0);
				result.put("resultList", resultList);
			}else{
				result.put("resultCount", resultList.size());
				result.put("resultList", resultList);
			}

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
	 * 앱에서 일가자 신청 유무를 보여 주기 위해...
	 * 상태가 대기 또는 취소가 있는지를 찾아야 한다.
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIlgajaStatus")	
	public void getIlgajaStatus(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_date]", ilboDTO.getIlbo_date());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
			ilboDTO.setWorker_seq(workerDTO.getWorker_seq());

			List<IlboDTO> resultList = ilboService.getIlgajaStatus(ilboDTO);

			if(resultList == null){
				result.put("resultCount", 0);
				result.put("resultList", resultList);
			}else{
				result.put("resultCount", resultList.size());
				result.put("resultList", resultList);
			}

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
	 * 신청 /  취소
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/setWorkerIlbo")	
	public void setWorkerIlbo(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();


		*//**
		 * ================== 필수 파라미터 체크 ======================
		 *//*

		
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_date]", ilboDTO.getIlbo_date());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[searchType]", ilboDTO.getSearchType());	//A :신청 C:취소
		
		

		try {

			if("C".equals(ilboDTO.getSearchType() )){// 취소일때
				pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());		
			}

			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			
			//인터 셉터에서 근로자 DTO 를 받아온다.
			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
			ilboDTO.setWorker_seq(workerDTO.getWorker_seq());

			//전체 현황을 가져 온다.
			List<IlboDTO> resultList = ilboService.getIlgajaStatus(ilboDTO);
		
			IlboDTO paramDTO = new IlboDTO();
			
			//신청 일때
			if("A".equals(ilboDTO.getSearchType() )){
				
				boolean isWorkN   =  false;
				boolean isWorkC =  false;
				String		ilbo_seq = "";
				
				if(resultList.size() >0 ){
					
					
					
					for (int i = 0; i < resultList.size(); i++) {
						IlboDTO resultDTO=  (IlboDTO) resultList.get(i);
					
						//배정된 구인처가 있을때
						if(!"0".equals( resultDTO.getWork_seq() ) ){
							
										String outputCode = resultDTO.getOutput_status_code();
							
										//- 취소,펑크, 째앰, 대마, 완료 :  신규접수
										if( "100013".equals(outputCode) || "100009".equals(outputCode) || "100010".equals(outputCode) || "100008".equals(outputCode)  || "100015".equals(outputCode) ){
											
											isWorkC = true;
										}
						}else{// 배정된 구인처가 없을때
								ilbo_seq =resultDTO.getIlbo_seq();
								isWorkN = true;
						}
					
					}//end for
				
					if(isWorkC && isWorkN){	//배정된 구인처가 없고 
						paramDTO.setIlbo_seq(ilbo_seq);
						paramDTO.setOutput_status_code("100002");
						paramDTO.setOutput_status_name("대기");
						paramDTO.setMod_output_date("1");//상태 날짜를 넣도록
						ilboService.setIlboInfo(paramDTO);	
					}else if(isWorkC){
						
						paramDTO.setIlbo_date(ilboDTO.getIlbo_date());
						paramDTO = setParamDto(paramDTO,workerDTO);
				 
						ilboService.insertIlboWorker(paramDTO);	
					}else if(isWorkN){
						//배정된 구인처가 없을때
						paramDTO.setIlbo_seq(ilbo_seq);
						paramDTO.setOutput_status_code("100002");
						paramDTO.setOutput_status_name("대기");
						paramDTO.setMod_output_date("1");//상태 날짜를 넣도록
						ilboService.setIlboInfo(paramDTO);	
					}
					
				}else{
					paramDTO.setIlbo_date(ilboDTO.getIlbo_date());
					paramDTO = setParamDto(paramDTO,workerDTO);
				 
					ilboService.insertIlboWorker(paramDTO);			
				}
				
				
				
			}else{// 취소 일때
				for (int i = 0; i < resultList.size(); i++) {
					IlboDTO resultDTO=  (IlboDTO) resultList.get(i);
				
					//배정된 구인처가 없을때
					if("0".equals( resultDTO.getWork_seq() ) && "100002".equals(resultDTO.getOutput_status_code()) ){
						
						//배정된 구인처가 없을때
						paramDTO.setIlbo_seq(resultDTO.getIlbo_seq() );
						paramDTO.setOutput_status_code("100013");
						paramDTO.setOutput_status_name("취소");
						paramDTO.setMod_output_date("1");//상태 날짜를 넣도록
						ilboService.setIlboInfo(paramDTO);	
					}
				}				
			}
			

			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());

		} catch (Exception e) {

			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();

		}
	}*/
	
	/**
	 * 신청 /  취소
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	
	/**
	  * @Method Name : setWorkerIlbo
	  * @작성일 : 2020. 5. 22.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 요청 / 취소
	
	  */
	@RequestMapping(value = "/setWorkerIlbo")	
	public void setWorkerIlbo(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_date]", ilboDTO.getIlbo_date());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[searchType]", ilboDTO.getSearchType());	//A :신청 C:취소

		try {
			if("C".equals(ilboDTO.getSearchType() )){// 취소일때
				pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());		
			}

			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			//인터 셉터에서 근로자 DTO 를 받아온다.
			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
			ilboDTO.setWorker_seq(workerDTO.getWorker_seq());

			//전체 현황을 가져 온다.
			List<IlboDTO> resultList = ilboService.getIlgajaStatus(ilboDTO);
			IlboDTO paramDTO = new IlboDTO();
			
			//신청 일때
			if("A".equals(ilboDTO.getSearchType() )){
				if(resultList.size() >0 ){
					boolean isWorkN   =  false;
					String		ilbo_seq = "";
					
					for (int i = 0; i < resultList.size(); i++) {
						IlboDTO resultDTO=  (IlboDTO) resultList.get(i);
					
						//배정된 구인처가 없으면
						if("0".equals( resultDTO.getWork_seq() ) ){
							ilbo_seq =resultDTO.getIlbo_seq();
							isWorkN = true;
							break;
						}
					
					}//end for
				
					if(isWorkN){//배정된 구인처가 없을때
						paramDTO.setIlbo_seq(ilbo_seq);
						paramDTO.setOutput_status_code("100002");
						paramDTO.setOutput_status_name("대기");
						paramDTO.setMod_output_date("1"); //상태 날짜를 넣도록
						ilboService.setIlboInfo(paramDTO);	
					}else{		//배정된 구인처가 있으면 신규로 넣는다.
						paramDTO.setIlbo_date(ilboDTO.getIlbo_date());
						paramDTO = setParamDto(paramDTO,workerDTO);
				 		ilboService.insertIlboWorker(paramDTO);	
					}
					
				}else{
					paramDTO.setIlbo_date(ilboDTO.getIlbo_date());
					paramDTO = setParamDto(paramDTO,workerDTO);
					
					ilboService.insertIlboWorker(paramDTO);			
				}
			}else{// 취소 일때
				paramDTO.setIlbo_seq(ilboDTO.getIlbo_seq() );
				paramDTO.setOutput_status_code("100013");
				paramDTO.setOutput_status_name("취소");
				paramDTO.setMod_output_date("1");//상태 날짜를 넣도록
				ilboService.setIlboInfo(paramDTO);	
			}
			
			msg.setResult(result);

			// 정상 결과 리턴
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// 예외 리턴
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		

			e.printStackTrace();
		}
	}

	private IlboDTO setParamDto(IlboDTO paramDTO, WorkerDTO workerDTO){
		paramDTO.setIlbo_assign_type( "A" );
		paramDTO.setCompany_seq(workerDTO.getCompany_seq());
		paramDTO.setWorker_company_seq(workerDTO.getCompany_seq());
		paramDTO.setWorker_seq(workerDTO.getWorker_seq());
		paramDTO.setIlbo_worker_name(workerDTO.getWorker_name());
		paramDTO.setIlbo_worker_phone(workerDTO.getWorker_phone());
		paramDTO.setIlbo_worker_addr(workerDTO.getWorker_addr());
		paramDTO.setIlbo_worker_latlng(workerDTO.getWorker_latlng() );
		paramDTO.setIlbo_worker_ilgaja_addr(workerDTO.getWorker_ilgaja_addr() );
		paramDTO.setIlbo_worker_ilgaja_latlng(workerDTO.getWorker_ilgaja_latlng() );
		paramDTO.setIlbo_worker_jumin(workerDTO.getWorker_jumin());
		paramDTO.setIlbo_worker_job_code(workerDTO.getWorker_job_code() );
		paramDTO.setIlbo_worker_job_name(workerDTO.getWorker_job_name() );
		paramDTO.setIlbo_worker_barcode(workerDTO.getWorker_barcode() );
		paramDTO.setIlbo_worker_memo(workerDTO.getWorker_memo() );
		paramDTO.setIlbo_worker_bank_code(workerDTO.getWorker_bank_code() );
		paramDTO.setIlbo_worker_bank_name(workerDTO.getWorker_bank_name() );
		paramDTO.setIlbo_worker_bank_account(workerDTO.getWorker_bank_account() );
		paramDTO.setIlbo_worker_bank_owner(workerDTO.getWorker_bank_owner() );
		paramDTO.setIlbo_worker_bankbook_scan_yn(workerDTO.getWorker_bankbook_scan_yn() );
		paramDTO.setIlbo_worker_feature(workerDTO.getWorker_feature() );
		paramDTO.setIlbo_worker_blood_pressure(workerDTO.getWorker_blood_pressure() );
		paramDTO.setIlbo_worker_OSH_yn(workerDTO.getWorker_OSH_scan_yn() );
		paramDTO.setIlbo_worker_jumin_scan_yn(workerDTO.getWorker_jumin_scan_yn() );
		paramDTO.setIlbo_worker_OSH_scan_yn(workerDTO.getWorker_OSH_scan_yn() );
		paramDTO.setIlbo_worker_app_status(workerDTO.getWorker_app_status() );
		paramDTO.setIlbo_worker_reserve_push_status(workerDTO.getWorker_reserve_push_status() );
		paramDTO.setIlbo_worker_app_use_status(workerDTO.getWorker_app_use_status() );
		paramDTO.setOutput_status_code("100002");							

		paramDTO.setOutput_status_name("대기");
		paramDTO.setIlbo_status("0");
		
		return paramDTO;
	}

	/**
	 * 오늘 현장 내일도 가요
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setCopyIlbo")	
	public void setCopyIlbo(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[to_ilbo_date]", ilboDTO.getTo_ilbo_date());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			ilboDTO.setCopy_worker("Y");
			ilboDTO.setIlbo_assign_type("A");
			ilboDTO.setOutput_status_code("100005");
			ilboDTO.setIlbo_status("3");
			ilboDTO.setCopy_rows(1);
			ilboDTO.setReg_admin("Worker");

			ilboService.copyIlboCell(ilboDTO);

			result.put("ilbo_seq", ilboDTO.getIlbo_seq());

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
	 * 내일은 다른일 잡아 주세요
	 * 
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setCopyWorker")	
	public void setCopyWorker(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[to_ilbo_date]", ilboDTO.getTo_ilbo_date());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);

			ilboDTO.setCopy_worker("Y");
			ilboDTO.setIlbo_assign_type("A");
			ilboDTO.setOutput_status_code("100002");
			ilboDTO.setIlbo_status("3");
			ilboDTO.setCopy_rows(1);
			ilboDTO.setReg_admin("Worker");
			ilboDTO.setCompany_seq(workerDTO.getCompany_seq());

			ilboService.setICopyWorkerCell(ilboDTO);

			result.put("ilbo_seq", ilboDTO.getIlbo_seq());

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
	 *  작업 상태 변경..
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setOutputCode")	
	public void setOutputCode(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[ilbo_seq]", ilboDTO.getIlbo_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[output_status_code]", ilboDTO.getOutput_status_code());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setCode_value(ilboDTO.getOutput_status_code());
			//코드값 가져오기
			CodeDTO resultDTO = codeService.getCodeInfo(codeDTO);
			
			//상태값 변경------------------------------------------------------------------
			ilboDTO.setOutput_status_name(resultDTO.getCode_name());
			ilboService.setIlboOutputStatus(ilboDTO);	//상태 변경값 저장

			result.put("output_status_code", ilboDTO.getOutput_status_code());
			result.put("output_status_name", resultDTO.getCode_name());
			
			//codelog 남기기 ====================================================
			WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
			CodeLogDTO logDTO = new CodeLogDTO();
			logDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
			logDTO.setCode_type("100");
			logDTO.setCode_value(ilboDTO.getOutput_status_code());
			logDTO.setCode_name( resultDTO.getCode_name());
			logDTO.setLog_user_type("U");
			logDTO.setLog_text( infoDTO.getWorker_phone() );
			logDTO.setReg_admin( infoDTO.getWorker_seq());
			ilboService.insertCodeLog(logDTO);
			//===============================================================
			    
		    //------------메니져 푸쉬 보내기------------------------//
		    ilboService.sendManagerPush(ilboDTO);
			
//		    String output_code 	= ilboDTO.getOutput_status_code();
//		    String ilbo_seq 	= ilboDTO.getIlbo_seq();
//		    sendManagerPush(ilbo_seq, output_code);

		    //-----------------------------------------------//
		    
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
	  * ilgajaM push
	 * @param ilbo_seq
	 * @param output_code
	 */
//	public void sendManagerPush(String ilbo_seq, String output_code){
//		if(!StringUtil.isNull(output_code)){
//			if(output_code.equals("100005") ||  output_code.equals("100003") || output_code.equals("100014")){
//	    		IlboDTO iDTO	  = new IlboDTO();
//		    	iDTO.setIlbo_seq(ilbo_seq);
//			    	
//		    	IlboDTO resultDTO = ilboService.selectIlboInfo(iDTO);	//일보 정보
//			    	
//		    	if(!"0".equals(resultDTO.getManager_seq()) ){
//		    		ManagerDTO paramDTO = new ManagerDTO();
//		    		paramDTO.setManager_seq(resultDTO.getManager_seq());
//		    		paramDTO.setUse_yn("1");
//		    		ManagerDTO managerDTO = managerService.selectManagerInfo(paramDTO);
//			    		
//		    		if(!"N".equals(managerDTO.getOs_type()) && "1".equals(managerDTO.getManager_push_yn() )){
//		    			PushUtil.sendManagerPush( 	managerDTO.getOs_type()
//			    										, resultDTO.getIlbo_date()
//			    										, managerDTO.getPush_token()
//			    										, "A"
//			    										, resultDTO.getIlbo_date()
//			    										,resultDTO.getIlbo_work_name()
//			    										,resultDTO.getIlbo_worker_name()
//			    										,resultDTO.getOutput_status_name());
//		    		}
//		    	}
//			}
//		}
//	}

	/**
	 * 디바이스 등록
	 *  
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setDeviceInfo")	
	public void setDeviceInfo(HttpServletResponse response,HttpServletRequest request, DeviceDTO deviceDTO) throws Exception {
		ApiMsg 			msg 		= new ApiMsg();				
		ParamValidate 	pVali 		= new ParamValidate();
		Map 			result 		= new HashMap();	
		JsonUtil 		jsonUtil 	= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_type]"	, deviceDTO.getApp_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]"		, deviceDTO.getOs_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_version]"	, deviceDTO.getApp_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_version]"	, deviceDTO.getOs_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_brand]", deviceDTO.getDevice_brand());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_id]"	, deviceDTO.getDevice_id());
		
		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			DeviceDTO resultDTO = deviceService.getDeviceInfo(deviceDTO);

			if(resultDTO != null){
				//throw new Exception(msg.setThrowMsg(Const.CODE_USER_0019, Const.MSG_USER_0019+"[" +resultDTO.getDevice_seq() + "]"));
				result.put("device_seq", resultDTO.getDevice_seq());
				result.put("isdevice", "1");
				msg.setResult(result);
				jsonUtil.responseJson(response, msg.getMsg());
			}else{
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
	 * App version 체크
	 * @param response
	 * @param request
	 * @param deviceDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkDeviceInfo")	
	public void checkDeviceInfo(HttpServletResponse response,HttpServletRequest request, DeviceDTO deviceDTO) throws Exception {
		ApiMsg msg 			= new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result 			= new HashMap();	
		JsonUtil jsonUtil 	= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_type]"	, deviceDTO.getApp_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]"		, deviceDTO.getOs_type());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			DeviceDTO resultDTO = deviceService.getAppVersion(deviceDTO);
			result.put("deviceInfo", resultDTO);

			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block							
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		
			e.printStackTrace();
		}
	}

	/**
	 * 디바이스 정보 업데이트
	 * 디바이스 테이블에는 정보를 계속 쌓고 업데이트 할 뿐이다. 
	 * @param response
	 * @param request
	 * @param deviceDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDeviceInfo")	
	public void updateDeviceInfo(HttpServletResponse response,HttpServletRequest request, DeviceDTO deviceDTO) throws Exception {
		ApiMsg 			msg 		= new ApiMsg();				
		ParamValidate 	pVali 		= new ParamValidate();
		Map 			result 		= new HashMap();	
		JsonUtil 		jsonUtil	= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ========================================================
		 */
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_type]", deviceDTO.getApp_type());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[app_version]", deviceDTO.getApp_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_version]", deviceDTO.getOs_version());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_seq]", deviceDTO.getDevice_seq());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_id]", deviceDTO.getDevice_id());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_num]", deviceDTO.getDevice_num());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[device_token]", deviceDTO.getDevice_token());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]", deviceDTO.getOs_type());

		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			if(deviceDTO.getApp_type().equals("W")){//구직자 앱
				WorkerDTO infoDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
				deviceDTO.setWorker_seq(infoDTO.getWorker_seq());

				//worker 테이블에 push 정보 쌓기
				WorkerDTO paramDTO = new WorkerDTO();
				paramDTO.setWorker_seq(infoDTO.getWorker_seq());
				paramDTO.setOs_type(deviceDTO.getOs_type());
				paramDTO.setPush_token(deviceDTO.getDevice_token());

				workerService.updateWorkerInfo(paramDTO);
			}else{//구인처 앱

			}

			deviceService.updateDeviceInfo(deviceDTO);

			result.put("device_seq", deviceDTO.getDevice_seq());
			msg.setResult(result);
			jsonUtil.responseJson(response, msg.getMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block							
			jsonUtil.responseJson(response, msg.getThrowMsg(e.getMessage()));		
			e.printStackTrace();
		}

	}
	
	/**
	 * ilbo에 지난 작업 리스트
	 * 오늘 부터 이전 일보 중 완료 된 일보를 보여 준다.
	 * @param response
	 * @param request
	 * @param pushDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPrevIlboList")	
	public void getPrevIlboList(HttpServletResponse response,HttpServletRequest request, IlboDTO ilboDTO) throws Exception {
		ApiMsg msg = new ApiMsg();				
		ParamValidate pVali = new ParamValidate();
		Map result = new HashMap();	
		JsonUtil jsonUtil = new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		//pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[send_seq]", pushSendDTO.getSend_seq());

		try {
			//if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));

			WorkerDTO workerDTO = (WorkerDTO) request.getAttribute(Const.APP_WORKER_INFO);
			
			ilboDTO.setWorker_seq(workerDTO.getWorker_seq());
			int ilboCount = ilboService.getPrevIlboCount(ilboDTO);
			List<IlboDTO> resultList = ilboService.getPrevIlboList(ilboDTO);
			
			ilboDTO.getPaging().setRowCount(ilboCount);
			ilboDTO.getPaging().setCurrentCnt(resultList.size());
			
			result.put("paging", ilboDTO.getPaging());
			result.put("ilboList", resultList);

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
	 *  app 로그인  토큰 조회
	 * 
	 * @param response
	 * @param userDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/setManagerApp")	
	public void setManagerApp(HttpServletResponse response,HttpServletRequest request, ManagerDTO managerDTO) throws Exception {
		ApiMsg 			msg 	= new ApiMsg();				
		ParamValidate 	pVali 	= new ParamValidate();
		Map 			result 	= new HashMap();	
		JsonUtil 		jsonUtil= new JsonUtil();

		/**
		 * ================== 필수 파라미터 체크 ======================
		 */

		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[manager_phone]", managerDTO.getManager_phone());
		pVali.nullChk(Const.MSG_INVALID_PARAMETER + "[os_type]"		, managerDTO.getOs_type());
		
		try {
			if(pVali.getErrorCnt() > 0) throw new Exception(pVali.getErrorMsg(Const.CODE_INVALID_PARAMETER));
			
			managerService.setManagerInfo(managerDTO);
			
			//device 정보
			DeviceDTO deviceDTO = new DeviceDTO();
			deviceDTO.setOs_type(managerDTO.getOs_type());
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
}
