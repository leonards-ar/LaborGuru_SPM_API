package com.laborguru.service.store.file;

import java.util.EnumSet;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.model.Area;
import com.laborguru.model.Customer;
import com.laborguru.model.Region;
import com.laborguru.model.Store;
import com.laborguru.util.PoiUtils;


/**
 * Represents a Store Information Section from the store definition upload file
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreInformation extends BaseStoreSection{
	private static final Logger log = Logger.getLogger(StoreInformation.class);

	public enum StoreInformationField{
		
		CLIENT("Client"),
		CLIENT_CODE("Client Code"),
		NUMBER("Number"),
		NAME("NAME"),
		REGION("Region"),
		AREA("Area");
		
		
		private String fieldName;
				
		StoreInformationField(String fieldName){
			this.fieldName = fieldName;
		}
		
		public String getFieldName(){
			return this.fieldName;
		}
	}
	
	private String client;
	private String clientCode;
	private String number;
	private String name;
	private String region;
	private String area;
	
	/**
	 * Default Constructor
	 */
	public StoreInformation(){
		super();
		setSection(StoreSection.STORE_INFORMATION);
	}
	
	
	/**
	 * @param fieldName
	 * @return
	 */
	public StoreInformationField getFielType(String fieldName){
		for (StoreInformationField field: EnumSet.allOf(StoreInformationField.class)){
			if (field.getFieldName().equalsIgnoreCase(fieldName)){
				return field;
			}
		}
		
		String message = "Store Information row is invalid  - fieldName:"+fieldName;
		log.error(message);
		throw new InvalidFieldUploadFileException(message, ErrorEnum.STORE_INVALID_ROW, new String[] {fieldName});
	}

	
	/**
	 * Add row to section
	 * @param row
	 * @see com.laborguru.service.store.file.BaseStoreSection#addRowToSection(org.apache.poi.hssf.usermodel.Row)
	 */
	@Override
	protected void addRowToSection(Row row) {

		String fieldName = PoiUtils.getStringValue(row.getCell((short)2));
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));

		StoreInformationField infoStoreType = getFielType(fieldName);
		
		switch(infoStoreType){		
			case AREA:
				setArea(fieldValue);
				break;
			case CLIENT:
				setClient(fieldValue);
				break;
			case CLIENT_CODE:
				setClientCode(fieldValue);
				break;
			case NAME:
				setName(fieldValue);
				break;
			case NUMBER:
				setNumber(fieldValue);
				break;
			case REGION:
				setRegion(fieldValue);
				break;
			default: throw new IllegalArgumentException("The type passed as parameter is wrong");			
		}
	}
	
	/**
	 * @param store
	 */
	public void assembleStore(Store store) {
		validate();
		
		Area area = new Area();
		Region region = new Region();
		Customer customer = new Customer();
		
		customer.setCode(getClientCode());
		customer.setName(getClient());
		
		region.setName(getRegion());
		
		area.setName(getArea());
		
		region.setCustomer(customer);
		area.setRegion(region);
		
		store.setArea(area);
		store.setCode(getNumber());
		store.setName(getName());
	}	
	
	/**
	 * 
	 */
	public void validate(){
		StoreAssemblerValidator.validateRequiredField(getArea(), StoreInformationField.AREA.getFieldName());
		StoreAssemblerValidator.validateRequiredField(getRegion(),StoreInformationField.REGION.getFieldName());
		StoreAssemblerValidator.validateRequiredField(getNumber(),StoreInformationField.NUMBER.getFieldName());
		StoreAssemblerValidator.validateRequiredField(getName(), StoreInformationField.NAME.getFieldName());
		StoreAssemblerValidator.validateRequiredField(getClientCode(), StoreInformationField.CLIENT_CODE.getFieldName());
	}
	
	
	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the clientCode
	 */
	public String getClientCode() {
		return clientCode;
	}

	/**
	 * @param clientCode the clientCode to set
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
}
