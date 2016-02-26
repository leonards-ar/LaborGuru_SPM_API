 package com.laborguru.service.projection.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.DailyProjection;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.Store;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ProjectionDaoHibernate extends HibernateDaoSupport implements ProjectionDao {

	private static final Logger log = Logger.getLogger(ProjectionDaoHibernate.class);

	private static final int DECIMAL_SCALE = 16;
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
	private static final String INIT_VALUE_ZERO = "0.00";

	/**
	 * 
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#getAdjustedDailyProjectionForAWeek(com.laborguru.model.Store, java.util.Date)
	 */
	public List<DailyProjection> getAdjustedDailyProjectionForAWeek(Store store, Date startWeekDate) {
		
		DateTime startDate = new DateTime(startWeekDate);
		
		DateTime startTime = new DateMidnight(startDate).toDateTime();
		DateTime endTime = startDate.plusDays(6).withTime(23, 59, 59, 999);
		if(log.isDebugEnabled()){
			log.debug("Before getting adjusted daily projections - Parameters: Store Id:"+ store.getId()+" startDate:"+startTime.toString()+" endDate:"+endTime.toString());
		}
		
		List<DailyProjection> projections  = (List<DailyProjection>)  getHibernateTemplate().findByNamedParam("from DailyProjection dp " +
				"where dp.store.id=:storeId AND dp.projectionDate >= :startDate AND dp.projectionDate <= :endDate order by dp.projectionDate",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), startTime.toDate(), endTime.toDate()} );		
		
		if(log.isDebugEnabled()){
			log.debug("After getting adjusted daily projections - Average sales list size: Store Id:"+ projections.size());
		}
				
		//if projections are less than 7
		//we complete the days for which there is no projections.		
		if ( projections.size() < 7){
			
			List<DailyProjection> completedProjected = new ArrayList<DailyProjection>(7);

			//if projection list is empty, we fill it with empty daily projections
			if (projections.isEmpty()){
				for (int i=0; i < 7; i++){				
					completedProjected.add(new DailyProjection());
				}
			}else{			
				DateTime auxDateTime = startTime;									
				for (int i=0, j =0; i < 7; i++){
					DailyProjection auxProj = new DailyProjection();
					
					if(j < projections.size() ){
						DateTime elementDateTime = new DateMidnight(projections.get(j).getSalesDate()).toDateTime();
						if (elementDateTime.getDayOfWeek() == auxDateTime.getDayOfWeek()){
							auxProj = projections.get(j++);
						}	
					}
					completedProjected.add(auxProj);
					
					auxDateTime = auxDateTime.plusDays(1);
				}
			}
			
			return completedProjected;			
		}
		
		
		return projections;
	}

	/**
	 * Returns the average daily projection values.
	 * 
	 * @param numberOfWeeks
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#getAvgDailyProjectionForAWeek(java.lang.Integer, com.laborguru.model.Store, java.util.Date)
	 */
	public List<BigDecimal> getAvgDailyProjectionForAWeek(Integer numberOfWeeks, Store store, Date startWeekDate) {
		
		DateTime startDate = new DateTime(startWeekDate);
		
		DateTime startTime = new DateMidnight(startDate.minusWeeks(numberOfWeeks)).toDateTime();
		DateTime endTime = startDate.minusDays(1).withTime(23, 59, 59, 999);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting avg daily projections - Parameters: Store Id:"+ store.getId() 
					+" number of weeks:"+numberOfWeeks
					+" startWeekDate:"+ startWeekDate
					+" startDate:"+startTime.toString()+" endDate:"+endTime.toString());
		}
		
		//We are retrieving daily totals for all the days that are in the date range
		List<Object[]> avgSalesList = getHibernateTemplate().findByNamedParam("select sum(hs.mainValue), hs.dayOfWeek from HistoricSales hs " +
				"where hs.store.id=:storeId AND hs.dateTime >= :startDate AND hs.dateTime <= :endDate AND hs.mainValue > 0 group by date(hs.dateTime) order by hs.dayOfWeek",
				new String[] {"storeId", "startDate", "endDate"}, new Object[] {store.getId(), startTime.toDate(), endTime.toDate()} );

		if(log.isDebugEnabled()){
			log.debug("After getting avg daily projections - Average Sales List size: Store Id:"+ avgSalesList.size());
		}
		
		List<BigDecimal> retSalesList = new ArrayList<BigDecimal>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);

		//We need to ignore the dates that don't have any projection so we need to this auxiliary variable:		
		//We use dayOfWeekArray to set the number of weeks that we are going to use in the average for each day projection, each element represents
		//the number of times that each day appear on the result.
		int dayOfWeekArray[] = {0,0,0,0,0,0,0};

		//We use the total sales map to accumulate the totals for each day in the projection
		Map<Integer, BigDecimal> totalSales = new HashMap<Integer, BigDecimal>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
		
		//Initializes the mapping
		for (int i=0; i <  SpmConstants.DAILY_PROJECTION_PERIOD_DAYS; i++){
			totalSales.put(i+1, BigDecimal.valueOf(0.00));
		}
		
		//We proccess the results calculting totals and the number of times that each day in on the result list.
		//TODO: Change the approache an use a query with subqueries see query for avg half hours
		for (int i=0; i < avgSalesList.size(); i++){
			Integer dayOfWeek = (Integer)(avgSalesList.get(i)[1]);
			dayOfWeekArray[dayOfWeek-1]++;
			BigDecimal totalValue = totalSales.get(dayOfWeek).add((BigDecimal)avgSalesList.get(i)[0]);
			totalSales.put(dayOfWeek, totalValue);
			
		}
		
		//We set result list. We start in 1 as we need to pull the total from the auxiliary map and calculate the avg.
		for (int i=1; i < 8; i++){
			BigDecimal aValue = new BigDecimal(INIT_VALUE_ZERO);
			
			int timesInResult = dayOfWeekArray[i-1];
			if (timesInResult > 0){
				aValue = totalSales.get(i).divide(BigDecimal.valueOf(timesInResult), DECIMAL_SCALE, ROUNDING_MODE);
			}
			
			retSalesList.add(aValue);
		}
		
		return retSalesList;
	}
	
	/**
	 * 
	 * @param store
	 * @param selectedDate
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#getDailyProjection(com.laborguru.model.Store, java.util.Date)
	 */
	public DailyProjection getDailyProjection(Store store, Date selectedDate) {
		DateTime dt = new DateMidnight(selectedDate).toDateTime();

		if(log.isDebugEnabled()) {
			log.debug("Before getting daily projections - Parameters: Store Id:"+ store.getId()+" selectedDate:"+dt.toString());
		}

		List<DailyProjection> projections =  getHibernateTemplate().findByNamedParam("from DailyProjection dp where dp.store.id=:storeId and dp.projectionDate=:projectionDate" , 
				new String[]{"storeId", "projectionDate"}, 
				new Object[]{store.getId(), dt.toDate()});
		
		if(projections.size() == 0) {
			return null;
		}
		
		if(projections.size() > 1) {
			log.warn("There is more than one result for store Id: " + store.getId() + " and date: " + selectedDate);
		}
		
		return projections.get(0);
	}
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#getDailyProjections(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public List<DailyProjection> getDailyProjections(Store store, Date startDate, Date endDate) {
		DateTime from = new DateMidnight(startDate).toDateTime();
		DateTime to = new DateTime(endDate).withTime(23, 59, 59, 999);
		
		if(log.isDebugEnabled()){
			log.debug("Before getting daily projections - Parameters: Store Id:" + store.getId() + " startDate: " + from.toString()+ " endDate: " + to.toString());
		}

		List<DailyProjection> projections =  (List<DailyProjection>) getHibernateTemplate().findByNamedParam("from DailyProjection dp where dp.store.id=:storeId and dp.projectionDate >= :fromProjectionDate and dp.projectionDate <= :toProjectionDate", 
				new String[]{"storeId", "fromProjectionDate", "toProjectionDate"}, 
				new Object[]{store.getId(), from.toDate(), to.toDate()});
		
		if(log.isDebugEnabled()) {
			log.debug("Retrieved " + projections.size() + " daily projections - Parameters: Store Id:" + store.getId() + " startDate: " + from.toString()+ " endDate: " + to.toString());
		}
		
		return projections;
	}
	
	/**
	 * 
	 * @param numberOfWeeks
	 * @param store
	 * @param selectedDate
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#getAvgHalfHourProjection(java.lang.Integer, com.laborguru.model.Store, java.util.Date)
	 */
	public List<HalfHourProjection> getAvgHalfHourProjection(Integer numberOfWeeks, Store store, Date selectedDate){
		
		DateTime startDate = new DateTime(selectedDate);
		
		DateTime startTime = new DateMidnight(startDate.minusWeeks(numberOfWeeks)).toDateTime();
		DateTime endTime = startDate.minusDays(1).withTime(23, 59, 59, 999);
		
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(selectedDate);
		
		if(log.isDebugEnabled()) {
			log.debug("Before getting avg half hour projections - Parameters: Store Id:"+ store.getId()
					+" number of weeks:"+numberOfWeeks
					+" selectedDate:"+selectedDate
					+" startDate:"+startTime.toString("yyyy-MM-dd HH:mm:ss") + " endDate:" + endTime.toString("yyyy-MM-dd HH:mm:ss") + " dayOfWeek:" + startDate.getDayOfWeek());
		}
		
		List avgProjections = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"halfHourProjections", 
				new String[]{"storeId","startDate","endDate","dayOfWeek"}, 
				new Object[]{store.getId(),startTime.toString("yyyy-MM-dd HH:mm:ss"),endTime.toString("yyyy-MM-dd HH:mm:ss"), calendarDate.get(Calendar.DAY_OF_WEEK)});
				
		
		Map<String, BigDecimal> totalMap = new HashMap<String, BigDecimal>(48);
		Set<String> differentDates = new HashSet<String>(numberOfWeeks);

		int size = avgProjections.size();				
		for(int i =0; i < size; i++){
			Object[] row = (Object[]) avgProjections.get(i);
			
			BigDecimal projectionValue =  (BigDecimal)row[1];
			String projectionTime = (String) row[0];
			String projectionDate = (String) row[2];
			
			differentDates.add(projectionDate);
			
			if(totalMap.containsKey(projectionTime)){
				BigDecimal auxValue = totalMap.get(projectionTime).add(projectionValue);
				totalMap.put(projectionTime, auxValue);
			}else{
				totalMap.put(projectionTime, projectionValue);
			}
		}
		
		List<HalfHourProjection> projections = new LinkedList<HalfHourProjection>();
		BigDecimal weeksNumber = new BigDecimal(differentDates.size());
		
		for (Map.Entry<String, BigDecimal> hhEntry:  totalMap.entrySet()) {			
			HalfHourProjection hhp = new HalfHourProjection();			
			
			hhp.setTime(hhEntry.getKey());
			
			BigDecimal avgValue = hhEntry.getValue().divide(weeksNumber, DECIMAL_SCALE, ROUNDING_MODE);
			hhp.setAdjustedValue(avgValue);
			projections.add(hhp);
		}
		
		Collections.sort(projections);
		
		return projections;
	}

	/**
	 * @param projection
	 * @see com.laborguru.service.projection.dao.ProjectionDao#save(com.laborguru.model.DailyProjection)
	 */
	public void save(DailyProjection projection) {
		
		DateTime dt = new DateMidnight(projection.getSalesDate()).toDateTime();
		projection.setSalesDate(dt.toDate());
		
		getHibernateTemplate().save(projection);
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.projection.dao.ProjectionDao#loadAll()
	 */
	public List<DailyProjection> loadAll() {
		return (List<DailyProjection>)getHibernateTemplate().loadAll(DailyProjection.class);
	}

	/**
	 * 
	 */
	public List<DailyProjection> loadAll(Date date) {
		List<DailyProjection> projections  = (List<DailyProjection>)  getHibernateTemplate().findByNamedParam("from DailyProjection dp " +
				"where dp.projectionDate = :date order by dp.projectionDate",
				new String[]{"date"}, new Object[] {date} );		
		
		return projections;
	}
}
