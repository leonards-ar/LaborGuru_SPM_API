/*
 * File name: UtilizationLimitsAction.java
 * Creation date: Jul 18, 2008 4:57:38 PM
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import com.laborguru.model.Position;

/**
 * Deals with limit utilization CRUD
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UtilizationLimitsAction extends StoreUtilizationBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UtilizationLimitsAction() {
	}
	
	/**
	 * Must set the values the user inputed to the existing domain object
	 * @param src Position object retrieved from GUI
	 * @param dest Position object retrieved from the DB
	 * @see com.laborguru.action.store.StoreUtilizationBaseAction#setPositionUtilizationValues(com.laborguru.model.Position, com.laborguru.model.Position)
	 */
	@Override
	protected void setPositionUtilizationValues(Position src, Position dest) {
		dest.setUtilizationMinimum(src.getUtilizationMinimum());
		dest.setUtilizationMaximum(src.getUtilizationMaximum());
	}	

}
