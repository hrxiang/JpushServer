package com.mb.jpush.util;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/***
 * @author hrxiang
 * @param T
 *            一般为String类型
 * @param PK
 *            一般为实体类 Gson有2个最基本的方法 1) toJson() – 转换java 对象到JSON 2) fromJson() –
 *            转换JSON到java对象
 * */
public class GsonUtils<T, PK extends Serializable> {
	/**
	 * 对象转换为json字符串
	 * **/
	@SuppressWarnings("unchecked")
	public T ObjectToJsonStr(PK obj) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(obj);
		return (T) jsonStr;
	}

	/**
	 * 集合转换为json字符串
	 * **/
	@SuppressWarnings("unchecked")
	public T CollectionToJsonStr(List<PK> c) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<PK>>() {
		}.getType();
		String beanListToJson = gson.toJson(c, type);
		return (T) beanListToJson;
	}

	/**
	 * json字符串转换为对象
	 * **/
	public PK JsonStrToObject(T jsonStr, Class<PK> clazz) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		try {
			if (jsonStr != null) {
				PK pk = gson.fromJson(jsonStr.toString(), clazz);
				return pk;
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转换为对象集合
	 * **/
	@SuppressWarnings("unchecked")
	public List<PK> JsonStrToObjectList(T jsonStr, Class<PK> clazz) {
		List<PK> list = new ArrayList<PK>();
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(jsonStr.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(JsonStrToObject((T) jsonArray.get(i), clazz));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
