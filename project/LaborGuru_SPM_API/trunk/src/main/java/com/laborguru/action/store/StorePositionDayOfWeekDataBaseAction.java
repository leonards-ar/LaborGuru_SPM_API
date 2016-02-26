/*
 * File name: StorePositionDayOfWeekDataBaseAction.java
 * Creation date: Jul 14, 2008 3:37:05 PM
 * Copyright Mindpool
 */
package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.DayOfWeekData;
import com.laborguru.model.Position;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class StorePositionDayOfWeekDataBaseAction extends StoreAdministrationBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3662017294898487870L;

	private static Logger log = Logger.getLogger(StorePositionDayOfWeekDataBaseAction.class);

	private String dayOfWeekValues[][];

	private List<String> dayOfWeekNames;
	
	/**
	 * 
	 */
	public StorePositionDayOfWeekDataBaseAction() {
	}
	
	/**
	 * @return the dayOfWeekValues
	 */
	public String[][] getDayOfWeekValues() {
		if(dayOfWeekValues == null) {
			List<Position> p = getStore().getPositions();
			setDayOfWeekValues(new String[p.size()][DayOfWeek.values().length]);
		}
		return dayOfWeekValues;
	}

	/**
	 * @param dayOfWeekValues the dayOfWeekValues to set
	 */
	public void setDayOfWeekValues(String[][] dayOfWeekValues) {
		this.dayOfWeekValues = dayOfWeekValues;
	}	

	/**
	 * Initializes the container object that will handle input of
	 * weekday guest services.
	 */
	private void loadDayOfWeekData() {
		if(getStore() != null) {
			List<Position> p = getStore().getPositions();

			DayOfWeekData aDayOfWeekData;
			int firstDayOfWeekIndex = getStore().getFirstDayOfWeek().ordinal();
			int dayOfWeekIndex;
			for(int i = 0; i < p.size(); i++) {
				for(int j = 0; j < DayOfWeek.values().length; j++) {
					dayOfWeekIndex = getDayOfWeekIndex(j, firstDayOfWeekIndex);
					aDayOfWeekData = dayOfWeekIndex < p.get(i).getDayOfWeekData().size() ? p.get(i).getDayOfWeekData().get(dayOfWeekIndex) : null;
					if(aDayOfWeekData != null) {
						getDayOfWeekValues()[i][j] = getValueToShow(aDayOfWeekData);
					} else {
						// Leave it null. At the bigining there might be no values.
						getDayOfWeekValues()[i][j] = null;
					}
				}
			}
		}
	}	
	
	/**
	 * 
	 * @param counter
	 * @param firstDayOfWeekIndex
	 * @return
	 */
	private int getDayOfWeekIndex(int counter, int firstDayOfWeekIndex) {
		int idx;
		if(counter + firstDayOfWeekIndex < DayOfWeek.values().length) {
			idx = counter + firstDayOfWeekIndex;
		} else {
			idx = (counter  + firstDayOfWeekIndex) - DayOfWeek.values().length;
		}
		return idx;  
	}
	
	/**
	 * Puts all the corresponding values in the Store object
	 * so it can be updated.
	 */
	private void setDayOfWeekData() {
		if(getStore() != null) {
			List<Position> p = getStore().getPositions();

			DayOfWeekData aDayOfWeekData;
			int firstDayOfWeekIndex = getStore().getFirstDayOfWeek().ordinal();
			int dayOfWeekIndex;
			for(int i = 0; i < p.size(); i++) {
				for(int j = 0; j < DayOfWeek.values().length; j++) {
					dayOfWeekIndex = getDayOfWeekIndex(j, firstDayOfWeekIndex);
					aDayOfWeekData = j < p.get(i).getDayOfWeekData().size() ? p.get(i).getDayOfWeekData().get(dayOfWeekIndex) : null;
					if(aDayOfWeekData != null) {
						// Already exists. Update value
						setDayOfWeekDataValue(aDayOfWeekData, getDayOfWeekValues()[i][j]);
					} else {
						// Add new day part data
						aDayOfWeekData = new DayOfWeekData();
						aDayOfWeekData.setPosition(p.get(i));
						aDayOfWeekData.setDayOfWeekIndex(dayOfWeekIndex);
						setDayOfWeekDataValue(aDayOfWeekData, getDayOfWeekValues()[i][j]);
						
						p.get(i).getDayOfWeekData().add(aDayOfWeekData);
					}
				}
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
		loadDayOfWeekData();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		loadDayOfWeekData();
		
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		setDayOfWeekData();
		
		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		this.saveStoreAndLoadItIntoSession(getStore());

		if(log.isInfoEnabled()) {
			log.info("Store position/day of week data successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * Validates all the times.
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		
		if(this.dayOfWeekValues != null) {
			List<Position> p = getStore().getPositions();
			
			for(int i=0; i < getDayOfWeekValues().length; i++) {
				for(int j=0; j < getDayOfWeekValues()[0].length; j++) {
					if(!validateValue(getDayOfWeekValues()[i][j])) {
						Position position = p.get(i);
						
						addFieldError( "dayOfWeekValues", getText(getValidationErrorMessageKey(), new String[] {getDayOfWeekValues()[i][j], position.getName(), getDayOfWeekNames().get(j) }) );
					}
				}
			}
		}
		
	}
	
	/**
	 * This method must be defined by the sub class to retrieve the
	 * correct value to show.
	 * @param dayOfWeekData
	 * @return
	 */
	protected abstract String getValueToShow(DayOfWeekData dayOfWeekData);	
	
	/**
	 * This method has to fill the day part data with the corresponding value
	 * @param dayOfWeekData
	 * @param value
	 */
	protected abstract void setDayOfWeekDataValue(DayOfWeekData dayOfWeekData, String value);	
	
	/**
	 * 
	 * @return
	 */
	protected abstract String getValidationErrorMessageKey();
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	protected abstract boolean validateValue(String value);

	/**
	 * @return the dayOfWeekNames
	 */
	public List<String> getDayOfWeekNames() {
		if(dayOfWeekNames == null) {
			dayOfWeekNames= new ArrayList<String>(DayOfWeek.values().length);
			int firstDayOfWeekIndex = getStore().getFirstDayOfWeek().ordinal();
			int dayOfWeekIndex;
			for(int j = 0; j < DayOfWeek.values().length; j++) {
				dayOfWeekIndex = getDayOfWeekIndex(j, firstDayOfWeekIndex);
				dayOfWeekNames.add(getText("dayofweek." + dayOfWeekIndex));
			}
		}
		return dayOfWeekNames;
	}

	/**
	 * @param dayOfWeekNames the dayOfWeekNames to set
	 */
	public void setDayOfWeekNames(List<String> dayOfWeekNames) {
		this.dayOfWeekNames = dayOfWeekNames;
	}	
}
