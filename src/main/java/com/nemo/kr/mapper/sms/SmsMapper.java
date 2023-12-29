package com.nemo.kr.mapper.sms;

import java.util.List;

import com.nemo.kr.core.MessageMapper;
import com.nemo.kr.dto.SmsDTO;


@MessageMapper("smsMapper")
public interface SmsMapper {

	public int selectListCnt(SmsDTO paramDTO);

	public List<SmsDTO> selectList(SmsDTO paramDTO);

	public void insertInfo(SmsDTO paramDTO);
	
	public void insertMmsInfo(SmsDTO paramDTO);

	public SmsDTO selectInfo(SmsDTO paramDTO);

	public void updateInfo(SmsDTO paramDTO);

	public void deleteInfo(SmsDTO paramDTO);

	public int selectSmsListCnt(SmsDTO smsDTO);
	
	public List<SmsDTO> selectSmsList(SmsDTO smsDTO);
}