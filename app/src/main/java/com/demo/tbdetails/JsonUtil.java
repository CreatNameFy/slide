/*
 * Copyright (c) 2017. 方燚
 */

package com.demo.tbdetails;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.List;

/**
 * Json解析的工具类
 * 
 * @author
 * @since 2015-8-8
 */

public class JsonUtil {

	/**
	 * 将JSON转成 数组类型对象
	 * 
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getListFromString(String json, Class<T> clazz) {
		List<T> t = null;
		try {
			t = JSON.parseArray(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将list转换成String
	 * 
	 * @param list
	 *            准备转换的集合
	 * @return string
	 * @throws Exception
	 */
	public static String getStringFromList(List list) {
		String string = "";
		try {
			string = JSON.toJSONString(list, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 将list转换成String
	 * 
	 * @param list
	 *            准备转换的集合
	 * @return string
	 * @throws Exception
	 */
	public static String toStringFromList(List list) {
		JSONArray json = new JSONArray();
		json.addAll(list);
		return json.toString();
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 *            准备转换的对象
	 * @return json字符串
	 * @throws Exception
	 */
	public static String getStringFromObject(Object obj) {
		String json = "";
		try {
			json = JSON.toJSONString(obj, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return object
	 */
	public static <T> T getObjectFromString(String json, Class<T> clazz) {
		T t = null;
		try {
			t = JSON.parseObject(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将 json 字符串转为 JSONArray 对象;
	 * 
	 * @param json
	 * @return JsonArray
	 */
	public static JSONArray getJsonArray(String json) {
		JSONArray array = null;

		try {
			array = JSON.parseArray(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * 将 json 字符串转为 JSONObect 对象
	 * 
	 * @param json
	 * @return JSONObject
	 */
	public static JSONObject getJsonObject(String json) {
		JSONObject object = null;
		try {
			object = (JSONObject) JSON.parse(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 得到一个json对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static JSONObject getJsonObject(String key, Object value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);
		return jsonObject;
	}
	public static String getCodeJson(String data, String key){
		org.json.JSONObject json= null;
		String code=null;
		try {
			json = new org.json.JSONObject(data);
			code=json.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (NullPointerException e){
			e.printStackTrace();
		}

		return code;
	}


}