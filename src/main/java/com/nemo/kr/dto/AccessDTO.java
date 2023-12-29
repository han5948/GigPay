package com.nemo.kr.dto;

import java.util.List;

import com.nemo.kr.dto.GridDefaultDTO;



/**
 * 접속로그 DTO
 * 
 * @author: nemojjang
 * @date: 2019. 8. 5.
 * @desc: 
 *
 */
public class AccessDTO extends DefaultDTO {
	// nemojjang 2019. 8. 5.
	private static final long serialVersionUID = 3754331426203942410L;
	
	private String utm_campaign; 
	private String utm_source;
	private String utm_medium;
	private String utm_content ; 
	private String utm_term;
	private String n_media; 
	private String n_query; 
	private String n_rank;
	private String n_ad_group;
	private String n_ad; 
	private String n_keyword_id; 
	private String n_keyword ;
	private String n_campaign_type;
	private String a_ip; 
	private String a_type;
	private String a_os;
	private String reg_date;
	private String a_agent;
	private String a_referer;	// 이전페이지
	
	public String getUtm_campaign() {
		return utm_campaign;
	}
	public void setUtm_campaign(String utm_campaign) {
		this.utm_campaign = utm_campaign;
	}
	public String getUtm_source() {
		return utm_source;
	}
	public void setUtm_source(String utm_source) {
		this.utm_source = utm_source;
	}
	public String getUtm_medium() {
		return utm_medium;
	}
	public void setUtm_medium(String utm_medium) {
		this.utm_medium = utm_medium;
	}
	public String getUtm_content() {
		return utm_content;
	}
	public void setUtm_content(String utm_content) {
		this.utm_content = utm_content;
	}
	public String getUtm_term() {
		return utm_term;
	}
	public void setUtm_term(String utm_term) {
		this.utm_term = utm_term;
	}
	public String getN_media() {
		return n_media;
	}
	public void setN_media(String n_media) {
		this.n_media = n_media;
	}
	public String getN_query() {
		return n_query;
	}
	public void setN_query(String n_query) {
		this.n_query = n_query;
	}
	public String getN_rank() {
		return n_rank;
	}
	public void setN_rank(String n_rank) {
		this.n_rank = n_rank;
	}
	public String getN_ad_group() {
		return n_ad_group;
	}
	public void setN_ad_group(String n_ad_group) {
		this.n_ad_group = n_ad_group;
	}
	public String getN_ad() {
		return n_ad;
	}
	public void setN_ad(String n_ad) {
		this.n_ad = n_ad;
	}
	public String getN_keyword_id() {
		return n_keyword_id;
	}
	public void setN_keyword_id(String n_keyword_id) {
		this.n_keyword_id = n_keyword_id;
	}
	public String getN_keyword() {
		return n_keyword;
	}
	public void setN_keyword(String n_keyword) {
		this.n_keyword = n_keyword;
	}
	public String getN_campaign_type() {
		return n_campaign_type;
	}
	public void setN_campaign_type(String n_campaign_type) {
		this.n_campaign_type = n_campaign_type;
	}
	public String getA_ip() {
		return a_ip;
	}
	public void setA_ip(String a_ip) {
		this.a_ip = a_ip;
	}
	public String getA_type() {
		return a_type;
	}
	public void setA_type(String a_type) {
		this.a_type = a_type;
	}
	public String getA_os() {
		return a_os;
	}
	public void setA_os(String a_os) {
		this.a_os = a_os;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getA_agent() {
		return a_agent;
	}
	public void setA_agent(String a_agent) {
		this.a_agent = a_agent;
	}
	public String getA_referer() {
		return a_referer;
	}
	public void setA_referer(String a_referer) {
		this.a_referer = a_referer;
	}
}
