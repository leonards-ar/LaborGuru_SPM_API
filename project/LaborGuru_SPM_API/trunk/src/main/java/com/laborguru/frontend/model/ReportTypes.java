package com.laborguru.frontend.model;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public enum ReportTypes {

	performanceEfficiency ("report.historicalComparison.performanceEfficiency.title", 0),
	schedulingEfficiency("report.historicalComparison.scheduleEfficiency.title", 1),
	scheduleExecutionEfficiency("report.historicalComparison.scheduleExecutionEfficiency.title", 2),
	forecastEfficiency("report.historicalComparison.forecastEfficiency.title", 3);
	
	private String title;
	private Integer index;
	
	private ReportTypes(String name, Integer index) {
		this.title = name;
		this.index = index;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public Integer getIndex(){
		return this.index;
	}
	
	public static ReportTypes getReportTypeByIndex(int index) {
		for(ReportTypes reportType: values()){
			if(reportType.getIndex().intValue() == index ) {
				return reportType;
			}
		}
		return null;
	}
	
	public String getName() {
		return this.name();
	}
	
	public String toString() {
		return this.name();
	}
	

}
