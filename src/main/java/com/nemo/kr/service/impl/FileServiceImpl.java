package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.mapper.ilgaja.write.FileMapper;
import com.nemo.kr.service.FileService;
import com.nemo.kr.util.FileUtil;




/**
 * 파일관리 ServiceImpl
 * @author 
 *
 */
@Service
public class FileServiceImpl implements FileService{

	@Autowired FileMapper fileMapper;


	@Override
	public void deleteInfo(FileDTO fileDTO) {
		// TODO Auto-generated method stub
		fileMapper.deleteFile(fileDTO);
	}

	@Override
	public void insertInfo(String regAdmin, List<FileDTO> listFileDTO) {

		// TODO Auto-generated method stub
		for ( FileDTO fileDTO : listFileDTO ) {
			fileDTO.setReg_admin(regAdmin);
			fileMapper.insertUploadFile(fileDTO);
		}
	}
	
	@Override
	public void insertInfo(FileDTO fileDTO) {
		fileMapper.insertUploadFile(fileDTO);
	}

	@Override
	public void editInfo(String regAdmin, List<FileDTO> listFileDTO) {

		// TODO Auto-generated method stub
		for ( FileDTO fileDTO : listFileDTO ) {
			
			
			List<FileDTO> fileList= fileMapper.selectList(fileDTO);
			for (FileDTO fDTO : fileList) {
				deleteFile(fDTO);
			}
			
			fileDTO.setReg_admin(regAdmin);
			
			fileMapper.insertUploadFile(fileDTO);
		}
	}

	
	@Override
	public int selectListCnt(FileDTO paramDTO) {
		// TODO Auto-generated method stub
		return fileMapper.selectListCnt(paramDTO);
	}

	@Override
	public List<FileDTO> selectList(FileDTO paramDTO) {
		// TODO Auto-generated method stub
		return fileMapper.selectList(paramDTO);
	}

	@Override
	public FileDTO selectInfo(FileDTO fileDTO) {
		// TODO Auto-generated method stub
		return fileMapper.selectInfo(fileDTO);
	}

	@Override
	public void deleteFile(FileDTO fileDTO) {
		// TODO Auto-generated method stub
		
		try {
			//원본파일 삭제
			String filePath = fileDTO.getFile_path() + fileDTO.getFile_name();
			FileUtil.deleteFile(filePath);
			//썸네일 삭제
			String thumFile = fileDTO.getFile_path() + fileDTO.getFile_thum_name();
			FileUtil.deleteFile(thumFile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		fileMapper.deleteFile(fileDTO);
	}

	@Override
	public List<FileDTO> selectFileList(FileDTO paramDTO) {
		// TODO Auto-generated method stub
		return fileMapper.selectFileList(paramDTO);
	}

}