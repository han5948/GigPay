package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.BoardDTO;
import com.nemo.kr.dto.BoardReplyDTO;
import com.nemo.kr.dto.ReplyDTO;
import com.nemo.kr.mapper.ilgaja.write.BoardMapper;
import com.nemo.kr.service.BoardService;


/**
 * 게시판 관리 ServiceImpl
 * @date 2018. 7. 5.
 * @desc  
 *
 */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;


	@Override
	public int selectListCnt(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectListCnt(paramDTO);
	}

	@Override
	public List<BoardDTO> selectList(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectList(paramDTO);
	}

	@Override
	public void insertInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.insertInfo(paramDTO);
	}

	@Override
	public BoardDTO selectInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectInfo(paramDTO);
	}

	@Override
	public void updateInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.updateInfo(paramDTO);
	}

	@Override
	public void deleteInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.deleteInfo(paramDTO);
	}

	@Override
	public int selectInfoListCnt(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectInfoListCnt(paramDTO);
	}

	@Override
	public List<BoardDTO> selectInfoList(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectInfoList(paramDTO);
	}

	@Override
	public void insertBoardInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.insertBoardInfo(paramDTO);
	}

	@Override
	public BoardDTO selectBoardInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardInfo(paramDTO);
	}
	@Override
	public void updateBoardInfo(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.updateBoardInfo(paramDTO);
	}

	@Override
	public void updateViewCount(BoardDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.updateViewCount(paramDTO);
	}

	//public void insertReply(ReplyDTO paramDTO) {
	@Override
	public void insertReply(BoardReplyDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.insertReply(paramDTO);
	}

	//public List<ReplyDTO> getReplyList(ReplyDTO paramDTO) {
	@Override
	public List<BoardReplyDTO> getReplyList(BoardReplyDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.getReplyList(paramDTO);
	}

	//public ReplyDTO replyInfo(ReplyDTO paramDTO) {
	@Override
	public BoardReplyDTO replyInfo(BoardReplyDTO paramDTO) {
		// TODO Auto-generated method stub
		return boardMapper.replyInfo(paramDTO);
	}

	//public void delReply(ReplyDTO paramDTO) {
	@Override
	public void delReply(BoardReplyDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.delReply(paramDTO);
	}

	//public void updateReply(ReplyDTO paramDTO) {
	@Override
	public void updateReply(BoardReplyDTO paramDTO) {
		// TODO Auto-generated method stub
		boardMapper.updateReply(paramDTO);
	}

	/**
	 * 공지사항 상위 10개 가져오기
	 */
	@Override
	public List<BoardDTO>  selectTopList() {
		
		BoardDTO paramDTO = new BoardDTO();
		return boardMapper.selectList(paramDTO);
		
	}

	@Override
	public int getReplyListCnt(BoardReplyDTO boardReplyDTO) {
		// TODO Auto-generated method stub
		return boardMapper.getReplyListCnt(boardReplyDTO);
	}

}