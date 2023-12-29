package com.nemo.kr.domain.administrativeDistrict;

import java.io.Serializable;
import java.util.List;

public class Sigungu implements Serializable {
	
	private static final long serialVersionUID = 1540183876453116614L;
	
	private String code;
	private String name;
	
	private List<Eupmyeondong> eupmyeondongList;
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<Eupmyeondong> getEupmyeondongList() {
		return eupmyeondongList;
	}
}
