package com.laborguru.service.employee;

import java.util.List;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchEmployeeFilter;
import com.laborguru.service.Service;

/**
 * Employee Service Interface. Handles employee services for SPM employees.  
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface EmployeeService extends Service {
	
	/**
	 * Saves employee
	 * @param employee Employee to save
	 * @return
	 * @throws SpmCheckedException 
	 */
	Employee save(Employee employee) throws SpmCheckedException;
	
	/**
	 * Deletes employee
	 * @param employee Employee to Delete
	 */
	void delete(Employee employee);

	/**
	 * 
	 * @param employee
	 * @throws SpmCheckedException
	 */
	void logicalDelete(Employee employee) throws SpmCheckedException;
	
	/**
	 * Retrieves an employee by Id
	 * @param employee Employee with id populated.
	 * @return Employee the employee
	 */
	Employee getEmployeeById(Employee employee);

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
	
	/**
	 * Retrieves an employee by Store
	 * @param store The store that is going to be used to retrieves the employees
	 * @return List<Employee> the list of employee
	 */
	List<Employee> getEmployeesByStore(Store store);
	
	/**
	 * Retrieves a list of employees filtered by the SearchEmployeeFilter
	 * @param searchEmployee The filter for the employee search
	 * @return The employee list
	 */
	List<Employee> filterEmployee(SearchEmployeeFilter searchEmployee);
}
