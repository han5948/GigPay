package com.nemo.kr.common.exception;

public class ApiException extends Exception{
	private static final long serialVersionUID = -1875659220674988860L;

	private String code;
	
	public ApiException(){
		super();
	}
	
	public ApiException(String msg){
		super(msg);
	}
	
	public ApiException(String msg, String code){
		super(msg);
		this.code = code;
	}
	
	public String getMessage(){		
		return super.getMessage();
	}
	
	public String getErrorCode(){
		return this.code;
	}
	
}
