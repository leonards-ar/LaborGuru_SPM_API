/*
 * File name: FixedGuestServicesPrepareAction.java
 * Creation date: 14/07/2008 12:11:32
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import com.laborguru.action.utils.CustomValidators;
import com.laborguru.model.DayPartData;

/**
 * This action deals with Store CRUD.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class FixedGuestServicesPrepareAction extends StorePositionDayPartDataBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3821676992364005487L;

	/**
	 * 
	 */
	public FixedGuestServicesPrepareAction() {
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.action.store.StorePositionDayPartDataBaseAction#getValidationErrorMessageKey()
	 */
	@Override
	protected String getValidationErrorMessageKey() {
		return "error.storeoperations.fixedguestservice.value.invalid";
	}
	
	
	/**
	 * 
	 * @param dayPartData
	 * @return
	 * @see com.laborguru.action.store.StorePositionDayPartDataBaseAction#getValueToShow(com.laborguru.model.DayPartData)
	 */
	@Override
	protected String getValueToShow(DayPartData dayPartData) {
		return doubleToDisplayDouble(dayPartData.getFixedGuestService());
	}

	/**
	 * 
	 * @param dayPartData
	 * @param value
	 * @see com.laborguru.action.store.StorePositionDayPartDataBaseAction#setDayPartDataValue(com.laborguru.model.DayPartData, java.lang.String)
	 */
	@Override
	protected void setDayPartDataValue(DayPartData dayPartData, String value) {
		dayPartData.setFixedGuestService(displayDoubleToDouble(value));
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @see com.laborguru.action.store.StorePositionDayPartDataBaseAction#validateValue(java.lang.String)
	 */
	@Override
	protected boolean validateValue(String value) {
		return CustomValidators.isValidDouble(value);
	}
}

