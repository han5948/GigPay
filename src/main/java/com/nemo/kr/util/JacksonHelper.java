package com.nemo.kr.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class JacksonHelper {
//	private static ObjectMapper toJSONMapper = new ObjectMapper();
//	private static ObjectMapper fromJSONMapper = new ObjectMapper();
//	static {
//		fromJSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		fromJSONMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//	}
//
//	public static String toJSON(Object obj) {
//		ObjectMapper mapper = toJSONMapper;
//		StringWriter writer = new StringWriter();
//		try {
//			mapper.writeValue(writer, obj);
//		} catch (JsonGenerationException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//
//		return writer.toString();
//	}
//
//	public static void toJSON(Object obj, OutputStream stream, String charset) {
//		ObjectMapper mapper = toJSONMapper;
//		try {
//			OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
//			mapper.writeValue(writer, obj);
//		} catch (JsonGenerationException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static <T> T fromJSON(String json, Class<T> clazz) {
//		ObjectMapper mapper = fromJSONMapper;
//		try {
//			return mapper.readValue(json, clazz);
//		} catch (JsonParseException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static <T> T fromJSON(InputStream json, Class<T> clazz) {
//		ObjectMapper mapper = fromJSONMapper;
//		try {
//			return mapper.readValue(json, clazz);
//		} catch (JsonParseException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static <T> boolean toJSONList(List<T> list) {
//		String jsonVal = null;
//		try {
//			jsonVal = toJSONMapper.writeValueAsString(list);
//		} catch (JsonGenerationException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		return jsonVal == null ? false : true;
//	}
//
//	public static <T> List<T> fromJSONList(String jsonVal, Class<?> clazz) {
//		
//		List<T> list = null;
//		try {
//			list = fromJSONMapper.readValue(jsonVal, TypeFactory.collectionType(ArrayList.class, clazz));
//		} catch (JsonParseException e) {
//			throw new RuntimeException(e);
//		} catch (JsonMappingException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		return list;
//	}
//
//
//	public static void main(String[] args) {
//		String json = "{'groupOp':'AND','rules':[{'field':'seq','op':'bw','data':'AAAA'},{'field':'name','op':'eq','data':'&igrave; &acute;&igrave;  &igrave;  '}]},search=<null>]";
//		
//		JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(json);
//		JSONArray rules=(JSONArray)jsonFilter.get("rules"); 
//		for (int i = 0; i < rules.size(); i++) {
//			JSONObject rule = rules.getJSONObject(i);
//			/*
//	System.out.println("field: " + rule.getString("field"));
//	System.out.println("op: " + rule.getString("op"));
//	System.out.println("data: " + rule.getString("data"));
//			 */
//		}         
//	}
}
