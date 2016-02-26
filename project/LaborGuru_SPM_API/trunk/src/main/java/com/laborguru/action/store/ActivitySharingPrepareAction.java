package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class ActivitySharingPrepareAction extends StoreAdministrationBaseAction {

	private static Logger log = Logger.getLogger(ActivitySharingPrepareAction.class);	

	private List<Position> storePositions;
	private List<PositionGroup> positionGroups;
	private List<String> positionPositionGroups;
	
	public ActivitySharingPrepareAction() {
		
	}

	/**
	 * @return the positionPositionGroups
	 */
	public List<String> getPositionPositionGroups() {
		if (this.positionPositionGroups == null){
			this.positionPositionGroups = new ArrayList<String>();
		}		
		return positionPositionGroups;
	}

	/**
	 * @param positionPositionGroups the positionPositionGroups to set
	 */
	public void setPositionPositionGroups(List<String> positionPositionGroups) {
		this.positionPositionGroups = positionPositionGroups;
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
	 * @return the positionGroup
	 */
	public List<PositionGroup> getPositionGroups() {
		return positionGroups;
	}

	/**
	 * @param positionGroup the positionGroup to set
	 */
	public void setPositionGroups(List<PositionGroup> positionGroup) {
		this.positionGroups = positionGroup;
	}

	/**
	 * 
	 */
	private void loadPositionGroupStringList() {
		int positionsSize = getStorePositions().size();
		this.positionPositionGroups = new ArrayList<String>(positionsSize);
		
		for (int i=0; i< positionsSize; i++){
			Position positionAux = getStorePositions().get(i);
			PositionGroup positionGroupAux = positionAux.getPositionGroup();
			String groupNameAux = (positionGroupAux != null)? positionGroupAux.getName() : null;
			getPositionPositionGroups().add(i, groupNameAux);
		}
	}

	/**
	 * 
	 */
	private void loadPositionsAndPositionGroups() {
		setStorePositions(getStore().getPositions());
		setPositionGroups(getStore().getOrderedPositionGroups());
	}	
	
	/**
	 * @param positionPositionGroupName
	 * @return
	 */
	private PositionGroup getPositionGroupByName(String positionPositionGroupName) {
		for (PositionGroup positionGroup: getPositionGroups()){
			if (positionGroup.getName().equals(positionPositionGroupName))
				return positionGroup;
		}
		
		return null;
	}	
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {		
		loadPositionsAndPositionGroups();
		loadPositionGroupStringList();
		
		return SpmActionResult.EDIT.getResult();
	}

	
	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		loadPositionsAndPositionGroups();		
		loadPositionGroupStringList();		
		
		return SpmActionResult.SHOW.getResult();
	}
	
	public void prepareSave() {
		loadPositionsAndPositionGroups();		
	}
	
	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		
		//Setting the group names into the store position list
		int positionsSize = getStorePositions().size();
		
		for(String positionGroup:getPositionPositionGroups()){
			if ((positionGroup == null) || "".equals(positionGroup.trim())){
				addActionError(getText("error.activity.sharing.required"));
			}
		}
		
		if (this.hasActionErrors()){
			return SpmActionResult.INPUT.getResult();
		}
				
		for (int i=0; i< positionsSize; i++){
			Position positionAux = getStorePositions().get(i);
			String positionPositionGroupName = getPositionPositionGroups().get(i);
			PositionGroup positionGroupAux = getPositionGroupByName(positionPositionGroupName);
			
			if (positionGroupAux == null){
				throw new IllegalArgumentException("PositionGroup with name:"+positionPositionGroupName+" was not found!!!");
			}
			
			Position positionToUpdate = getStore().getPositionByName(positionAux.getName());
			positionToUpdate.setPositionGroup(positionGroupAux);
		}		


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
