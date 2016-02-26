package com.laborguru.action.report;

import java.util.List;

import com.laborguru.model.report.TotalCustomerManagerHour;
import com.laborguru.service.report.ReportCustomerService;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class ReportCustomerPrepareAction extends ReportManagerBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2748619782373332120L;
	
	private ReportCustomerService reportCustomerService;
	
	private List<TotalCustomerManagerHour> totalManagerHours;
	
	private static String reportObject = "report.manager.region";
	
	@Override
	protected void forecastEfficiency() {
		setTotalManagerHours(getReportCustomerService().getForecastEfficiencyReport(getCustomer(), getStartDate(), getEndDate()));
		
	}

	@Override
	protected void performanceEfficiency() {
		setTotalManagerHours(getReportCustomerService().getPerformanceEfficiencyReport(getCustomer(), getStartDate(), getEndDate()));
	}

	@Override
	protected void scheduleExecutionEfficiency() {
		setTotalManagerHours(getReportCustomerService().getScheduleExecutionEfficiencyReport(getCustomer(), getStartDate(), getEndDate()));
		
	}

	@Override
	protected void schedulingEfficiency() {
		setTotalManagerHours(getReportCustomerService().getWeeklyTotalHours(getCustomer(), getStartDate(), getEndDate()));
		
	}	
	/**
	 * @return the totalManagerHours
	 */
	public List<TotalCustomerManagerHour> getTotalManagerHours() {
		return totalManagerHours;
	}

	/**
	 * @param totalManagerHours the totalManagerHours to set
	 */
	public void setTotalManagerHours(List<TotalCustomerManagerHour> totalManagerHours) {
		this.totalManagerHours = totalManagerHours;
	}


	/**
	 * @return the reportCustomerService
	 */
	public ReportCustomerService getReportCustomerService() {
		return reportCustomerService;
	}

	/**
	 * @param reportCustomerService the reportCustomerService to set
	 */
	public void setReportCustomerService(ReportCustomerService reportCustomerService) {
		this.reportCustomerService = reportCustomerService;
	}
	
    /**
     * @return the reportObject
     */
    public String getReportObject() {
        return reportObject;
    }
}
