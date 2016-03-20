package com.laborguru.service.store;

import java.util.List;

import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.Service;

/**
 * Store Service. Handles store services for SPM
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface StoreService extends Service {

	/**
	 * Saves or updates a store
	 * @param store store to save or update
	 * @return the Store updated.
	 */
	Store save(Store store);
	
	
	/**
	 * Removes a store
	 * @param store store to remove
	 */
	void delete(Store store);
	
	/**
	 * Retrieves a list of stores that match the filter passed in as parameter
	 * @param storeFilter the store filter
	 * @return the store list
	 */
	List<Store> filterStore(SearchStoreFilter storeFilter);
	
	/**
	 * Retrieves a store by id
	 * @param store the store with id populated
	 * @return the Store
	 */
	Store getStoreById(Store store);
	
	/**
	 * Retrieves all the stores saved on SPM
	 * @return a list of stores
	 */
	List<Store> findAll();
	

	/**
	 * Retrieves the n latest stores in the system
	 * @param n The number of stores to retrieve
	 * @return store list
	 */
	List<Store> findLatestStores(Integer n);


	/**
	 * Retrieves the n oldest updated stores in the system 
	 * @param n The number of stores to retrieve
	 * @return store list
	 */
	List<Store> findOldestStores(int n);


	/**
	 * Retrieves the number of stores
	 * @return the number of stores in the system.
	 */
	Integer getNumberOfStores();
	
	/**
	 * Retrieves all Demo Stores
	 * @return
	 */
	List<Store> findAllDemo();
}
