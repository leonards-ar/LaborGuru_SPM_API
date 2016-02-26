/*
 * File name: HalfHourStaffing.java
 * Creation date: 19/10/2008 15:50:50
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class HalfHourStaffing extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5159771872374865468L;
	
	private Long id;
	private Integer calculatedStaff = new Integer(0);
	private Double workContent = new Double(0.0);
	private Integer index;

	private Date time;
	
	/**
	 * 
	 */
	public HalfHourStaffing() {
	}

	/**
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

		final HalfHourStaffing other = (HalfHourStaffing) obj;
		
		return new EqualsBuilder()
		.append(getIndex(), other.getIndex())
		.append(getDailyStaffing(), other.getDailyStaffing())
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getIndex())
		.append(getDailyStaffing())
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("calculatedStaff",getCalculatedStaff())
	   	.append("index",getIndex())
	   	.toString();
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

	/**
	 * @return the calculatedStaff
	 */
	public Integer getCalculatedStaff() {
		if(calculatedStaff == null) {
			calculatedStaff = new Integer(0);
		}
		return calculatedStaff;
	}

	/**
	 * @param calculatedStaff the calculatedStaff to set
	 */
	public void setCalculatedStaff(Integer calculatedStaff) {
		this.calculatedStaff = calculatedStaff;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}


	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the workContent
	 */
	public Double getWorkContent() {
		return workContent;
	}

	/**
	 * @param workContent the workContent to set
	 */
	public void setWorkContent(Double workContent) {
		this.workContent = workContent;
	}

	/**
	 * 
	 * @return
	 */
	public abstract DailyStaffing getDailyStaffing();
	
	/**
	 * 
	 * @param dailyStaffing
	 */
	public abstract void setDailyStaffing(DailyStaffing dailyStaffing);
}
