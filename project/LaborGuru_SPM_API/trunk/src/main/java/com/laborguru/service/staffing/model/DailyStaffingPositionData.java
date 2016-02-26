/*
 * File name: DailyStaffingPositionData.java
 * Creation date: 12/11/2008 23:18:31
 * Copyright Mindpool
 */
package com.laborguru.service.staffing.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.laborguru.model.DayPartData;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DailyStaffingPositionData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2629969275009440055L;
	private BigDecimal dayPartTotalProjection = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private DayPartData dayPartData = null;
	private Position position = null;
	private Store store = null;
	
	/**
	 * 
	 */
	public DailyStaffingPositionData() {
	}

	/**
	 * @return the dayPartTotalProjection
	 */
	public BigDecimal getDayPartTotalProjection() {
		return dayPartTotalProjection;
	}

	/**
	 * @param dayPartTotalProjection the dayPartTotalProjection to set
	 */
	public void setDayPartTotalProjection(BigDecimal dayPartTotalProjection) {
		this.dayPartTotalProjection = dayPartTotalProjection;
	}
	
	/**
	 * 
	 * @param halfHourProjection
	 */
	public void addHalfHourProjection(BigDecimal halfHourProjection) {
		setDayPartTotalProjection(getDayPartTotalProjection().add(halfHourProjection));
	}

	/**
	 * @return the dayPartData
	 */
	public DayPartData getDayPartData() {
		if(dayPartData == null) {
			setDayPartData(new DayPartData());
		}
		return dayPartData;
	}

	/**
	 * @param dayPartData the dayPartData to set
	 */
	public void setDayPartData(DayPartData dayPartData) {
		this.dayPartData = dayPartData;
	}

	/**
	 * 
	 * @return
	 */
	public double getVariableFlexible() {
		if(getStore() != null) {
			return NumberUtils.getDoubleValue(getDayPartTotalProjection()) * NumberUtils.getDoubleValue(getDayPartData().getVariableFlexible()) / (NumberUtils.getDoubleValue(getStore().getAllPositionsUtilization()) / 100);
		} else {
			return NumberUtils.getDoubleValue(getDayPartTotalProjection()) * NumberUtils.getDoubleValue(getDayPartData().getVariableFlexible());
		}
	}

	/**
	 * 
	 * @return
	 */
	public double getVariableOpening() {
		if(getStore() != null) {
			return NumberUtils.getDoubleValue(getDayPartTotalProjection()) * NumberUtils.getDoubleValue(getDayPartData().getVariableOpening()) / (NumberUtils.getDoubleValue(getStore().getAllPositionsUtilization()) / 100);
		} else {
			return NumberUtils.getDoubleValue(getDayPartTotalProjection()) * NumberUtils.getDoubleValue(getDayPartData().getVariableOpening());
		}
	}

	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
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
}
