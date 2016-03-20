/*
 * File name: DayOfWeek.java
 * Creation date: Jul 6, 2008 11:54:22 AM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Calendar;

/**
 * This enum represents the days of the week.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public enum DayOfWeek {
	SUNDAY(Calendar.SUNDAY),
	MONDAY(Calendar.MONDAY),
	TUESDAY(Calendar.TUESDAY),
	WEDNESDAY(Calendar.WEDNESDAY),
	THURSDAY(Calendar.THURSDAY),
	FRIDAY(Calendar.FRIDAY),
	SATURDAY(Calendar.SATURDAY);
	
	private Integer dayOfWeek;
	
	/**
	 * 
	 * @param dayOfWeek
	 */
	private DayOfWeek(int dayOfWeek) {
		this.dayOfWeek = new Integer(dayOfWeek);
	}
	
	/**
	 * 
	 */
	public Integer getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	/**
	 * 
	 * @return
	 */
	public DayOfWeek getPreviousDayOfWeek() {
		int newOrdinal = ordinal() - 1;
		return newOrdinal >= 0 ? values()[newOrdinal] : values()[values().length - 1];
	}
	
	/**
	 * 
	 * @return
	 */
	public DayOfWeek getNextDayOfWeek() {
		int newOrdinal = ordinal() + 1;
		return newOrdinal < values().length ? values()[newOrdinal] : values()[0];
	}
	
}
