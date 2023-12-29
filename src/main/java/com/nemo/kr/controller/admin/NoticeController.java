package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.NotiReplyDTO;
import com.nemo.kr.dto.NoticeDTO;
import com.nemo.kr.dto.ReplyDTO;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.NoticeService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

@Controller
@RequestMapping("/admin/notice")
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@Autowired NoticeService noticeService;
	@Autowired CompanyService companyService;
	
	// Session 설정
	private NoticeDTO setSessionToDTO(HttpSession session, NoticeDTO noticeDTO) throws Exception {
	    // Session 정보
	    int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
	    String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
	    String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
	    String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
	    
	    // Session 설정
	    noticeDTO.setAdminLevel(""+ auth_level);
	    noticeDTO.setNotice_writer(company_name);
	    noticeDTO.setReg_admin(admin_id);
	    noticeDTO.setCompany_seq(company_seq);
	    
	    noticeDTO.setAdminAuth(company_seq);
	    
	    return noticeDTO;
	}
	  
	//공지사항 리스트
	@RequestMapping("/noticeList")
	public String noticeList(HttpSession session, Model model, NoticeDTO noticeDTO) throws Exception {
		noticeDTO = setSessionToDTO(session, noticeDTO);
		noticeDTO.getPaging().setPageSize(15);
		
		int cnt = noticeService.selectListCnt(noticeDTO);
		
		if(cnt > 0) {
			List<NoticeDTO> noticeList = noticeService.selectList(noticeDTO);

			model.addAttribute("noticeList", noticeList);
		}
		
		model.addAttribute("htmlHeader" , "공지사항");
		model.addAttribute("noticeDTO", noticeDTO);
		model.addAttribute("totalCnt", cnt);
		
		return "/admin/notice/noticeList.admin";
	}
	
	//공지사항 리스트 ajax
	@RequestMapping("/getNoticeList")
	@ResponseBody
	public Map<String, Object> getNoticeList(HttpSession session, NoticeDTO noticeDTO) throws Exception {
		noticeDTO = setSessionToDTO(session, noticeDTO);
		noticeDTO.getPaging().setPageSize(15);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			int cnt = noticeService.selectListCnt(noticeDTO);
			
			List<NoticeDTO> noticeList = noticeService.selectList(noticeDTO);
			
			resultMap.put("noticeList", noticeList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("noticeDTO", noticeDTO);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//공지사항 글쓰기
	@RequestMapping("/noticeWrite")
	public String noticeWrite(HttpSession session, Model model, NoticeDTO noticeDTO) throws Exception {
		noticeDTO = setSessionToDTO(session, noticeDTO);
		
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		model.addAttribute("htmlHeader" , "공지사항 작성");
		model.addAttribute("companyList", companyList);
		model.addAttribute("noticeDTO", noticeDTO);
		
		return "/admin/notice/noticeWrite.admin";
	}
	
	//공지사항 글쓰기 로직
	@RequestMapping("/insertNotice")
	@ResponseBody
	public Map<String, String> insertNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, NoticeDTO noticeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			noticeDTO = setSessionToDTO(session, noticeDTO);

			noticeService.insertNotice(noticeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//공지사항 수정
	@RequestMapping("/noticeEdit")
	public String noticeEdit(HttpSession session, Model model, NoticeDTO noticeDTO) throws Exception {
		noticeDTO = setSessionToDTO(session, noticeDTO);
		
		NoticeDTO resultDTO = noticeService.selectInfo(noticeDTO);
		
		noticeService.updateViewCnt(noticeDTO);
		
		CompanyDTO companyDTO = new CompanyDTO();
		NotiReplyDTO notiReplyDTO = new NotiReplyDTO();
		
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		
		notiReplyDTO.setNotice_seq(noticeDTO.getNotice_seq());
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		List<NotiReplyDTO> replyList = noticeService.replyList(notiReplyDTO);
		
		model.addAttribute("companyList", companyList);
		model.addAttribute("replyList", replyList);
		model.addAttribute("noticeDTO", resultDTO);
		model.addAttribute("loginId", noticeDTO.getReg_admin());
		model.addAttribute("htmlHeader" , "공지사항 상세");
		
		if(noticeDTO.getAdminLevel().equals("0") ||
			resultDTO.getReg_admin().equals(noticeDTO.getReg_admin())) {
			model.addAttribute("htmlHeader" , "공지사항 수정");
			
			return "/admin/notice/noticeEdit.admin";
		}
		
		if(noticeDTO.getAdminLevel().equals("1")) {
			if(resultDTO.getWriter_auth().equals("0") ||
				(Integer.parseInt(resultDTO.getWriter_auth()) < 2 &&
				!resultDTO.getReg_admin().equals(noticeDTO.getReg_admin()))) {
				return "/admin/notice/noticeDetail.admin";
			}else {
				model.addAttribute("htmlHeader" , "공지사항 수정");
				
				return "/admin/notice/noticeEdit.admin";
			}
		}
		
		return "/admin/notice/noticeDetail.admin";
	}
	
	//공지사항 수정 로직
	@RequestMapping("/updateNotice")
	@ResponseBody
	public Map<String, String> updateNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, NoticeDTO noticeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			noticeDTO = setSessionToDTO(session, noticeDTO);
			
			noticeService.updateNotice(noticeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//공지사항 삭제
	@RequestMapping("/deleteNotice")
	@ResponseBody
	public Map<String, String> deleteNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session, NoticeDTO noticeDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			noticeDTO = setSessionToDTO(session, noticeDTO);
			
			noticeService.deleteNotice(noticeDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//댓글 등록
	@RequestMapping("/insertReply")
	@ResponseBody
	public Map<String, Object> insertReply(HttpServletRequest request, HttpServletResponse response, HttpSession session, NotiReplyDTO notiReplyDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			noticeService.insertReply(notiReplyDTO);
			
			List<NotiReplyDTO> replyList = noticeService.replyList(notiReplyDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("replyList", replyList);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	//댓글 삭제
	@RequestMapping("/deleteReply")
	@ResponseBody
	public Map<String, Object> deleteReply(HttpServletRequest request, HttpServletResponse response, HttpSession session, NotiReplyDTO notiReplyDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			if(notiReplyDTO.getParent_seq().equals("0")) {
				noticeService.deleteReply(notiReplyDTO);
				noticeService.deleteReplyChild(notiReplyDTO);
			}else {
				noticeService.deleteReplyChild(notiReplyDTO);
			}
			
			List<NotiReplyDTO> replyList = noticeService.replyList(notiReplyDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("replyList", replyList);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
