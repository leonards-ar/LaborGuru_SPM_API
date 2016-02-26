package com.laborguru.action.util;

import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Shift;
import com.laborguru.service.schedule.ShiftService;

public class UpdateSchedulesAction extends SpmAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4235753113788304917L;

	private ShiftService shiftService;
	
	private List<Shift> shifts;
	
	public String execute() {
		
		setShifts(getShiftService().updateAll()); 
	
		return SpmActionResult.SUCCESS.getResult();
		
	}
	
	/**
	 * @return the shiftService
	 */
	public ShiftService getShiftService() {
		return shiftService;
	}
	/**
	 * @param shiftService the shiftService to set
	 */
	public void setShiftService(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

	/**
	 * @return the shifts
	 */
	public List<Shift> getShifts() {
		return shifts;
	}

	/**
	 * @param shifts the shifts to set
	 */
	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}
	
	
	
}
