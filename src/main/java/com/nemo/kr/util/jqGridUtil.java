package com.nemo.kr.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import com.nemo.kr.util.mysqlAES.AESUtil;

public class jqGridUtil {
	
	public static String filersToWhere (String filters){
		
		String where ="";

		try {
			AESUtil aesUtil = new AESUtil();
			JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(filters.replace("&quot;", "\""));
			//System.out.println("jsonFilter : " + jsonFilter);

			JSONArray rules=(JSONArray)jsonFilter.get("rules");
			for ( int i = 0; i < rules.size(); i++ ) {
				JSONObject rule = rules.getJSONObject(i);
				
				String fieldName 		= rule.getString("field");
				String searchData 	= rule.getString("data");
				
				//전자문서 관리 검색
				if( fieldName.indexOf(";!;") > -1 ) {
					String[] searchArr = fieldName.split(";!;");
					
					// 나중에 다른 계약서가 추가되면 || 구분자로 추가할수도 있음
					String category = searchArr[0];
					where += " AND category_code = '" + category + "'";
					fieldName = searchArr[1];
				}
				
				//field 명 바꾸기 
				if("ilbo_work_order_info".equals(fieldName)){
					fieldName = "ilbo_work_order_name";
				}else if("ilbo_use_info".equals(fieldName)){
					fieldName = "ilbo_use_name";
				}else if("worker_OSH_yn_hidden".equals(fieldName)) {
					fieldName = "worker_OSH_yn";
				}else if("auto_status_info".equals(fieldName)) {
					fieldName = "auto_status";
				}
				
				System.out.println(fieldName);
				//암호화 된 필드
				String[] encryptFields = {
						"worker_name", "worker_phone", "worker_jumin", "worker_bank_account", "worker_bank_owner"
						, "manager_name", "manager_phone", "m.manager_name"
						, "ilbo_work_manager_name", "ilbo_work_manager_phone", "ilbo_work_manager_email", "ilbo_worker_name", "ilbo_worker_phone","ilbo_worker_jumin"
						, "work_manager_name", "work_manager_phone", "work_manager_email"
						, "admin_name", "admin_phone", "admin_email"
						, "company_owner", "company_email", "company_phone"
						, "employer_owner", "tax_name", "tax_phone", "employer_phone", "employer_tel", "employer_email", "employer_phone1", "employer_phone2", "employer_phone3", "employer_phone4"
						, "worker_tel1", "worker_tel2", "worker_tel3"
						
				};
				/*
				for(int index=0; i< encryptFields.length; index++) {
					if(encryptFields[index].equals(fieldName) ) {
						fieldName = "fn_decrypt(" + fieldName + ")";
						break;
					}
				}
				*/
				// 암호화 된 필드 찾기
				boolean isEncryptField = Arrays.stream(encryptFields).anyMatch(fieldName::equals);
				if(isEncryptField) {
					fieldName = "fn_decrypt(" + fieldName + ")";
				}
				String[] removeBlankFields = { "ilbo_pay_name", "ilbo_income_name" };
				boolean isRemoveBlankField = Arrays.stream(removeBlankFields).anyMatch(fieldName::equals);
				if(isRemoveBlankField) {
					fieldName = "REPLACE(" + fieldName + ", ' ', '')";
					searchData = searchData.replace(" ", "");
				}
				
				if( searchData.equals("null") ){
					where += " AND " + fieldName + " IS NULL";
				}else if ( "eq".equals( rule.getString("op")) ) {
					where += " AND " +  fieldName + " = '"  +searchData + "'";
				} else if ( "ne".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " != '"  +searchData + "'";
				} else if ( "le".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " <= '"  +searchData + "'";
				} else if ( "lt".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " <  '"  +searchData + "'";
				} else if ( "gt".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " > '"  +searchData + "'";
				} else if ( "ge".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " >= '"  +searchData + "'";
				} else if ( "cn".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " like '%"  +searchData + "%'";
				} else if ( "nc".equals(rule.getString("op")) ) {
					where += " AND " +  fieldName + " not like '%"  +searchData + "%'";
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("filersToWhere : " + where);
		return where;
	
	}
	
	// DTO 를 Map 으로 변환
	public static Map converObjectToMap(Object obj) {
		Map resultMap = null;

		try {
		//      Field[] fields = obj.getClass().getFields();        // private field는 나오지 않음.

			Field[] fields = obj.getClass().getDeclaredFields();

			resultMap = new HashMap();
			for (int i = 0; i <= fields.length - 1; i++ ) {
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
		} catch (IllegalArgumentException e) {
		  e.printStackTrace();
		} catch (IllegalAccessException e) {
		  e.printStackTrace();
		}

		return resultMap;
	}


	// Map 을 DTO 로 변환
	public static Object convertMapToObject(Map map, Object objClass) {

		String keyAttribute    = null;
		String setMethodString = "set";
		String methodString    = null;

		Iterator itr = map.keySet().iterator();
		while ( itr.hasNext() ) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);

			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for ( int i = 0; i <= methods.length-1; i++ ) {
					if ( methodString.equals(methods[i].getName()) ) {
						//System.out.println("invoke : "+methodString);
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return objClass;
	}

  
}
