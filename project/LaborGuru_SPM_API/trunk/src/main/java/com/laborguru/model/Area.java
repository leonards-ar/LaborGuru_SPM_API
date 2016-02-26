package com.laborguru.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.SpmConstants;

/**
 * Area business object
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class Area extends SpmObject {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Region region;
	
	private Set<Store> stores;

	/**
	 * Area toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("name",name)
	   	.toString();		
	}
	
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.name)
		.append(this.region!=null?this.region.getId():null)
		.toHashCode();
	}
	
	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final Area other = (Area) obj;
		
		return new EqualsBuilder()
		.append(this.name, other.name)
		.append(this.region!=null?this.region.getId():null, other.region!=null?other.region.getId():null)
		.isEquals();
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
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return the stores
	 */
	public Set<Store> getStores() {
		return stores;
	}

	/**
	 * @param stores the stores to set
	 */
	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}
	
	public Double getAverageVariable() {
	    
	    Double averageVariable = SpmConstants.DOUBLE_ZERO_VALUE;
	    
	    for(Store store: getStores()) {
	        averageVariable += store.getMainVariableAverage() != null ? store.getMainVariableAverage() : SpmConstants.DOUBLE_ONE_VALUE;
	    }
	    
	    return averageVariable;
	}
	
}
