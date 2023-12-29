package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.domain.administrativeDistrict.Sido;
import com.nemo.kr.mapper.ilgaja.read.ReplicationRoadNameMapper;
import com.nemo.kr.service.RoadNameService;

@Service
public class RoadNameServiceImpl implements RoadNameService {

	@Autowired ReplicationRoadNameMapper replRoadNameMapper;
	
	@Override
	public List<Sido> getAdministrativeDistrictList() {
		// TODO Auto-generated method stub
		return replRoadNameMapper.selectAdministrativeDistrictList();
	}

}
