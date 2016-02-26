/*
 * File name: ScheduleHourLabelElement.java
 * Creation date: Sep 20, 2008 9:06:46 PM
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleHourLabelElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6089474005607716084L;
	private Date hour;
	private Integer colspan;
	private Integer selectableCount;
	
	/**
	 * 
	 */
	public ScheduleHourLabelElement() {
		this(null, null, null);
	}

	/**
	 * 
	 * @param hour
	 * @param colspan
	 */
	public ScheduleHourLabelElement(Date hour, Integer colspan, Integer selectableCount) {
		super();
		setHour(hour);
		setColspan(colspan);
		setSelectableCount(selectableCount);
	}
	
	/**
	 * @return the hour
	 */
	public Date getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(Date hour) {
		this.hour = hour;
	}

	/**
	 * @return the colspan
	 */
	public Integer getColspan() {
		return colspan;
	}

	/**
	 * @param colspan the colspan to set
	 */
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	/**
	 * @return the selectableCount
	 */
	public Integer getSelectableCount() {
		return selectableCount;
	}

	/**
	 * @param selectableCount the selectableCount to set
	 */
	public void setSelectableCount(Integer selectableCount) {
		this.selectableCount = selectableCount;
	}

}
