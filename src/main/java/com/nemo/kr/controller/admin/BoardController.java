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
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.BoardDTO;
import com.nemo.kr.dto.BoardReplyDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.service.BoardService;
import com.nemo.kr.service.FileService;
import com.nemo.kr.util.FileUtil;


@Controller
@RequestMapping("/admin/board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired BoardService boardService;
	@Autowired FileService 	fileService;

	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	// Session 설정
	private BoardDTO setSessionToDTO(HttpSession session, BoardDTO paramDTO) throws Exception {
		// Session 정보
		AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
		
		if( adminSession != null ) {
			paramDTO.setMod_admin(adminSession.getAdmin_id());
			paramDTO.setReg_admin(adminSession.getAdmin_id());
		}else {
			paramDTO.setMod_admin("");
			paramDTO.setReg_admin("");
		}
		
		return paramDTO;
	}

	/**
	  * @Method Name : boardGroupList
	  * @작성일 : 2019. 10. 4.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 게시판 관리
	
	  */
	@RequestMapping("/boardGroupList")
	public String boardGroupList(HttpServletRequest request, HttpSession session, Model model, BoardDTO boardDTO) throws Exception {
		String uri = request.getRequestURI();

		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);
		
		int cnt = boardService.selectInfoListCnt(boardDTO);
		
		if ( cnt > 0 ) {
			model.addAttribute("boardGroupList", boardService.selectInfoList(boardDTO));
		}
		
		model.addAttribute("htmlHeader", "게시판 관리");
		model.addAttribute("totalCnt", cnt);
		boardDTO.getPaging().setRowCount(cnt);

		return "/admin/board/boardGroupList.leftAdmin";
	}

	@RequestMapping(value="/boardGroupReg")
	public String boardGroupReg(HttpSession session, Model model, BoardDTO boardDTO) throws Exception {
		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);
		model.addAttribute("htmlHeader", "게시판 그룹 등록");
		model.addAttribute("boardDTO", boardDTO);

		return "/admin/board/boardGroupReg.leftAdmin";
	}

	@RequestMapping("/boardGroupRegProcess")
	@ResponseBody
	public Map<String, String> boardGroupRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);
			boardService.insertBoardInfo(boardDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	@RequestMapping(value="/boardGroupEdit")
	public String boardGroupEdit(HttpSession session, Model model,  BoardDTO boardDTO) throws Exception {
		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);

		//상세정보
		BoardDTO resultDTO = boardService.selectBoardInfo(boardDTO);
		
		model.addAttribute("htmlHeader", "게시판 그룹 수정");
		model.addAttribute("resultDTO", resultDTO);

		return "/admin/board/boardGroupEdit.leftAdmin";
	}

	@RequestMapping(value="/boardGroupEditProcess")
	@ResponseBody
	public Map<String, String> boardGroupEditProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);

			boardService.updateBoardInfo(boardDTO);

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}

	@RequestMapping("/boardList")
	public String boardList(HttpServletRequest request, HttpSession session, Model model, BoardDTO boardDTO) throws Exception {
		String uri = request.getRequestURI();

		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);
		boardDTO.setDel_yn("0");
		boardDTO.setUse_yn("1");
		
		BoardDTO boardGruop = boardService.selectBoardInfo(boardDTO);
		model.addAttribute("boardGroup", boardGruop);
		
		boardDTO.setBoard_group_seq(boardGruop.getBoard_group_seq());
		int cnt = boardService.selectListCnt(boardDTO);
		if ( cnt > 0 ) {
			model.addAttribute("boardList", boardService.selectList(boardDTO));
		}
		model.addAttribute("htmlHeader", "게시판 목록");
		model.addAttribute("totalCnt", cnt);
		boardDTO.getPaging().setRowCount(cnt);

		return "/admin/board/boardList.leftAdmin";
	}

	@RequestMapping(value="/boardReg")
	public String boardReg(HttpSession session, Model model, BoardDTO boardDTO) throws Exception {
		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);

		model.addAttribute("boardGroup", boardService.selectBoardInfo(boardDTO));
		model.addAttribute("htmlHeader", "게시판 등록");
		
		return "/admin/board/boardReg.leftAdmin";
	}
	
	@RequestMapping("/boardRegProcess")
	@ResponseBody
	public Map<String, String> boardRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			
			if( adminSession != null ) {
				boardDTO.setMod_admin(adminSession.getAdmin_id());
				boardDTO.setReg_admin(adminSession.getAdmin_id());
				boardDTO.setMember_seq(adminSession.getAdmin_seq());
			}else {
				boardDTO.setMod_admin("");
				boardDTO.setReg_admin("");
				boardDTO.setMember_seq("");
			}
