/*
 * File name: StaffingService.java
 * Creation date: 19/10/2008 16:59:08
 * Copyright Mindpool
 */
package com.laborguru.service.staffing;

import java.util.Date;
import java.util.Map;

import com.laborguru.model.DailySalesValue;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyFlashStaffing;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.model.StoreDailyStaffing;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface StaffingService extends Service {

	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	StoreDailyStaffing getDailyStaffingByDate(Store store, Date date);
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @param dailySalesValue
	 * @return
	 */
	StoreDailyFlashStaffing getDailyFlashSalesStaffingByDate(Store store, Date date, DailySalesValue dailySalesValue);
	
	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 */
	StoreDailyStaffing save(StoreDailyStaffing dailyStaffing);
	
	/**
	 * 
	 * @param store
	 * @param date
	 */
	void updateDailyStaffingForDate(Store store, Date date);	
	
	/**
	 * 
	 * @param store
	 * @param date
	 */
	void deleteDailyStaffingForDate(Store store, Date date);	
	
	/**
	 * 
	 * @param store
	 * @param date
	 */
	void deleteDailyStaffingFromDate(Store store, Date date);
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	StoreDailyHistoricSalesStaffing getDailyHistoricSalesStaffingByDate(Store store, Date date);
	
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
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Double getTotalProjectedStaffingForTimePeriod(Store store, Date startDate, Date endDate);
}
