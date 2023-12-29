package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.AdDTO;
import com.nemo.kr.mapper.ilgaja.write.AdMapper;
import com.nemo.kr.service.AdService;

@Service
public class AdServiceImpl implements AdService {
	@Autowired
	AdMapper adMapper;

	@Override
	public int selectAdListCnt(AdDTO adDTO) {
		// TODO Auto-generated method stub
		return adMapper.selectAdListCnt(adDTO);
	}

	@Override
	public List<AdDTO> selectAdList(AdDTO adDTO) {
		// TODO Auto-generated method stub
		return adMapper.selectAdList(adDTO);
	}

	
}
