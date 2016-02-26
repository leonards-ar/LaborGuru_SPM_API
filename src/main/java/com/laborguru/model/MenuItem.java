package com.laborguru.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.laborguru.model.comparator.MenuItemPositionComparator;

/**
 * This class represents an element on the front end application menu
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class MenuItem extends SpmObject {
	
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	private String labelKey;
	private String helpKey;
	private String target;
	private Integer position;
	private MenuItem parentMenuItem;

	private Permission permission;
	
	private Set<MenuItem> childMenuItems;
	
	/**
	 * MenuItem toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
	   	.append("id" , id)
	   	.append("labelKey",labelKey)
	   	.append("target",target)
	   	.append("position",position)
	   	.toString();		
	}		
	
	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getHelpKey() {
		return helpKey;
	}

	public void setHelpKey(String helpKey) {
		this.helpKey = helpKey;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	public void setParentMenuItem(MenuItem parent) {
		this.parentMenuItem = parent;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.parentMenuItem)
		.append(this.labelKey)
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
		
		final MenuItem other = (MenuItem) obj;
		
		return new EqualsBuilder()
		.append(this.parentMenuItem, other.parentMenuItem)
		.append(this.labelKey, other.labelKey)		
		.isEquals();
	}

	public Set<MenuItem> getChildMenuItems() {
		if(childMenuItems == null) {
			setChildMenuItems(new HashSet<MenuItem>());
		}
		return this.childMenuItems;
	}

	/**
	 * 
	 * @return
	 */
	public List<MenuItem> getOrderedChildMenuItems() {
		if(this.childMenuItems != null && this.childMenuItems.size() > 0) {
			List<MenuItem> ordered = new ArrayList<MenuItem>(childMenuItems);
			Collections.sort(ordered, new MenuItemPositionComparator());			
			return ordered;
		} else {
			return new ArrayList<MenuItem>();
		}
	}
	
	/**
	 * We leave it private to enforce the cardinality with the addChildMenuItem.
	 * DO NOT MAKE IT PUBLIC
	 * @param childMenuItems
	 */
	private void setChildMenuItems(Set<MenuItem> childMenuItems) {
		this.childMenuItems = childMenuItems;
	}

	/**
	 * 
	 * @param childMenuItem
	 */
	public void addChildMenuItem(MenuItem childMenuItem){
		
		if (childMenuItem == null){
			throw new IllegalArgumentException("Null child menu item passed in as parameter");
		}
		
		if (childMenuItem.getParentMenuItem() != null){
			childMenuItem.getParentMenuItem().getChildMenuItems().remove(childMenuItem);
		}
		
		childMenuItem.setParentMenuItem(this);
		getChildMenuItems().add(childMenuItem);
	}
	
	public String getMenuTarget() {
		if(target == null) {
			return getOrderedChildMenuItems().get(0).target;
		}
		return target;
	}
}
