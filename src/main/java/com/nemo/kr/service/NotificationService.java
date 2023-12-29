package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.NotificationDTO;

public interface NotificationService { 
	//게시판 리스트 cnt
	public int selectListCnt(NotificationDTO notificationDTO);
	//게시판 리스트
	public List<NotificationDTO> selectList(NotificationDTO notificationDTO);
	
	//소장용 앱에서사용
	public int selectNotificationListCnt(NotificationDTO notificationDTO);
	public List<NotificationDTO> selectNotificationList(NotificationDTO notificationDTO);

}
