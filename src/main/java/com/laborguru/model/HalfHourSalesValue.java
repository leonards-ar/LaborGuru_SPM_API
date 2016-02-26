/*
 * File name: DailySalesValue.java
 * Creation date: Feb 17, 2009 5:24:30 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;

import com.laborguru.util.CalendarUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class HalfHourSalesValue extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -696943173636004499L;
	private Long id;
	private BigDecimal value;
	private Integer index;
	private Date time;

	/**
	 * 
	 */
	public HalfHourSalesValue() {
	}

	/**
	 * @param other
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return false;
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return null;
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
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
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
	 * Sets time 
	 * @param time the time to set
	 */
	public void setTime(String time) {
		setTime(CalendarUtils.displayTimeToDate(time));
	}	
	
	/**
	 * Returns time as string if not null.
	 * @return the time as string
	 */
	public String getTimeAsString(){
		
		if (time != null)
			return CalendarUtils.dateToDisplayTime(this.getTime());
		
		return null;
	}	
}
