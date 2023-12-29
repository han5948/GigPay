package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.NameDTO;
import com.nemo.kr.dto.TestDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;


/**
 * Test mapper
 * @author nemojjang
 *
 */
public interface TestMapper {
	
	
	
	
	public List<TestDTO> getTestList(TestDTO testDTO) ;

	public int getTestTotalCnt(TestDTO testDTO);
	
	public void newInsertTest(TestDTO testDTO);

	public void updateTest(TestDTO testDTO);

	public void deleteTest(TestDTO testDTO);

	public void insertTest(TestDTO testDTO);

	public List<NameDTO> getNameList(NameDTO nameDTO);

	public NameDTO selectName(NameDTO nameDTO);

	public TestDTO selectTest(String seq);

	public List<NameDTO> getSearchName(NameDTO nameDTO);

	public int testTrans(TestDTO testDTO);

	public List<ManagerDTO> getManagerList(ManagerDTO managerDTO);

	public List<WorkDTO> getWorkList(WorkDTO paramDTO);

	public ManagerDTO getManager(ManagerDTO mParamDTO);

	public void updateWorkManger(WorkDTO workDTO);

	public void deleteManager(ManagerDTO childDTO);

	public void insertRelationWm();

	public void insertRelationEm();
	
	public List<EmployerDTO> getEmployerList();
	
	public EmployerDTO getFirstManagerSeq(EmployerDTO employerDTO);
	
	public void updateEmployerManagerSeq(EmployerDTO employerDTO);
	
	public List<WorkerDTO> getWorkerList();
	
	public JobDTO getJobList(JobDTO jobDTO);
	
	public void updateJobName(WorkerDTO workerDTO);
	
	public List<WorkDTO> selectWorkList();
	
	public int updateNewAddr(WorkDTO workDTO);
	
	public List<IlboDTO> selectIlboList();
	
	public int updateNewAddrIlbo(IlboDTO ilboDTO);
	
	public List<WorkerDTO> selectWorkerList();
	
	public void updateWorkerSex(WorkerDTO workerDTO);
	
public List<CompanyDTO> selectCompanyList();
	
	public AccountDTO selectLastBalance(AccountDTO accountDTO);
	
	public List<AccountDTO> selectAccountList(AccountDTO accountDTO);
	
	public void updateAccount(AccountDTO accountDTO);
	
	public List<WorkerDTO> findDeleteWorker();
	
	public List<FileDTO> selectFileList(FileDTO param);
	
	public void deleteFileInfo(FileDTO fileDTO);
}
