package com.nemo.kr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 날짜 유틸 Class
 * @author nemo
 *
 */
public class DateUtil {

	
	//현재 년월일
	public static String getCurDate() {
		Date dateNow = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(dateNow);
	}
	
	
	/**
	  * @Method Name : getCurDate2
	  * @작성일 : 2021. 3. 16.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : yyyy-mm-dd 형태로 반환
	
	  */
	public static String getCurDate2() {
		Date dateNow = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(dateNow);
	}
	
	//현재 년월일시분초
	public static String getCurDateTime() {
		Date dateNow = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(dateNow);
	}
	
	//현재 시분초
	public static String getCurTime() {
		Date dateNow = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		return formatter.format(dateNow);
	}
	
	//오늘 날짜
	public static String getNowDate(String format) {
		Date dateNow = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
		return formatter.format(dateNow);
	}
	
	
	
	//날짜빼기
	public static String getDaysAgo(String format, int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days * -1);
		Date dateAgo = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
		return formatter.format(dateAgo);
	}
	
	//오늘 날짜
	public static Calendar getNowDate2() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		return cal;
	}
	
	//날짜빼기
	public static Calendar getDaysAgo2(int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days * -1);
		return cal;
	}
	
	//월,일 두자릿수로 리턴
	public static String setDateCipher (String dateVal) throws Exception {
		String newDateVal = dateVal;
		if(Integer.parseInt(dateVal)< 10){
			newDateVal = "0"+dateVal;
		}
		return newDateVal;
	}
	
	//포맷양식
	public static String setDateFormat(String date, String format,int formatLen){
		int len = formatLen;
		int num = 4;
		String retVal = "";
		if(date != null && !"".equals(date)){
			if(len >= 6){ //년월
				retVal = date.substring(0, num);
				
				retVal += format;
				
				retVal += date.substring(num,num+2);
				num+=2;
			}
			
			if(len >= 8){ //년월일
				
				retVal += format;
				retVal += date.substring(num,num+2);
				num+=2;
			}
	
			//시간은 : 고정 포맷
			if(len >= 10){ //년월일 시간
				retVal += " " + date.substring(num,num+2);
				num+=2;
			}
	
			if(len >= 12){ //년월일 시간 분
				retVal += ":" + date.substring(num,num+2);
				num+=2;
			}
	
			if(len >= 14){ //년월일 시간 분 초
				retVal += ":" + date.substring(num,num+2);
			}
		}
		
		return retVal;
	}
	
	/**
	 * 오늘 날짜 월 더하기/빼기
	 * @param addMonth
	 * @param format
	 * @return
	 * @throws Exception 
	 */
	public static String addMonth(int addMonth, String format) throws Exception{
				
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		
		c.setTime(df.parse(DateUtil.getNowDate(format)));	
		c.add(Calendar.MONTH, addMonth);
		
		
		return df.format(c.getTime());
	}
	
	/**
	 * 오늘 날짜 일 더하기/빼기
	 * @param addMonth
	 * @param format
	 * @return
	 * @throws Exception 
	 */
	public static String addDay(int addDay, String format) throws Exception{
				
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		
		c.setTime(df.parse(DateUtil.getNowDate(format)));	
		c.add(Calendar.DATE, addDay);
		
		
		return df.format(c.getTime());
		
	}
	
	
	public static String addStringDay(int addDay, String strDate, String format) throws Exception{
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		
		c.setTime(df.parse(strDate));	
		c.add(Calendar.DATE, addDay);
		
		
		return df.format(c.getTime());
		
	}
	

	
	/**
	 * String date 를 특정 포맷으로
	 * 
	 * @param target
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String dateToFormat(String target, String format) throws ParseException{
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		
		Date date = df.parse(target);
		return df2.format(date);
		
	}

	public static String dateToFormat(Date date, String format) throws ParseException{
		if ( date != null ) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			
			return df.format(date);
		} else {
			return "";
		}
    
	}

	// 두시간의 차이를 분으로 구하기
	public static String twoDateBetweenMinute(String start, String end) throws ParseException{
		Calendar tempcal=Calendar.getInstance();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startday=sf.parse(start);
		Date endDate=sf.parse(end);
		
		long startTime=startday.getTime();
		long endTime=endDate.getTime();
		
			
		long mills=endTime-startTime;
			
		//분으로 변환
		long min=mills/60000;
		
				
		//System.out.println(mills/1000 );
		
		
		return String.valueOf(min); 
	}
	
	// 두시간의 차이를 시간으로 구하기
	public static int twoDateBetweenHour(String start, String end) throws ParseException{
		Calendar tempcal=Calendar.getInstance();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startday=sf.parse(start);
		Date endDate=sf.parse(end);
		
		long startTime=startday.getTime();
		long endTime=endDate.getTime();
		
			
		long mills=endTime-startTime;
			
		//분으로 변환
		long min=mills/60000;
		
				
		//System.out.println(mills/1000 );
		
		long hour = min / 60;
		
		return 1; 
	}
	
	
	// 두시간의 차이를 일시분초로 구하기
	public static String twoDateBetween(String start, String end) throws ParseException{
		Calendar tempcal=Calendar.getInstance();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startday=sf.parse(start);
		Date endDate=sf.parse(end);
		
		long startTime=startday.getTime();
		long endTime=endDate.getTime();
		
			
		long mills=endTime-startTime;
			
		//초로 변환
		long min=mills/1000;
		
				
		long day = min / (60*60*24);
		long hour = (min - day * 60 * 60 * 24) / (60 * 60);
		long minute = (min - day * 60 * 60 * 24 - hour * 3600) / 60;
		long second = min % 60;
		
		String result = "";
		if(day > 0) {
			result += day + "일 ";
		}
		if(hour > 0){
			result += hour + "시간 ";
		}
		if(minute > 0){
			result += minute + "분 ";
		}
		result += second + "초 ";
		
		return result; 
	}

	// 두시간의 차이를 일시분초로 구하기
	public static long getDateDiff(String begin, String end) throws ParseException {
		return getDateDiff(begin, end, "yyyyMMdd");
	}

	// 두시간의 차이를 일시분초로 구하기
	public static long getDateDiff(String begin, String end, String fmt) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat(fmt);

		Date beginDate = format.parse(begin);
		Date endDate = format.parse(end);

		long diff = endDate.getTime() - beginDate.getTime();
		long dateDiff = diff / (24 * 60 * 60 * 1000);

		return dateDiff;
	}
	
	// 문자를 날자 형으로 변환
	public static Date getDate(String str) throws ParseException{
		return getDate(str, "yyyyMMdd");
	}
  
  
	public static Date getDate(String str, String fmt) throws ParseException{

		SimpleDateFormat format = new SimpleDateFormat(fmt);

		return format.parse(str);
	}

  
	/*
	 * result > 0 : nowDate > destDate
	 * result < 0 : nowDate < destDate
	 * 0			: nowDate = destDate
	 */
  
	public static int dateCompare(String nowDate, String destDate){
		int result = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date 	day1 = null;
		Date	day2 = null;
		
		try {
			day1 = format.parse(nowDate);
			day2 = format.parse(destDate);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	  
		result = day1.compareTo(day2);
	  
		return result;
	}
	
	public static String addStringMonth(int addMonth, String strDate) throws Exception{
		return addStringMonth(addMonth, strDate, "yyyy-MM-dd");
	}
	
	public static String addStringMonth(int addMonth, String strDate, String format) throws Exception{
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		
		c.setTime(df.parse(strDate));	
		c.add(Calendar.MONTH, addMonth);
		
		return df.format(c.getTime());
	}
	
	public static String getLastDay(String strDate, String format) throws Exception{
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		
		c.setTime(df.parse(strDate));	
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return df.format(c.getTime());
	}
	
	public static String getFirstDayOfLastMonth(String dateString) {
        // 문자열을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        
        // 전달 날짜를 계산함
        LocalDate lastMonth = date.minusMonths(1);
        
        // 전달의 첫째 날을 구함
        LocalDate firstDayOfLastMonth = lastMonth.withDayOfMonth(1);
        
        return firstDayOfLastMonth.toString();
    }

	public static void main(String[] args) throws Exception {
		//System.out.println(DateUtil.twoDateBetweenMinute("2014-08-17 14:25:22.0", "2014-08-17 14:28:25"));
		//System.out.println(DateUtil.twoDateBetween("2014-01-17 14:25:22.0", "2014-08-17 14:28:25"));
		
		//System.out.println(getCurDate2());
		
		//System.out.println( dateToFormat("2017-05-11","yyyy-MM-dd"));
		
		//System.out.println(addStringDay(1, "2020-07-25", "yyyy-MM-dd"));
		
		//System.out.println(twoDateBetweenMinute("2020-11-11 00:00:00",getNowDate("yyyy-MM-dd HH:mm:ss")));
		String date = "2023-02-10";
		System.out.println(addStringMonth(1, date));
		System.out.println(getLastDay(date, "yyyy-MM-dd"));
	}
}
