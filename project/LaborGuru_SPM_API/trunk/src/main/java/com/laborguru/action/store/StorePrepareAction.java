package com.laborguru.action.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.HttpRequestConstants;
import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.Region;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.area.AreaService;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.region.RegionService;
import com.laborguru.service.store.StoreService;
import com.opensymphony.xwork2.Preparable;

/**
 * This action deals with Store CRUD.
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
@SuppressWarnings("serial")
public class StorePrepareAction extends SpmAction implements Preparable {
	
	private static Logger log = Logger.getLogger(StorePrepareAction.class);
	
	private StoreService storeService;
	private CustomerService customerService;
	private RegionService regionService;
	private AreaService areaService;

	private Store store;
	
	/**
	 * As the store CRUD is special and is editing among different
	 * pages, the Store CRUD only updates certain information. This
	 * property will hold the complete store to save with all it's
	 * relations.
	 */
	private Store storeToSave;
	
	private List<Store> stores;

	private List<Customer> customers;
	private List<Region> regions;
	private List<Area> areas;

	private Customer storeCustomer;
	private Region storeRegion;
	private Area storeArea;
	
	private SearchStoreFilter searchStore;
	private Integer storeId;

	private boolean removePage;

	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare data to be used to display store data
	 */
	public void prepareShow() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare the data to be used on the add page
	 * 
	 * @throws Exception
	 */
	public void prepareAdd() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare the data to be used on the add page. We should preload the list
	 * needed to render the add page. When a validation fails the application
	 * goes back to the add page and this data is needed.
	 * 
	 * @throws Exception
	 */
	public void prepareSave() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare the data to be used on the list page.
	 * Loads the lists that will be used in the store
	 * search form's combo boxes.
	 */
	public void prepareSearch() {
		loadListsForListPage();
	}

	/**
	 * Prepare the data to be used on the list page.
	 * Loads the lists that will be used in the store
	 * list search form's combo boxes.
	 */
	public void prepareList() {
		loadListsForListPage();
	}

	/**
	 * Loads customers.
	 */
	private void loadListsForListPage() {
		setCustomers(getCustomerService().findAll());
	}
	
	/**
	 * Loads customers, regions and areas lists
	 */
	private void loadListsForAddEditPage() {
		customers = customerService.findAll();
		if(getStoreCustomer() != null && getStoreCustomer().getRegions() != null) {
			regions = new ArrayList<Region>(getStoreCustomer().getRegions());
		} else {
			regions = new ArrayList<Region>();
		}
		
		if(getStoreRegion() != null && getStoreRegion().getAreas() != null) {
			areas = new ArrayList<Area>(getStoreRegion().getAreas());
		} else {
			areas = new ArrayList<Area>();
		}

	}

	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		// It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * Performs a Store Search
	 * 
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {

		this.setStores(getStoreService().filterStore(getSearchStore()));

		return SpmActionResult.LIST.getResult();
	}

	/**
	 * Retrieves all the stores that must be displayed on the list page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		
		setStores(storeService.findAll());
		
		return SpmActionResult.LIST.getResult();
	}

	/**
	 * Selects a Customer, filling the regions combo box
	 * with the corresponding regions.
	 * @return
	 * @throws Exception
	 */
	public String selectCustomer() throws Exception {
		/*
		 * We need the hibernate object so all the regions are loaded
		 * to fill the regions combo box (prepareSelectCustomer method)
		 */
		if(getStoreCustomer() != null && getStoreCustomer().getId() != null) {
			setStoreCustomer(getCustomerService().getCustomerById(getStoreCustomer()));
			// Force the load of the internal sets
			getStoreCustomer().getRegions();
		}
		// Refresh lists
		loadListsForAddEditPage();
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Selects a Region, filling the areas combo box
	 * with the corresponding areas.
	 * @return
	 * @throws Exception
	 */
	public String selectRegion() throws Exception {
		/*
		 * We need the hibernate object so all the regions are loaded
		 * to fill the regions combo box (prepareSelectCustomer method)
		 */
		if(getStoreCustomer() != null && getStoreCustomer().getId() != null) {
			setStoreCustomer(getCustomerService().getCustomerById(getStoreCustomer()));
			// Force the load of the internal sets
			getStoreCustomer().getRegions();
		}
		
		/*
		 * We need the hibernate object so all the areas are loaded
		 * to fill the areas combo box (prepareSelectRegion method)
		 */		
		setStoreRegion(getRegionService().getRegionById(getStoreRegion()));
		// Force the load of the internal sets
		getStoreRegion().getAreas();
		// Refresh lists
		loadListsForAddEditPage();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * Prepares the add page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		// Getting store
		loadStoreFromId();
		
		setStoreArea(getStore().getArea());
		setStoreRegion(getStoreArea().getRegion());
		setStoreCustomer(getStoreRegion().getCustomer());
		
		// Force the load of the internal sets
		getStoreCustomer().getRegions();
		getStoreRegion().getAreas();
		
		loadListsForAddEditPage();
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * Prepare removes page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception {
		// Getting store
		loadStoreFromId();

		this.setRemovePage(true);

		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Prepares the view page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		// Getting store
		loadStoreFromId();
		
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Stores a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		
		log.debug(getStore());

		if(getStore().getId() != null) {
			setStoreToSave(getStoreService().getStoreById(getStore()));
		} else {
			// storeToSave already contains default values (thanks Spring!)
		}
		
		getStoreToSave().setName(getStore().getName());
		getStoreToSave().setCode(getStore().getCode());
		getStoreToSave().setArea(getStoreArea());
		getStoreToSave().setDemo(getStore().isDemo());
		getStoreToSave().setInTimeOnly(getStore().isInTimeOnly());
		
		storeService.save(getStoreToSave());

		return SpmActionResult.LISTACTION.getResult();

	}

	/**
	 * Deletes a store from the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		//Getting store
		Store auxStore = storeService.getStoreById(getStore());		
		storeService.delete(auxStore);
		
		return SpmActionResult.LISTACTION.getResult();
	}

	/**
	 * Load full store from the property storeId
	 */
	private void loadStoreFromId() {
		Integer id = getStoreId();
		//:TODO: Better way to communicate actions in Struts 2?
		if(id == null) {
			id = (Integer) getSession().get(HttpRequestConstants.STORE_TO_EDIT_ID);
			setStoreId(id);
		}
		getSession().remove(HttpRequestConstants.STORE_TO_EDIT_ID);

		Store tmpStore = new Store();
		tmpStore.setId(id);
		this.setStore(getStoreService().getStoreById(tmpStore));
	}	
	

	/**
	 * 
	 * @return
	 */
	public boolean isRemovePage() {
		return removePage;
	}

	/**
	 * 
	 * @param removePage
	 */
	public void setRemovePage(boolean removePage) {
		this.removePage = removePage;
	}

	/**
	 * @return the storesSize
	 */
	public int getStoresSize() {
		return getStores() != null ? getStores().size() : 0;
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store
	 *            the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the stores
	 */
	public List<Store> getStores() {
		return stores;
	}

	/**
	 * @param stores
	 *            the stores to set
	 */
	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	/**
	 * @return the storeId
	 */
	public Integer getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId
	 *            the storeId to set
	 */
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @param storeService
	 *            the storeService to set
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
	 * @param customerService
	 *            the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService
	 *            the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * @return the areaService
	 */
	public AreaService getAreaService() {
		return areaService;
	}

	/**
	 * @param areaService
	 *            the areaService to set
	 */
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @return the regions
	 */
	public List<Region> getRegions() {
		return regions;
	}

	/**
	 * @param regions
	 *            the regions to set
	 */
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	/**
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas
	 *            the areas to set
	 */
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	/**
	 * @return the searchStore
	 */
	public SearchStoreFilter getSearchStore() {
		return searchStore;
	}

	/**
	 * @param searchStore
	 *            the searchStore to set
	 */
	public void setSearchStore(SearchStoreFilter searchStore) {
		this.searchStore = searchStore;
	}

	/**
	 * @return the storeCustomer
	 */
	public Customer getStoreCustomer() {
		return storeCustomer;
	}

	/**
	 * @param storeCustomer the storeCustomer to set
	 */
	public void setStoreCustomer(Customer storeCustomer) {
		this.storeCustomer = storeCustomer;
	}

	/**
	 * @return the storeRegion
	 */
	public Region getStoreRegion() {
		return storeRegion;
	}

	/**
	 * @param storeRegion the storeRegion to set
	 */
	public void setStoreRegion(Region storeRegion) {
		this.storeRegion = storeRegion;
	}

	/**
	 * @return the storeArea
	 */
	public Area getStoreArea() {
		return storeArea;
	}

	/**
	 * @param storeArea the storeArea to set
	 */
	public void setStoreArea(Area storeArea) {
		this.storeArea = storeArea;
	}

	/**
	 * @return the storeToSave
	 */
	public Store getStoreToSave() {
		return storeToSave;
	}

	/**
	 * @param storeToSave the storeToSave to set
	 */
	public void setStoreToSave(Store storeToSave) {
		this.storeToSave = storeToSave;
	}
}
