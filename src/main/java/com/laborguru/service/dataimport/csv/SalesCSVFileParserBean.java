package com.laborguru.service.dataimport.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.laborguru.exception.FileParserException;
import com.laborguru.exception.InvalidUploadFileException;
import com.laborguru.model.Customer;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.model.service.SalesCSVHistoricSales;
import com.laborguru.service.store.StoreService;

/**
 * Implements a parser for a CSV sales file
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SalesCSVFileParserBean implements SalesFileParser {

	private static final Logger log = Logger.getLogger(SalesCSVFileProcessorBean.class);

	private int allLinesCounter;
	private int validLinesCounter;
	private int errorLinesCounter;
	private StoreService storeService;
	private Store store;
	private CSVReader csvReader;
	private String filename;
	
	public SalesCSVFileParserBean(){
		
	}	
	
	/**
	 * This method initializes the parser with file we are going to process.
	 * @param fileToParse
	 * @return
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#assembleSalesFileParser(java.io.File)
	 */
	public SalesFileParser assembleSalesFileParser(File fileToParse, int ignoreLines) {

		FileReader inReader;

		try {
			inReader = new FileReader(fileToParse);
		} catch (FileNotFoundException e) {
			String message = "File to parse:"+fileToParse.getName()+ "is not found";
			log.error(message);
			throw new InvalidUploadFileException(e, message);			
		}
		
		this.filename = fileToParse.getName();
		this.csvReader = new CSVReader(inReader);
		this.allLinesCounter = 0;
		this.errorLinesCounter = 0;
		this.validLinesCounter = 0;
		
		if (ignoreLines > 0){
			for (int i=0; i < ignoreLines; i++){
				try {
					
					csvReader.readNext();
					allLinesCounter++;
					
				} catch (IOException e) {
					String message = "Error reading the line:"+allLinesCounter+ " on the file:"+this.filename;
					log.error(message);
					throw new FileParserException(e, message);			
				}
			}
		}
		
		return this;
	}

	/**
	 * Close underlying reading and release reources.
	 * Don't forget to call this method before you finish to preocess the upload.
	 * @throws IOException 
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#close()
	 */
	public void close() {
		try {
			this.csvReader.close();
		} catch (IOException e) {
			String message = "Error - Trying to close csv reader:"+this.csvReader+ " - IO reader for the file:"+this.filename+"could not be closed";
			log.error(message);
			throw new FileParserException(e, message);			
		}
	}	
	
	/**
	 * @return
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#getNextRecord()
	 */
	public HistoricSales getNextRecord() {		
		HistoricSales aHistoricSales = null;
		SalesCSVHistoricSales csvRecord = null;
		String nextLine[] = null;
		boolean getNext = true;

		while (getNext){
			try {
				nextLine = csvReader.readNext();
			} catch (IOException e) {
				String message = "Error reading the line:"+allLinesCounter+ " on the file:"+this.filename;
				log.debug(message);
				throw new FileParserException(e, message);			
			}
			
			if (nextLine != null){
				allLinesCounter++;
				try {
					csvRecord = HistoricSalesAssembler.getHistoricSales(nextLine);					
					aHistoricSales = csvRecord.getHistoricSales();
					
					Store store = getCurrentStore(aHistoricSales.getStore(), csvRecord.getCustomer());
					
					aHistoricSales.setStore(store);
					validLinesCounter++;
					getNext = false;
				} catch(FileParserException e) {
				    String message = "Invalid line - Line number:"+allLinesCounter + " - Error message: "+e.getMessage();
					log.error(message);
					errorLinesCounter++;
					continue;
				}
			} else{
				//End of file
				return null;
			}
		}
		
		return aHistoricSales;
	}	
	
	
	/**
	 * Get the current store instance for the line we are processing.
	 * 
	 * @param historicSales
	 * @return
	 */
	private Store getCurrentStore(Store hsStore, Customer customer) {
		
		if (this.store == null || !this.store.getCode().equals(hsStore.getCode()) 
				|| !this.store.getArea().getRegion().getCustomer().getCode().equals(customer.getCode())){
			SearchStoreFilter storeFilter = new SearchStoreFilter();
			
			storeFilter.setCode(hsStore.getCode());
			storeFilter.setCustomerCode(customer.getCode());
			
			List<Store> storeList = storeService.filterStore(storeFilter);
			
			if (storeList != null && storeList.size() > 0){
				this.store = storeList.get(0);
			} else {				
			    String message = "The store with store code:"+ hsStore.getCode()+" and customer code:"+customer.getCode()+" does not exist";
				log.error(message);
				throw new FileParserException(message);
			}
		}
		return this.store;
	}

	/**
	 * @param storeService
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#setStoreService(com.laborguru.service.store.StoreService)
	 */
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * @return the storeService
	 */
	public StoreService getStoreService() {
		return storeService;
	}

	/**
	 * @return
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#isFileValid()
	 */
	public boolean isFileValid() {
		return true;
	}

	/**
	 * @return the allLinesCounter
	 */
	public int getAllLinesCounter() {
		return allLinesCounter;
	}

	/**
	 * @return the validLinesCounter
	 */
	public int getValidLinesCounter() {
		return validLinesCounter;
	}
	
	/**
	 * @return the allLinesCounter
	 */
	public int getErrorLinesCounter() {
		return errorLinesCounter;
	}	
}
