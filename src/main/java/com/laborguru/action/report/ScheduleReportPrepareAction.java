package com.laborguru.action.report;

import java.util.Map;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.WeekDaySelector;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.report.ReportService;
import com.laborguru.util.FusionXmlDataConverter;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class ScheduleReportPrepareAction extends SpmAction  {

	private static final long serialVersionUID = 1L;
	protected static final String DEFAULT_REPORT="totalHoursReport_showFirstReport.action";
	protected static final String DEFAULT_GROUPING = "total";
	
	private WeekDaySelector weekDaySelector;
	private String selectedDate;
	private String selectedWeekDay;	
	private String selectedReport;
	private String selectedGrouping;
	private Integer itemId;

	private Map<String,String>reportMap;
	private Map<String,String>groupingMap;
	private Map<Integer, String>itemsMap;
	
	private ReferenceDataService referenceDataService;
	private ReportService reportService;
	private FusionXmlDataConverter fusionXmlDataConverter;
	
	private DayOfWeek getFirstDayOfWeek() {
		Store store = getEmployeeStoreOrNull();

		// Case 1: An employee is logged: The first day is the Store one
		if(store != null) {
			return store.getFirstDayOfWeek();
		}
		
		// Case 2: AreaUser, RegionUser, CustomerUser
		if(getLoggedUser() != null) {
			return DayOfWeek.MONDAY;
		}
		
		// Case 3: No logged user??? Impossible
		return null;
	}
	
	/**
	 * @return the weekDaySelector
	 */
	public WeekDaySelector getWeekDaySelector() {
		if(weekDaySelector == null) {
			weekDaySelector = new WeekDaySelector(getFirstDayOfWeek());
		}
		return weekDaySelector;
	}

	public String changeWeek() {
		
		pageSetup();
		
		getWeekDaySelector().initializeChangeWeek(getSelectedDate(), getSelectedWeekDay());
		
		loadCalendarData();
		
		return SpmActionResult.INPUT.getResult();
	}
	
	public String changeDay() {
		pageSetup();
		
		getWeekDaySelector().initializeChangeDay(getSelectedDate(),getSelectedWeekDay());
		
		loadCalendarData();
		
		return SpmActionResult.INPUT.getResult();
		
	}
	
	/**
	 * 
	 */
	protected void loadCalendarData() {
		setSelectedDate(getWeekDaySelector().getStringStartingWeekDay());
		setSelectedWeekDay(getWeekDaySelector().getStringSelectedDay());
	}

	/**
	 * @param weekDaySelector the weekDaySelector to set
	 */
	public void setWeekDaySelector(WeekDaySelector weekDaySelector) {
		this.weekDaySelector = weekDaySelector;
	}

	public void initWeekSelectorValues() {
		if(getSelectedDate() != null && getSelectedWeekDay() != null){
			getWeekDaySelector().initializeChangeWeek(getSelectedDate(), getSelectedWeekDay());
		}
	}
	/**
	 * @return the selectedDate
	 */
	public String getSelectedDate() {
		return selectedDate;
	}

	/**
	 * @param selectedDate the selectedDate to set
	 */
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	/**
	 * @return the selectedWeekDay
	 */
	public String getSelectedWeekDay() {
		return selectedWeekDay;
	}

	/**
	 * @param selectedWeekDay the selectedWeekDay to set
	 */
	public void setSelectedWeekDay(String selectedWeekDay) {
		this.selectedWeekDay = selectedWeekDay;
	}


	/**
	 * @return the selectedReport
	 */
	public String getSelectedReport() {
		return selectedReport;
	}

	/**
	 * @param selectedReport the selectedReport to set
	 */
	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}

	/**
	 * @return the reportList
	 */
	public Map<String, String> getReportMap() {
		return reportMap;
	}

	/**
	 * @param reportList the reportList to set
	 */
	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	/**
	 * @return the selectedGrouping
	 */
	public String getSelectedGrouping() {
		return selectedGrouping;
	}

	/**
	 * @param selectedGrouping the selectedGrouping to set
	 */
	public void setSelectedGrouping(String selectedGrouping) {
		this.selectedGrouping = selectedGrouping;
	}

	/**
	 * @return the groupingMap
	 */
	public Map<String, String> getGroupingMap() {
		return groupingMap;
	}

	/**
	 * @param groupingMap the groupingMap to set
	 */
	public void setGroupingMap(Map<String, String> groupingMap) {
		this.groupingMap = groupingMap;
	}

	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemsMap
	 */
	public Map<Integer, String> getItemsMap() {
		return itemsMap;
	}

	/**
	 * @param itemsMap the itemsMap to set
	 */
	public void setItemsMap(Map<Integer, String> itemsMap) {
		this.itemsMap = itemsMap;
	}

	/**
	 * @return the referenceDataService
	 */
	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}

	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
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
	 * @return the fusionXmlDataConverter
	 */
	public FusionXmlDataConverter getFusionXmlDataConverter() {
		return fusionXmlDataConverter;
	}

	/**
	 * @param fusionXmlDataConverter
	 *            the fusionXmlDataConverter to set
	 */
	public void setFusionXmlDataConverter(
			FusionXmlDataConverter fusionXmlDataConverter) {
		this.fusionXmlDataConverter = fusionXmlDataConverter;
	}

	protected void pageSetup() {
		setReportMap(getReferenceDataService().getReportTypes());
		setGroupingMap(getReferenceDataService().getReportGrouping());
		if(getSelectedReport() == null) {
			setSelectedReport(DEFAULT_REPORT);
		} else {
			setSelectedReport(getSelectedReport());
		}
		
		if(getSelectedGrouping() == null) {
			setSelectedGrouping(DEFAULT_GROUPING);
		} else {
			setSelectedGrouping(getSelectedGrouping());
		}
		
	}
	
	
	
}
