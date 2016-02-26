package com.laborguru.service.area.dao;

import java.util.List;

import com.laborguru.model.Area;

public interface AreaDao {

	List<Area> findAll();
	
	Area getAreaById(Area area);
}
