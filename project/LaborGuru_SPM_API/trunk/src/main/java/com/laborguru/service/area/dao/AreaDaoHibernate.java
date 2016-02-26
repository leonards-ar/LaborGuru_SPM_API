package com.laborguru.service.area.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.Area;

/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AreaDaoHibernate extends HibernateDaoSupport implements AreaDao {

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.area.dao.AreaDao#findAll()
	 */
	public List<Area> findAll(){
		return (List<Area>) getHibernateTemplate().loadAll(Area.class);
	}
	
	/**
	 * 
	 * @param area
	 * @return
	 * @see com.laborguru.service.area.dao.AreaDao#getAreaById(com.laborguru.model.Area)
	 */
	public Area getAreaById(Area area) {
		return (Area)getHibernateTemplate().get(Area.class, area.getId());
	}
}
