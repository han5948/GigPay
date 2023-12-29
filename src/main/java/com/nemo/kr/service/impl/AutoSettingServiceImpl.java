package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.AutoSettingDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationAutoSettingMapper;
import com.nemo.kr.mapper.ilgaja.write.AutoSettingMapper;
import com.nemo.kr.service.AutoSettingService;

@Service
public class AutoSettingServiceImpl implements AutoSettingService {
	@Autowired
	ReplicationAutoSettingMapper replicationAutoSettingMapper;
	
	@Autowired
	AutoSettingMapper autoSettingMapper;

	@Override
	public List<AutoSettingDTO> selectAutoSettingList() {
		// TODO Auto-generated method stub
		return replicationAutoSettingMapper.selectAutoSettingList();
	}

	@Override
	public void insertAutoSetting(List<AutoSettingDTO> autoSettinglist) {
		// TODO Auto-generated method stub
		autoSettingMapper.insertAutoSetting(autoSettinglist);
	}

}
