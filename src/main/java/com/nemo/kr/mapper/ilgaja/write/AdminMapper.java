package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.AdminLogDTO;




public interface AdminMapper {

	/**	
	 * 관리자 정보 조회
	 * @author nemo
	 *	2016. 2. 24.
	 * @param adminDTO
	 * @return AdminDTO
	 *
	 */

	public AdminDTO selectInfo(AdminDTO adminDTO);
	
	public List<AdminDTO> selectList(AdminDTO adminDTO);
	
	public List<AdminDTO> selectCounselorList(AdminDTO adminDTO);

	public void updateInfo(AdminDTO adminDTO);
	
	public int getAdminTotalCnt(AdminDTO adminDTO);

	public List<AdminDTO> getAdminList(AdminDTO adminDTO);

	public void setAdminCell(AdminDTO adminDTO);                           // Insert

	public void removeAdminInfo(AdminDTO adminDTO);                        // delete

	public void insertIlgajaLog(AdminLogDTO adminLogDTO);

	public AdminLogDTO selectIlgajaMenu(AdminLogDTO adminLogDTO);

}
