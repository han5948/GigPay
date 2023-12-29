package com.nemo.kr.dto;

public class IlboWorkOptionDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String option_seq;
	private String option_type; // 0 : 지점, 1 : 전체
	private String start_time;
	private String end_time;
	private String work_limit_count;
	private String employer_limit_count;
	private String use_cnt;
	private String data_flag;
	private String complete_score;
	private String re_score;
	private String punk_score;
	private String eva_score;
	
	public String getComplete_score() {
		return complete_score;
	}
	public void setComplete_score(String complete_score) {
		this.complete_score = complete_score;
	}
	public String getRe_score() {
		return re_score;
	}
	public void setRe_score(String re_score) {
		this.re_score = re_score;
	}
	public String getPunk_score() {
		return punk_score;
	}
	public void setPunk_score(String punk_score) {
		this.punk_score = punk_score;
	}
	public String getEva_score() {
		return eva_score;
	}
	public void setEva_score(String eva_score) {
		this.eva_score = eva_score;
	}
	public String getWork_limit_count() {
		return work_limit_count;
	}
	public void setWork_limit_count(String work_limit_count) {
		this.work_limit_count = work_limit_count;
	}
	public String getEmployer_limit_count() {
		return employer_limit_count;
	}
	public void setEmployer_limit_count(String employer_limit_count) {
		this.employer_limit_count = employer_limit_count;
	}
	public String getData_flag() {
		return data_flag;
	}
	public void setData_flag(String data_flag) {
		this.data_flag = data_flag;
	}
	public String getOption_seq() {
		return option_seq;
	}
	public void setOption_seq(String option_seq) {
		this.option_seq = option_seq;
	}
	public String getOption_type() {
		return option_type;
	}
	public void setOption_type(String option_type) {
		this.option_type = option_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getUse_cnt() {
		return use_cnt;
	}
	public void setUse_cnt(String use_cnt) {
		this.use_cnt = use_cnt;
	}
}
