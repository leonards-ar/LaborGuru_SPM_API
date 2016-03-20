package com.laborguru.service.dataimport.actualhours.csv;

import java.io.IOException;
import java.util.List;

import com.laborguru.exception.FileParserException;
import com.laborguru.model.ActualHours;
import com.laborguru.model.Customer;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.dataimport.actualhours.ActualHoursAssembler;
import com.laborguru.service.store.StoreService;

/**
 * Implements a parser for a CSV sales file
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ActualHoursSimpleCSVParserBean extends ActualHoursCSVParser {
 
	private ActualHoursAssembler assembler;
	private StoreService storeService;

	
	/**
	 * @return
	 * @see com.laborguru.service.dataimport.csv.SalesFileParser#getNextRecord()
	 */
	public ActualHours getNextRecord() {		
		ActualHours actualHour = null;
		Customer customer = null;
		Store lastStore = null;
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
					actualHour = assembler.getActualHours(nextLine);					
					customer = assembler.getCustomer(nextLine);
					Store store = getCurrentStore(lastStore, actualHour.getStore(), customer);					
					actualHour.setStore(store);					
					if (!store.equals(lastStore)){
						lastStore = store;
					}
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
		
		return actualHour;
	}	
	
	
	/**
	 * Get the current store instance for the line we are processing.
	 * 
	 * @param historicSales
	 * @return
	 */
	private Store getCurrentStore(Store currentStore, Store newStore, Customer customer) {
			
		if (currentStore == null || !currentStore.getCode().equals(newStore.getCode()) 
				|| !currentStore.getArea().getRegion().getCustomer().getCode().equals(customer.getCode())){
			SearchStoreFilter storeFilter = new SearchStoreFilter();
			
			storeFilter.setCode(newStore.getCode());
			storeFilter.setCustomerCode(customer.getCode());
			
			List<Store> storeList = storeService.filterStore(storeFilter);
			
			if (storeList != null && storeList.size() > 0){
				return storeList.get(0);
			} else {				
			    String message = "The store with store code:"+ newStore.getCode()+" and customer code:"+customer.getCode()+" does not exist";
				log.error(message);
				throw new FileParserException(message);
			}
		}
		
		return currentStore;
	}


	public boolean isFileValid() {
		return true;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	
	/**
	 * @param assembler the assembler to set
	 */
	public void setAssembler(ActualHoursAssembler assembler) {
		this.assembler = assembler;
	}	
}
