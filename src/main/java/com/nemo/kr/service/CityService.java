package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.CityDTO;

public interface CityService {
	public List<CityDTO> getCityList(CityDTO cityDTO);
	
	public int getCityListCnt(CityDTO cityDTO);
	
	public void updateCityDTO(CityDTO cityDTO);
}
