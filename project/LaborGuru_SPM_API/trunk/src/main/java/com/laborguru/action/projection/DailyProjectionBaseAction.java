package com.laborguru.action.projection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Store;
import com.laborguru.model.StoreVariableDefinition;

public abstract class DailyProjectionBaseAction extends ProjectionCalendarBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 *  Checks wheter operation times are defined for an store and adds an error to action error.
	 *  This validatin is only need it when we are gong to save a projection as the operation times are used
	 *  by the distribution calculation
	 */
	protected boolean areOperationTimesDefined() {
		
		Store storeAux = this.getEmployeeStore();		
		for (int j=0; j < 7; j++){
			OperationTime operationTime = storeAux.getStoreOperationTimeByDate(getWeekDaySelector().getWeekDays().get(j));
			if (operationTime == null || operationTime.getOpenHour() == null || operationTime.getCloseHour() == null){
				addActionError(new ErrorMessage(ErrorEnum.OPERATION_TIME_IS_NOT_SET_FOR_STORE.name()));
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Common save action
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		//Initialize Calendar
		initializeDayWeekSelector(getSelectedDate(), getSelectedDate());

		
		//If store operation times are not defined we can't save projections
		if (!areOperationTimesDefined()){
			prepareEdit();
			setupDailyProjectionData();				
			return SpmActionResult.INPUT.getResult();
		}

		//perform save
		customSave();
		
		//Setting screen for edition
		prepareEdit();
		edit();

		return SpmActionResult.SUCCESS.getResult();
	}	

	
	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
		pageSetup();
		loadVariablesNames();		
	}	
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		customEdit();
		setupDailyProjectionData();
		return SpmActionResult.EDIT.getResult();
	}	
	
	protected abstract void customSave();
	protected abstract void customEdit();
	protected abstract void setupDailyProjectionData();		

	
}
