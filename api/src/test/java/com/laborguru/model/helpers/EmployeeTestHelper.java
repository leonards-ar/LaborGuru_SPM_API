package com.laborguru.model.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.model.Employee;

/**
 * Test helper for Employee
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class EmployeeTestHelper {
	
	public static Employee getEmployee(String param){
		return getEmployee(param, 0);
	}

	public static Employee getEmployee(String param, int index) {
		Employee employee = new Employee();
		
		employee.setId(index);
		employee.setName("name_"+param+"_"+index);
		employee.setSurname("surname_"+param+"_"+index);
		employee.setUserName("username_"+param+"_"+index);
		employee.setEmail("email_"+param+"_"+index);
		employee.setCity("city_"+param+"_"+index);
		employee.setAddress("address_"+param+"_"+index);
		employee.setAddress2("address2_"+param+"_"+index);
		employee.setComments("comments_"+param+"_"+index);
		employee.setDefaultPosition(PositionTestHelper.getPosition("position_"+param));
		employee.setPhone("phone_"+param+"_"+index);
		employee.setPhone2("phone2_"+param+"_"+index);
		employee.setZip("zip_"+param+"_"+index);
		
		employee.setMaxHoursDay(8);
		employee.setMaxHoursWeek(40);
		employee.setStatus(0);
		
		employee.setHireDate(new Date());
		
		return employee;
	}
	
	public static List<Employee> getEmployees(String param, int size){
		List<Employee> employees = new ArrayList<Employee>(size);
		
		for (int i=0; i < size; i++){
			employees.add(getEmployee(param, i));
		}
		return employees;
	}	
}
