package com.nemo.kr.dto;

/**
 * search Opt 관리 DTO
 *
 * @author  yunsoo
 * @version 1.0
 * @since   2020-09-14
 */
public class CommSearchDTO {
	// ilboList
	private String ilboAllShow;								// 0 : 전체보기, 1 : 간략보기
	private String ilboStatusOpt;								// 0 : 미사용, 1 : 사용, 2 : 전체
	private String ilboOpt;									// 0 : 전체, 1 : 배정완료, 2 : 미배정 현장, 3 : 미배정 구직자, 4 : 현장, 5 : 구직자
	private String ilboSelOpt;								// 0 : 전체, 1 : 지점, 2 : 구직자, 3 : 현장
	private String ilboSearchText;								// 검색어 
	
	public String getIlboAllShow() {
		return ilboAllShow;
	}
	public void setIlboAllShow(String ilboAllShow) {
		this.ilboAllShow = ilboAllShow;
	}
	public String getIlboStatusOpt() {
		return ilboStatusOpt;
	}
	public void setIlboStatusOpt(String ilboStatusOpt) {
		this.ilboStatusOpt = ilboStatusOpt;
	}
	public String getIlboOpt() {
		return ilboOpt;
	}
	public void setIlboOpt(String ilboOpt) {
		this.ilboOpt = ilboOpt;
	}
	public String getIlboSelOpt() {
		return ilboSelOpt;
	}
	public void setIlboSelOpt(String ilboSelOpt) {
		this.ilboSelOpt = ilboSelOpt;
	}
	public String getIlboSearchText() {
		return ilboSearchText;
	}
	public void setIlboSearchText(String ilboSearchText) {
		this.ilboSearchText = ilboSearchText;
	}
}
