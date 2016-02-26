/*
 * File name: HalfHourStaffing.java
 * Creation date: 19/10/2008 15:50:50
 * Copyright Mindpool
 */
package com.laborguru.model;


/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourHistoricSalesStaffing extends HalfHourStaffing {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3687368197643897357L;
	
	private DailyHistoricSalesStaffing dailyStaffing;
	
	/**
	 * 
	 */
	public HalfHourHistoricSalesStaffing() {
	}

	/**
	 * @return the dailyStaffing
	 */
	public DailyStaffing getDailyStaffing() {
		return dailyStaffing;
	}

	/**
	 * @param dailyStaffing the dailyStaffing to set
	 */
	public void setDailyStaffing(DailyStaffing dailyStaffing) {
		if(!(dailyStaffing instanceof DailyHistoricSalesStaffing)) {
			throw new IllegalArgumentException("dailyStaffing passed in as parameter is not an instance of DailyHistoricSalesStaffing");
		}		
		this.dailyStaffing = (DailyHistoricSalesStaffing) dailyStaffing;
	}	
}
