package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.IlboDTO;

public interface IlboExpireMapper {

	public List<IlboDTO> selectList(IlboDTO ilboDTO);

	public int getIlboExpireTotalCnt(IlboDTO ilboDTO);

	public String selectEncryptPhone(IlboDTO ilboDTO);
}
