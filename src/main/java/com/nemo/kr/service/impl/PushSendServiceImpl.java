package com.nemo.kr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.PushLogDTO;
import com.nemo.kr.dto.PushSendDTO;
import com.nemo.kr.mapper.ilgaja.write.PushSendMapper;
import com.nemo.kr.service.PushSendService;


/**
 * @author nemojjang
 *
 */
@Service
public class PushSendServiceImpl implements PushSendService {
	@Autowired PushSendMapper pushSendMapper;

	@Override
	public PushSendDTO  selectPushSendInfo(PushSendDTO pushSendDTO) {
		// TODO Auto-generated method stub
	
		return pushSendMapper.selectPushSendInfo(pushSendDTO);
	}
	
	@Override
	@Transactional
	public void updatePushSend(PushSendDTO pushSendDTO) {
		// TODO Auto-generated method stub
		pushSendMapper.updatePushSend(pushSendDTO);
	}

	@Override
	@Transactional
	public void setIlboWPush(Map<String, Object> map) {
		// TODO Auto-generated method stub
		pushSendMapper.setIlboWPush(map);
	}

	@Override
	public int setIlboWPush(PushSendDTO pushSendDTO) {
		// TODO Auto-generated method stub
		return pushSendMapper.setIlboWPush(pushSendDTO);
	}

	@Override
	@Transactional
	public void insertPushLog(PushLogDTO pushLogDTO) {
		// TODO Auto-generated method stub
		pushSendMapper.insertPushLog(pushLogDTO);
	}

	@Override
	@Transactional
	public void updatePushLog(PushLogDTO pushLogDTO) {
		// TODO Auto-generated method stub
		pushSendMapper.updatePushLog(pushLogDTO);
	}

	@Override
	@Transactional
	public void insertReceivePushLog(PushLogDTO pushLogDTO) {
		// TODO Auto-generated method stub
		pushSendMapper.insertReceivePushLog(pushLogDTO);
	}

	@Override
	@Transactional
	public void insertPushLogList(List<PushLogDTO> pushLogList) {
		// TODO Auto-generated method stub
		pushSendMapper.insertPushLogList(pushLogList);
	}
}