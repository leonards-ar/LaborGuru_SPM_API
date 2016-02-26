package com.laborguru.service.report.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Region;
import com.laborguru.model.Store;
import com.laborguru.model.report.FixedLaborHours;
import com.laborguru.model.report.TotalAreaManagerHour;
import com.laborguru.model.report.TotalCustomerManagerHour;
import com.laborguru.model.report.TotalHour;
import com.laborguru.model.report.TotalHourByPosition;
import com.laborguru.model.report.TotalRegionManagerHour;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.0
 *
 */
public interface ReportDao {

	/**
	 * Retrieves all the schedule values for the Total Hour Weekly
	 * @param store 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<TotalHour>getScheduleWeeklyTotalHour(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves all the target values for the Total Hour Weekly
	 * @param store 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<TotalHour>getTargetWeeklyTotalHour(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves all the schedule values for the Total Hour Weekly By Position.
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getScheduleWeeklyTotalHourByPosition(Store store, Position position, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves all the target values for Total Hour Weekly By Position.
	 * @param store
	 * @param position
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getTargetWeeklyTotalHourByPosition(Store store, Position position, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the schedule forecast of all the positions.
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHourByPosition> getScheduleForecastByPosition(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the target forecast of all the positions.
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHourByPosition> getTargetForecastByPosition(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the schedule values for Total Hour Weekly by Position Group
	 * @param store
	 * @param positionGroup
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getScheduleWeeklyTotalHourByService(Store store, PositionGroup positionGroup, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the target values for Total Hour Weekly by Position Group
	 * @param store
	 * @param positionGroup
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getTargetWeeklyTotalHourByService(Store store, PositionGroup positionGroup, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the scheduled half hours for a Store on a selected date. It's calculated from the time is opened
	 * to the time is closed (included extra time). 
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlySchedule(Store store, Date date, Date startHour, Date endHour) throws SQLException;

	/**
	 * Retrieves the target half hours for a Store on a selected date. It's calculated from the time is opened
	 * to the time is closed. In these kind of reports it's not included the extra time because employees shouldn't be working.
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlyMinimumStaffing(Store store, Date date) throws SQLException;
	
	/**
	 * Retrieves the scheduled hours for a Store on a selected date filtered by a Position. It's calculated from the time is opened
	 * to the time is closed (included extra time).
	 * @param store
	 * @param position
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlyScheduleByPosition(Store store, Position position, Date date, Date startHour, Date endHour) throws SQLException;
	
	/**
	 * Retrieves the target hours for a Store on a selected date filtered by a Position. It's calculated from the time 
	 * to the time is closed. In these kind of reports it's not included the extra time because employees shouldn't be working.
	 * @param store
	 * @param position
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlyMinimumStaffingByPosition(Store store, Position position, Date date) throws SQLException;
	
	/**
	 * Retrieves the scheduled hours for a Store on a selected date filtered by a Position. It's calculated from the time is opened
	 * to the time is closed. (included extra time)
	 * @param store 
	 * @param positionGroup
	 * @param date
	 * @param startHour
	 * @param endHour
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlyScheduleByService(Store store, PositionGroup positionGroup, Date date, Date startHour, Date endHour) throws SQLException;
	
	/**
	 * Retrieves the target hours for a Store on a selected date filtered by a Position. It's calculated from the time is opened
	 * to the time is closed. In these kind of reports it's not included the extra time because employees shouldn't be working.
	 * @param store
	 * @param position
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getHalfHourlyMinimumStaffingByService(Store store, PositionGroup positionGroup, Date date) throws SQLException;
	
	/**
	 * Retrieves the sales for a Store
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<HistoricSales> getActualSales(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the Hours worked during a week for that store.
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalHour> getActualHours(Store store, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getScheduleFixedLaborHours(Store store, Date date) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getTargetFixedLaborHours(Store store, Date date) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours by Position
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getScheduleFixedLaborHoursByPosition(Store store, Date date, Position position) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours By Position
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getTargetFixedLaborHoursByPosition(Store store, Date date, Position position) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours by Position group
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getScheduleFixedLaborHoursByService(Store store, Date date, PositionGroup positionGroup) throws SQLException;
	
	/**
	 * Retrieves the Scheduled FixedLaborHours by Position group
	 * @param store
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	FixedLaborHours getTargetFixedLaborHoursByService(Store store, Date date, PositionGroup positionGroup) throws SQLException;
	
	/**
	 * Retrieves the sales for all the Stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalCustomerManagerHour> getActualSalesByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the actual hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalCustomerManagerHour> getActualHoursByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total hours of all the stores associated to a customer 
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalCustomerManagerHour> getScheduleTotalHourByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total target hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalCustomerManagerHour>getTargetTotalHourByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException;
		
	/**
	 * Retrieves the sales for all the Stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalRegionManagerHour> getActualSalesByRegion(Region region, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the actual hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalRegionManagerHour> getActualHoursByRegion(Region region, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total hours of all the stores associated to a customer 
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalRegionManagerHour> getScheduleTotalHourByRegion(Region region, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total target hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalRegionManagerHour>getTargetTotalHourByRegion(Region region, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the sales for all the Stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalAreaManagerHour> getActualSalesByArea(Area area, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the actual hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalAreaManagerHour> getActualHoursByArea(Area area, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total hours of all the stores associated to a customer 
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalAreaManagerHour> getScheduleTotalHourByArea(Area area, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * Retrieves the total target hours of all the stores associated to a customer
	 * @param customer
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	List<TotalAreaManagerHour>getTargetTotalHourByArea(Area area, Date startDate, Date endDate) throws SQLException;
	
	
}