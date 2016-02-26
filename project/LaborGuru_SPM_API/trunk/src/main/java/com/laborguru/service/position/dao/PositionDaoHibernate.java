package com.laborguru.service.position.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.service.employee.dao.EmployeeDaoHibernate;

/**
 * Hibernate Implementation for position Dao
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class PositionDaoHibernate extends HibernateDaoSupport implements PositionDao {
	private static final Logger log = Logger.getLogger(PositionDaoHibernate.class);	


	/**
	 * Retrieves a list of positions filtered by store
	 * @param store a store with id populated
	 * @return List of positions
	 * @see com.laborguru.service.position.dao.PositionDao#getPositionsByStore(com.laborguru.model.Store)
	 */
	public List<Position> getPositionsByStore(Store store) {
		return (List<Position>)getHibernateTemplate().findByNamedParam(
				"from Position position where position.store.id = :storeId", "storeId",store.getId());
	}

	/**
	 * @param position
	 * @return
	 * @see com.laborguru.service.position.dao.PositionDao#getPositionById(com.laborguru.model.Position)
	 */
	public Position getPositionById(Position position) {
		return (Position)getHibernateTemplate().get(Position.class, position.getId());
	}

	/**
	 * 
	 * @param position
	 * @return
	 * @see com.laborguru.service.position.dao.PositionDao#getStorePositionByName(com.laborguru.model.Position)
	 */
	public Position getStorePositionByName(Position position) {
		List<Position> result = (List<Position>)getHibernateTemplate().findByNamedParam(
				"from Position position where position.store.id = :storeId and position.name = :name", new String[] {"storeId", "name"}, new Object[] {position.getStore().getId(), position.getName()});
		
		Position retPosition = null;
		
		if(log.isDebugEnabled()) {
			log.debug("Found " + result.size() + " results matching the query for store id: [" + position.getStore().getId() + "] and name: [" + position.getName() + "]");
		}
		
		if(result.size() > 0) {
			retPosition = result.get(0);
			if(result.size() > 1) {
				log.warn("More than one employee matches the query for store id: [" + position.getStore().getId() + "] and name: [" + position.getName() + "]. Returning the first one with id: [" + retPosition.getId() + "]");
			}
		}
		return retPosition;
	}

}
