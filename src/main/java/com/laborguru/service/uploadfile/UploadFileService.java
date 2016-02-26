package com.laborguru.service.uploadfile;

import java.util.List;

import com.laborguru.model.UploadFile;

/**
 * Defines the business interface for UploadFile operations on the spm
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface UploadFileService {

	/**
	 * Retrieves an upload file instance by id
	 * @param uploadFile The upload file with id not null
	 * @return The instance associated or null
	 */
	UploadFile getUploadFileById(UploadFile uploadFile);

	/**
	 * Retrieves a list with all upload files of a given type
	 * @param The upload type
	 * @return
	 */
	List<UploadFile> findUploadFilesByType(UploadEnumType uploadType);
	
	/**
	 * Deletes an upload file from the spm
	 * @param uploadFile The upload file with id not null
	 * @return the UploadFile removed
	 */
	UploadFile delete(UploadFile uploadFile);
		
	/**
	 * Retrieves the number of the Historic Sales associated to the uploadFile passed as parameter.
	 * This method is an alternative to uploadFile.getHistoricSales.size(). 
	 * We used it when we only need the number of the HistoricSales but we don't want to iniatilize the historicSales collection. A call to size() 
	 * will initialize the entire list of historic sales of an upload file. In medium size files >10.000 records the call to size() can be very expensive.
 	 * 
	 * @return The number of historic sales associated to the upload file.
	 */	
	Integer getHistoricSalesSize(UploadFile uploadFile);	
}
