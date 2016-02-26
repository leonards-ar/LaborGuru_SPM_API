/*
 * File name: Shift.java
 * Creation date: Sep 20, 2008 5:10:17 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;

/**
 * This class represents a shift which is a period of time in which an
 * employee occupies a position.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class Shift extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8048083516798654020L;
	
	private Integer id;
	private EmployeeSchedule employeeSchedule;
	private Date fromHour;
	private Date toHour;
	private Position position;
	private Integer shiftIndex;
	private Shift contiguousShift;
	private Shift startingShift;
	private Integer contiguousShiftId;
	private Integer startingShiftId;
	private boolean referencedShiftToKeep;
	
	/**
	 * 
	 */
	public Shift() {
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
		
		final Shift other = (Shift) obj;
		
		return new EqualsBuilder().append(this.fromHour, other.fromHour)
		.append(this.toHour, other.toHour)
		.append(this.employeeSchedule != null? this.employeeSchedule.getId():null, other.employeeSchedule != null? other.employeeSchedule.getId():null)
		.isEquals();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.fromHour)
		.append(this.toHour)
		.append(this.employeeSchedule != null? this.employeeSchedule.getId():null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("employeeSchedule" , employeeSchedule)
	   	.append("from", fromHour)
	   	.append("to", toHour)
	   	.append("position", position)
	   	.toString();	
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBreak() {
		return this.position == null;
	}

	/**
	 * @return the fromHour
	 */
	public Date getFromHour() {
		return fromHour;
	}

	/**
	 * @param fromHour the fromHour to set
	 */
	public void setFromHour(Date fromHour) {
		this.fromHour = fromHour;
	}

	/**
	 * @return the toHour
	 */
	public Date getToHour() {
		return toHour;
	}

	/**
	 * @param toHour the toHour to set
	 */
	public void setToHour(Date toHour) {
		this.toHour = toHour;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
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
	 * @return the employeeSchedule
	 */
	public EmployeeSchedule getEmployeeSchedule() {
		return employeeSchedule;
	}

	/**
	 * @param employeeSchedule the employeeSchedule to set
	 */
	public void setEmployeeSchedule(EmployeeSchedule employeeSchedule) {
		this.employeeSchedule = employeeSchedule;
	}

	/**
	 * @return the shiftIndex
	 */
	public Integer getShiftIndex() {
		return shiftIndex;
	}

	/**
	 * @param shiftIndex the shiftIndex to set
	 */
	public void setShiftIndex(Integer shiftIndex) {
		this.shiftIndex = shiftIndex;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHours() {
		return CalendarUtils.differenceInHours(getToHour(), getFromHour());
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHoursWithContiguous() {
		return CalendarUtils.differenceInHours(getToHourWithContiguousShift(), getFromHour());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasContiguousShift() {
		return getContiguousShift() != null;
	}
	
	/**
	 * @return the contiguousShift
	 */
	public Shift getContiguousShift() {
		return contiguousShift;
	}

	/**
	 * @param contiguousShift the contiguousShift to set
	 */
	public void setContiguousShift(Shift contiguousShift) {
		if(contiguousShift != null) {
			contiguousShift.startingShift = this;
			contiguousShift.setStartingShiftId(this.getId());
		}
		setContiguousShiftId(contiguousShift != null ? contiguousShift.getId() : null);
		this.contiguousShift = contiguousShift;
	}	
	
	/**
	 * @return the toHour
	 */
	public Date getToHourWithContiguousShift() {
		return hasContiguousShift() ? getContiguousShift().getToHour() : getToHour();
	}

	/**
	 * @return the referencedShift
	 */
	public boolean isReferencedShift() {
		return getStartingShift() != null;
	}

	/**
	 * @return the startingShift
	 */
	public Shift getStartingShift() {
		return startingShift;
	}

	/**
	 * @param startingShift the startingShift to set
	 */
	public void setStartingShift(Shift startingShift) {
		if(startingShift != null) {
			startingShift.contiguousShift = this;
			startingShift.setContiguousShiftId(this.getId());
		}
		setStartingShiftId(startingShift != null ? startingShift.getId() : null);
		this.startingShift = startingShift;
	}

	/**
	 * @return the contiguousShiftId
	 */
	public Integer getContiguousShiftId() {
		if(contiguousShiftId == null && getContiguousShift() != null) {
			setContiguousShiftId(getContiguousShift().getId());
		}
		return contiguousShiftId;
	}

	/**
	 * @param contiguousShiftId the contiguousShiftId to set
	 */
	public void setContiguousShiftId(Integer contiguousShiftId) {
		this.contiguousShiftId = contiguousShiftId;
	}

	/**
	 * @return the startingShiftId
	 */
	public Integer getStartingShiftId() {
		if(startingShiftId == null && getStartingShift() != null) {
			setStartingShiftId(getStartingShift().getId());
		}
		return startingShiftId;
	}

	/**
	 * @param startingShiftId the startingShiftId to set
	 */
	private void setStartingShiftId(Integer startingShiftId) {
		this.startingShiftId = startingShiftId;
	}

	/**
	 * @return the referencedShiftToKeep
	 */
	public boolean isReferencedShiftToKeep() {
		return referencedShiftToKeep;
	}

	/**
	 * @param referencedShiftToKeep the referencedShiftToKeep to set
	 */
	public void setReferencedShiftToKeep(boolean referencedShiftToKeep) {
		this.referencedShiftToKeep = referencedShiftToKeep;
	}
	
	/**
	 * 
	 * @return
	 */
	private Store getStore() {
		try {
			return getEmployeeSchedule().getStoreSchedule().getStore();
		} catch(NullPointerException ex) {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private DayOfWeek getDayOfWeek() {
		try {
			return CalendarUtils.getDayOfWeek(getEmployeeSchedule().getStoreSchedule().getDay());
		} catch(Throwable ex) {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private OperationTime getStoreOperationTime() {
		Store store = getStore();
		DayOfWeek dof = getDayOfWeek();
		if(store != null && dof != null) {
			return getStore().getOperationTime(dof);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Date getStoreStartTime() {
		Store store = getStore();
		DayOfWeek dof = getDayOfWeek();
		if(store != null && dof != null) {
			return getStore().getStoreScheduleStartHour(dof);
		} else {
			return null;
		}		
	}
	
	/**
	 * 
	 * @return
	 */
	private Date getStoreEndTime() {
		Store store = getStore();
		DayOfWeek dof = getDayOfWeek();
		if(store != null && dof != null) {
			return getStore().getStoreScheduleEndHour(dof);
		} else {
			return null;
		}			
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getServiceHours() {
		OperationTime opTime = getStoreOperationTime();
		if(CalendarUtils.equalsTime(opTime.getOpenHour(), opTime.getCloseHour())) {
			// Operation time is the whole day!
			return CalendarUtils.differenceInHours(getToHour(), getFromHour());
		} else {
			if(CalendarUtils.inRangeIncludingEndTime(getFromHour(), opTime.getOpenHour(), opTime.getCloseHour()) && CalendarUtils.inRangeIncludingEndTime(getToHour(), opTime.getOpenHour(), opTime.getCloseHour())) {
				// The whole shift is inside service period
				return CalendarUtils.differenceInHours(getToHour(), getFromHour());
			} else if(CalendarUtils.inRangeNotIncludingEndTime(getFromHour(), opTime.getOpenHour(), opTime.getCloseHour())) {
				// The shift starts on service period
				return CalendarUtils.differenceInHours(opTime.getCloseHour(), getFromHour());
			} else if(CalendarUtils.inRangeNotIncludingStartTime(getToHour(), opTime.getOpenHour(), opTime.getCloseHour())) {
				// The shift ends on service period
				return CalendarUtils.differenceInHours(getToHour(), opTime.getOpenHour());
			} else if(CalendarUtils.inRangeIncludingEndTime(opTime.getOpenHour(), getFromHour(), getToHour()) && CalendarUtils.inRangeIncludingEndTime(opTime.getCloseHour(), getFromHour(), getToHour())) {
				// The shift occupies all the service period and more!
				return CalendarUtils.differenceInHours(opTime.getCloseHour(), opTime.getOpenHour());
			} else {
				// No service hours
				return new Double(0.0);
			}
		}
	}

	/**
	 * 
	 * @param serviceHours
	 */
	public void setServiceHours(Double serviceHours) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getOpeningHours() {
		OperationTime opTime = getStoreOperationTime();
		Date start = getStoreStartTime();
		if(CalendarUtils.equalsTime(opTime.getOpenHour(), opTime.getCloseHour())) {
			// Operation time is the whole day!
			return new Double(0.0);
		} else if(CalendarUtils.equalsTime(opTime.getOpenHour(), start)) {
			// There is no opening time configured
			return new Double(0.0);
		} else if(CalendarUtils.inRangeNotIncludingEndTime(getFromHour(), start, opTime.getOpenHour()) && CalendarUtils.inRangeNotIncludingEndTime(getToHour(), start, opTime.getOpenHour())) {
			// The whole shift is inside the opening period
			return CalendarUtils.differenceInHours(getToHour(), getFromHour());
		} else if(CalendarUtils.inRangeNotIncludingEndTime(getToHour(), start, opTime.getOpenHour())) {
			// The shifts ends in opening period
			return CalendarUtils.differenceInHours(getToHour(), start);
		} else if(CalendarUtils.inRangeNotIncludingEndTime(getFromHour(), start, opTime.getOpenHour())) {
			// The shifts starts in opening period
			return CalendarUtils.differenceInHours(opTime.getOpenHour(), getFromHour());
		} else if(CalendarUtils.smallerTime(getFromHour(), start)) {
			// This should never happen if validation is done while saving schedule
			// There are two options: the whole shift is before open hour or not
			return CalendarUtils.differenceInHours(CalendarUtils.smallerTime(getToHour(), opTime.getOpenHour()) ? getToHour() : opTime.getOpenHour(), getFromHour());
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @param openingHours
	 */
	public void setOpeningHours(Double openingHours) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getClosingHours() {
		OperationTime opTime = getStoreOperationTime();
		Date end = getStoreEndTime();
		
		if(CalendarUtils.equalsTime(opTime.getOpenHour(), opTime.getCloseHour())) {
			// Operation time is the whole day!
			return new Double(0.0);
		} else if(CalendarUtils.equalsTime(opTime.getCloseHour(), end)) {
			// There is no closing time configured
			return new Double(0.0);
		} else if(CalendarUtils.inRangeNotIncludingEndTime(getFromHour(), opTime.getCloseHour(), end) && CalendarUtils.inRangeNotIncludingStartTime(getToHour(), opTime.getCloseHour(), end)) {
			// The whole shift is inside the closing period
			return CalendarUtils.differenceInHours(getToHour(), getFromHour());
		} else if(CalendarUtils.inRangeNotIncludingEndTime(getFromHour(), opTime.getCloseHour(), end)) {
			// The shifts starts in closing period
			return CalendarUtils.differenceInHours(end, getFromHour());
		} else if(CalendarUtils.inRangeNotIncludingStartTime(getToHour(), opTime.getCloseHour(), end)) {
			// The shifts ends in closing period
			return CalendarUtils.differenceInHours(getToHour(), opTime.getCloseHour());
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @param closingHours
	 */
	public void setClosingHours(Double closingHours) {
		
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
