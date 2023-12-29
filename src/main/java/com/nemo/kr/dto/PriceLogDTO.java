package com.nemo.kr.dto;

public class PriceLogDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String log_seq;
	private String admin_seq;
	private String manager_seq;
	private String user_id;
	private String cellName;
	private String before_price;
	private String after_price;
	private String reg_date;
	private String ilbo_seq;
	
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getBefore_price() {
		return before_price;
	}
	public void setBefore_price(String before_price) {
		this.before_price = before_price;
	}
	public String getAfter_price() {
		return after_price;
	}
	public void setAfter_price(String after_price) {
		this.after_price = after_price;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	
	public String getCellNameKr() {
		String cellNameKr = "";
		if( cellName == null || cellName.isEmpty() ) {
			return cellNameKr;
		}
		
		switch (cellName) {
			case "ilbo_unit_price":
				cellNameKr = "단가";
				break;
			case "ilbo_fee":
				cellNameKr = "수수료";
				break;
			case "share_fee":
				cellNameKr = "쉐어료";
				break;
			case "counselor_fee":
				cellNameKr = "상담사";
				break;
			case "ilbo_pay":
				cellNameKr = "구직자";
				break;	
			default:
				break;
		}
		return cellNameKr;
	}
}
