package com.laborguru.service.historicsales.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.laborguru.model.DailyHistoricSales;
import com.laborguru.model.HalfHourHistoricSales;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Store;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;
import com.laborguru.util.SpmConstants;

/**
 * Hibernate implementation for Historic Sales Dao
 *  
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HistoricSalesDaoHibernate extends SpmHibernateDao implements HistoricSalesDao {

	private static final Logger log = Logger.getLogger(HistoricSalesDaoHibernate.class);
	private static final String HISTORIC_SALES_NULL = "The historic sales object passed in as parameter is null";
	private static final String STORE_NULL = "The store passed in as parameter is null";
	private static final String DATE_NULL = "The date passed in as parameter is null";

	/**
	 * 
	 * @param hs
	 * @see com.laborguru.service.historicsales.dao.HistoricSalesDao#saveOrUpdate(com.laborguru.model.HistoricSales)
	 */
	public void saveOrUpdate(HistoricSales hs) {
		
		if (hs == null){
			log.error(HISTORIC_SALES_NULL);
			throw new IllegalArgumentException(HISTORIC_SALES_NULL);			
		}
		
		getHibernateTemplate().saveOrUpdate(hs);
	}

	
	/**
	 * @param store
	 * @param datime
	 * @return
	 * @see com.laborguru.service.historicsales.dao.HistoricSalesDao#getHistoricSales(com.laborguru.model.Store, java.util.Date)
	 */
	public HistoricSales getHistoricSales(Store store, Date datime){

		List<HistoricSales> hsResult = (List<HistoricSales>) getHibernateTemplate().findByNamedParam("from HistoricSales hs " +
				"where hs.store.id=:storeId AND hs.dateTime = :dateTime",
				new String[] {"storeId", "dateTime"}, new Object[] {store.getId(), datime});
		
		if(hsResult.isEmpty()){
			return null;
		}
		
		return hsResult.get(0);
	}
	
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.historicsales.dao.HistoricSalesDao#getTotalHistoricSalesForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public BigDecimal getTotalHistoricSalesForTimePeriod(Store store, Date startDate, Date endDate) {
		if(store == null) {
			log.error(STORE_NULL);
			throw new IllegalArgumentException(STORE_NULL);			
		}
		
		DateTime from = startDate != null ? new DateMidnight(startDate).toDateTime() : new DateTime(0L);
		DateTime to = endDate != null ? new DateTime(endDate).withTime(23, 59, 59, 999) : new DateTime(Long.MAX_VALUE);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting total historic sales for time period - Parameters: Store Id: "+ store.getId() + ", from date: " + from.toDate() + ", to date: " + to.toDate());
		}
	
		List<BigDecimal> totalResult = getHibernateTemplate().findByNamedParam("select sum(hs.mainValue) from HistoricSales hs " +
				"where hs.store.id=:storeId AND hs.dateTime >= :startDate AND hs.dateTime <= :endDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()){
			log.debug("After getting total historic sales for time period - Result List size: Store Id: " + (totalResult != null ? totalResult.size() : "null"));
		}		

		return totalResult != null && totalResult.size() > 0 && totalResult.get(0) != null ? totalResult.get(0) : new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.historicsales.dao.HistoricSalesDao#getDailyHistoricSales(com.laborguru.model.Store, java.util.Date)
	 */
	public DailyHistoricSales getDailyHistoricSales(Store store, Date date) {
		
		if (store == null){
			throw new IllegalArgumentException(STORE_NULL);
		}
		
		if (date == null){
			throw new IllegalArgumentException(DATE_NULL);
		}

		DateTime selectedDate = new DateTime(date);
		
		
		if(log.isDebugEnabled()) {
			log.debug("Before getting avg half hour sales - Parameters: Store Id:"+ store.getId()+" selectedDate:"+selectedDate.toString("yyyy-MM-dd"));
		}
		
		List halfHourSales = getHibernateTemplate().findByNamedQueryAndNamedParam("halfHourByDate",
			new String[]{"storeId","selectedDate"}, new Object[]{store.getId(),selectedDate.toString("yyyy-MM-dd")});

		DailyHistoricSales retValue = new DailyHistoricSales();
		
		retValue.setStore(store);
		retValue.setSalesDate(date);
	
		int size = halfHourSales.size();
		
		if (size == 0){
			return null;
		}
		
		List<HalfHourHistoricSales> halfHoursList = new ArrayList<HalfHourHistoricSales>(size);	

		for (int i=0; i < size; i++){
			Object[] row = (Object[])halfHourSales.get(i);			
			String time = (String) row[0];
			BigDecimal value1 =  (BigDecimal)row[1];
			BigDecimal value2 =  (BigDecimal)row[2];
			BigDecimal value3 =  (BigDecimal)row[3];
			BigDecimal value4 =  (BigDecimal)row[4];
			
			HalfHourHistoricSales halfHour = new HalfHourHistoricSales();
			halfHour.setTime(time);
			halfHour.setValue(value1);
			halfHour.setSecondValue(value2);
			halfHour.setThirdValue(value3);
			halfHour.setFourthValue(value4);
			
			halfHoursList.add(halfHour);
		}
		
		setTimeDailyHistoricSales(halfHoursList, retValue);
		
		return retValue;
	}
	
	/**
	 * @param avgProjections
	 * @param store
	 */
	private void setTimeDailyHistoricSales(List<HalfHourHistoricSales> halfHoursList, DailyHistoricSales dailyHistoricSales) {
		
		DateTime openHour = new DateTime().withDate(1970, 1, 1).withTime(0,0,0,0);
		DateTime closeHour = new DateTime().withDate(1970, 1, 1).withTime(23,30,0,0);
		
		DateTime nextTime = new DateTime(openHour);
				
		for(HalfHourHistoricSales currentHalfHour: halfHoursList) {
			DateTime currentTime = new DateTime(currentHalfHour.getTime().getTime());
			
			while(currentTime.isAfter(nextTime)) {				
				HalfHourHistoricSales aHalfHour = new HalfHourHistoricSales();
				aHalfHour.setTime(nextTime.toDate());
				aHalfHour.setValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
				
				dailyHistoricSales.addHalfHourHistoricSales(aHalfHour);
				nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
			}
			
			dailyHistoricSales.addHalfHourHistoricSales(currentHalfHour);
			nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
		}
				
		while(nextTime.isBefore(closeHour) || nextTime.isEqual(closeHour)){
			HalfHourHistoricSales aHalfHour = new HalfHourHistoricSales();
			aHalfHour.setTime(nextTime.toDate());
			aHalfHour.setValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));

			dailyHistoricSales.addHalfHourHistoricSales(aHalfHour);			
			nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
		}		
	}


	/**
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.historicsales.dao.HistoricSalesDao#getHistoricSalesByStoreAndDate(com.laborguru.model.Store, java.util.Date)
	 */
	public Map<Date, HistoricSales> getHistoricSalesByStoreAndDate(Store store, Date date) {
		if (store == null){
			throw new IllegalArgumentException(STORE_NULL);
		}
		
		if (date == null){
			throw new IllegalArgumentException(DATE_NULL);
		}

		DateTime selectedDate = new DateTime(date);
			
		if(log.isDebugEnabled()) {
			log.debug("Before getting historic sales - Parameters: Store Id:"+ store.getId()+" selectedDate:"+selectedDate.toString("yyyy-MM-dd"));
		}
		
		List<HistoricSales> hsList = getHibernateTemplate().findByNamedParam("from HistoricSales hs where hs.store.id =:storeId and DATE(hs.dateTime) = STR_TO_DATE(:selectedDate,'%Y-%m-%d')",
			new String[]{"storeId","selectedDate"}, new Object[]{store.getId(),selectedDate.toString("yyyy-MM-dd")});

		Map<Date,HistoricSales> retMap = new HashMap<Date,HistoricSales>(SpmConstants.HALF_HOURS_IN_A_DAY);
		
		for(HistoricSales hs: hsList){
			retMap.put(hs.getDateTime(), hs);
		}
		
		return retMap;
	}

}
