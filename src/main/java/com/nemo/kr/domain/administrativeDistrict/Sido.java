package com.nemo.kr.domain.administrativeDistrict;

import java.io.Serializable;
import java.util.List;

public class Sido implements Serializable {

	private static final long serialVersionUID = -369620712529388211L;
	
	private String code;
	private String name;
	
	private List<Sigungu> sigunguList;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<Sigungu> getSigunguList() {
		return sigunguList;
	}
	
}
