package com.laborguru.service.position;

import java.util.List;

import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.service.position.dao.PositionDao;

/**
 * Spring implementation for Position Service
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionServiceBean implements PositionService {

	private PositionDao positionDao;
	
	/**
	 * 
	 * @return
	 */
	public PositionDao getPositionDao() {
		return positionDao;
	}

	/**
	 * 
	 * @param positionDao
	 */
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	/**
	 * Retrieves a list of positions filtered by store
	 * @param store a Store with id populated
	 * @return List of positions
	 * 
	 * @see com.laborguru.service.position.PositionService#getPositionsByStore(com.laborguru.model.Store)
	 */
	public List<Position> getPositionsByStore(Store store) {
		
		if (store == null)
			throw new IllegalArgumentException("Store passed in as parameter is null");

		if (store.getId() == null)
			throw new IllegalArgumentException("Store id  passed in as parameter is null");
		
		return positionDao.getPositionsByStore(store);
	}

	/**
	 * 
	 * @param position
	 * @see com.laborguru.service.position.PositionService#delete(com.laborguru.model.Position)
	 */
	public void delete(Position position) {
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @see com.laborguru.service.position.PositionService#getPositionById(com.laborguru.model.Position)
	 */
	public Position getPositionById(Position position) {
		if (position == null)
			throw new IllegalArgumentException("Position passed in as parameter is null");

		if (position.getId() == null)
			throw new IllegalArgumentException("Position id  passed in as parameter is null");
		
		return getPositionDao().getPositionById(position);
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @see com.laborguru.service.position.PositionService#save(com.laborguru.model.Position)
	 */
	public Position save(Position position) {
		return null;
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @see com.laborguru.service.position.PositionService#getStorePositionByName(com.laborguru.model.Position)
	 */
	public Position getStorePositionByName(Position position) {
		if (position == null)
			throw new IllegalArgumentException("Position passed in as parameter is null");

		if (position.getStore() == null)
			throw new IllegalArgumentException("Position store passed in as parameter is null");

		if (position.getStore() == null)
			throw new IllegalArgumentException("Position name passed in as parameter is null");
		
		return getPositionDao().getStorePositionByName(position);
		
	}

}
