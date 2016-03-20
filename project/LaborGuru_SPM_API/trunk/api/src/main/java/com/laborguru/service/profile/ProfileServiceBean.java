package com.laborguru.service.profile;

import java.util.List;

import com.laborguru.model.Profile;
import com.laborguru.service.profile.dao.ProfileDao;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ProfileServiceBean implements ProfileService {

	private ProfileDao profileDao;
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.profile.ProfileService#findAll()
	 */
	public List<Profile> findAll() {
		return profileDao.findAll();
	}
	/**
	 * @return the profileDao
	 */
	public ProfileDao getProfileDao() {
		return profileDao;
	}
	/**
	 * @param profileDao the profileDao to set
	 */
	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}
	
	

}
