package com.laborguru.service.store.file;

import java.io.File;

import com.laborguru.model.Store;
import com.laborguru.service.customer.CustomerService;
import com.laborguru.service.store.StoreService;

/**
 * Defines the business interface for upload store definitions to the system
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface UploadStoreDefinitionService {

	/**
	 * This method uploads a store definition to the SPM
	 * The file passed as parameter contains the parameters to create an store.
	 * 
	 * New Store: If the file passed as paramater defines a new store. A new store is created.
	 * Update Store: If the file passed as parameter defines an existing store, the existing store is updated with the new values.
	 * 
	 * @param storeDefinition
	 * @return the store created
	 */
	Store processStoreDefinitionAndSave(File storeDefinitionFile);

	/**
	 * @param storeDefinitionFileParser the storeDefinitionFileParser to set
	 */
	void setStoreDefinitionFileParser(StoreDefinitionFileParser storeDefinitionFileParser);
	
	/**
	 * 
	 * @param storeService
	 */
	void setStoreService(StoreService storeService);
	
	/**
	 * 
	 * @param customerService
	 */
	void setCustomerService(CustomerService customerService);
}