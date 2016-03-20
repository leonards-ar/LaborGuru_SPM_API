/*
 * File name: WeeklyScheduleRow.java
 * Creation date: 08/12/2008 11:15:09
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class WeeklyScheduleRow implements Serializable, Comparable<WeeklyScheduleRow> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8384388191864678705L;
	private Integer positionId;
	private String positionName;
	private Integer positionIndex;
	
	private Integer employeeId;
	private Integer originalEmployeeId;
	private String employeeName;
	private List<WeeklyScheduleDailyEntry> weeklySchedule;
	
	private Integer employeeMaxHoursWeek;
	private Integer employeeMaxDaysWeek;
	private Integer employeeMaxHoursDay;
	private Double employeeWage;
	
	private Integer groupById;
	
	private boolean orderByEmployee;
	
	private boolean firstRow = false;
	
	/**
	 * 
	 */
	public WeeklyScheduleRow() {
	}

	/**
	 * @return the positionId
	 */
	public Integer getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		/*
		 * Because of the missing attribute keyValue in the autocompleter
		 * component, then a shadow employeeId must be kept.
		 */
		if(employeeId == null) {
			return getOriginalEmployeeId();
		} else {
			return employeeId;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSelectedEmployeeChange() {
		return employeeId != null && !employeeId.equals(getOriginalEmployeeId());
	}
	
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the weeklySchedule
	 */
	public List<WeeklyScheduleDailyEntry> getWeeklySchedule() {
		if(weeklySchedule == null) {
			setWeeklySchedule(new ArrayList<WeeklyScheduleDailyEntry>());
		}
		return weeklySchedule;
	}

	/**
	 * @param weeklySchedule the weeklySchedule to set
	 */
	public void setWeeklySchedule(List<WeeklyScheduleDailyEntry> weeklySchedule) {
		if(weeklySchedule != null) {
			for(WeeklyScheduleDailyEntry entry : weeklySchedule) {
				entry.setRow(this);
			}
		}
		this.weeklySchedule = weeklySchedule;
	}

	/**
	 * @return the employeeMaxHoursWeek
	 */
	public Integer getEmployeeMaxHoursWeek() {
		return employeeMaxHoursWeek;
	}

	/**
	 * @param employeeMaxHoursWeek the employeeMaxHoursWeek to set
	 */
	public void setEmployeeMaxHoursWeek(Integer employeeMaxHoursWeek) {
		this.employeeMaxHoursWeek = employeeMaxHoursWeek;
	}

	/**
	 * @return the employeeMaxDaysWeek
	 */
	public Integer getEmployeeMaxDaysWeek() {
		return employeeMaxDaysWeek;
	}

	/**
	 * @param employeeMaxDaysWeek the employeeMaxDaysWeek to set
	 */
	public void setEmployeeMaxDaysWeek(Integer employeeMaxDaysWeek) {
		this.employeeMaxDaysWeek = employeeMaxDaysWeek;
	}

	/**
	 * @return the employeeMaxHoursDay
	 */
	public Integer getEmployeeMaxHoursDay() {
		return employeeMaxHoursDay;
	}

	/**
	 * @param employeeMaxHoursDay the employeeMaxHoursDay to set
	 */
	public void setEmployeeMaxHoursDay(Integer employeeMaxHoursDay) {
		this.employeeMaxHoursDay = employeeMaxHoursDay;
	}

	/**
	 * @return the originalEmployeeId
	 */
	public Integer getOriginalEmployeeId() {
		return originalEmployeeId;
	}

	/**
	 * @param originalEmployeeId the originalEmployeeId to set
	 */
	public void setOriginalEmployeeId(Integer originalEmployeeId) {
		this.originalEmployeeId = originalEmployeeId;
	}

	/**
	 * @return the groupById
	 */
	public Integer getGroupById() {
		return groupById;
	}

	/**
	 * @param groupById the groupById to set
	 */
	public void setGroupById(Integer groupById) {
		this.groupById = groupById;
	}

	/**
	 * @return the firstRow
	 */
	public boolean isFirstRow() {
		return firstRow;
	}

	/**
	 * @param firstRow the firstRow to set
	 */
	public void setFirstRow(boolean firstRow) {
		this.firstRow = firstRow;
	}
	
	/**
	 * 
	 */
	public void updateEntries() {
		for(WeeklyScheduleDailyEntry entry : getWeeklySchedule()) {
			entry.setRow(this);
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(WeeklyScheduleRow object) {
		if (object == null){
			return 1;
		}

		if(isOrderByEmployee()) {
			if(getEmployeeName() != null) {
				return object.getEmployeeName() != null ? getEmployeeName().compareToIgnoreCase(object.getEmployeeName()) : 1;
			} else {
				return object.getEmployeeName() != null ? -1 : 0;
			}
		} else {
			// Default is ordering by positionIndex
			if(getPositionIndex() != null) {
				return object.getPositionIndex() != null ? getPositionIndex().compareTo( object.getPositionIndex()) : 1;
			} else if(getPositionName() != null) {
				return object.getPositionName() != null ? getPositionName().compareToIgnoreCase(object.getPositionName()) : 1;
			} else {
				return object.getPositionIndex() != null ? -1 : 0;
			}
		}
	}

	/**
	 * @return the orderByEmployee
	 */
	public boolean isOrderByEmployee() {
		return orderByEmployee;
	}

	/**
	 * @param orderByEmployee the orderByEmployee to set
	 */
	public void setOrderByEmployee(boolean orderByEmployee) {
		this.orderByEmployee = orderByEmployee;
	}

	/**
	 * @return the positionIndex
	 */
	public Integer getPositionIndex() {
		return positionIndex;
	}

	/**
	 * @param positionIndex the positionIndex to set
	 */
	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

	/**
	 * 
	 * @return
	 */
	public Double getEmployeeWage() {
		return employeeWage;
	}

	/**
	 * 
	 * @param employeeWage
	 */
	public void setEmployeeWage(Double employeeWage) {
		this.employeeWage = employeeWage;
	}
	
	
}
