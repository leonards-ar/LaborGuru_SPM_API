package com.laborguru.service.profile.dao;

import java.util.List;

import com.laborguru.model.Profile;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ProfileDao {

	List<Profile> findAll();
	
}
