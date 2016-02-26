package com.laborguru.service.actualhours;

import java.util.Date;

import com.laborguru.model.ActualHours;
import com.laborguru.model.Store;
import com.laborguru.service.actualhours.dao.ActualHoursDao;

public class ActualHoursServiceBean implements ActualHoursService{

	private ActualHoursDao actualHoursDao;
	
	/**
	 * 
	 * @param objectToPersist
	 * @return
	 * @see com.laborguru.service.actualhours.ActualHoursService#saveOrUpdate(com.laborguru.model.ActualHours)
	 */
	public ActualHours saveOrUpdate(ActualHours objectToPersist){
		
		if (objectToPersist == null){
			throw new IllegalArgumentException("Object passed as parameter is null");
		}
		
		ActualHours actualHoursAux = actualHoursDao.getActualHoursByDateAndStore(objectToPersist);
		ActualHours actualHoursResponse = null;
		
		if (actualHoursAux != null){
			actualHoursAux.setHours(objectToPersist.getHours());
			actualHoursResponse = actualHoursDao.saveOrUpdate(actualHoursAux);
		}else{
			actualHoursResponse = actualHoursDao.saveOrUpdate(objectToPersist);
		}
		
		return actualHoursResponse;
	}

	/**
	 * @param actualHoursDao the actualHoursDao to set
	 */
	public void setActualHoursDao(ActualHoursDao actualHoursDao) {
		this.actualHoursDao = actualHoursDao;
	}

	/**
	 * @return the actualHoursDao
	 */
	public ActualHoursDao getActualHoursDao() {
		return actualHoursDao;
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.actualhours.ActualHoursService#getActualHoursByDate(com.laborguru.model.Store, java.util.Date)
	 */
	public ActualHours getActualHoursByDate(Store store, Date date) {
		ActualHours ah = new ActualHours();
		ah.setDate(date);
		ah.setStore(store);
		return getActualHoursDao().getActualHoursByDateAndStore(ah);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.actualhours.ActualHoursService#getTotalActualHoursForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Double getTotalActualHoursForTimePeriod(Store store, Date startDate, Date endDate) {
		return getActualHoursDao().getTotalActualHoursForTimePeriod(store, startDate, endDate);
	}

}
