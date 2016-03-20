/*
 * File name: DailyProjectedStaffing.java
 * Creation date: Feb 18, 2009 11:55:17 AM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DailyProjectedStaffing extends DailyStaffing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 306443720805302491L;
	
	private List<HalfHourProjectedStaffing> halfHourProjectedStaffing;
	
	/**
	 * 
	 */
	public DailyProjectedStaffing() {
	}

	/**
	 * @return the halfHourStaffing
	 */
	public List<? extends HalfHourStaffing> getHalfHourStaffing() {
		return getHalfHourProjectedStaffing();
	}

	/**
	 * @return the halfHourProjectedStaffing
	 */
	public List<HalfHourProjectedStaffing> getHalfHourProjectedStaffing() {
		if(halfHourProjectedStaffing == null) {
			halfHourProjectedStaffing = new ArrayList<HalfHourProjectedStaffing>();
		}		
		return halfHourProjectedStaffing;
	}

	/**
	 * @param halfHourProjectedStaffing the halfHourProjectedStaffing to set
	 */
	public void setHalfHourProjectedStaffing(List<HalfHourProjectedStaffing> halfHourProjectedStaffing) {
		this.halfHourProjectedStaffing = halfHourProjectedStaffing;
	}	
	
	/**
	 * Adds a HalfHourStaffing. Handles the bi-directional
	 * relation.
	 * @param halfHourStaffing The HalfHourStaffing to add
	 */
	public void addHalfHourStaffing(HalfHourStaffing halfHourStaffing) {
		
		if (halfHourStaffing == null){
			throw new IllegalArgumentException("Null halfHourStaffing passed in as parameter");
		}
		
		if(!(halfHourStaffing instanceof HalfHourProjectedStaffing)) {
			throw new IllegalArgumentException("halfHourStaffing passed in as parameter is not an instance of HalfHourProjectedStaffing");
		}
		
		halfHourStaffing.setDailyStaffing(this);
		getHalfHourProjectedStaffing().add((HalfHourProjectedStaffing)halfHourStaffing);
		halfHourStaffing.setIndex(getHalfHourStaffing().size() - 1);
	}
	
}
