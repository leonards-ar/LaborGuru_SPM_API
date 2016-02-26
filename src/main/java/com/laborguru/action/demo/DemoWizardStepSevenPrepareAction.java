/**
 * 
 */
package com.laborguru.action.demo;

import com.laborguru.action.SpmActionResult;

/**
 * @author mariano
 *
 */
public class DemoWizardStepSevenPrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepSevenPrepareAction() {
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
		if(!isBackButton()) {
			if(!validatePositionNames()) {
				return SpmActionResult.INPUT.getResult();
			}
			
			setPositions();
		}
		
		return SpmActionResult.STEP_7.getResult();
	}
}
