package com.nemo.kr.dto;

public class AutoIlboDTO extends DefaultDTO {
	private static final long serialVersionUID = 1L;
	
	private String auto_seq;
	private String auto_status;
	private String auto_status_name;
	private String auto_send_worker_count;
	private IlboDTO ilboDTO;
	private JobDTO jobDTO;
	
	public String getAuto_status_name() {
		return auto_status_name;
	}
	public void setAuto_status_name(String auto_status_name) {
		this.auto_status_name = auto_status_name;
	}
	public String getAuto_seq() {
		return auto_seq;
	}
	public void setAuto_seq(String auto_seq) {
		this.auto_seq = auto_seq;
	}
	public String getAuto_status() {
		return auto_status;
	}
	public void setAuto_status(String auto_status) {
		this.auto_status = auto_status;
	}
	public IlboDTO getIlboDTO() {
		return ilboDTO;
	}
	public void setIlboDTO(IlboDTO ilboDTO) {
		this.ilboDTO = ilboDTO;
	}
	public JobDTO getJobDTO() {
		return jobDTO;
	}
	public void setJobDTO(JobDTO jobDTO) {
		this.jobDTO = jobDTO;
	}
	public String getAuto_send_worker_count() {
		return auto_send_worker_count;
	}
	public void setAuto_send_worker_count(String auto_send_worker_count) {
		this.auto_send_worker_count = auto_send_worker_count;
	}
	public boolean isAutoSendStopStatus() {
		return "2".equals(auto_status) || "3".equals(auto_status)
				|| "4".equals(auto_status);
	}
}
