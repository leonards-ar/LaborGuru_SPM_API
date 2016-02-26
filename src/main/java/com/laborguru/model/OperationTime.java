/*
 * File name: DayOperationHours.java
 * Creation date: Jul 6, 2008 12:55:16 AM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 * This class holds the start and close hours on a daily basis for Stores.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class OperationTime extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5285245853035872927L;
	private Integer id;
	private Date openHour;
	private Date closeHour;
	private Store store;
	private Integer openingExtraHours;
	private Integer closingExtraHours;
	
	/**
	 * Integer holding the same value for
	 * day constants defined in the java.util.Calendar class.
	 */	
	private DayOfWeek dayOfWeek;
	
	/**
	 * 
	 */
	public OperationTime() {
	}

	/**
	 * @return the openHour
	 */
	public Date getOpenHour() {
		return openHour;
	}

	/**
	 * @param openHour the openHour to set
	 */
	public void setOpenHour(Date openHour) {
		this.openHour = CalendarUtils.removeDateFromTime(openHour);
	}

	/**
	 * @return the closeHour
	 */
	public Date getCloseHour() {
		return closeHour;
	}

	/**
	 * @param closeHour the closeHour to set
	 */
	public void setCloseHour(Date closeHour) {
		this.closeHour = CalendarUtils.removeDateFromTime(closeHour);
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
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
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 37)
		.append(getDayOfWeek())
		.append(getStore() != null? getStore().getId() : null)
		.toHashCode();
	}

	/**
	 * @param other
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
		
		final OperationTime other = (OperationTime) obj;

		return new EqualsBuilder()
		.append(getDayOfWeek(), other.getDayOfWeek())
		.append(getStore() != null ? getStore().getId() : null, other.getStore() != null ? other.getStore().getId() : null)
		.isEquals();
	}
	
	/**
	 * @return the dayOfWeek
	 */
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
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
	
	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * 
	 * @param list
	 * @param dayOfWeek
	 * @return
	 */
	public static OperationTime getOperationTimeByDayOfWeek(List<OperationTime> list, DayOfWeek dayOfWeek) {
		for(OperationTime aOperationTime: list) {
			if(aOperationTime.getDayOfWeek().equals(dayOfWeek)) {
				return aOperationTime;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("store" , store)
	   	.append("day of week", dayOfWeek)
	   	.append("open hour", openHour)
	   	.append("close hour", closeHour)
	   	.toString();
	}

	/**
	 * Returns whether this instance of operation time is spanned over 2 calendar days, taking
	 * into account just operation hours (Open & Close) without taking into account opening and
	 * closing extra hours. 
	 * @return
	 */
	public boolean operationTimeEndsTomorrow() {
		if ((getOpenHour() == null) || (getCloseHour() == null)){
			throw new IllegalArgumentException("openHour or closeHour is null");
		}
		
		return CalendarUtils.equalsOrGreaterTime(getOpenHour(), getCloseHour());
	}

	/**
	 * Returns whether this instance of operation time is spanned over 2 calendar days, taking
	 * into account opening and closing extra hours.
	 * @return
	 */
	public boolean endsTomorrow() {
		if(operationTimeEndsTomorrow()) {
			// No matter if extra hours are 0 or more, it ends tomorrow!
			return true;
		} else {
			return CalendarUtils.equalsOrGreaterTime(getCloseHour(), getEndHour());		
		}
	}
	
	/**
	 * Returns whether this instance of operation time starts yesterday, taking into
	 * account the opening extra hours.
	 * @return
	 */
	public boolean startsYesterday() {
		return CalendarUtils.equalsOrGreaterTime(getStartHour(), getOpenHour());	
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getOpeningExtraHours() {
		return openingExtraHours;
	}

	/**
	 * 
	 * @param openingExtraHours
	 */
	public void setOpeningExtraHours(Integer openingExtraHours) {
		this.openingExtraHours = openingExtraHours;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getClosingExtraHours() {
		return closingExtraHours;
	}

	/**
	 * 
	 * @param closingExtraHours
	 */
	public void setClosingExtraHours(Integer closingExtraHours) {
		this.closingExtraHours = closingExtraHours;
	}
	
	/**
	 * Time the store will stop working. This is is the closing hour plus the extra closing hours
	 * @return
	 */
	public Date getEndHour() {
		return CalendarUtils.addOrSubstractHours(getCloseHour(), NumberUtils.getIntegerValue(getClosingExtraHours()));
	}
	
	/**
	 * Time the store will start working. This is the opening hour less the extra opening hours.
	 * @return
	 */
	public Date getStartHour() {
		return CalendarUtils.addOrSubstractHours(getOpenHour(), (-1) * NumberUtils.getIntegerValue(getOpeningExtraHours()));
	}
	
	/**
	 * Total amount of hours the store operates (Opening + Operation + Closing)
	 * @return
	 */
	public Double getTotalOperationHours() {
		double operationHours = CalendarUtils.differenceInHours(getCloseHour(), getOpenHour()).doubleValue();
		return new Double(NumberUtils.getIntegerValue(getOpeningExtraHours()) + operationHours + NumberUtils.getIntegerValue(getClosingExtraHours()));
	}
}
