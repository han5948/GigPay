package com.nemo.kr.service;

import com.nemo.kr.dto.CareerDTO;

public interface CareerService {
	public CareerDTO selectCareerOption();
	
	public void insertCareer(CareerDTO careerDTO);
	
	public void updateCareer(CareerDTO careerDTO);
}	
