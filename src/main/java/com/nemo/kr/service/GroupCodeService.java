package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.GroupCodeDTO;

/**
  * @FileName : GroupCodeService.java

  * @Project : ilgaja

  * @Date : 2020. 6. 16. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface GroupCodeService {
	//그룹 코드 리스트 카운트
	public int selectListCnt(GroupCodeDTO groupCodeDTO);
	//그룹 코드 리스트
	public List<GroupCodeDTO> selectList(GroupCodeDTO groupCodeDTO);
	//그룹 코드 추가
	public void insertGroupCode(GroupCodeDTO groupCodeDTO);
	//그룹 코드 상세
	public GroupCodeDTO selectInfo(GroupCodeDTO groupCodeDTO);
	//그룹 코드 수정
	public void updateGroupCode(GroupCodeDTO groupCodeDTO);
	//그룹 코드 삭제
	public void deleteGroupCode(GroupCodeDTO groupCodeDTO);
}
