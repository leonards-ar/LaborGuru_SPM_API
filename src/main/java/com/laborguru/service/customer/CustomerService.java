package com.laborguru.service.customer;

import java.util.List;

import com.laborguru.model.Customer;
import com.laborguru.service.Service;
import com.laborguru.service.customer.dao.CustomerDao;


/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface CustomerService extends Service {

	/**
	 * Get all Customers
	 * @return List with all the customers.
	 */
	List<Customer> findAll();
	
	/**
	 * Retrieve a customer by its ID
	 * @param customer
	 * @return The complete customer object
	 */
	Customer getCustomerById(Customer customer);

	
	/**
	 * Retrieves a customer by code
	 * @param customer
	 * @return a Customer or null
	 */
	Customer getCustomerByCode(Customer customer);

	
	/**
	 * Retrieve a list of all customers
	 * @param customer
	 * @return The complete customer object
	 */
	List<Customer> getAllCustomers();

	/**
	 * Retrieves a list of customers filtered by the customerSearch
	 * @param customerSearch The filter for the customer
	 * @return The customer list
	 */
	List<Customer> filterCustomers(Customer customerSearch);

	/**
	 * Saves or updates a customer object
	 * @param customer The customer
	 */
	void save(Customer customer);

	/**
	 * Deletes a customer
	 * @param customer The customer
	 */
	void delete(Customer customer);

	/**
	 * Retrieves the number of customers in the system
	 * @return
	 */
	Integer getNumberOfCustomers();
	
	void setCustomerDao(CustomerDao customerDao);

}
