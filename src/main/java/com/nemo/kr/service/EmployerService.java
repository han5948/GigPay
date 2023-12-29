package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.nemo.kr.dto.EmployerDTO;


/**
 * 일가자 구인처 관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
public interface EmployerService {
	public int getEmployerTotalCnt(EmployerDTO employerDTO);

	public List<EmployerDTO> getEmployerList(EmployerDTO employerDTO);

	public EmployerDTO getEmployerView(EmployerDTO employerDTO);

	public void setEmployerCell(EmployerDTO employerDTO);                         // Insert

	public void setEmployerInfo(EmployerDTO employerDTO);                         // Update

	public void removeEmployerInfo(EmployerDTO employerDTO);                      // Delete

	public int chkWorkerPhone(EmployerDTO employerDTO);

	public  List<EmployerDTO> chkEmployerNum(EmployerDTO employerDTO);

	public Map<String, String> employerLoginProc(HttpServletRequest request, EmployerDTO eDTO);	//구인처용에서 로그인 처리

	public EmployerDTO selectEmployerInfo(EmployerDTO employerDTO);

	public List<EmployerDTO> selectInList(EmployerDTO employerDTO);

	public JSONObject setEmployerMerge(EmployerDTO employerDTO);
	
	public int isContainEmployerName(String employerName);
}
