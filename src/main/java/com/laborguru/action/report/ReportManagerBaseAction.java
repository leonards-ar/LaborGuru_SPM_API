package com.laborguru.action.report;

import java.util.Date;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.ReportTypes;
import com.laborguru.util.CalendarUtils;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public abstract class ReportManagerBaseAction extends SpmAction implements Preparable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8381935927184225232L;
	
	private Date startDate;
	private Date endDate;
	private String selectView;
	
	private String scheduleHeader, targetHeader, reportTitle;
	
	public void prepareShowFirstReport(){
		//starts 7 days ago and ends today
		setEndDate(CalendarUtils.todayWithoutTime());
		
		setStartDate(CalendarUtils.addOrSubstractDays(getEndDate(), -7));
		
	}
	
	public String showFirstReport() {
		return SpmActionResult.INPUT.getResult();
	}

	public String showReport() {
		ReportTypes reportType = ReportTypes.valueOf(getSelectView());
		
		switch(reportType) {
		case performanceEfficiency: performanceEfficiency();
			setReportTitle("report.manager.performanceEfficiency.title.label");
			setScheduleHeader("report.historicalComparison.performanceEfficiency.schedule.label");
			setTargetHeader("report.historicalComparison.performanceEfficiency.target.label");
			break;
		case schedulingEfficiency: schedulingEfficiency();
		    setReportTitle("report.manager.scheduleEfficiency.title.label");
		    setScheduleHeader("report.historicalComparison.scheduleEfficiency.schedule.label");
		    setTargetHeader("report.historicalComparison.scheduleEfficiency.target.label");		
			break;
		case scheduleExecutionEfficiency: scheduleExecutionEfficiency();
			setReportTitle("report.manager.scheduleExecutionEfficiency.title.label");
			setScheduleHeader("report.historicalComparison.scheduleExecutionEfficiency.schedule.label");
			setTargetHeader("report.historicalComparison.scheduleExecutionEfficiency.target.label");
			break;
		case forecastEfficiency: forecastEfficiency();
			setReportTitle("report.manager.forecastEfficiency.title.label");
			setScheduleHeader("report.historicalComparison.forecastEfficiency.schedule.label");
			setTargetHeader("report.historicalComparison.forecastEfficiency.target.label");
			break;
		}
		
		return SpmActionResult.SHOW.getResult();
		
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the selectView
	 */
	public String getSelectView() {
		return selectView;
	}
	/**
	 * @param selectView the selectView to set
	 */
	public void setSelectView(String selectView) {
		this.selectView = selectView;
	}

	/**
	 * @return the scheduleHeader
	 */
	public String getScheduleHeader() {
		return scheduleHeader;
	}
	/**
	 * @param scheduleHeader the scheduleHeader to set
	 */
	public void setScheduleHeader(String scheduleHeader) {
		this.scheduleHeader = scheduleHeader;
	}
	/**
	 * @return the targetHeader
	 */
	public String getTargetHeader() {
		return targetHeader;
	}
	/**
	 * @param targetHeader the targetHeader to set
	 */
	public void setTargetHeader(String targetHeader) {
		this.targetHeader = targetHeader;
	}
	/**
	 * @return the reportTitle
	 */
	public String getReportTitle() {
		return reportTitle;
	}
	
	public ReportTypes[] getReportTypes() {
		return ReportTypes.values();
	}
		
	/**
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
    public void prepare() throws Exception {
	}		
    
    protected abstract void performanceEfficiency();
	protected abstract void schedulingEfficiency();
	protected abstract void scheduleExecutionEfficiency();
	protected abstract void forecastEfficiency();
	

}
