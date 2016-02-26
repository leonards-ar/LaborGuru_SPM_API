package com.laborguru.action.utils;

/**
 * Placeholder for key value components 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class KeyValuePair {

	private String key;
	private String value;
	
	/**
	 * The constructor
	 * 
	 * @param key
	 * @param value
	 */
	public KeyValuePair(String key, String value) {
		
		if (key == null){
			throw new IllegalArgumentException("The key passed in as parameter is null");
		}
		
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
