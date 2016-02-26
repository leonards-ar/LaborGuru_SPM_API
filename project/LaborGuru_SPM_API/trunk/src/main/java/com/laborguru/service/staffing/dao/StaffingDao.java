/*
 * File name: StaffingDao.java
 * Creation date: 19/10/2008 16:55:31
 * Copyright Mindpool
 */
package com.laborguru.service.staffing.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.laborguru.model.DailyProjectedStaffing;
import com.laborguru.model.Position;
import com.laborguru.model.Store;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface StaffingDao {

	/**
	 * 
	 * @param position
	 * @param date
	 * @return
	 */
	DailyProjectedStaffing getDailyStaffingByDate(Position position, Date date);
	
	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 */
	DailyProjectedStaffing save(DailyProjectedStaffing dailyStaffing);	
	
	/**
	 * 
	 * @param storeDailyStaffing
	 */
	void deleteAll(List<DailyProjectedStaffing> storeDailyStaffing);	
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	List<DailyProjectedStaffing> getStoreDailyStaffingByDate(Store store, Date date);
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	List<DailyProjectedStaffing> getStoreDailyStaffingFromDate(Store store, Date date);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Double getTotalProjectedStaffingForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<Integer, Double> getTotalProjectedStaffingByPositionForTimePeriod(Store store, Date startDate, Date endDate);
	
	/**
	 * 
	 * @param store
	 * @param date
	 */
	void deleteStoreDailyStaffingByDate(Store store, Date date);	
}
