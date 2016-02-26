package com.laborguru.model.helpers;

import com.laborguru.model.Region;

/**
 * Region Test helper
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class RegionTestHelper {

	public static Region getRegion(String param, int i) {
		Region region = new Region();
		
		region.setId(i);
		region.setName("name:"+param);
		region.setCustomer(CustomerTestHelper.getCustomer(param, i));
		
		return region;
	}

}
