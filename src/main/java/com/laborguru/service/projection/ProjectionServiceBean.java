package com.laborguru.service.projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.laborguru.frontend.model.HalfHourElement;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DistributionType;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Store;
import com.laborguru.service.projection.dao.ProjectionDao;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ProjectionServiceBean implements ProjectionService {

	private static final Logger log = Logger.getLogger(ProjectionServiceBean.class);
	
	private ProjectionDao projectionDao;
	private StaffingService staffingService;
	
	/**
	 * This method returns a list with the historic average sales value for a week, starting since the "startWeekDate" and using "numberOfWeeks" weeks as source for the
	 * calculations. 
	 * @param numberOfWeeks
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#getAvgDailyProjectionForAWeek(java.lang.Integer, com.laborguru.model.Store, java.util.Date)
	 */
	public List<BigDecimal> getAvgDailyProjectionForAWeek(Integer numberOfWeeks, Store store, Date startWeekDate) {
		
		log.debug("getAvgDailyProjectionForAWeek - Before to call projectionDao.getAvgDailyProjectionForAWeek with: Number of weeks:"+numberOfWeeks+" Store name:"+store.getName()+" Date:"+startWeekDate);
		List<BigDecimal> retProjections = projectionDao.getAvgDailyProjectionForAWeek(numberOfWeeks, store, startWeekDate);
				
		return retProjections;
	}

	/**
	 * This methods returns a list with the adjusted daily projections values for a complete week (7 days) starting from the week before to the date
	 * passed as parameter. 
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#getAdjustedDailyProjectionForAWeek(com.laborguru.model.Store, java.util.Date)
	 */
	public List<BigDecimal> getWeeklyProjectionForLastWeek(Store store, Date startWeekDate) {		
		
		List<BigDecimal> retList = new ArrayList<BigDecimal>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
		
		Date lastWeekDate = CalendarUtils.addOrSubstractDays(startWeekDate,-7);

		log.debug("getWeeklyProjectionForLastWeek - Before to call projectionDao.getAdjustedDailyProjectionForAWeek with: Store name:"+store.getName()+" Last week date:"+lastWeekDate+" Date:"+startWeekDate);
		
		List<DailyProjection> lastWeekProjections = projectionDao.getAdjustedDailyProjectionForAWeek(store, lastWeekDate);
		
		for(DailyProjection dailyProjection: lastWeekProjections){
			if (dailyProjection.getDailyProjectionValue()!=null){				
				retList.add(dailyProjection.getDailyProjectionValue()); 
			}else{
				retList.add(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
			}
		}
		
		return retList;
	}	
	
	/**
	 * This method returns the projection value for a week, starting since the "startWeekDate" and using "numberOfWeeks" weeks as source for the
	 * calculations. It takes the distribution to used in the calculations form the store field distribution type. 
	 * TODO: if more optional paramaters depending on the projection are needed, consider refactor and introduce a parameterObject or
	 * a builder.
	 * @param numberOfWeeks This field is optional depending on the distribution type of the store.
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#getAvgDailyProjectionForAWeek(java.lang.Integer, com.laborguru.model.Store, java.util.Date)
	 */
	public List<BigDecimal> calculateWeeklyProjectionValues(Integer numberOfWeeks, Store store, Date startWeekDate) {

		List<BigDecimal> retProjections = null;
		
		if (DistributionType.STATIC.equals(store.getDistributionType())){
			retProjections =  getWeeklyProjectionForLastWeek(store,startWeekDate);
		}else{
			if (numberOfWeeks == null){
				numberOfWeeks = store.getDailyProjectionsWeeksDefault();
			}
			retProjections = projectionDao.getAvgDailyProjectionForAWeek(numberOfWeeks, store, startWeekDate);
		}
		
		
		return retProjections;
	}
	
	
	/**
	 * This methods returns a list with the adjusted daily projections values for a complete week (7 days) starting since the parameter "startWeekDate" 
	 * @param store
	 * @param startWeekDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#getAdjustedDailyProjectionForAWeek(com.laborguru.model.Store, java.util.Date)
	 */
	public List<DailyProjection> getAdjustedDailyProjectionForAWeek(Store store, Date startWeekDate) {		
		
		return projectionDao.getAdjustedDailyProjectionForAWeek(store, startWeekDate);	
	}

	/**
	 * @param store
	 * @param selectedDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#getDailyProjection(com.laborguru.model.Store, java.util.Date)
	 */
	public DailyProjection getDailyProjection(Store store, Date selectedDate) {
		
		return projectionDao.getDailyProjection(store, selectedDate);
	}
	
	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<DailyProjection> getDailyProjections(Store store, Date startDate, Date endDate) {
		return getProjectionDao().getDailyProjections(store, startDate, endDate);
	}

	
	/**
	 * Updates only half hour projection list of a projection.
	 * 
	 * @param store
	 * @param halfHourProjectionList
	 * @param selectedDate
	 * @see com.laborguru.service.projection.ProjectionService#updateHalfHourProjection(com.laborguru.model.Store, java.util.List, java.util.Date)
	 */
	public void updateHalfHourProjection(Store store, List<HalfHourProjection> halfHourProjectionList, Date selectedDate) {

		DailyProjection projection =  projectionDao.getDailyProjection(store, selectedDate);

		if (projection == null){
			String errorMsg = "A projection does not exist for store ["+store+"] and date ["+selectedDate+"]." ;
			log.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		
		int i=0;
		for (HalfHourProjection aHalfHour: projection.getHalfHourProjections()){
			aHalfHour.setAdjustedValue(halfHourProjectionList.get(i).getAdjustedValue());
			i++;
		}		

		projection.setStartingTime(store.getStoreOperationTimeByDate(selectedDate).getOpenHour());

		saveProjection(projection);		
	}
	
	/**
	 * Saves or updates the dailyProjections passed as parameter.
	 * @param dailyProjectionData
	 */
	public void saveOrUpdateDailyProjection(DailyProjection dailyProjectionData){
		
		Store store = dailyProjectionData.getStore();
		List<HalfHourProjection> halfHourProjectionList = dailyProjectionData.getHalfHourProjections();
		Date selectedDate = dailyProjectionData.getProjectionDate();
		
		DailyProjection projection =  projectionDao.getDailyProjection(store, selectedDate);
		
		if (projection == null){
			projection = new DailyProjection();
			projection.setProjectionDate(selectedDate);
			projection.setStore(store);

			for (HalfHourProjection aHalfHour: halfHourProjectionList){
				projection.addHalfHourProjection(aHalfHour);
			}
		} else {		
			int i=0;
			for (HalfHourProjection aHalfHour: projection.getHalfHourProjections()){
				aHalfHour.setAdjustedValue(halfHourProjectionList.get(i).getAdjustedValue());
				i++;
			}		
		}
		
		//Updating the additional variable values.
		projection.setDailyProjectionVariable2(dailyProjectionData.getDailyProjectionVariable2());
		projection.setDailyProjectionVariable3(dailyProjectionData.getDailyProjectionVariable3());
		projection.setDailyProjectionVariable4(dailyProjectionData.getDailyProjectionVariable4());		
				
		projection.setStartingTime(store.getStoreOperationTimeByDate(selectedDate).getOpenHour());

		saveProjection(projection);		
	}
	
	/**
	 * Saves or updates the dailyProjections passed as parameter.
	 * 
	 * @param dailyProjectionData
	 */
	private void saveProjection(DailyProjection projection){		
		
		projectionDao.save(projection);
		
		//Delete staffing calculations associated with the projection saved.
		staffingService.updateDailyStaffingForDate(projection.getStore(), projection.getProjectionDate());
	}
	
	/**
	 * 
	 * @param continueOnError
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#updateAll()
	 */
	public List<DailyProjection> updateAll(boolean continueOnError) {
		List<DailyProjection> projections = getProjectionDao().loadAll();
		
		for(DailyProjection projection: projections) {
			try {
				getStaffingService().deleteDailyStaffingForDate(projection.getStore(), projection.getProjectionDate());
				getStaffingService().getDailyStaffingByDate(projection.getStore(), projection.getProjectionDate());
				getProjectionDao().save(projection);
			} catch(RuntimeException ex) {
				log.error("Error updating projection and daily staffing " + projection, ex);
				if(!continueOnError) {
					throw ex;
				}
			}
		}
		
		return projections;
	}
	
	/**
	 * 
	 */
	public List<DailyProjection> updateAll(Date date, boolean continueOnError) {
		List<DailyProjection> projections = getProjectionDao().loadAll(date);
		
		for(DailyProjection projection: projections) {
			try {
				getStaffingService().deleteDailyStaffingForDate(projection.getStore(), projection.getProjectionDate());
				getStaffingService().getDailyStaffingByDate(projection.getStore(), projection.getProjectionDate());
				getProjectionDao().save(projection);
			} catch(RuntimeException ex) {
				log.error("Error updating projection and daily staffing " + projection, ex);
				if(!continueOnError) {
					throw ex;
				}
			}
		}
		
		return projections;
	}	
	
	/**
	 * This method calculate and saves the set of half hour projections for a day.
	 * 
	 * @param dailyProjectionData
	 * @param projectionAmount
	 * @param dateForCalculation
	 * @see com.laborguru.service.projection.ProjectionService#calculateAndSaveDailyProjection(com.laborguru.model.DailyProjection, java.math.BigDecimal, java.util.Date)
	 */
	public void calculateAndSaveDailyProjection(DailyProjection dailyProjectionData, BigDecimal projectionAmount, Date dateForCalculation) {
		
		DailyProjection aDailyProjection = calculateDailyProjection(dailyProjectionData, projectionAmount, dateForCalculation);		
		
		saveOrUpdateDailyProjection(aDailyProjection);	
	}	
		
	/**
	 * @param dailyProjectionData
	 * @param projectionAmount
	 * @param dateForCalculation
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#calculateDailyProjection(com.laborguru.model.DailyProjection, java.math.BigDecimal, java.util.Date)
	 */
	public DailyProjection calculateDailyProjection(DailyProjection dailyProjectionData, BigDecimal projectionAmount, Date dateForCalculation){
		
		if (dailyProjectionData == null){
			throw new IllegalArgumentException("DailyProjection cannot be null");
		}
		
		if (dailyProjectionData.getStore() == null){
			throw new IllegalArgumentException("Store cannot be null in daily projection");
		}

		if (dailyProjectionData.getStore().getHalfHourProjectionsWeeksDefault() == null){
			throw new IllegalArgumentException("HalfHourProjectionsWeeksDefault cannot be null");
		}
				
		List<HalfHourProjection> calculatedHalfHourList = null;
		
		//If the distribution is static we use last week date as calculation date 
		if (DistributionType.STATIC.equals(dailyProjectionData.getStore().getDistributionType())){
			calculatedHalfHourList = calculateStaticHalfHourProjections(dailyProjectionData.getStore(), projectionAmount, dailyProjectionData.getProjectionDate());								
		}else {
			calculatedHalfHourList = calculateHistoricHalfHourProjections(dailyProjectionData.getStore(), projectionAmount, dateForCalculation, dailyProjectionData.getStore().getHalfHourProjectionsWeeksDefault());					
		}
		
		dailyProjectionData.setHalfHourProjections(calculatedHalfHourList);
		
		return dailyProjectionData;
	}
									
	/**
	 * @param store
	 * @param projectionAmount
	 * @param selectedDate
	 * @param numberOfWeeks
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#calculateHistoricHalfHourProjections(com.laborguru.model.Store, java.math.BigDecimal, java.util.Date, java.lang.Integer)
	 */
	public List<HalfHourProjection> calculateHistoricHalfHourProjections(Store store, BigDecimal projectionAmount, Date selectedDate, Integer numberOfWeeks){
		List<HalfHourProjection> avgCalculatedHalfHourList = null;

		//Getting the average half hours values for the last "numberOfWeeks" weeks.
		avgCalculatedHalfHourList = getAvgHalfHourListByStoreAndDate(store, selectedDate, numberOfWeeks);			
				
		return calculateDailyHalfHourProjection(store, projectionAmount, selectedDate, avgCalculatedHalfHourList);
	}
	

	/**
	 * @param store
	 * @param projectionAmount
	 * @param selectedDate
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#calculateStaticHalfHourProjections(com.laborguru.model.Store, java.math.BigDecimal, java.util.Date)
	 */
	public List<HalfHourProjection> calculateStaticHalfHourProjections(Store store, BigDecimal projectionAmount, Date selectedDate){

		//Getting last week half hour projection to 
		List<HalfHourProjection> avgCalculatedHalfHourList = getLastWeekHalfHourProjectionList(store, selectedDate);			
		
		return calculateDailyHalfHourProjection(store, projectionAmount, selectedDate, avgCalculatedHalfHourList);
	}	
	
	/**
	 * @param projectionAmount
	 * @param selectedDate
	 * @param store
	 */
	public List<HalfHourProjection> calculateDailyHalfHourProjection(Store store, BigDecimal projectionAmount, Date selectedDate, List<HalfHourProjection> avgCalculatedHalfHourList){		
		
		
		BigDecimal totalAvgProjections = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		
		//Calculating the total
		for(HalfHourProjection halfHour: avgCalculatedHalfHourList){
			totalAvgProjections = totalAvgProjections.add(halfHour.getAdjustedValue());
		}		
				
		//Calculating the weight of each half hour (the distribution) and getting the value for the projection.
		//If total avg projections is zero, set all the values with constant distribution.
		if (totalAvgProjections.doubleValue() > 0.0){
			for(HalfHourProjection halfHour: avgCalculatedHalfHourList){
				BigDecimal halfHourWeight = halfHour.getAdjustedValue().divide(totalAvgProjections, SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE);
				halfHour.setAdjustedValue(projectionAmount.multiply(halfHourWeight));			
			}
		}else{
			//If the avg total is zero we distribute the projection constantly between the store open and close hours.
			OperationTime operationTime = store.getStoreOperationTimeByDate(selectedDate);
			
			BigDecimal avgProjectionValue = calculateAvgDistributionValue(operationTime, projectionAmount);
			
			Date closeHour = operationTime.getCloseHour();
			Date openHour = operationTime.getOpenHour();
			
			//Setting the values when open and close time are in the same day
			if (!operationTime.operationTimeEndsTomorrow()){
				for(HalfHourProjection halfHour: avgCalculatedHalfHourList){
					if ((halfHour.getTime().compareTo(openHour) >=0) && halfHour.getTime().before(closeHour)){
						halfHour.setAdjustedValue(new BigDecimal(avgProjectionValue.toString()));	
					}
				}
			}else{			
				//TODO: The values for the hours that falls in the following day are not set. This is because in projections we
				//manage the calendar dates and not the operation defined day. Should we moved to the operation day? LOADS of Changes!!!
				//The workaround is to complete the half hours list for the same day with the values that we were left behind.				
				for(HalfHourProjection halfHour: avgCalculatedHalfHourList){
					if ((halfHour.getTime().compareTo(openHour) >=0) || (halfHour.getTime().before(closeHour))){
						halfHour.setAdjustedValue(new BigDecimal(avgProjectionValue.toString()));	
					}
				}
			}
		}
				
		//TODO:Put an assertion to check that the total is the same to projection amount.
		return avgCalculatedHalfHourList;
	}

	
	/**
	 * @param operationTime
	 * @param projectionAmount
	 * @return
	 */
	private BigDecimal calculateAvgDistributionValue(OperationTime operationTime, BigDecimal projectionAmount){
		DateTime openTime = new DateTime(operationTime.getOpenHour());			
		DateTime closeTime = new DateTime(operationTime.getCloseHour());

		int minutesOpenTime = openTime.getMinuteOfDay();
		int minutesCloseTime = closeTime.getMinuteOfDay();
		
		//If the close time falls in the following day we have to add a day
		if (operationTime.operationTimeEndsTomorrow()){
			minutesCloseTime += SpmConstants.HALF_HOUR * SpmConstants.HALF_HOURS_IN_A_DAY;
		}
		
		int sizeListOfHalfHours = ((minutesCloseTime - minutesOpenTime) / SpmConstants.HALF_HOUR);	
		// SPM#222 avoid divide by zero
		if(sizeListOfHalfHours > 0) {
			BigDecimal numberOfHalfHours = new BigDecimal(sizeListOfHalfHours);
			BigDecimal valueToSet = projectionAmount.divide(numberOfHalfHours, SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE);
			
			return valueToSet;
		} else {
			return SpmConstants.BD_ZERO_VALUE;
		}
	}

	/**
	 * @param store
	 * @param selectedDate
	 * @param numberOfWeeks
	 * @return
	 */
	public List<HalfHourProjection> getAvgHalfHourListByStoreAndDate(Store store, Date selectedDate, Integer numberOfWeeks) {
		
		List<HalfHourProjection> avgProjections = projectionDao.getAvgHalfHourProjection(numberOfWeeks, store, selectedDate);
		
		return completeTimeHalfHourProjectionsList(avgProjections);		
	}
	
	/**
	 * @param store
	 * @param selectedDate
	 * @param numberOfWeeks
	 * @return
	 */
	public List<HalfHourProjection> getLastWeekHalfHourProjectionList(Store store, Date selectedDate) {
		
		Date lastWeekDate = CalendarUtils.addOrSubstractDays(selectedDate,-7);
		log.debug("getLastWeekHalfHourProjectionList - Before to call getDailyProjection with: Store name: " + store.getName() + " Last week date: " + lastWeekDate + " Date: " + selectedDate);
				
		DailyProjection dailyProjection = getDailyProjection(store, lastWeekDate);
		List<HalfHourProjection> halfHourProjections = null;
		
		if (dailyProjection != null){
			halfHourProjections = dailyProjection.getHalfHourProjections();
		}else{
			halfHourProjections = new ArrayList<HalfHourProjection>();
		}
		
		return completeTimeHalfHourProjectionsList(halfHourProjections);		
	}	
	
	/**
	 * @param avgProjections
	 * @param store
	 */
	private List<HalfHourProjection> completeTimeHalfHourProjectionsList(List<HalfHourProjection> avgProjections) {
		DateTime openHour = new DateTime().withDate(1970, 1, 1).withTime(0,0,0,0);
		DateTime closeHour = new DateTime().withDate(1970, 1, 1).withTime(23,30,0,0);
				
		List<HalfHourProjection> retList = new ArrayList<HalfHourProjection>(SpmConstants.HALF_HOURS_IN_A_DAY);

		DateTime nextTime = new DateTime(openHour);
		
		int index=0;

		//Filling up the return list with: 
		//1.- The list of half hours passed as parameter.
		//2.- The halfhours from open time and close time of the store, that are not in the list. 
		for(HalfHourProjection currentHalfHour: avgProjections) {
			DateTime currentTime = new DateTime(currentHalfHour.getTime().getTime());

			while(currentTime.isAfter(nextTime)) {
				HalfHourProjection aHalfHourProjection = new HalfHourProjection();
				aHalfHourProjection.setTime(nextTime.toDate());
				aHalfHourProjection.setAdjustedValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
				aHalfHourProjection.setIndex(index++);
				retList.add(aHalfHourProjection);
				nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
			}
			
			//Adding current Half hour to return list.
			HalfHourProjection aHalfHourProjection = new HalfHourProjection();
			aHalfHourProjection.setTime(currentTime.toDate());			
			aHalfHourProjection.setAdjustedValue(new BigDecimal(currentHalfHour.getAdjustedValue().toString()));
			aHalfHourProjection.setIndex(index++);

			retList.add(aHalfHourProjection);			

			nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
		}
		
		
		while(nextTime.isBefore(closeHour) || nextTime.isEqual(closeHour)){
			HalfHourProjection aHalfHourProjection = new HalfHourProjection();
			aHalfHourProjection.setTime(nextTime.toDate());
			aHalfHourProjection.setAdjustedValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
			aHalfHourProjection.setIndex(index++);
			retList.add(aHalfHourProjection);
			nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
		}
		
		return retList;
	}

	
	/**
	 * @param halfHourElement
	 * @param totalAdjusted
	 * @param totalProjected
	 * @param hoursNotChanged
	 * @return
	 * @see com.laborguru.service.projection.ProjectionService#calculateRevisedValue(com.laborguru.frontend.model.HalfHourElement, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 */
	public HalfHourElement calculateRevisedValue(HalfHourElement halfHourElement, BigDecimal totalAdjusted, BigDecimal totalProjected, BigDecimal hoursNotChanged){

		if (halfHourElement.getAdjustedValue() != null ) {
			halfHourElement.setRevisedValue(halfHourElement.getAdjustedValue());
		}else {
			BigDecimal auxVal = (new BigDecimal("1").subtract(totalAdjusted.divide(totalProjected, SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE)));
			halfHourElement.setRevisedValue(auxVal.multiply(halfHourElement.getProjectedValue()).divide(hoursNotChanged, SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE));
		}
		
		return halfHourElement;
	}	
	
	
	
	/**
	 * @return the projectionDao
	 */
	public ProjectionDao getProjectionDao() {
		return projectionDao;
	}

	/**
	 * @param projectionDao the projectionDao to set
	 */
	public void setProjectionDao(ProjectionDao projectionDao) {
		this.projectionDao = projectionDao;
	}


	/**
	 * @return the staffingService
	 */
	public StaffingService getStaffingService() {
		return staffingService;
	}


	/**
	 * @param staffingService the staffingService to set
	 */
	public void setStaffingService(StaffingService staffingService) {
		this.staffingService = staffingService;
	}
}
