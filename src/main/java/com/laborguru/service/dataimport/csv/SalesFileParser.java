package com.laborguru.service.dataimport.csv;

import java.io.File;

import com.laborguru.model.HistoricSales;
import com.laborguru.service.store.StoreService;

/**
 * Defines the Sales File Parser interface.
 * This defines the parsing mechanism when uploading a Sales File to Spm.
 * Implementation of this interface can allow the system to import: csv files, fixed-lenght files, excel or xml files.
 *  
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface SalesFileParser {
	
	/**
	 * Initalizes a SalesFileParser instance.
	 * @param the file to parse
	 * @param the number of lines to ignore at the begining of the file
	 * @return A salesFileParser initialized with the file passed in as parameter.
	 */ 
	SalesFileParser assembleSalesFileParser(File fileToParse, int ignoreLines);
	
	/**
	 * Returns the next Historic Sales Record from the file. When the end of file is reached or if there is no more valid records to return.
	 * this method returns NULL
	 * @return HistoricSales a HistoricSales instance;
	 */
	HistoricSales getNextRecord();
	
	/**
	 * Set the store service to be used when creating the HistoricSales.
	 * @param storeService
	 */
	void setStoreService(StoreService storeService);
	
	/**
	 * Check whether the file is valid
	 * @return whether the file is valid
	 */
	boolean isFileValid();
	
	
	/**
	 * Close underlying reading and release reources.
	 * Don't forget to call this method before you finish to preocess the upload.
	 */
	void close();
	
	
	/**
	 * Returns the total number of lines that have been processed in the file.
	 * It includes headers, valid and invalid lines.
	 * @return the number of lines that have been processed.
	 */
	int getAllLinesCounter();

	/**
	 * Returns the number of valid lines that have been processed in the file.
	 * It only includes the valid lines.
	 * @return the number of valid lines that have been processed.
	 */
	int getValidLinesCounter(); 
	
	/**
	 * Returns the number of errors that have been processed in the file.
	 * It only includes the lines with errors.
	 * @return the number of errors that have been processed.
	 */
	int getErrorLinesCounter();
	
}
