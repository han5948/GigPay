package com.nemo.kr.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 파라미터 체크 Class
 * @author nemo
 *
 */
public class ParamValidate {
	private static final Logger logger = LoggerFactory.getLogger(ParamValidate.class);
	
	private int errorCnt = 0;
	private String errorMsg = "";
	
	public ParamValidate(){
		this.errorCnt = 0;
		this.errorMsg = "";
	}

	/**
	 * 파라미터 null 체크
	 * - null 체크하여 null 이면 해당 메시지를 담는다
	 * @param msg
	 * @param param
	 */
	public void nullChk(String msg, String param){
		if(param == null || param.length() == 0){
			if(this.errorCnt == 0) this.errorMsg = msg;
			this.errorCnt++;			
		}
	}
	
	/**
	 * 파라미터 중 file null 체크
	 * @param msg
	 * @param request
	 * @param name
	 */
	public void fileNullChk(String msg, HttpServletRequest request,  String name){
		MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;	
		MultipartFile fileName = multiReq.getFile(name); //실제 넘어 온 파일

		if(fileName == null){
			if(this.errorCnt == 0) this.errorMsg = msg;
			this.errorCnt++;
		}else{
			if(fileName.isEmpty()){
				if(this.errorCnt == 0) this.errorMsg = msg;
				this.errorCnt++;
			}
		}
	}
	
	public int getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String getErrorMsg(String code) {
		return code + "!;!"+errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
