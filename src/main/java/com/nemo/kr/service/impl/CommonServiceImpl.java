package com.nemo.kr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.CommonDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.SignLogDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.dto.WorkerLandingLogDTO;
import com.nemo.kr.mapper.ilgaja.write.CommonMapper;
import com.nemo.kr.mapper.ilgaja.write.CompanyMapper;
import com.nemo.kr.mapper.ilgaja.write.CompanySignLogMapper;
import com.nemo.kr.mapper.ilgaja.write.EmployerMapper;
import com.nemo.kr.mapper.ilgaja.write.FileMapper;
import com.nemo.kr.mapper.ilgaja.write.LogMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkerMapper;
import com.nemo.kr.service.CommonService;
import com.nemo.kr.util.FileUtil;
//import com.nemo.kr.util.StringUtil;


/**
 * 일가자 공통모듈 Service Impl
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	@Autowired CommonMapper commonMapper;
	@Autowired WorkerMapper workerMapper;
	@Autowired CompanyMapper companyMapper;
	@Autowired FileMapper fileMapper;
	@Autowired EmployerMapper employerMapper;
	@Autowired LogMapper logMapper;
	@Autowired CompanySignLogMapper companySignLogMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<CodeDTO> getCommonCodeList(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		return (List<CodeDTO>) commonMapper.getCommonCodeList(codeDTO);
	}

	@Override
	public List<CommonDTO> getCompanyNameList(CommonDTO commonDTO) {
		// TODO Auto-generated method stub

		return (List<CommonDTO>) commonMapper.getCompanyNameList(commonDTO);
	}

	@Override
	public List<CommonDTO> getEmployerNameList(CommonDTO commonDTO) {
		// TODO Auto-generated method stub

		return (List<CommonDTO>) commonMapper.getEmployerNameList(commonDTO);
	}

	@Override
	public int chkEmployerName(CommonDTO commonDTO) {
		// TODO Auto-generated method stub

		return commonMapper.chkEmployerName(commonDTO);
	}

	@Override
	public List<WorkerDTO> getWorkerNameList(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub

		return (List<WorkerDTO>) commonMapper.getWorkerNameList(workerDTO);
	}

	@Override
	public List<WorkerDTO> getTodayWorkerList(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub

		return (List<WorkerDTO>) commonMapper.getTodayWorkerList(workerDTO);
	}

	@Override
	public List<WorkDTO> getWorkNameList(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		return (List<WorkDTO>) commonMapper.getWorkNameList(workDTO);
	}

	@Override
	public void insertUploadFile(HttpServletRequest request, CommonDTO commonDTO) throws Exception {
		String filePath = commonProperties.getProperty("uploadPath");
		String fileUrl 	= commonProperties.getProperty("fileUrl");

		insertUploadFile(request, commonDTO, filePath, fileUrl);
	}

	@Override
	@Transactional
	public void insertUploadFile(HttpServletRequest request, CommonDTO commonDTO, String filePath, String fileUrl) throws Exception {
		List<FileDTO> listFileDTO = FileUtil.fileUploadTagName(request, filePath, fileUrl, commonProperties.getProperty("fileSecretKeyPath"));

		if ( listFileDTO != null && listFileDTO.size() > 0 ) {
			FileDTO fileDTO =  new FileDTO();

			for ( int i = 0; i < listFileDTO.size(); i++ ) {
				fileDTO = new FileDTO();

				fileDTO.setFile_url(listFileDTO.get(i).getFile_url());
				fileDTO.setFile_path(listFileDTO.get(i).getFile_path());
				fileDTO.setFile_name(listFileDTO.get(i).getFile_name());
				fileDTO.setFile_org_name(listFileDTO.get(i).getFile_org_name());
				fileDTO.setFile_size(listFileDTO.get(i).getFile_size());
				fileDTO.setFile_ext(listFileDTO.get(i).getFile_ext());

				fileDTO.setFile_order(String.valueOf(i));
				fileDTO.setService_type(commonDTO.getService_type());
				fileDTO.setService_code(commonDTO.getService_code());
				fileDTO.setService_seq(commonDTO.getService_seq());
				//System.out.println(fileDTO);

				fileMapper.insertUploadFile(fileDTO);
			}     
		}
	}

	@Override
	@Transactional
	public void uploadFile(HttpServletRequest request, CommonDTO commonDTO, String filePath, String fileUrl) throws Exception {
		String serviceCode = "";

		List<FileDTO> listFileDTO = FileUtil.fileUploadTagName(request, filePath, fileUrl, commonProperties.getProperty("fileSecretKeyPath"));

		if ( listFileDTO != null && listFileDTO.size() > 0 ) {
			FileDTO fileDTO;

			for ( int i = 0; i < listFileDTO.size(); i++ ) {
				fileDTO = new FileDTO();

				serviceCode = StringUtil.isNullToString(listFileDTO.get(i).getFileTagName()).toUpperCase().replace("FILE_", "");

				fileDTO.setFile_url(listFileDTO.get(i).getFile_url());
				fileDTO.setFile_path(listFileDTO.get(i).getFile_path());
				fileDTO.setFile_name(listFileDTO.get(i).getFile_name());
				fileDTO.setFile_org_name(listFileDTO.get(i).getFile_org_name());
				fileDTO.setFile_thum_name(listFileDTO.get(i).getFile_thum_name());
				fileDTO.setFile_size(listFileDTO.get(i).getFile_size());
				fileDTO.setFile_ext(listFileDTO.get(i).getFile_ext());

				fileDTO.setFile_order(String.valueOf(i));
				fileDTO.setService_type(commonDTO.getService_type());
				fileDTO.setService_code(serviceCode);
				fileDTO.setService_seq(commonDTO.getService_seq());

				fileDTO.setStatus("99");

				fileDTO.setReg_admin(commonDTO.getReg_admin());
				fileMapper.insertUploadFile(fileDTO);
				
				if(commonDTO.getService_type().equals("WORKER")){
					WorkerDTO workerDTO = new WorkerDTO();

					workerDTO.setWorker_seq(commonDTO.getService_seq());
					if ( "JUMIN".equals(serviceCode) )        workerDTO.setWorker_jumin_scan_yn("1");
					else if ( "BANK".equals(serviceCode) )  workerDTO.setWorker_bankbook_scan_yn("1");
					else if ( "OSH".equals(serviceCode) )     { 
						workerDTO.setWorker_OSH_scan_yn("1"); 
						workerDTO.setWorker_OSH_yn("1"); 
					}
					else if ( "CERT".equals(serviceCode) )    workerDTO.setWorker_certificate_scan_yn("1");
					else if ( "JOB".equals(serviceCode) )    	workerDTO.setWorker_job_scan_yn("1");
					else if ( "MYPHOTO".equals(serviceCode) )    	workerDTO.setWorker_myphoto_scan_yn("1");
					
					workerDTO.setReg_admin(commonDTO.getReg_admin());

					workerMapper.updateWorkerInfo(workerDTO);

				}else if(commonDTO.getService_type().equals("COMPANY")){
					CompanyDTO companyDTO = new CompanyDTO();

					companyDTO.setCompany_seq(commonDTO.getService_seq());

					if ( "LICENSE".equals(serviceCode) )        companyDTO.setCompany_license_scan_yn("1");
					else if ( "BANK".equals(serviceCode) )    companyDTO.setCompany_bank_scan_yn("1");
					else if ( "CORP".equals(serviceCode) )    companyDTO.setCompany_corp_scan_yn("1");
					else if ( "ETC".equals(serviceCode) )    companyDTO.setCompany_etc_scan_yn("1");
					else if("SIGN".equals(serviceCode)) companyDTO.setCompany_sign_scan_yn("1");

					companyDTO.setReg_admin(commonDTO.getReg_admin());

					if("SIGN".equals(serviceCode)) {
						DefaultTransactionDefinition def = new DefaultTransactionDefinition();
						TransactionStatus status = transactionManager.getTransaction(def);
						
						SignLogDTO signLogDTO = new SignLogDTO();
						FileDTO resultDTO = new FileDTO();
						
						resultDTO.setService_type("COMPANY");
						resultDTO.setService_code("SIGN");
						resultDTO.setService_seq(commonDTO.getService_seq());
						
						resultDTO = fileMapper.selectInfo(resultDTO);
						
						signLogDTO.setFile_seq(resultDTO.getFile_seq());
						signLogDTO.setCompany_seq(commonDTO.getService_seq());
						signLogDTO.setSeq(commonDTO.getAdminSeq());
						signLogDTO.setName(commonDTO.getName());
						signLogDTO.setPhone(commonDTO.getPhone());
						signLogDTO.setType("A");
						signLogDTO.setOs_type("");
						signLogDTO.setIp(request.getRemoteAddr());
						signLogDTO.setUser_agent(request.getHeader("user-agent"));
						signLogDTO.setReg_admin(commonDTO.getReg_admin());
						
						try {
							companySignLogMapper.insertCompanySignLog(signLogDTO);
							companyMapper.setCompanyInfo(companyDTO);
							
							transactionManager.commit(status);
						}catch(Exception e) {
							e.printStackTrace();
							transactionManager.rollback(status);
						}
					}else {
						companyMapper.setCompanyInfo(companyDTO);
					}
				}else if(commonDTO.getService_type().equals("EMPLOYER")){
					EmployerDTO employerDTO = new EmployerDTO();

					employerDTO.setEmployer_seq(commonDTO.getService_seq());

					if ( "CORP".equals(serviceCode) )    employerDTO.setEmployer_corp_scan_yn("1");
					else if ( "ETC".equals(serviceCode) )    employerDTO.setEmployer_etc_scan_yn("1");


					employerDTO.setReg_admin(commonDTO.getReg_admin());

					employerMapper.setEmployerInfo(employerDTO);
				}

			}
		}else{
			System.out.println("파일없음.");
		}
	}

	@Override
	public List<FileDTO> selectFileList(FileDTO fileDTO) throws Exception {
		// TODO Auto-generated method stub

		return (List<FileDTO>) fileMapper.selectFileList(fileDTO);
	}

	@Override
	public List<WorkerDTO> getManagerNameList(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return (List<WorkerDTO>) commonMapper.getManagerNameList(managerDTO);
	}

	@Override
	public int chkManagerPhone(CommonDTO commonDTO) {
		// TODO Auto-generated method stub
		return commonMapper.chkManagerPhone(commonDTO);
	}

	@Override
	public List<CodeLogDTO> getCodeLogList(CodeLogDTO logDTO) {
		// TODO Auto-generated method stub
		return logMapper.getCodeLogList(logDTO);
	}

	@Override
	public List<WorkDTO> getSearchWorkList(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return (List<WorkDTO>) commonMapper.getSearchWorkList(workDTO);
	}

	@Override
	public List<EmployerDTO> getSearchEmployerList(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub
		return (List<EmployerDTO>) commonMapper.getSearchEmployerList(employerDTO);
	}

	@Override
	@Transactional
	public void deleteFile(FileDTO fileDTO) {
		// TODO Auto-generated method stub
		if("WORKER".equals(fileDTO.getService_type())) {
			WorkerDTO workerDTO = new WorkerDTO();

			workerDTO.setWorker_seq(fileDTO.getService_seq());
			String serviceCode = fileDTO.getService_code();
			if ( "MYPHOTO".equals(serviceCode) )        workerDTO.setWorker_myphoto_scan_yn("0");
			if ( "JUMIN".equals(serviceCode) )        workerDTO.setWorker_jumin_scan_yn("0");
			else if ( "BANK".equals(serviceCode) )  workerDTO.setWorker_bankbook_scan_yn("0");
			else if ( "OSH".equals(serviceCode) )     { 
				workerDTO.setWorker_OSH_scan_yn("0"); 
				workerDTO.setWorker_OSH_yn("0"); 
			}
			else if ( "CERT".equals(serviceCode) )    workerDTO.setWorker_certificate_scan_yn("0");
			else if ( "JOB".equals(serviceCode) )    	workerDTO.setWorker_job_scan_yn("0");

			workerDTO.setReg_admin(fileDTO.getReg_admin());

			workerMapper.updateWorkerInfo(workerDTO);
		}

		fileMapper.deleteFile(fileDTO);
	}

	@Override
	public List<HashMap> selectWorkerKindCount(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return (List<HashMap>) commonMapper.selectWorkerKindCount(workerDTO);
	}

	@Override
	public List<EmployerDTO> selectEmployerList(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub
		return commonMapper.selectEmployerList(employerDTO);
	}

	@Override
	public void insertWorkerLandingLog(WorkerLandingLogDTO workerLandingLogDTO) {
		// TODO Auto-generated method stub
		commonMapper.insertWorkerLandingLog(workerLandingLogDTO);
	}

	@Override
	public List<WorkDTO> getWorkNameList3(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return (List<WorkDTO>) commonMapper.getWorkNameList3(workDTO);
	}
	
	@Override
	public List<WorkDTO> getWorkNameList4(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return (List<WorkDTO>) commonMapper.getWorkNameList4(workDTO);
	}

	@Override
	public int selectRecommendationCount(CommonDTO commonDTO) {
		// TODO Auto-generated method stub
		return commonMapper.selectRecommendationCount(commonDTO);
	}

}
