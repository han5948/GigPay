package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
import com.nemo.kr.mapper.ilgaja.write.TestMapper;
import com.nemo.kr.service.TestService;


@Service
public class TestServiceImpl implements TestService {

	@Autowired TestMapper testMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public List<TestDTO> getTestList(TestDTO testDTO) {
		// TODO Auto-generated method stub

		return (List<TestDTO>) testMapper.getTestList(testDTO);	
	}

	@Override
	public int getTestTotalCnt(TestDTO testDTO) {
		// TODO Auto-generated method stub


		return testMapper.getTestTotalCnt(testDTO);
	}

	@Override
	public void newInsertTest(TestDTO testDTO) {
		// TODO Auto-generated method stub
		testMapper.newInsertTest(testDTO);
	}

	@Override
	public void updateTest(TestDTO testDTO) {
		// TODO Auto-generated method stub
		testMapper.updateTest(testDTO);
	}

	@Override
	public void deleteTest(TestDTO testDTO) {
		// TODO Auto-generated method stub
		testMapper.deleteTest(testDTO);
	}

	@Override
	public void insertTest(TestDTO testDTO) {
		// TODO Auto-generated method stub
		testMapper.insertTest(testDTO);
	}

	@Override
	public List<NameDTO> getNameList(NameDTO nameDTO) {
		// TODO Auto-generated method stub
		return (List<NameDTO>) testMapper.getNameList(nameDTO);	
	}

	@Override
	public NameDTO selectName(NameDTO nameDTO) {
		// TODO Auto-generated method stub
		return testMapper.selectName(nameDTO);
	}

	@Override
	public TestDTO selectTest(String seq) {
		// TODO Auto-generated method stub
		return testMapper.selectTest(seq);
	}

	@Override
	public List<NameDTO> getSearchName(NameDTO nameDTO) {
		// TODO Auto-generated method stub
		return (List<NameDTO>) testMapper.getSearchName(nameDTO);	
	}

	@Override
	public int testTrans() throws Exception {
		// TODO Auto-generated method stub
		int result = -1;




		TestDTO testDTO = new TestDTO();
		try {			
			testDTO.setSeq("1ww");
			result = testMapper.testTrans(testDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("-8888");
			
		}	
		try {
			testDTO.setSeq("가");
			result = testMapper.testTrans(testDTO);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("-9999");
		} 
		return result;

	}

	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void setManager(ManagerDTO managerDTO) throws Exception{
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		 	
			// 1. 자식 매니져를 모두 찾아온다.
			managerDTO.setParent_company_seq("0");
			managerDTO.setManager_type("O");
			List<ManagerDTO> managerList = testMapper.getManagerList(managerDTO);
			
			int count = 0;
			try {
				for (ManagerDTO childDTO : managerList) {
					// 2. 부모 매니져를 찾아온다.
					ManagerDTO mParamDTO = new ManagerDTO();
					mParamDTO.setManager_phone(childDTO.getManager_phone());
					mParamDTO.setParent_company_seq("0");
					
					ManagerDTO pMangerDTO =new ManagerDTO();
					
						pMangerDTO = testMapper.getManager(mParamDTO);
					
						// 3. 자식 매니저의 work 를 찾아온다.
						WorkDTO paramDTO = new WorkDTO();
						paramDTO.setUse_yn("1");
						paramDTO.setManager_seq(childDTO.getManager_seq());
						
						List<WorkDTO> workList = testMapper.getWorkList(paramDTO);
						
						for (WorkDTO workDTO : workList) {
							//4. 자식 매니저의 work를 부모 매니져 로 교체한다. ( 부모 매니저 순번, 부모내니저 이름, 전화번호 )
							
							workDTO.setManager_seq(pMangerDTO.getManager_seq());
							workDTO.setManager_phone(pMangerDTO.getManager_phone());
							workDTO.setManager_name(pMangerDTO.getManager_name());
							testMapper.updateWorkManger(workDTO);
							
							
						}
						
						//자식 매니져 삭제
						testMapper.deleteManager(childDTO);
					
				}
				
				testMapper.insertRelationWm();
				testMapper.insertRelationEm();
				
				transactionManager.commit(status);
				
			} catch (Exception e) {
				transactionManager.rollback(status);
				// TODO: handle exception
				e.printStackTrace();
				throw e;
				
				
			}
			
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void setManagerSeq() throws Exception {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		 	
		List<EmployerDTO> employerList = testMapper.getEmployerList();
		
		try {
			for (EmployerDTO employerDTO : employerList) {
				employerDTO = testMapper.getFirstManagerSeq(employerDTO);
				
				testMapper.updateEmployerManagerSeq(employerDTO);
			}
			transactionManager.commit(status);
			
		} catch (Exception e) {
			transactionManager.rollback(status);
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateJobName() throws Exception {
		// TODO Auto-generated method stub
		List<WorkerDTO> workerList = testMapper.getWorkerList();
		
		JobDTO jobDTO = new JobDTO();
		WorkerDTO workerDTO = new WorkerDTO();
		String jobName = "";
		
		for(int i = 0; i < workerList.size(); i++) {
			jobName = "";
			String kind = workerList.get(i).getWorker_job_code();
			
			if(kind != null && !kind.equals("")) {
				String[] kindArray = kind.split(",");
				jobDTO.setJob_seq_arr(kindArray);
				
				JobDTO resultDTO = testMapper.getJobList(jobDTO);
				
				jobName = resultDTO.getJob_name();
			}
			
			workerDTO = workerList.get(i);
			workerDTO.setWorker_job_name(jobName);
			
			testMapper.updateJobName(workerDTO);
		}
	}

	@Override
	public List<WorkDTO> selectWorkList() throws Exception {
		// TODO Auto-generated method stub
		return testMapper.selectWorkList();
	}

	@Override
	public int updateNewAddr(WorkDTO workDTO) throws Exception {
		// TODO Auto-generated method stub
		return testMapper.updateNewAddr(workDTO);
	}

	@Override
	public List<IlboDTO> selectIlboList() throws Exception {
		// TODO Auto-generated method stub
		return testMapper.selectIlboList();
	}

	@Override
	public int updateNewAddrIlbo(IlboDTO ilboDTO) throws Exception {
		// TODO Auto-generated method stub
		return testMapper.updateNewAddrIlbo(ilboDTO);
	}

	@Override
	public List<WorkerDTO> selectWorkerList() throws Exception {
		// TODO Auto-generated method stub
		return testMapper.selectWorkerList();
	}

	@Override
	public void updateWorkerSex(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		testMapper.updateWorkerSex(workerDTO);
	}

	@Override
	public List<CompanyDTO> selectCompanyList() {
		// TODO Auto-generated method stub
		return testMapper.selectCompanyList();
	}

	@Override
	public AccountDTO selectBalance(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return testMapper.selectLastBalance(accountDTO);
	}

	@Override
	public List<AccountDTO> selectAccountList(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return testMapper.selectAccountList(accountDTO);
	}

	@Override
	public void updateAccount(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		testMapper.updateAccount(accountDTO);
	}

	@Override
	public List<WorkerDTO> findDeleteWorker() {
		// TODO Auto-generated method stub
		return testMapper.findDeleteWorker();
	}

	@Override
	public List<FileDTO> selectFileList(FileDTO param) {
		// TODO Auto-generated method stub
		return testMapper.selectFileList(param);
	}

	@Override
	public void deleteFileInfo(FileDTO fileDTO) {
		// TODO Auto-generated method stub
		testMapper.deleteFileInfo(fileDTO);
	}

}
