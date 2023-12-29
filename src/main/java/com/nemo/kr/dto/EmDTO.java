package com.nemo.kr.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.nemo.kr.dto.GridDefaultDTO;



/**
 * @author nemojjang
 *
 */
public class EmDTO  {
	
	@Expose
	private int id;
	@Expose
	private String text;
	@Expose
	private List<WmDTO> children = new ArrayList<WmDTO>();
	
	@Expose
	private Map<String, Object> state = new HashMap<String, Object>();
	
	private String em_seq;
	private String manager_seq;				//메니져 순번
	private String manager_name;
	private String manager_type;
	
	@Expose
	private String employer_seq;
	private String employer_name;
	private String employer_num;
	private String use_yn;
	private List<WmDTO> wmList = new ArrayList<WmDTO>();
	
	public EmDTO() {
			this.state.put("opened", true);
			
	}
	
	public String getEm_seq() {
		return em_seq;
	}
	public void setEm_seq(String em_seq) {
		this.em_seq = em_seq;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getManager_type() {
		return manager_type;
	}
	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getEmployer_num() {
		return employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public List<WmDTO> getWmList() {
		return wmList;
	}
	public void setWmList(List<WmDTO> wmList) {
		this.wmList = wmList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<WmDTO> getChildren() {
		return children;
	}
	public void setChildren(List<WmDTO> children) {
		this.children = children;
	}

	public Map<String, Object> getState() {
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}
		
}
