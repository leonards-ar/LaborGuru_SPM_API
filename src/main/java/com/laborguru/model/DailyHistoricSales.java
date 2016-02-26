package com.laborguru.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.laborguru.util.SpmConstants;

/**
 * Deals with the Daily projection behaviour.
 * Holds a list of half an hour projections.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DailyHistoricSales extends DailySalesValue {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OF_HALF_HOURS = 48;

	private List<HalfHourHistoricSales> halfHourHistoricSales = new ArrayList<HalfHourHistoricSales>(NUMBER_OF_HALF_HOURS);
	
	@Override
	public void loadDailyValues() {
		BigDecimal value1 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value2 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value3 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value4 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		
		for (HalfHourHistoricSales aHalfHourHistoricSales: getHalfHourHistoricSales()) {
			value1 = value1.add(aHalfHourHistoricSales.getValue() != null ? aHalfHourHistoricSales.getValue() : SpmConstants.BD_ZERO_VALUE);
			value2 = value2.add(aHalfHourHistoricSales.getSecondValue() != null ? aHalfHourHistoricSales.getSecondValue() : SpmConstants.BD_ZERO_VALUE);
			value3 = value3.add(aHalfHourHistoricSales.getThirdValue() != null ? aHalfHourHistoricSales.getThirdValue() : SpmConstants.BD_ZERO_VALUE);
			value4 = value4.add(aHalfHourHistoricSales.getFourthValue() != null ? aHalfHourHistoricSales.getFourthValue() : SpmConstants.BD_ZERO_VALUE);
		}

		setDailySalesValue(value1);
		setDailyProjectionVariable2(value2);
		setDailyProjectionVariable3(value3);
		setDailyProjectionVariable4(value4);
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		final DailyHistoricSales other = (DailyHistoricSales) obj;
		
		return new EqualsBuilder()
		.append(getSalesDate(), other.getSalesDate())
		.isEquals();		
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getSalesDate())
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("date",getSalesDate())
	   	.append("Store Id",getStore()!= null?getStore().getId():null)
	   	.toString();		
	}

	/**
	 * @return the halfHourHistoricSales
	 */
	public List<HalfHourHistoricSales> getHalfHourHistoricSales() {
		return halfHourHistoricSales;
	}

	/**
	 * @param halfHourHistoricSales the halfHourHistoricSales to set
	 */
	@SuppressWarnings("unused")
	private void setHalfHourHistoricSales(List<HalfHourHistoricSales> halfHourHistoricSales) {
		this.halfHourHistoricSales = halfHourHistoricSales;
	}
	
	
	/**
	 * Adds a HalfHourHistoricSales. Handles the bi-directional
	 * relation.
	 * @param halfHourHistoricSales The HalfHourHistoricSales to add
	 */
	public void addHalfHourHistoricSales(HalfHourHistoricSales halfHourHistoricSales){
		
		if (halfHourHistoricSales == null){
			throw new IllegalArgumentException("Null halfHourHistoricSales passed in as parameter");
		}
		
		if (halfHourHistoricSales.getDailyHistoricSales() != null){
			halfHourHistoricSales.getDailyHistoricSales().getHalfHourHistoricSales().remove(halfHourHistoricSales);
		}
		
		halfHourHistoricSales.setDailyHistoricSales(this);
		getHalfHourHistoricSales().add(halfHourHistoricSales);
	}
	
	/**
	 * Removes HalfHourHistoricSales from the historic sales list. Handles the bi-directional
	 * relation.
	 * @param halfHourHistoricSales The halfHourHistoricSales to remove
	 */
	public void removeHalfHourHistoricSales(HalfHourHistoricSales halfHourHistoricSales){
		
		if (halfHourHistoricSales == null){
			throw new IllegalArgumentException("Null halfHourHistoricSales passed in as parameter");
		}
				
		getHalfHourHistoricSales().remove(halfHourHistoricSales);
		
		if (halfHourHistoricSales.getDailyHistoricSales() != null){
			halfHourHistoricSales.setDailyHistoricSales(null);
		}
	}
	
	/**
	 * Returns an empty instance of this class.
	 * :TODO: Move to another class and/or package? Too many logic for a model class??
	 * @param store
	 * @param date
	 * @return
	 */
	public static DailyHistoricSales getEmptyDailyHistoricSalesInstance(Store store, Date date) {
		DailyHistoricSales dailyHistoricSales = new DailyHistoricSales();
		dailyHistoricSales.setSalesDate(date);
		dailyHistoricSales.setStore(store);
		
		HalfHourHistoricSales aHalfHourHistoricSales;
		DateTime nextTime = new DateTime().withDate(1970, 1, 1).withTime(0,30,0,0);
		
		for(int i = 0; i < SpmConstants.HALF_HOURS_IN_A_DAY; i++) {
			aHalfHourHistoricSales = new HalfHourHistoricSales();
			aHalfHourHistoricSales.setTime(nextTime.toDate());
			aHalfHourHistoricSales.setValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
			dailyHistoricSales.addHalfHourHistoricSales(aHalfHourHistoricSales);
			
			nextTime = nextTime.plusMinutes(SpmConstants.HALF_HOUR);
		}
		
		return dailyHistoricSales;
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.model.DailySalesValue#getHalfHourSalesValues()
	 */
	@Override
	public List<? extends HalfHourSalesValue> getHalfHourSalesValues() {
		return getHalfHourHistoricSales();
	}
}
