package com.nemo.kr.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;



public class CommonUtil {

	public static String getServerAddr(HttpServletRequest request){
		
		String serverName = request.getServerName();
		String serverPort = String.valueOf(request.getServerPort());
		
		if("80".equals(serverPort) || "443".equals(serverPort)){
			serverPort = "";
		}else{
			serverPort = ":"+serverPort;
		}
		
		String  serverAddr = "http://" + serverName +serverPort;	
				
		
		//System.out.println("serverPort : "+serverPort);
		
		
		//System.out.println("serverAddr : "+serverAddr);
		
		return serverAddr;
	}
	
	
	/**
	 * 애플리케이션 물리경로 가져오기
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request){
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return realPath;
	}
	
	
	/**
	 * 9의 보수 구하기
	 * @param val
	 * @return
	 */
	public static String get9Complementary(String val){
		String result = "";
    	for(int x=0;x<val.length();x++){
    		int i = Character.getNumericValue(val.charAt(x));
    		result += Integer.toString(9-i);
    	}
    	
    	return result;
	}
	
	/**
	 * Exception Logging
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Exception e)
	{
	    StringWriter sWriter = new StringWriter();
	    PrintWriter pWriter = new PrintWriter(sWriter);
	    e.printStackTrace(pWriter);
	    return sWriter.toString();
	}
	
	/**
	 * 주문번호 랜덤생성 15자리
	 * @param length
	 * @return
	 */
	public static String getOrderNum(int length){
    	StringBuffer result = new StringBuffer();
        Random rnd = new Random();
        
        for (int i = 1; i <= length; i++) {           
            result.append((rnd.nextInt(10)));              

        }
        
       return result.toString();
    }
	

	public static void main(String[] args) {

	}
	
	
	/**
	 * 앱 로그인 키 생성
	 * @param appCode
	 * @param seq
	 * @return
	 */
	public static String getAppLoginKey(String seq){	
		//System.out.println("getAppLoginKey");
		String key = seq + "!;!" +  DateUtil.getNowDate("yyyyMMddHHmmssSSS");
		return SHA.encrypt(key);
	}
	
	
	// Str배열의 중복문자열을 제거 후 리턴한다.
	public static Object[] removeDuplicateArray(String[] array){
		   
		Object[] removeArray=null;
		TreeSet ts=new TreeSet();
		
		for(int i=0; i<array.length; i++){
			ts.add(array[i]);
		}
		 
		removeArray= ts.toArray();
		return removeArray;
			  
	}
	
	public static boolean isMobile(HttpServletRequest request){
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
		
		if(mobile1 || mobile2){
			return true;
		}else{
			return false;
		}
	}

	public static String getOsType(HttpServletRequest request) { 
		String userAgent = request.getHeader("user-agent");
	    String osType = "";
	    if(userAgent.indexOf("iPhone") > -1){
	    	osType = "I";
	    }else if(userAgent.indexOf("Android") > -1){
	    	osType = "A";
	    }else if(userAgent.indexOf("Windows") > -1) {
	    	osType = "W";
	    }else {
	    	osType = "E";	//기타
	    }
	    
	    return osType;
	}

	
	/**
	 * 로그인 토큰 생성
	 * - yyyyMMddHHmmss + val
	 * @param val
	 * @return
	 */
	public static String makeLoginToken(String val){
		String _t = DateUtil.getCurDateTime() + val;
		_t = AES.encrypt(_t);		
		return _t;
	}
	
	
	

}
