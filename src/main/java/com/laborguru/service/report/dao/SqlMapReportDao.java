package com.laborguru.service.report.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

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
import com.laborguru.util.CalendarUtils;

public class SqlMapReportDao extends SqlMapClientDaoSupport implements ReportDao {
	private static final Logger log = Logger.getLogger(SqlMapReportDao.class);
	
	public List<TotalHour> getScheduleWeeklyTotalHour(Store store, Date startDate,Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getWeeklyTotalHour: before select params: store_id:" + store.getId() + " startDate: " + startDate + " endDate" + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleWeeklyTotalHours", ReportDaoHelper.mapTotalHoursReport(store, startDate, endDate));
		
	}
	
	public List<TotalHour> getTargetWeeklyTotalHour(Store store, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getWeeklyTotalHour: before select params: store_id:" + store.getId() + " startDate: " + startDate + " endDate" + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetWeeklyTotalHours", ReportDaoHelper.mapTotalHoursReport(store, startDate, endDate));
		
	}
	
	public List<TotalHour> getScheduleWeeklyTotalHourByPosition(Store store, Position position, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleWeeklyTotalHourByPosition: before select params: store_id:" + store.getId() + " position: " + position + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleWeeklyTotalHoursByPosition", ReportDaoHelper.mapTotalHoursReportByPosition(store, position, startDate, endDate));
	}
	
	public List<TotalHour> getTargetWeeklyTotalHourByPosition(Store store, Position position, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetWeeklyTotalHourByPosition: before select params: store_id:" + store.getId() + " position: " + position + " startDate: " + startDate + " endDate" + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetWeeklyTotalHoursByPosition", ReportDaoHelper.mapTotalHoursReportByPosition(store, position, startDate, endDate));
	}

	public List<TotalHour> getScheduleWeeklyTotalHourByService(Store store, PositionGroup positionGroup, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()){
			log.debug("getScheduleWeeklyTotalHourByService: before select params: store_id: " + store.getId() + " positon_group_id: " + positionGroup.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleWeeklyTotalHoursByService", ReportDaoHelper.mapTotalHoursReportByService(store, positionGroup, startDate, endDate));
	}
	
	public List<TotalHour> getTargetWeeklyTotalHourByService(Store store, PositionGroup positionGroup, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()){
			log.debug("getTargetWeeklyTotalHourByService: before select params: store_id: " + store.getId() + " positon_group_id: " + positionGroup.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetWeeklyTotalHoursByService", ReportDaoHelper.mapTotalHoursReportByService(store, positionGroup, startDate, endDate));
	}
	
	public List<TotalHour> getHalfHourlySchedule(Store store, Date date, Date startHour, Date endHour) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlySchedule: before select params: store_id: " + store.getId() + " date: " + date + " startHour: " + CalendarUtils.removeDateFromTime(startHour) + "endHour: " + CalendarUtils.removeDateFromTime(endHour));
		}
		
		String namedQuery = "getScheduleHalfHourlyTotalHours";
		
		//if close hour is smaller or equals  than open hour, it means that its opens on one day and closes on the following day.		
		if(isNextDay(startHour, endHour)) {
			namedQuery = "getScheduleHalfHourlyTotalHoursSplitInTwoDays";
		}
		
		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReport(store, date, startHour, endHour));
	}
	
	public List<TotalHour> getHalfHourlyMinimumStaffing(Store store, Date date) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlyMinimumStaffing: before select params: store_id: " + store.getId() + " date: " + date);
		}

		String namedQuery = "getTargetHalfHourlyTotalHours";
		
		//if close hour is smaller or equals  than open hour, it means that its opens on one day and closes on the following day.
		Date openHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getOpenHour();
		Date closeHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getCloseHour();
		if(isNextDay(openHour, closeHour)) {
			namedQuery = "getTargetHalfHourlyTotalHoursSplitInTwoDays";
		}
		
		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReport(store, date, openHour, closeHour));
	}
	
	public List<TotalHour> getHalfHourlyScheduleByPosition(Store store, Position position, Date date, Date startHour, Date endHour) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlyScheduleByPosition: before select params: store_id: " + store.getId() + " position_id: " + position.getId() + " date: " + date + " startHour: " + CalendarUtils.removeDateFromTime(startHour) + "endHour: " + CalendarUtils.removeDateFromTime(endHour));
		}

		String namedQuery = "getScheduleHalfHourlyTotalHoursByPosition"; 
		
		//if close hour is smaller or equals  than open hour, it means that its opens on one day and closes on the following day. 
		if(isNextDay(startHour, endHour)) {
			namedQuery = "getScheduleHalfHourlyTotalHoursByPositionSplitInTwoDays";
		}
		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReportByPosition(store, position, date, startHour, endHour));
	}
	
	public List<TotalHour> getHalfHourlyMinimumStaffingByPosition(Store store, Position position, Date date) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlyTargetByPosition: before select params: store_id: " + store.getId() + " position_id: " + position.getId() + " date: " + date);
		}
		
		String namedQuery = "getTargetHalfHourlyTotalHoursByPosition";
		
		Date openHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getOpenHour();
		Date closeHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getCloseHour();

		//if close hour is smaller or equals  than open hour, it means that its opens on one day and closes on the following day. 
		if(isNextDay(openHour, closeHour)) {
			namedQuery = "getTargetHalfHourlyTotalHoursByPositionSplitInTwoDays";
		}
		
		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReportByPosition(store, position, date, openHour, closeHour));
	}

	public List<TotalHour> getHalfHourlyScheduleByService(Store store, PositionGroup positionGroup, Date date, Date startHour, Date endHour) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlyScheduleByService: before select params: store_id: " + store.getId() + " positionGroup_id: " + positionGroup.getId() + " date: " + date + " startHour: " + CalendarUtils.removeDateFromTime(startHour) + "endHour: " + CalendarUtils.removeDateFromTime(endHour));
		}

		String namedQuery = "getScheduleHalfHourlyTotalHoursByService";
		
		if(isNextDay(startHour, endHour)) {
			namedQuery = "getScheduleHalfHourlyTotalHoursByServiceSplitInTwoDays";
		}
		
		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReportByService(store, positionGroup, date, startHour, endHour));
	}
	
	public List<TotalHour> getHalfHourlyMinimumStaffingByService(Store store, PositionGroup positionGroup, Date date) throws SQLException{
		if(log.isDebugEnabled()){
			log.debug("getHalfHourlyTargetByService: before select params: store_id: " + store.getId() + " position_id: " + positionGroup.getId() + " date: " + date);
		}

		String namedQuery = "getTargetHalfHourlyTotalHoursByService";
		
		//if close hour is smaller or equals  than open hour, it means that its opens on one day and closes on the following day.
		Date openHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getOpenHour();
		Date closeHour = store.getOperationTime(CalendarUtils.getDayOfWeek(date)).getCloseHour();

		if(isNextDay(openHour, closeHour)) {
			namedQuery = "getTargetHalfHourlyTotalHoursByServiceSplitInTwoDays";
		}

		return getSqlMapClient().queryForList(namedQuery, ReportDaoHelper.mapHalfHoursReportByService(store, positionGroup, date, openHour, closeHour));
	}
	
	public List<TotalHourByPosition> getScheduleForecastByPosition(Store store, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleForecastByPosition: before select params: store_id:" + store.getId() + " startDate: " + startDate + " endDate" + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleForecastByPosition", ReportDaoHelper.mapTotalHoursReport(store, startDate, endDate));

	}

	public List<TotalHourByPosition> getTargetForecastByPosition(Store store, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetForecastByPosition: before select params: store_id:" + store.getId() + " startDate: " + startDate + " endDate" + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetForecastByPosition", ReportDaoHelper.mapTotalHoursReport(store, startDate, endDate));
	}
	
	public List<HistoricSales> getActualSales(Store store, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getActualSales: before select params: store_id: " + store.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualSales", ReportDaoHelper.mapActualSalesReport(store, startDate, endDate));
	}
	
	public List<TotalHour> getActualHours(Store store, Date startDate, Date endDate) throws SQLException{
		if(log.isDebugEnabled()) {
			log.debug("getActualHours: before select params: store_id: " + store.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualHours", ReportDaoHelper.mapActualHoursReport(store, startDate, endDate));
	}
	
	public FixedLaborHours getScheduleFixedLaborHours(Store store, Date date) throws SQLException {

		if(log.isDebugEnabled()) {
			log.debug("getScheduleFixedLaborHours: before select params: store_id: " + store.getId() + " date: " + date);
		}

		FixedLaborHours fixedLaborHours = (FixedLaborHours)getSqlMapClient().queryForObject("getScheduleFixedLaborHours", ReportDaoHelper.mapScheduleFixedLaborHours(store, date));
		
		//It's always zero.
		fixedLaborHours.setFlexHours(0.0);
		
		return fixedLaborHours;
	}
	
	public FixedLaborHours getTargetFixedLaborHours(Store store, Date date) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetFixedLaborHours: before select params: store_id: " + store.getId() + " date: " + date);
		}
		
		return (FixedLaborHours)getSqlMapClient().queryForObject("getTargetFixedLaborHours", ReportDaoHelper.mapTargetFixedLaborHours(store, date));
	}
	
	public FixedLaborHours getScheduleFixedLaborHoursByPosition(Store store, Date date, Position position) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleFixedLaborHoursByPosition: before select params: store_id: " + store.getId() + " date: " + date + " position: " + position);
		}

		FixedLaborHours fixedLaborHours = (FixedLaborHours)getSqlMapClient().queryForObject("getScheduleFixedLaborHoursByPosition", ReportDaoHelper.mapScheduleFixedLaborHoursByPosition(store, date, position));
		
		//It's always zero.
		fixedLaborHours.setFlexHours(0.0);
		
		return fixedLaborHours;
	}
	
	public FixedLaborHours getTargetFixedLaborHoursByPosition(Store store, Date date, Position position) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetFixedLaborHoursByPosition: before select params: store_id: " + store.getId() + " date: " + date + " position: " + position);
		}
		
		return (FixedLaborHours)getSqlMapClient().queryForObject("getTargetFixedLaborHoursByPosition", ReportDaoHelper.mapTargetFixedLaborHoursByPosition(store, date, position));
	}	

	public FixedLaborHours getScheduleFixedLaborHoursByService(Store store, Date date, PositionGroup positionGroup) throws SQLException {

		if(log.isDebugEnabled()) {
			log.debug("getScheduleFixedLaborHoursByService: before select params: store_id: " + store.getId() + " date: " + date + " positionGroup: " + positionGroup);
		}

		FixedLaborHours fixedLaborHours = (FixedLaborHours)getSqlMapClient().queryForObject("getScheduleFixedLaborHoursByService", ReportDaoHelper.mapScheduleFixedLaborHoursByService(store, date, positionGroup));
		
		//It's always zero.
		fixedLaborHours.setFlexHours(0.0);
		
		return fixedLaborHours;
	}
	
	public FixedLaborHours getTargetFixedLaborHoursByService(Store store, Date date, PositionGroup positionGroup) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetFixedLaborHoursByService: before select params: store_id: " + store.getId() + " date: " + date + " positionGroup: " + positionGroup);
		}
		
		return (FixedLaborHours)getSqlMapClient().queryForObject("getTargetFixedLaborHoursByService", ReportDaoHelper.mapTargetFixedLaborHoursByService(store, date, positionGroup));
	}
	
	public List<TotalCustomerManagerHour> getActualSalesByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getActualSalesByCustomer: before select params: customer_id: " + customer.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualSalesByCustomer", ReportDaoHelper.mapActualSalesReport(customer, startDate, endDate));
	} 
	
	public List<TotalCustomerManagerHour> getActualHoursByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException{
		if(log.isDebugEnabled()) {
			log.debug("getActualHoursByCustomer: before select params: customer_id: " + customer.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualHoursByCustomer", ReportDaoHelper.mapActualHoursReport(customer, startDate, endDate));
	}
	
	public List<TotalCustomerManagerHour> getScheduleTotalHourByCustomer(Customer customer, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleTotalHourByCustomer: before select params: customer_id: " + customer.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleTotalHoursByCustomer", ReportDaoHelper.mapTotalHoursReport(customer, startDate, endDate));
	}
	
	public List<TotalCustomerManagerHour>getTargetTotalHourByCustomer (Customer customer, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetTotalHourByCustomer: before select params: customer_id: " + customer.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetTotalHoursByCustomer", ReportDaoHelper.mapTotalHoursReport(customer, startDate, endDate));
	}

	private boolean isNextDay(Date startHour, Date endHour){
		return CalendarUtils.equalsOrSmallerTime(endHour, startHour);
	}
	
	public List<TotalRegionManagerHour> getActualSalesByRegion(Region region, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getActualSalesByRegion: before select params: region_id: " + region.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualSalesByRegion", ReportDaoHelper.mapActualSalesReport(region, startDate, endDate));
	} 
	
	public List<TotalRegionManagerHour> getActualHoursByRegion (Region region, Date startDate, Date endDate) throws SQLException{
		if(log.isDebugEnabled()) {
			log.debug("getActualHoursByRegion: before select params: region_id: " + region.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualHoursByRegion", ReportDaoHelper.mapActualHoursReport(region, startDate, endDate));
	}
	
	public List<TotalRegionManagerHour> getScheduleTotalHourByRegion(Region region, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleTotalHourByRegion: before select params: region_id: " + region.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleTotalHoursByRegion", ReportDaoHelper.mapTotalHoursReport(region, startDate, endDate));
	}
	
	public List<TotalRegionManagerHour>getTargetTotalHourByRegion(Region region, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetTotalHourByRegion: before select params: region_id: " + region.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetTotalHoursByRegion", ReportDaoHelper.mapTotalHoursReport(region, startDate, endDate));
	}

	public List<TotalAreaManagerHour> getActualSalesByArea(Area area, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getActualSalesByArea: before select params: area_id: " + area.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualSalesByArea", ReportDaoHelper.mapActualSalesReport(area, startDate, endDate));
	} 
	
	public List<TotalAreaManagerHour> getActualHoursByArea (Area area, Date startDate, Date endDate) throws SQLException{
		if(log.isDebugEnabled()) {
			log.debug("getActualHoursByArea: before select params: area_id: " + area.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getActualHoursByArea", ReportDaoHelper.mapActualHoursReport(area, startDate, endDate));
	}
	
	public List<TotalAreaManagerHour> getScheduleTotalHourByArea(Area area, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getScheduleTotalHourByArea: before select params: area_id: " + area.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getScheduleTotalHoursByArea", ReportDaoHelper.mapTotalHoursReport(area, startDate, endDate));
	}
	
	public List<TotalAreaManagerHour>getTargetTotalHourByArea(Area area, Date startDate, Date endDate) throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("getTargetTotalHourByArea: before select params: area_id: " + area.getId() + " startDate: " + startDate + " endDate: " + endDate);
		}
		
		return getSqlMapClient().queryForList("getTargetTotalHoursByArea", ReportDaoHelper.mapTotalHoursReport(area, startDate, endDate));
	}

}
