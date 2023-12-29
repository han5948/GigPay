package com.nemo.kr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.controller.LoginManager;
import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerLoginDTO;
import com.nemo.kr.dto.WmDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationEmployerMapper;
import com.nemo.kr.mapper.ilgaja.write.EmployerMapper;
import com.nemo.kr.mapper.ilgaja.write.EmployerMergeMapper;
import com.nemo.kr.mapper.ilgaja.write.FileMapper;
import com.nemo.kr.service.EmployerService;


/**
 * 일가자 구인처 관리 Service Impl
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired EmployerMapper employerMapper;
	@Autowired ReplicationEmployerMapper replicationEmployerMapper;
	@Autowired FileMapper fileMapper;
	@Autowired EmployerMergeMapper employerMergeMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;
	@Override
	public int getEmployerTotalCnt(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		return replicationEmployerMapper.getEmployerTotalCnt(employerDTO);
	}

	@Override
	public List<EmployerDTO> getEmployerList(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		return (List<EmployerDTO>) replicationEmployerMapper.getEmployerList(employerDTO);
	}

	@Override
	public EmployerDTO getEmployerView(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		return replicationEmployerMapper.getEmployerView(employerDTO);
	}

	@Override
	@Transactional
	public void setEmployerCell(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		employerMapper.setEmployerCell(employerDTO);
	}

	@Override
	@Transactional
	public void setEmployerInfo(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		employerMapper.setEmployerInfo(employerDTO);
	}

	@Override
	@Transactional
	public void removeEmployerInfo(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		employerMapper.removeEmployerInfo(employerDTO);
	}

	@Override
	public int chkWorkerPhone(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub

		return replicationEmployerMapper.chkWorkerPhone(employerDTO);
	}

	@Override
	public  List<EmployerDTO> chkEmployerNum(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub
		return replicationEmployerMapper.chkEmployerNum(employerDTO);
	}


	/* 
	 * 구인처 용에서 로그인 처리
	 */
	@Override
	@Transactional
	public Map<String, String> employerLoginProc(HttpServletRequest request, EmployerDTO eDTO) {

		

		Map resultMap = new HashMap();
		resultMap.put("code", "0000");

		
		EmployerDTO resultDTO = replicationEmployerMapper.selectEmployerInfo(eDTO);

		if ( resultDTO == null ) {
			resultMap.put("code", Const.CODE_USER_0001);
			resultMap.put("message", Const.MSG_USER_0001);
			return resultMap;
		}
		
		employerMapper.updateEmployerVisit(resultDTO);	    

		resultDTO.setLogin_type(eDTO.getLogin_type());
		setSession(request, resultDTO);

		
		return resultMap;

	}
	
	public void setSession(HttpServletRequest request, EmployerDTO paramDTO){

		ManagerLoginDTO sessionDTO = new ManagerLoginDTO();
		sessionDTO.setSeq(paramDTO.getEmployer_seq());
		sessionDTO.setEmployer_seq(paramDTO.getEmployer_seq());
		sessionDTO.setLogin_id(paramDTO.getEmployer_num());
		sessionDTO.setLogin_pass(paramDTO.getEmployer_pass());
		sessionDTO.setLogin_type("E");
		sessionDTO.setManager_type("E");
		sessionDTO.setLogin_name(paramDTO.getEmployer_name());
		sessionDTO.setOwner_id(paramDTO.getOwner_id());
		sessionDTO.setCompany_seq(paramDTO.getCompany_seq());
		sessionDTO.setCompany_tel(paramDTO.getCompany_tel());
		sessionDTO.setPartner_tel(paramDTO.getPartner_tel());
		
		
		
		HttpSession session = request.getSession();
		LoginManager loginManager = LoginManager.getInstance();
		
		if ( loginManager.isUsing(sessionDTO.getLogin_id()) ) {
			loginManager.removeSession(sessionDTO.getLogin_id());
			
		}
		session.setAttribute(Const.managerSession, sessionDTO);
		session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	}

	public void updateSession(HttpServletRequest request, EmployerDTO paramDTO){

		ManagerLoginDTO sessionDTO = new ManagerLoginDTO();
		sessionDTO.setSeq(paramDTO.getEmployer_seq());
		sessionDTO.setEmployer_seq(paramDTO.getEmployer_seq());
		sessionDTO.setLogin_id(paramDTO.getEmployer_num());
		sessionDTO.setLogin_pass(paramDTO.getEmployer_pass());
		sessionDTO.setLogin_type("E");
		sessionDTO.setManager_type("E");
		sessionDTO.setLogin_name(paramDTO.getEmployer_name());
		sessionDTO.setOwner_id(paramDTO.getOwner_id());
		sessionDTO.setCompany_seq(paramDTO.getCompany_seq());
		sessionDTO.setCompany_tel(paramDTO.getCompany_tel());
		sessionDTO.setPartner_tel(paramDTO.getPartner_tel());
		
		HttpSession session = request.getSession();
		LoginManager loginManager = LoginManager.getInstance();
		
		loginManager.setSession(session, sessionDTO.getLogin_id());
		session.setAttribute(Const.managerSession, sessionDTO);
		session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	}

	@Override
	public EmployerDTO selectEmployerInfo(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub
		return replicationEmployerMapper.selectEmployerInfo(employerDTO);
	}

	@Override
	public List<EmployerDTO> selectInList(EmployerDTO employerDTO) {
		// TODO Auto-generated method stub
		return replicationEmployerMapper.selectInList(employerDTO);
	}

	/**
	 * 구인처 병합
	 */
	//@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public JSONObject setEmployerMerge(EmployerDTO employerDTO) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		JSONObject jsonObj = new JSONObject();
		try {
			
			jsonObj.put("code", "0000");
			jsonObj.put("message", "성공");
		
			//1. source 구인처의 현장을 구해 온다.
			List<WorkDTO> workList = employerMergeMapper.selectWorkList(employerDTO);
			
			for (WorkDTO workDTO : workList) {
				//2. source 구인처의 현장의 현장manager 를 dest 구인처의 현장 매니져로 바꾼다. WM 기존것을 update
				WmDTO wmParam = new WmDTO();
				wmParam.setEmployer_seq(employerDTO.getSource_employer_seq());
				wmParam.setWork_seq(workDTO.getWork_seq());
				
				List<WmDTO> wmList = employerMergeMapper.selectWmList(wmParam);
				
				for (WmDTO wmDTO : wmList ) {
				
					//employer_seq 를 바꾸어서 찾아본다.
					// manager_seq 는 그대로
					wmDTO.setEmployer_seq(employerDTO.getEmployer_seq());
					WmDTO wmInfo = employerMergeMapper.selectWm(wmDTO);
					
					if(wmInfo == null) {//없으면 
						employerMergeMapper.updateWm(wmDTO);
					}else {//바꾼값이 있으면 기존것은 삭제한다.
						employerMergeMapper.deleteWm(wmDTO);
					}
				}
				
				
			}
			
			//3. source 구인처의 manager 를 dest 구인처의 현장 매니져로 바꾼다. WM 기존것을 update
			EmDTO emParam = new EmDTO();
			emParam.setEmployer_seq(employerDTO.getSource_employer_seq());
						
			List<EmDTO> emList = employerMergeMapper.selectEmList(emParam);
			
			for (EmDTO emDTO : emList ) {
			
				//employer_seq 를 값만 바꾸어서 찾아본다.
				//manager seq 는 그대로
				emDTO.setEmployer_seq(employerDTO.getEmployer_seq());
				EmDTO emInfo = employerMergeMapper.selectEm(emDTO);
				
				if(emInfo == null) {//없으면 
					employerMergeMapper.updateEm(emDTO);
				}else {//바꾼값이 있으면 기존것은 삭제한다.
					employerMergeMapper.deleteEm(emDTO);
				}
			}
				
			
			//4. source 구인처의 현장을 dest 구인처의 현장으로 바꾼다.
			employerMergeMapper.updateWorkEmployer(employerDTO);
			
			//3. source 구인처의 현장의 본사매니저를 dest 구인처의 본사매니져로 바꾼다.
			employerMergeMapper.updateManagerEmployer(employerDTO);
			
			//4. 일일대장의 source 구인처를 dest 구인처로 바꾼다.
			employerMergeMapper.updateIlboEmployer(employerDTO);
			
			//5. source 구인처  를 삭제 한다.
			employerMergeMapper.deleteEmployer(employerDTO);
			
			
			transactionManager.commit(status);
		} catch (Exception e) {
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
	public int isContainEmployerName(String employerName) {
		// TODO Auto-generated method stub
		List<String> employerNames = new ArrayList<String>();
		employerNames.add(employerName);
		
		if(employerName.indexOf("이앤씨") > -1) {
			employerNames.add(employerName.replaceAll("이앤씨", "이엔씨"));
			employerNames.add(employerName.replaceAll("이앤씨", "E&C"));
		}
		if(employerName.indexOf("이엔씨") > -1){
			employerNames.add(employerName.replaceAll("이엔씨", "이앤씨"));
			employerNames.add(employerName.replaceAll("이엔씨", "E&C"));
		}
		//대소문자 구별안하도록
		String upperCaseEmployerName = employerName.toUpperCase();
		if(upperCaseEmployerName.indexOf("E&C") > -1){
			employerNames.add(upperCaseEmployerName.replaceAll("E&C", "이앤씨"));
			employerNames.add(upperCaseEmployerName.replaceAll("E&C", "이엔씨"));
		}
		if(upperCaseEmployerName.indexOf("ENC") > -1) {
			employerNames.add(upperCaseEmployerName.replaceAll("ENC", "이앤씨"));
			employerNames.add(upperCaseEmployerName.replaceAll("ENC", "이엔씨"));
		}
		
		if(employerName.indexOf("이앤지") > -1) {
			employerNames.add(employerName.replaceAll("이앤지", "이엔지"));
			employerNames.add(employerName.replaceAll("이앤지", "ENG"));
		}
		if(employerName.indexOf("이엔지") > -1){
			employerNames.add(employerName.replaceAll("이엔지", "이앤지"));
			employerNames.add(employerName.replaceAll("이엔지", "ENG"));
		}
		//대소문자 구별안하도록
		if(upperCaseEmployerName.indexOf("ENG") > -1){
			employerNames.add(upperCaseEmployerName.replaceAll("ENG", "이앤지"));
			employerNames.add(upperCaseEmployerName.replaceAll("ENG", "이엔지"));
		}
		
		String changeEmployerName = StringUtil.removeCompanySuffix(employerName);
		if( !employerName.equals(changeEmployerName) ) {
			employerNames.add(changeEmployerName);
		}
		
		return Math.min(replicationEmployerMapper.selectEmployerNameCount(employerNames), 1);
	}
}
