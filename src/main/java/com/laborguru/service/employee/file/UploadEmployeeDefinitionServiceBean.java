/**
 * 
 */
package com.laborguru.service.employee.file;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.model.Customer;
import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.employee.EmployeeService;
import com.laborguru.service.position.PositionService;
import com.laborguru.service.store.StoreService;

/**
 * @author mariano
 *
 */
public class UploadEmployeeDefinitionServiceBean implements UploadEmployeeDefinitionService {
	
	private EmployeeDefinitionFileParser employeeDefinitionFileParser;
	private EmployeeService employeeService;
	private CustomerService customerService;
	private PositionService positionService;
	private StoreService storeService;
	
	/**
	 * 
	 */
	public UploadEmployeeDefinitionServiceBean() {
	}

	/**
	 * @param employeeDefinitionFile
	 * @return
	 * @see com.laborguru.service.employee.file.UploadEmployeeDefinitionService#processEmployeeDefinitionAndSave(java.io.File)
	 */
	public UploadEmployeesResult processEmployeeDefinitionAndSave(File employeeDefinitionFile) {
		UploadEmployeesResult result = new UploadEmployeesResult();
		List<Employee> employees = getEmployeeDefinitionFileParser().parseEmployees(employeeDefinitionFile);
		int size = employees != null ? employees.size() : 0;

		Employee employee;
		
		for(int i = 0; i < size; i++) {
			employee = employees.get(i);
			try {
				if(validate(employee, i + 1, result)) {
					Employee existingEmployee = getExistingEmployee(employee);
					if(existingEmployee != null) {
						// Existing employee. Copy values from employee in file
						copyEmployee(employee, existingEmployee);
						getEmployeeService().save(existingEmployee);
						result.addUpdated(employee, i);
					} else {
						getEmployeeService().save(employee);
						// New employee
						result.addCreated(employee, i);
					}
				}
			} catch(SpmUncheckedException ex) {
				result.addError(employee, ex, i);
			} catch(Throwable ex) {
				result.addError(employee, new SpmUncheckedException(ex.getCause(), ex.getMessage(), ErrorEnum.GENERIC_DATABASE_ERROR), i);
			}
		}
		
		return result;
	}

	private void copyEmployee(Employee source, Employee destination) {
		// First Name
		destination.setName(source.getName());
		// Last Name
		destination.setSurname(source.getSurname());
		// Username
		destination.setUserName(source.getUserName());
		// e-mail
		destination.setEmail(source.getEmail());
		// Password
		destination.setPassword(source.getPassword());
		//$ Wage
		destination.setWage(source.getWage());
		//Phone
		destination.setPhone(source.getPhone());
		// Alternate Phone
		destination.setPhone2(source.getPhone2());
		// Max hours per week
		destination.setMaxHoursWeek(source.getMaxHoursWeek());
		// Max hours per day
		destination.setMaxHoursDay(source.getMaxHoursDay());
		// Max days per week
		destination.setMaxDaysWeek(source.getMaxDaysWeek());
		// Default position
		destination.setDefaultPosition(source.getDefaultPosition());
		// Status
		destination.setStatus(source.getStatus());
		// Manager (1 yes, 0 No)
		destination.setManager(source.isManager());
		// Hire Date
		destination.setHireDate(source.getHireDate());
		// Address
		destination.setAddress(source.getAddress());
		//Address 2
		destination.setAddress2(source.getAddress2());
		// City
		destination.setCity(source.getCity());
		//State
		destination.setState(source.getState());
		//Zip
		destination.setZip(source.getZip());
		//Comments
		destination.setComments(source.getComments());
		
	}
	
	private Employee getExistingEmployee(Employee employeeFromFile) {
		Employee existingEmployee = getEmployeeService().getEmployeeByUsername(employeeFromFile);
		
		if(existingEmployee == null) {
			existingEmployee = getEmployeeService().getStoreEmployeeByCompleteName(employeeFromFile);
		}
		
		return existingEmployee;
	}
	
	private boolean validate(Employee employee, int row, UploadEmployeesResult result) {
		boolean valid = true;
		
		valid &= validateCustomer(employee, row, result);
		valid &= validateStore(employee, row, result);
		valid &= validatePosition(employee, row, result);
		valid &= validateEmployee(employee, row, result);
		
		return valid;
	}

