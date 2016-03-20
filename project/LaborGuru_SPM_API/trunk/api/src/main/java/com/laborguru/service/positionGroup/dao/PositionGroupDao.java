package com.laborguru.service.positionGroup.dao;

import java.util.List;

import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;

public interface PositionGroupDao {

	/**
	 * Retrieves a list of position groups filtered by store
	 * @param store a Store with id populated
	 * @return List of position groups
	 */
	List<PositionGroup> getPositionGroupsByStore(Store store);
	
	/**
	 * 
	 * @param positionGroup
	 * @return
	 */
	PositionGroup getPositionGroupById(PositionGroup positionGroup);
}
