package com.laborguru.service.dataimport.csv;

import java.io.File;

import com.laborguru.model.UploadFile;
import com.laborguru.model.service.UploadFileProcessed;

/**
 * This interface define the sale file processor used to import sales data to the SPM
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface SalesFileProcessorService {
	
	/**
	 * This method process the file passed as parameter, creates and persist an UploadFile object and the sales records contained in the file.
	 * @param file: the file to upload and save
	 * @param uploadFile: Optional helper objec used to pass information to the service. The values defined in the object are going to be used when 
	 * creating the UploadFile instance. If null method defaults are used. 	 
	 * 
	 * @return The upload file entity created.
	 */
	UploadFileProcessed processAndSaveFile(File file, UploadFile uploadFile);
	
	/**
	 * Sets the file parser to use when processing the file
	 * @param fileParser The file parser
	 */
	void setFileParser(SalesFileParser fileParser);
}
