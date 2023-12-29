package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.CityDTO;
import com.nemo.kr.dto.CodeDTO;

public interface CityMapper {
	public List<CityDTO> getCityList(CityDTO cityDTO);
	
	public int getCityListCnt(CityDTO cityDTO);
	
	public void updateCityDTO(CityDTO cityDTO);
}
