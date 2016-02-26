/*
 * File name: ScheduleDataKey.java
 * Creation date: Jul 15, 2009 7:43:29 PM
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleDataKey implements Serializable, Comparable<ScheduleDataKey> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer key;
	private Object sortKey;
	
	/**
	 * 
	 */
	public ScheduleDataKey() {
		this(null);
	}

	/**
	 * 
	 * @param key
	 */
	public ScheduleDataKey(Integer key) {
		this(key, null);
	}

	/**
	 * 
	 * @param key
	 * @param sortKey
	 */
	public ScheduleDataKey(Integer key, Object sortKey) {
		super();
		setKey(key);
		setSortKey(sortKey);
	}
	
	/**
	 * @param other
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ScheduleDataKey other) {
		if(other == null) {
			return 1;
		} else {
			if(getSortKey() != null) {
				if(getSortKey() instanceof String && other.getSortKey() instanceof String) {
					return other.getSortKey() != null ? ((String)getSortKey()).compareToIgnoreCase((String)other.getSortKey()) : 1;
				} else if(getSortKey() instanceof Comparable && other.getSortKey() instanceof Comparable) {
					return other.getSortKey() != null ? ((Comparable<Object>)getSortKey()).compareTo(other.getSortKey()) : 1;
				} else {
					return 1;
				}
			} else {
				return other.getSortKey() != null ? -1 : 0;
			}			
		}
	}

	/**
	 * @return the key
	 */
	public Integer getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Integer key) {
		this.key = key;
	}

	/**
	 * @return the sortKey
	 */
	public Object getSortKey() {
		return sortKey;
	}

	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(Object sortKey) {
		this.sortKey = sortKey;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof ScheduleDataKey) || getKey() == null) {
			return false;
		}
		return getKey().equals(((ScheduleDataKey)obj).getKey());
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getKey() != null ? getKey() : super.hashCode();
	}

}
