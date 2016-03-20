/*
 * File name: HalfHourProjectedStaffing.java
 * Creation date: Feb 18, 2009 11:56:08 AM
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
public class HalfHourProjectedStaffing extends HalfHourStaffing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7769979852346853688L;
	
	private DailyProjectedStaffing dailyStaffing;

	/**
	 * 
	 */
	public HalfHourProjectedStaffing() {
	}

	/**
	 * @return the dailyStaffing
	 */
	public DailyStaffing getDailyStaffing() {
		return dailyStaffing;
	}

	/**
	 * @param dailyStaffing the dailyStaffing to set
	 */
	public void setDailyStaffing(DailyStaffing dailyStaffing) {
		if(!(dailyStaffing instanceof DailyProjectedStaffing)) {
			throw new IllegalArgumentException("dailyStaffing passed in as parameter is not an instance of DailyProjectedStaffing");
		}		
		this.dailyStaffing = (DailyProjectedStaffing) dailyStaffing;
	}
	
}
