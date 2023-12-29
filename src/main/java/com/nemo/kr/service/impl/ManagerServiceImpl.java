package com.nemo.kr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.controller.LoginManager;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.ManagerLoginDTO;
import com.nemo.kr.dto.ManagerWorkDTO;
import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationEmployerMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationManagerMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationWorkMapper;
import com.nemo.kr.mapper.ilgaja.write.EmployerMapper;
import com.nemo.kr.mapper.ilgaja.write.IlboMapper;
import com.nemo.kr.mapper.ilgaja.write.ManagerMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkMapper;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.util.CommonUtil;
import com.nemo.kr.util.NotificationUtil;
//import com.nemo.kr.util.StringUtil;


/**
 * 관리자 서비스 구현체
 *
 */
@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired ManagerMapper managerMapper;
	@Autowired ReplicationManagerMapper replicationManagerMapper;
	@Autowired WorkMapper workMapper;
	@Autowired ReplicationWorkMapper replicationWorkMapper;
	@Autowired IlboMapper ilboMapper;
	@Autowired EmployerMapper employerMapper;
	@Autowired ReplicationEmployerMapper replicationEmployerMapper;
	@Autowired SmsMapper smsMapper;
	@Autowired RestTemplate restTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);
	
	@Override
	public Map managerLoginProc(HttpServletRequest request, ManagerDTO managerDTO) throws Exception {
		// TODO Auto-generated method stub
	
		Map resultMap = new HashMap();
		resultMap.put("code", "0000");

		ManagerDTO resultDTO = replicationManagerMapper.selectManagerInfo(managerDTO);

		if ( resultDTO == null ) {
			resultMap.put("code", Const.CODE_USER_0001);
			resultMap.put("message", Const.MSG_USER_0001);
			return resultMap;
		}
	
		resultDTO.setVisit_type(managerDTO.getVisit_type());
		//session 설정
		managerMapper.updateManagerVisit(resultDTO);
			
		setSession(request, resultDTO);
	
		return resultMap;
	}
	
	public void setSession(HttpServletRequest request, ManagerDTO paramDTO){
		ManagerLoginDTO sessionDTO = new ManagerLoginDTO();
		sessionDTO.setSeq(paramDTO.getManager_seq());
		sessionDTO.setLogin_id(paramDTO.getManager_phone());
		sessionDTO.setLogin_pass(paramDTO.getManager_pass());
		sessionDTO.setLogin_type("M");
		sessionDTO.setLogin_name(paramDTO.getManager_name());
		sessionDTO.setManager_name(paramDTO.getManager_name());
		sessionDTO.setManager_phone(paramDTO.getManager_phone());
		sessionDTO.setManager_push_yn(paramDTO.getManager_push_yn());
		sessionDTO.setOwner_id(paramDTO.getOwner_id());
		sessionDTO.setCompany_seq(paramDTO.getCompany_seq());
		sessionDTO.setCompany_tel(paramDTO.getCompany_tel());
		sessionDTO.setPartner_tel(paramDTO.getPartner_tel());
		sessionDTO.setEmployer_seq(paramDTO.getEmployer_seq());
		sessionDTO.setManager_type(paramDTO.getManager_type());
			
		HttpSession session = request.getSession();
		LoginManager loginManager = LoginManager.getInstance();
		
		if ( loginManager.isUsing(sessionDTO.getLogin_id()) ) {
			loginManager.removeSession(sessionDTO.getLogin_id());
		}
		
		session.setAttribute(Const.managerSession, sessionDTO);
		session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	}
	
	public void updateSession(HttpServletRequest request, ManagerDTO paramDTO){
		ManagerLoginDTO sessionDTO = new ManagerLoginDTO();
			
		sessionDTO.setSeq(paramDTO.getManager_seq());
		sessionDTO.setLogin_id(paramDTO.getManager_phone());
		sessionDTO.setLogin_pass(paramDTO.getManager_pass());
		sessionDTO.setLogin_type("M");
		sessionDTO.setLogin_name(paramDTO.getManager_name());
		sessionDTO.setManager_name(paramDTO.getManager_name());
		sessionDTO.setManager_phone(paramDTO.getManager_phone());
		sessionDTO.setManager_push_yn(paramDTO.getManager_push_yn());
		sessionDTO.setOwner_id(paramDTO.getOwner_id());
		sessionDTO.setCompany_seq(paramDTO.getCompany_seq());
		sessionDTO.setCompany_tel(paramDTO.getCompany_tel());
		sessionDTO.setPartner_tel(paramDTO.getPartner_tel());
		sessionDTO.setEmployer_seq(paramDTO.getEmployer_seq());
		sessionDTO.setManager_type(paramDTO.getManager_type());
			
		HttpSession session = request.getSession();
		LoginManager loginManager = LoginManager.getInstance();
			
		loginManager.setSession(session, sessionDTO.getLogin_id());
		session.setAttribute(Const.managerSession, sessionDTO);
		session.removeAttribute("__rsaPrivateKeyManager__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	}
	
		/* 
		 * 인증번호 보내기
		 */
	@Override
	public String getAuthNum(ManagerDTO managerDTO) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String authNum = "";
		
		authNum = CommonUtil.getOrderNum(4);

		//sms 보내기=====================================
		String message = "[일가자] 회원 인증을 위한 인증번호 : " + authNum;
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setTr_msg(message);
		smsDTO.setTr_etc1("2");	//메니져 회원인증
		smsDTO.setTr_phone(managerDTO.getManager_phone());
		smsMapper.insertInfo(smsDTO);
			
		ManagerDTO result = replicationManagerMapper.selectManagerInfo(managerDTO);
		if ( result != null ) {
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_seq(result.getManager_seq());
			paramDTO.setAuth_num(authNum);
			
			managerMapper.setManagerInfo(paramDTO);
		}
		
		return authNum;
	}
	
	@Override
	public ManagerDTO selectManagerInfo(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.selectManagerInfo(managerDTO);
	}
	
	@Override
	public void setManagerInfo(ManagerDTO paramDTO) {
		managerMapper.setManagerInfo(paramDTO);
	}
	
	//session 을 바꿔야 할 경우 사용 된다.
	@Override
	public void setManagerInfo(HttpServletRequest request,HttpSession session, ManagerDTO paramDTO) {
		//매니저 정보 업데이트
		managerMapper.setManagerInfo(paramDTO);

		//session 설정
		ManagerDTO result = managerMapper.selectManagerInfo(paramDTO);
			
		result.setLogin_type( ( (ManagerLoginDTO) session.getAttribute(Const.managerSession)).getLogin_type() );
			
		updateSession (request, result);
	}
	
	@Override
	public int getIlboTotalCnt(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getIlboTotalCnt(ilboDTO);
	}
	
	@Override
	public List<IlboDTO> getIlboList(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getIlboList(ilboDTO);
	}

	@Override
	public IlboDTO getIlboWorker(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getIlboWorker(ilboDTO);
	}
	
		/**
		 * nemojjang 메니져 등록.
		 */
	public void setManager(ManagerDTO managerDTO) throws Exception{
		try {
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_phone(managerDTO.getManager_phone());
			
			int mCount = replicationManagerMapper.selectManagerCount(paramDTO);
			
			if(mCount == 0l){
				managerDTO.setParent_company_seq("0");
				managerMapper.setManager(managerDTO);
			}else {
				throw new Exception("이미 등록된 매니져");
			}
			
			insertRelation(managerDTO);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
		
	@Override
	public void insertRelationM(ManagerDTO managerDTO) throws Exception {
		// TODO Auto-generated method stub
		insertRelation(managerDTO);
	}
		
	@Transactional(propagation = Propagation.SUPPORTS)
	private void insertRelation(ManagerDTO managerDTO) throws Exception{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			ManagerDTO wmDTO = replicationManagerMapper.selectWmInfo(managerDTO);
			
			if(wmDTO == null) {
				managerMapper.insertWm(managerDTO);
			}
			
			ManagerDTO emDTO = replicationManagerMapper.selectEmInfo(managerDTO);
			if(emDTO == null) {
				managerMapper.insertEm(managerDTO);
			}
			
			transactionManager.commit(status);
		} catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(status);
			throw e;
		}
		
		def = null;
		status = null;
	}
	
	@Override
	public int getManagerTotalCnt(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getManagerTotalCnt(managerDTO);
	}

	@Override
	public List<ManagerDTO> getManagerList(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getManagerList(managerDTO);
	}

	@Override
	public void setManagerCell(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		managerMapper.setManagerCell(managerDTO);
	}

	@Override
	public void removeManagerInfo(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		managerMapper.removeManagerInfo(managerDTO);
	}

	@Override
	public List<WorkDTO> getWorkList(WorkDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getWorkList(paramDTO);
	}

	@Override
	public void updateManagerVisit(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		managerMapper.updateManagerVisit(managerDTO);
	}

	@Override
	public List<ManagerDTO> getCheckManagerEmployerNumList(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getCheckManagerEmployerNumList(managerDTO);
	}

	@Override
	public List<ManagerDTO> selectManagerList(ManagerDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.selectManagerList(paramDTO);
	}

	@Override
	public List<WorkDTO> getEmployerList(WorkDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getEmployerList(paramDTO);
	}
	
		/*
		 * manager APP 에서 오더 등록
		 */
	@Override
	public void regWorkProcess( HttpSession session, ManagerWorkDTO mDTO) {
		// TODO Auto-generated method stub
		
		ManagerLoginDTO sessionDTO = (ManagerLoginDTO) session.getAttribute(Const.managerSession);
		
		//work 정보를 가져 온다.
		WorkDTO workDTO = new WorkDTO();
		workDTO.setWork_seq(mDTO.getWork_seq());
		WorkDTO wDTO = replicationWorkMapper.getSelectInfo(workDTO);
		
		IlboDTO ilboDTO = new IlboDTO();
		
		ilboDTO.setIlbo_date(mDTO.getWork_date());
		ilboDTO.setCompany_seq(wDTO.getCompany_seq());
		ilboDTO.setEmployer_seq(wDTO.getEmployer_seq());
		ilboDTO.setWork_seq(wDTO.getWork_seq());
		ilboDTO.setOrder_work_seq("0");
		ilboDTO.setIlbo_work_order_code("ORD002");
		ilboDTO.setIlbo_work_order_name("APP");
		ilboDTO.setIlbo_work_name(wDTO.getWork_name());
		ilboDTO.setIlbo_work_arrival(mDTO.getWork_arrival());
		ilboDTO.setIlbo_work_finish(mDTO.getWork_finish());
		ilboDTO.setIlbo_work_addr(wDTO.getWork_addr());
		ilboDTO.setIlbo_work_latlng(wDTO.getWork_latlng());
		ilboDTO.setIlbo_work_age(mDTO.getWork_age());
		ilboDTO.setIlbo_work_blood_pressure(mDTO.getWork_blood_pressure());
		ilboDTO.setOutput_status_code("100002");
		ilboDTO.setOutput_status_name("대기");
		ilboDTO.setLabor_contract_seq(wDTO.getLabor_contract_seq());
		ilboDTO.setReceive_contract_seq(wDTO.getReceive_contract_seq());
		
		EmployerDTO employerInfo = new EmployerDTO();
		
		employerInfo.setEmployer_seq(wDTO.getEmployer_seq());
		
		replicationEmployerMapper.getEmployerView(employerInfo);
		
		if(employerInfo.getSign_manager_type().equals("E")) {
			ilboDTO.setSign_manager_seq(employerInfo.getSign_manager_seq());
		}else if(employerInfo.getSign_manager_type().equals("O")) {
			ilboDTO.setSign_manager_seq(wDTO.getManager_seq());
		}else {
			ilboDTO.setSign_manager_seq("0");
		}
			
		if(StringUtil.isNull(mDTO.getWork_manager_phone() )){
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_seq(wDTO.getManager_seq());
			
			ManagerDTO resultDTO =  replicationManagerMapper.selectManagerInfo(paramDTO);
				
			if(resultDTO != null){
				ilboDTO.setIlbo_work_manager_name(resultDTO.getManager_name());
				ilboDTO.setIlbo_work_manager_phone(resultDTO.getManager_phone());
			}
		}else{
			ilboDTO.setIlbo_work_manager_name(mDTO.getWork_manager());
			ilboDTO.setIlbo_work_manager_phone(mDTO.getWork_manager_phone());
		}
			
		ilboDTO.setIlbo_income_code( mDTO.getPay_type());

		String payType = "(노현)";
		if("300006".equals(mDTO.getPay_type())){
			payType = "(노통)";
		}
		ilboDTO.setIlbo_income_name(payType );
				
		ilboDTO.setIlbo_pay_name("(통수)");
		ilboDTO.setIlbo_pay_code("200006");
		
		int workCout = mDTO.getArr_kind_code().length;

		for (int i = 0; i < workCout; i++) {
			ilboDTO.setIlbo_kind_code(mDTO.getArr_kind_code()[i]);
			ilboDTO.setIlbo_kind_name(mDTO.getArr_kind_name()[i]);
			
			int _ilbo_unit_price = 0;
			int _ilbo_fee = 0;
			int _ilbo_pay = 0;
			
			try {
				_ilbo_unit_price = Integer.parseInt( mDTO.getArr_price()[i]);
				_ilbo_fee = (int)(_ilbo_unit_price * 0.1);
				_ilbo_pay = _ilbo_unit_price - _ilbo_fee;
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			ilboDTO.setIlbo_unit_price( Integer.toString(_ilbo_unit_price));	
			ilboDTO.setIlbo_fee( Integer.toString(_ilbo_fee) );
			ilboDTO.setIlbo_pay( Integer.toString(_ilbo_pay) );
			
			String ilboJobComment = mDTO.getWork_days() + "\n" + mDTO.getArr_memo()[i] + "\n" + mDTO.getWork_memo();
			
			ilboDTO.setIlbo_job_comment(ilboJobComment); 
			ilboDTO.setReg_admin( wDTO.getReg_admin() );
			ilboDTO.setWork_owner(wDTO.getOwner_id());
			ilboDTO.setOwner_id(wDTO.getOwner_id());
			
			//작업자 수 만큼 일보에 추가 한다.....=========================================
			int workerCount = Integer.parseInt(mDTO.getArr_worker_num()[i]);
			
			//일보에 추가			
			for (int j = 0; j < workerCount; j++) {
				ilboMapper.addOrderToIlboCell(ilboDTO);
			}
		}
				
		/*=========관리자 에게 SMS 보내기   manager APP 에서 오더 등록 =================================================================================================================*/
		String message = "[★구인접수 APP]\n"+ wDTO.getReg_admin() +"\n"+wDTO.getEmployer_name() +"\n"+ ilboDTO.getIlbo_date() + "\n"+ ilboDTO.getIlbo_work_arrival() + "\n"+ ilboDTO.getIlbo_work_manager_name() + "\n" + ilboDTO.getIlbo_work_manager_phone();
		
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setTr_msg(message);
		smsDTO.setTr_etc1("5");
		
		AdminDTO adminDTO = new AdminDTO();
		String comapnySeq = wDTO.getCompany_seq();
		if("0".equals(comapnySeq )) {
			comapnySeq = "13";
		}
		adminDTO.setSrh_company_seq(comapnySeq);
		adminDTO.setWork_app_sms("1");
		adminDTO.setSidx("admin_seq");
		adminDTO.setPaging(null);
		adminDTO.setSrh_use_yn("1");
			
			// notification에서 SMS를 발송하기 때문에 필요없음.
	//		List<AdminDTO> smsList = adminMapper.getAdminList(adminDTO);
	//		if(smsList.size() > 0) {
	//			for (int j = 0; j < smsList.size(); j++) {
	//				AdminDTO smsAdmin = smsList.get(j);
	//				if(!StringUtil.isNull(smsAdmin.getAdmin_phone())) {
	//					
	//					smsDTO.setTr_phone(smsAdmin.getAdmin_phone().replaceAll("-", ""));
	//					smsMapper.insertInfo(smsDTO);
	//				    
	//				}
	//			}
	//		}
			// SMS 끝 ===================================================================================================================================================
			
		String[] workerCntArr = mDTO.getArr_worker_num();
		
		int workerCnt = 0;
		
		for(int i = 0; i < workerCntArr.length; i++) {
			workerCnt += Integer.parseInt(workerCntArr[i]);
		}
		
		try {
			NotificationDTO notificationDTO = new NotificationDTO();
		    
			notificationDTO.setIlbo_seq(ilboDTO.getIlbo_seq());
	    	notificationDTO.setStatus_flag("ALR009");
	    	notificationDTO.setWorkerCount(workerCnt);
		    
		    // 알림 보내는 메소드
	    	NotificationUtil notificationUtil = new NotificationUtil(restTemplate);
	    	notificationUtil.ilboAlim(notificationDTO);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Map<String, String> setManagerWork(ManagerLoginDTO sessionDTO,	WorkDTO workDTO) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
	    	// ========== 구인처등록 ===================================
	    	String employerSeq 	="";
	    	String employerName = workDTO.getEmployer_name();
	    	String regAdmin = "";
	    	
	    	if(StringUtil.isNull(workDTO.getEmployer_seq())){				// 구인처 신규등록시	메니져의 경우
		    	EmployerDTO employerDTO = new EmployerDTO();
		    	employerDTO.setCompany_seq(sessionDTO.getCompany_seq());
		    	employerDTO.setEmployer_name(workDTO.getEmployer_name());
		    	employerDTO.setReg_admin("manager");
		    	employerDTO.setMod_admin("manager");
		    	
		    	employerMapper.setEmployerCell(employerDTO);
		    	employerSeq = employerDTO.getEmployer_seq();
	    	}else{
	    		employerSeq = workDTO.getEmployer_seq();
	    	}
	    		    	
	    	String managerSeq = "0";
	    	String managerName		= "";
	    	String managerPhone 	= "";

	    	//======== 매니저 등록=========================================
	    	//Root 메니져 검색
	    	if(!StringUtil.isNull(workDTO.getManager_phone())){
	    		ManagerDTO managerDTO = new ManagerDTO();
	    		
	    		ManagerDTO paramDTO = new ManagerDTO();
	    		paramDTO.setManager_phone(workDTO.getManager_phone());
	    		paramDTO.setParent_company_seq("0");
	    		paramDTO.setUse_yn("1");

	    		List<ManagerDTO> managerList = replicationManagerMapper.selectManagerList(paramDTO);
	    		if( managerList.size() ==  0){
	    			logger.debug("=============> 등록된 매니저 없음");
	    			managerDTO.setParent_company_seq("0");
	    		}else if( managerList.size() > 1){
	    			logger.debug("=============> 등록된 매니저 여러개");
	    			transactionManager.rollback(status);
	    			resultMap.put("code", "1000");
	    			resultMap.put("message", "오더매니저가 여러명 등록 되어 있어 등록 할 수 없습니다.\n관리자에게 문의 하세요..");
	    			return resultMap;
	    		}else{//리스트가 1개일때
	    			logger.debug("=============> 등록된 매니저 1개");
	    			ManagerDTO resultDTO = managerList.get(0);
	    			String mCompanySeq = resultDTO.getCompany_seq();

	    			if(mCompanySeq.equals( sessionDTO.getCompany_seq() )){//회사순번이 같으면 그대로 적용 새로등록 할 필요 없음
	    				logger.debug("===> 등록된 매니저 1개가 같은 직업소개소것임");
	    				managerSeq = resultDTO.getManager_seq();
	    			}else{//회사에 있는지 확인한다.
	    				paramDTO.setParent_company_seq(null);
	    				paramDTO.setCompany_seq(mCompanySeq);
	    				paramDTO.setUse_yn("1");

	    				managerList = replicationManagerMapper.selectManagerList(paramDTO);

	    				if( managerList.size() ==  0){//신규로 등록 한다.
	    					logger.debug("=============> 매니저 회가가 틀림");
	    					managerDTO.setParent_company_seq( mCompanySeq );
	    				}else if( managerList.size() > 1){
	    					logger.debug("=============> 매니저 회가 여러개");
	    					transactionManager.rollback(status);

	    					resultMap.put("code", "1000");
	    					resultMap.put("message", "오더매니저가 여러명 등록 되어 있어 등록 할 수 없습니다.\n관리자에게 문의 하세요..");
	    					return resultMap;

	    				}else{
	    					logger.debug("=============> 매니저 sub 로 회사에 등록되어 있음");
	    					resultDTO = managerList.get(0);
	    					managerSeq = resultDTO.getManager_seq();
	    				}
	    			}
	    		}
	
	    		if(  "0".equals( managerSeq) ){
	    			logger.debug("=============> 매니저 등록 한다.");
	    			managerDTO.setManager_phone(workDTO.getManager_phone() );
	    			managerDTO.setManager_name( workDTO.getManager_name() );
	    			managerDTO.setManager_type("O");
	    			managerDTO.setCompany_seq( sessionDTO.getCompany_seq() );
	    			managerDTO.setEmployer_seq( employerSeq );
	    			managerDTO.setMod_admin("employer");
	    			managerDTO.setReg_admin("employer");

	    			managerMapper.setManager(managerDTO);
	    			managerSeq = managerDTO.getManager_seq();
	    		}//매니저 등록 끝
	    	}
		    	
	    	//== 현장등록===============================================
	    	workDTO.setCompany_seq(sessionDTO.getCompany_seq());
	    	workDTO.setEmployer_seq(employerSeq);
	    	workDTO.setWork_manager_name( sessionDTO.getLogin_name());
	    	workDTO.setWork_manager_phone( sessionDTO.getLogin_id());
	    	
	    	if( "O".equals( sessionDTO.getManager_type()) ){
		    	workDTO.setManager_seq(sessionDTO.getSeq());
		    	workDTO.setManager_name(sessionDTO.getLogin_name());
		    	workDTO.setManager_phone(sessionDTO.getLogin_id());
		    	workDTO.setReg_admin("manager");
		    	workDTO.setMod_admin("manager");
	    	}else{
	    		workDTO.setManager_seq(managerSeq);
	    		workDTO.setManager_name(workDTO.getManager_name());
	    		workDTO.setManager_phone(workDTO.getManager_phone());
	    		workDTO.setWork_manager_name(workDTO.getManager_name());
	    		workDTO.setWork_manager_phone(workDTO.getManager_phone());
	    		workDTO.setReg_admin("employer");
		    	workDTO.setMod_admin("employer");
	    	}
		    	
	    	workMapper.setWorkCell(workDTO);
	    	
	    	transactionManager.commit(status);
	    	
	    	resultMap.put("code", "0000");
	    	resultMap.put("employerSeq", employerSeq );
	    	resultMap.put("employerName", employerName );
	    	resultMap.put("workSeq", workDTO.getWork_seq() );
	    	resultMap.put("workName", workDTO.getWork_name());
	    } catch (Exception e) {
	      // TODO: handle exception
	    	transactionManager.rollback(status);
			e.printStackTrace();
			resultMap.put("code", "9999");
			resultMap.put("message","오류가 발생하였습니다.\n관리자에게 문의 하세요..!!");
	    }
		return resultMap;
	}
	
	@Override
	public String selectParentCompanySeq(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.selectParentCompanySeq(managerDTO);
	}

	@Override
	public List<ManagerDTO> getWorkManagerList(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getWorkManagerList(managerDTO);
	}

	@Override
	public void deleteRelationM(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		
		ManagerDTO resultDTO = replicationManagerMapper.selectWmInfo(managerDTO);
		managerMapper.deleteWm(managerDTO);
		
		resultDTO.setWork_seq(null);
		resultDTO.setUse_yn("1");
		int ct = managerMapper.selectWmCount(resultDTO);
		if(ct == 0) {
			managerMapper.deleteEm(resultDTO);
		}
	}
	
	@Override
	public void updateWm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		managerMapper.updateWm(managerDTO);
	}

	@Override
	public void updateEm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		managerMapper.updateEm(managerDTO);
	}

	@Override
	public void updateRelation(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		ManagerDTO paramDTO = new ManagerDTO();
		paramDTO.setWm_seq(managerDTO.getWm_seq());
		
		ManagerDTO resultDTO = managerMapper.selectWmInfo(paramDTO);
		managerMapper.updateWm(managerDTO);
		
		ManagerDTO resultDTO2 = managerMapper.selectEmInfo(resultDTO);
		
		ManagerDTO emParamDTO = new ManagerDTO();
		
		if("0".equals(managerDTO.getEmployer_seq())){
			emParamDTO.setEm_seq(resultDTO2.getEm_seq());
			managerMapper.deleteEm(emParamDTO);
		}else {
			emParamDTO.setEmployer_seq(managerDTO.getEmployer_seq());
			if(resultDTO2 == null) {
				emParamDTO.setManager_seq(resultDTO.getManager_seq());
				managerMapper.insertEm(emParamDTO);
			}else {
				managerMapper.updateEm(emParamDTO);
			}
		}
	}
	
	@Override
	public List<ManagerDTO> getSearchManagerList(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getSearchManagerList(managerDTO);
	}
	
	@Override
	public void setProcessEm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		EmployerDTO employerDTO = new EmployerDTO();
		employerDTO.setEmployer_seq(managerDTO.getEmployer_seq());
		employerDTO = replicationEmployerMapper.getEmployerView(employerDTO);
		
		if("0".equals(employerDTO.getManager_seq())) {
			managerMapper.insertEm(managerDTO);
		}else {
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setEmployer_seq(employerDTO.getEmployer_seq());
			paramDTO.setManager_seq(managerDTO.getManager_seq());
			
			ManagerDTO resultDTO = replicationManagerMapper.selectEmInfo(paramDTO);
			
			if("0".equals(managerDTO.getManager_seq())) {
				managerDTO.setEm_seq(resultDTO.getEm_seq());
				managerMapper.deleteEm(managerDTO);
			}else {
			
				if(resultDTO == null) {
					managerMapper.insertEm(managerDTO);
				}else {
					managerDTO.setEm_seq(resultDTO.getEm_seq());
					managerMapper.updateEm(managerDTO);
				}
			}
		}
	}

	@Override
	public List<EmDTO> getManagerEWTreeList(EmDTO emDTO) {
		// TODO Auto-generated method stub
		return replicationManagerMapper.getManagerEWTreeList(emDTO);
	}

	@Override
	public Map<String, String> insertEm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0000");
		
		ManagerDTO resultDTO = replicationManagerMapper.selectEmInfo(managerDTO);
		if(resultDTO == null) {
			managerMapper.insertEm(managerDTO);
		}else {
			resultMap.put("code", "1111");
			resultMap.put("message", "이미 추가된 구인처 입니다.");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, String> insertWm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0000");
		
		ManagerDTO resultDTO = replicationManagerMapper.selectEmInfo(managerDTO);
		if(resultDTO == null) {
			resultMap.put("code", "2222");
			resultMap.put("message", "추가 되지 않은 구인처의 현장입니다. 구인처를 먼저 추가 하세요.");
			
			return resultMap;
		}
		
		ManagerDTO wmDTO = replicationManagerMapper.selectWmInfo(managerDTO);
		
		if(wmDTO == null) {
			managerMapper.insertWm(managerDTO);
		}else {
			resultMap.put("code", "1111");
			resultMap.put("message", "이미 추가된 현장 입니다.");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, String> deleteEm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0000");
		
		managerMapper.deleteEm(managerDTO);
		return resultMap;
	}

	@Override
	public Map<String, String> deleteWm(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", "0000");
		
		managerMapper.deleteWm(managerDTO);
		return resultMap;
	}
}
