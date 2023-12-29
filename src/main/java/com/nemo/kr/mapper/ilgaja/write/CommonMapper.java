package com.nemo.kr.mapper.ilgaja.write;

import java.util.HashMap;
import java.util.List;

import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.dto.WorkerLandingLogDTO;



public interface CommonMapper {

	public List<CodeDTO> getCommonCodeList(CodeDTO codeDTO);                      // 코드 조회

	public List<CommonDTO> getCompanyNameList(CommonDTO commonDTO);               // 회사 명 조회

	public List<CommonDTO> getEmployerNameList(CommonDTO commonDTO);              // 구인처 명 조회

	public int chkEmployerName(CommonDTO commonDTO);                              // 구인처 명 확인

	public List<WorkerDTO> getWorkerNameList(WorkerDTO workerDTO);                // 구직자 명 조회

	public List<WorkerDTO> getTodayWorkerList(WorkerDTO workerDTO);               // 출근자 명 조회

	public List<WorkDTO> getWorkNameList(WorkDTO workDTO);                        // 현장 명 조회

	public List<WorkerDTO> getManagerNameList(ManagerDTO managerDTO);

	public int chkManagerPhone(CommonDTO commonDTO);

	public List<WorkDTO> getSearchWorkList(WorkDTO workDTO);


	public List<EmployerDTO> getSearchEmployerList(EmployerDTO employerDTO);

	public List<HashMap> selectWorkerKindCount(WorkerDTO workerDTO);
	
	public List<EmployerDTO> selectEmployerList(EmployerDTO employerDTO);
	
	public void insertWorkerLandingLog(WorkerLandingLogDTO workerLandingLogDTO);

	public List<WorkDTO> getWorkNameList3(WorkDTO workDTO);
	
	public List<WorkDTO> getWorkNameList4(WorkDTO workDTO);
	
	public int selectRecommendationCount(CommonDTO commonDTO);
}
