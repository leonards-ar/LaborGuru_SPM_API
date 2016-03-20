/*
 * File name: ScheduleService.java
 * Creation date: Oct 4, 2008 3:56:19 PM
 * Copyright Mindpool
 */
package com.laborguru.service.schedule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.laborguru.model.Store;
import com.laborguru.model.StoreSchedule;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ScheduleService extends Service {

	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	StoreSchedule getStoreScheduleByDate(Store store, Date date);
	
	/**
	 * 
	 * @param schedule
	 * @return
	 */
	StoreSchedule save(StoreSchedule schedule);
	
	/**
	 * 
	 * @param schedule
	 * @return
	 */
	StoreSchedule detach(StoreSchedule schedule);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	BigDecimal getTotalScheduledHoursForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	BigDecimal getTotalScheduledLaborCostForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<Integer, BigDecimal> getTotalScheduledHoursByPositionForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 */
	void updateAllStoreSchedules();
	
	/**
	 * 
	 * @param schedule
	 */
	void evict(StoreSchedule schedule);
	
	/**
	 * 
	 * @param sourceSchedule
	 * @param date
	 */
	StoreSchedule copyScheduleTo(StoreSchedule sourceSchedule, Date date, StoreSchedule destinationNextDaySchedule);
}
 