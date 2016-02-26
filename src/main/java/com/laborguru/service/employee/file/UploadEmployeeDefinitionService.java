/**
 * 
 */
package com.laborguru.service.employee.file;

import java.io.File;

/**
 * @author mariano
 *
 */
public interface UploadEmployeeDefinitionService {

	/**
	 * Processes employees from an input file creating or updating the employee based on the following rules
	 * 1) Equal username (if username existis in the definition file)
	 * 2) Equal name and surname in the same Store
	 * 
	 * @param employeeDefinitionFile
	 * @return
	 */
	UploadEmployeesResult processEmployeeDefinitionAndSave(File employeeDefinitionFile);
}
