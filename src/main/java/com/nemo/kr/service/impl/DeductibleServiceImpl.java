package com.nemo.kr.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilgajaComm.util.TaxCalc;
import com.ilgajaComm.util.TaxCalc.TaxPriceDTO;
import com.nemo.kr.column.LimitCount;
import com.nemo.kr.common.exception.FutureIlboException;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.IlboDeductionDTO;
import com.nemo.kr.dto.TaxRateDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationIlboMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationTaxRateMapper;
import com.nemo.kr.mapper.ilgaja.write.IlboMapper;
import com.nemo.kr.service.DeductibleService;
import com.nemo.kr.util.DateUtil;

@Service
public class DeductibleServiceImpl implements DeductibleService {

	@Autowired
	ReplicationTaxRateMapper replicationTaxRateMapper;
	@Autowired
	IlboMapper ilboMapper;
	@Autowired
	ReplicationIlboMapper replicationIlboMapper;
	
	@Override
	public String joinInsurance(IlboDTO ilboDTO) throws Exception {
		
		IlboDTO ilboParam = new IlboDTO();
		ilboParam.setIlbo_seq(ilboDTO.getIlbo_seq());
		IlboDTO beforeIlboInfo = replicationIlboMapper.selectIlboInfo(ilboParam);
		
		// 공제액이 계산되어있으면 가입안시킴.
		if( Integer.parseInt(beforeIlboInfo.getDeductible_sum()) > 0 ) {
			return null;
		}
		
		// 해당 현장, 구인처의 출역일수 제한 확인 =============================================
		LimitCount resultLimitCount = beforeIlboInfo.getLimitCount();
		
		// 제한없음-구인처별, 제한없음-현장별 일 때 공제액 계산
		if( resultLimitCount.isUncheckedLimitCount() ) {
			beforeIlboInfo.setLimit_count(resultLimitCount.getValue());
			
			if( isFutureIlbo(beforeIlboInfo) ) {
				throw new FutureIlboException(beforeIlboInfo.getIlbo_worker_name() + "구직자의 " + beforeIlboInfo.getIlbo_date() + " 출역은 사회보험 공제 시스템으로 인해 제한됩니다.");
			}
			
			//보험기간 일보목록
			List<IlboDTO> deductibleIlboList = getDeductibleIlboList(beforeIlboInfo);
			IlboDeductibleDTO ilboDeductibleDTO;
			IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
			
			if( deductibleIlboList.size() < 8) {
				ilboDeductibleDTO = calculateEightDaysUnderDeductible(beforeIlboInfo);
				
				ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				ilboDeductionDTO.setDeduction_status("0");
			}else if( deductibleIlboList.size() > 8 ){
				ilboDeductibleDTO = calculateEightDaysOverDeductible(beforeIlboInfo);
				
				ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				ilboDeductionDTO.setDeduction_status("1");
			}else {
				ilboDeductibleDTO = calculateEightDaysDeductible(deductibleIlboList, "1");
				ilboDeductibleDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				
				String[] sel_ilbo_seq = deductibleIlboList.stream()
						.filter(item -> "0".equals(item.getDeduction_status()) || item.getDeduction_status() == null)
						.map(i -> i.getIlbo_seq())
						.toArray(String[]::new);
				ilboDeductionDTO.setSel_ilbo_seq(sel_ilbo_seq);
				ilboDeductionDTO.setDeduction_status("1");
			}
			
			ilboDeductionDTO.setReg_admin(ilboDTO.getReg_admin());
			ilboDeductibleDTO.setReg_admin(ilboDTO.getReg_admin());
			
			ilboMapper.insertDeductible(ilboDeductibleDTO);
			ilboMapper.insertIlboDeduction(ilboDeductionDTO);
			
			return ilboDeductibleDTO.getDeducatible_sum();
		}else { // 구인처별, 현장별 제한일 때 근로소득세, 지방세, 고용보험 계산
			// 공제 상태 DTO
			IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
			ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
			ilboDeductionDTO.setDeduction_status("0");
			ilboDeductionDTO.setReg_admin(ilboDTO.getReg_admin());
			ilboMapper.insertIlboDeduction(ilboDeductionDTO);
			
			// 공제 금액 DTO
			IlboDeductibleDTO ilboDeductibleDTO = calculateEightDaysUnderDeductible(beforeIlboInfo);
			ilboDeductibleDTO.setReg_admin(ilboDTO.getReg_admin());
			ilboMapper.insertDeductible(ilboDeductibleDTO);
			
			return ilboDeductibleDTO.getDeducatible_sum();
		}
	}
	
