/*
 * File name: PrintShiftByPositionByWeekPrepareAction.java
 * Creation date: Jan 7, 2009 2:20:28 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Employee;
import com.laborguru.model.EmployeeSchedule;
import com.laborguru.model.Position;
import com.laborguru.model.Shift;
import com.laborguru.model.StoreSchedule;
import com.laborguru.util.CalendarUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PrintTotalHoursByPositionByWeekPrepareAction extends PrintShiftBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PrintTotalHoursByPositionByWeekPrepareAction.class);
	
	private List<StoreSchedule> storeSchedules = null;
	private List<Date> weekDays = null;
	
	/**
	 * 
	 */
	public PrintTotalHoursByPositionByWeekPrepareAction() {
	}

	/**
	 * @return the storeSchedule
	 */
	protected List<StoreSchedule> getStoreSchedules() {
		if(storeSchedules == null) {
			storeSchedules = new ArrayList<StoreSchedule>();
			try {
				StoreSchedule aSchedule;
				//:TODO: Probably should retrieve the whole week from the database
				for(Date aDate : getWeekDays()) {
					aSchedule = getScheduleService().getStoreScheduleByDate(getEmployeeStore(), aDate);
					if(aSchedule == null) {
						aSchedule = new StoreSchedule();
						aSchedule.setStore(getEmployeeStore());
						aSchedule.setDay(aDate);
					}					
					storeSchedules.add(aSchedule);
				}
			} catch(RuntimeException ex) {
				log.error("Could not retrieve schedule for week days " + getWeekDays() + " for store " + getEmployeeStore(), ex);
			}

		}
		return storeSchedules;
	}
	
	/**
	 * @return the weekDays
	 */
	public List<Date> getWeekDays() {
		if(weekDays == null) {
			this.weekDays = getWeekDaySelector().getWeekDays();
		}
		return weekDays;
	}
	
	/**
	 * 
	 * @return
	 */
	public String view() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
	}

	/**
	 * @param weekDays the weekDays to set
	 */
	public void setWeekDays(List<Date> weekDays) {
		this.weekDays = weekDays;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Position> getWeeklySchedulePositions() {
		Set<Position> positions = new HashSet<Position>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			positions.addAll(schedule.getSchedulePositions());
		}
		
		return new ArrayList<Position>(positions);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Employee> getWeeklyScheduleEmployeesFor(Position position) {
		Set<Employee> employees = new HashSet<Employee>();
		
		for(StoreSchedule schedule : getStoreSchedules()) {
			employees.addAll(schedule.getScheduleEmployeesFor(position));
		}
		
		return sortEmployees(new ArrayList<Employee>(employees));
	}
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @param dayIndex
	 * @return
	 */
	public List<Shift> getShiftsFor(Position position, Employee employee, int dayIndex) {
		StoreSchedule storeSchedule = getStoreSchedules().get(dayIndex);
		EmployeeSchedule schedule = storeSchedule.getEmployeeSchedule(employee);
		if(schedule != null) {
			return schedule.getShiftsFor(position);
		} else if(schedule != null && position == null) {
			return schedule.getBreakShifts();
		} else {
			return new ArrayList<Shift>();
		}
	}

	/**
	 * 
	 * @param position
	 * @param employee
	 * @param dayIndex
	 * @return
	 */
	public String getTotalHoursFor(Position position, Employee employee, int dayIndex) {
		List<Shift> shifts = getShiftsFor(position, employee, dayIndex);
		double total = 0.0;
		
		if(shifts != null) {
			for(Shift shift : shifts) {
				total += shift.getTotalShiftHours().doubleValue();
			}
		}
		
		return total > 0.0 ? CalendarUtils.hoursToTime(new Double(total)) : "&nbsp;";
	}
	
	/**
	 * 
	 * @param position
	 * @param employee
	 * @return
	 */
	public String getWeekTotalHoursFor(Position position, Employee employee) {
		double total = 0.0;
		for(int i=0; i < getWeekDays().size(); i++) {
			for(Shift shift : getShiftsFor(position, employee, i)) {
				total += shift.getTotalShiftHours().doubleValue();
			}
		}
		return CalendarUtils.hoursToTime(new Double(total));
	}
}
