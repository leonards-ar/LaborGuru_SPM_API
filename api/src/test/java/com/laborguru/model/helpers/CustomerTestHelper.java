package com.laborguru.model.helpers;

import com.laborguru.model.Customer;

/**
 * Customer Test Helper
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class CustomerTestHelper {

	public static Customer getCustomer(String param, int i) {
		
		Customer customer = new Customer();
		customer.setId(i);
		customer.setName("name:"+param);
		
		return customer;
	}

}
