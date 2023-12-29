package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.AdminLogDTO;

public interface ReplicationAdminMapper {

	public AdminDTO selectInfo(AdminDTO adminDTO);
	
	public List<AdminDTO> selectList(AdminDTO adminDTO);
	
	public List<AdminDTO> selectCounselorList(AdminDTO adminDTO);

	public int getAdminTotalCnt(AdminDTO adminDTO);

	public List<AdminDTO> getAdminList(AdminDTO adminDTO);

	public AdminLogDTO selectIlgajaMenu(AdminLogDTO adminLogDTO);

}
