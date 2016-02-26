package com.laborguru.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.comparator.ComparableObject;

/**
 * Area business object
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */

public class PositionGroup extends SpmObject implements ComparableObject {

	private static final long serialVersionUID = 1L;
	
	private static final String UID_PREFIX = "pg_";
	
	private Integer id;
	private String name;
	private Store store;
	private Set<Position> positions;
	
	
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
	 * @return the positions
	 */
	public Set<Position> getPositions() {
		if(positions == null) {
			setPositions(new HashSet<Position>());
		}
		return positions;
	}

	/**
	 * @param positions the positions to set
	 */
	private void setPositions(Set<Position> positions) {
		this.positions = positions;
	}
	
	/**
	 * 
	 * @param position
	 */
	public void addPosition(Position position){
		
		if (position == null){
			throw new IllegalArgumentException("Null position passed in as parameter");
		}

		if(position.getPositionGroup() != null) {
			position.getPositionGroup().getPositions().remove(position);
		}
		
		position.setPositionGroup(this);
		getPositions().add(position);
	}

	/**
	 * 
	 * @param position
	 */
	public void removePosition(Position position){
		
		if (position == null){
			throw new IllegalArgumentException("Null position passed in as parameter");
		}

		getPositions().remove(position);

		if(position.getPositionGroup() != null) {
			position.setPositionGroup(null);
		}		
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) 
			return false;
		
		if(!(obj instanceof PositionGroup)) 
			return false;
		
		if(this == obj)
			return true;
		
		PositionGroup other = (PositionGroup)obj;
		
		return new EqualsBuilder().append(this.name, other.name)
		.append(this.getStore() != null ? this.getStore().getId() : null, other.getStore() != null ? other.getStore().getId() : null)
		.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name)
		.append(this.store != null ? this.store.getId() : null)
		.toHashCode();
	}

	/**
	 * PositionGroup toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("name",name)
	   	.toString();		
	}

	/**
	 * 
	 */
	public PositionGroup() {
		this(null);
	}
	
	/**
	 * @param name
	 */
	public PositionGroup(String name) {
		super();
		setName(name);
	}

	/**
	 * 
	 * @return
	 */
	public String getUniqueId() {
		return new StringBuilder().append(UID_PREFIX).append(getId()).toString();
	}
	
	/**
	 * 
	 * @param uniqueId
	 */
	public static Integer getId(String uniqueId) {
		if(isValidUniqueId(uniqueId)) {
			return new Integer(uniqueId.substring(UID_PREFIX.length()));
		} else {
			throw new IllegalArgumentException("Invalid position unique id [" + uniqueId + "]");
		}
	}
	
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	public static boolean isValidUniqueId(String uniqueId) {
		return uniqueId != null && uniqueId.startsWith(UID_PREFIX);
	}	
}
