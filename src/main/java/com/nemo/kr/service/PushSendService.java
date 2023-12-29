package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import com.nemo.kr.dto.PushLogDTO;
import com.nemo.kr.dto.PushSendDTO;


/**
 * PushService
 * @author nemojjang
 *
 */
public interface PushSendService {

    public PushSendDTO  selectPushSendInfo(PushSendDTO pushSendDTO);

	public void updatePushSend(PushSendDTO pushSendDTO);

	public void setIlboWPush(Map<String, Object> map);  	// 수동푸쉬 삽입                               

	public int setIlboWPush(PushSendDTO pushSendDTO);		//푸쉬 한건씩
	
	public void insertPushLog(PushLogDTO pushLogDTO);		// 푸쉬 Log Insert
	
	public void updatePushLog(PushLogDTO pushLogDTO);		// 푸쉬 Log Update
	
	public void insertReceivePushLog(PushLogDTO pushLogDTO);		// 푸쉬 수신자 Log Insert
	
	public void insertPushLogList(List<PushLogDTO> pushLogList);
}
