package com.laborguru.service.positionGroup;

import java.util.List;

import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.service.positionGroup.dao.PositionGroupDao;

public class PositionGroupServiceBean implements PositionGroupService {

	private PositionGroupDao positionGroupDao;
	
	public void delete(PositionGroup positionGroup) {
		//not implemented.
	}

	public PositionGroup getPositionGroupById(PositionGroup positionGroup) {
		return positionGroupDao.getPositionGroupById(positionGroup);
	}

	public List<PositionGroup> getPositionGroupsByStore(Store store) {
		return positionGroupDao.getPositionGroupsByStore(store);
	}

	public PositionGroup save(PositionGroup positionGroup) {
		//not implemented.
		return null;
	}

	/**
	 * @return the positionGroupDao
	 */
	public PositionGroupDao getPositionGroupDao() {
		return positionGroupDao;
	}

	/**
	 * @param positionGroupDao the positionGroupDao to set
	 */
	public void setPositionGroupDao(PositionGroupDao positionGroupDao) {
		this.positionGroupDao = positionGroupDao;
	}

}
