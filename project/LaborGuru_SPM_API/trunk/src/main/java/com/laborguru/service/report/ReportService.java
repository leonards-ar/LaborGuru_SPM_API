package com.laborguru.service.report;

import java.util.Date;
import java.util.List;

import com.laborguru.model.DailyFlashDetail;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.model.report.DailyFlashObject;
import com.laborguru.model.report.FixedLaborHoursReport;
import com.laborguru.model.report.TotalHour;
import com.laborguru.model.report.TotalHourByPosition;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ReportService extends Service {

	/**
	 * Retrieves the Hours that has been projected and the hours that has been consumed.
	 * @param store
	 * @param start
	 * @param end
	 * @param getProjections
	 * @return
	 */
	List<TotalHour> getWeeklyTotalHours(Store store, Date start, Date end, boolean getProjections);
	
	/**
	 * Retrieves the Hours that has been projected and the hours that has been consumed by position.
	 * @param store
	 * @param position
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalHour> getWeeklyTotalHoursByPosition(Store store, Position position, Date start, Date end);
	
	/**
	 * Retrieves the Hours that has been projected and the hours that has been consumed by position group.
	 * @param store
	 * @param positiongroup
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalHour> getWeeklyTotalHoursByService(Store store, PositionGroup positiongroup, Date start, Date end);
	
	/**
	 * Retrieves the total forecast for each position in the store.
	 * @param store
	 * @param startingWeekDate
	 * @return
	 */
	List<TotalHourByPosition> getForecastByPosition(Store store, List<Position> positions, Date startingWeekDate);
	
	/**
	 * Retrieves the half hours that has been projected and the hours that has been consumed
	 * @param store
	 * @param date
	 * @return
	 */
	List<TotalHour> getHalfHourlyReport(Store store, Date date);
	
	/**
	 * Retrieves the half hours filtered by a Position.
	 * @param store
	 * @param position
	 * @param date
	 * @return
	 */
	List<TotalHour> getHalfHourlyReportByPosition(Store store, Position position, Date date);
	
	/**
	 * Retrieves the half hours filtered by a Position Group.
	 * @param store
	 * @param positionGroup
	 * @param date
	 * @return
	 */
	List<TotalHour> getHalfHourlyReportByService(Store store, PositionGroup positionGroup, Date date);
	
	/**
	 * Retrieves the Performance Efficiency Report
	 * @param store
	 * @param start
	 * @param end
	 * @param getSales
	 * @return
	 */
	List<TotalHour> getPerformanceEfficiencyReport(Store store, Date start, Date end, boolean getSales);
	
	/**
	 * Retrieves the Schedule Execution Efficiency
	 * @param store
	 * @param start
	 * @param end
	 * @param getSales
	 * @return
	 */
	List<TotalHour> getScheduleExecutionEfficiencyReport(Store store, Date start, Date end, boolean getSales);
	
	/**
	 * Retrieves the Fixed Labor Hours for a store.
	 * @param store
	 * @param date
	 * @return
	 */
	FixedLaborHoursReport getFixedLaborHoursReport(Store store, Date date);
	
	/**
	 * Retrieves the Fixed Labor Hours for a store filtered by position.
	 * @param store
	 * @param date
	 * @return
	 */
	FixedLaborHoursReport getFixedLaborHoursReportByPosition(Store store, Date date, Position position);	
	
	/**
	 * Retrieves the Fixed Labor Hours for a store filtered by position group.
	 * @param store
	 * @param date
	 * @return
	 */
	FixedLaborHoursReport getFixedLaborHoursReportByService(Store store, Date date, PositionGroup positionGroup);
	
	/**
	 * Retrieves the Forecast Efficiency for a Store
	 * @param store
	 * @param start
	 * @param end
	 * @return
	 */
	List<TotalHour> getForecastEfficiencyReport(Store store, Date start, Date end);
	
	/**
	 * Retrives the projection information of the day
	 * @param store
	 * @param date
	 * @return
	 */
	DailyFlashObject getDailyFlashReport(Store store, Date date, List<DailyFlashDetail> details);
		
}
