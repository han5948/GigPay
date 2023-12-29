package com.nemo.kr.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.LaborContractDTO;
import com.nemo.kr.dto.WmDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationWorkMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkMergeMapper;
import com.nemo.kr.service.WorkService;


/**
 * 일가자 현장 관리 Service Impl
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
@Service
public class WorkServiceImpl implements WorkService {
	@Autowired WorkMapper workMapper;
	@Autowired ReplicationWorkMapper replicationWorkMapper;
	@Autowired WorkMergeMapper workMergeMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public int getWorkTotalCnt(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		return replicationWorkMapper.getWorkTotalCnt(workDTO);
	}

	@Override
	public List<WorkDTO> getWorkList(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		return (List<WorkDTO>) replicationWorkMapper.getWorkList(workDTO);
	}

	@Override
	@Transactional
	public void setWorkCell(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		workMapper.setWorkCell(workDTO);
	}

	@Override
	@Transactional
	public void setWorkInfo(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		workMapper.setWorkInfo(workDTO);
	}
	
	@Override
	@Transactional
	public void removeWorkInfo(WorkDTO workDTO) {
		// TODO Auto-generated method stub

		workMapper.removeWorkInfo(workDTO);
	}

	@Override
	public WorkDTO getSelectInfo(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return replicationWorkMapper.getSelectInfo(workDTO);
	}

	@Override
	public WorkDTO selectWorkInfo(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return replicationWorkMapper.selectWorkInfo(workDTO);
	}

	@Override
	public List<WorkDTO> selectInList(WorkDTO workDTO) {
		// TODO Auto-generated method stub
		return replicationWorkMapper.selectInList(workDTO);
	}

	//@Transactional
	@Override
	public JSONObject setWorkMerge(WorkDTO workDTO, HttpSession session) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("code", "0000");
			jsonObj.put("message", "성공");
			
			String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
			
			// 현장 정보 가져오기
			WorkDTO destWorkDTO = new WorkDTO();
			
			destWorkDTO.setWork_seq(workDTO.getWork_seq());
	
			destWorkDTO = replicationWorkMapper.getSelectInfo(destWorkDTO);
		
		
			// 1. Ilbo의 현장 정보 변경
			destWorkDTO.setSource_work_seq(workDTO.getSource_work_seq());
			destWorkDTO.setMod_admin(admin_id);
			
			workMergeMapper.updateIlboWork(destWorkDTO);
			
			// 2. Work delete
			workDTO.setMod_admin(admin_id);
			
			workMergeMapper.deleteWork(workDTO);
			
			// 3. 병합되는 wm List 가져오기
			List<WmDTO> wmList = workMergeMapper.selectMergeWmList(workDTO);

			WmDTO wmDTO = new WmDTO();
			
			// 4. 하나씩 update
			for(int i = 0; i < wmList.size(); i++) {
				wmDTO = wmList.get(i);
				wmDTO.setWork_seq(workDTO.getWork_seq());
				wmDTO.setMod_admin(admin_id);
				
				WmDTO resultDTO = workMergeMapper.selectWm(wmDTO);
				
				if(resultDTO == null) {
					workMergeMapper.updateWmWork(wmDTO);
				}else {
					workMergeMapper.updateWmUse(wmDTO);
				}
			}
			
			transactionManager.commit(status);
		}catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(status);
			e.printStackTrace();
			
			jsonObj.put("code", "9999");
			jsonObj.put("message", "병합에 실패 하였습니다.");
		}
		
		def = null;
		status = null;
		
		return jsonObj;
	}

	@Override
	@Transactional
	public void insertLaborContractLog(LaborContractDTO laborContractDTO) {
		// TODO Auto-generated method stub
		workMapper.insertLaborContractLog(laborContractDTO);
	}
}
