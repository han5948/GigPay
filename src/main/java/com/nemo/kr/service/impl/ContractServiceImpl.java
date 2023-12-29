package com.nemo.kr.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nemo.kr.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nemo.kr.dto.CategoryDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationContractMapper;
import com.nemo.kr.mapper.ilgaja.write.ContractMapper;
import com.nemo.kr.service.ContractService;
import com.nemo.kr.util.ContractFileUtil;


@Service
public class ContractServiceImpl implements ContractService {
	@Autowired
	ContractMapper contractMapper;
	@Autowired
	ReplicationContractMapper replicationContractMapper;
	
	@Resource(name="commonProperties")	  
	private Properties commonProperties;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public int selectCategoryListCnt() {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectCategoryListCnt();
	}
	
	@Override
	public List<CategoryDTO> selectCategoryList(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectCategoryList(categoryDTO);
	}

	@Override
	public void insertCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		contractMapper.insertCategory(categoryDTO);
	}

	@Override
	public CategoryDTO selectCategoryInfo(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectCategoryInfo(categoryDTO);
	}

	@Override
	public void updateCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		String category_seq = categoryDTO.getCategory_seq();
		String mod_admin = categoryDTO.getMod_admin();
		
		if(categoryDTO.getDel_yn().equals("1")) {
			categoryDTO = new CategoryDTO();
			
			categoryDTO.setCategory_seq(category_seq);
			categoryDTO.setMod_admin(mod_admin);
			categoryDTO.setDel_yn("1");
		}
		
		contractMapper.updateCategory(categoryDTO);
	}

	@Override
	public int selectContractListCnt(ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectContractListCnt(contractDTO);
	}
	
	@Override
	public List<ContractFileDTO> selectContractList(ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectContractList(contractDTO);
	}

	@Override
	public ContractFileDTO selectContractInfo(ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectContractInfo(contractDTO);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insertContract(HttpServletRequest request, ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			// 새로 등록하는 양식의 기본값을 사용으로 바꾸면 기존에 사용중인 기본값 양식을 미사용으로 바꿈
			if(contractDTO.getContract_default_use_yn() != null && contractDTO.getContract_default_use_yn().equals("1")) {
				contractMapper.initializationDefaultUseYn(contractDTO);
			}
			
			contractMapper.insertContract(contractDTO);
			
			ContractFileDTO contractFileDTO = ContractFileUtil.fileUploadTagName(
					request
					, commonProperties.getProperty("signUploadPath")
					, commonProperties.getProperty("fileSecretKeyPath")
			);
			
			if(contractFileDTO != null) {
				contractFileDTO.setContract_seq(contractDTO.getContract_seq());
				contractFileDTO.setReg_admin(contractDTO.getReg_admin());
				contractFileDTO.setMod_admin(contractDTO.getMod_admin());
				
				contractMapper.insertContractFile(contractFileDTO);
			}
			
			transactionManager.commit(status);
		}catch(Exception e) {
			transactionManager.rollback(status);
			
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateContract(HttpServletRequest request, ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			String contract_seq = contractDTO.getContract_seq();
			String reg_admin = contractDTO.getReg_admin();
			
			if(contractDTO.getDel_yn().equals("1")) {
				contractDTO = new ContractDTO();
				
				contractDTO.setContract_seq(contract_seq);
				contractDTO.setReg_admin(reg_admin);
				contractDTO.setMod_admin(reg_admin);
				contractDTO.setDel_yn("1");
			}
			
			if(contractDTO.getContract_default_use_yn() != null && contractDTO.getContract_default_use_yn().equals("1")) {
				contractMapper.initializationDefaultUseYn(contractDTO);
			}
			
			contractMapper.updateContract(contractDTO);
			
			ContractFileDTO contractFileDTO = ContractFileUtil.fileUploadTagName(request, commonProperties.getProperty("signUploadPath"), commonProperties.getProperty("fileSecretKeyPath"));
			
			if(contractDTO.getDel_yn().equals("1")) {
				contractFileDTO = new ContractFileDTO();
				
				contractFileDTO.setContract_seq(contract_seq);
				contractFileDTO.setReg_admin(contractDTO.getReg_admin());
				contractFileDTO.setMod_admin(contractDTO.getMod_admin());
				contractFileDTO.setDel_yn("1");
			}else {
				if(contractFileDTO != null) {
					contractFileDTO.setContract_seq(contractDTO.getContract_seq());
					contractFileDTO.setReg_admin(contractDTO.getReg_admin());
					contractFileDTO.setMod_admin(contractDTO.getMod_admin());
					contractFileDTO.setUse_yn(contractDTO.getUse_yn());
				}
			}
			
			int contractFileCnt = contractMapper.selectContractFileListCnt(contractDTO);
			
			if(contractFileCnt > 0) {
				contractMapper.updateContractFile(contractFileDTO);
			}else if(contractFileCnt == 0) {
				contractMapper.insertContractFile(contractFileDTO);
			}
			
			transactionManager.commit(status);
		}catch(Exception e) {
			transactionManager.rollback(status);
			
			e.printStackTrace();
		}
	}

	@Override
	public int selectContractUseCnt(ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectContractUseCnt(contractDTO);
	}

	@Override
	public ContractDTO selectContractDefaultInfo(ContractDTO contractDTO) {
		// TODO Auto-generated method stub
		return replicationContractMapper.selectContractDefaultInfo(contractDTO);
	}
	@Override
	public FileDTO selectContractFile(ContractDTO contractDTO) {
		return replicationContractMapper.selectContractFile(contractDTO);
	}
}
