/**
 * 
 */
package com.nemo.kr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.NameDTO;
import com.nemo.kr.dto.TestDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;

/**
 * @author nemojjang
 *
 */
@Service
@Transactional
public interface TestService {
	/**
	 * 테스트 리스트
	 * @return List<TestDTO>
	 */
	public List<TestDTO> getTestList(TestDTO testDTO);

	public int getTestTotalCnt(TestDTO testDTO);
	
	
	public void newInsertTest(TestDTO testDTO);

	public void updateTest(TestDTO testDTO);

	public void deleteTest(TestDTO testDTO);

	public void insertTest(TestDTO testDTO);

	
	
	public List<NameDTO> getNameList(NameDTO nameDTO);

	public NameDTO selectName(NameDTO nameDTO);

	public TestDTO selectTest(String seq);

	public List<NameDTO> getSearchName(NameDTO nameDTO);

	@Transactional
	public int testTrans() throws Exception;

	public void setManager(ManagerDTO managerDTO) throws Exception;
	
	public void setManagerSeq() throws Exception;

	public void updateJobName() throws Exception;
	
	public List<WorkDTO> selectWorkList() throws Exception;
	
	@Transactional
	public int updateNewAddr(WorkDTO workDTO) throws Exception;
	
	public List<IlboDTO> selectIlboList() throws Exception;
	
	public List<WorkerDTO> selectWorkerList() throws Exception;
	
	public void updateWorkerSex(WorkerDTO workerDTO);
	
	@Transactional
	public int updateNewAddrIlbo(IlboDTO ilboDTO) throws Exception;
	
	public List<CompanyDTO> selectCompanyList();
	
	public AccountDTO selectBalance(AccountDTO accountDTO);
	
	public List<AccountDTO> selectAccountList(AccountDTO accountDTO);
	
	public void updateAccount(AccountDTO accountDTO);
	
	public List<WorkerDTO> findDeleteWorker();
	
	public List<FileDTO> selectFileList(FileDTO param);
	
	public void deleteFileInfo(FileDTO fileDTO);
}
