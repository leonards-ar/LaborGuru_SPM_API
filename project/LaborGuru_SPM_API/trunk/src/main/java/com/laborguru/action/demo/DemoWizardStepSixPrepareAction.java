/**
 * 
 */
package com.laborguru.action.demo;

import org.apache.commons.lang.StringUtils;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;

/**
 * @author mariano
 *
 */
public class DemoWizardStepSixPrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DemoWizardStepSixPrepareAction() {
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
			if(!validateVariableDefinitionNames()) {
				return SpmActionResult.INPUT.getResult();
			}
			setVariableDefinitionNames();
		}
		
		loadPositions();
		
		return SpmActionResult.STEP_6.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String addPosition() {
		if(getNewPosition() != null && !StringUtils.isBlank(getNewPosition().getName())) {
			Position newPos = new Position();
			buildDefaultPosition(newPos);
			newPos.setName(getNewPosition().getName());
			newPos.setPositionIndex(new Integer(getPositions().size()));
			newPos.setGuestService(getNewPosition().isGuestService());
			newPos.setManager(getNewPosition().isManager());
			getPositions().add(newPos);
			newPos = null;				
		} else if(getNewPosition() == null || StringUtils.isBlank(getNewPosition().getName())) {
			addActionError("error.storeoperations.positionnames.addnew.name.required");
		}
		
		setNewPosition(null);
		return SpmActionResult.STEP_6.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String removePosition() {
		int idx = getIndex() != null ? getIndex().intValue() : -1;
		if(idx >= 0 && idx < getPositions().size()) {
			getPositions().remove(idx);
		}
		return SpmActionResult.STEP_6.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String positionOneUp() {
		if(getPositions() != null) {
			int idx = getIndex() != null ? getIndex().intValue() : -1;
			if(idx > 0 && idx < getPositions().size()) {
				Position aux = getPositions().get(idx-1);
				getPositions().set(idx-1, getPositions().get(idx));
				getPositions().set(idx, aux);
			}
		}
		return SpmActionResult.STEP_6.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String positionOneDown() {
		if(getPositions() != null) {
			int idx = getIndex() != null ? getIndex().intValue() : -1;
			if(idx >= 0 && idx < getPositions().size() - 1) {
				Position aux = getPositions().get(idx+1);
				getPositions().set(idx+1, getPositions().get(idx));
				getPositions().set(idx, aux);
			}
		}
		return SpmActionResult.STEP_6.getResult();
	}	
}
