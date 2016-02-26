package com.laborguru.model.filter;

import com.laborguru.model.Store;

/**
 * Search Employee Filter
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SearchEmployeeFilter {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	private String fullName;
	private Store store;
	
	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
}