package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.GroupCodeDTO;
import com.nemo.kr.mapper.ilgaja.write.GroupCodeMapper;
import com.nemo.kr.service.GroupCodeService;

/**

  * @FileName : GroupCodeServiceImpl.java

  * @Project : ilgaja

  * @Date : 2020. 6. 16. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
@Service
public class GroupCodeServiceImpl implements GroupCodeService {
	@Autowired GroupCodeMapper groupCodeMapper;

	@Override
	public int selectListCnt(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		return groupCodeMapper.selectListCnt(groupCodeDTO);
	}
	
	@Override
	public List<GroupCodeDTO> selectList(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		return groupCodeMapper.selectList(groupCodeDTO);
	}

	@Override
	public void insertGroupCode(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		groupCodeMapper.insertGroupCode(groupCodeDTO);
	}

	@Override
	public GroupCodeDTO selectInfo(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		return groupCodeMapper.selectInfo(groupCodeDTO);
	}

	@Override
	public void updateGroupCode(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		groupCodeMapper.updateGroupCode(groupCodeDTO);
	}

	@Override
	public void deleteGroupCode(GroupCodeDTO groupCodeDTO) {
		// TODO Auto-generated method stub
		groupCodeMapper.deleteGroupCode(groupCodeDTO);
	} 
	
}
