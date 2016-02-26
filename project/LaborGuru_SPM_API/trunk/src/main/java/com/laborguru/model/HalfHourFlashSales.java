package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;


/**
 * Deals with Half an hour historic sales behaviour.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourFlashSales extends HalfHourSalesValue implements Comparable<HalfHourFlashSales> {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal value = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private BigDecimal secondValue = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private BigDecimal thirdValue = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private BigDecimal fourthValue = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	
	private DailyHistoricSales dailyHistoricSales;
	private Date time;
	
	
	public HalfHourFlashSales(){
		
	}
		
	/**
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

		final HalfHourFlashSales other = (HalfHourFlashSales) obj;
		
		return new EqualsBuilder()
		.append(getValue(), other.getValue())
		.append(getTime(), other.getTime())
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getValue())
		.append(getTime())
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("value",getValue())
	   	.append("time",getTime())
	   	.toString();	
	}

	/**
	 * @param dailyHistoricSales
	 */
	public void setDailyHistoricSales(DailyHistoricSales dailyHistoricSales) {
		this.dailyHistoricSales = dailyHistoricSales;
	}

	/**
	 * @return
	 */
	public DailyHistoricSales getDailyHistoricSales() {
		return dailyHistoricSales;
	}	


	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	
	/**
	 * Sets time 
	 * @param time the time to set
	 */
	public void setTime(String time) {
		setTime(CalendarUtils.displayTimeToDate(time));
	}	
	
	/**
	 * Returns time as string if not null.
	 * @return the time as string
	 */
	public String getTimeAsString(){
		
		if (time != null)
			return CalendarUtils.dateToDisplayTime(this.getTime());
		
		return null;
	}

	/**
	 * Compares to HalfHourHistoricSales by time
	 * @param object
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(HalfHourFlashSales object) {

		if (object == null){
			return 1;
		}
		
		Date anotherDate = object.getTime();
		
		if (getTime() != null){
			return (anotherDate != null)? getTime().compareTo(anotherDate): 1;
		}
		
		return (anotherDate != null)?-1:0;
	}

	/**
	 * @return the secondValue
	 */
	public BigDecimal getSecondValue() {
		return secondValue;
	}

	/**
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(BigDecimal secondValue) {
		this.secondValue = secondValue;
	}

	/**
	 * @return the thirdValue
	 */
	public BigDecimal getThirdValue() {
		return thirdValue;
	}

	/**
	 * @param thirdValue the thirdValue to set
	 */
	public void setThirdValue(BigDecimal thirdValue) {
		this.thirdValue = thirdValue;
	}

	/**
	 * @return the fourthValue
	 */
	public BigDecimal getFourthValue() {
		return fourthValue;
	}

	/**
	 * @param fourthValue the fourthValue to set
	 */
	public void setFourthValue(BigDecimal fourthValue) {
		this.fourthValue = fourthValue;
	}
}
