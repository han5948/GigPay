package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.AccountListDTO;
import com.nemo.kr.dto.AccountingDTO;
import com.nemo.kr.dto.OrderStatisticsDTO;
import com.nemo.kr.dto.StaticDTO;
import com.nemo.kr.dto.UseStatisticsDTO;

/**
 * 일가자 기간 통계
 * 
 * @author  : nemojjang
 * @date    : 2018. 8. 17.
 * @desc    : 
 *
 */
public interface StaticService {

	public List<StaticDTO> getStaticList(StaticDTO staticDTO);
	

	//정산
	public List<AccountingDTO> selectAccountDayList(AccountingDTO accountingDTO);
	
	public List<AccountingDTO> selectCompanyAccountDayList(AccountingDTO accountingDTO);

	public List<AccountingDTO> selectCompanyAccountDayList2(AccountingDTO accountingDTO);
	
	public List<AccountingDTO> selectAccountAll(AccountingDTO accountingDTO);

	public List<AccountingDTO> selectCompanyAccountAll(AccountingDTO accountingDTO);
	
	public List<AccountingDTO> selectCompanyAccountAll2(AccountingDTO accountingDTO);
  
	public List<AccountingDTO> selectSmartList(AccountingDTO accountingDTO);
	
	public List<AccountingDTO> selectSmartList2(AccountingDTO accountingDTO);
	
	public int selectSmartFee(AccountingDTO accountingDTO);
	
	public List<OrderStatisticsDTO> selectOrderStatisticsList(OrderStatisticsDTO orderStatisticsDTO);
	
	public List<OrderStatisticsDTO> selectAllOrderStatisticsList(OrderStatisticsDTO orderStatisticsDTO);
	
	public List<UseStatisticsDTO> selectUseStatisticsList(UseStatisticsDTO useStatisticsDTO);
	
	public List<UseStatisticsDTO> selectAllUseStatisticsList(UseStatisticsDTO useStatisticsDTO);
	
	public List<AccountListDTO> selectAccountingList(AccountingDTO accountingDTO);

	public List<AccountListDTO> getAccountConsultingList(AccountingDTO accountingDTO);

	public List<AccountingDTO> selectAccountConsultingAll(AccountingDTO accountingDTO);

	public List<AccountingDTO> selectAccountConsultingDayList(AccountingDTO accountingDTO);

	public List<AccountingDTO> selectAccountingAdList(AccountingDTO accountingDTO);
	
	public List<AccountingDTO> selectAllCompanyAccountDayList(AccountingDTO accountingDTO);


	public List<AccountListDTO> getCompanyStatic(AccountingDTO accountingDTO);
	
	public List<AccountDTO> getJnpTaxExcel(AccountDTO accountDTO);
}
