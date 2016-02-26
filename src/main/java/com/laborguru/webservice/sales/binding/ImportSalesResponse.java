package com.laborguru.webservice.sales.binding;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class ImportSalesResponse {

	private int transactionsReceived;
	private int transactionsProcessed;
	
	/**
	 * Default Constructor 
	 */
	public ImportSalesResponse() {
	}
	
	/**
	 * @param transactionsReceived
	 * @param transactionProcessed
	 */
	public ImportSalesResponse(int transactionsReceived, int transactionProcessed) {
		this.transactionsReceived = transactionsReceived;
		this.transactionsProcessed = transactionProcessed;
	}
	
	/**
	 * @return the transactionsReceived
	 */
	public int getTransactionsReceived() {
		return transactionsReceived;
	}
	/**
	 * @param transactionsReceived the transactionsReceived to set
	 */
	public void setTransactionsReceived(int transactionsReceived) {
		this.transactionsReceived = transactionsReceived;
	}
	/**
	 * @return the transactionProccessed
	 */
	public int getTransactionsProcessed() {
		return transactionsProcessed;
	}
	/**
	 * @param transactionProccessed the transactionProccessed to set
	 */
	public void setTransactionsProcessed(int transactionProccessed) {
		this.transactionsProcessed = transactionProccessed;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
	   	.append("transactionsReceived" , transactionsReceived)
	   	.append("transactionsProcessed",transactionsProcessed)
	   	.toString();		
	}
}
