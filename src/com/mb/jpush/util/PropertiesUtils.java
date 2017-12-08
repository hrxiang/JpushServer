package com.mb.jpush.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class PropertiesUtils {
	private Map<String, String> proMap;

	public PropertiesUtils(String source) {
		proMap = new HashMap<String, String>();
		ResourceBundle rs = ResourceBundle.getBundle(source);
		Enumeration<String> e = rs.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			String value = rs.getString(key);
			proMap.put(key, value);
		}
	}

	public String getValue(String key) {
		return proMap == null ? null : proMap.get(key);
	}

	public Set<String> getKeys() {
		return proMap == null ? null : proMap.keySet();
	}
}
