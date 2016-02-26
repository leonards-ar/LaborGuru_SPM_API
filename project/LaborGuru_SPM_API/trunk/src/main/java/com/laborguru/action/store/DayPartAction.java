package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.DayPart;

/**
 * DayPart Action: this action handles daypart CRUD operations for a store
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
public class DayPartAction extends StoreAdministrationBaseAction {

	private static Logger log = Logger.getLogger(DayPartAction.class);

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private List<DayPart> dayParts = new ArrayList<DayPart>();
	private DayPart newDayPart = new DayPart();
	private Integer index;
	
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the newDayPart
	 */
	public DayPart getNewDayPart() {
		return newDayPart;
	}

	/**
	 * @param newDayPart the newDayPart to set
	 */
	public void setNewDayPart(DayPart newDayPart) {
		this.newDayPart = newDayPart;
	}


	/**
	 * @return the dayParts
	 */
	public List<DayPart> getDayParts() {
		return dayParts;
	}

	/**
	 * @param dayParts
	 *            the dayParts to set
	 */
	public void setDayParts(List<DayPart> dayParts) {
		this.dayParts = dayParts;
	}

	/**
	 * Load the dayparts list into the action
	 */
	private void loadDayPartList() {

		setDayParts(getStore().getDayParts());

	}

	/**
	 * This method prepares the edit day part page
	 * 
	 * @return
	 */
	public String edit() {
		loadDayPartList();
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * This method prepares the show dayPart page
	 * 
	 * @return
	 */
	public String show() {
		loadDayPartList();
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * This method add a daypart to the list
	 * 
	 * @return
	 */
	public String add() {
		addNewDayPartToList();		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Adds the newDayPart to dayParts List
	 * 
	 */
	private void addNewDayPartToList() {
		
			getNewDayPart().setPositionIndex(getDayParts().size());
			getDayParts().add(getNewDayPart());

			//Clearing dayPartHelper
			setNewDayPart(new DayPart());
	}

	/**
	 * This method removes a daypart from the list
	 * 
	 * @return
	 */
	public String remove() {
		
		if (getIndex() != null)
		{
			getDayParts().remove(getIndex().intValue());
			fixDayPartsPositionIndex();
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	

	/**
	 * Moves up the daypart selected on the dayParts lists
	 * 
	 * @return
	 */
	public String oneUp() {
		
		if (getIndex() != null && getIndex() > 0)
		{
			int indexAux = getIndex(); 
			int indexPrev = indexAux - 1;
			
			swapDayParts(indexAux, indexPrev);			
		}
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Moves down the daypart selected on the dayParts lists
	 * 
	 * @return
	 */
	public String oneDown() {
		
		
		if ((getIndex() != null) && (getIndex() < (getDayParts().size() - 1)))
		{
			int indexAux = getIndex(); 
			int indexNext = indexAux + 1;
			
			swapDayParts(indexNext, indexAux);
		}
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Saves a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {

		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		//Adding a the newDayPart to the list if needed
		if (getNewDayPart().getName() != null && !"".equals(getNewDayPart().getName()) 
			&& getNewDayPart().getStartHour() != null){				
			addNewDayPartToList();
		}
		
		setDayPartsToStore();

		this.saveStoreAndLoadItIntoSession(getStore());

		if(log.isInfoEnabled()) {
			log.info("Store dayparts successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}	
	


	/**
	 * Prepares the dayParts list to be saved on the DB.
	 * Handles the updated, created and removed dayParts on the list.
	 */
	private void setDayPartsToStore() {
		HashMap<Integer, DayPart> dbDayPartTable= new HashMap<Integer, DayPart>();
		HashMap<Integer, DayPart> requestDayPartTable= new HashMap<Integer, DayPart>();
		
		//Removing the deleted dayParts
		//we use an auxliar hashtables to identify the changes.
		
		for (DayPart auxDayPart: getDayParts()){
			requestDayPartTable.put(auxDayPart.getId(), auxDayPart);
		}
		
		//Ttmporal array is needed as we need objects that don't have any link to original list.
		DayPart[] dayPartsDb = (DayPart[]) getStore().getDayParts().toArray(new DayPart[getStore().getDayParts().size()]);
		
		for (int i=0; i < dayPartsDb.length; i++){
			if (!requestDayPartTable.containsKey(dayPartsDb[i].getId())){
				getStore().removeDayPart(dayPartsDb[i]);
			}
		}
		
		//Updating the existing dayParts and adding the new ones
		//we use an auxliar hashtables to identify the changes.

		for (DayPart auxDayPart: getStore().getDayParts()){
			if (requestDayPartTable.containsKey(auxDayPart.getId())){
				dbDayPartTable.put(auxDayPart.getId(), auxDayPart);
			}
		}
		
		for(DayPart auxDayPart : getDayParts()){			
			if (dbDayPartTable.containsKey(auxDayPart.getId())){
				DayPart dayPart = dbDayPartTable.get(auxDayPart.getId());

				dayPart.setName(auxDayPart.getName());
				dayPart.setPositionIndex(auxDayPart.getPositionIndex());
				dayPart.setStartHour(auxDayPart.getStartHour());
			}else {
				if (auxDayPart.getId() == null){
					getStore().addDayPart(auxDayPart);
				}
			}			
		}				
		
		
	}

	/**
	 * This method swaps 2 dayParts on the DayParts list. 
	 * Indexes passed as parameter should be valid indexes.
	 * 
	 * It also updates the index Positions on the dayParts references.
	 * 
	 * @param index1 index of the dayPart to swap 
	 * @param index0 index of the dayPart to swap
	 */
	private void swapDayParts(int index1, int index0) {
		DayPart dayPartAux = getDayParts().get(index1);
		DayPart dayPartPrev = getDayParts().get(index0);
		
		dayPartAux.setPositionIndex(index0); 			
		dayPartPrev.setPositionIndex(index1);
					
		dayPartAux = getDayParts().set(index1, dayPartPrev);
		getDayParts().set(index0, dayPartAux);
	}	
	
	/**
	 * This method sets the index Position of the dayParts elements with the element index of list.
	 */
	private void fixDayPartsPositionIndex(){
		int i = 0;
		for (DayPart dayPart:getDayParts()){
			dayPart.setPositionIndex(i++);
		}
	}
}
