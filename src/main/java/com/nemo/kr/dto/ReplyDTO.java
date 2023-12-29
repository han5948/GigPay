package com.nemo.kr.dto;


public class ReplyDTO extends DefaultDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2744777371276133771L;
	
	
	//댓글
	private String reply_seq;
	private String noti_seq;
	private String gallery_seq;
	private String member_seq;
	private String reply_content;
	private String member_name;
	private String member_nick_name;
	private int replyPageNo =1;
	private String file_seq;
	
	
	public String getReply_seq() {
		return reply_seq;
	}
	public void setReply_seq(String reply_seq) {
		this.reply_seq = reply_seq;
	}
	public String getNoti_seq() {
		return noti_seq;
	}
	public void setNoti_seq(String noti_seq) {
		this.noti_seq = noti_seq;
	}
	public String getGallery_seq() {
		return gallery_seq;
	}
	public void setGallery_seq(String gallery_seq) {
		this.gallery_seq = gallery_seq;
	}
	public String getMember_seq() {
		return member_seq;
	}
	public void setMember_seq(String member_seq) {
		this.member_seq = member_seq;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_nick_name() {
		return member_nick_name;
	}
	public void setMember_nick_name(String member_nick_name) {
		this.member_nick_name = member_nick_name;
	}
	public int getReplyPageNo() {
		return replyPageNo;
	}
	public void setReplyPageNo(int replyPageNo) {
		this.replyPageNo = replyPageNo;
	}
	public String getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(String file_seq) {
		this.file_seq = file_seq;
	}
		

}