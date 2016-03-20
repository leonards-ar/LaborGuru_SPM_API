package com.laborguru.service.actualhours.dao;

import java.util.Date;

import com.laborguru.model.ActualHours;
import com.laborguru.model.Store;

public interface ActualHoursDao {

	/**
	 * 
	 * @param objectToPersist
	 * @return
	 */
	ActualHours getActualHoursByDateAndStore(ActualHours objectToPersist);

	/**
	 * 
	 * @param actualHoursAux
	 * @return
	 */
	ActualHours saveOrUpdate(ActualHours actualHoursAux);
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Double getTotalActualHoursForTimePeriod(Store store, Date startDate, Date endDate);
}
