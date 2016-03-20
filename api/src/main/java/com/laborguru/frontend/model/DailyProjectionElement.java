package com.laborguru.frontend.model;

import java.math.BigDecimal;
import java.util.Date;

import com.laborguru.model.DailyProjection;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

public class DailyProjectionElement {
	
	private BigDecimal calculatedProjection;
	
	private BigDecimal adjustedProjection;
	private BigDecimal projectionVariable2;
	private BigDecimal projectionVariable3;
	private BigDecimal projectionVariable4;

	
	private Date projectionDate;
	
	/**
	 * @return the calculatedProjection
	 */
	public BigDecimal getCalculatedProjection() {
		return calculatedProjection;
	}
	/**
	 * @param calculatedProjection the calculatedProjection to set
	 */
	public void setCalculatedProjection(BigDecimal calculatedProjection) {
		this.calculatedProjection = calculatedProjection;
	}
	/**
	 * @return the adjustedProjection
	 */
	public BigDecimal getAdjustedProjection() {
		return adjustedProjection;
	}
	/**
	 * @param adjustedProjection the adjustedProjection to set
	 */
	public void setAdjustedProjection(BigDecimal adjustedProjection) {
		this.adjustedProjection = adjustedProjection;
	}
	/**
	 * @return the projectionDate
	 */
	public Date getProjectionDate() {
		return projectionDate;
	}
	/**
	 * @param projectionDate the projectionDate to set
	 */
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}
	
	public Boolean getEditable() {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return Boolean.TRUE;
		} else {		
			return (CalendarUtils.todayWithoutTime().compareTo(getProjectionDate()) < 0);
		}
	}
	/**
	 * @return the projectionVariable2
	 */
	public BigDecimal getProjectionVariable2() {
		return projectionVariable2;
	}
	/**
	 * @param projectionVariable2 the projectionVariable2 to set
	 */
	public void setProjectionVariable2(BigDecimal projectionVariable2) {
		this.projectionVariable2 = projectionVariable2;
	}
	/**
	 * @return the projectionVariable3
	 */
	public BigDecimal getProjectionVariable3() {
		return projectionVariable3;
	}
	/**
	 * @param projectionVariable3 the projectionVariable3 to set
	 */
	public void setProjectionVariable3(BigDecimal projectionVariable3) {
		this.projectionVariable3 = projectionVariable3;
	}
	/**
	 * @return the projectionVariable4
	 */
	public BigDecimal getProjectionVariable4() {
		return projectionVariable4;
	}
	/**
	 * @param projectionVariable4 the projectionVariable4 to set
	 */
	public void setProjectionVariable4(BigDecimal projectionVariable4) {
		this.projectionVariable4 = projectionVariable4;
	}

	/**
	 * @return
	 */
	public DailyProjection createDailyProjection(){
		DailyProjection dailyProjection = new DailyProjection();
		
		dailyProjection.setProjectionDate(getProjectionDate());
		
		dailyProjection.setDailyProjectionVariable3(getProjectionVariable3());
		dailyProjection.setDailyProjectionVariable2(getProjectionVariable2());
		dailyProjection.setDailyProjectionVariable4(getProjectionVariable4());
		
		return dailyProjection;
	}
}
