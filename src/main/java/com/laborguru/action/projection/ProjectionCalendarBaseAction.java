/*
 * File name: ProjectionCalendarBaseAction.java
 * Creation date: Aug 11, 2008 3:19:42 PM
 * Copyright Mindpool
 */
package com.laborguru.action.projection;

import java.util.Map;
import java.util.TreeMap;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.WeekDaySelector;
import com.laborguru.model.DistributionType;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.projection.ProjectionService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class ProjectionCalendarBaseAction extends SpmAction {

	private static final long serialVersionUID = 1L;

	private ProjectionService projectionService;

	private ReferenceDataService referenceDataService;
	
	private WeekDaySelector weekDaySelector;
	
	private String selectedDate;
	
	private String selectedWeekDay;
	
	private Map<Integer, String> usedWeeksMap;

	private Integer usedWeeks;

	private Boolean projectionError = false;
	
	/**
	 * 
	 */
	public ProjectionCalendarBaseAction() {
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
	 * Performs all the initializations that must be performed before the
	 * destination page is shown
	 */
	protected void pageSetup() {
		if (getUsedWeeks() == null || getUsedWeeks() == 0){
			setUsedWeeks(this.getEmployeeStore().getDailyProjectionsWeeksDefault());			
		}

		setUsedWeeksMap(new TreeMap<Integer, String>(referenceDataService
				.getUsedWeeks()));
	}
	
	/**
	 * @param weekDaySelector the weekDaySelector to set
	 */
	public void setWeekDaySelector(WeekDaySelector weekDaySelector) {
		this.weekDaySelector = weekDaySelector;
	}

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
	 * @return
	 */
	public String changeWeek() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedDate());
		
		processChangeWeek();

		return SpmActionResult.INPUT.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String changeDay() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		processChangeDay();
		
		return SpmActionResult.INPUT.getResult();
	}

	
	/**
	 * @param aSelectedDate
	 */
	protected void initializeDayWeekSelector(String aSelectedDate, String weekDay){
		getWeekDaySelector().setStringStartingWeekDay(aSelectedDate);
		getWeekDaySelector().setStringSelectedDay(weekDay);
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
	
	/**
	 * @return the projectionService
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}


	/**
	 * @param projectionService the projectionService to set
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}

	/**
	 * @return the usedWeeks
	 */
	public Integer getUsedWeeks() {
		return usedWeeks;
	}

	/**
	 * @param usedWeeks
	 *            the usedWeeks to set
	 */
	public void setUsedWeeks(Integer usedWeeks) {
		this.usedWeeks = usedWeeks;
	}

	/**
	 * @return the usedWeeksMap
	 */
	public Map<Integer, String> getUsedWeeksMap() {
		return usedWeeksMap;
	}

	/**
	 * @param usedWeeksMap the usedWeeksMap to set
	 */
	public void setUsedWeeksMap(Map<Integer, String> usedWeeksMap) {
		this.usedWeeksMap = usedWeeksMap;
	}

	/**
	 * @return the referenceDataService
	 */
	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}

	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	/**
	 * Returns whether the week used listbox should be displayed
	 * @return
	 */
	public Boolean getDisplayWeekUsed(){
		return !DistributionType.STATIC.equals(getEmployeeStore().getDistributionType());
	}

	public void setProjectionError(Boolean projectionError) {
		this.projectionError = projectionError;
	}

	public Boolean getProjectionError() {
		return projectionError;
	}
}
