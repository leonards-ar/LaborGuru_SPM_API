/*
 * File name: StoreVariableDefinition.java
 * Creation date: Sep 19, 2009 9:27:59 PM
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.StringTokenizer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreVariableDefinition extends SpmObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_VARIABLE_DEFINITIONS_QUANTITY = 4;
	
	private Integer id;
	private Store store;
	private String name;
	private Integer variableIndex;
	private Double average;
	
	/**
	 * 
	 */
	public StoreVariableDefinition() {
	}

	/**
	 * @param obj
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		
		
		StoreVariableDefinition other = (StoreVariableDefinition) obj;
		
		return new EqualsBuilder()
		.append(this.store != null? this.store.getId():null, other.store != null? other.store.getId():null)
		.append(this.variableIndex, other.variableIndex)
		.isEquals();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.variableIndex)
		.append(this.store != null? this.store.getId():null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("name",name)
	   	.append("store",getStore())
	   	.append("variableIndex", getVariableIndex())
	   	.toString();	
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the variableIndex
	 */
	public Integer getVariableIndex() {
		return variableIndex;
	}

	/**
	 * @param variableIndex the variableIndex to set
	 */
	public void setVariableIndex(Integer variableIndex) {
		this.variableIndex = variableIndex;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getVariablePosition() {
		return getVariableIndex() != null ? new Integer(getVariableIndex().intValue() + 1) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVariableInitials() {
		if(getName() != null) {
			StringTokenizer st = new StringTokenizer(getName());
			StringBuffer initials = new StringBuffer();
			String token;
			while(st.hasMoreTokens()) {
				token = st.nextToken();
				if(token != null && token.trim().length() > 0) {
					initials.append(token.trim().charAt(0));
				}
			}
			return initials.toString().toUpperCase();
		} else {
			return null;
		}
	}

	/**
	 * @return the average
	 */
	public Double getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(Double average) {
		this.average = average;
	}
}
