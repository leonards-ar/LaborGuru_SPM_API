package com.laborguru.service.store.file;

import java.io.File;

import com.laborguru.model.Store;

/**
 * Defines the store definition parser funtionality
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface StoreDefinitionFileParser {
	
	/**
	 * Makes an store from the file passed as parameter
	 * 
	 * @param storeToUpload
	 * @return The store to upload
	 */
	Store parseStore(File storeToUpload);
}
