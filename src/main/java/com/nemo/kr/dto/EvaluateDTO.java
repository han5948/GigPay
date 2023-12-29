package com.nemo.kr.dto;

public class EvaluateDTO extends DefaultDTO {
	
	/**
	 *  평가하기
	 */
	private static final long serialVersionUID = 5892614840218402713L;
	
	private String seq;	private String ilbo_seq;
	private String employer_seq;
	private String work_seq;
	private String worker_seq;
	private String evaluate_grade;                                  
	private String evaluate_comment;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	
	public String getEvaluate_comment() {
		return evaluate_comment;
	}
	public void setEvaluate_comment(String evaluate_comment) {
		this.evaluate_comment = evaluate_comment;
	}
	public String getEvaluate_grade() {
		return evaluate_grade;
	}
	public void setEvaluate_grade(String evaluate_grade) {
		this.evaluate_grade = evaluate_grade;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	
	
	
	
}
