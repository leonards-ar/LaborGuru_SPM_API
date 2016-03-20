package com.laborguru.service.store.dao;

import java.util.List;

import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;

/**
 * Store Dao. Handles store database interaction for SPM
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface StoreDao {

	public enum OrderFindN{
		LASTEST, OLDEST
	}
	
	/**
	 * Saves or updates a store
	 * @param store store to save
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
	List<Store> applyFilter(SearchStoreFilter storeFilter);
	
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
	 * Retrieves the number of stores
	 * @return the number of stores in the system.
	 * @see com.laborguru.service.store.StoreService#getNumberOfStores()
	 */
	Integer getNumberOfStores();

	/**
	 * Retrieves the n oldest/latest updated stores in the system 
	 * @param n The number of stores to retrieve
	 * @param order The order (latest/oldest)
	 * @return the list of stores
	 */
	List<Store> findNStores(Integer n, OrderFindN order);

	/**
	 * Retrieves all Demo Stores
	 * @return
	 */
	List<Store> findAllDemo();
}
