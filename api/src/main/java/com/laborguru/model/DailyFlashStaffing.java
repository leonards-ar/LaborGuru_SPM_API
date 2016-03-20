/*
 * File name: DailyStaffing.java
 * Creation date: 19/10/2008 15:46:47
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
public class DailyFlashStaffing extends DailyStaffing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468262896963405878L;
	
	private List<HalfHourFlashStaffing> halfHourFlashStaffing;
	
	/**
	 * 
	 */
	public DailyFlashStaffing() {
	}

	/**
	 * @return the halfHourStaffing
	 */
	public List<? extends HalfHourStaffing> getHalfHourStaffing() {
		return getHalfHourFlashStaffing();
	}

	/**
	 * @return the halfHourHistoricSalesStaffing
	 */
	public List<HalfHourFlashStaffing> getHalfHourFlashStaffing() {
		if(halfHourFlashStaffing == null) {
			halfHourFlashStaffing = new ArrayList<HalfHourFlashStaffing>();
		}		
		return halfHourFlashStaffing;
	}

	/**
	 * @param halfHourFlashStaffing the halfHourProjectedStaffing to set
	 */
	public void setHalfHourFlashStaffing(List<HalfHourFlashStaffing> halfHourFlashStaffing) {
		this.halfHourFlashStaffing = halfHourFlashStaffing;
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
		
		if(!(halfHourStaffing instanceof HalfHourFlashStaffing)) {
			throw new IllegalArgumentException("halfHourStaffing passed in as parameter is not an instance of HalfHourHistoricSalesStaffing");
		}
		
		halfHourStaffing.setDailyStaffing(this);
		getHalfHourFlashStaffing().add((HalfHourFlashStaffing)halfHourStaffing);
		halfHourStaffing.setIndex(getHalfHourStaffing().size() - 1);
	}

}
