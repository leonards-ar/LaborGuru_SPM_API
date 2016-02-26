/*
 * File name: StaffingDaoHibernate.java
 * Creation date: 19/10/2008 16:57:47
 * Copyright Mindpool
 */
package com.laborguru.service.staffing.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.DailyProjectedStaffing;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.util.CalendarUtils;


/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StaffingDaoHibernate extends HibernateDaoSupport implements StaffingDao {
	private static final Logger log = Logger.getLogger(StaffingDaoHibernate.class);
	
	/**
	 * 
	 */
	public StaffingDaoHibernate() {
	}

	/**
	 * 
	 * @param position
	 * @param date
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#getDailyStaffingByDate(com.laborguru.model.Position, java.util.Date)
	 */
	public DailyProjectedStaffing getDailyStaffingByDate(Position position, Date date) {
		date = CalendarUtils.removeTimeFromDate(date);
		
		if(log.isDebugEnabled()) {
			log.debug("Searching staffing for day [" + date + "] and for position [" + position + "]");
		}
		
		List<DailyProjectedStaffing> staffing = (List<DailyProjectedStaffing>) getHibernateTemplate().findByNamedParam("from DailyProjectedStaffing staff where staff.position.id = :positionId and staff.date = :date", new String[]{"positionId", "date"}, new Object[] {position.getId(),date});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (staffing != null ? staffing.size() : "null") + "] staffing for day [" + date + "] and position [" + position + "]");
		}
		return staffing != null && staffing.size() > 0 ? staffing.get(0) : null;
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @see com.laborguru.service.staffing.dao.StaffingDao#deleteStoreDailyStaffingByDate(com.laborguru.model.Store, java.util.Date)
	 */
	public void deleteStoreDailyStaffingByDate(Store store, Date date) {
		date = CalendarUtils.removeTimeFromDate(date);
		
		if(log.isDebugEnabled()) {
			log.debug("Deleting staffing for day [" + date + "] and for store [" + store + "]");
		}
		getHibernateTemplate().flush();
		
		List<DailyProjectedStaffing> staffing = (List<DailyProjectedStaffing>) getHibernateTemplate().findByNamedParam("from DailyProjectedStaffing staff where staff.position.store.id = :storeId and staff.date = :date", new String[]{"storeId", "date"}, new Object[] {store.getId(),date});
		
		if(staffing != null && staffing.size() > 0) {

			getHibernateTemplate().deleteAll(staffing);

			if(log.isDebugEnabled()) {
				log.debug("Deleted staffing for day [" + date + "] and for store [" + store + "]. Total records: " + staffing.size());
			}		
		}
		
		
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#getStoreDailyStaffingByDate(com.laborguru.model.Store, java.util.Date)
	 */
	public List<DailyProjectedStaffing> getStoreDailyStaffingByDate(Store store, Date date) {
		date = CalendarUtils.removeTimeFromDate(date);
		
		if(log.isDebugEnabled()) {
			log.debug("Searching staffing for day [" + date + "] and for store [" + store + "]");
		}
		
		List<DailyProjectedStaffing> staffing = (List<DailyProjectedStaffing>) getHibernateTemplate().findByNamedParam("from DailyProjectedStaffing staff where staff.position.store.id = :storeId and staff.date = :date", new String[]{"storeId", "date"}, new Object[] {store.getId(),date});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (staffing != null ? staffing.size() : "null") + "] staffing for day [" + date + "] and store [" + store + "]");
		}
		
		return staffing;
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#getStoreDailyStaffingFromDate(com.laborguru.model.Store, java.util.Date)
	 */
	public List<DailyProjectedStaffing> getStoreDailyStaffingFromDate(Store store, Date date) {
		date = CalendarUtils.removeTimeFromDate(date);
		
		if(log.isDebugEnabled()) {
			log.debug("Searching staffing starting from day [" + date + "] and for store [" + store + "]");
		}
		
		List<DailyProjectedStaffing> staffing = (List<DailyProjectedStaffing>) getHibernateTemplate().findByNamedParam("from DailyProjectedStaffing staff where staff.position.store.id = :storeId and staff.date >= :date", new String[]{"storeId", "date"}, new Object[] {store.getId(),date});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (staffing != null ? staffing.size() : "null") + "] staffing starting from day [" + date + "] and store [" + store + "]");
		}
		
		return staffing;
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#save(com.laborguru.model.DailyProjectedStaffing)
	 */
	public DailyProjectedStaffing save(DailyProjectedStaffing dailyStaffing) {
		if (dailyStaffing == null){
			throw new IllegalArgumentException("The dailyStaffing passed as parameter is null");
		}
		
		getHibernateTemplate().saveOrUpdate(dailyStaffing);
		
		return dailyStaffing;
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @see com.laborguru.service.staffing.dao.StaffingDao#delete(com.laborguru.model.StoreDailyStaffing)
	 */
	public void deleteAll(List<DailyProjectedStaffing> storeDailyStaffing) {
		getHibernateTemplate().deleteAll(storeDailyStaffing);
	}
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#getTotalProjectedStaffingForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Double getTotalProjectedStaffingForTimePeriod(Store store, Date startDate, Date endDate) {
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total projected staffing for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<Double> totalResult = getHibernateTemplate().findByNamedParam("select sum(ds.totalDailyTarget) from DailyProjectedStaffing ds " +
				"where ds.position.store.id=:storeId AND ds.date >= :startDate AND ds.date <= :endDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total projected staffing for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		return totalResult != null && totalResult.size() > 0 && totalResult.get(0) != null ? totalResult.get(0) : new Double(0.0);
	}	
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.staffing.dao.StaffingDao#getTotalProjectedStaffingByPositionForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Map<Integer, Double> getTotalProjectedStaffingByPositionForTimePeriod(Store store, Date startDate, Date endDate) {
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total projected staffing by position for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<Object[]> totalResult = getHibernateTemplate().findByNamedParam("select ds.position.id, sum(ds.totalDailyTarget) from DailyProjectedStaffing ds " +
				"where ds.position.store.id=:storeId AND ds.date >= :startDate AND ds.date <= :endDate group by ds.position.id",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total projected staffing by position for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		Map<Integer, Double> totalHoursByPosition;
		if(totalResult != null && totalResult.size() > 0) {
			totalHoursByPosition = new HashMap<Integer, Double>(totalResult.size());
			
			for(Object[] row : totalResult) {
				totalHoursByPosition.put((Integer) row[0], (Double)row[1]);
			}
		} else {
			totalHoursByPosition = new HashMap<Integer, Double>();
		}

		return totalHoursByPosition;
	}	
}
