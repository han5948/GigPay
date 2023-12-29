package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.FileDTO;

/**
 * 첨부파일 정보 Mapper
 * @author nemo
 *
 */
public interface FileMapper {
  /**
   * 첨부파일 정보 등록
   * @param fileDTO
   */
	public void insertUploadFile(FileDTO fileDTO);

  /**
   * 첨부파일 목록 조회
   * @param fileDTO
   * @return
   */
	public List<FileDTO> selectFileList(FileDTO fileDTO);

  /**
   * 파일삭제
   * @param fileDTO
   */
	public void deleteFile(FileDTO fileDTO);

	public List<FileDTO> selectList(FileDTO fileDTO);

	public int selectListCnt(FileDTO paramDTO);

	public FileDTO selectInfo(FileDTO fileDTO);
}
