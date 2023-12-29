package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AdDTO;




public interface AdMapper {
	public int selectAdListCnt(AdDTO adDTO);
	
	public List<AdDTO> selectAdList(AdDTO adDTO);
}
