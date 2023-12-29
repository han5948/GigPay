package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AccessDTO;


/**
 * 일가자 코드 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
public interface AccessMapper {
	public int getTotalCnt(AccessDTO accessDTO);

	public List<AccessDTO> getList(AccessDTO accessDTO);

	public void insertInfo(AccessDTO accessDTO);                                     // Insert

	public void updateInfo(AccessDTO accessDTO);                                     // Update

	public void removeInfo(AccessDTO accessDTO);                                  // delete

	public AccessDTO selectInfo(AccessDTO accessDTO);
}
