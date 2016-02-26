package com.laborguru.action.report;

import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.util.CalendarUtils;

public class SchedulingEfficiencyReportPrepareAction extends WeeklyReportBaseAction {

	private static final long serialVersionUID = 1L;

	protected void getReport() {
		setTotalHours(getReportService().getWeeklyTotalHours(
				getEmployeeStore(), getWeekDaySelector().getStartingWeekDay(), 
				CalendarUtils.getEndOfWeekDay(getWeekDaySelector().getStartingWeekDay()), true));
	}

	protected void getReportByPosition() {
		
		Position position = new Position();
		position.setId(getItemId());
		setTotalHours(getReportService().getWeeklyTotalHoursByPosition(
				getEmployeeStore(), position,
				getWeekDaySelector().getStartingWeekDay(), CalendarUtils.getEndOfWeekDay(getWeekDaySelector().getStartingWeekDay())));
	}

	protected void getReportByService() {
		PositionGroup positionGroup = new PositionGroup();
		positionGroup.setId(getItemId());
		setTotalHours(getReportService().getWeeklyTotalHoursByService(
				getEmployeeStore(), positionGroup,
				getWeekDaySelector().getStartingWeekDay(), CalendarUtils.getEndOfWeekDay(getWeekDaySelector().getStartingWeekDay())));
	}
	
	protected void setAxisLabels() {
		setScheduleAxisName("report.weeklytotalhours.scheduled.label");
		setTargetAxisName("report.weeklytotalhours.target.label");
	}
	
	/**
	 * Weather the following totals should be calculated:
	 * totalVplhSchedule, totalVplhTarget, totalProjectedSales, totalScheduleLaborPercentage, totalTargetLaborPercentage
	 * @return
	 */
	protected boolean calculateExtendedTotals() {
		return true;
	}
}
