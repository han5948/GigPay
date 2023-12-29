package com.nemo.kr.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.JobAlimDTO;
import com.nemo.kr.dto.JobAlimReceiveDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.AlimService;
import com.nemo.kr.service.JobAlimService;
import com.nemo.kr.service.PushSendService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.MessageUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/jobAlim")
public class JobAlimController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@Autowired AlimService alimService;
	@Autowired JobAlimService jobAlimService;
	@Autowired PushSendService pushSendService;
	@Autowired WorkerService workerService;
	@Autowired MessageUtil messageUtil;
	
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	// Session 설정
	private JobAlimDTO setSessionToDTO(HttpSession session, JobAlimDTO jobAlimDTO) throws Exception {
		// Session 정보
		int auth_level = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);

		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		// Session 설정
		jobAlimDTO.setCompany_seq(company_seq);
		jobAlimDTO.setAdminLevel("" + auth_level);
		jobAlimDTO.setReg_admin(admin_id);
		jobAlimDTO.setJobAlim_writer(company_name);

		return jobAlimDTO;
	}

	/**
	 * 
	 * @Method Name : alimWrite
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Park YunSoo
	 * @Method 설명 : 일가자 알림 작성 팝업
	 */
	@RequestMapping("/jobAlimWrite")
	public String jobAlimWrite(Model model, JobAlimReceiveDTO jobAlimReceiveDTO, HttpServletRequest request) {
		//check된 값이 있는지 없는지 판단하기 위한 분기 처리
		if (jobAlimReceiveDTO.getWorker_seq() != null && !jobAlimReceiveDTO.getWorker_seq().equals(null)) {
			model.addAttribute("worker_seq", jobAlimReceiveDTO.getWorker_seq());
			model.addAttribute("worker_length", jobAlimReceiveDTO.getWorker_length());
		} else {
			int cnt = jobAlimService.selectListCntWorkerSeq(jobAlimReceiveDTO);

			model.addAttribute("worker_length", cnt);
		}

		String title = "";
		String work_addr = "";
		
		if(jobAlimReceiveDTO.getEmployer_name() != null && !jobAlimReceiveDTO.getEmployer_name().equals("") ) {
			title = jobAlimReceiveDTO.getEmployer_name();
		}
		
		if(jobAlimReceiveDTO.getWork_sido() != null && !jobAlimReceiveDTO.getWork_sido().equals("") ) {
			title += "-" + jobAlimReceiveDTO.getWork_sido();
			work_addr += jobAlimReceiveDTO.getWork_sido();
		}
		
		if(jobAlimReceiveDTO.getWork_sigugun() != null && !jobAlimReceiveDTO.getWork_sigugun().equals("") ) {
			title += " " + jobAlimReceiveDTO.getWork_sigugun();
			work_addr += jobAlimReceiveDTO.getWork_sigugun();
		}
		
		if(jobAlimReceiveDTO.getWork_dongmyun() != null && !jobAlimReceiveDTO.getWork_dongmyun().equals("") ) {
			title += " " + jobAlimReceiveDTO.getWork_dongmyun();
			work_addr += jobAlimReceiveDTO.getWork_dongmyun();
		}
		
		model.addAttribute("work_addr", work_addr);
		model.addAttribute("workFlag", jobAlimReceiveDTO.getWorkFlag());
		model.addAttribute("employer_seq", jobAlimReceiveDTO.getEmployer_seq());
		model.addAttribute("employer_name", jobAlimReceiveDTO.getEmployer_name());
		model.addAttribute("work_seq", jobAlimReceiveDTO.getWork_seq());
		model.addAttribute("work_name", jobAlimReceiveDTO.getWork_name());
		model.addAttribute("worker_alias", request.getParameter("worker_alias"));
		model.addAttribute("job_name", request.getParameter("job_name"));
		model.addAttribute("queryFlag", jobAlimReceiveDTO.getQueryFlag());
		model.addAttribute("title", title);

		return "/admin/jobAlim/jobAlimWrite.popup_admin";
	}

	/**
	 * 
	 * @Method Name : insertInfo
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Park YunSoo
	 * @Method 설명 : 일가자 알림 Insert
	 */
	@RequestMapping("/insertInfo")
	@ResponseBody
	public Map<String, Object> insertInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobAlimDTO jobAlimDTO, JobAlimReceiveDTO jobAlimReceiveDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			jobAlimDTO = setSessionToDTO(session, jobAlimDTO);

			List<String> list = new ArrayList<String>();
			String worker_seq;
			String[] worker_seq_array = null;

			if (jobAlimReceiveDTO.getWorker_seq() != null && !jobAlimReceiveDTO.getWorker_seq().equals(null)) {
				worker_seq = jobAlimReceiveDTO.getWorker_seq();
				worker_seq_array = worker_seq.split(",");
			} else {
				jobAlimReceiveDTO.setCompany_seq(jobAlimDTO.getCompany_seq());
				
				// workerList select
				List<JobAlimReceiveDTO> workerSeqList = jobAlimService.getWorkerSeq(jobAlimReceiveDTO);

				// list 중복 제거
				for(int i = 0; i < workerSeqList.size(); i++) {
					if(!list.contains(workerSeqList.get(i).getWorker_seq())) {
						list.add(workerSeqList.get(i).getWorker_seq());
					}
				}
				
				worker_seq_array = new String[list.size()];
				
				// list > array
				for(int i = 0; i < list.size(); i++) {
					worker_seq_array[i] = list.get(i);
				}
			}
			
			if(jobAlimDTO.getMain_view_yn() == null) {
				jobAlimDTO.setMain_view_yn("0");
			}
			
			// 일자리 Push Insert
			jobAlimService.insertInfo(jobAlimDTO);
			jobAlimReceiveDTO.setJobAlim_seq(jobAlimDTO.getJobAlim_seq());
			
			// 일자리 Push Insert (tbl_alim)
			AlimDTO alimDTO = new AlimDTO();
			alimDTO.setAlim_title(jobAlimDTO.getJobAlim_title());
			alimDTO.setAlim_content(jobAlimDTO.getJobAlim_content());
			alimDTO.setFlag("W");
			alimDTO.setAlim_writer(jobAlimDTO.getJobAlim_writer());
			alimDTO.setCompany_seq(jobAlimDTO.getCompany_seq());
			alimDTO.setMain_view_yn(jobAlimDTO.getMain_view_yn());
			alimDTO.setAdminLevel(jobAlimDTO.getAdminLevel());
			
			if(jobAlimDTO.getWork_seq() != null && jobAlimDTO.getWork_seq().equals("")) {
				alimDTO.setWork_seq("0");
			}else {
				alimDTO.setWork_seq(jobAlimDTO.getWork_seq());
			}
			
			alimDTO.setReg_admin(jobAlimDTO.getReg_admin());
			alimService.insertAlim(alimDTO);
			
			AlimReceiveDTO alimReceiveDTO = new AlimReceiveDTO();
			alimReceiveDTO.setAlim_seq(alimDTO.getAlim_seq());
			
			WorkerDTO workerDTO = new WorkerDTO();
			workerDTO.setSel_worker_seq(worker_seq_array);
			
			// 알림 수신자 token List
			List<WorkerDTO> tokenList = jobAlimService.selectInfoToken(workerDTO);
			
			//String[] tokenArray = new String[tokenList.size()];
			
			// 알림 수신자 Insert (forEach를 이용한 다중 insert)
			if(tokenList.size() > 0) {
				jobAlimReceiveDTO.setWorkerSeqList(tokenList);
				jobAlimService.insertInfoReceive(jobAlimReceiveDTO);
				
				alimReceiveDTO.setWorkerSeqList(tokenList);
				alimService.insertAlimReceive(alimReceiveDTO);
			}
			
