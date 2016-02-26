package com.laborguru.service.report;

import java.util.Date;
import java.util.List;

import com.laborguru.model.Customer;
import com.laborguru.model.report.TotalCustomerManagerHour;
import com.laborguru.service.Service;

public interface ReportCustomerService extends Service {

	/**
	 * Retrieves a Performance Efficiency Report for Customers
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalCustomerManagerHour> getPerformanceEfficiencyReport(Customer customer, Date start, Date end);
	
	/**
	 * Retrieves a Schedule Efficiency Report for Customers
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalCustomerManagerHour> getWeeklyTotalHours(Customer customer, Date start, Date end);
	
	/**
	 * Retrieve a Schedule Execution Efficiency Report for Customers
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalCustomerManagerHour> getScheduleExecutionEfficiencyReport(Customer customer, Date start, Date end);
	
	/**
	 * Retrieves ForecasEfficiencyReport for Customers
	 * @param customer
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalCustomerManagerHour> getForecastEfficiencyReport(Customer customer, Date start, Date end);
	
}
