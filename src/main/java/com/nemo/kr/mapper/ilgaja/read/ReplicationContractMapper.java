package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.CategoryDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.dto.FileDTO;

public interface ReplicationContractMapper {
	
	public int selectCategoryListCnt();
	
	public List<CategoryDTO> selectCategoryList(CategoryDTO categoryDTO);
	
	public CategoryDTO selectCategoryInfo(CategoryDTO categoryDTO);
	
	public int selectContractListCnt(ContractDTO contractDTO);
	
	public List<ContractFileDTO> selectContractList(ContractDTO contractDTO);
	
	public ContractFileDTO selectContractInfo(ContractDTO contractDTO);
	
	public int selectContractUseCnt(ContractDTO contractDTO);
	
	public List<ContractDTO> selectContractDefaultList();
	
	public ContractDTO selectContractDefaultInfo(ContractDTO contractDTO);
	public FileDTO selectContractFile(ContractDTO contractDTO);
}
