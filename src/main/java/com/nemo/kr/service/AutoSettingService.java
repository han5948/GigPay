package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.AutoSettingDTO;

public interface AutoSettingService {
	public List<AutoSettingDTO> selectAutoSettingList();
	
	public void insertAutoSetting(List<AutoSettingDTO> autoSettingList);
}	
