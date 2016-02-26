package com.laborguru.action.employee;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Profile;

/**
 * This action deals with Employee CRUD.
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class EmployeePrepareAction extends EmployeeBaseAction {


	/**
	 * Sets additional filters for this special action
	 * 
	 * @see com.laborguru.action.employee.EmployeeBaseAction#addFilters()
	 */
	protected void addFilters(){
		getSearchEmployee().setStore(getEmployeeStore());
	}
		
	/**
	 * Retrieves the store that is in session
	 * @return
	 * @see com.laborguru.action.employee.EmployeeBaseAction#getStore()
	 */
	protected List<Employee> getEmployees() {
		return getEmployeeService().getEmployeesByStore(getEmployeeStore());
	}

	/**
	 * Retrieves the Profile that he user should have.
	 * @return
	 * @see com.laborguru.action.employee.EmployeeBaseAction#getEmployeeProfile()
	 */
	protected Profile getEmployeeProfile() {
		if(getEmployee().isManager()) {
			return getReferenceDataService().getManagerRole();
		} 
		return getReferenceDataService().getEmployeeRole();
	}
	
	/**
	 * @return
	 * @see com.laborguru.action.employee.EmployeeBaseAction#retrievePositions()
	 */
	@Override
	protected List<Position> retrievePositions() {
		List<Position> positions = getPositionService().getPositionsByStore(getEmployeeStore());
		return positions != null ? positions : new ArrayList<Position>();
	}

	/**
	 * Adds a Store, Area, Region or Customer association to an employee
	 * 
	 * @see com.laborguru.action.employee.EmployeeBaseAction#setAssociation()
	 */
	protected void setAssociation(){
		getEmployee().setStore(getEmployeeStore());
	}

	@Override
	protected void setExtraInformation() {
	}

}
