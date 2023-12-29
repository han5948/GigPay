package com.nemo.kr.mapper.ilgaja.read;

import com.nemo.kr.dto.IlboSettingDTO;

public interface ReplicationIlboSettingMapper {
	
	public IlboSettingDTO selectSettingInfo(IlboSettingDTO ilboSettingDTO);
	
	public IlboSettingDTO selectSettingWidth(IlboSettingDTO ilboSettingDTO);
	
}
