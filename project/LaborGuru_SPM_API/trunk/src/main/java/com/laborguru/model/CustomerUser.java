package com.laborguru.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class CustomerUser extends User implements Manager{
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	public CustomerUser(){
		
	}
	
	public CustomerUser(User user) {
		super(user);
	}
	
	private Customer customer;
	
	
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean equals(Object other) {
		return super.equals(other) && customer.equals(((CustomerUser)other).getCustomer());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE).appendSuper(super.toString())
		.append("customer", customer).toString();
	}

}
