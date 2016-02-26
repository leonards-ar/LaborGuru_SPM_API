/**
 * 
 */
package com.laborguru.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;

/**
 * This class represents a shift which is a period of time in which an
 * employee occupies a position.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.1
 *
 */
public class CompleteShift extends SpmObject {
	
	private List<Shift> shifts = null;
	private Position position;
	private Employee employee;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CompleteShift() {
	}

	/**
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("shifts" , shifts)
	   	.toString();
	}

	/**
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
		
		final CompleteShift other = (CompleteShift) obj;
		
		return new EqualsBuilder().append(this.shifts, other.shifts)
		.append(this.position, other.position)
		.append(this.employee, other.employee)
		.isEquals();
	}

	/**
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.shifts)
		.append(this.position)
		.append(this.employee)
		.toHashCode();
	}

	/**
	 * 
	 * @return
	 */
	public List<Shift> getShifts() {
		if(shifts == null)
		{
			setShifts(new ArrayList<Shift>());
		}
		return shifts;
	}

	/**
	 * 
	 * @param shifts
	 */
	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	/**
	 * 
	 * @return
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * 
	 * @return
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * 
	 * @param employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	/**
	 * Adds a new shifts. Returns true if the shift belonged to the same
	 * complete shift (this means it was added) or false if the shift
	 * was not part of this whole shift (thus was not added)
	 * Complete shifts cannot start with breaks
	 * @param shift
	 * @return
	 */
	public boolean addShift(Shift shift) {
		Shift lastShift = getShifts().size() > 0 ? getShifts().get(getShifts().size() - 1) : null;
		if(shift != null && (lastShift == null || CalendarUtils.equalsTime(lastShift.getToHour(), shift.getFromHour()))) {
			if(!(lastShift == null && shift.isBreak())) {
				shifts.add(shift);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the fromHour
	 */
	public Date getFromHour() {
		Shift firstShift = getShifts().size() > 0 ? getShifts().get(0) : null;
		return firstShift != null ? firstShift.getFromHour() : null;
	}

	/**
	 * @return the toHour
	 */
	public Date getToHour() {
		int size = getShifts().size();
		Shift lastShift = null;
		for(int i = size - 1; i >=0; i--) {
			lastShift = getShifts().get(i);
			if(!lastShift.isBreak()) {
				return lastShift.getToHour();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFromHourAsString() {
		return CalendarUtils.dateToDisplayTime(getFromHour());
	}

	/**
	 * 
	 * @return
	 */
	public String getToHourAsString() {
		return CalendarUtils.dateToDisplayTime(getToHour());
	}
}
