package com.nemo.kr.controller.admin;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.AlimService;
import com.nemo.kr.service.PushSendService;
import com.nemo.kr.service.WorkerService;
import com.nemo.kr.util.MessageUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/alim")
public class AlimController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired AlimService alimService;
	@Autowired PushSendService pushSendService;
	@Autowired WorkerService workerService;
	@Autowired MessageUtil messageUtil;
	
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	// Session 설정
	private AlimDTO setSessionToDTO(HttpSession session, AlimDTO alimDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		
		// Session 설정
		alimDTO.setCompany_seq(company_seq);
		alimDTO.setAdminLevel(""+ auth_level);
		alimDTO.setReg_admin(admin_id);
		alimDTO.setAlim_writer(company_name);
		alimDTO.setFlag("W");
		
		return alimDTO;
	}
	
	//알림 작성 팝업
	@RequestMapping("/alimWrite")
	public String alimWrite(Model model, AlimReceiveDTO alimReciveDTO) {
		model.addAttribute("worker_seq", alimReciveDTO.getWorker_seq());
		
		return "/admin/alim/alimWrite.popup_admin";
	}
	
	@RequestMapping("/alimManagerWrite")
	public String alimManagerWrite(Model model, ManagerDTO managerDTO) {
		model.addAttribute("manager_seq", managerDTO.getManager_seq());
		
		return "/admin/alim/alimWrite.popup_admin";
	}
	
	//알림 보내기
	/**
	  * @Method Name : insertAlim
	  * @작성일 : 2020. 11. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 :
	  */
	@RequestMapping("/insertAlim")
	@ResponseBody
	public Map<String, Object> insertAlim(HttpServletRequest request, HttpServletResponse response, HttpSession session, AlimDTO alimDTO, AlimReceiveDTO alimReceiveDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			alimDTO = setSessionToDTO(session, alimDTO);
			
			String worker_seq = alimReceiveDTO.getWorker_seq();
			String[] sel_worker_seq = worker_seq.split(",");
			
			if(alimDTO.getMain_view_yn() == null) {
				alimDTO.setMain_view_yn("0");
			}

			// 알림 Insert
			alimService.insertAlim(alimDTO);
			alimReceiveDTO.setAlim_seq(alimDTO.getAlim_seq());
			
			WorkerDTO workerDTO = new WorkerDTO();
			workerDTO.setSel_worker_seq(sel_worker_seq);
			
			// 알림 수신자 List
			List<WorkerDTO> tokenList = alimService.selectReceiveTokenInfo(workerDTO);
			
			//String[] tokenArray = new String[tokenList.size()];
			
			// 알림 수신자 Insert (forEach를 이용한 다중 insert)
			if(tokenList.size() > 0) {
				alimReceiveDTO.setWorkerSeqList(tokenList);
				alimService.insertAlimReceive(alimReceiveDTO);
			}
			
			// 알림 수신자 Insert
//			for(int i = 0; i < tokenList.size(); i++) {
//				alimReceiveDTO.setWorker_seq(tokenList.get(i).getWorker_seq());
//					
//				//tokenArray[i] = tokenList.get(i).getPush_token();
//				
//				alimService.insertAlimReceive(alimReceiveDTO);
//			}
			
			messageUtil.commThread(alimDTO.getAlim_title(), alimDTO.getAlim_content(), alimDTO.getAlim_seq(), tokenList, alimDTO.getMain_view_yn(), "N", "W", sel_worker_seq.length);
				
			resultMap.put("alimCnt", sel_worker_seq.length);
			resultMap.put("successCnt", tokenList.size());
			resultMap.put("failCnt", sel_worker_seq.length - tokenList.size());
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/insertManagerAlim")
	@ResponseBody
	public Map<String, Object> insertManagerAlim(HttpServletRequest request, HttpServletResponse response, HttpSession session, AlimDTO alimDTO, AlimReceiveDTO alimReceiveDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			alimDTO = setSessionToDTO(session, alimDTO);
			
			String manager_seq = alimReceiveDTO.getManager_seq();
			String[] sel_manager_seq = manager_seq.split(",");
			
			if(alimDTO.getMain_view_yn() == null) {
				alimDTO.setMain_view_yn("0");
			}

			// 알림 Insert
			alimService.insertAlim(alimDTO);
			alimReceiveDTO.setAlim_seq(alimDTO.getAlim_seq());
			
			ManagerDTO managerDTO = new ManagerDTO();
			
			managerDTO.setSel_manager_seq(sel_manager_seq);
			
			// 알림 수신자 List
			List<ManagerDTO> tokenList = alimService.selectManagerReceiveTokenInfo(managerDTO);
			
			//String[] tokenArray = new String[tokenList.size()];
			
			if(tokenList.size() > 0) {
				alimReceiveDTO.setManagerSeqList(tokenList);
				alimService.insertManagerAlimReceive(alimReceiveDTO);
			}
			// 알림 수신자 Insert
			//for(int i = 0; i < tokenList.size(); i++) {
				//alimReceiveDTO.setManager_seq(tokenList.get(i).getManager_seq());
					
				//tokenArray[i] = tokenList.get(i).getPush_token();
				
				//alimService.insertAlimReceive(alimReceiveDTO);
			//}
			
			messageUtil.commManagerThread(alimDTO.getAlim_title(), alimDTO.getAlim_content(), "0", tokenList, alimDTO.getMain_view_yn(), "N", "W", sel_manager_seq.length);
				
			resultMap.put("alimCnt", sel_manager_seq.length);
			resultMap.put("successCnt", tokenList.size());
			resultMap.put("failCnt", sel_manager_seq.length - tokenList.size());
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//알림 리스트
	/**
	  * @Method Name : alimList
	  * @작성일 : 2020. 11. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 :
	  */
	@RequestMapping("/alimList")
	public String alimList(HttpSession session, Model model, AlimDTO alimDTO) throws Exception {
		alimDTO = setSessionToDTO(session, alimDTO);
		alimDTO.getPaging().setPageSize(15);
		
		int cnt = alimService.selectListCnt(alimDTO);
		
		if(cnt > 0) {
			List<AlimDTO> alimList = alimService.selectList(alimDTO);
			
			model.addAttribute("alimList", alimList);
		}
		
		model.addAttribute("alimDTO", alimDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader" , "알림");
		
		return "/admin/alim/alimList.admin";
	}
	
	//알림 리스트 ajax
	@RequestMapping("/ajaxAlimList")
	@ResponseBody
	public Map<String, Object> ajaxAlimList(HttpSession session, AlimDTO alimDTO) throws Exception {
		alimDTO = setSessionToDTO(session, alimDTO);
		alimDTO.getPaging().setPageSize(15);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = alimService.selectListCnt(alimDTO);
			
			List<AlimDTO> alimList = alimService.selectSearchList(alimDTO);
			
			resultMap.put("alimList", alimList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("alimDTO", alimDTO);
		} catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/alimSearchList")
	public String alimSearchList(HttpSession session, Model model, AlimDTO alimDTO) throws Exception {
		alimDTO = setSessionToDTO(session, alimDTO);
		alimDTO.getPaging().setPageSize(15);
		
		int cnt = alimService.selectListCnt(alimDTO);
		
		if(cnt > 0) {
			List<AlimDTO> alimList = alimService.selectSearchList(alimDTO);
			
			model.addAttribute("alimList", alimList);
		}
		
		model.addAttribute("alimDTO", alimDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader" , "알림");
		
		return "/admin/alim/alimList.admin";
	}
	
	//알림 수정
	@RequestMapping("/alimEdit")
	public String alimEdit(HttpSession session, Model model, AlimDTO alimDTO, AlimReceiveDTO alimReceiveDTO) throws Exception {
		alimDTO = setSessionToDTO(session, alimDTO);
		
		AlimDTO resultDTO = alimService.selectInfo(alimDTO);
		List<AlimReceiveDTO> alimReceiveList = alimService.selectReceiveList(alimReceiveDTO);
		
		model.addAttribute("alimDTO", resultDTO);
		model.addAttribute("alimReceiveList", alimReceiveList);
		model.addAttribute("htmlHeader" , "알림 상세");
		
		if(alimDTO.getAdminLevel().equals("0") ||
			resultDTO.getReg_admin().equals(alimDTO.getReg_admin())) {
			model.addAttribute("htmlHeader" , "알림 수정");
			
			return "/admin/alim/alimEdit.admin";
		}
		
		if(alimDTO.getAdminLevel().equals("1")) {
			if(resultDTO.getWriter_auth().equals("0") ||
				(Integer.parseInt(resultDTO.getWriter_auth()) < 2 &&
				!resultDTO.getReg_admin().equals(alimDTO.getReg_admin()))) {
				return "/admin/alim/alimDetail.admin";
			}else {
				model.addAttribute("htmlHeader" , "알림 수정");
				
				return "/admin/alim/alimEdit.admin";
			}
		}
		
		return "/admin/alim/alimDetail.admin";
	}
	
	//알림 수정 로직
	@RequestMapping("/updateAlim")
	@ResponseBody
	public Map<String, String> updateAlim(HttpServletRequest request, HttpServletResponse response, HttpSession session, AlimDTO alimDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			alimDTO = setSessionToDTO(session, alimDTO);
			
			alimService.updateAlim(alimDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//알림 삭제 로직
	@RequestMapping("/deleteAlim")
	@ResponseBody
	public Map<String, String> deleteNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, AlimDTO alimDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			alimDTO = setSessionToDTO(session, alimDTO);
			
			//알림 테이블 데이터 삭제
			alimService.deleteAlim(alimDTO);
			//알림 받는 사람 테이블 데이터 삭제
			alimService.deleteReceiveAlim(alimDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
