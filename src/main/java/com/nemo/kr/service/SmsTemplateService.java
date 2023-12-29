package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.SmsTemplateDTO;

/**
  * @FileName : SmsTemplateService.java

  * @Project : ilgaja

  * @Date : 2020. 6. 23. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface SmsTemplateService {
	//SMS 템플릿 리스트 카운트
	public int selectListCnt(SmsTemplateDTO smsTemplateDTO);
	//SMS 템플릿 리스트
	public List<SmsTemplateDTO> selectList(SmsTemplateDTO smsTemplateDTO);
	//SMS 템플릿 추가
	public void insertSmsTemplate(SmsTemplateDTO smsTemplateDTO);
	//SMS 템플릿 상세
	public SmsTemplateDTO selectInfo(SmsTemplateDTO smsTemplateDTO);
	//SMS 템플릿 수정
	public void updateSmsTemplate(SmsTemplateDTO smsTemplateDTO);
	//SMS 템플릿 삭제
	public void deleteSmsTemplate(SmsTemplateDTO smsTemplateDTO);
}
