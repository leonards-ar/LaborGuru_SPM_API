package com.laborguru.service.position.dao;

import java.util.List;

import com.laborguru.model.Position;
import com.laborguru.model.Store;

/**
 * Position Dao
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface PositionDao {

	/**
	 * Retrieves a list of positions filtered by store
	 * @param store a Store with id populated
	 * @return List of positions
	 */
	List<Position> getPositionsByStore(Store store);
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	Position getPositionById(Position position);
	
	/**
	 * Retrieves a position by position store and name
	 * @param position Position with store and name populated
	 * @return the Position
	 */
	Position getStorePositionByName(Position position);	
}
