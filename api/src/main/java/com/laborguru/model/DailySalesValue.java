/*
 * File name: DailySalesValue.java
 * Creation date: Feb 17, 2009 5:24:30 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class DailySalesValue extends SpmObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date salesDate;
	private Date startingTime;
	
	private Store store;

	private BigDecimal dailySalesValue = null;
	private BigDecimal dailyProjectionVariable2;
	private BigDecimal dailyProjectionVariable3;
	private BigDecimal dailyProjectionVariable4;
	
	/**
	 * 
	 */
	public DailySalesValue() {
	}

	/**
	 * 
	 * @return
	 */
	public abstract List<? extends HalfHourSalesValue> getHalfHourSalesValues();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the startingTime
	 */
	public Date getStartingTime() {
		return startingTime;
	}

	/**
	 * @param startingTime the startingTime to set
	 */
	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the salesDate
	 */
	public Date getSalesDate() {
		return salesDate;
	}

	/**
	 * @param salesDate the salesDate to set
	 */
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}
	
	/**
	 * Returns the sum of all the halfhours defined for the projection.
	 * If there is no halfhours returns NULL
	 * @return the projection value or null
	 */
	public BigDecimal getDailySalesValue() {
		if(dailySalesValue == null) {
			loadDailyValues();
		}
		return dailySalesValue;
	}
	
	public void loadDailyValues() {
		BigDecimal retValue = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		
		for (HalfHourSalesValue aHalfHourSalesValue: getHalfHourSalesValues()){
			retValue = retValue.add(aHalfHourSalesValue.getValue());
		}
		
		setDailySalesValue(retValue);
	}
	
	/**
	 * @return the dailyProjectionVariable3
	 */
	public BigDecimal getDailyProjectionVariable3() {
		
		if(dailyProjectionVariable3 == null) {
			setDailyProjectionVariable3(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		return dailyProjectionVariable3;
	}

	/**
	 * @param dailyProjectionVariable3 the dailyProjectionVariable3 to set
	 */
	public void setDailyProjectionVariable3(BigDecimal dailyProjectionVariable3) {
		this.dailyProjectionVariable3 = dailyProjectionVariable3;
	}

	/**
	 * @return the dailyProjectionVariable2
	 */
	public BigDecimal getDailyProjectionVariable2() {
		
		if(dailyProjectionVariable2 == null) {			
			setDailyProjectionVariable2(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		return dailyProjectionVariable2;
	}

	/**
	 * @param dailyProjectionVariable2 the dailyProjectionVariable2 to set
	 */
	public void setDailyProjectionVariable2(BigDecimal dailyProjectionVariable2) {
		this.dailyProjectionVariable2 = dailyProjectionVariable2;
	}
	/**
	 * @return the dailyProjectionVariable4
	 */
	public BigDecimal getDailyProjectionVariable4() {
		if(dailyProjectionVariable4 == null) {			
			setDailyProjectionVariable4(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		
		return dailyProjectionVariable4;
	}

	/**
	 * @param dailyProjectionVariable4 the dailyProjectionVariable4 to set
	 */
	public void setDailyProjectionVariable4(BigDecimal dailyProjectionVariable4) {
		this.dailyProjectionVariable4 = dailyProjectionVariable4;
	}

	/**
	 * @param dailySalesValue the dailySalesValue to set
	 */
	public void setDailySalesValue(BigDecimal dailySalesValue) {
		this.dailySalesValue = dailySalesValue;
	}	
	
	public BigDecimal getTotalDailyValue() {
		return getDailySalesValue().add(getDailyProjectionVariable2()).add(getDailyProjectionVariable3()).add(getDailyProjectionVariable4());
	}
}
