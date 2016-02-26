package com.laborguru.service.store.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Utility class use to keep a hash map of <String, Number>
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class NumberMap {

	private Map<String, Number> dataMap;

	/**
	 * Default Constructor
	 */
	public NumberMap(){
		this.dataMap = new HashMap<String,Number>();
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void put(String key, Number value){
		dataMap.put(key, value);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Number get(String key){
		return dataMap.get(key);
	}
	
	public Set<String> getKeySet(){
		return dataMap.keySet();
	}
}
