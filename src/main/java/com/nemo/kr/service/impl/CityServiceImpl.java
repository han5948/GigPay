package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.CityDTO;
import com.nemo.kr.mapper.ilgaja.write.CityMapper;
import com.nemo.kr.service.CityService;

@Service
public class CityServiceImpl implements CityService{
	
	@Autowired CityMapper cityMapper;
	
	@Override
	public List<CityDTO> getCityList(CityDTO cityDTO) {
		return cityMapper.getCityList(cityDTO);
	}

	@Override
	public int getCityListCnt(CityDTO cityDTO) {
		// TODO Auto-generated method stub
		return cityMapper.getCityListCnt(cityDTO);
	}

	@Override
	public void updateCityDTO(CityDTO cityDTO) {
		// TODO Auto-generated method stub
		cityMapper.updateCityDTO(cityDTO);
	}

}
