/*
 * File name: ScheduleShiftBaseAction.java
 * Creation date: Jan 7, 2009 2:11:31 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.WeekDaySelector;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class ScheduleShiftBaseAction extends ScheduleBaseAction implements Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ScheduleShiftBaseAction.class);
	
	private WeekDaySelector weekDaySelector;
	private String selectedDate;
	private String selectedWeekDay;
	
	/**
	 * 
	 */
	public ScheduleShiftBaseAction() {
	}

	/**
	 * @return the weekDaySelector
	 */
	public WeekDaySelector getWeekDaySelector() {
		if(weekDaySelector == null) {
			weekDaySelector = new WeekDaySelector(getEmployeeStore().getFirstDayOfWeek());
		}
		return weekDaySelector;
	}
	
	/**
	 * @param weekDaySelector the weekDaySelector to set
	 */
	public void setWeekDaySelector(WeekDaySelector weekDaySelector) {
		this.weekDaySelector = weekDaySelector;
	}
	
	/**
	 * Performs any needed calculations after
	 * a change week action is issued.
	 */
	protected abstract void processChangeWeek();

	/**
	 * Performs any needed calculations after
	 * a change day action is issued.
	 */
	protected abstract void processChangeDay();
	
	/**
	 * 
	 */
	protected abstract void onWeekdaySelectorChange();
	
	/**
	 * Actions that must be performed before
	 * this action is executed
	 */
	public abstract void prepareChangeWeek();
	
	/**
	 * Actions that must be performed before
	 * this action is executed
	 */
	public abstract void prepareChangeDay();
	
	/**
	 * 
	 * @param weekSelectedDay
	 * @param selectedDay
	 */
	protected void initializeDayWeekSelector(String weekSelectedDay, String selectedDay) {
		if(log.isDebugEnabled()) {
			log.debug("Initializing day week selector with weekSelectedDay: " + weekSelectedDay + " and selectedDay: " + selectedDay);
		}
		getWeekDaySelector().initializeChangeDay(weekSelectedDay, selectedDay);
	}

	/**
	 * 
	 */
	protected void loadCalendarData() {
		setSelectedDate(getWeekDaySelector().getStringStartingWeekDay());
		setSelectedWeekDay(getWeekDaySelector().getStringSelectedDay());
	}
	
	/**
	 * 
	 * @return
	 */
	public String changeWeek() {
		getWeekDaySelector().initializeChangeWeek(getSelectedDate(), getSelectedWeekDay());
		
		processChangeWeek();
		
		loadCalendarData();
		
		onWeekdaySelectorChange();
		
		return SpmActionResult.INPUT.getResult();
	}

	/**
	 * 
	 * @return
	 */
	public String changeDay() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		processChangeDay();
		
		loadCalendarData();

		onWeekdaySelectorChange();
		
		return SpmActionResult.INPUT.getResult();
	}
	
	/**
	 * @return the selectedDate
	 */
	public String getSelectedDate() {
		return selectedDate;
	}

	/**
	 * @param selectedDate the selectedDate to set
	 */
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	/**
	 * @return the selectedWeekDay
	 */
	public String getSelectedWeekDay() {
		return selectedWeekDay;
	}

	/**
	 * @param selectedWeekDay the selectedWeekDay to set
	 */
	public void setSelectedWeekDay(String selectedWeekDay) {
		this.selectedWeekDay = selectedWeekDay;
	}
}
