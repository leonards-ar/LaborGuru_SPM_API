/**
 * 
 */
package com.laborguru.action.demo;

import org.apache.commons.lang.StringUtils;

import com.laborguru.action.SpmActionResult;

/**
 * @author mariano
 *
 */
public class DemoWizardStepFivePrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepFivePrepareAction() {
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#validateSession()
	 */
	@Override
	public boolean validateSession() {
		return true;
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#executeStep()
	 */
	@Override
	public String executeStep() throws Exception {
		if(isBackButton()) {
			// If BACK from step 6
			setPositions();
		} else {
			if(!validateOperationTimes()) {
				return SpmActionResult.INPUT.getResult();
			}
			setOperationTimes();
		}
		
		loadVariableDefinitionNames();
		return SpmActionResult.STEP_5.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String variableDefinitionOneUp() {
		if(getVariableDefinitionNames() != null) {
			int idx = getIndex() != null ? getIndex().intValue() : -1;
			if(idx > 0 && idx < getVariableDefinitionNames().size()) {
				String aux = getVariableDefinitionNames().get(idx-1);
				getVariableDefinitionNames().set(idx-1, getVariableDefinitionNames().get(idx));
				getVariableDefinitionNames().set(idx, aux);
			}
		}
		return SpmActionResult.STEP_5.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String variableDefinitionOneDown() {
		if(getVariableDefinitionNames() != null) {
			int idx = getIndex() != null ? getIndex().intValue() : -1;
			if(idx >= 0 && idx < getVariableDefinitionNames().size() - 1) {
				String aux = getVariableDefinitionNames().get(idx+1);
				getVariableDefinitionNames().set(idx+1, getVariableDefinitionNames().get(idx));
				getVariableDefinitionNames().set(idx, aux);
			}
		}
		return SpmActionResult.STEP_5.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String addVariableDefinition() {
		if(!isMaximumVariableDefinitionsReached() && !StringUtils.isBlank(getNewVariableDefinitionName())) {
			getVariableDefinitionNames().add(getNewVariableDefinitionName());
		} else if(!isMaximumVariableDefinitionsReached() && StringUtils.isBlank(getNewVariableDefinitionName())) {
			addActionError("error.storevariabledefinitions.variablenames.addnew.name.required");
		}
		setNewVariableDefinitionName(null);
		return SpmActionResult.STEP_5.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeVariableDefinition() {
		int idx = getIndex() != null ? getIndex().intValue() : -1;
		if(idx >= 0 && idx < getVariableDefinitionNames().size()) {
			getVariableDefinitionNames().remove(idx);
		}
		return SpmActionResult.STEP_5.getResult();
	}	
}
