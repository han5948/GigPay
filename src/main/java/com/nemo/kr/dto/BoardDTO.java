package com.nemo.kr.dto;


public class BoardDTO extends DefaultDTO {

	private static final long serialVersionUID = -2231950057788485849L;
	                    
	private String board_group_seq;                                                         
	private String board_type;      	//  B:일반게시판 F파일게시판 Q:QnA 게시판  
	private String board_name;      	//  게시판이름                                     
	private String board_comment;  //  게시판설명   
	private String reply_yn;				// 댓글유무
	private String pass_yn;				// 파일 업로드 유무
	private String editor_yn;			// 에디터 사용유무
	private String email_yn;			// 이메일 사용유무
	private String phone_yn;			// 휴대폰 사용유무
	private String write_company_seq;	// 글쓰기 지점 (0: 전체, 13:본사)
	private String write_admin_auth;	// 글쓰기 권한 (-1: 전체, 0:슈퍼관리자, 1:관리자이상)
	
	
	private String board_seq;			// 게시판 순번
	private String board_writer;
	private String member_seq;
	private String board_pass;
	private String board_email;
	private String board_phone;
	private String board_title;			// 제목
	private String board_contents;	// 내용
	private String view_cnt;
	private String query;
	
	public String getBoard_group_seq() {
		return board_group_seq;
	}
	public void setBoard_group_seq(String board_group_seq) {
		this.board_group_seq = board_group_seq;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getBoard_comment() {
		return board_comment;
	}
	public void setBoard_comment(String board_comment) {
		this.board_comment = board_comment;
	}
	public String getReply_yn() {
		return reply_yn;
	}
	public void setReply_yn(String reply_yn) {
		this.reply_yn = reply_yn;
	}
	public String getPass_yn() {
		return pass_yn;
	}
	public void setPass_yn(String pass_yn) {
		this.pass_yn = pass_yn;
	}
	public String getEditor_yn() {
		return editor_yn;
	}
	public void setEditor_yn(String editor_yn) {
		this.editor_yn = editor_yn;
	}
	public String getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(String board_seq) {
		this.board_seq = board_seq;
	}
	public String getBoard_writer() {
		return board_writer;
	}
	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}
	public String getMember_seq() {
		return member_seq;
	}
	public void setMember_seq(String member_seq) {
		this.member_seq = member_seq;
	}
	public String getBoard_pass() {
		return board_pass;
	}
	public void setBoard_pass(String board_pass) {
		this.board_pass = board_pass;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_contents() {
		return board_contents;
	}
	public void setBoard_contents(String board_contents) {
		this.board_contents = board_contents;
	}
	public String getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(String view_cnt) {
		this.view_cnt = view_cnt;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getEmail_yn() {
		return email_yn;
	}
	public void setEmail_yn(String email_yn) {
		this.email_yn = email_yn;
	}
	public String getPhone_yn() {
		return phone_yn;
	}
	public void setPhone_yn(String phone_yn) {
		this.phone_yn = phone_yn;
	}
	public String getBoard_email() {
		return board_email;
	}
	public void setBoard_email(String board_email) {
		this.board_email = board_email;
	}
	public String getBoard_phone() {
		return board_phone;
	}
	public void setBoard_phone(String board_phone) {
		this.board_phone = board_phone;
	}
	public String getWrite_admin_auth() {
		return write_admin_auth;
	}
	public void setWrite_admin_auth(String write_admin_auth) {
		this.write_admin_auth = write_admin_auth;
	}
	public String getWrite_company_seq() {
		return write_company_seq;
	}
	public void setWrite_company_seq(String write_company_seq) {
		this.write_company_seq = write_company_seq;
	}


}