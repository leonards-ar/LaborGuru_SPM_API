/*
 * File name: EmployeeSchedule.java
 * Creation date: Sep 20, 2008 6:13:10 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class EmployeeSchedule extends SpmObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5102605001510861305L;
	
	private Integer id;
	private Employee employee;
	private List<Shift> shifts;
	private StoreSchedule storeSchedule;

	/**
	 * 
	 */
	public EmployeeSchedule() {
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
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the shifts
	 */
	public List<Shift> getShifts() {
		if(shifts == null) {
			shifts = new ArrayList<Shift>();
		}
		return shifts;
	}

	/**
	 * 
	 * @param shiftsToRemove
	 */
	public void removeShifts(Set<Shift> shiftsToRemove) {
		if(shiftsToRemove != null && !shiftsToRemove.isEmpty()) {
			getShifts().removeAll(shiftsToRemove);
			reindexShifts();
		}
	}
	
	/**
	 * 
	 * @param shiftIndex
	 */
	public void removeShift(int shiftIndex) {
		if(shiftIndex >= 0 && shiftIndex < getShifts().size()) {
			getShifts().remove(shiftIndex);
			
			if(shiftIndex < getShifts().size()) {
				// The shift removed is not the last one. So reindex
				reindexShifts();
			}
		}
	}
	
	/**
	 * 
	 */
	public void reindexShifts() {
		List<Shift> reindexed = new ArrayList<Shift>(getShifts().size());
		boolean updateList = false;
		int index = 0;
		
		for(Shift aShift : getShifts()) {
			if(aShift != null) {
				aShift.setShiftIndex(new Integer(index));
				reindexed.add(aShift);
				++index;
			} else {
				updateList = true;
			}
		}
		
		if (updateList) {
			getShifts().clear();
			getShifts().addAll(reindexed);
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public boolean hasMultipleShifts(Position position) {
		int count = 0;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					count++;
					if(count > 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public boolean hasMultipleShiftsWithoutContiguous(Position position) {
		int count = 0;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId()) && !shift.isReferencedShift()) {
					count++;
					if(count > 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Double getTotalShiftHours(Position position) {
		double total = 0.0;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					total += shift.getTotalShiftHours().doubleValue();
				}
			}
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
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId()) && !shift.isReferencedShift() && !shift.isBreak()) {
					total += shift.getTotalShiftHoursWithContiguous().doubleValue();
				}
			}
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHoursWithContiguous() {
		double total = 0.0;
		for(Shift shift : getShifts()) {
			if(shift != null && !shift.isBreak() && !shift.isReferencedShift()) {
				total += shift.getTotalShiftHoursWithContiguous().doubleValue();
			}
		}
		return new Double(total);		
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalShiftHours() {
		double total = 0.0;
		for(Shift shift : getShifts()) {
			if(shift != null && !shift.isBreak()) {
				total += shift.getTotalShiftHours().doubleValue();
			}
		}
		return new Double(total);		
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Date getFromHour(Position position) {
		Date inTime = null;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId()) && !shift.isReferencedShift()) {
					inTime = shift.getFromHour();
					break;
					/*
					if(inTime == null || CalendarUtils.greaterTime(inTime, shift.getFromHour())) {
						inTime = shift.getFromHour();
					}	
					*/				
				}
			}
		}
		return inTime;		
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getFromHour() {
		Date inTime = null;
		for(Shift shift : getShifts()) {
			if(shift != null && !shift.isReferencedShift()) {
				inTime = shift.getFromHour();
				break;
				/*
				if(inTime == null || CalendarUtils.greaterTime(inTime, shift.getFromHour())) {
					inTime = shift.getFromHour();
				}	
				*/				
			}
		}
		return inTime;			
	}

	/**
	 * 
	 * @return
	 */
	public Date getToHourWithContiguous() {
		Shift shift = getShifts() != null && getShifts().size() > 0 ? getShifts().get(getShifts().size() - 1) : null;
		if(shift != null) {
			return shift.hasContiguousShift() ? shift.getContiguousShift().getToHour() : shift.getToHour();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Date getToHour() {
		return getShifts() != null && getShifts().size() > 0 ? getShifts().get(getShifts().size() - 1).getToHour() : null;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public Date getToHourWithContiguous(Position position) {
		Date outTime = null;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					//if(outTime == null || CalendarUtils.smallerTime(outTime, shift.getToHour())) {
					// The out time of the last shift
					outTime = shift.hasContiguousShift() ? shift.getContiguousShift().getToHour() : shift.getToHour();
					//}					
				}
			}
		}
		return outTime;				
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Date getToHour(Position position) {
		Date outTime = null;
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					//if(outTime == null || CalendarUtils.smallerTime(outTime, shift.getToHour())) {
					// The out time of the last shift
					outTime = shift.getToHour();
					//}					
				}
			}
		}
		return outTime;				
	}
	
	/**
	 * 
	 * @param shift
	 */
	public void addShift(Shift shift) {
		if(shift != null) {
			shift.setEmployeeSchedule(this);
			shift.setShiftIndex(new Integer(getShifts().size()));
			getShifts().add(shift);
		}
	}
	
	/**
	 * 
	 * @param shift
	 */
	public void addFirstShift(Shift shift) {
		if(shift != null) {
			List<Shift> newShifts = new ArrayList<Shift>();
			shift.setEmployeeSchedule(this);
			shift.setShiftIndex(new Integer(0));
			
			newShifts.add(shift);
			
			int index = 1;
			for(Shift aShift : getShifts()) {
				aShift.setShiftIndex(new Integer(index));
				newShifts.add(aShift);
				index++;
			}
			
			setShifts(newShifts);
		}
	}
	
	/**
	 * @param shifts the shifts to set
	 */
	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	/**
	 * @return the storeSchedule
	 */
	public StoreSchedule getStoreSchedule() {
		return storeSchedule;
	}

	/**
	 * @param storeSchedule the storeSchedule to set
	 */
	public void setStoreSchedule(StoreSchedule storeSchedule) {
		this.storeSchedule = storeSchedule;
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
		
		final EmployeeSchedule other = (EmployeeSchedule) obj;
		
		return new EqualsBuilder()
		.append(this.employee != null? this.employee.getId():null, other.employee != null? other.employee.getId():null)
		.append(this.storeSchedule != null? this.storeSchedule.getId():null, other.storeSchedule != null? other.storeSchedule.getId():null)
		.isEquals();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.employee != null? this.employee.getId():null)
		.append(this.storeSchedule != null? this.storeSchedule.getId():null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("employee" , employee)
	   	.append("storeSchedule", storeSchedule)
	   	.toString();
	}

	/**
	 * 
	 * @return
	 */
	public Set<Position> getSchedulePositions() {
		Set<Position> positions = new HashSet<Position>();
		
		for(Shift shift : getShifts()) {
			if(shift != null && shift.getPosition() != null) {
				positions.add(shift.getPosition());
			}
		}
		
		return positions;		
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Shift> getBreakShifts() {
		List<Shift> shifts = new ArrayList<Shift>();
		for(Shift shift : getShifts()) {
			if(shift != null && shift.isBreak()) {
				shifts.add(shift);
			}
		}
		
		return shifts;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Shift> getShiftsFor(Position position) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					shifts.add(shift);
				}
			}
		}
		return shifts;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Shift> getUnreferencedShiftsFor(Position position) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && !shift.isReferencedShift() && shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())) {
					shifts.add(shift);
				}
			}
		}
		return shifts;
	}
	
	/**
	 * Complete shifts are shifts for the same position that only have breaks inside. So for example:
	 * If shifts are:
	 * shifts[0] => 08am to 10am -> Position 1
	 * shifts[1] => 10am to 11am -> Break
	 * shifts[2] => 11am to 01pm -> Position 1
	 * 
	 * Then this method will return just one Shift:
	 * unreferencedCompleteShifts[0] => 08am to 01pm -> Position 1
	 * @param position
	 * @return
	 */
	public List<CompleteShift> getUnreferencedCompleteShiftsFor(Position position) {
		List<CompleteShift> shifts = new ArrayList<CompleteShift>();
		if(position != null && position.getId() != null) {
			CompleteShift completeShift = new CompleteShift();
			completeShift.setPosition(position);
			completeShift.setEmployee(getEmployee());
			
			for(Shift shift : getShifts()) {
				if(shift != null && !shift.isReferencedShift() && (shift.isBreak() || (shift.getPosition() != null && position.getId().equals(shift.getPosition().getId())))) {
					if(!completeShift.addShift(shift)) {
						shifts.add(completeShift);

						completeShift = new CompleteShift();
						completeShift.setPosition(position);
						completeShift.setEmployee(getEmployee());
						completeShift.addShift(shift);
					}
				}
			}
			if(completeShift.getShifts().size() > 0) {
				shifts.add(completeShift);
			}
		}
		return shifts;
	}
	
	/**
	 * 
	 * @param shift
	 * @return
	 */
	public Shift getShift(Shift shift) {
		if(shift != null) {
			for(Shift aShift : getShifts()) {
				if(shift != null && aShift != null && aShift.getId() != null && aShift.getId().equals(shift.getId())) {
					return aShift;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public Shift getLastShiftFor(Position position) {
		List<Shift> shifts = getShiftsFor(position);
		return shifts != null && shifts.size() > 0 ? shifts.get(shifts.size() - 1) : null;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public Shift getFirstShiftFor(Position position) {
		List<Shift> shifts = getShiftsFor(position);
		return shifts != null && shifts.size() > 0 ? shifts.get(0) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Shift> getReferencedShifts() {
		List<Shift> shifts = new ArrayList<Shift>();
		for(Shift shift : getShifts()) {
			if(shift != null && shift.isReferencedShift()) {
				shifts.add(shift);
			}
		}
		return shifts;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Shift> getReferencedShifts(Position position) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.isReferencedShift() && position.getId().equals(shift.getPosition().getId())) {
					shifts.add(shift);
				}
			}
		}
		return shifts;		
	}
	
	/**
	 * 
	 * @param positions
	 * @return
	 */
	public List<Shift> getReferencedShifts(List<Position> positions) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(positions != null) {
			for(Position aPosition : positions) {
				shifts.addAll(getReferencedShifts(aPosition));
			}
		}
		return shifts;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Shift> getShiftsWithContiguous() {
		List<Shift> shifts = new ArrayList<Shift>();
		for(Shift shift : getShifts()) {
			if(shift != null && shift.hasContiguousShift()) {
				shifts.add(shift);
			}
		}
		return shifts;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public List<Shift> getShiftsWithContiguous(Position position) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(position != null && position.getId() != null) {
			for(Shift shift : getShifts()) {
				if(shift != null && shift.hasContiguousShift() && position.getId().equals(shift.getPosition().getId())) {
					shifts.add(shift);
				}
			}
		}
		return shifts;		
	}
	
	/**
	 * 
	 * @param positions
	 * @return
	 */
	public List<Shift> getShiftsWithContiguous(List<Position> positions) {
		List<Shift> shifts = new ArrayList<Shift>();
		if(positions != null) {
			for(Position aPosition : positions) {
				shifts.addAll(getShiftsWithContiguous(aPosition));
			}
		}
		return shifts;
	}
	
	public void removeLastShift() {
		int size = getShifts().size();
		if(size > 0) {
			getShifts().remove(size - 1);
		}
	}
}
