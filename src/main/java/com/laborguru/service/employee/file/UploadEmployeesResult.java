/**
 * 
 */
package com.laborguru.service.employee.file;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.Employee;

/**
 * @author mariano
 *
 */
public class UploadEmployeesResult {
	private int created = 0;
	private int updated = 0;
	private int error = 0;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
	
	/**
	 * 
	 */
	public UploadEmployeesResult() {
	}

	/**
	 * 
	 * @param employee
	 */
	public void addCreated(Employee employee, int index) {
		getEmployees().add(employee);
		this.created++;
	}

	/**
	 * 
	 * @param employee
	 */
	public void addUpdated(Employee employee, int index) {
		getEmployees().add(employee);
		this.updated++;
	}

	public void addError(Employee employee, SpmUncheckedException ex, int index) {
		getEmployees().add(employee);
		this.errorMessages.add(ex.getErrorMessage());
		this.error++;
	}

	public void addError(Employee employee, ErrorMessage error, int index) {
		getEmployees().add(employee);
		this.errorMessages.add(error);
		this.error++;
	}
	
	/**
	 * @return the updated
	 */
	public int getUpdated() {
		return updated;
	}

	/**
	 * @return the created
	 */
	public int getCreated() {
		return created;
	}

	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}
	
	public int getTotal() {
		return getCreated() + getUpdated() + getError();
	}

	/**
	 * @return the errorMessages
	 */
	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}
	
	public boolean hasErrors() {
		return getError() > 0;
	}
}
