package com.nemo.kr.common.msg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.nemo.kr.common.Const;
import com.nemo.kr.util.DateUtil;


/**
 * Api 응답 객체 
 * @
 *
 */
public class ApiMsg implements Serializable {
	private static final long serialVersionUID = -5786641609538363632L;

	// msg
	private Map<String, Object> msg = new HashMap<String, Object>();

	// status
	private Map<String, Object> status = new HashMap<String, Object>();
	private String code = "";
	private String message = "";
	private String responseTime = "";
	
	private static String Header = "header";
	private static String BODY = "body";

	// result
	private Object result = null;

	// 생성자
	public ApiMsg(){			
		setStatus(null);
	}
	
	/**
	 * 초기화
	 * @param code
	 * @param message
	 * @param result
	 */
	public void init(String code, String message, Object result){
		this.code = code;
		this.message = message;		
		setStatus(null);		
		this.result = result;
	}

	// status Setter
	public void setStatus(Map<String, Object> status) {
		if(status != null){
			this.status = status;
		}else{
			this.status.put("code", getCode());
			this.status.put("message", getMessage());
			this.status.put("responseTime", DateUtil.getCurDateTime());
		}
		
	}
	
	// msg Getter
	public Map<String, Object> getMsg() {
		setStatus(null);
		
		this.msg.put(this.Header, this.status);
		this.msg.put(this.BODY, this.result);	
		
		return this.msg;
	}
	
	// 공통에러(9999) Getter
	public Map<String, Object> getCommonErrorMsg() {	
		init(Const.CODE_SYSTEM_ERROR, Const.MSG_SYSTEM_ERROR, null);	
		
		this.msg.put(this.Header, this.status);
		this.msg.put(this.BODY, this.result);		
		return this.msg;
	}
	
	// 공통에러(9999) Getter
	public Map<String, Object> getCommonErrorMsg(Object result) {	
		init(Const.CODE_SYSTEM_ERROR, Const.MSG_SYSTEM_ERROR, result);	
			
		this.msg.put(this.Header, this.status);
		this.msg.put(this.BODY, this.result);		
		return this.msg;
	}
	
	/**
	 * Service에서 Exception 발생시 throws 할 메시지 셋팅(code + "!;!" + msg)
	 * @param Code
	 * @param Msg
	 * @return Code!;!Msg
	 */
	public String setThrowMsg(String Code, String Msg){		
		return Code+"!;!"+Msg;
	}
	
	/**
	 * Service throw 한 메시지를 응답할 메시지로 재 조합한다.
	 * @param Msg
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getThrowMsg(String Msg){
		if(Msg == null || Msg.length() == 0){
			return this.getCommonErrorMsg();
		}
				
		if(Msg.indexOf("!;!")<0){
			return this.getCommonErrorMsg();
		}
		
		if(Msg.split("!;!").length != 2){
			return this.getCommonErrorMsg();
		}
		
		init(Msg.split("!;!")[0], Msg.split("!;!")[1], null);
		
		return this.getMsg();		
	}
	
	/**
	 * Service throw 한 메시지를 응답할 메시지로 재 조합한다.
	 * @param Msg
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getThrowMsg(String Msg, Object result){
		if(Msg == null || Msg.length() == 0){
			return this.getCommonErrorMsg(result);
		}
				
		if(Msg.indexOf("!;!")<0){
			return this.getCommonErrorMsg(result);
		}
		
		if(Msg.split("!;!").length != 2){
			return this.getCommonErrorMsg(result);
		}
		
		init(Msg.split("!;!")[0], Msg.split("!;!")[1], result);
		
		return this.getMsg();		
	}
	
	/**
	 * result setter
	 * @param result
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	public String getCode() {
		if(StringUtils.isEmpty(code)){
			code = Const.CODE_SUCCESS;
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		
	}

	public String getMessage() {
		if(StringUtils.isEmpty(message)){
			message = Const.MSG_SUCCESS;
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public Map<String, Object> getStatus() {
		return status;
	}

	public Object getResult() {
		return result;
	}

	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}
}
