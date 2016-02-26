package com.laborguru.model;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class RegionalUser extends User implements Manager{

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Region region;

	public RegionalUser(){
		
	}
	
	public RegionalUser(User user){
		super(user);
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)&& region.equals(((RegionalUser)obj).getRegion());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE).appendSuper(super.toString())
		.append("region", region).toString();
	}	
	
	

}
