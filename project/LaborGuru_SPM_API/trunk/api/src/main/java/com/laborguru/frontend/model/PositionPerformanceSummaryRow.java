/*
 * File name: PositionPerformanceSummaryRow.java
 * Creation date: 22/02/2009 16:16:26
 * Copyright Mindpool
 */
package com.laborguru.frontend.model;

import java.io.Serializable;

import com.laborguru.model.Position;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionPerformanceSummaryRow implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4653289499855419092L;
	
	private Double projectedTarget;
	private Double projectedScheduled;
	private Position position;
	
	/**
	 * 
	 */
	public PositionPerformanceSummaryRow() {
	}

	/**
	 * @return the projectedScheduled
	 */
	public Double getProjectedScheduled() {
		return projectedScheduled;
	}

	/**
	 * @param projectedScheduled the projectedScheduled to set
	 */
	public void setProjectedScheduled(Double projectedScheduled) {
		this.projectedScheduled = projectedScheduled;
	}

	/**
	 * @return the projectedTarget
	 */
	public Double getProjectedTarget() {
		return projectedTarget;
	}

	/**
	 * @param projectedTarget the projectedTarget to set
	 */
	public void setProjectedTarget(Double projectedTarget) {
		this.projectedTarget = projectedTarget;
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
	 * 
	 * @return
	 */
	public Double getProjectedDifference() {
		return new Double(NumberUtils.getDoubleValue(getProjectedScheduled()) - NumberUtils.getDoubleValue(getProjectedTarget()));
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getProjectedDifferencePercentage() {
		double target = NumberUtils.getDoubleValue(getProjectedTarget());
		if(target != 0.0) {
			double diff = getProjectedDifference().doubleValue();
			return new Double(diff / target * 100);
		} else {
			return new Double(0.0);
		}
	}		
}
