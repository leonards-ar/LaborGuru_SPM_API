/*
 * File name: StoreDailyStaffing.java
 * Creation date: Oct 21, 2008 2:13:30 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreDailyFlashStaffing extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5438231250357406297L;
	
	private Map<Position, DailyFlashStaffing> storeDailyStaffing = new HashMap<Position, DailyFlashStaffing>();
	private Store store;
	private Date date;
	
	/**
	 * 
	 */
	public StoreDailyFlashStaffing(Store store) {
		super();
		setStore(store);
	}

	/**
	 * 
	 * @param dailyStaffing
	 */
	public void addDailyStaffing(DailyFlashStaffing dailyStaffing) {
		if(dailyStaffing != null && dailyStaffing.getPosition() != null) {
			this.storeDailyStaffing.put(dailyStaffing.getPosition(), dailyStaffing);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<DailyFlashStaffing> getStoreDailyStaffing() {
		return this.storeDailyStaffing.values();
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public int getHalfHourStaffing(Date time) {
		int total = 0;
		int halfHourIndex = -1;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			if(halfHourIndex < 0) {
				halfHourIndex = dailyStaffing.getHalfHourIndex(time);
			}
			total += dailyStaffing.getHalfHourStaffing().get(halfHourIndex).getCalculatedStaff().intValue();
		}
		return total;
	}
	
	/**
	 * 
	 * @param positions
	 * @param time
	 * @return
	 */
	public int getHalfHourStaffing(List<Position> positions, Date time) {
		int total = 0;
		if(positions != null) {
			for(Position pos : positions) {
				total += getHalfHourStaffing(pos, time);
			}
		}
		return total;
	}
	
	/**
	 * 
	 * @param position
	 * @param time
	 * @return
	 */
	public int getHalfHourStaffing(Position position, Date time) {
		DailyFlashStaffing dailyStaffing = getDailyStaffingFor(position);
		if(dailyStaffing != null) {
			int halfHourIndex = dailyStaffing.getHalfHourIndex(time);
			return dailyStaffing.getHalfHourStaffing().get(halfHourIndex).getCalculatedStaff().intValue();
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public DailyFlashStaffing getDailyStaffingFor(Position position) {
		if(position != null && position.getId() != null) {
			for(Position key : this.storeDailyStaffing.keySet()) {
				if(position.getId().equals(key.getId())) {
					return this.storeDailyStaffing.get(key);
				}
			}
		}
		return null;
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
	 * 
	 * @return
	 */
	public Double getTotalOpening() {
		double total = 0.0;
		Double positionTotal;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			positionTotal = dailyStaffing.getTotalOpening();
			total += positionTotal != null ? positionTotal.doubleValue() : 0.0;
		}
		return new Double(total);
	}

	/**
	 * 
	 * @return
	 */
	public Double getTotalClosing() {
		double total = 0.0;
		Double positionTotal;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			positionTotal = dailyStaffing.getFixedClosing();
			total += positionTotal != null ? positionTotal.doubleValue() : 0.0;
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalFlexible() {
		double total = 0.0;
		Double positionTotal;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			positionTotal = dailyStaffing.getTotalFlexible();
			total += positionTotal != null ? positionTotal.doubleValue() : 0.0;
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalPostRush() {
		double total = 0.0;
		Double positionTotal;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			positionTotal = dailyStaffing.getFixedPostRush();
			total += positionTotal != null ? positionTotal.doubleValue() : 0.0;
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalDailyTarget() {
		double total = 0.0;
		Double positionTotal;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			positionTotal = dailyStaffing.getTotalDailyTarget();
			total += positionTotal != null ? positionTotal.doubleValue() : 0.0;
		}
		return new Double(total);
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public Integer getTotalMinimumStaffingFor(Date time) {
		int total = 0;
		
		Integer minimumStaffing;
		for(DailyFlashStaffing dailyStaffing : getStoreDailyStaffing()) {
			minimumStaffing = dailyStaffing.getHalfHourStaffing(time);
			total += minimumStaffing != null ? minimumStaffing.intValue() : 0;
		}
		
		return new Integer(total);
	}
}
