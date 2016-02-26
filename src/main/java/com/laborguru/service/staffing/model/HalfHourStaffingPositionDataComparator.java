/*
 * File name: HalfHourStaffingPositionDataComparator.java
 * Creation date: Oct 21, 2008 1:39:26 PM
 * Copyright Mindpool
 */
package com.laborguru.service.staffing.model;

import java.util.Comparator;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourStaffingPositionDataComparator implements Comparator<HalfHourStaffingPositionData> {

	/**
	 * 
	 */
	public HalfHourStaffingPositionDataComparator() {
	}

	/**
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(HalfHourStaffingPositionData o1, HalfHourStaffingPositionData o2) {
		if(o1.getWorkContentDecimalPart() > o2.getWorkContentDecimalPart()) {
			return -1;
		} else if(o1.getWorkContentDecimalPart() < o2.getWorkContentDecimalPart()) {
			return 1;
		} else {
			return 0;
		}
	}

}
