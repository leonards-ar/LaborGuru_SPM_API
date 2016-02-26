package com.laborguru.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Permission Type
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class Permission extends SpmObject {
	
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	private String name;
	private String description;
		
	private List<MenuItem> menuItems;

	
	/**
	 * Permission toString
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		
		final Permission other = (Permission) obj;
		
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}
	
	
	public void addMenuItem(MenuItem menuItem){
		
		if (menuItem == null){
			throw new IllegalArgumentException("Null child menu item passed in as parameter");
		}

		if (menuItem.getPermission() != null){
			menuItem.getPermission().menuItems.remove(menuItem);
		}
		
		menuItem.setPermission(this);
		getMenuItems().add(menuItem);
	}

	/**
	 * @return the menuItems
	 */
	public List<MenuItem> getMenuItems() {
		if(menuItems == null) {
			menuItems = new ArrayList<MenuItem>();
		}
		return menuItems;
	}

	/**
	 * @param menuItems the menuItems to set
	 */
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}
