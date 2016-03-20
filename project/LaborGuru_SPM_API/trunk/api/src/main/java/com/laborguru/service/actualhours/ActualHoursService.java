package com.laborguru.service.actualhours;

import java.util.Date;

import com.laborguru.model.ActualHours;
import com.laborguru.model.Store;
import com.laborguru.service.actualhours.dao.ActualHoursDao;

public interface ActualHoursService {

	/**
	 * 
	 * @param objectToPersist
	 * @return
	 */
	ActualHours saveOrUpdate(ActualHours objectToPersist);
	
	/**
	 * 
	 * @param actualHoursDao
	 */
	void setActualHoursDao(ActualHoursDao actualHoursDao);

	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	ActualHours getActualHoursByDate(Store store, Date date);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Double getTotalActualHoursForTimePeriod(Store store, Date startDate, Date endDate);
}
