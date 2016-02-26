package com.laborguru.action.report;

import java.util.List;

import com.laborguru.model.Region;
import com.laborguru.model.report.TotalRegionManagerHour;
import com.laborguru.service.report.ReportRegionService;

public class ReportRegionPrepareAction extends ReportManagerBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4388691116146562677L;

	private ReportRegionService reportRegionService;
	
	private List<TotalRegionManagerHour> totalManagerHours;
	
	private Region region;
	
	private static String reportObject = "report.manager.area";
	
	@Override
	protected void forecastEfficiency() {
		setTotalManagerHours(getReportRegionService().getForecastEfficiencyReport(getRegion(), getStartDate(), getEndDate()));
	}

	@Override
	protected void performanceEfficiency() {
		setTotalManagerHours(getReportRegionService().getPerformanceEfficiencyReport(getRegion(), getStartDate(), getEndDate()));
	}

	@Override
	protected void scheduleExecutionEfficiency() {
		setTotalManagerHours(getReportRegionService().getScheduleExecutionEfficiencyReport(getRegion(), getStartDate(), getEndDate()));
	}

	@Override
	protected void schedulingEfficiency() {
		setTotalManagerHours(getReportRegionService().getWeeklyTotalHours(getRegion(), getStartDate(), getEndDate()));
	}

	/**
	 * @return the reportRegionService
	 */
	public ReportRegionService getReportRegionService() {
		return reportRegionService;
	}

	/**
	 * @param reportRegionService the reportRegionService to set
	 */
	public void setReportRegionService(ReportRegionService reportRegionService) {
		this.reportRegionService = reportRegionService;
	}

	/**
	 * @return the totalManagerHours
	 */
	public List<TotalRegionManagerHour> getTotalManagerHours() {
		return totalManagerHours;
	}

	/**
	 * @param totalManagerHours the totalManagerHours to set
	 */
	public void setTotalManagerHours(List<TotalRegionManagerHour> totalManagerHours) {
		this.totalManagerHours = totalManagerHours;
	}

	/**
	 * @return the region
	 */
	public Region getRegion() {
		if(region == null) {
			return super.getRegion();
		}
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

    /**
     * @return the reportObject
     */
    public String getReportObject() {
        return reportObject;
    }
	
}
