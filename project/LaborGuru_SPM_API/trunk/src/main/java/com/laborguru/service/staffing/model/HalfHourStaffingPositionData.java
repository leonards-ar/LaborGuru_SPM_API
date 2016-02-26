/*
 * File name: HalfHourStaffingPositionData.java
 * Creation date: Oct 21, 2008 11:50:40 AM
 * Copyright Mindpool
 */
package com.laborguru.service.staffing.model;

import java.io.Serializable;

import com.laborguru.model.Position;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourStaffingPositionData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3722974469725917225L;
	
	private Position position;
	private double utilization;
	private double projection;
	private double workContent;
	private int workContentIntegerPart;
	private double workContentDecimalPart;
	private boolean additionalEmployee;
	private int positionMinimumStaffing;

	/**
	 * 
	 * @param position
	 */
	public HalfHourStaffingPositionData(Position position) {
		super();
		setPosition(position);
	}

	/**
	 * @return the utilization
	 */
	public double getUtilization() {
		return utilization;
	}

	/**
	 * @param utilization the utilization to set
	 */
	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}

	/**
	 * @return the projection
	 */
	public double getProjection() {
		return projection;
	}

	/**
	 * @param projection the projection to set
	 */
	public void setProjection(double projection) {
		this.projection = projection;
	}

	/**
	 * @return the workContent
	 */
	public double getWorkContent() {
		return workContent;
	}

	/**
	 * @param workContent the workContent to set
	 */
	public void setWorkContent(double workContent) {
		this.workContent = workContent;
		this.workContentIntegerPart = (int) workContent;
		this.workContentDecimalPart = workContent - this.workContentIntegerPart;
	}

	/**
	 * @return the additionalEmployee
	 */
	public boolean isAdditionalEmployee() {
		return additionalEmployee;
	}

	/**
	 * @param additionalEmployee the additionalEmployee to set
	 */
	public void setAdditionalEmployee(boolean additionalEmployee) {
		this.additionalEmployee = additionalEmployee;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWorkContentIntegerPart() {
		return workContentIntegerPart;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getWorkContentDecimalPart() {
		return workContentDecimalPart;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMinimumStaffing() {
		int calculatedMinimumStaffing = isAdditionalEmployee() ? getWorkContentIntegerPart() + 1 : getWorkContentIntegerPart();
		return Math.max(calculatedMinimumStaffing, getPositionMinimumStaffing()); 
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
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) 
			return false;
		
		if(!(obj instanceof HalfHourStaffingPositionData)) 
			return false;
		
		if(this == obj)
			return true;
		
		HalfHourStaffingPositionData other = (HalfHourStaffingPositionData) obj;
		
		return getPosition() != null ? getPosition().equals(other.getPosition()) : false;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getPosition() != null ? getPosition().hashCode() : super.hashCode();
	}

	/**
	 * @return the positionMinimumStaffing
	 */
	public int getPositionMinimumStaffing() {
		return positionMinimumStaffing;
	}

	/**
	 * @param positionMinimumStaffing the positionMinimumStaffing to set
	 */
	public void setPositionMinimumStaffing(int positionMinimumStaffing) {
		this.positionMinimumStaffing = positionMinimumStaffing;
	}

}
