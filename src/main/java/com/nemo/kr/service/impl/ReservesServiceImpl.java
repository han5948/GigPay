package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.ReservesDTO;
import com.nemo.kr.mapper.ilgaja.write.ReservesMapper;
import com.nemo.kr.service.ReservesService;


@Service
public class ReservesServiceImpl implements ReservesService {
	@Autowired 
	ReservesMapper reservesMapper;
	
	@Override
	public AccountDTO selectAccountTotalAcc(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectAccountTotalAcc(accountDTO);
	}
	
	@Override
	public List<AccountDTO> selectAccountList(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectAccountList(accountDTO);
	}
	
	@Override
	public List<AccountDTO> selectAccountDetailList(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectAccountDetailList(accountDTO);
	}

	@Override
	public List<ReservesDTO> selectReservesList(ReservesDTO reservesDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectReservesList(reservesDTO);
	}

	@Override
	@Transactional
	public void insertReserves(ReservesDTO reservesDTO) {
		// TODO Auto-generated method stub
		reservesMapper.insertReserves(reservesDTO);
	}

	@Override
	@Transactional
	public void updateReserves(ReservesDTO reservesDTO) {
		// TODO Auto-generated method stub
		reservesMapper.updateReserves(reservesDTO);
	}

	@Override
	@Transactional
	public void updateReservesMemo(ReservesDTO reservesDTO) {
		// TODO Auto-generated method stub
		reservesMapper.updateReservesMemo(reservesDTO);
	}

	@Override
	@Transactional
	public void insertAccount(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		reservesMapper.insertAccount(accountDTO);
	}

	@Override
	public AccountDTO selectAccountTotal(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectAccountTotal(accountDTO);
	}

	@Override
	public List<ReservesDTO> selectReservesTotal(ReservesDTO reservesDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectReservesTotal(reservesDTO);
	}

	@Override
	public AccountDTO selectAccountBefore(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return reservesMapper.selectAccountBefore(accountDTO);
	}

	@Override
	public List<AccountDTO> selectAccountExcelList(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		if( accountDTO.getCompany_seq().equals("1") ) {
			return reservesMapper.selectSmartHouseAccountExcelList(accountDTO);
		}
		return reservesMapper.selectAccountExcelList(accountDTO);
	}

}
