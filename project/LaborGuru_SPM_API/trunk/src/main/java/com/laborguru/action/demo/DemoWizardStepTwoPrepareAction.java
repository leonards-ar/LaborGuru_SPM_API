/**
 * 
 */
package com.laborguru.action.demo;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorMessage;

/**
 * @author mariano
 *
 */
public class DemoWizardStepTwoPrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepTwoPrepareAction() {
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#validateSession()
	 */
	@Override
	public boolean validateSession() {
		return false;
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#executeStep()
	 */
	@Override
	public String executeStep() throws Exception {
		if(getSourceDemoStore() == null) {
			addActionError(new ErrorMessage("error.demo.wizard.noDemoStore"));
			return SpmActionResult.CANCEL.getResult();
		}
		if(!isBackButton()) {
			// Start with new store
			getSession().remove(STORE_SESSION_KEY);
			getSession().remove(EMPLOYEE_SESSION_KEY);
			copyStore();
		}
		return SpmActionResult.STEP_2.getResult();
	}
}
