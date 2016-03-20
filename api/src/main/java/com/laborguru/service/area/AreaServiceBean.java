package com.laborguru.service.area;

import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.service.area.dao.AreaDao;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AreaServiceBean implements AreaService {

	private AreaDao areaDao;
	
	public List<Area> findAll() {
		return areaDao.findAll();
	}
	
	public Area getAreaById(Area area) {
		return areaDao.getAreaById(area);
	}

	/**
	 * @return the areaDao
	 */
	public AreaDao getAreaDao() {
		return areaDao;
	}

	/**
	 * @param areaDao the areaDao to set
	 */
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}
	
	

}
