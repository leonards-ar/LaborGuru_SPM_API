package com.laborguru.service.report;

import java.util.Date;
import java.util.List;

import com.laborguru.model.Region;
import com.laborguru.model.report.TotalRegionManagerHour;
import com.laborguru.service.Service;

public interface ReportRegionService extends Service{

	/**
	 * Retrieves a Performance Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalRegionManagerHour> getPerformanceEfficiencyReport(Region region, Date start, Date end);
	
	/**
	 * Retrieves a Schedule Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalRegionManagerHour> getWeeklyTotalHours(Region region, Date start, Date end);
	
	/**
	 * Retrieve a Schedule Execution Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalRegionManagerHour> getScheduleExecutionEfficiencyReport(Region region, Date start, Date end);
	
	/**
	 * Retrieves ForecasEfficiencyReport for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalRegionManagerHour> getForecastEfficiencyReport(Region region, Date start, Date end);
	
}
