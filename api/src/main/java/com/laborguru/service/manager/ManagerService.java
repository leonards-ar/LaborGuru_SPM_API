package com.laborguru.service.manager;

import java.util.List;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.Manager;
import com.laborguru.model.Region;
import com.laborguru.model.filter.SearchManagerFilter;

/**
 * It's intended to handle special kind of users.
 * This users can be:
 * - Customer User: User with a client associated.
 * - Regional User: User with a region associated.
 * - Area User: User with an area associated.
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ManagerService {

	/**
	 * deletes a Manager
	 * @param manager
	 */
	void delete(Manager manager);
	
	/**
	 * Saves a manager
	 * @param manager
	 * @return
	 * @throws SpmCheckedException
	 */
	Manager save(Manager manager) throws SpmCheckedException;
	
	/**
	 * Retrieves a manager by its id
	 * @param manager
	 * @return
	 */
	Manager getManagerById(Manager manager);
	
	
	/**
	 * Retrieves a list of CustomerUsers associated to a client.
	 * @param customer
	 * @return
	 */
	List<Manager> getCustomerUsersByCustomer(Customer customer);
	
	/**
	 * Retrieves a list of RegionalUsers associated to a region.
	 * @param region
	 * @return
	 */
	List<Manager> getRegionalUsersByRegion(Region region);
	
	/**
	 * Retrieves a list of AreaUsers associated to an area.
	 * @param area
	 * @return
	 */
	List<Manager> getAreaUsersByArea(Area area);
	
	
	/**
	 * Retrieves a List of Managers applying a searchManagerFilter
	 * @param searchManagerFilter
	 * @return
	 */
	List<Manager> filterUser(SearchManagerFilter searchManagerFilter);
}
