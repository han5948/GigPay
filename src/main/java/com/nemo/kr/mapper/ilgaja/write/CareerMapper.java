package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.CareerDTO;




public interface CareerMapper {
	public CareerDTO selectCareerOption();
	
	public void insertCareer(CareerDTO careerDTO);
	
	public void updateCareer(CareerDTO careerDTO);
}
