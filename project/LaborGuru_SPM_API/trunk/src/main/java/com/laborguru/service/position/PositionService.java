package com.laborguru.service.position;

import java.util.List;

import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.service.Service;

/**
 * Position Service
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface PositionService extends Service {

	/**
	 * Retrieves a list of positions filtered by store
	 * @param store a Store with id populated
	 * @return List of positions
	 */
	List<Position> getPositionsByStore(Store store);
	
	/**
	 * Saves or updates a position
	 * @param position The position to save
	 * @return The updated position 
	 */
	Position save(Position position);

	
	/**
	 * Deletes a position 
	 * @param position
	 */
	void delete(Position position);
	
	/**
	 * Retrieves a position by position id
	 * @param position Position with id populated
	 * @return the Position
	 */
	Position getPositionById(Position position);
	
	/**
	 * Retrieves a position by position store and name
	 * @param position Position with store and name populated
	 * @return the Position
	 */
	Position getStorePositionByName(Position position);
	
}
