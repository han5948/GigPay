package com.nemo.kr.dto;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.nemo.kr.dto.GridDefaultDTO;



/**
 * @author nemojjang
 *
 */
public class WmDTO  {
	
	@Expose
	private int id;
	@Expose
	private String text;
	@Expose
	private String icon = "jstree-file";
	
	private String wm_seq;
	private String manager_seq;				//메니져 순번
	private String manager_name;
	private String manager_type;
	private String employer_seq;
	private String employer_name;
	private String employer_num;
	private String work_seq;
	private String work_name;
	private String use_yn;
	private String mod_admin;
	
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
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
	public String getWm_seq() {
		return wm_seq;
	}
	public void setWm_seq(String wm_seq) {
		this.wm_seq = wm_seq;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
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
	
}
