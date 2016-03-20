package com.laborguru.frontend.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.laborguru.model.DayOfWeek;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 * Weekday selector Test Class
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class WeekDaySelectorTest {

	@Test
	public void getFirstDayOfWeekBeforeInitDay(){
		WeekDaySelector weekDaySelector = new WeekDaySelector(DayOfWeek.WEDNESDAY);
		Date tuesday = CalendarUtils.stringToDate("07/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		Date wednesday = CalendarUtils.stringToDate("01/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		
		Date testRet = weekDaySelector.getFirstDayOfWeek(tuesday);
		
		assertEquals("The dates are not equals", wednesday, testRet);		
	}
	
	@Test
	public void getFirstDayOfWeekHappyPathSameInitDay(){
		WeekDaySelector weekDaySelector = new WeekDaySelector(DayOfWeek.WEDNESDAY);
		Date wednesday = CalendarUtils.stringToDate("01/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		Date wednesdayResponse = CalendarUtils.stringToDate("01/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		
		Date testRet = weekDaySelector.getFirstDayOfWeek(wednesday);
		
		assertEquals("The dates are not equals", wednesdayResponse, testRet);		
	}
	
	@Test
	public void getFirstDayOfWeekHappyPathAfterInitDay(){
		WeekDaySelector weekDaySelector = new WeekDaySelector(DayOfWeek.WEDNESDAY);
		Date sunday = CalendarUtils.stringToDate("12/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		Date wednesdayResponse = CalendarUtils.stringToDate("08/10/2008", SpmConstants.DATE_FORMAT.toPattern());
		
		Date testRet = weekDaySelector.getFirstDayOfWeek(sunday);
		
		assertEquals("The dates are not equals", wednesdayResponse, testRet);		
	}
}
