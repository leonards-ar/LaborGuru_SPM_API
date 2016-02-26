package com.laborguru.action.employee;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Profile;
import com.laborguru.model.Store;
import com.laborguru.service.store.StoreService;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class EmployeeStorePrepareAction extends EmployeeBaseAction {

	private Integer storeId;
	private Store store;
	
	private StoreService storeService; 
	
	@Override
	protected void addFilters() {
	}

	@Override
	protected List<Employee> getEmployees() {
		Store auxStore = new Store();
		auxStore.setId(getStoreId());
		return getEmployeeService().getEmployeesByStore(auxStore);
	}

	@Override
	protected void setExtraInformation(){
		if(getStoreId() != null) {
			Store auxStore = new Store();
			auxStore.setId(getStoreId());
			setStore(getStoreService().getStoreById(auxStore));
		} else {
			setStore(getStoreService().getStoreById(getEmployee().getStore()));
			setStoreId(getStore().getId());
		}
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
	 * Adds a Store association to an employee
	 * 
	 * @see com.laborguru.action.employee.EmployeeBaseAction#setAssociation()
	 */
	protected void setAssociation(){
		Store auxStore = new Store();
		auxStore.setId(getStoreId());
		getEmployee().setStore(auxStore);
	}

	/**
	 * @return the storeId
	 */
	public Integer getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService the storeService to set
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	/**
	 * @return
	 * @see com.laborguru.action.employee.EmployeeBaseAction#retrievePositions()
	 */
	@Override
	protected List<Position> retrievePositions() {
		if(getStoreId() != null) {
			Store auxStore = new Store();
			auxStore.setId(getStoreId());
			List<Position> positions = getPositionService().getPositionsByStore(auxStore);
			return positions != null ? positions : new ArrayList<Position>();
		} else {
			// :TODO: Fix. This method is called for listing employees where positions are not
			// required! 
			return new ArrayList<Position>();
		}
	}
	
}
