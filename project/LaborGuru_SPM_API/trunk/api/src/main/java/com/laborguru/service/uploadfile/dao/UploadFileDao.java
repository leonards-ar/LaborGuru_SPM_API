package com.laborguru.service.uploadfile.dao;

import java.util.Date;
import java.util.List;

import com.laborguru.model.UploadFile;
import com.laborguru.service.uploadfile.UploadEnumType;

/**
 * Defines the business interface for the data access operations for Upload File
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface UploadFileDao {

	/**
	 * It creates or updates an uploadFile instance
	 * @param uploadFile the object to persist
	 */
	void saveOrUpdate(UploadFile uploadFile);

	/**
	 * Retrieves an uploadfile file instance by id. Takes the id of the instance passed as parameter.
	 * @param id An uploadfile file instance with id not null.
	 * @return The full instance associated with the id or null
	 */
	UploadFile getUploadFileById(UploadFile uploadFile);

	/**
	 * Returns all the uploadfile instances of a given type
	 * @param The uploadfile Type
	 * @return a list of UploadFiles
	 */
	List<UploadFile> findByType(UploadEnumType uploadType);	
	
	/**
	 * Returns all the uploadfile instances stored in the DB
	 * @return a list of UploadFiles
	 */
	List<UploadFile> findAll();

	/**
	 * Removes an uploadFile from the system. It also removes all the historic sales associated with the uploadfile file.
	 * @param uploadFile an attached instance of uploadFile.
	 */
	void delete(UploadFile uploadFile);	
	
	/**
	 * Returns an uploadfile file by storeId, Historic Sales date and UploadType
	 * It used to retrieve the uploadfile file in the actual load through web.
	 * @param storeId
	 * @param hsDate
	 * @param uploadType
	 * @return
	 */
	UploadFile getUploadsByStoreAndHSDateAndType(Integer storeId, Date hsDate, UploadEnumType uploadType);
	
	/**
	 * Retrieves the number of the Historic Sales associated to the uploadFile passed as parameter.
	 * This method is an alternative to uploadFile.getHistoricSales.size(). 
	 * We used it when we only need the number of the HistoricSales but we don't want to iniatilize the historicSales collection. A call to size() 
	 * will initialize the entire list of historic sales of an uploadfile file.
	 * 
	 * @return The number of historic sales associated to the uploadfile file.
	 */	
	Integer getHistoricSalesSize(UploadFile uploadFile);
}
