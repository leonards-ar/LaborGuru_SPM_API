package com.laborguru.service.customer.dao;

import java.util.List;

import com.laborguru.model.Customer;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface CustomerDao {
	List<Customer> findAll();
	
	Customer getCustomerById(Customer customer);

	/**
	 * Retrieves a list of customers based on the filter passed in as parameter.
	 * If there is no customers that match the criteria return an empty list.
	 * 
	 * @param customerSearch The filter
	 * @return The customer list
	 */
	List<Customer> applyFilter(Customer customerSearch);

	/**
	 * Saves or updates a customer
	 * @param customer The customer
	 */
	void save(Customer customer);
	
	/**
	 * Deletes a customer
	 * @param customer The customer
	 */
	public void delete(Customer customer);

	/**
	 * Retrieves a customer by customer code
	 * @param customer
	 * @return Customer
	 */
	Customer getCustomerByCode(Customer customer);

	/**
	 * Retrieves the number of customers in the system
	 * @return
	 */
	Integer getNumberOfCustomers();
}
