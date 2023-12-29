package com.nemo.kr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.NotificationLogDTO;
import com.nemo.kr.mapper.ilgaja.write.NotificationLogMapper;
import com.nemo.kr.service.NotificationLogService;

@Service
public class NotificationLogServiceImpl implements NotificationLogService{
	private static final Logger logger = LoggerFactory.getLogger(NotificationLogServiceImpl.class);
	
	@Autowired NotificationLogMapper notificationLogMapper;
	
	@Override
	public void updateNotificationLog(NotificationLogDTO notificationLogDTO) {
		// TODO Auto-generated method stub
		notificationLogMapper.updateNotificationLog(notificationLogDTO);
	}

	


}
