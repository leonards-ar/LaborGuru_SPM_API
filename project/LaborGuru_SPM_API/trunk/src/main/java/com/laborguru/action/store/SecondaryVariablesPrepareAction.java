/*
 * File name: SecondaryVariablesPrepareAction.java
 * Creation date: Sep 27, 2009 9:09:28 AM
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;
import com.laborguru.model.StoreVariableDefinition;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SecondaryVariablesPrepareAction extends StoreAdministrationBaseAction {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SecondaryVariablesPrepareAction.class);	
	private List<Position> storePositions;	
	private List<String> secondaryVariableNames;
	
	/**
	 * 
	 */
	public SecondaryVariablesPrepareAction() {
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
	private void loadSecondaryVariables() {
		List<StoreVariableDefinition> variableDefinitions = getStore().getVariableDefinitions();
		
		for (StoreVariableDefinition variableDef: variableDefinitions){
			getSecondaryVariableNames().add(variableDef.getVariableIndex(), !StringUtils.isEmpty(variableDef.getName()) ? variableDef.getName() : getText("store.secondary.variable" + variableDef.getVariableIndex() + ".label"));
		}
		
		setStorePositions(getStore().getPositions());

	}	
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isSecondaryVariablePresent(int secondaryVariableIndex) {
		List<StoreVariableDefinition> variableDefinitions = getStore().getVariableDefinitions();
		return variableDefinitions != null && variableDefinitions.size() > secondaryVariableIndex && variableDefinitions.get(secondaryVariableIndex) != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTotalSecondaryVariablesColumns() {
		List<StoreVariableDefinition> variableDefinitions = getStore().getVariableDefinitions();
		if(variableDefinitions == null || variableDefinitions.size() <= 1) {
			return 1; // Just for position label column
		} else {
			return 1 + ((variableDefinitions.size() - 1) * 2);
		}
	}
	
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
	 * 
	 */
	private void setSecondaryVariables() {
		for(Position pos : getStore().getPositions()) {
			int idx = getIndexById(pos.getId());
			if(idx >= 0) {
				Position src = getStorePositions().get(idx);
				pos.setVariable2Flexible(src.getVariable2Flexible());
				pos.setVariable2Opening(src.getVariable2Opening());
				pos.setVariable3Flexible(src.getVariable3Flexible());
				pos.setVariable3Opening(src.getVariable3Opening());
				pos.setVariable4Flexible(src.getVariable4Flexible());
				pos.setVariable4Opening(src.getVariable4Opening());
			}
		}
	}	
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		loadSecondaryVariables();
		
		return SpmActionResult.EDIT.getResult();
	}	
	
	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		loadSecondaryVariables();
		
		return SpmActionResult.SHOW.getResult();
	}	
	
	/**
	 * 
	 */
	public void prepareSave() {
		loadSecondaryVariables();
	}	
	
	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {	
		setSecondaryVariables();
		
		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		this.saveStoreAndLoadItIntoSession(getStore());

		if(log.isInfoEnabled()) {
			log.info("Store secondary variables successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the secondaryVariableNames
	 */
	public List<String> getSecondaryVariableNames() {
		if(secondaryVariableNames == null) {
			setSecondaryVariableNames(new ArrayList<String>(StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY));
		}
		return secondaryVariableNames;
	}

	/**
	 * @param secondaryVariableNames the secondaryVariableNames to set
	 */
	public void setSecondaryVariableNames(List<String> secondaryVariableNames) {
		this.secondaryVariableNames = secondaryVariableNames;
	}		
}
