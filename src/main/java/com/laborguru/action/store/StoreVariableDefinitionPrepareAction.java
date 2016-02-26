package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.StoreVariableDefinition;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro<a>
 * @version 1.1
 * @since SPM 1.1
 * 
 */
@SuppressWarnings("serial")
public class StoreVariableDefinitionPrepareAction extends StoreAdministrationBaseAction implements Preparable {

	private static Logger log = Logger.getLogger(StoreVariableDefinitionPrepareAction.class);

	private List<StoreVariableDefinition> variableDefinitions;
	
	private List<StoreVariableDefinition> removeVariableDefinitions;

	private String newVariableDefinitionName;
	
	private Integer index;

	/**
	 * Prepare the data to be used on the page
	 * 
	 * @throws Exception
	 */
	public void prepare() throws Exception {
	}

	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() throws Exception {
	}

	/**
	 * Prepare the data to be used on the show page
	 * 
	 * @throws Exception
	 */
	public void prepareShow() throws Exception {
	}

	/**
	 * Shows the edit page
	 * 
	 * @return
	 */
	public String edit() {
		loadVariableDefinitions();
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Shows the list page
	 * 
	 * @return
	 */
	public String show() {
		loadVariableDefinitions();
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private StoreVariableDefinition getVariableDefinitionById(Integer id) {
		if(id != null) {
			for(StoreVariableDefinition storeVariableDefinition: getStore().getVariableDefinitions()){
				if (id.equals(storeVariableDefinition.getId())) {
					return storeVariableDefinition;
				}
			}		
		}
		return null;
	}
	
	/**
	 * 
	 */
	private void setStoreVariableDefinitionNames() throws SpmCheckedException{
		if (!"".equals(getNewVariableDefinitionName().trim())) {
			addNewVariableDefinition();
		}

		// Add or update existing variable definitions
		for (StoreVariableDefinition variableDefinition : getVariableDefinitions()) {
			StoreVariableDefinition storeVariableDefinition = getVariableDefinitionById(variableDefinition.getId());
			if (storeVariableDefinition != null) {
				storeVariableDefinition.setName(variableDefinition.getName());
			} else {
				getStore().addVariableDefinition(variableDefinition);
			}
		}

		// Delete variable definitions
		for (StoreVariableDefinition variableDefinition : getRemoveVariableDefinitions()) {
			StoreVariableDefinition variableDefinitionToRemove = getVariableDefinitionById(variableDefinition.getId());
			getStore().getVariableDefinitions().remove(variableDefinitionToRemove);
		}

		int i = 0;
		for (StoreVariableDefinition variableDefinition : getStore().getVariableDefinitions()) {
			variableDefinition.setVariableIndex(new Integer(i));
			i++;
		}
		
	}
	/**
	 * Save the variable definitions to the store.
	 * @return
	 */
	public String save() {
		try{
			setStoreVariableDefinitionNames();
		}catch(SpmCheckedException e){
			addActionError(e.getErrorMessage());
			return SpmActionResult.INPUT.getResult();			
		}
		
		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		this.saveStoreAndLoadItIntoSession(getStore());
		
		if(log.isInfoEnabled()) {
			log.info("Store variable definitions successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * load the store's variable definitions
	 */
	public void loadVariableDefinitions() {
		if (getStore() != null) {
			setVariableDefinitions(getStore().getVariableDefinitions());
		}
	}

	/**
	 * Add a new variable definition to the Variable Definitions List; 
	 * @return
	 */
	private void addNewVariableDefinition() {
		StoreVariableDefinition newVariableDefinition = new StoreVariableDefinition();
		newVariableDefinition.setName(getNewVariableDefinitionName());
		newVariableDefinition.setVariableIndex(getVariableDefinitions().size());
		newVariableDefinition.setStore(getStore());
		getVariableDefinitions().add(newVariableDefinition);
	}
	
	
	
	/**
	 * add a variable definition
	 * 
	 * @return
	 */
	public String addVariableDefinition() {
		addNewVariableDefinition();
		setNewVariableDefinitionName(null);
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Removes an element from VariableDefinitions
	 * 
	 * @return
	 */
	public String removeVariableDefinition() {
		StoreVariableDefinition removeVariableDefinition = getVariableDefinitions().remove(getIndex().intValue());
		if(removeVariableDefinition.getId() != null) {
			getRemoveVariableDefinitions().add(removeVariableDefinition);
			fixVariableDefinitionIndexes();
		}
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * This method sets the index Variable Definition of the dayParts elements with the element index of list.
	 */
	private void fixVariableDefinitionIndexes(){
		int i = 0;
		for (StoreVariableDefinition variableDefinition: getVariableDefinitions()){
			variableDefinition.setVariableIndex(new Integer(i++));
		}
	}
	
	/**
	 * Moves a variable definition up.
	 * @return
	 */
	public String oneUp() {
		if (getIndex() != null && getIndex() > 0) {
			int indexAux = getIndex(); 
			int indexPrev = indexAux - 1;
			
			swapVariableDefinitions(indexAux, indexPrev);			
		}
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * Moves a variable definition Down
	 * @return
	 */
	public String oneDown() {
		if (getIndex() !=null && getIndex() < getVariableDefinitions().size() - 1) {
			int indexAux = getIndex();
			int indexNext = indexAux + 1;
			if(indexNext < getVariableDefinitions().size()) {
				swapVariableDefinitions(indexNext, indexAux);
			}
		}
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * This method swaps 2 variable definitions on the variable definitions list. 
	 * Indexes passed as parameter should be valid indexes.
	 * 
	 * @param index1 index of the variable definition to swap 
	 * @param index0 index of the variable definition to swap
	 */
	private void swapVariableDefinitions(int index1, int index0) {
		StoreVariableDefinition varDefAux = getVariableDefinitions().get(index1);
		StoreVariableDefinition prevVarDef = getVariableDefinitions().get(index0);
		
		varDefAux.setVariableIndex(new Integer(index0)); 			
		prevVarDef.setVariableIndex(new Integer(index1));
					
		varDefAux = getVariableDefinitions().set(index1, prevVarDef);
		getVariableDefinitions().set(index0, varDefAux);
	}	
	
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		if (index == null) {
			index = new Integer(-1);
		}
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}


	/**
	 * @return the variableDefinitions
	 */
	public List<StoreVariableDefinition> getVariableDefinitions() {
		if(variableDefinitions == null) {
			setVariableDefinitions(new ArrayList<StoreVariableDefinition>());
		}
		return variableDefinitions;
	}

	/**
	 * @param variableDefinitions the variableDefinitions to set
	 */
	public void setVariableDefinitions(
			List<StoreVariableDefinition> variableDefinitions) {
		this.variableDefinitions = variableDefinitions;
	}

	/**
	 * @return the removeVariableDefinitions
	 */
	public List<StoreVariableDefinition> getRemoveVariableDefinitions() {
		if(removeVariableDefinitions == null) {
			setRemoveVariableDefinitions(new ArrayList<StoreVariableDefinition>());
		}
		return removeVariableDefinitions;
	}

	/**
	 * @param removeVariableDefinitions the removeVariableDefinitions to set
	 */
	public void setRemoveVariableDefinitions(
			List<StoreVariableDefinition> removeVariableDefinitions) {
		this.removeVariableDefinitions = removeVariableDefinitions;
	}

	/**
	 * @return the newVariableDefinitionName
	 */
	public String getNewVariableDefinitionName() {
		return newVariableDefinitionName;
	}

	/**
	 * @param newVariableDefinitionName the newVariableDefinitionName to set
	 */
	public void setNewVariableDefinitionName(String newVariableDefinitionName) {
		this.newVariableDefinitionName = newVariableDefinitionName;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMaximumVariableDefinitionsReached() {
		return getVariableDefinitions().size() >= StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY;
	}
}
