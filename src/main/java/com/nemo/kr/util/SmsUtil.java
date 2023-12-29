package com.nemo.kr.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.SmsService;

/**
 * sms 보내기
 * 
 * 
 인증번호 보내기
    SmsUtil smsUtil = new SmsUtil(workerDTO.getWorker_phone(), 0, 0);
    smsUtil.setAuthNum(authNum);
    boolean sendResult = smsUtil.sendSms();
일반 SMS 보내기
	SmsUtil smsUtil = new SmsUtil(workerDTO.getWorker_phone(), 0, 1);
    smsUtil.setMessage(message);
    boolean sendResult = smsUtil.sendSms();
 * @author nemojjang
 *
 */
public class SmsUtil {
	
	
	@Autowired SmsService smsService;
	@Autowired	SmsMapper smsMapper;
	
	private String recivePhone;
	private int msgType;
	private int smsType;
	
	private String authNum;
	private String message;
	private String sendPhone;
	private String trEtc1;
	
	
	/**
	 * @param recivePhone	받는 폰번호
	 * @param msgType		문자전송 형태 0 : 일반메시지 1 : 콜백 URL 메시지 2 : 국제 SMS 3 : PUSH 6: Web to Web
	 * @param smsType		0: 인증번호 1:일반 sms 
	 * trEtc1 	1: 구인접수
	 */
	public SmsUtil(String recivePhone,  int msgType, int smsType){
		
		this.recivePhone 	= recivePhone;
		this.msgType 		= msgType;
		this.smsType		= smsType;
		this.sendPhone	= "1668-1903";
		
	}
	
	public boolean sendSms(){
		
		boolean 	result 	=  false;

	    try {
		      String msg =  "";
		      if(smsType == 0){
		    	  msg = "[일가자] 회원 인증을 위한 인증번호 : " + authNum;
		      }else{
		    	  msg = message;
		      }
		      
		      SmsDTO smsDTO = new SmsDTO();
		      smsDTO.setTr_msgtype(Integer.toString(msgType));
		      smsDTO.setTr_phone(recivePhone);
		      smsDTO.setTr_callback(sendPhone);
		      smsDTO.setTr_msg(msg);
		      smsDTO.setTr_etc1(trEtc1);
		      
		      smsMapper.insertInfo(smsDTO);
		      
		      result = true;

	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}

	     
	      
	    return result;
	}

	public String getAuthNum() {
		return authNum;
	}

	public String getMessage() {
		return message;
	}

	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getRecivePhone() {
		return recivePhone;
	}

	public void setRecivePhone(String recivePhone) {
		this.recivePhone = recivePhone;
	}
	
	public String getTrEtc1() {
		return trEtc1;
	}

	public void setTrEtc1(String trEtc1) {
		this.trEtc1 = trEtc1;
	}
	

	public static void main(String[] args) {
		SmsUtil smsUtil = new SmsUtil("01039178283", 0,0);
		smsUtil.sendSms();
		System.out.println("전송");
	}

}
