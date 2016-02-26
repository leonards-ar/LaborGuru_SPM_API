/*package com.laborguru.service.employee.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.laborguru.model.Employee;
import com.laborguru.model.Office;
import com.laborguru.model.Position;
import com.laborguru.model.Profile;
import com.laborguru.model.Store;

public class EmployeeDaoTest extends TestCase {
/*
	ApplicationContext ctx = null;
	Employee employee = null;
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		employee = getMockEmployee();
	}
	
	
	public EmployeeDaoTest(String name) {
		super(name);
	}
	
	public void ntestSaveEmployee() {
		
		EmployeeDao employeeDao = (EmployeeDao)ctx.getBean("employeeDao");
		
		Employee resultEmployee = employeeDao.save(employee);
		
		employee.setId(resultEmployee.getId());
		assertEquals(resultEmployee, employee);
		
	}
	
	public void testGetEmployeeByStore() {
		EmployeeDao employeeDao = (EmployeeDao)ctx.getBean("employeeDao");
		
		List<Employee> resultList = employeeDao.getEmployeesByStore(getMockStore());
		
		assertTrue(resultList.size() == 1);
		
		for(Employee emp: resultList) {
			assertEquals(employee.getUserName(), emp.getUserName());
		}
	}
	
	public void testGetEmployeeById() {
		try{
		EmployeeDao employeeDao = (EmployeeDao) ctx.getBean("employeeDao");
		Employee empById = new Employee();
		empById.setId(2);
		Employee resultEmployee = employeeDao.getEmployeeById(empById);
		assertEquals(employee.getUserName(), resultEmployee.getUserName());
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testDeleteEmployee() {
		EmployeeDao employeeDao = (EmployeeDao) ctx.getBean("employeeDao");
		employeeDao.delete(employee);
	}
	
	private Employee getMockEmployee() {
		Employee mockEmployee = new Employee();
		mockEmployee.setUserName("user1");
		mockEmployee.setPassword("12345");
		mockEmployee.setStatus(new Integer(1));
		mockEmployee.setName("Name1");
		mockEmployee.setSurname("Surname1");
		mockEmployee.setCreationDate(new Date());
		//insert user's profile
		Profile profile = new Profile();
		profile.setId(1);
		profile.setName("ROLE_USER");
		profile.setDescription("Profile Desc");
		mockEmployee.addProfile(profile);
		
		//insert user's store
		mockEmployee.setStore(getMockStore());
		
		//insert user's default position
		Position position = new Position();
		position.setId(1);
		position.setName("Position 1");
		position.setStore(getMockStore());
		mockEmployee.setDefaultPosition(position);
		
		mockEmployee.setManager(false);
		mockEmployee.setMobilePhone("759-234-1904");
		mockEmployee.setHomePhone("719-456-3456");
		try{
		mockEmployee.setHireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2004-03-01"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("It shouldn't through an excpetion");
		}
		mockEmployee.setMaxHoursWeek(40);
		mockEmployee.setMaxHoursDay(6);
		mockEmployee.setMaxDaysWeek(6);
		mockEmployee.setAddress("24th Street 1390");
		mockEmployee.setAddress2("PO BOX 345");
		mockEmployee.setCity("Miami");
		mockEmployee.setState("Florida");
		mockEmployee.setZip("FL");
		mockEmployee.setComments("No comments at all");
		
		return mockEmployee;

	}
	
	private Store getMockStore() {
		Store store = new Store();
		store.setId(1);
		store.setName("Store Potatoe");
		store.setOffice(getMockOffice());
		return store;
	}
	
	private Office getMockOffice() {
		Office office = new Office();
		office.setId(1);
		office.setName("Office");
		office.setType('A');
		return office;
	}
	

}
*/