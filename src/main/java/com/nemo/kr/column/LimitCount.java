package com.nemo.kr.column;

import org.apache.ibatis.type.MappedTypes;

import com.nemo.kr.common.handler.LimitCountTypeHandler;

public enum LimitCount {
	
	UNCHECKED_EMPLOYER("제한없음-구인처별", "0")					//제한없음-구인처
	, UNCHECKED_WORK("제한없음-현장별", "1")
	, EMPLOYER("구인처별", "2")			//구인처별
	, WORK("현장별", "3");			//현장별
	
	private String name;
	private String value;
	
	LimitCount(String name,String value){
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getText() {
		return this.name;
	}
	
	public boolean isUncheckedLimitCount() {
		return "0".equals(this.value) || "1".equals(this.value); 
	}
	
	public boolean isCheckedLimitCount() {
		return "2".equals(this.value) || "3".equals(this.value);
	}
	
	@MappedTypes(LimitCount.class)
    public static class TypeHandler extends LimitCountTypeHandler<LimitCount> {
        public TypeHandler() {
            super(LimitCount.class);
        }
    }
}
