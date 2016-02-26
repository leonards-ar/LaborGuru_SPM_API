/*
 * File name: AddShiftByEmployeeByDayPrepareAction.java
 * Creation date: Sep 20, 2008 1:26:42 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ctc.wstx.util.StringUtil;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.ScheduleRow;
import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Shift;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AddShiftByEmployeeByDayPrepareAction extends AddShiftByDayBaseAction implements Preparable {
	private static final Logger log = Logger.getLogger(AddShiftByEmployeeByDayPrepareAction.class);
	
	private List<ScheduleRow> scheduleData;
	private List<Integer> minimumStaffing;
	
	private Integer newEmployeeId;
	private String newEmployeeName;
	private Integer newEmployeePositionId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8960503469589990853L;

	/**
	 * 
	 */
	public AddShiftByEmployeeByDayPrepareAction() {
	}

	/**
	 * 
	 * @return
	 */
	public Integer getScheduleRows() {
		return new Integer(getScheduleData().size());
	}
	
	/**
	 * Loads position and status list
	 */
	private void loadPageData() {
		loadPositions();
		loadEmployees();
		loadCalendarData();
		loadCopyTargetDay();
	}
	
	/**
	 * 
	 */
	public Integer getTotalMinimutStaffingMinutes() {
		int total = 0;
		
		for(Integer i : getMinimumStaffing()) {
			total += i != null ? i.intValue() : 0;
		}
		
		return new Integer(SpmConstants.MINUTES_INTERVAL * total);
	}
	
	/**
	 * 
	 */
	public String getTotalMinimutStaffingTime() {
		Integer min = getTotalMinimutStaffingMinutes();
		return CalendarUtils.minutesToTime(min);
	}
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		// It's needed by the Preparable interface, don't comment out or removed
	}
	
	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#prepareChangeDay()
	 */
	@Override
	public void prepareChangeDay() {
		loadPageData();
	}

	/**
	 * 
	 */
	public void prepareSelectPosition() {
		loadPageData();
	}
	
	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#prepareChangeWeek()
	 */
	@Override
	public void prepareChangeWeek() {
		loadPageData();
	}

	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#processChangeDay()
	 */
	@Override
	protected void processChangeDay() {
		setStoreSchedule(null);
		resetScheduleData();
		resetStaffingData();
		resetDayData();
		
		setScheduleData();
		loadCopyTargetDay();
	}

	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#processChangeWeek()
	 */
	@Override
	protected void processChangeWeek() {
		setStoreSchedule(null);
		resetScheduleData();
		resetStaffingData();
		resetDayData();
		
		setScheduleData();
		loadCopyTargetDay();
	}

	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareEdit() {
		loadPageData();
	}
	
	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareSave() {
		loadPageData();
	}

	/**
	 * 
	 */
	public void prepareCopySchedule() {
		loadPageData();
	}
	
	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareCancel() {
		loadPageData();
	}
	
	/**
	 * 
	 */
	private void setScheduleData() {
		if(scheduleData == null || scheduleData.isEmpty()) {
			setScheduleData(buildScheduleFor(getSelectedPositions()));
		}
		
		if(minimumStaffing == null || minimumStaffing.isEmpty()) {
			setMinimumStaffing(buildMinimumStaffingFor(getSelectedPositions()));
		}
	}
	
	/**
	 * 
	 */
	public void prepareSelectView() {
		loadPageData();
	}

	/**
	 * 
	 * @return
	 */
	public String selectView() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		resetScheduleData();
		resetStaffingData();
		
		resetDayData();
		
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String edit() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		initializeSelectView();
		initializeCopyTargetDay();
		
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * 
	 * @return
	 */
	public String selectPosition() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
	
		resetScheduleData();
		resetStaffingData();
		resetDayData();
		
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 */
	public void prepareAddEmployee() {
		loadPageData();
	}
	
	/**
	 * 
	 * @return
	 */
	public String addEmployee() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		Employee newEmployee = null;
		if(getNewEmployeeId() != null) {
			newEmployee = getEmployeeService().getEmployeeById(new Employee(getNewEmployeeId()));

		}
		
		ScheduleRow newRow = new ScheduleRow();
		newRow.setEmployeeId(getNewEmployeeId());
		newRow.setOriginalEmployeeId(getNewEmployeeId());
		newRow.setPositionId(getNewEmployeePositionId());
		Position newPosition = getPosition(getNewEmployeePositionId());
		newRow.setPositionName(newPosition != null ? newPosition.getName() : null);
		newRow.setEmployeeName(getNewEmployeeName());
		newRow.setSchedule(initializeScheduleRow());
		newRow.setHours(initializeScheduleHoursRow());

		if(newEmployee != null) {
			newRow.setEmployeeMaxDaysWeek(newEmployee.getMaxDaysWeek());
			newRow.setEmployeeMaxHoursDay(newEmployee.getMaxHoursDay());
			newRow.setEmployeeMaxHoursWeek(newEmployee.getMaxHoursWeek());	
			newRow.setEmployeeWage(newEmployee.getWage());
		}
		
		getScheduleData().add(newRow);
		
		sortScheduleRows(getScheduleData());
		
		setNewEmployeeId(null);
		setNewEmployeeName(null);
		setNewEmployeePositionId(null);
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String copySchedule() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

		if(log.isDebugEnabled()) {
			log.debug("About to copy schedule data " + getScheduleData() + " to date " + getCopyTargetDay());
		}

		Date currentDay = getWeekDaySelector().getSelectedDay();
		List<Object> params = new ArrayList<Object>();
		params.add(getCopyTargetDay());
		params.add(currentDay);
		params.add(new Date());

		if(getCopyTargetDay() != null && getCopyTargetDay().after(new Date())) {
			/*
			 * Force selected date to the target date.
			 */
			setStoreSchedule(null);
			getWeekDaySelector().setSelectedDay(getCopyTargetDay());
			
			setSchedule(getScheduleData(), getSelectedPositions());

			if(log.isDebugEnabled()) {
				log.debug("About to copy schedule " + getStoreSchedule());
			}
		
			getScheduleService().save(getStoreSchedule());

			/*
			 * Set selected day back again
			 */
			getWeekDaySelector().setSelectedDay(currentDay);
			setStoreSchedule(null);
			
			addActionMessage(getText("schedule.addshift.copy_success", params));
		} else {
			addActionError(getText("error.schedule.addshift.invalid_target_day", params));
		}
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * 
	 * @return
	 */
	public String save() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		if(log.isDebugEnabled()) {
			log.debug("About to save schedule data " + getScheduleData());
		}
		
		Map<Integer, List<Shift>> shiftsWithContiguous = retrieveCurrentShiftsWithContiguous(getSelectedPositions());
		Map<Integer, List<Shift>> referencedShifts = retrieveCurrentReferencedShifts(getSelectedPositions());
		
		setSchedule(getScheduleData(), getSelectedPositions());
		
		List<Shift> outOfScheduleShiftsToUpdate = new ArrayList<Shift>(shiftsWithContiguous.size() + referencedShifts.size());
		outOfScheduleShiftsToUpdate.addAll(updateShiftsWithContiguous(shiftsWithContiguous));
		outOfScheduleShiftsToUpdate.addAll(updateReferencedShifts(referencedShifts));
		
		if(log.isDebugEnabled()) {
			log.debug("About to save schedule " + getStoreSchedule());
		}
	
		getScheduleService().save(getStoreSchedule());

		updateShifts(outOfScheduleShiftsToUpdate);
		
		resetScheduleData();
		setScheduleData();
		
		addActionMessage(getText("schedule.addshift.save_success"));
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 */
	private void resetScheduleData() {
		setScheduleData(null);
	}
	
	/**
	 * 
	 */
	private void resetStaffingData() {
		setMinimumStaffing(null);
	}
	
	/**
	 * 
	 * @return
	 */
	public String cancel() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		resetScheduleData();
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * @return the scheduleData
	 */
	public List<ScheduleRow> getScheduleData() {
		if(scheduleData == null) {
			setScheduleData(new ArrayList<ScheduleRow>());
		}
		return scheduleData;
	}

	/**
	 * @param scheduleData the scheduleData to set
	 */
	public void setScheduleData(List<ScheduleRow> scheduleData) {
		this.scheduleData = scheduleData;
	}

	/**
	 * @return the newEmployeeId
	 */
	public Integer getNewEmployeeId() {
		return newEmployeeId;
	}

	/**
	 * @param newEmployeeId the newEmployeeId to set
	 */
	public void setNewEmployeeId(Integer newEmployeeId) {
		this.newEmployeeId = newEmployeeId;
	}

	/**
	 * @return the newEmployeePositionId
	 */
	public Integer getNewEmployeePositionId() {
		return newEmployeePositionId;
	}

	/**
	 * @param newEmployeePositionId the newEmployeePositionId to set
	 */
	public void setNewEmployeePositionId(Integer newEmployeePositionId) {
		this.newEmployeePositionId = newEmployeePositionId;
	}

	/**
	 * @return the newEmployeeName
	 */
	public String getNewEmployeeName() {
		return newEmployeeName;
	}

	/**
	 * @param newEmployeeName the newEmployeeName to set
	 */
	public void setNewEmployeeName(String newEmployeeName) {
		this.newEmployeeName = newEmployeeName;
	}

	/**
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		if(StringUtils.isNotBlank(getSaveSchedule()) || StringUtils.isNotBlank(getCopySchedule())) {
			initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
			
			validateSchedule(getScheduleData());
		}
	}

	/**
	 * @return the minimumStaffing
	 */
	public List<Integer> getMinimumStaffing() {
		if(minimumStaffing == null) {
			setMinimumStaffing(new ArrayList<Integer>());
		}		
		return minimumStaffing;
	}

	/**
	 * @param minimumStaffing the minimumStaffing to set
	 */
	public void setMinimumStaffing(List<Integer> minimumStaffing) {
		this.minimumStaffing = minimumStaffing;
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#initializeSelectView()
	 */
	@Override
	protected void initializeSelectView() {
		setSelectView("addshiftbyemployee_selectView.action");
	}
}
