package com.laborguru.service.report;

import java.util.Date;
import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.model.report.TotalAreaManagerHour;
import com.laborguru.service.Service;

public interface ReportAreaService extends Service {

	/**
	 * Retrieves a Performance Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalAreaManagerHour> getPerformanceEfficiencyReport(Area area, Date start, Date end);
	
	/**
	 * Retrieves a Schedule Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalAreaManagerHour> getWeeklyTotalHours(Area area, Date start, Date end);
	
	/**
	 * Retrieve a Schedule Execution Efficiency Report for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalAreaManagerHour> getScheduleExecutionEfficiencyReport(Area area, Date start, Date end);
	
	/**
	 * Retrieves ForecasEfficiencyReport for Regions
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalAreaManagerHour> getForecastEfficiencyReport(Area area, Date start, Date end);
	
}
