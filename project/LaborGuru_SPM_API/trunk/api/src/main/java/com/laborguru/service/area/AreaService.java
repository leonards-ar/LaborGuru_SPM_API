package com.laborguru.service.area;

import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.service.Service;


/**
 * @author fb21734
 *
 */
public interface AreaService extends Service {

	List<Area> findAll();
	
	Area getAreaById(Area area);
}
