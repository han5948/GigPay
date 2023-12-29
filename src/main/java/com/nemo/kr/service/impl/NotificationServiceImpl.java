package com.nemo.kr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.NotificationDTO;
import com.nemo.kr.mapper.ilgaja.write.NotificationMapper;
import com.nemo.kr.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired NotificationMapper notificationMapper;
	
	@Override
	public int selectListCnt(NotificationDTO notificationDTO) {
		if(notificationDTO.getAdminLevel().equals("0")) {
			return notificationMapper.selectSuperAdminListCount(notificationDTO);
		}
		return notificationMapper.selectListCnt(notificationDTO);
	}

	@Override
	public List<NotificationDTO> selectList(NotificationDTO notificationDTO) {
		// TODO Auto-generated method stub
		if(notificationDTO.getAdminLevel().equals("0")) {
			return notificationMapper.selectSuperAdminList(notificationDTO);
		}
		return notificationMapper.selectList(notificationDTO);
	}

	@Override
	public int selectNotificationListCnt(NotificationDTO notificationDTO) {
		// TODO Auto-generated method stub
		return notificationMapper.selectNotificationListCnt(notificationDTO);
	}

	@Override
	public List<NotificationDTO> selectNotificationList(NotificationDTO notificationDTO) {
		// TODO Auto-generated method stub
		return notificationMapper.selectNotificationList(notificationDTO);
	}


}
