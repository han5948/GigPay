package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.GroupCodeDTO;

/**

  * @FileName : GroupCodeMapper.java

  * @Project : ilgaja

  * @Date : 2020. 6. 16. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface GroupCodeMapper {
	public int selectListCnt(GroupCodeDTO groupCodeDTO);
	
	public List<GroupCodeDTO> selectList(GroupCodeDTO groupCodeDTO);
	
	public void insertGroupCode(GroupCodeDTO groupCodeDTO);
	
	public GroupCodeDTO selectInfo(GroupCodeDTO groupCodeDTO);
	
	public void updateGroupCode(GroupCodeDTO groupCodeDTO);
	
	public void deleteGroupCode(GroupCodeDTO groupCodeDTO);
}
