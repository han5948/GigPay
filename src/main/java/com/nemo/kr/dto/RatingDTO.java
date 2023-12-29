package com.nemo.kr.dto;

public class RatingDTO extends DefaultDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2687254836239666332L;
	
	private String rating_seq;
	private String rating_type; 
	private String worker_seq;           
	private String ilbo_seq;
	private String employer_seq;
	private String work_seq;
	private String manager_seq;
	private String rate;
	private String comment;
	
	
	public String getRating_seq() {
		return rating_seq;
	}
	public void setRating_seq(String rating_seq) {
		this.rating_seq = rating_seq;
	}
	public String getRating_type() {
		return rating_type;
	}
	public void setRating_type(String rating_type) {
		this.rating_type = rating_type;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	
	
	
}
