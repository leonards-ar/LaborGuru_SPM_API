/**
 * 
 */
package com.laborguru.action.demo;

import com.laborguru.action.SpmActionResult;

/**
 * @author mariano
 *
 */
public class DemoWizardStepFourPrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepFourPrepareAction() {
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
			// If BACK from step 5
			setVariableDefinitionNames();
		}

		loadOperationTimes();
		return SpmActionResult.STEP_4.getResult();
	}
}
