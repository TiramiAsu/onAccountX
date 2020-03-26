/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.json.JsonSanitizer;
import com.onaccountx.generic.GenericRESTBean;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [JSON 工具] 2020-01-05 02:58
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class JsonUtils<T> {
	
	// 將 Object 取得 bean 後轉 JSONObject(都是屬性)
	public static JSONObject parseAttributes(String beanName, Object json) {
		return (JSONObject) ((JSONObject) json).get(beanName);
	}
	
	// InputStream 轉 JSONObject
	public static JSONObject toJsonObj(InputStream in) {
		JSONObject jsonObj = null;
		JSONParser parser = new JSONParser();
		try {
			jsonObj = (JSONObject)parser.parse(new BufferedReader(new InputStreamReader(in)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	// JSONObject 映射 RESTBean
	public static <T extends GenericRESTBean>T toRESTBean(JSONObject jsonObj, String entityName, Class<T> clazz) {
		T restBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(entityName).toString();
			if (data == null || data.length() == 0) {
				return null;
			}
			
			restBean = mapper.readValue(JsonSanitizer.sanitize(data), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restBean;
	}

}
