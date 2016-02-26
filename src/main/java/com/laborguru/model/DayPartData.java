/*
 * File name: DayPartData.java
 * Creation date: 13/07/2008 09:41:42
 * Copyright Mindpool
 */
package com.laborguru.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This object stores data related to positions and day parts.<br/>
 * Among the values stored in this object you can find
 * <ul>
 * <li>Labor Allowances - Weekday Guest Services</li>
 * <li>Labor Allowances - Weekend Guest Services</li>
 * <li>Labor Allowances - Variable Flexible</li>
 * <li>Labor Allowances - Variable Opening</li>
 * <li>Labor Allowances - Fixed Guest Service</li>
 * <li>Labor Assumptions - Minimum Staffing</li>
 * </ul>
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DayPartData extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6322463798852263411L;

	private Integer id;
	private Double weekdayGuestService;
	private Double weekendGuestService;
	private Double variableFlexible;
	private Double variableOpening;
	private Double fixedGuestService;
	private Integer minimunStaffing;
	private Position position;
	private DayPart dayPart;
	
	/**
	 * 
	 */
	public DayPartData() {
	}

	/**
	 * CN - DayPartData needs in the "position" equals and not "position id", as we need to distinguish DayPartData objects before
	 * they are persisted. DayPartData depends of position anyway, so there is no significant penalization on performance as there is already a position preloaded
	 * in the hibernate cache. 
	 * @param obj
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		
		
		final DayPartData other = (DayPartData) obj;
		
		return new EqualsBuilder()
		.append(this.position != null ? this.position : null, other.position != null ? other.position : null)
		.append(this.dayPart != null ? this.dayPart : null, other.dayPart != null ? other.dayPart : null)
		.isEquals();
	}
	
	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.position != null ? this.position : null)
		.append(this.dayPart != null ? this.dayPart : null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("position" , position)
	   	.append("day part", dayPart)
	   	.append("weekday guest service", weekdayGuestService)
	   	.append("weekend guest service", weekendGuestService)
	   	.append("variable flexible", variableFlexible)
	   	.append("variable opening", variableOpening)
	   	.append("fixed guest service", fixedGuestService)
	   	.append("minimun staffing", minimunStaffing)
	   	.toString();
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * @return the weekdayGuestService
	 */
	public Double getWeekdayGuestService() {
		return weekdayGuestService;
	}

	/**
	 * @param weekdayGuestService the weekdayGuestService to set
	 */
	public void setWeekdayGuestService(Double weekdayGuestService) {
		this.weekdayGuestService = weekdayGuestService;
	}

	/**
	 * @return the weekendGuestService
	 */
	public Double getWeekendGuestService() {
		return weekendGuestService;
	}

	/**
	 * @param weekendGuestService the weekendGuestService to set
	 */
	public void setWeekendGuestService(Double weekendGuestService) {
		this.weekendGuestService = weekendGuestService;
	}

	/**
	 * @return the variableFlexible
	 */
	public Double getVariableFlexible() {
		return variableFlexible;
	}

	/**
	 * @param variableFlexible the variableFlexible to set
	 */
	public void setVariableFlexible(Double variableFlexible) {
		this.variableFlexible = variableFlexible;
	}

	/**
	 * @return the variableOpening
	 */
	public Double getVariableOpening() {
		return variableOpening;
	}

	/**
	 * @param variableOpening the variableOpening to set
	 */
	public void setVariableOpening(Double variableOpening) {
		this.variableOpening = variableOpening;
	}

	/**
	 * @return the fixedGuestService
	 */
	public Double getFixedGuestService() {
		return fixedGuestService;
	}

	/**
	 * @param fixedGuestService the fixedGuestService to set
	 */
	public void setFixedGuestService(Double fixedGuestService) {
		this.fixedGuestService = fixedGuestService;
	}

	/**
	 * @return the minimunStaffing
	 */
	public Integer getMinimunStaffing() {
		return minimunStaffing;
	}

	/**
	 * @param minimunStaffing the minimunStaffing to set
	 */
	public void setMinimunStaffing(Integer minimunStaffing) {
		this.minimunStaffing = minimunStaffing;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the dayPart
	 */
	public DayPart getDayPart() {
		return dayPart;
	}

	/**
	 * @param dayPart the dayPart to set
	 */
	public void setDayPart(DayPart dayPart) {
		this.dayPart = dayPart;
	}

}
