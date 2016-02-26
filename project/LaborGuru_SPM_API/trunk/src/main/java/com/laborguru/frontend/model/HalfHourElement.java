package com.laborguru.frontend.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author 
 *
 */
public class HalfHourElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3084686923963998744L;
	
	private Long id;
	private String hour;
	private BigDecimal projectedValue;
	private BigDecimal adjustedValue;
	private BigDecimal revisedValue;
	
	public HalfHourElement() {
		
	}
	
	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}


	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}


	/**
	 * @return the projectedValue
	 */
	public BigDecimal getProjectedValue() {
		return projectedValue;
	}

	/**
	 * @param projectedValue the projectedValue to set
	 */
	public void setProjectedValue(BigDecimal projectedValue) {
		this.projectedValue = projectedValue;
	}

	/**
	 * @return the adjustedValue
	 */
	public BigDecimal getAdjustedValue() {
		return adjustedValue;
	}
	
	/**
	 * @param adjustedValue the adjustedValue to set
	 */
	public void setAdjustedValue(BigDecimal adjustedValue) {
		this.adjustedValue = adjustedValue;
	}
	
	/**
	 * @return the revisedValue
	 */
	public BigDecimal getRevisedValue() {
		return revisedValue;
	}
	
	/**
	 * @param revisedValue the revisedValue to set
	 */
	public void setRevisedValue(BigDecimal revisedValue) {
		this.revisedValue = revisedValue;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
