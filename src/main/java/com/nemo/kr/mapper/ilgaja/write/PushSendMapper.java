package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;
import java.util.Map;

import com.nemo.kr.dto.PushLogDTO;
import com.nemo.kr.dto.PushSendDTO;


/**
 * @author nemojjang
 *
 */
public interface PushSendMapper {
	public PushSendDTO selectPushSendInfo(PushSendDTO pushSendDTO);

	public void updatePushSend(PushSendDTO pushSendDTO);

	public void setIlboWPush(Map<String, Object> map);

	public int setIlboWPush(PushSendDTO pushSendDTO);

	public void insertPushLog(PushLogDTO pushLogDTO);
  
	public void updatePushLog(PushLogDTO pushLogDTO);
  
	public void insertReceivePushLog(PushLogDTO pushLogDTO);
	
	public void insertPushLogList(List<PushLogDTO> pushLogList);
}
