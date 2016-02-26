/*
 * File name: StoreUtilizationBaseAction.java
 * Creation date: 20/07/2008 12:06:55
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class StoreUtilizationBaseAction extends StoreAdministrationBaseAction {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StoreUtilizationBaseAction.class);	
	private List<Position> storePositions;
	
	/**
	 * 
	 */
	public StoreUtilizationBaseAction() {
	}

	/**
	 * @return the storePositions
	 */
	public List<Position> getStorePositions() {
		if(storePositions == null) {
			setStorePositions(new ArrayList<Position>());
		}
		return storePositions;
	}

	/**
	 * @param storePositions the storePositions to set
	 */
	public void setStorePositions(List<Position> storePositions) {
		this.storePositions = storePositions;
	}	
	
	/**
	 * 
	 */
	private void loadUtilization() {
		setStorePositions(getStore().getPositions());
		loadExtraInformation();
	}
	
	/**
	 * 
	 */
	protected void loadExtraInformation() {
		// Do nothing unless needed by subclasses
	}

	/**
	 * 
	 */
	protected void setExtraInformation() {
		// Do nothing unless needed by subclasses
	}
	
	/**
	 * 
	 */
	private void setUtilization() {
		for(Position pos : getStore().getPositions()) {
			int idx = getIndexById(pos.getId());
			if(idx >= 0) {
				setPositionUtilizationValues(getStorePositions().get(idx), pos);
			}
		}
	}
	
	/**
	 * Must set the values the user inputed to the existing domain object
	 * @param src Position object retrieved from GUI
	 * @param dest Position object retrieved from the DB
	 */
	protected abstract void setPositionUtilizationValues(Position src, Position dest);
	
	/**
	 * As equality in Positions is defined taking into account
	 * other values rather than the ID, and this CRUD just
	 * references previously existing positions, then
	 * this method searches a position by its ID.
	 * @param positionId
	 * @return
	 */
	private int getIndexById(Integer positionId) {
		if(positionId == null) {
			return -1;
		}
		for(int i=0; i < getStorePositions().size(); i++) {
			if(positionId.equals(getStorePositions().get(i).getId())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		loadUtilization();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		loadUtilization();
		
		return SpmActionResult.SHOW.getResult();
	}	
	
	/**
	 * 
	 */
	public void prepareSave() {
		loadUtilization();
	}
	
	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {	
		
		setUtilization();
		
		setExtraInformation();
		
		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		this.saveStoreAndLoadItIntoSession(getStore());

		if(log.isInfoEnabled()) {
			log.info("Store utilization successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}	
}
