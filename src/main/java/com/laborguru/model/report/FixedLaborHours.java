package com.laborguru.model.report;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.SpmObject;

public class FixedLaborHours extends SpmObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345100028198191437L;
	private Double serviceHours;
	private Double openHours;
	private Double closeHours;
	private Double flexHours;
	/**
	 * @return the serviceHours
	 */
	public Double getServiceHours() {
		return (serviceHours != null)?serviceHours:0.0;
	}
	/**
	 * @param serviceHours the serviceHours to set
	 */
	public void setServiceHours(Double serviceHours) {
		this.serviceHours = serviceHours;
	}
	/**
	 * @return the openHours
	 */
	public Double getOpenHours() {
		return (openHours!=null)?openHours:0.0;
	}
	/**
	 * @param openHours the openHours to set
	 */
	public void setOpenHours(Double openHours) {
		this.openHours = openHours;
	}
	/**
	 * @return the closeHours
	 */
	public Double getCloseHours() {
		return (closeHours!=null)?closeHours:0.0;
	}
	/**
	 * @param closeHours the closeHours to set
	 */
	public void setCloseHours(Double closeHours) {
		this.closeHours = closeHours;
	}
	/**
	 * @return the flexHours
	 */
	public Double getFlexHours() {
		return (flexHours!=null)?flexHours:0.0;
	}
	/**
	 * @param flexHours the flexHours to set
	 */
	public void setFlexHours(Double flexHours) {
		this.flexHours = flexHours;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final FixedLaborHours other = (FixedLaborHours) obj;
		
		return new EqualsBuilder().append(this.serviceHours, other.serviceHours)
		.append(this.openHours, other.openHours)
		.append(this.closeHours, other.closeHours)
		.append(this.flexHours, other.flexHours)
		.isEquals();		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.serviceHours)
		.append(this.openHours)
		.append(this.closeHours)
		.append(this.flexHours)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
		.append(this.serviceHours)
		.append(this.openHours)
		.append(this.closeHours)
		.append(this.flexHours).toString();
	}
	
	
}
