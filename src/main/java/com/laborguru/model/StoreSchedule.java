/*
 * File name: StoreSchedule.java
 * Creation date: Sep 20, 2008 5:09:08 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreSchedule extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4145515420736901455L;
	
	private Integer id;
	private Store store;
	private Date day;
	private Set<EmployeeSchedule> employeeSchedules;

	/**
	 * 
	 */
	public StoreSchedule() {
	}
	
	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
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
		this.day = CalendarUtils.removeTimeFromDate(day);
	}

	/**
	 * @return the employeeSchedules
	 */
	public Set<EmployeeSchedule> getEmployeeSchedules() {
		if(employeeSchedules == null) {
			setEmployeeSchedules(new HashSet<EmployeeSchedule>());
		}		
		return employeeSchedules;
	}

	/**
	 * @param employeeSchedules the employeeSchedules to set
	 */
	public void setEmployeeSchedules(Set<EmployeeSchedule> employeeSchedules) {
		this.employeeSchedules = employeeSchedules;
	}

	/**
	 * @param other
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final StoreSchedule other = (StoreSchedule) obj;
		
		return new EqualsBuilder().append(this.day, other.day)
		.append(this.store != null? this.store.getId():null, other.store != null? other.store.getId():null)
		.isEquals();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.day)
		.append(this.store != null? this.store.getId():null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("store" , store)
	   	.append("day", day)
	   	.toString();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @param employee
	 * @return
	 */
	public EmployeeSchedule getEmployeeSchedule(Employee employee) {
		for(EmployeeSchedule anEmployeeSchedule : getEmployeeSchedules()) {
			if(anEmployeeSchedule.getEmployee() != null && anEmployeeSchedule.getEmployee().getId() != null && employee != null && anEmployeeSchedule.getEmployee().getId().equals(employee.getId())) {
				return anEmployeeSchedule;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Position> getSchedulePositions() {
		Set<Position> positions = new HashSet<Position>();
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			positions.addAll(employeeSchedule.getSchedulePositions());
		}
		
		return positions;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Employee> getScheduleEmployees() {
		Set<Employee> employees = new HashSet<Employee>();
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			employees.add(employeeSchedule.getEmployee());
		}
		
		return employees;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHoursWithContiguous() {
		double total = 0.0;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			total += employeeSchedule.getTotalShiftHoursWithContiguous().doubleValue();
		}
		
		return new Double(total);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Double getTotalShiftHoursWithContiguous(Position position) {
		double total = 0.0;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			total += employeeSchedule.getTotalShiftHoursWithContiguous(position).doubleValue();
		}
		
		return new Double(total);
	}	
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHours() {
		double total = 0.0;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			total += NumberUtils.getDoubleValue(employeeSchedule.getTotalShiftHours());
		}
		
		return new Double(total);
	}	

	/**
	 * 
	 * @param position
	 * @return
	 */
	public Double getTotalShiftHours(Position position) {
		double total = 0.0;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			total += employeeSchedule.getTotalShiftHours(position).doubleValue();
		}
		
		return new Double(total);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Set<Employee> getScheduleEmployeesFor(Position position) {
		Set<Employee> employees = new HashSet<Employee>();
		
		List<Shift> positionShifts;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			positionShifts = employeeSchedule.getShiftsFor(position);
			if(positionShifts != null && positionShifts.size() > 0) {
				employees.add(employeeSchedule.getEmployee());
			}
		}		
		
		return employees;
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 */
	public Set<Position> getSchedulePositionsFor(Employee employee) {
		EmployeeSchedule employeeSchedule = getEmployeeSchedule(employee);
		
		return employeeSchedule != null ? employeeSchedule.getSchedulePositions() : new HashSet<Position>();
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Employee> getScheduleEmployeesBreaks() {
		Set<Employee> employees = new HashSet<Employee>();
		
		List<Shift> positionShifts;
		
		for(EmployeeSchedule employeeSchedule : getEmployeeSchedules()) {
			positionShifts = employeeSchedule.getBreakShifts();
			if(positionShifts != null && positionShifts.size() > 0) {
				employees.add(employeeSchedule.getEmployee());
			}
		}		
		
		return employees;		
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getAverageWage() {
		if(NumberUtils.getDoubleValue(getTotalShiftHours()) > 0) {
			return new Double(NumberUtils.getDoubleValue(getTotalWage()) / NumberUtils.getDoubleValue(getTotalShiftHours()));
		} else {
			return new Double(0.0);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalWage() {
		double total = 0.0;
		for(EmployeeSchedule anEmployeeSchedule : getEmployeeSchedules()) {
			total += NumberUtils.getDoubleValue(anEmployeeSchedule.getTotalShiftHours()) * NumberUtils.getDoubleValue(anEmployeeSchedule.getEmployee().getWage());
		}
		
		return new Double(total);		
	}
}