//			boardDTO.setBoard_writer(adminSession.getAdmin_id());

			boardService.insertInfo(boardDTO);
			List<FileDTO> listFileDTO =  FileUtil.fileUpload(request, "board", boardDTO.getBoard_seq(), commonProperties.getProperty("uploadPath"), commonProperties.getProperty("fileUrl") );
			
			if( adminSession != null ) {
				fileService.insertInfo(adminSession.getAdmin_id(), listFileDTO);
			}else {
				fileService.insertInfo("", listFileDTO);
			}
		
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}


	/**
	  * @Method Name : boardRead
	  * @작성일 : 2019. 10. 15.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 게시팜 보기
	
	  */
	
	@RequestMapping(value="/boardDetail")
	public String boardRead(HttpSession session, Model model, BoardDTO boardDTO) throws Exception {
		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);

		//읽음 +1
		boardService.updateViewCount(boardDTO);

		//게시판정보
		BoardDTO boardGroup =  boardService.selectBoardInfo(boardDTO);
		model.addAttribute("boardGroup",boardGroup);
		//상세정보
		BoardDTO resultDTO = boardService.selectInfo(boardDTO);
		model.addAttribute("resultDTO", resultDTO);
		
		//댓글사용 게시판이면
		if("1".contentEquals(boardGroup.getReply_yn())) {
			BoardReplyDTO replyDTO = new BoardReplyDTO();
			replyDTO.setBoard_seq(boardDTO.getBoard_seq());
			replyDTO.setUse_yn("1");
			replyDTO.setDel_yn("0");
			replyDTO.getPaging().setPageSize(10);
			
			int cnt = boardService.getReplyListCnt(replyDTO);
			if( cnt > 0 ) {
				model.addAttribute("totalCnt", cnt);
				List<BoardReplyDTO> replyList =  boardService.getReplyList(replyDTO);
				model.addAttribute("replyList", replyList);
			}
			model.addAttribute("replyDTO", replyDTO);
		}
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setService_type("board");
		fileDTO.setService_seq(boardDTO.getBoard_seq());
		model.addAttribute("htmlHeader", "게시판 내용");
		model.addAttribute("fileList", fileService.selectFileList(fileDTO));
		return "/admin/board/boardDetail.leftAdmin";
	}
	
	@RequestMapping(value="/boardEdit")
	public String boardEdit(HttpSession session, Model model,  BoardDTO boardDTO) throws Exception {
		// Session 정보 설정
		boardDTO = setSessionToDTO(session, boardDTO);
		
		//게시판정보
		model.addAttribute("boardGroup", boardService.selectBoardInfo(boardDTO));
		
		//상세정보
		BoardDTO resultDTO = boardService.selectInfo(boardDTO);
		
		model.addAttribute("resultDTO", resultDTO);
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setService_type("board");
		fileDTO.setService_seq(boardDTO.getBoard_seq());
		
		model.addAttribute("fileList", fileService.selectFileList(fileDTO));
		model.addAttribute("fileListCnt", fileService.selectFileList(fileDTO).size());
		model.addAttribute("htmlHeader", "게시판 수정");

		return "/admin/board/boardEdit.leftAdmin";
	}

	@RequestMapping(value="/boardEditProcess")
	@ResponseBody
	public Map<String, Object> boardEditProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);

			boardService.updateInfo(boardDTO);
			List<FileDTO> listFileDTO =  FileUtil.fileUpload(request, "board", boardDTO.getBoard_seq() , commonProperties.getProperty("uploadPath"), commonProperties.getProperty("fileUrl") );

			if( adminSession != null ) {
				fileService.insertInfo(adminSession.getAdmin_id(), listFileDTO);
			}else {
				fileService.insertInfo("", listFileDTO);
			}

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	@RequestMapping(value="/boardDelProcess")
	@ResponseBody
	public Map<String, String> boardDelProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);

			boardService.deleteInfo(boardDTO);

			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	/**
	  * @Method Name : boardCheckPass
	  * @작성일 : 2019. 10. 15.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 게시글 패스워드 확인 게시판이 패스워드 사용 할때만 사용 한다.
	  */
	@RequestMapping(value="/boardCheckPass")
	@ResponseBody
	public Map<String, String> boardCheckPass(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);
			
			BoardDTO resultDTO = boardService.selectInfo(boardDTO);
			
			if(resultDTO.getBoard_pass() == null) {
				resultDTO.setBoard_pass("");
			}
			//패스워드 체크
			if( boardDTO.getBoard_pass().equals(resultDTO.getBoard_pass()) ){
				resultMap.put("code", Const.CODE_SUCCESS);	
			}else {
				resultMap.put("code", Const.CODE_USER_0014);
				resultMap.put("message", "비밀번호가 틀렸습니다");
			}

			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	/**
	  * @Method Name : boardCheckAdmin
	  * @작성일 : 2020. 12. 4.
	  * @작성자 : Na GilJin
	  * @Method 설명 : 게시물 작성자와 로그인한 유저의 아이디가 같은지 확인할 때 사용
	  */
	@RequestMapping(value="/boardCheckAdmin")
	@ResponseBody
	public Map<String, String> boardCheckAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);
			
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			String adminId = "";
			if(adminSession != null) {
				adminId = adminSession.getAdmin_id();
			}
			BoardDTO resultDTO = boardService.selectInfo(boardDTO);
			
			if( resultDTO == null ) {
				resultMap.put("message", "없는 게시물 입니다.");
				return resultMap;
			}
			
			//작성자 체크
			if( resultDTO.getBoard_writer() != null && resultDTO.getBoard_writer().equals(adminId) ) {
				resultMap.put("code", Const.CODE_SUCCESS);
			}else {
				resultMap.put("message", "게시물 작성자가 아닙니다.");
				return resultMap;
			}
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	
	/**
	  * @Method Name : boardReplySave
	  * @작성일 : 2019. 10. 15.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 댓글등록
	
	  */
	@RequestMapping(value="/replyRegProcess")
	@ResponseBody
	public Map<String, Object> replyRegProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardReplyDTO boardReplyDTO) {
		//리턴값
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);

			if( adminSession != null ) {
				boardReplyDTO.setMod_admin(adminSession.getAdmin_id());
				boardReplyDTO.setReg_admin(adminSession.getAdmin_id());
			}else {
				boardReplyDTO.setMod_admin("");
				boardReplyDTO.setReg_admin("");
			}
			boardReplyDTO.setUse_yn("1");
			boardReplyDTO.setDel_yn("0");
						
			//정보입력
			boardService.insertReply(boardReplyDTO);
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("replySeq", boardReplyDTO.getReply_seq());
			resultMap.put("replyList", boardService.getReplyList(boardReplyDTO));
			resultMap.put("totalCnt", boardService.getReplyListCnt(boardReplyDTO));
			resultMap.put("boardReplyDTO", boardReplyDTO);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/replyDelProcess")
	@ResponseBody
	public Map<String, Object> replyDelProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardReplyDTO boardReplyDTO) {
		//리턴값
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			if( adminSession != null ) {
				boardReplyDTO.setMod_admin(adminSession.getAdmin_id());
			}else {
				boardReplyDTO.setMod_admin("");
			}
			
			BoardReplyDTO resultDTO = boardService.replyInfo(boardReplyDTO);
			
			if( resultDTO.getReply_pass().equals(boardReplyDTO.getReply_pass()) || "0".equals(adminSession.getAuth_level()) ){
				boardService.delReply(boardReplyDTO);
				boardReplyDTO.setUse_yn("1");
				boardReplyDTO.setDel_yn("0");
				
				//해당페이지에 댓글이 없으면 이전페이지 댓글목록을 가져옴  
				List<BoardReplyDTO> replyList = boardService.getReplyList(boardReplyDTO);
				if( replyList.size() == 0 ) {
					if( boardReplyDTO.getPaging().getPageNo() > 1 ) {
						boardReplyDTO.getPaging().setPageNo( boardReplyDTO.getPaging().getPageNo() - 1 );
					}
					resultMap.put("replyList", boardService.getReplyList(boardReplyDTO));
				}else {
					resultMap.put("replyList", replyList);
				}
				
				resultMap.put("boardReplyDTO", boardReplyDTO);
				resultMap.put("totalCnt", boardService.getReplyListCnt(boardReplyDTO));
				resultMap.put("code", Const.CODE_SUCCESS);	
			}else {
				resultMap.put("code", Const.CODE_USER_0009);
				resultMap.put("message", Const.MSG_USER_0009);
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : boardCheckPass
	  * @작성일 : 2019. 10. 15.
	  * @작성자 : nemojjang
	  * @변경이력 : 
	  * @Method 설명 : 댓글 패스워드 확인
	
	  */
	/*
	@RequestMapping(value="/replyCheckPass")
	@ResponseBody
	public Map<String, String> replyCheckPass(HttpServletRequest request, HttpServletResponse response, HttpSession session, ReplyDTO rplyDTO) {

		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			
			ReplyDTO resultDTO = boardService.replyInfo(rplyDTO);
			
			//패스워드 체크
			if( rplyDTO.getReplyPass().equals( resultDTO.getReplyPass())){
				resultMap.put("code", Const.CODE_SUCCESS);	
			}else {
				resultMap.put("code", Const.CODE_USER_0014);
				resultMap.put("message", Const.MSG_USER_0014);
			}

			
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}

		return resultMap;
	}
	*/
	
	@RequestMapping(value="/replyModifyProcess")
	@ResponseBody
	public Map<String, Object> replyModifyProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardReplyDTO boardReplyDTO) {
		//리턴값
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			if( adminSession != null ) {
				boardReplyDTO.setMod_admin(adminSession.getAdmin_id());
			}else {
				boardReplyDTO.setMod_admin("");
			}
			boardService.updateReply(boardReplyDTO);
			
			boardReplyDTO.setUse_yn("1");
			boardReplyDTO.setDel_yn("0");
			resultMap.put("replyList", boardService.getReplyList(boardReplyDTO));
			
			resultMap.put("code", Const.CODE_SUCCESS);
//			resultMap.put("replySeq", replyDTO.getReplySeq());
//			resultMap.put("parentSeq", replyDTO.getParentSeq());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/getReplyInfo")
	@ResponseBody
	public Map<String, Object> getReplyInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardReplyDTO boardReplyDTO) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			
			if( adminSession != null ) {
				boardReplyDTO.setMod_admin(adminSession.getAdmin_id());
			}else {
				boardReplyDTO.setMod_admin("");
			}
			BoardReplyDTO resultDTO = boardService.replyInfo(boardReplyDTO); 
			
			//패스워드 체크
			if( "0".equals(adminSession.getAuth_level()) || resultDTO.getReply_pass().equals( boardReplyDTO.getReply_pass() ) ){
				resultMap.put("boardReplyDTO", resultDTO);
				resultMap.put("code", Const.CODE_SUCCESS);	
			}else {
				resultMap.put("code", Const.CODE_USER_0009);
				resultMap.put("message", Const.MSG_USER_0009);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("mssage", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/getBoardGroupList")
	@ResponseBody
	public Map<String, Object> getBoardGroupList(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);

			int cnt = boardService.selectInfoListCnt(boardDTO);
			if ( cnt > 0 ) {
				resultMap.put("boardGroupList", boardService.selectInfoList(boardDTO));
			}
			resultMap.put("boardDTO", boardDTO);
			resultMap.put("totalCnt", cnt);
			boardDTO.getPaging().setRowCount(cnt);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/getBoardList")
	@ResponseBody
	public Map<String, Object> getBoardList(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			// Session 정보 설정
			boardDTO = setSessionToDTO(session, boardDTO);
			boardDTO.setDel_yn("0");
			boardDTO.setUse_yn("1");
			if( boardDTO.getQuery() != null ) {
				boardDTO.setBoard_group_seq( boardDTO.getQuery() );
			}
			
			resultMap.put("boardGroup", boardService.selectBoardInfo(boardDTO));
			
			int cnt = boardService.selectListCnt(boardDTO);
			if ( cnt > 0 ) {
				resultMap.put("boardList", boardService.selectList(boardDTO));
			}
			resultMap.put("boardDTO", boardDTO);
			resultMap.put("totalCnt", cnt);
			boardDTO.getPaging().setRowCount(cnt);
			
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/getBoardInfo")
	@ResponseBody
	public Map<String, Object> getBoardInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardDTO boardDTO) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			//읽음 +1
			boardService.updateViewCount(boardDTO);
			
			resultMap.put("resultDTO", boardService.selectInfo(boardDTO));
			
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("mssage", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/getReplyList")
	@ResponseBody
	public Map<String, Object> getReplyList(HttpServletRequest request, HttpServletResponse response, HttpSession session, BoardReplyDTO boardReplyDTO) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			if( boardReplyDTO.getQuery() != null ) {
				boardReplyDTO.setBoard_seq(boardReplyDTO.getQuery());
			}
			boardReplyDTO.getPaging().setPageSize(10);
			boardReplyDTO.setUse_yn("1");
			boardReplyDTO.setDel_yn("0");
			int totalCnt = boardService.getReplyListCnt(boardReplyDTO);
			if( totalCnt > 0 ) {
				resultMap.put("replyList", boardService.getReplyList(boardReplyDTO));
				resultMap.put("totalCnt", totalCnt);
			}
			resultMap.put("boardReplyDTO", boardReplyDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("mssage", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/fileDown")
	public void fileDown(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		
		String name = request.getParameter("name");
		String org_name = request.getParameter("org_name");
		String path = request.getParameter("path");

		FileUtil.fileDown(name, path, org_name, request, response);
	}
}

