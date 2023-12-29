package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.SmsService;


/**
 * 게시판 관리 ServiceImpl
 * @date 2018. 7. 5.
 * @desc  
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

	@Autowired	SmsMapper smsMapper;
	//@Resource(name = "smsMapper")	private SmsMapper smsMapper;

	@Override
	public int selectListCnt(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		return smsMapper.selectListCnt(paramDTO);
	}

	@Override
	public List<SmsDTO> selectList(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		return smsMapper.selectList(paramDTO);
	}

	@Override
	public void insertInfo(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		smsMapper.insertInfo(paramDTO);
	}
	
	@Override
	public void insertMmsInfo(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		smsMapper.insertMmsInfo(paramDTO);
	}

	@Override
	public SmsDTO selectInfo(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		return smsMapper.selectInfo(paramDTO);
	}

	@Override
	public void updateInfo(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		smsMapper.updateInfo(paramDTO);
	}

	@Override
	public void deleteInfo(SmsDTO paramDTO) {
		// TODO Auto-generated method stub
		smsMapper.deleteInfo(paramDTO);
	}

	@Override
	public int selectSmsListCnt(SmsDTO smsDTO) {
		// TODO Auto-generated method stub
		return smsMapper.selectSmsListCnt(smsDTO);
	}

	@Override
	public List<SmsDTO> selectSmsList(SmsDTO smsDTO) {
		// TODO Auto-generated method stub
		return smsMapper.selectSmsList(smsDTO);
	}

	@Override
	public void sendSMS(SmsDTO smsDTO) {
		// TODO Auto-generated method stub
		int byteLength = smsDTO.getTr_msg().getBytes().length;
		
		if(byteLength > 90)
			smsMapper.insertMmsInfo(smsDTO);
		else 
			smsMapper.insertInfo(smsDTO);
		
	}


}