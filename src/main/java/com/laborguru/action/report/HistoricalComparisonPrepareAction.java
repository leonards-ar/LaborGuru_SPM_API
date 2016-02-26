package com.laborguru.action.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.ReportTypes;
import com.laborguru.model.report.TotalHistoricalHour;
import com.laborguru.model.report.TotalHour;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.report.ReportService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.FusionXmlDataConverter;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="fbarreraoro">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HistoricalComparisonPrepareAction extends SpmAction implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5732711967413965288L;
    private static final int LAST_DAYS = 7;
	private Integer index;
	private Date startDate;
	private Date endDate;
	
	private List<TotalHistoricalHour> totalHistoricalHours;

	private String scheduleHeader, targetHeader, reportTitle, scheduleTrendHeader, targetTrendHeader; 
	
	private String xmlValues, xmlPercentValues;
	private String dataType;
	
	private ReportService reportService;
	private ReferenceDataService referenceDataService;
	private FusionXmlDataConverter fusionXmlDataConverter;

	public void prepareShowFirstReport(){
		//starts 4 weeks ago and ends today
		setEndDate(CalendarUtils.todayWithoutTime());
		
		setStartDate(CalendarUtils.addOrSubstractDays(getEndDate(), -27));
	}
	
	public String showFirstReport() {
		return SpmActionResult.INPUT.getResult();
	}
	
	public String showReport() {
		ReportTypes reportType = ReportTypes.getReportTypeByIndex(getIndex());
		switch(reportType) {
		case performanceEfficiency: setTotalHours(getReportService().getPerformanceEfficiencyReport(getEmployeeStore(), getStartDate(), getEndDate(), false));
									setReportTitle("report.historicalcomparison.performanceEfficiency.title.label");
									setScheduleHeader("report.historicalComparison.performanceEfficiency.schedule.label");
									setTargetHeader("report.historicalComparison.performanceEfficiency.target.label");
									setScheduleTrendHeader("report.hisotricalComparison.performanceEfficiency.actualTrend.label");
									setTargetTrendHeader("report.historicalComparison.performanceEfficiency.idealTrend.label");
									break;
		case schedulingEfficiency: setTotalHours(getReportService().getWeeklyTotalHours(getEmployeeStore(), getStartDate(), getEndDate(), false));
									setReportTitle("report.historicalcomparison.scheduleEfficiency.title.label");
									setScheduleHeader("report.historicalComparison.scheduleEfficiency.schedule.label");
									setTargetHeader("report.historicalComparison.scheduleEfficiency.target.label");
									setScheduleTrendHeader("report.historicalComparison.scheduleEfficiency.scheduleTrend.label");
									setTargetTrendHeader("report.historicalComparison.scheduleEfficiency.targetTrend.label");
									
									break;
		case scheduleExecutionEfficiency: setTotalHours(getReportService().getScheduleExecutionEfficiencyReport(getEmployeeStore(), getStartDate(), getEndDate(), false));
									setReportTitle("report.historicalcomparison.performanceEfficiency.title.label");
									setScheduleHeader("report.historicalComparison.scheduleExecutionEfficiency.schedule.label");
									setTargetHeader("report.historicalComparison.scheduleExecutionEfficiency.target.label");
									setScheduleTrendHeader("report.historicalComparison.scheduleExecutionEfficiency.scheduleTrend.label");
									setTargetTrendHeader("report.historicalComparison.scheduleExecutionEfficiency.targetTrend.label");
									
									break;
		case forecastEfficiency: setTotalHours(getReportService().getForecastEfficiencyReport(getEmployeeStore(), getStartDate(), getEndDate()));
								setReportTitle("report.historicalcomparison.forecastEfficiency.title.label");
								setScheduleHeader("report.historicalComparison.forecastEfficiency.schedule.label");
								setTargetHeader("report.historicalComparison.forecastEfficiency.target.label");
								setScheduleTrendHeader("report.historicalComparison.forecastEfficiency.scheduleTrend.label");
								setTargetTrendHeader("report.historicalComparison.forecastEfficiency.targetTrend.label");
								break;
		default: setTotalHours(null);
				 break;
		}
		
		generateXmlGraph();
		return SpmActionResult.SHOW.getResult();
	}
	

	
	private void setTotalHours(List<TotalHour> totalHours) {
		
		totalHistoricalHours = new LinkedList<TotalHistoricalHour>();
		
		BigDecimal scheduleSum = SpmConstants.BD_ZERO_VALUE;
		BigDecimal targetSum = SpmConstants.BD_ZERO_VALUE;
		
		for(int i=0; i < totalHours.size(); i++) {
			
			TotalHistoricalHour totalHistoricalHour = new TotalHistoricalHour(totalHours.get(i));
			
			scheduleSum = scheduleSum.add(totalHistoricalHour.getTotalHour().getSchedule());
			targetSum = targetSum.add(totalHistoricalHour.getTotalHour().getTarget());
			
			if(i >= LAST_DAYS - 1) {
				
				totalHistoricalHour.setActualTrend(scheduleSum.divide(new BigDecimal(LAST_DAYS),SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE));
				totalHistoricalHour.setIdealTrend(targetSum.divide(new BigDecimal(LAST_DAYS),SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE));

				scheduleSum = scheduleSum.subtract(totalHours.get(i - (LAST_DAYS - 1)).getSchedule());
				targetSum = targetSum.subtract(totalHours.get(i - (LAST_DAYS - 1)).getTarget());
					
			} else {
				
				totalHistoricalHour.setActualTrend(null);
				totalHistoricalHour.setIdealTrend(null);
				
			}
			
			totalHistoricalHours.add(totalHistoricalHour);
			
		}
		
	}
	
	private void generateXmlGraph(){
		
		setXmlValues(getFusionXmlDataConverter().historicalComparisonXmlConverter(getTotalHistoricalHours(), getTexts("defaultmessages"), getScheduleHeader(), getTargetHeader(), getReportTitle(), getScheduleTrendHeader(), getTargetTrendHeader()));
		setXmlPercentValues(getFusionXmlDataConverter().historicalComparisonPercentXmlConverter(getTotalHistoricalHours(), getTexts("defaultmessages"), "report.historicalComparison.difference.percentage.graph", "report.historicalComparison.difference.trendpercentage.graph", "report.historicalComparison.difference.percentageLabel.graph"));
	}
	
	public ReportTypes[] getReportTypes(){
		return ReportTypes.values();
	}
	

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
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

	/**
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
    /**
     * 
     * @return the scheduleTrendHeader
     */
	public String getScheduleTrendHeader() {
		return scheduleTrendHeader;
	}

	/**
	 * 
	 * @param scheduleTrendHeader
	 */
	public void setScheduleTrendHeader(String scheduleTrendHeader) {
		this.scheduleTrendHeader = scheduleTrendHeader;
	}

	/**
	 * 
	 * @return the target Trend Header
	 */
	public String getTargetTrendHeader() {
		return targetTrendHeader;
	}

	/**
	 * 
	 * @param targetTrendHeader
	 */
	public void setTargetTrendHeader(String targetTrendHeader) {
		this.targetTrendHeader = targetTrendHeader;
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
	
	/**
	 * @return the totalHours
	 */
	public List<TotalHistoricalHour> getTotalHistoricalHours() {
		return totalHistoricalHours;
	}
	
	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHistoricalHours(List<TotalHistoricalHour> totalHistoricalHours) {
		this.totalHistoricalHours = totalHistoricalHours;
	}
	
	/**
	 * @return the xmlValues
	 */
	public String getXmlValues() {
		return xmlValues;
	}
	
	/**
	 * @param xmlValues the xmlValues to set
	 */
	public void setXmlValues(String xmlValues) {
		this.xmlValues = xmlValues;
	}

	/**
	 * @return the xmlValues
	 */
	public String getXmlPercentValues() {
		return xmlPercentValues;
	}
	
	/**
	 * @param xmlValues the xmlValues to set
	 */
	public void setXmlPercentValues(String xmlPercentValues) {
		this.xmlPercentValues = xmlPercentValues;
	}
	
	/**
	 * @return the ReferenceService
	 */
	public ReferenceDataService getReferenceDataService(){
		return this.referenceDataService;
	}
	
	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}
	
	public Map getReportDataTypeMap(){
		return referenceDataService.getHistoricalReportDataType();
	}

	/**
	 * @return the fusionXmlDataConverter
	 */
	public FusionXmlDataConverter getFusionXmlDataConverter() {
		return fusionXmlDataConverter;
	}

	/**
	 * 
	 * @return dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @param fusionXmlDataConverter the fusionXmlDataConverter to set
	 */
	public void setFusionXmlDataConverter(
			FusionXmlDataConverter fusionXmlDataConverter) {
		this.fusionXmlDataConverter = fusionXmlDataConverter;
	}

	public void prepare() throws Exception {
	}
	
}

