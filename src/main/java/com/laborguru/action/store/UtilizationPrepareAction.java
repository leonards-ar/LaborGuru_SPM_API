/*
 * File name: UtilizationPrepareAction.java
 * Creation date: Jul 18, 2008 4:57:38 PM
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import com.laborguru.model.Position;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UtilizationPrepareAction extends StoreUtilizationBaseAction {

	private Double allPositionsUtilization;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7232005512019173339L;

	/**
	 * 
	 */
	public UtilizationPrepareAction() {
	}
	

	/**
	 * 
	 * 
	 * @see com.laborguru.action.store.StoreUtilizationBaseAction#loadExtraInformation()
	 */
	protected void loadExtraInformation() {
		setAllPositionsUtilization(getStore().getAllPositionsUtilization());
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.store.StoreUtilizationBaseAction#setExtraInformation()
	 */
	protected void setExtraInformation() {
		getStore().setAllPositionsUtilization(getAllPositionsUtilization());
	}	

	/**
	 * @return the allPositionsUtilization
	 */
	public Double getAllPositionsUtilization() {
		return allPositionsUtilization;
	}

	/**
	 * @param allPositionsUtilization the allPositionsUtilization to set
	 */
	public void setAllPositionsUtilization(Double allPositionsUtilization) {
		this.allPositionsUtilization = allPositionsUtilization;
	}

	/**
	 * Must set the values the user inputed to the existing domain object
	 * @param src Position object retrieved from GUI
	 * @param dest Position object retrieved from the DB
	 * @see com.laborguru.action.store.StoreUtilizationBaseAction#setPositionUtilizationValues(com.laborguru.model.Position, com.laborguru.model.Position)
	 */
	@Override
	protected void setPositionUtilizationValues(Position src, Position dest) {
		dest.setUtilizationBottom(src.getUtilizationBottom());
		dest.setUtilizationTop(src.getUtilizationTop());
	}	

}
