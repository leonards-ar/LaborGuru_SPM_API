package com.laborguru.service.report;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.DailyFlashDetail;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.model.StoreSchedule;
import com.laborguru.model.StoreVariableDefinition;
import com.laborguru.model.report.DailyFlashHour;
import com.laborguru.model.report.DailyFlashObject;
import com.laborguru.model.report.FixedLaborHoursReport;
import com.laborguru.model.report.TotalHour;
import com.laborguru.model.report.TotalHourByPosition;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.report.dao.ReportDao;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.service.store.dao.StoreDao;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 * 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
public class ReportServiceBean implements ReportService {
	private static final Logger log = Logger.getLogger(ReportServiceBean.class);

	private StaffingService staffingService;
	private ProjectionService projectionService;
	private ScheduleService scheduleService;
	private DailyFlashService dailyFlashService;
	private ReportDao reportDao;
	private StoreDao storeDao;

	/**
	 * @param store
	 * @param startingWeekDate
	 * @return
	 * @see com.laborguru.service.report.ReportService#getWeeklyTotalHours(com.laborguru.model.Store, java.util.Date)
	 */
	public List<TotalHour> getWeeklyTotalHours(Store store, Date start, Date end, boolean getProjections) {

			try {

				List<DailyProjection> projections;
				if(getProjections){
					projections = projectionService.getAdjustedDailyProjectionForAWeek(store, start);
				} else {
					projections = new ArrayList<DailyProjection>();
				}
				List<TotalHour> scheduleTotalHours = reportDao.getScheduleWeeklyTotalHour(store, start, end);
				List<TotalHour> targetTotalHours = reportDao.getTargetWeeklyTotalHour(store, start, end);
				
				return getMergedTotalHours(store, scheduleTotalHours, targetTotalHours, projections, start, end); 
			} catch (SQLException e) {
				log.error("An SQLError has occurred", e);
				throw new SpmUncheckedException(e.getCause(), e.getMessage(),
						ErrorEnum.GENERIC_DATABASE_ERROR);
			}
	}

