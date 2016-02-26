package com.laborguru.action.projection;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.model.StoreVariableDefinition;
import com.laborguru.service.store.StoreService;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * This action deals with Projections Settings.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
@SuppressWarnings("serial")
public class ProjectionSettingsPrepareAction extends SpmAction implements Preparable {
	private static Logger log = Logger.getLogger(ProjectionSettingsPrepareAction.class);

	private Integer dailyWeeksUsedDefault;
	private Integer halfHourWeeksUsedDefault;
	private List<Double> storeVariablesAverages;

	private StoreService storeService;

	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
		setDailyWeeksUsedDefault(this.getEmployeeStore().getDailyProjectionsWeeksDefault());
		setHalfHourWeeksUsedDefault(this.getEmployeeStore().getHalfHourProjectionsWeeksDefault());
		loadSecondaryVariablesAverages();
	}

	private void loadSecondaryVariablesAverages() {
		List<StoreVariableDefinition> variableDefinitions = getEmployeeStore().getVariableDefinitions();
		
		boolean isMainVariable = true;
		for (StoreVariableDefinition variableDef: variableDefinitions) {
			// Remove all this code when averageVariable gets finally removed
			if(isMainVariable) {
				Double currentValue = getEmployeeStore().getAverageVariable();
				if(currentValue != null && !currentValue.equals(variableDef.getAverage())) {
					variableDef.setAverage(currentValue);
				}
				isMainVariable = false;
			}
			// End of code that should be removed
			getStoreVariablesAverages().add(variableDef.getVariableIndex(), variableDef.getAverage() != null ? variableDef.getAverage() : SpmConstants.DOUBLE_ONE_VALUE);
		}
	}	
	
	/**
	 * 
	 * @return
	 */
	public String getMainVariable(int index) {
		StoreVariableDefinition varDef = this.getEmployeeStore().getVariableDefinitions().get(index);
		return varDef != null ? varDef.getName() : "";
	}
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		// It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Stores an employee on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		
		getEmployeeStore().setDailyProjectionsWeeksDefault(getDailyWeeksUsedDefault());
		getEmployeeStore().setHalfHourProjectionsWeeksDefault(getHalfHourWeeksUsedDefault());
		getEmployeeStore().setAverageVariable(null); // First make sure we null the deprecated variable
		
		for(int i=0; i < getStoreVariablesAverages().size(); i++) {
			if(i < getEmployeeStore().getVariableDefinitions().size()) {
				getEmployeeStore().getVariableDefinitions().get(i).setAverage(getStoreVariablesAverages().get(i));
			} else {
				log.warn("The size of the variable definitions don't match with the whats configured");
			}
		}
		
		storeService.save(getEmployeeStore());
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the dailyWeeksUsedDefault
	 */
	public Integer getDailyWeeksUsedDefault() {
		return dailyWeeksUsedDefault;
	}

	/**
	 * @param dailyWeeksUsedDefault the dailyWeeksUsedDefault to set
	 */
	public void setDailyWeeksUsedDefault(Integer dailyWeeksUsedDefault) {
		this.dailyWeeksUsedDefault = dailyWeeksUsedDefault;
	}

	/**
	 * @return the halfHourWeeksUsedDefault
	 */
	public Integer getHalfHourWeeksUsedDefault() {
		return halfHourWeeksUsedDefault;
	}

	/**
	 * @param halfHourWeeksUsedDefault the halfHourWeeksUsedDefault to set
	 */
	public void setHalfHourWeeksUsedDefault(Integer halfHourWeeksUsedDefault) {
		this.halfHourWeeksUsedDefault = halfHourWeeksUsedDefault;
	}

	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService the storeService to set
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * @return the secondaryVariablesAverages
	 */
	public List<Double> getStoreVariablesAverages() {
		if(storeVariablesAverages == null) {
			setStoreVariablesAverages(new ArrayList<Double>(StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY));
		}
		
		return storeVariablesAverages;
	}

	/**
	 * @param secondaryVariablesAverages the secondaryVariablesAverages to set
	 */
	public void setStoreVariablesAverages(List<Double> secondaryVariablesAverages) {
		this.storeVariablesAverages = secondaryVariablesAverages;
	}
}
