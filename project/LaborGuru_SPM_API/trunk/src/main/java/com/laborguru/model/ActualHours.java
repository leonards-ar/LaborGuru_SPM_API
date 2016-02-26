package com.laborguru.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ActualHours extends SpmObject {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date date;
	private Double hours;	
	private Store store;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		final ActualHours other = (ActualHours) obj;
		
		return new EqualsBuilder()
		.append(getDate(), other.getDate())
		.append(getHours(), other.getHours())
		.isEquals();	
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getDate())
		.append(getHours())
		.toHashCode();	
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("date",getDate())
	   	.append("hours",getHours())	   	
	   	.append("store",getStoreId())
	   	.toString();		
	}

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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the hours
	 */
	public Double getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Double hours) {
		this.hours = hours;
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
	 * @return
	 */
	public Integer getStoreId(){
		return getStore()!=null?getStore().getId():null;
	}
}
