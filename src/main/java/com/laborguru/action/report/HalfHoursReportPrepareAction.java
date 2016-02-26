package com.laborguru.action.report;

import java.math.BigDecimal;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.report.TotalHour;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHoursReportPrepareAction extends ScheduleReportPrepareAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -101891023525910101L;


	List<TotalHour>totalHours;
	
	private BigDecimal totalSales = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalSchedule = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalTarget = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalDifference = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalPercentage = SpmConstants.BD_ZERO_VALUE;
	
	private String xmlValues;
	public void prepareShowReport(){
		pageSetup();
	}

	public void prepareShowFirstReport() {
		pageSetup();
	}

	public String showFirstReport() {
		initWeekSelectorValues();
		return SpmActionResult.INPUT.getResult();
	}
	
	public String showReport(){
		initWeekSelectorValues();
		
		if (getItemId() == null) {
			getDailyReport();
		} else {
			if ("byPosition".equals(getSelectedGrouping())) {
				getDailyReportByPosition();
			} else if ("byService".equals(getSelectedGrouping())) {
				getDailyReportByService();
			} else {
				getDailyReport();
			}
		}		
		calculateTotals();
		generateXmlGraph();
		
		return SpmActionResult.SHOW.getResult();
	}
	
	private void getDailyReport(){
		setTotalHours(getReportService().getHalfHourlyReport(getEmployeeStore(), getWeekDaySelector().getSelectedDay()));
	}
	
	private void getDailyReportByPosition(){
		Position position = new Position();
		position.setId(getItemId());
		setTotalHours(getReportService().getHalfHourlyReportByPosition(getEmployeeStore(), position, getWeekDaySelector().getSelectedDay()));
	}
	
	private void getDailyReportByService(){
		PositionGroup positionGroup = new PositionGroup();
		positionGroup.setId(getItemId());
		setTotalHours(getReportService().getHalfHourlyReportByService(getEmployeeStore(), positionGroup, getWeekDaySelector().getSelectedDay()));
	}

	private void calculateTotals() {

		for (TotalHour th : getTotalHours()) {
			setTotalSales(getTotalSales().add(th.getSales()));
			setTotalSchedule(getTotalSchedule().add(th.getSchedule()));
			setTotalTarget(getTotalTarget().add(th.getTarget()));
			setTotalDifference(getTotalDifference().add(th.getDifference()));
		}
		setTotalSchedule(getTotalSchedule().divide(new BigDecimal(2)));
		setTotalTarget(getTotalTarget().divide(new BigDecimal(2)));
		setTotalDifference(getTotalDifference().divide(new BigDecimal(2)));
	}
	
	public void generateXmlGraph(){
		setXmlValues(getFusionXmlDataConverter().halfHoursXmlConverter(
				getTotalHours(), getTexts("defaultmessages")));
	}
	
	/**
	 * Gets the data to display in the graphic chart.
	 * 
	 * @return
	 */
	public String getXmlValues() {
		return xmlValues;
	}
	
	public void setXmlValues(String xmlValues) {
		this.xmlValues = xmlValues;
	}
	/**
	 * @return the totalHours
	 */
	public List<TotalHour> getTotalHours() {
		return totalHours;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(List<TotalHour> totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @return the totalSales
	 */
	public BigDecimal getTotalSales() {
		return totalSales;
	}

	/**
	 * @param totalSales the totalSales to set
	 */
	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	/**
	 * @return the totalSchedule
	 */
	public BigDecimal getTotalSchedule() {
		return totalSchedule;
	}

	/**
	 * @param totalSchedule the totalSchedule to set
	 */
	public void setTotalSchedule(BigDecimal totalSchedule) {
		this.totalSchedule = totalSchedule;
	}

	/**
	 * @return the totalTarget
	 */
	public BigDecimal getTotalTarget() {
		return totalTarget;
	}

	/**
	 * @param totalTarget the totalTarget to set
	 */
	public void setTotalTarget(BigDecimal totalTarget) {
		this.totalTarget = totalTarget;
	}

	/**
	 * @return the totalDifference
	 */
	public BigDecimal getTotalDifference() {
		return totalDifference;
	}

	/**
	 * @param totalDifference the totalDifference to set
	 */
	public void setTotalDifference(BigDecimal totalDifference) {
		this.totalDifference = totalDifference;
	}

	/**
	 * @return the totalpercentage
	 */
	public BigDecimal getTotalPercentage() {
		return totalPercentage;
	}

	/**
	 * @param totalpercentage the totalpercentage to set
	 */
	public void setTotalPercentage(BigDecimal totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public void prepare() throws Exception {
	}
	
	

}
