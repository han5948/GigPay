package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.NotiReplyDTO;
import com.nemo.kr.dto.NoticeDTO;
import com.nemo.kr.mapper.ilgaja.write.NoticeMapper;
import com.nemo.kr.service.NoticeService;

/**

  * @FileName : NoticeServiceImpl.java

  * @Project : ilgaja

  * @Date : 2020. 6. 1. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired 
	NoticeMapper noticeMapper;
	
	@Override
	public int selectListCnt(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		return noticeMapper.selectListCnt(noticeDTO);
	}
	
	@Override
	public List<NoticeDTO> selectList(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		return noticeMapper.selectList(noticeDTO);
	}
	
	@Override
	public void insertNotice(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		noticeMapper.insertNotice(noticeDTO);
	}
	
	@Override
	public void updateViewCnt(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		noticeMapper.updateViewCnt(noticeDTO);
	}
	
	@Override
	public NoticeDTO selectInfo(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		return noticeMapper.selectInfo(noticeDTO);
	}
	
	@Override
	public void updateNotice(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		noticeMapper.updateNotice(noticeDTO);
	}
	
	@Override
	public void deleteNotice(NoticeDTO noticeDTO) {
		// TODO Auto-generated method stub
		noticeMapper.deleteNotice(noticeDTO);
	}
	
	@Override
	public List<NotiReplyDTO> replyList(NotiReplyDTO notiReplyDTO) {
		// TODO Auto-generated method stub
		return noticeMapper.replyList(notiReplyDTO);
	}
	
	@Override
	public void insertReply(NotiReplyDTO notiReplyDTO) {
		// TODO Auto-generated method stub
		noticeMapper.insertReply(notiReplyDTO);
	}
	
	@Override
	public void deleteReply(NotiReplyDTO notiReplyDTO) {
		// TODO Auto-generated method stub
		noticeMapper.deleteReply(notiReplyDTO);
	}
	
	@Override
	public void deleteReplyChild(NotiReplyDTO notiReplyDTO) {
		// TODO Auto-generated method stub
		noticeMapper.deleteReplyChild(notiReplyDTO);
	}

	@Override
	public NoticeDTO selectOneInfo(NoticeDTO noticeParam) {
		// TODO Auto-generated method stub
		return noticeMapper.selectOneInfo(noticeParam);
	}
}
