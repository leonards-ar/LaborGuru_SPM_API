package com.laborguru.model.service;

import com.laborguru.model.Customer;
import com.laborguru.model.HistoricSales;

/**
 * This class is a wrapper for HistoricSales and Customer object
 * It is used by the HistoricSales Assembler to return a parsed lined SPM objects
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SalesCSVHistoricSales {

	private HistoricSales historicSales;
	private Customer customer;
	
	/**
	 * @return the historicSales
	 */
	public HistoricSales getHistoricSales() {
		return historicSales;
	}
	/**
	 * @param historicSales the historicSales to set
	 */
	public void setHistoricSales(HistoricSales historicSales) {
		this.historicSales = historicSales;
	}
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
}
