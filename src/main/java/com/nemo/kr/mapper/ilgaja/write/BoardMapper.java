package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.BoardDTO;
import com.nemo.kr.dto.BoardReplyDTO;
import com.nemo.kr.dto.ReplyDTO;


/**
 * 게시판 Mapper
 * @author NEMODREAM Co., Ltd.
 *
 */
public interface BoardMapper {

	public int selectListCnt(BoardDTO paramDTO);

	public List<BoardDTO> selectList(BoardDTO paramDTO);

	public void insertInfo(BoardDTO paramDTO);

	public BoardDTO selectInfo(BoardDTO paramDTO);

	public void updateInfo(BoardDTO paramDTO);

	public void deleteInfo(BoardDTO paramDTO);

	public int selectInfoListCnt(BoardDTO paramDTO);

	public List<BoardDTO> selectInfoList(BoardDTO paramDTO);

	public void insertBoardInfo(BoardDTO paramDTO);

	public BoardDTO selectBoardInfo(BoardDTO paramDTO);

	public void updateBoardInfo(BoardDTO paramDTO);

	public void updateViewCount(BoardDTO paramDTO);

	//public void insertReply(ReplyDTO paramDTO);
	public void insertReply(BoardReplyDTO paramDTO);

	//public List<ReplyDTO> getReplyList(ReplyDTO paramDTO);
	public List<BoardReplyDTO> getReplyList(BoardReplyDTO paramDTO);

	//public ReplyDTO replyInfo(ReplyDTO paramDTO);
	public BoardReplyDTO replyInfo(BoardReplyDTO paramDTO);

	//public void delReply(ReplyDTO paramDTO);
	public void delReply(BoardReplyDTO paramDTO);

	//public void updateReply(ReplyDTO paramDTO);
	public void updateReply(BoardReplyDTO paramDTO);
	
	public int getReplyListCnt(BoardReplyDTO boardReplyDTO);



}