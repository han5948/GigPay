package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.SmsDTO;


/**
 * 게시판 관리 Service
 * @date 2018. 7. 5.
 * @desc 
 *
 */
public interface SmsService {
	
	public int selectListCnt(SmsDTO paramDTO);

	public List<SmsDTO> selectList(SmsDTO paramDTO);

	public void insertInfo(SmsDTO paramDTO);
	
	public void insertMmsInfo(SmsDTO paramDTO);

	public SmsDTO selectInfo(SmsDTO paramDTO);

	public void updateInfo(SmsDTO paramDTO);

	public void deleteInfo(SmsDTO paramDTO);

	public int selectSmsListCnt(SmsDTO smsDTO);
	
	public List<SmsDTO> selectSmsList(SmsDTO smsDTO);
	
	/**
	  * @Method Name : sendSMS
	  * @작성일 : 2021. 3. 25.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : impl 에서 mms 를 보낼지 sms를 보낼지 판단해서 보낸다.
	
	  */
	public void sendSMS(SmsDTO smsDTO);	
}