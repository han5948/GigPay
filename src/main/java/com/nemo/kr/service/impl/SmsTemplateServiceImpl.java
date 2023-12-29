package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.SmsTemplateDTO;
import com.nemo.kr.mapper.ilgaja.write.SmsTemplateMapper;
import com.nemo.kr.service.SmsTemplateService;

/**

  * @FileName : SmsTemplateServiceImpl.java

  * @Project : ilgaja

  * @Date : 2020. 6. 23. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {
	@Autowired SmsTemplateMapper smsTemplateMapper;

	@Override
	public int selectListCnt(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		return smsTemplateMapper.selectListCnt(smsTemplateDTO);
	}
	
	@Override
	public List<SmsTemplateDTO> selectList(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		return smsTemplateMapper.selectList(smsTemplateDTO);
	}

	@Override
	public void insertSmsTemplate(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		smsTemplateMapper.insertSmsTemplate(smsTemplateDTO);
	}

	@Override
	public SmsTemplateDTO selectInfo(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		return smsTemplateMapper.selectInfo(smsTemplateDTO);
	}

	@Override
	public void updateSmsTemplate(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		smsTemplateMapper.updateSmsTemplate(smsTemplateDTO);
	}

	@Override
	public void deleteSmsTemplate(SmsTemplateDTO smsTemplateDTO) {
		// TODO Auto-generated method stub
		smsTemplateMapper.deleteSmsTemplate(smsTemplateDTO);
	} 
}
