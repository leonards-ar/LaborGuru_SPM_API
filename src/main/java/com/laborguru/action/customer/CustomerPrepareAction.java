package com.laborguru.action.customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.HttpRequestConstants;
import com.laborguru.model.Customer;
import com.laborguru.model.Region;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.region.RegionService;
import com.opensymphony.xwork2.Preparable;

/**
 * This class deals with Customer CRUD
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class CustomerPrepareAction extends SpmAction implements Preparable{

	private static Logger log = Logger.getLogger(CustomerPrepareAction.class);

	private static final long serialVersionUID = 1L;

	private CustomerService customerService;
	private RegionService regionService;

	private List<Customer> customers = new ArrayList<Customer>();
	private Customer customerSearch = new Customer();
	private Customer customer = new Customer();
	
	private List<Region> regions = new ArrayList<Region>();
	private String newRegionName;
	private String customerName;
	private String customerCode;

	
	private Integer index;
	private Integer customerId;
	private boolean removePage;

	
	/**
	 * @return the removePage
	 */
	public boolean isRemovePage() {
		return removePage;
	}

	/**
	 * @param removePage the removePage to set
	 */
	public void setRemovePage(boolean removePage) {
		this.removePage = removePage;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the newRegionName
	 */
	public String getNewRegionName() {
		return newRegionName;
	}

	/**
	 * @param newRegionName the newRegionName to set
	 */
	public void setNewRegionName(String newRegionName) {
		this.newRegionName = newRegionName;
	}

	/**
	 * @return the regions
	 */
	public List<Region> getRegions() {
		return regions;
	}

	/**
	 * @param regions the regions to set
	 */
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
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


	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerSearch
	 */
	public Customer getCustomerSearch() {
		return customerSearch;
	}

	/**
	 * @param customerSearch the customerSearch to set
	 */
	public void setCustomerSearch(Customer customerSearch) {
		this.customerSearch = customerSearch;
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
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * Executes the list action
	 * @return
	 */
	public String list(){
		setCustomers(customerService.getAllCustomers());
		
		return SpmActionResult.LIST.getResult();
	}


	/**
	 * Executes search action
	 * @return
	 */
	public String search(){
		
		setCustomers(customerService.filterCustomers(this.getCustomerSearch()));
		
		return SpmActionResult.LIST.getResult();
	}

	/**
	 * Prepares the edit page
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		
		loadCustomerFromId();
		
		setCustomerName(getCustomer().getName());
		setCustomerCode(getCustomer().getCode());
		setRegions(new ArrayList<Region>(getCustomer().getRegions()));
		
		return SpmActionResult.EDIT.getResult();
	}

	
	/**
	 * Prepares the view page
	 * 
	 * @return
	 */
	public String show() {
		loadCustomerFromId();
		
		setCustomerName(getCustomer().getName());
		setCustomerCode(getCustomer().getCode());
		
		return SpmActionResult.SHOW.getResult();
	}	
	
	
	/**
	 * Prepare removes page
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception {

		loadCustomerFromId();
				
		this.setRemovePage(true);
		
		return SpmActionResult.SHOW.getResult();
	}
	
	/**
	 * Prepares the add page
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		return SpmActionResult.EDIT.getResult();
	}	

	/**
	 * Adds a region to the regions list
	 * @return
	 * @throws Exception
	 */
	public String addRegion() throws Exception {
		addNewRegion();
		setNewRegionName(null);
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * adds new region to Region list
	 */
	private void addNewRegion() {
		Region regionAux = new Region();
		regionAux.setName(getNewRegionName());
		getRegions().add(regionAux);
	}	
	
	/**
	 * Removes a region from the regions list
	 * @return
	 * @throws Exception
	 */
	public String removeRegion() throws Exception {
		if(getIndex() != null && getIndex() >= 0 && getIndex() < getRegions().size()) {
			getRegions().remove(getIndex().intValue());
		}
		
		return SpmActionResult.EDIT.getResult();
	}	

	/**
	 * Deletes a customer
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {		
		
		if(log.isDebugEnabled()) {
			log.debug("About to delete customer: " + getCustomer());
		}
		
		Customer aCustomer = customerService.getCustomerById(this.getCustomer());		
		customerService.delete(aCustomer);
		
		if(log.isDebugEnabled()) {
			log.debug("Customer successfully deleted, for customer with id: " + getCustomer().getId());
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}	
	
	/**
	 * Saves a customer
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		
		if (getCustomerId() != null)
			loadCustomerFromId();
		
		if(log.isDebugEnabled()) {
			log.debug("About to save customer: " + getCustomer());
		}
		
		getCustomer().setName(getCustomerName());
		getCustomer().setCode(getCustomerCode());

		
		//Adding a new region to the list if needed
		if (getNewRegionName() != null && !"".equals(getNewRegionName().trim()) ){				
			addNewRegion();
		}
		
		setRegionsToCustomer();

		getCustomerService().save(getCustomer());

		if(log.isInfoEnabled()) {
			log.info("Customer successfully updated for customer with id [" + getCustomer().getId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}	

	/**
	 * Prepares the region list to be saved on the DB.
	 * Handles the updated, created and removed region on the list.
	 */	
	private void setRegionsToCustomer() {
		Set<Region> regionsToRemove = new HashSet<Region>();
		
		// Update existing regions
		for (Region dbRegion : getCustomer().getRegions()) {
			Region aRegion = getRegionById(getRegions(), dbRegion.getId());
			if(aRegion != null) {
				dbRegion.setName(aRegion.getName());
			} else {
				regionsToRemove.add(dbRegion);
			}
		}

		// Remove regions
		for(Region regionToRemove : regionsToRemove) {
			getCustomer().removeRegion(regionToRemove);
		}
		
		// Add new regions
		for(Region aRegion : getRegions()) {
			if(aRegion.getId() == null) {
				getCustomer().addRegion(aRegion);
			}
		}			
	
	}

	/**
	 * Lookup for a region with id:"id".
	 * @param regionList
	 * @param id
	 * @return The region or null if doesn't find any match
	 */
	private Region getRegionById(List<Region> regionList, Integer id) {

		for(Region aRegion : regionList) {
			if(id.equals(aRegion.getId())) {
				return aRegion;
			}
		}
		
		return null;	
	}

	/**
	 *  Load full customer from the property customerId
	 */
	private void loadCustomerFromId() {
		Integer id = getCustomerId();
		
		//TODO: Better way to communicate actions in Struts 2?
		if(id == null) {
			id = (Integer) getSession().get(HttpRequestConstants.CUSTOMER_TO_EDIT_ID);
			setCustomerId(id);
		}
		getSession().put(HttpRequestConstants.CUSTOMER_TO_EDIT_ID, id);
		
		Customer tmpCustomer = new Customer();
		tmpCustomer.setId(this.getCustomerId());
		this.setCustomer(customerService.getCustomerById(tmpCustomer));
	}
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		//It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}	
}
