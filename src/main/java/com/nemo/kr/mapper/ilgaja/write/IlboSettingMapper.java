package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.IlboSettingDTO;

/**

  * @FileName : IlboSettingMapper.java

  * @Project : ilgaja

  * @Date : 2020. 9. 7. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface IlboSettingMapper {
	public IlboSettingDTO selectSettingInfo(IlboSettingDTO ilboSettingDTO);
	
	public void updateIlboSetting(IlboSettingDTO ilboSettingDTO);
	
	public void insertIlboSetting(IlboSettingDTO ilboSettingDTO);
	
	public IlboSettingDTO selectSettingWidth(IlboSettingDTO ilboSettingDTO);
	
	public void updateIlboSettingReset(IlboSettingDTO ilboSettingDTO);
}
