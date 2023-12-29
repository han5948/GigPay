package com.nemo.kr.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String encStr(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String encStr(String value, String encType) {
		try {
			return URLEncoder.encode(value, encType);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String decStr(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String decStr(String value, String decType) {
		try {
			return URLDecoder.decode(value, decType);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean isNull(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}
	
	public static boolean isNullNZero(String str) {
		return ((str == null) || (str.trim().length() == 0) || ("0".equals(str.trim()) ) );
	}

	public static String isNullToString(String str, String rtn) {
		if ( !isNull(str) ) {
			rtn = str.trim();
		}

		return rtn;
	}
	public static String isNullToString(String str) {
		return isNullToString(str, "");
	}

	public static String isNullToString(Object obj, String rtn) {
		if ( obj != null ) {
			rtn = obj.toString().trim();
		}

		return rtn;
	}
	public static String isNullToString(Object obj) {
		return isNullToString(obj, "");
	}

	public static int isNullToInt(String str) {
		return isNullToInt(str, 0);
	}
	
	
	
	public static int isNullToInt(String str, int rtn) {
		try {
			rtn = Integer.parseInt(str);
		} catch (Exception e) {
		}

		return rtn;
	}
	
	
	
	public static double isNullToDouble(String str, double rtn) {
		try {
			rtn = Double.parseDouble(str);
		} catch (Exception e) {
		}

		return rtn;
	}
	
	public static int isNullToInt(Object obj) {
		return isNullToInt(obj, 0);
	}
	public static int isNullToInt(Object obj, int rtn) {
		if ( obj != null ) {
			rtn = isNullToInt(obj.toString(), 0);
		}

		return rtn;
	}
	public static double isNullToDouble(Object object) {
		return isNullToDouble(object, 0);
	}
	public static double isNullToDouble(Object obj, double rtn) {
		if(obj != null) {
			rtn = isNullToDouble(obj.toString(), 0);
		}
		
		return rtn;
	}
	public static double isNullToDouble(String str) {
		return isNullToDouble(str, 0);
	}

	public static long isNullToLong(String str) {
		return isNullToLong(str, 0);
	}
	
	public static long isNullToLong(String str, long rtn) {
		try {
			rtn = Long.parseLong(str);
		} catch (Exception e) {
		}

		return rtn;
	}
	
	public static long isNullToLong(Object obj) {
		return isNullToLong(obj, 0);
	}
	public static long isNullToLong(Object obj, long rtn) {
		if ( obj != null ) {
			rtn = isNullToLong(obj.toString(), 0);
		}

		return rtn;
	}

	// 영문+숫자 비밀번호 생성
	public static String randomPwd(int cnt) {

		Random rnd = new Random();

		StringBuffer str = new StringBuffer();
		for (int i = 0; i < cnt; i++) {
			if (rnd.nextBoolean()) {
				str.append(String
						.valueOf((char) ((int) (rnd.nextInt(26)) + 97))
						.toLowerCase());
			} else {
				str.append((rnd.nextInt(10)));
			}
		}

		return str.toString();
	}

	/**
	 * 이미지 태그 제거
	 * 
	 * @param str
	 * @return
	 */
	public static String imgTagDelete(String str) {
		String result = "";
		if (!StringUtil.isNull(str)) {
			Pattern p = Pattern.compile("<img .*?>", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);
			result = m.replaceAll("");
		}
		return result;
	}

	/**
	 * html 태그 제거
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlTagDelete(String str) {
		String result = "";
		if (!StringUtil.isNull(str)) {
			result = str.replaceAll(
					"<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		}
		return result;
	}

	/**
	 * html 태그 제거 후 글자 자르기
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlTagDeleteSubString(String str, int start, int end) {

		String result = "";
		if (!StringUtil.isNull(str)) {
			result = str.replaceAll(
					"<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			if (end > result.length()) {
				end = result.length();
			}
			result = result.substring(start, end);
		}

		return result;
	}

	// rsa util
	public static String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		byte[] encryptedBytes = hexToByteArray(securedValue);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.

		return decryptedValue;
	}

	/**
	 * 16진 문자열을 byte 배열로 변환한다.
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[]{};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}

		return bytes;
	}
	
	/**
	  * @Method Name : getjuminAge
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 주민증록번호에서 나이 추출
	
	  */
	public static String getjuminAge(String str) {
		String result = "";

		String birthStr = "";
		int sex = 0;

		if ( StringUtils.isEmpty(str) ) {
			return "";
		}

		if ( str.length() >= 13 ) {
			result = str.replace("-",  "");

			birthStr = result.substring(0, 6);              // 출생년월일
			sex = isNullToInt(result.substring(6, 7));      // 성별 및 출생년도 확인

			if ( sex == 9 || sex == 0 ) {                                             // 1800년대에 태어난 출생자
				birthStr = "18"+ birthStr;   
			} else if ( sex == 1 || sex == 2 || sex == 5 || sex == 6 ) {              // 1900년대에 태어난 출생자 (5, 6 : 외국인)
				birthStr = "19"+ birthStr;
			} else if ( sex == 3 || sex == 4 || sex == 7 || sex == 8 ) {              // 2000년대에 태어난 출생자 (7, 8 : 외국인)
				birthStr = "20"+ birthStr;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
			try {
				Date birthDay = sdf.parse(birthStr);

				GregorianCalendar today = new GregorianCalendar();
				GregorianCalendar birth = new GregorianCalendar();
				birth.setTime(birthDay);

				int factor = 0;
				if ( today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR) ) {
					factor = -1;
				}

				result = ""+ (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor);
			} catch (Exception e) {}
		}

		return result;
	}

	
	/**
	  * @Method Name : getjuminSex
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 주민번호에서 성별 추출
	
	  */
	public static String getjuminSex(String str) {

		String result = "";
		int sex = 0;

		if ( StringUtils.isEmpty(str) ) {
			return "";
		}

		if ( str.length() >= 13 ) {
			result = str.replace("-",  "");

			sex = isNullToInt(result.substring(6, 7));     // 성별확인

			if ( sex == 1 || sex == 3 ) {               // 2000년 이전 출생자
				result = "M";
			} else if ( sex == 2 || sex == 4 ) {        // 2000년 이후 출생자
				result = "F";
			}
		}

		return result;
	}

	/**
	  * @Method Name : numberFormat
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 문자는 빼고 숫자만 출력한다.
	
	  */
	public static String numberFormat(String num){
		/* replaceAll 된 문자는 "124"이다.*/
		if(!isNull(num)) {
			
			num = num.replaceAll("[^0-9]", "");
		}
		return  num;

	}


	
	/**
	  * @Method Name : juminFormat
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 주민번호 마스킹
	
	  */
	public static String juminFormat(String val) {
		return juminFormat(val, true);
	}

	// 주민등록번호 형식 출력
	// true  : 6510081234567 > 651008-1234567
	// false : 6510081234567 > 651008-1******
	public static String juminFormat(String val, boolean bVisible) {

		String result = "";


		if ( StringUtils.isEmpty(val) ) {
			return "";
		}

		if ( val.length() == 13 ) {
			result = val.substring(0, 6) +"-";

			if ( bVisible ) {
				result += val.substring(6);
			} else {
				result += val.substring(6, 7) + "******";
			}
		} else {
			result = val;
		}

		return result;
	}


	
	
	/**
	  * @Method Name : phoneFormat
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 전화번호 마스킹
	
	  */
	public static String phoneFormat(String val) {
		return phoneFormat(val, true);
	}

	// 전화번호 형식 출역
	// true  : 01011112222 > 010-1111-2222
	// false : 01011112222 > 010-**11-**22
	public static String phoneFormat(String val, boolean bVisible) {
		
		String result = "";

		if ( StringUtils.isEmpty(val) ) {
			return "";
		}
		
		if(val.length() < 7) {
			return "";
		}

		String sArea = val.substring(0, 2);

		if ( "02".equals(sArea) ) {
			result = val.substring(0, 2);

			if ( val.length() == 10 ) {
				if ( bVisible ) {
					result += "-"+ val.substring(2, 6) +"-"+ val.substring(6);
				} else {
					result += "-**"+ val.substring(4, 6) +"-**"+ val.substring(8); 
				}
			} else if ( val.length() == 9 ) {
				if ( bVisible ) {
					result += "-"+ val.substring(2, 5) +"-"+ val.substring(5);
				} else {
					result += "-**"+ val.substring(4, 5) +"-**"+ val.substring(7); 
				}
			} else {
				result = val;
			}
		} else if ( val.length() == 11 ) {
			result = val.substring(0, 3);

			if ( bVisible ) {
				result += "-"+ val.substring(3, 7) +"-"+ val.substring(7);
			} else {
				result += "-**"+ val.substring(5, 7) +"-**"+ val.substring(9); 
			}
		} else if ( val.length() == 10 ) {
			if ( bVisible ) {
				result += "-"+ val.substring(3, 6) +"-"+ val.substring(6);
			} else {
				result += "-**"+ val.substring(5, 6) +"-**"+ val.substring(8); 
			}
		} else {
			result = val;
		}

		return result;
	}
	



	public static String formatTime(String timeString) {
		String regEx = "(\\d{2})(\\d{2})";

		if(!Pattern.matches(regEx, timeString)) return timeString;

		return timeString.replaceAll(regEx, "$1:$2");

	}

	/**
	  * @Method Name : zeroString
	  * @작성일 : 2021. 1. 6.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : num 앞에 totalLen 만큼 0을 붙인다. 
	  * 
	  * StringUtil.zeroString(1,2) - > 01
	
	  */
	public static String zeroString(int totalLen, String num){
		String result = num;
		int numLen = num.length();
		int zeroLen = totalLen - numLen;

		for (int i = 0; i < zeroLen; i++) {
			result = "0" + result;
		}


		return result;
	}


	/**
	 * @Method Name : strComma
	 * @작성일 : 2020. 1. 7.
	 * @작성자 : nemojjang
	 * @변경이력 : 
	 * @Method 설명 : 천단위 콤마

	 */
	public static String strComma(String val) {
		if(isNull(val)) {
			return "";
		}
		return String.format("%,d",  Long.parseLong(val));
	}
	


	public static void main(String[] args) {
		//System.out.println(formatTime("1800"));
		//System.out.println( zeroString(5,"10"));

		//System.out.println(decStr("%EC%9D%B8%EB%A0%A5"));
		// System.out.println(decStr("https%3A%2F%2Fadcr.naver.com%2Fadcr%3Fx%3DmnFqna0ugz93yYUGJu3Bdf%2F%2F%2Fw%3D%3Dk8MCejhxVpw1vTCvq8rowv%2BzwP%2B7kBczwEEsIeOgWRs0mxbiqnaBKx15SQ72qFivNCIt3y6Bx2SrckRnVtv2SofnPttdUPTBwv%2F7soKziPR4JX9ET0TmBxtSXfogoneBFBRGA1MpbArNfsAwi4JBdlDmu3hF5rjRyziuEI%2BDJ3lCVhIxz%2FUM2I0ll86g4oZjO3OD4MCnbEwqZgNa0qYzeME5E1iqy7YYRqm0gMz%2FC9KS9vHVqWvwqFxY0v6dO%2BhomkDD%2BP7gw7cw%2FjR6Sx3vpUW4s8LnkryzJUnclLC59SOVw1TyizWT8n%2FyIMBPloghyJwSLVJyjo%2BZ6fW24RfTMZjPHFv2T%2BeS%2B86LHsSfeuodAj9g29fs35BUwizAHIgeM6608FoTK%2B2qRVeGL9DBv0KRX2JyD69eBksQKIVuXsjeTEflQlkORfYSbBD9bpQqMk23KQ4UsgpU33vxYgAvIaWawmqMK%2FdwoFO3LnGe293zrGyXCuWcODMK0gS95jIyl%2BHYZpgf7nJjzelmLyRqTMDtAkCQpu7dt2IoQT8SzOQmGikADJWP%2BPZM00qq7VVhAgZr3DAEisIeQn5nHdbALTh6O6RrgQE%2FNlq1CuJIl7CxU8F3%2FSQd9MQzcrL16EvZKPox0eRyN%2BAt5vhW4JoZY3jaEfnMGKlS3I6saAigDEPef4GEGHvTyUt%2BuYNSBdfvRHowx3NWUutM2FtXRHm6dvxT%2FuDJZ%2FZVLShox1CX95tw6lGRaLT9wAehKtGrS8AXmNOmVzVZYu3in1lg0uRiIW9B7UiHfrps%2BCE1%2FQ0dk8SyyA0eu8Js8wPKwVSpP0RwIghtu79Xk3%2FcXXHYmtbjKDp35B257zwpTpI0%2FPuNrilw%3D%26p%3D0"));

		
		String a = "1.0";
		//System.out.println( Integer.toString((int) Float.parseFloat(a) ));
		
		System.out.println(numberFormat("123ㄴㅁㅇㄹ444"));
		
		
		
		
	}


}
