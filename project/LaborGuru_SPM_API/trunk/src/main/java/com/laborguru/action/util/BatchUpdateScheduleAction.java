/*
 * File name: ScheduleDetailsAction.java
 * Creation date: Nov 27, 2008 11:06:46 AM
 * Copyright Mindpool
 */
package com.laborguru.action.util;

import com.laborguru.action.SpmActionResult;
import com.laborguru.action.schedule.ScheduleBaseAction;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.schedule.ScheduleService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class BatchUpdateScheduleAction extends ScheduleBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7865174211531869913L;
	
	private ScheduleService scheduleService;
	private ProjectionService projectionService;
	
	/**
	 * @return the projectionService
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}

	/**
	 * @param projectionService the projectionService to set
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}

	/**
	 * 
	 */
	public BatchUpdateScheduleAction() {
	}

	/**
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		getScheduleService().updateAllStoreSchedules();
		getProjectionService().updateAll(true);
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the scheduleService
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * @param scheduleService the scheduleService to set
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
}
