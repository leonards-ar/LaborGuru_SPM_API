/*
 * File name: PrintShiftByPositionByWeekPrepareAction.java
 * Creation date: Jan 7, 2009 2:20:28 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PrintShiftByPositionByWeekPrepareAction extends PrintScheduleBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PrintShiftByPositionByWeekPrepareAction.class);
	
	/**
	 * 
	 */
	public PrintShiftByPositionByWeekPrepareAction() {
	}


	/**
	 * 
	 * @return
	 */
	public String view() {
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		return SpmActionResult.SUCCESS.getResult();
	}
	
	/**
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		setViewMap(getReferenceDataService().getPrintScheduleViews());
		setSelectedView("printweeklyshiftbyposition_view.action");
		setInTimeOnly(getEmployeeStore().isInTimeOnly());
	}	
}
