package com.nemo.kr.dto;

public class BoardReplyDTO extends DefaultDTO{
	
	private String reply_seq;
	private String board_seq;
	private String parent_seq;
	private String depth;
	private String reply_content;
	private String reply_writer;
	private String reply_pass;
	private String use_yn;
	private String del_yn;
	private String reg_date;
	private String reg_admin;
	private String mod_date;
	private String mod_admin;
	private String query;
	
	
	public String getReply_seq() {
		return reply_seq;
	}
	public void setReply_seq(String reply_seq) {
		this.reply_seq = reply_seq;
	}
	public String getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(String board_seq) {
		this.board_seq = board_seq;
	}
	public String getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(String parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_writer() {
		return reply_writer;
	}
	public void setReply_writer(String reply_writer) {
		this.reply_writer = reply_writer;
	}
	public String getReply_pass() {
		return reply_pass;
	}
	public void setReply_pass(String reply_pass) {
		this.reply_pass = reply_pass;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	

}
