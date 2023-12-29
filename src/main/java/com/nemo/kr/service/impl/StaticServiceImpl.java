package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.AccountListDTO;
import com.nemo.kr.dto.AccountingDTO;
import com.nemo.kr.dto.OrderStatisticsDTO;
import com.nemo.kr.dto.StaticDTO;
import com.nemo.kr.dto.UseStatisticsDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationStaticMapper;
import com.nemo.kr.service.StaticService;



@Service
public class StaticServiceImpl implements StaticService {

	@Autowired ReplicationStaticMapper replicationStaticMapper;

	@Override
	public List<StaticDTO> getStaticList(StaticDTO staticDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.getStaticList(staticDTO);
	}


	@Override
	public List<AccountingDTO> selectAccountDayList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectAccountDayList(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectAccountAll(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectAccountAll(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectCompanyAccountDayList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectCompanyAccountDayList(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectCompanyAccountAll(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectCompanyAccountAll(accountingDTO);
	}
	
	@Override
	public List<AccountingDTO> selectCompanyAccountAll2(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectCompanyAccountAll2(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectCompanyAccountDayList2(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectCompanyAccountDayList2(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectSmartList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectSmartList(accountingDTO);
	}

	@Override
	public int selectSmartFee(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectSmartFee(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectSmartList2(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return (List<AccountingDTO>) replicationStaticMapper.selectSmartList2(accountingDTO);
	}

	@Override
	public List<OrderStatisticsDTO> selectOrderStatisticsList(OrderStatisticsDTO orderStatisticsDTO) {
		return replicationStaticMapper.selectOrderStatisticsList(orderStatisticsDTO);
	}

	@Override
	public List<OrderStatisticsDTO> selectAllOrderStatisticsList(OrderStatisticsDTO orderStatisticsDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAllOrderStatisticsList(orderStatisticsDTO);
	}

	@Override
	public List<UseStatisticsDTO> selectUseStatisticsList(UseStatisticsDTO useStatisticsDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectUseStatisticsList(useStatisticsDTO);
	}

	@Override
	public List<UseStatisticsDTO> selectAllUseStatisticsList(UseStatisticsDTO useStatisticsDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAllUseStatisticsList(useStatisticsDTO);
	}

	@Override
	public List<AccountListDTO> selectAccountingList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAccountingList(accountingDTO);
	}

	@Override
	public List<AccountListDTO> getAccountConsultingList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.getAccountConsultingList(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectAccountConsultingAll(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAccountConsultingAll(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectAccountConsultingDayList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAccountConsultingDayList(accountingDTO);
	}

	@Override
	public List<AccountingDTO> selectAccountingAdList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAccountingAdList(accountingDTO);
	}


	@Override
	public List<AccountingDTO> selectAllCompanyAccountDayList(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.selectAllCompanyAccountDayList(accountingDTO);
	}


	@Override
	public List<AccountListDTO> getCompanyStatic(AccountingDTO accountingDTO) {
		// TODO Auto-generated method stub
		return replicationStaticMapper.getCompanyStatic(accountingDTO);
	}


	@Override
	public List<AccountDTO> getJnpTaxExcel(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		if( accountDTO.getSrh_tax_type().equals("systemFee") ) {
			accountDTO.setAccount_date(accountDTO.getStartDate().replace("-", "") + "25");
			return replicationStaticMapper.selectSystemFeeTaxList(accountDTO);
		}
		
		accountDTO.setAccount_date(accountDTO.getStartDate().replace("-", "") + "28");
		accountDTO.setStartDate(accountDTO.getStartDate() + "-01");
		return replicationStaticMapper.selectRunningRoyaltyTaxList(accountDTO);
	}

}
