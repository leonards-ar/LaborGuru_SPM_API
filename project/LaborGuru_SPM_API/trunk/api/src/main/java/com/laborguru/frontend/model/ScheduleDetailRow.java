/*
 * File name: ScheduleDetailRow.java
 * Creation date: Nov 27, 2008 11:24:06 AM
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
public class ScheduleDetailRow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9095049092298889809L;
	private Date hour;
	private Double value;
	
	/**
	 * 
	 */
	public ScheduleDetailRow() {
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
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		if(value != null) {
			setValue(new Double(value.intValue()));
		} else {
			setValue(new Double(0.0));
		}
	}
}
