/*
 * File name: StaffingServiceBean.java
 * Creation date: 19/10/2008 17:00:27
 * Copyright Mindpool
 */
package com.laborguru.service.staffing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.laborguru.model.DailyFlashSales;
import com.laborguru.model.DailyFlashStaffing;
import com.laborguru.model.DailyHistoricSales;
import com.laborguru.model.DailyHistoricSalesStaffing;
import com.laborguru.model.DailyProjectedStaffing;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DailySalesValue;
import com.laborguru.model.DailyStaffing;
import com.laborguru.model.DayOfWeekData;
import com.laborguru.model.DayPart;
import com.laborguru.model.DayPartData;
import com.laborguru.model.HalfHourFlashSales;
import com.laborguru.model.HalfHourFlashStaffing;
import com.laborguru.model.HalfHourHistoricSalesStaffing;
import com.laborguru.model.HalfHourProjectedStaffing;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.HalfHourSalesValue;
import com.laborguru.model.HalfHourStaffing;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyFlashStaffing;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.model.StoreDailyStaffing;
import com.laborguru.service.historicsales.dao.HistoricSalesDao;
import com.laborguru.service.position.dao.PositionDao;
import com.laborguru.service.projection.dao.ProjectionDao;
import com.laborguru.service.staffing.dao.StaffingDao;
import com.laborguru.service.staffing.model.DailyStaffingPositionData;
import com.laborguru.service.staffing.model.HalfHourStaffingPositionData;
import com.laborguru.service.staffing.model.HalfHourStaffingPositionDataComparator;
import com.laborguru.service.store.dao.StoreDao;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StaffingServiceBean implements StaffingService {
	private static final String GROUP_TOKEN = "_grp_";
	private static final String POSITION_TOKEN = "_pos_";
	
	private StaffingDao staffingDao;
	private PositionDao positionDao;
	private ProjectionDao projectionDao;
	private StoreDao storeDao;
	private HistoricSalesDao historicSalesDao;
	
	/**
	 * 
	 */
	public StaffingServiceBean() {
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	public StoreDailyStaffing getDailyStaffingByDate(Store store, Date date) {
		List<DailyStaffing> managerDailyStaffings = new ArrayList<DailyStaffing>();
		double nonManagerTargetAddition = 0.0;
		
		store = getStoreDao().getStoreById(store);
		DailySalesValue dailySalesValue = getDailyProjection(store, date);
		
		StoreDailyStaffing storeDailyStaffing = new StoreDailyStaffing(store);
		storeDailyStaffing.setDate(date);
		
		List<Map<String, List<HalfHourStaffingPositionData>>> staffingData = initializeHalfHourStaffingData(store, date, dailySalesValue);
		
		//:TODO: Improve performance? Or complicate coding?
		boolean mustSave = false;
		for(Position position : store.getPositions()) {
			DailyStaffing dailyStaffing = getStaffingDao().getDailyStaffingByDate(position, date);

			if(dailyStaffing == null) {
				dailyStaffing = calculateDailyStaffing(position, date, dailySalesValue, staffingData);
				mustSave = true;
			}

			if(dailyStaffing != null) {
				if(isManagerDailyStaffing(dailyStaffing)) {
					managerDailyStaffings.add(dailyStaffing);
				} else {
					nonManagerTargetAddition += NumberUtils.getDoubleValue(dailyStaffing.getTotalDailyTarget());
				}
			}
			
			if(dailyStaffing != null) {
				storeDailyStaffing.addDailyStaffing((DailyProjectedStaffing)dailyStaffing);
			}
		}


		if(mustSave) {
			applyOtherFactorsToManagerPositions(managerDailyStaffings, store, nonManagerTargetAddition);

			save(storeDailyStaffing);
		}
		
		return storeDailyStaffing;
	}
	
	//This method calculates dailyStaffing giving a dailySalesValue. (It's being used in DailyFlashReport to calculate ideal hours)
	public StoreDailyFlashStaffing getDailyFlashSalesStaffingByDate(Store store, Date date, DailySalesValue dailySalesValue) {
		List<DailyStaffing> managerDailyStaffings = new ArrayList<DailyStaffing>();
		double nonManagerTargetAddition = 0.0;
		
		store = getStoreDao().getStoreById(store);
		
		StoreDailyFlashStaffing storeDailyStaffing = new StoreDailyFlashStaffing(store);
		storeDailyStaffing.setDate(date);
		
		List<Map<String, List<HalfHourStaffingPositionData>>> staffingData = initializeHalfHourStaffingData(store, date, dailySalesValue);
		
		//:TODO: Improve performance? Or complicate coding?
		for(Position position : store.getPositions()) {
			DailyStaffing dailyStaffing = calculateDailyStaffing(position, date, dailySalesValue, staffingData);
			if(dailyStaffing != null) {
				storeDailyStaffing.addDailyStaffing((DailyFlashStaffing) dailyStaffing);
				
				if(isManagerDailyStaffing(dailyStaffing)) {
					managerDailyStaffings.add(dailyStaffing);
				} else {
					nonManagerTargetAddition += NumberUtils.getDoubleValue(dailyStaffing.getTotalDailyTarget());
				}
			}
		}
		
		applyOtherFactorsToManagerPositions(managerDailyStaffings, store, nonManagerTargetAddition);
		
		return storeDailyStaffing;
	}	
	
	/**
	 * 
	 * @param managerDailyStaffing
	 * @param position
	 * @param nonManagerTargetAddition
	 */
	private void applyOtherFactorsToManagerPositions(List<DailyStaffing> managerDailyStaffings, Store store, double nonManagerTargetAddition) {
		if(managerDailyStaffings != null) {
			for(DailyStaffing managerDailyStaffing : managerDailyStaffings) {
				double floorMgmtFactor = nonManagerTargetAddition * NumberUtils.getDoubleValue(store.getFloorManagementFactor()) / 100;
				int minFloorMgmt = NumberUtils.getIntegerValue(store.getMinimumFloorManagementHours());
				floorMgmtFactor = Math.max(floorMgmtFactor, (double) minFloorMgmt);
				//:TODO: Fix, if recalculation, floor management of the previous value must be ignored
				//       TotalFlexible must be recalculated
				double totalFlexible = NumberUtils.getDoubleValue(managerDailyStaffing.getTotalFlexible()) + floorMgmtFactor;
				
				managerDailyStaffing.setTotalFlexible(new Double(totalFlexible));
				
				managerDailyStaffing.setTotalDailyTarget(managerDailyStaffing.getBaseDailyTarget());
			}
		}
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 */
	private boolean isManagerDailyStaffing(DailyStaffing dailyStaffing) {
		return dailyStaffing != null && dailyStaffing.getPosition() != null && dailyStaffing.getPosition().isManager();
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	private DailySalesValue getDailyProjection(Store store, Date date) {
		DailySalesValue dailySalesValue = getProjectionDao().getDailyProjection(store, date);
		if(dailySalesValue == null) {
			dailySalesValue = DailyProjection.getEmptyDailyProjectionInstance(store, date);
		}
		return dailySalesValue;
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	private List<Map<String, List<HalfHourStaffingPositionData>>> initializeHalfHourStaffingData(Store store, Date date, DailySalesValue dailySalesValue) {
		int size = dailySalesValue.getHalfHourSalesValues().size();
		List<Map<String, List<HalfHourStaffingPositionData>>> data = new ArrayList<Map<String,List<HalfHourStaffingPositionData>>>(size);
		
		for(int i = 0; i < size; i++) {
			data.add(initializeHalfHourStaffingData(store, date, dailySalesValue.getHalfHourSalesValues().get(i)));
		}
		
		return data;
	}
	
	/**
	 * 
	 * @param dailySalesValue
	 * @return
	 */
	private DailyStaffing getDailyStaffingInstance(DailySalesValue dailySalesValue) {
		//:TODO: Check if another class arrives??? Should not happen :)
		if(dailySalesValue instanceof DailyProjection) {
			return new DailyProjectedStaffing();
		} else if (dailySalesValue instanceof DailyFlashSales) {
			return new DailyFlashStaffing();
		} else {
			return new DailyHistoricSalesStaffing();
		}
	}

	/**
	 * 
	 * @param halfHourSalesValue
	 * @return
	 */
	private HalfHourStaffing getHalfHourStaffingInstance(HalfHourSalesValue halfHourSalesValue) {
		//:TODO: Check if another class arrives??? Should not happen :)
		if(halfHourSalesValue instanceof HalfHourProjection) {
			return new HalfHourProjectedStaffing();
		} 
		if(halfHourSalesValue instanceof HalfHourFlashSales) {
			return new HalfHourFlashStaffing();
		}
		
		return new HalfHourHistoricSalesStaffing();
		
	}
	
	/**
	 * 
	 * @param position
	 * @param date
	 * @param dailySalesValue
	 * @param staffingData
	 * @return
	 */
	private DailyStaffing calculateDailyStaffing(Position position, Date date, DailySalesValue dailySalesValue, List<Map<String, List<HalfHourStaffingPositionData>>> staffingData) {
		if(dailySalesValue != null) {
			DailyStaffing dailyStaffing = getDailyStaffingInstance(dailySalesValue);
			dailyStaffing.setDate(date);
			dailyStaffing.setPosition(position);
			Date open = position.getStore().getOperationTime(CalendarUtils.getDayOfWeek(date)).getOpenHour();
			Date close = position.getStore().getOperationTime(CalendarUtils.getDayOfWeek(date)).getCloseHour();
			dailyStaffing.setStartingTime(open);
			
			int size = dailySalesValue.getHalfHourSalesValues().size();
			
			// SPM#221: If sales (sales + any of secondary variables) is zero, then staffing is zero
			boolean calculateStaffing = dailySalesValue.getTotalDailyValue() != null ? dailySalesValue.getTotalDailyValue().compareTo(BigDecimal.ZERO) > 0 : false;
			double totalWorkContent = 0.0;
			int totalMinimumStaffing = 0;
			
			HalfHourStaffing aHalfHourStaffing;
			for(int i = 0; i < size; i++) {
				// SPM#221: If sales is zero, then staffing is zero
				aHalfHourStaffing = calculateStaffing ? calculateHalfHourStaffing(position, date, dailySalesValue.getHalfHourSalesValues().get(i), staffingData.get(i)) : zeroHalfHourStaffing(position, date, dailySalesValue.getHalfHourSalesValues().get(i), staffingData.get(i));

				// Only take into account store operation hours
				if(CalendarUtils.inRangeNotIncludingEndTime(aHalfHourStaffing.getTime(), open, close) || CalendarUtils.equalsTime(open, close)) {
					//:TODO: Round up Work Content to 2 decimals????
					totalWorkContent += NumberUtils.getDoubleValue(aHalfHourStaffing.getWorkContent());
					totalMinimumStaffing += NumberUtils.getIntegerValue(aHalfHourStaffing.getCalculatedStaff());
				}
				
				dailyStaffing.addHalfHourStaffing(aHalfHourStaffing);
			}
			
			// Half hours to hours
			dailyStaffing.setTotalMinimumStaffing(totalMinimumStaffing);
			dailyStaffing.setTotalWorkContent(totalWorkContent);
			
			// SPM#221: If sales is zero, then staffing is zero
			if(calculateStaffing) {
				calculateDailyTarget(dailyStaffing, position, date, dailySalesValue, initializeDailyStaffingData(position, date, dailySalesValue));
			} else {
				zeroDailyTarget(dailyStaffing, position, date, dailySalesValue, initializeDailyStaffingData(position, date, dailySalesValue));
			}
			
			return dailyStaffing;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 */
	private void setTotalServiceHours(DailyStaffing dailyStaffing, Position position) {
		dailyStaffing.setTotalServiceHours((double) NumberUtils.getIntegerValue(dailyStaffing.getTotalHourStaffing()));
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 * @param date
	 * @param dailySalesValue
	 * @param dailyStaffingData
	 */
	private void zeroDailyTarget(DailyStaffing dailyStaffing, Position position, Date date, DailySalesValue dailySalesValue, Map<DayPart, DailyStaffingPositionData> dailyStaffingData) {
		// DailyStaffing should default all values to zero!
	}	
	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 * @param date
	 * @param dailySalesValue
	 * @param dailyStaffingData
	 */
	private void calculateDailyTarget(DailyStaffing dailyStaffing, Position position, Date date, DailySalesValue dailySalesValue, Map<DayPart, DailyStaffingPositionData> dailyStaffingData) {
		setTotalServiceHours(dailyStaffing, position);
		
		setVariableTotals(dailyStaffing, dailyStaffingData, position, dailySalesValue);
		setFixedValues(dailyStaffing, position, date);
		setTotalOpening(dailyStaffing, position);
		setTotalFlexible(dailyStaffing, position);

		setTotalDailyTarget(dailyStaffing, position);
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 */
	private void setTotalDailyTarget(DailyStaffing dailyStaffing, Position position) {
		double baseTarget = NumberUtils.getDoubleValue(dailyStaffing.getBaseDailyTarget());
		dailyStaffing.setTotalDailyTarget(new Double(baseTarget));
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	private double getTrainingFactor(Position position) {
		return position != null ? NumberUtils.getDoubleValue(position.getStore().getTrainingFactor()) / 100 : 0.0;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	private double getBreakFactor(Position position) {
		return 	position != null ? NumberUtils.getDoubleValue(position.getStore().getEarnedBreakFactor()) / 100 : 0.0;

	}

	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 */
	private void setTotalOpening(DailyStaffing dailyStaffing, Position position) {
		double totalOpening = NumberUtils.getDoubleValue(dailyStaffing.getTotalVariableOpening()) +
		                      NumberUtils.getDoubleValue(dailyStaffing.getFixedOpening());
		
		dailyStaffing.setTotalOpening(new Double(totalOpening * (1 + getTrainingFactor(position)) * (1 + getBreakFactor(position))));
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 */
	private void setTotalFlexible(DailyStaffing dailyStaffing, Position position) {
		double totalService = NumberUtils.getDoubleValue(dailyStaffing.getTotalServiceHours());
		double diffWorkContentService = totalService - NumberUtils.getDoubleValue(dailyStaffing.getTotalHourWorkContent());

		double totalFlexible = NumberUtils.getDoubleValue(dailyStaffing.getTotalVariableFlexible()) + NumberUtils.getDoubleValue(dailyStaffing.getFixedFlexible());

		double available = diffWorkContentService * (NumberUtils.getDoubleValue(position.getStore().getFillInefficiency()) / 100);
		double diff = totalFlexible - available;
		diff = diff >= 0.0 ? diff : 0.0;
		
		diff += NumberUtils.getDoubleValue(dailyStaffing.getFixedPostRush());
		
		if(!position.isManager()) {
			double scheduleInefficiency = (position.getStore() != null ? NumberUtils.getDoubleValue(position.getStore().getScheduleInefficiency()) : 0.0) / 100;
			double breakFactor = getBreakFactor(position);
			double trainingFactor = getTrainingFactor(position);

			dailyStaffing.setTotalFlexible(new Double((diff * (1 + breakFactor) * (1 + trainingFactor))) + totalService * breakFactor + totalService * trainingFactor + totalService * scheduleInefficiency);
		} else {
			dailyStaffing.setTotalFlexible(new Double(diff));
		}
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 * @param position
	 * @param date
	 */
	private void setFixedValues(DailyStaffing dailyStaffing, Position position, Date date) {
		DayOfWeekData dofData = getDayOfWeekDataFor(position, date);
		if(dofData != null) {
			dailyStaffing.setFixedFlexible(dofData.getFixedFlexible());
			dailyStaffing.setFixedOpening(dofData.getFixedOpening());
			dailyStaffing.setFixedPostRush(dofData.getFixedPostRush());
			dailyStaffing.setFixedClosing(dofData.getFixedClosing());
		}
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @param dailyStaffingData
	 * @param position
	 * @param dailySalesValue
	 */
	private void setVariableTotals(DailyStaffing dailyStaffing, Map<DayPart, DailyStaffingPositionData> dailyStaffingData, Position position, DailySalesValue dailySalesValue) {
		double totalVariableFlexible = 0.0;
		double totalVariableOpening = 0.0;
		for(DailyStaffingPositionData dailyData : dailyStaffingData.values()) {
			totalVariableFlexible += dailyData.getVariableFlexible();

			totalVariableOpening += dailyData.getVariableOpening();
		}

		totalVariableFlexible = totalVariableFlexible +
			(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable2()) * NumberUtils.getDoubleValue(position.getVariable2Flexible())) +
			(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable3()) * NumberUtils.getDoubleValue(position.getVariable3Flexible())) +
			(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable4()) * NumberUtils.getDoubleValue(position.getVariable4Flexible()));
		
		totalVariableOpening = totalVariableOpening +
        	(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable2()) * NumberUtils.getDoubleValue(position.getVariable2Opening())) +
        	(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable3()) * NumberUtils.getDoubleValue(position.getVariable3Opening())) +
        	(NumberUtils.getDoubleValue(dailySalesValue.getDailyProjectionVariable4()) * NumberUtils.getDoubleValue(position.getVariable4Opening()));
		
		dailyStaffing.setTotalVariableFlexible(new Double(totalVariableFlexible));
		dailyStaffing.setTotalVariableOpening(new Double(totalVariableOpening));
	}
	
	/**
	 * 
	 * @param position
	 * @param date
	 * @param dailySalesValue
	 * @return
	 */
	private Map<DayPart, DailyStaffingPositionData> initializeDailyStaffingData(Position position, Date date, DailySalesValue dailySalesValue) {
		Map<DayPart, DailyStaffingPositionData> data = new HashMap<DayPart, DailyStaffingPositionData>();

		DayPart aDayPart;
		DailyStaffingPositionData aDailyData;
		
		for(HalfHourSalesValue aHalfHourSalesValue : dailySalesValue.getHalfHourSalesValues()) {
			aDayPart = getDayPartFor(position, aHalfHourSalesValue.getTime());
			aDailyData = data.get(aDayPart);
			if(aDailyData == null) {
				aDailyData = new DailyStaffingPositionData();
				DayPartData dayPartData = getDayPartDataFor(position, aDayPart);
				//:TODO: What if dayPartData is null???
				aDailyData.setDayPartData(dayPartData);
				aDailyData.setStore(position.getStore());
				aDailyData.setPosition(position);
				
				data.put(aDayPart, aDailyData);
			}
			aDailyData.addHalfHourProjection(aHalfHourSalesValue.getValue());
		}
		
		return data;
	}
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @param halfHourSalesValue
	 * @return
	 */
	private Map<String, List<HalfHourStaffingPositionData>> initializeHalfHourStaffingData(Store store, Date date, HalfHourSalesValue halfHourSalesValue) {
		Map<String, List<HalfHourStaffingPositionData>> data = new HashMap<String, List<HalfHourStaffingPositionData>>();
		
		HalfHourStaffingPositionData staffingData;
		String key;
		List<HalfHourStaffingPositionData> groupStaffingData;
		DayPartData dayPartData;
		
		for(Position position : store.getPositions()) {
			key = buildStaffingDataKey(position);
			if(key != null) {
				groupStaffingData = data.get(key);
				dayPartData = getDayPartDataFor(position, halfHourSalesValue.getTime());
				
				staffingData = new HalfHourStaffingPositionData(position);
				staffingData.setProjection(NumberUtils.getDoubleValue(halfHourSalesValue.getValue()));
				staffingData.setUtilization(getUtilization(position, staffingData.getProjection()));
				staffingData.setWorkContent(getWorkContent(dayPartData, date, staffingData.getProjection(), staffingData.getUtilization()));
				staffingData.setPositionMinimumStaffing(getPositionMinimumStaffing(dayPartData));
				
				if(groupStaffingData == null) {
					groupStaffingData = new ArrayList<HalfHourStaffingPositionData>();
					groupStaffingData.add(staffingData);
					data.put(key, groupStaffingData);
				} else {
					groupStaffingData.add(staffingData);
					Collections.sort(groupStaffingData, new HalfHourStaffingPositionDataComparator());
				}
			}
		}
		
		for(List<HalfHourStaffingPositionData> groupStaffing : data.values()) {
			updateAdditionalEmployee(groupStaffing);
		}
		
		return data;
	}
	
	/**
	 * 
	 * @param dayPartData
	 * @return
	 */
	private int getPositionMinimumStaffing(DayPartData dayPartData) {
		return dayPartData != null ? NumberUtils.getIntegerValue(dayPartData.getMinimunStaffing()) : 0;
	}
	
	/**
	 * 
	 * @param groupStaffing
	 */
	private void updateAdditionalEmployee(List<HalfHourStaffingPositionData> groupStaffing) {
		int total = getTotalEmployeesForGroup(groupStaffing);
		total = total < groupStaffing.size() ? total : groupStaffing.size();
		for(int i = 0; i < total; i++) {
			groupStaffing.get(i).setAdditionalEmployee(true);
		}
	}
	
	/**
	 * 
	 * @param groupStaffing
	 * @return
	 */
	private int getTotalEmployeesForGroup(List<HalfHourStaffingPositionData> groupStaffing) {
		double total = 0.0;
		for(HalfHourStaffingPositionData staffing : groupStaffing) {
			total += staffing.getWorkContentDecimalPart();
		}
		//:TODO: Check why sometimes it is returning a number larger than groupStaffing.size
		return (int) Math.ceil(total);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	private String buildStaffingDataKey(Position position) {
		if(position != null && position.getPositionGroup() != null && position.getPositionGroup().getName() != null) {
			return GROUP_TOKEN + position.getPositionGroup().getName();
		} else if(position != null && position.getName() != null) {
			return POSITION_TOKEN + position.getName();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param data
	 * @param position
	 * @return
	 */
	private HalfHourStaffingPositionData getHalfHourStaffingPositionData(Map<String, List<HalfHourStaffingPositionData>> data, Position position) {
		String key = buildStaffingDataKey(position);
		List<HalfHourStaffingPositionData> groupStaffingData = data.get(key);
		if(groupStaffingData != null) {
			return searchHalfHourStaffingPositionData(groupStaffingData, position);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param groupStaffingData
	 * @param position
	 * @return
	 */
	private HalfHourStaffingPositionData searchHalfHourStaffingPositionData(List<HalfHourStaffingPositionData> groupStaffingData, Position position) {
		for(HalfHourStaffingPositionData data : groupStaffingData) {
			if(position != null && position.getId() != null && data.getPosition() != null && position.getId().equals(data.getPosition().getId())) {
				return data;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param position
	 * @param date
	 * @param halfHourSalesValue
	 * @return
	 */
	private HalfHourStaffing calculateHalfHourStaffing(Position position, Date date, HalfHourSalesValue halfHourSalesValue, Map<String, List<HalfHourStaffingPositionData>> data) {
		HalfHourStaffing halfHourStaffing = getHalfHourStaffingInstance(halfHourSalesValue);
		halfHourStaffing.setTime(halfHourSalesValue.getTime());
		halfHourStaffing.setIndex(halfHourSalesValue.getIndex());
	
		HalfHourStaffingPositionData staffingData = getHalfHourStaffingPositionData(data, position);
		if(staffingData != null) {
			halfHourStaffing.setCalculatedStaff(new Integer(staffingData.getMinimumStaffing()));
			halfHourStaffing.setWorkContent(new Double(staffingData.getWorkContent()));
		}
		
		return halfHourStaffing;
	}
	
	private HalfHourStaffing zeroHalfHourStaffing(Position position, Date date, HalfHourSalesValue halfHourSalesValue, Map<String, List<HalfHourStaffingPositionData>> data) {
		HalfHourStaffing halfHourStaffing = getHalfHourStaffingInstance(halfHourSalesValue);
		halfHourStaffing.setTime(halfHourSalesValue.getTime());
		halfHourStaffing.setIndex(halfHourSalesValue.getIndex());
		halfHourStaffing.setCalculatedStaff(new Integer(0));
		halfHourStaffing.setWorkContent(new Double(0.0));

		return halfHourStaffing;
	}
	
	/**
	 * 
	 * @param position
	 * @param date
	 * @param time
	 * @return
	 */
	private double getUtilization(Position position, double projection) {
		double minPercent = NumberUtils.getDoubleValue(position.getUtilizationBottom());
		double maxPercent = NumberUtils.getDoubleValue(position.getUtilizationTop());
		int min = NumberUtils.getIntegerValue(position.getUtilizationMinimum());
		int max = NumberUtils.getIntegerValue(position.getUtilizationMaximum());
		double utilization;
		
		if(projection <= min) {
			utilization = minPercent;
		} else if(projection >= max) {
			utilization = maxPercent;
		} else {
			utilization = minPercent + ((projection - min) * (maxPercent - minPercent) / (max - min));
		}
		return utilization / 100;
	}
	
	/**
	 * 
	 * @param dayPartData
	 * @param date
	 * @param projection
	 * @param utilization
	 * @return
	 */
	private double getWorkContent(DayPartData dayPartData, Date date, double projection, double utilization) {
		double variableService = getVariableService(dayPartData, date);
		double fixedService = getFixedService(dayPartData, date);
		return ((projection * variableService) + fixedService) / utilization;
	}

	/**
	 * 
	 * @param dayPartData
	 * @param date
	 * @return
	 */
	private double getVariableService(DayPartData dayPartData, Date date) {
		if(dayPartData == null) {
			return 0.0;
		} else if(CalendarUtils.isWeekendDay(date)) {
			return NumberUtils.getDoubleValue(dayPartData.getWeekendGuestService());
		} else {
			return NumberUtils.getDoubleValue(dayPartData.getWeekdayGuestService());
		}
	}
	
	/**
	 * 
	 * @param dayPartData
	 * @param date
	 * @return
	 */
	private double getFixedService(DayPartData dayPartData, Date date) {
		if(dayPartData == null) {
			return 0.0;
		} else {
			return NumberUtils.getDoubleValue(dayPartData.getFixedGuestService());
		}
	}
	
	/**
	 * 
	 * @param position
	 * @param time
	 * @return
	 */
	private DayPart getDayPartFor(Position position, Date time) {
		return position.getStore() != null ? position.getStore().getDayPartFor(time) : null;
	}
	
	/**
	 * 
	 * @param position
	 * @param time
	 * @return
	 */
	private DayPartData getDayPartDataFor(Position position, Date time) {
		return position.getDayPartDataFor(getDayPartFor(position, time));
	}
	
	/**
	 * 
	 * @param posisiton
	 * @param date
	 * @return
	 */
	private DayOfWeekData getDayOfWeekDataFor(Position position, Date date) {
		return position.getDayOfWeekDataFor(CalendarUtils.getDayOfWeek(date));
	}
	
	/**
	 * 
	 * @param position
	 * @param dayPart
	 * @return
	 */
	private DayPartData getDayPartDataFor(Position position, DayPart dayPart) {
		return position.getDayPartDataFor(dayPart);
	}
	
	/**
	 * @return the staffingDao
	 */
	public StaffingDao getStaffingDao() {
		return staffingDao;
	}

	/**
	 * @param staffingDao the staffingDao to set
	 */
	public void setStaffingDao(StaffingDao staffingDao) {
		this.staffingDao = staffingDao;
	}

	/**
	 * @return the positionDao
	 */
	public PositionDao getPositionDao() {
		return positionDao;
	}

	/**
	 * @param positionDao the positionDao to set
	 */
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
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
	 * @return the storeDao
	 */
	public StoreDao getStoreDao() {
		return storeDao;
	}

	/**
	 * @param storeDao the storeDao to set
	 */
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 * @see com.laborguru.service.staffing.StaffingService#save(com.laborguru.model.StoreDailyStaffing)
	 */
	public StoreDailyStaffing save(StoreDailyStaffing dailyStaffing) {
		// Transactional behaviour given by Spring
		for(DailyProjectedStaffing ds : dailyStaffing.getStoreDailyStaffing()) {
			getStaffingDao().save(ds);
		}
		return dailyStaffing;
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @see com.laborguru.service.staffing.StaffingService#deleteDailyStaffingForDate(com.laborguru.model.Store, java.util.Date)
	 */
	public void deleteDailyStaffingForDate(Store store, Date date) {
		getStaffingDao().deleteStoreDailyStaffingByDate(store, date);
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @see com.laborguru.service.staffing.StaffingService#deleteDailyStaffingFromDate(com.laborguru.model.Store, java.util.Date)
	 */
	public void deleteDailyStaffingFromDate(Store store, Date date) {
		List<DailyProjectedStaffing> storeDailyStaffing = getStaffingDao().getStoreDailyStaffingFromDate(store, date);
		if(storeDailyStaffing != null && !storeDailyStaffing.isEmpty()) {
			getStaffingDao().deleteAll(storeDailyStaffing);
		}
	}	
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	public StoreDailyHistoricSalesStaffing getDailyHistoricSalesStaffingByDate(Store store, Date date) {
		List<DailyStaffing> managerDailyStaffings = new ArrayList<DailyStaffing>();
		double nonManagerTargetAddition = 0.0;
		
		store = getStoreDao().getStoreById(store);
		DailySalesValue dailySalesValue = getDailyHistoricSales(store, date);
		
		StoreDailyHistoricSalesStaffing storeDailyStaffing = new StoreDailyHistoricSalesStaffing(store);
		storeDailyStaffing.setDate(date);
		
		List<Map<String, List<HalfHourStaffingPositionData>>> staffingData = initializeHalfHourStaffingData(store, date, dailySalesValue);
		
		//:TODO: Improve performance? Or complicate coding?
		for(Position position : store.getPositions()) {
			DailyStaffing dailyStaffing = calculateDailyStaffing(position, date, dailySalesValue, staffingData);
			if(dailyStaffing != null) {
				storeDailyStaffing.addDailyStaffing((DailyHistoricSalesStaffing)dailyStaffing);
				
				if(isManagerDailyStaffing(dailyStaffing)) {
					managerDailyStaffings.add(dailyStaffing);
				} else {
					nonManagerTargetAddition += NumberUtils.getDoubleValue(dailyStaffing.getTotalDailyTarget());
				}
			}
		}
		
		applyOtherFactorsToManagerPositions(managerDailyStaffings, store, nonManagerTargetAddition);
		
		return storeDailyStaffing;
	}	
	
	/**
	 * 
	 * @param store
	 * @param date
	 * @return
	 */
	private DailyHistoricSales getDailyHistoricSales(Store store, Date date) {
		DailyHistoricSales dailyHistoricSales = getHistoricSalesDao().getDailyHistoricSales(store, date);
		if(dailyHistoricSales == null) {
			dailyHistoricSales = DailyHistoricSales.getEmptyDailyHistoricSalesInstance(store, date);
		}
		return dailyHistoricSales;
	}

	/**
	 * @return the historicSalesDao
	 */
	public HistoricSalesDao getHistoricSalesDao() {
		return historicSalesDao;
	}

	/**
	 * @param historicSalesDao the historicSalesDao to set
	 */
	public void setHistoricSalesDao(HistoricSalesDao historicSalesDao) {
		this.historicSalesDao = historicSalesDao;
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.staffing.StaffingService#getTotalProjectedStaffingForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Double getTotalProjectedStaffingForTimePeriod(Store store, Date startDate, Date endDate) {
		return getStaffingDao().getTotalProjectedStaffingForTimePeriod(store, startDate, endDate);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.staffing.StaffingService#getTotalProjectedStaffingByPositionForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Map<Integer, Double> getTotalProjectedStaffingByPositionForTimePeriod(
			Store store, Date startDate, Date endDate) {
		return getStaffingDao().getTotalProjectedStaffingByPositionForTimePeriod(store, startDate, endDate);
	}

	/**
	 * 
	 * @param store
	 * @param date
	 * @see com.laborguru.service.staffing.StaffingService#updateDailyStaffingForDate(com.laborguru.model.Store, java.util.Date)
	 */
	public void updateDailyStaffingForDate(Store store, Date date) {
		//:TODO: Probably update without deleting?
		deleteDailyStaffingForDate(store, date);
		getDailyStaffingByDate(store, date);
	}
}