	/**
	 * @param store
	 * @param position
	 * @param startingWeekDate
	 * @return
	 * @see com.laborguru.service.report.ReportService#getWeeklyTotalHoursByPosition(com.laborguru.model.Store, com.laborguru.model.Position, java.util.Date)
	 */
	public List<TotalHour> getWeeklyTotalHoursByPosition(Store store, Position position, Date start, Date end){
		try {

			List<DailyProjection> projections = projectionService.getAdjustedDailyProjectionForAWeek(store, start);
			List<TotalHour> scheduleTotalHours = reportDao.getScheduleWeeklyTotalHourByPosition(store, position, start, end);
			List<TotalHour> targetTotalHours = reportDao.getTargetWeeklyTotalHourByPosition(store, position, start, end);
			
			return getMergedTotalHours(store, scheduleTotalHours, targetTotalHours, projections, start, end);
			
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
		
	}
	
	public List<TotalHour> getWeeklyTotalHoursByService(Store store, PositionGroup positiongroup, Date start, Date end){
		try {
			List<DailyProjection> projections = projectionService.getAdjustedDailyProjectionForAWeek(store, start);
			List<TotalHour> scheduleTotalHours = reportDao.getScheduleWeeklyTotalHourByService(store, positiongroup, start, end);
			List<TotalHour> targetTotalHours = reportDao.getTargetWeeklyTotalHourByService(store, positiongroup, start, end);
			return getMergedTotalHours(store, scheduleTotalHours, targetTotalHours, projections, start, end);
			
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	/**
	 * @param store
	 * @param startingWeekDate
	 * @return
	 * @see com.laborguru.service.report.ReportService#getForecastByPosition(com.laborguru.model.Store, java.util.Date)
	 */
	public List<TotalHourByPosition> getForecastByPosition(Store store, List<Position> positions, Date startingWeekDate) {
		try{
			List<TotalHourByPosition> scheduleTotalHourByPosition = reportDao.getScheduleForecastByPosition(store, startingWeekDate, CalendarUtils.addOrSubstractDays(startingWeekDate, 6));
			List<TotalHourByPosition> targetTotalHourByPosition = reportDao.getTargetForecastByPosition(store, startingWeekDate, CalendarUtils.addOrSubstractDays(startingWeekDate, 6));
			List<TotalHourByPosition> result = new ArrayList<TotalHourByPosition>();
			for(Position position: positions) {
				TotalHour schedule = getTotalHourByPosition(position, scheduleTotalHourByPosition);
				TotalHour target = getTotalHourByPosition(position, targetTotalHourByPosition);
				TotalHourByPosition thByPosition = new TotalHourByPosition();
				thByPosition.setPosition(position);
				thByPosition.setTotalHour(new TotalHour());
				if(schedule != null){
					thByPosition.getTotalHour().setSchedule(schedule.getSchedule());
				} else {
					thByPosition.getTotalHour().setSchedule(SpmConstants.BD_ZERO_VALUE);
				}
				
				if(target != null){
					thByPosition.getTotalHour().setTarget(target.getTarget());
				} else {
					thByPosition.getTotalHour().setTarget(SpmConstants.BD_ZERO_VALUE);
				}
				result.add(thByPosition);
			}
			
			return result;
			
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}

	public List<TotalHour> getHalfHourlyReport(Store store, Date date) {
		try{
			//Initialize Minimum staffing in case it doesn't exist.
			getStaffingService().getDailyStaffingByDate(store, date);
			DailyProjection projections = projectionService.getDailyProjection(store, date);
			Date startHour = store.getStoreScheduleStartHour(CalendarUtils.getDayOfWeek(date));
			Date endHour = store.getStoreScheduleEndHour(CalendarUtils.getDayOfWeek(date));
			
			List<TotalHour> schedule = reportDao.getHalfHourlySchedule(store, date, startHour, endHour);
			List<TotalHour> target = reportDao.getHalfHourlyMinimumStaffing(store, date);
			
			List<HalfHourProjection> halfHourProjections = new ArrayList<HalfHourProjection>();
			
			if(projections != null) {
				halfHourProjections = projections.getHalfHourProjections();
			}
			
			return getMergedHalfHours(store, startHour, endHour, halfHourProjections, schedule, target);
			
		}catch(SQLException e){
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public List<TotalHour> getHalfHourlyReportByPosition(Store store, Position position, Date date){
		try{
			//Initialize Minimum staffing in case it doesn't exist.			
			getStaffingService().getDailyStaffingByDate(store, date);
			
			DailyProjection projections = projectionService.getDailyProjection(store, date);
			Date startHour = store.getStoreScheduleStartHour(CalendarUtils.getDayOfWeek(date));
			Date endHour = store.getStoreScheduleEndHour(CalendarUtils.getDayOfWeek(date));
			
			List<TotalHour> schedule = reportDao.getHalfHourlyScheduleByPosition(store, position, date, startHour, endHour);
			List<TotalHour> target = reportDao.getHalfHourlyMinimumStaffingByPosition(store, position, date);
			
			List<HalfHourProjection> halfHourProjections = new ArrayList<HalfHourProjection>();
			
			if(projections != null) {
				halfHourProjections = projections.getHalfHourProjections();
			}

			return getMergedHalfHours(store, startHour, endHour, halfHourProjections, schedule, target);
		} catch(SQLException e){
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public List<TotalHour> getHalfHourlyReportByService(Store store, PositionGroup positionGroup, Date date){
		try{
			//Initialize Minimum staffing in case it doesn't exist.			
			getStaffingService().getDailyStaffingByDate(store, date);

			DailyProjection projections = projectionService.getDailyProjection(store, date);
			Date startHour = store.getStoreScheduleStartHour(CalendarUtils.getDayOfWeek(date));
			Date endHour = store.getStoreScheduleEndHour(CalendarUtils.getDayOfWeek(date));
			
			List<TotalHour> schedule = reportDao.getHalfHourlyScheduleByService(store, positionGroup, date, startHour, endHour);
			List<TotalHour> target = reportDao.getHalfHourlyMinimumStaffingByService(store, positionGroup, date);
			
			List<HalfHourProjection> halfHourProjections = new ArrayList<HalfHourProjection>();
			
			if(projections != null) {
				halfHourProjections = projections.getHalfHourProjections();
			}
	
			return getMergedHalfHours(store, startHour, endHour, halfHourProjections, schedule, target);
			
		} catch(SQLException e){
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public List<TotalHour> getPerformanceEfficiencyReport(Store store, Date start, Date end, boolean getSales) {
		try{
			List<HistoricSales> actualSales;
			if(getSales) {
				actualSales = reportDao.getActualSales(store, start, end);
			} else {
				actualSales = new ArrayList<HistoricSales>();
			}
			
			List<TotalHour> actualHours = reportDao.getActualHours(store, start, end);			
			List<TotalHour> minimumStaffing = getActualMinimumStaffing(store, start, end);
			 
			return getMergedTotalEfficiencyHours(store, actualSales, actualHours, minimumStaffing, start, end, false);
			
		} catch(SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
		
	}
	
	public List<TotalHour> getScheduleExecutionEfficiencyReport(Store store, Date start, Date end, boolean getSales){
		try{
			List<HistoricSales> actualSales;
			if(getSales) {
				actualSales = reportDao.getActualSales(store, start, end);
			} else {
				actualSales = new ArrayList<HistoricSales>();
			}
			
			List<TotalHour> scheduleTotalHours = reportDao.getScheduleWeeklyTotalHour(store, start, end);
			List<TotalHour> actualHours = reportDao.getActualHours(store, start, end);
		
			// Bug 299 - actualHours are stored in schedule, but should be moved to target
			return getMergedTotalEfficiencyHours(store, actualSales, scheduleTotalHours, actualHours, start, end, true);
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}

	public FixedLaborHoursReport getFixedLaborHoursReport(Store store, Date date) {
		try{
		FixedLaborHoursReport fixedLaborHoursReport = new FixedLaborHoursReport();
		
		fixedLaborHoursReport.setSchedule(reportDao.getScheduleFixedLaborHours(store, date));
		
		fixedLaborHoursReport.setTarget(reportDao.getTargetFixedLaborHours(store, date));
		
		return fixedLaborHoursReport;
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public FixedLaborHoursReport getFixedLaborHoursReportByPosition(Store store, Date date, Position position) {
		try{
		FixedLaborHoursReport fixedLaborHoursReport = new FixedLaborHoursReport();
		
		fixedLaborHoursReport.setSchedule(reportDao.getScheduleFixedLaborHoursByPosition(store, date, position));
		
		fixedLaborHoursReport.setTarget(reportDao.getTargetFixedLaborHoursByPosition(store, date, position));
		
		return fixedLaborHoursReport;
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public FixedLaborHoursReport getFixedLaborHoursReportByService(Store store, Date date, PositionGroup positionGroup) {
		try{
		FixedLaborHoursReport fixedLaborHoursReport = new FixedLaborHoursReport();
		
		fixedLaborHoursReport.setSchedule(reportDao.getScheduleFixedLaborHoursByService(store, date, positionGroup));
		
		fixedLaborHoursReport.setTarget(reportDao.getTargetFixedLaborHoursByService(store, date, positionGroup));
		
		return fixedLaborHoursReport;
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}	
	
	public List<TotalHour> getForecastEfficiencyReport(Store store, Date start, Date end) {
		try{
			List<HistoricSales> actualSales = reportDao.getActualSales(store, start, end);
			List<DailyProjection> projections = getProjectionService().getDailyProjections(store, start, end);
			return getMergedForecastHours(store, start, end, actualSales, projections);
			
		}catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}
	
	public DailyFlashObject getDailyFlashReport(Store store, Date date, List<DailyFlashDetail>details) {
		try{
			//Initialize Minimum staffing in case it doesn't exist.
			getStaffingService().getDailyStaffingByDate(store, date);
			store = storeDao.getStoreById(store);
			DailyProjection projections = projectionService.getDailyProjection(store, date);
			OperationTime storeOperationTime = store.getOperationTime(CalendarUtils.getDayOfWeek(date));
			Date startHour = storeOperationTime != null? storeOperationTime.getOpenHour() : null;
			Date endHour = storeOperationTime != null? storeOperationTime.getCloseHour() : null;

			List<TotalHour> schedule = reportDao.getHalfHourlySchedule(store, date, startHour, endHour);
			List<TotalHour> target = reportDao.getHalfHourlyMinimumStaffing(store, date);

			List<HalfHourProjection> halfHourProjections = new ArrayList<HalfHourProjection>();
			
			if(projections != null) {
				halfHourProjections = projections.getHalfHourProjections();
				
			}
			DailyFlashObject dfo = getMergedDailyFlashHours(store, startHour, endHour, halfHourProjections, schedule, target, details);
			
			if(projections != null){
				dfo.setVariable2(projections.getDailyProjectionVariable2());
				dfo.setVariable3(projections.getDailyProjectionVariable3());
				dfo.setVariable4(projections.getDailyProjectionVariable4());
			}
			return dfo;
			
		}catch(SQLException e){
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}	
	
	private List<TotalHour> getActualMinimumStaffing(Store store, Date startDate, Date endDate){
		List<TotalHour> totalHours = new ArrayList<TotalHour>();
		for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
			TotalHour totalhour = new TotalHour();
			
			StoreDailyHistoricSalesStaffing saleStaffing = getStaffingService().getDailyHistoricSalesStaffingByDate(store, date);
			
			totalhour.setDay(date);
			totalhour.setTarget(new BigDecimal(saleStaffing.getTotalDailyTarget()));
			
			totalHours.add(totalhour);
			
		}
		return totalHours;
	}
	
	private TotalHour getTotalHourByDay(Date day, List<TotalHour> list) {
		for(TotalHour th: list) {
			if(day.equals(th.getDay())) {
				return th;
			}
		}
		return null;
	}
	
	private TotalHour getTotalHourByTime(Date hour, List<TotalHour> list) {
		for(TotalHour th: list) {
			if(CalendarUtils.equalsTime(hour, th.getDay())){
				return th;
			}
		}
		return null;
	}
	
	private DailyFlashDetail getDailyFlashDetailByTime(Date hour, List<DailyFlashDetail> list){
		for(DailyFlashDetail detail: list) {
			if(CalendarUtils.equalsTime(hour, detail.getHour())) {
				return detail;
			}
		}
		return null;
	}
	
	private TotalHour getTotalHourByPosition(Position position, List<TotalHourByPosition> totalHours) {
		for(TotalHourByPosition th: totalHours) {
			if(th.getPosition().getId().equals(position.getId())) {
				return th.getTotalHour();
			}
		}
		return null;
	}
	
	private List<TotalHour> getMergedTotalHours(Store store, List<TotalHour> scheduleTotalHours, List<TotalHour> targetTotalHours, List<DailyProjection>projections, Date startDate, Date endDate) {
		
		List<TotalHour> totalHours = new ArrayList<TotalHour>();
		
		for (Date date=startDate; date.compareTo(endDate) <= 0; date=CalendarUtils.addOrSubstractDays(date, 1)) {
			Double storeWages[] = getStoreWages(store, date);
			TotalHour totalhour = new TotalHour(store.getMainVariableAverage(), storeWages[0], storeWages[1]);
			StoreVariableDefinition varDef = store.getSecondaryVariableDefinition(1);
			totalhour.setStoreAverageVariable2(varDef != null ? varDef.getAverage() : SpmConstants.DOUBLE_ZERO_VALUE);
			varDef = store.getSecondaryVariableDefinition(2);
			totalhour.setStoreAverageVariable3(varDef != null ? varDef.getAverage() : SpmConstants.DOUBLE_ZERO_VALUE);
			varDef = store.getSecondaryVariableDefinition(3);
			totalhour.setStoreAverageVariable4(varDef != null ? varDef.getAverage() : SpmConstants.DOUBLE_ZERO_VALUE);
			
			totalhour.setDay(date);
			DailyProjection dp = getDailyProjectionByDate(date, projections);
			if(dp != null){
				totalhour.setSales(dp.getDailyProjectionValue());
				totalhour.setVariable2(dp.getDailyProjectionVariable2());
				totalhour.setVariable3(dp.getDailyProjectionVariable3());
				totalhour.setVariable4(dp.getDailyProjectionVariable4());
			} else {
				totalhour.setSales(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable2(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable3(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable4(SpmConstants.BD_ZERO_VALUE);
			}

			totalhour.setSchedule(getScheduleValue(getTotalHourByDay(date, scheduleTotalHours)));
			totalhour.setTarget(getTargetValue(getTotalHourByDay(date, targetTotalHours)));
			
			totalHours.add(totalhour);
		}
		
		return totalHours;

	}
	
	private List<TotalHour> getMergedForecastHours(Store store, Date startDate, Date endDate, List<HistoricSales> actualSales, List<DailyProjection> projections){
		List<TotalHour> totalHours = new ArrayList<TotalHour>();
		
		for (Date date=startDate; date.compareTo(endDate) <= 0; date=CalendarUtils.addOrSubstractDays(date, 1)) {
			TotalHour th = new TotalHour();
			th.setDay(date);
			
			DailyProjection dp = getDailyProjectionByDate(date, projections);
			if(dp != null) {
				th.setSchedule(dp.getTotalDailyValue());
			} else {
				th.setSchedule(SpmConstants.BD_ZERO_VALUE);
			}
			
			HistoricSales hs = getHistoricSalesByDate(date, actualSales);
			if(hs != null) {
				th.setTarget(hs.getTotalValue());
			} else {
				th.setTarget(SpmConstants.BD_ZERO_VALUE);
			}
			
			totalHours.add(th);
		}
		
		return totalHours;
		
	}
	private List<TotalHour> getMergedHalfHours(Store store, Date startHour, Date endHour, List<HalfHourProjection> halfHourProjections, List<TotalHour> schedule, List<TotalHour> target){
		List <TotalHour> totalHours = new ArrayList<TotalHour>();
		
		//if start hour is greater or equals than end hour, it means that 
		//the store opens one day and closes the following date.
		//So a day should be added to the endHour in order to iterate through
		//all the hours the store is open.
		if(startHour.compareTo(endHour) >= 0) {
			endHour = CalendarUtils.addOrSubstractDays(endHour, 1);
		}
		
		for(Date hour = startHour; hour.compareTo(endHour) <= 0; hour = CalendarUtils.addOrSubstractMinutes(hour, 30)) {
			
			
			TotalHour totalhour = new TotalHour();
			totalhour.setDay(hour);
			HalfHourProjection hp = getHalfHourProyectionByHour(hour, halfHourProjections);
			if(hp != null){
				totalhour.setSales(hp.getValue());
			} else {
				totalhour.setSales(SpmConstants.BD_ZERO_VALUE);
			}
			
			totalhour.setSchedule(getScheduleValue(getTotalHourByTime(hour, schedule)));
			totalhour.setTarget(getTargetValue(getTotalHourByTime(hour, target)));
			
			totalHours.add(totalhour);
		}
		
		return totalHours;
	}
	
	private List<TotalHour> getMergedTotalEfficiencyHours(Store store, List<HistoricSales> actualSales, List<TotalHour> scheduleTotalHours, List<TotalHour> targetTotalHours, Date startDate, Date endDate, boolean targetInScheduled) {
		List<TotalHour> totalHours = new ArrayList<TotalHour>();
		
		for (Date date=startDate; date.compareTo(endDate) <= 0; date=CalendarUtils.addOrSubstractDays(date, 1)) {
			
			TotalHour totalhour = new TotalHour();
			totalhour.setDay(date);
			HistoricSales hs = getHistoricSalesByDate(date, actualSales);
			if(hs != null){
				totalhour.setSales(hs.getMainValue());
				totalhour.setVariable2(hs.getSecondValue());
				totalhour.setVariable3(hs.getThirdValue());
				totalhour.setVariable4(hs.getFourthValue());
			} else {
				totalhour.setSales(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable2(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable3(SpmConstants.BD_ZERO_VALUE);
				totalhour.setVariable4(SpmConstants.BD_ZERO_VALUE);
			}

			totalhour.setSchedule(getScheduleValue(getTotalHourByDay(date, scheduleTotalHours)));
			
			// Bug 299 - Target can be in schedule for some reports
			if(targetInScheduled) {
				totalhour.setTarget(getScheduleValue(getTotalHourByDay(date, targetTotalHours)));
			} else {
				totalhour.setTarget(getTargetValue(getTotalHourByDay(date, targetTotalHours)));
			}
			
			totalHours.add(totalhour);
		}
		
		return totalHours;
	}
	
	private DailyFlashObject getMergedDailyFlashHours(Store store, Date startHour, Date endHour, List<HalfHourProjection> halfHourProjections, List<TotalHour> scheduleHours, List<TotalHour> targetHours,List<DailyFlashDetail> details){
		DailyFlashObject dfo = new DailyFlashObject();
		List <DailyFlashHour> totalHours = new ArrayList<DailyFlashHour>();
		
		//if start hour is greater or equals than end hour, it means that 
		//the store opens one day and closes the following date.
		//So a day should be added to the endHour in order to iterate through
		//all the hours the store is open.
		if(startHour.compareTo(endHour) >= 0) {
			endHour = CalendarUtils.addOrSubstractDays(endHour, 1);
		}
		
		for(Date hour = startHour; hour.compareTo(endHour) <= 0; hour = CalendarUtils.addOrSubstractMinutes(hour, 30)) {
			
			DailyFlashHour dailyFlashHour = new DailyFlashHour();
			
			dailyFlashHour.setDay(hour);
			dailyFlashHour.setDayPart(store.getDayPartFor(hour));
			
			HalfHourProjection hp = getHalfHourProyectionByHour(hour, halfHourProjections);
			
			dailyFlashHour.setSales((hp != null) ? hp.getValue() : SpmConstants.BD_ZERO_VALUE);
			
			
			DailyFlashDetail df = getDailyFlashDetailByTime(hour, details);
			dailyFlashHour.setActualHour(df != null && df.getActualHour() != null? new BigDecimal(df.getActualHour()) : SpmConstants.BD_ZERO_VALUE);
			dailyFlashHour.setActualSale(df != null && df.getActualSale() != null? new BigDecimal(df.getActualSale()) : SpmConstants.BD_ZERO_VALUE);

			dailyFlashHour.setScheduleHour(getScheduleValue(getTotalHourByTime(hour, scheduleHours)));
			
			BigDecimal targetHour = getTargetValue(getTotalHourByTime(hour, targetHours));
			//If it's the first time, then new Projection is the same as the Schedule Projection
			//So the IdealHour will be the same as the targetHour.
			
			dailyFlashHour.setTargetHour(targetHour);
			dailyFlashHour.setIdealHour(df != null && df.getIdealHour() != null? new BigDecimal(df.getIdealHour()) : targetHour);

			totalHours.add(dailyFlashHour);
			
		}
		dfo.setDailyFlashHours(totalHours);
		return dfo;
	}	
	
	private DailyProjection getDailyProjectionByDate(Date date, List<DailyProjection> projections) {
		for(DailyProjection dp: projections) {
			if(date.equals(dp.getSalesDate())) {
				return dp;
			}
		
		}
		return null;
	}
	
	private HistoricSales getHistoricSalesByDate(Date date, List<HistoricSales> historicSales){
		for(HistoricSales hs: historicSales) {
			Date hsDate = CalendarUtils.removeTimeFromDate(hs.getDateTime());
			if(date.equals(hsDate)) {
				return hs;
			}
		}
		return null;
	}

	private HalfHourProjection getHalfHourProyectionByHour(Date date, List<HalfHourProjection> halfHourProjections) {
		for(HalfHourProjection hp: halfHourProjections) {
			if(CalendarUtils.equalsTime(date, hp.getTime())) {
				return hp;
			}
		}
		return null;
	}
	
	private HistoricSales getHistoricSalesByHour(Date date, List<HistoricSales> actualSales){
		for(HistoricSales hs: actualSales){
			if(CalendarUtils.equalsTime(date, hs.getDateTime())){
				return hs;
			}
		}
		return null;
	}

	private BigDecimal getScheduleValue(TotalHour th) {
		if(th != null){
			if(th.getSchedule() != null) {
				return th.getSchedule();
			}
		}
		return SpmConstants.BD_ZERO_VALUE;
	}

	private BigDecimal getTargetValue(TotalHour th) {
		if(th != null){
			if(th.getTarget() != null) {
				return th.getTarget();
			}
		}
		return SpmConstants.BD_ZERO_VALUE;
	}
	
	/**
	 * @return the reportDao
	 */
	public ReportDao getReportDao() {
		return reportDao;
	}

	/**
	 * @param reportDao
	 *            the reportDao to set
	 */
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
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

	/**
	 * @return the projectionService
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}

	/**
	 * @param projectionService the projectionService to set
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}
	
	/**
	 * 
	 * @param store
	 * @param day
	 * @return
	 */
	private Double[] getStoreWages(Store store, Date day) {
		Double storeWages[] = new Double[2];
		StoreSchedule schedule = getScheduleService().getStoreScheduleByDate(store, day);
		if(schedule != null) {
			storeWages[0] = schedule.getAverageWage();
			storeWages[1] = schedule.getTotalWage();
		} else {
			storeWages[0] = new Double(0.0);
			storeWages[1] = new Double(0.0);
		}
		return storeWages;
	}

	/**
	 * 
	 * @return
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * 
	 * @param scheduleService
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	
	public DailyFlashService getDailyFlashService() {
		return dailyFlashService;
	}

	public void setDailyFlashService(DailyFlashService dailyFlashService) {
		this.dailyFlashService = dailyFlashService;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}
	

}
