package com.laborguru.model.filter;


/**
 * Search Store Filter
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SearchStoreFilter {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer customerId;
	
	//The customer code matchs exact value. This behaviour It's required by the uploader. Do not modify without looking at
	//the consequences in the historic sales upload process. 
	private String customerCode;
	
	private String name;
	private String code;
		
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}


	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
}
