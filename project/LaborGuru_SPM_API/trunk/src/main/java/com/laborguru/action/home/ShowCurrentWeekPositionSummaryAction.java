/*
 * File name: ShowCurrentWeekSummaryAction.java
 * Creation date: 22/02/2009 10:52:15
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.PositionPerformanceSummaryRow;
import com.laborguru.model.Position;
import com.laborguru.service.position.PositionService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ShowCurrentWeekPositionSummaryAction extends EmployeeHomeSummaryBaseAction {
	private static final Logger log = Logger.getLogger(ShowCurrentWeekPositionSummaryAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3248835945937051460L;
	
	private PositionService positionService;
	
	private List<PositionPerformanceSummaryRow> currentDayPositionSummary;
	private Double totalProjectedScheduled;
	private Double totalProjectedTarget;
	private Double totalProjectedDifference;
	
	/**
	 * 
	 */
	public ShowCurrentWeekPositionSummaryAction() {
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	/**
	 * 
	 */
	private void buildPositionSummary() {
		Date start = getWeekDaySelector().getStartingWeekDay();
		Date end = CalendarUtils.getEndOfWeekDay(start);
		
		List<Position> positions = getPositionService().getPositionsByStore(getEmployeeStore());
		Map<Integer, Double> staffingTotals = getStaffingService().getTotalProjectedStaffingByPositionForTimePeriod(getEmployeeStore(), start, end);
		Map<Integer, BigDecimal> scheduleTotals = getScheduleService().getTotalScheduledHoursByPositionForTimePeriod(getEmployeeStore(), start, end); 

		PositionPerformanceSummaryRow row;
		for(Position position : positions) {
			row = new PositionPerformanceSummaryRow();
			
			row.setPosition(position);
			if(scheduleTotals != null && scheduleTotals.containsKey(position.getId())) {
				row.setProjectedScheduled(new Double(NumberUtils.getDoubleValue(scheduleTotals.get(position.getId()))));
			} else {
				row.setProjectedScheduled(new Double(0.0));
			}

			if(staffingTotals != null && staffingTotals.containsKey(position.getId())) {
				row.setProjectedTarget(new Double(NumberUtils.getDoubleValue(staffingTotals.get(position.getId()))));
			} else {
				row.setProjectedTarget(new Double(0.0));
			}

			getCurrentDayPositionSummary().add(row);
		}
	}

	/**
	 * 
	 */
	private void setPositionTotals() {
		double target = 0.0;
		double scheduled = 0.0;
		for(PositionPerformanceSummaryRow row : getCurrentDayPositionSummary()) {
			target += NumberUtils.getDoubleValue(row.getProjectedTarget());
			scheduled += NumberUtils.getDoubleValue(row.getProjectedScheduled());
		}
		setTotalProjectedScheduled(new Double(scheduled));
		setTotalProjectedTarget(new Double(target));
		setTotalProjectedDifference(new Double(scheduled - target));
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		try {
			buildPositionSummary();
		} catch(Throwable ex) {
			log.error("Could not retrieve current day position performance summary", ex);
		}
		setPositionTotals();
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the currentDayPositionSummary
	 */
	public List<PositionPerformanceSummaryRow> getCurrentDayPositionSummary() {
		if(currentDayPositionSummary == null) {
			setCurrentDayPositionSummary(new ArrayList<PositionPerformanceSummaryRow>());
		}
		return currentDayPositionSummary;
	}

	/**
	 * @param currentDayPositionSummary the currentDayPositionSummary to set
	 */
	public void setCurrentDayPositionSummary(List<PositionPerformanceSummaryRow> currentDayPositionSummary) {
		this.currentDayPositionSummary = currentDayPositionSummary;
	}

	/**
	 * @return the totalProjectedTarget
	 */
	public Double getTotalProjectedTarget() {
		return totalProjectedTarget;
	}

	/**
	 * @param totalProjectedTarget the totalProjectedTarget to set
	 */
	public void setTotalProjectedTarget(Double totalProjectedTarget) {
		this.totalProjectedTarget = totalProjectedTarget;
	}

	/**
	 * @return the totalProjectedDifference
	 */
	public Double getTotalProjectedDifference() {
		return totalProjectedDifference;
	}

	/**
	 * @param totalProjectedDifference the totalProjectedDifference to set
	 */
	public void setTotalProjectedDifference(Double totalProjectedDifference) {
		this.totalProjectedDifference = totalProjectedDifference;
	}

	/**
	 * @param totalProjectedScheduled the totalProjectedScheduled to set
	 */
	public void setTotalProjectedScheduled(Double totalProjectedScheduled) {
		this.totalProjectedScheduled = totalProjectedScheduled;
	}

	/**
	 * @return the totalProjectedScheduled
	 */
	public Double getTotalProjectedScheduled() {
		return totalProjectedScheduled;
	}	

	
}
