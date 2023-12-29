package com.nemo.kr.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ilgajaComm.util.DateUtil;
import com.ilgajaComm.util.IlgajaPayUtil;
import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.dto.AutoIlboDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.IlboDeductionDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.OutputDTO;
import com.nemo.kr.dto.StatusSumDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationIlboMapper;
import com.nemo.kr.mapper.ilgaja.write.IlboMapper;
import com.nemo.kr.mapper.ilgaja.write.LogMapper;
import com.nemo.kr.service.IlboService;
//import com.nemo.kr.util.IlgajaPayUtil;
import com.nemo.kr.util.MessageUtil;



/**
  * @FileName : IlboServiceImpl.java
  * @Project : ilgaja
  * @Date : 2020. 6. 10. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 일일대장
  */
@Service
public class IlboServiceImpl implements IlboService {

	@Autowired IlboMapper ilboMapper;
	@Autowired ReplicationIlboMapper replicationIlboMapper;
	@Autowired LogMapper logMapper;
	@Autowired MessageUtil messageUtil;
	@Autowired RestTemplate restTemplate;
	
	@Value("#{common['autoSendBaseUrl']}")
	String autoSendBaseUrl;

	@Override
	public int getIlboTotalCnt(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		return replicationIlboMapper.getIlboTotalCnt(ilboDTO);
	}

