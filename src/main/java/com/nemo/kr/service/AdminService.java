package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.AdminLogDTO;


/**
 * 관리자 Service
 * @author nemo
 *
 */

public interface AdminService {



	/**
	 * 관리자 로그인 프로세스
	 * @author nemo
	 *	2016. 2. 24.
	 * @param request
	 * @param adminDTO
	 * @return
	 * @throws Exception AdminDTO
	 *
	 */
	public Map adminLoginProc(HttpServletRequest request, AdminDTO adminDTO) throws Exception ;

	public  List<AdminDTO> selectList(AdminDTO adminDTO);

	public AdminDTO selectInfo(AdminDTO adminDTO);

	public void updateInfo(AdminDTO adminDTO);

	public List<AdminDTO> selectCounselorList(AdminDTO adminDTO);

	public Map<String, String> getAuthNum(AdminDTO adminDTO) throws Exception;


	public int getAdminTotalCnt(AdminDTO adminDTO);

	public List<AdminDTO> getAdminList(AdminDTO adminDTO);

	public void setAdminCell(AdminDTO adminDTO);                           // Insert

	public void removeAdminInfo(AdminDTO adminDTO);                        // delete

	
	public int selectListCnt(AdminDTO adminDTO);

	public int duplicationCheckAdminId(AdminDTO adminDTO);

	public void insertInfo(AdminDTO adminDTO);

	public void insertIlgajaLog(AdminLogDTO adminLogDTO);

}	
