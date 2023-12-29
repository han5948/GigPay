package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.BoardDTO;
import com.nemo.kr.dto.BoardReplyDTO;


/**
 * 게시판 관리 Service
 * @date 2018. 7. 5.
 * @desc 
 *
 */
public interface BoardService {
	public int selectListCnt(BoardDTO paramDTO);

	public List<BoardDTO> selectList(BoardDTO paramDTO);

	public void insertInfo(BoardDTO paramDTO);

	public BoardDTO selectInfo(BoardDTO paramDTO);

	public void updateInfo(BoardDTO paramDTO);

	public void deleteInfo(BoardDTO paramDTO);

	//게시판 관리 리스트
	public int selectInfoListCnt(BoardDTO boardDTO);

	public List<BoardDTO> selectInfoList(BoardDTO boardDTO);

	public void insertBoardInfo(BoardDTO boardDTO);

	public BoardDTO selectBoardInfo(BoardDTO boardDTO);
	
	public void updateBoardInfo(BoardDTO paramDTO);

	public void updateViewCount(BoardDTO boardDTO);

	//public void insertReply(ReplyDTO replyDTO);
	public void insertReply(BoardReplyDTO boardReplyDTO);

	//public List<ReplyDTO> getReplyList(ReplyDTO replyDTO);
	public List<BoardReplyDTO> getReplyList(BoardReplyDTO boardReplyDTO);
	
	//public ReplyDTO replyInfo(ReplyDTO replyDTO);
	public BoardReplyDTO replyInfo(BoardReplyDTO boardReplyDTO);

	//public void delReply(ReplyDTO replyDTO);
	public void delReply(BoardReplyDTO boardReplyDTO);

	//public void updateReply(ReplyDTO replyDTO);
	public void updateReply(BoardReplyDTO boardReplyDTO);
	
	//공지사항 상위10개 가져오기
	public List<BoardDTO>  selectTopList();
	
	public int getReplyListCnt(BoardReplyDTO boardReplyDTO);
}