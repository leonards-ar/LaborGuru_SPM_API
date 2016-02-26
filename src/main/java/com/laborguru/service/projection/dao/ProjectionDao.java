package com.laborguru.service.projection.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.laborguru.model.DailyProjection;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.Store;

public interface ProjectionDao {
	
	/**
	 * 
	 * @param numberOfWeeks
	 * @param store
	 * @param startWeekDate
	 * @return
	 */
	List<BigDecimal> getAvgDailyProjectionForAWeek(Integer numberOfWeeks, Store store, Date startWeekDate);

	/**
	 * 
	 * @param store
	 * @param startWeekDate
	 * @return
	 */
	List<DailyProjection> getAdjustedDailyProjectionForAWeek(Store store, Date startWeekDate);
	
	/**
	 * 
	 * @param store
	 * @param selectedDate
	 * @return
	 */
	DailyProjection getDailyProjection(Store store, Date selectedDate);
	
	/**
	 * 
	 * @param numberOfWeeks
	 * @param store
	 * @param selectedDate
	 * @return
	 */
	List<HalfHourProjection> getAvgHalfHourProjection(Integer numberOfWeeks, Store store, Date selectedDate);

	/**
	 * 
	 * @param projection
	 */
	void save(DailyProjection projection);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<DailyProjection> getDailyProjections(Store store, Date startDate, Date endDate);

	/**
	 * 
	 * @return
	 */
	List<DailyProjection> loadAll();
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	List<DailyProjection> loadAll(Date date);
	
}