//			for(int i = 0; i < tokenList.size(); i++) {
//				jobAlimReceiveDTO.setWorker_seq(tokenList.get(i).getWorker_seq());
//				jobAlimReceiveDTO.setWorkerSeqArray(tokenList);
//				//tokenArray[i] = tokenList.get(i).getPush_token();
//				
//				jobAlimService.insertInfoReceive(jobAlimReceiveDTO);
//				
//				//alimReceive 테이블
//				alimReceiveDTO.setWorker_seq(tokenList.get(i).getWorker_seq());
//				alimService.insertAlimReceive(alimReceiveDTO);
//			}
			
			transactionManager.commit(status);
			
			messageUtil.commThread("일가자 일자리", jobAlimDTO.getJobAlim_content(), alimDTO.getAlim_seq(), tokenList, jobAlimDTO.getMain_view_yn(), "I", "W", worker_seq_array.length);
			
			resultMap.put("jobAlimCnt", worker_seq_array.length);
			resultMap.put("successCnt", tokenList.size());
			resultMap.put("failCnt", worker_seq_array.length - tokenList.size());
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch (Exception e) {
			
			transactionManager.rollback(status);
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	/**
	 * 
	 * @Method Name : jobAlimList
	 * @작성일 : 2020. 6. 9.
	 * @작성자 : Park YunSoo
	 * @Method 설명 : 일가자 푸쉬 보낸 리스트
	 */
	/* TODO : 2020-06-11 안쓰는 로직 성급하게 만듬.... 
	 * @RequestMapping("/jobAlimList") public String jobAlimList(HttpSession
	 * session, Model model, JobAlimDTO jobAlimDTO) throws Exception { jobAlimDTO =
	 * setSessionToDTO(session, jobAlimDTO);
	 * 
	 * int cnt = jobAlimService.selectListCnt(jobAlimDTO);
	 * 
	 * if(cnt > 0) { List<JobAlimDTO> jobAlimList =
	 * jobAlimService.selectList(jobAlimDTO);
	 * 
	 * model.addAttribute("jobAlimList", jobAlimList); }
	 * 
	 * model.addAttribute("jobAlimDTO", jobAlimDTO); model.addAttribute("totalCnt",
	 * cnt);
	 * 
	 * return "/admin/jobAlim/jobAlimList.admin"; }
	 * 
	 *//**
		 * 
		 * @Method Name : ajaxJobAlimList
		 * @작성일 : 2020. 6. 9.
		 * @작성자 : Park YunSoo
		 * @Method 설명 : 일가자 푸쉬 보낸 리스트 ajax
		 */
	/*
	 * @RequestMapping("/ajaxJobAlimList")
	 * 
	 * @ResponseBody public Map<String, Object> ajaxJobAlimList(JobAlimDTO
	 * jobAlimDTO) { Map<String, Object> resultMap = new HashMap<String, Object>();
	 * 
	 * try { resultMap.put("code", Const.CODE_SUCCESS);
	 * 
	 * int cnt = jobAlimService.selectListCnt(jobAlimDTO);
	 * 
	 * List<JobAlimDTO> jobAlimList = jobAlimService.selectList(jobAlimDTO);
	 * 
	 * resultMap.put("jobAlimList", jobAlimList); resultMap.put("totalCnt", cnt);
	 * resultMap.put("jobAlimDTO", jobAlimDTO); } catch(Exception e) {
	 * e.printStackTrace();
	 * 
	 * resultMap.put("code", Const.CODE_SYSTEM_ERROR); resultMap.put("message",
	 * Const.MSG_SYSTEM_ERROR); }
	 * 
	 * return resultMap; }
	 * 
	 *//**
		 * 
		 * @Method Name : jobAlimEdit
		 * @작성일 : 2020. 6. 9.
		 * @작성자 : Park YunSoo
		 * @Method 설명 : 일가자 수정 페이지 이동
		 */
	/*
	 * @RequestMapping("/jobAlimEdit") public String jobAlimEdit(HttpSession
	 * session, Model model, JobAlimDTO jobAlimDTO, JobAlimReceiveDTO
	 * jobAlimReceiveDTO) throws Exception { jobAlimDTO = setSessionToDTO(session,
	 * jobAlimDTO);
	 * 
	 * JobAlimDTO resultDTO = jobAlimService.selectInfo(jobAlimDTO);
	 * List<JobAlimReceiveDTO> jobAlimReceiveList =
	 * jobAlimService.selectListReceive(jobAlimReceiveDTO);
	 * 
	 * model.addAttribute("jobAlimDTO", resultDTO);
	 * model.addAttribute("jobAlimReceiveList", jobAlimReceiveList);
	 * 
	 * return "/admin/jobAlim/jobAlimEdit.admin"; }
	 * 
	 *//**
		 * 
		 * @Method Name : updateInfo
		 * @작성일 : 2020. 6. 9.
		 * @작성자 : Park YunSoo
		 * @Method 설명 : 일가자 알림 수정
		 */
	/*
	 * @RequestMapping("/updateInfo")
	 * 
	 * @ResponseBody public Map<String, String> updateInfo(HttpServletRequest
	 * request, HttpServletResponse response, HttpSession session, JobAlimDTO
	 * jobAlimDTO) { Map<String, String> resultMap = new HashMap<String, String>();
	 * 
	 * try { jobAlimDTO = setSessionToDTO(session, jobAlimDTO);
	 * 
	 * jobAlimService.updateInfo(jobAlimDTO);
	 * 
	 * resultMap.put("code", Const.CODE_SUCCESS); }catch(Exception e) {
	 * e.printStackTrace(); resultMap.put("code", Const.CODE_SYSTEM_ERROR);
	 * resultMap.put("message", Const.MSG_SYSTEM_ERROR); }
	 * 
	 * return resultMap; }
	 * 
	 *//**
		 * 
		 * @Method Name : deleteInfo
		 * @작성일 : 2020. 6. 9.
		 * @작성자 : Park YunSoo
		 * @Method 설명 : 일가자 알림 삭제
		 *//*
			 * @RequestMapping("/deleteInfo")
			 * 
			 * @ResponseBody public Map<String, String> deleteInfo(HttpServletRequest
			 * request, HttpServletResponse response, HttpSession session, JobAlimDTO
			 * jobAlimDTO) { Map<String, String> resultMap = new HashMap<String, String>();
			 * 
			 * try { jobAlimDTO = setSessionToDTO(session, jobAlimDTO);
			 * 
			 * //알림 테이블 데이터 삭제 jobAlimService.deleteInfo(jobAlimDTO); //알림 받는 사람 테이블 데이터 삭제
			 * jobAlimService.deleteInfoReceive(jobAlimDTO);
			 * 
			 * resultMap.put("code", Const.CODE_SUCCESS); } catch(Exception e) {
			 * e.printStackTrace(); resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			 * resultMap.put("message", Const.MSG_SYSTEM_ERROR); }
			 * 
			 * return resultMap; }
			 */
}
