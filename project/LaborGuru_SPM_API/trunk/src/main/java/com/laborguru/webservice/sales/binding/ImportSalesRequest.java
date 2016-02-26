
package com.laborguru.webservice.sales.binding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.HistoricSales;
import com.laborguru.model.Store;
import com.laborguru.model.filter.SearchStoreFilter;
import com.laborguru.service.store.StoreService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 * 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.laborguru.com/spm/webservices/sales" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="importSalesRequest">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element type="ns:SalesItem" name="salesItem" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class ImportSalesRequest {
	private static final Logger logger = Logger.getLogger(ImportSalesRequest.class);
	private static final String DEFAULT_DATE_FORMAT="yyyyMMdd";
    private String storeCode;
    private String storeLocation;
    private String salesDate;
    private String salesDateFormat;
    
    private SalesItemList salesItemList;

    /** 
     * Get the 'storeCode' element value.
     * 
     * @return value
     */
    public String getStoreCode() {
        return storeCode;
    }

    /** 
     * Set the 'storeCode' element value.
     * 
     * @param storeCode
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /** 
     * Get the 'storeLocation' element value.
     * 
     * @return value
     */
    public String getStoreLocation() {
        return storeLocation;
    }

    /** 
     * Set the 'storeLocation' element value.
     * 
     * @param storeLocation
     */
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    /** 
     * Get the 'salesDate' element value.
     * 
     * @return value
     */
    public String getSalesDate() {
        return salesDate;
    }

    /** 
     * Set the 'salesDate' element value.
     * 
     * @param salesDate
     */
    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }  
    
	/**
	 * @return the dateFormat
	 */
	public String getSalesDateFormat() {
		return salesDateFormat != null ? salesDateFormat : DEFAULT_DATE_FORMAT;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setSalesDateFormat(String dateFormat) {
		if(dateFormat != null) this.salesDateFormat = dateFormat;
	}

	/**
	 * @return the salesItems
	 */
	public SalesItemList getSalesItemList() {
		return salesItemList;
	}

	/**
	 * @param salesItemList the salesItems to set
	 */
	public void setSalesItemList(SalesItemList salesItemList) {
		this.salesItemList = salesItemList;
	}

	public List<HistoricSales> bindDb(StoreService storeService) throws Exception {
		SearchStoreFilter storeFilter = new SearchStoreFilter();
		Store store;
		
		storeFilter.setCode(storeCode);
		storeFilter.setCustomerCode(storeLocation);
		
		List<Store> storeList = storeService.filterStore(storeFilter);
		
		if(storeList == null || storeList.size() == 0){
		    String message = "The store with store code:"+ storeCode+" and customer code:"+storeLocation+" does not exist";
			logger.error(message);
			throw new SpmCheckedException(message, ErrorEnum.STORE_DOES_NOT_EXIST);
		}

		store = storeList.get(0);
		
		List<HistoricSales>historicSales = new ArrayList<HistoricSales>();
		
		for(SalesItem salesItem: salesItemList.getSalesItemLists()) {
			HistoricSales historicSale = salesItem.getHistoricSale();
			historicSale.setStore(store);
				
			Date time = SpmConstants.TIME_FORMAT.parse(salesItem.getHalfHour());
			Date date = CalendarUtils.stringToDate(salesDate, getSalesDateFormat());
			Calendar calendarDate = CalendarUtils.getCalendar(date);
			if (time != null){
				Calendar calendarTime = CalendarUtils.getCalendar(time);		

				calendarDate.add(Calendar.HOUR, calendarTime.get(Calendar.HOUR_OF_DAY));
				calendarDate.add(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
			
				historicSale.setDateTime(calendarDate.getTime());
				historicSale.setDayOfWeek(calendarDate.get(Calendar.DAY_OF_WEEK));
			}
			historicSales.add(historicSale);
			
		}
		return historicSales;
	}
	
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
	   	.append("storeCode" , storeCode)
	   	.append("storeLocation",storeLocation)
	   	.append("salesDate", salesDate)
	   	.append("salesItemList", salesItemList)
	   	.toString();		
	}
		
 
}
