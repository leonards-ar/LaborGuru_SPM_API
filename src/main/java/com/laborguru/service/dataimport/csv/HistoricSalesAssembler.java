package com.laborguru.service.dataimport.csv;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.laborguru.exception.FileParserException;
import com.laborguru.model.Customer;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Store;
import com.laborguru.model.service.SalesCSVHistoricSales;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 * Line[]
 * 0 - Customer Code - We use 0 and 1 to identify the store
 * 1 - Store Number - We use 0 and 1 to identify the store
 * 2 - String Location Code
 * 3 - Date - M/dd/yyyy
 * 4 - Half hour - h:mm a
 * 5 - Main Value
 * 6 - Sencond Value - Optional
 * 7 - Thrid Value - Optional
 * 8 - Fourth Value - Optional
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HistoricSalesAssembler {
	private static final Logger log = Logger.getLogger(HistoricSalesAssembler.class);

	private final static String DATE_PARSE_FORMAT = "M/dd/yyyy";
	
	public static SalesCSVHistoricSales getHistoricSales(String[] line){		
		
		if (!isValidLine(line)){
			String message = "Parsing error: the line is invalid";
			log.debug(message);
			throw new FileParserException(message);			
		}
		
		SalesCSVHistoricSales customerHistoricSales = new SalesCSVHistoricSales();
		Customer customer = new Customer();		
		HistoricSales historicSale = new HistoricSales();		
		
		//Setting store code
		String customerCode = line[0];
		if (customerCode != null && !("".equals(customerCode.trim()))){
			customer.setCode(customerCode.trim());
		
		} else {
			String message = "Parsing error: customer code is:"+customerCode;
			log.debug(message);
			throw new FileParserException(message);
		}
				
		String storeCode = line[1];
		
		if (storeCode != null && !("".equals(storeCode.trim()))){
			Store store = new Store();
			store.setCode(storeCode.trim());		
			historicSale.setStore(store);
		} else {
			String message = "Parsing error: store code is:"+storeCode;
			log.debug(message);
			throw new FileParserException(message);
		}
		
		//Setting date time & day of the week
		Date date = CalendarUtils.stringToDate(line[3], DATE_PARSE_FORMAT);
		
		if (date != null){
			Calendar calendarDate = CalendarUtils.getCalendar(date);		
			
			Date time = CalendarUtils.stringToDate(line[4], SpmConstants.TIME_PARSE_FORMAT);
			if (time != null){
				Calendar calendarTime = CalendarUtils.getCalendar(time);		

				calendarDate.add(Calendar.HOUR, calendarTime.get(Calendar.HOUR_OF_DAY));
				calendarDate.add(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
			
				historicSale.setDateTime(calendarDate.getTime());
				historicSale.setDayOfWeek(calendarDate.get(Calendar.DAY_OF_WEEK));
			} else {
				String message = "Parsing error: time "+line[4]+" is not valid";
				log.debug(message);
				throw new FileParserException(message);
			}
		} else {
			String message = "Parsing error: date "+line[3]+" is not valid";
			log.debug(message);
			throw new FileParserException(message);
		}
		
		//Setting Main Value
		Number mainValue = NumberUtils.stringToNumber(line[5], NumberUtils.DECIMAL_FORMAT);
		
		if (mainValue != null){
			BigDecimal mainBD = new BigDecimal(mainValue.toString());
			historicSale.setMainValue(mainBD);
		} else {
			String message = "Parsing error: main value "+line[5]+" is not valid";
			log.debug(message);
			throw new FileParserException(message);
		}
		
		//Setting optional fields
		if (line.length > 6){
			//Setting Second Value
			BigDecimal secondValue = processNumber(line[6], 6);			
			historicSale.setSecondValue(secondValue);
						
			if (line.length > 7){
				//Setting Third Value
				BigDecimal thirdValue = processNumber(line[7], 7);
				historicSale.setThirdValue(thirdValue);					
				
				if (line.length > 8){
					//Setting fourth Value
					BigDecimal fourthValue = processNumber(line[8], 8);
					historicSale.setFourthValue(fourthValue);
				}
			}	
		}
		
		customerHistoricSales.setCustomer(customer);
		customerHistoricSales.setHistoricSales(historicSale);
		
		return customerHistoricSales;
	}
		
	
	/**
	 * @param line
	 * @param historicSale
	 */
	private static BigDecimal processNumber(String valueString, int column){
		
		if ((valueString != null) && !("".equals(valueString.trim()))){
			String valueStringAux = valueString.trim();
			Number aValue = NumberUtils.stringToNumber(valueStringAux, NumberUtils.DECIMAL_FORMAT);
			
			if (aValue != null){
				return new BigDecimal(aValue.toString());
			} 
			
			String message = "Parsing error - field: "+column+ " - Error: number "+valueStringAux+" is not valid";
			log.debug(message);			
		}
		
		return null;
	}
	
	
	/**
	 * Checks wether a line is valid. A valid line is different to null and has a lenght greater or equal to 5.
	 * @param line the array that represents the line
	 * @return true if valid
	 */
	public static boolean isValidLine(String[] line){
		return (line != null) && (line.length >=6);
	}
	
}
