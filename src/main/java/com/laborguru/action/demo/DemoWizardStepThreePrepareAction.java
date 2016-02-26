/**
 * 
 */
package com.laborguru.action.demo;

import com.laborguru.action.SpmActionResult;

/**
 * @author mariano
 *
 */
public class DemoWizardStepThreePrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepThreePrepareAction() {
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
		// If BACK from step 4
		setOperationTimes();
		return SpmActionResult.STEP_3.getResult();
	}
}
