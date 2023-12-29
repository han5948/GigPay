package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationWorkerMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkerMapper;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.CommonUtil;
//import com.nemo.kr.util.StringUtil;


/**
 * 일가자 구직자 관리 Service Impl
 *
 * @author  nemojabg
 * @version 1.0
 * @since   2017-04-20
 */
@Service
public class WorkerServiceImpl implements WorkerService {
	@Autowired WorkerMapper workerMapper;
	@Autowired ReplicationWorkerMapper replicationWorkerMapper;
	@Autowired SmsMapper smsMapper;

	@Override
	public int getWorkerTotalCnt(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub

		return replicationWorkerMapper.getWorkerTotalCnt(workerDTO);
	}

	@Override
	public List<WorkerDTO> getWorkerList(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		workerDTO.setSidx(workerDTO.getSidx().replace("c.company_name", "w.company_name"));
		return (List<WorkerDTO>) replicationWorkerMapper.getWorkerList(workerDTO);
	}

	@Override
	public WorkerDTO getWorkerView(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub

		return replicationWorkerMapper.getWorkerView(workerDTO);
	}

	@Override
	@Transactional
	public void setWorkerCell(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub

		workerMapper.setWorkerCell(workerDTO);
	}

	@Override
	@Transactional
	public void setWorkerInfo(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		//    workerMapper.setWorkerInfo(workerDTO);
		workerMapper.updateWorkerInfo(workerDTO);
	}
  
	@Override
	public WorkerDTO getAppLoginToKenProc(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub

		String loginKey = "";

		WorkerDTO result = replicationWorkerMapper.selectWorkerInfo(workerDTO);

		if ( result != null ) {
			// 로그인 토큰 생성
			if ( "3".equals(result.getWorker_app_status()) ) {	// 관리자가 중지 시켰을때
				throw new Exception(Const.CODE_USER_0016 +"!;!"+ Const.MSG_USER_0016);
			}
			
			if ( !"1".equals(result.getWorker_app_use_status()) ) {
				throw new Exception(Const.CODE_USER_0017 +"!;!"+ Const.MSG_USER_0017);
			}
			
			if ( !"1".equals(result.getUse_yn()) ) {
				throw new Exception(Const.CODE_USER_0007 +"!;!"+ Const.MSG_USER_0007);
			}
			
			loginKey = CommonUtil.getAppLoginKey(result.getWorker_seq());
			WorkerDTO paramDTO = new WorkerDTO();
			paramDTO.setWorker_seq(result.getWorker_seq());
			paramDTO.setLogin_key(loginKey);
			paramDTO.setWorker_app_status("1"); //로그인
			paramDTO.setApp_version(workerDTO.getApp_version());
			paramDTO.setPush_token(workerDTO.getPush_token());
			
			workerMapper.updateWorkerInfo(paramDTO);
			
			result.setLogin_key(loginKey);
		} else {
			throw new Exception(Const.CODE_USER_0001+"!;!"+ Const.MSG_USER_0001);
		}

		return result;
	}

	@Override
	public WorkerDTO getAppLoginToKenProcV2(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		String loginKey = "";

		workerDTO.setUse_yn("1");
		WorkerDTO result = replicationWorkerMapper.selectWorkerInfo(workerDTO);

		if ( result != null ) {
			// 로그인 토큰 생성
			if ( "0".equals(result.getWorker_app_status()) ) {	// 미설치 일때
				throw new Exception(Const.CODE_USER_0016 +"!;!"+ Const.MSG_USER_0016);
			}
			
			//승인 과 승인대가가 아니면 승인대기인 경우 APP 에서 관리 한다.
			if ( !"1".equals(result.getWorker_app_use_status()) &&  !"4".equals(result.getWorker_app_use_status()) && !"5".equals(result.getWorker_app_use_status()) && !"3".equals(result.getWorker_app_use_status()) ) {
				throw new Exception(Const.CODE_USER_0017 +"!;!"+ Const.MSG_USER_0017);
			}
            
			loginKey = CommonUtil.getAppLoginKey(result.getWorker_seq());
			WorkerDTO paramDTO = new WorkerDTO();
			paramDTO.setWorker_seq(result.getWorker_seq());
			paramDTO.setLogin_key(loginKey);
			paramDTO.setWorker_app_status("1"); //로그인
			paramDTO.setApp_last_date("now");
			paramDTO.setApp_version(workerDTO.getApp_version());
			paramDTO.setOs_type(workerDTO.getOs_type());
			
			if(!StringUtil.isNull(workerDTO.getPush_token())) {
				paramDTO.setPush_token(workerDTO.getPush_token());
			}
			
			workerMapper.updateWorkerInfo(paramDTO);
			result.setLogin_key(loginKey);
		} else {
			throw new Exception(Const.CODE_USER_0001+"!;!"+ Const.MSG_USER_0001);
		}

		return result;
	}