	private boolean isFutureIlbo(IlboDTO ilboInfo) {
		IlboDTO checkIlbo = SerializationUtils.clone(ilboInfo);
		// TODO :: 제한 3 - 미래 출역에 공제액이 계산되었다면 해당 출역은 출발, 도착, 완료로 바꿀수 없음.
		// 미래 출역 - 해당 출역일로부터 다음 달 말일까지 
		// 1. 공제액 계산 된 미래 출역이 있는지 구하기
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(ilboInfo.getIlbo_date().split("-")[0]), Integer.parseInt(ilboInfo.getIlbo_date().split("-")[1]), 1);
		int endDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Integer.parseInt(ilboInfo.getIlbo_date().split("-")[0]), Integer.parseInt(ilboInfo.getIlbo_date().split("-")[1]), endDate);
		
		checkIlbo.setStart_ilbo_date(ilboInfo.getIlbo_date());
		checkIlbo.setEnd_ilbo_date(df.format(cal.getTime()).toString());
		
		// 해당 출역을 포함한 미래 출역
		List<IlboDTO> futureIlbo = replicationIlboMapper.selectAgoLastIlbo(checkIlbo);
		
		// size > 1 이면 미래 출역이 있다는 뜻
		if(futureIlbo.size() > 1) {
			return true;
		}
		
		return false;
	};
	
	private List<IlboDTO> getDeductibleIlboList(IlboDTO ilboInfo) throws Exception {
		// TODO Auto-generated method stub
		IlboDTO checkIlboDTO = SerializationUtils.clone(ilboInfo);
		
		// 전달 일 수
		checkIlboDTO.setAgo_count("1");
		int agoIlboCount = replicationIlboMapper.selectAgoIlboCount(checkIlboDTO);
		if( agoIlboCount == 0 ) {
			// 최초 기준일 ~ 한 달까지 구하기
			checkIlboDTO.setStart_ilbo_date(getCurrentMonthFirstIlboDate(checkIlboDTO));
			String addOneMonthDate = DateUtil.addStringMonth(1, checkIlboDTO.getStart_ilbo_date());
			checkIlboDTO.setEnd_ilbo_date(DateUtil.addStringDay(-1, addOneMonthDate, "yyyy-MM-dd"));
			
			return replicationIlboMapper.selectAgoLastIlbo(checkIlboDTO);
		}

		// 두달 전 일수
		checkIlboDTO.setAgo_count("2");
		int twoMonthAgoIlboCnt = replicationIlboMapper.selectAgoIlboCount(checkIlboDTO);
		if( twoMonthAgoIlboCnt == 0 ) {
			// 전달 최초 근로일 구하기
			IlboDTO infoDTO = SerializationUtils.clone(checkIlboDTO);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			
			String[] ilboDateArr = checkIlboDTO.getIlbo_date().split("-");
			int ilboDateYear = Integer.parseInt(ilboDateArr[0]);
			int ilboDateMonth = Integer.parseInt(ilboDateArr[1])-1;
			calendar.set(ilboDateYear, ilboDateMonth - 1, 1);
			
			String firstOfLastMonth = df.format(calendar.getTime()).toString();
			infoDTO.setIlbo_date(firstOfLastMonth);
			
			// 최초 근로일
			String firstDate = getCurrentMonthFirstIlboDate(infoDTO);
			String lastDate = DateUtil.addStringDay(-1, DateUtil.addStringMonth(1, firstDate), "yyyy-MM-dd");
			
			// 최초 근로일 ~ +해당 달 안에 해당 근로가 포함되어 있는가??
			int ilboDateBool = com.ilgajaComm.util.DateUtil.dateCompareTo(firstDate, ilboInfo.getIlbo_date());
			int ilboDateBool2 = com.ilgajaComm.util.DateUtil.dateCompareTo(ilboInfo.getIlbo_date(), lastDate);
			
			if(ilboDateBool == -1 && ilboDateBool2 == -1) {
				IlboDTO lastIlboDTO = SerializationUtils.clone(checkIlboDTO);
				lastIlboDTO.setStart_ilbo_date(firstDate);
				lastIlboDTO.setEnd_ilbo_date(lastDate);
				lastIlboDTO.setIlbo_seq(ilboInfo.getIlbo_seq());

				return replicationIlboMapper.selectAgoLastIlbo(lastIlboDTO);
			}
		}
		
		checkIlboDTO.setIlbo_date( DateUtil.getLastDay(checkIlboDTO.getIlbo_date(), "yyyy-MM-dd") );
		return replicationIlboMapper.selectAgoIlbo(checkIlboDTO);
	}
	
	private String getCurrentMonthFirstIlboDate(IlboDTO ilboInfo) {
		// 해당 달 최초 근로일 구하기
		IlboDTO parmaDTO = SerializationUtils.clone(ilboInfo);
		parmaDTO.setStart_ilbo_date(ilboInfo.getIlbo_date());
		parmaDTO.setEnd_ilbo_date(ilboInfo.getIlbo_date());
		
		IlboDTO firstIlbo = replicationIlboMapper.selectFirstIlbo(parmaDTO);
		if( firstIlbo != null ) {
			return firstIlbo.getIlbo_date();
		}
		
		return ilboInfo.getIlbo_date();
	}

	private IlboDeductibleDTO calculateEightDaysUnderDeductible(IlboDTO ilboInfo) {
		// TODO Auto-generated method stub
		IlboDeductibleDTO ilboDeductibleDTO = new IlboDeductibleDTO();
		
		// 세금 및 사회보험 요율 가져오기
		TaxRateDTO taxRateDTO = replicationTaxRateMapper.selectApplyTaxRate(ilboInfo); 
		TaxCalc taxCalc = new TaxCalc(Integer.parseInt(taxRateDTO.getDeduction_price()), taxRateDTO.getIncome_tax_rate(), taxRateDTO.getEmployer_insurance_rate(), taxRateDTO.getNational_pension_rate(), taxRateDTO.getHealth_insurance_rate(), taxRateDTO.getCare_insurance_rate());
		TaxPriceDTO taxPriceDTO = taxCalc.getTaxPriceDTO(Integer.parseInt(ilboInfo.getIlbo_pay()));
		System.out.println(taxPriceDTO);
		ilboDeductibleDTO.setIlbo_seq(ilboInfo.getIlbo_seq());
		ilboDeductibleDTO.setIncome_tax_price(taxPriceDTO.getIncomeTaxPrice() + "");
		ilboDeductibleDTO.setLocal_tax_price(taxPriceDTO.getLocalTaxPrice() + "");
		
		if(ilboInfo.isEnableEmployerInsurance()) {
			ilboDeductibleDTO.setEmployer_insurance_price(taxPriceDTO.getEmployerInsurancePrice() + "");
		}else { 
			ilboDeductibleDTO.setEmployer_insurance_price("0");
		}
		ilboDeductibleDTO.setNational_pension_price("0");
		ilboDeductibleDTO.setHealth_insurance_price("0");
		ilboDeductibleDTO.setCare_insurance_price("0");

		return ilboDeductibleDTO;
	}
	
	/**
	  * @작성일 : 2023. 8. 4.
	  * @작성자 : Na GilJin 
	  * @Method 설명 : caculateFlag == 1이면 1~8일 중 아직 공제되지 않은 보험만 계산
	  *  			 , caculateFlag != 1이면 1~8일의 모든 보험 계산 (해당경우 caculateFlag:-1 사용함.)
	  */
	private IlboDeductibleDTO calculateEightDaysDeductible(List<IlboDTO> ilboList, String caculateFlag) {
		// TODO Auto-generated method stub
		int totalIncome = 0;
		int totalLocal = 0;
		int totalEmployer = 0;
		int totalNational = 0;
		int totalHealth = 0;
		int totalCare = 0;
		
		for(int i = 0; i < ilboList.size(); i++) {
			IlboDTO item = ilboList.get(i);
			if( caculateFlag.equals(item.getDeduction_status()) ) {
				continue;
			}
			
			// 각 일보마다 세액 계산하기
			TaxRateDTO taxRateDTO = replicationTaxRateMapper.selectApplyTaxRate(item);
			TaxCalc taxCalc = new TaxCalc(Integer.parseInt(taxRateDTO.getDeduction_price()), taxRateDTO.getIncome_tax_rate(), taxRateDTO.getEmployer_insurance_rate(), taxRateDTO.getNational_pension_rate(), taxRateDTO.getHealth_insurance_rate(), taxRateDTO.getCare_insurance_rate());
			TaxPriceDTO taxPriceDTO = taxCalc.getTaxPriceDTO(Integer.parseInt(item.getIlbo_pay()));
			
			totalIncome = taxPriceDTO.getIncomeTaxPrice();
			totalLocal = taxPriceDTO.getLocalTaxPrice();
			
			if(item.isEnableEmployerInsurance()) {
				totalEmployer = taxPriceDTO.getEmployerInsurancePrice();
			}
			if(item.isEnableHealthInsuranceAndNationalPension()) {
				totalNational += taxPriceDTO.getNationalPensionPrice();
				totalHealth += taxPriceDTO.getHealthInsurancePrice();
				totalCare += taxPriceDTO.getCareInsurancePrice();
			}
		}
		
		IlboDeductibleDTO ilboDeductibleDTO = new IlboDeductibleDTO();
		ilboDeductibleDTO.setIncome_tax_price(totalIncome + "");
		ilboDeductibleDTO.setLocal_tax_price(totalLocal + "");
		ilboDeductibleDTO.setEmployer_insurance_price(totalEmployer + "");
		ilboDeductibleDTO.setNational_pension_price(totalNational + "");
		ilboDeductibleDTO.setHealth_insurance_price(totalHealth + "");
		ilboDeductibleDTO.setCare_insurance_price(totalCare + "");
		
		return ilboDeductibleDTO;
	}

	private IlboDeductibleDTO calculateEightDaysOverDeductible(IlboDTO ilboInfo) {
		// TODO Auto-generated method stub
		IlboDeductibleDTO ilboDeductibleDTO = new IlboDeductibleDTO();
		
		TaxRateDTO taxRateDTO = replicationTaxRateMapper.selectApplyTaxRate(ilboInfo); 
		TaxCalc taxCalc = new TaxCalc(Integer.parseInt(taxRateDTO.getDeduction_price()), taxRateDTO.getIncome_tax_rate(), taxRateDTO.getEmployer_insurance_rate(), taxRateDTO.getNational_pension_rate(), taxRateDTO.getHealth_insurance_rate(), taxRateDTO.getCare_insurance_rate());
		TaxPriceDTO taxPriceDTO = taxCalc.getTaxPriceDTO(Integer.parseInt(ilboInfo.getIlbo_pay()));
		
		ilboDeductibleDTO.setIlbo_seq(ilboInfo.getIlbo_seq());
		ilboDeductibleDTO.setIncome_tax_price(taxPriceDTO.getIncomeTaxPrice() + "");
		ilboDeductibleDTO.setLocal_tax_price(taxPriceDTO.getLocalTaxPrice() + "");
		
		if(ilboInfo.isEnableEmployerInsurance()) {
			ilboDeductibleDTO.setEmployer_insurance_price(taxPriceDTO.getEmployerInsurancePrice() + "");
		}else { 
			ilboDeductibleDTO.setEmployer_insurance_price("0");
		}
		if(ilboInfo.isEnableHealthInsuranceAndNationalPension()) {
			ilboDeductibleDTO.setNational_pension_price(taxPriceDTO.getNationalPensionPrice() + "");
			ilboDeductibleDTO.setHealth_insurance_price(taxPriceDTO.getHealthInsurancePrice() + "");
			ilboDeductibleDTO.setCare_insurance_price(taxPriceDTO.getCareInsurancePrice() + "");
		}else {
			ilboDeductibleDTO.setNational_pension_price("0");
			ilboDeductibleDTO.setHealth_insurance_price("0");
			ilboDeductibleDTO.setCare_insurance_price("0");
		}
		
		return ilboDeductibleDTO;
	}

	@Override
	public boolean isDeletePermisson(IlboDTO ilboInfo) throws Exception {
		// TODO Auto-generated method stub
		
		IlboDTO checkIlboDTO = SerializationUtils.clone(ilboInfo);
		checkIlboDTO.setLimit_count(ilboInfo.getLimitCount().getValue());
		List<IlboDTO> deductibleIlboList = getDeductibleIlboList(checkIlboDTO);
		
		boolean isFrontIlboListCancel = isCancellationPossible(deductibleIlboList, ilboInfo);
		
		// 최초근로일과 삭제하려고 하는 일의 월 비교
		String firstIlboDateMonth = deductibleIlboList.get(0).getIlbo_date().split("-")[1];
		String selectIlboDateMonth = checkIlboDTO.getIlbo_date().split("-")[1];
		if(firstIlboDateMonth.equals(selectIlboDateMonth)) {
			return isFrontIlboListCancel;
		}
		
		//삭제하려는 일의 해당 월에 일 목록
		checkIlboDTO.setIlbo_date( DateUtil.getLastDay(checkIlboDTO.getIlbo_date(), "yyyy-MM-dd") );
		List<IlboDTO> deleteMonthIlboList = replicationIlboMapper.selectAgoIlbo(checkIlboDTO);
		boolean isBackIlboListCancel = isCancellationPossible(deleteMonthIlboList, ilboInfo);
		
		return isFrontIlboListCancel && isBackIlboListCancel;
	}
	
	private int indexOfIlbo(List<IlboDTO> ilboList, IlboDTO ilboInfo) {
		for(int i=0; i<ilboList.size(); i++) {
			if( ilboList.get(i).getIlbo_seq().equals(ilboInfo.getIlbo_seq()) ) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean isCancellationPossible(List<IlboDTO> ilboList, IlboDTO ilboInfo) {
		if( ilboList.size() < 8 ) {
			return true;
		}
		
		ilboList.indexOf(ilboInfo);
		int searchIndex = indexOfIlbo(ilboList, ilboInfo);
		if( searchIndex >= 8 ) {
			return true;
		}
		
		long cnt = ilboList.stream()
				.filter(i -> DateUtil.dateCompare(i.getIlbo_date(), ilboInfo.getIlbo_date()) > 0)
				.filter(i -> "1".equals(i.getDeduction_status()))
				.count();
		return cnt==0;
	}

	@Override
	public void cancelInsurance(IlboDTO ilboDTO, String regAdmin) throws Exception {
		// TODO Auto-generated method stub
		IlboDTO checkIlboDTO = SerializationUtils.clone(ilboDTO);
		checkIlboDTO.setLimit_count(ilboDTO.getLimitCount().getValue());
		List<IlboDTO> deductibleIlboList = getDeductibleIlboList(checkIlboDTO);
		
		int selectIlboIndex = indexOfIlbo(deductibleIlboList, checkIlboDTO);
		if( selectIlboIndex == 7 ) {
			IlboDTO paramDTO = deductibleIlboList.stream().findFirst().orElse(null);
			paramDTO.setLimit_count(checkIlboDTO.getLimitCount().getValue());
			paramDTO.setEmployer_seq(checkIlboDTO.getEmployer_seq());
			paramDTO.setWork_seq(checkIlboDTO.getWork_seq());
			paramDTO.setWorker_seq(checkIlboDTO.getWorker_seq());
			
			List<IlboDTO> duplicateDeductibleIlboList = getDeductibleIlboList(paramDTO).stream()
					.filter(i -> "1".equals(i.getDeduction_status()))
					.collect(Collectors.toList());
			String[] selIlboSeqs = getCancelIlboSeqList(duplicateDeductibleIlboList, deductibleIlboList);
			
			cancelDeductedInsurancesInBatch(selIlboSeqs, regAdmin);
		}else if( selectIlboIndex > 7 ) {
			IlboDeductionDTO deductionDTO = new IlboDeductionDTO();
			deductionDTO.setDeduction_status("0");
			deductionDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
			deductionDTO.setReg_admin(regAdmin);
			
			ilboMapper.insertIlboDeduction(deductionDTO);
		}
		
		IlboDTO paramDTO = new IlboDTO();
		paramDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
		paramDTO.setDeductible_sum("0");
		paramDTO.setMod_admin(regAdmin);
		ilboMapper.setIlboInfo(paramDTO);
	}
	
	private String[] getCancelIlboSeqList(List<IlboDTO> duplicateList, List<IlboDTO> deductibleList) {
		if( duplicateList.size() < 8 ) {
			return deductibleList.stream()
					.limit(8)
					.map(i -> i.getIlbo_seq())
					.toArray(String[]::new);
		}
		
		IlboDTO duplicateFirstIlbo = duplicateList.get(0);
		if( deductibleList.get(0).matchIlboSeq(duplicateFirstIlbo.getIlbo_seq()) ) {
			return deductibleList.stream()
					.limit(8)
					.map(i -> i.getIlbo_seq())
					.toArray(String[]::new);
		}
		
		return deductibleList.stream()
				.filter(i -> DateUtil.dateCompare(i.getIlbo_date(), duplicateList.get(duplicateList.size()-1).getIlbo_date()) > 0)
				.map(i -> i.getIlbo_seq())
				.toArray(String[]::new);
		
	}
	
	//
	private void cancelDeductedInsurancesInBatch(String[] ilboSeqs, String regAdmin) {
		if( ilboSeqs.length == 0 ) {
			return;
		}
		
		IlboDeductionDTO deductionDTO = new IlboDeductionDTO();
		deductionDTO.setDeduction_status("0");
		deductionDTO.setSel_ilbo_seq(ilboSeqs);
		deductionDTO.setReg_admin(regAdmin);
		
		ilboMapper.insertIlboDeduction(deductionDTO);
	}

	@Override
	public String updateInsurance(IlboDTO ilboDTO) throws Exception {
		// TODO Auto-generated method stub
		IlboDTO ilboParam = new IlboDTO();
		ilboParam.setIlbo_seq(ilboDTO.getIlbo_seq());
		IlboDTO beforeIlboInfo = replicationIlboMapper.selectIlboInfo(ilboParam);
		
		// 공제액이 계산되어있는 것만
		if( Integer.parseInt(beforeIlboInfo.getDeductible_sum()) == 0 ) {
			return null;
		}
		
		// 해당 현장, 구인처의 출역일수 제한 확인 =============================================
		LimitCount resultLimitCount = beforeIlboInfo.getLimitCount();
		
		// 제한없음-구인처별, 제한없음-현장별 일 때 공제액 계산
		if( resultLimitCount.isUncheckedLimitCount() ) {
			beforeIlboInfo.setLimit_count(resultLimitCount.getValue());
			
			//보험기간 일보목록
			List<IlboDTO> deductibleIlboList = getDeductibleIlboList(beforeIlboInfo);
			IlboDeductibleDTO ilboDeductibleDTO;
			//IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
			System.out.println("==========>" + deductibleIlboList.size() + "일차");
			if( deductibleIlboList.size() < 8) {
				ilboDeductibleDTO = calculateEightDaysUnderDeductible(beforeIlboInfo);
				
				//ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				//ilboDeductionDTO.setDeduction_status("0");
			}else if( deductibleIlboList.size() > 8 ){
				ilboDeductibleDTO = calculateEightDaysOverDeductible(beforeIlboInfo);
				
				//ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				//ilboDeductionDTO.setDeduction_status("1");
			}else {
				ilboDeductibleDTO = calculateEightDaysDeductible(deductibleIlboList, "-1");
				ilboDeductibleDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
				
				//String[] sel_ilbo_seq = deductibleIlboList.stream()
						//.filter(item -> "0".equals(item.getDeduction_status()) || item.getDeduction_status() == null)
						//.map(i -> i.getIlbo_seq())
						//.toArray(String[]::new);
				//ilboDeductionDTO.setSel_ilbo_seq(sel_ilbo_seq);
				//ilboDeductionDTO.setDeduction_status("1");
			}
			
			//ilboDeductionDTO.setReg_admin(ilboDTO.getReg_admin());
			ilboDeductibleDTO.setReg_admin(ilboDTO.getReg_admin());
			
			ilboMapper.insertDeductible(ilboDeductibleDTO);
			//ilboMapper.insertIlboDeduction(ilboDeductionDTO);
			
			return ilboDeductibleDTO.getDeducatible_sum();
		}else { // 구인처별, 현장별 제한일 때 근로소득세, 지방세, 고용보험 계산
			// 공제 상태 DTO
			//IlboDeductionDTO ilboDeductionDTO = new IlboDeductionDTO();
			//ilboDeductionDTO.setIlbo_seq(beforeIlboInfo.getIlbo_seq());
			//ilboDeductionDTO.setDeduction_status("0");
			//ilboDeductionDTO.setReg_admin(ilboDTO.getReg_admin());
			//ilboMapper.insertIlboDeduction(ilboDeductionDTO);
			
			// 공제 금액 DTO
			IlboDeductibleDTO ilboDeductibleDTO = calculateEightDaysUnderDeductible(beforeIlboInfo);
			ilboDeductibleDTO.setReg_admin(ilboDTO.getReg_admin());
			ilboMapper.insertDeductible(ilboDeductibleDTO);
			
			return ilboDeductibleDTO.getDeducatible_sum();
		}
	}
}
