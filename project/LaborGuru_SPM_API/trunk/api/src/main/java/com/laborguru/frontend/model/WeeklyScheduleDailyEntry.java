/*
 * File name: WeeklyScheduleDailyEntry.java
 * Creation date: 08/12/2008 11:19:59
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class WeeklyScheduleDailyEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5232573319988760565L;
	private Date day = null;
	private Date inHour;
	private Date outHour;
	private Double totalHours;
	private boolean multipleShifts = false;
	private List<String> shiftHours;
	private WeeklyScheduleRow row;
	
	/**
	 * 
	 */
	public WeeklyScheduleDailyEntry() {
	}

	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}

	/**
	 * @return the inHour
	 */
	public Date getInHour() {
		return inHour;
	}

	/**
	 * @param inHour the inHour to set
	 */
	public void setInHour(Date inHour) {
		this.inHour = inHour;
	}

	/**
	 * @return the outHour
	 */
	public Date getOutHour() {
		return outHour;
	}

	/**
	 * @param outHour the outHour to set
	 */
	public void setOutHour(Date outHour) {
		this.outHour = outHour;
	}

	/**
	 * @return the totalHours
	 */
	public Double getTotalHours() {
		if(totalHours == null) {
			setTotalHours(CalendarUtils.differenceInHours(getOutHour(), getInHour()));
		}
		return totalHours;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * 
	 * @return
	 */
	public String getTotalHoursAsString() {
		return CalendarUtils.hoursToTime(getTotalHours());
	}
	
	/**
	 * @return the multipleShifts
	 */
	public boolean isMultipleShifts() {
		return multipleShifts;
	}

	/**
	 * @param multipleShifts the multipleShifts to set
	 */
	public void setMultipleShifts(boolean multipleShifts) {
		this.multipleShifts = multipleShifts;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInHourAsString() {
		return CalendarUtils.dateToDisplayTime(getInHour());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOutHourAsString() {
		return CalendarUtils.dateToDisplayTime(getOutHour());
	}

	/**
	 * 
	 * @param inHour
	 */
	public void setInHourAsString(String inHour) {
		setInHour(CalendarUtils.inputTimeToDate(inHour));
	}

	/**
	 * 
	 * @param inHour
	 */
	public void setOutHourAsString(String outHour) {
		setOutHour(CalendarUtils.inputTimeToDate(outHour));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isShift() {
		return getInHour() != null && getOutHour() != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEditable() {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return true;
		} else {
			return CalendarUtils.isAfterToday(getDay());
		}
	}

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public void addShiftHours(Date from, Date to) {
		getShiftHours().add(CalendarUtils.dateToDisplayTime(from) + " - " + CalendarUtils.dateToDisplayTime(to));
	}
	
	/**
	 * @return the shiftHours
	 */
	public List<String> getShiftHours() {
		if(shiftHours == null) {
			setShiftHours(new ArrayList<String>());
		}
		return shiftHours;
	}

	/**
	 * @param shiftHours the shiftHours to set
	 */
	private void setShiftHours(List<String> shiftHours) {
		this.shiftHours = shiftHours;
	}
	
	/**
	 * 
	 * @return
	 * @see java.lang.Object#clone()
	 */
	public WeeklyScheduleDailyEntry clone() {
		WeeklyScheduleDailyEntry cloned = new WeeklyScheduleDailyEntry();
		cloned.setDay(getDay());
		cloned.setInHour(getInHour());
		cloned.setOutHour(getOutHour());
		cloned.setMultipleShifts(isMultipleShifts());
		cloned.setTotalHours(getTotalHours());
		cloned.setRow(getRow());
		return cloned;
	}

	/**
	 * @return the row
	 */
	public WeeklyScheduleRow getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(WeeklyScheduleRow row) {
		this.row = row;
	}
}
