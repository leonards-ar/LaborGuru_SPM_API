package com.laborguru.action.report;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.DailyFlash;
import com.laborguru.model.DailyFlashDetail;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.model.report.DailyFlashHour;
import com.laborguru.model.report.DailyFlashObject;
import com.laborguru.model.report.FixedLaborHoursReport;
import com.laborguru.service.report.DailyFlashService;
import com.laborguru.service.store.StoreService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

public class DailyFlashReportPrepareAction extends ScheduleReportPrepareAction implements Preparable {

	private static final long serialVersionUID = 7417726746002576268L;
	
	private DailyFlashService dailyFlashService;
	private StoreService storeService;
	
	private DailyFlash dailyFlash;
	
	private DailyFlashObject dailyFlashObject;

	private Date partDay;
	private BigDecimal totalSales;
	private BigDecimal totalActualSales;
	private BigDecimal totalActualLabor;
	private BigDecimal partialActualLabor;
	private BigDecimal totalScheduleHour;
	private BigDecimal partialScheduleHour;
	private BigDecimal totalDifference;
	private DailyFlashHour lastDailyFlashHour;
	private FixedLaborHoursReport fixedLaborHoursReport;
	private Double secondVariableHours = null;
	
	public void prepareShowReport(){
		
	}

	public void prepareShowFirstReport() {
		
	}
	
	public String showFirstReport() {
		
		showProjectedSalesReport();
		showCateringReport();
		return SpmActionResult.INPUT.getResult();
	}

	public void showProjectedSalesReport() {
		Date now = new Date();
		
		if(getDailyFlash() == null) {
			setDailyFlash(getDailyFlashService().getDailyFlashByDate(getEmployeeStore(), now));
		}
		
		setDailyFlashObject(getReportService().getDailyFlashReport(getEmployeeStore(), now, getDailyFlash() != null? new LinkedList<DailyFlashDetail>(getDailyFlash().getDetails()):new LinkedList<DailyFlashDetail>()));
		setFixedLaborHoursReport(getReportService().getFixedLaborHoursReport(getEmployeeStore(), now));
		Store store = getStoreService().getStoreById(getEmployeeStore());
		Double hours = new Double(0.0);
		for(Position pos: store.getPositions()) {
			hours += pos.getVariable2Flexible() + pos.getVariable2Opening();  
		}
		setSecondVariableHours(hours);
		return;
	}
	
	public void showCateringReport() {
		return;
	}
	
	public void prepare() throws Exception {
	}

	public DailyFlashObject getDailyFlashObject() {
		return dailyFlashObject;
	}

	public void setDailyFlashObject(DailyFlashObject dailyFlashObject) {
		this.dailyFlashObject = dailyFlashObject;
	}

	public List<DailyFlashHour> getDailyFlashHours() {
		return dailyFlashObject.getDailyFlashHours();
	}

	public void setDailyFlashHours(List<DailyFlashHour> dailyFlashHours) {
		this.dailyFlashObject.setDailyFlashHours(dailyFlashHours);
	}

	public Date getPartDay() {
		return partDay;
	}

