package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.CareerDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationCareerMapper;
import com.nemo.kr.mapper.ilgaja.write.CareerMapper;
import com.nemo.kr.service.CareerService;

@Service
public class CareerServiceImpl implements CareerService {
	@Autowired CareerMapper careerMapper;
	@Autowired ReplicationCareerMapper replicationCareerMapper;
	
	@Override
	public CareerDTO selectCareerOption() {
		// TODO Auto-generated method stub
		return replicationCareerMapper.selectCareerOption();
	}

	@Override
	public void insertCareer(CareerDTO careerDTO) {
		// TODO Auto-generated method stub
		careerMapper.insertCareer(careerDTO);
	}
	
	@Override
	public void updateCareer(CareerDTO careerDTO) {
		// TODO Auto-generated method stub
		careerMapper.updateCareer(careerDTO);
	}
}
