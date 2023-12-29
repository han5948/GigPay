package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.NotiReplyDTO;
import com.nemo.kr.dto.NoticeDTO;

/**

  * @FileName : NoticeMapper.java

  * @Project : ilgaja

  * @Date : 2020. 6. 1. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface NoticeMapper {
	public int selectListCnt(NoticeDTO noticeDTO);
	
	public List<NoticeDTO> selectList(NoticeDTO noticeDTO);
	
	public void insertNotice(NoticeDTO noticeDTO);
	
	public void updateViewCnt(NoticeDTO noticeDTO);
	
	public NoticeDTO selectInfo(NoticeDTO noticeDTO);
	
	public void updateNotice(NoticeDTO noticeDTO);
	
	public void deleteNotice(NoticeDTO noticeDTO);
	
	public List<NotiReplyDTO> replyList(NotiReplyDTO notiReplyDTO);
	
	public void insertReply(NotiReplyDTO notiReplyDTO);
	
	public void deleteReply(NotiReplyDTO notiReplyDTO);

	public void deleteReplyChild(NotiReplyDTO notiReplyDTO);

	public NoticeDTO selectOneInfo(NoticeDTO noticeParam);
}
