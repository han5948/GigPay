package com.nemo.kr.util;

import java.util.Random;
import java.util.regex.Pattern;

public class DefaultUtil {
	
	
	public static String getNewPassword() {
		//  8~12자리로 대소문자/특수기호/숫자
		Random random = new Random();
		String tempPassword = "";
		
		int min = 5, max = 7;
		int randomNm = random.nextInt(max - min + 1) + min;	// 8~12 자리 생성을 위해
		
		StringBuffer sc = new StringBuffer("!@#$%^&*-=?~");  // 특수문자 모음, {}[] 같은 문자는 뺌
		// 규칙을 위해 3글자는 미리 생성
		tempPassword += ((char)((Math.random() * 26)+97)); // 소문자
		tempPassword += ((char)((Math.random() * 10)+48)); // 숫자
		tempPassword += sc.charAt((int)(Math.random()*sc.length()-1)); // 특수기호
		
		for(int i=0; i<randomNm; i++) {
			int rndVal = (int)(Math.random() * 65);
			if(rndVal < 10) {
				tempPassword += rndVal;
			} else if(rndVal > 35) {
				tempPassword += (char)(rndVal + 61);
			} else {
				tempPassword += (char)(rndVal + 55);
			}
		}
		
		return tempPassword;
	}
	
	public static String makePhoneNumber(String phoneNumber) {
		String regEx = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})";
		   
		if(!Pattern.matches(regEx, phoneNumber))  {
			if (phoneNumber.equals("null")) {
				return "";
				   
			} else {
				return phoneNumber;
				   
			}
		}
		   
		return phoneNumber.replaceAll(regEx, "$1-$2-$3");
		   
	}
	public static String makeHpNumber(String phoneNumber) {
		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		   
		if(!Pattern.matches(regEx, phoneNumber))  {
			if (phoneNumber.equals("null")) {
				return "";
				
			} else {
				return phoneNumber;
				   
			}
		}
		   
		return phoneNumber.replaceAll(regEx, "$1-$2-$3");
	}
	public static String replacdPhoneNo(String phoneNumber) {
		String phoneArr[] = phoneNumber.split("-");
		
		if (phoneArr.length != 3) {
			String hpNo = phoneNumber.replace("-", "");
			hpNo = DefaultUtil.makePhoneNumber(hpNo);
			return hpNo;
		} else {
			return phoneNumber;
		}
	}
	public static String replacdHpNo(String phoneNumber) {
		String hpNoArr[] = phoneNumber.split("-");
		if (hpNoArr.length != 3) {	// 전화번호 형식이 맞지 않을때 - 채워서 저장
			String hpNo = phoneNumber.replace("-", "");
			hpNo = DefaultUtil.makeHpNumber(hpNo);
			return hpNo;
		} else {
			return phoneNumber;
		}
		
	}

}
