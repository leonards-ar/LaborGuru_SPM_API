package com.laborguru.service.positionGroup.dao;

import java.util.List;

import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionGroupDaoHibernate extends SpmHibernateDao implements PositionGroupDao {

	public List<PositionGroup> getPositionGroupsByStore(Store store){
		return (List<PositionGroup>)getHibernateTemplate().findByNamedParam(
				"from PositionGroup positionGroup where positionGroup.store.id = :storeId", "storeId", store.getId());
		
	}

	public PositionGroup getPositionGroupById(PositionGroup positionGroup) {
		return (PositionGroup)getHibernateTemplate().load(PositionGroup.class, positionGroup.getId());
	}
}
