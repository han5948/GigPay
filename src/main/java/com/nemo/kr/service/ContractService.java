package com.nemo.kr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nemo.kr.dto.CategoryDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.ContractFileDTO;
import com.nemo.kr.dto.FileDTO;

public interface ContractService {
	public int selectCategoryListCnt();
	
	public List<CategoryDTO> selectCategoryList(CategoryDTO categoryDTO);
	
	public void insertCategory(CategoryDTO categoryDTO);
	
	public CategoryDTO selectCategoryInfo(CategoryDTO categoryDTO);
	
	public void updateCategory(CategoryDTO categoryDTO);
	
	public int selectContractListCnt(ContractDTO contractDTO);
	
	public List<ContractFileDTO> selectContractList(ContractDTO contractDTO);
	
	public ContractFileDTO selectContractInfo(ContractDTO contractDTO);
	
	public void insertContract(HttpServletRequest request, ContractDTO contractDTO);
	
	public void updateContract(HttpServletRequest request, ContractDTO contractDTO);
	
	public int selectContractUseCnt(ContractDTO contractDTO);
	
	public ContractDTO selectContractDefaultInfo(ContractDTO contractDTO);
	public FileDTO selectContractFile(ContractDTO contractDTO);
}
