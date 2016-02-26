/*
 * File name: AdministratorHomeAction.java
 * Creation date: 21/02/2009 15:28:13
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import java.util.ArrayList;
import java.util.List;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Store;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.store.StoreService;
import com.laborguru.service.user.UserService;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AdministratorHomeAction extends SpmAction implements Preparable {

	private static final long serialVersionUID = 1L;
	private List<Store> latestStores = new ArrayList<Store>(5);
	private List<Store> oldestStores = new ArrayList<Store>(5);
	private Integer numberOfCustomers = 0;
	private Integer numberOfStores = 0;
	private Integer totalUsers = 0;
	private Integer enabledUsers = 0;

	
	private StoreService storeService;
	private CustomerService customerService;
	private UserService userService;

	/**
	 * 
	 */
	public AdministratorHomeAction() {
	}

	/**
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {

		latestStores = storeService.findLatestStores(5);
		oldestStores = storeService.findOldestStores(5);
		
		numberOfCustomers = customerService.getNumberOfCustomers();
		numberOfStores = storeService.getNumberOfStores();
		totalUsers = userService.getNumberOfUsers();
		enabledUsers = userService.getNumberOfEnabledUsers();

		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the latestStores
	 */
	public List<Store> getLatestStores() {
		return latestStores;
	}

	/**
	 * @param latestStores the latestStores to set
	 */
	public void setLatestStores(List<Store> latestStores) {
		this.latestStores = latestStores;
	}

	/**
	 * @return the oldestStores
	 */
	public List<Store> getOldestStores() {
		return oldestStores;
	}

	/**
	 * @param oldestStores the oldestStores to set
	 */
	public void setOldestStores(List<Store> oldestStores) {
		this.oldestStores = oldestStores;
	}

	/**
	 * @return the numberOfCustomers
	 */
	public Integer getNumberOfCustomers() {
		return numberOfCustomers;
	}

	/**
	 * @param numberOfCustomers the numberOfCustomers to set
	 */
	public void setNumberOfCustomers(Integer numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}

	/**
	 * @return the numberOfStores
	 */
	public Integer getNumberOfStores() {
		return numberOfStores;
	}

	/**
	 * @param numberOfStores the numberOfStores to set
	 */
	public void setNumberOfStores(Integer numberOfStores) {
		this.numberOfStores = numberOfStores;
	}


	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService the storeService to set
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * @return the customerService
	 */
	public CustomerService getCustomerService() {
		return customerService;
	}

	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the totalUsers
	 */
	public Integer getTotalUsers() {
		return totalUsers;
	}

	/**
	 * @param totalUsers the totalUsers to set
	 */
	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	/**
	 * @return the enabledUsers
	 */
	public Integer getEnabledUsers() {
		return enabledUsers;
	}

	/**
	 * @param enabledUsers the enabledUsers to set
	 */
	public void setEnabledUsers(Integer enabledUsers) {
		this.enabledUsers = enabledUsers;
	}
}
