package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.PushReservekDTO;
import com.nemo.kr.mapper.ilgaja.write.PushReserveMapper;
import com.nemo.kr.service.PushReserveService;


/**
 * @author nemojjang
 *
 */
@Service
public class PushReserveServiceImpl implements PushReserveService {
	@Autowired PushReserveMapper pushReserveMapper;

	@Override
	public PushReservekDTO  selectPushReserveInfo(PushReservekDTO pushReservekDTO) {
		// TODO Auto-generated method stub
		return pushReserveMapper.selectPushReserveInfo(pushReservekDTO);
	}
	
	@Override
	@Transactional
	public void updatePushReserveStatus(PushReservekDTO pushReservekDTO) {
		// TODO Auto-generated method stub
		pushReserveMapper.updatePushReserveStatus(pushReservekDTO);
	}
}