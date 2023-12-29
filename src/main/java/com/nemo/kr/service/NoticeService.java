package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.NotiReplyDTO;
import com.nemo.kr.dto.NoticeDTO;

/**
  * @FileName : NoticeService.java

  * @Project : ilgaja

  * @Date : 2020. 6. 1. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface NoticeService {
	//게시판 리스트 cnt
	public int selectListCnt(NoticeDTO noticeDTO);
	//게시판 리스트
	public List<NoticeDTO> selectList(NoticeDTO noticeDTO);
	//게시판 작성
	public void insertNotice(NoticeDTO noticeDTO);
	//게시판 조회수 증가
	public void updateViewCnt(NoticeDTO noticeDTO);
	//게시판 상세
	public NoticeDTO selectInfo(NoticeDTO noticeDTO);
	//게시판 수정
	public void updateNotice(NoticeDTO noticeDTO);
	//게시판 삭제
	public void deleteNotice(NoticeDTO noticeDTO);
	//댓글 리스트
	public List<NotiReplyDTO> replyList(NotiReplyDTO notiReplyDTO);
	//댓글 등록
	public void insertReply(NotiReplyDTO notiReplyDTO);
	//댓글 삭제
	public void deleteReply(NotiReplyDTO notiReplyDTO);
	//댓글 원본 삭제시 답글 삭제
	public void deleteReplyChild(NotiReplyDTO notiReplyDTO);
	
	//메인에 노출할 1개의 공지사항
	public NoticeDTO selectOneInfo(NoticeDTO noticeParam);
}
