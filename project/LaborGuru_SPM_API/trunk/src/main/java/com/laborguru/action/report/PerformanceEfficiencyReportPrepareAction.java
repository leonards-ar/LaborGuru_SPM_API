package com.laborguru.action.report;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.util.CalendarUtils;



public class PerformanceEfficiencyReportPrepareAction extends WeeklyReportBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643112299027218254L;

	protected void getReport() {
		setTotalHours(getReportService().getPerformanceEfficiencyReport(
				getEmployeeStore(), getWeekDaySelector().getStartingWeekDay(), CalendarUtils.getEndOfWeekDay(getWeekDaySelector().getStartingWeekDay()), true));		
	}

	protected void getReportByPosition() {
		throw new SpmUncheckedException("Method not implemented", ErrorEnum.NOT_IMPLEMENTED_METHOD);
	}

	protected void getReportByService() {
		throw new SpmUncheckedException("Method not implemented", ErrorEnum.NOT_IMPLEMENTED_METHOD);
	}
	
	protected void setAxisLabels() {
		setScheduleAxisName("report.performance.scheduled.label");
		setTargetAxisName("report.performance.target.label");
	}
	
}
