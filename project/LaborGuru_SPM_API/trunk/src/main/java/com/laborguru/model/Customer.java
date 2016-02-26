package com.laborguru.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Customer business object
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class Customer extends SpmObject {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String code;
	
	private Set<Region> regions;
	
	/**
	 * Retrieves a region that belongs to the customer by region name
	 * @param regionName
	 * @return a Region
	 */
	public Region getRegionByName(String regionName){
		for (Region region : getRegions()){
			if (region.getName().equalsIgnoreCase(regionName)){
				return region;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Customer toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("name",name)
	   	.append("code", code)
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
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name).toHashCode();
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

		final Customer other = (Customer) obj;
		
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}
	
	/**
	 * @return the regions
	 */
	public Set<Region> getRegions() {
		if(regions == null) {
			setRegions(new HashSet<Region>());
		}
		return regions;
	}

	/**
	 * @param regions the regions to set
	 */
	private void setRegions(Set<Region> regions) {
		this.regions = regions;
	}	
	
	/**
	 * Adds a region to this customer. Handles the bi-directional
	 * relation.
	 * @param region The region to add
	 */
	public void addRegion(Region region){
		
		if (region == null){
			throw new IllegalArgumentException("Null region passed in as parameter");
		}
		
		if (region.getCustomer() != null){
			region.getCustomer().getRegions().remove(region);
		}
		
		region.setCustomer(this);
		getRegions().add(region);
	}
	
	/**
	 * Removes a region from the customer. Handles the bi-directional
	 * relation.
	 * @param region The region to remove
	 */
	public void removeRegion(Region region){
		
		if (region == null){
			throw new IllegalArgumentException("Null region passed in as parameter");
		}
				
		getRegions().remove(region);
		
		if (region.getCustomer() != null){
			region.setCustomer(null);
		}
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}	
	
}
