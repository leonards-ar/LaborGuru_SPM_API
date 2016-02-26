/*
 * File name: AddShiftByWeekBaseAction.java
 * Creation date: 08/12/2008 19:02:03
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.WeeklyScheduleDailyEntry;
import com.laborguru.frontend.model.WeeklyScheduleData;
import com.laborguru.frontend.model.WeeklyScheduleRow;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.Employee;
import com.laborguru.model.EmployeeSchedule;
import com.laborguru.model.Position;
import com.laborguru.model.Shift;
import com.laborguru.model.StoreDailyStaffing;
import com.laborguru.model.StoreSchedule;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class AddShiftByWeekBaseAction extends AddShiftBaseAction implements Preparable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AddShiftByWeekBaseAction.class);
	
	private WeeklyScheduleData weeklyScheduleData = null;
	
	private List<StoreSchedule> storeSchedules = null;
	private StoreSchedule firstDayNextWeekStoreSchedule = null;
	private List<Date> weekDays = null;
	private List<StoreDailyStaffing> dailyStaffings = null;
	
	private Integer newEmployeeId;
	private String newEmployeeName;
	private Integer newEmployeePositionId;
	
	private BigDecimal weeklyVolume;
	
	private Map<Integer, Double> positionWeeklyTarget;
	
	/**
	 * 
	 */
	public AddShiftByWeekBaseAction() {
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
					} else {
						getScheduleService().evict(aSchedule);
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
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#onWeekdaySelectorChange()
	 */
	@Override
	protected void onWeekdaySelectorChange() {
	}
	
	/**
	 * 
	 * @param positions
	 */
	protected void buildScheduleDataFor(List<Position> positions) {
		int size = getStoreSchedules().size();
		StoreSchedule aSchedule;
		WeeklyScheduleRow aRow;
		double hiddenTotalScheduled = 0.0D;
		
		for(int i = 0; i < size; i++) {
			aSchedule = getStoreSchedules().get(i);
			for(EmployeeSchedule employeeSchedule : aSchedule.getEmployeeSchedules()) {
				if(employeeSchedule != null) {
					for(Shift shift : employeeSchedule.getShifts()) {
						if(shift != null && !shift.isBreak() && (isAllPositions() || isPositionInList(positions, shift.getPosition())) && !shift.isReferencedShift()) {
							aRow = getRowFor(employeeSchedule, shift);
							if(aRow == null) {
								aRow = buildRowFor(employeeSchedule, shift);
								getWeeklyScheduleData().addScheduleRow(getGroupById(employeeSchedule.getEmployee(), shift), aRow);
							}
							buildScheduleDataFor(aRow, employeeSchedule, shift, i);
						} else if(shift != null && !shift.isBreak() && !isAllPositions()) {
							// Total shift hours that are not shown, but should be taken into account
							// for totals
							hiddenTotalScheduled += NumberUtils.getDoubleValue(shift.getTotalShiftHours());
						}
					}
				}
			}			
		}
		setHiddenTotalScheduled(hiddenTotalScheduled);
	}
	
	/**
	 * 
	 * @param row
	 * @param employeeSchedule
	 * @param shift
	 * @param dayIndex
	 */
	private void buildScheduleDataFor(WeeklyScheduleRow row, EmployeeSchedule employeeSchedule, Shift shift, int dayIndex) {
		WeeklyScheduleDailyEntry entry = getDailyEntry(row.getWeeklySchedule(), dayIndex);
		if(!entry.isMultipleShifts()) {
			entry.setMultipleShifts(employeeSchedule.hasMultipleShiftsWithoutContiguous(shift.getPosition()));
			
		}
		
		entry.setTotalHours(employeeSchedule.getTotalShiftHoursWithContiguous(shift.getPosition()));
		
		if(entry.getInHour() == null) {
			entry.setInHour(employeeSchedule.getFromHour(shift.getPosition()));
		}

		if(entry.getOutHour() == null) {
			entry.setOutHour(employeeSchedule.getToHourWithContiguous(shift.getPosition()));
		}

		entry.addShiftHours(shift.getFromHour(), shift.getToHourWithContiguousShift());
	}
	
	/**
	 * 
	 * @param entries
	 * @param dayIndex
	 * @return
	 */
	private WeeklyScheduleDailyEntry getDailyEntry(List<WeeklyScheduleDailyEntry> entries, int dayIndex) {
		if(entries.size() > dayIndex && dayIndex >= 0) {
			return entries.get(dayIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param employeeSchedule
	 * @param shift
	 * @return
	 */
	private WeeklyScheduleRow buildRowFor(EmployeeSchedule employeeSchedule, Shift shift) {
		WeeklyScheduleRow row = new WeeklyScheduleRow();
		Employee employee = employeeSchedule.getEmployee();
		Position position = shift.getPosition();
		if(employee != null && position != null) {
			row.setEmployeeId(employee.getId());
			row.setEmployeeMaxDaysWeek(employee.getMaxDaysWeek());
			row.setEmployeeMaxHoursDay(employee.getMaxHoursDay());
			row.setEmployeeMaxHoursWeek(employee.getMaxHoursWeek());
			row.setEmployeeWage(employee.getWage());
			row.setEmployeeName(employee.getFullName());
			row.setPositionId(position.getId());
			row.setPositionName(position.getName());
			row.setPositionIndex(position.getPositionIndex());
			row.setOrderByEmployee(isOrderByEmployee());
			row.setOriginalEmployeeId(employee.getId());
			row.setGroupById(getGroupById(employeeSchedule.getEmployee(), shift));
			
			row.setWeeklySchedule(initializeWeeklySchedule());
		}
		return row;
	}
	
	/**
	 * 
	 * @return
	 */
	protected List<WeeklyScheduleDailyEntry> initializeWeeklySchedule() {
		List<WeeklyScheduleDailyEntry> weekData = new ArrayList<WeeklyScheduleDailyEntry>();
		WeeklyScheduleDailyEntry entry;
		for(Date d : getWeekDays()) {
			entry = new WeeklyScheduleDailyEntry();
			entry.setDay(d);
			weekData.add(entry);
		}
		return weekData;
	}	
	
	/**
	 * 
	 * @param employeeSchedule
	 * @param shift
	 * @return
	 */
	private WeeklyScheduleRow getRowFor(EmployeeSchedule employeeSchedule, Shift shift) {
		List<WeeklyScheduleRow> dataRows = getWeeklyScheduleData().getScheduleDataFor(getGroupById(employeeSchedule.getEmployee(), shift));
		if(dataRows != null) {
			for(WeeklyScheduleRow aRow : dataRows) {
				if(isEqualId(aRow.getEmployeeId(), employeeSchedule.getEmployee().getId()) && isEqualId(aRow.getPositionId(), shift.getPosition().getId())) {
					return aRow;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param employee
	 * @param shift
	 * @return
	 */
	protected abstract Integer getGroupById(Employee employee, Shift shift);

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
	 * @return the scheduleData
	 */
	public WeeklyScheduleData getWeeklyScheduleData() {
		if(weeklyScheduleData == null) {
			setWeeklyScheduleData(new WeeklyScheduleData());
		}
		return weeklyScheduleData;
	}

	/**
	 * @param weeklyScheduleData the scheduleData to set
	 */
	public void setWeeklyScheduleData(WeeklyScheduleData weeklyScheduleData) {
		this.weeklyScheduleData = weeklyScheduleData;
	}
	
	/**
	 * 
	 */
	protected void setScheduleData() {
		if(getWeeklyScheduleData().isEmpty()) {
			buildScheduleDataFor(getSelectedPositions());
		}
	}

	/**
	 * 
	 */
	protected void resetScheduleData() {
		setWeeklyScheduleData(null);
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
	 * 
	 * @param schedule
	 * @return
	 */
	private Set<Integer> getDifferentEmployeeIds() {
		Set<Integer> ids = new HashSet<Integer>();
		
		for(WeeklyScheduleRow aRow : getWeeklyScheduleData().getScheduleData()) {
			if(aRow.getEmployeeId() != null && !ids.contains(aRow.getEmployeeId())) {
				ids.add(aRow.getEmployeeId());
			}
		}
		
		return ids;
	}
	
	/**
	 * 
	 * @param shiftsFromPreviousDay
	 * @param storeSchedule
	 */
	private void processShiftsFromPreviousDay(List<Shift> shiftsFromPreviousDay, StoreSchedule storeSchedule) {
		if (storeSchedule != null && shiftsFromPreviousDay != null && shiftsFromPreviousDay.size() > 0) {
			EmployeeSchedule schedule;
			for (Shift shift : shiftsFromPreviousDay) {
				schedule = storeSchedule.getEmployeeSchedule(shift.getEmployeeSchedule().getEmployee());
				if (schedule == null) {
					schedule = new EmployeeSchedule();
					schedule.setEmployee(getEmployeeService().getEmployeeById(shift.getEmployeeSchedule().getEmployee()));
					schedule.setStoreSchedule(storeSchedule);
					storeSchedule.getEmployeeSchedules().add(schedule);
				}
				shift.setEmployeeSchedule(schedule);
				schedule.addFirstShift(shift);
			}
		}
	}
	
	/**
	 * 
	 * @param schedule
	 * @param position
	 * @param day
	 */
	protected void setSchedule() {
		int size = getStoreSchedules().size();
		StoreSchedule storeSchedule;
		List<Shift> shiftsForNextDay = null;
		List<Shift> shiftsFromPreviousDay = null;
		
		for(int dayIndex = 0; dayIndex < size; dayIndex++ ) {
			if(isEditable(dayIndex)) {
				storeSchedule = getStoreSchedules().get(dayIndex);
				
				shiftsFromPreviousDay = shiftsForNextDay;
				shiftsForNextDay = new ArrayList<Shift>();
				
				Set<Integer> employeeIds = getDifferentEmployeeIds();
				Employee employee;
				for(Integer employeeId : employeeIds) {
					employee = getEmployeeService().getEmployeeById(new Employee(employeeId));
					EmployeeSchedule employeeSchedule = storeSchedule.getEmployeeSchedule(employee);
					if(employeeSchedule == null) {
						employeeSchedule = new EmployeeSchedule();
						employeeSchedule.setEmployee(employee);
						employeeSchedule.setStoreSchedule(storeSchedule);
						storeSchedule.getEmployeeSchedules().add(employeeSchedule);
					}
					setShifts(getEmployeeSchedule(employeeId), employeeSchedule, dayIndex, shiftsForNextDay);
				}

				// Remove non existing shifts for the employee and for a position
				cleanStoreSchedule(employeeIds, storeSchedule, dayIndex, shiftsFromPreviousDay != null && !shiftsFromPreviousDay.isEmpty());
				
				processShiftsFromPreviousDay(shiftsFromPreviousDay, storeSchedule);
			}
		}
		
		if(isEditable(size) && shiftsForNextDay != null && shiftsForNextDay.size() > 0) {
			processShiftsFromPreviousDay(shiftsForNextDay, getFirstDayNextWeekStoreSchedule());
		}
	}

	/**
	 * 
	 * @param employeeIds
	 * @param employeeSchedule
	 * @param dayIndex
	 */
	private void cleanStoreSchedule(Set<Integer> employeeIds, StoreSchedule storeSchedule, int dayIndex, boolean shiftsFromPreviousDay) {
		if(storeSchedule != null && employeeIds != null) {
			Set<EmployeeSchedule> employeeSchedulesToRemove = new HashSet<EmployeeSchedule>();
			Set<Shift> shiftsToRemove = new HashSet<Shift>();
			
			for(EmployeeSchedule employeeSchedule : storeSchedule.getEmployeeSchedules()) {
				if(employeeSchedule.getEmployee() != null && !employeeIds.contains(employeeSchedule.getEmployee().getId())) {
					if(isAllPositions() && !isByPositionView()) {
						// Applies for all positions
						employeeSchedulesToRemove.add(employeeSchedule);
					} else {
						for(Shift shift : employeeSchedule.getShifts()) {
							if(shift != null && isPositionInList(getSelectedPositions(), shift.getPosition())) {
								shiftsToRemove.add(shift);
							}
						}
						employeeSchedule.removeShifts(shiftsToRemove);
						// There are no more shifts, then remove the employee schedule
						//:TODO: Should remove also all employee schedules with just break shifts???
						if(employeeSchedule.getShifts().isEmpty()) {
							employeeSchedulesToRemove.add(employeeSchedule);
						}
					}
				} else if(employeeSchedule.getShifts().isEmpty() && !shiftsFromPreviousDay) {
					employeeSchedulesToRemove.add(employeeSchedule);
				}
			}
			storeSchedule.getEmployeeSchedules().removeAll(employeeSchedulesToRemove);
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param dayIndex
	 * @param shiftsFromPreviousDay
	 */
	private void setShifts(List<WeeklyScheduleRow> source, EmployeeSchedule employeeSchedule, int dayIndex, List<Shift> shiftsFromPreviousDay) {
		if(isAllPositions() && !isByPositionView()) {
			setShiftsAllPositions(source, employeeSchedule, dayIndex, shiftsFromPreviousDay);
		} else {
			setShiftsForPositions(source, employeeSchedule, getSelectedPositions(), dayIndex, shiftsFromPreviousDay);
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param dayIndex
	 * @return
	 */
	private Shift retrieveShift(WeeklyScheduleRow row, int dayIndex) {
		if(row != null) {
			WeeklyScheduleDailyEntry entry = row.getWeeklySchedule().get(dayIndex);
			if(entry.isShift()) {
				Shift shift = new Shift();
				shift.setFromHour(entry.getInHour());
				shift.setToHour(entry.getOutHour());
				if(row.getPositionId() != null) {
					Position pos = new Position();
					pos.setId(row.getPositionId());
					shift.setPosition(getPositionService().getPositionById(pos));
				}
				
				return shift;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param position
	 * @param shift
	 * @param employeeSchedule
	 * @return
	 */
	private boolean changeShiftForPosition(Shift shift, EmployeeSchedule employeeSchedule) {
		if(shift != null && shift.getPosition() != null) {
			Date in = employeeSchedule.getFromHour(shift.getPosition());
			Date out = employeeSchedule.getToHourWithContiguous(shift.getPosition());
			return !CalendarUtils.equalsTime(in, shift.getFromHour()) || !CalendarUtils.equalsTime(out, shift.getToHourWithContiguousShift());
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param dayIndex
	 * @return
	 */
	private List<Shift> retrieveShiftsToKeep(List<WeeklyScheduleRow> source, EmployeeSchedule employeeSchedule, int dayIndex) {
		List<Shift> shiftsToKeep = new ArrayList<Shift>();

		for(Shift shift : employeeSchedule.getShifts()) {
			if(shift != null) {
				if(shift.isBreak() || shift.isReferencedShiftToKeep()) {
					processContiguousShiftToKeep(shift, employeeSchedule, dayIndex);
					shiftsToKeep.add(shift);
				} else if(dayIndex == 0 && shift.isReferencedShift()) {
					// It's a referenced shift from the previous week
					shiftsToKeep.add(shift);
				} else {
					// Check to see if from and to times change
					Shift rowShift = retrieveShift(getWeeklyScheduleRowForPosition(source, shift.getPosition()), dayIndex);
					if(rowShift != null && !changeShiftForPosition(rowShift, employeeSchedule)) {
						processContiguousShiftToKeep(shift, employeeSchedule, dayIndex);
						shiftsToKeep.add(shift);
					}
				}
			}
		}
		
		return shiftsToKeep;
	}

	/**
	 * 
	 * @param shiftToKeep
	 * @param employeeSchedule
	 * @param dayIndex
	 */
	private void processContiguousShiftToKeep(Shift shiftToKeep, EmployeeSchedule employeeSchedule, int dayIndex) {
		if(shiftToKeep.hasContiguousShift()) {
			EmployeeSchedule nextDayEmployeeSchedule = getNextDayEmployeeSchedule(dayIndex, employeeSchedule);
			if(nextDayEmployeeSchedule != null) {
				Shift contiguousShiftToKeep = nextDayEmployeeSchedule.getShift(shiftToKeep.getContiguousShift());
				if(contiguousShiftToKeep != null) {
					contiguousShiftToKeep.setReferencedShiftToKeep(true);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param position
	 * @return
	 */
	private WeeklyScheduleRow getWeeklyScheduleRowForPosition(List<WeeklyScheduleRow> source, Position position) {
		for(WeeklyScheduleRow row : source) {
			if(isEqualId(position.getId(), row.getPositionId())) {
				return row;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param shiftsToKeep
	 * @param shift
	 * @return
	 */
	private boolean isShiftToKeep(List<Shift> shiftsToKeep, Shift shift) {
		if(shift != null && shiftsToKeep != null && shiftsToKeep.size() > 0) {
			for(Shift aShift : shiftsToKeep) {
				if(aShift != null && isEqualId(shift.getId(), aShift.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param dayIndex
	 * @param employeeSchedule
	 * @return
	 */
	private EmployeeSchedule getNextDayEmployeeSchedule(int dayIndex, EmployeeSchedule employeeSchedule) {
		StoreSchedule nextDayStoreSchedule = dayIndex + 1 < getStoreSchedules().size() ? getStoreSchedules().get(dayIndex + 1) : getFirstDayNextWeekStoreSchedule();
		if(nextDayStoreSchedule != null) {
			return nextDayStoreSchedule.getEmployeeSchedule(employeeSchedule.getEmployee());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param shift
	 * @param dayIndex
	 * @param employeeSchedule
	 * @return
	 */
	private Shift getCandidateContiguousShift(Shift shift, int dayIndex, EmployeeSchedule employeeSchedule) {
		EmployeeSchedule nextDayEmployeeSchedule = getNextDayEmployeeSchedule(dayIndex, employeeSchedule);
		if(nextDayEmployeeSchedule != null) {
			for(Shift candidate : nextDayEmployeeSchedule.getReferencedShifts(shift.getPosition())) {
				if(!candidate.isReferencedShiftToKeep()) {
					return candidate;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param shift
	 * @param dayIndex
	 * @param employeeSchedule
	 * @return
	 */
	private Shift breakAndUpdateContiguousShift(Shift shift, int dayIndex, EmployeeSchedule employeeSchedule) {
		Date selectedDayEndTime = getStoreScheduleEndHour(getDay(dayIndex));
		Date selectedDayNextDayStartTime = getStoreScheduleStartHour(getDay(dayIndex + 1));
		Shift contiguousShift = null;
		if(breakIntoContiguousShift(shift, selectedDayEndTime, selectedDayNextDayStartTime)) {
			contiguousShift = getCandidateContiguousShift(shift, dayIndex, employeeSchedule);
			if(contiguousShift == null) {
				contiguousShift = new Shift();
				// Just for the employee! Should be replaced when assigned to the
				// next day schedule
				contiguousShift.setEmployeeSchedule(employeeSchedule);			
			}
			contiguousShift.setContiguousShift(null);
			contiguousShift.setPosition(shift.getPosition());
			contiguousShift.setToHour(shift.getToHour());
			contiguousShift.setFromHour(selectedDayNextDayStartTime);
			contiguousShift.setReferencedShiftToKeep(true);
			
			shift.setToHour(selectedDayEndTime);
		}
		shift.setContiguousShift(contiguousShift);
		return contiguousShift;
	}
	
	/**
	 * 
	 * @param shift
	 * @param selectedDayEndTime
	 * @param selectedDayNextDayStartTime
	 * @return
	 */
	private boolean breakIntoContiguousShift(Shift shift, Date selectedDayEndTime, Date selectedDayNextDayStartTime) {
		return CalendarUtils.equalsTime(selectedDayEndTime, selectedDayNextDayStartTime) &&
			   (
			   // Case 1: 24hs shift and limit between them
			   (CalendarUtils.equalsTime(shift.getFromHour(), shift.getToHour()) && !CalendarUtils.equalsTime(shift.getFromHour(), selectedDayEndTime)) ||
			   // Case 2: To Hour is greater than From Hour and limit is between them
			   (CalendarUtils.greaterTime(shift.getToHour(), shift.getFromHour()) && CalendarUtils.inRangeNotIncludingEndTime(selectedDayEndTime, shift.getFromHour(), shift.getToHour())) ||
			   // Case 3: From Hours is greater than To Hour and limit is between them
			   (CalendarUtils.greaterTime(shift.getFromHour(), shift.getToHour()) && CalendarUtils.inRangeNotIncludingEndTime(selectedDayEndTime, shift.getFromHour(), shift.getToHour()))
			   );
	}
	
	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param dayIndex
	 * @param shiftsForNextDay
	 */
	private void setShiftsAllPositions(List<WeeklyScheduleRow> source, EmployeeSchedule employeeSchedule, int dayIndex, List<Shift> shiftsForNextDay) {
		int shiftPosition = 0;
		Shift rowShift;
		employeeSchedule.reindexShifts();
		List<Shift> currentShifts = employeeSchedule.getShifts();
		int currentShiftsSize = currentShifts.size();
		List<Shift> shiftsToKeep = retrieveShiftsToKeep(source, employeeSchedule, dayIndex);
		Shift contiguousShift = null;
		
		for(WeeklyScheduleRow row : source) {
			rowShift = retrieveShift(row, dayIndex);
			if(rowShift != null && changeShiftForPosition(rowShift, employeeSchedule)) {
				// Do not replace breaks
				while(shiftPosition < currentShifts.size() && isShiftToKeep(shiftsToKeep, currentShifts.get(shiftPosition))) {
					shiftPosition++;
				}
				contiguousShift = breakAndUpdateContiguousShift(rowShift, dayIndex, employeeSchedule);
				if(contiguousShift != null && contiguousShift.getId() == null) {
					shiftsForNextDay.add(contiguousShift);
				}
				
				if(shiftPosition < currentShiftsSize) {
					updateShift(rowShift, currentShifts.get(shiftPosition));
					shiftPosition++;
				} else {
					rowShift.setEmployeeSchedule(employeeSchedule);
					employeeSchedule.addShift(rowShift);
				}			
			}
		}
		
		// Remove non-existing shifts
		if(shiftPosition < currentShiftsSize) {
			for(int i = currentShiftsSize - 1; i >= shiftPosition; i--) {
				if(!isShiftToKeep(shiftsToKeep, currentShifts.get(i))) {
					currentShifts.remove(i);
				}
			}
		}
	}

	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param positions
	 * @param dayIndex
	 * @param shiftsForNextDay
	 */
	private void setShiftsForPositions(List<WeeklyScheduleRow> source, EmployeeSchedule employeeSchedule, List<Position> positions, int dayIndex, List<Shift> shiftsForNextDay) {
		int shiftPosition = 0;
		Shift rowShift;
		employeeSchedule.reindexShifts();
		List<Shift> currentShifts = employeeSchedule.getShifts();
		int currentShiftsSize = currentShifts.size();
		List<Shift> shiftsToKeep = retrieveShiftsToKeep(source, employeeSchedule, dayIndex);
		Shift contiguousShift = null;
	
		for(WeeklyScheduleRow row : source) {
			rowShift = retrieveShift(row, dayIndex);
			if(rowShift != null && isPositionInList(positions, rowShift.getPosition()) && changeShiftForPosition(rowShift, employeeSchedule)) {
				while(shiftPosition < currentShifts.size() && (isShiftToKeep(shiftsToKeep, currentShifts.get(shiftPosition)) || !isPositionInList(positions, currentShifts.get(shiftPosition).getPosition()))) {
					shiftPosition++;
				}
				
				contiguousShift = breakAndUpdateContiguousShift(rowShift, dayIndex, employeeSchedule);
				if(contiguousShift != null && contiguousShift.getId() == null) {
					shiftsForNextDay.add(contiguousShift);
				}
				
				if(shiftPosition < currentShiftsSize) {
					updateShift(rowShift, currentShifts.get(shiftPosition));
					shiftPosition++;
				} else {
					rowShift.setEmployeeSchedule(employeeSchedule);
					employeeSchedule.addShift(rowShift);
				}
			}
		}
		
		// Remove non-existing shifts
		if(shiftPosition < currentShiftsSize) {
			for(int i = currentShiftsSize - 1; i >= shiftPosition; i--) {
				if(isPositionInList(positions, currentShifts.get(i).getPosition()) && !isShiftToKeep(shiftsToKeep, currentShifts.get(i))) {
					currentShifts.remove(i);
				}
			}
		}	
	}
	
	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareEdit() {
		loadPageData();
	}
	
	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#prepareChangeDay()
	 */
	@Override
	public void prepareChangeDay() {
		/*
		 * Change day does not apply for weekly view
		 * as changeDay is executed directly on daily
		 * actions
		 */ 
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
		/*
		 * Change day does not apply for weekly view
		 * as changeDay is executed directly on daily
		 * actions
		 */ 
	}

	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#processChangeWeek()
	 */
	@Override
	protected void processChangeWeek() {
		setStoreSchedules(null);
		setFirstDayNextWeekStoreSchedule(null);
		resetScheduleData();
		resetWeekData();
		//resetStaffingData();
		setScheduleData();
		loadCopyTargetDay();		
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
	
	protected void loadCopyTargetDay() {
		setCopyTargetDay(CalendarUtils.addOrSubstractDays(getWeekDaySelector().getStartingWeekDay(), DayOfWeek.values().length));
	}
	
	protected void initializeCopyTargetDay() {
		if(getCopyTargetDay() == null || !CalendarUtils.isAfterToday(getCopyTargetDay())) {
			loadCopyTargetDay();
		}
	}
	
	/**
	 * 
	 */
	private void loadPageData() {
		loadPositions();
		loadEmployees();
	}

	/**
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
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

		WeeklyScheduleRow newRow = new WeeklyScheduleRow();
		
		newRow.setEmployeeId(getNewEmployeeId());
		newRow.setOriginalEmployeeId(getNewEmployeeId());
		newRow.setPositionId(getNewEmployeePositionId());
		Position newPosition = getPosition(getNewEmployeePositionId());
		newRow.setPositionName(newPosition != null ? newPosition.getName() : null);
		newRow.setPositionIndex(newPosition != null ? newPosition.getPositionIndex() : null);
		newRow.setEmployeeName(getNewEmployeeName());
		newRow.setGroupById(getAddEmployeeGroupById());
		newRow.setWeeklySchedule(initializeWeeklySchedule());
		newRow.setOrderByEmployee(isOrderByEmployee());

		if(newEmployee != null) {
			newRow.setEmployeeMaxDaysWeek(newEmployee.getMaxDaysWeek());
			newRow.setEmployeeMaxHoursDay(newEmployee.getMaxHoursDay());
			newRow.setEmployeeMaxHoursWeek(newEmployee.getMaxHoursWeek());		
			newRow.setEmployeeWage(newEmployee.getWage());
		}
		
		getWeeklyScheduleData().addScheduleRow(getAddEmployeeGroupById(), newRow);

		setNewEmployeeId(null);
		setNewEmployeeName(null);
		setNewEmployeePositionId(null);
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * 
	 * @return
	 */
	protected abstract Integer getAddEmployeeGroupById();
	
	/**
	 * 
	 * @return
	 */
	protected abstract boolean isOrderByEmployee();
	
	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareSave() {
		loadPageData();
	}
	
	/**
	 * 
	 * @return
	 */
	public String save() {
		Map<Integer, RuntimeException> errors = new HashMap<Integer, RuntimeException>();
		
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		// Build schedule for correct days
		setSchedule();

		int size = getStoreSchedules().size();
		StoreSchedule storeSchedule = null;
		
		// Inverse so that contiguous shifts are saved first!
		if(isFirstDayNextWeekStoreSchedule()) {
			try {
				storeSchedule = getFirstDayNextWeekStoreSchedule();
				getScheduleService().save(storeSchedule);
			} catch(RuntimeException ex) {
				log.error("Failed to save first day of next week for store schedule " + storeSchedule, ex);
				errors.put(new Integer(size), ex);
			}
		}
		
		for(int dayIndex = size - 1; dayIndex >= 0; dayIndex--) {
			if(isEditable(dayIndex)) {
				storeSchedule = getStoreSchedules().get(dayIndex);

				if(log.isDebugEnabled()) {
					log.debug("About to save schedule " + storeSchedule);
				}

				try {
					getScheduleService().save(storeSchedule);
				} catch(RuntimeException ex) {
					log.error("Failed to save day " + storeSchedule.getDay() + " for store schedule " + storeSchedule, ex);
					errors.put(new Integer(dayIndex), ex);
				}				
				
				if(log.isDebugEnabled()) {
					log.debug("Saved schedule for date " + storeSchedule.getDay());
				}
			}
		}
		
		resetScheduleData();
		setScheduleData();
		resetWeekData();
		
		if (errors.size() == 0) {
			addActionMessage(getText("schedule.addshift.save_success"));
		} else {
			List<Object> params = new ArrayList<Object>();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;
			for (Map.Entry<Integer, RuntimeException> error : errors.entrySet()) {
				Date d = getDay(error.getKey() < size ? error.getKey() : size - 1);
				if (!isFirst) {
					sb.append(", ");
				}
				sb.append(df.format(d));
				isFirst = false;
			}
			params.add(sb.toString());
			
			addActionError(getText("error.schedule.addshift.save_error", params));
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	public boolean isEditable(int dayIndex) {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return true;
		} else {
			Date d = getDay(dayIndex);
			return d != null ? CalendarUtils.isAfterToday(d) : false;
		}
	}
	
	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	protected Date getDay(int dayIndex) {
		if(dayIndex < getWeekDays().size() && dayIndex >= 0) {
			return getWeekDays().get(dayIndex);
		} else if (dayIndex >= getWeekDays().size()) {
			return CalendarUtils.addOrSubstractDays(getWeekDays().get(getWeekDays().size() - 1), dayIndex - getWeekDays().size() + 1);
		} else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEditable() {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return true;
		} else {
			for(int i = 0; i < getWeekDays().size(); i++) {
				if(isEditable(i)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTotalScheduleRows() {
		return getWeeklyScheduleData().getScheduleData().size();
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	protected abstract List<WeeklyScheduleRow> getEmployeeSchedule(Integer employeeId);
	
	/**
	 * Prepare the data to be used on the edit page
	 */
	public void prepareCancel() {
		loadPageData();
	}
	
	/**
	 * 
	 * @return
	 */
	public String cancel() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		resetScheduleData();
		setScheduleData();
		resetWeekData();
		
		return SpmActionResult.EDIT.getResult();
	}	
	
	/**
	 * 
	 */
	public void prepareSelectPosition() {
		loadPageData();
	}

	/**
	 * @param storeSchedules the storeSchedules to set
	 */
	public void setStoreSchedules(List<StoreSchedule> storeSchedules) {
		this.storeSchedules = storeSchedules;
	}
	
	/**
	 * 
	 * @return
	 */
	public String selectPosition() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
	
		resetScheduleData();
		//resetStaffingData();
		resetWeekData();
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * @return the weeklyVolume
	 */
	public BigDecimal getWeeklyVolume() {
		if(weeklyVolume == null) {
			//:TODO: Retrieve the SUM in only one query
			List<DailyProjection> dailyProjections = getProjectionService().getDailyProjections(getEmployeeStore(), getWeekDays().get(0), getWeekDays().get(getWeekDays().size() - 1));
			if(dailyProjections != null) {
				BigDecimal total = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
				for(DailyProjection dp : dailyProjections) {
					total = total.add(dp.getDailyProjectionValue());
				}
				setWeeklyVolume(total);
			} else {
				setWeeklyVolume(SpmConstants.BD_ZERO_VALUE);
			}
		}
		return weeklyVolume;		
	}

	/**
	 * @param weeklyVolume the weeklyVolume to set
	 */
	public void setWeeklyVolume(BigDecimal weeklyVolume) {
		this.weeklyVolume = weeklyVolume;
	}	
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public String getTotalPositionTarget(Position position) {
		double total = 0.0;
		if(position != null && position.getId() != null) {
			total = NumberUtils.getDoubleValue(getPositionWeeklyTarget().get(position.getId()));
		}
		return CalendarUtils.hoursToTime(new Double(total));
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTotalTarget() {
		double total = 0.0;
		for(Double d : getPositionWeeklyTarget().values()) {
			total += NumberUtils.getDoubleValue(d);
		}
		return CalendarUtils.hoursToTime(new Double(total));
	}
	
	/**
	 * @return the dailyStaffings
	 */
	public List<StoreDailyStaffing> getDailyStaffings() {
		if(dailyStaffings == null) {
			dailyStaffings = new ArrayList<StoreDailyStaffing>();
			try {
				StoreDailyStaffing aDailyStaffing;
				//:TODO: Probably should retrieve the whole week from the database
				for(Date aDate : getWeekDays()) {
					aDailyStaffing = getStaffingService().getDailyStaffingByDate(getEmployeeStore(), aDate);
					dailyStaffings.add(aDailyStaffing);
				}
			} catch(RuntimeException ex) {
				log.error("Could not retrieve store daily staffing for week days " + getWeekDays() + " for store " + getEmployeeStore(), ex);
			}

		}
		return dailyStaffings;		
	}

	/**
	 * @param dailyStaffings the dailyStaffings to set
	 */
	public void setDailyStaffings(List<StoreDailyStaffing> dailyStaffings) {
		this.dailyStaffings = dailyStaffings;
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#loadScheduleViewsMap()
	 */
	@Override
	protected void loadScheduleViewsMap() {
		setScheduleViewsMap(getReferenceDataService().getWeeklyScheduleViews());
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
		//resetStaffingData();
		resetWeekData();
		setScheduleData();
		
		return SpmActionResult.EDIT.getResult();
	}	
	
	/**
	 * 
	 */
	public void prepareCopySchedule() {
		loadPageData();
	}
	
	/**
	 * 
	 * @return
	 */
	public String copySchedule() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

		if(log.isDebugEnabled()) {
			log.debug("About to copy schedule data " + getWeeklyScheduleData() + " to week starting " + getCopyTargetDay());
		}

		List<Object> params = new ArrayList<Object>();
		params.add(getCopyTargetDay());
		params.add(getWeekDaySelector().getStartingWeekDay());
		params.add(new Date());

		// Memorize current schedule
		setSchedule();

		// Only able to copy after today and after the last day of the current week
		if(getCopyTargetDay() != null && getCopyTargetDay().after(new Date()) && getCopyTargetDay().after(getWeekDays().get(getWeekDays().size() - 1))) {

			if(log.isDebugEnabled()) {
				log.debug("About to copy schedules starting in day [" + getCopyTargetDay() + "] -> " + getStoreSchedules());
			}
			
			int size = getStoreSchedules().size();
			StoreSchedule storeSchedule;

			StoreSchedule nextDaySchedule = getScheduleService().getStoreScheduleByDate(getEmployeeStore(), CalendarUtils.addOrSubstractDays(getCopyTargetDay(), size));

			for(int dayIndex = size - 1; dayIndex >= 0; dayIndex--) {
				storeSchedule = getScheduleService().copyScheduleTo(getStoreSchedules().get(dayIndex), CalendarUtils.addOrSubstractDays(getCopyTargetDay(), dayIndex), nextDaySchedule);
				
				if(log.isDebugEnabled()) {
					log.debug("About to save schedule " + storeSchedule);
				}
				
				getScheduleService().save(storeSchedule);
				
				if(log.isDebugEnabled()) {
					log.debug("Saved schedule for date " + storeSchedule.getDay());
				}
				nextDaySchedule = storeSchedule;
			}

			addActionMessage(getText("schedule.addshift.weekly.copy_success", params));
		} else {
			addActionError(getText("error.schedule.addshift.invalid_target_day", params));
		}
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * @param weekDays the weekDays to set
	 */
	public void setWeekDays(List<Date> weekDays) {
		this.weekDays = weekDays;
	}

	/**
	 * @return the firstDayNextWeekStoreSchedule
	 */
	public StoreSchedule getFirstDayNextWeekStoreSchedule() {
		if(firstDayNextWeekStoreSchedule == null) {
			Date day = CalendarUtils.addOrSubstractDays(getWeekDays().get(getWeekDays().size() - 1), 1);
			setFirstDayNextWeekStoreSchedule(getScheduleService().getStoreScheduleByDate(getEmployeeStore(), day));
		}
		return firstDayNextWeekStoreSchedule;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirstDayNextWeekStoreSchedule() {
		return firstDayNextWeekStoreSchedule != null;
	}
	
	/**
	 * @param firstDayNextWeekStoreSchedule the firstDayNextWeekStoreSchedule to set
	 */
	public void setFirstDayNextWeekStoreSchedule(StoreSchedule firstDayNextWeekStoreSchedule) {
		this.firstDayNextWeekStoreSchedule = firstDayNextWeekStoreSchedule;
	}
	
	/**
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		if(StringUtils.isNotBlank(getSaveSchedule()) || StringUtils.isNotBlank(getCopySchedule())) {
			initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

			Map<String, Integer> employeesPositions = new HashMap<String, Integer>();
			String key;
			
			int rowCount = 1;
			for(WeeklyScheduleRow row : getWeeklyScheduleData().getScheduleData()) {
				row.updateEntries();
				
				if(row.getEmployeeId() == null || row.getEmployeeId().intValue() <= 0) {
					addActionError(getText("error.schedule.addshift.employee_missing", new String[]{String.valueOf(rowCount)}));
				} else {
					// Validate there is no other employeeId - positionId combination
					key = row.getEmployeeId() + "_" + row.getPositionId();
					if(employeesPositions.containsKey(key)) {
						addActionError(getText("error.schedule.addshift.weekly_employee_position_exists", new String[]{String.valueOf(rowCount), String.valueOf(employeesPositions.get(key)), row.getEmployeeName(), row.getPositionName()}));
					} else {
						employeesPositions.put(key, new Integer(rowCount));
					}
				}
				
				rowCount++;
				for(int i = 0; i < row.getWeeklySchedule().size(); i++) {
					if(isEditable(i)) {
						validateWeeklyScheduleDailyEntry(row, row.getWeeklySchedule().get(i), i);
					}
				}
			}
			validateOverlappingShifts();
		}
	}	


	/**
	 * 
	 * @param row
	 * @param entry
	 * @param dayIndex
	 * @return
	 */
	private List<Object> buildValidationErrorParameters(WeeklyScheduleRow row, WeeklyScheduleDailyEntry entry, int dayIndex) {
		List<Object> params = new ArrayList<Object>();
		params.add(row.getEmployeeName());
		params.add(row.getPositionName());
		params.add(entry.getDay());
		return params;
	}
	
	/**
	 * 
	 * @param row
	 * @param entry
	 * @param dayIndex
	 */
	private void validateWeeklyScheduleDailyEntry(WeeklyScheduleRow row, WeeklyScheduleDailyEntry entry, int dayIndex) {
		if(entry.getInHour() == null && entry.getOutHour() != null) {
			addActionError(getText("error.schedule.addshift.weekly.no_in_hour", buildValidationErrorParameters(row, entry, dayIndex)));
		} else if(entry.getOutHour() == null && entry.getInHour() != null) {
			addActionError(getText("error.schedule.addshift.weekly.no_out_hour", buildValidationErrorParameters(row, entry, dayIndex)));
		} else if(entry.isShift()) {
			// Validate start and end time is inside the operation hours
			Date selectedDayStartTime = getStoreScheduleStartHour(getDay(dayIndex));
			Date selectedDayEndTime = getStoreScheduleEndHour(getDay(dayIndex));
			if(!CalendarUtils.equalsTime(selectedDayStartTime, selectedDayEndTime) && (!CalendarUtils.inRangeIncludingEndTime(entry.getInHour(), selectedDayStartTime, selectedDayEndTime) || !CalendarUtils.inRangeIncludingEndTime(entry.getOutHour(), selectedDayStartTime, selectedDayEndTime))) {
				addActionError(getText("error.schedule.addshift.weekly.out_of_range_hours", buildValidationErrorParameters(row, entry, dayIndex)));
			}
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param entry
	 * @param dayIndex
	 */
	private void validateOverlappingShifts() {
		 for(Integer employeeId : getDifferentEmployeeIds()) {
			 List<List<WeeklyScheduleDailyEntry>> weeklyScheduleToValidate = getEmployeeWeeklyScheduleToValidate(employeeId);
			 for(int dayIndex = 0; dayIndex < weeklyScheduleToValidate.size(); dayIndex++) {
				 List<WeeklyScheduleDailyEntry> dailySchedule = weeklyScheduleToValidate.get(dayIndex);
				 if(!dailySchedule.isEmpty()) {
					 validateEmployeeDailyOverlapingShifts(employeeId, dailySchedule, dayIndex);
				 }
			 }
		 }
	}

	/**
	 * 
	 * @param employeeId
	 * @param dailySchedule
	 */
	private void validateEmployeeDailyOverlapingShifts(Integer employeeId, List<WeeklyScheduleDailyEntry> dailySchedule, int dayIndex) {
		int size = dailySchedule != null ? dailySchedule.size() : 0;
		for(int i = 0; i < size; i++) {
			WeeklyScheduleDailyEntry entryToValidate = dailySchedule.get(i);
			if(entryToValidate.getRow() != null) {
				for(int j = i + 1; j < size; j++) {
					WeeklyScheduleDailyEntry entry = dailySchedule.get(j);
					if(CalendarUtils.isOverlappingTimePeriod(entryToValidate.getInHour(), entryToValidate.getOutHour(), entry.getInHour(), entry.getOutHour())) {
						addActionError(getText("error.schedule.addshift.weekly.employee_shift_collision", buildValidationErrorParameters(entryToValidate.getRow(), entryToValidate, dayIndex)));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	private List<List<WeeklyScheduleDailyEntry>> getEmployeeWeeklyScheduleToValidate(Integer employeeId) {
		int size = getWeekDays().size() + 1;
		List<List<WeeklyScheduleDailyEntry>> weeklySchedule = new ArrayList<List<WeeklyScheduleDailyEntry>>(size);

		// Initialize
		for(int i = 0; i < size; i++) {
			weeklySchedule.add(new ArrayList<WeeklyScheduleDailyEntry>());
		}
		
		List<WeeklyScheduleDailyEntry> employeeData;
		Iterator<WeeklyScheduleDailyEntry> splittedEntries;
		
		for(int dayIndex = 0; dayIndex < size - 1; dayIndex++) {
			employeeData = getWeeklyScheduleData().getEmployeeDailyScheduleData(employeeId, dayIndex);
			for(WeeklyScheduleDailyEntry entry : employeeData) {
				splittedEntries = breakIntoDailyShifts(entry, dayIndex).iterator();
				if(splittedEntries.hasNext()) {
					weeklySchedule.get(dayIndex).add(splittedEntries.next());
				}
				if(splittedEntries.hasNext()) {
					weeklySchedule.get(dayIndex + 1).add(splittedEntries.next());
				}				
			}
		}
		
		// Must load shifts for next week first day
		if(!weeklySchedule.get(size - 1).isEmpty()) {
			EmployeeSchedule schedule = getFirstDayNextWeekStoreSchedule().getEmployeeSchedule(new Employee(employeeId));
			for(Shift shift : schedule.getShifts()) {
				WeeklyScheduleDailyEntry entry = new WeeklyScheduleDailyEntry();
				entry.setOutHour(shift.getToHour());
				entry.setInHour(shift.getFromHour());
				entry.setDay(getDay(size - 1));
				weeklySchedule.get(size - 1).add(entry);
			}
		}
		
		return weeklySchedule;
	}

	/**
	 * 
	 * @param entry
	 * @param dayIndex
	 * @return
	 */
	private List<WeeklyScheduleDailyEntry> breakIntoDailyShifts(WeeklyScheduleDailyEntry entry, int dayIndex) {
		List<WeeklyScheduleDailyEntry> splittedEntries = new ArrayList<WeeklyScheduleDailyEntry>(2);
		Date selectedDayEndTime = getStoreScheduleEndHour(getDay(dayIndex));
		Date selectedDayNextDayStartTime = getStoreScheduleStartHour(getDay(dayIndex + 1));
		if(CalendarUtils.equalsTime(selectedDayEndTime, selectedDayNextDayStartTime) && splitEntries(entry, selectedDayEndTime, selectedDayNextDayStartTime)) {
			WeeklyScheduleDailyEntry current = entry.clone();
			WeeklyScheduleDailyEntry next = new WeeklyScheduleDailyEntry();
			next.setInHour(selectedDayNextDayStartTime);
			next.setOutHour(current.getOutHour());
			current.setOutHour(selectedDayEndTime);
		} else {
			splittedEntries.add(entry.clone());
		}
		return splittedEntries;
	}
	
	/**
	 * 
	 * @param shift
	 * @param selectedDayEndTime
	 * @param selectedDayNextDayStartTime
	 * @return
	 */
	private boolean splitEntries(WeeklyScheduleDailyEntry entry, Date selectedDayEndTime, Date selectedDayNextDayStartTime) {
		return CalendarUtils.equalsTime(selectedDayEndTime, selectedDayNextDayStartTime) &&
			   (
			   // Case 1: 24hs shift and limit between them
			   (CalendarUtils.equalsTime(entry.getInHour(), entry.getOutHour()) && !CalendarUtils.equalsTime(entry.getInHour(), selectedDayEndTime)) ||
			   // Case 2: To Hour is greater than From Hour and limit is between them
			   (CalendarUtils.greaterTime(entry.getOutHour(), entry.getInHour()) && CalendarUtils.inRangeNotIncludingEndTime(selectedDayEndTime, entry.getInHour(), entry.getOutHour())) ||
			   // Case 3: From Hours is greater than To Hour and limit is between them
			   (CalendarUtils.greaterTime(entry.getInHour(), entry.getOutHour()) && CalendarUtils.inRangeNotIncludingEndTime(selectedDayEndTime, entry.getInHour(), entry.getOutHour()))
			   );
	}

	/**
	 * @return the positionWeeklyTarget
	 */
	public Map<Integer, Double> getPositionWeeklyTarget() {
		if(positionWeeklyTarget == null) {
			setPositionWeeklyTarget(getStaffingService().getTotalProjectedStaffingByPositionForTimePeriod(getEmployeeStore(), getWeekDays().get(0), getWeekDays().get(getWeekDays().size() - 1)));
		}
		return positionWeeklyTarget;
	}

	/**
	 * @param positionWeeklyTarget the positionWeeklyTarget to set
	 */
	public void setPositionWeeklyTarget(Map<Integer, Double> positionWeeklyTarget) {
		this.positionWeeklyTarget = positionWeeklyTarget;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Double getVplhSchedule() {
		if(getTotalHoursSchedule() > 0.0) {
			return new Double(NumberUtils.getDoubleValue(getWeeklyVolume()) / getTotalHoursSchedule());
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Double getVplhTarget() {
		if(getTotalHoursTarget() > 0) {
			return new Double(NumberUtils.getDoubleValue(getWeeklyVolume()) / getTotalHoursTarget());
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}

	/**
	 * 
	 * @return
	 */
	private double getTotalHoursSchedule() {
		double total = 0.0;
		for(StoreSchedule aStoreSchedule : getStoreSchedules()) {
			total += NumberUtils.getDoubleValue(aStoreSchedule.getTotalShiftHours());
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @return
	 */
	private double getTotalHoursTarget() {
		double total = 0.0;
		for(Double d : getPositionWeeklyTarget().values()) {
			total += NumberUtils.getDoubleValue(d);
		}
		return new Double(total);		
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageSchedule() {
		double d = NumberUtils.getDoubleValue(getWeeklyVolume()) * getStoreAverageVariable();
		if(d != 0.0) {
			return (NumberUtils.getDoubleValue(getAverageWage()) * getTotalHoursSchedule() / d) * 100;
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageTarget() {
		double d = NumberUtils.getDoubleValue(getWeeklyVolume()) * getStoreAverageVariable();
		if(d != 0.0) {
			return (NumberUtils.getDoubleValue(getAverageWage()) * getTotalHoursTarget() / d) * 100;
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getAverageWage() {
		double totalWage = 0.0;
		double totalHours = 0.0;
		for(StoreSchedule aStoreSchedule : getStoreSchedules()) {
			totalWage += aStoreSchedule.getTotalWage();
			totalHours += aStoreSchedule.getTotalShiftHours();
		}
		return new Double(totalWage / totalHours);
	}	
	
	/**
	 * 
	 */
	protected void resetWeekData() {
		setWeeklyVolume(null);
	}
}
