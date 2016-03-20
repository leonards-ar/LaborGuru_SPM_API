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
public class DailyHistoricSalesStaffing extends DailyStaffing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7468262896963405878L;
	
	private List<HalfHourHistoricSalesStaffing> halfHourHistoricSalesStaffing;
	
	/**
	 * 
	 */
	public DailyHistoricSalesStaffing() {
	}

	/**
	 * @return the halfHourStaffing
	 */
	public List<? extends HalfHourStaffing> getHalfHourStaffing() {
		return getHalfHourHistoricSalesStaffing();
	}

	/**
	 * @return the halfHourHistoricSalesStaffing
	 */
	public List<HalfHourHistoricSalesStaffing> getHalfHourHistoricSalesStaffing() {
		if(halfHourHistoricSalesStaffing == null) {
			halfHourHistoricSalesStaffing = new ArrayList<HalfHourHistoricSalesStaffing>();
		}		
		return halfHourHistoricSalesStaffing;
	}

	/**
	 * @param halfHourHistoricSalesStaffing the halfHourProjectedStaffing to set
	 */
	public void setHalfHourHistoricSalesStaffing(List<HalfHourHistoricSalesStaffing> halfHourHistoricSalesStaffing) {
		this.halfHourHistoricSalesStaffing = halfHourHistoricSalesStaffing;
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
		
		if(!(halfHourStaffing instanceof HalfHourHistoricSalesStaffing)) {
			throw new IllegalArgumentException("halfHourStaffing passed in as parameter is not an instance of HalfHourHistoricSalesStaffing");
		}
		
		halfHourStaffing.setDailyStaffing(this);
		getHalfHourHistoricSalesStaffing().add((HalfHourHistoricSalesStaffing)halfHourStaffing);
		halfHourStaffing.setIndex(getHalfHourStaffing().size() - 1);
	}

}
