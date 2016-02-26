package com.laborguru.service.employee;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Customer;
import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.model.User;
import com.laborguru.model.UserStatus;
import com.laborguru.model.filter.SearchEmployeeFilter;
import com.laborguru.service.employee.dao.EmployeeDao;
import com.laborguru.service.store.dao.StoreDao;
import com.laborguru.service.user.dao.UserDao;

public class EmployeeServiceBean implements EmployeeService {

	private static final Logger log = Logger.getLogger(EmployeeServiceBean.class);
	
	private EmployeeDao employeeDao;
	private StoreDao storeDao;
	private UserDao userDao;

	/**
	 * 
	 * @param employee
	 * @see com.laborguru.service.employee.EmployeeService#delete(com.laborguru.model.Employee)
	 */
	public void delete(Employee employee) {
		if(employee == null) {
			log.error("Employee passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		employeeDao.delete(employee);
		
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @see com.laborguru.service.employee.EmployeeService#getEmployeeById(com.laborguru.model.Employee)
	 */
	public Employee getEmployeeById(Employee employee) {
		if(employee == null) {
			log.error("Employee passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		return employeeDao.getEmployeeById(employee);
	}

	/**
	 * 
	 * @param store
	 * @return
	 * @see com.laborguru.service.employee.EmployeeService#getEmployeesByStore(com.laborguru.model.Store)
	 */
	public List<Employee> getEmployeesByStore(Store store) {

		if(store == null) {
			log.error("Store passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		List<Employee> employees = employeeDao.getEmployeesByStore(store);

		return employees;
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.employee.EmployeeService#save(com.laborguru.model.Employee)
	 */
	public Employee save(Employee employee) throws SpmCheckedException {

		Employee retEmployee = null;
		
		if(employee == null) 
		{
			log.error("Employee passed in as parameter is null");
			throw new IllegalArgumentException("Employee passed in as parameter is null");				
		}
		
		log.debug("before save employee:"+ employee);

		retEmployee = employee.getId()!= null? update(employee):create(employee);

		log.debug("after save employee:"+ retEmployee);
		
		return retEmployee;
	}
	
	/**
	 * Creates employee
	 */
	private Employee create(Employee employee) throws SpmCheckedException {		
							
		//Checking if user name already exist
		if (userDao.existUser(employee.getUserName()))
		{
			String exMgs = "username: "+ employee.getUserName()+" already exist in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[]{employee.getUserName()});
		}
		
		createIfTransientStore(employee);

		employee.setCreationDate(new Date());
		employee.setLastUpdateDate(new Date());

		return  employeeDao.save(employee);
	}

	/**
	 * Updates employee
	 */
	private Employee update(Employee employee) throws SpmCheckedException {
							
		//Checking if user name already exist
		User auxUser = userDao.getUserByUsername(employee);
		
		if ((auxUser != null) && !auxUser.getId().equals(employee.getId()))
		{
			String exMgs = "username: "+ employee.getUserName()+" already exist in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[]{employee.getUserName()});
			
		}
		
		//Evicting the user
		userDao.evict(auxUser);

		createIfTransientStore(employee);

		employee.setLastUpdateDate(new Date());

		return employeeDao.save(employee);
	}

	/**
	 * 
	 * @param employee
	 * @return
	 */
	private void createIfTransientStore(Employee employee) throws SpmCheckedException {
		Store store = employee.getStore();
		if(store != null && (store.getId() == null || store.getId().intValue() <= 0)) {
			log.info("Employee [" + employee + "] has transient store. Store will be created");
			employee.setStore(getStoreDao().save(store));
		}
	}
	
	/**
	 * @return the employeeDao
	 */
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	/**
	 * @param employeeDao the employeeDao to set
	 */
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Retrieves a list of employees filtered by the SearchEmployeeFilter
	 * @param searchEmployee The filter for the employee search
	 * @return The employee list
	 * @see com.laborguru.service.employee.EmployeeService#filterEmployee(com.laborguru.model.filter.SearchEmployeeFilter)
	 */
	public List<Employee> filterEmployee(SearchEmployeeFilter searchEmployee) {
		
		if(searchEmployee == null) {
			log.error("The filter passed as parameter is null");
			throw new IllegalArgumentException("The filter passed as parameter is null");
		}
		
		
		List<Employee> employees = employeeDao.applyFilter(searchEmployee);

		return employees;	
	}
	
	public List<Employee> getEmployeeByCustomer(Customer customer){
		return null;
	}

	/**
	 * 
	 * @param employee
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.employee.EmployeeService#logicalDelete(com.laborguru.model.Employee)
	 */
	public void logicalDelete(Employee employee) throws SpmCheckedException {
		if(employee == null) {
			log.error("Employee passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		if(log.isDebugEnabled()) {
			log.debug("About to logically delete employee " + employee);
		}
		employee.setUserStatus(UserStatus.DELETED);
		employee.setUserName(employee.getUserName() + "_" + employee.getId());
		save(employee);
	}

	/**
	 * 
	 * @return
	 */
	public StoreDao getStoreDao() {
		return storeDao;
	}

	/**
	 * 
	 * @param storeDao
	 */
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @see com.laborguru.service.employee.EmployeeService#getEmployeeByUsername(com.laborguru.model.Employee)
	 */
	public Employee getEmployeeByUsername(Employee employee) {
		if(employee == null) {
			log.error("Employee passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		return employeeDao.getEmployeeByUsername(employee);
	}

	/**
	 * 
	 * @param employee
	 * @return
	 * @see com.laborguru.service.employee.EmployeeService#getStoreEmployeeByCompleteName(com.laborguru.model.Employee)
	 */
	public Employee getStoreEmployeeByCompleteName(Employee employee) {
		if(employee == null) {
			log.error("Employee passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		return employeeDao.getStoreEmployeeByCompleteName(employee);
	}
}
