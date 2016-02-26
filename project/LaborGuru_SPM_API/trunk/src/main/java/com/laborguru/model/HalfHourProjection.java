package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Deals with Half an hour projection behaviour.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourProjection extends HalfHourSalesValue implements Comparable<HalfHourProjection> {
	
	private static final long serialVersionUID = 1L;
	

	private DailyProjection projection;
	
	
	public HalfHourProjection(){
		
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

		final HalfHourProjection other = (HalfHourProjection) obj;
		
		return new EqualsBuilder()
		.append(getAdjustedValue(), other.getAdjustedValue())
		.append(getIndex(), other.getIndex())
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getAdjustedValue())
		.append(getIndex())
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
	   	.append("adjustedValue",getAdjustedValue())
	   	.append("index",getIndex())
	   	.toString();	
	}

	/**
	 * @param projection
	 */
	public void setProjection(DailyProjection projection) {
		this.projection = projection;
	}

	/**
	 * @return
	 */
	public DailyProjection getProjection() {
		return projection;
	}	
	
	/**
	 * @return the adjustedValue
	 */
	public BigDecimal getAdjustedValue() {
		return getValue();
	}

	/**
	 * @param adjustedValue the adjustedValue to set
	 */
	public void setAdjustedValue(BigDecimal adjustedValue) {
		setValue(adjustedValue);
	}

	/**
	 * Compares to HalfHourProject by time
	 * @param object
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(HalfHourProjection object) {

		if (object == null){
			return 1;
		}
		
		Date anotherDate = object.getTime();
		
		if (getTime() != null){
			return (anotherDate != null)? getTime().compareTo(anotherDate): 1;
		}
		
		return (anotherDate != null)?-1:0;
	}
}
