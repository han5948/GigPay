package com.nemo.kr.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.dto.WorkerLandingLogDTO;



/**
 * 일가자 공통관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
public interface CommonService {

	public List<CommonDTO> getCompanyNameList(CommonDTO commonDTO);

	public List<CodeDTO> getCommonCodeList(CodeDTO codeDTO);

	public List<CommonDTO> getEmployerNameList(CommonDTO commonDTO);

	public int chkEmployerName(CommonDTO commonDTO);

	public List<WorkerDTO> getWorkerNameList(WorkerDTO workerDTO);

	public List<WorkerDTO> getTodayWorkerList(WorkerDTO workerDTO);

	public List<WorkDTO> getWorkNameList(WorkDTO workDTO);

	public void insertUploadFile(HttpServletRequest request, CommonDTO commonDTO) throws Exception;

	public void insertUploadFile(HttpServletRequest request, CommonDTO commonDTO, String filePath, String fileUrl) throws Exception;

	public void uploadFile(HttpServletRequest request, CommonDTO commonDTO, String filePath, String fileUrl) throws Exception;

	public List<FileDTO> selectFileList(FileDTO fileDTO) throws Exception;

	public List<WorkerDTO> getManagerNameList(ManagerDTO managerDTO);

	public int chkManagerPhone(CommonDTO commonDTO);

	public List<CodeLogDTO> getCodeLogList(CodeLogDTO logDTO);

	public List<WorkDTO> getSearchWorkList(WorkDTO workDTO);

	public List<EmployerDTO> getSearchEmployerList(EmployerDTO employerDTO);

	public void deleteFile(FileDTO fileDTO);

	public List<HashMap> selectWorkerKindCount(WorkerDTO workerDTO);

	public List<EmployerDTO> selectEmployerList(EmployerDTO employerDTO);
	
	public void insertWorkerLandingLog(WorkerLandingLogDTO workerLandingLogDTO);

	public List<WorkDTO> getWorkNameList3(WorkDTO workDTO);
	
	public List<WorkDTO> getWorkNameList4(WorkDTO workDTO);
	
	public int selectRecommendationCount(CommonDTO commonDTO);
}
