/*
 * File name: AddShiftByEmployeeByWeekPrepareAction.java
 * Creation date: 07/12/2008 20:26:38
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.frontend.model.WeeklyScheduleRow;
import com.laborguru.model.Employee;
import com.laborguru.model.Shift;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AddShiftByEmployeeByWeekPrepareAction extends AddShiftByWeekBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1728336763892104682L;

	/**
	 * 
	 */
	public AddShiftByEmployeeByWeekPrepareAction() {
	}

	/**
	 * 
	 * @param employee
	 * @param shift
	 * @return
	 * @see com.laborguru.action.schedule.AddShiftByWeekBaseAction#getGroupById(com.laborguru.model.Employee, com.laborguru.model.Shift)
	 */
	@Override
	protected Integer getGroupById(Employee employee, Shift shift) {
		return employee != null ? employee.getId() : null;
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 * @see com.laborguru.action.schedule.AddShiftByWeekBaseAction#getEmployeeSchedule(java.lang.Integer)
	 */
	@Override
	protected List<WeeklyScheduleRow> getEmployeeSchedule(Integer employeeId) {
		List<WeeklyScheduleRow> schedule = getWeeklyScheduleData().getScheduleDataFor(employeeId);
		return schedule != null ? schedule : new ArrayList<WeeklyScheduleRow>();
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.action.schedule.AddShiftByWeekBaseAction#getAddEmployeeGroupById()
	 */
	@Override
	protected Integer getAddEmployeeGroupById() {
		return getNewEmployeeId();
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#initializeSelectView()
	 */
	@Override
	protected void initializeSelectView() {
		setSelectView("addweeklyshiftbyemployee_selectView.action");
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.action.schedule.AddShiftByWeekBaseAction#isOrderByEmployee()
	 */
	@Override
	protected boolean isOrderByEmployee() {
		return true;
	}	
}
