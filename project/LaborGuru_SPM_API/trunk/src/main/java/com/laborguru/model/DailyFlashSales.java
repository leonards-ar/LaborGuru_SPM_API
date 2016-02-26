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
public class DailyFlashSales extends DailySalesValue {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OF_HALF_HOURS = 48;

	private List<HalfHourFlashSales> halfHourFlashSales = new ArrayList<HalfHourFlashSales>(NUMBER_OF_HALF_HOURS);
	
	@Override
	public void loadDailyValues() {
		BigDecimal value1 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value2 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value3 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal value4 = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		
		for (HalfHourFlashSales aHalfHourFlashSales: getHalfHourFlashSales()) {
			value1 = value1.add(aHalfHourFlashSales.getValue() != null ? aHalfHourFlashSales.getValue() : SpmConstants.BD_ZERO_VALUE);
			value2 = value2.add(aHalfHourFlashSales.getSecondValue() != null ? aHalfHourFlashSales.getSecondValue() : SpmConstants.BD_ZERO_VALUE);
			value3 = value3.add(aHalfHourFlashSales.getThirdValue() != null ? aHalfHourFlashSales.getThirdValue() : SpmConstants.BD_ZERO_VALUE);
			value4 = value4.add(aHalfHourFlashSales.getFourthValue() != null ? aHalfHourFlashSales.getFourthValue() : SpmConstants.BD_ZERO_VALUE);
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

		final DailyFlashSales other = (DailyFlashSales) obj;
		
		return new EqualsBuilder()
		.append(getSalesDate(), other.getHalfHourFlashSales())
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
	 * @return the halfHourFlashSales
	 */
	public List<HalfHourFlashSales> getHalfHourFlashSales() {
		return halfHourFlashSales;
	}

	/**
	 * @param halfHourFlashSales the halfHourFlashSales to set
	 */
	@SuppressWarnings("unused")
	private void setHalfHourFlashSales(List<HalfHourFlashSales> halfHourFlashSales) {
		this.halfHourFlashSales = halfHourFlashSales;
	}
	
	
	/**
	 * Adds a HalfHourFlashSales. Handles the bi-directional
	 * relation.
	 * @param halfHourFlashSales The HalfHourFlashSales to add
	 */
	public void addHalfHourFlashSales(HalfHourFlashSales halfHourFlashSales){
		
		if (halfHourFlashSales == null){
			throw new IllegalArgumentException("Null halfHourFlashSales passed in as parameter");
		}
		
		if (halfHourFlashSales.getDailyHistoricSales() != null){
			//halfHourFlashSales.getDailyHistoricSales().getHalfHourFlashSales().remove(halfHourFlashSales);
		}
		
		//halfHourFlashSales.setDailyHistoricSales(this);
		getHalfHourFlashSales().add(halfHourFlashSales);
	}
	
	/**
	 * Removes HalfHourFlashSales from the historic sales list. Handles the bi-directional
	 * relation.
	 * @param halfHourFlashSales The halfHourFlashSales to remove
	 */
	public void removeHalfHourFlashSales(HalfHourFlashSales halfHourFlashSales){
		
		if (halfHourFlashSales == null){
			throw new IllegalArgumentException("Null halfHourFlashSales passed in as parameter");
		}
				
		getHalfHourFlashSales().remove(halfHourFlashSales);
		
		if (halfHourFlashSales.getDailyHistoricSales() != null){
			halfHourFlashSales.setDailyHistoricSales(null);
		}
	}
	
	/**
	 * Returns an empty instance of this class.
	 * :TODO: Move to another class and/or package? Too many logic for a model class??
	 * @param store
	 * @param date
	 * @return
	 */
	public static DailyFlashSales getEmptyDailyHistoricSalesInstance(Store store, Date date) {
		DailyFlashSales dailyHistoricSales = new DailyFlashSales();
		dailyHistoricSales.setSalesDate(date);
		dailyHistoricSales.setStore(store);
		
		HalfHourFlashSales aHalfHourFlashSales;
		DateTime nextTime = new DateTime().withDate(1970, 1, 1).withTime(0,30,0,0);
		
		for(int i = 0; i < SpmConstants.HALF_HOURS_IN_A_DAY; i++) {
			aHalfHourFlashSales = new HalfHourFlashSales();
			aHalfHourFlashSales.setTime(nextTime.toDate());
			aHalfHourFlashSales.setValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
			dailyHistoricSales.addHalfHourFlashSales(aHalfHourFlashSales);
			
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
		return getHalfHourFlashSales();
	}
}
