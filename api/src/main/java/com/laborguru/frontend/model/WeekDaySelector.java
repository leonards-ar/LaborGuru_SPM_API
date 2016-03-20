/*
 * File name: WeekDaySelector.java
 * Creation date: 10/08/2008 08:17:23
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.laborguru.model.DayOfWeek;
import com.laborguru.util.CalendarUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class WeekDaySelector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7529374667043414603L;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	private static final int DEFAULT_WEEKS_TO_SHOW = 3;
	
	private int weeksToShow = DEFAULT_WEEKS_TO_SHOW;

	private DayOfWeek startingDayOfWeek;

	private Date startingWeekDay;
	
	private Date selectedDay;
	
	
	/**
	 * 
	 */
	public WeekDaySelector() {
		this(null);
	}
	
	/**
	 * 
	 * @param startingDayOfWeek
	 */
	public WeekDaySelector(DayOfWeek startingDayOfWeek) {
		setStartingDayOfWeek(startingDayOfWeek != null ? startingDayOfWeek : DayOfWeek.MONDAY);
		Date today = CalendarUtils.todayWithoutTime();
		setStartingWeekDay(getFirstDayOfWeek(today));
		setSelectedDay(today);
	}
	
	/**
	 * @return the weeksToShow
	 */
	public int getWeeksToShow() {
		return weeksToShow;
	}

	/**
	 * @param weeksToShow the weeksToShow to set
	 */
	public void setWeeksToShow(int weeksToShow) {
		this.weeksToShow = weeksToShow;
	}

	/**
	 * @return the startingWeekDay
	 */
	public Date getStartingWeekDay() {
		return startingWeekDay;
	}

	/**
	 * @param startingWeekDay the startingWeekDay to set
	 */
	public void setStartingWeekDay(Date startingWeekDay) {
		this.startingWeekDay = startingWeekDay;
	}

	/**
	 * @return the selectedDay
	 */
	public Date getSelectedDay() {
		return selectedDay;
	}

	/**
	 * 
	 * @return
	 */
	public DayOfWeek getSelectedDayOfWeek() {
		return  CalendarUtils.getDayOfWeek(getSelectedDay());
	}
	
	/**
	 * @param selectedDay the selectedDay to set
	 */
	public void setSelectedDay(Date selectedDay) {
		this.selectedDay = selectedDay;
	}
	
	/**
	 * 
	 * @param startingWeekDay
	 * @param selectedDay
	 */
	public void initializeChangeDay(String startingWeekDay, String selectedDay) {
		setStringSelectedDay(selectedDay);
		setStringStartingWeekDay(startingWeekDay);
	}
	
	/**
	 * 
	 * @param startingWeekDay
	 * @param oldSelectedDay
	 */
	public void initializeChangeWeek(String startingWeekDay, String oldSelectedDay) {
		setStringStartingWeekDay(startingWeekDay);
		setStringSelectedDay(oldSelectedDay);
		DayOfWeek oldSelectedDayOfWeek = getSelectedDayOfWeek();
		DayOfWeek startingWeekDayDayOfWeek = getStartingDayOfWeek();
		int daysToAdd = oldSelectedDayOfWeek.ordinal() - startingWeekDayDayOfWeek.ordinal();
		daysToAdd = daysToAdd >=0 ? daysToAdd : daysToAdd + DayOfWeek.values().length;
		Date newSelectedDay = CalendarUtils.addOrSubstractDays(getStartingWeekDay(), daysToAdd);
		setSelectedDay(newSelectedDay);
	}
	
	/**
	 * 
	 * @param startingWeekDay
	 */
	public void setStringStartingWeekDay(String startingWeekDay) {
		try {
			setStartingWeekDay(DATE_FORMAT.parse(startingWeekDay));
		} catch (Throwable e) {
			setStartingWeekDay(getFirstDayOfWeek(CalendarUtils.todayWithoutTime()));
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStringStartingWeekDay() {
		if(getStartingWeekDay() != null) {
			return DATE_FORMAT.format(getStartingWeekDay());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param startingWeekDay
	 */
	public void setStringSelectedDay(String selectedDay) {
		try {
			setSelectedDay(DATE_FORMAT.parse(selectedDay));
		} catch (Throwable e) {
			setSelectedDay(CalendarUtils.todayWithoutTime());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStringSelectedDay() {
		if(getSelectedDay() != null) {
			return DATE_FORMAT.format(getSelectedDay());
		} else {
			return null;
		}
	}

	/**
	 * @return the startingDayOfWeek
	 */
	public DayOfWeek getStartingDayOfWeek() {
		return startingDayOfWeek;
	}

	/**
	 * @param startingDayOfWeek the startingDayOfWeek to set
	 */
	public void setStartingDayOfWeek(DayOfWeek startingDayOfWeek) {
		this.startingDayOfWeek = startingDayOfWeek;
	}
	
	/**
	 * 
	 * @param d
	 */
	public void updateStartingWeekDay(Date d) {
		setStartingWeekDay(getFirstDayOfWeek(d));
	}
	

	
	/**
	 * 
	 * @return
	 */
	public List<String> getPreviousStringStartingWeekDays() {
		return getStringFormattedList(getPreviousStartingWeekDays());

	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getNextStringStartingWeekDays() {
		return getStringFormattedList(getNextStartingWeekDays());
	}
	
	/**
	 * 
	 * @param dates
	 * @return
	 */
	private List<String> getStringFormattedList(List<Date> dates) {
		if(dates != null) {
			List<String> formattedDates = new ArrayList<String>(dates.size());
			
			for(Date d : dates) {
				formattedDates.add(DATE_FORMAT.format(d));
			}
			
			return formattedDates;
		} else {
			return new ArrayList<String>();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Date> getPreviousStartingWeekDays() {
		List<Date> startingWeekDays = new ArrayList<Date>(getWeeksToShow());
		
		for(int i = getWeeksToShow(); i > 0; i--) {
			startingWeekDays.add(CalendarUtils.addOrSubstractDays(getStartingWeekDay(), -1 * DayOfWeek.values().length * i));
		}
		
		return startingWeekDays;
	}
	
	/**
	 * 
	 * @param d
	 * @param startingDayOfWeek
	 * @return
	 */
	public Date getFirstDayOfWeek(Date d) {
		DayOfWeek dayOfWeek = CalendarUtils.getDayOfWeek(d);
		Calendar day = CalendarUtils.getCalendar(d);
		int daysTosubstract = 0;
		
		if(dayOfWeek != null && day != null) {
			
			if (dayOfWeek.ordinal() < getStartingDayOfWeek().ordinal()){
				daysTosubstract = 7 - (getStartingDayOfWeek().ordinal() - dayOfWeek.ordinal());
			} else if (dayOfWeek.ordinal() > getStartingDayOfWeek().ordinal()){
				daysTosubstract = dayOfWeek.ordinal() - getStartingDayOfWeek().ordinal();
			} 			
			
			day.add(Calendar.DAY_OF_MONTH, -1 * daysTosubstract);
			return day.getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getPreviousStartingWeekDay() {
		return CalendarUtils.addOrSubstractDays(getStartingWeekDay(), -1 * DayOfWeek.values().length);
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getNextStartingWeekDay() {
		return CalendarUtils.addOrSubstractDays(getStartingWeekDay(), DayOfWeek.values().length);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Date> getNextStartingWeekDays() {
		List<Date> startingWeekDays = new ArrayList<Date>(getWeeksToShow());
		
		for(int i = 1; i <= getWeeksToShow(); i++) {
			startingWeekDays.add(CalendarUtils.addOrSubstractDays(getStartingWeekDay(), DayOfWeek.values().length * i));
		}
		
		return startingWeekDays;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getStringWeekDays() {
		return getStringFormattedList(getWeekDays());
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Date> getWeekDays() {
		List<Date> weekDays = new ArrayList<Date>(DayOfWeek.values().length);
		
		for(int i = 0; i < DayOfWeek.values().length; i++) {
			weekDays.add(CalendarUtils.addOrSubstractDays(getStartingWeekDay(), i));
		}
		
		return weekDays;
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Date getAsDate(String d) {
		try {
			return DATE_FORMAT.parse(d);
		} catch(Throwable ex) {
			return CalendarUtils.todayWithoutTime();
		}
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public boolean isSelectedWeekDay(Date d) {
		return getStringSelectedDay() != null && d != null && getStringSelectedDay().equals(DATE_FORMAT.format(d));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSelectedDateBeforeToday() {
		Date today = CalendarUtils.todayWithoutTime();
		return !getSelectedDay().after(today);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSelectedWeekBeforeTodaysWeek() {
		Date today = CalendarUtils.todayWithoutTime();
		return getStartingWeekDay().before(getFirstDayOfWeek(today));
	}
}
