package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.WeatherDTO;
import com.nemo.kr.mapper.ilgaja.write.WeatherMapper;
import com.nemo.kr.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService{

	@Autowired WeatherMapper weatherMapper;
	
	@Override
	public WeatherDTO getWeather(WeatherDTO weatherDTO) {
		return weatherMapper.getWeather(weatherDTO);
	}

}
