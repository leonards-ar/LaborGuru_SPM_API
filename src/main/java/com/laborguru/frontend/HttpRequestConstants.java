/*
 * File name: HttpRequestConstants.java
 * Creation date: 19/06/2008 20:59:15
 * Copyright Mindpool
 */
package com.laborguru.frontend;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface HttpRequestConstants {

	/**
	 * The session key holding the logged user
	 */
	String USER = "spmUser";

	/**
	 * The session key holding the logged user's menu
	 */
	String MENU = "spmMenu";
	
	/**
	 * The store of the logged user when he/she is an employee.
	 * If the user is an administrator, then the session will
	 * hold the store that is currently been edited.
	 */
	String STORE = "spmStore";
	
	/**
	 * The store id that is being edited.
	 */
	String STORE_TO_EDIT_ID = "spmStoreToEditId";

	/**
	 * The customer id that is being edited.
	 */
	String CUSTOMER_TO_EDIT_ID = "spmCustomerToEditId";
	
	/**
	 * The request key holding the newly selected item index
	 */
	String MENU_ITEM_INDEX = "menuItemIndex";
	
	/**
	 * The request key holding the newly selected sub item index
	 */
	String SUB_MENU_ITEM_INDEX = "subMenuItemIndex";
	
	/**
	 * The customer of the logged user when he/she is an employee.
	 */	
	String CUSTOMER = "spmCustomer";
	
	/**
	 * The region of the logged user when he/she is an employee.
	 */
	String REGION = "spmRegion";
	
	/**
	 * The area of the logged user when he/she is an employee.
	 */
	String AREA = "spmArea";	
}
