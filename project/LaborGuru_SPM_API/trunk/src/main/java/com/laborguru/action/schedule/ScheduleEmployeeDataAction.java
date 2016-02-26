/*
 * File name: ScheduleEmployeeAutocompleterAction.java
 * Creation date: Oct 4, 2008 8:51:07 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import com.laborguru.action.SpmAction;
import com.laborguru.model.Employee;
import com.laborguru.service.employee.EmployeeService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleEmployeeDataAction extends SpmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577253668801585437L;

	private Integer employeeId;
	private Employee employee;
	
	private EmployeeService employeeService;
	
	/**
	 * 
	 */
	public ScheduleEmployeeDataAction() {
	}

    /**
     * 
     * @return
     * @throws Exception
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() throws Exception {
    	Employee employee = getEmployeeService().getEmployeeById(new Employee(getEmployeeId()));
    	
    	setEmployee(employee);
    	
        return SUCCESS;
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
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
