package com.laborguru.action.report;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.report.FixedLaborHoursReport;
import com.laborguru.service.report.ReportService;
import com.opensymphony.xwork2.Preparable;

public class FixedLaborHoursReportPrepareAction extends ScheduleReportPrepareAction implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9088006513047913789L;

	private FixedLaborHoursReport fixedLaborHoursReport;
	
	private ReportService reportService;
	
	public void prepare() throws Exception {
	}

	public String showReport() {
		initWeekSelectorValues();
		
		if (getItemId() == null) {
			getFixedLaborHours();
		} else {
			if ("byPosition".equals(getSelectedGrouping())) {
				getFixedLaborHoursByPosition();
			} else if ("byService".equals(getSelectedGrouping())) {
				getFixedLaborHoursByService();
			} else {
				getFixedLaborHours();
			}
		}		
		
		return SpmActionResult.SHOW.getResult();
	}

	private void getFixedLaborHours() {
		setFixedLaborHoursReport(reportService.getFixedLaborHoursReport(getEmployeeStore(), getWeekDaySelector().getSelectedDay()));
	}
	
	private void getFixedLaborHoursByPosition() {
		Position position = new Position();
		position.setId(getItemId());
		setFixedLaborHoursReport(reportService.getFixedLaborHoursReportByPosition(getEmployeeStore(), getWeekDaySelector().getSelectedDay(), position));
	}
	
	private void getFixedLaborHoursByService() {
		PositionGroup positionGroup = new PositionGroup();
		positionGroup.setId(getItemId());
		setFixedLaborHoursReport(reportService.getFixedLaborHoursReportByService(getEmployeeStore(), getWeekDaySelector().getSelectedDay(), positionGroup));
	}
	
	/**
	 * @return the fixedLaborHoursReport
	 */
	public FixedLaborHoursReport getFixedLaborHoursReport() {
		return fixedLaborHoursReport;
	}

	/**
	 * @param fixedLaborHoursReport the fixedLaborHoursReport to set
	 */
	public void setFixedLaborHoursReport(FixedLaborHoursReport fixedLaborHoursReport) {
		this.fixedLaborHoursReport = fixedLaborHoursReport;
	}

	/**
	 * @return the reportService
	 */
	public ReportService getReportService() {
		return reportService;
	}

	/**
	 * @param reportService the reportService to set
	 */
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	

}
