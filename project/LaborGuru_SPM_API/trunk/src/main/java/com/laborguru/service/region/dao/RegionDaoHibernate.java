package com.laborguru.service.region.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Region;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class RegionDaoHibernate extends HibernateDaoSupport implements RegionDao {

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.region.dao.RegionDao#findAll()
	 */
	public List<Region> findAll() {
		return getHibernateTemplate().loadAll(Region.class);
	}

	/**
	 * 
	 * @param region
	 * @return
	 * @see com.laborguru.service.region.dao.RegionDao#getRegionById(com.laborguru.model.Region)
	 */
	public Region getRegionById(Region region) {
		return (Region)getHibernateTemplate().get(Region.class, region.getId());
	}

	/**
	 * 
	 * @param region
	 * @return
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.region.dao.RegionDao#save(com.laborguru.model.Region)
	 */
	public Region save(Region region) throws SpmCheckedException {
		if (region == null){
			throw new IllegalArgumentException("the region passed in as parameter is null");
		}
		
		getHibernateTemplate().saveOrUpdate(region);
		
		return region;
	}

}
