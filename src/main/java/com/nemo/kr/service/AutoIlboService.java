package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.AdDTO;

public interface AutoIlboService {
	public int selectAdListCnt(AdDTO adDTO);
	
	public List<AdDTO> selectAdList(AdDTO adDTO);
}	
