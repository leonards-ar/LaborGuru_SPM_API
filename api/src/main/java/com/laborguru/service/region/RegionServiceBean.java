package com.laborguru.service.region;

import java.util.List;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Region;
import com.laborguru.service.region.dao.RegionDao;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class RegionServiceBean implements RegionService {

	private RegionDao regionDao;
	
	public List<Region> findAll() {
		return regionDao.findAll();
	}

	/**
	 * @return the regionDao
	 */
	public RegionDao getRegionDao() {
		return regionDao;
	}

	/**
	 * @param regionDao the regionDao to set
	 */
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	/**
	 * 
	 * @param region
	 * @return
	 * @see com.laborguru.service.region.RegionService#getRegionById(com.laborguru.model.Region)
	 */
	public Region getRegionById(Region region) {
		return getRegionDao().getRegionById(region);
	}

	/**
	 * 
	 * @param region
	 * @return
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.region.RegionService#save(com.laborguru.model.Region)
	 */
	public Region save(Region region) throws SpmCheckedException {
		return getRegionDao().save(region);
	}
	
	

}
