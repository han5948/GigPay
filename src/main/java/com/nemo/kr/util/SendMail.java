package com.nemo.kr.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SendMail {

	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	
	static class EmailAuthenticator extends Authenticator {
		private String id;
		private String pw;

		public EmailAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, pw);
		}
	}
	
	
	/*
	 public static void sendFindPwdEmail(UserDTO userDTO) throws UnsupportedEncodingException {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Const.EMAIL_HOST);
		props.put("mail.smtp.port", Const.EMAIL_PORT);
		props.put("mail.smtp.auth", "true");
		
		
		System.out.println("userDTO.getAdminEmail() : "+userDTO.getAdminEmail());
		System.out.println("userDTO.getAdminEmail() : "+userDTO.getAdminEmailPwd());
		

		EmailAuthenticator authenticator = new EmailAuthenticator(userDTO.getAdminEmail(), userDTO.getAdminEmailPwd());

		Session session = Session.getInstance(props, authenticator);

		String to = userDTO.getEmail();	// 받는사람 이메일
				
		try {
			logger.info("find ID sendEmail to >>>>> : " + to);
			
			Message msg = new MimeMessage(session);			
			msg.setFrom(new InternetAddress(userDTO.getAdminEmail(), MimeUtility.encodeText(userDTO.getAdminEmail(),"UTF-8","B")));						
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			msg.setSubject(StringUtil.decStr(userDTO.getAdminEmail()));
			msg.setContent(					
					 "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
					+ "<html xmlns='http://www.w3.org/1999/xhtml'>"
					+ "<head>"
					+ "<title></title>"
					+ "<meta http-equiv='Pragma' content='no-cache' />"
					+ "<meta http-equiv='Cache-Control' content='no-cache' />"
					+ "<meta http-equiv='Content-type' content='text/html; charset=utf-8' />"
					+ "<meta http-equiv='Imagetoolbar' content='no' />"
					+ "<meta http-equiv='Content-Style-Type' content='text/css' />"
					+ "<meta http-equiv='Content-Script-Type' content='text/javascript' />"
					+ "</head>"
					+ "<body>"
										
					+"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
					+"	<tr>"
					+"		<td style='padding:0; line-height:0;' colspan='2'><img src='"+Const.FILE_URL+"/resources/mw/images/neo_01.jpg' border='0' style='height:'></td>"
					+"	</tr>"
					
					+"	<tr>"
					+"		<td colspan='2' style=\"font-family: 'Malgun Gothic'; color:#737373;text-align:center;height:100px;line-height:25px;font-size:1.0em;\">"
					+"		&nbsp;네오LINK "+userDTO.getUserName()+" 님의 비밀번호 정보는 다음과 같습니다.<br/>"
					+"					네오LINK를 실행하여, 아래의 비밀번호로 로그인하세요."
					+"		</td>"
					+"	</tr>"					
					+"	<tr>"					
					+"		<td colspan='2' style=\"font-family: 'Malgun Gothic'; color:#737373;text-align:center;line-height:25px;font-size:1.0em;font-weight:bold;\" >비밀번호 : &nbsp;"+userDTO.getUserPass()+"</td>"
					+"	</tr>"
					+"	<tr>"
					+"		<td style='padding:0; line-height:0;' colspan='2'><img src='"+Const.FILE_URL+"/resources/mw/images/neo_04.jpg' border='0'></td>"
					+"	</tr>"
					+"</table>"
					
					
					+ "</body>"							
					+ "</html>",
			"text/html; charset=EUC-KR");
			msg.setSentDate(new Date());

			Transport.send(msg);
			
			System.out.println("EMAIL END>>>");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	
	public static void main(String[] args) throws Exception {
		/*UserDTO test = new UserDTO();
		
		test.setEmail("dinom81@nemodream.com");
		test.setUserName("박성재");
		test.setUserPass("1111");
		
		SendMail.sendFindPwdEmail(test);*/

	}
}