	@Override
	public List<IlboDTO> getIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		return (List<IlboDTO>) replicationIlboMapper.getIlboList(ilboDTO);
	}

	@Override
	public IlboDTO getIlboExcelInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		return replicationIlboMapper.getIlboExcelInfo(ilboDTO);
	}

	@Override
	public List<Map<String, Object>> getIlboExcel(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		return (List<Map<String, Object>>) replicationIlboMapper.getIlboExcel(ilboDTO);
	}

	@Override
	@Transactional
	public void setIlboCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		ilboMapper.setIlboCell(ilboDTO);
	}

	@Override
	@Transactional
	public void setIlboInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub

		ilboMapper.setIlboInfo(ilboDTO);
	}

	@Override
	@Transactional
	public void copyIlboCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		for( int i = 0; i < ilboDTO.getSel_ilbo_seq().length; i++ ) {
			IlboDTO paramDTO = new IlboDTO();
			paramDTO.setIlbo_seq(ilboDTO.getSel_ilbo_seq()[i]);
			paramDTO.setCopy_worker(ilboDTO.getCopy_worker());
			IlboDTO resultDTO = replicationIlboMapper.selectIlboPriceInfo(paramDTO);
			
			if( resultDTO.getIlbo_kind_code() != null ) {
				IlgajaPayUtil payUtil = new IlgajaPayUtil(
						resultDTO.getIlbo_unit_price() 
						, resultDTO.getWork_company_seq()
						, resultDTO.getWorker_company_seq()
						, resultDTO.getCounselor_rate()
						, resultDTO.getWork_owner()
						, Double.parseDouble(resultDTO.getJob_work_fee())
						, Double.parseDouble(resultDTO.getJob_worker_fee()));
				
				paramDTO.setIlbo_unit_price( payUtil.getIlboUnitPrice());
				paramDTO.setIlbo_fee( payUtil.getIlboFee() );
				paramDTO.setShare_fee(payUtil.getShareFee());
				paramDTO.setIlbo_pay(payUtil.getIlboPay() );
				paramDTO.setCounselor_fee(payUtil.getCounselorFee() );
				paramDTO.setFee_info(payUtil.getFee_info());
				paramDTO.setPay_info(payUtil.getPay_info());
				paramDTO.setWork_fee(payUtil.getWorkFee() + "");
				paramDTO.setWorker_fee(payUtil.getWorkerFee() + "");
			}else {
				paramDTO.setIlbo_unit_price( resultDTO.getIlbo_unit_price() );
				paramDTO.setIlbo_fee( resultDTO.getIlbo_fee() );
				paramDTO.setShare_fee(resultDTO.getShare_fee());
				paramDTO.setIlbo_pay(resultDTO.getIlbo_pay() );
				paramDTO.setCounselor_fee(resultDTO.getCounselor_fee() );
				paramDTO.setFee_info(resultDTO.getFee_info());
				paramDTO.setPay_info(resultDTO.getPay_info());
			}
			
			paramDTO.setTo_ilbo_date(ilboDTO.getTo_ilbo_date());
			paramDTO.setReg_admin(ilboDTO.getReg_admin());
			
			for ( int copyRow = 0; copyRow < ilboDTO.getCopy_rows(); copyRow++ ) {
				ilboMapper.copyIlboCell(paramDTO);
			}
		}
	}

	@Override
	@Transactional
	public void addWorkToIlboCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		for ( int i = 0; i < ilboDTO.getCopy_rows(); i++ ) {
			ilboMapper.addWorkToIlboCell(ilboDTO);
		}
			
	}

	@Override
	@Transactional
	public void addWorkerToIlboCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		if ( ilboDTO != null && ilboDTO.getCopy_rows() > 0 ) {
			for ( int i = 0; i < ilboDTO.getCopy_rows(); i++ ) {
				ilboMapper.addWorkerToIlboCell(ilboDTO);
			}
		}
	}

	@Override
	@Transactional
	public void removeIlboInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.removeIlboInfo(ilboDTO);
	}

	@Override
	public IlboDTO selectIlboInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboInfo(ilboDTO);
	}

	@Override
	@Transactional
	public void setIlboOutputStatus(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.setIlboOutputStatus(ilboDTO);
	}

	@Override
	public List<IlboDTO> getIlgajaIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return (List<IlboDTO>) replicationIlboMapper.getIlgajaIlboList(ilboDTO);
	}

	@Override
	@Transactional
	public void setICopyWorkerCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.setICopyWorkerCell(ilboDTO);	
	}

	@Override
	public List<IlboDTO> getIlgajaStatus(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return  (List<IlboDTO>) replicationIlboMapper.getIlgajaStatus(ilboDTO);
	}

	@Override
	@Transactional
	public void insertIlboWorker(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.insertIlboWorker(ilboDTO);	
	}

	@Override
	public int getPrevIlboCount(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getPrevIlboCount(ilboDTO);
	}

	@Override
	public List<IlboDTO> getPrevIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return (List<IlboDTO>) replicationIlboMapper.getPrevIlboList(ilboDTO);
	}

	@Override
	@Transactional
	public void insertCodeLog(CodeLogDTO logDTO) {
		// TODO Auto-generated method stub
		logMapper.insertCodeLog(logDTO);
	}

	/* 
	 * 현장매니저 푸쉬 보내기
	 * @see com.nemo.kr.service.IlboService#sendManagerPush(com.nemo.kr.dto.IlboDTO)
	 */
	@Override
	public void sendManagerPush(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		String outputCode = ilboDTO.getOutput_status_code();

		if(!StringUtil.isNull(outputCode) && (outputCode.equals("100005") ||  outputCode.equals("100003") || outputCode.equals("100014") ) ){

			IlboDTO resultDTO =  replicationIlboMapper.selectPushInfo(ilboDTO);

			if(resultDTO != null) {
				if( !"0".equals(resultDTO.getManager_seq())	&& !"N".equals(resultDTO.getOs_type())	&& "1".equals(resultDTO.getManager_push_yn()) ){
					
					
					ManagerDTO tokenDTO = new ManagerDTO();
					tokenDTO.setPush_token(resultDTO.getPush_token());
					tokenDTO.setManager_push_yn(resultDTO.getManager_push_yn());
					tokenDTO.setOs_type(resultDTO.getOs_type());
					tokenDTO.setManager_seq(resultDTO.getManager_seq());
					List<ManagerDTO> tokenList = new ArrayList<ManagerDTO>();
					tokenList.add(tokenDTO);
//					if("출역".equals(resultDTO.getOutput_status_name()) || "출력".equals(resultDTO.getOutput_status_name()) ) workStatus = "출발";

					String sendMessage = "구인일: " +resultDTO.getIlbo_date() + "\n현장명: " + resultDTO.getIlbo_work_name() +"\n구직자: "+ resultDTO.getIlbo_worker_name() + "["+resultDTO.getOutput_status_name()+ "] " ;
					messageUtil.commManagerThread("일가자M", sendMessage, resultDTO.getIlbo_seq(), tokenList, "1", "M", "M", 1);
					
//					PushUtil.sendManagerPush( 	
//							resultDTO.getOs_type()
//							, resultDTO.getIlbo_date()
//							, resultDTO.getPush_token()
//							, "A"
//							, resultDTO.getIlbo_date()
//							,resultDTO.getIlbo_work_name()
//							,resultDTO.getIlbo_worker_name()
//							,resultDTO.getOutput_status_name()
//							);
				}
			}
		}
	}

	@Override
	@Transactional
	public void addOrderToIlboCell(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.addOrderToIlboCell(ilboDTO);
	}

	@Override
	@Transactional
	public void setCopyIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.setCopyIlbo(ilboDTO);
	}

	@Override
	public IlboDTO getIlboInfoW2(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboInfoW2(paramDTO);
	}


	@Override
	public List<IlboDTO> getIlboListW2(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboListW2(paramDTO);
	}

	@Override
	public List<IlboDTO> getIlboOutputCodeListW2(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboOutputCodeListW2(ilboDTO);
	}

	@Override
	@Transactional
	public void copyIlboWork(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.copyIlboWork(ilboDTO);
	}

	@Override
	public List<IlboDTO> getIlboHistList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboHistList(ilboDTO);
	}

	@Override
	public int selectIlboHistCnt(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboHistCnt(ilboDTO);
	}

	@Override
	public int selectIlboWorkCnt(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboWorkCnt(ilboDTO);
	}

	@Override
	public List<IlboDTO> getIlboWorkList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboWorkList(ilboDTO);
	}

	@Override
	public IlboDTO selectSmsInfo(String ilbo_seq) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectSmsInfo(ilbo_seq);
	}

	@Override
	public List<IlboDTO> selectIlboTimeList(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboTimeList(paramDTO);
	}

	/**
	 * api W 에서 호출된다. 기존에 대기, 지금 상태인 구직자가 있으면 remove 시키기 위함이다.
	 */
	@Override
	@Transactional
	public void setIlboReserve(IlboDTO ilboInfo, IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		
		//일 요청 상태가 있다면 제거 한다.
		IlboDTO paramDTO = new IlboDTO();
		paramDTO.setIlbo_date(ilboInfo.getIlbo_date());
		paramDTO.setWorker_seq(ilboDTO.getWorker_seq());
		
		List<IlboDTO> workerIlboList = replicationIlboMapper.selectWorkerIlbo(paramDTO);
		
		if(workerIlboList.size() > 0) {
			for (IlboDTO workerIlbo : workerIlboList) {
				if( Integer.parseInt(workerIlbo.getWork_seq()) > 0 ) {
					workerIlbo.setMod_admin(ilboDTO.getMod_admin());
					ilboMapper.setIlboWorkerReset(workerIlbo);
				}else {
					ilboMapper.removeIlboInfo(workerIlbo);
				}
			}
		}
		
		//해당 일보 구직자정보 업데이트 한다.
		ilboMapper.setIlboInfo(ilboDTO);
		
		
	}

	@Override
	public CodeLogDTO selectCode(CodeLogDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectCode(paramDTO);
	}

	@Override
	@Transactional
	public void insertIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.insertIlbo(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectCompanionList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectCompanionList(ilboDTO);
	}

	@Override
	public List<IlboDTO> getIlboListV3(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getIlboListV3(paramDTO);
	}

	@Override
	public List<IlboDTO> getMyIlboList(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getMyIlboList(paramDTO);
	}

	@Override
	public void setIlboWorkerReset(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.setIlboWorkerReset(ilboDTO);
	}

	@Override
	public List<IlboDTO> getSelectIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getSelectIlboList(ilboDTO);
	}

	@Override
	public OutputDTO getOutputStatusSum(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getOutputStatusSum(ilboDTO);
	}

	@Override
	public StatusSumDTO getAssignWorkSum(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getAssignWorkSum(ilboDTO);
	}

	@Override
	public StatusSumDTO getNotAssignWorkSum(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.getNotAssignWorkSum(ilboDTO);
	}

	@Override
	public IlboDTO selectWebPushIlboInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		String defaultDate = "19600101";
		IlboDTO managerInfo = replicationIlboMapper.selectManagerPhoneInfo(ilboDTO);
		IlboDTO workManagerInfo = replicationIlboMapper.selectWorkManagerPhoneInfo(ilboDTO);
		
		String managerIlboDate = defaultDate;
		String workManagerIlboDate = defaultDate;
		if( managerInfo != null ) {
			managerIlboDate = managerInfo.getIlbo_date();
		}
		if( workManagerInfo != null ) {
			workManagerIlboDate = workManagerInfo.getIlbo_date();
		}
		try {
			Date managerDate = DateUtil.getDate(managerIlboDate);
			Date workManagerDate = DateUtil.getDate(workManagerIlboDate);
			
			int compareValue = managerDate.compareTo(workManagerDate);
			if( compareValue == 1 ) {
				return managerInfo;
			}
			if( compareValue == -1 ) {
				return workManagerInfo; 
			}
			if( managerIlboDate.equals(defaultDate) ) {
				return null;
			}
			return managerInfo;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		//return ilboMapper.selectWebPushIlboInfo(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectIlboResetList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboResetList(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectLimitList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectLimitList(ilboDTO);
	}

	@Override
	public IlboDTO selectIlboSignInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectIlboSignInfo(ilboDTO);
	}

	@Override
	public int selectAgoIlboCount(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAgoIlboCount(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectAgoIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAgoIlbo(ilboDTO);
	}

	@Override
	public IlboDTO selectFirstIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectFirstIlbo(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectAgoLastIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAgoLastIlbo(ilboDTO);
	}

	@Override
	public void insertIlboDeduction(IlboDeductionDTO ilboDeductionDTO) {
		// TODO Auto-generated method stub
		ilboMapper.insertIlboDeduction(ilboDeductionDTO);
	}

	@Override
	public List<IlboDTO> selectDeductionStatusList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectDeductionStatusList(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectDeductionList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectDeductionList(ilboDTO);
	}

	@Override
	public void updateIlboDeduction(IlboDeductionDTO ilboDeductionDTO) {
		// TODO Auto-generated method stub
		ilboMapper.updateIlboDeduction(ilboDeductionDTO);
	}

	@Override
	public void insertDeductible(IlboDeductibleDTO ilboDeductibleDTO) {
		// TODO Auto-generated method stub
		ilboMapper.insertDeductible(ilboDeductibleDTO);
	}

	@Override
	public IlboDeductibleDTO selectDeductibleInfo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectDeductibleInfo(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectFirstLastIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectFirstLastIlbo(ilboDTO);
	}

	@Override
	public List<IlboDTO> selectAgoIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAgoIlboList(ilboDTO);
	}

	@Override
	public void setAutoIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		ilboMapper.setAutoIlbo(ilboDTO);
		
		if( ilboDTO.isAutoSendStopStatus() ) {
			//autosendworker 개수 구하기
			String autoSendWorkerCount = getAutoSendWorkerCount(ilboDTO);
			ilboDTO.setAuto_send_worker_count(autoSendWorkerCount);
			//autosendworker 삭제
			restTemplate.delete(autoSendBaseUrl + "/autosendworkers/" + ilboDTO.getIlbo_seq());
		}
		ilboMapper.insertAutoIlboStatusLog(ilboDTO);
	}

	@Override
	public List<AutoIlboDTO> selectAutoIlboLog(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAutoIlboLog(ilboDTO);
	}

	@Override
	public AutoIlboDTO selectAutoIlbo(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationIlboMapper.selectAutoIlbo(ilboDTO);
	}

	@Override
	public String getAutoSendWorkerCount(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		URI uri = UriComponentsBuilder
                .fromUriString(autoSendBaseUrl)
                .path("/autosendworkers/" + ilboDTO.getIlbo_seq() + "/count")
                .encode()
                .build()
                .toUri();
		
		return restTemplate.getForEntity(uri, String.class)
					.getBody();
	}
}

