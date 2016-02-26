/*
 * File name: ScheduleDaoHibernate.java
 * Creation date: Oct 4, 2008 4:08:38 PM
 * Copyright Mindpool
 */
package com.laborguru.service.schedule.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.Store;
import com.laborguru.model.StoreSchedule;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleDaoHibernate extends HibernateDaoSupport implements ScheduleDao {
	private static final Logger log = Logger.getLogger(ScheduleDaoHibernate.class);	

	private static final String STORE_NULL = "The store passed in as parameter is null";
	/**
	 * 
	 */
	public ScheduleDaoHibernate() {
	}

	/**
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#getStoreScheduleByDate(com.laborguru.model.Store, java.util.Date)
	 */
	public StoreSchedule getStoreScheduleByDate(Store store, Date date) {
		date = CalendarUtils.removeTimeFromDate(date);
		if(log.isDebugEnabled()) {
			log.debug("Searching schedule for day [" + date + "] for store [" + store + "]");
		}
		
		List<StoreSchedule> schedules = (List<StoreSchedule>) getHibernateTemplate().findByNamedParam("from StoreSchedule schedule where schedule.store.id = :storeId and schedule.day = :day", new String[]{"storeId", "day"}, new Object[] {store.getId(),date});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (schedules != null ? schedules.size() : "null") + "] schedules for day [" + date + "] and store [" + store + "]");
		}
		
		if(schedules.size() > 0) {
			log.warn("Found [" + (schedules != null ? schedules.size() : "null") + "] schedules for day [" + date + "] and store [" + store + "]");
		}
		
		return schedules != null && schedules.size() > 0 ? schedules.get(0) : null;
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#getAllStoreSchedules()
	 */
	public List<StoreSchedule> getAllStoreSchedules() {
		if(log.isDebugEnabled()) {
			log.debug("Searching all store schedules");
		}
		
		List<StoreSchedule> schedules = (List<StoreSchedule>) getHibernateTemplate().find("from StoreSchedule schedule");
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (schedules != null ? schedules.size() : "null") + "] schedules");
		}
		
		return schedules;		
	}
	
	/**
	 * @param schedule
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#save(com.laborguru.model.StoreSchedule)
	 */
	public StoreSchedule save(StoreSchedule schedule) {
		if (schedule == null){
			throw new IllegalArgumentException("The schedule passed as parameter is null");
		}
		
		getHibernateTemplate().saveOrUpdate(schedule);
		
		return schedule;
	}

	/**
	 * 
	 * @param schedule
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#detach(com.laborguru.model.StoreSchedule)
	 */
	public StoreSchedule detach(StoreSchedule schedule) {
		getHibernateTemplate().evict(schedule);
		return schedule;
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#getTotalScheduledHoursForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public BigDecimal getTotalScheduledHoursForTimePeriod(Store store, Date startDate, Date endDate) {
		if(store == null) {
			log.error(STORE_NULL);
			throw new IllegalArgumentException(STORE_NULL);			
		}
		
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total scheduled hours for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<BigDecimal> totalResult = getHibernateTemplate().findByNamedQueryAndNamedParam("totalScheduledHoursByDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total scheduled hours for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		return totalResult != null && totalResult.size() > 0 && totalResult.get(0) != null ? totalResult.get(0) : new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#getTotalScheduledLaborCostForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public BigDecimal getTotalScheduledLaborCostForTimePeriod(Store store, Date startDate, Date endDate) {
		if(store == null) {
			log.error(STORE_NULL);
			throw new IllegalArgumentException(STORE_NULL);			
		}
		
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total scheduled labor cost for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<BigDecimal> totalResult = getHibernateTemplate().findByNamedQueryAndNamedParam("totalScheduledLaborCostByDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total scheduled labor cost for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		return totalResult != null && totalResult.size() > 0 && totalResult.get(0) != null ? totalResult.get(0) : new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	}
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.dao.ScheduleDao#getTotalScheduledHoursByPositionForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Map<Integer, BigDecimal> getTotalScheduledHoursByPositionForTimePeriod(Store store, Date startDate, Date endDate) {
		if(store == null) {
			log.error(STORE_NULL);
			throw new IllegalArgumentException(STORE_NULL);			
		}
		
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total scheduled hours by position for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<Object[]> totalResult = getHibernateTemplate().findByNamedQueryAndNamedParam("totalScheduledHoursByPositionByDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total scheduled hours by position for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}
		
		Map<Integer, BigDecimal> totalHoursByPosition;
		if(totalResult != null && totalResult.size() > 0) {
			totalHoursByPosition = new HashMap<Integer, BigDecimal>(totalResult.size());
			
			for(Object[] row : totalResult) {
				totalHoursByPosition.put((Integer) row[0], (BigDecimal)row[1]);
			}
		} else {
			totalHoursByPosition = new HashMap<Integer, BigDecimal>();
		}
		
		return totalHoursByPosition;
	}

	public StoreSchedule delete(StoreSchedule schedule) {
		if (schedule == null){
			throw new IllegalArgumentException("The schedule passed as parameter is null");
		}
		
		getHibernateTemplate().delete(schedule);
		
		return schedule;		
	}

	public void evict(StoreSchedule schedule) {
		getHibernateTemplate().evict(schedule);
	}	
	
}
