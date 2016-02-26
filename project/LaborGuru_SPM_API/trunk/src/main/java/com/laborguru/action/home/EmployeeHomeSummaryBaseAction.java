/*
 * File name: EmployeeHomeSummaryBaseAction.java
 * Creation date: 22/02/2009 11:00:06
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.frontend.model.PerformanceSummaryRow;
import com.laborguru.frontend.model.WeekDaySelector;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DailySalesValue;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.StoreDailyHistoricSalesStaffing;
import com.laborguru.service.actualhours.ActualHoursService;
import com.laborguru.service.historicsales.HistoricSalesService;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class EmployeeHomeSummaryBaseAction extends SpmAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WeekDaySelector weekDaySelector;

	private ScheduleService scheduleService;
	private StaffingService staffingService;
	private ProjectionService projectionService;
	private HistoricSalesService historicSalesService;
	private ActualHoursService actualHoursService;
	
	/**
	 * 
	 */
	public EmployeeHomeSummaryBaseAction() {
	}

	/**
	 * @return the weekDaySelector
	 */
	public WeekDaySelector getWeekDaySelector() {
		if(weekDaySelector == null) {
			weekDaySelector = new WeekDaySelector(getEmployeeStore().getFirstDayOfWeek());
		}
		return weekDaySelector;
	}

	/**
	 * @param weekDaySelector the weekDaySelector to set
	 */
	public void setWeekDaySelector(WeekDaySelector weekDaySelector) {
		this.weekDaySelector = weekDaySelector;
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
	 * @return the historicSalesService
	 */
	public HistoricSalesService getHistoricSalesService() {
		return historicSalesService;
	}

	/**
	 * @param historicSalesService the historicSalesService to set
	 */
	public void setHistoricSalesService(HistoricSalesService historicSalesService) {
		this.historicSalesService = historicSalesService;
	}	
	
	/**
	 * @return the actualHoursService
	 */
	public ActualHoursService getActualHoursService() {
		return actualHoursService;
	}

	/**
	 * @param actualHoursService the actualHoursService to set
	 */
	public void setActualHoursService(ActualHoursService actualHoursService) {
		this.actualHoursService = actualHoursService;
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	private Double getScheduleForWeekStartingOn(Date day) {
		BigDecimal total =getScheduleService().getTotalScheduledHoursForTimePeriod(getEmployeeStore(), day, CalendarUtils.getEndOfWeekDay(day));
		
		return new Double(NumberUtils.getDoubleValue(total));
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private Double getDailyStaffingForWeekStartingOn(Date day) {
		return getStaffingService().getTotalProjectedStaffingForTimePeriod(getEmployeeStore(), day, CalendarUtils.getEndOfWeekDay(day));
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private List<StoreDailyHistoricSalesStaffing> getDailyHistoricSalesStaffingForWeekStartingOn(Date day) {
		List<StoreDailyHistoricSalesStaffing> dailyStaffings = new ArrayList<StoreDailyHistoricSalesStaffing>(DayOfWeek.values().length);
		
		for(int i = 0; i < DayOfWeek.values().length; i++) {
			dailyStaffings.add(getStaffingService().getDailyHistoricSalesStaffingByDate(getEmployeeStore(), CalendarUtils.addOrSubstractDays(day, i)));
		}
		
		return dailyStaffings;
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	private List<DailyProjection> getDailyProjectionsForWeekStartingOn(Date day) {
		return getProjectionService().getDailyProjections(getEmployeeStore(), day, CalendarUtils.addOrSubstractDays(day, DayOfWeek.values().length - 1));
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	private BigDecimal getDailyHistoricSalesForWeekStartingOn(Date day) {
		return getHistoricSalesService().getTotalHistoricSalesForTimePeriod(getEmployeeStore(), day, CalendarUtils.getEndOfWeekDay(day));		
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private Double getDailyActualHoursForWeekStartingOn(Date day) {
		return getActualHoursService().getTotalActualHoursForTimePeriod(getEmployeeStore(), day, CalendarUtils.getEndOfWeekDay(day));
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private BigDecimal getLaborCostForWeekStartingOn(Date day) {
		return getScheduleService().getTotalScheduledLaborCostForTimePeriod(getEmployeeStore(), day, CalendarUtils.getEndOfWeekDay(day));
	}
	
	/**
	 * 
	 * @param salesValues
	 * @return
	 */
	private BigDecimal calculateVolume(List<? extends DailySalesValue> salesValues) {
		if(salesValues != null) {
			BigDecimal total = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
			for(DailySalesValue sv : salesValues) {
				if(sv != null) {
					total = total.add(sv.getDailySalesValue());
				}
			}
			return total;
		} else {
			return SpmConstants.BD_ZERO_VALUE;
		}
	}

	/**
	 * 
	 * @param dailyStaffings
	 * @return
	 */
	private Double calculateHistoricSalesTarget(List<StoreDailyHistoricSalesStaffing> dailyStaffings) {
		double total = 0.0;
		
		if(dailyStaffings != null) {
			for(StoreDailyHistoricSalesStaffing dailyStaffing : dailyStaffings) {
				if(dailyStaffing != null) {
					total += dailyStaffing.getTotalDailyTarget().doubleValue();
				}
			}
		}
		
		return new Double(total);
	}	
	
	/**
	 * 
	 * @param startingWeekDay
	 * @param row
	 * @return
	 */
	protected PerformanceSummaryRow buildProjectedPerformanceSummaryRow(Date startingWeekDay, PerformanceSummaryRow row) {
		row.setDate(startingWeekDay);
		
		row.setProjectedVolume(calculateVolume(getDailyProjectionsForWeekStartingOn(startingWeekDay)));
		row.setProjectedTarget(getDailyStaffingForWeekStartingOn(startingWeekDay));
		row.setProjectedScheduled(getScheduleForWeekStartingOn(startingWeekDay));
		
		return row;
	}	

	/**
	 * 
	 * @param startingWeekDay
	 * @return
	 */
	protected PerformanceSummaryRow buildActualPerformanceSummaryRow(Date startingWeekDay, PerformanceSummaryRow row) {
		row.setDate(startingWeekDay);
		
		row.setActualVolume(getDailyHistoricSalesForWeekStartingOn(startingWeekDay));
		row.setActualTarget(calculateHistoricSalesTarget(getDailyHistoricSalesStaffingForWeekStartingOn(startingWeekDay)));
		row.setActualScheduled(getDailyActualHoursForWeekStartingOn(startingWeekDay));
		
		row.setLaborCost(getLaborCostForWeekStartingOn(startingWeekDay));
		
		return row;
	}		
}
