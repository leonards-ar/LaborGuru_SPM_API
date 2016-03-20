package com.laborguru.service.store.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class use to keep a hash map of <String, NumberMap>
 * It is used to facilitate the work of HashMaps of HashMaps.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionValueMap {

	private Map<String, NumberMap> positionData;

	/**
	 * 
	 */
	public PositionValueMap(){		
		this.positionData = new HashMap<String,NumberMap>();		
	}
	
	/**
	 * @param position
	 * @param data
	 * @param value
	 */
	public void put(String position, String data, Number value){
		
		if ((position == null) || (data == null)){
			throw new IllegalArgumentException("Parameters passed as parameter cannot be null");
		}
		
		NumberMap numberMap = this.positionData.get(position);
		
		if (numberMap == null){
			numberMap = new NumberMap();
			this.positionData.put(position, numberMap);
		}
		
		numberMap.put(data, value);
	}
	
	/**
	 * @param position
	 * @param data
	 * @return
	 */
	public Number get(String position, String data){
		
		if ((position == null) || (data == null)){
			throw new IllegalArgumentException("Parameters passed as parameter cannot be null");
		}
		
		NumberMap numberMap = this.positionData.get(position);
		
		if (numberMap != null)
			return numberMap.get(data);
		
		return null;
	}
	
	/**
	 * Return a set with all the position names contained by this map
	 * @return
	 */
	public Set<String> getKeyNames(){
		return this.positionData.keySet();
	}
	
	public Set<String> getMapDataKeySet(){
		Set<String> keySet = new HashSet<String>();
		
		for (NumberMap aMap :this.positionData.values()){
			keySet.addAll(aMap.getKeySet());
		}
		
		return keySet;
	}
		
}
