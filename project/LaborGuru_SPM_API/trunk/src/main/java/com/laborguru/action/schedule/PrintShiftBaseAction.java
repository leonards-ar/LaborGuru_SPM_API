/*
 * File name: PrintShiftBaseAction.java
 * Creation date: Jan 7, 2009 2:08:16 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import org.apache.log4j.Logger;

import com.laborguru.service.schedule.ScheduleService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class PrintShiftBaseAction extends ScheduleShiftBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AddShiftBaseAction.class);
	
	private ScheduleService scheduleService;
	
	/**
	 * 
	 */
	public PrintShiftBaseAction() {
		super();
	}

	/**
	 * @return the scheduleService
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * @param scheduleService the scheduleService to set
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}	
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.ScheduleShiftBaseAction#onWeekdaySelectorChange()
	 */
	@Override
	protected void onWeekdaySelectorChange() {
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.ScheduleShiftBaseAction#prepareChangeDay()
	 */
	@Override
	public void prepareChangeDay() {
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.ScheduleShiftBaseAction#prepareChangeWeek()
	 */
	@Override
	public void prepareChangeWeek() {
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.ScheduleShiftBaseAction#processChangeDay()
	 */
	@Override
	protected void processChangeDay() {
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.ScheduleShiftBaseAction#processChangeWeek()
	 */
	@Override
	protected void processChangeWeek() {
	}
}
