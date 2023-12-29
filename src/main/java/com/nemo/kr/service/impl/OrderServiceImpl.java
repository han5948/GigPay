package com.nemo.kr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.ContractDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.OrderDTO;
import com.nemo.kr.dto.OrderLogDTO;
import com.nemo.kr.dto.OrderWorkDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationAdminMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationContractMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationEmployerMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationManagerMapper;
import com.nemo.kr.mapper.ilgaja.read.ReplicationOrderMapper;
import com.nemo.kr.mapper.ilgaja.write.AdminMapper;
import com.nemo.kr.mapper.ilgaja.write.ContractMapper;
import com.nemo.kr.mapper.ilgaja.write.EmployerMapper;
import com.nemo.kr.mapper.ilgaja.write.ManagerMapper;
import com.nemo.kr.mapper.ilgaja.write.OrderMapper;
import com.nemo.kr.mapper.ilgaja.write.WorkMapper;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.OrderService;
//import com.nemo.kr.util.StringUtil;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired OrderMapper orderMapper;
	@Autowired ReplicationOrderMapper replicationOrderMapper;
	@Autowired WorkMapper workMapper;
	@Autowired EmployerMapper employerMapper;
	@Autowired ReplicationEmployerMapper replicationEmployerMapper;
	@Autowired ManagerMapper managerMapper;
	@Autowired ReplicationManagerMapper replicationManagerMapper;
	@Autowired AdminMapper adminMapper;
	@Autowired ReplicationAdminMapper replicationAdminMapper;
	@Autowired ContractMapper contractMapper;
	@Autowired ReplicationContractMapper replicationContractMapper;
	@Autowired EmployerService employerService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public int getOrderTotalCnt(OrderDTO orderDTO) {
		// TODO Auto-generated method stub

		return replicationOrderMapper.getOrderTotalCnt(orderDTO);
	}

	@Override
	public List<OrderDTO> getOrderList(OrderDTO orderDTO) {
		// TODO Auto-generated method stub

		return (List<OrderDTO>) replicationOrderMapper.getOrderList(orderDTO);
	}

	@Override
	public int getOrderWorkTotalCnt(OrderWorkDTO orderWorkDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getOrderWorkTotalCnt(orderWorkDTO);
	}

	@Override
	public List<OrderDTO> getOrderWorkList(OrderWorkDTO orderWorkDTO) {
		// TODO Auto-generated method stub
		return (List<OrderDTO>) replicationOrderMapper.getOrderWorkList(orderWorkDTO);
	}

	@Override
	@Transactional
	public void setOrderCell(OrderDTO orderDTO) {
		// TODO Auto-generated method stub

		orderMapper.setOrderCell(orderDTO);
	}

	/*
	 * 행수정
	 * */
	@Override
	@Transactional
	public void setOrderInfo(OrderDTO orderDTO) {
		// TODO Auto-generated method stub

		//로그쌓기====================================================================
		OrderLogDTO paramDTO = new OrderLogDTO();
		paramDTO.setOrder_seq( orderDTO.getOrder_seq() );
		List<OrderLogDTO> logList = replicationOrderMapper.getOrderLogList(paramDTO);
		
		
		OrderLogDTO logDTO = new OrderLogDTO();
		logDTO.setOrder_seq( orderDTO.getOrder_seq() );
		
		if(logList.size() ==0){
			logDTO.setLog_type("D");	//초기
			logDTO.setPrarent_seq("0");
		}else{
			if(StringUtil.isNull( orderDTO.getParent_seq() ) ){
				logDTO.setLog_type("C"); //변경
				logDTO.setPrarent_seq("0");
			}else{
				logDTO.setLog_type("R"); //복구
				logDTO.setPrarent_seq( orderDTO.getParent_seq() );
			}
		}
		
		
		//이전 데이터
		OrderDTO  resultDTO = replicationOrderMapper.getSelectOrder(orderDTO);
		Gson gson =  new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String prevData 		= gson.toJson(resultDTO);
		String changeData 	= gson.toJson(orderDTO);
		
		logDTO.setPrev_data(prevData);
		logDTO.setChange_data(changeData);
		logDTO.setReg_admin( orderDTO.getMod_admin());
		orderMapper.insertOrderLog( logDTO );
		//==============================================================================
		
//		orderDTO.setWork_latlng(resultDTO.getWork_latlng());
		String employerName = orderDTO.getEmployer_name();
		if( employerName != null ) {
			orderDTO.setEmployer_name_exists(employerService.isContainEmployerName(orderDTO.getEmployer_name())+"");
		}
		orderMapper.setOrderInfo(orderDTO);
	}

	/*
	 * 행복구
	 * */
	@Override
	@Transactional
	public void setOrderLogRestore(OrderLogDTO orderLogDTO) {
		// TODO Auto-generated method stub
		Gson gson =  new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
		//바꿀  log 정보
		OrderLogDTO logDTO = replicationOrderMapper.getSelectOrderLog(orderLogDTO);
		String changeData = logDTO.getPrev_data();
		
		//현재 order 정보
		OrderDTO paramDTO = new OrderDTO();
		paramDTO.setOrder_seq( logDTO.getOrder_seq() );
		OrderDTO orderDTO = replicationOrderMapper.getSelectOrder(paramDTO);
		String prevData 	= gson.toJson(orderDTO);
		
		paramDTO =  gson.fromJson(changeData, OrderDTO.class);
		paramDTO.setMod_admin( orderLogDTO.getReg_admin() );
		
		if( paramDTO.getWork_latlng().isEmpty() ) {
			orderMapper.initWorkLatlng(paramDTO);
		}
		//order 정보 바꾸기
		orderMapper.setOrderInfo(paramDTO);
		
		//로그 쌓기
		logDTO = new OrderLogDTO();
		logDTO.setOrder_seq( paramDTO.getOrder_seq() );
		logDTO.setLog_type("R");	// 복구
		logDTO.setPrarent_seq( orderLogDTO.getLog_seq() );
		logDTO.setPrev_data(prevData);
		logDTO.setChange_data(changeData);
		logDTO.setReg_admin( orderLogDTO.getReg_admin());
		orderMapper.insertOrderLog( logDTO );
	
		
	}
	
	@Override
	@Transactional
	public void removeOrderInfo(OrderDTO orderDTO) {
		// TODO Auto-generated method stub

		orderMapper.removeOrderInfo(orderDTO);
	}

	@Override
	public OrderDTO getSelectOrder(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getSelectOrder(orderDTO);
	}

	@Override
	public List<OrderLogDTO> getOrderLog(OrderLogDTO orderLogDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getOrderLogList(orderLogDTO);
	}

	@Override
	public OrderDTO getSelectInfo(OrderDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getSelectInfo(paramDTO);
	}

	@Override
	public OrderWorkDTO getOrderWorkInfo(OrderWorkDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getOrderWorkInfo(paramDTO);
	}

	/*
	 * 오더 저장
	 * java 내에서 트랜잭션을 사용할때 @Transactional(propagation = Propagation.SUPPORTS) 옵션을 사용 하여야 한다.
	 * https://preamtree.tistory.com/97
	 * */
	//@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Map<String, String> setOrderProcess(OrderDTO orderDTO, HttpSession session) throws Exception {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
		
			if(StringUtil.isNull(orderDTO.getOwner_id())) {
				orderDTO.setOwner_id("company");
			}
		
			String companySeq 		= orderDTO.getCompany_seq();
			String companyName 	= orderDTO.getCompany_name();
			String employerSeq 		= orderDTO.getEmployer_seq();
			String managerSeq 		= orderDTO.getManager_seq();
			String workSeq 			= orderDTO.getWork_seq();
			String ownerId			= orderDTO.getOwner_id();
			String managerType = "";
			boolean newEmployer = true;
			
			if(companySeq.equals("0")) {
				companySeq = orderDTO.getOrder_company_seq();
				companyName = orderDTO.getOrder_company_name();
			}
			
			ManagerDTO managerDTO = new ManagerDTO();
			//메니져 검색
			ManagerDTO paramDTO = new ManagerDTO();
			paramDTO.setManager_phone(orderDTO.getManager_phone());
			paramDTO.setParent_company_seq("0");
			paramDTO.setUse_yn("1");
			
			List<ManagerDTO> managerList = replicationManagerMapper.selectManagerList(paramDTO);
			if( managerList.size() ==  0){
				logger.debug("=============> 매니저 등록 한다.");
//				companySeq =  orderDTO.getCompany_seq();
//				companyName = orderDTO.getCompany_name();
//				ownerId = orderDTO.getOwner_id();
				if("?".equals(orderDTO.getManager_name())){
					managerDTO.setOrder_seq(orderDTO.getOrder_seq());
				}else{
					managerDTO.setManager_name( orderDTO.getManager_name() );
				}
				managerDTO.setManager_phone(orderDTO.getManager_phone() );

				managerDTO.setManager_type("O");
				
				String employerOwnerId = "";
				if( !"0".equals(employerSeq ) ) {
					//기존구인처 신규매니저 -> 기존구인처 지점을 따라감
					EmployerDTO employerParam = new EmployerDTO();
					employerParam.setEmployer_seq(employerSeq);
					EmployerDTO employerResult = replicationEmployerMapper.getEmployerView(employerParam);
					managerDTO.setCompany_seq(employerResult.getCompany_seq());
					employerOwnerId = employerResult.getOwner_id();
				}else if(orderDTO.getOrder_company_seq() != null) {
					managerDTO.setCompany_seq(orderDTO.getOrder_company_seq());
				}else {
					managerDTO.setCompany_seq( companySeq );
				}
				
				managerDTO.setEmployer_seq("0");
				managerDTO.setMod_admin(orderDTO.getReg_admin());
				managerDTO.setReg_admin(orderDTO.getReg_admin());
				
				AdminDTO adminDTO = new AdminDTO();
				
				if(!employerOwnerId.isEmpty()) {
					//기존구인처 신규매니저 -> 기존구인처 소유를 따라감
					managerDTO.setOwner_id(employerOwnerId);
				}else if(!orderDTO.getOwner_id().equals("company")) {
					adminDTO.setAdmin_id(orderDTO.getOwner_id());
					adminDTO.setUse_yn("1");
					
					adminDTO = replicationAdminMapper.selectInfo(adminDTO);
					
					if(adminDTO.getAuth_level().equals("3")) {
						managerDTO.setOwner_id(adminDTO.getAdmin_id());
					}
				}
				
				// 오더관리에서 매니저 신규 생성 시 메모에 구인처명 입력 
				if(orderDTO.getEmployer_name() != null && !orderDTO.getEmployer_name().equals("")) {
					managerDTO.setManager_memo(orderDTO.getEmployer_name());
				}
				
				managerDTO.setJoin_route("MJC002"); //가입경로 : 오더관리
				managerMapper.setManager(managerDTO);
				managerSeq = managerDTO.getManager_seq();
				
			}else if( managerList.size() > 1){
				
				transactionManager.rollback(status);
				resultMap.put("code", "1000");
				resultMap.put("message", "오더매니저가 여러명 등록 되어 있어 등록 할 수 없습니다.\n관리자에게 문의 하세요..");
				return resultMap;
				
			}else{//리스트가 1개일때
				ManagerDTO resultDTO = managerList.get(0);
				managerSeq = resultDTO.getManager_seq();
				ownerId = resultDTO.getOwner_id();
				companySeq = resultDTO.getCompany_seq();
				companyName = resultDTO.getCompany_name();
				managerType = resultDTO.getManager_type();
			}
			
			if( "0".equals(employerSeq )){
				logger.error("=============> 구인처 등록");
				EmployerDTO employerDTO = new EmployerDTO();
				employerDTO.setCompany_seq(companySeq);
				employerDTO.setCompany_name( companyName );
				employerDTO.setEmployer_name(orderDTO.getEmployer_name());
				employerDTO.setEmployer_num(orderDTO.getEmployer_num());
				employerDTO.setIs_tax( orderDTO.getIs_tax() );
				employerDTO.setTax_name( orderDTO.getTax_name() );
				employerDTO.setTax_phone( orderDTO.getTax_phone() );
				employerDTO.setMod_admin( orderDTO.getReg_admin());
				employerDTO.setReg_admin( orderDTO.getReg_admin());
				employerDTO.setOwner_id(ownerId);
				employerDTO.setManager_seq(managerSeq);
				employerDTO.setLabor_contract_seq(orderDTO.getLabor_contract_seq());
				employerDTO.setReceive_contract_seq(orderDTO.getReceive_contract_seq());
				
				employerMapper.setEmployerCell(employerDTO);	//insert
				
				employerSeq = employerDTO.getEmployer_seq();
				
				// 기존 구인처인지 아닌지 구분 위한 flag(신규 구인처)
				newEmployer = true;
			}else{
				logger.error("=============> 구인처 수정");
				EmployerDTO employerDTO = new EmployerDTO();
				employerDTO.setEmployer_seq( employerSeq );
				employerDTO.setIs_tax( orderDTO.getIs_tax() );
				employerDTO.setTax_name( orderDTO.getTax_name() );
				employerDTO.setTax_phone( orderDTO.getTax_phone() );
				employerDTO.setReg_admin(  orderDTO.getReg_admin() );
				//update
				employerMapper.setEmployerInfo(employerDTO);
				
				// 기존 구인처인지 아닌지 구분 위한 flag(기존 구인처)
				newEmployer = false;
			}
			
			// 현장 담당자가 있으면 매니저 table에 insert
			if(orderDTO.getWork_manager_phone() != null && !orderDTO.getWork_manager_phone().equals("")) {
				// 현장 담당자 manager 테이블에서 조회
				ManagerDTO readManagerDTO = new ManagerDTO();
				readManagerDTO.setManager_phone(orderDTO.getWork_manager_phone());
				readManagerDTO.setUse_yn("1");
				
				readManagerDTO = managerMapper.selectManagerInfo(readManagerDTO);
				
				// manager 테이블에 해당 폰 번호가 없으면 insert
				if(readManagerDTO == null) {
					ManagerDTO newManagerDTO = new ManagerDTO();
					
					newManagerDTO.setManager_phone(orderDTO.getWork_manager_phone());
					newManagerDTO.setManager_name(orderDTO.getWork_manager_name());
					newManagerDTO.setManager_type("O");
					
					EmployerDTO employerParam = new EmployerDTO();
					
					employerParam.setEmployer_seq(employerSeq);
					
					EmployerDTO employerResult = employerMapper.getEmployerView(employerParam);
					
					newManagerDTO.setCompany_seq(employerResult.getCompany_seq());
					newManagerDTO.setOwner_id(employerResult.getOwner_id());
					newManagerDTO.setReg_admin(orderDTO.getReg_admin());
					newManagerDTO.setMod_admin(orderDTO.getReg_admin());
					
					newManagerDTO.setJoin_route("MJC002"); //가입경로 : 오더관리
					managerMapper.setManager(newManagerDTO);
				}
			}
			
			if( "0".equals(workSeq)){
				logger.debug("=============> 현장 등록 한다.");
				WorkDTO workDTO = new WorkDTO();

				// 기존 구인처의 신규 현장이면 기존 구인처의 지점을 따라간다.
				EmployerDTO employerParam = new EmployerDTO();
				
				employerParam.setEmployer_seq(employerSeq);
				
				EmployerDTO employerResult = employerMapper.getEmployerView(employerParam);
				//EmployerDTO employerResult = replicationEmployerMapper.getEmployerView(employerParam);
				
				workDTO.setCompany_seq(employerResult.getCompany_seq());
				
				workDTO.setEmployer_seq( employerSeq );
				workDTO.setWork_name(orderDTO.getWork_name());
				workDTO.setWork_addr(orderDTO.getWork_addr());
				workDTO.setWork_sido(orderDTO.getWork_sido());
				workDTO.setWork_sigugun(orderDTO.getWork_sigugun());
				workDTO.setWork_dongmyun(orderDTO.getWork_dongmyun());
				workDTO.setWork_rest(orderDTO.getWork_rest());
				workDTO.setWork_latlng(orderDTO.getWork_latlng());
				workDTO.setWork_arrival( orderDTO.getWork_arrival());
				workDTO.setWork_finish( orderDTO.getWork_finish());
				workDTO.setWork_age( orderDTO.getWork_age());
				workDTO.setWork_blood_pressure( orderDTO.getWork_blood_pressure());
				workDTO.setIs_worker_info( orderDTO.getIs_worker_info() );
				
				int auth_level = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
				String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
				
				// 상담사 계정으로 신규 현장 생성 시 신규 현장의  owner_id는 상담사 아이디로 들어간다.
				if(auth_level == 3) {
					workDTO.setOwner_id(admin_id);
				}else {
					workDTO.setOwner_id(employerResult.getOwner_id());
				}
				
				workDTO.setWork_breakfast_yn(orderDTO.getBreakfast_yn());
				workDTO.setWork_memo(orderDTO.getEmployer_name());
				
				//현장담당자
				if(StringUtil.isNull( orderDTO.getWork_manager_name() )){
					workDTO.setWork_manager_name( orderDTO.getManager_name());
				}else{
					workDTO.setWork_manager_name( orderDTO.getWork_manager_name());
				}
				
				//현장담당자 전화번호
				if(StringUtil.isNull( orderDTO.getWork_manager_phone() )){
					workDTO.setWork_manager_phone( orderDTO.getManager_phone());
				}else{
					workDTO.setWork_manager_phone( orderDTO.getWork_manager_phone());
				}
				
				workDTO.setManager_seq( managerSeq );
				workDTO.setManager_name( orderDTO.getManager_name());
				workDTO.setManager_phone( orderDTO.getManager_phone());
				workDTO.setMod_admin(orderDTO.getReg_admin());
				workDTO.setReg_admin(orderDTO.getReg_admin());
				workDTO.setLabor_contract_seq(orderDTO.getLabor_contract_seq());
				workDTO.setReceive_contract_seq(orderDTO.getReceive_contract_seq());
				
				workMapper.setWorkCell(workDTO);
				workSeq = workDTO.getWork_seq();

			}else{
				logger.debug("=============> 현장 수정 한다.");
				WorkDTO workDTO = new WorkDTO();
				workDTO.setWork_seq( workSeq );
				workDTO.setIs_worker_info( orderDTO.getIs_worker_info() ); //작업자정보요청유무
				
				workDTO.setWork_arrival( orderDTO.getWork_arrival());
				workDTO.setWork_finish( orderDTO.getWork_finish());
				workDTO.setWork_age( orderDTO.getWork_age());
				workDTO.setWork_blood_pressure( orderDTO.getWork_blood_pressure());
				workDTO.setWork_breakfast_yn(orderDTO.getBreakfast_yn());
				workDTO.setReg_admin(orderDTO.getReg_admin());
				
				workMapper.setWorkInfo(workDTO);
			}
			
			logger.debug("=============> wm 정보등록");
			//if(!managerType.equals("E")) {
				ManagerDTO wmPDTO = new ManagerDTO();
				wmPDTO.setWork_seq(workSeq);
				wmPDTO.setEmployer_seq(employerSeq);
				wmPDTO.setManager_seq(managerSeq);
				wmPDTO.setMod_admin(orderDTO.getReg_admin());
				wmPDTO.setReg_admin(orderDTO.getReg_admin());
        
				ManagerDTO wmDTO = replicationManagerMapper.selectWmInfo(wmPDTO);
				
				if(wmDTO == null) {
					managerMapper.insertWm(wmPDTO);
				}
				logger.debug("=============> em 정보등록");
				ManagerDTO emDTO = replicationManagerMapper.selectEmInfo(wmPDTO);
				if(emDTO == null) {
					managerMapper.insertEm(wmPDTO);
				}	
			//}
	        	
						
			logger.debug("=============> 오더정보수정 수정 한다.");
			OrderDTO orderParam = new OrderDTO();
			orderParam.setOrder_seq( orderDTO.getOrder_seq());
			orderParam.setCompany_seq(companySeq);
			orderParam.setEmployer_seq(employerSeq);
			orderParam.setWork_seq(workSeq);
			orderParam.setManager_seq(managerSeq);
			orderParam.setOwner_id(ownerId);
			orderParam.setEmployer_search_yn(orderDTO.getEmployer_search_yn());
			
			if(StringUtil.isNull( orderDTO.getWork_manager_name() )){
				orderParam.setWork_manager_name( orderDTO.getManager_name());
			}
			
			if(StringUtil.isNull( orderDTO.getWork_manager_phone() )){
				orderParam.setWork_manager_phone( orderDTO.getManager_phone());
			}
			
			orderParam.setOrder_state("4");
			orderParam.setMod_admin(orderDTO.getReg_admin());
			orderMapper.updateOrderInfo(orderParam);

			transactionManager.commit(status);
			resultMap.put("code", "0000");


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
	@Transactional
	public void setOrderWorkInfo(OrderWorkDTO wParamDTO) {
		// TODO Auto-generated method stub
		orderMapper.setOrderWorkInfo(wParamDTO);
	}

	/*
	 * Web 에서 오더 등록 할때 호출 됨......
	 * */
	//@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Map<String, String> regWorkProcess(OrderDTO orderDTO,	OrderWorkDTO orderWorkDTO) {
		// TODO Auto-generated method stub
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			/*매니져가 있는지 검색 하여 매니져의 소속 지점을 넣어 준다.*/
			ManagerDTO mParamDTO = new ManagerDTO();
			mParamDTO.setManager_phone(orderDTO.getManager_phone());
			mParamDTO.setUse_yn("1");
			ManagerDTO  managerDTO = replicationManagerMapper.selectManagerInfo(mParamDTO);
			
			if(managerDTO != null) {
				orderDTO.setCompany_seq(managerDTO.getCompany_seq());
				orderDTO.setManager_seq(managerDTO.getManager_seq());
				orderDTO.setManager_name(managerDTO.getManager_name());
			}
			
			// 오더관리로 들어가는 항목 근로계약서, 대리수령동의서 기본값 넣어주기 위한 처리
			List<ContractDTO> contractList = replicationContractMapper.selectContractDefaultList();
			
			for(int i = 0; i < contractList.size(); i++) {
				if(contractList.get(i).getCategory_code().equals("ELM")) {
					orderDTO.setLabor_contract_seq(contractList.get(i).getContract_seq());
				}else if(contractList.get(i).getCategory_code().equals("ERM")) {
					orderDTO.setReceive_contract_seq(contractList.get(i).getContract_seq());
				}
			}
			
			orderDTO.setEmployer_name_exists(employerService.isContainEmployerName(orderDTO.getEmployer_name())+"");
			orderMapper.insertOrder(orderDTO);
			
			int workCout = orderWorkDTO.getArr_kind_code().length;
			for (int i = 0; i < workCout; i++) {
				
				OrderWorkDTO paramDTO = new OrderWorkDTO();
				paramDTO.setOrder_seq(orderDTO.getOrder_seq());
				
				paramDTO.setWork_kind_code(orderWorkDTO.getArr_kind_code()[i]);
				paramDTO.setWork_kind_name(orderWorkDTO.getArr_kind_name()[i]);
				
				if(orderWorkDTO.getArr_job_detail_code().length > 0) {
					paramDTO.setWork_job_detail_code(orderWorkDTO.getArr_job_detail_code()[i]);
				}
				
				if(orderWorkDTO.getArr_job_detail_name().length > 0) { 
					paramDTO.setWork_job_detail_name(orderWorkDTO.getArr_job_detail_name()[i].replaceAll("\\|", ","));
				}
				//paramDTO.setWork_job_detail_name(orderWorkDTO.getWork_job_detail_name());
				
				if(orderWorkDTO.getArr_job_add_code().length > 0) {
					paramDTO.setWork_job_add_code(orderWorkDTO.getArr_job_add_code()[i]);
				}
				
				if(orderWorkDTO.getArr_job_add_name().length > 0) {
					paramDTO.setWork_job_add_name(orderWorkDTO.getArr_job_add_name()[i].replaceAll("\\|", ","));
				}
				
				try {
					paramDTO.setWork_price(orderWorkDTO.getArr_price()[i]);	
				} catch (Exception e) {
					// TODO: handle exception
					paramDTO.setWork_price("0");
				}
				
				paramDTO.setWork_memo(orderWorkDTO.getArr_memo()[i].replace("&#44", ","));
				paramDTO.setWorker_count(orderWorkDTO.getArr_count()[i]);
				paramDTO.setReg_admin( orderWorkDTO.getReg_admin());
				orderMapper.insertOrderWork(paramDTO);	
			}
			
			transactionManager.commit(status);
			resultMap.put("code", "0000");
			
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
	public int getTotalWork(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return replicationOrderMapper.getTotalWork(orderDTO);
	}

	/**
	 * 사업자(주민)번호로 구인처 조회
	 * @param orderDTO
	 * @return EmployerDTO
	 */
	@Override
	public EmployerDTO getEmployerByEmployerNum(OrderDTO orderDTO){
		return replicationOrderMapper.getEmployerByEmployerNum(orderDTO);
	}
	/**
	 * 좌표가 같은 현장 리스트 조회
	 * @param orderDTO
	 * @return WorkDTO
	 */
	@Override
	public List<WorkDTO> getWorkListByWorkLatlng(OrderDTO orderDTO){
		return replicationOrderMapper.getWorkListByWorkLatlng(orderDTO);
	}

	
}
