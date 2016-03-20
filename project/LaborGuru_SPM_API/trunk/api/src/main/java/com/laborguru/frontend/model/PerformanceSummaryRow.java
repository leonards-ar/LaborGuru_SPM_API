/*
 * File name: PerformanceSummaryRow.java
 * Creation date: 21/02/2009 16:09:43
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PerformanceSummaryRow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812126214877741390L;
	
	private Date date;
	private BigDecimal projectedVolume;
	private Double projectedTarget;
	private Double projectedScheduled;
	
	private BigDecimal actualVolume;
	private Double actualTarget;
	private Double actualScheduled;	
	
	private BigDecimal laborCost;
	
	/**
	 * 
	 */
	public PerformanceSummaryRow() {
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the projectedVolume
	 */
	public BigDecimal getProjectedVolume() {
		return projectedVolume;
	}
	/**
	 * @param projectedVolume the projectedVolume to set
	 */
	public void setProjectedVolume(BigDecimal projectedVolume) {
		this.projectedVolume = projectedVolume;
	}
	/**
	 * @return the projectedTarget
	 */
	public Double getProjectedTarget() {
		return projectedTarget;
	}
	/**
	 * @param projectedTarget the projectedTarget to set
	 */
	public void setProjectedTarget(Double projectedTarget) {
		this.projectedTarget = projectedTarget;
	}
	/**
	 * @return the projectedScheduled
	 */
	public Double getProjectedScheduled() {
		return projectedScheduled;
	}
	/**
	 * @param projectedScheduled the projectedScheduled to set
	 */
	public void setProjectedScheduled(Double projectedScheduled) {
		this.projectedScheduled = projectedScheduled;
	}
	/**
	 * @return the actualVolume
	 */
	public BigDecimal getActualVolume() {
		return actualVolume;
	}
	/**
	 * @param actualVolume the actualVolume to set
	 */
	public void setActualVolume(BigDecimal actualVolume) {
		this.actualVolume = actualVolume;
	}
	/**
	 * @return the actualTarget
	 */
	public Double getActualTarget() {
		return actualTarget;
	}
	/**
	 * @param actualTarget the actualTarget to set
	 */
	public void setActualTarget(Double actualTarget) {
		this.actualTarget = actualTarget;
	}
	/**
	 * @return the actualScheduled
	 */
	public Double getActualScheduled() {
		return actualScheduled;
	}
	/**
	 * @param actualScheduled the actualScheduled to set
	 */
	public void setActualScheduled(Double actualScheduled) {
		this.actualScheduled = actualScheduled;
	}

	/**
	 * 
	 * @return
	 */
	public Double getProjectedDifference() {
		return new Double(NumberUtils.getDoubleValue(getProjectedScheduled()) - NumberUtils.getDoubleValue(getProjectedTarget()));
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getProjectedDifferencePercentage() {
		return percentage(getProjectedDifference(), getProjectedTarget());
	}	

	/**
	 * 
	 * @return
	 */
	public Double getActualDifference() {
		return new Double(NumberUtils.getDoubleValue(getActualScheduled()) - NumberUtils.getDoubleValue(getActualTarget()));
	}
	
	/**
	 * Performance
	 * @return
	 */
	public Double getActualDifferencePercentage() {
		return percentage(getActualDifference(), getActualTarget());
	}

	/**
	 * 
	 * @param num
	 * @param den
	 * @return
	 */
	private Double percentage(Double num, Double den) {
		double d = NumberUtils.getDoubleValue(den);
		if(d != 0.0) {
			return new Double(NumberUtils.getDoubleValue(num) / d * 100);
		} else {
			return new Double(0.0);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProjectedTargetAsString() {
		return CalendarUtils.hoursToTime(getProjectedTarget());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getActualTargetAsString() {
		return CalendarUtils.hoursToTime(getActualTarget());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProjectedScheduledAsString() {
		return CalendarUtils.hoursToTime(getProjectedScheduled());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getActualScheduledAsString() {
		return CalendarUtils.hoursToTime(getActualScheduled());
	}

	/**
	 * 
	 * @return
	 */
	public String getProjectedDifferenceAsString() {
		return CalendarUtils.hoursToTime(getProjectedDifference());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getActualDifferenceAsString() {
		return CalendarUtils.hoursToTime(getActualDifference());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isProjectedVolumeSet() {
		return NumberUtils.getDoubleValue(getProjectedVolume()) > 0.0;
	}
	
	/**
	 * actual hours – scheduled hours
	 * @return
	 */
	public Double getExecutionDifference() {
		return new Double(NumberUtils.getDoubleValue(getActualScheduled()) - NumberUtils.getDoubleValue(getProjectedScheduled()));
	}
	
	/**
	 * actual hours – scheduled hours) /scheduled hours
	 * @return
	 */
	public Double getExecutionDifferencePercentage() {
		return percentage(getExecutionDifference(), getProjectedScheduled());
	}	
	
	/**
	 * (projection – actual)/ actual
	 * @return
	 */
	public Double getProjectionDifference() {
		return new Double(NumberUtils.getDoubleValue(getProjectedVolume()) - NumberUtils.getDoubleValue(getActualVolume()));
	}
	
	/**
	 * (projection – actual)/ actual
	 * @return
	 */
	public Double getProjectionDifferencePercentage() {
		return percentage(getProjectionDifference(), new Double(NumberUtils.getDoubleValue(getActualVolume())));
	}
	/**
	 * @return the laborCost
	 */
	public BigDecimal getLaborCost() {
		return laborCost;
	}
	/**
	 * @param laborCost the laborCost to set
	 */
	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}		
	
	/**
	 * 
	 * @return
	 */
	public Double getVolumeLaborTarget() {
		double volume = NumberUtils.getDoubleValue(getActualVolume());
		double target = NumberUtils.getDoubleValue(getActualTarget());
		return target != 0.0 ? new Double(volume / target) : new Double(0.0);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getVolumeLaborActual() {
		double volume = NumberUtils.getDoubleValue(getActualVolume());
		double scheduled = NumberUtils.getDoubleValue(getActualScheduled());
		return scheduled != 0.0 ? new Double(volume / scheduled) : new Double(0.0);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageOfSalesIdeal() {
		double targetLaborCost = NumberUtils.getDoubleValue(getActualTarget()) * getAverageWage();
		double volume = NumberUtils.getDoubleValue(getActualVolume());
		return volume != 0.0 ? new Double((targetLaborCost / volume) * 100) : new Double(0.0);
	}

	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageOfSalesActual() {
		double actualLaborCost = NumberUtils.getDoubleValue(getActualScheduled()) * getAverageWage();
		double volume = NumberUtils.getDoubleValue(getActualVolume());
		return volume != 0.0 ? new Double((actualLaborCost / volume) * 100) : new Double(0.0);
	}	
	
	/**
	 * 
	 * @return
	 */
	private double getAverageWage() {
		double laborCost = NumberUtils.getDoubleValue(getLaborCost());
		double scheduled = NumberUtils.getDoubleValue(getProjectedScheduled());
		return scheduled != 0.0 ? laborCost / scheduled : 0.0;
	}
}
