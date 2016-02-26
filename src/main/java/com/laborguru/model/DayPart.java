/*
 * File name: DayPart.java
 * Creation date: 13/07/2008 11:38:54
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;

/**
 * This class is the abstraction of a store day part.
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class DayPart extends SpmObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3882219454178476292L;

	private Integer id;
	private Store store;
	private Date startHour;
	private Integer positionIndex;
	private String name;
	
	/**
	 * 
	 */
	public DayPart() {
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
		
		final DayPart other = (DayPart) obj;
		
		return new EqualsBuilder()
		.append(this.name, other.name)
		.append(this.store != null ? this.store.getId() : null, other.store != null ? other.store.getId() : null)
		.isEquals();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.name)
		.append(this.store != null ? this.store.getId() : null)
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id", id)
	   	.append("name", name)
	   	.append("store", store)
	   	.append("start hour", startHour)
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
	 * @return the startHour
	 */
	public Date getStartHour() {
		return startHour;
	}

	/**
	 * @param startHour the startHour to set
	 */
	public void setStartHour(Date startHour) {
		this.startHour = CalendarUtils.removeDateFromTime(startHour);
	}

	/**
	 * @return the positionIndex
	 */
	public Integer getPositionIndex() {
		return positionIndex;
	}

	/**
	 * @param positionIndex the positionIndex to set
	 */
	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
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

}
