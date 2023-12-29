package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.AccessDTO;


/**
 * 일가자 회사관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
public interface AccessService {

	public int getTotalCnt(AccessDTO accessDTO);

	public List<AccessDTO> getList(AccessDTO accessDTO);

	public void insertInfo(AccessDTO accessDTO);                                     // Insert

	public void updateInfo(AccessDTO accessDTO);                                     // Update

	public void removeInfo(AccessDTO accessDTO);                                  // Delete

	public AccessDTO selectInfo(AccessDTO accessDTO);									// select

}
