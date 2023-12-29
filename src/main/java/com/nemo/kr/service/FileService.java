package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.FileDTO;




public interface FileService {
	
	public void deleteInfo(FileDTO fileDTO);
	
	public void insertInfo(String regAdmin, List<FileDTO> listFileDTO);
	
	public void insertInfo(FileDTO fileDTO);
	
	public void editInfo(String regAdmin, List<FileDTO> listFileDTO);

	public int selectListCnt(FileDTO paramDTO);
	
	public List<FileDTO> selectList(FileDTO paramDTO);

	public FileDTO selectInfo(FileDTO fileDTO);

	public void deleteFile(FileDTO fileDTO);

	public List<FileDTO> selectFileList(FileDTO paramDTO);


}
