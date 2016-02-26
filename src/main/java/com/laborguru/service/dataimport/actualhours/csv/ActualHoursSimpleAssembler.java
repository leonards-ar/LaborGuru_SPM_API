package com.laborguru.service.dataimport.actualhours.csv;

import java.util.Date;

import org.apache.log4j.Logger;

import com.laborguru.exception.FileParserException;
import com.laborguru.model.ActualHours;
import com.laborguru.model.Customer;
import com.laborguru.model.Store;
import com.laborguru.service.dataimport.actualhours.ActualHoursAssembler;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 * Line[]
 * 0 - Customer Code - We use 0 and 1 to identify the store
 * 1 - Store Number - We use 0 and 1 to identify the store
 * 2 - String Location Code
 * 3 - Date - M/dd/yyyy
 * 5 - Labor Hours Value
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ActualHoursSimpleAssembler implements ActualHoursAssembler {
	private static final Logger log = Logger.getLogger(ActualHoursSimpleAssembler.class);

	private final static String DATE_PARSE_FORMAT = "M/dd/yyyy";

	/**
	 * @param line
	 * @return
	 * @see com.laborguru.service.dataimport.actualhours.ActualHoursAssembler#getCustomer(java.lang.String[])
	 */
	public Customer getCustomer(String[] line){	
		Customer customer = new Customer();	
		
		String customerCode = line[0];
		
		if (customerCode != null && !("".equals(customerCode.trim()))){
			customer.setCode(customerCode.trim());
		
		} else {
			String message = "Parsing error: customer code is:"+customerCode;
			log.debug(message);
			throw new FileParserException(message);
		}
		
		return customer;
	}

	
	/**
	 * @param line
	 * @return
	 * @see com.laborguru.service.dataimport.actualhours.ActualHoursAssembler#getActualHours(java.lang.String[])
	 */
	public ActualHours getActualHours(String[] line){		
		
		if (!isValidLine(line)){
			String message = "Parsing error: the line is invalid";
			log.debug(message);
			throw new FileParserException(message);			
		}
		
		ActualHours actualHours = new ActualHours();		
		
		//Setting store code
		String storeCode = line[1];
		
		if (storeCode != null && !("".equals(storeCode.trim()))){
			Store store = new Store();
			store.setCode(storeCode.trim());		
			actualHours.setStore(store);
		} else {
			String message = "Parsing error: store code is:"+storeCode;
			log.debug(message);
			throw new FileParserException(message);
		}
		
		//Setting date
		Date date = CalendarUtils.stringToDate(line[3], DATE_PARSE_FORMAT);
		
		if (date != null){			
			actualHours.setDate(date);
		} else {
			String message = "Parsing error: date "+line[3]+" is not valid";
			log.debug(message);
			throw new FileParserException(message);
		}
		
		//Setting Hours
		Number hours = NumberUtils.stringToNumber(line[4], NumberUtils.DECIMAL_FORMAT);
		
		if (hours != null){
			Double hoursDouble = hours.doubleValue();
			actualHours.setHours(hoursDouble);
		} else {
			String message = "Parsing error: hours value "+line[4]+" is not valid";
			log.debug(message);
			throw new FileParserException(message);
		}
		

		
		return actualHours;
	}
		
	/**
	 * Checks wether a line is valid. A valid line is different to null and has a lenght equal to 5.
	 * The number of fields is restricted to 5.
	 * @param line the array that represents the line
	 * @return true if valid
	 */
	private boolean isValidLine(String[] line){
		return (line != null) && (line.length ==5);
	}
	
}
