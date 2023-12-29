package com.nemo.kr.dto;

import java.util.HashMap;
import java.util.List;

public class IlboDeductionDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String deduction_seq;
	private String ilbo_seq;
	private String deduction_status;
	private String deduction_order;
	private String reg_date;
	private String[] sel_ilbo_seq;
	private List<IlboDTO> list;
	
	public String getDeduction_order() {
		return deduction_order;
	}
	public void setDeduction_order(String deduction_order) {
		this.deduction_order = deduction_order;
	}
	public List<IlboDTO> getList() {
		return list;
	}
	public void setList(List<IlboDTO> list) {
		this.list = list;
	}
	public String[] getSel_ilbo_seq() {
		return sel_ilbo_seq;
	}
	public void setSel_ilbo_seq(String[] sel_ilbo_seq) {
		this.sel_ilbo_seq = sel_ilbo_seq;
	}
	public String getDeduction_seq() {
		return deduction_seq;
	}
	public void setDeduction_seq(String deduction_seq) {
		this.deduction_seq = deduction_seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getDeduction_status() {
		return deduction_status;
	}
	public void setDeduction_status(String deduction_status) {
		this.deduction_status = deduction_status;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
}
