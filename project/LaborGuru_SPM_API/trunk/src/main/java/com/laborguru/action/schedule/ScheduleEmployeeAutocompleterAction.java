/*
 * File name: ScheduleEmployeeAutocompleterAction.java
 * Creation date: Oct 4, 2008 8:51:07 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Employee;
import com.laborguru.service.employee.EmployeeService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleEmployeeAutocompleterAction extends SpmAction implements ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577253668801585437L;

	private Map<Integer, String> storeEmployees;
	
	private EmployeeService employeeService;
	
	private HttpServletResponse response;
	
	/**
	 * 
	 */
	public ScheduleEmployeeAutocompleterAction() {
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
    private void loadStoreEmployees() {
    	storeEmployees = new HashMap<Integer, String>();
    	List<Employee> employees = getEmployeeService().getEmployeesByStore(getEmployeeStore());
    	
    	for(Employee anEmployoee : employees) {
    		storeEmployees.put(anEmployoee.getId(), anEmployoee.getFullName());
    	}
    	
    }

    /**
     * 
     * @return
     * @throws Exception
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() throws Exception {
    	loadStoreEmployees();
    	invalidateResponseCache();
        return SpmActionResult.SUCCESS.getResult();
    }
    
	/**
	 * @return the storeEmployees
	 */
	public Map<Integer, String> getStoreEmployees() {
		return storeEmployees;
	}
	
	/**
	 * @param storeEmployees the storeEmployees to set
	 */
	public void setStoreEmployees(Map<Integer, String> storeEmployees) {
		this.storeEmployees = storeEmployees;
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
	 * 
	 * @param arg0
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 
	 * @param arg0
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}	
	
	/**
	 * 
	 */
	private void invalidateResponseCache() {
		if(getServletResponse() != null) {
			getServletResponse().setHeader("Cache-Control","no-cache"); //HTTP 1.1
			getServletResponse().setHeader("Pragma","no-cache"); //HTTP 1.0
			getServletResponse().setDateHeader ("Expires", 0); //prevents caching at the proxy server		
		}
	}
}
