/*
 * File name: AddShiftByDayBaseAction.java
 * Creation date: 08/12/2008 18:45:39
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.laborguru.frontend.model.ScheduleHourLabelElement;
import com.laborguru.frontend.model.ScheduleRow;
import com.laborguru.model.DailyProjectedStaffing;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.Employee;
import com.laborguru.model.EmployeeSchedule;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Position;
import com.laborguru.model.Shift;
import com.laborguru.model.StoreDailyStaffing;
import com.laborguru.model.StoreSchedule;
import com.laborguru.service.schedule.ShiftService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class AddShiftByDayBaseAction extends AddShiftBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AddShiftByDayBaseAction.class);
	
	private List<ScheduleHourLabelElement> scheduleLabelHours;
	private List<Date> scheduleIndividualHours;
	private StoreSchedule storeSchedule;
	private List<StoreDailyStaffing> dailyStaffings = null;
	private BigDecimal dailyVolume;
	
	
	private ShiftService shiftService;
	
	/**
	 * 
	 */
	public AddShiftByDayBaseAction() {
	}

	/**
	 * @return the storeSchedule
	 */
	public StoreSchedule getStoreSchedule() {
		if(storeSchedule == null) {
			try {
				storeSchedule = getScheduleService().getStoreScheduleByDate(getEmployeeStore(), getWeekDaySelector().getSelectedDay());
			} catch(RuntimeException ex) {
				log.error("Could not retrieve schedule for " + getWeekDaySelector().getSelectedDay() + " for store " + getEmployeeStore(), ex);
			}
			if(storeSchedule == null) {
				storeSchedule = new StoreSchedule();
				storeSchedule.setStore(getEmployeeStore());
			}
		}
		return storeSchedule;
	}

	/**
	 * @param storeSchedule the storeSchedule to set
	 */
	public void setStoreSchedule(StoreSchedule storeSchedule) {
		this.storeSchedule = storeSchedule;
	}

	/**
	 * This method returns a list with all the individaul 
	 * selectable times (For example all the 15 minutes
	 * periods from the store open hour to the store close
	 * hour)
	 * @return
	 */
	public List<Date> getScheduleIndividualHours() {
		if(this.scheduleIndividualHours == null) {
			if(getStoreSchedule() != null && getStoreSchedule().getStore() != null) {
				OperationTime operationTime = getStoreSchedule().getStore().getOperationTime(getWeekDaySelector().getSelectedDayOfWeek());
				Date closeHour = getScheduleCloseHour(getStoreScheduleEndHour(getWeekDaySelector().getSelectedDay()));
				Date d = operationTime != null ? getScheduleOpenHour(getStoreScheduleStartHour(getWeekDaySelector().getSelectedDay())) : null;

				List<Date> hours = new ArrayList<Date>();

				if(operationTime != null && d != null) {
					Date realOpen = operationTime.getOpenHour();
					Date realClose = operationTime.getCloseHour();
					
					// Hours from yesterday
					if(CalendarUtils.greaterTime(d, realOpen)) {
						while(!CalendarUtils.equalsTime(d, CalendarUtils.getMidnightTime())) {
							hours.add(d);
							d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
						}
					}
					
					// Hours before opening
					while(CalendarUtils.smallerTime(d, realOpen)) {
						hours.add(d);
						d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);						
					}
					
					// Operation hours
					boolean endsTomorrow = CalendarUtils.equalsOrGreaterTime(realOpen, realClose);
					
					if(endsTomorrow) {
						// Special case: Open 24hs from 00:00 to 00:00
						if(CalendarUtils.equalsTime(realOpen, CalendarUtils.getMidnightTime()) && CalendarUtils.equalsTime(realOpen, realClose)) {
							hours.add(d);
							d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
						}
						
						// Today hours
						while(!CalendarUtils.equalsTime(d, CalendarUtils.getMidnightTime())) {
							hours.add(d);
							d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
						}
					}
					
					while(CalendarUtils.smallerTime(d, realClose)) {
						hours.add(d);
						d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
					}
					
					// Hours after closing
					if(CalendarUtils.smallerTime(closeHour, realClose)) {
						while(!CalendarUtils.equalsTime(d, CalendarUtils.getMidnightTime())) {
							hours.add(d);
							d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
						}						
					}
					
					// Hours from tomorrow
					while(CalendarUtils.smallerTime(d, closeHour)) {
						hours.add(d);
						d = new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);						
					}
				}
				
				this.scheduleIndividualHours = hours;
			} else {
				this.scheduleIndividualHours = new ArrayList<Date>();
			}
		}
		return this.scheduleIndividualHours;
	}

	/**
	 * 
	 * @return
	 */
	public Date getLastScheduleIndividualHour() {
		Date d = getScheduleIndividualHours().get(getScheduleIndividualHours().size() - 1);
		return new Date(d.getTime() + SpmConstants.MINUTES_INTERVAL * 60000L);
	}
	
	/**
	 * This method returns a list with the labels of the
	 * schedule hours
	 * @return
	 */
	public List<ScheduleHourLabelElement> getScheduleLabelHours() {
		if(this.scheduleLabelHours == null) {
			List<ScheduleHourLabelElement> hours = new ArrayList<ScheduleHourLabelElement>();
			
			int size = getScheduleIndividualHours().size();
			Date firstHour = size > 0 ? getScheduleIndividualHours().get(0) : null;
			Date lastHour = size > 0 ? getScheduleIndividualHours().get(size - 1) : null;

			if(firstHour != null && lastHour != null) {
				// Open hour is a special case
				hours.add(new ScheduleHourLabelElement(getScheduleBaseHour(firstHour), getHourColspan(firstHour), getOpenHourSelectable(firstHour)));		
				
				Date lastHourInserted = getScheduleBaseHour(firstHour);
				Date lastHourBase = getScheduleBaseHour(lastHour);
				Date currentHour = lastHourInserted;
				
				// Skip last hour
				while(CalendarUtils.equalsTime(lastHourBase, getScheduleBaseHour(getScheduleIndividualHours().get(size - 1)))) {
					size--;
				}
				
				for(int i = 1; i < size; i++) {
					currentHour = getScheduleBaseHour(getScheduleIndividualHours().get(i));
					if(!CalendarUtils.equalsTime(lastHourInserted, currentHour)) {
						hours.add(new ScheduleHourLabelElement(currentHour, getHourColspan(currentHour), getHourColspan(currentHour)));
						lastHourInserted = currentHour;
					}
				}
				
				// Close hour
				hours.add(new ScheduleHourLabelElement(lastHourBase, getHourColspan(lastHourBase), getCloseHourSelectable(getStoreScheduleEndHour(getWeekDaySelector().getSelectedDay()))));

			}
			this.scheduleLabelHours = hours;
		}
		return this.scheduleLabelHours;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getScheduleIndividualStartHoursToIgnore() {
		Integer total = new Integer(60 / SpmConstants.MINUTES_INTERVAL);
		List<Integer> toIgnore = new ArrayList<Integer>();
		Integer start = getScheduleLabelHours() != null && getScheduleLabelHours().size() > 0 ?  getScheduleLabelHours().get(0).getSelectableCount() : null;

		if(start != null && start > 0) {
			for(Integer i = start; i < total; i++) {
				toIgnore.add(i);
			}
		}
		
		return toIgnore;
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getScheduleIndividualEndHoursToIgnore() {
		Integer total = new Integer(60 / SpmConstants.MINUTES_INTERVAL);
		List<Integer> toIgnore = new ArrayList<Integer>();
		Integer start = getScheduleLabelHours() != null && getScheduleLabelHours().size() > 1 ?  getScheduleLabelHours().get(getScheduleLabelHours().size() - 1).getSelectableCount() : null;

		if(start != null && start > 0) {
			for(Integer i = start; i < total; i++) {
				toIgnore.add(i);
			}
		}
		
		return toIgnore;
	}
	
	/**
	 * 
	 * @param storeOpenHour
	 * @return
	 */
	private Date getScheduleOpenHour(Date storeOpenHour) {
		if(storeOpenHour != null) {
			Calendar cal = CalendarUtils.getCalendar(storeOpenHour);
			int minutes = cal.get(Calendar.MINUTE);
			for(int i = 0; SpmConstants.MINUTES_INTERVAL * i <= 60 ;i++ ) {
				if(minutes <= SpmConstants.MINUTES_INTERVAL * i) {
					cal.set(Calendar.MINUTE, SpmConstants.MINUTES_INTERVAL * i);
					return cal.getTime();
				}
			}
			// This should never happend as minutes should always be less than 60
			return storeOpenHour;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param storeOpenHour
	 * @return
	 */
	private Date getScheduleCloseHour(Date storeCloseHour) {
		if(storeCloseHour != null) {
			Calendar cal = CalendarUtils.getCalendar(storeCloseHour);
			int minutes = cal.get(Calendar.MINUTE);

			for(int i = 0; minutes > 0 && SpmConstants.MINUTES_INTERVAL * i <= 60 ;i++ ) {
				if(minutes <= SpmConstants.MINUTES_INTERVAL * i) {
					cal.set(Calendar.MINUTE, SpmConstants.MINUTES_INTERVAL * i);
					return cal.getTime();
				}
			}
			// Null means that close hour is at the minute 0
			return storeCloseHour;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Date getScheduleBaseHour(Date storeCloseHour) {
		if(storeCloseHour != null) {
			Calendar cal = CalendarUtils.getCalendar(storeCloseHour);
			cal.set(Calendar.MINUTE, 0);
			return cal.getTime();
		} else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param hour Hour that must be normalized according to SpmConstants.MINUTES_INTERVAL
	 * @return
	 */
	private Integer getCloseHourSelectable(Date hour) {
		Calendar cal = CalendarUtils.getCalendar(hour);
		int minutes = cal.get(Calendar.MINUTE);
		for(int i = 0; SpmConstants.MINUTES_INTERVAL * i <= 60 ;i++ ) {
			if(minutes <= SpmConstants.MINUTES_INTERVAL * i) {
				return new Integer(i);
			}
		}
		// This should never happend as minutes should always be less than 60
		return null;
	}
	
	/**
	 * 
	 * @param hour
	 * @return
	 */
	private Integer getHourColspan(Date hour) {
		return new Integer(60 / SpmConstants.MINUTES_INTERVAL);
	}
	
	/**
	 * 
	 * @param hour Hour that must be normalized according to SpmConstants.MINUTES_INTERVAL
	 * @return
	 */
	private Integer getOpenHourSelectable(Date hour) {
		Calendar cal = CalendarUtils.getCalendar(hour);
		int minutes = cal.get(Calendar.MINUTE);
		for(int i = 0; SpmConstants.MINUTES_INTERVAL * i <= 60 ;i++ ) {
			if(minutes <= SpmConstants.MINUTES_INTERVAL * i) {
				return new Integer((60/SpmConstants.MINUTES_INTERVAL) - i);
			}
		}
		// This should never happend as minutes should always be less than 60
		return null;
	}
	
	/**
	 * Builds the frontend object that will hold the information to be displayed
	 * in the GUI. If position is null, then all positions will be taken
	 * into account, if not the schedule will be filtered by position.
	 * The StoreSchedule object is used as the source of data.
	 * @param positions
	 * @return
	 */
	protected List<ScheduleRow> buildScheduleFor(List<Position> positions) {
		if(getStoreSchedule() != null) {
			List<ScheduleRow> schedule = new ArrayList<ScheduleRow>();
			ScheduleRow aRow = null;
			double hiddenTotalScheduled = 0.0D;
			
			for(EmployeeSchedule employeeSchedule : getStoreSchedule().getEmployeeSchedules()) {
				boolean isFirst = true;
				for(Shift shift : employeeSchedule.getShifts()) {
					if(shift != null) {
						if(!shift.isBreak() && ((isAllPositions() && !isByPositionView()) || isPositionInList(positions, shift.getPosition()))) {
							aRow = buildScheduleRow(employeeSchedule, shift, schedule, isFirst);
							isFirst = false;
							if(aRow != null) {
								schedule.add(aRow);
							}
						} else if(!shift.isBreak() && !isAllPositions()) {
							// Total shift hours that are not shown, but should be taken into account
							// for totals
							hiddenTotalScheduled += NumberUtils.getDoubleValue(shift.getTotalShiftHours());
						}
					}
				}
			}
			setHiddenTotalScheduled(hiddenTotalScheduled);
			
			// Sort schedule
			sortScheduleRows(schedule);
			return schedule;
		} else {
			return new ArrayList<ScheduleRow>();
		}
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	protected List<ScheduleRow> buildScheduleFor(Position position) {
		return buildScheduleFor(getPositionInList(position));
	}
	
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	protected boolean isExtraHourTime(Date time) {
		Date selectedDay = getWeekDaySelector().getSelectedDay();
		OperationTime operationTime = getOperationTime(selectedDay);
		if(isMultiDaySchedule(selectedDay)) {
			return CalendarUtils.smallerTime(time, operationTime.getOpenHour()) && CalendarUtils.equalsOrGreaterTime(time, operationTime.getCloseHour());
		} else {
			return CalendarUtils.smallerTime(time, operationTime.getOpenHour()) || CalendarUtils.equalsOrGreaterTime(time, operationTime.getCloseHour());
		}
	}
	
	/**
	 * 
	 * @param positions
	 * @return
	 */
	protected List<Integer> buildMinimumStaffingFor(List<Position> positions) {
		int size = getScheduleIndividualHours().size();
		List<Integer> minimumStaffing = new ArrayList<Integer>(size);

		// :TODO: Improve performance. This will call getHalfHourStaffing every 15 minutes (2 times)
		// This should be calculated only once
		StoreDailyStaffing dailyStaffing = getSelectedDayDailyStaffing();
		Date lastProcessedTime = null;
		if(isAllPositions() && !isByPositionView()) {
			for(Date time : getScheduleIndividualHours()) {
				if(lastProcessedTime != null && CalendarUtils.smallerTime(time, lastProcessedTime)) {
					dailyStaffing = getNextDayDailyStaffing();
				}
				if(isExtraHourTime(time)) {
					minimumStaffing.add(new Integer(0));
				} else {
					minimumStaffing.add(new Integer(dailyStaffing.getHalfHourStaffing(time)));
				}
				lastProcessedTime = time;
			}			
		} else {
			for(Date time : getScheduleIndividualHours()) {
				if(lastProcessedTime != null && CalendarUtils.smallerTime(time, lastProcessedTime)) {
					dailyStaffing = getNextDayDailyStaffing();
				}
				if(isExtraHourTime(time)) {
					minimumStaffing.add(new Integer(0));
				} else {
					minimumStaffing.add(new Integer(dailyStaffing.getHalfHourStaffing(positions, time)));
				}				
				lastProcessedTime = time;
			}			
		}
		
		return minimumStaffing;
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	protected List<Integer> buildMinimumStaffingFor(Position position) {
		return buildMinimumStaffingFor(	getPositionInList(position));
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	protected Map<Integer, List<Shift>> retrieveCurrentShiftsWithContiguous(List<Position> positions) {
		Map<Integer, List<Shift>> shiftsWithContiguous = new HashMap<Integer, List<Shift>>();
		
		List<Shift> shifts;
		for(EmployeeSchedule schedule : getStoreSchedule().getEmployeeSchedules()) {
			shifts = isAllPositions() && !isByPositionView() ? schedule.getShiftsWithContiguous() : schedule.getShiftsWithContiguous(positions);
			if(shifts != null && shifts.size() > 0) {
				shiftsWithContiguous.put(schedule.getEmployee().getId(), cloneShifts(shifts));
			}
		}
		
		return shiftsWithContiguous;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	private List<Shift> cloneShifts(List<Shift> source) {
		if(source == null || source.size() <= 0) {
			return null;
		} else {
			List<Shift> clonedShifts = new ArrayList<Shift>(source.size());
			Shift cloned;
			for(Shift aShift : source) {
				cloned = new Shift();
				cloned.setId(aShift.getId());
				cloned.setStartingShift(aShift.getStartingShift());
				cloned.setContiguousShift(aShift.getContiguousShift());
				cloned.setFromHour(aShift.getFromHour());
				cloned.setToHour(aShift.getToHour());
				cloned.setPosition(aShift.getPosition());
				clonedShifts.add(cloned);
			}
			return clonedShifts;
		}
	}
	
	/**
	 * 
	 * @param shiftsWithContiguous
	 * @return
	 */
	private Collection<Position> retrieveShiftsWithContiguousPositions(List<Shift> shiftsWithContiguous) {
		if(shiftsWithContiguous != null) {
			Map<Integer, Position> positions = new HashMap<Integer, Position>(shiftsWithContiguous.size());
			for(Shift shift : shiftsWithContiguous) {
				if(!shift.isBreak() && shift.getPosition() != null && shift.getPosition().getId() != null) {
					positions.put(shift.getPosition().getId(), shift.getPosition());
				}
			}
			return positions.values();
		} else {
			return new HashSet<Position>();
		}
		
	}
	
	/**
	 * 
	 * @param shiftsWithContiguous
	 * @param position
	 * @return
	 */
	private Shift retrieveShiftWithContiguousFor(List<Shift> shiftsWithContiguous, Position position) {
		for(Shift shift : shiftsWithContiguous) {
			if(isEqualPosition(shift.getPosition(), position)) {
				return shift;
			}
		}
		// Should never happen as position should exit
		return null;
	}
	
	/**
	 * 
	 * @param shiftsWithContiguous
	 */
	protected List<Shift> updateShiftsWithContiguous(Map<Integer, List<Shift>> shiftsWithContiguous) {
		Date selectedDayEndHour = getStoreScheduleEndHour(getWeekDaySelector().getSelectedDay());
		List<Shift> referencedShiftToUpdate = new ArrayList<Shift>();
		
		for(EmployeeSchedule schedule : getStoreSchedule().getEmployeeSchedules()) {
			List<Shift> originalShifts = shiftsWithContiguous.get(schedule.getEmployee().getId());
			if(originalShifts != null && originalShifts.size() > 0) {
				for(Position position : retrieveShiftsWithContiguousPositions(originalShifts)) {
					Shift originalShift = retrieveShiftWithContiguousFor(originalShifts, position);
					if(originalShift != null) {
						Shift scheduleShift = schedule.getLastShiftFor(position);
						
						// Re-establish the contiguous relation when there is no change in the shift end time
						if(scheduleShift != null && CalendarUtils.equalsTime(selectedDayEndHour, scheduleShift.getToHour()) && CalendarUtils.equalsTime(scheduleShift.getToHour(), originalShift.getToHour())) {
							scheduleShift.setContiguousShift(originalShift.getContiguousShift());
						} else {
							originalShift.getContiguousShift().setStartingShift(null);
						}
						referencedShiftToUpdate.add(originalShift.getContiguousShift());
					}
				}
			}
		}
		return referencedShiftToUpdate;
	}

	/**
	 * 
	 * @param positions
	 * @return
	 */
	protected Map<Integer, List<Shift>> retrieveCurrentReferencedShifts(List<Position> positions) {
		Map<Integer, List<Shift>> referencedShifts = new HashMap<Integer, List<Shift>>();
		
		List<Shift> shifts;
		for(EmployeeSchedule schedule : getStoreSchedule().getEmployeeSchedules()) {
			shifts = isAllPositions() && !isByPositionView() ? schedule.getReferencedShifts() : schedule.getReferencedShifts(positions);
			if(shifts != null && shifts.size() > 0) {
				referencedShifts.put(schedule.getEmployee().getId(), cloneShifts(shifts));
			}
		}
		
		return referencedShifts;
	}
	
	/**
	 * 
	 * @param shifts
	 */
	protected void updateShifts(List<Shift> shifts) {
		for(Shift shift : shifts) {
			getShiftService().save(shift);
		}
	}
	
	/**
	 * 
	 * @param referencedShifts
	 */
	protected List<Shift> updateReferencedShifts(Map<Integer, List<Shift>> referencedShifts) {
		EmployeeSchedule schedule;
		Shift referencingShift, currentFirstShift;
		List<Shift> referencedShiftToUpdate = new ArrayList<Shift>();
		Date selectedDayStartHour = getStoreScheduleStartHour(getWeekDaySelector().getSelectedDay());
		
		for(Integer employeeId : referencedShifts.keySet()) {
			schedule = getStoreSchedule().getEmployeeSchedule(new Employee(employeeId));
			if(schedule != null) {
				for(Shift shift : referencedShifts.get(employeeId)) {
					referencingShift = shift.getStartingShift();
					currentFirstShift = schedule.getFirstShiftFor(shift.getPosition());
					
					if(referencedShifts != null && currentFirstShift != null && CalendarUtils.equalsTime(selectedDayStartHour, currentFirstShift.getFromHour()) && CalendarUtils.equalsTime(referencingShift.getToHour(), currentFirstShift.getFromHour())) {
						currentFirstShift.setStartingShift(referencingShift);
					} else {
						currentFirstShift.setStartingShift(null);
						breakContiguousShiftReference(referencingShift);
					}
					referencedShiftToUpdate.add(referencingShift);
				}
			} else {
				List<Shift> referencingShifts = referencedShifts.get(employeeId);
				breakContiguousShiftReference(referencingShifts);
				referencedShiftToUpdate.addAll(referencingShifts);
			}
		}
		return referencedShiftToUpdate;
	}
	
	/**
	 * 
	 * @param shift
	 */
	private void breakContiguousShiftReference(Shift shift) {
		shift.setContiguousShift(null);
	}
	
	/**
	 * 
	 * @param shifts
	 */
	private void breakContiguousShiftReference(List<Shift> shifts) {
		for(Shift shift : shifts) {
			breakContiguousShiftReference(shift);
		}
	}
	
	/**
	 * 
	 * @param schedule
	 * @param positions
	 * @param day
	 */
	protected void setSchedule(List<ScheduleRow> schedule, List<Position> positions) {
		getStoreSchedule().setDay(getWeekDaySelector().getSelectedDay());
		getStoreSchedule().setStore(getEmployeeStore());
		
		Set<Integer> employeeIds = getDifferentEmployeeIds(schedule);
		Employee employee;
		for(Integer employeeId : employeeIds) {
			if(employeeId != null) {
				employee = getEmployeeService().getEmployeeById(new Employee(employeeId));
				EmployeeSchedule employeeSchedule = getStoreSchedule().getEmployeeSchedule(employee);
				if(employeeSchedule == null) {
					employeeSchedule = new EmployeeSchedule();
					employeeSchedule.setEmployee(employee);
					employeeSchedule.setStoreSchedule(getStoreSchedule());
					getStoreSchedule().getEmployeeSchedules().add(employeeSchedule);
				}
				setShifts(getEmployeeSchedule(employeeId, schedule), employeeSchedule, positions);				
			}
		}
		
		// Remove non existing shifts for the employee and for a position
		cleanStoreSchedule(employeeIds, positions);
	}

	/**
	 * 
	 * @param schedule
	 * @param position
	 */
	protected void setSchedule(List<ScheduleRow> schedule, Position position) {
		setSchedule(schedule, getPositionInList(position));
	}
	
	/**
	 * 
	 * @param employeeIds
	 * @param positions
	 */
	private void cleanStoreSchedule(Set<Integer> employeeIds, List<Position> positions) {
		if(getStoreSchedule() != null && employeeIds != null) {
			Set<EmployeeSchedule> employeeSchedulesToRemove = new HashSet<EmployeeSchedule>();
			Set<Shift> shiftsToRemove = new HashSet<Shift>();
			
			for(EmployeeSchedule employeeSchedule : getStoreSchedule().getEmployeeSchedules()) {
				if(employeeSchedule.getEmployee() != null && !employeeIds.contains(employeeSchedule.getEmployee().getId())) {
					if(isAllPositions() && !isByPositionView()) {
						// Applies for all positions
						employeeSchedulesToRemove.add(employeeSchedule);
					} else {
						for(Shift shift : employeeSchedule.getShifts()) {
							if(shift != null && isPositionInList(positions, shift.getPosition())) {
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
				}
			}
			getStoreSchedule().getEmployeeSchedules().removeAll(employeeSchedulesToRemove);
		}
	}

	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param position
	 */
	private void setShifts(List<ScheduleRow> source, EmployeeSchedule employeeSchedule, List<Position> positions) {
		if(isAllPositions() && !isByPositionView()) {
			setShiftsAllPositions(source, employeeSchedule);
		} else {
			setShiftsForPosition(source, employeeSchedule, positions);
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 * @param positions
	 */
	private void setShiftsForPosition(List<ScheduleRow> source, EmployeeSchedule employeeSchedule, List<Position> positions) {
		int shiftPosition = 0;
		List<Shift> rowShifts;
		employeeSchedule.reindexShifts();
		List<Shift> currentShifts = employeeSchedule.getShifts();
		int currentShiftsSize = currentShifts.size();
		
		for(ScheduleRow row : source) {
			rowShifts = retrieveShifts(row);
			
			for(Shift aShift : rowShifts) {
				if(isPositionInList(positions, aShift.getPosition())) {
					while(shiftPosition < currentShifts.size() && !isPositionInList(positions, currentShifts.get(shiftPosition).getPosition())) {
						shiftPosition++;
					}
					
					if(shiftPosition < currentShiftsSize) {
						updateShift(aShift, currentShifts.get(shiftPosition));
						shiftPosition++;
					} else {
						aShift.setEmployeeSchedule(employeeSchedule);
						employeeSchedule.addShift(aShift);
					}
				}
			}
		}
		
		// Remove non-existing shifts
		if(shiftPosition < currentShiftsSize) {
			for(int i = currentShiftsSize - 1; i >= shiftPosition; i--) {
				if(isPositionInList(positions, currentShifts.get(i).getPosition())) {
					currentShifts.remove(i);
				}
			}
		}		
	}
	
	
	
	/**
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	protected void validateSchedule(List<ScheduleRow> schedule) {
		validateScheduleRows(schedule);
		Set<Integer> employeeIds = getDifferentEmployeeIds(schedule);

		for(Integer employeeId : employeeIds) {
			validateEmployeeSchedule(employeeId, schedule);
		}
	}

	/**
	 * 
	 * @param schedule
	 */
	private void validateScheduleRows(List<ScheduleRow> schedule) {
		int rowCount = 1;
		
		for(ScheduleRow aRow : schedule) {
			if(aRow.getEmployeeId() == null || aRow.getEmployeeId().intValue() <= 0) {
				addActionError(getText("error.schedule.addshift.employee_missing", new String[]{String.valueOf(rowCount)}));
			}
			rowCount++;
		}
	}
	
	/**
	 * 
	 * @param employeeId
	 * @param schedule
	 */
	private void validateEmployeeSchedule(Integer employeeId, List<ScheduleRow> schedule) {
		List<ScheduleRow> employeeSchedules = getEmployeeSchedule(employeeId, schedule);
		int buckets = getScheduleIndividualHours().size();
		boolean collision = false;
		int quantity;
		
		for(int i = 0; i < buckets && !collision; i++) {
			quantity = 0;
			for(int j = 0; j < employeeSchedules.size() && !collision; j++) {
				if(i < employeeSchedules.get(j).getSchedule().size()) {
					if(!SpmConstants.SCHEDULE_FREE.equals(employeeSchedules.get(j).getSchedule().get(i))) {
						quantity++;
					}
					collision = quantity > 1;
				} else {
					log.warn("Buckets size " + buckets + " is greater than schedule length " + employeeSchedules.get(j).getSchedule().size() + " for employee schedule " + employeeSchedules.get(j));
				}
			}
		}
		
		if(collision) {
			String employeeName = employeeSchedules.get(0).getEmployeeName();
			addActionError(getText("error.schedule.addshift.employee_shift_collision", new String[]{employeeName}));
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param employeeSchedule
	 */
	private void setShiftsAllPositions(List<ScheduleRow> source, EmployeeSchedule employeeSchedule) {
		int shiftPosition = 0;
		List<Shift> rowShifts;
		employeeSchedule.reindexShifts(); // Remove shifts that are null
		int currentShiftsSize = employeeSchedule.getShifts().size();
		
		for(ScheduleRow row : source) {
			rowShifts = retrieveShifts(row);
			for(Shift aShift : rowShifts) {
				if(shiftPosition < currentShiftsSize) {
					updateShift(aShift, employeeSchedule.getShifts().get(shiftPosition));
					shiftPosition++;
				} else {
					aShift.setEmployeeSchedule(employeeSchedule);
					employeeSchedule.addShift(aShift);
				}
			}
		}
		
		// Remove non-existing shifts
		if(shiftPosition < currentShiftsSize) {
			for(int i = currentShiftsSize - 1; i >= shiftPosition; i--)
				employeeSchedule.removeShift(i);
		}
	}
	
	/**
	 * 
	 * @param row
	 * @return
	 */
	private List<Shift> retrieveShifts(ScheduleRow row) {
		List<Shift> rowShifts = new ArrayList<Shift>();
		
		Shift aShift = null;
		String currentShift = null;
		String element;
		for(int i = 0; i < row.getSchedule().size(); i++) {
			element = row.getSchedule().get(i);
			
			if(currentShift == null && !SpmConstants.SCHEDULE_FREE.equals(element)) {
				currentShift = element;
				aShift = new Shift();
				aShift.setFromHour(getScheduleIndividualHours().get(i));
			} else if(currentShift != null && !currentShift.equals(element)) {
				aShift.setToHour(getScheduleIndividualHours().get(i));
				
				if(SpmConstants.SCHEDULE_BREAK.equals(currentShift)) {
					aShift.setPosition(null);
				} else {
					Position pos = new Position();
					//:TODO: Validation
					pos.setId(new Integer(currentShift));
					aShift.setPosition(pos);
				}
				rowShifts.add(aShift);
				aShift = null;
				
				if(SpmConstants.SCHEDULE_FREE.equals(element)) {
					currentShift = null;
				} else {
					currentShift = element;
					aShift = new Shift();
					aShift.setFromHour(getScheduleIndividualHours().get(i));
				}
			}
		}
		
		if(currentShift != null) {
			aShift.setToHour(getLastScheduleIndividualHour());
			
			if(SpmConstants.SCHEDULE_BREAK.equals(currentShift)) {
				aShift.setPosition(null);
			} else {
				Position pos = new Position();
				//:TODO: Validation
				pos.setId(new Integer(currentShift));
				aShift.setPosition(pos);
			}
			rowShifts.add(aShift);			
		}
		return rowShifts;
	}

	/**
	 * 
	 * @param employeeId
	 * @param schedule
	 * @return
	 */
	private List<ScheduleRow> getEmployeeSchedule(Integer employeeId, List<ScheduleRow> schedule) {
		List<ScheduleRow> employeeSchedule = new ArrayList<ScheduleRow>();
		
		if(schedule != null) {
			for(ScheduleRow aRow : schedule) {
				if(employeeId != null && employeeId.equals(aRow.getEmployeeId())) {
					employeeSchedule.add(aRow);
				}
			}
		}
		return employeeSchedule;
	}
	
	/**
	 * 
	 * @param schedule
	 * @return
	 */
	private Set<Integer> getDifferentEmployeeIds(List<ScheduleRow> schedule) {
		Set<Integer> ids = new HashSet<Integer>();
		
		if(schedule != null) {
			for(ScheduleRow aRow : schedule) {
				if(!ids.contains(aRow.getEmployeeId())) {
					ids.add(aRow.getEmployeeId());
				}
			}
		}
		
		return ids;
	}
	
	/**
	 * 
	 * @param schedule
	 * @param employee
	 * @param shift
	 * @return
	 */
	private boolean alreadyInSchedule(List<ScheduleRow> schedule, Employee employee, Shift shift) {
		for(ScheduleRow row : schedule) {
			if(isEqualId(row.getEmployeeId(), employee != null ? employee.getId() : null)) {
				if(shift.isBreak()/* && row.getPositionId() == null*/) {
					// Employee has only breaks
					return true;
				} else if(isEqualId(row.getPositionId(), shift.getPosition() != null ? shift.getPosition().getId() : null)) {
					// Position already taken into account
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param employeeSchedule
	 * @param shift
	 * @param schedule
	 * @return
	 */
	private ScheduleRow buildScheduleRow(EmployeeSchedule employeeSchedule, Shift shift, List<ScheduleRow> schedule, boolean isFirst) {
		if(!alreadyInSchedule(schedule, employeeSchedule.getEmployee(), shift) && employeeSchedule.getEmployee() != null) {
			ScheduleRow aRow = new ScheduleRow();
			aRow.setEmployeeId(employeeSchedule.getEmployee().getId());
			aRow.setOriginalEmployeeId(employeeSchedule.getEmployee().getId());
			aRow.setEmployeeName(employeeSchedule.getEmployee().getFullName());
			aRow.setPositionId(shift.getPosition() != null ? shift.getPosition().getId() : null);
			aRow.setPositionName(shift.getPosition() != null ? shift.getPosition().getName() : null);
			aRow.setEmployeeMaxDaysWeek(employeeSchedule.getEmployee().getMaxDaysWeek());
			aRow.setEmployeeMaxHoursDay(employeeSchedule.getEmployee().getMaxHoursDay());
			aRow.setEmployeeMaxHoursWeek(employeeSchedule.getEmployee().getMaxHoursWeek());
			aRow.setEmployeeWage(employeeSchedule.getEmployee().getWage());
			
			List<Date> scheduleBuckets = getScheduleIndividualHours();
			List<String> occupation = initializeScheduleRow();
			List<String> hours = initializeScheduleHoursRow();
			boolean foundFirst = false;
			
			for(Shift aShift : employeeSchedule.getShifts()) {
				// Added because of Bug#220 (getShifts has null references)
				if(aShift != null) {
					if((aShift.isBreak() && isFirst) || (aShift.isBreak() && foundFirst) || isEqualPosition(shift.getPosition(), aShift.getPosition())) {
						setScheduleOccupation(occupation, scheduleBuckets, aShift);
						if(!aShift.isBreak()) {
							foundFirst = true;
						}
					} else {
						// Skip until first
						if(foundFirst && !aShift.isBreak()) {
							// Position changed!
							break;
						}
					}
				}
			}
			
			aRow.setSchedule(occupation);
			aRow.setHours(hours);
			
			return aRow;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param occupation
	 * @param scheduleBuckets
	 * @param shift
	 */
	private void setScheduleOccupation(List<String> occupation, List<Date> scheduleBuckets, Shift shift) {
		int from = getFirstIndexOfBucket(shift.getFromHour(), scheduleBuckets);
		int to = CalendarUtils.equalsOrGreaterTime(shift.getFromHour(), shift.getToHour()) ? getLastIndexOfBucket(shift.getToHour(), scheduleBuckets) :
			getFirstIndexOfBucket(shift.getToHour(), scheduleBuckets) - 1;
		
		String value;
		for(int i = from; i <= to; i++) {
			if(i < occupation.size()) {
				value = occupation.get(i);
				//Never override a shift with a break
				if(!shift.isBreak() || (shift.isBreak() && SpmConstants.SCHEDULE_FREE.equals(value))) {
					occupation.set(i, shift.isBreak() ? SpmConstants.SCHEDULE_BREAK : String.valueOf(shift.getPosition().getId()));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param hour
	 * @param scheduleBuckets
	 * @return
	 */
	private int getFirstIndexOfBucket(Date hour, List<Date> scheduleBuckets) {
		Date anHour;
		for(int i = 0; i < scheduleBuckets.size(); i++) {
			anHour = scheduleBuckets.get(i);
			if(CalendarUtils.equalsOrSmallerTime(hour, anHour)) {
				return i;
			}
		}
		return scheduleBuckets.size();
	}
	
	/**
	 * 
	 * @param hour
	 * @param scheduleBuckets
	 * @return
	 */
	private int getLastIndexOfBucket(Date hour, List<Date> scheduleBuckets) {
		Date anHour;

		// Added for special midnight value of 00:00hs
		if(CalendarUtils.isMidnightTime(hour)) {
			for(int i = scheduleBuckets.size() - 1; i >= 0; i--) {
				anHour = scheduleBuckets.get(i);
				if(CalendarUtils.isMidnightTime(anHour)) {
					return i - 1;
				}
			}
		} else {
			for(int i = scheduleBuckets.size() - 1; i >= 0; i--) {
				anHour = scheduleBuckets.get(i);
				if(CalendarUtils.greaterTime(hour, anHour)) {
					return i;
				}
			}
		}
		
		return scheduleBuckets.size();
	}
	
	/**
	 * 
	 * @param scheduleBuckets
	 * @return
	 */
	protected List<String> initializeScheduleRow() {
		int size = getScheduleIndividualHours().size();
		List<String> scheduleRow = new ArrayList<String>(size);
		for(int i = 0; i < size; i++) {
			scheduleRow.add(SpmConstants.SCHEDULE_FREE);
		}
		return scheduleRow;
	}

	/**
	 * 
	 * @param scheduleBuckets
	 * @return
	 */
	protected List<String> initializeScheduleHoursRow() {
		List<Date> scheduleBuckets = getScheduleIndividualHours();
		List<String> hoursRow = new ArrayList<String>(scheduleBuckets.size());
		final SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		for(int i = 0; i < scheduleBuckets.size(); i++) {
			hoursRow.add(df.format(scheduleBuckets.get(i)));
		}
		return hoursRow;
	}	
	
	
	/**
	 * @param scheduleLabelHours the scheduleLabelHours to set
	 */
	public void setScheduleLabelHours(
			List<ScheduleHourLabelElement> scheduleLabelHours) {
		this.scheduleLabelHours = scheduleLabelHours;
	}

	/**
	 * @param scheduleIndividualHours the scheduleIndividualHours to set
	 */
	public void setScheduleIndividualHours(List<Date> scheduleIndividualHours) {
		this.scheduleIndividualHours = scheduleIndividualHours;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getTotalIndividualHours() {
		return new Integer(getScheduleIndividualHours().size());
	}
	
	/**
	 * @return the dailyStaffings
	 */
	public List<StoreDailyStaffing> getDailyStaffings() {
		if(dailyStaffings == null) {
			setDailyStaffings(new ArrayList<StoreDailyStaffing>(2));
			Date selectedDay = getWeekDaySelector().getSelectedDay();
			
			dailyStaffings.add(getStaffingService().getDailyStaffingByDate(getEmployeeStore(), selectedDay));
			if(isMultiDaySchedule(selectedDay)) {
				dailyStaffings.add(getStaffingService().getDailyStaffingByDate(getEmployeeStore(), CalendarUtils.addOrSubstractDays(selectedDay, 1)));
			}
			
		}
		return dailyStaffings;
	}

	/**
	 * @param dailyStaffing the dailyStaffing to set
	 */
	public void setDailyStaffings(List<StoreDailyStaffing> dailyStaffings) {
		this.dailyStaffings = dailyStaffings;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	private int getTotalPositionTargetInMinutes(Position position) {
		StoreDailyStaffing storeDailyStaffing = getSelectedDayDailyStaffing();
		if(storeDailyStaffing != null) {
			DailyProjectedStaffing dailyStaffing = storeDailyStaffing.getDailyStaffingFor(position);
			return getTotalDailyStaffingInMinutes(dailyStaffing);
		} else {
			return 0;
		}		
	}
	
	/**
	 * @return the dailyVolume
	 */
	public BigDecimal getDailyVolume() {
		if(dailyVolume == null) {
			DailyProjection dailyProjection = getProjectionService().getDailyProjection(getEmployeeStore(), getWeekDaySelector().getSelectedDay());
			if(dailyProjection != null) {
				setDailyVolume(dailyProjection.getDailyProjectionValue());
			} else {
				setDailyVolume(new BigDecimal("0"));
			}
		}
		return dailyVolume;
	}



	/**
	 * @param dailyVolume the dailyVolume to set
	 */
	public void setDailyVolume(BigDecimal dailyVolume) {
		this.dailyVolume = dailyVolume;
	}
	
	/**
	 * 
	 * @return
	 */
	protected StoreDailyStaffing getSelectedDayDailyStaffing() {
		List<StoreDailyStaffing> staffings = getDailyStaffings();
		if(staffings != null && staffings.size() > 0) {
			return staffings.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected StoreDailyStaffing getNextDayDailyStaffing() {
		List<StoreDailyStaffing> staffings = getDailyStaffings();
		if(staffings != null && staffings.size() > 1) {
			return staffings.get(1);
		} else if(staffings.size() > 0) {
			return staffings.get(0);
		} else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getTotalTarget() {
		StoreDailyStaffing storeDailyStaffing = getSelectedDayDailyStaffing();
		if(storeDailyStaffing != null) {
			return CalendarUtils.minutesToTime(new Integer(getTotalTargetInMinutes(storeDailyStaffing.getStoreDailyStaffing())));
		} else {
			return CalendarUtils.minutesToTime(new Integer(0));
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public String getTotalPositionTarget(Position position) {
		return CalendarUtils.minutesToTime(new Integer(getTotalPositionTargetInMinutes(position)));
	}

	/**
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#onWeekdaySelectorChange()
	 */
	@Override
	protected void onWeekdaySelectorChange() {
		setDailyVolume(null);
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.schedule.AddShiftBaseAction#loadScheduleViewsMap()
	 */
	@Override
	protected void loadScheduleViewsMap() {
		setScheduleViewsMap(getReferenceDataService().getScheduleViews());
	}

	/**
	 * @return the shiftService
	 */
	public ShiftService getShiftService() {
		return shiftService;
	}

	/**
	 * @param shiftService the shiftService to set
	 */
	public void setShiftService(ShiftService shiftService) {
		this.shiftService = shiftService;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getVplhSchedule() {
		if(getTotalHoursSchedule() > 0.0) {
			return new Double(NumberUtils.getDoubleValue(getDailyVolume()) / getTotalHoursSchedule());
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Double getVplhTarget() {
		StoreDailyStaffing storeDailyStaffing = getSelectedDayDailyStaffing();
		if(storeDailyStaffing != null) {
			return new Double(NumberUtils.getDoubleValue(getDailyVolume()) / NumberUtils.getDoubleValue(storeDailyStaffing.getTotalDailyTarget()));
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}

	/**
	 * 
	 * @return
	 */
	private double getTotalHoursSchedule() {
		return NumberUtils.getDoubleValue(getStoreSchedule().getTotalShiftHours());
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageSchedule() {
		double d = NumberUtils.getDoubleValue(getDailyVolume()) * getStoreAverageVariable();
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
		StoreDailyStaffing storeDailyStaffing = getSelectedDayDailyStaffing();
		double d = NumberUtils.getDoubleValue(getDailyVolume()) * getStoreAverageVariable();
		if(storeDailyStaffing != null && d != 0.0) {
			return (NumberUtils.getDoubleValue(getAverageWage()) * NumberUtils.getDoubleValue(storeDailyStaffing.getTotalDailyTarget()) / d) * 100;
		} else {
			return NumberUtils.EMPTY_DOUBLE;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getAverageWage() {
		return getStoreSchedule().getAverageWage();
	}
	
	/**
	 * 
	 */
	protected void resetDayData() {
		setDailyVolume(null);
	}
	
	/**
	 * 
	 */
	protected void loadCopyTargetDay() {
		setCopyTargetDay(CalendarUtils.addOrSubstractDays(getWeekDaySelector().getSelectedDay(), 1));
	}
	
	/**
	 * 
	 */
	protected void initializeCopyTargetDay() {
		if(getCopyTargetDay() == null || !CalendarUtils.isAfterToday(getCopyTargetDay())) {
			loadCopyTargetDay();
		}
	}	
}