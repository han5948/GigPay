package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.mapper.ilgaja.write.IlboExpireMapper;
import com.nemo.kr.service.IlboExpireService;

@Service
public class IlboExpireServiceImpl implements IlboExpireService {
	@Autowired IlboExpireMapper ilboExpireMapper;

	@Override
	public List<IlboDTO> selectList(IlboDTO ilboDTO) {
		return ilboExpireMapper.selectList(ilboDTO);
	}

	@Override
	public int getIlboExpireTotalCnt(IlboDTO ilboDTO) {
		return ilboExpireMapper.getIlboExpireTotalCnt(ilboDTO);
	}

	@Override
	public String selectEncryptPhone(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return ilboExpireMapper.selectEncryptPhone(ilboDTO);
	}

}
