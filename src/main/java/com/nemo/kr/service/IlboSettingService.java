package com.nemo.kr.service;

import com.nemo.kr.dto.IlboSettingDTO;

/**
  * @FileName : IlboSettingService.java

  * @Project : ilgaja

  * @Date : 2020. 9. 7. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface IlboSettingService {
	// 일일대장 설정 화면 정보
	public IlboSettingDTO selectSettingInfo(IlboSettingDTO ilboSettingDTO);
	
	// 일일대장 설정 수정
	public void updateIlboSetting(IlboSettingDTO ilboSettingDTO);
	
	// 일일대장 설정 생성
	public void insertIlboSetting(IlboSettingDTO ilboSettingDTO);
	
	// 일일대장 설정 width
	public IlboSettingDTO selectSettingWidth(IlboSettingDTO ilboSettingDTO);
	
	public void updateIlboSettingReset(IlboSettingDTO ilboSettingDTO);
}
