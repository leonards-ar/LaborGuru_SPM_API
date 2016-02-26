package com.laborguru.service.employee.dao;

import java.util.List;

import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchEmployeeFilter;

public interface EmployeeDao {

	/**
	 * Saves or updates employee
	 * @param employee Employee to save or update
	 * @return
	 */
	Employee save(Employee employee);
	
	/**
	 * Deletes employee
	 * @param employee Employee to Delete
	 */
	void delete(Employee employee);
	
	/**
	 * Retrieves an employee by Id
	 * @param employee Employee with id populated.
	 * @return Employee the employee
	 */
	Employee getEmployeeById(Employee employee);
	
	/**
	 * Retrieves an employee by Store
	 * @param store The store that is going to be used to retrieves the employees
	 * @return List<Employee> the list of employee
	 */
	List<Employee> getEmployeesByStore(Store store);

	/**
	 * Retrieves a list of employees based on the filter passed in as parameter.
	 * If there is no employees that match the criteria return an empty list.
	 * 
	 * @param searchEmployee The filter
	 * @return The employee list
	 */
	List<Employee> applyFilter(SearchEmployeeFilter searchEmployee);

	/**
	 * Retrieves an employee by username
	 * @param employee with the username populated
	 * @return
	 */
	Employee getEmployeeByUsername(Employee employee);

	/**
	 * Retrieves an employee by name, surname and store
	 * @param employee with store, name and surname fields populated
	 * @return
	 */
	Employee getStoreEmployeeByCompleteName(Employee employee);
	
}
