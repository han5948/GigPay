package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.IlboDTO;

public interface IlboExpireService {

	public List<IlboDTO> selectList(IlboDTO ilboDTO);

	public int getIlboExpireTotalCnt(IlboDTO ilboDTO);

	public String selectEncryptPhone(IlboDTO ilboDTO);
}
