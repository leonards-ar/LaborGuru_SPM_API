/*
 * File name: ScheduleBaseAction.java
 * Creation date: 11/01/2009 09:06:37
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.frontend.model.ScheduleRow;
import com.laborguru.model.Employee;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.model.comparator.PositionNameComparator;
import com.laborguru.model.comparator.UserFullNameComparator;
import com.laborguru.util.CalendarUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class ScheduleBaseAction extends SpmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ScheduleBaseAction() {
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	protected Date getStoreScheduleStartHour(Date day) {
		return getEmployeeStore() != null ? getEmployeeStore().getStoreScheduleStartHour(CalendarUtils.getDayOfWeek(day)) : null;
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	protected OperationTime getOperationTime(Date day) {
		Store store = getEmployeeStore();
		return store != null ? store.getOperationTime(CalendarUtils.getDayOfWeek(day)) : null;
	}
	
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	protected Date getStoreScheduleEndHour(Date day) {
		return getEmployeeStore() != null ? getEmployeeStore().getStoreScheduleEndHour(CalendarUtils.getDayOfWeek(day)) : null;
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	protected boolean isMultiDaySchedule(Date day) {
		return getEmployeeStore() != null ? getEmployeeStore().isMultiDaySchedule(CalendarUtils.getDayOfWeek(day)) : false;
	}	

	/**
	 * 
	 * @param day
	 * @return
	 */
	protected boolean isMultiDayScheduleWithExtraHours(Date day) {
		return getEmployeeStore() != null ? getEmployeeStore().isMultiDayScheduleWithExtraHours(CalendarUtils.getDayOfWeek(day)) : false;
	}	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	protected List<Employee> sortEmployees(List<Employee> data) {
		Collections.sort(data, new UserFullNameComparator());
		return data;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	protected List<Position> sortPositions(List<Position> data) {
		Collections.sort(data, new PositionNameComparator());
		return data;
	}
	
	/**
	 * 
	 */
	protected List<ScheduleRow> sortScheduleRows(List<ScheduleRow> data) {
		if(data != null) {
			Collections.sort(data);
		}
		return data;
	}	
}
