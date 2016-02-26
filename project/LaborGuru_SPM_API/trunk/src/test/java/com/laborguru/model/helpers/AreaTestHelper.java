package com.laborguru.model.helpers;

import com.laborguru.model.Area;

/**
 * AreaTest Helper
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AreaTestHelper {

	public static Area getArea(String param, int i) {
		Area area = new Area();
		
		area.setName("name:"+param);
		area.setId(i);
		area.setRegion(RegionTestHelper.getRegion(param, i));
		
		return area;
	}

}
