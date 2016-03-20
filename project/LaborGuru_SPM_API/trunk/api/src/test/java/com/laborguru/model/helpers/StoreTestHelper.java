package com.laborguru.model.helpers;

import com.laborguru.model.Store;

/**
 * Store Test Helper
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreTestHelper {

	public static Store getStore(String param){
		return getStore(param, 1);
	}

	public static Store getStore(String param, int i) {
		Store store = new Store();
		
		store.setId(i);		
		store.setCode("code:"+param);
		store.setName("name:"+param);
		store.setArea(AreaTestHelper.getArea(param,i));
		
		return store;
	}
}
