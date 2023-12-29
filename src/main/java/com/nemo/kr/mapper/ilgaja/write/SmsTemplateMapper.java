package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.SmsTemplateDTO;

/**

  * @FileName : SmsTemplateMapper.java

  * @Project : ilgaja

  * @Date : 2020. 6. 23. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface SmsTemplateMapper {
	public int selectListCnt(SmsTemplateDTO smsTemplateDTO);
	
	public List<SmsTemplateDTO> selectList(SmsTemplateDTO smsTemplateDTO);
	
	public void insertSmsTemplate(SmsTemplateDTO smsTemplateDTO);
	
	public SmsTemplateDTO selectInfo(SmsTemplateDTO smsTemplateDTO);
	
	public void updateSmsTemplate(SmsTemplateDTO smsTemplateDTO);
	
	public void deleteSmsTemplate(SmsTemplateDTO smsTemplateDTO);
}
