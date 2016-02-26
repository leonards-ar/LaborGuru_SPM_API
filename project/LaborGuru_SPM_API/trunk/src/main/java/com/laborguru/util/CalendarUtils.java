/*
 * File name: CalendarUtils.java
 * Creation date: Sep 20, 2008 9:10:37 PM
 * Copyright Mindpool
 */
package com.laborguru.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.laborguru.model.DayOfWeek;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class CalendarUtils {

	private final static Logger log = Logger.getLogger(CalendarUtils.class);

	private final static Pattern INPUT_TIME_REGEXP[] = {Pattern.compile("^\\s*(\\d{1})(\\d\\d){1}\\s*([aApP][mM]*)*\\s*$"), Pattern.compile("^\\s*(\\d\\d){1}(\\d\\d){1}\\s*([aApP][mM]*)*\\s*$"), Pattern.compile("^\\s*(\\d*)[:.]*(\\d\\d)*\\s*([aApP][mM]*)*\\s*$")};

	private final static Date MIDNIGHT_TIME = todayWithoutTime();
	
	private final static String DEFAULT_ZERO_TIME = "00:00";
	
	/**
	 * 
	 */
	private CalendarUtils() {
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date d) {
		if(d != null) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(d);
			return cal;
		} else {
			return null;
		}			
	}
	
	/**
	 * 
	 * @param d
	 * @param daysToAddOrSubstract
	 * @return
	 */
	public static Date addOrSubstractDays(Date d, int daysToAddOrSubstract) {
		Calendar cal = CalendarUtils.getCalendar(d);
		cal.add(Calendar.DAY_OF_MONTH, daysToAddOrSubstract);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param d
	 * @param hoursToAddOrSubstract
	 * @return
	 */
	public static Date addOrSubstractHours(Date d, int hoursToAddOrSubstract) {
		if(d != null) {
			Calendar cal = CalendarUtils.getCalendar(d);
			cal.add(Calendar.HOUR_OF_DAY, hoursToAddOrSubstract);
			return cal.getTime();		
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param d
	 * @param minutesToAddOrSubstract
	 * @return
	 */
	public static Date addOrSubstractMinutes(Date d, int minutesToAddOrSubstract) {
		Calendar cal = CalendarUtils.getCalendar(d);
		cal.add(Calendar.MINUTE, minutesToAddOrSubstract);
		return cal.getTime();		
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static DayOfWeek getDayOfWeek(Date d) {
		Calendar cal = CalendarUtils.getCalendar(d);
		if(cal != null) {
			int dof = cal.get(Calendar.DAY_OF_WEEK);
			return DayOfWeek.values()[dof - 1];
		} else {
			return null;
		}		
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static boolean isWeekendDay(Date d) {
		DayOfWeek dof = getDayOfWeek(d);
		return dof != null ? dof.equals(DayOfWeek.SATURDAY) || dof.equals(DayOfWeek.SUNDAY): false;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date todayWithoutTime() {
		return removeTimeFromDate(new Date());
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static boolean isAfterToday(Date d) {
		if(d != null) {
			return removeTimeFromDate(d).after(todayWithoutTime()); 
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if date1 is greater or equal than date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean equalsOrGreaterDate(Date date1, Date date2) {
		try {
			long d1 = removeTimeFromDate(date1).getTime();
			long d2 = removeTimeFromDate(date2).getTime();
			
			return d1 >= d2;
		} catch(Throwable ex) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Date removeTimeFromDate(Date d) {
		try {
			if(d != null) {
				String str = SpmConstants.REMOVE_TIME_FORMAT.format(d);
				return SpmConstants.REMOVE_TIME_FORMAT.parse(str);
			} else { 
				return null;
			}
		} catch(ParseException ex) {
			// Should never happen
			return null;
		}
		
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Date removeDateFromTime(Date time) {
		try {
			String str = SpmConstants.REMOVE_DATE_FORMAT.format(time);
			return SpmConstants.REMOVE_DATE_FORMAT.parse(str);
		} catch(Throwable ex) {
			// Null or invalid time
			return null;
		}		
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Date displayTimeToDate(String time) {
		// Display time can now be different to HH:mm
		return inputTimeToDate(time);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Date inputTimeToDate(String time) {
		if(time == null || time.trim().length() <= 0) {
			return null;
		} else {
			try {
				Matcher m = null;
				int i = 0;
				do {
					m = INPUT_TIME_REGEXP[i].matcher(time);
					i++;
				} while(!m.matches() && i < INPUT_TIME_REGEXP.length);

				if(m != null && m.matches()) {
					return SpmConstants.TIME_FORMAT.parse(getTime(m.group(1), m.group(2), m.group(3)));
				} else {
					return null;
				}			
			} catch (Throwable ex) {
				log.error("Cannot parse date/time [" + time + "]", ex);
				return null;
			}
		}
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String inputTimeToMilitaryTime(String time) {
		if(time == null || time.trim().length() <= 0) {
			return null;
		} else {
			try {
				Matcher m = null;
				int i = 0;
				do {
					m = INPUT_TIME_REGEXP[i].matcher(time);
					i++;
				} while(!m.matches() && i < INPUT_TIME_REGEXP.length);

				if(m != null && m.matches()) {
					return getTime(m.group(1), m.group(2), m.group(3));
				} else {
					return null;
				}			
			} catch (Throwable ex) {
				log.error("Cannot parse date/time [" + time + "]", ex);
				return null;
			}
		}
	}
	
	/**
	 * 
	 * @param dayPart
	 * @return
	 */
	private static boolean isPM(String dayPart) {
		return dayPart != null && (dayPart.equalsIgnoreCase("pm") || dayPart.equalsIgnoreCase("p"));
	}

	/**
	 * 
	 * @param dayPart
	 * @return
	 */
	private static boolean isAM(String dayPart) {
		return dayPart != null && (dayPart.equalsIgnoreCase("am") || dayPart.equalsIgnoreCase("a"));
	}
	
	/**
	 * 
	 * @param hours
	 * @param minutes
	 * @param dayPart
	 * @return
	 */
	private static String getTime(String hours, String minutes, String dayPart) {
		int hs = Integer.parseInt(hours != null ? hours : "0");
		int mins = Integer.parseInt(minutes != null ? minutes : "0");
		if(dayPart != null && isAM(dayPart) && hs == 12) {
			hs = 0;
		} else if (dayPart != null && isPM(dayPart) && hs < 12) {
			hs += 12;
		}
		return formatTimeComponent(hs) + ":" + formatTimeComponent(mins);
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String dateToDisplayTime(Date time) {
		if(time != null) {
			String displayTime = SpmConstants.DISPLAY_TIME_FORMAT.format(time);
			return displayTime.replaceAll(":00", "").toLowerCase().replaceAll("m", "");
			//return SpmConstants.TIME_FORMAT.format(time);
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Date stringToDate(String time, String format) {
		try {
			return new SimpleDateFormat(format).parse(time);
		} catch (ParseException ex) {
			log.error("Cannot parse date/time [" + time + "]", ex);
			return null;
		}
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String dateToString(Date time, String format) {
			return new SimpleDateFormat(format).format(time);
	}
	
	/**
	 * Returns if time1 is greater or equal than time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean equalsOrGreaterTime(Date time1, Date time2) {
		try {
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time1));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time2));
			
			return t1 >= t2;
		} catch(Throwable ex) {
			return false;
		}
	}

	/**
	 * Returns if time1 is equal to time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean equalsTime(Date time1, Date time2) {
		try {
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time1));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time2));
			
			return t1 == t2;
		} catch(Throwable ex) {
			return false;
		}
	}

	
	/**
	 * Returns if time1 is smaller or equal than time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean equalsOrSmallerTime(Date time1, Date time2) {
		try {
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time1));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time2));
			
			return t1 <= t2;
		} catch(Throwable ex) {
			return false;
		}
	}
	
	/**
	 * Returns if time is greater or equal than startTime and less or equal than endTime
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean inRangeIncludingEndTime(Date time, Date startTime, Date endTime) {
		try {
			long t = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time));
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(startTime));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(endTime));
			
			if(t1 <= t2) {
				return t >= t1 && t <= t2;
			} else {
				// Multi day range
				final long midnight = 2400L;
				return t >= t1 && t < midnight || t <= t2;
			}
		} catch(Throwable ex) {
			return false;
		}		
	}
	
	/**
	 * Returns if time is greater or equal than startTime and less than endTime
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean inRangeNotIncludingEndTime(Date time, Date startTime, Date endTime) {
		try {
			long t = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time));
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(startTime));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(endTime));
			
			if(t1 <= t2) {
				return t >= t1 && t < t2;
			} else {
				// Multi day range
				final long midnight = 2400L;
				return t >= t1 && t < midnight || t < t2;
			}
		} catch(Throwable ex) {
			return false;
		}		
	}
	
	/**
	 * Returns if time is greater than startTime and less or equal than endTime
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean inRangeNotIncludingStartTime(Date time, Date startTime, Date endTime) {
		try {
			long t = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time));
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(startTime));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(endTime));
			
			if(t1 <= t2) {
				return t > t1 && t <= t2;
			} else {
				// Multi day range
				final long midnight = 2400L;
				return t > t1 && t < midnight || t <= t2;
			}
		} catch(Throwable ex) {
			return false;
		}		
	}
	
	/**
	 * 
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	public static boolean isOverlappingTimePeriod(Date startTime1, Date endTime1, Date startTime2, Date endTime2) {
		return inRangeNotIncludingEndTime(startTime1, startTime2, endTime2) || inRangeNotIncludingStartTime(endTime1, startTime2, endTime2) || inRangeNotIncludingEndTime(startTime2, startTime1, endTime1) || inRangeNotIncludingStartTime(endTime2, startTime1, endTime1);
	}
	
	/**
	 * Returns if time1 is smaller than time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean smallerTime(Date time1, Date time2) {
		try {
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time1));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time2));
			
			return t1 < t2;
		} catch(Throwable ex) {
			return false;
		}
	}
	
	/**
	 * Returns if time1 is greater than time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean greaterTime(Date time1, Date time2) {
		try {
			long t1 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time1));
			long t2 = Long.parseLong(SpmConstants.TIME_NUMBER_FORMAT.format(time2));
			
			return t1 > t2;
		} catch(Throwable ex) {
			return false;
		}
	}
	

	/**
	 * This method returns a Calendar that represents this week's weekday taking as example the date's weekday passed as parameter.
	 * For example: If we passed Monday 20/10/2008 as parameter, the method returns this week monday.
	 * 
	 * @param auxSelectedDate
	 * @return
	 */
	public static Calendar getDayOfThisWeek(Date auxSelectedDate) {
		DayOfWeek dayOfWeekCalculatedDay = CalendarUtils.getDayOfWeek(auxSelectedDate);

		Date today = CalendarUtils.todayWithoutTime();
		DayOfWeek dayOfWeek = CalendarUtils.getDayOfWeek(today);
		
		Calendar day = CalendarUtils.getCalendar(today);
		int daysTosubstract = 0;
					
		if (dayOfWeek.ordinal() < dayOfWeekCalculatedDay.ordinal()){
			daysTosubstract = 7 - (dayOfWeekCalculatedDay.ordinal() - dayOfWeek.ordinal());
		} else if (dayOfWeek.ordinal() > dayOfWeekCalculatedDay.ordinal()){
			daysTosubstract = dayOfWeek.ordinal() - dayOfWeekCalculatedDay.ordinal();
		} 			
			
		day.add(Calendar.DAY_OF_MONTH, -1 * daysTosubstract);
		return day;
	}

	/**
	 * Get the date for the specific week that starts on initialDateOfWeek
	 * @param initialDateOfWeek
	 * @param dayOfWeekToCalculate
	 * @return
	 */
	public static Date getDayOfTheWeek(Date initialDateOfWeek, DayOfWeek dayOfWeekToCalculate) {
		
		int initialDayOrdinal = CalendarUtils.getDayOfWeek(initialDateOfWeek).ordinal();				
		int dayToCalculateOrdinal = dayOfWeekToCalculate.ordinal();				
		
		int daysToAdd = 0;
					
		if (initialDayOrdinal < dayToCalculateOrdinal){
			daysToAdd = dayToCalculateOrdinal - initialDayOrdinal;
		} else if (initialDayOrdinal > dayToCalculateOrdinal){
			daysToAdd = 7 - (initialDayOrdinal - dayToCalculateOrdinal);
		} 
		
		Calendar initialDay = CalendarUtils.getCalendar(initialDateOfWeek);	
		initialDay.add(Calendar.DAY_OF_MONTH, daysToAdd);
		
		return initialDay.getTime();
	}

	/**
	 * 
	 * @param minutes
	 * @return
	 */
	public static String minutesToTime(Integer minutes) {
		if(minutes != null) {
			int hs = (int) (minutes.intValue() / 60);
			int mins = minutes.intValue() % 60;
			return formatTimeComponent(hs) + ":" + formatTimeComponent(mins);
		} else {
			return DEFAULT_ZERO_TIME;
		}
	}
	
	/**
	 * 
	 * @param hours
	 * @return
	 */
	public static Integer hoursToMinutes(Double hours) {
		Integer minutes;
		if(hours != null) {
			minutes = new Integer((int)(hours.doubleValue() * 60));
		} else {
			minutes = new Integer(0); 
		}
		return minutes;
	}
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	private static int timeToMinutes(Date t) {
		int hs = Integer.parseInt(SpmConstants.TIME_HOUR_FORMAT.format(t));
		int mins = Integer.parseInt(SpmConstants.TIME_MINUTE_FORMAT.format(t));
		return (hs * 60) + mins;
	}
	
	/**
	 * 
	 * @param date1 The end time
	 * @param date2 The start time
	 * @return
	 */
	public static Double differenceInHours(Date date1, Date date2) {
		int t1 = date1 != null ? timeToMinutes(date1) : 0;
		int t2 = date2 != null ? timeToMinutes(date2) : 0;
		if(t1 > t2 || (t1 == 0 && t2 == 0)) {
			return new Double((t1 - t2) / 60D);
		} else {
			final int ONE_DAY_MINUTES = 24 * 60;
			return new Double( (ONE_DAY_MINUTES + (t1 - t2)) / 60D);
		}
	}
	
	/**
	 * 
	 * @param hours
	 * @return
	 */
	public static String hoursToTime(Double hours) {
		return minutesToTime(hoursToMinutes(hours));
	}
	
	/**
	 * 
	 * @param hours
	 * @param zeroText
	 * @return
	 */
	public static String hoursToTime(Double hours, String zeroText) {
		String time = hoursToTime(hours);
		return DEFAULT_ZERO_TIME.equals(time) ? zeroText : time;
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	private static String formatTimeComponent(int n) {
		if(n >= 0 && n < 10) {
			return "0" + n;
		} else {
			return String.valueOf(n);
		}
	}

	/**
	 * 
	 * @return
	 */
	public static Date getMidnightTime() {
		return MIDNIGHT_TIME;
	}
	
	/**
	 * 
	 * @param endingDayOfWeek
	 * @return
	 */
	public static Date getEndOfWeekDay(Date startingDayOfWeek) {
		return addOrSubstractDays(startingDayOfWeek, DayOfWeek.values().length - 1);
	}
	
	/**
	 * Adds hour and minutes to a date.
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date setTimeToDate(Date date, Date time){
		Calendar calendarDate = CalendarUtils.getCalendar(date);
		Calendar calendarTime = CalendarUtils.getCalendar(time);		

		calendarDate.set(Calendar.HOUR, calendarTime.get(Calendar.HOUR_OF_DAY));
		calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
		
		return calendarDate.getTime();
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static boolean isMidnightTime(Date d) {
		return equalsTime(d, getMidnightTime());
	}
	
	public static Date roundHalfHourUp(Date time) {
		
		Calendar calendarTime = CalendarUtils.getCalendar(time);
		
		int minutes = calendarTime.get(Calendar.MINUTE);
		if(minutes < 30) {
			calendarTime.set(Calendar.MINUTE, 30);
		} else {
			calendarTime.add(Calendar.HOUR_OF_DAY, 1);
			calendarTime.set(Calendar.MINUTE, 0);
		}
		
		return calendarTime.getTime();
	}
}
