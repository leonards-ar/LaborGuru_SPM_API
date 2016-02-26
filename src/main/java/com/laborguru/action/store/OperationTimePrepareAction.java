package com.laborguru.action.store;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.action.utils.CustomValidators;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.OperationTime;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 * This action deals with Store CRUD.
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
@SuppressWarnings("serial")
public class OperationTimePrepareAction extends StoreAdministrationBaseAction {
	
	private static Logger log = Logger.getLogger(OperationTimePrepareAction.class);

	private Integer firstDayOfWeek;
	
	private String weekOperationTimeOpen[] = new String[DayOfWeek.values().length];
	private String weekOperationTimeClose[] = new String[DayOfWeek.values().length];
	private String closingExtraHours[] = new String[DayOfWeek.values().length];
	private String openingExtraHours[] = new String[DayOfWeek.values().length];
	
	
	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
	}

	/**
	 * Prepare data to be used to display store data
	 */
	public void prepareShow() {
	}

	/**
	 * Prepare the data to be used on the add page. We should preload the list
	 * needed to render the add page. When a validation fails the application
	 * goes back to the add page and this data is needed.
	 * 
	 * @throws Exception
	 */
	public void prepareSave() {
	}

	/**
	 * Initializes the container object that will handle input of
	 * open and close operation times.
	 */
	private void loadOperationTimes() {
		if(getStore() != null) {
			for(OperationTime time : getStore().getOperationTimes()) {
				if(time != null && time.getDayOfWeekIndex() != null) {
					weekOperationTimeOpen[time.getDayOfWeekIndex().intValue()] = dateToDisplayTime(time.getOpenHour());
					weekOperationTimeClose[time.getDayOfWeekIndex().intValue()] = dateToDisplayTime(time.getCloseHour());
					openingExtraHours[time.getDayOfWeekIndex().intValue()] = time.getOpeningExtraHours() != null ? String.valueOf(time.getOpeningExtraHours()) : null;
					closingExtraHours[time.getDayOfWeekIndex().intValue()] = time.getClosingExtraHours() != null ? String.valueOf(time.getClosingExtraHours()) : null;
				}
			}
		}
	}

	/**
	 * Puts all the corresponding values in the Store object
	 * so it can be updated.
	 */
	private void setOperationTimes() {
		if(getStore().getOperationTimes() != null) {
			OperationTime anOperationTime;

			for(int i=0; i < getWeekOperationTimeOpen().length; i++) {
				if(getStore().getOperationTimes().size() > i && getStore().getOperationTimes().get(i) != null) {
					// Already exists
					anOperationTime = getStore().getOperationTimes().get(i);
					anOperationTime.setOpenHour(displayTimeToDate(weekOperationTimeOpen[i]));
					anOperationTime.setCloseHour(displayTimeToDate(weekOperationTimeClose[i]));
					anOperationTime.setOpeningExtraHours(NumberUtils.stringToInteger(openingExtraHours[i]));
					anOperationTime.setClosingExtraHours(NumberUtils.stringToInteger(closingExtraHours[i]));
				} else {
					// New Operation time for the current day of week
					anOperationTime = new OperationTime();
					anOperationTime.setStore(getStore());
					anOperationTime.setDayOfWeek(DayOfWeek.values()[i]);
					anOperationTime.setOpenHour(displayTimeToDate(weekOperationTimeOpen[i]));
					anOperationTime.setCloseHour(displayTimeToDate(weekOperationTimeClose[i]));
					anOperationTime.setOpeningExtraHours(NumberUtils.stringToInteger(openingExtraHours[i]));
					anOperationTime.setClosingExtraHours(NumberUtils.stringToInteger(closingExtraHours[i]));
					getStore().getOperationTimes().add(anOperationTime);
				}
			}
		}
	}
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		loadOperationTimes();
		setFirstDayOfWeek(getStore().getFirstDayOfWeekAsInteger());
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
	
		loadOperationTimes();
		
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
			setOperationTimes();
			getStore().setFirstDayOfWeekAsInteger(getFirstDayOfWeek());
			if(log.isDebugEnabled()) {
				log.debug("About to save store: " + getStore());
			}
			
			saveStoreAndLoadItIntoSession(getStore());
			
			if(log.isInfoEnabled()) {
				log.info("Store hours of operation successfully updated for store with id [" + getStoreId() + "]");
			}
			
			return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the weekOperationTimeOpen
	 */
	public String[] getWeekOperationTimeOpen() {
		return weekOperationTimeOpen;
	}

	/**
	 * @param weekOperationTimeOpen the weekOperationTimeOpen to set
	 */
	public void setWeekOperationTimeOpen(String[] weekOperationTimeOpen) {
		this.weekOperationTimeOpen = weekOperationTimeOpen;
	}

	/**
	 * @return the weekOperationTimeClose
	 */
	public String[] getWeekOperationTimeClose() {
		return weekOperationTimeClose;
	}

	/**
	 * @param weekOperationTimeClose the weekOperationTimeClose to set
	 */
	public void setWeekOperationTimeClose(String[] weekOperationTimeClose) {
		this.weekOperationTimeClose = weekOperationTimeClose;
	}

	/**
	 * @return the firstDayOfWeek
	 */
	public Integer getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	/**
	 * @param firstDayOfWeek the firstDayOfWeek to set
	 */
	public void setFirstDayOfWeek(Integer firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}

	/**
	 * Validates all the times.
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		// Validate values
		for(int i=0; i < DayOfWeek.values().length; i++) {
			if(getWeekOperationTimeOpen() != null && getWeekOperationTimeOpen().length > i && !CustomValidators.isValidMilitaryTime(getWeekOperationTimeOpen()[i])) {
				addFieldError( "weekOperationTimeOpen", getText("error.storeoperations.hoursofoperation.opentime.invalid", new String[] {getWeekOperationTimeOpen()[i], getText("dayofweek." + i)}) );
			}
			if(getWeekOperationTimeClose() != null && getWeekOperationTimeClose().length > i && !CustomValidators.isValidMilitaryTime(getWeekOperationTimeClose()[i])) {
				addFieldError( "weekOperationTimeClose", getText("error.storeoperations.hoursofoperation.closetime.invalid", new String[] {getWeekOperationTimeClose()[i], getText("dayofweek." + i)}) );
			}
			if(getOpeningExtraHours() != null && getOpeningExtraHours().length > i) {
				String v = getOpeningExtraHours()[i];
				if(v != null) {
					int hours = NumberUtils.isValidInteger(v) ? NumberUtils.getIntegerValue(NumberUtils.stringToInteger(v)) : -1;
					if(hours < 0 || hours >= 24) {
						addFieldError( "openingExtraHours", getText("error.storeoperations.hoursofoperation.opening_extra_hours.invalid", new String[] {getOpeningExtraHours()[i], getText("dayofweek." + i)}) );
					}
				}
			}
			if(getClosingExtraHours() != null && getClosingExtraHours().length > i) {
				String v = getClosingExtraHours()[i];
				if(v != null) {
					int hours = NumberUtils.isValidInteger(v) ? NumberUtils.getIntegerValue(NumberUtils.stringToInteger(v)) : -1;
					if(hours < 0 || hours >= 24) {
						addFieldError( "closingExtraHours", getText("error.storeoperations.hoursofoperation.closing_extra_hours.invalid", new String[] {getClosingExtraHours()[i], getText("dayofweek." + i)}) );
					}
				}
			}
		}

		// Validate overlapping operation times only when all data is valid
		if(getFieldErrors().size() <= 0) {
			for(int i=0; i < DayOfWeek.values().length; i++) {
				if(!validDayStartEnd(DayOfWeek.values()[i])) {
					addFieldError( "weekOperationTimeOpen", getText("error.storeoperations.hoursofoperation.operation_time_collision.invalid", new String[] {getText("dayofweek." + i), getText("dayofweek." + (DayOfWeek.values()[i].getPreviousDayOfWeek().ordinal()))}) );
				}
				if(!validDayDuration(DayOfWeek.values()[i])) {
					addFieldError( "weekOperationTimeOpen", getText("error.storeoperations.hoursofoperation.operation_time_limit.invalid", new String[] {getText("dayofweek." + i)}));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private boolean validDayDuration(DayOfWeek day) {
		try {
			OperationTime today = buildOperationTime(day.ordinal());
			
			return !(today.getTotalOperationHours() > 24);
		} catch(Throwable t) {
			// If a value is null, validation is not required
			return true;
		}
	}
	
	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	private boolean validDayStartEnd(DayOfWeek day) {
		try {
			OperationTime today = buildOperationTime(day.ordinal());
			OperationTime yesterday = buildOperationTime(day.getPreviousDayOfWeek().ordinal());
			
			if(CalendarUtils.equalsTime(today.getStartHour(), yesterday.getEndHour())) {
				return true;
			} else if((!today.startsYesterday() && yesterday.endsTomorrow()) || (today.startsYesterday() && !yesterday.endsTomorrow())) {
				return CalendarUtils.equalsOrGreaterTime(today.getStartHour(), yesterday.getEndHour());
			} else {
				return CalendarUtils.equalsOrGreaterTime(yesterday.getEndHour(), today.getStartHour());
			}
		} catch(Throwable t) {
			// If a value is null, validation is not required
			return true;
		}
	}

	/**
	 * 
	 * @param dayIndex
	 * @return
	 */
	private OperationTime buildOperationTime(int dayIndex) {
		OperationTime opTime = new OperationTime();
		opTime.setDayOfWeekIndex(new Integer(dayIndex));
		opTime.setOpenHour(displayTimeToDate(weekOperationTimeOpen[dayIndex]));
		opTime.setCloseHour(displayTimeToDate(weekOperationTimeClose[dayIndex]));
		opTime.setOpeningExtraHours(NumberUtils.stringToInteger(openingExtraHours[dayIndex]));
		opTime.setClosingExtraHours(NumberUtils.stringToInteger(closingExtraHours[dayIndex]));
		
		return opTime;
	}
	

	
	/**
	 * 
	 * @return
	 */
	public String[] getClosingExtraHours() {
		return closingExtraHours;
	}

	/**
	 * 
	 * @param closingExtraHours
	 */
	public void setClosingExtraHours(String[] closingExtraHours) {
		this.closingExtraHours = closingExtraHours;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getOpeningExtraHours() {
		return openingExtraHours;
	}

	/**
	 * 
	 * @param openingExtraHours
	 */
	public void setOpeningExtraHours(String[] openingExtraHours) {
		this.openingExtraHours = openingExtraHours;
	}

}
