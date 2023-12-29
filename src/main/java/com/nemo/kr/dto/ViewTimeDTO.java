package com.nemo.kr.dto;

public class ViewTimeDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String view_time_seq;
	private String start_view_time;
	private String end_view_time;
	
	public String getView_time_seq() {
		return view_time_seq;
	}
	public void setView_time_seq(String view_time_seq) {
		this.view_time_seq = view_time_seq;
	}
	public String getStart_view_time() {
		return start_view_time;
	}
	public void setStart_view_time(String start_view_time) {
		this.start_view_time = start_view_time;
	}
	public String getEnd_view_time() {
		return end_view_time;
	}
	public void setEnd_view_time(String end_view_time) {
		this.end_view_time = end_view_time;
	}
}
