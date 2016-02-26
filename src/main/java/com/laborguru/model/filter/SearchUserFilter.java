package com.laborguru.model.filter;

import com.laborguru.model.Profile;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SearchUserFilter {

	private Integer userId;
	private String fullName;
	private Profile profile;
	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
