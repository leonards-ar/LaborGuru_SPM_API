/**
 * 
 */
package com.laborguru.service.employee.file;

import java.io.File;
import java.util.List;

import com.laborguru.model.Employee;

/**
 * @author mariano
 *
 */
public interface EmployeeDefinitionFileParser {

	/**
	 * Parses the input file and returns the list of employees
	 * @param employeesToUpload
	 * @return
	 */
	List<Employee> parseEmployees(File employeesToUpload);
}
