package com.nemo.kr.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 가상의 폼 유틸
 * @author nemo
 *
 */
public class FrmUtil {

	/**
	 * server form submit;
	 */
	public static void submitFrm(HttpServletRequest request, HttpServletResponse response, String message, String location, Map<String, Object> params) {
		params = params!=null ? params : new HashMap();
		Object[] key = params.keySet().toArray();
		StringBuffer sb = new StringBuffer();
		sb.append("<form id='tmpFrm' action='" + request.getContextPath() + location + "' method='POST'>");
		for(int i=0; i<params.size(); i++) {
			sb.append("<input type='hidden' name='" + key[i] + "' value='" + params.get(key[i]) + "'/>");			
		}
		sb.append("</form>");
		sb.append("<script type=\"text/javascript\">");
		if(message != null && !"".equals(message)){
			sb.append("alert('" + message + "');");
		}
		sb.append("document.getElementById('tmpFrm').submit();");
		sb.append("</script>");
		try{
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb);
			out.flush();
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void redirectFrm(HttpServletResponse response, String message, String location) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		if(message != null && !"".equals(message)){
			sb.append("alert('" + message + "');");
		}
								
		sb.append("location.href='"+location+"'");
		sb.append("</script>");
		
		try{
			response.setContentType("text/html; charset=utf-8");
			//PrintWriter out = response.getWriter();
			//out.println(sb);			
			ServletOutputStream out = response.getOutputStream();
			
			out.print(new String(sb.toString().getBytes("UTF-8"),"8859_1"));
			out.flush();
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void onlyFunc(HttpServletResponse response, String func) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		sb.append(func);
		sb.append("</script>");
		try{
			response.setContentType("text/html; charset=utf-8");
			ServletOutputStream out = response.getOutputStream();
			//PrintWriter out = response.getWriter();
			//out.println(sb);
			out.print(new String(sb.toString().getBytes("UTF-8"),"8859_1"));
			out.flush();
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