	private boolean validateStore(Employee employee, int row, UploadEmployeesResult result) {
		try {
			SearchStoreFilter filter = new SearchStoreFilter();
			filter.setCode(employee.getStore().getCode());
			filter.setCustomerCode(employee.getStore().getArea().getRegion().getCustomer().getCode());
			List<Store> stores = getStoreService().filterStore(filter);

			if(stores == null || stores.isEmpty()) {
				result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_INVALID_STORE, new String[] {employee.getStore().getCode(), employee.getStore().getArea().getRegion().getCustomer().getCode()}, row), row - 1);
				return false;
			} else {
				employee.setStore(stores.get(0));
				return true;
			}
		} catch(NullPointerException ex) {
			result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_STORE_REQUIRED, null, row), row - 1);
			return false;
		} catch(IllegalArgumentException ex) {
			result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_STORE_REQUIRED, null, row), row - 1);
			return false;
		}
		
	}
	
	private boolean validateEmployee(Employee employee, int row, UploadEmployeesResult result) {
		boolean valid = true;
		valid &= validateEmployeeField(employee, employee.getUserName(), "error.username.required", row, result);
		valid &= validateEmployeeField(employee, employee.getPassword(), "error.password.required", row, result);
		valid &= validateEmployeeField(employee, employee.getName(), "error.name.required", row, result);
		valid &= validateEmployeeField(employee, employee.getSurname(), "error.surname.required", row, result);
		
		if(!StringUtils.isBlank(employee.getUserName())) {
			Employee e = getEmployeeService().getEmployeeByUsername(employee); 
			if(e != null && e.getStore() != null && !e.getStore().equals(employee.getStore())) {
				result.addError(employee, new ErrorMessage(ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[]{employee.getUserName()}, row), row - 1);				
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean validateEmployeeField(Employee employee, String field, String messageKey, int row, UploadEmployeesResult result) {
		if(StringUtils.isBlank(field)) {
			ErrorMessage error = new ErrorMessage(messageKey, null);
			error.setRow(row);
			result.addError(employee, error, row - 1);
			return false;
		}
		return true;
	}
	
	private boolean validateCustomer(Employee employee, int row, UploadEmployeesResult result) {
		try {
			Customer customer = getCustomerService().getCustomerByCode(employee.getStore().getArea().getRegion().getCustomer());
			if(customer == null) {
				result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_INVALID_CUSTOMER, new String[] {employee.getStore().getArea().getRegion().getCustomer().getCode()}, row), row - 1);
				return false;
			}
			return true;
		} catch(NullPointerException ex) {
			result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_CUSTOMER_REQUIRED, null, row), row - 1);
			return false;
		} catch(IllegalArgumentException ex) {
			result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_CUSTOMER_REQUIRED, null, row), row - 1);
			return false;
		}
	}
	
	private boolean validatePosition(Employee employee, int row, UploadEmployeesResult result) {
		if(employee.getDefaultPosition() != null) {
			try {
				employee.getDefaultPosition().setStore(employee.getStore());
				Position position = getPositionService().getStorePositionByName(employee.getDefaultPosition());
				if(position == null) {
					result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_INVALID_DEFAULT_POSITION, new String[] {employee.getDefaultPosition().getName()}, row), row - 1);
					return false;
				}
				employee.setDefaultPosition(position);
				return true;
			} catch(IllegalArgumentException ex) {
				result.addError(employee, new ErrorMessage(ErrorEnum.EMPLOYEE_STORE_REQUIRED, null, row), row - 1);
				return false;
			}
		} else {
			// This field is optional
			return true;
		}
	}
	
	/**
	 * @return the employeeDefinitionParser
	 */
	public EmployeeDefinitionFileParser getEmployeeDefinitionFileParser() {
		return employeeDefinitionFileParser;
	}

	/**
	 * @param employeeDefinitionFileParser the employeeDefinitionParser to set
	 */
	public void setEmployeeDefinitionFileParser(EmployeeDefinitionFileParser employeeDefinitionFileParser) {
		this.employeeDefinitionFileParser = employeeDefinitionFileParser;
	}

	/**
	 * @return the employeeService
	 */
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @return the customerService
	 */
	public CustomerService getCustomerService() {
		return customerService;
	}

	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
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
}
