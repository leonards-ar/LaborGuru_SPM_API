/*
 * File name: OtherfactorsPrepareAction.java
 * Creation date: 18/10/2008 09:45:28
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class OtherFactorsPrepareAction extends StoreAdministrationBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1320796348800866148L;

	private static Logger log = Logger.getLogger(OtherFactorsPrepareAction.class);

	private Double scheduleInefficiency = null;
	private Double fillInefficiency = null;
	private Double trainingFactor = null;
	private Double earnedBreakFactor = null;
	private Double floorManagementFactor = null;
	private Integer minimumFloorManagementHours = null;
	
	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
	}

	/**
	 * Prepare data to be used to display store data
	 */
	public void prepareShow() {
	}

	/**
	 * Prepare the data to be used on the add page. We should preload the list
	 * needed to render the add page. When a validation fails the application
	 * goes back to the add page and this data is needed.
	 * 
	 * @throws Exception
	 */
	public void prepareSave() {
	}

	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
	setScheduleInefficiency(getStore().getScheduleInefficiency());
		setFillInefficiency(getStore().getFillInefficiency());
		setFloorManagementFactor(getStore().getFloorManagementFactor());
		setMinimumFloorManagementHours(getStore().getMinimumFloorManagementHours());
		setTrainingFactor(getStore().getTrainingFactor());
		setEarnedBreakFactor(getStore().getEarnedBreakFactor());
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Prepares the view page
 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
			
			if(log.isDebugEnabled()) {
				log.debug("About to save store: " + getStore());
			}

			getStore().setScheduleInefficiency(getScheduleInefficiency());
			getStore().setFillInefficiency(getFillInefficiency());
			getStore().setFloorManagementFactor(getFloorManagementFactor());
			getStore().setMinimumFloorManagementHours(getMinimumFloorManagementHours());
			getStore().setTrainingFactor(getTrainingFactor());
			getStore().setEarnedBreakFactor(getEarnedBreakFactor());			
			
			saveStoreAndLoadItIntoSession(getStore());
			
			if(log.isInfoEnabled()) {
				log.info("Store other factors successfully updated for store with id [" + getStoreId() + "]");
			}
			
			return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
	}

	/**
	 * @return the scheduleInefficiency
	 */
	public Double getScheduleInefficiency() {
		return scheduleInefficiency;
	}

	/**
	 * @param scheduleInefficiency the scheduleInefficiency to set
	 */
	public void setScheduleInefficiency(Double scheduleInefficiency) {
		this.scheduleInefficiency = scheduleInefficiency;
	}

	/**
	 * @return the fillInefficiency
	 */
	public Double getFillInefficiency() {
		return fillInefficiency;
	}

	/**
	 * @param fillInefficiency the fillInefficiency to set
	 */
	public void setFillInefficiency(Double fillInefficiency) {
		this.fillInefficiency = fillInefficiency;
	}

	/**
	 * @return the trainingFactor
	 */
	public Double getTrainingFactor() {
		return trainingFactor;
	}

	/**
	 * @param trainingFactor the trainingFactor to set
	 */
	public void setTrainingFactor(Double trainingFactor) {
		this.trainingFactor = trainingFactor;
	}

	/**
	 * @return the earnedBreakFactor
  */
	public Double getEarnedBreakFactor() {
		return earnedBreakFactor;
	}

	/**
	 * @param earnedBreakFactor the earnedBreakFactor to set
	 */
	public void setEarnedBreakFactor(Double earnedBreakFactor) {
		this.earnedBreakFactor = earnedBreakFactor;
	}

	/**
	 * @return the floorManagementFactor
	 */
	public Double getFloorManagementFactor() {
		return floorManagementFactor;
	}

	/**
	 * @param floorManagementFactor the floorManagementFactor to set
	 */
	public void setFloorManagementFactor(Double floorManagementFactor) {
		this.floorManagementFactor = floorManagementFactor;
	}

	/**
	 * @return the minimumFloorManagementHours
	 */
	public Integer getMinimumFloorManagementHours() {
		return minimumFloorManagementHours;
	}

	/**
	 * @param minimumFloorManagementHours the minimumFloorManagementHours to set
	 */
	public void setMinimumFloorManagementHours(Integer minimumFloorManagementHours) {
		this.minimumFloorManagementHours = minimumFloorManagementHours;
	}
}