	public void setPartDay(Date partDay) {
		this.partDay = partDay;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getTotalActualSales() {
		return totalActualSales;
	}

	
	public BigDecimal getTotalHalfScheduleHour(){
		return totalScheduleHour;
	}
	
	public BigDecimal getTotalScheduleHour() {
		return totalScheduleHour.divide(new BigDecimal(2), 0, 0);
	}

	public void setTotalScheduleHour(BigDecimal totalScheduleHour) {
		this.totalScheduleHour = totalScheduleHour;
	}
	
	public void setTotalActualSales(BigDecimal totalActualSales) {
		this.totalActualSales = totalActualSales;
	}

	public BigDecimal getTotalHalfActualLabor(){
		return totalActualLabor;
	}
	
	public BigDecimal getTotalActualLabor() {
		return totalActualLabor.divide(new BigDecimal(2), 0, 0);
	}

	public void setTotalActualLabor(BigDecimal totalActualLabor) {
		this.totalActualLabor = totalActualLabor;
	}
	
	public String getPartialDifferenceHours(){
		BigDecimal diff = getPartialActualLabor().subtract(getPartialScheduleHour());
		return diff.compareTo(SpmConstants.BD_ZERO_VALUE) < 0 ? "(" + (new DecimalFormat("#0")).format(diff.abs()) + ")" : (new DecimalFormat("#0")).format(diff);
	}
	
	public BigDecimal getRestTotalScheduleHour(){
		return getTotalScheduleHour().subtract(getPartialScheduleHour());
	}
	
	public BigDecimal getPartialHalfActualLabor() {
		return partialActualLabor;
	}
	
	public BigDecimal getPartialActualLabor() {
		return partialActualLabor.divide(new BigDecimal(2), 0, 0);
	}

	public void setPartialActualLabor(BigDecimal partialActualLabor) {
		this.partialActualLabor = partialActualLabor;
	}

	public BigDecimal getPartialHalfScheduleHour() {
		return partialScheduleHour;
	}
	
	public BigDecimal getPartialScheduleHour() {
		return partialScheduleHour.divide(new BigDecimal(2),0,0);
	}

	public void setPartialScheduleHour(BigDecimal partialScheduleHour) {
		this.partialScheduleHour = partialScheduleHour;
	}

	public BigDecimal getTotalDifference() {
		return totalDifference;
	}

	public void setTotalDifference(BigDecimal totalDifference) {
		this.totalDifference = totalDifference;
	}
	
	public String getTotalFormattedDifference(){
		BigDecimal diff = getTotalDifference();
		return (diff.compareTo(SpmConstants.BD_ZERO_VALUE) < 0 ? "(" + (new DecimalFormat("#,##0")).format(diff.abs()) + ")" : (new DecimalFormat("#,##0")).format(diff));		
	}

	public DailyFlashHour getLastDailyFlashHour() {
		return lastDailyFlashHour;
	}

	public void setLastDailyFlashHour(DailyFlashHour lastDailyFlashHour) {
		this.lastDailyFlashHour = lastDailyFlashHour;
	}
	
	public boolean isEquals(Date date) {
		return CalendarUtils.equalsTime(date, partDay);
	}
	
	public Store getStore(){
		return getEmployeeStore();
	}

	public DailyFlash getDailyFlash() {
		return dailyFlash;
	}

	public void setDailyFlash(DailyFlash dailyFlash) {
		this.dailyFlash = dailyFlash;
	}

	public DailyFlashService getDailyFlashService() {
		return dailyFlashService;
	}
	
	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setDailyFlashService(DailyFlashService dailyFlashService) {
		this.dailyFlashService = dailyFlashService;
	}

	public FixedLaborHoursReport getFixedLaborHoursReport() {
		return fixedLaborHoursReport;
	}

	public void setFixedLaborHoursReport(FixedLaborHoursReport fixedLaborHoursReport) {
		this.fixedLaborHoursReport = fixedLaborHoursReport;
	}
	
	public Double getScheduleOpeningHours() {
		return getFixedLaborHoursReport().getSchedule().getOpenHours();
	}
	
	public Double getScheduleFlexibleHours() {
		return getFixedLaborHoursReport().getSchedule().getFlexHours();
	}
	
	public Double getProjectedOpeningHours() {
		return getFixedLaborHoursReport().getTarget().getOpenHours();
	}
	
	public Double getProjectedFlexibleHours() {
		return getFixedLaborHoursReport().getTarget().getFlexHours();
	}
	
	public Double getScheduleCloseHours() {
		return getFixedLaborHoursReport().getSchedule().getCloseHours();
	}
	
	public Double getProjectedCloseHours() {
		return getFixedLaborHoursReport().getTarget().getCloseHours();
	}
	
	public Double getSecondVariableHours() {
		return secondVariableHours;
	}

	public void setSecondVariableHours(Double secondVariableHours) {
		this.secondVariableHours = secondVariableHours;
	}

}
