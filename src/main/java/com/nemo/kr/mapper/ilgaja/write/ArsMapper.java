package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.ArsDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;




/**
 * ARS Mapper
 * @author NEMODREAM Co., Ltd.
 *
 */
public interface ArsMapper {

	WorkDTO getWork(String uSERCID);

	IlboDTO getMilbo(String uSERCID);

	IlboDTO getWilbo(String uSERCID);

	ManagerDTO getManager(String uSERCID);

	WorkerDTO getWorker(String uSERCID);

	CompanyDTO getComapny(String companySeq);

	void insertInfo(ArsDTO arsDTO);

	ArsDTO getSelectInfoLimitOne(ArsDTO arsDTO);
	

}
