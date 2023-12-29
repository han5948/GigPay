package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.core.MessageMapper;
import com.nemo.kr.dto.CategoryDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;

@MessageMapper("contractMapper")
public interface ContractMapper {
	public int selectCategoryListCnt();
	
	public List<CategoryDTO> selectCategoryList(CategoryDTO categoryDTO);
	
	public void insertCategory(CategoryDTO categoryDTO);
	
	public CategoryDTO selectCategoryInfo(CategoryDTO categoryDTO);
	
	public void updateCategory(CategoryDTO categoryDTO);
	
	public int selectContractListCnt(ContractDTO contractDTO);
	
	public List<ContractFileDTO> selectContractList(ContractDTO contractDTO);
	
	public ContractFileDTO selectContractInfo(ContractDTO contractDTO);
	
	public void insertContract(ContractDTO contractDTO);
	
	public void insertContractFile(ContractFileDTO contractFileDTO);
	
	public void updateContract(ContractDTO contractDTO);
	
	public void updateContractFile(ContractFileDTO contractFileDTO);
	
	public int selectContractUseCnt(ContractDTO contractDTO);
	
	public List<ContractDTO> selectContractDefaultList();
	
	public void initializationDefaultUseYn(ContractDTO contractDTO);
	
	public int selectContractFileListCnt(ContractDTO contractDTO);
}
