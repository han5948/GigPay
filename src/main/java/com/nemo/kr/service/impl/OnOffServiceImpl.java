package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.OnOffDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationOnOffMapper;
import com.nemo.kr.mapper.ilgaja.write.OnOffMapper;
import com.nemo.kr.service.OnOffService;

@Service
public class OnOffServiceImpl implements OnOffService {

	@Autowired OnOffMapper onOffMapper;
	@Autowired ReplicationOnOffMapper replOnOffMapper;
	
	@Override
	public OnOffDTO getOnOffInfo(OnOffDTO onOffDTO) {
		// TODO Auto-generated method stub
		return replOnOffMapper.selectOnOffInfo(onOffDTO);
	}

	@Override
	public void setOnOffInfo(OnOffDTO onOffDTO) {
		// TODO Auto-generated method stub
		onOffMapper.updateOnOffInfo(onOffDTO);
	}

}
