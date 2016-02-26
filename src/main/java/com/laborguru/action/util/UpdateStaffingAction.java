package com.laborguru.action.util;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.util.CalendarUtils;

public class UpdateStaffingAction extends SpmAction {

	private String date;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4235753113788304917L;

	private ProjectionService projectionService;
	
	/**
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		
		if(getDate() != null && getDate().trim().length() > 0) {
			getProjectionService().updateAll(CalendarUtils.stringToDate(getDate(), "yyyyMMdd"), true);
		} else {
			getProjectionService().updateAll(true);
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
