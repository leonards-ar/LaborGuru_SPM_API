package com.laborguru.service.employee.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchEmployeeFilter;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;

public class EmployeeDaoHibernate extends SpmHibernateDao implements EmployeeDao {

	private static final Logger log = Logger.getLogger(EmployeeDaoHibernate.class);	

	/**
	 * 
	 */
	public void delete(Employee employee) {
		getHibernateTemplate().delete(employee);
	}

	/**
	 * 
	 */
	public Employee getEmployeeById(Employee employee) {
		return (Employee)getHibernateTemplate().get(Employee.class, employee.getId());		
	}
	
	/**
	 * 
	 */
	public List<Employee> getEmployeesByStore(Store store) {
		return (List<Employee>)getHibernateTemplate().findByNamedParam("from Employee employee where employee.store.id = :searchString and employee.status != 2", "searchString", store.getId());
	}

	/**
	 * 
	 */
	public Employee save(Employee employee) {
		getHibernateTemplate().saveOrUpdate(employee);
		return employee;
	}

	/**
	 * Retrieves a list of employees based on the filter passed in as parameter.
	 * If there is no employees that match the criteria return an empty list.
	 * 
	 * TODO: CNR - 25/06/2008 At the moment doesn't work if there is no store. So we should refactor this method and move it up to a BaseDao class. In order to run the filter on a BaseDao.
	 * I'll work on this on the following week.
	 *       MC - 05/07/2008 Created supper class for every Dao
	 * 
	 * @param searchEmployee The filter
	 * @return The employee list
	 * @see com.laborguru.service.employee.dao.EmployeeDao#applyFilter(com.laborguru.model.filter.SearchEmployeeFilter)
	 */
	public List<Employee> applyFilter(SearchEmployeeFilter searchEmployee) {
		
		//TODO: Remove this when fix the general case
		if (searchEmployee.getStore() == null){
			throw new IllegalArgumentException("The store in the filter is empty.");
		}
		
		log.debug("In applyFilter with search employee filter:"+ searchEmployee);
		
		StringBuilder sb = new StringBuilder("from Employee employee where employee.status != 2");

		if (includeInFilter(searchEmployee.getStore())){
			sb.append(" and employee.store.id = " + searchEmployee.getStore().getId());
		}
		
		if (includeInFilter(searchEmployee.getEmployeeId())){
			sb.append(" and employee.employeeId = "+ searchEmployee.getEmployeeId());
		}
		
		if (includeInFilter(searchEmployee.getFullName())){
			sb.append(" and employee.name like '%"+searchEmployee.getFullName()+"%'");
			sb.append(" or employee.surname like '%"+searchEmployee.getFullName()+"%'");
		}
		
		log.debug("Calling hibernate with query:"+ sb.toString());

		return (List<Employee>)getHibernateTemplate().find(sb.toString());
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @see com.laborguru.service.employee.dao.EmployeeDao#getEmployeeByUsername(com.laborguru.model.Employee)
	 */
	public Employee getEmployeeByUsername(Employee employee) {

		List<Employee> result = (List<Employee>)getHibernateTemplate().findByNamedParam(
				"from Employee employee where employee.userName = :searchString and employee.status != 2", "searchString", employee.getUserName());

		Employee retEmployee = null;
		
		if(log.isDebugEnabled()) {
			log.debug("Found " + result.size() + " results matching the query for username: [" + employee.getUserName() + "]");
		}
		
		if(result.size() > 0) {
			retEmployee = result.get(0);
			if(result.size() > 1) {
				log.warn("More than one employee matches the query for username: [" + employee.getUserName() + "]. Returning the first one with id: [" + retEmployee.getId() + "]");
			}
		}
		
		return retEmployee;
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @see com.laborguru.service.employee.dao.EmployeeDao#getStoreEmployeeByCompleteName(com.laborguru.model.Employee)
	 */
	public Employee getStoreEmployeeByCompleteName(Employee employee) {
		if (employee.getStore() == null){
			throw new IllegalArgumentException("The store in the employee is empty.");
		}
		
		List<Employee> result = (List<Employee>)getHibernateTemplate().findByNamedParam(
				"from Employee employee where employee.store.id = :storeId and employee.name = :name and employee.surname = :surname and employee.status != 2", new String[] {"storeId", "name", "surname"}, new Object[] {employee.getStore().getId(), employee.getName(), employee.getSurname()});

		if(log.isDebugEnabled()) {
			log.debug("Found " + result.size() + " results matching the query for store id: [" + employee.getStore().getId() + "], name: [" + employee.getName() + "] and surname: [" + employee.getSurname() + "]");
		}

		Employee retEmployee = null;
		
		if(result.size() > 0) {
			retEmployee = result.get(0);
			if(result.size() > 1) {
				log.warn("More than one employee matches the query for store id: [" + employee.getStore().getId() + "], name: [" + employee.getName() + "] and surname: [" + employee.getSurname() + "]. Returning the first one with id: [" + retEmployee.getId() + "]");
			}
			
		}
		
		return retEmployee;
	}	
}
