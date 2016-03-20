package com.laborguru.service.positionGroup;

import java.util.List;

import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface PositionGroupService extends Service{

	/**
	 * Retrieves a list of position groups filtered by store
	 * @param store a Store with id populated
	 * @return List of positions
	 */
	List<PositionGroup> getPositionGroupsByStore(Store store);
	
	/**
	 * Saves or updates a position groups
	 * @param position The position to save
	 * @return The updated position 
	 */
	PositionGroup save(PositionGroup positionGroup);

	
	/**
	 * Deletes a position group 
	 * @param position
	 */
	void delete(PositionGroup positionGroup);
	
	/**
	 * Retrieves a position group by position group id
	 * @param positionGroup Position group with id populated
	 * @return the position group
	 */
	PositionGroup getPositionGroupById(PositionGroup positionGroup);
}
