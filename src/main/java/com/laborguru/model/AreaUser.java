package com.laborguru.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class AreaUser extends User implements Manager{
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	private Area area;

	public AreaUser() {
		
	}
	
	public AreaUser(User user){
		super(user);
	}
	
	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && area.equals(((AreaUser)obj).getArea());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE).appendSuper(super.toString())
		.append("area", area).toString();
	}	
	
	
	
}
