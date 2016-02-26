package com.laborguru.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Profile Type
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class Profile extends SpmObject {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String description;
	private String homeResult;
	
	/**
	 * Profile toString
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
	
	private Set<Permission> permissions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 */
	public Set<Permission> getPermissions() {
		if(permissions == null) {
			setPermissions(new HashSet<Permission>());
		}
		return permissions;
	}

	
	/**
	 * We leave it private to enforce the cardinality with the addPermissions.
	 * DO NOT MAKE IT PUBLIC
	 * @param permissions
	 */
	private void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @param permission
	 */
	public void addPermission(Permission permission){
		
		if (permission == null){
			throw new IllegalArgumentException("Null permission passed in as parameter");
		}

		getPermissions().add(permission);
	}	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param permission
	 * @return
	 */
	public boolean hasPermission(Permission permission) {
		return getPermissions() != null && getPermissions().contains(permission);
	}
	
	
	/* (non-Javadoc)
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name).toHashCode();
	}

	/* (non-Javadoc)
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		final Profile other = (Profile) obj;
		
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	/**
	 * @return the homeResult
	 */
	public String getHomeResult() {
		return homeResult;
	}

	/**
	 * @param homeResult the homeResult to set
	 */
	public void setHomeResult(String homeResult) {
		this.homeResult = homeResult;
	}


}