	@Override
	@Transactional
	public void setWorkerPassword(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub

		int count = replicationWorkerMapper.setWorkerPassword(workerDTO);

		if ( count == 0 ) {
			throw new Exception(Const.CODE_USER_0014+"!;!"+ Const.MSG_USER_0014);
		}
	}

	@Override
	@Transactional
	public void setWorkerConsulting(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		workerDTO.setUse_yn("1");
		WorkerDTO result = replicationWorkerMapper.selectWorkerInfo(workerDTO);
		
		if ( result != null ) {
			// 로그인 토큰 생성
			throw new Exception(Const.CODE_USER_0015+"!;!"+ Const.MSG_USER_0015);
		} else {
			workerMapper.setWorkerConsulting(workerDTO);
		}
	}

	@Override
	@Transactional
	public String getAuthNum(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		String authNum = "";
		WorkerDTO result = replicationWorkerMapper.selectWorkerInfo(workerDTO);
		
		if ( result == null ) {
			throw new Exception(Const.CODE_USER_0007+"!;!"+ Const.MSG_USER_0007);
		}
		
		if ( "3".equals(result.getWorker_app_use_status() ) ) {//관리자가 중지 시켰을때는 다시 인증을 받을 수 없다.
			throw new Exception(Const.CODE_USER_0016+"!;!"+ Const.MSG_USER_0016);
		}
		
		authNum = CommonUtil.getOrderNum(4);

		//sms 보내기=====================================
		String message = "[일가자] 회원 인증을 위한 인증번호 : " + authNum;
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setTr_msg(message);
		smsDTO.setTr_etc1("4");	//구직자 회원인증
		smsDTO.setTr_phone(workerDTO.getWorker_phone());
		smsMapper.insertInfo(smsDTO);
		
		// 소장이 구직자를 등록 하고 구직자가 앱을 설치 -> 비밀번호 변경시 미설치를 승인대기로 바꿔 준다.
		if("0".equals(result.getWorker_app_use_status())) {
			//앱설치 -> 승인대기
			workerDTO.setWorker_app_use_status("4");
		}
		workerDTO.setSms_auth(authNum);
		workerMapper.updateSmsAuth(workerDTO);
		return authNum;
	}
  
	@Override
	@Transactional
	public String getJoinAuthNum(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		String authNum = "";
		
		WorkerDTO result = replicationWorkerMapper.selectWorkerInfo(workerDTO);
		
		if ( result != null ) {
			throw new Exception(Const.CODE_USER_0015+"!;!"+ Const.MSG_USER_0015);
		}

		authNum = CommonUtil.getOrderNum(4);

		//sms 보내기=====================================
		String message = "[일가자] 회원가입을 위한 인증번호 : " + authNum;
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setTr_msg(message);
		smsDTO.setTr_etc1("6");	//구직자 회원인증
		smsDTO.setTr_phone(workerDTO.getWorker_phone());
		smsMapper.insertInfo(smsDTO);
		
		return authNum;
	}

	@Override
	public WorkerDTO getWorkerInfo(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.selectWorkerInfo(workerDTO);
	}

	@Override
	@Transactional
	public void updateWorkerInfo(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		workerMapper.updateWorkerInfo(workerDTO);
	}

	@Override
	@Transactional
	public void removeWorkerInfo(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		workerMapper.removeWorkerInfo(workerDTO);
	}

	@Override
	public int chkWorkerPhone(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.chkWorkerPhone(workerDTO);
	}

	@Override
	public int chkWorkerJumin(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.chkWorkerJumin(workerDTO);
	}

	@Override
	@Transactional
	public void insertWorker(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		 workerMapper.insertWorker(workerDTO);
	}

	@Override
	public int selectWorkerCount(WorkerDTO paramDTO) {
		// TODO Auto-generated method stub
		 return replicationWorkerMapper.selectWorkerCount(paramDTO);
	}

	@Override
	@Transactional
	public void updateWorkerLogout(WorkerDTO paramDTO) {
		// TODO Auto-generated method stub
		workerMapper.updateWorkerLogout(paramDTO);
	}

	@Override
	public List<WorkerDTO> locationMap(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.locationMap(workerDTO);
	}

	@Override
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.selectReceiveTokenInfo(workerDTO);
	}

	@Override
	public List<WorkerDTO> selectWaitApprovalCount(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.selectWaitApprovalCount(workerDTO);
	}

	@Override
	public WorkerDTO selectWorkerInfo(WorkerDTO workerDTO) throws Exception {
		// TODO Auto-generated method stub
		return replicationWorkerMapper.selectWorkerInfo(workerDTO);
	}

	@Override
	public WorkerDTO selectWorkerInfoMasterDB(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return workerMapper.selectWorkerInfo(workerDTO);
	}
}
