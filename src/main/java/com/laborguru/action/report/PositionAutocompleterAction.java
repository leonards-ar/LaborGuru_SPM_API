package com.laborguru.action.report;

import java.util.HashMap;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.service.position.PositionService;
import com.laborguru.service.positionGroup.PositionGroupService;

/**
 * This action is intended to load positions or positiongroups 
 * according to the selected option. 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionAutocompleterAction extends ScheduleReportPrepareAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -333892522802167631L;
	
	private PositionService positionService;
	
	private PositionGroupService positionGroupService;
	
	private boolean disabled = false;
	/**
	 * @return the positionGroupService
	 */
	public PositionGroupService getPositionGroupService() {
		return positionGroupService;
	}

	/**
	 * @param positionGroupService the positionGroupService to set
	 */
	public void setPositionGroupService(PositionGroupService positionGroupService) {
		this.positionGroupService = positionGroupService;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public String execute() throws Exception {

		if("byPosition".equals(getSelectedGrouping())){
			setItemWithPositions();
			setDisabled(false);
		} else if("byService".equals(getSelectedGrouping())){
			setItemWithPositionGroups();
			setDisabled(false);
		} else {
			setDisabled(true);
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			setItemsMap(map);
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	private void setItemWithPositions() {
		List<Position> positions = positionService.getPositionsByStore(getEmployeeStore());
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		for(Position position: positions) {
			map.put(position.getId(), position.getName());
		}
		setItemsMap(map);
	}
	
	private void setItemWithPositionGroups() {
		List<PositionGroup> positionGroups = positionGroupService.getPositionGroupsByStore(getEmployeeStore());
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		for(PositionGroup positionGroup: positionGroups) {
			map.put(positionGroup.getId(), positionGroup.getName());
		}
		
		setItemsMap(map);
	}
	
	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	
}
