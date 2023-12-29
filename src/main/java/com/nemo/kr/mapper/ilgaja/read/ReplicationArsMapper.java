package com.nemo.kr.mapper.ilgaja.read;

import com.nemo.kr.dto.ArsDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;

public interface ReplicationArsMapper {

	public WorkDTO getWork(String uSERCID);

	public IlboDTO getMilbo(String uSERCID);

	public IlboDTO getWilbo(String uSERCID);

	public ManagerDTO getManager(String uSERCID);

	public WorkerDTO getWorker(String uSERCID);

	public CompanyDTO getComapny(String companySeq);

	public ArsDTO getSelectInfoLimitOne(ArsDTO arsDTO);

}
