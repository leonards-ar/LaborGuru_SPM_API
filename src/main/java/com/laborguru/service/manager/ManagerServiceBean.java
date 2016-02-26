package com.laborguru.service.manager;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.Manager;
import com.laborguru.model.Region;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchManagerFilter;
import com.laborguru.service.manager.dao.ManagerDao;
import com.laborguru.service.user.dao.UserDao;

/**
 * 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
public class ManagerServiceBean implements ManagerService {
	private static final Logger log = Logger
			.getLogger(ManagerServiceBean.class);

	private ManagerDao managerDao;
	private UserDao userDao;

	/**
	 * @param manager
	 * @see com.laborguru.service.manager.ManagerService#delete(com.laborguru.model.Manager)
	 */
	public void delete(Manager manager) {
		managerDao.delete(manager);
	}

	/**
	 * @param manager
	 * @return
	 * @see com.laborguru.service.manager.ManagerService#getManagerById(com.laborguru.model.Manager)
	 */
	public Manager getManagerById(Manager manager) {
		return managerDao.getManagerById(manager);
	}

	/**
	 * @param customer
	 * @return
	 * @see com.laborguru.service.manager.ManagerService#getCustomerUsersByCustomer(com.laborguru.model.Customer)
	 */
	public List<Manager> getCustomerUsersByCustomer(Customer customer) {
		return managerDao.getCustomerUsers(customer);
	}

	/**
	 * @param region
	 * @return
	 * @see com.laborguru.service.manager.ManagerService#getRegionalUserByRegion(com.laborguru.model.Region)
	 */
	public List<Manager> getRegionalUsersByRegion(Region region) {
		return managerDao.getRegionalUsers(region);
	}

	/**
	 * @param area
	 * @return
	 */
	public List<Manager> getAreaUsersByArea(Area area) {
		return managerDao.getAreaUsers(area);
	}

	/**
	 * Searches a manager by name and surname
	 * 
	 * @param searchUserFilter
	 * @return
	 */
	public List<Manager> filterUser(SearchManagerFilter searchManagerFilter) {

		if (searchManagerFilter == null) {
			log.error("The filter passed as parameter is null");
			throw new IllegalArgumentException(
					"The filter passed as parameter is null");
		}

		return managerDao.applyFilters(searchManagerFilter);
	}

	/**
	 * @param manager
	 * @return
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.manager.ManagerService#save(com.laborguru.model.Manager)
	 */
	public Manager save(Manager manager) throws SpmCheckedException {
		Manager retUser = null;

		if (manager == null) {
			log.error("Manager passed in as parameter is null");
			throw new IllegalArgumentException(
					"Manager passed in as parameter is null");
		}

		if (log.isDebugEnabled()) {
			log.debug("before save Manager:" + manager);
		}

		retUser = manager.getId() != null ? update(manager) : create(manager);

		if (log.isDebugEnabled()) {
			log.debug("after save manager:" + manager);
		}

		return retUser;
	}

	/**
	 * @param manager
	 * @return
	 * @throws SpmCheckedException
	 */
	private Manager create(Manager manager) throws SpmCheckedException {
		
		// Checking if user name already exist
		if (userDao.existUser(manager.getUserName())) {
			String exMgs = "username: " + manager.getUserName()	+ " already exist in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[] { manager.getUserName() });
		}

		manager.setCreationDate(new Date());
		return managerDao.save(manager);

	}

	/**
	 * @param manager
	 * @return
	 * @throws SpmCheckedException
	 */
	private Manager update(Manager manager) throws SpmCheckedException {
		// Checking if user name already exist
		User auxUser = userDao.getUserByUsername((User) manager);

		if ((auxUser != null) && !auxUser.getId().equals(auxUser.getId())) {
			String exMgs = "username: " + manager.getUserName() + " already exist in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[] { manager.getUserName() });

		}

		// Evicting the user
		userDao.evict(auxUser);

		manager.setLastUpdateDate(new Date());
		
		return managerDao.save(manager);
	}

	/**
	 * @return the managerDao
	 */
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	/**
	 * @param managerDao
	 *            the managerDao to set
	 */
	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
