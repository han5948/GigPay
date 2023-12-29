package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.TestDTO;




/**
 * API 서비스
 * @author nemojjang
 *
 */
public interface ApiService {

	
	
	/**
	 * 일반 관리자 목록 조회 
	 * @return List<AdminDTO>
	 */
	public List<TestDTO>  getTestList(TestDTO testDTO);
	
	
	
}	
