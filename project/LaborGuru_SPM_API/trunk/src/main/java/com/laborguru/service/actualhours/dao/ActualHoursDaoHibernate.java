package com.laborguru.service.actualhours.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.laborguru.model.ActualHours;
import com.laborguru.model.Store;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;

public class ActualHoursDaoHibernate extends SpmHibernateDao implements ActualHoursDao {

	private static final Logger log = Logger.getLogger(ActualHoursDaoHibernate.class);
	private static final String ACTUAL_HOURS_NULL = "The actual hours object passed in as parameter is null";
	private static final String ACTUAL_HOURS_SEARCH_PARAMETERS_NULL = "The search parameters (date, store id) passed in as parameter are null";

	/**
	 * 
	 * @param ah
	 * @return
	 * @see com.laborguru.service.actualhours.dao.ActualHoursDao#saveOrUpdate(com.laborguru.model.ActualHours)
	 */
	public ActualHours saveOrUpdate(ActualHours ah) {
		
		if (ah == null){
			log.error(ACTUAL_HOURS_NULL);
			throw new IllegalArgumentException(ACTUAL_HOURS_NULL);			
		}
		
		getHibernateTemplate().saveOrUpdate(ah);
		
		return ah;
	}
	
	/**
	 * 
	 * @param ah
	 * @return
	 * @see com.laborguru.service.actualhours.dao.ActualHoursDao#getActualHoursByDateAndStore(com.laborguru.model.ActualHours)
	 */
	public ActualHours getActualHoursByDateAndStore(ActualHours ah) {

		if (ah == null){
			log.error(ACTUAL_HOURS_NULL);
			throw new IllegalArgumentException(ACTUAL_HOURS_NULL);			
		}

		
		Date date = ah.getDate();
		Integer storeId = ah.getStoreId();
		
		if (date == null || storeId == null){
			log.error(ACTUAL_HOURS_SEARCH_PARAMETERS_NULL);
			throw new IllegalArgumentException(ACTUAL_HOURS_SEARCH_PARAMETERS_NULL);
		}
		
		DateTime dateTime = new DateTime(date);
		
		List<ActualHours> actualHours = (List<ActualHours>)getHibernateTemplate().findByNamedParam(
				"from ActualHours actualHours where actualHours.store.id = :storeId and DATE(actualHours.date) = STR_TO_DATE(:date,'%Y-%m-%d')", 
				new String []{"storeId", "date"}, new Object[]{storeId, dateTime.toString("yyyy-MM-dd")});
		
		if (actualHours.isEmpty()){
			return null;
		}
		
		return actualHours.get(0);
	}
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.actualhours.dao.ActualHoursDao#getTotalActualHoursForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Double getTotalActualHoursForTimePeriod(Store store, Date startDate, Date endDate) {
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total actual hours for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<Double> totalResult = getHibernateTemplate().findByNamedParam("select sum(ah.hours) from ActualHours ah " +
				"where ah.store.id=:storeId AND ah.date >= :startDate AND ah.date <= :endDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total actual hours for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		return totalResult != null && totalResult.size() > 0 && totalResult.get(0) != null ? totalResult.get(0) : new Double(0.0);
	}	
}
