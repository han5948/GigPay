package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.AdminLogDTO;



public interface AdminLogMapper {

	public void insertAdminLog(AdminLogDTO adminLogDTO);	//로그 쌓기
}
