package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IlboSettingDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationIlboSettingMapper;
import com.nemo.kr.mapper.ilgaja.write.IlboSettingMapper;
import com.nemo.kr.service.IlboSettingService;

/**

  * @FileName : IlboSettingServiceImpl.java

  * @Project : ilgaja

  * @Date : 2020. 9. 7. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
@Service
public class IlboSettingServiceImpl implements IlboSettingService {
	@Autowired 
	IlboSettingMapper ilboSettingMapper;
	@Autowired
	ReplicationIlboSettingMapper replicationIlboSettingMapper;

	@Override
	public IlboSettingDTO selectSettingInfo(IlboSettingDTO ilboSettingDTO) {
		// TODO Auto-generated method stub
		return replicationIlboSettingMapper.selectSettingInfo(ilboSettingDTO);
	}

	@Override
	public void updateIlboSetting(IlboSettingDTO ilboSettingDTO) {
		// TODO Auto-generated method stub
		ilboSettingMapper.updateIlboSetting(ilboSettingDTO);
	}

	@Override
	public void insertIlboSetting(IlboSettingDTO ilboSettingDTO) {
		// TODO Auto-generated method stub
		ilboSettingMapper.insertIlboSetting(ilboSettingDTO);
	}

	@Override
	public IlboSettingDTO selectSettingWidth(IlboSettingDTO ilboSettingDTO) {
		// TODO Auto-generated method stub
		return replicationIlboSettingMapper.selectSettingWidth(ilboSettingDTO);
	}
	
	@Override
	public void updateIlboSettingReset(IlboSettingDTO ilboSettingDTO) {
		ilboSettingMapper.updateIlboSettingReset(ilboSettingDTO);
	}
}
