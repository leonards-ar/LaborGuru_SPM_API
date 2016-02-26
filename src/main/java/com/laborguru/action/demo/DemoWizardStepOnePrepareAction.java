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
public class DemoWizardStepOnePrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepOnePrepareAction() {
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
		getSession().remove(STORE_SESSION_KEY);
		getSession().remove(EMPLOYEE_SESSION_KEY);
		
		if(getDemoStores() == null || getDemoStores().size() <= 0) {
			addActionError(new ErrorMessage("error.demo.wizard.noDemoStore"));
			return SpmActionResult.CANCEL.getResult();
		} else if(getDemoStores().size() == 1) {
			setSourceDemoStoreId(getDemoStores().get(0).getId());
			// Start with new store
			getSession().remove(STORE_SESSION_KEY);
			getSession().remove(EMPLOYEE_SESSION_KEY);
			copyStore();
			
			return SpmActionResult.STEP_2.getResult();
		} else {
			
			return SpmActionResult.STEP_1.getResult();
		}
	}
}
