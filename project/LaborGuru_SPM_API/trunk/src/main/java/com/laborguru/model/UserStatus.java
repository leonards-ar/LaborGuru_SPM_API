/*
 * File name: UserStatus.java
 * Creation date: Aug 24, 2009 7:11:16 PM
 * Copyright Mindpool
 */
package com.laborguru.model;


/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public enum UserStatus {
	ENABLED(0),
	DISABLED(1),
	DELETED(2);
	
	private Integer status;
	
	/**
	 * 
	 * @param dayOfWeek
	 */
	private UserStatus(int status) {
		this.status = new Integer(status);
	}
	
	/**
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}
}
