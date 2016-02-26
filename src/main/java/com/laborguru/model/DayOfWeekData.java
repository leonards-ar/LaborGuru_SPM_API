/*
 * File name: DayOfWeekData.java
 * Creation date: 12/07/2008 21:56:52
 * Copyright Mindpool
 */
package com.laborguru.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This object stores data related to positions and days of
 * the week.<br/>
 * Among the values stored in this object you can find
 * <ul>
 * <li>Labor Allowances - Fixed Flexible</li>
 * <li>Labor Allowances - Fixed Opening</li>
 * <li>Labor Allowances - Fixed Post Rush</li>
 * <li>Labor Allowances - Fixed Closing</li>
 * </ul>
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DayOfWeekData extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6389366750902049911L;

	private Integer id;
	private Double fixedFlexible;
	private Double fixedOpening;
	private Double fixedPostRush;
	private Double fixedClosing;
	private DayOfWeek dayOfWeek;
	private Position position;
	
	/**
	 * 
	 */
	public DayOfWeekData() {
	}

	/**
	 * CN - DayOfWeekData needs "position" in the equals and not "position id" as we need to distinguish DayOfWeekData objects before
	 * they are persisted. DayOfWeekData depends of position anyway, so there is no significant penalization on performance as there is already a position preloaded
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
		
		final DayOfWeekData other = (DayOfWeekData) obj;
		
		return new EqualsBuilder()
		.append(this.position != null ? this.position : null, other.position != null ? other.position : null)
		.append(this.dayOfWeek != null ? this.dayOfWeek.getDayOfWeek() : null, other.dayOfWeek != null ? other.dayOfWeek.getDayOfWeek() : null)
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
		.append(this.dayOfWeek != null ? this.dayOfWeek.getDayOfWeek() : null)
		.toHashCode();
	}

	/**
	 * Store toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id", id)
	   	.append("position", position)
	   	.append("day of week", dayOfWeek.toString())
	   	.append("fixed flexible", fixedFlexible)
	   	.append("fixed opening", fixedOpening)
	   	.append("fixed closing", fixedClosing)
	   	.append("fixed post rush", fixedPostRush)
	   	.toString();		
	}
	
	/**
	 * @return the fixedFlexible
	 */
	public Double getFixedFlexible() {
		return fixedFlexible;
	}

	/**
	 * @param fixedFlexible the fixedFlexible to set
	 */
	public void setFixedFlexible(Double fixedFlexible) {
		this.fixedFlexible = fixedFlexible;
	}

	/**
	 * @return the fixedOpening
	 */
	public Double getFixedOpening() {
		return fixedOpening;
	}

	/**
	 * @param fixedOpening the fixedOpening to set
	 */
	public void setFixedOpening(Double fixedOpening) {
		this.fixedOpening = fixedOpening;
	}

	/**
	 * @return the fixedPostRush
	 */
	public Double getFixedPostRush() {
		return fixedPostRush;
	}

	/**
	 * @param fixedPostRush the fixedPostRush to set
	 */
	public void setFixedPostRush(Double fixedPostRush) {
		this.fixedPostRush = fixedPostRush;
	}

	/**
	 * @return the fixedClosing
	 */
	public Double getFixedClosing() {
		return fixedClosing;
	}

	/**
	 * @param fixedClosing the fixedClosing to set
	 */
	public void setFixedClosing(Double fixedClosing) {
		this.fixedClosing = fixedClosing;
	}

	/**
	 * @return the dayOfWeek
	 */
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
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
	 * 
	 * @return
	 */
	public Integer getDayOfWeekIndex() {
		return getDayOfWeek() != null ? new Integer(getDayOfWeek().ordinal()) : null;
	}
	
	/**
	 * 
	 * @param dayofWeek
	 */
	public void setDayOfWeekIndex(Integer dayOfWeek) {
		if(dayOfWeek != null) {
			setDayOfWeek(DayOfWeek.values()[dayOfWeek.intValue()]);
		} else {
			setDayOfWeek(null);
		}
	}	
}
