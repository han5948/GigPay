package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.ReservesDTO;

public interface ReservesMapper {
	public AccountDTO selectAccountTotalAcc(AccountDTO accountDTO);
	
	public AccountDTO selectAccountBefore(AccountDTO accountDTO);
	
	public AccountDTO selectAccountTotal(AccountDTO accountDTO);
	
	public List<AccountDTO> selectAccountList(AccountDTO accountDTO);
	
	public List<AccountDTO> selectAccountDetailList(AccountDTO accountDTO);
	
	public List<ReservesDTO> selectReservesTotal(ReservesDTO reservesDTO);
	
	public List<ReservesDTO> selectReservesList(ReservesDTO reservesDTO);
	
	public void insertReserves(ReservesDTO reservesDTO);
	
	public void updateReserves(ReservesDTO reservesDTO);
	
	public void updateReservesMemo(ReservesDTO reservesDTO);
	
	public void insertAccount(AccountDTO accountDTO);
	
	public List<AccountDTO> selectAccountExcelList(AccountDTO accountDTO);
	
	public List<AccountDTO> selectSmartHouseAccountExcelList(AccountDTO accountDTO);
}
