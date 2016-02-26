package com.laborguru.action.report;

import java.math.BigDecimal;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.report.TotalHour;
import com.laborguru.service.position.PositionService;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public abstract class WeeklyReportBaseAction extends ScheduleReportPrepareAction implements Preparable{

	private List<TotalHour> totalHours;
	private Integer positionId;

	private PositionService positionService;

	private BigDecimal totalSchedule = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalTarget = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalDifference = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalPercentage = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalSales = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalVariable2 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalVariable3 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalVariable4 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal total = SpmConstants.BD_ZERO_VALUE;
	// Extended totals
	private BigDecimal totalVplhSchedule = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalVplhTarget = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalProjectedSales = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalScheduleLaborPercentage = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal totalTargetLaborPercentage = SpmConstants.BD_ZERO_VALUE;
	// End of extended totals
	
	private String scheduleAxisName;
	private String targetAxisName;
	
	private String xmlValues;

	/**
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
	}

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

	public String showReport() {
		initWeekSelectorValues();
		if (getItemId() == null) {
			getReport();
			
		} else {
			if ("byPosition".equals(getSelectedGrouping())) {
				getReportByPosition();
			} else if ("byService".equals(getSelectedGrouping())) {
				getReportByService();
			} else {
				getReport();
			}
		}
		calculateTotals();
		setAxisLabels();
		generateXmlGraph();
		return SpmActionResult.SHOW.getResult();
	}

	private void calculateTotals() {
		double totalWage = 0.0;
		for (TotalHour th : getTotalHours()) {
			setTotalSales(getTotalSales().add(th.getSales()));
			setTotalVariable2(getTotalVariable2().add(th.getVariable2()));
			setTotalVariable3(getTotalVariable3().add(th.getVariable3()));
			setTotalVariable4(getTotalVariable4().add(th.getVariable4()));
			setTotal(getTotal().add(th.getTotal()));
			setTotalSchedule(getTotalSchedule().add(th.getSchedule()));
			setTotalTarget(getTotalTarget().add(th.getTarget()));
			setTotalDifference(getTotalDifference().add(th.getDifference()));
			totalWage += NumberUtils.getDoubleValue(th.getStoreTotalWage());
		}
		// totalDifference/totalTarget
		if (getTotalTarget().compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			setTotalPercentage(SpmConstants.BD_ZERO_VALUE);
		} else {
			setTotalPercentage(getTotalDifference().divide(getTotalTarget(), 2,
					SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100)));
		}
		
		if(calculateExtendedTotals()) {
			if(getTotalSchedule().compareTo(SpmConstants.BD_ZERO_VALUE) != 0) {
				setTotalVplhSchedule(getTotal().divide(getTotalSchedule(), 2, SpmConstants.ROUNDING_MODE));
			}

			if(getTotalTarget().compareTo(SpmConstants.BD_ZERO_VALUE) != 0) {
				setTotalVplhTarget(getTotal().divide(getTotalTarget(), 2, SpmConstants.ROUNDING_MODE));
			}
			
			if(getTotalSales() != null && getEmployeeStore() != null) {
				BigDecimal var1 = new BigDecimal(NumberUtils.getDoubleValue(getEmployeeStore().getMainVariableAverage())).multiply(getTotalSales());
				Double avg2 = getEmployeeStore().getSecondaryVariableDefinition(1) != null ? getEmployeeStore().getSecondaryVariableDefinition(1).getAverage() : SpmConstants.DOUBLE_ZERO_VALUE;
				Double avg3 = getEmployeeStore().getSecondaryVariableDefinition(2) != null ? getEmployeeStore().getSecondaryVariableDefinition(2).getAverage() : SpmConstants.DOUBLE_ZERO_VALUE;
				Double avg4 = getEmployeeStore().getSecondaryVariableDefinition(3) != null ? getEmployeeStore().getSecondaryVariableDefinition(3).getAverage() : SpmConstants.DOUBLE_ZERO_VALUE;
				BigDecimal var2 =  new BigDecimal(NumberUtils.getDoubleValue(avg2)).multiply(getTotalVariable2());
				BigDecimal var3 = new BigDecimal(NumberUtils.getDoubleValue(avg3)).multiply(getTotalVariable3());
				BigDecimal var4 = new BigDecimal(NumberUtils.getDoubleValue(avg4)).multiply(getTotalVariable4());
				
				setTotalProjectedSales(var1.add(var2.add(var3.add(var4))));
			}
			double averageWage = 0.0;
			if(getTotalSchedule().compareTo(SpmConstants.BD_ZERO_VALUE) != 0 && getTotalProjectedSales().compareTo(SpmConstants.BD_ZERO_VALUE) != 0) {
				averageWage = totalWage / NumberUtils.getDoubleValue(getTotalSchedule());
				
				setTotalScheduleLaborPercentage(getTotalSchedule().multiply(new BigDecimal(averageWage)).divide(getTotalProjectedSales(), 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100)));
				setTotalTargetLaborPercentage(getTotalTarget().multiply(new BigDecimal(averageWage)).divide(getTotalProjectedSales(), 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100)));
			}
			
		}
	}
	
	private void generateXmlGraph(){
		setXmlValues(getFusionXmlDataConverter().weeklyTotalHoursXmlConverter(
				getTotalHours(), getScheduleAxisName(), getTargetAxisName(), getTexts("defaultmessages")));
	}
	
	protected abstract void getReport();
	protected abstract void getReportByPosition();
	protected abstract void getReportByService();
	protected abstract void setAxisLabels();

	/**
	 * Weather the following totals should be calculated:
	 * totalVplhSchedule, totalVplhTarget, totalProjectedSales, totalScheduleLaborPercentage, totalTargetLaborPercentage
	 * @return
	 */
	protected boolean calculateExtendedTotals() {
		return false;
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
	 * @return the positionId
	 */
	public Integer getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
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

	/**
	 * @return the totalSales
	 */
	public BigDecimal getTotalSales() {
		return totalSales != null ? totalSales : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param totalSales the totalSales to set
	 */
	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	/**
	 * @return the xmlValues
	 */
	public String getXmlValues() {
		return xmlValues;
	}

	/**
	 * @param xmlValues the xmlValues to set
	 */
	public void setXmlValues(String xmlValues) {
		this.xmlValues = xmlValues;
	}

	/**
	 * @return the scheduleAxisName
	 */
	public String getScheduleAxisName() {
		return scheduleAxisName;
	}

	/**
	 * @param scheduleAxisName the scheduleAxisName to set
	 */
	public void setScheduleAxisName(String scheduleAxisName) {
		this.scheduleAxisName = scheduleAxisName;
	}

	/**
	 * @return the targetAxisName
	 */
	public String getTargetAxisName() {
		return targetAxisName;
	}

	/**
	 * @param targetAxisName the targetAxisName to set
	 */
	public void setTargetAxisName(String targetAxisName) {
		this.targetAxisName = targetAxisName;
	}

	public BigDecimal getTotalVplhSchedule() {
		return totalVplhSchedule != null ? totalVplhSchedule : SpmConstants.BD_ZERO_VALUE;
	}

	public void setTotalVplhSchedule(BigDecimal totalVplhSchedule) {
		this.totalVplhSchedule = totalVplhSchedule;
	}

	public BigDecimal getTotalVplhTarget() {
		return totalVplhTarget != null ? totalVplhTarget : SpmConstants.BD_ZERO_VALUE;
	}

	public void setTotalVplhTarget(BigDecimal totalVplhTarget) {
		this.totalVplhTarget = totalVplhTarget;
	}

	public BigDecimal getTotalProjectedSales() {
		return totalProjectedSales != null ? totalProjectedSales : SpmConstants.BD_ZERO_VALUE;
	}

	public void setTotalProjectedSales(BigDecimal totalProjectedSales) {
		this.totalProjectedSales = totalProjectedSales;
	}

	public BigDecimal getTotalScheduleLaborPercentage() {
		return totalScheduleLaborPercentage != null ? totalScheduleLaborPercentage : SpmConstants.BD_ZERO_VALUE;
	}

	public void setTotalScheduleLaborPercentage(
			BigDecimal totalScheduleLaborPercentage) {
		this.totalScheduleLaborPercentage = totalScheduleLaborPercentage;
	}

	public BigDecimal getTotalTargetLaborPercentage() {
		return totalTargetLaborPercentage != null ? totalTargetLaborPercentage : SpmConstants.BD_ZERO_VALUE;
	}

	public void setTotalTargetLaborPercentage(BigDecimal totalTargetLaborPercentage) {
		this.totalTargetLaborPercentage = totalTargetLaborPercentage;
	}

	/**
	 * @return the totalVariable2
	 */
	public BigDecimal getTotalVariable2() {
		return totalVariable2 != null ? totalVariable2 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param totalVariable2 the totalVariable2 to set
	 */
	public void setTotalVariable2(BigDecimal totalVariable2) {
		this.totalVariable2 = totalVariable2;
	}

	/**
	 * @return the totalVariable3
	 */
	public BigDecimal getTotalVariable3() {
		return totalVariable3 != null ? totalVariable3 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param totalVariable3 the totalVariable3 to set
	 */
	public void setTotalVariable3(BigDecimal totalVariable3) {
		this.totalVariable3 = totalVariable3;
	}

	/**
	 * @return the totalVariable4
	 */
	public BigDecimal getTotalVariable4() {
		return totalVariable4 != null ? totalVariable4 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param totalVariable4 the totalVariable4 to set
	 */
	public void setTotalVariable4(BigDecimal totalVariable4) {
		this.totalVariable4 = totalVariable4;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total != null ? total : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
