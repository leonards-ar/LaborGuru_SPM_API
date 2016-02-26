package com.laborguru.service.report;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.Area;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.Store;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.model.StoreSchedule;
import com.laborguru.model.report.TotalAreaManagerHour;
import com.laborguru.service.area.AreaService;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.report.dao.ReportDao;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

public class ReportAreaServiceBean implements ReportAreaService {
	private static Logger log = Logger.getLogger(ReportAreaServiceBean.class);
	
	private ReportDao reportDao;
	private AreaService areaService;
	private StaffingService staffingService;
	private ProjectionService projectionService;
	private ScheduleService scheduleService;
	
	public List<TotalAreaManagerHour> getForecastEfficiencyReport(Area area, Date start, Date end) {
		try{
			List<TotalAreaManagerHour> actualSales = reportDao.getActualSalesByArea(area, start, end);
			List<TotalAreaManagerHour> projections = getDailyProjections(area, start, end);
			
			return mergeForecast(actualSales, projections);
		}catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}		
	}

	public List<TotalAreaManagerHour> getPerformanceEfficiencyReport(Area area, Date start, Date end) {
		try{
			
			List<TotalAreaManagerHour> actualSales = getReportDao().getActualSalesByArea(area, start, end);
			List<TotalAreaManagerHour> actualHours = getReportDao().getActualHoursByArea(area, start, end);			
			List<TotalAreaManagerHour> minimumStaffing = getActualMinimumStaffing(area, start, end);
			List<TotalAreaManagerHour> wages = getStoreWages(area, start, end);
			
			return merge(actualSales, actualHours, minimumStaffing, wages, null);
		
		} catch(SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}

	public List<TotalAreaManagerHour> getScheduleExecutionEfficiencyReport(Area area, Date start, Date end) {
		try{

			List<TotalAreaManagerHour> actualSales = getReportDao().getActualSalesByArea(area, start, end);
			List<TotalAreaManagerHour> scheduleTotalHours = getReportDao().getScheduleTotalHourByArea(area, start, end);
			List<TotalAreaManagerHour> actualHours = getReportDao().getActualHoursByArea(area, start, end);
			List<TotalAreaManagerHour> wages = getStoreWages(area, start, end);
			List<TotalAreaManagerHour> projections = getDailyProjections(area, start, end);
		
			return merge(actualSales, scheduleTotalHours, actualHours, wages, projections);
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}		
	}

	public List<TotalAreaManagerHour> getWeeklyTotalHours(Area area, Date start, Date end) {
		try {
			List<TotalAreaManagerHour> actualSales = getDailyProjections(area, start, end);//getReportDao().getActualSalesByArea(area, start, end);
			List<TotalAreaManagerHour> scheduleTotalHours = getReportDao().getScheduleTotalHourByArea(area, start, end);
			List<TotalAreaManagerHour> targetTotalHours = getReportDao().getTargetTotalHourByArea(area, start, end);
			List<TotalAreaManagerHour> wages = getStoreWages(area, start, end);
			return merge(actualSales, scheduleTotalHours, targetTotalHours, wages, null); 
		} catch (SQLException e) {
			log.error("An SQLError has occurred", e);
			throw new SpmUncheckedException(e.getCause(), e.getMessage(),
					ErrorEnum.GENERIC_DATABASE_ERROR);
		}
	}

	/**
	 * Returns the minimum staffing for each region. This is the sum of all the stores of each region
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<TotalAreaManagerHour> getActualMinimumStaffing(Area area, Date startDate, Date endDate){
		List<TotalAreaManagerHour> totalHours = new ArrayList<TotalAreaManagerHour>();
		
		Area tmpArea = getAreaService().getAreaById(area);
		for(Store store: tmpArea.getStores()) {
			TotalAreaManagerHour totalManagerHour = new TotalAreaManagerHour();
			totalManagerHour.setStore(store);
			totalHours.add(totalManagerHour);
			BigDecimal targetStores = SpmConstants.BD_ZERO_VALUE;
			for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
				StoreDailyHistoricSalesStaffing saleStaffing = getStaffingService().getDailyHistoricSalesStaffingByDate(store, date);
				targetStores = targetStores.add(new BigDecimal(saleStaffing.getTotalDailyTarget()));
			}
			totalManagerHour.setSchedule(targetStores);
		}
		
		return totalHours;
	}

	private List<TotalAreaManagerHour> getDailyProjections(Area area, Date startDate, Date endDate){
		List<TotalAreaManagerHour> totalHours = new ArrayList<TotalAreaManagerHour>();
		
		Area tmpArea= getAreaService().getAreaById(area);

			for(Store store: tmpArea.getStores()) {
				TotalAreaManagerHour totalManagerHour = new TotalAreaManagerHour();
				totalManagerHour.setStore(store);
				totalHours.add(totalManagerHour);
				
				BigDecimal targetStores = SpmConstants.BD_ZERO_VALUE;
				for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
					DailyProjection dailyProjection = getProjectionService().getDailyProjection(store, date);
					targetStores = targetStores.add(dailyProjection != null? dailyProjection.getDailyProjectionValue() :  SpmConstants.BD_ZERO_VALUE);
				}

				totalManagerHour.setSales(targetStores);
				totalManagerHour.setSchedule(targetStores);
		}
		return totalHours;
	}	
	
	private List<TotalAreaManagerHour> getStoreWages(Area area, Date startDate, Date endDate) {
		List<TotalAreaManagerHour> totalHours = new ArrayList<TotalAreaManagerHour>();
		
		Area tmpArea = getAreaService().getAreaById(area);
		for(Store store: tmpArea.getStores()) {
			TotalAreaManagerHour totalManagerHour = new TotalAreaManagerHour();
			totalManagerHour.setStore(store);
			totalHours.add(totalManagerHour);
			
			Double totalWage = SpmConstants.DOUBLE_ZERO_VALUE;
			Double totalShiftHours = SpmConstants.DOUBLE_ZERO_VALUE;
			
			for(Date date = startDate; CalendarUtils.equalsOrGreaterDate(endDate, date); date = CalendarUtils.addOrSubstractDays(date,1)){
				StoreSchedule schedule = getScheduleService().getStoreScheduleByDate(store, date);
				if(schedule != null) {
					totalWage += schedule.getTotalWage();
					totalShiftHours += schedule.getTotalShiftHours();
				} else {
					totalWage += new Double(0.0);
					totalShiftHours += new Double(0.0);
				}
			}
			
			if(totalShiftHours > 0) {
				totalManagerHour.setAverageWage(
						new Double(NumberUtils.getDoubleValue(totalWage) / NumberUtils.getDoubleValue(totalShiftHours))
				);			
			} else {
				totalManagerHour.setAverageWage(new Double(0.0));
			}
			
			totalManagerHour.setTotalWage(totalWage);
			
		}
		
	
		return totalHours;
	}
	
	private List<TotalAreaManagerHour> merge(List<TotalAreaManagerHour> actualSales, List<TotalAreaManagerHour> scheduleHours, List<TotalAreaManagerHour> targetHours, List<TotalAreaManagerHour> wages, List<TotalAreaManagerHour> projections) {
		
		List<TotalAreaManagerHour> totalHours = new ArrayList<TotalAreaManagerHour>();
		for(TotalAreaManagerHour total: actualSales) {
			if(total.getSales() == null) total.setSales(SpmConstants.BD_ZERO_VALUE);
			TotalAreaManagerHour scheduleHour = getTotalHour(total.getStore(), scheduleHours); 
			total.setSchedule((scheduleHour.getSchedule() != null)? scheduleHour.getSchedule(): SpmConstants.BD_ZERO_VALUE);
			TotalAreaManagerHour targetHour = getTotalHour(total.getStore(), targetHours);
			total.setTarget(((targetHour.getSchedule() != null)? targetHour.getSchedule(): SpmConstants.BD_ZERO_VALUE));
			if(wages != null) {
				TotalAreaManagerHour wage = getTotalHour(total.getStore(), wages);
				if(wage != null) {
					total.setAverageWage(wage.getAverageWage());
					total.setTotalWage(wage.getTotalWage());
				} else {
					total.setAverageWage(SpmConstants.DOUBLE_ZERO_VALUE);
					total.setTotalWage(SpmConstants.DOUBLE_ZERO_VALUE);
				}
				
				total.setAverageVariable(total.getStore().getMainVariableAverage());
			}
			
            if(projections != null) {
                TotalAreaManagerHour projectionHour = getTotalHour(total.getStore(), projections);
                total.setProjections(((projectionHour.getSales() != null)? projectionHour.getSales() : SpmConstants.BD_ZERO_VALUE));
            }			
			
			totalHours.add(total);
		}
		
		return totalHours;
	}	
	
	private List<TotalAreaManagerHour> mergeForecast(List<TotalAreaManagerHour> actualHours, List<TotalAreaManagerHour> scheduleHours) {

	    List<TotalAreaManagerHour> totalHours = new ArrayList<TotalAreaManagerHour>();
        for(TotalAreaManagerHour total: actualHours) {
            TotalAreaManagerHour scheduleHour = getTotalHour(total.getStore(), scheduleHours); 
            total.setSchedule((scheduleHour.getSchedule() != null)? scheduleHour.getSchedule(): SpmConstants.BD_ZERO_VALUE);
            total.setTarget(((total.getSales() != null)? total.getSales(): SpmConstants.BD_ZERO_VALUE));
            
            totalHours.add(total);
        }
        
        return totalHours;	    
	    
	}
	private TotalAreaManagerHour getTotalHour(Store store, List<TotalAreaManagerHour> totalManagerHours) {
		
		for(TotalAreaManagerHour totalManagerHour: totalManagerHours) {
			if(store.getId().equals(totalManagerHour.getStore().getId())) {
				return totalManagerHour;
			}
		}
		
		return null;
	}
	
	/**
	 * @return the reportDao
	 */
	public ReportDao getReportDao() {
		return reportDao;
	}

	/**
	 * @param reportDao the reportDao to set
	 */
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	/**
	 * @return the areaService
	 */
	public AreaService getAreaService() {
		return areaService;
	}

	/**
	 * @param areaService the areaService to set
	 */
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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
	 * @return the scheduleService
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * @param scheduleService the scheduleService to set
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	

}
