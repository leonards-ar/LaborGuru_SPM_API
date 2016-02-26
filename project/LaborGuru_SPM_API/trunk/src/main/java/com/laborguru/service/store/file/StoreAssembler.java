package com.laborguru.service.store.file;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.model.Store;
import com.laborguru.service.store.file.BaseStoreSection.StoreSection;

/**
 * This class assembles a store instance from an excel file.
 * Usage:
 * Rows from the excel file are added and when is finished assemble is called.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreAssembler {
	
	private static final Logger log = Logger.getLogger(StoreAssembler.class);
	
	private Store store;
	private StoreInformation storeInformation;
	private StoreOperation storeOperation;	
	private LaborAssumption laborAssumption;
	private StoreAllowance storeAllowances;
	
	
	private StoreAssembler(){
		this.store = new Store();
		this.storeInformation = new StoreInformation();
		this.storeOperation = new StoreOperation();
		this.laborAssumption = new LaborAssumption();
		this.storeAllowances = new StoreAllowance();
	}
	
	public static StoreAssembler getStoreAssembler(){
		return new StoreAssembler();
	}
	
	/**
	 * @param row
	 */
	public void addToStore(Row row){
				
		StoreSection section = BaseStoreSection.getRowSection(row);
		switch(section){
			case STORE_INFORMATION:
				storeInformation.addField(row);		
				break;
			case LABOR_ASSUMPTIONS:
				laborAssumption.addField(row);
				break;
			case STORE_ALLOWANCES:
				storeAllowances.addField(row);
				break;
			case STORE_OPERATIONS:
				storeOperation.addField(row);				
				break;
			default: throw new IllegalArgumentException("The type passed as parameter is wrong");		
		}
	}

	
	/**
	 * @return
	 */
	public Store assemble(){				
		storeInformation.assembleStore(this.store);
		storeOperation.assembleStore(this.store);
		laborAssumption.assembleStore(this.store);
		storeAllowances.assembleStore(this.store);
		
		return this.store;
	}
}
