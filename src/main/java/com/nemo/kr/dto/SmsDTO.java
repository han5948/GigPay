package com.nemo.kr.dto;

public class SmsDTO extends DefaultDTO {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3443249669163432129L;
	
	
	
	private String tr_num;           //  메시지 고유번호로 자동 증가됨                                                              
	private String tr_senddate;      //  메시지를 전송할 시간, 미래 시간을 넣으면 예약 발송됨                              
	private String tr_id;            //  고객이 발급한 subid로 null값이어도 됨                                                       
	private String tr_sendstat;      //  발송상태 0 : 발송대기 1 : 결과수신대기 2 : 결과수신완료                             
	private String tr_rsltstat;      //  발송 결과수신 값으로 세부 사항은 결과 코드표 참조                                  
	private String tr_msgtype = "0" ;       //  문자전송 형태 0 : 일반메시지 1 : 콜백 url 메시지 2 : 국제 sms 3 : push 6: web to web  
	private String tr_phone;         //  수신할 핸드폰 번호                                                                              
	private String tr_callback = "1668-1903";      //  송신자 전화번호                                                                                  
	private String tr_rsltdate;      //  이동통신사로부터 결과를 통보 받은 시간                                                 
	private String tr_modified;      //  프로그램 내부적으로 사용                                                                     
	private String tr_msg;           //  전송할 메시지                                                                                     
	private String tr_net;           //  전송 완료 후 최종 이동통신사 정보                                                         
	private String tr_etc1;          //  1:구인접수(웹)  2:메니져 회원인증    3:구직 접수 4:구직자 회원인증 5:구인접수(앱)             
	private String tr_etc2;          //                                                                                                          
	private String tr_etc3;          //                                                                                                          
	private String tr_etc4;          //                                                                                                          
	private String tr_etc5;          //                                                                                                          
	private String tr_etc6;          //                                                                                                          
	private String tr_routeid;       //  실제 발송한 세션 id                                                                              
	private String tr_realsenddate;  //  모듈이 실제 발송(deliver)한 시간
	private String tr_name;			 // 수신할 사람 이름
	private String menuFlag;			 // 어떤 메뉴에서 들어왔는지 ('I' : [일일대장], 'W' : [구직자관리], 'M' : [매니저관리])
	private int resultCnt;			// 성공 실패 카운트
	private String tableName;		// 테이블명 변수로 받기위해 선언
	private String m_type;			// 'S' : SMS, 'M' : LMS 
	
	
	public String getM_type() {
		return m_type;
	}
	public void setM_type(String m_type) {
		this.m_type = m_type;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	public int getResultCnt() {
		return resultCnt;
	}
	public void setResultCnt(int resultCnt) {
		this.resultCnt = resultCnt;
	}
	public String getTr_name() {
		return tr_name;
	}
	public void setTr_name(String tr_name) {
		this.tr_name = tr_name;
	}
	public String getTr_num() {
		return tr_num;
	}
	public void setTr_num(String tr_num) {
		this.tr_num = tr_num;
	}
	public String getTr_senddate() {
		return tr_senddate;
	}
	public void setTr_senddate(String tr_senddate) {
		this.tr_senddate = tr_senddate;
	}
	public String getTr_id() {
		return tr_id;
	}
	public void setTr_id(String tr_id) {
		this.tr_id = tr_id;
	}
	public String getTr_sendstat() {
		return tr_sendstat;
	}
	public void setTr_sendstat(String tr_sendstat) {
		this.tr_sendstat = tr_sendstat;
	}
	public String getTr_rsltstat() {
		return tr_rsltstat;
	}
	public void setTr_rsltstat(String tr_rsltstat) {
		this.tr_rsltstat = tr_rsltstat;
	}
	public String getTr_msgtype() {
		return tr_msgtype;
	}
	public void setTr_msgtype(String tr_msgtype) {
		this.tr_msgtype = tr_msgtype;
	}
	public String getTr_phone() {
		return tr_phone;
	}
	public void setTr_phone(String tr_phone) {
		this.tr_phone = tr_phone;
	}
	public String getTr_callback() {
		return tr_callback;
	}
	public void setTr_callback(String tr_callback) {
		this.tr_callback = tr_callback;
	}
	public String getTr_rsltdate() {
		return tr_rsltdate;
	}
	public void setTr_rsltdate(String tr_rsltdate) {
		this.tr_rsltdate = tr_rsltdate;
	}
	public String getTr_modified() {
		return tr_modified;
	}
	public void setTr_modified(String tr_modified) {
		this.tr_modified = tr_modified;
	}
	public String getTr_msg() {
		return tr_msg;
	}
	public void setTr_msg(String tr_msg) {
		this.tr_msg = tr_msg;
	}
	public String getTr_net() {
		return tr_net;
	}
	public void setTr_net(String tr_net) {
		this.tr_net = tr_net;
	}
	public String getTr_etc1() {
		return tr_etc1;
	}
	public void setTr_etc1(String tr_etc1) {
		this.tr_etc1 = tr_etc1;
	}
	public String getTr_etc2() {
		return tr_etc2;
	}
	public void setTr_etc2(String tr_etc2) {
		this.tr_etc2 = tr_etc2;
	}
	public String getTr_etc3() {
		return tr_etc3;
	}
	public void setTr_etc3(String tr_etc3) {
		this.tr_etc3 = tr_etc3;
	}
	public String getTr_etc4() {
		return tr_etc4;
	}
	public void setTr_etc4(String tr_etc4) {
		this.tr_etc4 = tr_etc4;
	}
	public String getTr_etc5() {
		return tr_etc5;
	}
	public void setTr_etc5(String tr_etc5) {
		this.tr_etc5 = tr_etc5;
	}
	public String getTr_etc6() {
		return tr_etc6;
	}
	public void setTr_etc6(String tr_etc6) {
		this.tr_etc6 = tr_etc6;
	}
	public String getTr_routeid() {
		return tr_routeid;
	}
	public void setTr_routeid(String tr_routeid) {
		this.tr_routeid = tr_routeid;
	}
	public String getTr_realsenddate() {
		return tr_realsenddate;
	}
	public void setTr_realsenddate(String tr_realsenddate) {
		this.tr_realsenddate = tr_realsenddate;
	}
	
	
}
